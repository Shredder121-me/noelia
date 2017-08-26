package chat.amy.noelia.message;

import com.google.gson.JsonElement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * TODO: Document
 *
 * @author amy
 * @since 8/22/17.
 */
@Data
@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor
public class NoeliaMessage {
    /**
     * The name of the service the message came from
     */
    private final String source;
    /**
     * "Topic" of the message, to be parsed by <code>check()</code> predicates.
     */
    private final String topic;
    /**
     * "Body" of the message. Should be handled by <code>accept()</code>or
     * functions.
     */
    private final JsonElement data;
}
