package chat.amy.noelia.message;

import com.google.gson.JsonElement;
import lombok.Value;

/**
 * TODO: Document
 *
 * @author amy
 * @since 8/22/17.
 */
@Value
@SuppressWarnings("WeakerAccess")
public class NoeliaMessage {
    private final String source;
    private final String topic;
    private final JsonElement data;
}
