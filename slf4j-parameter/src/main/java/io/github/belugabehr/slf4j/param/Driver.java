package io.github.belugabehr.slf4j.param;

import java.io.IOException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J supports an advanced feature called parameterized logging which can
 * significantly boost logging performance for disabled logging statement. The
 * methods that facilitate parameterized logging has different method
 * signatures. To support three or more parameters in a single log statement,
 * the debug method uses the ellipsis (...) i.e. a variable number of arguments,
 * therefore each invocation of the method requires wrapping the three arguments
 * into a native array of Objects. SLF4J carries method signatures for one or
 * two arguments to avoid the array instantiation.
 *
 * @see Logger#debug(String)
 * @see Logger#debug(String, Object, Object)
 * @see Logger#debug(String, Object...)
 */
public class Driver {

  private final static Logger LOGGER = LoggerFactory.getLogger(Driver.class);

  /**
   * Make the variables volatile so that the compiler does not attempt to
   * optimize the static string into the methods.
   */
  @State(Scope.Thread)
  public static class Context {
    volatile String quick = "quick";
    volatile String brown = "brown";
    volatile String lazy = "lazy";
  }

  @Benchmark
  public void logParamsOne(final Context context) {
    LOGGER.debug("The {} brown fox jumps over the lazy dog", context.quick);
  }

  @Benchmark
  public void logParamsTwo(final Context context) {
    LOGGER.debug("The {} {} fox jumps over the lazy dog", context.quick,
        context.brown);
  }

  @Benchmark
  public void logParamsThree(final Context context) {
    LOGGER.debug("The {} {} fox jumps over the {} dog", context.quick,
        context.brown, context.lazy);
  }

  public void logGuardsZero(final Context context) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("The quick brown fox jumps over the lazy dog");
    }
  }

  @Benchmark
  public void logGuardsOne(final Context context) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER
          .debug("The " + context.quick + " brown fox jumps over the lazy dog");
    }
  }

  @Benchmark
  public void logGuardsTwo(final Context context) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("The " + context.quick + " " + context.brown
          + " fox jumps over the lazy dog");
    }
  }

  @Benchmark
  public void logGuardsThree(final Context context) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("The " + context.quick + " " + context.brown
          + " fox jumps over the " + context.lazy + " dog");
    }
  }

  @Benchmark
  public void standardLogZero(final Context context) {
    LOGGER.debug("The quick brown fox jumps over the lazy dog");
  }

  @Benchmark
  public void standardLogOne(final Context context) {
    LOGGER.debug("The " + context.quick + " brown fox jumps over the lazy dog");
  }

  @Benchmark
  public void standardLogTwo(final Context context) {
    LOGGER.debug("The " + context.quick + " " + context.brown
        + " fox jumps over the lazy dog");
  }

  @Benchmark
  public void standardLogThree(final Context context) {
    LOGGER.debug("The " + context.quick + " " + context.brown
        + " fox jumps over the " + context.lazy + " dog");
  }

  public static void main(String[] args) throws RunnerException, IOException {
    Options opt = new OptionsBuilder()
        .include(".*" + Driver.class.getSimpleName() + ".*").forks(1).build();

    new Runner(opt).run();
  }
}
