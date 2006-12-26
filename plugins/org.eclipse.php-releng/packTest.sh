#!/bin/sh

# creates build directory
md ../src

# operate build test target with nightly build 
./buildall.sh -vm /shared/common/ibm-java2-ppc-50 -target buildTest -platform "-Dbaseos=linux -Dbasews=gtk -Dbasearch=ppc" N >../src/log

chmod +x ../src/*

../src/runtests -ws gtk -os linux -arch ppc >>../src/log

