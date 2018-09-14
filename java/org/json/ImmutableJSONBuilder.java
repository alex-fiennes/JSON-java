package org.json;

public class ImmutableJSONBuilder
  extends JSONBuilder<ImmutableJSONArray, ImmutableJSONObject>
{
  private static final ImmutableJSONBuilder INSTANCE = new ImmutableJSONBuilder();

  public static final ImmutableJSONBuilder getInstance()
  {
    return INSTANCE;
  }

  private ImmutableJSONBuilder()
  {
    super(ImmutableJSONArray.class,
          ImmutableJSONObject.class);
  }

  @Override
  public JSONArrayBuilder<ImmutableJSONArray> createJSONArrayBuilder()
  {
    return new ImmutableJSONArray.Builder();
  }

  @Override
  public JSONObjectBuilder<ImmutableJSONObject> createJSONObjectBuilder()
  {
    return new ImmutableJSONObject.Builder();
  }

}
