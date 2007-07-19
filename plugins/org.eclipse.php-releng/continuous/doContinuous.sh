#!/bin/sh
# This file is intend to be executed in the build machine 

# removes build label of the previous time
rm /opt/users/phpBuild/src/installmanifest.properties
rm -r /opt/users/phpBuild/src/eclipse

cd /opt/users/phpBuild/org.eclipse.php-releng/

# operate build test target with nightly build 
./buildall.sh -vm /shared/common/ibm-java2-ppc-50 -target buildRuntime -platform  "-Dbaseos=linux -Dbasews=gtk -Dbasearch=ppc -DnotUnpack=true" -loggerfilename /opt/users/phpBuild/continuous/mail.properties N 
