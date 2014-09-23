package org.json;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface JSONArray
  extends Iterable<Object>
{
  public Iterator<Object> iterator();

  public boolean equalsList(List<Object> list);
  
  public WritableJSONArray writableClone();
  
  /**
   * Get the object value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return An object value.
   * @throws JSONException
   *           If there is no value for the index.
   */
  public Object get(int index)
      throws JSONException;

  /**
   * Get the boolean value associated with an index. The string values "true"
   * and "false" are converted to boolean.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The truth.
   * @throws JSONException
   *           If there is no value for the index or if the value is not
   *           convertible to boolean.
   */
  public boolean getBoolean(int index)
      throws JSONException;

  /**
   * Get the double value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value cannot be converted to a
   *           number.
   */
  public double getDouble(int index)
      throws JSONException;

  /**
   * Get the int value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value is not a number.
   */
  public int getInt(int index)
      throws JSONException;

  /**
   * Get the JSONArray associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A JSONArray value.
   * @throws JSONException
   *           If there is no value for the index. or if the value is not a
   *           JSONArray
   */
  public JSONArray getJSONArray(int index)
      throws JSONException;

  /**
   * Get the JSONObject associated with an index.
   * 
   * @param index
   *          subscript
   * @return A JSONObject value.
   * @throws JSONException
   *           If there is no value for the index or if the value is not a
   *           JSONObject
   */
  public JSONObject getJSONObject(int index)
      throws JSONException;

  /**
   * Get the long value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value cannot be converted to a
   *           number.
   */
  public long getLong(int index)
      throws JSONException;

  /**
   * Get the string associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A string value.
   * @throws JSONException
   *           If there is no value for the index.
   */
  public String getString(int index)
      throws JSONException;

  /**
   * Determine if the value is null.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return true if the value at the index is null, or if there is no value.
   */
  public boolean isNull(int index);

  /**
   * Make a string from the contents of this JSONArray. The
   * <code>separator</code> string is inserted between each element. Warning:
   * This method assumes that the data structure is acyclical.
   * 
   * @param separator
   *          A string that will be inserted between the elements.
   * @return a string.
   * @throws JSONException
   *           If the array contains an invalid number.
   */
  public String join(String separator)
      throws JSONException;

  /**
   * Get the number of elements in the JSONArray, included nulls.
   * 
   * @return The length (or size).
   */
  public int length();

  /**
   * Get the optional object value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return An object value, or null if there is no object at that index.
   */
  public Object opt(int index);

  /**
   * Get the optional boolean value associated with an index. It returns false
   * if there is no value at that index, or if the value is not Boolean.TRUE or
   * the String "true".
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The truth.
   */
  public boolean optBoolean(int index);

  /**
   * Get the optional boolean value associated with an index. It returns the
   * defaultValue if there is no value at that index or if it is not a Boolean
   * or the String "true" or "false" (case insensitive).
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          A boolean default.
   * @return The truth.
   */
  public boolean optBoolean(int index,
                            boolean defaultValue);

  /**
   * Get the optional double value associated with an index. NaN is returned if
   * there is no value for the index, or if the value is not a number and
   * cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  public double optDouble(int index);

  /**
   * Get the optional double value associated with an index. The defaultValue
   * is returned if there is no value for the index, or if the value is not a
   * number and cannot be converted to a number.
   * 
   * @param index
   *          subscript
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  public double optDouble(int index,
                          double defaultValue);

  /**
   * Get the optional int value associated with an index. Zero is returned if
   * there is no value for the index, or if the value is not a number and
   * cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  public int optInt(int index);

  /**
   * Get the optional int value associated with an index. The defaultValue is
   * returned if there is no value for the index, or if the value is not a
   * number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  public int optInt(int index,
                    int defaultValue);

  /**
   * Get the optional JSONArray associated with an index.
   * 
   * @param index
   *          subscript
   * @return A JSONArray value, or null if the index has no value, or if the
   *         value is not a JSONArray.
   */
  public JSONArray optJSONArray(int index);

  /**
   * Get the optional JSONObject associated with an index. Null is returned if
   * the key is not found, or null if the index has no value, or if the value
   * is not a JSONObject.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A JSONObject value.
   */
  public JSONObject optJSONObject(int index);

  /**
   * Get the optional long value associated with an index. Zero is returned if
   * there is no value for the index, or if the value is not a number and
   * cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  public long optLong(int index);

  /**
   * Get the optional long value associated with an index. The defaultValue is
   * returned if there is no value for the index, or if the value is not a
   * number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  public long optLong(int index,
                      long defaultValue);

  /**
   * Get the optional string value associated with an index. It returns an
   * empty string if there is no value at that index. If the value is not a
   * string and is not null, then it is coverted to a string.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A String value.
   */
  public String optString(int index);

  /**
   * Get the optional string associated with an index. The defaultValue is
   * returned if the key is not found.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return A String value.
   */
  public String optString(int index,
                          String defaultValue);

  /**
   * Append a boolean value. This increases the array's length by one.
   * 
   * @param value
   *          A boolean value.
   * @return this.
   */
  public JSONArray put(boolean value);

  /**
   * Put a value in the JSONArray, where the value will be a JSONArray which is
   * produced from a Collection.
   * 
   * @param value
   *          A Collection value.
   * @return this.
   */
  public JSONArray put(Collection<?> value);

  /**
   * Append a double value. This increases the array's length by one.
   * 
   * @param value
   *          A double value.
   * @throws JSONException
   *           if the value is not finite.
   * @return this.
   */
  public JSONArray put(double value)
      throws JSONException;

  /**
   * Append an int value. This increases the array's length by one.
   * 
   * @param value
   *          An int value.
   * @return this.
   */
  public JSONArray put(int value);

  /**
   * Append an long value. This increases the array's length by one.
   * 
   * @param value
   *          A long value.
   * @return this.
   */
  public JSONArray put(long value);

  /**
   * Put a value in the JSONArray, where the value will be a JSONObject which
   * is produced from a Map.
   * 
   * @param value
   *          A Map value.
   * @return this.
   */
  public JSONArray put(Map<String, ?> value);

  /**
   * Append an object value. This increases the array's length by one.
   * 
   * @param value
   *          An object value. The value should be a Boolean, Double, Integer,
   *          JSONArray, JSONObject, Long, or String, or the JSONObject.NULL
   *          object.
   * @return this.
   */
  public JSONArray put(Object value);

  /**
   * Put or replace a boolean value in the JSONArray. If the index is greater
   * than the length of the JSONArray, then null elements will be added as
   * necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A boolean value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  public JSONArray put(int index,
                        boolean value)
      throws JSONException;

  /**
   * Put a value in the JSONArray, where the value will be a JSONArray which is
   * produced from a Collection.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A Collection value.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the value is not finite.
   */
  public JSONArray put(int index,
                        Collection<?> value)
      throws JSONException;

  /**
   * Put or replace a double value. If the index is greater than the length of
   * the JSONArray, then null elements will be added as necessary to pad it
   * out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A double value.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the value is not finite.
   */
  public JSONArray put(int index,
                        double value)
      throws JSONException;

  /**
   * Put or replace an int value. If the index is greater than the length of
   * the JSONArray, then null elements will be added as necessary to pad it
   * out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          An int value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  public JSONArray put(int index,
                        int value)
      throws JSONException;

  /**
   * Put or replace a long value. If the index is greater than the length of
   * the JSONArray, then null elements will be added as necessary to pad it
   * out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A long value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  public JSONArray put(int index,
                        long value)
      throws JSONException;

  /**
   * Put a value in the JSONArray, where the value will be a JSONObject which
   * is produced from a Map.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          The Map value.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the the value is an invalid
   *           number.
   */
  public JSONArray put(int index,
                        Map<String, ?> value)
      throws JSONException;

  /**
   * Put or replace an object value in the JSONArray. If the index is greater
   * than the length of the JSONArray, then null elements will be added as
   * necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          The value to put into the array. The value should be a Boolean,
   *          Double, Integer, JSONArray, JSONObject, Long, or String, or the
   *          JSONObject.NULL object.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the the value is an invalid
   *           number.
   */
  public JSONArray put(int index,
                        Object value)
      throws JSONException;

  /**
   * Remove an index and close the hole.
   * 
   * @param index
   *          The index of the element to be removed.
   * @return The value that was associated with the index, or null if there was
   *         no value.
   */
  public Object remove(int index);
  
  /**
   * Remove the first instance of an Object from the array.
   * @param obj The object to be removed from the array.
   * @return true if it is has been removed.
   */
  public boolean remove(Object obj);

  /**
   * Produce a JSONObject by combining a JSONArray of names with the values of
   * this JSONArray.
   * 
   * @param names
   *          A JSONArray containing a list of key strings. These will be
   *          paired with the values.
   * @return A JSONObject, or null if there are no names or if this JSONArray
   *         has no values.
   * @throws JSONException
   *           If any of the names are null.
   */
  public JSONObject toJSONObject(JSONArray names)
      throws JSONException;

  /**
   * Make a JSON text of this JSONArray. For compactness, no unnecessary
   * whitespace is added. If it is not possible to produce a syntactically
   * correct JSON text then null will be returned instead. This could occur if
   * the array contains an invalid number.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return a printable, displayable, transmittable representation of the
   *         array.
   */
  public String toString();

  /**
   * Make a prettyprinted JSON text of this JSONArray. Warning: This method
   * assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @return a printable, displayable, transmittable representation of the
   *         object, beginning with <code>[</code>&nbsp;<small>(left
   *         bracket)</small> and ending with <code>]</code>&nbsp;<small>(right
   *         bracket)</small>.
   * @throws RuntimeException if there is an unformattable item inside the JSONArray.
   */
  public String toString(int indentFactor);

  /**
   * Write the contents of the JSONArray as JSON text to a writer. For
   * compactness, no whitespace is added.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return The writer.
   * @throws JSONException
   */
  public Writer write(Writer writer)
      throws JSONException;
  
  public int indexOf(Object value);
}