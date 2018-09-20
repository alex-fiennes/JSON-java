package org.json;

import java.io.IOException;
import java.util.List;

public class JSONArrays
{
  public static <A extends JSONArray, O extends JSONObject> A clone(List<Object> values,
                                                                    JSONBuilder<A, O> builder)
      throws JSONException
  {
    JSONArrayBuilder<A> arrayBuilder = builder.createJSONArrayBuilder();
    for (int i = 0; i < values.size(); i++) {
      arrayBuilder.put(values.get(i));
    }
    return arrayBuilder.build();
  }

  /**
   * Make a prettyprinted JSON text of this JSONArray. Warning: This method assumes that the data
   * structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indention of the top level.
   * @throws IOException
   */
  public final static void write(JSONArray jArr,
                                 Appendable buf,
                                 int indentFactor,
                                 int indent)
      throws IOException
  {
    buf.append('[');
    int len = jArr.length();
    switch (len) {
      case 0:
        break;
      case 1:
        JSONComponents.writeValue(jArr.get(0), buf, indentFactor, indent);
        break;
      default:
        int newindent = indent + indentFactor;
        buf.append('\n');
        for (int i = 0; i < len; i += 1) {
          if (i > 0) {
            buf.append(",\n");
          }
          JSONComponents.indent(buf, newindent);
          JSONComponents.writeValue(jArr.get(i), buf, indentFactor, newindent);
        }
        buf.append('\n');
        JSONComponents.indent(buf, indent);
    }
    buf.append(']');
  }

  /**
   * Make a JSON text of this JSONArray. For compactness, no unnecessary whitespace is added. If it
   * is not possible to produce a syntactically correct JSON text then null will be returned
   * instead. This could occur if the array contains an invalid number.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @throws IOException
   */
  public static void write(JSONArray jArr,
                           Appendable buf)
      throws IOException
  {
    buf.append('[');
    int len = jArr.length();
    for (int i = 0; i < len; i += 1) {
      if (i > 0) {
        buf.append(',');
      }
      JSONComponents.writeValue(jArr.get(i), buf);
    }
    buf.append(']');
  }

}
