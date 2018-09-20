package org.json;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ImmutableJSONObject
  extends MapBasedJSONObject
{

  private final ImmutableMap<String, Object> __map;

  private ImmutableJSONObject(ImmutableMap<String, Object> map)
  {
    __map = map;
  }

  // public static ImmutableJSONObject create(JSONTokener x)
  // throws JSONException
  // {
  // Builder builder = getJSONObjectBuilderSupplier().get();
  // JSONParser.populateObjectBuilder(x, builder);
  // return builder.build();
  // }
  //
  // public static ImmutableJSONObject create(Map<?, ?> values)
  // throws JSONException
  // {
  // Builder builder = getJSONObjectBuilderSupplier().get();
  // for (Map.Entry<?, ?> entry : values.entrySet()) {
  // builder.putOnce(entry.getKey().toString(), entry.getValue());
  // }
  // return builder.build();
  // }

  @Override
  public boolean equalsMap(Map<String, Object> map)
  {
    return __map.equals(map);
  }

  // @Override
  // public WritableJSONObject writableClone()
  // {
  // WritableJSONObject clone = new WritableJSONObject();
  // try {
  // for (Map.Entry<String, Object> entry : __map.entrySet()) {
  // Object value = entry.getValue();
  // if (value instanceof JSONObject) {
  // value = ((JSONObject) value).writableClone();
  // } else if (value instanceof JSONArray) {
  // value = ((JSONArray) value).writableClone();
  // }
  // clone.put(entry.getKey(), value);
  // }
  // } catch (JSONException e) {
  // throw new RuntimeException(e);
  // }
  // return clone;
  // }

  @SuppressWarnings("unchecked")
  @Override
  public <A extends JSONArray, O extends JSONObject> O clone(JSONBuilder<A, O> builder)
      throws JSONException
  {
    return (O) (ImmutableJSON.getInstance().equals(builder) ? this : super.clone(builder));
  }

  @Override
  public JSONObject accumulate(String key,
                               Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject append(String key,
                           Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject increment(String key)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject put(String key,
                        boolean value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  // @Override
  // public JSONObject put(String key,
  // Collection<?> value)
  // throws JSONException
  // {
  // throw new UnsupportedOperationException();
  // }

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

  // @Override
  // public JSONObject put(String key,
  // Map<String, ?> value)
  // throws JSONException
  // {
  // throw new UnsupportedOperationException();
  // }

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
      __builder.put(key, value);
      return this;
    }

  }

  // private static final Supplier<Builder> BUILDERSUPPLIER = new Supplier<Builder>() {
  // @Override
  // public Builder get()
  // {
  // return new Builder();
  // }
  // };

  // public static Supplier<Builder> getJSONObjectBuilderSupplier()
  // {
  // return BUILDERSUPPLIER;
  // }

  @Override
  public Appendable write(Appendable writer)
      throws JSONException
  {
    JSONObjects.write(this,
                      // ImmutableJSONObject.getJSONObjectBuilderSupplier(),
                      // ImmutableJSONArray.getJSONArrayBuilderSupplier(),
                      writer);
    return writer;
  }

}
