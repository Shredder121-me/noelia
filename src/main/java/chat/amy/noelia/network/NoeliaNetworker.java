package chat.amy.noelia.network;

import chat.amy.noelia.message.NoeliaMessage;

import java.util.Collection;
import java.util.Map;

/**
 * This is probably easiest to implement as a {@link com.google.common.util.concurrent.AbstractExecutionThreadService}
 *
 * @author amy
 * @since 8/24/17.
 */
public interface NoeliaNetworker {
    /**
     * Result of sending messages to other "workers" on the network
     * <p/>
     * TODO: Specialization
     */
    enum NetworkState {
        OK,
        ERR
    }
    
    NetworkState sendMany(Map<String, Collection<NoeliaMessage>> messages);
    
    NetworkState sendMany(String dest, Collection<NoeliaMessage> messages);
}
