package chat.amy.noelia.network.websocket.client;

import chat.amy.noelia.message.NoeliaMessage;
import chat.amy.noelia.network.NoeliaNetworker;
import com.google.common.util.concurrent.Service;

import java.util.Collection;

/**
 * TODO: nv-websocket-client
 *
 * @author amy
 * @since 8/27/17.
 */
public class WebsocketClientNetworker implements NoeliaNetworker {
    @Override
    public NetworkState sendMany(final String dest, final Collection<NoeliaMessage> messages) {
        return null;
    }
    
    @Override
    public Service getNetworkService() {
        return null;
    }
}
