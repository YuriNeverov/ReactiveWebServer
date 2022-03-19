package utils;

public class CurrencyUtils {
    public enum Type {
        RUB("RUB", 1.0),
        ILS("ILS", 0.03),
        KZT("KZT", 4.73),
        MNT("MNT", 26.81);

        final String name;
        final double rate;

        Type(String name, double rate) {
            this.name = name;
            this.rate = rate;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static Type fromString(String typeName) {
        switch (typeName) {
            case "ruble":
            case "RUB":
                return Type.RUB;
            case "shekel":
            case "ILS":
                return Type.ILS;
            case "tenge":
            case "KZT":
                return Type.KZT;
            case "tugrik":
            case "MNT":
                return Type.MNT;
        }
        return null;
    }

    public static double scale(double rublePrice, Type currencyType) {
        return rublePrice * currencyType.rate;
    }
}
