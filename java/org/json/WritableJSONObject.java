package org.json;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

/**
 * A JSONObject is an unordered collection of name/value pairs. Its external form is a string
 * wrapped in curly braces with colons between the names and values, and commas between the values
 * and names. The internal form is an object having <code>get</code> and <code>opt</code> methods
 * for accessing the values by name, and <code>put</code> methods for adding or replacing values by
 * name. The values can be any of these types: <code>Boolean</code>, <code>JSONArray</code>,
 * <code>JSONObject</code>, <code>Number</code>, <code>String</code>, or the
 * <code>JSONObject.NULL</code> object. A JSONObject constructor can be used to convert an external
 * form JSON text into an internal form whose values can be retrieved with the <code>get</code> and
 * <code>opt</code> methods, or to convert values into a JSON text using the <code>put</code> and
 * <code>toString</code> methods. A <code>get</code> method returns a value if one can be found, and
 * throws an exception if one cannot be found. An <code>opt</code> method returns a default value
 * instead of throwing an exception, and so is useful for obtaining optional values.
 * <p>
 * The generic <code>get()</code> and <code>opt()</code> methods return an object, which you can
 * cast or query for type. There are also typed <code>get</code> and <code>opt</code> methods that
 * do type checking and type coercion for you. The opt methods differ from the get methods in that
 * they do not throw. Instead, they return a specified value, such as null.
 * <p>
 * The <code>put</code> methods add or replace values in an object. For example,
 * 
 * <pre>
 * myString = new JSONObject().put(&quot;JSON&quot;, &quot;Hello, World!&quot;).toString();
 * </pre>
 * 
 * produces the string <code>{"JSON": "Hello, World"}</code>.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to the JSON syntax
 * rules. The constructors are more forgiving in the texts they will accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just before the closing brace.
 * </li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote or single quote,
 * and if they do not contain leading or trailing spaces, and if they do not contain any of these
 * characters: <code>{ } [ ] / \ : , = ; #</code> and if they do not look like numbers and if they
 * are not the reserved words <code>true</code>, <code>false</code>, or <code>null</code>.</li>
 * <li>Keys can be followed by <code>=</code> or <code>=></code> as well as by <code>:</code>.</li>
 * <li>Values can be followed by <code>;</code> <small>(semicolon)</small> as well as by
 * <code>,</code> <small>(comma)</small>.</li>
 * <li>Numbers may have the <code>0x-</code> <small>(hex)</small> prefix.</li>
 * </ul>
 * 
 * @author JSON.org
 * @version 2010-12-28
 */
public class WritableJSONObject
  extends MapBasedJSONObject
  implements Cloneable
{
  /**
   * @see WritableJSON#toJSONObject(String)
   */
  public static WritableJSONObject create(String source)
      throws JSONException
  {
    return WritableJSON.get().toJSONObject(source);
  }

  /**
   * The map where the JSONObject's properties are kept.
   */
  private final Map<String, Object> __map;

  private final static Supplier<Builder> BUILDERSUPPLIER = new Supplier<Builder>() {
    @Override
    public Builder get()
    {
      return new Builder();
    }
  };

  public static Supplier<Builder> getJSONObjectBuilderSupplier()
  {
    return BUILDERSUPPLIER;
  }

  @Override
  protected Map<String, Object> getMap()
  {
    return __map;
  }

  /**
   * Construct an empty JSONObject.
   */
  public WritableJSONObject()
  {
    __map = Maps.newHashMap();
  }

  // @Override
  // public WritableJSONObject writableClone()
  // {
  // return this.clone();
  // }

  @Override
  public WritableJSONObject clone()
  {
    WritableJSONObject clone = new WritableJSONObject();
    try {
      for (Map.Entry<String, Object> entry : __map.entrySet()) {
        Object value = entry.getValue();
        if (value instanceof Cloneable) {
          clone.put(entry.getKey(), value.getClass().getMethod("clone").invoke(value));
        } else {
          clone.put(entry.getKey(), value);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return clone;
  }

  @Override
  public boolean equalsMap(Map<String, Object> map)
  {
    return this.__map.equals(map);
  }

  // /**
  // * Construct a JSONObject from a subset of another JSONObject. An array of strings is used to
  // * identify the keys that should be copied. Missing keys are ignored.
  // *
  // * @param jo
  // * A JSONObject.
  // * @param names
  // * An array of strings.
  // */
  // public WritableJSONObject(JSONObject jo,
  // String[] names)
  // {
  // this();
  // for (int i = 0; i < names.length; i += 1) {
  // try {
  // putOnce(names[i], jo.opt(names[i]));
  // } catch (Exception ignore) {
  // }
  // }
  // }

  // /**
  // * Construct a JSONObject from a JSONTokener.
  // *
  // * @param x
  // * A JSONTokener object containing the source string.
  // * @throws JSONException
  // * If there is a syntax error in the source string or a duplicated key.
  // */
  // public WritableJSONObject(JSONTokener x) throws JSONException
  // {
  // this();
  // JSONParser.populateObjectBuilder(x, WritableJSONBuilder.getInstance());
  // }

  private <K, V> WritableJSONObject(Map<String, Object> map)
  {
    super();
    __map = map;
  }

  // /**
  // * Construct a JSONObject from a Map.
  // *
  // * @param map
  // * A map object that can be used to initialize the contents of the JSONObject.
  // */
  // public <K, V> WritableJSONObject(Map<K, V> map)
  // {
  // this.__map = new HashMap<String, Object>();
  // if (map != null) {
  // Iterator<Entry<K, V>> i = map.entrySet().iterator();
  // while (i.hasNext()) {
  // Entry<K, V> e = i.next();
  // V value = e.getValue();
  // if (value != null) {
  // this.__map.put(e.getKey().toString(), wrap(value));
  // }
  // }
  // }
  // }

  // /**
  // * Construct a JSONObject from an Object using bean getters. It reflects on all of the public
  // * methods of the object. For each of the methods with no parameters and a name starting with
  // * <code>"get"</code> or <code>"is"</code> followed by an uppercase letter, the method is
  // invoked,
  // * and a key and the value returned from the getter method are put into the new JSONObject. The
  // * key is formed by removing the <code>"get"</code> or <code>"is"</code> prefix. If the second
  // * remaining character is not upper case, then the first character is converted to lower case.
  // For
  // * example, if an object has a method named <code>"getName"</code>, and if the result of calling
  // * <code>object.getName()</code> is <code>"Larry Fine"</code>, then the JSONObject will contain
  // * <code>"name": "Larry Fine"</code>.
  // *
  // * @param bean
  // * An object that has getter methods that should be used to make a JSONObject.
  // */
  // public WritableJSONObject(Object bean)
  // {
  // this();
  // populateMap(bean);
  // }

  // /**
  // * Construct a JSONObject from an Object, using reflection to find the public members. The
  // * resulting JSONObject's keys will be the strings from the names array, and the values will be
  // * the field values associated with those keys in the object. If a key is not found or not
  // * visible, then it will not be copied into the new JSONObject.
  // *
  // * @param object
  // * An object that has fields that should be used to make a JSONObject.
  // * @param names
  // * An array of strings, the names of the fields to be obtained from the object.
  // */
  // public WritableJSONObject(Object object,
  // String names[])
  // {
  // this();
  // Class<?> c = object.getClass();
  // for (int i = 0; i < names.length; i += 1) {
  // String name = names[i];
  // try {
  // putOpt(name, c.getField(name).get(object));
  // } catch (Exception ignore) {
  // }
  // }
  // }

  // /**
  // * Construct a JSONObject from a source JSON text string. This is the most commonly used
  // * JSONObject constructor.
  // *
  // * @param source
  // * A string beginning with <code>{</code>&nbsp;<small>(left brace)</small> and ending
  // * with <code>}</code>&nbsp;<small>(right brace)</small>.
  // * @exception JSONException
  // * If there is a syntax error in the source string or a duplicated key.
  // */
  // public WritableJSONObject(String source) throws JSONException
  // {
  // this(new JSONTokener(new StringReader(source), WritableJSONBuilder.getInstance()));
  // }

  /**
   * Construct a JSONObject from a ResourceBundle.
   * 
   * @param baseName
   *          The ResourceBundle base name.
   * @param locale
   *          The Locale to load the ResourceBundle for.
   * @throws JSONException
   *           If any JSONExceptions are detected.
   */
  public WritableJSONObject(String baseName,
                            Locale locale)
      throws JSONException
  {
    this();
    ResourceBundle r =
        ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());

    // Iterate through the keys in the bundle.

    Enumeration<?> keys = r.getKeys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      if (key instanceof String) {

        // Go through the path, ensuring that there is a nested JSONObject for
        // each
        // segment except the last. Add the value using the last segment's name
        // into
        // the deepest nested JSONObject.

        String[] path = ((String) key).split("\\.");
        int last = path.length - 1;
        WritableJSONObject target = this;
        for (int i = 0; i < last; i += 1) {
          String segment = path[i];
          WritableJSONObject nextTarget = target.optJSONObject(segment);
          if (nextTarget == null) {
            nextTarget = new WritableJSONObject();
            target.put(segment, nextTarget);
          }
          target = nextTarget;
        }
        target.put(path[last], r.getString((String) key));
      }
    }
  }

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
  @Override
  public WritableJSONObject accumulate(String key,
                                       Object value)
      throws JSONException
  {
    JSONComponents.testValidity(value);
    Object object = opt(key);
    if (object == null) {
      put(key, value instanceof WritableJSONArray ? new WritableJSONArray().put(value) : value);
    } else if (object instanceof WritableJSONArray) {
      ((WritableJSONArray) object).put(value);
    } else {
      put(key, new WritableJSONArray().put(object).put(value));
    }
    return this;
  }

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
  @Override
  public WritableJSONObject append(String key,
                                   Object value)
      throws JSONException
  {
    JSONComponents.testValidity(value);
    Object object = opt(key);
    if (object == null) {
      put(key, new WritableJSONArray().put(value));
    } else if (object instanceof WritableJSONArray) {
      put(key, ((WritableJSONArray) object).put(value));
    } else {
      throw new JSONException("JSONObject[" + key + "] is not a JSONArray.");
    }
    return this;
  }

  @Override
  public WritableJSONArray getJSONArray(String key)
      throws JSONException
  {
    return (WritableJSONArray) super.getJSONArray(key);
  }

  /**
   * Get the JSONObject value associated with a key.
   * 
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   * @throws JSONException
   *           if the key is not found or if the value is not a JSONObject.
   */
  @Override
  public WritableJSONObject getJSONObject(String key)
      throws JSONException
  {
    return (WritableJSONObject) super.getJSONObject(key);
  }

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
  @Override
  public WritableJSONObject increment(String key)
      throws JSONException
  {
    Object value = opt(key);
    if (value == null) {
      put(key, 1);
    } else if (value instanceof Integer) {
      put(key, ((Integer) value).intValue() + 1);
    } else if (value instanceof Long) {
      put(key, ((Long) value).longValue() + 1);
    } else if (value instanceof Double) {
      put(key, ((Double) value).doubleValue() + 1);
    } else if (value instanceof Float) {
      put(key, ((Float) value).floatValue() + 1);
    } else {
      throw new JSONException("Unable to increment [" + JSONComponents.quote(key) + "].");
    }
    return this;
  }

  /**
   * Get an optional JSONArray associated with a key. It returns null if there is no such key, or if
   * its value is not a JSONArray.
   * 
   * @param key
   *          A key string.
   * @return A JSONArray which is the value.
   */
  @Override
  public WritableJSONArray optJSONArray(String key)
  {
    return (WritableJSONArray) super.optJSONArray(key);
  }

  /**
   * Get an optional JSONObject associated with a key. It returns null if there is no such key, or
   * if its value is not a JSONObject.
   * 
   * @param key
   *          A key string.
   * @return A JSONObject which is the value.
   */
  @Override
  public WritableJSONObject optJSONObject(String key)
  {
    return (WritableJSONObject) super.optJSONObject(key);
  }

  // private void populateMap(Object bean)
  // {
  // Class<?> klass = bean.getClass();
  //
  // // If klass is a System class then set includeSuperClass to false.
  //
  // boolean includeSuperClass = klass.getClassLoader() != null;
  //
  // Method[] methods = (includeSuperClass) ? klass.getMethods() : klass.getDeclaredMethods();
  // for (int i = 0; i < methods.length; i += 1) {
  // try {
  // Method method = methods[i];
  // if (Modifier.isPublic(method.getModifiers())) {
  // String name = method.getName();
  // String key = "";
  // if (name.startsWith("get")) {
  // if (name.equals("getClass") || name.equals("getDeclaringClass")) {
  // key = "";
  // } else {
  // key = name.substring(3);
  // }
  // } else if (name.startsWith("is")) {
  // key = name.substring(2);
  // }
  // if (key.length() > 0 && Character.isUpperCase(key.charAt(0))
  // && method.getParameterTypes().length == 0) {
  // if (key.length() == 1) {
  // key = key.toLowerCase();
  // } else if (!Character.isUpperCase(key.charAt(1))) {
  // key = key.substring(0, 1).toLowerCase() + key.substring(1);
  // }
  //
  // Object result = method.invoke(bean, (Object[]) null);
  // if (result != null) {
  // __map.put(key, JSONObjects.wrap(result));
  // }
  // }
  // }
  // } catch (Exception ignore) {
  // }
  // }
  // }

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
  @Override
  public WritableJSONObject put(String key,
                                boolean value)
      throws JSONException
  {
    put(key, value ? Boolean.TRUE : Boolean.FALSE);
    return this;
  }

  // /**
  // * Put a key/value pair in the JSONObject, where the value will be a JSONArray which is produced
  // * from a Collection.
  // *
  // * @param key
  // * A key string.
  // * @param value
  // * A Collection value.
  // * @return this.
  // * @throws JSONException
  // */
  // @Override
  // public WritableJSONObject put(String key,
  // Collection<?> value)
  // throws JSONException
  // {
  // put(key, new WritableJSONArray(value));
  // return this;
  // }

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
  @Override
  public WritableJSONObject put(String key,
                                double value)
      throws JSONException
  {
    put(key, Double.valueOf(value));
    return this;
  }

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
  @Override
  public WritableJSONObject put(String key,
                                int value)
      throws JSONException
  {
    put(key, Integer.valueOf(value));
    return this;
  }

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
  @Override
  public WritableJSONObject put(String key,
                                long value)
      throws JSONException
  {
    put(key, Long.valueOf(value));
    return this;
  }

  // /**
  // * Put a key/value pair in the JSONObject, where the value will be a JSONObject which is
  // produced
  // * from a Map.
  // *
  // * @param key
  // * A key string.
  // * @param value
  // * A Map value.
  // * @return this.
  // * @throws JSONException
  // */
  // @Override
  // public WritableJSONObject put(String key,
  // Map<String, ?> value)
  // throws JSONException
  // {
  // put(key, new WritableJSONObject(value));
  // return this;
  // }

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
  @Override
  public WritableJSONObject put(String key,
                                Object value)
      throws JSONException
  {
    if (key == null) {
      throw new JSONException("Null key.");
    }
    if (value != null) {
      JSONComponents.testValidity(value);
      this.__map.put(key, value);
    } else {
      remove(key);
    }
    return this;
  }

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
  @Override
  public WritableJSONObject putOnce(String key,
                                    Object value)
      throws JSONException
  {
    if (key != null && value != null) {
      if (opt(key) != null) {
        throw new JSONException("Duplicate key \"" + key + "\"");
      }
      put(key, value);
    }
    return this;
  }

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
  @Override
  public WritableJSONObject putOpt(String key,
                                   Object value)
      throws JSONException
  {
    if (key != null && value != null) {
      put(key, value);
    }
    return this;
  }

  /**
   * Remove a name and its value, if present.
   * 
   * @param key
   *          The name to be removed.
   * @return The value that was associated with the name, or null if there was no value.
   */
  @Override
  public Object remove(String key)
  {
    return this.__map.remove(key);
  }

  public static class Builder
    implements JSONObjectBuilder<WritableJSONObject>
  {
    private final Map<String, Object> __builder;

    protected Builder()
    {
      __builder = Maps.newHashMap();
    }

    @Override
    public WritableJSONObject build()
    {
      return new WritableJSONObject(__builder);
    }

    @Override
    public Builder putOnce(String key,
                           Object value)
        throws JSONException
    {
      __builder.put(key, WritableJSON.get().wrap(value));
      return this;
    }
  }

  @Override
  public Appendable write(Appendable writer)
      throws IOException
  {
    return JSONObjects.write(this,
                             // WritableJSONObject.getJSONObjectBuilderSupplier(),
                             // WritableJSONArray.getJSONArrayBuilderSupplier(),
                             writer);
  }

}