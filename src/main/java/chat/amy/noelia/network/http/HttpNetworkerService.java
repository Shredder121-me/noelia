package chat.amy.noelia.network.http;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import static spark.Spark.*;

/**
 * @author amy
 * @since 8/24/17.
 */
public class HttpNetworkerService extends AbstractIdleService {
    private final JsonParser parser = new JsonParser();
    
    @Override
    protected void startUp() throws Exception {
        port(80);
        post("/", (req, res) -> {
            final String body = req.body();
            final JsonElement parse = parser.parse(body);
            // TODO: Delegate out parsing and return as early as possible
            return null;
        });
    }
    
    @Override
    protected void shutDown() throws Exception {
        stop();
    }
}
