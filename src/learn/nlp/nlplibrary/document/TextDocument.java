package learn.nlp.nlplibrary.document;

import java.io.File;
import learn.nlp.nlplibrary.delegate.Action;
import learn.nlp.nlplibrary.delegate.Action2;
import learn.nlp.nlplibrary.io.CopyWithTemplate;

public class TextDocument {

    public static void exportTo(String folder, String templateFile, DocumentReaderAction2 reader, boolean isChecking) {
        new TextDocument(reader).exportTo(folder, templateFile, isChecking);
    }
    public Action2<Integer, String> ActionLoadChapter; //index_chapter, output_file
    public Action<Integer> EndDocumentLoad; //int -> numberChapter index [start = 0]

    public TextDocument(DocumentReaderAction2 action2) {
        this.action2 = action2;
        this.action2.EndDocument = new Action<Object>() {
            @Override
            public void action(Object... values) {
                numberChapter = numberChapterLoaded;
                isLoading = false;
                OnEndDocument(numberChapter);
            }
        };
    }

    public void exportTo(String folder, String templateFile, boolean isChecking) {
        try {
        	this.action2.setSpellChecking(isChecking);
            this.isLoading = true;
            File file = new File(folder);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (File f : file.listFiles()) {
                f.delete();
            }
            int index = 0;
            while (true) {
                String outputFile = folder + "/" + String.format(OUTPUT_FILE_STRING_FORMAT, ++index);
                if (!CopyWithTemplate.exportWithTemplate(outputFile, templateFile, action2)) {
                    break;
                }
                this.numberChapterLoaded = index;
                OnChapterLoadDone(index, outputFile);
            }
        } catch (Exception ex) {
        }
    }

    public void OnChapterLoadDone(int chapter_index, String outputFile) throws Exception {
        if (this.ActionLoadChapter != null) {
            this.ActionLoadChapter.action(chapter_index, outputFile);
        }
    }
    public void OnEndDocument(int number_chapter) {
        if(this.EndDocumentLoad != null) {
            this.EndDocumentLoad.action(number_chapter);
        }
    }
    public int getNumberChapterLoaded() {
        return this.numberChapterLoaded;
    }

    public int getNumberChapter(){
        if (this.isLoading) {
            return -1;
        }
        return this.numberChapter;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    private final DocumentReaderAction2 action2;
    private int numberChapter = -1;
    private int numberChapterLoaded = 0;
    private boolean isLoading = false;
    private final static String OUTPUT_FILE_STRING_FORMAT = "chapter_%1$s.html";
}
