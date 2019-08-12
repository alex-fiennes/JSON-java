package org.json;

public class JSONParser
{
  public static <A extends JSONArray, O extends JSONObject> void populateObjectBuilder(JSONTokener<A, O> tokener,
                                                                                       JSONObjectBuilder<O> builder)
      throws JSONException
  {
    char c;
    String key;

    if (tokener.nextClean() != '{') {
      throw tokener.syntaxError("A JSONObject text must begin with '{'");
    }
    for (;;) {
      c = tokener.nextClean();
      switch (c) {
        case 0:
          throw tokener.syntaxError("A JSONObject text must end with '}'");
        case '}':
          return;
        default:
          tokener.back();
          key = tokener.nextKey();
      }

      // The key is followed by ':'. We will also tolerate '=' or '=>'.

      c = tokener.nextClean();
      if (c == '=') {
        if (tokener.next() != '>') {
          tokener.back();
        }
      } else if (c != ':') {
        throw tokener.syntaxError("Expected a ':' after a key");
      }
      builder.putOnce(key, tokener.nextValue());

      // Pairs are separated by ','. We will also tolerate ';'.

      switch (tokener.nextClean()) {
        case ';':
        case ',':
          if (tokener.nextClean() == '}') {
            return;
          }
          tokener.back();
          break;
        case '}':
          return;
        default:
          throw tokener.syntaxError("Expected a ',' or '}'");
      }
    }
  }

  public static <A extends JSONArray, O extends JSONObject> void populateArrayBuilder(JSONTokener<A, O> tokener,
                                                                                      JSONArrayBuilder<A> builder)
      throws JSONException
  {
    if (tokener.nextClean() != '[') {
      throw tokener.syntaxError("A JSONArray text must start with '['");
    }

    if (tokener.nextClean() != ']') {
      tokener.back();
      for (;;) {

        if (tokener.nextClean() == ',') {
          tokener.back();
          builder.put(Null.getInstance());
        } else {
          tokener.back();
          builder.put(tokener.nextValue());
        }

        switch (tokener.nextClean()) {
          case ';':
          case ',':
            if (tokener.nextClean() == ']') {
              return;
            }
            tokener.back();
            break;
          case ']':
            return;
          default:
            throw tokener.syntaxError("Expected a ',' or ']'");
        }

      }
    }
  }
}
