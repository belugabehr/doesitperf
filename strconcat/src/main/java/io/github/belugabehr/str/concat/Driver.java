package io.github.belugabehr.str.concat;

import java.io.IOException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Driver {

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
  public String stringConcatOpTwo(final Context context) {
    return context.quick + context.brown;
  }

  @Benchmark
  public String concatTwo(final Context context) {
    return context.quick.concat(context.brown);
  }

  @Benchmark
  public String stringBuilderTwo(final Context context) {
    return new StringBuilder().append(context.quick).append(context.brown)
        .toString();
  }

  @Benchmark
  public String stringBuilderInitTwo(final Context context) {
    return new StringBuilder(context.quick).append(context.brown).toString();
  }

  @Benchmark
  public String stringBuffererTwo(final Context context) {
    return new StringBuffer().append(context.quick).append(context.brown)
        .toString();
  }

  @Benchmark
  public String stringConcatOpThree(final Context context) {
    return context.quick + context.brown + context.lazy;
  }

  @Benchmark
  public String concatThree(final Context context) {
    return context.quick.concat(context.brown).concat(context.lazy);
  }

  @Benchmark
  public String stringBuilderThree(final Context context) {
    return new StringBuilder().append(context.quick).append(context.brown)
        .append(context.lazy).toString();
  }

  @Benchmark
  public String stringBuilderInitThree(final Context context) {
    return new StringBuilder(context.quick).append(context.brown)
        .append(context.lazy).toString();
  }

  @Benchmark
  public String stringBuffererThree(final Context context) {
    return new StringBuffer().append(context.quick).append(context.brown)
        .append(context.lazy).toString();
  }

  @Benchmark
  public String joinThree(final Context context) {
    return String.join("", context.quick, context.brown, context.lazy);
  }

  public static void main(String[] args) throws RunnerException, IOException {
    Options opt = new OptionsBuilder()
        .include(".*" + Driver.class.getSimpleName() + ".*").forks(1).build();

    new Runner(opt).run();
  }
}
