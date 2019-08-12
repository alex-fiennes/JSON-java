package org.json;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import com.google.common.base.Preconditions;

public abstract class JSONBuilder<A extends JSONArray, O extends JSONObject>
{
  private final Class<A> __jsonArrayClass;
  private final Class<O> __jsonObjectClass;

  public JSONBuilder(Class<A> jsonArrayClass,
                     Class<O> jsonObjectClass)
  {
    __jsonArrayClass = Preconditions.checkNotNull(jsonArrayClass, "jsonArrayClass");
    __jsonObjectClass = Preconditions.checkNotNull(jsonObjectClass, "jsonObjectClass");
  }

  public final Class<A> getJSONArrayClass()
  {
    return __jsonArrayClass;
  }

  public final Class<O> getJSONObjectClass()
  {
    return __jsonObjectClass;
  }

  public abstract JSONArrayBuilder<A> createJSONArrayBuilder();
  public abstract JSONObjectBuilder<O> createJSONObjectBuilder();

  public Object cast(Object object)
      throws JSONException
  {
    if (object == null) {
      return Null.getInstance();
    }
    if (object instanceof JSONObject) {
      return cast((JSONObject) object);
    }
    if (object instanceof JSONArray) {
      return cast((JSONArray) object);
    }
    if (object instanceof JSONString || object instanceof Byte || object instanceof Character
        || object instanceof Short || object instanceof Integer || object instanceof Long
        || object instanceof Boolean || object instanceof Float || object instanceof Double
        || object instanceof String || object instanceof Null) {
      return object;
    }
    throw new JSONException(String.format("Invalid JSON data value %s of class %s",
                                          object,
                                          object.getClass()));
  }

  /**
   * Wrap an object, if necessary. If the object is null, return the NULL object. If it is an array
   * or collection, wrap it in a JSONArray. If it is a map, wrap it in a JSONObject. If it is a
   * standard property (Double, String, et al) then it is already wrapped. Otherwise, if it comes
   * from one of the java packages, turn it into a string. And if it doesn't, try to wrap it in a
   * JSONObject. If the wrapping fails, then null is returned.
   * 
   * @param object
   *          The object to wrap
   * @return The wrapped value
   */
  public Object wrap(Object object)
      throws JSONException
  {
    if (object == null) {
      return Null.getInstance();
    }
    if (object instanceof JSONObject) {
      return ((JSONObject) object).clone(this);
    }
    if (object instanceof JSONArray) {
      return ((JSONArray) object).clone(this);
    }
    if (object instanceof JSONString || object instanceof Byte || object instanceof Character
        || object instanceof Short || object instanceof Integer || object instanceof Long
        || object instanceof Boolean || object instanceof Float || object instanceof Double
        || object instanceof String || object instanceof Null) {
      return object;
    }
    // if (object instanceof Collection<?>) {
    // return new WritableJSONArray((Collection<?>) object);
    // }
    // if (object.getClass().isArray()) {
    // return new WritableJSONArray(object);
    // }
    // if (object instanceof Map<?, ?>) {
    // return new WritableJSONObject((Map<?, ?>) object);
    // }
    // Package objectPackage = object.getClass().getPackage();
    // String objectPackageName = (objectPackage != null ? objectPackage.getName() : "");
    // if (objectPackageName.startsWith("java.") || objectPackageName.startsWith("javax.")
    // || object.getClass().getClassLoader() == null) {
    // return object.toString();
    // }
    // return new WritableJSONObject(object);
    throw new JSONException(String.format("Invalid JSON data value %s of class %s",
                                          object,
                                          object.getClass()));
  }

  public A clone(JSONArray source)
      throws JSONException
  {
    return source.clone(this);
  }

  public abstract A cast(JSONArray source);
  public abstract O cast(JSONObject source);

  /**
   * Create an empty JSONArray. If this JSONBuilder creates immutable implementations then this can
   * be overridden with a singleton return.
   */
  public A emptyJSONArray()
  {
    return createJSONArrayBuilder().build();
  }

  public A toJSONArray(Iterable<?> values)
  {
    JSONArrayBuilder<A> jsonArrayBuilder = createJSONArrayBuilder();
    Iterator<?> i = values.iterator();
    while (i.hasNext()) {
      Object value = i.next();
      try {
        jsonArrayBuilder.put(value);
      } catch (JSONException e) {
        throw new JSONLogicException(String.format("Unable to clone %s from %s", value, values));
      }
    }
    return jsonArrayBuilder.build();
  }

  public A toJSONArray(JSONArray source)
  {
    JSONArrayBuilder<A> jsonArrayBuilder = createJSONArrayBuilder();
    final int length = source.length();
    for (int i = 0; i < length; i++) {
      Object value = source.opt(i);
      try {
        jsonArrayBuilder.put(value);
      } catch (JSONException e) {
        throw new JSONLogicException(String.format("Unable to clone %s from %s", value, source));
      }
    }
    return jsonArrayBuilder.build();
  }

  public A toJSONArray(JSONTokener<A, O> source)
      throws JSONException
  {
    JSONArrayBuilder<A> jsonArrayBuilder = createJSONArrayBuilder();
    JSONParser.populateArrayBuilder(source, jsonArrayBuilder);
    return jsonArrayBuilder.build();
  }

  public A toJSONArray(Reader source)
      throws JSONException
  {
    return toJSONArray(new JSONTokenerReader<A, O>(source, this));
  }

  public A toJSONArray(String source)
      throws JSONException
  {
    return toJSONArray(new JSONTokenerString<A, O>(source, this));
  }

  public O clone(JSONObject source)
      throws JSONException
  {
    return source.clone(this);
  }

  /**
   * Create an empty JSONObject. If this JSONBuilder creates immutable implementations then this can
   * be overridden with a singleton return.
   */
  public O emptyJSONObject()
  {
    return createJSONObjectBuilder().build();
  }

  public O toJSONObject(JSONObject source)
  {
    JSONObjectBuilder<O> jsonObjectBuilder = createJSONObjectBuilder();
    Iterator<String> keys = source.keys();
    while (keys.hasNext()) {
      String key = keys.next();
      Object value = source.opt(key);
      try {
        jsonObjectBuilder.putOnce(key, value);
      } catch (JSONException e) {
        throw new JSONLogicException(String.format("Unable to clone %s from %s", value, source));
      }
    }
    return jsonObjectBuilder.build();
  }

  public O toJSONObject(JSONTokener<A, O> source)
      throws JSONException
  {
    JSONObjectBuilder<O> jsonObjectBuilder = createJSONObjectBuilder();
    JSONParser.populateObjectBuilder(source, jsonObjectBuilder);
    return jsonObjectBuilder.build();
  }

  public O toJSONObject(Reader source)
      throws JSONException
  {
    return toJSONObject(new JSONTokenerReader<A, O>(source, this));
  }

  public O toJSONObject(String source)
      throws JSONException
  {
    return toJSONObject(new JSONTokenerString<A, O>(source, this));
  }

}
