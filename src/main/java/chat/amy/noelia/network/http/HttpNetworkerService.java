package chat.amy.noelia.network.http;

import chat.amy.noelia.Noelia;
import chat.amy.noelia.message.NoeliaMessage;
import chat.amy.noelia.message.util.GsonHelper;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static spark.Spark.*;

/**
 * @author amy
 * @since 8/24/17.
 */
public class HttpNetworkerService extends AbstractIdleService {
    @Override
    protected void startUp() throws Exception {
        port(80);
        post("/", (req, res) -> {
            final List<NoeliaMessage> messages = GsonHelper.getGson().fromJson(req.body(), new TypeToken<List<NoeliaMessage>>() {
            }.getType());
            Noelia.accept(messages);
            return null;
        });
    }
    
    @Override
    protected void shutDown() throws Exception {
        stop();
    }
}
