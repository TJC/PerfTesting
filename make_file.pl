#!/usr/bin/env perl
use strict;
use warnings;

my @names = qw(
    Alpha Bravo Charlie Delta Echo Foxtrot Golf Hotel
    India Juliet Kilo Lima Mike November Oscar Papa
    Quebec Romeo Sierra Tango Uniform Victor
    Whiskey Xray Yankee Zulu
);

print "name,integer,float\n";
for (1..10000000) {
    my $int = int(rand(99) + 1);
    my $float = rand(1);
    my $name = $names[ int(rand(scalar(@names))) ];
    print join(',', $name, $int, $float) . "\n";
}
