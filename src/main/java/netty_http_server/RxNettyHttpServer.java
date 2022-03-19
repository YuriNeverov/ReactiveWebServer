package netty_http_server;

import events.*;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;
import utils.Either;

import java.util.List;
import java.util.Map;

public class RxNettyHttpServer {
    public static void main(final String[] args) {
        HttpServer
                .newServer(8074)
                .start((req, resp) -> {
                    String path = req.getDecodedPath();
                    var params = req.getQueryParameters();
                    Observable<String> response = Observable
                            .just(path)
                            .map(query -> parseEvent(query, params))
                            .flatMap(either -> either.match(Event::makeAction,
                                                            x -> Observable.just("Error occurred: " + x)));
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    public static Either<Event, String> parseEvent(String query, Map<String, List<String>> params) {
        switch (query) {
            case "/register":
                return RegisterUserEvent.makeFromParams(params);
            case "/items":
                return ItemsListEvent.makeFromParams(params);
            case "/add":
                return AddItemEvent.makeFromParams(params);
        }
        return Either.right("Unknown query path starts with '" + query + "'");
    }
}
