#!/bin/sh

ant_path=`ls $HOME/src/eclipse/plugins/org.apache.ant_* -d`
export JAVA_HOME="/shared/common/ibm-java2-ppc-50/"
export ANT_HOME="$ant_path"
export FINDBUGS_HOME="/opt/users/phpBuild/analysis/findbugs/"

ant --noconfig -lib /opt/users/phpBuild/analysis/findbugs/lib 


