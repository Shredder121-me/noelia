package chat.amy.noelia.network.http;

import chat.amy.noelia.Noelia;
import chat.amy.noelia.message.NoeliaMessage;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static spark.Spark.*;

/**
 * @author amy
 * @since 8/24/17.
 */
public class HttpNetworkerService extends AbstractIdleService {
    private final Gson gson = new GsonBuilder()
            // TODO: So I've not proven that gson behaves right here, but this is commented out in case it doesn't.
            /*
            .registerTypeAdapter(NoeliaMessage.class, new JsonDeserializer<NoeliaMessage>() {
                @Override
                public NoeliaMessage deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return null;
                }
            })
            .registerTypeAdapter(NoeliaMessage.class, new JsonSerializer<NoeliaMessage>() {
                @Override
                public JsonElement serialize(final NoeliaMessage noeliaMessage, final Type type, final JsonSerializationContext jsonSerializationContext) {
                    return null;
                }
            })
            */
            .setPrettyPrinting().serializeNulls().create();
    
    @Override
    protected void startUp() throws Exception {
        port(80);
        post("/", (req, res) -> {
            final List<NoeliaMessage> messages = gson.fromJson(req.body(), new TypeToken<List<NoeliaMessage>>() {
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
