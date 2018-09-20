package org.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public abstract class MapBasedJSONObject
  implements JSONObject
{
  protected abstract Map<String, Object> getMap();

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
    throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] is not a Boolean.");
  }

  public static String toString(MapBasedJSONObject jObj)
  {
    StringBuilder sb = new StringBuilder("{");
    for (Entry<String, Object> entry : jObj.getMap().entrySet()) {
      sb.append(JSONComponents.quote(entry.getKey()));
      sb.append(':');
      sb.append(JSONComponents.valueToString(entry.getValue()));
    }
    sb.append("}");
    return sb.toString();
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
      throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] not found in " + this);
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
      throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] is not a number.");
    }
  }

  @Override
  public final Double optDoubleObj(String key,
                                   Double defaultValue)
  {
    Object object = opt(key);
    if (object == null) {
      return defaultValue;
    }
    if (object instanceof Double) {
      return (Double) object;
    }
    if (object instanceof Number) {
      return Double.valueOf(((Number) object).doubleValue());
    }
    try {
      return Double.valueOf(Double.parseDouble(object.toString()));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  @Override
  public final Double getDoubleObj(String key)
      throws JSONException
  {
    Object object = get(key);
    if (object instanceof Double) {
      return (Double) object;
    }
    if (object instanceof Number) {
      return Double.valueOf(((Number) object).doubleValue());
    }
    try {
      return Double.valueOf(Double.parseDouble(object.toString()));
    } catch (NumberFormatException e) {
      throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] is not a number.");
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
      throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] is not an int.");
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
    throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] is not a JSONArray.");
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
    throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] is not a JSONObject.");
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
      throw new JSONException("JSONObject[" + JSONComponents.quote(key) + "] is not a long.");
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
    return Null.getInstance().equals(object) ? null : object.toString();
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
    return Null.getInstance().equals(opt(key));
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
      return Integer.valueOf(((Number) object).intValue());
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
    return Null.getInstance().equals(object) ? defaultValue : object.toString();
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
    return JSONObjects.toString(this);
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
    return JSONObjects.toString(this, indentFactor, indent);
  }

  @Override
  public final Appendable write(Appendable buf)
      throws IOException
  {
    return JSONObjects.write(this, buf);
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
   * Get an Iterator of the keys of the JSONObject. The keys will be sorted alphabetically.
   * 
   * @return An iterator of the keys.
   */
  @Override
  public final Iterator<String> sortedKeys()
  {
    Set<String> unsortedKeys = getMap().keySet();
    switch (unsortedKeys.size()) {
      case 0:
      case 1:
        return unsortedKeys.iterator();
      default:
        return new TreeSet<String>(unsortedKeys).iterator();
    }
  }

  @Override
  public <A extends JSONArray, O extends JSONObject> O clone(JSONBuilder<A, O> builder)
      throws JSONException
  {
    return JSONObjects.clone(getMap(), builder);
  }

}
