package org.json;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ImmutableJSONObject
  extends MapBasedJSONObject
{
  protected static final ImmutableJSONObject EMPTY =
      new ImmutableJSONObject(ImmutableMap.<String, Object> of());

  private final ImmutableMap<String, Object> __map;

  private ImmutableJSONObject(ImmutableMap<String, Object> map)
  {
    __map = map;
  }

  @Override
  public boolean equalsMap(Map<String, Object> map)
  {
    return __map.equals(map);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <A extends JSONArray, O extends JSONObject> O clone(JSONBuilder<A, O> builder)
      throws JSONException
  {
    return (O) (ImmutableJSON.get().equals(builder) ? this : super.clone(builder));
  }

  @Override
  public JSONObject put(String key,
                        boolean value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject put(String key,
                        double value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject put(String key,
                        int value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject put(String key,
                        long value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject put(String key,
                        Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject putOnce(String key,
                            Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject putOpt(String key,
                           Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object remove(String key)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  protected ImmutableMap<String, Object> getMap()
  {
    return __map;
  }

  public static class Builder
    implements JSONObjectBuilder<ImmutableJSONObject>
  {
    private final ImmutableMap.Builder<String, Object> __builder;

    public Builder()
    {
      __builder = ImmutableMap.builder();
    }

    @Override
    public ImmutableJSONObject build()
    {
      return new ImmutableJSONObject(__builder.build());
    }

    @Override
    public Builder putOnce(String key,
                           Object value)
        throws JSONException
    {
      __builder.put(key, ImmutableJSON.get().cast(value));
      return this;
    }

  }

}
