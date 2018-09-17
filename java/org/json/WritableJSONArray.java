package org.json;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

/**
 * A JSONArray is an ordered sequence of values. Its external text form is a string wrapped in
 * square brackets with commas separating the values. The internal form is an object having
 * <code>get</code> and <code>opt</code> methods for accessing the values by index, and
 * <code>put</code> methods for adding or replacing values. The values can be any of these types:
 * <code>Boolean</code>, <code>JSONArray</code>, <code>JSONObject</code>, <code>Number</code>,
 * <code>String</code>, or the <code>JSONObject.NULL object</code>.
 * <p>
 * The constructor can convert a JSON text into a Java object. The <code>toString</code> method
 * converts to JSON text.
 * <p>
 * A <code>get</code> method returns a value if one can be found, and throws an exception if one
 * cannot be found. An <code>opt</code> method returns a default value instead of throwing an
 * exception, and so is useful for obtaining optional values.
 * <p>
 * The generic <code>get()</code> and <code>opt()</code> methods return an object which you can cast
 * or query for type. There are also typed <code>get</code> and <code>opt</code> methods that do
 * type checking and type coercion for you.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to JSON syntax rules.
 * The constructors are more forgiving in the texts they will accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just before the closing
 * bracket.</li>
 * <li>The <code>null</code> value will be inserted when there is <code>,</code>
 * &nbsp;<small>(comma)</small> elision.</li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote or single quote,
 * and if they do not contain leading or trailing spaces, and if they do not contain any of these
 * characters: <code>{ } [ ] / \ : , = ; #</code> and if they do not look like numbers and if they
 * are not the reserved words <code>true</code>, <code>false</code>, or <code>null</code>.</li>
 * <li>Values can be separated by <code>;</code> <small>(semicolon)</small> as well as by
 * <code>,</code> <small>(comma)</small>.</li>
 * <li>Numbers may have the <code>0x-</code> <small>(hex)</small> prefix.</li>
 * </ul>
 * 
 * @author JSON.org
 * @version 2010-12-28
 */
public class WritableJSONArray
  extends ListBasedJSONArray
  implements JSONArray, Cloneable
{
  // public static WritableJSONArray create(JSONTokener x)
  // throws JSONException
  // {
  // Builder builder = getJSONArrayBuilderSupplier().get().create();
  // JSONParser.populateArrayBuilder(x, builder);
  // return builder.build();
  // }

  /**
   * Construct an empty JSONArray.
   */
  public WritableJSONArray()
  {
    super(new ArrayList<Object>());
  }

  private WritableJSONArray(List<Object> values)
  {
    super(values);
  }

//  @Override
//  public WritableJSONArray writableClone()
//  {
//    return this.clone();
//  }

  @Override
  public WritableJSONArray clone()
  {
    WritableJSONArray clone = new WritableJSONArray();
    try {
      for (Object value : getBackingList()) {
        if (value instanceof Cloneable) {
          clone.put(value.getClass().getMethod("clone").invoke(value));
        } else {
          clone.put(value);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return clone;
  }

  // /**
  // * Construct a JSONArray from a JSONTokener.
  // *
  // * @param x
  // * A JSONTokener
  // * @throws JSONException
  // * If there is a syntax error.
  // */
  // public WritableJSONArray(JSONTokener x) throws JSONException
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
  // public WritableJSONArray(String source) throws JSONException
  // {
  // this(new JSONTokener(source, WritableJSONFactory.getInstance()));
  // }

  // /**
  // * Construct a JSONArray from a Collection.
  // *
  // * @param collection
  // * A Collection.
  // */
  // public WritableJSONArray(Collection<?> collection)
  // {
  // super(new ArrayList<Object>());
  // if (collection != null) {
  // List<Object> backingList = getBackingList();
  // Iterator<?> iter = collection.iterator();
  // while (iter.hasNext()) {
  // backingList.add(JSONObjects.wrap(iter.next()));
  // }
  // }
  // }

  // /**
  // * Construct a JSONArray from an array
  // *
  // * @throws JSONException
  // * If not an array.
  // */
  // public WritableJSONArray(Object array) throws JSONException
  // {
  // this();
  // if (array.getClass().isArray()) {
  // int length = Array.getLength(array);
  // for (int i = 0; i < length; i += 1) {
  // this.put(JSONObjects.wrap(Array.get(array, i)));
  // }
  // } else {
  // throw new JSONException("JSONArray initial value should be a string or collection or array.");
  // }
  // }

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
  public WritableJSONArray getJSONArray(int index)
      throws JSONException
  {
    Object object = get(index);
    if (object instanceof WritableJSONArray) {
      return (WritableJSONArray) object;
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
  public WritableJSONObject getJSONObject(int index)
      throws JSONException
  {
    Object object = get(index);
    if (object instanceof WritableJSONObject) {
      return (WritableJSONObject) object;
    }
    throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
  }

//  /**
//   * Make a string from the contents of this JSONArray. The <code>separator</code> string is
//   * inserted between each element. Warning: This method assumes that the data structure is
//   * acyclical.
//   * 
//   * @param separator
//   *          A string that will be inserted between the elements.
//   * @return a string.
//   * @throws JSONException
//   *           If the array contains an invalid number.
//   */
//  @Override
//  public String join(String separator)
//      throws JSONException
//  {
//    return join(separator,
//                WritableJSONObject.getJSONObjectBuilderSupplier(),
//                WritableJSONArray.getJSONArrayBuilderSupplier());
//    // int len = length();
//    // StringBuilder sb = new StringBuilder();
//    //
//    // for (int i = 0; i < len; i += 1) {
//    // if (i > 0) {
//    // sb.append(separator);
//    // }
//    // sb.append(WritableJSONObject.valueToString(this.myArrayList.get(i)));
//    // }
//    // return sb.toString();
//  }

  // /**
  // * Get the number of elements in the JSONArray, included nulls.
  // *
  // * @return The length (or size).
  // */
  // @Override
  // public int length()
  // {
  // return this.myArrayList.size();
  // }

  // /**
  // * Get the optional object value associated with an index.
  // *
  // * @param index
  // * The index must be between 0 and length() - 1.
  // * @return An object value, or null if there is no object at that index.
  // */
  // @Override
  // public Object opt(int index)
  // {
  // return (index < 0 || index >= length()) ? null : this.myArrayList.get(index);
  // }

  /**
   * Get the optional JSONArray associated with an index.
   * 
   * @param index
   *          subscript
   * @return A JSONArray value, or null if the index has no value, or if the value is not a
   *         JSONArray.
   */
  @Override
  public WritableJSONArray optJSONArray(int index)
  {
    return (WritableJSONArray) super.optJSONArray(index);
    // Object o = opt(index);
    // return o instanceof WritableJSONArray ? (WritableJSONArray) o : null;
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
  public WritableJSONObject optJSONObject(int index)
  {
    Object o = opt(index);
    return o instanceof WritableJSONObject ? (WritableJSONObject) o : null;
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
  // public WritableJSONArray put(Collection<?> value)
  // {
  // put(new WritableJSONArray(value));
  // return this;
  // }

  // /**
  // * Put a value in the JSONArray, where the value will be a JSONObject which is produced from a
  // * Map.
  // *
  // * @param value
  // * A Map value.
  // * @return this.
  // */
  // @Override
  // public WritableJSONArray put(Map<String, ?> value)
  // {
  // put(new WritableJSONObject(value));
  // return this;
  // }

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
  // public WritableJSONArray put(int index,
  // Collection<?> value)
  // throws JSONException
  // {
  // put(index, new WritableJSONArray(value));
  // return this;
  // }

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
  // public WritableJSONArray put(int index,
  // Map<String, ?> value)
  // throws JSONException
  // {
  // put(index, new WritableJSONObject(value));
  // return this;
  // }

  // /**
  // * Produce a JSONObject by combining a JSONArray of names with the values of this JSONArray.
  // *
  // * @param names
  // * A JSONArray containing a list of key strings. These will be paired with the values.
  // * @return A JSONObject, or null if there are no names or if this JSONArray has no values.
  // * @throws JSONException
  // * If any of the names are null.
  // */
  // @Override
  // public WritableJSONObject toJSONObject(JSONArray names)
  // throws JSONException
  // {
  // if (names == null || names.length() == 0 || length() == 0) {
  // return null;
  // }
  // WritableJSONObject jo = new WritableJSONObject();
  // for (int i = 0; i < names.length(); i += 1) {
  // jo.put(names.getString(i), this.opt(i));
  // }
  // return jo;
  // }

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
  public Writer write(Writer writer)
      throws JSONException
  {
    write(writer);
//    ,
//          WritableJSONObject.getJSONObjectBuilderSupplier(),
//          WritableJSONArray.getJSONArrayBuilderSupplier());
    return writer;
  }

  private static final Supplier<Builder> BUILDERSUPPLIER = new Supplier<Builder>() {
    @Override
    public Builder get()
    {
      return new Builder();
    }
  };

  public static Supplier<Builder> getJSONArrayBuilderSupplier()
  {
    return BUILDERSUPPLIER;
  }

  public static class Builder
    implements JSONArrayBuilder<WritableJSONArray>
  {
    private List<Object> __builder = Lists.newArrayList();

    @Override
    public Builder put(Object value) throws JSONException
    {
      __builder.add(WritableJSON.get().wrap(value));
      return this;
    }

    @Override
    public WritableJSONArray build()
    {
      return new WritableJSONArray(__builder);
    }

  }
}