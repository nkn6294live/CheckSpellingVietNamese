package learn.nlp.nlplibrary.delegate;

@SuppressWarnings("unchecked")
public interface Function<TParam, TResult> {
    public TResult action(TParam... values);
}
