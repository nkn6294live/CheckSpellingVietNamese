package learn.nlp.nlplibrary.common;

import java.io.InputStream;

public interface StreamImportAble {
    public boolean ImportData(InputStream inputStream);
    public boolean ImportData(String filePath);
}
