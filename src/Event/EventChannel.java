package Event;

import java.util.LinkedList;
import java.util.List;

public class EventChannel<T> {
	List<Subscriber<T>> subscribers;
	
	public EventChannel() {
		subscribers = new LinkedList<Subscriber<T>>();
	}
	
	public void subscribe(Subscriber<T> subscriber) {
		subscribers.add(subscriber);
	}
	
	public void notifyAll(T message) {
		subscribers.forEach(s -> s.notify(message));
	}
}
