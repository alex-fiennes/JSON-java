package org.json;

public class JSONUtils
{
  private JSONUtils()
  {

  }

  public static Object unmodifiable(Object obj)
  {
    if (obj instanceof JSONObjectI) {
      return UnmodifiableJSONObject.getInstance((JSONObjectI) obj);
    }
    if (obj instanceof JSONArrayI) {
      return UnmodifiableJSONArray.getInstance((JSONArrayI) obj);
    }
    return obj;
  }
}
