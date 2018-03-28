package learn.nlp.nlplibrary.search;

import java.io.StringWriter;

public class DocumentSearchResult {
    
    public static DocumentSearchResult createDocumentSearchResult(int chapterId, int paragraphID, String content) {
        DocumentSearchResult result = new DocumentSearchResult();
        result.setAbstractContent(content);
        result.setChapterID(chapterId);
        result.setParagraphID(paragraphID);
        return result;
    }
    
    public DocumentSearchResult() {
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public int getParagraphID() {
        return paragraphID;
    }

    public void setParagraphID(int paragraphID) {
        this.paragraphID = paragraphID;
    }

    public String getAbstractContent() {
        return abstractContent;
    }

    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }
    
    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        writer.append("ChapterID:").append(this.chapterID + "").append("\n");
        writer.append("\tParagraphID:").append(this.paragraphID + "").append("\n");
        writer.append("\tAbstractContent:").append(this.abstractContent).append("\n");
        return writer.toString();
    }
    
    private int chapterID = 0;
    private int paragraphID = 0;
    private String abstractContent = "Tóm tắt";
}
