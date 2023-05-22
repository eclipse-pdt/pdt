<?php

// Start of exif v.8.2.6

/**
 * Get the header name for an index
 * @link http://www.php.net/manual/en/function.exif-tagname.php
 * @param int $index 
 * @return string|false Returns the header name, or false if index is
 * not a defined EXIF tag id.
 */
function exif_tagname (int $index): string|false {}

/**
 * Reads the EXIF headers from an image file
 * @link http://www.php.net/manual/en/function.exif-read-data.php
 * @param resource|string $file 
 * @param string|null $required_sections [optional] 
 * @param bool $as_arrays [optional] 
 * @param bool $read_thumbnail [optional] 
 * @return array|false It returns an associative array where the array indexes are 
 * the header names and the array values are the values associated with 
 * those headers. If no data can be returned, 
 * exif_read_data will return false.
 */
function exif_read_data ($file, ?string $required_sections = null, bool $as_arrays = false, bool $read_thumbnail = false): array|false {}

/**
 * Retrieve the embedded thumbnail of an image
 * @link http://www.php.net/manual/en/function.exif-thumbnail.php
 * @param resource|string $file 
 * @param int $width [optional] 
 * @param int $height [optional] 
 * @param int $image_type [optional] 
 * @return string|false Returns the embedded thumbnail, or false if the image contains no 
 * thumbnail.
 */
function exif_thumbnail ($file, int &$width = null, int &$height = null, int &$image_type = null): string|false {}

/**
 * Determine the type of an image
 * @link http://www.php.net/manual/en/function.exif-imagetype.php
 * @param string $filename 
 * @return int|false When a correct signature is found, the appropriate constant value will be
 * returned otherwise the return value is false. The return value is the
 * same value that getimagesize returns in index 2 but
 * exif_imagetype is much faster.
 * <p>The following constants are defined, and represent possible 
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
 * </table></p>
 * <p>exif_imagetype will emit an E_NOTICE
 * and return false if it is unable to read enough bytes from the file to
 * determine the image type.</p>
 */
function exif_imagetype (string $filename): int|false {}


/**
 * This constant has a value of 1 if the
 * mbstring is enabled, otherwise
 * it has a value of 0.
 * @link http://www.php.net/manual/en/ref.mbstring.php
 * @var int
 */
define ('EXIF_USE_MBSTRING', 1);

// End of exif v.8.2.6
