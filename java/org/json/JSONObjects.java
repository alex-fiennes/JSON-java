package org.json;

import java.io.IOException;
import java.util.Map;

public class JSONObjects
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
          return JSONObject.NULL;
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

  public static <A extends JSONArray, O extends JSONObject> O clone(Map<String, Object> values,
                                                                    JSONBuilder<A, O> builder)
      throws JSONException
  {
    JSONObjectBuilder<O> objBuilder = builder.createJSONObjectBuilder();
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      objBuilder.putOnce(entry.getKey(), builder.wrap(entry.getValue()));
    }
    return objBuilder.build();
  }
}
