package handlers;

import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

import java.util.List;
import java.util.Map;

public class QueryStringExampleHandler implements RouteHandler {
    public void handle(MuRequest request, MuResponse response, Map<String,String> pathParams) {
        String something = request.query().get("something");

        String somethingElse = request.query().get("something", "default value");

        List<String> somethingList = request.query().getAll("something");

        int intValue = request.query().getInt("something", 42);

        response.sendChunk(something + " / " + somethingElse + " / " + somethingList + " / " + intValue);
    }
}