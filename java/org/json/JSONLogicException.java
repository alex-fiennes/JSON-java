package org.json;

public class JSONLogicException
  extends JSONRuntimeException
{

  public JSONLogicException()
  {
    super();
  }

  public JSONLogicException(String message,
                            Throwable cause)
  {
    super(message,
          cause);
  }

  public JSONLogicException(String message)
  {
    super(message);
  }

  public JSONLogicException(Throwable cause)
  {
    super(cause);
  }

}
