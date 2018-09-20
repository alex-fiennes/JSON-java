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

  // public static String toString(JSONObject jObj)
  // // Supplier<? extends JSONObjectBuilder> jsonObjectBuilderSupplier,
  // // Supplier<? extends JSONArrayBuilder> jsonArrayBuilderSupplier)
  // {
  // try {
  // Iterator<String> keys = jObj.keys();
  // StringBuilder sb = new StringBuilder("{");
  //
  // while (keys.hasNext()) {
  // if (sb.length() > 1) {
  // sb.append(',');
  // }
  // String string = keys.next();
  // sb.append(JSONComponents.quote(string));
  // sb.append(':');
  // sb.append(JSONComponents.valueToString(jObj.opt(string)));
  // // jsonObjectBuilderSupplier,
  // // jsonArrayBuilderSupplier));
  // }
  // sb.append('}');
  // return sb.toString();
  // } catch (Exception e) {
  // return null;
  // }
  // }

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
        sb.append(JSONComponents.quote(key.toString()));
        sb.append(": ");
        sb.append(JSONComponents.valueToString(jObj.opt(key), indentFactor, indent));
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
          sb.append(JSONComponents.quote(key.toString()));
          sb.append(": ");
          sb.append(JSONComponents.valueToString(jObj.opt(key), indentFactor, newindent));
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

  public static Appendable write(JSONObject jObj,
                                 // Supplier<? extends JSONObjectBuilder> jsonObjectBuilderSupplier,
                                 // Supplier<? extends JSONArrayBuilder> jsonArrayBuilderSupplier,
                                 Appendable writer)
      throws IOException
  {
    boolean commanate = false;
    Iterator<String> keys = jObj.keys();
    writer.append('{');

    while (keys.hasNext()) {
      if (commanate) {
        writer.append(',');
      }
      String key = keys.next();
      writer.append(JSONComponents.quote(key.toString()));
      writer.append(':');
      Object value = jObj.opt(key);
      if (value instanceof JSONObject) {
        ((JSONObject) value).write(writer);
      } else if (value instanceof JSONArray) {
        ((JSONArray) value).write(writer);
      } else {
        writer.append(JSONComponents.valueToString(value));
        // , jsonObjectBuilderSupplier, jsonArrayBuilderSupplier));
      }
      commanate = true;
    }
    writer.append('}');
    return writer;
  }
}
