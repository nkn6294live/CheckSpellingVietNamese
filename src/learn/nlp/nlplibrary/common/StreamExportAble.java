package learn.nlp.nlplibrary.common;

import java.io.OutputStream;

public interface StreamExportAble {
    public boolean exportData(OutputStream stream);
    public boolean exportData(String fileName); //folderName
}
