#!/bin/sh

usage() {
	echo "usage: changelog [-ant <url to ANT 1.7+ to run build>] [-daysInPast <number of days to include in report>]"
}

# ant used to run the build.  Defaults to ant on system path
ant=ant

#sets number of days to include in log
daysInPast=""

if [ "x$1" == "x" ] ; then
        usage
        exit 0
fi

while [ "$#" -gt 0 ] ; do
        case $1 in
                '-ant')
                        ant=$2;
                        shift 1
                    ;;
                '-daysInPast')
                        daysInPast="-DlogTime=$2";
                        shift 1
                    ;;
		        *)
                    ;;
        esac
        shift 1
done

$ant/bin/ant --noconfig $daysInPast
