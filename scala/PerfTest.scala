import scala.io.Source

object PerfTest {
    def main(args: Array[String]) {
        if (args.length < 1) {
            println("Please pass filename as parameter.")
            exit(1)
        }
        val filename = args(0)
        time(csvparser(filename))
    }

    def time(f: => Unit) {
        val t1 = System.currentTimeMillis()
        f
        val t2 = System.currentTimeMillis()
        System.err.println("Routine took: " + (t2 - t1).asInstanceOf[Float] + " msecs")
    }

    def csvparser(filename: String) {
        val input = Source.fromPath(filename).getLines()
        // skip header line:
        val header = input.next

        input.foreach(csv_line)
    }

    def csv_line(line: String) {
        val columns = line.split(",")

        val name = columns(0)
        val result = columns(1).toDouble * columns(2).toDouble

        printf("%s is %.02f\n", name, result);
    }
}
