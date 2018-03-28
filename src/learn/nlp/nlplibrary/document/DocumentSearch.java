package learn.nlp.nlplibrary.document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import learn.nlp.nlplibrary.delegate.Action3;
import learn.nlp.nlplibrary.delegate.Function;
import learn.nlp.nlplibrary.delegate.Predicate;
import learn.nlp.nlplibrary.io.InputStreamBlock;
import learn.nlp.nlplibrary.search.DocumentSearchResult;
import learn.nlp.nlplibrary.search.SearchControl;
import learn.nlp.nlplibrary.search.SearchControl.SearchAction;

public class DocumentSearch {

    public static List<DocumentSearchResult> searchDocument(String filePath, String key) throws Exception {
        return new DocumentSearch(filePath).search(key);
    }

    public DocumentSearch(String filePath) {
        this.filePath = filePath;
    }

    public List<DocumentSearchResult> search(String key) throws Exception {
        final String[] keys = splitString(key);
        int chapterID = 0;
        final List<DocumentSearchResult> results = new ArrayList<>();
        final DocumentReader reader = new InputStreamBlock(new FileInputStream(this.filePath));
        try {
            while (true) {
                searchNextChapter(keys, reader, ++chapterID, results);
            }
        } catch (Exception ex) {
        }
        return results;
    }
    
    public void search(String key, final List<DocumentSearchResult> results) throws Exception {
        final String[] keys = splitString(key);
        int chapterID = 0;
        final DocumentReader reader = new InputStreamBlock(new FileInputStream(this.filePath));
        try {
            while (true) {
                searchNextChapter(keys, reader, ++chapterID, results);
            }
        } catch (Exception ex) {
            System.out.println("Document end;" + ex.getMessage());
        }
    }
    
    public void search(String key, final List<DocumentSearchResult> results, final SearchControl control) throws Exception {
        final String[] keys = splitString(key);
        final Counter counter = new Counter(0);
        final DocumentReader reader = new InputStreamBlock(new FileInputStream(this.filePath));
        Thread searchThread = new Thread() {
        	public void run() {
        		try {
                    while (true) {
                    	if(control.getSearchAction() == SearchAction.STOP) {
                    		break;
                    	}
                    	if(control.getSearchAction() == SearchAction.PAUSE) {
                    		Thread.sleep(1000);
                    	}
                    	counter.increase();
                        searchNextChapter(keys, reader, counter.getCounterValue(), results);
                    }
                } catch (Exception ex) {
                    System.out.println("Document end;" + ex.getMessage());
                }
        	}
        };
        searchThread.start();
    }
    
    

    public List<DocumentSearchResult> search(int maxResult, String key) throws Exception {
        final String[] keys = splitString(key);
        int chapterID = 0;
        final List<DocumentSearchResult> results = new ArrayList<>();
        final DocumentReader reader = new InputStreamBlock(new FileInputStream(this.filePath));
        try {
            while (true) {
                if (results.size() >= maxResult) {
                    break;
                }
                searchNextChapter(keys, reader, ++chapterID, results);
            }
        } catch (Exception ex) {
            System.out.println("Document end;" + ex.getMessage());
        }
        return results;
    }

    public void search(int maxResult, String key, List<DocumentSearchResult> results) throws Exception {
        final String[] keys = splitString(key);
        int chapterID = 0;
        final DocumentReader reader = new InputStreamBlock(new FileInputStream(this.filePath));
        try {
            while (true) {
                if (results.size() >= maxResult) {
                    break;
                }
                searchNextChapter(keys, reader, ++chapterID, results);
            }
        } catch (Exception ex) {
            System.out.println("Document end;" + ex.getMessage());
        }
    }
    
    public DocumentReader createNewDocumentReader() throws FileNotFoundException {
        return new InputStreamBlock(new FileInputStream(this.filePath));
    }

    public List<DocumentSearchResult> search(String key, final int maxChapterIndex) throws Exception {
        return this.search(key, new Predicate<Integer>() {
            @Override
            public boolean isInvalid(Integer... values) {
                if (values.length == 0) {
                    return true;
                }
                return values[0] <= maxChapterIndex; //Chapter id
            }
        });
    }

    public List<DocumentSearchResult> search(String key, Predicate<Integer> isContinue) throws Exception {
        final String[] keys = splitString(key);
        int chapterID = 0;
        final List<DocumentSearchResult> results = new ArrayList<>();
        final DocumentReader reader = new InputStreamBlock(new FileInputStream(this.filePath));
        try {
            while (true) {
                searchNextChapter(keys, reader, ++chapterID, results);
                if (isContinue != null) {
                    if (!isContinue.isInvalid(chapterID)) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Document end;");
        }
        return results;
    }

    public List<DocumentSearchResult> search(String key, Action3<DocumentReader, Integer, List<DocumentSearchResult>> action) throws Exception {
        final String[] keys = splitString(key);
        int chapterID = 0;
        final List<DocumentSearchResult> results = new ArrayList<>();
        final DocumentReader reader = new InputStreamBlock(new FileInputStream(this.filePath));
        try {
            while (true) {
                searchNextChapter(keys, reader, ++chapterID, results);
                if (action != null) {
                    action.action(reader, chapterID, results);
                }
            }
        } catch (Exception ex) {
            System.out.println("Document end;");
        }
        return results;
    }
    
    public List<DocumentSearchResult> searchNextChapter(final String key, final DocumentReader reader, int chapterID) throws Exception {
        return this.searchNextChapter(splitString(key), reader, chapterID);
    }

    public void searchNextChapter(final String key, final DocumentReader reader, int chapterID, final List<DocumentSearchResult> results) throws Exception {
        this.searchNextChapter(splitString(key), reader, chapterID, results);
    }

    public List<DocumentSearchResult> searchNextChapter(final String[] keys, final DocumentReader reader, int chapterID) throws Exception {
        int maxItem = keys.length;
        int count = 0;
        List<DocumentSearchResult> results = new ArrayList<>();
        while (++count <= this.maxParagraphInChapter) {
            final AbstractWriter abstractWriter = new AbstractWriter();
            if (searchNextParagraph(keys, reader, abstractWriter) == maxItem) {
                abstractWriter.flush();
                results.add(DocumentSearchResult.createDocumentSearchResult(chapterID, count, abstractWriter.toString()));
            }
        }
        return results;
    }

     public void searchNextChapter(final String[] keys, final DocumentReader reader, int chapterID, final List<DocumentSearchResult> results) throws Exception {
        int maxItem = keys.length;
        int count = 0;
        while (++count <= this.maxParagraphInChapter) {
            final AbstractWriter abstractWriter = new AbstractWriter();
            if (searchNextParagraph(keys, reader, abstractWriter) == maxItem) {
                abstractWriter.flush();
                results.add(DocumentSearchResult.createDocumentSearchResult(chapterID, count, abstractWriter.toString()));
            }
        }
    }

    private int searchNextParagraph(final String[] keys, final DocumentReader reader, final AbstractWriter writer) throws Exception {
        final int lengthKey = keys.length;
        final Counter counter = new Counter(0);
        final Function<String, String> convert = new Function<String, String>() {
            @Override
            public String action(String... values) {
                if (values.length == 0) {
                    return null;
                }
                String str = values[0];
                try {
                    if (str.toLowerCase().equals(keys[counter.getCounterValue()])) {
                        counter.increase();
                        if (writer.isFull()) {
                            writer.setMaxLength(writer.getMaxLength() + 20);
                            writer.append("...");
                        }
                    }
                    writer.append(str).append(" ");
                } catch (Exception ex) {

                }
                return str;
            }
        };
        String paragraph = reader.nextParagraph(convert);
        if (paragraph == null) {
            throw new Exception("Document End");
        }
        if (counter.getCounterValue() >= lengthKey) {
            return lengthKey;
        } else {
            return counter.getCounterValue();
        }
    }

    public int getMaxParagraphInChapter() {
        return maxParagraphInChapter;
    }

    public void setMaxParagraphInChapter(int maxParagraphInChapter) {
        this.maxParagraphInChapter = maxParagraphInChapter;
    }

    private final String filePath;
    private int maxParagraphInChapter = 1000;

    public static String[] splitString(String key) {
        List<String> list = new ArrayList<>();
        try {
            final InputStreamBlock reader = new InputStreamBlock(new StringReader(key));
            String word = reader.nextWord();
            while (word != null) {
                list.add(word.toLowerCase());
                word = reader.nextWord();
            }
        } catch (IOException ex) {
//            return new String[]{};
        }
        return list.toArray(new String[list.size()]);
    }

    public static class Counter {

        public Counter() {
            this.counter = 0;
        }

        public Counter(int value) {
            this.counter = value;
        }

        public int getCounterValue() {
            return counter;
        }

        public void increase() {
            counter++;
        }

        public void deincrease() {
            counter--;
        }

        public void setCounterValue(int value) {
            this.counter = value;
        }

        int counter;
    }

    public static class AbstractWriter {

        public AbstractWriter() {
            this.writer = new StringWriter();
        }

        public AbstractWriter append(String string) {
            if (count++ == maxLength) {
                this.writer.append("...");
                return this;
            }
            if (count++ > maxLength) {

                return this;
            }
            this.writer.append(string);
            count++;
            return this;
        }

        public AbstractWriter append(char c) {
            if (count++ == maxLength) {
                this.writer.append("...");
                return this;
            }
            if (count++ > maxLength) {

                return this;
            }
            this.writer.append(c);
            count++;
            return this;
        }

        public void flush() {
            this.writer.flush();
            this.count = 0;
        }

        public void reset() {
            writer = new StringWriter();
            count = 0;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }

        public int getCount() {
            return count;
        }

        public boolean isFull() {
            return this.count >= maxLength;
        }

        @Override
        public String toString() {
            if (writer != null) {
                return writer.toString();
            }
            return "";
        }

        private StringWriter writer;
        private int maxLength = 50;
        private int count = 0;
    }
}
