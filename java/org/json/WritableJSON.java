package org.json;

public class WritableJSON
  extends JSONBuilder<WritableJSONArray, WritableJSONObject>
{
  private static final WritableJSON INSTANCE = new WritableJSON();

  public static final WritableJSON get()
  {
    return INSTANCE;
  }

  private WritableJSON()
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

  @Override
  public WritableJSONArray cast(JSONArray source)
  {
    if (source instanceof WritableJSONArray) {
      return (WritableJSONArray) source;
    }
    return WritableJSON.get().toJSONArray(source);
  }

  @Override
  public WritableJSONObject cast(JSONObject source)
  {
    if (source instanceof WritableJSONObject) {
      return (WritableJSONObject) source;
    }
    return WritableJSON.get().toJSONObject(source);
  }
}
