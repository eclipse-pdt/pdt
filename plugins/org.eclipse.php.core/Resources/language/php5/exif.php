<?php

// Start of exif v.1.4 $Id: exif.php,v 1.4 2008/09/04 12:02:41 mspector Exp $

/**
 * Reads the <acronym>EXIF</acronym> headers from <acronym>JPEG</acronym> or <acronym>TIFF</acronym>
 * @link http://php.net/manual/en/function.exif-read-data.php
 * @param filename string
 * @param sections string[optional]
 * @param arrays bool[optional]
 * @param thumbnail bool[optional]
 * @return array 
 */
function exif_read_data ($filename, $sections = null, $arrays = null, $thumbnail = null) {}

/**
 * &Alias; <function>exif_read_data</function>
 * @link http://php.net/manual/en/function.read-exif-data.php
 * @param filename
 * @param sections_needed[optional]
 * @param sub_arrays[optional]
 * @param read_thumbnail[optional]
 */
function read_exif_data ($filename, $sections_needed, $sub_arrays, $read_thumbnail) {}

/**
 * Get the header name for an index
 * @link http://php.net/manual/en/function.exif-tagname.php
 * @param index string
 * @return string the header name, or false if index is
 */
function exif_tagname ($index) {}

/**
 * Retrieve the embedded thumbnail of a TIFF or JPEG image
 * @link http://php.net/manual/en/function.exif-thumbnail.php
 * @param filename string
 * @param width int[optional]
 * @param height int[optional]
 * @param imagetype int[optional]
 * @return string the embedded thumbnail, or false if the image contains no
 */
function exif_thumbnail ($filename, &$width = null, &$height = null, &$imagetype = null) {}

/**
 * Determine the type of an image
 * @link http://php.net/manual/en/function.exif-imagetype.php
 * @param filename string
 * @return int 
 */
function exif_imagetype ($filename) {}

define ('EXIF_USE_MBSTRING', 0);

// End of exif v.1.4 $Id: exif.php,v 1.4 2008/09/04 12:02:41 mspector Exp $
?>
