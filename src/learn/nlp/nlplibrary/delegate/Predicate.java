package learn.nlp.nlplibrary.delegate;

@SuppressWarnings("unchecked")
public interface Predicate<T> {
    public boolean isInvalid(T... values);
}
