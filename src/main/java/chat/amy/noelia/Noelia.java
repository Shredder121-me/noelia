package chat.amy.noelia;

import chat.amy.noelia.message.NoeliaMessage;
import chat.amy.noelia.network.NoeliaNetworker;
import chat.amy.noelia.network.http.HttpNetworker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author amy
 * @since 8/22/17.
 */
@SuppressWarnings("unused")
public final class Noelia {
    @Getter(AccessLevel.PACKAGE)
    private static final Collection<NoeliaFlow> flows = new CopyOnWriteArrayList<>();
    private static final ExecutorService pool = Executors.newCachedThreadPool(new NoeliaThreadFactory("noelia"));
    @Getter
    @Setter
    private static NoeliaNetworker networker = new HttpNetworker();
    
    private Noelia() {
    }
    
    @CheckReturnValue
    public static NoeliaFlow flow() {
        return new NoeliaFlow();
    }
    
    public static void accept(final Collection<NoeliaMessage> messages) {
        submit(new NoeliaWorker(messages));
    }
    
    static void take(final NoeliaFlow flow) {
        if(!networker.getNetworkService().isRunning()) {
            // TODO: Log
            networker.getNetworkService().startAsync();
        }
        if(!flows.contains(flow)) {
            flows.add(flow);
        }
    }
    
    @SuppressWarnings("TypeMayBeWeakened")
    private static void submit(final NoeliaWorker worker) {
        pool.execute(worker);
    }
    
    private static final class NoeliaThreadFactory implements ThreadFactory {
        private final String identifier;
        private final AtomicInteger threadCount = new AtomicInteger(1);
        
        private NoeliaThreadFactory(final String id) {
            identifier = id;
        }
        
        @Override
        public Thread newThread(@Nonnull final Runnable r) {
            final Thread thread = new Thread(r, identifier + " worker thread " + threadCount.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }
}
