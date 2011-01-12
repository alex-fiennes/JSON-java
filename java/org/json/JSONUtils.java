package org.json;

public class JSONUtils
{
  private JSONUtils()
  {

  }

  public static Object unmodifiable(Object obj)
  {
    if (obj instanceof JSONObject) {
      return UnmodifiableJSONObject.getInstance((JSONObject) obj);
    }
    if (obj instanceof JSONArray) {
      return UnmodifiableJSONArray.getInstance((JSONArray) obj);
    }
    return obj;
  }
  
  public static UnmodifiableJSONObject unmodifiable(JSONObject jObj)
  {
    return UnmodifiableJSONObject.getInstance(jObj);
  }
  
  public static UnmodifiableJSONArray unmodifiable(JSONArray jArr)
  {
    return UnmodifiableJSONArray.getInstance(jArr);
  }
}
