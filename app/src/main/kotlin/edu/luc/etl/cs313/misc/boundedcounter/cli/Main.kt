package edu.luc.etl.cs313.misc.boundedcounter.cli

import java.io.BufferedReader
import java.io.InputStreamReader

import edu.luc.etl.cs313.misc.boundedcounter.model.SimpleBoundedCounter

object Main {

    private val DEFAULT_MIN = 0

    private val DEFAULT_MAX = 5

    /**
     * Command-line interface to bounded counter.

     * @param args lower and upper bounds for counter
     */
    @Throws(Throwable::class)
    @JvmStatic fun main(args: Array<String>) {
        var min = DEFAULT_MIN
        var max = DEFAULT_MAX
        when (args.size) {
            0 -> {
            }
            2 -> {
                min = Integer.parseInt(args[0])
                max = Integer.parseInt(args[1])
            }
            else -> {
                println("usage: Main [min max]")
                System.exit(1)
            }
        }
        Cli(min, max).run()
    }

    private class Cli(private val min: Int, private val max: Int) {

        private val model: BoundedCounter

        init {
            model = SimpleBoundedCounter(min, max)
        }

        @Throws(Throwable::class)
        fun run() {
            val `in` = BufferedReader(InputStreamReader(System.`in`))
            var line: String

            prompt()
            line = `in`.readLine()
            while (line != null) {
                // remove unnecessary spaces
                line = line.trim { it <= ' ' }

                if (!model.isFull && "+" == line) {
                    model.increment()
                } else if (!model.isEmpty && "-" == line) {
                    model.decrement()
                } else if ("quit".startsWith(line)) {
                    return
                }

                prompt()
                line = `in`.readLine()
            }
        }

        private fun prompt() {
            val sb = StringBuilder()
            sb.append("value=")
            sb.append(model.get())
            sb.append(" [")
            sb.append(if (model.isEmpty) "" else "not ")
            sb.append("empty, ")
            sb.append(if (model.isFull) "" else "not ")
            sb.append("full")
            sb.append(", min=")
            sb.append(min)
            sb.append(", max=")
            sb.append(max)
            sb.append("], command: ")
            if (!model.isFull) {
                sb.append("+ | ")
            }
            if (!model.isEmpty) {
                sb.append("- | ")
            }
            sb.append("q[uit] > ")
            print(sb.toString())
        }
    }
}
