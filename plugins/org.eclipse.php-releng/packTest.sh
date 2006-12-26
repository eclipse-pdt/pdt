#!/bin/sh

mkdir ../src

#dos2unix 
dos2unix buildall.sh 

# operate build test target with nightly build 
./buildall.sh -vm /shared/common/ibm-java2-ppc-50 -target buildTest -platform "-Dbaseos=linux -Dbasews=gtk -Dbasearch=ppc" N >../src/log

chmod +x ../src/*

#dos2unix 
dos2unix ../src/runtests

../src/runtests -ws gtk -os linux -arch ppc >>../src/log
