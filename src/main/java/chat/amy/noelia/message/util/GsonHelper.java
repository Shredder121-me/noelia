package chat.amy.noelia.message.util;

import chat.amy.noelia.message.NoeliaMessage;
import com.google.gson.*;
import lombok.Getter;

/**
 * @author amy
 * @since 8/27/17.
 */
public final class GsonHelper {
    @Getter
    private static final Gson gson = new GsonBuilder()
            // TODO: Fuck serialization
            .registerTypeAdapter(NoeliaMessage.class, (JsonDeserializer<NoeliaMessage>) (jsonElement, type, jsonDeserializationContext) -> {
                final JsonObject o = jsonElement.getAsJsonObject();
                return new NoeliaMessage(o.get("s").getAsString(), o.get("t").getAsString(), o.get("d"));
            })
            .registerTypeAdapter(NoeliaMessage.class, (JsonSerializer<NoeliaMessage>) (noeliaMessage, type, jsonSerializationContext) -> {
                JsonObject o = new JsonObject();
                o.addProperty("s", noeliaMessage.getSource());
                o.addProperty("t", noeliaMessage.getTopic());
                o.add("s", noeliaMessage.getData());
                return o;
            })
            .setPrettyPrinting().serializeNulls().create();
    
    private GsonHelper() {
    }
}
