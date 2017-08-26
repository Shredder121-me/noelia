package chat.amy.noelia.message.util.heartbeat;

import chat.amy.noelia.message.NoeliaMessage;

/**
 * A simple packet that represents a "heartbeat" message to ensure that a
 * service is still up. Could be used for ex. a WebSocket implementation of
 * {@link chat.amy.noelia.network.NoeliaNetworker}. The topic of this message
 * is of the format <code>heartbeat:source</code>, ex.
 * <code>heartbeat:service-1</code>.
 *
 * @author amy
 * @since 8/25/17.
 */
public class HeartbeatMessage extends NoeliaMessage {
    public HeartbeatMessage(final String source) {
        super(source, "heartbeat:" + source, null);
    }
}
