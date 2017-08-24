# noelia

noelia is a library for reactive-like actor-style message passing, designed for use over a network of distributed microservices. noelia was developed to power [amybot](https://amy.chat/).

## Message Format

By default, noelia assumes that you're sending JSON messages over the network; the `NoeliaMessage` class contains a Gson `JsonObject` by default. This many change eventually(TM), but for now JSON is good enough. 

Other than this, messages are to be in the following format:
```JSON
{
  "source": "source", // To know where it came from, since it's the likely place for sending responses
  "topic": "something:whatever:etc", // This is parsed by check() predicates
  "data": {
    // This can be literally anything you want. 
  }
}
``` 

## Networking Types

Currently, noelia only provides an `HttpNetworker`; the system is designed to also allow for other "protocols" like WebSockets or \*MQ or etc., but only HTTP is provided by default. 