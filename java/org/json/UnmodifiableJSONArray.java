package org.json;

import java.io.Writer;
import java.util.Collection;
import java.util.Map;

public class UnmodifiableJSONArray
  implements JSONArray
{
  public static UnmodifiableJSONArray getInstance(JSONArray jArr)
  {
    if (jArr == null) {
      throw new NullPointerException();
    }
    if (jArr instanceof UnmodifiableJSONArray) {
      return (UnmodifiableJSONArray) jArr;
    }
    return new UnmodifiableJSONArray(jArr);
  }
  
  private final JSONArray __jArr;
  
  private UnmodifiableJSONArray(JSONArray jArr)
  {
    __jArr = jArr;
  }

  @Override
  public Object get(int index)
      throws JSONException
  {
    return JSONUtils.unmodifiable(__jArr.get(index));
  }

  @Override
  public boolean getBoolean(int index)
      throws JSONException
  {
    return __jArr.getBoolean(index);
  }

  @Override
  public double getDouble(int index)
      throws JSONException
  {
    return __jArr.getDouble(index);
  }

  @Override
  public int getInt(int index)
      throws JSONException
  {
    return __jArr.getInt(index);
  }

  @Override
  public JSONArray getJSONArray(int index)
      throws JSONException
  {
    return UnmodifiableJSONArray.getInstance(__jArr.getJSONArray(index));
  }

  @Override
  public JSONObject getJSONObject(int index)
      throws JSONException
  {
    return UnmodifiableJSONObject.getInstance(__jArr.getJSONObject(index));
  }

  @Override
  public long getLong(int index)
      throws JSONException
  {
    return __jArr.getLong(index);
  }

  @Override
  public String getString(int index)
      throws JSONException
  {
    return __jArr.getString(index);
  }

  @Override
  public boolean isNull(int index)
  {
    return __jArr.isNull(index);
  }

  @Override
  public String join(String separator)
      throws JSONException
  {
    return __jArr.join(separator);
  }

  @Override
  public int length()
  {
    return __jArr.length();
  }

  @Override
  public Object opt(int index)
  {
    return __jArr.opt(index);
  }

  @Override
  public boolean optBoolean(int index)
  {
    return __jArr.optBoolean(index);
  }

  @Override
  public boolean optBoolean(int index,
                            boolean defaultValue)
  {
    return __jArr.optBoolean(index, defaultValue);
  }

  @Override
  public double optDouble(int index)
  {
    return __jArr.optDouble(index);
  }

  @Override
  public double optDouble(int index,
                          double defaultValue)
  {
    return __jArr.optDouble(index, defaultValue);
  }

  @Override
  public int optInt(int index)
  {
    return __jArr.optInt(index);
  }

  @Override
  public int optInt(int index,
                    int defaultValue)
  {
    return __jArr.optInt(index, defaultValue);
  }

  @Override
  public JSONArray optJSONArray(int index)
  {
    return UnmodifiableJSONArray.getInstance(__jArr.optJSONArray(index));
  }

  @Override
  public JSONObject optJSONObject(int index)
  {
    return UnmodifiableJSONObject.getInstance(__jArr.optJSONObject(index));
  }

  @Override
  public long optLong(int index)
  {
    return __jArr.optLong(index);
  }

  @Override
  public long optLong(int index,
                      long defaultValue)
  {
    return __jArr.optLong(index, defaultValue);
  }

  @Override
  public String optString(int index)
  {
    return __jArr.optString(index);
  }

  @Override
  public String optString(int index,
                          String defaultValue)
  {
    return __jArr.optString(index, defaultValue);
  }

  @Override
  public JSONArray put(boolean value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(Collection<?> value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(double value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(long value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(Map<String, ?> value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(Object value)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int index,
                        boolean value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int index,
                        Collection<?> value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int index,
                        double value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int index,
                        int value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int index,
                        long value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int index,
                        Map<String, ?> value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray put(int index,
                        Object value)
      throws JSONException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object remove(int index)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject toJSONObject(JSONArray names)
      throws JSONException
  {
    return UnmodifiableJSONObject.getInstance(__jArr.toJSONObject(names));
  }

  @Override
  public String toString(int indentFactor)
      throws JSONException
  {
    return __jArr.toString(indentFactor);
  }  

  @Override
  public String toString()
  {
    return __jArr.toString();
  }

  @Override
  public Writer write(Writer writer)
      throws JSONException
  {
    return __jArr.write(writer);
  }
}
