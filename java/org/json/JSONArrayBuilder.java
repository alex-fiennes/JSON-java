package org.json;

public interface JSONArrayBuilder<A extends JSONArray>
{
  public JSONArrayBuilder<A> put(Object value)
      throws JSONException;

  public A build();
}
