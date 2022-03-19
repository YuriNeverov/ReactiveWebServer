package events;

import reactive_mongo_driver.Item;
import reactive_mongo_driver.ReactiveMongoDriver;
import rx.Observable;
import utils.Either;

import java.util.List;
import java.util.Map;

import static events.QueryParamHandler.ensureLong;

public class ItemsListEvent implements Event {
    long id;

    public ItemsListEvent(long id) {
        this.id = id;
    }

    @Override
    public Observable<String> makeAction() {
        return ReactiveMongoDriver.getAllItems(id).map(Item::toString);
    }

    public static Either<Event, String> makeFromParams(Map<String, List<String>> params) {
        return ensureLong(params, "id").bind(id -> Either.left(new ItemsListEvent(id)));
    }
}
