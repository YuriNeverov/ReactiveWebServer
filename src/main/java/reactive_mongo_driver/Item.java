package reactive_mongo_driver;

import org.bson.Document;
import utils.CurrencyUtils;

import java.util.Map;

public class Item {
    public final String name;
    public final double price;
    public final CurrencyUtils.Type currencyType;

    public Item(Document doc, CurrencyUtils.Type currencyType) {
        this(
            doc.getString("name"),
            CurrencyUtils.scale(doc.getDouble("price"), currencyType),
            currencyType
        );
    }

    public Item(String name, double price, CurrencyUtils.Type currencyType) {
        this.name = name;
        this.price = price;
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return "Item{name='" + name + '\'' + ", price='" + price + " " + currencyType + "' }";
    }

    public Document toDocument() {
        return new Document(Map.of("name", name, "price", price));
    }
}