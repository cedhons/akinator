package Event;

public interface Subscriber<T> {
	void notify(T message);
}
