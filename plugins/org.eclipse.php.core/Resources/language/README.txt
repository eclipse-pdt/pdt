This document explains how to rebuild Language Model files for PHP5.

I. Installing environment:

	1. Create empty PHP project, put this file into it /org.eclipse.php.core/Resources/language/generate.php
	
	2. Export latest PHPDoc into created PHP project using svn export, or using CLI:

		svn export http://svn.php.net/repository/phpdoc/en/trunk php-doc

II. Generating Language Model:

	1. Create launch configuration, provide arguments to the script php-doc, path of documentation directory
		
	   Or using CLI:

		path_to_bin/php -q generate.php php-doc

	3. Look for updated PHP files in php5 directory.
	
	4. Go over II.1-3 using PHP 5.3

