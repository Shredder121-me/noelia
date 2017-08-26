package chat.amy.noelia.message.util.heartbeat;

import chat.amy.noelia.Noelia;
import com.google.common.util.concurrent.AbstractScheduledService;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * A service to send {@link HeartbeatMessage}s to a specified gateway service,
 * intended for ensuring that a service hasn't gone full :fire:.
 *
 * @author amy
 * @since 8/25/17.
 */
public class HeartbeatService extends AbstractScheduledService {
    private final String service;
    private final String gatewayService;
    private final long heartbeatPeriodMs;
    
    public HeartbeatService(final String service, final String gatewayService, final long heartbeatPeriodMs) {
        this.service = service;
        this.gatewayService = gatewayService;
        this.heartbeatPeriodMs = heartbeatPeriodMs;
    }
    
    @Override
    protected void runOneIteration() throws Exception {
        // No point in using ImmutableMap.of() because sendMany() will just take the map apart internally anyway
        Noelia.getNetworker().sendMany(gatewayService, Collections.singletonList(new HeartbeatMessage(service)));
    }
    
    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, heartbeatPeriodMs, TimeUnit.MILLISECONDS);
    }
}
