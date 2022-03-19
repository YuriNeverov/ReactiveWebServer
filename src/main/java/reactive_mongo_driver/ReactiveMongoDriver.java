package reactive_mongo_driver;

import com.mongodb.rx.client.*;
import rx.Observable;
import utils.CurrencyUtils;
import utils.Either;

import static com.mongodb.client.model.Filters.eq;

public class ReactiveMongoDriver {
    private static final MongoDatabase database = createMongoClient().getDatabase("rxservice");

    public static Observable<Either<Success, String>> registerUser(long id, String name,
                                                                   String login, String currencyType) {
        if (CurrencyUtils.fromString(currencyType) == null) {
            return Observable.just(Either.right("Cannot parse currency type"));
        }
        return database.getCollection("users")
                .insertOne(new User(id, name, login, currencyType).toDocument()).map(Either::left);
    }

    public static Observable<Success> addItem(String name, double price) {
        return database.getCollection("items").insertOne(new Item(name, price, CurrencyUtils.Type.RUB).toDocument());
    }

    public static Observable<Item> getAllItems(long id) {
        return database.getCollection("users").find(eq("id", id))
                .toObservable()
                .map(User::new)
                .flatMap(user ->
                    database.getCollection("items").find()
                            .toObservable()
                            .map(doc -> new Item(doc, user.getCurrencyType())));
    }

    public static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
