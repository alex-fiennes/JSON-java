package org.json;

public class ImmutableJSON
  extends JSONBuilder<ImmutableJSONArray, ImmutableJSONObject>
{
  private static final ImmutableJSON INSTANCE = new ImmutableJSON();

  public static final ImmutableJSON getInstance()
  {
    return INSTANCE;
  }

  private ImmutableJSON()
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
