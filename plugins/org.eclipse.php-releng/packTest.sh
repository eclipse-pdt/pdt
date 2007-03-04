#!/bin/sh
# This file is intend to be executed in the build machine 
# remove any leftovers

cd /opt/users/phpBuild/org.eclipse.php-releng/
rm -rf ../src

#dos2unix 
dos2unix buildall.sh 

# operate build test target with nightly build 
./buildall.sh -vm /shared/common/ibm-java2-ppc-50 -target buildTest -platform "-Dbaseos=linux -Dbasews=gtk -Dbasearch=ppc" N 

cd ../src

chmod +x *

#dos2unix 
dos2unix runtests

# runtests
./runtests -ws gtk -os linux -arch ppc -xserver

# update by rss + mail
cd ../rss
./rssProducer



