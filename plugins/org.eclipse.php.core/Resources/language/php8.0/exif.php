<?php

// Start of exif v.8.0.28

/**
 * Get the header name for an index
 * @link http://www.php.net/manual/en/function.exif-tagname.php
 * @param int $index The Tag ID for which a Tag Name will be looked up.
 * @return mixed the header name, or false if index is
 * not a defined EXIF tag id.
 */
function exif_tagname (int $index): string|false {}

/**
 * Reads the EXIF headers from an image file
 * @link http://www.php.net/manual/en/function.exif-read-data.php
 * @param mixed $file The location of the image file. This can either be a path to the file
 * (stream wrappers are also supported as usual)
 * or a stream resource.
 * @param mixed $required_sections [optional] Is a comma separated list of sections that need to be present in file 
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
 * @param bool $as_arrays [optional] Specifies whether or not each section becomes an array. The 
 * required_sections COMPUTED,
 * THUMBNAIL, and COMMENT 
 * always become arrays as they may contain values whose names conflict
 * with other sections.
 * @param bool $read_thumbnail [optional] When set to true the thumbnail itself is read. Otherwise, only the
 * tagged data is read.
 * @return mixed It returns an associative array where the array indexes are 
 * the header names and the array values are the values associated with 
 * those headers. If no data can be returned, 
 * exif_read_data will return false.
 */
function exif_read_data ($file, $required_sections = null, bool $as_arrays = null, bool $read_thumbnail = null): array|false {}

/**
 * Retrieve the embedded thumbnail of an image
 * @link http://www.php.net/manual/en/function.exif-thumbnail.php
 * @param mixed $file The location of the image file. This can either be a path to the file 
 * or a stream resource.
 * @param int $width [optional] The return width of the returned thumbnail.
 * @param int $height [optional] The returned height of the returned thumbnail.
 * @param int $image_type [optional] The returned image type of the returned thumbnail. This is either
 * TIFF or JPEG.
 * @return mixed the embedded thumbnail, or false if the image contains no 
 * thumbnail.
 */
function exif_thumbnail ($file, int &$width = null, int &$height = null, int &$image_type = null): string|false {}

/**
 * Determine the type of an image
 * @link http://www.php.net/manual/en/function.exif-imagetype.php
 * @param string $filename The image being checked.
 * @return mixed When a correct signature is found, the appropriate constant value will be
 * returned otherwise the return value is false. The return value is the
 * same value that getimagesize returns in index 2 but
 * exif_imagetype is much faster.
 * <p>
 * The following constants are defined, and represent possible 
 * exif_imagetype return values:
 * <table>
 * Imagetype Constants
 * <table>
 * <tr valign="top">
 * <td>Value</td>
 * <td>Constant</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>IMAGETYPE_GIF</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>IMAGETYPE_JPEG</td>
 * </tr>
 * <tr valign="top">
 * <td>3</td>
 * <td>IMAGETYPE_PNG</td>
 * </tr>
 * <tr valign="top">
 * <td>4</td>
 * <td>IMAGETYPE_SWF</td>
 * </tr> 
 * <tr valign="top">
 * <td>5</td>
 * <td>IMAGETYPE_PSD</td>
 * </tr> 
 * <tr valign="top">
 * <td>6</td>
 * <td>IMAGETYPE_BMP</td>
 * </tr> 
 * <tr valign="top">
 * <td>7</td>
 * <td>IMAGETYPE_TIFF_II (intel byte order)</td>
 * </tr> 
 * <tr valign="top">
 * <td>8</td>
 * <td>
 * IMAGETYPE_TIFF_MM (motorola byte order)
 * </td>
 * </tr> 
 * <tr valign="top">
 * <td>9</td>
 * <td>IMAGETYPE_JPC</td>
 * </tr> 
 * <tr valign="top">
 * <td>10</td>
 * <td>IMAGETYPE_JP2</td>
 * </tr> 
 * <tr valign="top">
 * <td>11</td>
 * <td>IMAGETYPE_JPX</td>
 * </tr>
 * <tr valign="top">
 * <td>12</td>
 * <td>IMAGETYPE_JB2</td>
 * </tr>
 * <tr valign="top">
 * <td>13</td>
 * <td>IMAGETYPE_SWC</td>
 * </tr>
 * <tr valign="top">
 * <td>14</td>
 * <td>IMAGETYPE_IFF</td>
 * </tr>
 * <tr valign="top">
 * <td>15</td>
 * <td>IMAGETYPE_WBMP</td>
 * </tr>
 * <tr valign="top">
 * <td>16</td>
 * <td>IMAGETYPE_XBM</td>
 * </tr>
 * <tr valign="top">
 * <td>17</td>
 * <td>IMAGETYPE_ICO</td>
 * </tr>
 * <tr valign="top">
 * <td>18</td>
 * <td>IMAGETYPE_WEBP</td>
 * </tr>
 * </table> 
 * </table> 
 * </p>
 * <p>
 * exif_imagetype will emit an E_NOTICE
 * and return false if it is unable to read enough bytes from the file to
 * determine the image type.
 * </p>
 */
function exif_imagetype (string $filename): int|false {}


/**
 * This constant has a value of 1 if the
 * mbstring is enabled, otherwise
 * it has a value of 0.
 * @link http://www.php.net/manual/en/exif.constants.php
 */
define ('EXIF_USE_MBSTRING', 1);

// End of exif v.8.0.28
