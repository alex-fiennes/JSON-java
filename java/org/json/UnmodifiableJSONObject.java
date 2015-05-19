package org.json;

import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

public class UnmodifiableJSONObject
  extends AbstractUnmodifiableJSONObject
{
  private final static UnmodifiableJSONObject EMPTY = getInstance(new WritableJSONObject());

  public static final UnmodifiableJSONObject emptyInstance()
  {
    return EMPTY;
  }

  public static UnmodifiableJSONObject getInstance(JSONObject jObj)
  {
    if (jObj == null) {
      throw new NullPointerException();
    }
    if (jObj instanceof UnmodifiableJSONObject) {
      return (UnmodifiableJSONObject) jObj;
    }
    return new UnmodifiableJSONObject((WritableJSONObject) jObj);
  }

  private final WritableJSONObject __jObj;

  private UnmodifiableJSONObject(WritableJSONObject jObj)
  {
    __jObj = jObj;
  }

  @Override
  public WritableJSONObject writableClone()
  {
    return __jObj.clone();
  }

  @Override
  public Object get(String key)
      throws JSONException
  {
    return JSONUtils.unmodifiable(__jObj.get(key));
  }

  @Override
  public boolean getBoolean(String key)
      throws JSONException
  {
    return __jObj.getBoolean(key);
  }

  @Override
  public double getDouble(String key)
      throws JSONException
  {
    return __jObj.getDouble(key);
  }

  @Override
  public int getInt(String key)
      throws JSONException
  {
    return __jObj.getInt(key);
  }

  @Override
  public UnmodifiableJSONArray getJSONArray(String key)
      throws JSONException
  {
    return UnmodifiableJSONArray.getInstance(__jObj.getJSONArray(key));
  }

  @Override
  public UnmodifiableJSONObject getJSONObject(String key)
      throws JSONException
  {
    return UnmodifiableJSONObject.getInstance(__jObj.getJSONObject(key));
  }

  @Override
  public long getLong(String key)
      throws JSONException
  {
    return __jObj.getLong(key);
  }

  @Override
  public String getString(String key)
      throws JSONException
  {
    return __jObj.getString(key);
  }

  @Override
  public boolean has(String key)
  {
    return __jObj.has(key);
  }

  @Override
  public boolean isNull(String key)
  {
    return __jObj.isNull(key);
  }

  @Override
  public Iterator<String> keys()
  {
    return new UnmodifiableIterator<String>(__jObj.keys());
  }

  @Override
  public int length()
  {
    return __jObj.length();
  }

  @Override
  public UnmodifiableJSONArray names()
  {
    return UnmodifiableJSONArray.getInstance(__jObj.names());
  }

  @Override
  public Object opt(String key)
  {
    return JSONUtils.unmodifiable(__jObj.opt(key));
  }

  @Override
  public boolean optBoolean(String key)
  {
    return __jObj.optBoolean(key);
  }

  @Override
  public boolean optBoolean(String key,
                            boolean defaultValue)
  {
    return __jObj.optBoolean(key, defaultValue);
  }

  @Override
  public double optDouble(String key)
  {
    return __jObj.optDouble(key);
  }

  @Override
  public double optDouble(String key,
                          double defaultValue)
  {
    return __jObj.optDouble(key, defaultValue);
  }

  @Override
  public int optInt(String key)
  {
    return __jObj.optInt(key);
  }

  @Override
  public int optInt(String key,
                    int defaultValue)
  {
    return __jObj.optInt(key, defaultValue);
  }

  @Override
  public UnmodifiableJSONArray optJSONArray(String key)
  {
    JSONArray jArr = __jObj.optJSONArray(key);
    return jArr == null ? null : UnmodifiableJSONArray.getInstance(jArr);
  }

  @Override
  public UnmodifiableJSONObject optJSONObject(String key)
  {
    JSONObject jObj = __jObj.optJSONObject(key);
    return jObj == null ? null : UnmodifiableJSONObject.getInstance(jObj);
  }

  @Override
  public long optLong(String key)
  {
    return __jObj.optLong(key);
  }

  @Override
  public long optLong(String key,
                      long defaultValue)
  {
    return __jObj.optLong(key, defaultValue);
  }

  @Override
  public String optString(String key)
  {
    return __jObj.optString(key);
  }

  @Override
  public String optString(String key,
                          String defaultValue)
  {
    return __jObj.optString(key, defaultValue);
  }

  @Override
  public Iterator<String> sortedKeys()
  {
    return new UnmodifiableIterator<String>(__jObj.sortedKeys());
  }

  @Override
  public UnmodifiableJSONArray toJSONArray(JSONArray names)
      throws JSONException
  {
    return UnmodifiableJSONArray.getInstance(__jObj.toJSONArray(names));
  }

  @Override
  public String toString(int indentFactor)
  {
    return __jObj.toString(indentFactor);
  }

  @Override
  public Writer write(Writer writer)
      throws JSONException
  {
    return __jObj.write(writer);
  }

  @Override
  public String toString()
  {
    return __jObj.toString();
  }

  @Override
  public boolean equalsMap(Map<String, Object> map)
  {
    return __jObj.equalsMap(map);
  }

  @Override
  public boolean equals(Object obj)
  {
    return __jObj.equals(obj);
  }

  @Override
  public int hashCode()
  {
    return __jObj.hashCode();
  }

  @Override
  public Integer optInteger(String key)
  {
    return __jObj.optInteger(key);
  }

  @Override
  public Integer optInteger(String key,
                            Integer defaultValue)
  {
    return __jObj.optInteger(key, defaultValue);
  }

  @Override
  public String toString(int indentFactor,
                         int indent)
  {
    return __jObj.toString(indentFactor, indent);
  }

}
