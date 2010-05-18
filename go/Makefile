testread: testread.6 csv.6
	6l -o testread testread.6

testread.6: testread.go
	6g testread.go

csv.6: csv.go
	6g csv.go

run: testread
	./testread ../input.csv > output.csv
