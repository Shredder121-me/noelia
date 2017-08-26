package chat.amy.noelia.message.util.heartbeat;

import chat.amy.noelia.message.NoeliaMessage;

import java.util.function.Predicate;

/**
 * @author amy
 * @since 8/26/17.
 */
public class HeartbeatPredicate implements Predicate<NoeliaMessage> {
    @Override
    public boolean test(final NoeliaMessage noeliaMessage) {
        return noeliaMessage.getTopic().startsWith("heartbeat:");
    }
}
