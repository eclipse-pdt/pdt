How to create project and center in the RSSLib:

ant_path=`ls $HOME/src/eclipse/plugins/org.apache.ant_* -d`
export JAVA_HOME="/shared/common/ibm-java2-ppc-50/"
export ANT_HOME="$ant_path"

# short summary
ant --noconfig -lib ext-lib -f rss.xml createProject 
ant --noconfig -lib ext-lib -f rss.xml createCenter 

