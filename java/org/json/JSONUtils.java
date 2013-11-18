package org.json;

import java.util.Iterator;

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

  public static WritableJSONObject writableDeepCopy(JSONObject jObj)
      throws JSONException
  {
    WritableJSONObject copy = new WritableJSONObject();
    Iterator<String> keys = jObj.keys();
    while (keys.hasNext()) {
      String key = keys.next();
      Object value = jObj.opt(key);
      if (value instanceof JSONObject) {
        copy.put(key, writableDeepCopy((JSONObject) value));
      } else if (value instanceof JSONArray) {
        copy.put(key, writableDeepCopy((JSONArray) value));
      } else {
        copy.put(key, value);
      }
    }
    return copy;
  }
  
  public static WritableJSONArray writableDeepCopy(JSONArray jArr)
      throws JSONException
  {
    WritableJSONArray copy = new WritableJSONArray();
    for (int i = 0; i < jArr.length(); i++) {
      Object value = jArr.get(i);
      if (value instanceof JSONObject) {
        copy.put(writableDeepCopy((JSONObject) value));
      } else if (value instanceof JSONArray) {
        copy.put(writableDeepCopy((JSONArray) value));
      } else {
        copy.put(value);
      }
    }
    return copy;
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
