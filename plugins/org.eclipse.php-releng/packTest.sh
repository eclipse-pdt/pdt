#!/bin/sh

mkdir ../src

#dos2unix 
dos2unix buildall.sh 

# operate build test target with nightly build 
./buildall.sh -vm /shared/common/ibm-java2-ppc-50 -target buildTest -platform "-Dbaseos=linux -Dbasews=gtk -Dbasearch=ppc" N 

cd ../src

chmod +x *

#dos2unix 
dos2unix runtests

./runtests -ws gtk -os linux -arch ppc 

cd ../rss
./rssProducer "Unit test results available to data $(date +%Y%m%d)" "Success Rate: " "Php Build Server"



