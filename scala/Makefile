all: PerfTest.class

PerfTest.class: PerfTest.scala
	scalac -optimise -deprecation -classpath opencsv-2.2.jar PerfTest.scala

run: PerfTest.class
	scala -classpath opencsv-2.2.jar:. PerfTest ../input.csv > /dev/null
