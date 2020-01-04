package io.github.belugabehr.perf.queue;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Generate random integers between 0 and 1000.
 */
public class Driver {

  private static final Random RANDOM = new Random();

  @State(Scope.Thread)
  public static class Context {
    final Random random = new Random();
    final Random sRandom = new SecureRandom();
  }

  @Benchmark
  public int randomGlobal(final Context context) {
    return RANDOM.nextInt(1000);
  }

  @Benchmark
  public int random(final Context context) {
    return context.random.nextInt(1000);
  }

  @Benchmark
  public int randomSecure(final Context context) {
    return context.sRandom.nextInt(1000);
  }

  @Benchmark
  public int randomMath(final Context context) {
    return (int) (Math.random() * 1000);
  }

  @Benchmark
  public int threadLocalRandom(final Context context) {
    return ThreadLocalRandom.current().nextInt(1000);
  }

  public static void main(String[] args) throws RunnerException, IOException {
    Options opt = new OptionsBuilder()
        .include(".*" + Driver.class.getSimpleName() + ".*").forks(1).build();

    new Runner(opt).run();
  }
}
