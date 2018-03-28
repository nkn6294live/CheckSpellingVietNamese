package learn.nlp.nlplibrary.common;

public interface CollectionCheckAble extends Changable<Object> {
    public boolean isExited(String value);
    public String getStartString(String string);
}
