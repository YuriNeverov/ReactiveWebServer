package events;

import reactive_mongo_driver.ReactiveMongoDriver;
import rx.Observable;
import utils.Either;

import java.util.List;
import java.util.Map;

import static events.QueryParamHandler.ensureString;
import static events.QueryParamHandler.ensureDouble;

public class AddItemEvent implements Event {
    private final String name;
    private final double price;

    public AddItemEvent(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public Observable<String> makeAction() {
        return ReactiveMongoDriver.addItem(name, price).map(s -> "Successfully added new item");
    }

    public static Either<Event, String> makeFromParams(Map<String, List<String>> params) {
        return
            ensureString(params, "name").bind(name ->
                ensureDouble(params, "price").bind(price -> Either.left(new AddItemEvent(name, price))));
    }
}

