package org.json;

public class JSONRuntimeException
  extends RuntimeException
{
  private static final long serialVersionUID = 6175761779024608329L;

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
