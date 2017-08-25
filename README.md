# noelia

noelia is a library for reactive-like actor-style message passing, designed for use over a network of distributed microservices. noelia was developed to power [amybot](https://amy.chat/).

## Example

```Java
public class Example {
    public static void main(String[] args) {
        // This is the default, but is shown here for clarity
        Noelia.setNetworker(new HttpNetworker());
        
        // Set up a basic "flow" for messages to pass through
        // This implicitly starts the networker if it isn't running
        // While you can start it manually, it's not recommended
        Noelia.flow()
            // If it's a message being sent to us...
            .check(message -> message.getTopic().equals("message:send"))
            // ...we run this function that takes it in and sends back some output
            // Note: We can have as many check() predicates as we want
            .check(message -> true)
            .check(message -> 1 == 1)
            .accept(message -> {
                return ImmutableMap.of("service-1", Collections.singletonList(
                            new NoeliaMessage("example", "message:send", null)
                    ),
                    message.getSource(), Collections.singletonList(
                            new NoeliaMessage("example", "message:respond", null)
                    ));
            })
            .subscribe();
        // Sending messages is pretty similar to what the accept() function returns
        Noelia.getNetworker().sendMany(
            ImmutableMap.of(
                "service-1", Collections.singletonList(
                    new NoeliaMessage("example", "message:send", null)
                ),
                message.getSource(), Collections.singletonList(
                    new NoeliaMessage("example", "message:respond", null)
                )
            )
        );
        // Or we can do it the "manual" way if we want
        Noelia.getNetworker().sendMany("service-1", Collections.singletonList(
                new NoeliaMessage("example", "message-send", null)
        ));
    }
}
```

## Explanations and stuff

### Message Format

By default, noelia assumes that you're sending JSON messages over the network; the `NoeliaMessage` class contains a Gson `JsonObject` by default. This many change eventually(TM), but for now JSON is good enough. 

Other than this, messages are to be in the following format:
```Javascript
{
  "s": "source", // To know where it came from, since it's the likely 
                 // place for sending responses
  "t": "something:whatever:etc", // This is parsed by check() predicates
  "d": {
    // This can be literally anything you want, as long as you write the
    // predicates and handlers for it.  
  }
}
``` 

### Networking Types

Currently, noelia only provides an `HttpNetworker`; the system is designed to also allow for other "protocols" like WebSockets or \*MQ or etc., but only HTTP is provided by default. 