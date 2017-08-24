package chat.amy.noelia.network;

import chat.amy.noelia.message.NoeliaMessage;
import com.google.common.util.concurrent.Service;

import java.util.Collection;
import java.util.Map;

import static chat.amy.noelia.network.NoeliaNetworker.NetworkState.ERR;
import static chat.amy.noelia.network.NoeliaNetworker.NetworkState.OK;

/**
 * This is probably easiest to implement as a {@link com.google.common.util.concurrent.AbstractExecutionThreadService}
 *
 * @author amy
 * @since 8/24/17.
 */
public interface NoeliaNetworker {
    default NetworkState sendMany(final Map<String, Collection<NoeliaMessage>> messages) {
        final int[] okCount = {0};
        messages.forEach((k, v) -> {
            if(sendMany(k, v) == OK) {
                ++okCount[0];
            }
        });
        return okCount[0] == messages.size() ? OK : ERR;
    }
    
    NetworkState sendMany(String dest, Collection<NoeliaMessage> messages);
    
    /**
     * The networking service. This is meant to be kept internally as a Guava
     * {@link Service} so that any sort of networker - HTTP, WebSockets, *MQ,
     * ... - can be "genericized" over with service start / stop / etc.
     * <p/>
     * Use of this service is basically entirely up to the implementaiton, but
     * implementation should <b>NOT</b> require that uses mess with its
     * internal implementation; if it can't be used through the methods exposed
     * in the {@link NoeliaNetworker} interface, it's probably designed wrong.
     * For example, if you wanted to implement this for something like
     * RabbitMQ, you probably want things like {@link #sendMany(Map)} and
     * {@link #sendMany(String, Collection)} to just update a
     * {@link java.util.Queue} that gets polled by this service.
     *
     * @return The Service that this networker manages
     */
    Service getNetworkService();
    
    /**
     * Result of sending messages to other "workers" on the network
     * <p/>
     * TODO: Specialization
     */
    enum NetworkState {
        OK,
        ERR
    }
}
