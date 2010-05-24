#!/usr/bin/env perl
use strict;
use warnings;
use autodie;
use Text::CSV_XS;
use IO::File;
use IO::Handle;
use Benchmark ':hireswallclock';

my $filename = $ARGV[0];
unless ($filename and -f $filename) {
    die("Please pass the filename on the command line.\n");
}

my $result = timeit(1, sub {

    my $csv = Text::CSV_XS->new;
    my $fh = IO::File->new("<$filename");

    my $header = $csv->getline($fh);
    # $csv->column_names(@$header);

    while (not $csv->eof) {
        my $cols = $csv->getline($fh);
        next unless $cols;
        printf('%s is %.02f%s',
            $cols->[0],
            ($cols->[1] * $cols->[2]),
            "\n"
        );
    }

    $fh->close;
});

warn "Code took " . timestr($result) . "\n";
