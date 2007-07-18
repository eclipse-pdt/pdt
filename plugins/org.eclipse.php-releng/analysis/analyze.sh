#!/bin/sh

# find bugs report
./findbugs.sh
rsync -avz --password-file=$HOME/.pass --stats FINDBUGS-php.html apeled@dev.eclipse.org:/home/data/httpd/download.eclipse.org/tools/pdt/downloads/drops/results/

# analysis tool here
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
