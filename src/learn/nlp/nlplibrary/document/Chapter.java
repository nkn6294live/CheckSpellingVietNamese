package learn.nlp.nlplibrary.document;

public interface Chapter extends Iterable<Paragraph> {
    public Document getDocument();
    public String getTitle();
    public Paragraph getParagraph(int index);
    public int getIndex();
}
