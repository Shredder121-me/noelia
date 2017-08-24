package chat.amy.noelia.message;

import com.google.gson.JsonObject;
import lombok.Value;

/**
 * Since this is designed to listen on networked event busses that pipe into it
 * ifrom disparate sources that may or may not hjave events that match the
 * names of events of other things, we should have a topics or whatever feature
 * that basically exists to make sure that events ar enicely namespaced and
 * whatnot. So like, if an event came from twitch and was a message send, the
 * topic becomes twitch:message-send kinda thing.
 *
 * @author amy
 * @since 8/22/17.
 */
@Value
@SuppressWarnings("WeakerAccess")
public class NoeliaMessage {
    private final String source;
    private final JsonObject data;
}
