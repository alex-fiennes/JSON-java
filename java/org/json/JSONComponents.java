package org.json;

import java.io.IOException;

public class JSONComponents
{

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
  public static void escapeChars(String string,
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
    switch (string.length()) {
      case 0:
        return string;
      case 4:
        if (string.equalsIgnoreCase("true")) {
          return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase("false")) {
          return Boolean.FALSE;
        }
        if (string.equalsIgnoreCase("null")) {
          return Null.getInstance();
        }
    }
    /*
     * If it might be a number, try converting it. We support the non-standard 0x- convention. If a
     * number cannot be produced, then the value will just be a string. Note that the 0x-, plus, and
     * implied string conventions are non-standard. A JSON parser may accept non-JSON forms as long
     * as it accepts all correct JSON forms.
     */
    char b = string.charAt(0);
    switch (b) {
      case '0':
        if (string.length() > 2 && (string.charAt(1) == 'x' || string.charAt(1) == 'X')) {
          try {
            return Integer.valueOf(Integer.parseInt(string.substring(2), 16));
          } catch (Exception ignore) {
          }
        }
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
      case '.':
      case '-':
      case '+':
        try {
          if (string.indexOf('.') > -1 || string.indexOf('e') > -1 || string.indexOf('E') > -1) {
            return Double.valueOf(string);
          } else {
            long myLong = Long.parseLong(string);
            if (myLong < Integer.MAX_VALUE & myLong > Integer.MIN_VALUE) {
              return Integer.valueOf((int) myLong);
            } else {
              return Long.valueOf(myLong);
            }
          }
        } catch (Exception ignore) {
        }
    }

    return string;
  }

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
    try {
      quote(string, sb);
    } catch (IOException e) {
      throw new RuntimeException("StringBuilder should not thrown an IOException", e);
    }
    return sb.toString();
  }

  public static void quote(String string,
                           Appendable buf)
      throws IOException
  {
    buf.append('"');
    escapeChars(string, buf);
    buf.append('"');
  }

  /**
   * Throw an exception if the object is a NaN or infinite number.
   * 
   * @param o
   *          The object to test.
   * @throws JSONRuntimeException
   *           If o is a non-finite number.
   */
  public static void testValidity(Object o)
      throws JSONRuntimeException
  {
    if (o != null) {
      if (o instanceof Double) {
        if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
          throw new JSONRuntimeException("JSON does not allow non-finite numbers.");
        }
      } else if (o instanceof Float) {
        if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
          throw new JSONRuntimeException("JSON does not allow non-finite numbers.");
        }
      }
    }
  }

  /**
   * Produce a string from a Number.
   * 
   * @param number
   *          A Number
   * @return A String.
   * @throws JSONRuntimeException
   *           If n is a non-finite number.
   */
  public static String numberToString(Number number)
      throws JSONRuntimeException
  {
    if (number == null) {
      throw new JSONRuntimeException("Null pointer");
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
   * @throws JSONRuntimeException
   *           If the value is or contains an invalid number.
   */
  public static String valueToString(Object value)
  {
    if (value == null || value.equals(null)) {
      return "null";
    }
    if (value instanceof JSONString) {
      return ((JSONString) value).toJSONString();
    }
    if (value instanceof Number) {
      return numberToString((Number) value);
    }
    if (value instanceof Boolean || value instanceof JSONObject || value instanceof JSONArray) {
      return value.toString();
    }
    return quote(value.toString());
  }

  public static void writeValue(Object value,
                                Appendable buf)
      throws IOException
  {
    if (value == null || value.equals(null)) {
      buf.append("null");
      return;
    }
    if (value instanceof JSONString) {
      ((JSONString) value).toJSONString(buf);
      return;
    }
    if (value instanceof Number) {
      buf.append(numberToString((Number) value));
      return;
    }
    if (value instanceof Boolean) {
      buf.append(value.toString());
      return;
    }
    if (value instanceof JSONComponent) {
      ((JSONComponent) value).write(buf);
      return;
    }
    buf.append(quote(value.toString()));
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
      return numberToString((Number) value);
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
    return quote(value.toString());
  }

  static final void writeValue(Object value,
                               Appendable buf,
                               int indentFactor,
                               int indent)
      throws IOException
  {
    if (value == null || value.equals(Null.getInstance())) {
      buf.append("null");
      return;
    }
    if (value instanceof JSONString) {
      buf.append(((JSONString) value).toJSONString());
      return;
    }
    if (value instanceof Number) {
      buf.append(numberToString((Number) value));
      return;
    }
    if (value instanceof Boolean) {
      buf.append(value.toString());
      return;
    }
    if (value instanceof JSONObject) {
      JSONObjects.write(((JSONObject) value), buf, indentFactor, indent);
      return;
    }
    if (value instanceof JSONArray) {
      JSONArrays.write(((JSONArray) value), buf, indentFactor, indent);
      return;
    }
    // if (value instanceof Map<?, ?>) {
    // return new WritableJSONObject((Map<?, ?>) value).toString(indentFactor, indent);
    // }
    // if (value instanceof Collection<?>) {
    // return new WritableJSONArray((Collection<?>) value).toString(indentFactor, indent);
    // }
    // if (value.getClass().isArray()) {
    // return new WritableJSONArray(value).toString(indentFactor, indent);
    // }
    buf.append(quote(value.toString()));
  }

  static void indent(Appendable buf,
                     int depth)
      throws IOException
  {
    for (int i = 0; i < depth; i++) {
      buf.append(' ');
    }
  }

}
