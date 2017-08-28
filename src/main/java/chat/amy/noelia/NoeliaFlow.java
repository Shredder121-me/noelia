package chat.amy.noelia;

import chat.amy.noelia.message.NoeliaMessage;
import com.google.common.collect.ImmutableMap;

import javax.annotation.CheckReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author amy
 * @since 8/22/17.
 */
public class NoeliaFlow {

    private static final Function<?, Map<String, Collection<NoeliaMessage>>> NO_OP = __ -> ImmutableMap.of();

    private final Collection<Predicate<NoeliaMessage>> checks = new CopyOnWriteArrayList<>();
    private Function<NoeliaMessage, Map<String, Collection<NoeliaMessage>>> acceptFunction;
    
    NoeliaFlow() {
    }
    
    @CheckReturnValue
    public NoeliaFlow check(final Predicate<NoeliaMessage> check) {
        if(!checks.contains(check)) {
            checks.add(check);
        }
        return this;
    }

    @CheckReturnValue
    public NoeliaFlow accept(Consumer<NoeliaMessage> consumer) {
        return accept(NO_OP.compose(message -> {
            consumer.accept(message);
            return null;
        }));
    }

    @CheckReturnValue
    public NoeliaFlow accept(final Function<NoeliaMessage, Map<String, Collection<NoeliaMessage>>> function) {
        if(acceptFunction == null) {
            acceptFunction = function;
        }
        return this;
    }
    
    public void subscribe() {
        Noelia.take(this);
    }
    
    @CheckReturnValue
    boolean check(final NoeliaMessage message) {
        return message != null && checks.stream().filter(check -> check.test(message)).count() == checks.size();
    }
    
    @CheckReturnValue
    Map<String, Collection<NoeliaMessage>> accept(final NoeliaMessage message) {
        if(message != null) {
            return acceptFunction.apply(message);
        }
        return null;
    }
}
