#!/bin/sh

usage() {
	echo "USAGE: $0 [-b <branch tag, default: HEAD>]"
}

logdir=~/.changelog/
if [ ! -d $logdir ]; then
	mkdir $logdir
fi

branch=HEAD

while [ "$#" -gt 0 ] ; do
        case $1 in
                '-b')
                        branch=$2
                        shift 1
                    ;;
		*)
			usage
			exit 1
                    ;;
        esac
        shift 1
done

current=`date +"%d %b %Y"`

# Retrieve last build date:
last=`cat $logdir/$branch 2>/dev/null`

if [ -z "$last" ]; then
	last=`date -d "7 days ago" +"%d %b %Y"`
fi

/shared/common/ibm-java2-ppc-50/bin/java -jar ~/org.eclipse.releng.basebuilder/plugins/org.eclipse.equinox.launcher.jar -application org.eclipse.ant.core.antRunner -f build.xml -Dstart="$last" -Dend="$current"

if [ $? -ne 0 ]; then
	echo ChangeLog build failed
	exit 1
fi

# Save current build date:
echo $current > $logdir/$branch

