package io.github.belugabehr.perf.toarray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

public class Driver {
  @State(Scope.Thread)
  public static class Context {
    final Collection<String> arrayList = new ArrayList<>();

    @Setup(Level.Trial)
    public void setupTrial() {
      arrayList.addAll(Collections.nCopies(2048, ""));
    }
  }

  @Benchmark
  public String[] toArrayPreInitArray(final Context context) {
    return context.arrayList.toArray(new String[context.arrayList.size()]);
  }

  @Benchmark
  public String[] toArrayEmptyArray(final Context context) {
    return context.arrayList.toArray(new String[0]);
  }

  @Benchmark
  public String[] toArrayStreamsMethod(final Context context) {
    return context.arrayList.stream().toArray(String[]::new);
  }

  @Benchmark
  public String[] toArrayStreamsLambda(final Context context) {
    return context.arrayList.stream().toArray(n -> new String[n]);
  }

  @Benchmark
  public String[] toArrayGuavaFluent(final Context context) {
    return FluentIterable.from(context.arrayList).toArray(String.class);
  }

  @Benchmark
  public String[] toArrayGuavaIterables(final Context context) {
    return Iterables.toArray(context.arrayList, String.class);
  }

  public static void main(String[] args) throws RunnerException, IOException {
    Options opt = new OptionsBuilder()
        .include(".*" + Driver.class.getSimpleName() + ".*").forks(1).build();

    new Runner(opt).run();
  }
}
