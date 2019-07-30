package org.json;

import java.util.Iterator;

@Deprecated
public class JSONUtils
{
  private JSONUtils()
  {

  }

  @Deprecated
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

  @Deprecated
  public static UnmodifiableJSONObject unmodifiable(JSONObject jObj)
  {
    return UnmodifiableJSONObject.getInstance(jObj);
  }

  @Deprecated
  public static UnmodifiableJSONArray unmodifiable(JSONArray jArr)
  {
    return UnmodifiableJSONArray.getInstance(jArr);
  }

  @Deprecated
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

  @Deprecated
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
   */
  @Deprecated
  public static Object stripNULL(Object obj)
  {
    if (obj == null) {
      throw new NullPointerException();
    }
    if (Null.getInstance().equals(obj)) {
      return null;
    }
    return obj;
  }
}
