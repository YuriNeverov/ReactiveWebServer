package events;

import reactive_mongo_driver.ReactiveMongoDriver;
import rx.Observable;
import utils.Either;

import java.util.List;
import java.util.Map;

import static events.QueryParamHandler.ensureLong;
import static events.QueryParamHandler.ensureString;

public class RegisterUserEvent implements Event {
    long id;
    String name;
    String login;
    String currencyType;

    public RegisterUserEvent(long id, String name, String login, String currencyType) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.currencyType = currencyType;
    }

    @Override
    public Observable<String> makeAction() {
        return ReactiveMongoDriver.registerUser(id, name, login, currencyType).map(either ->
            either.match(s -> "Successfully inserted new user", r -> "Error occurred: " + r)
        );
    }

    public static Either<Event, String> makeFromParams(Map<String, List<String>> params) {
        return
            ensureLong(params, "id").bind(id ->
                 ensureString(params, "login").bind(login ->
                       ensureString(params, "name").bind(name ->
                             ensureString(params, "currency_type").bind(currencyType ->
                                     Either.left(new RegisterUserEvent(id, name, login, currencyType))))));

    }
}
