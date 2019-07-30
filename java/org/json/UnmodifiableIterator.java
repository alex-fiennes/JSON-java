package org.json;

import java.util.Iterator;

@Deprecated
public class UnmodifiableIterator<T>
  implements Iterator<T>
{
  private final Iterator<T> __i;
  
  public UnmodifiableIterator(Iterator<T> i)
  {
    if (i == null) {
      throw new NullPointerException();
    }
    __i = i;
  }

  @Override
  public boolean hasNext()
  {
    return __i.hasNext();
  }

  @Override
  public T next()
  {
    return __i.next();
  }

  @Override
  public void remove()
  {
    throw new UnsupportedOperationException();
  }
}
