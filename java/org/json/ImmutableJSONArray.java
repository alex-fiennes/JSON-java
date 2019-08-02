package org.json;

import com.google.common.collect.ImmutableList;

public class ImmutableJSONArray
  extends ListBasedJSONArray
{
  protected final static ImmutableJSONArray EMPTY = new ImmutableJSONArray(ImmutableList.of());
  
  private ImmutableJSONArray(ImmutableList<Object> list)
  {
    super(list);
  }

//  @Override
//  public Appendable write(Appendable writer)
//      throws IOException
//  {
//    JSONArrays.write(this, writer);
//    return writer;
//  }
  
  @SuppressWarnings("unchecked")
  @Override
  public <A extends JSONArray, O extends JSONObject> A clone(JSONBuilder<A, O> builder)
      throws JSONException
  {
    return (A) (ImmutableJSON.get().equals(builder) ? this : super.clone(builder));
  }


  // @Override
  // public WritableJSONArray writableClone()
  // {
  // return new WritableJSONArray(getBackingList());
  // }

  @Override
  public ImmutableJSONArray getJSONArray(int index)
      throws JSONException
  {
    return (ImmutableJSONArray) super.getJSONArray(index);
  }

  @Override
  public ImmutableJSONObject getJSONObject(int index)
      throws JSONException
  {
    return (ImmutableJSONObject) super.getJSONObject(index);
  }

//  @Override
//  public String join(String separator)
//      throws JSONException
//  {
//    return join(separator,
//                ImmutableJSONObject.getJSONObjectBuilderSupplier(),
//                ImmutableJSONArray.getJSONArrayBuilderSupplier());
//  }

  @Override
  public ImmutableJSONArray optJSONArray(int index)
  {
    return (ImmutableJSONArray) super.optJSONArray(index);
  }

  @Override
  public ImmutableJSONObject optJSONObject(int index)
  {
    return (ImmutableJSONObject) super.optJSONObject(index);
  }

  // @Override
  // public ImmutableJSONArray put(Collection<?> value)
  // {
  // put(create(value));
  // return this;
  // }

  // public static ImmutableJSONArray create(Iterable<?> values)
  // {
  // Builder builder = getJSONArrayBuilderSupplier().get();
  // Iterator<?> i = values.iterator();
  // while (i.hasNext()) {
  // builder.put(i.next());
  // }
  // return builder.build();
  // }

  // @Override
  // public ImmutableJSONArray put(int index,
  // Collection<?> value)
  // throws JSONException
  // {
  // put(index, create(value));
  // return this;
  // }

  // @Override
  // public JSONArray put(int index,
  // Map<String, ?> value)
  // throws JSONException
  // {
  // put(index, ImmutableJSONObject.create(value));
  // return this;
  // }

  // @Override
  // public JSONObject toJSONObject(JSONArray names)
  // throws JSONException
  // {
  // return toJSONObject(names, ImmutableJSONObject.getJSONObjectBuilderSupplier());
  // }

  // private static final Supplier<Builder> BUILDERSUPPLIER = new Supplier<Builder>() {
  // @Override
  // public Builder get()
  // {
  // return new Builder();
  // }
  // };

  // public static Supplier<Builder> getJSONArrayBuilderSupplier()
  // {
  // return BUILDERSUPPLIER;
  // }

  public static class Builder
    implements JSONArrayBuilder<ImmutableJSONArray>
  {
    private final ImmutableList.Builder<Object> __builder;

    public Builder()
    {
      __builder = ImmutableList.builder();
    }

    @Override
    public ImmutableJSONArray build()
    {
      return new ImmutableJSONArray(__builder.build());
    }

    @Override
    public Builder put(Object value) throws JSONException
    {
      __builder.add(ImmutableJSON.get().cast(value));
      return this;
    }
  }
}
