package learn.nlp.nlplibrary.document;

public class TitleFactory {

    protected int index = 0;

    public String getNextTitle(String title) {
        return String.format(getStringFormat(), ++index, title.length() > 0 ? title : "NO TITLE");
    }
    public String getStringFormat() {
        return "Chapter %1$s:%2$s";
    }

}
