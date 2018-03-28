package learn.nlp.nlplibrary.delegate;

@SuppressWarnings("unchecked")
public interface Action<T> {
	public void action(T... values);
}
