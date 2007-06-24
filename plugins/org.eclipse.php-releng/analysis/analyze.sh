#!/bin/sh

export DISPLAY=:3

# remove old
# rm eclipse*
# rm tptp*
# rm emf*
# rm dtp*
# rm wtp*
# rm GEF*
# rm -r eclipse

# fetch from site
# cd /opt/users/phpBuild/analysis
# wget http://download.eclipse.org/tptp/4.4.0/dev/TPTP-4.4.0-200705080100D/tptp.runtime-TPTP-4.4.0-200705080100D.zip
# wget http://download.eclipse.org/eclipse/downloads/drops/S-3.3RC1-200705171700/eclipse-SDK-3.3RC1-linux-gtk-ppc.tar.gz
# wget http://download.eclipse.org/modeling/emf/emf/downloads/drops/2.3.0/S200706130003/emf-sdo-xsd-SDK-2.3.0RC3.zip
# wget http://download.eclipse.org/datatools/downloads/1.5/dtp_1.5RC0.zip
# wget http://download.eclipse.org/tools/gef/downloads/drops/S-3.3M7-200705101223/GEF-runtime-3.3M7.zip
# wget http://download.eclipse.org/webtools/downloads/drops/R2.0/S-2.0RC0-200705171455/wtp-S-2.0RC0-200705171455.zip

# build eclipse environment
# tar -xf eclipse-SDK-3.3RC1-linux-gtk-ppc.tar.gz --overwrite 
# unzip -o tptp.runtime-TPTP-4.4.0-200705080100D.zip
# unzip -o emf-sdo-xsd-SDK-2.3.0RC3.zip
# unzip -o dtp_1.5RC0.zip
# unzip -o GEF-runtime-3.3M7.zip
# unzip -o wtp-S-2.0RC0-200705171455.zip

# copy the analysis plugin
# cp plugin-analysis/* eclipse/plugins/ -r

export DISPLAY=:3

rm -r workspace

mkdir results
mkdir workspace
cd workspace

cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.core
cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.ui
cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.debug.core
cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.debug.ui
cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.debug.daemon
cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.help
cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.server.core
cvs -d :pserver:anonymous@dev.eclipse.org:/cvsroot/tools export -r HEAD org.eclipse.php.server.ui

cd ..

eclipse/eclipse -application org.eclipse.php.analysis.analysisapplication -data /opt/users/phpBuild/analysis/workspace/ -vm /shared/common/ibm-java2-ppc-50/bin -consolelog -dev bin -root $HOME/analysis/workspace -rules /opt/users/phpBuild/analysis/eclipse/rules.dat -output /opt/users/phpBuild/analysis/results/ANALYSIS-php.xml -projectName org.eclipse.php.ui -projectName org.eclipse.php.debug.core -projectName org.eclipse.php.debug.ui -projectName org.eclipse.php.debug.daemon -projectName org.eclipse.php.help -projectName org.eclipse.php.server.core -projectName org.eclipse.php.server.ui -projectName org.eclipse.php.core

cd results
ant

rsync -avz --password-file=$HOME/.pass --stats ANALYSIS-php.html apeled@dev.eclipse.org:/home/data/httpd/download.eclipse.org/tools/pdt/downloads/drops/results/

# change permissions
