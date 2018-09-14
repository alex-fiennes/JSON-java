package org.json;

import java.io.Writer;

public interface JSONComponent
{
  /**
   * Make a JSON text of this JSONObject. For compactness, no whitespace is added. If this would not
   * result in a syntactically correct JSON text, then null will be returned instead.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return a printable, displayable, portable, transmittable representation of the object,
   *         beginning with <code>{</code>&nbsp;<small>(left brace)</small> and ending with
   *         <code>}</code>&nbsp;<small>(right brace)</small>.
   */
  @Override
  public String toString();

  /**
   * Make a prettyprinted JSON text of this JSONObject.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @return a printable, displayable, portable, transmittable representation of the object,
   *         beginning with <code>{</code>&nbsp;<small>(left brace)</small> and ending with
   *         <code>}</code>&nbsp;<small>(right brace)</small>.
   */
  public String toString(int indentFactor);

  /**
   * Make a prettyprinted JSON text of this JSONObject.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @param indentFactor
   *          The number of spaces to add to each level of indentation.
   * @param indent
   *          The indentation of the top level.
   * @return a printable, displayable, transmittable representation of the object, beginning with
   *         <code>{</code>&nbsp;<small>(left brace)</small> and ending with <code>}</code>
   *         &nbsp;<small>(right brace)</small>.
   * @throws RuntimeException
   *           If the object contains an invalid number.
   */
  public String toString(int indentFactor,
                         int indent);

  /**
   * Write the contents of the JSONObject as JSON text to a writer. For compactness, no whitespace
   * is added.
   * <p>
   * Warning: This method assumes that the data structure is acyclical.
   * 
   * @return The writer.
   * @throws JSONException
   */
  public Writer write(Writer writer)
      throws JSONException;

}
