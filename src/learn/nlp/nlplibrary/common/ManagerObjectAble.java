package learn.nlp.nlplibrary.common;

import java.util.Iterator;

public interface ManagerObjectAble<T> extends Iterator<T>{
    public boolean add(T item);
}
