package reactive_mongo_driver;

import org.bson.Document;
import utils.CurrencyUtils;

import java.util.Map;

public class User {
    private final long id;
    private final String name;
    private final String login;
    private final CurrencyUtils.Type currencyType;

    public User(Document doc) {
        this(doc.getLong("id"), doc.getString("name"),
             doc.getString("login"), doc.getString("currency_type"));
    }

    public User(long id, String name, String login, String currencyType) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.currencyType = CurrencyUtils.fromString(currencyType);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + '\'' +
                ", login='" + login + "', currency_type='" + currencyType + "'}";
    }

    public Document toDocument() {
        return new Document(Map.of("id", id, "name", name,
                                "login", login, "currency_type", currencyType));
    }

    public CurrencyUtils.Type getCurrencyType() {
        return currencyType;
    }
}
