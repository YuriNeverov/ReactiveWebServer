package events;

import rx.Observable;

public interface Event {
    Observable<String> makeAction();
}
