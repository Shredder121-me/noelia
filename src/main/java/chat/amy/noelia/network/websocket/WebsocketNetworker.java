package chat.amy.noelia.network.websocket;

import chat.amy.noelia.message.NoeliaMessage;
import chat.amy.noelia.network.NoeliaNetworker;
import com.google.common.util.concurrent.Service;

import java.util.Collection;

/**
 * TODO
 *
 * @author amy
 * @since 8/26/17.
 */
public class WebsocketNetworker implements NoeliaNetworker {
    @Override
    public NetworkState sendMany(final String dest, final Collection<NoeliaMessage> messages) {
        return null;
    }
    
    @Override
    public Service getNetworkService() {
        return null;
    }
}
