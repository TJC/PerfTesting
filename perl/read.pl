#!/usr/bin/env perl
use strict;
use warnings;
use autodie;
use Text::CSV;
use IO::File;
use Benchmark ':hireswallclock';

my $result = timeit(1, sub {
    my $filename = $ARGV[0];
    unless ($filename and -f $filename) {
        die("Please pass the filename on the command line.\n");
    }

    my $csv = Text::CSV->new;
    my $fh = IO::File->new("<$filename");

    my $header = $csv->getline($fh);
    $csv->column_names(@$header);

    while (not $csv->eof) {
        my $cols = $csv->getline_hr($fh);
        next unless $cols;
        printf('%s is %.02f%s',
            $cols->{name},
            ($cols->{'integer'} * $cols->{'float'}),
            "\n"
        );
    }

    $fh->close;
});

warn "Code took " . timestr($result) . "\n";
