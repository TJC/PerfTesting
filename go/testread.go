package main

import (
    "flag"
    "fmt"
    "os"
    "bufio"
    "strconv"
    "time"
    "./csv"
)

func main() {
    if (flag.NArg() < 1) {
        fmt.Printf("Please pass filename on command line.\n")
        os.Exit(1)
    }

    time1 := time.Nanoseconds()

    process_file(flag.Arg(0))

    time2 := time.Nanoseconds()

    elapsed := (time2 - time1) / 1000000

    fmt.Fprintf(os.Stderr, "Elapsed time: %d ms\n", elapsed)
}

func process_file(filename string) {

    filehandle, e := os.Open(filename, os.O_RDONLY, 0)
    if e != nil {
        panic(e)
    }
    defer filehandle.Close()
    inputfh := bufio.NewReader(filehandle)

    csvreader := csv.NewReader(inputfh)

    // Skip first line:
    csvreader.ReadRow()

    for {
        switch row, err := csvreader.ReadRow(); true {
            case err == nil:
                do_line(row)
            case err.String() == "EOF":
                return
            case err != nil:
                panic(err.String())
        }
    }

}

func do_line(row []string) {
    name := row[0]
    col1, _ := strconv.Atof32(row[1])
    col2, _ := strconv.Atof32(row[2])

    result := col1 * col2

    fmt.Printf("%s is %.02f\n", name, result)
}
