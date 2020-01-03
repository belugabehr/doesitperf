package io.github.belugabehr.perf.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Driver {
  @State(Scope.Thread)
  public static class Context {
    final List<String> linkedList = new LinkedList<>();
    final List<String> arrayList = new ArrayList<>();

    @Setup(Level.Trial)
    public void setupTrial() {
      linkedList.addAll(Collections.nCopies(2048, ""));
      arrayList.addAll(Collections.nCopies(2048, ""));
    }
  }

  @Benchmark
  @Measurement(batchSize = 2048)
  public boolean arrayListAddDefaultSize() {
    List<String> list = new ArrayList<>();
    boolean result = false;
    for (int i = 0; i < 2048; i++) {
      result |= list.add("");
    }
    return result;
  }

  @Benchmark
  @Measurement(batchSize = 2048)
  public boolean arrayListAddPreSized() {
    List<String> list = new ArrayList<>(1024);
    boolean result = false;
    for (int i = 0; i < 2048; i++) {
      result |= list.add("");
    }
    return result;
  }

  @Benchmark
  @Measurement(batchSize = 2048)
  public boolean linkedListAdd() {
    List<String> list = new LinkedList<>();
    boolean result = false;
    for (int i = 0; i < 2048; i++) {
      result |= list.add("");
    }
    return result;
  }

  @Benchmark
  public void arrayListIterateForLoop(final Context context, final Blackhole blackHole) {
    for (int i = 0; i < context.arrayList.size(); i++) {
      blackHole.consume(context.arrayList.get(i));
    }
  }

  @Benchmark
  public void arrayListIterateEnhancedForLoop(final Context context, final Blackhole blackHole) {
    for (String s : context.arrayList) {
      blackHole.consume(s);
    }
  }

  @Benchmark
  public void arrayListIterateIterator(final Context context, final Blackhole blackHole) {
    Iterator<String> iter = context.arrayList.iterator();
    while (iter.hasNext()) {
      blackHole.consume(iter.next());
    }
  }

  @Benchmark
  public void linkedListIterateForLoop(final Context context, final Blackhole blackHole) {
    for (int i = 0; i < context.linkedList.size(); i++) {
      blackHole.consume(context.linkedList.get(i));
    }
  }

  @Benchmark
  public void linkedListIterateEnhancedForLoop(final Context context, final Blackhole blackHole) {
    for (String s : context.linkedList) {
      blackHole.consume(s);
    }
  }

  @Benchmark
  public void linkedListIterateIterator(final Context context, final Blackhole blackHole) {
    Iterator<String> iter = context.linkedList.iterator();
    while (iter.hasNext()) {
      blackHole.consume(iter.next());
    }
  }

  public static void main(String[] args) throws RunnerException, IOException {
    Options opt = new OptionsBuilder()
        .include(".*" + Driver.class.getSimpleName() + ".*").forks(1).build();

    new Runner(opt).run();
  }
}
