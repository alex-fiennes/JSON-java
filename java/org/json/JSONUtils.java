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

  /**
   * Transform NULL into null
   * 
   * @param obj
   *          The compulsory object
   * @return obj or null if obj equalled NULL
   * @throws NullPointerException
   *           if obj was null
   * @see JSONObject#NULL
   */
  public static Object stripNULL(Object obj)
  {
    if (obj == null) {
      throw new NullPointerException();
    }
    if (JSONObject.NULL.equals(obj)) {
      return null;
    }
    return obj;
  }
}
