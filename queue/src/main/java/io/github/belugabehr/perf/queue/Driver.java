package io.github.belugabehr.perf.queue;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Driver {
  @State(Scope.Thread)
  public static class Context {
    final Queue<String> arrayQueue = new ArrayDeque<>();
    final Queue<String> linkedQueue = new LinkedList<>();
    final List<String> arrayList = new ArrayList<>();

    @Setup(Level.Trial)
    public void setupTrial() {
      arrayQueue.addAll(Collections.nCopies(2048, ""));
      linkedQueue.addAll(Collections.nCopies(2048, ""));
      arrayList.addAll(Collections.nCopies(2048, ""));
    }
  }

  @Benchmark
  public boolean arrayQueueRemoveHead(final Context context) {
    return context.arrayQueue.add(context.arrayQueue.remove());
  }

  @Benchmark
  public boolean linkedQueueRemoveHead(final Context context) {
    return context.linkedQueue.add(context.linkedQueue.remove());
  }

  @Benchmark
  public boolean arrayListRemoveHead(final Context context) {
    return context.arrayList.add(context.arrayList.remove(0));
  }

  public static void main(String[] args) throws RunnerException, IOException {
    Options opt = new OptionsBuilder()
        .include(".*" + Driver.class.getSimpleName() + ".*").forks(1).build();

    new Runner(opt).run();
  }
}
