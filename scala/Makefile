all: PerfTest.class

PerfTest.class: PerfTest.scala
	scalac -optimise -deprecation -verbose PerfTest.scala

run: PerfTest.class
	scala PerfTest ../input.csv > output.csv
