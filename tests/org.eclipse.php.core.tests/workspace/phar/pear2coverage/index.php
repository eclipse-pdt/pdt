<?php
namespace PEAR2\Pyrus\Developer\CoverageAnalyzer {
$class = "PEAR2\Pyrus\Developer\CoverageAnalyzer\Web\View";
var_dump(str_replace("PEAR2\Pyrus\Developer\CoverageAnalyzer", "", $class));
$view = new Web\View;
$rooturl = $_SERVER["REQUEST_URI"];
$controller = new Web\Controller($view, $rooturl);
$controller->route();
}