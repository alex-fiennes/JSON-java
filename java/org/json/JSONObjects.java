package org.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JSONObjects
{

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

  public static String toString(JSONObject jObj)
  {
    StringBuilder buf = new StringBuilder();
    try {
      write(jObj, buf);
    } catch (IOException e) {
      throw new RuntimeException("Impossible", e);
    }
    return buf.toString();
  }

  public static Appendable write(JSONObject jObj,
                                 Appendable buf)
      throws IOException
  {
    boolean commanate = false;
    Iterator<String> keys = jObj.keys();
    buf.append('{');

    while (keys.hasNext()) {
      if (commanate) {
        buf.append(',');
      }
      String key = keys.next();
      buf.append(JSONComponents.quote(key.toString()));
      buf.append(':');
      Object value = jObj.opt(key);
      if (value instanceof JSONObject) {
        ((JSONObject) value).write(buf);
      } else if (value instanceof JSONArray) {
        ((JSONArray) value).write(buf);
      } else {
        JSONComponents.writeValue(value, buf);
      }
      commanate = true;
    }
    buf.append('}');
    return buf;
  }

  public static String toString(JSONObject jObj,
                                int indentFactor,
                                int indent)
  {
    StringBuilder buf = new StringBuilder();
    try {
      write(jObj, buf, indentFactor, indent);
    } catch (IOException e) {
      throw new RuntimeException("Impossible", e);
    }
    return buf.toString();
  }

  public static void write(JSONObject jObj,
                           Appendable buf,
                           int indentFactor,
                           int indent)
      throws IOException
  {
    buf.append('{');
    String key;
    switch (jObj.length()) {
      case 0:
        break;
      case 1:
        key = jObj.keys().next();
        buf.append(JSONComponents.quote(key.toString())).append(": ");
        JSONComponents.writeValue(jObj.opt(key), buf, indentFactor, indent);
        break;
      default:
        Iterator<String> keys = jObj.sortedKeys();
        int newindent = indent + indentFactor;
        boolean isFirst = true;
        while (keys.hasNext()) {
          key = keys.next();
          if (isFirst) {
            buf.append('\n');
            isFirst = false;
          } else {
            buf.append(",\n");
          }
          JSONComponents.indent(buf, newindent);
          buf.append(JSONComponents.quote(key.toString()));
          buf.append(": ");
          JSONComponents.writeValue(jObj.opt(key), buf, indentFactor, newindent);
        }
        buf.append('\n');
        JSONComponents.indent(buf, indent);
    }
    buf.append('}');
  }

}
