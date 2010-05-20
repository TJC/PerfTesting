#!/usr/bin/env perl
use strict;
use warnings;
use Text::CSV;
use feature 'say';

my @ops = ('+', '-', ',', '&', '*', '"');

my @names = qw(
    Alpha Bravo Charlie Delta Echo Foxtrot Golf Hotel
    India Juliet Kilo Lima Mike November Oscar Papa
    Quebec Romeo Sierra Tango Uniform Victor
    Whiskey Xray Yankee Zulu
);

my $csv = Text::CSV->new;
$csv->combine(qw(name integer float));
say $csv->string;


for (1..100000) {
    my $int = int(rand(99) + 1);
    my $float = rand(1);
    my $op = $ops[int(rand(scalar(@ops)))];
    my $name = join($op, @names[
            int(rand(scalar(@names))),
            int(rand(scalar(@names)))
        ]);

    $csv->combine($name, $int, $float);
    say $csv->string;
}
