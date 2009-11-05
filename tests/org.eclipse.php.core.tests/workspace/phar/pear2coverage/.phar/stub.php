<?php
function __autoload($class)
{
    $class = str_replace("PEAR2\Pyrus\Developer\CoverageAnalyzer", "", $class);
    include "phar://" . __FILE__ . "/" . str_replace("\\", "/", $class) . ".php";
}
Phar::webPhar("pear2coverage.phar.php");
echo "This phar is a web application, run within your web browser to use\n";
exit -1;
__HALT_COMPILER(); ?>
