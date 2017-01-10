package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public abstract class MapBasedJSONObject
  implements JSONObject
{
  protected abstract Map<String, Object> getMap();

  /**
   * Produce a string in double quotes with backslash sequences in all the right places. A backslash
   * will be inserted within </, producing <\/, allowing JSON text to be delivered in HTML. In JSON
   * text, a string cannot contain a control character or an unescaped quote or backslash.
   * 
   * @param string
   *          A String
   * @return A String correctly formatted for insertion in a JSON text.
   */
  public static String quote(String string)
  {
    if (string == null || string.length() == 0) {
      return "\"\"";
    }
    StringBuilder sb = new StringBuilder(string.length() + 4);
    sb.append('"');
    try {
      quote(string, sb);
    } catch (IOException e) {
      throw new RuntimeException("StringBuilder should not thrown an IOException", e);
    }
    sb.append('"');
    return sb.toString();
  }

  /**
   * Append a string with backslash sequences in all the right places. A backslash will be inserted
   * within </, producing <\/, allowing JSON text to be delivered in HTML. In JSON text, a string
   * cannot contain a control character or an unescaped quote or backslash.
   * 
   * @param string
   *          A String
   * @param buf
   *          The Appendable that the escaped String will be appended onto.
   * @throws IOException
   *           if it is not possible to write to the buf
   */
  public static void quote(String string,
                           Appendable buf)
      throws IOException
  {
    char b;
    char c = 0;
    String hhhh;
    int i;
    int len = string.length();

    for (i = 0; i < len; i += 1) {
      b = c;
      c = string.charAt(i);
      switch (c) {
        case '\\':
        case '"':
          buf.append('\\');
          buf.append(c);
          break;
        case '/':
          if (b == '<') {
            buf.append('\\');
          }
          buf.append(c);
          break;
        case '\b':
          buf.append("\\b");
          break;
        case '\t':
          buf.append("\\t");
          break;
        case '\n':
          buf.append("\\n");
          break;
        case '\f':
          buf.append("\\f");
          break;
        case '\r':
          buf.append("\\r");
          break;
        default:
          if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
            hhhh = "000" + Integer.toHexString(c);
            buf.append("\\u" + hhhh.substring(hhhh.length() - 4));
          } else {
            buf.append(c);
          }
      }
    }
  }

  /**
   * Try to convert a string into a number, boolean, or null. If the string can't be converted,
   * return the string.
   * 
   * @param string
   *          A String.
   * @return A simple JSON value.
   */
  public static Object stringToValue(String string)
  {
    if (string.equals("")) {
      return string;
    }
    if (string.equalsIgnoreCase("true")) {
      return Boolean.TRUE;
    }
    if (string.equalsIgnoreCase("false")) {
      return Boolean.FALSE;
    }
    if (string.equalsIgnoreCase("null")) {
      return JSONObject.NULL;
    }

    /*
     * If it might be a number, try converting it. We support the non-standard 0x- convention. If a
     * number cannot be produced, then the value will just be a string. Note that the 0x-, plus, and
     * implied string conventions are non-standard. A JSON parser may accept non-JSON forms as long
     * as it accepts all correct JSON forms.
     */

    char b = string.charAt(0);
    if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
      if (b == '0' && string.length() > 2 && (string.charAt(1) == 'x' || string.charAt(1) == 'X')) {
        try {
          return Integer.valueOf(Integer.parseInt(string.substring(2), 16));
        } catch (Exception ignore) {
        }
      }
      try {
        if (string.indexOf('.') > -1 || string.indexOf('e') > -1 || string.indexOf('E') > -1) {
          return Double.valueOf(string);
        } else {
          Long myLong = Long.valueOf(string);
          if (myLong.longValue() == myLong.intValue()) {
            return Integer.valueOf(myLong.intValue());
          } else {
            return myLong;
          }
        }
      } catch (Exception ignore) {
      }
    }
    return string;
  }

  /**
   * Throw an exception if the object is a NaN or infinite number.
   * 
   * @param o
   *          The object to test.
   * @throws JSONException
   *           If o is a non-finite number.
   */
  public static void testValidity(Object o)
      throws JSONException
  {
    if (o != null) {
      if (o instanceof Double) {
        if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
          throw new JSONException("JSON does not allow non-finite numbers.");
        }
      } else if (o instanceof Float) {
        if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
          throw new JSONException("JSON does not allow non-finite numbers.");
        }
      }
    }
  }

  /**
   * Produce a string from a double. The string "null" will be returned if the number is not finite.
   * 
   * @param d
   *          A double.
   * @return A String.
   */
  public static String doubleToString(double d)
  {
    if (Double.isInfinite(d) || Double.isNaN(d)) {
      return "null";
    }

    // Shave off trailing zeros and decimal point, if possible.

    String string = Double.toString(d);
    if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0) {
      while (string.endsWith("0")) {
        string = string.substring(0, string.length() - 1);
      }
      if (string.endsWith(".")) {
        string = string.substring(0, string.length() - 1);
      }
    }
    return string;
  }

  /**
   * Get an array of field names from a JSONObject.
   * 
   * @return An array of field names, or null if there are no names.
   */
  public static String[] getNames(WritableJSONObject jo)
  {
    int length = jo.length();
    if (length == 0) {
      return null;
    }
    Iterator<String> iterator = jo.keys();
    String[] names = new String[length];
    int i = 0;
    while (iterator.hasNext()) {
      names[i] = iterator.next();
      i += 1;
    }
    return names;
  }

  /**
   * Get an array of field names from an Object.
   * 
   * @return An array of field names, or null if there are no names.
   */
  public static String[] getNames(Object object)
  {
    if (object == null) {
      return null;
    }
    Class<?> klass = object.getClass();
    Field[] fields = klass.getFields();
    int length = fields.length;
    if (length == 0) {
      return null;
    }
    String[] names = new String[length];
    for (int i = 0; i < length; i += 1) {
      names[i] = fields[i].getName();
    }
    return names;
  }

  /**
   * Get an optional value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return An object which is the value, or null if there is no value.
   */
  @Override
  public final Object opt(String key)
  {
    return key == null ? null : getMap().get(key);
  }

  /**
   * Get the boolean value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The truth.
   * @throws JSONException
   *           if the value is not a Boolean or the String "true" or "false".
   */
  @Override
  public final boolean getBoolean(String key)
      throws JSONException
  {
    Object object = get(key);
    if (object.equals(Boolean.FALSE)
        || (object instanceof String && ((String) object).equalsIgnoreCase("false"))) {
      return false;
    } else if (object.equals(Boolean.TRUE)
               || (object instanceof String && ((String) object).equalsIgnoreCase("true"))) {
      return true;
    }
    throw new JSONException("JSONObject[" + quote(key) + "] is not a Boolean.");
  }

  public static Writer write(JSONObject jObj,
                             Writer writer)
      throws JSONException
  {
    try {
      boolean commanate = false;
      Iterator<String> keys = jObj.keys();
      writer.write('{');

      while (keys.hasNext()) {
        if (commanate) {
          writer.write(',');
        }
        String key = keys.next();
        writer.write(quote(key.toString()));
        writer.write(':');
        Object value = jObj.opt(key);
        if (value instanceof JSONObject) {
          ((JSONObject) value).write(writer);
        } else if (value instanceof JSONArray) {
          ((JSONArray) value).write(writer);
        } else {
          writer.write(WritableJSONObject.valueToString(value));
        }
        commanate = true;
      }
      writer.write('}');
      return writer;
    } catch (IOException exception) {
      throw new JSONException(exception);
    }
  }

  public static String toString(JSONObject jObj)
  {
    try {
      Iterator<String> keys = jObj.keys();
      StringBuilder sb = new StringBuilder("{");

      while (keys.hasNext()) {
        if (sb.length() > 1) {
          sb.append(',');
        }
        String string = keys.next();
        sb.append(quote(string));
        sb.append(':');
        sb.append(valueToString(jObj.opt(string)));
      }
      sb.append('}');
      return sb.toString();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Produce a string from a Number.
   * 
   * @param number
   *          A Number
   * @return A String.
   * @throws JSONException
   *           If n is a non-finite number.
   */
  public static String numberToString(Number number)
      throws JSONException
  {
    if (number == null) {
      throw new JSONException("Null pointer");
    }
    testValidity(number);

    // Shave off trailing zeros and decimal point, if possible.

    String string = number.toString();
    if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0) {
      while (string.endsWith("0")) {
        string = string.substring(0, string.length() - 1);
      }
      if (string.endsWith(".")) {
        string = string.substring(0, string.length() - 1);
      }
    }
    return string;
  }

  public static WritableJSONArray names(JSONObject jObj)
  {
    WritableJSONArray ja = new WritableJSONArray();
    Iterator<String> keys = jObj.keys();
    while (keys.hasNext()) {
      ja.put(keys.next());
    }
    return ja.length() == 0 ? null : ja;
  }

  @Override
  public final boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj instanceof JSONObject) {
      return ((JSONObject) obj).equalsMap(getMap());
    }
    return false;
  }

  @Override
  public final int hashCode()
  {
    return getMap().hashCode();
  }

  /**
   * Get the value object associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The object associated with the key.
   * @throws JSONException
   *           if the key is not found.
   */
  @Override
  public final Object get(String key)
      throws JSONException
  {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    Object object = opt(key);
    if (object == null) {
      throw new JSONException("JSONObject[" + quote(key) + "] not found in " + this);
    }
    return object;
  }

  /**
   * Get the double value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The numeric value.
   * @throws JSONException
   *           if the key is not found or if the value is not a Number object and cannot be
   *           converted to a number.
   */
  @Override
  public final double getDouble(String key)
      throws JSONException
  {
    Object object = get(key);
    try {
      return object instanceof Number
          ? ((Number) object).doubleValue()
          : Double.parseDouble((String) object);
    } catch (Exception e) {
      throw new JSONException("JSONObject[" + quote(key) + "] is not a number.");
    }
  }

  /**
   * Get the int value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The integer value.
   * @throws JSONException
   *           if the key is not found or if the value cannot be converted to an integer.
   */
  @Override
  public final int getInt(String key)
      throws JSONException
  {
    Object object = get(key);
    try {
      return object instanceof Number
          ? ((Number) object).intValue()
          : Integer.parseInt((String) object);
    } catch (Exception e) {
      throw new JSONException("JSONObject[" + quote(key) + "] is not an int.");
    }
  }

  /**
   * Get the JSONArray value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return A JSONArray which is the value.
   * @throws JSONException
   *           if the key is not found or if the value is not a JSONArray.
   */
  @Override
  public JSONArray getJSONArray(String key)
      throws JSONException
  {
    Object object = get(key);
    if (object instanceof JSONArray) {
      return (JSONArray) object;
    }
    throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONArray.");
  }

  /**
   * Get the JSONObject value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   * @throws JSONException
   *           if the key is not found or if the value is not a JSONObject.
   */
  @Override
  public JSONObject getJSONObject(String key)
      throws JSONException
  {
    Object object = get(key);
    if (object instanceof JSONObject) {
      return (JSONObject) object;
    }
    throw new JSONException("JSONObject[" + quote(key) + "] is not a JSONObject.");
  }

  /**
   * Get the long value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The long value.
   * @throws JSONException
   *           if the key is not found or if the value cannot be converted to a long.
   */
  @Override
  public final long getLong(String key)
      throws JSONException
  {
    Object object = get(key);
    try {
      return object instanceof Number
          ? ((Number) object).longValue()
          : Long.parseLong((String) object);
    } catch (Exception e) {
      throw new JSONException("JSONObject[" + quote(key) + "] is not a long.");
    }
  }

  /**
   * Get the string associated with a key.
   * 
   * @param key
   *          A key string.
   * @return A string which is the value.
   * @throws JSONException
   *           if the key is not found.
   */
  @Override
  public final String getString(String key)
      throws JSONException
  {
    Object object = get(key);
    return object == JSONObject.NULL ? null : object.toString();
  }

  /**
   * Determine if the JSONObject contains a specific key.
   * 
   * @param key
   *          A key string.
   * @return true if the key exists in the JSONObject.
   */
  @Override
  public final boolean has(String key)
  {
    return getMap().containsKey(key);
  }

  /**
   * Determine if the value associated with the key is null or if there is no value.
   * 
   * @param key
   *          A key string.
   * @return true if there is no value associated with the key or if the value is the
   *         JSONObject.NULL object.
   */
  @Override
  public final boolean isNull(String key)
  {
    return JSONObject.NULL.equals(opt(key));
  }

  /**
   * Get an enumeration of the keys of the JSONObject.
   * 
   * @return An iterator of the keys.
   */
  @Override
  public final Iterator<String> keys()
  {
    return getMap().keySet().iterator();
  }

  /**
   * Get the number of keys stored in the JSONObject.
   * 
   * @return The number of keys in the JSONObject.
   */
  @Override
  public final int length()
  {
    return getMap().size();
  }

  /**
   * Produce a WritableJSONArray containing the names of the elements of this JSONObject. Note that
   * this is not a view - ie changes to the original JSONObject or the names JSONArray are not
   * connected.
   * 
   * @return A WritableJSONArray containing the key strings, or null if the JSONObject is empty.
   */
  @Override
  public final WritableJSONArray names()
  {
    return MapBasedJSONObject.names(this);
  }

  /**
   * Get an optional boolean associated with a key. It returns false if there is no such key, or if
   * the value is not Boolean.TRUE or the String "true".
   * 
   * @param key
   *          A key string.
   * @return The truth.
   */
  @Override
  public final boolean optBoolean(String key)
  {
    return optBoolean(key, false);
  }

  /**
   * Get an optional boolean associated with a key. It returns the defaultValue if there is no such
   * key, or if it is not a Boolean or the String "true" or "false" (case insensitive).
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return The truth.
   */
  @Override
  public final boolean optBoolean(String key,
                                  boolean defaultValue)
  {
    try {
      return getBoolean(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get an optional double associated with a key, or NaN if there is no such key or if its value is
   * not a number. If the value is a string, an attempt will be made to evaluate it as a number.
   * 
   * @param key
   *          A string which is the key.
   * @return An object which is the value.
   */
  @Override
  public final double optDouble(String key)
  {
    return optDouble(key, Double.NaN);
  }

  /**
   * Get an optional double associated with a key, or the defaultValue if there is no such key or if
   * its value is not a number. If the value is a string, an attempt will be made to evaluate it as
   * a number.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  @Override
  public final double optDouble(String key,
                                double defaultValue)
  {
    try {
      return getDouble(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get an optional int value associated with a key, or zero if there is no such key or if the
   * value is not a number. If the value is a string, an attempt will be made to evaluate it as a
   * number.
   * 
   * @param key
   *          A key string.
   * @return An object which is the value.
   */
  @Override
  public final int optInt(String key)
  {
    return optInt(key, 0);
  }

  /**
   * Get an optional int value associated with a key, or the default if there is no such key or if
   * the value is not a number. If the value is a string, an attempt will be made to evaluate it as
   * a number.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  @Override
  public final int optInt(String key,
                          int defaultValue)
  {
    try {
      return getInt(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  @Override
  public final Integer optInteger(String key,
                                  Integer defaultValue)
  {
    Object object = opt(key);
    if (object == null) {
      return defaultValue;
    }
    if (object instanceof Integer) {
      return (Integer) object;
    }
    if (object instanceof Number) {
      return ((Number) object).intValue();
    }
    try {
      Integer.parseInt(object.toString());
    } catch (Exception e) {
    }
    return defaultValue;
  }

  @Override
  public final Integer optInteger(String key)
  {
    return optInteger(key, null);
  }

  /**
   * Get an optional long value associated with a key, or zero if there is no such key or if the
   * value is not a number. If the value is a string, an attempt will be made to evaluate it as a
   * number.
   * 
   * @param key
   *          A key string.
   * @return An object which is the value.
   */
  @Override
  public final long optLong(String key)
  {
    return optLong(key, 0);
  }

  /**
   * Get an optional long value associated with a key, or the default if there is no such key or if
   * the value is not a number. If the value is a string, an attempt will be made to evaluate it as
   * a number.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  @Override
  public final long optLong(String key,
                            long defaultValue)
  {
    try {
      return getLong(key);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get an optional string associated with a key. It returns an empty string if there is no such
   * key. If the value is not a string and is not null, then it is converted to a string.
   * 
   * @param key
   *          A key string.
   * @return A string which is the value.
   */
  @Override
  public final String optString(String key)
  {
    return optString(key, "");
  }

  /**
   * Get an optional string associated with a key. It returns the defaultValue if there is no such
   * key.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return A string which is the value.
   */
  @Override
  public final String optString(String key,
                                String defaultValue)
  {
    Object object = opt(key);
    return JSONObject.NULL.equals(object) ? defaultValue : object.toString();
  }

  /**
   * Make a JSON text of this JSONObject. For compactness, no whitespace is added. If this would not
   * result in a syntactically correct JSON text, then null will be returned instead.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return a printable, displayable, portable, transmittable representation of the object,
   *         beginning with <code>{</code>&nbsp;<small>(left brace)</small> and ending with
   *         <code>}</code>&nbsp;<small>(right brace)</small>.
   */
  @Override
  public final String toString()
  {
    return toString(this);
  }

  /**
   * Make a prettyprinted JSON text of this JSONObject.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @return a printable, displayable, portable, transmittable representation of the object,
   *         beginning with <code>{</code>&nbsp;<small>(left brace)</small> and ending with
   *         <code>}</code>&nbsp;<small>(right brace)</small>.
   * @throws JSONException
   *           If the object contains an invalid number.
   */
  @Override
  public final String toString(int indentFactor)
  {
    return toString(indentFactor, 0);
  }

  /**
   * Make a prettyprinted JSON text of this JSONObject.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indentation of the top level.
   * @return a printable, displayable, transmittable representation of the object, beginning with
   *         <code>{</code>&nbsp;<small>(left brace)</small> and ending with <code>}</code>
   *         &nbsp;<small>(right brace)</small>.
   * @throws RuntimeException
   *           If the object contains an invalid number.
   */
  @Override
  public final String toString(int indentFactor,
                               int indent)
  {
    return toString(this, indentFactor, indent);
  }

  public static String toString(JSONObject jObj,
                                int indentFactor,
                                int indent)
  {
    try {
      int i;
      int length = jObj.length();
      if (length == 0) {
        return "{}";
      }
      Iterator<String> keys = jObj.sortedKeys();
      int newindent = indent + indentFactor;
      String key;
      StringBuilder sb = new StringBuilder("{");
      if (length == 1) {
        key = keys.next();
        sb.append(quote(key.toString()));
        sb.append(": ");
        sb.append(valueToString(jObj.opt(key), indentFactor, indent));
      } else {
        while (keys.hasNext()) {
          key = keys.next();
          if (sb.length() > 1) {
            sb.append(",\n");
          } else {
            sb.append('\n');
          }
          for (i = 0; i < newindent; i += 1) {
            sb.append(' ');
          }
          sb.append(quote(key.toString()));
          sb.append(": ");
          sb.append(valueToString(jObj.opt(key), indentFactor, newindent));
        }
        if (sb.length() > 1) {
          sb.append('\n');
          for (i = 0; i < indent; i += 1) {
            sb.append(' ');
          }
        }
      }
      sb.append('}');
      return sb.toString();
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Make a JSON text of an Object value. If the object has an value.toJSONString() method, then
   * that method will be used to produce the JSON text. The method is required to produce a strictly
   * conforming text. If the object does not contain a toJSONString method (which is the most common
   * case), then a text will be produced by other means. If the value is an array or Collection,
   * then a JSONArray will be made from it and its toJSONString method will be called. If the value
   * is a MAP, then a JSONObject will be made from it and its toJSONString method will be called.
   * Otherwise, the value's toString method will be called, and the result will be quoted.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param value
   *          The value to be serialized.
   * @return a printable, displayable, transmittable representation of the object, beginning with
   *         <code>{</code>&nbsp;<small>(left brace)</small> and ending with <code>}</code>
   *         &nbsp;<small>(right brace)</small>.
   * @throws JSONException
   *           If the value is or contains an invalid number.
   */
  public static String valueToString(Object value)
      throws JSONException
  {
    if (value == null || value.equals(null)) {
      return "null";
    }
    if (value instanceof JSONString) {
      Object object;
      try {
        object = ((JSONString) value).toJSONString();
      } catch (Exception e) {
        throw new JSONException(e);
      }
      if (object instanceof String) {
        return (String) object;
      }
      throw new JSONException("Bad value from toJSONString: " + object);
    }
    if (value instanceof Number) {
      return MapBasedJSONObject.numberToString((Number) value);
    }
    if (value instanceof Boolean || value instanceof JSONObject || value instanceof JSONArray) {
      return value.toString();
    }
    if (value instanceof Map<?, ?>) {
      return new WritableJSONObject((Map<?, ?>) value).toString();
    }
    if (value instanceof Collection<?>) {
      return new WritableJSONArray((Collection<?>) value).toString();
    }
    if (value.getClass().isArray()) {
      return new WritableJSONArray(value).toString();
    }
    return quote(value.toString());
  }

  /**
   * Make a prettyprinted JSON text of an object value.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param value
   *          The value to be serialized.
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indentation of the top level.
   * @return a printable, displayable, transmittable representation of the object, beginning with
   *         <code>{</code>&nbsp;<small>(left brace)</small> and ending with <code>}</code>
   *         &nbsp;<small>(right brace)</small>.
   * @throws JSONException
   *           If the object contains an invalid number.
   */
  static final String valueToString(Object value,
                                    int indentFactor,
                                    int indent)
      throws JSONException
  {
    if (value == null || value.equals(null)) {
      return "null";
    }
    try {
      if (value instanceof JSONString) {
        Object o = ((JSONString) value).toJSONString();
        if (o instanceof String) {
          return (String) o;
        }
      }
    } catch (Exception ignore) {
    }
    if (value instanceof Number) {
      return MapBasedJSONObject.numberToString((Number) value);
    }
    if (value instanceof Boolean) {
      return value.toString();
    }
    if (value instanceof JSONObject) {
      return ((JSONObject) value).toString(indentFactor, indent);
    }
    if (value instanceof JSONArray) {
      return ((JSONArray) value).toString(indentFactor, indent);
    }
    if (value instanceof Map<?, ?>) {
      return new WritableJSONObject((Map<?, ?>) value).toString(indentFactor, indent);
    }
    if (value instanceof Collection<?>) {
      return new WritableJSONArray((Collection<?>) value).toString(indentFactor, indent);
    }
    if (value.getClass().isArray()) {
      return new WritableJSONArray(value).toString(indentFactor, indent);
    }
    return quote(value.toString());
  }

  /**
   * Write the contents of the JSONObject as JSON text to a writer. For compactness, no whitespace
   * is added.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return The writer.
   * @throws JSONException
   */
  @Override
  public final Writer write(Writer writer)
      throws JSONException
  {
    return MapBasedJSONObject.write(this, writer);
  }

  /**
   * Get an optional JSONArray associated with a key. It returns null if there is no such key, or if
   * its value is not a JSONArray.
   * 
   * @param key
   *          A key string.
   * @return A JSONArray which is the value.
   */
  @Override
  public JSONArray optJSONArray(String key)
  {
    Object o = opt(key);
    return o instanceof JSONArray ? (JSONArray) o : null;
  }

  /**
   * Get an optional JSONObject associated with a key. It returns null if there is no such key, or
   * if its value is not a JSONObject.
   * 
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   */
  @Override
  public JSONObject optJSONObject(String key)
  {
    Object object = opt(key);
    return object instanceof JSONObject ? (JSONObject) object : null;
  }

  /**
   * Get an enumeration of the keys of the JSONObject. The keys will be sorted alphabetically.
   * 
   * @return An iterator of the keys.
   */
  @Override
  public final Iterator<String> sortedKeys()
  {
    return new TreeSet<String>(getMap().keySet()).iterator();
  }

  /**
   * Produce a JSONArray containing the values of the members of this JSONObject.
   * 
   * @param names
   *          A JSONArray containing a list of key strings. This determines the sequence of the
   *          values in the result.
   * @return A JSONArray of values.
   * @throws JSONException
   *           If any of the values are non-finite numbers.
   */
  @Override
  public final WritableJSONArray toJSONArray(JSONArray names)
      throws JSONException
  {
    return toJSONArray(this, names);
  }

  public static WritableJSONArray toJSONArray(JSONObject jObj,
                                              JSONArray names)
      throws JSONException
  {
    if (names == null || names.length() == 0) {
      return null;
    }
    WritableJSONArray ja = new WritableJSONArray();
    for (int i = 0; i < names.length(); i += 1) {
      ja.put(jObj.opt(names.getString(i)));
    }
    return ja;
  }

}
