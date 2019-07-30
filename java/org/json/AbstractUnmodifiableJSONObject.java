package org.json;

/**
 * Abstract implementation of JSONObject that only implements final versions of
 * all the methods that change the state of the JSONOBject all of which are
 * implemented as throwing an exception.
 * 
 * @author alex
 */
@Deprecated
public abstract class AbstractUnmodifiableJSONObject
  implements JSONObject
{
  @Override
  public final JSONObject put(String key,
                              boolean value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public final JSONObject put(String key,
                              double value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public final JSONObject put(String key,
                              int value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public final JSONObject put(String key,
                              long value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public final JSONObject put(String key,
                              Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public final JSONObject putOnce(String key,
                                  Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public final JSONObject putOpt(String key,
                                 Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public final Object remove(String key)
  {
    throw new UnsupportedOperationException();
  }
}
