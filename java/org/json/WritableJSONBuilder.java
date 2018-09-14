package org.json;

public class WritableJSONBuilder
  extends JSONBuilder<WritableJSONArray, WritableJSONObject>
{
  private static final WritableJSONBuilder INSTANCE = new WritableJSONBuilder();

  public static final WritableJSONBuilder getInstance()
  {
    return INSTANCE;
  }

  private WritableJSONBuilder()
  {
    super(WritableJSONArray.class,
          WritableJSONObject.class);
  }

  @Override
  public JSONArrayBuilder<WritableJSONArray> createJSONArrayBuilder()
  {
    return new WritableJSONArray.Builder();
  }

  @Override
  public JSONObjectBuilder<WritableJSONObject> createJSONObjectBuilder()
  {
    return new WritableJSONObject.Builder();
  }

}
