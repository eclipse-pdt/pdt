This document explains how to rebuild Language Model files for PHP5 and above

# Installing environment:

* Create empty PHP project, put this file into it /org.eclipse.php.core/Resources/language/generate.php
	
* Download and generate latest phpmanual, follow: https://doc.php.net/guide/local-setup.md#without-docker

# Generating Language Model:

* Run in CLI: `path_to_bin/php -q generate.php php-doc.xml dir`

* Look for updated PHP files in php5 directory.
	
* Go over II.1-3 using PHP 5.3

