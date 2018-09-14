package org.json;

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

}
