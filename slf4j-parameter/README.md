# Perf Test SLF4J Parameterized Logging

SLF4J supports an advanced feature called parameterized logging which can significantly boost logging performance for disabled logging statement.

## Motivation

I think less code is usually better code; therefore, I often advocate that folks use parameterized logging for all the trivial log messages that applications usually emit instead of logging guards. There are of course times when large blocks of code or complicated debugging data must be generated are required for generating useful log messages, but the vast majority of messages are simple one-liners with a variable or two injected.

There is some concern that the overhead of using parameterized is going to negatively impact the performance of applications.

## Example

```
// Logging Guard
if (LOGGER.isDebugEnabled()) {
  LOGGER.debug("My application name is: " + varApplicationName);
}

// Parameterized Logging
LOGGER.debug("My application name is: {}", varApplicationName);
```

## Additional Reading

* [The fastest way of (not) logging](http://www.slf4j.org/faq.html#logging_performance)