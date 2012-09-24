package edu.luc.etl.cs313.android.clickcounter.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.luc.etl.cs313.android.clickcounter.model.Counter;
import edu.luc.etl.cs313.android.clickcounter.model.DefaultBoundedCounter;

public class Main {

  private final static int DEFAULT_MIN = 0;

  private final static int DEFAULT_MAX = 10;

  /**
   * Command-line interface to bounded counter.
   * @param args lower and upper bounds for counter
   */
  public static void main(final String[] args) throws Throwable {
    int min = DEFAULT_MIN;
    int max = DEFAULT_MAX;
    if (args.length == 2) {
      min = Integer.parseInt(args[0]);
      max = Integer.parseInt(args[1]);
    } else if (args.length > 0) {
      System.out.println("usage: Main [min max]");
      System.exit(1);
    }
    new Cli(min, max).run();
  }

  private static class Cli {

    private final int min, max;

    private final Counter model;

    public Cli(final int min, final int max) {
      this.min = min;
      this.max = max;
      model = new DefaultBoundedCounter(min, max);
    }

    public void run() throws Throwable {
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String line = null;

      prompt();
      while ((line = in.readLine()) != null) {
        // remove unnecessary spaces
        line = line.trim();

        if (! model.isFull() && "+".equals(line)) {
          model.increment();
        } else if (! model.isEmpty() && "-".equals(line)) {
          model.decrement();
        } else if ("0".equals(line)) {
          model.reset();
        } else if ("quit".startsWith(line)) {
          return;
        }

        prompt();
      }
    }

    private void prompt() {
      final StringBuilder sb = new StringBuilder();
      sb.append("value=");
      sb.append(model.get());
      sb.append(" [");
      sb.append(model.isEmpty() ? "" : "not ");
      sb.append("empty, ");
      sb.append(model.isFull() ? "" : "not ");
      sb.append("full");
      sb.append(", min=");
      sb.append(min);
      sb.append(", max=");
      sb.append(max);
      sb.append("], command: ");
      if (! model.isFull()) { sb.append("+ | "); }
      if (! model.isEmpty()) { sb.append("- | "); }
      sb.append("0 | q[uit] > ");
      System.out.print(sb.toString());
    }
  }
}
