package learn.nlp.nlplibrary.delegate;

@SuppressWarnings("unchecked")
public interface Function2<TParam1, TPram2, TResult> {
    public TResult action(TParam1 param1, TPram2... param2s);
}
