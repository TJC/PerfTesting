import scala.io.Source
import au.com.bytecode.opencsv.CSVReader // from opencsv.sf.net
import java.io.FileReader // for CSVReader
import java.io.BufferedOutputStream
import java.text.DecimalFormat

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

    val output = new BufferedOutputStream(System.out)
    val formatter = new DecimalFormat("0.00")

    def csvparser(filename: String) {
        val reader = new CSVReader(new FileReader(filename))

        // skip header line:
        val header = reader.readNext()

        do_rows(reader)
    }

    def do_rows(reader: CSVReader) {

        reader.readNext() match {
            case null => 
                output.flush
                return

            case columns =>
                output.write(
                  (
                    columns(0) + " is " + formatter.format(
                        columns(1).toDouble * columns(2).toDouble
                    ) + "\n"
                  ).getBytes
                )

                do_rows(reader)
        }

    }

}
