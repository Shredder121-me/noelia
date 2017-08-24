package chat.amy.noelia;

import chat.amy.noelia.message.NoeliaMessage;
import chat.amy.noelia.network.NoeliaNetworker;
import lombok.AccessLevel;
import lombok.Getter;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author amy
 * @since 8/22/17.
 */
@SuppressWarnings("unused")
public final class Noelia {
    @Getter(AccessLevel.PACKAGE)
    private static final Collection<NoeliaFlow> flows = new CopyOnWriteArrayList<>();
    private static final ExecutorService pool = Executors.newCachedThreadPool(new NoeliaThreadFactory("Noelia"));
    private static final Queue<NoeliaMessage> queue = new ConcurrentLinkedDeque<>();
    @Getter
    private static NoeliaNetworker networker;
    
    private Noelia() {
    }
    
    @CheckReturnValue
    public static NoeliaFlow flow() {
        return new NoeliaFlow();
    }
    
    public static void accept(final NoeliaMessage message) {
        submit(new NoeliaWorker(message));
    }
    
    static void take(final NoeliaFlow flow) {
        if(!flows.contains(flow)) {
            flows.add(flow);
        }
    }
    
    @SuppressWarnings("TypeMayBeWeakened")
    private static void submit(final NoeliaWorker worker) {
        pool.submit(worker);
    }
    
    private static final class NoeliaThreadFactory implements ThreadFactory {
        private final String identifier;
        private final AtomicInteger threadCount = new AtomicInteger(1);
        
        private NoeliaThreadFactory(final String id) {
            identifier = id;
        }
        
        @Override
        public Thread newThread(@Nonnull final Runnable r) {
            final Thread thread = new Thread(r, identifier + " thread " + threadCount.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }
}
