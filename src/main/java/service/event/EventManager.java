package service.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Observer + Singleton Pattern — Central event bus.
 *
 * Singleton: only one EventManager exists in the entire application.
 * Observer:  listeners subscribe to event types and are notified
 *            automatically when those events are published.
 */
public class EventManager {

    // ── Singleton ──────────────────────────────────────────────
    private static EventManager instance;

    private EventManager() {
        this.listeners = new HashMap<>();
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    // ── Observer ───────────────────────────────────────────────
    private final Map<String, List<EventListener>> listeners;

    /**
     * Subscribe a listener to a specific event type.
     */
    public void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
        System.out.println("[EventManager] Subscribed " + listener.getClass().getSimpleName()
                + " to event: " + eventType);
    }

    /**
     * Unsubscribe a listener from a specific event type.
     */
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
            System.out.println("[EventManager] Unsubscribed " + listener.getClass().getSimpleName()
                    + " from event: " + eventType);
        }
    }

    /**
     * Publish an event — all registered listeners for that event type are notified.
     */
    public void publish(String eventType, String data) {
        System.out.println("\n[EventManager] Publishing event: " + eventType);
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.update(eventType, data);
            }
        } else {
            System.out.println("[EventManager] No listeners for event: " + eventType);
        }
    }

    /** Reset for testing purposes. */
    public void clearAll() {
        listeners.clear();
    }
}
