package learn.nlp.nlplibrary.cache;

import learn.nlp.nlplibrary.common.StreamExportAble;
import learn.nlp.nlplibrary.common.StreamImportAble;

public interface Cache<T> extends StreamImportAble, StreamExportAble {
    public boolean isExited(T item); //exited in cached.
    public boolean insert(T item); // insert if not exited.
    public boolean reset();
}
