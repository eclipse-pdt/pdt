#!/bin/sh
# This file is intend to be executed in the build machine 
# remove any leftovers

cd /opt/users/phpBuild/org.eclipse.php-releng/
rm -rf ../src

dos2unix buildall.sh 

# operate build test target with nightly build 
./buildall.sh -vm /shared/common/ibm-java2-ppc-50 -target buildTest -platform "-Dbaseos=linux -Dbasews=gtk -Dbasearch=ppc" N 

# generate changelog
cd changelog
./changelog.sh

cd ../../src

# run Unit test
chmod +x runtests
dos2unix runtests
./runtests -ws gtk -os linux -arch ppc -vm /shared/common/ibm-java2-ppc-50/bin/java -xserver

# update by rss + mail
cd ../rss
./rssProducer

