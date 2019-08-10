package org.json;

import java.util.Arrays;

/**
 * simplified copy of StringBuilder that only supports appending characters and which also supports
 * efficient resuse. You should invoke {@link #open()} before you start using it and
 * {@link #close()} when you are finished with it. You cannot nest usage of a single
 * StringCharBuilder and attempting to call {@link #open()} before it is {@link #close()}d will
 * result in an {@link IllegalStateException}.
 */
public class StringCharBuilder
  implements AutoCloseable
{
  public static final int UNCLAIMED = -1;

  char[] value;
  int count = UNCLAIMED;

  StringCharBuilder(int capacity)
  {
    value = new char[capacity];
  }

  public int length()
  {
    return count;
  }

  public int capacity()
  {
    return value.length;
  }

  /**
   * This method has the same contract as ensureCapacity, but is never synchronized.
   */
  private void ensureCapacityInternal(int minimumCapacity)
  {
    if (minimumCapacity - value.length > 0)
      expandCapacity(minimumCapacity);
  }

  /**
   * This implements the expansion semantics of ensureCapacity with no size check or
   * synchronization.
   */
  private void expandCapacity(int minimumCapacity)
  {
    int newCapacity = value.length * 2 + 2;
    if (newCapacity - minimumCapacity < 0)
      newCapacity = minimumCapacity;
    if (newCapacity < 0) {
      if (minimumCapacity < 0) // overflow
        throw new OutOfMemoryError();
      newCapacity = Integer.MAX_VALUE;
    }
    value = Arrays.copyOf(value, newCapacity);
  }

  public StringCharBuilder open()
  {
    if (count != UNCLAIMED) {
      throw new IllegalStateException("Cannot claim a StringCharBuilder that is in use: "
                                      + toStringInternal());
    }
    count = 0;
    return this;
  }

  @Override
  public void close()
  {
    if (count == UNCLAIMED) {
      throw new IllegalStateException("Cannot release a StringCharBuilder that is unclaimed");
    }
    count = UNCLAIMED;
  }

  private void assertIsClaimed()
  {
    if (count == UNCLAIMED) {
      throw new IllegalStateException("You must claim a StringCharBuilder before using it");
    }
  }

  public char charAt(int index)
  {
    assertIsClaimed();
    if ((index < 0) || (index >= count))
      throw new StringIndexOutOfBoundsException(index);
    return value[index];
  }

  public void append(char c)
  {
    assertIsClaimed();
    ensureCapacityInternal(count + 1);
    value[count++] = c;
  }

  public String substring(int start,
                          int end)
  {
    assertIsClaimed();
    if (start < 0)
      throw new StringIndexOutOfBoundsException(start);
    if (end > count)
      throw new StringIndexOutOfBoundsException(end);
    if (start > end)
      throw new StringIndexOutOfBoundsException(end - start);
    return new String(value, start, end - start);
  }

  @Override
  public String toString()
  {
    return count == UNCLAIMED ? "UNCLAIMED" : toStringInternal();
  }

  private String toStringInternal()
  {
    return new String(value, 0, count);
  }

}
