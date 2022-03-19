package events;

import utils.Either;

import java.util.List;
import java.util.Map;

public class QueryParamHandler {
    public static Either<Long, String> ensureLong(Map<String, List<String>> mapping, String expectedKey) {
        return ensureString(mapping, expectedKey).bind(value -> {
            try {
                return Either.left(Long.valueOf(value));
            } catch (NumberFormatException e) {
                return Either.right("Couldn't parse parameter " + expectedKey + "='" + value + "' to number");
            }
        });
    }

    public static Either<Double, String> ensureDouble(Map<String, List<String>> mapping, String expectedKey) {
        return ensureString(mapping, expectedKey).bind(value -> {
            try {
                return Either.left(Double.valueOf(value));
            } catch (NumberFormatException e) {
                return Either.right("Couldn't parse parameter " + expectedKey + "='" + value + "' to number");
            }
        });
    }

    public static Either<String, String> ensureString(Map<String, List<String>> mapping, String expectedKey) {
        if (mapping.containsKey(expectedKey)) {
            List<String> values = mapping.get(expectedKey);
            if (values.size() != 1) {
                return Either.right("Expected one value for parameter '" + expectedKey + "'");
            }
            return Either.left(values.get(0));
        }
        return Either.right("Parameters should contain '" + expectedKey + "' parameter");
    }
}
