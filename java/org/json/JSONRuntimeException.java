package org.json;

public class JSONRuntimeException
  extends RuntimeException
{
  public JSONRuntimeException()
  {
    super();
  }

  public JSONRuntimeException(String message,
                              Throwable cause)
  {
    super(message,
          cause);
  }

  public JSONRuntimeException(String message)
  {
    super(message);
  }

  public JSONRuntimeException(Throwable cause)
  {
    super(cause);
  }
}
