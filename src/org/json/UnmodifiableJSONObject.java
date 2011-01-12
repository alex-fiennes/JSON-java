package org.json;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class UnmodifiableJSONObject
  implements JSONObjectI
{
  public static UnmodifiableJSONObject getInstance(JSONObjectI jObj)
  {
    if (jObj == null) {
      throw new NullPointerException();
    }
    if (jObj instanceof UnmodifiableJSONObject) {
      return (UnmodifiableJSONObject) jObj;
    }
    return new UnmodifiableJSONObject(jObj);
  }

  private final JSONObjectI __jObj;

  private UnmodifiableJSONObject(JSONObjectI jObj)
  {
    __jObj = jObj;
  }

  @Override
  public JSONObjectI accumulate(String key,
                                Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI append(String key,
                            Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
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
  public JSONObjectI increment(String key)
      throws JSONException
  {
    throw new UnsupportedOperationException();
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
    return UnmodifiableJSONArray.getInstance(__jObj.optJSONArray(key));
  }

  @Override
  public UnmodifiableJSONObject optJSONObject(String key)
  {
    return UnmodifiableJSONObject.getInstance(__jObj.optJSONObject(key));
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
  public JSONObjectI put(String key,
                         boolean value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI put(String key,
                         Collection<?> value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI put(String key,
                         double value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI put(String key,
                         int value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI put(String key,
                         long value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI put(String key,
                         Map<String, ?> value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI put(String key,
                         Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI putOnce(String key,
                             Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObjectI putOpt(String key,
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
  public Iterator<String> sortedKeys()
  {
    return new UnmodifiableIterator<String>(__jObj.sortedKeys());
  }

  @Override
  public UnmodifiableJSONArray toJSONArray(JSONArrayI names)
      throws JSONException
  {
    return UnmodifiableJSONArray.getInstance(__jObj.toJSONArray(names));
  }

  @Override
  public String toString(int indentFactor)
      throws JSONException
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
}
