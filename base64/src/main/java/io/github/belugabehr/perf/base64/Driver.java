package io.github.belugabehr.perf.base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

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
    final byte[] buf = new byte[1024];

    @Setup(Level.Trial)
    public void setupTrial() {
      ThreadLocalRandom.current().nextBytes(buf);
    }
  }

  @Benchmark
  public String base64JDK(final Context context) {
    return Base64.getEncoder().encodeToString(context.buf);
  }

  @Benchmark
  public String base64ApacheCommons(final Context context) {
    return new String(
        org.apache.commons.codec.binary.Base64.encodeBase64(context.buf),
        StandardCharsets.UTF_8);
  }

  public static void main(String[] args) throws RunnerException, IOException {
    Options opt = new OptionsBuilder()
        .include(".*" + Driver.class.getSimpleName() + ".*").forks(1).build();

    new Runner(opt).run();
  }
}
