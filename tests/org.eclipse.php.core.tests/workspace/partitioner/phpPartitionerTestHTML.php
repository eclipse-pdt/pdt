<html>
<head>
<title><? print(Date("1 F d, Y")); ?></title>
</head>
<body>
<?php
	/** PHP_Doc
	*/
	print("Running test");//PHP_Single_Comment
?>
<?php
// PHP_Single_Comment ?> <? echo "non comment";
echo "Quote";
//PHP_Single_Comment
/*
 * PHP_Multi_Comment
 */
/**
 * PHP_Doc
 */

?>
<a href="#<?php echo $num;?>"></a>
</body>
</html>
