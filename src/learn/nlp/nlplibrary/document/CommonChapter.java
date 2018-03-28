package learn.nlp.nlplibrary.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonChapter implements  Chapter {

    public CommonChapter(String title, int index, Document document) {
        this.title = title;
        this.document = document;
        this.index = index;
        this.paragraphs = new ArrayList<>();
    }
    @Override
    public Document getDocument() {
        return this.document;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Paragraph getParagraph(int index) {
        for(Paragraph paragraph : paragraphs) {
            if(paragraph.getIndex() == index) {
                return paragraph;
            }
        }
        return null;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Paragraph> iterator() {
        return this.paragraphs.iterator();
    }
    
    private Document document;
    private String title;
    private int index;
    private List<Paragraph> paragraphs;
}
