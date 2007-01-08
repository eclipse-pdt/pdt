#!/bin/sh

usage() {
	echo "usage: buildAll [-mapVersionTag HEAD|<branch name>] [-cvsuser cvsuser] [-vm <url to java executable to run build>] [-bc <bootclasspath>] [-target <buildall target to execute>] [-buildID <buildID, e.g. 2.1.2>]  [-ftp <userid> <password>] [-rsync <rsync password file>] I|M|N"
}

# tag to use when checking out .map file project
mapVersionTag=HEAD

# default setting for buildType
buildType=""

# default setting for buildID
buildID=""

# default bootclasspath
bootclasspath=""

#sets skip.performance.tests Ant property
skipPerf=""

#sets skip.tests Ant property
skipTest=""

# vm used to run the build.  Defaults to java on system path
vm=java

# target used if not default (to allow run just a portion of buildAll)
target=""

# logger
logger=""

#sets fetchTag="HEAD" for nightly builds if required
tag=""

# FTP user/password, required for Windows to ftp. Without it, no push.
ftpUser=""
ftpPassword=""

# RSYNC Password file location, required for Linux. Without it, no push.
rsyncPWFile=""

# NOTEST flags
notest=""

#platform specific parameters
platformParams=""

# CVS Flags
cvsuser=

if [ "x$1" == "x" ] ; then
        usage
        exit 0
fi

while [ "$#" -gt 0 ] ; do
        case $1 in
                '-mapVersionTag')
                        mapVersionTag=$2;
                        shift 1
                    ;;
                '-vm')
                        vm=$2;
                        shift 1
                    ;;
                '-bc')
                        bootclasspath="-Dbootclasspath=$2";
                        shift 1
                    ;;
                '-target')
                        target="${target} $2";
                        shift 1
                    ;;
                '-buildID')
                        buildID="-DbuildId=$2";
                        shift 1
                    ;;
                '-ftp')
                        ftpUser="-DftpUser=$2";
                        ftpPassword="-DftpPassword=$3"
                        shift 2
                    ;;
                '-rsync')
               		rsyncPWFile="-DrsyncPWFile=$2"
               		shift 1
	        	;;
                '-platform')
               		platformParams="$2"
               		shift 1
	        	;;
                '-loggerfilename')
               		logger="-logger org.apache.tools.ant.listener.MailLogger -DMailLogger.properties.file=$2"
               		shift 1
	        	;;
                '-notest')
                		notest="-Dnotest=true"
                	;;
				'-cvsuser')
						cvsuser="-Dcvsuser=$2";
						export CVS_RSH=ssh
						shift 1
					;;                	
		        *)
    	                buildType=$1
                    ;;
        esac
        shift 1
done

if [[ -z $target && -z $buildType ]] ; then
	usage
	exit 0
fi

buildTypeArg=""
if [[ -n $buildType ]] ; then
	buildTypeArg="-DbuildType=$buildType"
fi

#Set the tag to HEAD for Nightly builds
if [ "$buildType" = "N" ]
then
        tag="-DfetchTag=HEAD"
fi

if [ ! -r ../org.eclipse.releng.basebuilder ]
  then
    cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/eclipse export -r r322_v20061115a org.eclipse.releng.basebuilder
    mv org.eclipse.releng.basebuilder ../
fi

$vm/bin/java -jar ../org.eclipse.releng.basebuilder/startup.jar -application org.eclipse.ant.core.antRunner -f buildAll.xml $target $logger $bootclasspath -DbuildingOSGi=true -DmapVersionTag=$mapVersionTag $cvsuser $buildTypeArg $notest $buildID $rsyncPWFile $ftpUser $ftpPassword $tag $versionQualifier -Djava-home=$vm $platformParams
#rm -rf ../org.eclipse.releng.basebuilder
