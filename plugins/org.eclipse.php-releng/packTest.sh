#!/bin/sh

# define os, arch, ws for the packaging tool
export BASEOS="linx";
export BASEARCH="ppc";
export BASEWS="gtk";

# operate build test target with nightly build 
./buildall.sh -vm /shared/common/ibm-java2-ppc-50 -target buildTest N 
