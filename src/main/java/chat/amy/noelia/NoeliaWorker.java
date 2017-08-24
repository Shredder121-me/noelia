package chat.amy.noelia;

import chat.amy.noelia.message.NoeliaMessage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author amy
 * @since 8/22/17.
 */
final class NoeliaWorker implements Runnable {
    private final Collection<NoeliaMessage> messages;
    
    NoeliaWorker(final Collection<NoeliaMessage> messages) {
        this.messages = messages;
    }
    
    @Override
    public void run() {
        messages.forEach(message -> {
            final Map<String, Collection<NoeliaMessage>> output = new ConcurrentHashMap<>();
            Noelia.getFlows().stream().filter(e -> e.check(message)).forEach(e -> e.accept(message).forEach((k, v) -> {
                if(output.containsKey(k)) {
                    output.get(k).addAll(v);
                } else {
                    output.put(k, v);
                }
            }));
            switch(Noelia.getNetworker().sendMany(output)) {
                // TODO: Specialized errors
                case OK:
                    break;
                case ERR:
                    // logging :S
                    break;
            }
        });
    }
}
