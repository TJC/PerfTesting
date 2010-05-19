import scala.io.Source
import au.com.bytecode.opencsv.CSVReader // from opencsv.sf.net
import java.io.FileReader // for CSVReader

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
        val reader = new CSVReader(new FileReader(filename))

        // val input = Source.fromPath(filename).getLines()
        // skip header line:
        val header = reader.readNext()

        do {
            val nextLine = reader.readNext()
            nextLine match {
                case null => return
                case _ => csv_line(nextLine)
            }
        } while (true)
    }

    def csv_line(columns: Array[String]) {

        val name = columns(0)
        val result = columns(1).toDouble * columns(2).toDouble

        printf("%s is %.02f\n", name, result);
    }
}
