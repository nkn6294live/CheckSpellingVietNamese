package learn.nlp.nlplibrary.document;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import learn.nlp.nlplibrary.checker.Checker;
import learn.nlp.nlplibrary.delegate.Action;
import learn.nlp.nlplibrary.delegate.Action2;
import learn.nlp.nlplibrary.delegate.Function;
import learn.nlp.nlplibrary.io.InputStreamBlock;

public class DocumentReaderAction2 implements Action2<String, Writer> {

    public Action<Object> EndDocument;
    public Action2<Integer, Integer> EndParagraph;

    public DocumentReaderAction2(InputStream inputStream, Checker checker, TitleFactory titleFactory) {
        this.errors = new ArrayList<>();
        this.titleFactory = titleFactory == null ? new TitleFactory() : titleFactory;
        this.checker = checker;
        this.reader = new InputStreamBlock(inputStream);
    }

    public DocumentReaderAction2(Reader reader, Checker checker, TitleFactory titleFactory) {
        this.errors = new ArrayList<>();
        this.titleFactory = titleFactory == null ? new TitleFactory() : titleFactory;
        this.checker = checker;
        this.reader = new InputStreamBlock(reader);
    }

    public DocumentReaderAction2(DocumentReader reader, Checker checker, TitleFactory titleFactory) {
        this.errors = new ArrayList<>();
        this.titleFactory = titleFactory == null ? new TitleFactory() : titleFactory;
        this.checker = checker;
        this.reader = reader;
    }

    @Override
    public void action(String key, Writer writer) throws Exception {
        try {
            switch (key) {
                case TITLE_TEMPLATE_KEY:
                    exportTitle(writer);
                    break;
                case CONTENT_TEMPLATE_KEY:
                    exportNextChapter(writer, isChecking);
                    break;
            }
        } catch (EndReaderException ex) {
            this.onEndReader();
            throw new Exception();
        } catch (Exception ex) {
            throw new Exception();
        } finally {
            try {
                writer.flush();
            } catch (IOException ex1) {
            }
        }
    }

    public void exportNextChapter(Writer writer, boolean isCheckingSpelling) throws Exception {
        this.currentParagraphIndex = 0;
        while (++currentParagraphIndex <= this.maxParagraphInChapter) {
            exportNextParagraph(writer, isCheckingSpelling, currentParagraphIndex);
            onEndParagraph(currentParagraphIndex, maxParagraphInChapter);
        }
    }

    public void exportNextParagraph(Writer writer, boolean isCheckingSpelling, int paragraph_index) throws Exception {
        String paragraph;
        if (isCheckingSpelling) {
            paragraph = reader.nextParagraph(SpellingConvert);
        } else {
            paragraph = reader.nextParagraph();
        }
        if (paragraph == null) { // end document

            throw new EndReaderException("Document End");
        }
        writer.write(String.format(PARAGRAPH_OUTPUT_STRING_FORMAT, paragraph_index, paragraph));
        writer.write(BREAK_PARAGRAPH);
    }

    public void exportTitle(Writer writer) throws Exception {
        writer.write(String.format(TITLE_OUTPUT_STRING_FORMAT,
                titleFactory.getNextTitle(reader.nextTitle())));
    }

    public List<String> getErrorsSpelling() {
        return this.errors;
    }

    public DocumentReader getReader() {
        return reader;
    }

    public Checker getChecker() {
        return checker;
    }

    public TitleFactory getTitleFactory() {
        return titleFactory;
    }

    public void onEndReader() {
        if (this.EndDocument != null) {
            this.EndDocument.action();
        }
    }
    
    public void onEndParagraph(int current_paragraph, int max_paragraph_in_chapter) {
    	if(this.EndParagraph != null) {
    		try {
				this.EndParagraph.action(current_paragraph, max_paragraph_in_chapter);
			} catch (Exception e) {
			}
    	}
    }

    public void setSpellChecking(boolean isSpellChecking) {
        this.isChecking = isSpellChecking;
    }

    public boolean isSpellChecking() {
        return this.isChecking;
    }

    private final DocumentReader reader;
    private final Checker checker;
    private final List<String> errors;
    private final TitleFactory titleFactory;
    private int currentParagraphIndex = 0;
    private boolean isChecking = true;

    private final Function<String, String> SpellingConvert = new Function<String, String>() {

        @Override
        public String action(String... values) {
            if (values.length == 0) {
                return null;
            }
            String value = values[0];
            if (checker.isInvalid(value.toLowerCase())) {
                return value;
            } else if (!errors.contains(value)) {
                errors.add(value);
            }
            return String.format(ERROR_OUTPUT_STRING_FORMAT, value);
        }
    };

//    private final static String TITLE_OUTPUT_STRING_FORMAT = "<a name='0'></a><h1 id='title'>%1$s</h1>";
    private final static String TITLE_OUTPUT_STRING_FORMAT = "<h1 id='title'>%1$s</h1>";
    private final static String ERROR_OUTPUT_STRING_FORMAT = "<span class='error'>%1$s</span>";
//    private final static String PARAGRAPH_OUTPUT_STRING_FORMAT = "<a name='%1$s'/><p class='paragraph' id='paragraph_%1$s'>%2$s</p>";
    private final static String PARAGRAPH_OUTPUT_STRING_FORMAT = "<p class='paragraph' id='paragraph_%1$s'>%2$s</p>";
    private final static String BREAK_PARAGRAPH = "\n";
    private final static String TITLE_TEMPLATE_KEY = "TITLE";
    private final static String CONTENT_TEMPLATE_KEY = "CONTENT";

    public int getMaxParagraphInChapter() {
        return maxParagraphInChapter;
    }

    public void setMaxParagraphInChapter(int maxParagraphInChapter) {
        this.maxParagraphInChapter = maxParagraphInChapter;
    }

    private int maxParagraphInChapter = 1000;

    public int getCurrentParagraphIndex() {
        return currentParagraphIndex;
    }
}
