<?
echo "[BEGIN Search and Replace amp char]\n";
$content = file_get_contents("center.xml");
$newString = str_replace("&amp;", "&", $content);
file_put_contents ("center.xml", $newString);
echo "[END Search and Replace amp char]\n";
?>