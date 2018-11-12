<?php

// Start of exif v.7.3.0

/**
 * Reads the EXIF headers from an image file
 * @link http://www.php.net/manual/en/function.exif-read-data.php
 * @param mixed $stream The location of the image file. This can either be a path to the file
 * (stream wrappers are also supported as usual)
 * or a stream resource.
 * @param string $sections [optional] Is a comma separated list of sections that need to be present in file 
 * to produce a result array. If none of the requested 
 * sections could be found the return value is false.
 * <table>
 * <tr valign="top">
 * <td>FILE</td>
 * <td>FileName, FileSize, FileDateTime, SectionsFound</td>
 * </tr>
 * <tr valign="top">
 * <td>COMPUTED</td>
 * <td>
 * html, Width, Height, IsColor, and more if available. Height and 
 * Width are computed the same way getimagesize
 * does so their values must not be part of any header returned. 
 * Also, html is a height/width text string to be used inside normal 
 * HTML.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>ANY_TAG</td>
 * <td>Any information that has a Tag e.g. IFD0, EXIF, ...</td>
 * </tr>
 * <tr valign="top">
 * <td>IFD0</td>
 * <td>
 * All tagged data of IFD0. In normal imagefiles this contains
 * image size and so forth.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>THUMBNAIL</td>
 * <td>
 * A file is supposed to contain a thumbnail if it has a second IFD.
 * All tagged information about the embedded thumbnail is stored in 
 * this section.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>COMMENT</td>
 * <td>Comment headers of JPEG images.</td>
 * </tr>
 * <tr valign="top">
 * <td>EXIF</td>
 * <td>
 * The EXIF section is a sub section of IFD0. It contains
 * more detailed information about an image. Most of these entries
 * are digital camera related.
 * </td>
 * </tr>
 * </table>
 * @param bool $arrays [optional] Specifies whether or not each section becomes an array. The 
 * sections COMPUTED,
 * THUMBNAIL, and COMMENT 
 * always become arrays as they may contain values whose names conflict
 * with other sections.
 * @param bool $thumbnail [optional] When set to true the thumbnail itself is read. Otherwise, only the
 * tagged data is read.
 * @return array It returns an associative array where the array indexes are 
 * the header names and the array values are the values associated with 
 * those headers. If no data can be returned, 
 * exif_read_data will return false.
 */
function exif_read_data ($stream, string $sections = null, bool $arrays = null, bool $thumbnail = null) {}

/**
 * Alias: exif_read_data
 * @link http://www.php.net/manual/en/function.read-exif-data.php
 * @param mixed $filename
 * @param mixed $sections_needed [optional]
 * @param mixed $sub_arrays [optional]
 * @param mixed $read_thumbnail [optional]
 * @deprecated 
 */
function read_exif_data ($filename, $sections_needed = null, $sub_arrays = null, $read_thumbnail = null) {}

/**
 * Get the header name for an index
 * @link http://www.php.net/manual/en/function.exif-tagname.php
 * @param int $index The Tag ID for which a Tag Name will be looked up.
 * @return string the header name, or false if index is
 * not a defined EXIF tag id.
 */
function exif_tagname (int $index) {}

/**
 * Retrieve the embedded thumbnail of an image
 * @link http://www.php.net/manual/en/function.exif-thumbnail.php
 * @param mixed $stream The location of the image file. This can either be a path to the file 
 * or a stream resource.
 * @param int $width [optional] The return width of the returned thumbnail.
 * @param int $height [optional] The returned height of the returned thumbnail.
 * @param int $imagetype [optional] The returned image type of the returned thumbnail. This is either
 * TIFF or JPEG.
 * @return string the embedded thumbnail, or false if the image contains no 
 * thumbnail.
 */
function exif_thumbnail ($stream, int &$width = null, int &$height = null, int &$imagetype = null) {}

/**
 * Determine the type of an image
 * @link http://www.php.net/manual/en/function.exif-imagetype.php
 * @param string $filename The image being checked.
 * @return int When a correct signature is found, the appropriate constant value will be
 * returned otherwise the return value is false. The return value is the
 * same value that getimagesize returns in index 2 but
 * exif_imagetype is much faster.
 * <p>
 * exif_imagetype will emit an E_NOTICE
 * and return false if it is unable to read enough bytes from the file to
 * determine the image type.
 * </p>
 */
function exif_imagetype (string $filename) {}


/**
 * This constant have a value of 1 if the 
 * mbstring is enabled, otherwise 
 * the value is 0.
 * @link http://www.php.net/manual/en/exif.constants.php
 */
define ('EXIF_USE_MBSTRING', 1);

// End of exif v.7.3.0
