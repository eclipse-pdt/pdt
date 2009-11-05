<?php
//include 'phar://dd.phar';
try {
    // open an existing phar
    $p = new Phar('dd.phar', 0);
    // Phar extends SPL's DirectoryIterator class
    foreach (new RecursiveIteratorIterator($p) as $file) {
        // $file is a PharFileInfo class, and inherits from SplFileInfo
        echo $file->getFileName() . "\n";
        echo file_get_contents($file->getPathName()) . "\n"; // display contents;

	if (Phar::canCompress(Phar::GZ)) {
            $p[$file->getFileName()]->compress(Phar::GZ);
        }
    }
} catch (Exception $e) {
    echo 'Could not open Phar: ', $e;
}
echo("test");
// TODO: Is this right??
//??%?(
$Cities = array (
1 => 'London',
2 => 'Toronto',
3 => 'Warsaw'
);

class MyClass{
   public static $var;
   public static function f() {}
}

//MyClass::