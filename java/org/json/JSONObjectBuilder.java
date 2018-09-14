package org.json;

public interface JSONObjectBuilder<O extends JSONObject>
{
  /**
   * @return self
   */
  public JSONObjectBuilder<O> putOnce(String key,
                                      Object value)
      throws JSONException;

  /**
   * Compile the values supplied to {@link #putOnce(String, Object)} into a JSONObject
   * representation.
   */
  public O build();
}
