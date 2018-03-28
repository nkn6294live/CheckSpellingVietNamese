package learn.nlp.nlplibrary.document;

public interface Document extends Iterable<Chapter>{
    public String getTitle();
    public Chapter getChapter(int index);
}
