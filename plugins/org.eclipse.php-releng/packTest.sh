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
./rssProducer "Unit test results are available for $(date +%Y%m%d)" "" "Php Build Server"



