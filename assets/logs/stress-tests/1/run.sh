#!/usr/bin/env bash

test_number="$(( $RANDOM % 10 ))$(( $RANDOM % 10 ))$(( $RANDOM % 10 ))"
for i in {1..100}
do
  python3 script.py -m 5 -t 0 -c 100 -T INFO -C test_${test_number}_c${i}
done
