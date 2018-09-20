package org.json;

import java.util.Iterator;
import java.util.Map;

public interface JSONObject
  extends JSONComponent
{
  public static final JSONObject EMPTY =
      UnmodifiableJSONObject.getInstance(new WritableJSONObject());

  public boolean equalsMap(Map<String, Object> map);

  public <A extends JSONArray, O extends JSONObject> O clone(JSONBuilder<A, O> builder)
      throws JSONException;

  /**
   * Accumulate values under a key. It is similar to the put method except that if there is already
   * an object stored under the key then a JSONArray is stored under the key to hold all of the
   * accumulated values. If there is already a JSONArray, then the new value is appended to it. In
   * contrast, the put method replaces the previous value.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object to be accumulated under the key.
   * @return this.
   * @throws JSONException
   *           If the value is an invalid number or if the key is null.
   */
  public JSONObject accumulate(String key,
                               Object value)
      throws JSONException;

  /**
   * Append values to the array under a key. If the key does not exist in the JSONObject, then the
   * key is put in the JSONObject with its value being a JSONArray containing the value parameter.
   * If the key was already associated with a JSONArray, then the value parameter is appended to it.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object to be accumulated under the key.
   * @return this.
   * @throws JSONException
   *           If the key is null or if the current value associated with the key is not a
   *           JSONArray.
   */
  public JSONObject append(String key,
                           Object value)
      throws JSONException;

  /**
   * Get the value object associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The object associated with the key.
   * @throws JSONException
   *           if the key is not found.
   */
  public Object get(String key)
      throws JSONException;

  /**
   * Get the boolean value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The truth.
   * @throws JSONException
   *           if the value is not a Boolean or the String "true" or "false".
   */
  public boolean getBoolean(String key)
      throws JSONException;

  /**
   * Get the double value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The numeric value.
   * @throws JSONException
   *           if the key is not found or if the value is not a Number object and cannot be
   *           converted to a number.
   */
  public double getDouble(String key)
      throws JSONException;

  /**
   * Get the int value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The integer value.
   * @throws JSONException
   *           if the key is not found or if the value cannot be converted to an integer.
   */
  public int getInt(String key)
      throws JSONException;

  /**
   * Get the JSONArray value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return A JSONArray which is the value.
   * @throws JSONException
   *           if the key is not found or if the value is not a JSONArray.
   */
  public JSONArray getJSONArray(String key)
      throws JSONException;

  /**
   * Get the JSONObject value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   * @throws JSONException
   *           if the key is not found or if the value is not a JSONObject.
   */
  public JSONObject getJSONObject(String key)
      throws JSONException;

  /**
   * Get the long value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return The long value.
   * @throws JSONException
   *           if the key is not found or if the value cannot be converted to a long.
   */
  public long getLong(String key)
      throws JSONException;

  /**
   * Get the string associated with a key.
   * 
   * @param key
   *          A key string.
   * @return A string which is the value.
   * @throws JSONException
   *           if the key is not found.
   */
  public String getString(String key)
      throws JSONException;

  /**
   * Determine if the JSONObject contains a specific key.
   * 
   * @param key
   *          A key string.
   * @return true if the key exists in the JSONObject.
   */
  public boolean has(String key);

  /**
   * Increment a property of a JSONObject. If there is no such property, create one with a value of
   * 1. If there is such a property, and if it is an Integer, Long, Double, or Float, then add one
   * to it.
   * 
   * @param key
   *          A key string.
   * @return this.
   * @throws JSONException
   *           If there is already a property with this name that is not an Integer, Long, Double,
   *           or Float.
   */
  public JSONObject increment(String key)
      throws JSONException;

  /**
   * Determine if the value associated with the key is null or if there is no value.
   * 
   * @param key
   *          A key string.
   * @return true if there is no value associated with the key or if the value is the
   *         JSONObject.NULL object.
   */
  public boolean isNull(String key);

  /**
   * Get an enumeration of the keys of the JSONObject.
   * 
   * @return An iterator of the keys.
   */
  public Iterator<String> keys();

  /**
   * Get the number of keys stored in the JSONObject.
   * 
   * @return The number of keys in the JSONObject.
   */
  public int length();

  /**
   * Get an optional value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return An object which is the value, or null if there is no value.
   */
  public Object opt(String key);

  /**
   * Get an optional boolean associated with a key. It returns false if there is no such key, or if
   * the value is not Boolean.TRUE or the String "true".
   * 
   * @param key
   *          A key string.
   * @return The truth.
   */
  public boolean optBoolean(String key);

  /**
   * Get an optional boolean associated with a key. It returns the defaultValue if there is no such
   * key, or if it is not a Boolean or the String "true" or "false" (case insensitive).
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return The truth.
   */
  public boolean optBoolean(String key,
                            boolean defaultValue);

  /**
   * Get an optional double associated with a key, or NaN if there is no such key or if its value is
   * not a number. If the value is a string, an attempt will be made to evaluate it as a number.
   * 
   * @param key
   *          A string which is the key.
   * @return An object which is the value.
   */
  public double optDouble(String key);

  /**
   * Get an optional double associated with a key, or the defaultValue if there is no such key or if
   * its value is not a number. If the value is a string, an attempt will be made to evaluate it as
   * a number.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  public double optDouble(String key,
                          double defaultValue);

  /**
   * Get the compulsory number as a Double Object throwing a JSONException if this is not possible.
   */
  public Double getDoubleObj(String key)
      throws JSONException;

  /**
   * Get the optional number as a Double Object returning the optional defaultValue if this is not
   * possible.
   */
  public Double optDoubleObj(String key,
                             Double defaultValue);

  /**
   * Get an optional int value associated with a key, or zero if there is no such key or if the
   * value is not a number. If the value is a string, an attempt will be made to evaluate it as a
   * number.
   * 
   * @param key
   *          A key string.
   * @return An object which is the value.
   */
  public int optInt(String key);

  /**
   * Get an optional Integer value associated with the key, or null if there is no such key or if
   * the value is not a number and the string representation cannot be parsed as an Integer.
   */
  public Integer optInteger(String key);

  /**
   * Get an optional int value associated with a key, or the default if there is no such key or if
   * the value is not a number. If the value is a string, an attempt will be made to evaluate it as
   * a number.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  public int optInt(String key,
                    int defaultValue);

  /**
   * Get an optional Integer value associated with the key, or the specified defaultValue if there
   * is no such key or if the value is not a number and the string representation cannot be parsed
   * as an Integer.
   */
  public Integer optInteger(String key,
                            Integer defaultValue);

  /**
   * Get an optional JSONArray associated with a key. It returns null if there is no such key, or if
   * its value is not a JSONArray.
   * 
   * @param key
   *          A key string.
   * @return A JSONArray which is the value.
   */
  public JSONArray optJSONArray(String key);

  /**
   * Get an optional JSONObject associated with a key. It returns null if there is no such key, or
   * if its value is not a JSONObject.
   * 
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   */
  public JSONObject optJSONObject(String key);

  /**
   * Get an optional long value associated with a key, or zero if there is no such key or if the
   * value is not a number. If the value is a string, an attempt will be made to evaluate it as a
   * number.
   * 
   * @param key
   *          A key string.
   * @return An object which is the value.
   */
  public long optLong(String key);

  /**
   * Get an optional long value associated with a key, or the default if there is no such key or if
   * the value is not a number. If the value is a string, an attempt will be made to evaluate it as
   * a number.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return An object which is the value.
   */
  public long optLong(String key,
                      long defaultValue);

  /**
   * Get an optional string associated with a key. It returns an empty string if there is no such
   * key. If the value is not a string and is not null, then it is converted to a string.
   * 
   * @param key
   *          A key string.
   * @return A string which is the value.
   */
  public String optString(String key);

  /**
   * Get an optional string associated with a key. It returns the defaultValue if there is no such
   * key.
   * 
   * @param key
   *          A key string.
   * @param defaultValue
   *          The default.
   * @return A string which is the value.
   */
  public String optString(String key,
                          String defaultValue);

  /**
   * Put a key/boolean pair in the JSONObject.
   * 
   * @param key
   *          A key string.
   * @param value
   *          A boolean which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null.
   */
  public JSONObject put(String key,
                        boolean value)
      throws JSONException;

  /**
   * Put a key/double pair in the JSONObject.
   * 
   * @param key
   *          A key string.
   * @param value
   *          A double which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null or if the number is invalid.
   */
  public JSONObject put(String key,
                        double value)
      throws JSONException;

  /**
   * Put a key/int pair in the JSONObject.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An int which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null.
   */
  public JSONObject put(String key,
                        int value)
      throws JSONException;

  /**
   * Put a key/long pair in the JSONObject.
   * 
   * @param key
   *          A key string.
   * @param value
   *          A long which is the value.
   * @return this.
   * @throws JSONException
   *           If the key is null.
   */
  public JSONObject put(String key,
                        long value)
      throws JSONException;

  /**
   * Put a key/value pair in the JSONObject. If the value is null, then the key will be removed from
   * the JSONObject if it is present.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object which is the value. It should be of one of these types: Boolean, Double,
   *          Integer, JSONArray, JSONObject, Long, String, or the JSONObject.NULL object.
   * @return this.
   * @throws JSONException
   *           If the value is non-finite number or if the key is null.
   */
  public JSONObject put(String key,
                        Object value)
      throws JSONException;

  /**
   * Put a key/value pair in the JSONObject, but only if the key and the value are both non-null,
   * and only if there is not already a member with that name.
   * 
   * @param key
   * @param value
   * @return his.
   * @throws JSONException
   *           if the key is a duplicate
   */
  public JSONObject putOnce(String key,
                            Object value)
      throws JSONException;

  /**
   * Put a key/value pair in the JSONObject, but only if the key and the value are both non-null.
   * 
   * @param key
   *          A key string.
   * @param value
   *          An object which is the value. It should be of one of these types: Boolean, Double,
   *          Integer, JSONArray, JSONObject, Long, String, or the JSONObject.NULL object.
   * @return this.
   * @throws JSONException
   *           If the value is a non-finite number.
   */
  public JSONObject putOpt(String key,
                           Object value)
      throws JSONException;

  /**
   * Remove a name and its value, if present.
   * 
   * @param key
   *          The name to be removed.
   * @return The value that was associated with the name, or null if there was no value.
   */
  public Object remove(String key);

  /**
   * Get an enumeration of the keys of the JSONObject. The keys will be sorted alphabetically.
   * 
   * @return An iterator of the keys.
   */
  public Iterator<String> sortedKeys();
}