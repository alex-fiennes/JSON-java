package org.json;

import com.google.common.base.Preconditions;

public class JSONTokenerString<A extends JSONArray, O extends JSONObject>
  implements JSONTokener<A, O>
{
  private final JSONBuilder<A, O> __jsonBuilder;
  private final String __source;
  private final int __sourceLength;
  private int _index;
  private StringCharBuilder _sharedBuf = null;

  public JSONTokenerString(String source,
                           JSONBuilder<A, O> jsonBuilder)
  {
    __source = Preconditions.checkNotNull(source);
    __sourceLength = __source.length();
    __jsonBuilder = Preconditions.checkNotNull(jsonBuilder);
    _index = 0;
  }

  private StringCharBuilder getSharedBuf()
  {
    if (_sharedBuf == null) {
      _sharedBuf = new StringCharBuilder(8);
    }
    return _sharedBuf;
  }

  @Override
  public void back()
      throws JSONException
  {
    _index--;
  }

  @Override
  public char nextClean()
      throws JSONException
  {
    for (;;) {
      char c = next();
      if (c > ' ' | c == 0) {
        return c;
      }
    }
  }

  @Override
  public char next()
      throws JSONException
  {
    return _index == __sourceLength ? 0 : __source.charAt(_index++);
  }

  @Override
  public String nextKey()
      throws JSONException
  {
    char c = nextClean();
    switch (c) {
      case '"':
      case '\'':
        return nextString(c);
    }
    return getUnquotedText(c);
  }

  @Override
  public Object nextValue()
      throws JSONException
  {
    char c = nextClean();
    switch (c) {
      case '"':
      case '\'':
        return nextString(c);
      case '{':
        back();
        return __jsonBuilder.toJSONObject(this);
      case '[':
        back();
        return __jsonBuilder.toJSONArray(this);
    }
    return JSONComponents.stringToValue(getUnquotedText(c));
  }

  @Override
  public JSONException syntaxError(String message)
  {
    return new JSONException(message + ":" + _index);
  }

  private String getUnquotedText(char c)
      throws JSONException
  {
    while (c <= ' ') {
      c = next();
    }

    int start = _index - 1;
    appendUnquotedText(c);
    back();

    final int length = _index - start;
    if (length == 0) {
      throw syntaxError("Missing value");
    }
    int last;
    for (last = length; last > 0; last--) {
      if (__source.charAt(_index - last - 1) > 32) {
        break;
      }
    }
    return __source.substring(start, start + last);
  }

  public String nextString(char quote)
      throws JSONException
  {
    int start = _index;
    char c;
    for (;;) {
      c = next();
      switch (c) {
        case 0:
        case '\n':
        case '\r':
          throw syntaxError("Unterminated string");
        case '\\':
          _index = start;
          return nextString(quote, getSharedBuf());
        default:
          if (c == quote) {
            return __source.substring(start, _index - 1);
          }
      }
    }
  }

  public String nextString(char quote,
                           StringCharBuilder sharedBuf)
      throws JSONException
  {
    char c;
    try (StringCharBuilder buf = sharedBuf.open()) {
      for (;;) {
        c = next();
        switch (c) {
          case 0:
          case '\n':
          case '\r':
            throw syntaxError("Unterminated string");
          case '\\':
            c = next();
            switch (c) {
              case 'b':
                buf.append('\b');
                break;
              case 't':
                buf.append('\t');
                break;
              case 'n':
                buf.append('\n');
                break;
              case 'f':
                buf.append('\f');
                break;
              case 'r':
                buf.append('\r');
                break;
              case 'u':
                buf.append((char) Integer.parseInt(new String(__source.substring(_index,
                                                                                 _index + 4)),
                                                   16));
                _index += 4;
                break;
              case '"':
              case '\'':
              case '\\':
              case '/':
                buf.append(c);
                break;
              default:
                throw syntaxError("Illegal escape.");
            }
            break;
          default:
            if (c == quote) {
              return buf.toString();
            }
            buf.append(c);
        }
      }
    }
  }

  private void appendUnquotedText(char c)
      throws JSONException
  {
    while (c >= ' ') {
      switch (c) {
        case ',':
        case ':':
        case ']':
        case '}':
        case '/':
        case '\\':
        case '"':
        case '[':
        case '{':
        case ';':
        case '=':
        case '#':
          return;
        default:
          c = next();
      }
    }
  }

}
