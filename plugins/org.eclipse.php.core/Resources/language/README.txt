This document explains how to rebuild Language Model files for PHP5.

I. Installing environment:

	1. Download latest PHPDoc:

		cvs -d :pserver:cvsread@cvs.php.net:/repository checkout phpdoc

	2. Install latest Zend CE containing all extensions and PHP-cli binary:

		apt-get install zend-ce php5-extra-extensions-zend-ce php-dev-zend-ce


II. Generating Language Model:

	1. Update PHPDoc checkout:

		cvs -d :pserver:cvsread@cvs.php.net:/repository update phpdoc

	2. Run:

		/usr/local/zend/bin/php -q generate.php phpdoc

	3. Look for updated PHP files in php5/ directory.

