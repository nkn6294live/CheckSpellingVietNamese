package learn.nlp.nlplibrary.checker;

public interface CheckerStatus {
    public CheckerStatus getNext(char c);
    public String getStatus();
}
