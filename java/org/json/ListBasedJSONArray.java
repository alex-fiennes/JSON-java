package org.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

public abstract class ListBasedJSONArray
  implements JSONArray
{
  /**
   * the backing List where the JSONArray's properties are kept.
   */
  private final List<Object> __backingList;

  public ListBasedJSONArray(List<Object> backingList)
  {
    __backingList = Preconditions.checkNotNull(backingList, "backingList");
  }

  protected List<Object> getBackingList()
  {
    return __backingList;
  }

  // /**
  // * Construct an empty JSONArray.
  // */
  // public ListBasedJSONArray()
  // {
  // this.__list = new ArrayList<Object>();
  // }

  // @Override
  // public ListBasedJSONArray writableClone()
  // {
  // return this.clone();
  // }

  // @Override
  // public ListBasedJSONArray clone()
  // {
  // ListBasedJSONArray clone = new ListBasedJSONArray();
  // try {
  // for (Object value : this.myArrayList) {
  // if (value instanceof Cloneable) {
  // clone.put(value.getClass().getMethod("clone").invoke(value));
  // } else {
  // clone.put(value);
  // }
  // }
  // } catch (Exception e) {
  // throw new RuntimeException(e);
  // }
  // return clone;
  // }

  // /**
  // * Construct a JSONArray from a JSONTokener.
  // *
  // * @param x
  // * A JSONTokener
  // * @throws JSONException
  // * If there is a syntax error.
  // */
  // public ListBasedJSONArray(JSONTokener x) throws JSONException
  // {
  // this();
  // JSONParser.populateArrayBuilder(x, this);
  // }

  // /**
  // * Construct a JSONArray from a source JSON text.
  // *
  // * @param source
  // * A string that begins with <code>[</code>&nbsp;<small>(left bracket)</small> and ends
  // * with <code>]</code>&nbsp;<small>(right bracket)</small>.
  // * @throws JSONException
  // * If there is a syntax error.
  // */
  // public ListBasedJSONArray(String source) throws JSONException
  // {
  // this(new JSONTokener(source, WritableJSONFactory.getInstance()));
  // }

  // /**
  // * Construct a JSONArray from a Collection.
  // *
  // * @param collection
  // * A Collection.
  // */
  // public ListBasedJSONArray(Collection<?> collection)
  // {
  // this.myArrayList = new ArrayList<Object>();
  // if (collection != null) {
  // Iterator<?> iter = collection.iterator();
  // while (iter.hasNext()) {
  // this.myArrayList.add(WritableJSONObject.wrap(iter.next()));
  // }
  // }
  // }

  // /**
  // * Construct a JSONArray from an array
  // *
  // * @throws JSONException
  // * If not an array.
  // */
  // public ListBasedJSONArray(Object array) throws JSONException
  // {
  // this();
  // if (array.getClass().isArray()) {
  // int length = Array.getLength(array);
  // for (int i = 0; i < length; i += 1) {
  // this.put(WritableJSONObject.wrap(Array.get(array, i)));
  // }
  // } else {
  // throw new JSONException("JSONArray initial value should be a string or collection or array.");
  // }
  // }

  /**
   * Get the object value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return An object value.
   * @throws JSONException
   *           If there is no value for the index.
   */
  @Override
  public Object get(int index)
      throws JSONException
  {
    Object object = opt(index);
    if (object == null) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    return object;
  }

  /**
   * Get the boolean value associated with an index. The string values "true" and "false" are
   * converted to boolean.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The truth.
   * @throws JSONException
   *           If there is no value for the index or if the value is not convertible to boolean.
   */
  @Override
  public boolean getBoolean(int index)
      throws JSONException
  {
    Object object = get(index);
    if (object.equals(Boolean.FALSE)
        || (object instanceof String && ((String) object).equalsIgnoreCase("false"))) {
      return false;
    } else if (object.equals(Boolean.TRUE)
               || (object instanceof String && ((String) object).equalsIgnoreCase("true"))) {
      return true;
    }
    throw new JSONException("JSONArray[" + index + "] is not a boolean.");
  }

  /**
   * Get the double value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value cannot be converted to a number.
   */
  @Override
  public double getDouble(int index)
      throws JSONException
  {
    Object object = get(index);
    try {
      return object instanceof Number
          ? ((Number) object).doubleValue()
          : Double.parseDouble((String) object);
    } catch (Exception e) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
  }

  /**
   * Get the int value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value is not a number.
   */
  @Override
  public int getInt(int index)
      throws JSONException
  {
    Object object = get(index);
    try {
      return object instanceof Number
          ? ((Number) object).intValue()
          : Integer.parseInt((String) object);
    } catch (Exception e) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
  }

  /**
   * Get the JSONArray associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A JSONArray value.
   * @throws JSONException
   *           If there is no value for the index. or if the value is not a JSONArray
   */
  @Override
  public ListBasedJSONArray getJSONArray(int index)
      throws JSONException
  {
    Object object = get(index);
    if (object instanceof ListBasedJSONArray) {
      return (ListBasedJSONArray) object;
    }
    throw new JSONException("JSONArray[" + index + "] is not a JSONArray.");
  }

  /**
   * Get the JSONObject associated with an index.
   * 
   * @param index
   *          subscript
   * @return A JSONObject value.
   * @throws JSONException
   *           If there is no value for the index or if the value is not a JSONObject
   */
  @Override
  public MapBasedJSONObject getJSONObject(int index)
      throws JSONException
  {
    Object object = get(index);
    if (object instanceof MapBasedJSONObject) {
      return (MapBasedJSONObject) object;
    }
    throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
  }

  /**
   * Get the long value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   * @throws JSONException
   *           If the key is not found or if the value cannot be converted to a number.
   */
  @Override
  public long getLong(int index)
      throws JSONException
  {
    Object object = get(index);
    try {
      return object instanceof Number
          ? ((Number) object).longValue()
          : Long.parseLong((String) object);
    } catch (Exception e) {
      throw new JSONException("JSONArray[" + index + "] is not a number.");
    }
  }

  /**
   * Get the string associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A string value.
   * @throws JSONException
   *           If there is no value for the index.
   */
  @Override
  public String getString(int index)
      throws JSONException
  {
    Object object = get(index);
    return Null.getInstance().equals(object) ? null : object.toString();
  }

  /**
   * Determine if the value is null.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return true if the value at the index is null, or if there is no value.
   */
  @Override
  public boolean isNull(int index)
  {
    return Null.getInstance().equals(opt(index));
  }

  // /**
  // * Make a string from the contents of this JSONArray. The <code>separator</code> string is
  // * inserted between each element. Warning: This method assumes that the data structure is
  // * acyclical.
  // *
  // * @param separator
  // * A string that will be inserted between the elements.
  // * @return a string.
  // * @throws JSONException
  // * If the array contains an invalid number.
  // */
  // public final String join(String separator)
  // // ,
  // // Supplier<? extends JSONObjectBuilder> jsonObjectBuilderSupplier,
  // // Supplier<? extends JSONArrayBuilder> jsonArrayBuilderSupplier)
  // throws JSONException
  // {
  // int len = length();
  // StringBuilder sb = new StringBuilder();
  //
  // for (int i = 0; i < len; i += 1) {
  // if (i > 0) {
  // sb.append(separator);
  // }
  // sb.append(MapBasedJSONObject.valueToString(this.__backingList.get(i)));
  // // jsonObjectBuilderSupplier,
  // // jsonArrayBuilderSupplier));
  // }
  // return sb.toString();
  // }

  /**
   * Get the number of elements in the JSONArray, included nulls.
   * 
   * @return The length (or size).
   */
  @Override
  public int length()
  {
    return this.__backingList.size();
  }

  /**
   * Get the optional object value associated with an index.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return An object value, or null if there is no object at that index.
   */
  @Override
  public Object opt(int index)
  {
    return (index < 0 || index >= length()) ? null : this.__backingList.get(index);
  }

  /**
   * Get the optional boolean value associated with an index. It returns false if there is no value
   * at that index, or if the value is not Boolean.TRUE or the String "true".
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The truth.
   */
  @Override
  public boolean optBoolean(int index)
  {
    return optBoolean(index, false);
  }

  /**
   * Get the optional boolean value associated with an index. It returns the defaultValue if there
   * is no value at that index or if it is not a Boolean or the String "true" or "false" (case
   * insensitive).
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          A boolean default.
   * @return The truth.
   */
  @Override
  public boolean optBoolean(int index,
                            boolean defaultValue)
  {
    try {
      return getBoolean(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional double value associated with an index. NaN is returned if there is no value
   * for the index, or if the value is not a number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  @Override
  public double optDouble(int index)
  {
    return optDouble(index, Double.NaN);
  }

  /**
   * Get the optional double value associated with an index. The defaultValue is returned if there
   * is no value for the index, or if the value is not a number and cannot be converted to a number.
   * 
   * @param index
   *          subscript
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  @Override
  public double optDouble(int index,
                          double defaultValue)
  {
    try {
      return getDouble(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional int value associated with an index. Zero is returned if there is no value for
   * the index, or if the value is not a number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  @Override
  public int optInt(int index)
  {
    return optInt(index, 0);
  }

  /**
   * Get the optional int value associated with an index. The defaultValue is returned if there is
   * no value for the index, or if the value is not a number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  @Override
  public int optInt(int index,
                    int defaultValue)
  {
    try {
      return getInt(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional JSONArray associated with an index.
   * 
   * @param index
   *          subscript
   * @return A JSONArray value, or null if the index has no value, or if the value is not a
   *         JSONArray.
   */
  @Override
  public ListBasedJSONArray optJSONArray(int index)
  {
    Object o = opt(index);
    return o instanceof ListBasedJSONArray ? (ListBasedJSONArray) o : null;
  }

  /**
   * Get the optional JSONObject associated with an index. Null is returned if the key is not found,
   * or null if the index has no value, or if the value is not a JSONObject.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A JSONObject value.
   */
  @Override
  public JSONObject optJSONObject(int index)
  {
    Object o = opt(index);
    return o instanceof JSONObject ? (JSONObject) o : null;
  }

  /**
   * Get the optional long value associated with an index. Zero is returned if there is no value for
   * the index, or if the value is not a number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return The value.
   */
  @Override
  public long optLong(int index)
  {
    return optLong(index, 0);
  }

  /**
   * Get the optional long value associated with an index. The defaultValue is returned if there is
   * no value for the index, or if the value is not a number and cannot be converted to a number.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return The value.
   */
  @Override
  public long optLong(int index,
                      long defaultValue)
  {
    try {
      return getLong(index);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Get the optional string value associated with an index. It returns an empty string if there is
   * no value at that index. If the value is not a string and is not null, then it is coverted to a
   * string.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @return A String value.
   */
  @Override
  public String optString(int index)
  {
    return optString(index, "");
  }

  /**
   * Get the optional string associated with an index. The defaultValue is returned if the key is
   * not found.
   * 
   * @param index
   *          The index must be between 0 and length() - 1.
   * @param defaultValue
   *          The default value.
   * @return A String value.
   */
  @Override
  public String optString(int index,
                          String defaultValue)
  {
    Object object = opt(index);
    return object != null ? object.toString() : defaultValue;
  }

  /**
   * Append a boolean value. This increases the array's length by one.
   * 
   * @param value
   *          A boolean value.
   * @return this.
   */
  @Override
  public ListBasedJSONArray put(boolean value)
  {
    put(value ? Boolean.TRUE : Boolean.FALSE);
    return this;
  }

  // /**
  // * Put a value in the JSONArray, where the value will be a JSONArray which is produced from a
  // * Collection.
  // *
  // * @param value
  // * A Collection value.
  // * @return this.
  // */
  // @Override
  // public ListBasedJSONArray put(Collection<?> value)
  // {
  // put(new ListBasedJSONArray(value));
  // return this;
  // }

  /**
   * Append a double value. This increases the array's length by one.
   * 
   * @param value
   *          A double value.
   * @throws JSONException
   *           if the value is not finite.
   * @return this.
   */
  @Override
  public ListBasedJSONArray put(double value)
      throws JSONException
  {
    Double d = Double.valueOf(value);
    JSONComponents.testValidity(d);
    put(d);
    return this;
  }

  /**
   * Append an int value. This increases the array's length by one.
   * 
   * @param value
   *          An int value.
   * @return this.
   */
  @Override
  public ListBasedJSONArray put(int value)
  {
    put(Integer.valueOf(value));
    return this;
  }

  /**
   * Append an long value. This increases the array's length by one.
   * 
   * @param value
   *          A long value.
   * @return this.
   */
  @Override
  public ListBasedJSONArray put(long value)
  {
    put(Long.valueOf(value));
    return this;
  }

  // /**
  // * Put a value in the JSONArray, where the value will be a JSONObject which is produced from a
  // * Map.
  // *
  // * @param value
  // * A Map value.
  // * @return this.
  // */
  // @Override
  // public ListBasedJSONArray put(Map<String, ?> value)
  // {
  // put(new WritableJSONObject(value));
  // return this;
  // }

  /**
   * Append an object value. This increases the array's length by one.
   * 
   * @param value
   *          An object value. The value should be a Boolean, Double, Integer, JSONArray,
   *          JSONObject, Long, or String, or the JSONObject.NULL object.
   * @return this.
   */
  @Override
  public ListBasedJSONArray put(Object value)
  {
    this.__backingList.add(value);
    return this;
  }

  /**
   * Put or replace a boolean value in the JSONArray. If the index is greater than the length of the
   * JSONArray, then null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A boolean value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  @Override
  public ListBasedJSONArray put(int index,
                                boolean value)
      throws JSONException
  {
    put(index, value ? Boolean.TRUE : Boolean.FALSE);
    return this;
  }

  // /**
  // * Put a value in the JSONArray, where the value will be a JSONArray which is produced from a
  // * Collection.
  // *
  // * @param index
  // * The subscript.
  // * @param value
  // * A Collection value.
  // * @return this.
  // * @throws JSONException
  // * If the index is negative or if the value is not finite.
  // */
  // @Override
  // public ListBasedJSONArray put(int index,
  // Collection<?> value)
  // throws JSONException
  // {
  // put(index, new ListBasedJSONArray(value));
  // return this;
  // }

  /**
   * Put or replace a double value. If the index is greater than the length of the JSONArray, then
   * null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A double value.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the value is not finite.
   */
  @Override
  public ListBasedJSONArray put(int index,
                                double value)
      throws JSONException
  {
    put(index, Double.valueOf(value));
    return this;
  }

  /**
   * Put or replace an int value. If the index is greater than the length of the JSONArray, then
   * null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          An int value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  @Override
  public ListBasedJSONArray put(int index,
                                int value)
      throws JSONException
  {
    put(index, Integer.valueOf(value));
    return this;
  }

  /**
   * Put or replace a long value. If the index is greater than the length of the JSONArray, then
   * null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          A long value.
   * @return this.
   * @throws JSONException
   *           If the index is negative.
   */
  @Override
  public ListBasedJSONArray put(int index,
                                long value)
      throws JSONException
  {
    put(index, Long.valueOf(value));
    return this;
  }

  // /**
  // * Put a value in the JSONArray, where the value will be a JSONObject which is produced from a
  // * Map.
  // *
  // * @param index
  // * The subscript.
  // * @param value
  // * The Map value.
  // * @return this.
  // * @throws JSONException
  // * If the index is negative or if the the value is an invalid number.
  // */
  // @Override
  // public ListBasedJSONArray put(int index,
  // Map<String, ?> value)
  // throws JSONException
  // {
  // put(index, new WritableJSONObject(value));
  // return this;
  // }

  /**
   * Put or replace an object value in the JSONArray. If the index is greater than the length of the
   * JSONArray, then null elements will be added as necessary to pad it out.
   * 
   * @param index
   *          The subscript.
   * @param value
   *          The value to put into the array. The value should be a Boolean, Double, Integer,
   *          JSONArray, JSONObject, Long, or String, or the JSONObject.NULL object.
   * @return this.
   * @throws JSONException
   *           If the index is negative or if the the value is an invalid number.
   */
  @Override
  public ListBasedJSONArray put(int index,
                                Object value)
      throws JSONException
  {
    JSONComponents.testValidity(value);
    if (index < 0) {
      throw new JSONException("JSONArray[" + index + "] not found.");
    }
    if (index < length()) {
      this.__backingList.set(index, value);
    } else {
      while (index != length()) {
        put(Null.getInstance());
      }
      put(value);
    }
    return this;
  }

  /**
   * Remove an index and close the hole.
   * 
   * @param index
   *          The index of the element to be removed.
   * @return The value that was associated with the index, or null if there was no value.
   */
  @Override
  public Object remove(int index)
  {
    Object o = opt(index);
    this.__backingList.remove(index);
    return o;
  }

  @Override
  public boolean remove(Object object)
  {
    return this.__backingList.remove(object);
  }

  // /**
  // * Produce a JSONObject by combining a JSONArray of names with the values of this JSONArray.
  // *
  // * @param names
  // * A JSONArray containing a list of key strings. These will be paired with the values.
  // * @return A JSONObject, or null if there are no names or if this JSONArray has no values.
  // * @throws JSONException
  // * If any of the names are null.
  // */
  // public JSONObject toJSONObject(JSONArray names,
  // Supplier<? extends JSONObjectBuilder> jsonObjectBuilderSupplier)
  // throws JSONException
  // {
  // if (names == null || names.length() == 0 || length() == 0) {
  // return null;
  // }
  // JSONObjectBuilder builder = jsonObjectBuilderSupplier.get();
  // for (int i = 0; i < names.length(); i += 1) {
  // builder.putOnce(names.getString(i), this.opt(i));
  // }
  // return builder.build();
  // }

  /**
   * Make a JSON text of this JSONArray. For compactness, no unnecessary whitespace is added. If it
   * is not possible to produce a syntactically correct JSON text then null will be returned
   * instead. This could occur if the array contains an invalid number.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return a printable, displayable, transmittable representation of the array.
   */
  @Override
  public String toString()
  {
    try {
      int len = length();
      StringBuilder sb = new StringBuilder("[");

      for (int i = 0; i < len; i += 1) {
        if (i > 0) {
          sb.append(',');
        }
        sb.append(JSONComponents.valueToString(this.__backingList.get(i)));
        // jsonObjectBuilderSupplier,
        // jsonArrayBuilderSupplier));
      }
      sb.append("]");
      return sb.toString();
    } catch (JSONException e) {
      throw new RuntimeException("immpossible?", e);
    }
    //
    // try {
    // return '[' + join(",") + ']';
    // } catch (Exception e) {
    // return null;
    // }
  }

  /**
   * Make a prettyprinted JSON text of this JSONArray. Warning: This method assumes that the data
   * structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @return a printable, displayable, transmittable representation of the object, beginning with
   *         <code>[</code>&nbsp;<small>(left bracket)</small> and ending with <code>]</code>
   *         &nbsp;<small>(right bracket)</small>.
   */
  @Override
  public String toString(int indentFactor)
  {
    return toString(indentFactor, 0);
  }

  /**
   * Make a prettyprinted JSON text of this JSONArray. Warning: This method assumes that the data
   * structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indention of the top level.
   * @return a printable, displayable, transmittable representation of the array.
   */
  @Override
  public final String toString(int indentFactor,
                               int indent)
  {
    try {
      int len = length();
      if (len == 0) {
        return "[]";
      }
      int i;
      StringBuilder sb = new StringBuilder("[");
      if (len == 1) {
        sb.append(JSONComponents.valueToString(this.__backingList.get(0), indentFactor, indent));
      } else {
        int newindent = indent + indentFactor;
        sb.append('\n');
        for (i = 0; i < len; i += 1) {
          if (i > 0) {
            sb.append(",\n");
          }
          for (int j = 0; j < newindent; j += 1) {
            sb.append(' ');
          }
          sb.append(JSONComponents.valueToString(this.__backingList.get(i),
                                                 indentFactor,
                                                 newindent));
        }
        sb.append('\n');
        for (i = 0; i < indent; i += 1) {
          sb.append(' ');
        }
      }
      sb.append(']');
      return sb.toString();
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Write the contents of the JSONArray as JSON text to a writer. For compactness, no whitespace is
   * added.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return The writer.
   * @throws JSONException
   */
  @Override
  public Appendable write(Appendable writer)
      // ,
      // Supplier<? extends JSONObjectBuilder> jsonObjectBuilderSupplier,
      // Supplier<? extends JSONArrayBuilder> jsonArrayBuilderSupplier)
      throws JSONException
  {
    try {
      boolean b = false;
      int len = length();

      writer.append('[');

      for (int i = 0; i < len; i += 1) {
        if (b) {
          writer.append(',');
        }
        Object v = this.__backingList.get(i);
        if (v instanceof WritableJSONObject) {
          ((WritableJSONObject) v).write(writer);
        } else if (v instanceof ListBasedJSONArray) {
          ((ListBasedJSONArray) v).write(writer);
        } else {
          writer.append(JSONComponents.valueToString(v));
          // ,
          // jsonObjectBuilderSupplier,
          // jsonArrayBuilderSupplier));
        }
        b = true;
      }
      writer.append(']');
      return writer;
    } catch (IOException e) {
      throw new JSONException(e);
    }
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj instanceof JSONArray) {
      return ((JSONArray) obj).equalsList(__backingList);
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return __backingList.hashCode();
  }

  @Override
  public boolean equalsList(List<Object> list)
  {
    return __backingList.equals(list);
  }

  @Override
  public Iterator<Object> iterator()
  {
    return __backingList.iterator();
  }

  @Override
  public int indexOf(Object value)
  {
    return __backingList.indexOf(value);
  }

  @Override
  public <A extends JSONArray, O extends JSONObject> A clone(JSONBuilder<A, O> builder)
      throws JSONException
  {
    return JSONArrays.clone(getBackingList(), builder);
  }

}
