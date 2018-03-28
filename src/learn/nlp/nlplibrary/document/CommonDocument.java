package learn.nlp.nlplibrary.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonDocument implements Document {

    public CommonDocument(String title) {
        this.title = title;
        this.chapters = new ArrayList<>();
    }
    
    public CommonDocument() {
        this("No Title");
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Chapter getChapter(int index) {
        for(Chapter chapter : this.chapters) {
            if(chapter.getIndex() == index) {
                return chapter;
            }
        }
        return null;
    }

    @Override
    public Iterator<Chapter> iterator() {
        return this.chapters.iterator();
    }
    
    private String title;
    private final List<Chapter> chapters;
}
