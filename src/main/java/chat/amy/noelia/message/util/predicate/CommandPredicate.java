package chat.amy.noelia.message.util.predicate;

import chat.amy.noelia.message.NoeliaMessage;

import java.util.function.Predicate;

/**
 * A simple predicate to derive command name from topic. Topics that satisfy
 * this predicate are of the format <code>command:name</code>, ex.
 * <code>command:ping</code>.
 *
 * @author amy
 * @since 8/25/17.
 */
public class CommandPredicate implements Predicate<NoeliaMessage> {
    private final String name;
    
    public CommandPredicate(final String name) {
        this.name = name;
    }
    
    @Override
    public boolean test(final NoeliaMessage noeliaMessage) {
        return noeliaMessage.getTopic().equalsIgnoreCase("command:" + name);
    }
}
