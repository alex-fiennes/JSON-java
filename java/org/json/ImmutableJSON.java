package org.json;

public class ImmutableJSON
  extends JSONBuilder<ImmutableJSONArray, ImmutableJSONObject>
{
  private static final ImmutableJSON INSTANCE = new ImmutableJSON();

  public static final ImmutableJSON get()
  {
    return INSTANCE;
  }

  /**
   * @deprecated in preference of {@link #get()}
   */
  @Deprecated
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

  /**
   * @return Singleton immutable empty JSONObject
   */
  @Override
  public ImmutableJSONObject emptyJSONObject()
  {
    return ImmutableJSONObject.EMPTY;
  }

  /**
   * @return Singleton immutable empty JSONArray
   */
  @Override
  public ImmutableJSONArray emptyJSONArray()
  {
    return ImmutableJSONArray.EMPTY;
  }
}
