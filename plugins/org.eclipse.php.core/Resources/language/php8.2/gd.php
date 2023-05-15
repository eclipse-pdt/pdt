<?php

// Start of gd v.8.2.6

/**
 * A fully opaque class which replaces gd resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.gdimage.php
 */
final class GdImage  {
}

/**
 * A fully opaque class which replaces gd font resources as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.gdfont.php
 */
final class GdFont  {
}

/**
 * Retrieve information about the currently installed GD library
 * @link http://www.php.net/manual/en/function.gd-info.php
 * @return array an associative array.
 * <p>
 * <table>
 * Elements of array returned by gd_info
 * <table>
 * <tr valign="top">
 * <td>Attribute</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>GD Version</td>
 * <td>string value describing the installed
 * libgd version.</td>
 * </tr>
 * <tr valign="top">
 * <td>FreeType Support</td>
 * <td>bool value. true
 * if FreeType Support is installed.</td>
 * </tr>
 * <tr valign="top">
 * <td>FreeType Linkage</td>
 * <td>string value describing the way in which
 * FreeType was linked. Expected values are: 'with freetype',
 * 'with TTF library', and 'with unknown library'. This element will
 * only be defined if FreeType Support evaluated to
 * true.</td>
 * </tr>
 * <tr valign="top">
 * <td>GIF Read Support</td>
 * <td>bool value. true
 * if support for reading GIF
 * images is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>GIF Create Support</td>
 * <td>bool value. true
 * if support for creating GIF
 * images is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>JPEG Support</td>
 * <td>bool value. true
 * if JPEG support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>PNG Support</td>
 * <td>bool value. true
 * if PNG support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>WBMP Support</td>
 * <td>bool value. true
 * if WBMP support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>XBM Support</td>
 * <td>bool value. true
 * if XBM support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>WebP Support</td>
 * <td>bool value. true
 * if WebP support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>AVIF Support</td>
 * <td>bool value. true
 * if AVIF support is included.
 * Available as of PHP 8.1.0.</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 */
function gd_info (): array {}

/**
 * Load a new font
 * @link http://www.php.net/manual/en/function.imageloadfont.php
 * @param string $filename <p>
 * The font file format is currently binary and architecture
 * dependent. This means you should generate the font files on the
 * same type of CPU as the machine you are running PHP on.
 * </p>
 * <p>
 * <table>
 * Font file format
 * <table>
 * <tr valign="top">
 * <td>byte position</td>
 * <td>C data type</td>
 * <td>description</td>
 * </tr>
 * <tr valign="top">
 * <td>byte 0-3</td>
 * <td>int</td>
 * <td>number of characters in the font</td>
 * </tr>
 * <tr valign="top">
 * <td>byte 4-7</td>
 * <td>int</td>
 * <td>
 * value of first character in the font (often 32 for space)
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>byte 8-11</td>
 * <td>int</td>
 * <td>pixel width of each character</td>
 * </tr>
 * <tr valign="top">
 * <td>byte 12-15</td>
 * <td>int</td>
 * <td>pixel height of each character</td>
 * </tr>
 * <tr valign="top">
 * <td>byte 16-</td>
 * <td>char</td>
 * <td>
 * array with character data, one byte per pixel in each
 * character, for a total of (nchars&#42;width&#42;height) bytes.
 * </td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * @return mixed an GdFont instance, or false on failure.
 */
function imageloadfont (string $filename): GdFont|false {}

/**
 * Set the style for line drawing
 * @link http://www.php.net/manual/en/function.imagesetstyle.php
 * @param GdImage $image 
 * @param array $style An array of pixel colors. You can use the 
 * IMG_COLOR_TRANSPARENT constant to add a 
 * transparent pixel.
 * Note that style must not be an empty array.
 * @return bool true on success or false on failure
 */
function imagesetstyle (GdImage $image, array $style): bool {}

/**
 * Create a new true color image
 * @link http://www.php.net/manual/en/function.imagecreatetruecolor.php
 * @param int $width Image width.
 * @param int $height Image height.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatetruecolor (int $width, int $height): GdImage|false {}

/**
 * Finds whether an image is a truecolor image
 * @link http://www.php.net/manual/en/function.imageistruecolor.php
 * @param GdImage $image 
 * @return bool true if the image is truecolor, false
 * otherwise.
 */
function imageistruecolor (GdImage $image): bool {}

/**
 * Convert a true color image to a palette image
 * @link http://www.php.net/manual/en/function.imagetruecolortopalette.php
 * @param GdImage $image 
 * @param bool $dither Indicates if the image should be dithered - if it is true then
 * dithering will be used which will result in a more speckled image but
 * with better color approximation.
 * @param int $num_colors Sets the maximum number of colors that should be retained in the palette.
 * @return bool true on success or false on failure
 */
function imagetruecolortopalette (GdImage $image, bool $dither, int $num_colors): bool {}

/**
 * Converts a palette based image to true color
 * @link http://www.php.net/manual/en/function.imagepalettetotruecolor.php
 * @param GdImage $image 
 * @return bool true if the convertion was complete, or if the source image already 
 * is a true color image, otherwise false is returned.
 */
function imagepalettetotruecolor (GdImage $image): bool {}

/**
 * Makes the colors of the palette version of an image more closely match the true color version
 * @link http://www.php.net/manual/en/function.imagecolormatch.php
 * @param GdImage $image1 A truecolor image object.
 * @param GdImage $image2 A palette image object pointing to an image that has the same
 * size as image1.
 * @return bool true on success or false on failure
 */
function imagecolormatch (GdImage $image1, GdImage $image2): bool {}

/**
 * Set the thickness for line drawing
 * @link http://www.php.net/manual/en/function.imagesetthickness.php
 * @param GdImage $image 
 * @param int $thickness Thickness, in pixels.
 * @return bool true on success or false on failure
 */
function imagesetthickness (GdImage $image, int $thickness): bool {}

/**
 * Draw a filled ellipse
 * @link http://www.php.net/manual/en/function.imagefilledellipse.php
 * @param GdImage $image 
 * @param int $center_x x-coordinate of the center.
 * @param int $center_y y-coordinate of the center.
 * @param int $width The ellipse width.
 * @param int $height The ellipse height.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilledellipse (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $color): bool {}

/**
 * Draw a partial arc and fill it
 * @link http://www.php.net/manual/en/function.imagefilledarc.php
 * @param GdImage $image 
 * @param int $center_x x-coordinate of the center.
 * @param int $center_y y-coordinate of the center.
 * @param int $width The arc width.
 * @param int $height The arc height.
 * @param int $start_angle The arc start angle, in degrees.
 * @param int $end_angle The arc end angle, in degrees.
 * 0° is located at the three-o'clock position, and the arc is drawn
 * clockwise.
 * @param int $color gd.identifier.color
 * @param int $style <p>
 * A bitwise OR of the following possibilities:
 * <p>
 * <br>IMG_ARC_PIE
 * <br>IMG_ARC_CHORD
 * <br>IMG_ARC_NOFILL
 * <br>IMG_ARC_EDGED
 * </p>
 * IMG_ARC_PIE and IMG_ARC_CHORD are
 * mutually exclusive; IMG_ARC_CHORD just
 * connects the starting and ending angles with a straight line, while
 * IMG_ARC_PIE produces a rounded edge.
 * IMG_ARC_NOFILL indicates that the arc
 * or chord should be outlined, not filled. IMG_ARC_EDGED,
 * used together with IMG_ARC_NOFILL, indicates that the
 * beginning and ending angles should be connected to the center - this is a
 * good way to outline (rather than fill) a 'pie slice'.
 * </p>
 * @return bool true on success or false on failure
 */
function imagefilledarc (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $start_angle, int $end_angle, int $color, int $style): bool {}

/**
 * Set the blending mode for an image
 * @link http://www.php.net/manual/en/function.imagealphablending.php
 * @param GdImage $image 
 * @param bool $enable Whether to enable the blending mode or not. On true color images 
 * the default value is true otherwise the default value is false
 * @return bool true on success or false on failure
 */
function imagealphablending (GdImage $image, bool $enable): bool {}

/**
 * Whether to retain full alpha channel information when saving images
 * @link http://www.php.net/manual/en/function.imagesavealpha.php
 * @param GdImage $image 
 * @param bool $enable Whether to save the alpha channel or not. Defaults to false.
 * @return bool true on success or false on failure
 */
function imagesavealpha (GdImage $image, bool $enable): bool {}

/**
 * Set the alpha blending flag to use layering effects
 * @link http://www.php.net/manual/en/function.imagelayereffect.php
 * @param GdImage $image 
 * @param int $effect <p>
 * One of the following constants:
 * <p>
 * IMG_EFFECT_REPLACE
 * <br>
 * Use pixel replacement (equivalent of passing true to
 * imagealphablending)
 * IMG_EFFECT_ALPHABLEND
 * <br>
 * Use normal pixel blending (equivalent of passing false to
 * imagealphablending)
 * IMG_EFFECT_NORMAL
 * <br>
 * Same as IMG_EFFECT_ALPHABLEND.
 * IMG_EFFECT_OVERLAY
 * <br>
 * Overlay has the effect that black background pixels will remain
 * black, white background pixels will remain white, but grey
 * background pixels will take the colour of the foreground pixel.
 * IMG_EFFECT_MULTIPLY
 * <br>
 * Overlays with a multiply effect.
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function imagelayereffect (GdImage $image, int $effect): bool {}

/**
 * Allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolorallocatealpha.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return mixed A color identifier or false if the allocation failed.
 */
function imagecolorallocatealpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int|false {}

/**
 * Get the index of the specified color + alpha or its closest possible alternative
 * @link http://www.php.net/manual/en/function.imagecolorresolvealpha.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return int a color index.
 */
function imagecolorresolvealpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * Get the index of the closest color to the specified color + alpha
 * @link http://www.php.net/manual/en/function.imagecolorclosestalpha.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return int the index of the closest color in the palette.
 */
function imagecolorclosestalpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * Get the index of the specified color + alpha
 * @link http://www.php.net/manual/en/function.imagecolorexactalpha.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return int the index of the specified color+alpha in the palette of the
 * image, or -1 if the color does not exist in the image's palette.
 */
function imagecolorexactalpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * Copy and resize part of an image with resampling
 * @link http://www.php.net/manual/en/function.imagecopyresampled.php
 * @param GdImage $dst_image Destination image resource.
 * @param GdImage $src_image Source image resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $dst_width Destination width.
 * @param int $dst_height Destination height.
 * @param int $src_width Source width.
 * @param int $src_height Source height.
 * @return bool true on success or false on failure
 */
function imagecopyresampled (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $dst_width, int $dst_height, int $src_width, int $src_height): bool {}

/**
 * Rotate an image with a given angle
 * @link http://www.php.net/manual/en/function.imagerotate.php
 * @param GdImage $image 
 * @param float $angle Rotation angle, in degrees. The rotation angle is interpreted as the
 * number of degrees to rotate the image anticlockwise.
 * @param int $background_color Specifies the color of the uncovered zone after the rotation
 * @param bool $ignore_transparent [optional] This parameter is unused.
 * @return mixed an image object for the rotated image, or false on failure.
 */
function imagerotate (GdImage $image, float $angle, int $background_color, bool $ignore_transparent = null): GdImage|false {}

/**
 * Set the tile image for filling
 * @link http://www.php.net/manual/en/function.imagesettile.php
 * @param GdImage $image 
 * @param GdImage $tile The image object to be used as a tile.
 * @return bool true on success or false on failure
 */
function imagesettile (GdImage $image, GdImage $tile): bool {}

/**
 * Set the brush image for line drawing
 * @link http://www.php.net/manual/en/function.imagesetbrush.php
 * @param GdImage $image 
 * @param GdImage $brush An image object.
 * @return bool true on success or false on failure
 */
function imagesetbrush (GdImage $image, GdImage $brush): bool {}

/**
 * Create a new palette based image
 * @link http://www.php.net/manual/en/function.imagecreate.php
 * @param int $width The image width.
 * @param int $height The image height.
 * @return mixed an image object on success, false on errors.
 */
function imagecreate (int $width, int $height): GdImage|false {}

/**
 * Return the image types supported by this PHP build
 * @link http://www.php.net/manual/en/function.imagetypes.php
 * @return int a bit-field corresponding to the image formats supported by the
 * version of GD linked into PHP. The following bits are returned,
 * IMG_AVIF | IMG_BMP |
 * IMG_GIF | IMG_JPG |
 * IMG_PNG | IMG_WBMP | 
 * IMG_XPM | IMG_WEBP.
 */
function imagetypes (): int {}

/**
 * Create a new image from the image stream in the string
 * @link http://www.php.net/manual/en/function.imagecreatefromstring.php
 * @param string $data A string containing the image data.
 * @return mixed An image object will be returned on success. false is returned if
 * the image type is unsupported, the data is not in a recognised format,
 * or the image is corrupt and cannot be loaded.
 */
function imagecreatefromstring (string $data): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromavif.php
 * @param string $filename Path to the AVIF raster image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromavif (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgif.php
 * @param string $filename Path to the GIF image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromgif (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromjpeg.php
 * @param string $filename Path to the JPEG image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromjpeg (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefrompng.php
 * @param string $filename Path to the PNG image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefrompng (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromwebp.php
 * @param string $filename Path to the WebP image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromwebp (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromxbm.php
 * @param string $filename Path to the XBM image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromxbm (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromxpm.php
 * @param string $filename Path to the XPM image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromxpm (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromwbmp.php
 * @param string $filename Path to the WBMP image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromwbmp (string $filename): GdImage|false {}

/**
 * Create a new image from GD file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd.php
 * @param string $filename Path to the GD file.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromgd (string $filename): GdImage|false {}

/**
 * Create a new image from GD2 file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd2.php
 * @param string $filename Path to the GD2 image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromgd2 (string $filename): GdImage|false {}

/**
 * Create a new image from a given part of GD2 file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd2part.php
 * @param string $filename Path to the GD2 image.
 * @param int $x x-coordinate of source point.
 * @param int $y y-coordinate of source point.
 * @param int $width Source width.
 * @param int $height Source height.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromgd2part (string $filename, int $x, int $y, int $width, int $height): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefrombmp.php
 * @param string $filename Path to the BMP image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefrombmp (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromtga.php
 * @param string $filename Path to the Truevision TGA image.
 * @return mixed an image object on success, false on errors.
 */
function imagecreatefromtga (string $filename): GdImage|false {}

/**
 * Output an XBM image to browser or file
 * @link http://www.php.net/manual/en/function.imagexbm.php
 * @param GdImage $image 
 * @param mixed $filename <p>The path to save the file to, given as string. If null, the raw image stream will be output directly.</p>
 * <p>
 * The filename (without the .xbm extension) is also
 * used for the C identifiers of the XBM, whereby non
 * alphanumeric characters of the current locale are substituted by
 * underscores. If filename is set to null,
 * image is used to build the C identifiers.
 * </p>
 * @param mixed $foreground_color [optional] You can set the foreground color with this parameter by setting an
 * identifier obtained from imagecolorallocate.
 * The default foreground color is black. All other colors are treated as
 * background.
 * @return bool true on success or false on failure
 */
function imagexbm (GdImage $image, $filename, $foreground_color = null): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imageavif.php
 * @param GdImage $image 
 * @param mixed $file [optional] The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.
 * @param int $quality [optional] quality is optional, and ranges from 0 (worst quality, smaller file)
 * to 100 (best quality, larger file).
 * If -1 is provided, the default value 30 is used.
 * @param int $speed [optional] speed is optional, and ranges from 0 (slow, smaller file)
 * to 10 (fast, larger file).
 * If -1 is provided, the default value 6 is used.
 * @return bool true on success or false on failure
 */
function imageavif (GdImage $image, $file = null, int $quality = null, int $speed = null): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagegif.php
 * @param GdImage $image 
 * @param mixed $file [optional] The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.
 * @return bool true on success or false on failure
 */
function imagegif (GdImage $image, $file = null): bool {}

/**
 * Output a PNG image to either the browser or a file
 * @link http://www.php.net/manual/en/function.imagepng.php
 * @param GdImage $image 
 * @param mixed $file [optional] <p>The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.</p>
 * <p>
 * null is invalid if the quality and
 * filters arguments are not used.
 * </p>
 * @param int $quality [optional] Compression level: from 0 (no compression) to 9.
 * The default (-1) uses the zlib compression default.
 * For more information see the zlib manual.
 * @param int $filters [optional] <p>
 * Allows reducing the PNG file size. It is a bitmask field which may be
 * set to any combination of the PNG_FILTER_XXX 
 * constants. PNG_NO_FILTER or 
 * PNG_ALL_FILTERS may also be used to respectively
 * disable or activate all filters.
 * The default value (-1) disables filtering.
 * </p>
 * The filters parameter is ignored by system libgd.
 * @return bool true on success or false on failure
 */
function imagepng (GdImage $image, $file = null, int $quality = null, int $filters = null): bool {}

/**
 * Output a WebP image to browser or file
 * @link http://www.php.net/manual/en/function.imagewebp.php
 * @param GdImage $image 
 * @param mixed $file [optional] The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.
 * @param int $quality [optional] quality ranges from 0 (worst
 * quality, smaller file) to 100 (best quality, biggest file).
 * @return bool true on success or false on failure
 */
function imagewebp (GdImage $image, $file = null, int $quality = null): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagejpeg.php
 * @param GdImage $image 
 * @param mixed $file [optional] The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.
 * @param int $quality [optional] quality is optional, and ranges from 0 (worst
 * quality, smaller file) to 100 (best quality, biggest file). The 
 * default (-1) uses the default IJG quality value (about 75).
 * @return bool true on success or false on failure
 */
function imagejpeg (GdImage $image, $file = null, int $quality = null): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagewbmp.php
 * @param GdImage $image 
 * @param mixed $file [optional] The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.
 * @param mixed $foreground_color [optional] You can set the foreground color with this parameter by setting an
 * identifier obtained from imagecolorallocate.
 * The default foreground color is black.
 * @return bool true on success or false on failure
 */
function imagewbmp (GdImage $image, $file = null, $foreground_color = null): bool {}

/**
 * Output GD image to browser or file
 * @link http://www.php.net/manual/en/function.imagegd.php
 * @param GdImage $image 
 * @param mixed $file [optional] The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.
 * @return bool true on success or false on failure
 */
function imagegd (GdImage $image, $file = null): bool {}

/**
 * Output GD2 image to browser or file
 * @link http://www.php.net/manual/en/function.imagegd2.php
 * @param GdImage $image 
 * @param mixed $file [optional] The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.
 * @param int $chunk_size [optional] Chunk size.
 * @param int $mode [optional] Either IMG_GD2_RAW or 
 * IMG_GD2_COMPRESSED. Default is 
 * IMG_GD2_RAW.
 * @return bool true on success or false on failure
 */
function imagegd2 (GdImage $image, $file = null, int $chunk_size = null, int $mode = null): bool {}

/**
 * Output a BMP image to browser or file
 * @link http://www.php.net/manual/en/function.imagebmp.php
 * @param GdImage $image 
 * @param mixed $file [optional] <p>The path or an open stream resource (which is automatically closed after this function returns) to save the file to. If not set or null, the raw image stream will be output directly.</p>
 * <p>
 * null is invalid if the compressed arguments is
 * not used.
 * </p>
 * @param bool $compressed [optional] Whether the BMP should be compressed with run-length encoding (RLE), or not.
 * @return bool true on success or false on failure
 */
function imagebmp (GdImage $image, $file = null, bool $compressed = null): bool {}

/**
 * Destroy an image
 * @link http://www.php.net/manual/en/function.imagedestroy.php
 * @param GdImage $image 
 * @return bool true on success or false on failure
 */
function imagedestroy (GdImage $image): bool {}

/**
 * Allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolorallocate.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return mixed A color identifier or false if the allocation failed.
 */
function imagecolorallocate (GdImage $image, int $red, int $green, int $blue): int|false {}

/**
 * Copy the palette from one image to another
 * @link http://www.php.net/manual/en/function.imagepalettecopy.php
 * @param GdImage $dst The destination image object.
 * @param GdImage $src The source image object.
 * @return void 
 */
function imagepalettecopy (GdImage $dst, GdImage $src): void {}

/**
 * Get the index of the color of a pixel
 * @link http://www.php.net/manual/en/function.imagecolorat.php
 * @param GdImage $image 
 * @param int $x x-coordinate of the point.
 * @param int $y y-coordinate of the point.
 * @return mixed the index of the color or false on failure.
 */
function imagecolorat (GdImage $image, int $x, int $y): int|false {}

/**
 * Get the index of the closest color to the specified color
 * @link http://www.php.net/manual/en/function.imagecolorclosest.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int the index of the closest color, in the palette of the image, to
 * the specified one
 */
function imagecolorclosest (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * Get the index of the color which has the hue, white and blackness
 * @link http://www.php.net/manual/en/function.imagecolorclosesthwb.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int an integer with the index of the color which has 
 * the hue, white and blackness nearest the given color.
 */
function imagecolorclosesthwb (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * De-allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolordeallocate.php
 * @param GdImage $image 
 * @param int $color The color identifier.
 * @return bool true on success or false on failure
 */
function imagecolordeallocate (GdImage $image, int $color): bool {}

/**
 * Get the index of the specified color or its closest possible alternative
 * @link http://www.php.net/manual/en/function.imagecolorresolve.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int a color index.
 */
function imagecolorresolve (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * Get the index of the specified color
 * @link http://www.php.net/manual/en/function.imagecolorexact.php
 * @param GdImage $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int the index of the specified color in the palette, or -1 if the
 * color does not exist.
 */
function imagecolorexact (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * Set the color for the specified palette index
 * @link http://www.php.net/manual/en/function.imagecolorset.php
 * @param GdImage $image 
 * @param int $color An index in the palette.
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha [optional] Value of alpha component.
 * @return mixed The function returns null on success, or false on failure.
 */
function imagecolorset (GdImage $image, int $color, int $red, int $green, int $blue, int $alpha = null): ?false {}

/**
 * Get the colors for an index
 * @link http://www.php.net/manual/en/function.imagecolorsforindex.php
 * @param GdImage $image 
 * @param int $color The color index.
 * @return array an associative array with red, green, blue and alpha keys that
 * contain the appropriate values for the specified color index.
 */
function imagecolorsforindex (GdImage $image, int $color): array {}

/**
 * Apply a gamma correction to a GD image
 * @link http://www.php.net/manual/en/function.imagegammacorrect.php
 * @param GdImage $image 
 * @param float $input_gamma The input gamma.
 * @param float $output_gamma The output gamma.
 * @return bool true on success or false on failure
 */
function imagegammacorrect (GdImage $image, float $input_gamma, float $output_gamma): bool {}

/**
 * Set a single pixel
 * @link http://www.php.net/manual/en/function.imagesetpixel.php
 * @param GdImage $image 
 * @param int $x x-coordinate.
 * @param int $y y-coordinate.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagesetpixel (GdImage $image, int $x, int $y, int $color): bool {}

/**
 * Draw a line
 * @link http://www.php.net/manual/en/function.imageline.php
 * @param GdImage $image 
 * @param int $x1 x-coordinate for first point.
 * @param int $y1 y-coordinate for first point.
 * @param int $x2 x-coordinate for second point.
 * @param int $y2 y-coordinate for second point.
 * @param int $color The line color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imageline (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draw a dashed line
 * @link http://www.php.net/manual/en/function.imagedashedline.php
 * @param GdImage $image 
 * @param int $x1 Upper left x coordinate.
 * @param int $y1 Upper left y coordinate 0, 0 is the top left corner of the image.
 * @param int $x2 Bottom right x coordinate.
 * @param int $y2 Bottom right y coordinate.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagedashedline (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draw a rectangle
 * @link http://www.php.net/manual/en/function.imagerectangle.php
 * @param GdImage $image 
 * @param int $x1 Upper left x coordinate.
 * @param int $y1 Upper left y coordinate
 * 0, 0 is the top left corner of the image.
 * @param int $x2 Bottom right x coordinate.
 * @param int $y2 Bottom right y coordinate.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagerectangle (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draw a filled rectangle
 * @link http://www.php.net/manual/en/function.imagefilledrectangle.php
 * @param GdImage $image 
 * @param int $x1 x-coordinate for point 1.
 * @param int $y1 y-coordinate for point 1.
 * @param int $x2 x-coordinate for point 2.
 * @param int $y2 y-coordinate for point 2.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilledrectangle (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draws an arc
 * @link http://www.php.net/manual/en/function.imagearc.php
 * @param GdImage $image 
 * @param int $center_x x-coordinate of the center.
 * @param int $center_y y-coordinate of the center.
 * @param int $width The arc width.
 * @param int $height The arc height.
 * @param int $start_angle The arc start angle, in degrees.
 * @param int $end_angle The arc end angle, in degrees.
 * 0° is located at the three-o'clock position, and the arc is drawn
 * clockwise.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagearc (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $start_angle, int $end_angle, int $color): bool {}

/**
 * Draw an ellipse
 * @link http://www.php.net/manual/en/function.imageellipse.php
 * @param GdImage $image 
 * @param int $center_x x-coordinate of the center.
 * @param int $center_y y-coordinate of the center.
 * @param int $width The ellipse width.
 * @param int $height The ellipse height.
 * @param int $color The color of the ellipse. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imageellipse (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $color): bool {}

/**
 * Flood fill to specific color
 * @link http://www.php.net/manual/en/function.imagefilltoborder.php
 * @param GdImage $image 
 * @param int $x x-coordinate of start.
 * @param int $y y-coordinate of start.
 * @param int $border_color The border color. gd.identifier.color
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilltoborder (GdImage $image, int $x, int $y, int $border_color, int $color): bool {}

/**
 * Flood fill
 * @link http://www.php.net/manual/en/function.imagefill.php
 * @param GdImage $image 
 * @param int $x x-coordinate of start point.
 * @param int $y y-coordinate of start point.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefill (GdImage $image, int $x, int $y, int $color): bool {}

/**
 * Find out the number of colors in an image's palette
 * @link http://www.php.net/manual/en/function.imagecolorstotal.php
 * @param GdImage $image 
 * @return int the number of colors in the specified image's palette or 0 for
 * truecolor images.
 */
function imagecolorstotal (GdImage $image): int {}

/**
 * Define a color as transparent
 * @link http://www.php.net/manual/en/function.imagecolortransparent.php
 * @param GdImage $image 
 * @param mixed $color [optional] gd.identifier.color
 * @return int The identifier of the new (or current, if none is specified)
 * transparent color is returned. If color
 * is null, and the image has no transparent color, the
 * returned identifier will be -1.
 */
function imagecolortransparent (GdImage $image, $color = null): int {}

/**
 * Enable or disable interlace
 * @link http://www.php.net/manual/en/function.imageinterlace.php
 * @param GdImage $image 
 * @param mixed $enable [optional] 
 * @return bool true if the interlace bit is set for the image, false otherwise.
 */
function imageinterlace (GdImage $image, $enable = null): bool {}

/**
 * Draws a polygon
 * @link http://www.php.net/manual/en/function.imagepolygon.php
 * @param GdImage $image 
 * @param array $points An array containing the polygon's vertices, e.g.:
 * <table>
 * <tr valign="top">
 * <td>points[0]</td>
 * <td>= x0</td>
 * </tr>
 * <tr valign="top">
 * <td>points[1]</td>
 * <td>= y0</td>
 * </tr>
 * <tr valign="top">
 * <td>points[2]</td>
 * <td>= x1</td>
 * </tr>
 * <tr valign="top">
 * <td>points[3]</td>
 * <td>= y1</td>
 * </tr>
 * </table>
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagepolygon (GdImage $image, array $points, int $color): bool {}

/**
 * Draws an open polygon
 * @link http://www.php.net/manual/en/function.imageopenpolygon.php
 * @param GdImage $image 
 * @param array $points An array containing the polygon's vertices, e.g.:
 * <table>
 * <tr valign="top">
 * <td>points[0]</td>
 * <td>= x0</td>
 * </tr>
 * <tr valign="top">
 * <td>points[1]</td>
 * <td>= y0</td>
 * </tr>
 * <tr valign="top">
 * <td>points[2]</td>
 * <td>= x1</td>
 * </tr>
 * <tr valign="top">
 * <td>points[3]</td>
 * <td>= y1</td>
 * </tr>
 * </table>
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imageopenpolygon (GdImage $image, array $points, int $color): bool {}

/**
 * Draw a filled polygon
 * @link http://www.php.net/manual/en/function.imagefilledpolygon.php
 * @param GdImage $image 
 * @param array $points An array containing the x and y
 * coordinates of the polygons vertices consecutively.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilledpolygon (GdImage $image, array $points, int $color): bool {}

/**
 * Get font width
 * @link http://www.php.net/manual/en/function.imagefontwidth.php
 * @param mixed $font 
 * @return int the pixel width of the font.
 */
function imagefontwidth ($font): int {}

/**
 * Get font height
 * @link http://www.php.net/manual/en/function.imagefontheight.php
 * @param mixed $font 
 * @return int the pixel height of the font.
 */
function imagefontheight ($font): int {}

/**
 * Draw a character horizontally
 * @link http://www.php.net/manual/en/function.imagechar.php
 * @param GdImage $image 
 * @param mixed $font 
 * @param int $x x-coordinate of the start.
 * @param int $y y-coordinate of the start.
 * @param string $char The character to draw.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagechar (GdImage $image, $font, int $x, int $y, string $char, int $color): bool {}

/**
 * Draw a character vertically
 * @link http://www.php.net/manual/en/function.imagecharup.php
 * @param GdImage $image 
 * @param mixed $font 
 * @param int $x x-coordinate of the start.
 * @param int $y y-coordinate of the start.
 * @param string $char The character to draw.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagecharup (GdImage $image, $font, int $x, int $y, string $char, int $color): bool {}

/**
 * Draw a string horizontally
 * @link http://www.php.net/manual/en/function.imagestring.php
 * @param GdImage $image 
 * @param mixed $font 
 * @param int $x x-coordinate of the upper left corner.
 * @param int $y y-coordinate of the upper left corner.
 * @param string $string The string to be written.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagestring (GdImage $image, $font, int $x, int $y, string $string, int $color): bool {}

/**
 * Draw a string vertically
 * @link http://www.php.net/manual/en/function.imagestringup.php
 * @param GdImage $image 
 * @param mixed $font 
 * @param int $x x-coordinate of the bottom left corner.
 * @param int $y y-coordinate of the bottom left corner.
 * @param string $string The string to be written.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagestringup (GdImage $image, $font, int $x, int $y, string $string, int $color): bool {}

/**
 * Copy part of an image
 * @link http://www.php.net/manual/en/function.imagecopy.php
 * @param GdImage $dst_image Destination image resource.
 * @param GdImage $src_image Source image resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $src_width Source width.
 * @param int $src_height Source height.
 * @return bool true on success or false on failure
 */
function imagecopy (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height): bool {}

/**
 * Copy and merge part of an image
 * @link http://www.php.net/manual/en/function.imagecopymerge.php
 * @param GdImage $dst_image Destination image resource.
 * @param GdImage $src_image Source image resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $src_width Source width.
 * @param int $src_height Source height.
 * @param int $pct The two images will be merged according to pct
 * which can range from 0 to 100. When pct = 0,
 * no action is taken, when 100 this function behaves identically
 * to imagecopy for pallete images, except for
 * ignoring alpha components, while it implements alpha transparency
 * for true colour images.
 * @return bool true on success or false on failure
 */
function imagecopymerge (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height, int $pct): bool {}

/**
 * Copy and merge part of an image with gray scale
 * @link http://www.php.net/manual/en/function.imagecopymergegray.php
 * @param GdImage $dst_image Destination image resource.
 * @param GdImage $src_image Source image resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $src_width Source width.
 * @param int $src_height Source height.
 * @param int $pct The src_image will be changed to grayscale according 
 * to pct where 0 is fully grayscale and 100 is 
 * unchanged. When pct = 100 this function behaves 
 * identically to imagecopy for pallete images, except for
 * ignoring alpha components, while
 * it implements alpha transparency for true colour images.
 * @return bool true on success or false on failure
 */
function imagecopymergegray (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height, int $pct): bool {}

/**
 * Copy and resize part of an image
 * @link http://www.php.net/manual/en/function.imagecopyresized.php
 * @param GdImage $dst_image Destination image resource.
 * @param GdImage $src_image Source image resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $dst_width Destination width.
 * @param int $dst_height Destination height.
 * @param int $src_width Source width.
 * @param int $src_height Source height.
 * @return bool true on success or false on failure
 */
function imagecopyresized (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $dst_width, int $dst_height, int $src_width, int $src_height): bool {}

/**
 * Get image width
 * @link http://www.php.net/manual/en/function.imagesx.php
 * @param GdImage $image 
 * @return int Return the width of the image.
 */
function imagesx (GdImage $image): int {}

/**
 * Get image height
 * @link http://www.php.net/manual/en/function.imagesy.php
 * @param GdImage $image 
 * @return int Return the height of the image.
 */
function imagesy (GdImage $image): int {}

/**
 * Set the clipping rectangle
 * @link http://www.php.net/manual/en/function.imagesetclip.php
 * @param GdImage $image 
 * @param int $x1 The x-coordinate of the upper left corner.
 * @param int $y1 The y-coordinate of the upper left corner.
 * @param int $x2 The x-coordinate of the lower right corner.
 * @param int $y2 The y-coordinate of the lower right corner.
 * @return bool true on success or false on failure
 */
function imagesetclip (GdImage $image, int $x1, int $y1, int $x2, int $y2): bool {}

/**
 * Get the clipping rectangle
 * @link http://www.php.net/manual/en/function.imagegetclip.php
 * @param GdImage $image 
 * @return array The function returns an indexed array with the coordinates of the clipping
 * rectangle which has the following entries:
 * <p>
 * <br>
 * x-coordinate of the upper left corner
 * <br>
 * y-coordinate of the upper left corner
 * <br>
 * x-coordinate of the lower right corner
 * <br>
 * y-coordinate of the lower right corner
 * </p>
 */
function imagegetclip (GdImage $image): array {}

/**
 * Give the bounding box of a text using fonts via freetype2
 * @link http://www.php.net/manual/en/function.imageftbbox.php
 * @param float $size The font size in points.
 * @param float $angle Angle in degrees in which string will be 
 * measured.
 * @param string $font_filename The name of the TrueType font file (can be a URL). Depending on
 * which version of the GD library that PHP is using, it may attempt to
 * search for files that do not begin with a leading '/' by appending
 * '.ttf' to the filename and searching along a library-defined font path.
 * @param string $string The string to be measured.
 * @param array $options [optional] <table>
 * Possible array indexes for options
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Type</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>linespacing</td>
 * <td>float</td>
 * <td>Defines drawing linespacing</td>
 * </tr>
 * </table>
 * </table>
 * @return mixed imageftbbox returns an array with 8
 * elements representing four points making the bounding box of the
 * text:
 * <table>
 * <tr valign="top">
 * <td>0</td>
 * <td>lower left corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>lower left corner, Y position</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>lower right corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>3</td>
 * <td>lower right corner, Y position</td>
 * </tr>
 * <tr valign="top">
 * <td>4</td>
 * <td>upper right corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>5</td>
 * <td>upper right corner, Y position</td>
 * </tr>
 * <tr valign="top">
 * <td>6</td>
 * <td>upper left corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>7</td>
 * <td>upper left corner, Y position</td>
 * </tr>
 * </table>
 * <p>
 * The points are relative to the text regardless of the
 * angle, so "upper left" means in the top left-hand 
 * corner seeing the text horizontally.
 * </p>
 * <p>
 * On failure, false is returned.
 * </p>
 */
function imageftbbox (float $size, float $angle, string $font_filename, string $string, array $options = null): array|false {}

/**
 * Write text to the image using fonts using FreeType 2
 * @link http://www.php.net/manual/en/function.imagefttext.php
 * @param GdImage $image 
 * @param float $size The font size to use in points.
 * @param float $angle The angle in degrees, with 0 degrees being left-to-right reading text.
 * Higher values represent a counter-clockwise rotation. For example, a 
 * value of 90 would result in bottom-to-top reading text.
 * @param int $x The coordinates given by x and
 * y will define the basepoint of the first
 * character (roughly the lower-left corner of the character). This
 * is different from the imagestring, where
 * x and y define the
 * upper-left corner of the first character. For example, "top left"
 * is 0, 0.
 * @param int $y The y-ordinate. This sets the position of the fonts baseline, not the
 * very bottom of the character.
 * @param int $color The index of the desired color for the text, see 
 * imagecolorexact.
 * @param string $font_filename <p>
 * The path to the TrueType font you wish to use.
 * </p>
 * <p>
 * Depending on which version of the GD library PHP is using, when
 * font_filename does not begin with a leading
 * / then .ttf will be appended
 * to the filename and the library will attempt to search for that
 * filename along a library-defined font path.
 * </p>
 * <p>
 * In many cases where a font resides in the same directory as the script using it
 * the following trick will alleviate any include problems.
 * <pre>
 * <code>&lt;?php
 * &#47;&#47; Set the environment variable for GD
 * putenv(&&#35;039;GDFONTPATH=&&#35;039; . realpath(&&#35;039;.&&#35;039;));
 * &#47;&#47; Name the font to be used (note the lack of the .ttf extension)
 * $font = &&#35;039;SomeFont&&#35;039;;
 * ?&gt;</code>
 * </pre>
 * </p>
 * @param string $text Text to be inserted into image.
 * @param array $options [optional] <table>
 * Possible array indexes for options
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Type</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>linespacing</td>
 * <td>float</td>
 * <td>Defines drawing linespacing</td>
 * </tr>
 * </table>
 * </table>
 * @return mixed This function returns an array defining the four points of the box, starting in the lower left and moving counter-clockwise:
 * <table>
 * <tr valign="top">
 * <td>0</td>
 * <td>lower left x-coordinate</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>lower left y-coordinate</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>lower right x-coordinate</td>
 * </tr>
 * <tr valign="top">
 * <td>3</td>
 * <td>lower right y-coordinate</td>
 * </tr>
 * <tr valign="top">
 * <td>4</td>
 * <td>upper right x-coordinate</td>
 * </tr>
 * <tr valign="top">
 * <td>5</td>
 * <td>upper right y-coordinate</td>
 * </tr>
 * <tr valign="top">
 * <td>6</td>
 * <td>upper left x-coordinate</td>
 * </tr>
 * <tr valign="top">
 * <td>7</td>
 * <td>upper left y-coordinate</td>
 * </tr>
 * </table>
 * <p>
 * On failure, false is returned.
 * </p>
 */
function imagefttext (GdImage $image, float $size, float $angle, int $x, int $y, int $color, string $font_filename, string $text, array $options = null): array|false {}

/**
 * Give the bounding box of a text using TrueType fonts
 * @link http://www.php.net/manual/en/function.imagettfbbox.php
 * @param float $size The font size in points.
 * @param float $angle Angle in degrees in which string will be measured.
 * @param string $font_filename 
 * @param string $string The string to be measured.
 * @param array $options [optional] 
 * @return mixed imagettfbbox returns an array with 8
 * elements representing four points making the bounding box of the
 * text on success and false on error.
 * <table>
 * <tr valign="top">
 * <td>key</td>
 * <td>contents</td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td>lower left corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>lower left corner, Y position</td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>lower right corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>3</td>
 * <td>lower right corner, Y position</td>
 * </tr>
 * <tr valign="top">
 * <td>4</td>
 * <td>upper right corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>5</td>
 * <td>upper right corner, Y position</td>
 * </tr>
 * <tr valign="top">
 * <td>6</td>
 * <td>upper left corner, X position</td>
 * </tr>
 * <tr valign="top">
 * <td>7</td>
 * <td>upper left corner, Y position</td>
 * </tr>
 * </table>
 * <p>
 * The points are relative to the text regardless of the
 * angle, so "upper left" means in the top left-hand 
 * corner seeing the text horizontally.
 * </p>
 */
function imagettfbbox (float $size, float $angle, string $font_filename, string $string, array $options = null): array|false {}

/**
 * Write text to the image using TrueType fonts
 * @link http://www.php.net/manual/en/function.imagettftext.php
 * @param GdImage $image 
 * @param float $size The font size in points.
 * @param float $angle The angle in degrees, with 0 degrees being left-to-right reading text.
 * Higher values represent a counter-clockwise rotation. For example, a 
 * value of 90 would result in bottom-to-top reading text.
 * @param int $x The coordinates given by x and
 * y will define the basepoint of the first
 * character (roughly the lower-left corner of the character). This
 * is different from the imagestring, where
 * x and y define the
 * upper-left corner of the first character. For example, "top left"
 * is 0, 0.
 * @param int $y The y-ordinate. This sets the position of the fonts baseline, not the
 * very bottom of the character.
 * @param int $color The color index. Using the negative of a color index has the effect of
 * turning off antialiasing. See imagecolorallocate.
 * @param string $font_filename 
 * @param string $text <p>
 * The text string in UTF-8 encoding.
 * </p>
 * <p>
 * May include decimal numeric character references (of the form:
 * &amp;#8364;) to access characters in a font beyond position 127.
 * The hexadecimal format (like &amp;#xA9;) is supported.
 * Strings in UTF-8 encoding can be passed directly.
 * </p>
 * <p>
 * Named entities, such as &amp;copy;, are not supported. Consider using 
 * html_entity_decode
 * to decode these named entities into UTF-8 strings.
 * </p>
 * <p>
 * If a character is used in the string which is not supported by the
 * font, a hollow rectangle will replace the character.
 * </p>
 * @param array $options [optional] 
 * @return mixed an array with 8 elements representing four points making the
 * bounding box of the text. The order of the points is lower left, lower 
 * right, upper right, upper left. The points are relative to the text
 * regardless of the angle, so "upper left" means in the top left-hand 
 * corner when you see the text horizontally.
 * Returns false on error.
 */
function imagettftext (GdImage $image, float $size, float $angle, int $x, int $y, int $color, string $font_filename, string $text, array $options = null): array|false {}

/**
 * Applies a filter to an image
 * @link http://www.php.net/manual/en/function.imagefilter.php
 * @param GdImage $image 
 * @param int $filter <p>
 * filter can be one of the following:
 * <p>
 * <br>
 * IMG_FILTER_NEGATE: Reverses all colors of
 * the image.
 * <br>
 * IMG_FILTER_GRAYSCALE: Converts the image into
 * grayscale by changing the red, green and blue components to their
 * weighted sum using the same coefficients as the REC.601 luma (Y')
 * calculation. The alpha components are retained. For palette images the
 * result may differ due to palette limitations.
 * <br>
 * IMG_FILTER_BRIGHTNESS: Changes the brightness
 * of the image. Use args to set the level of
 * brightness. The range for the brightness is -255 to 255.
 * <br>
 * IMG_FILTER_CONTRAST: Changes the contrast of
 * the image. Use args to set the level of
 * contrast.
 * <br>
 * IMG_FILTER_COLORIZE: Like
 * IMG_FILTER_GRAYSCALE, except you can specify the
 * color. Use args, arg2 and
 * arg3 in the form of
 * red, green,
 * blue and arg4 for the
 * alpha channel. The range for each color is 0 to 255.
 * <br>
 * IMG_FILTER_EDGEDETECT: Uses edge detection to
 * highlight the edges in the image.
 * <br>
 * IMG_FILTER_EMBOSS: Embosses the image.
 * <br>
 * IMG_FILTER_GAUSSIAN_BLUR: Blurs the image using
 * the Gaussian method.
 * <br>
 * IMG_FILTER_SELECTIVE_BLUR: Blurs the image.
 * <br>
 * IMG_FILTER_MEAN_REMOVAL: Uses mean removal to
 * achieve a "sketchy" effect.
 * <br>
 * IMG_FILTER_SMOOTH: Makes the image smoother.
 * Use args to set the level of smoothness.
 * <br>
 * IMG_FILTER_PIXELATE: Applies pixelation effect 
 * to the image, use args to set the block size 
 * and arg2 to set the pixelation effect mode.
 * <br>
 * IMG_FILTER_SCATTER: Applies scatter effect 
 * to the image, use args and 
 * arg2 to define the effect strength and 
 * additionally arg3 to only apply the 
 * on select pixel colors.
 * </p>
 * </p>
 * @param mixed $args <br>
 * IMG_FILTER_BRIGHTNESS: Brightness level.
 * <br>
 * IMG_FILTER_CONTRAST: Contrast level.
 * <br>
 * IMG_FILTER_COLORIZE: Value of red component.
 * <br>
 * IMG_FILTER_SMOOTH: Smoothness level.
 * <br>
 * IMG_FILTER_PIXELATE: Block size in pixels.
 * <br>
 * IMG_FILTER_SCATTER: Effect substraction level. 
 * This must not be higher or equal to the addition level set with 
 * arg2.
 * @return bool true on success or false on failure
 */
function imagefilter (GdImage $image, int $filter, $args): bool {}

/**
 * Apply a 3x3 convolution matrix, using coefficient and offset
 * @link http://www.php.net/manual/en/function.imageconvolution.php
 * @param GdImage $image 
 * @param array $matrix A 3x3 matrix: an array of three arrays of three floats.
 * @param float $divisor The divisor of the result of the convolution, used for normalization.
 * @param float $offset Color offset.
 * @return bool true on success or false on failure
 */
function imageconvolution (GdImage $image, array $matrix, float $divisor, float $offset): bool {}

/**
 * Flips an image using a given mode
 * @link http://www.php.net/manual/en/function.imageflip.php
 * @param GdImage $image 
 * @param int $mode <p>
 * Flip mode, this can be one of the IMG_FLIP_&#42; constants:
 * </p>
 * <p>
 * <table>
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>IMG_FLIP_HORIZONTAL</td>
 * <td>
 * Flips the image horizontally.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>IMG_FLIP_VERTICAL</td>
 * <td>
 * Flips the image vertically.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>IMG_FLIP_BOTH</td>
 * <td>
 * Flips the image both horizontally and vertically.
 * </td>
 * </tr>
 * </table>
 * </p>
 * @return bool true on success or false on failure
 */
function imageflip (GdImage $image, int $mode): bool {}

/**
 * Should antialias functions be used or not
 * @link http://www.php.net/manual/en/function.imageantialias.php
 * @param GdImage $image 
 * @param bool $enable Whether to enable antialiasing or not.
 * @return bool true on success or false on failure
 */
function imageantialias (GdImage $image, bool $enable): bool {}

/**
 * Crop an image to the given rectangle
 * @link http://www.php.net/manual/en/function.imagecrop.php
 * @param GdImage $image 
 * @param array $rectangle The cropping rectangle as array with keys
 * x, y, width and
 * height.
 * @return mixed Return cropped image object on success or false on failure.
 */
function imagecrop (GdImage $image, array $rectangle): GdImage|false {}

/**
 * Crop an image automatically using one of the available modes
 * @link http://www.php.net/manual/en/function.imagecropauto.php
 * @param GdImage $image 
 * @param int $mode [optional] <p>
 * One of the following constants:
 * </p>
 * <p>
 * IMG_CROP_DEFAULT
 * <br>
 * Same as IMG_CROP_TRANSPARENT.
 * Before PHP 7.4.0, the bundled libgd fell back to IMG_CROP_SIDES,
 * if the image had no transparent color.
 * IMG_CROP_TRANSPARENT
 * <br>
 * Crops out a transparent background.
 * IMG_CROP_BLACK
 * <br>
 * Crops out a black background.
 * IMG_CROP_WHITE
 * <br>
 * Crops out a white background.
 * IMG_CROP_SIDES
 * <br>
 * Uses the 4 corners of the image to attempt to detect the background to
 * crop.
 * IMG_CROP_THRESHOLD
 * <br>
 * Crops an image using the given threshold and
 * color.
 * </p>
 * @param float $threshold [optional] <p>
 * Specifies the tolerance in percent to be used while comparing the image
 * color and the color to crop. The method used to calculate the color
 * difference is based on the color distance in the RGB(a) cube.
 * </p>
 * <p>
 * Used only in IMG_CROP_THRESHOLD mode.
 * </p>
 * Before PHP 7.4.0, the bundled libgd used a somewhat different algorithm,
 * so the same threshold yielded different results
 * for system and bundled libgd.
 * @param int $color [optional] <p>
 * Either an RGB color value or a palette index.
 * </p>
 * <p>
 * Used only in IMG_CROP_THRESHOLD mode.
 * </p>
 * @return mixed a cropped image object on success or false on failure.
 * If the complete image was cropped, imagecrop returns false.
 */
function imagecropauto (GdImage $image, int $mode = null, float $threshold = null, int $color = null): GdImage|false {}

/**
 * Scale an image using the given new width and height
 * @link http://www.php.net/manual/en/function.imagescale.php
 * @param GdImage $image 
 * @param int $width The width to scale the image to.
 * @param int $height [optional] The height to scale the image to. If omitted or negative, the aspect
 * ratio will be preserved.
 * @param int $mode [optional] One of IMG_NEAREST_NEIGHBOUR,
 * IMG_BILINEAR_FIXED,
 * IMG_BICUBIC,
 * IMG_BICUBIC_FIXED or anything else (will use two
 * pass).
 * IMG_WEIGHTED4 is not yet supported.
 * @return mixed Return the scaled image object on success or false on failure.
 */
function imagescale (GdImage $image, int $width, int $height = null, int $mode = null): GdImage|false {}

/**
 * Return an image containing the affine transformed src image, using an optional clipping area
 * @link http://www.php.net/manual/en/function.imageaffine.php
 * @param GdImage $image 
 * @param array $affine Array with keys 0 to 5.
 * @param mixed $clip [optional] Array with keys "x", "y", "width" and "height"; or null.
 * @return mixed Return affined image object on success or false on failure.
 */
function imageaffine (GdImage $image, array $affine, $clip = null): GdImage|false {}

/**
 * Get an affine transformation matrix
 * @link http://www.php.net/manual/en/function.imageaffinematrixget.php
 * @param int $type One of the IMG_AFFINE_&#42; constants.
 * @param mixed $options <p>
 * If type is IMG_AFFINE_TRANSLATE
 * or IMG_AFFINE_SCALE,
 * options has to be an array with keys x
 * and y, both having float values.
 * </p>
 * <p>
 * If type is IMG_AFFINE_ROTATE,
 * IMG_AFFINE_SHEAR_HORIZONTAL or IMG_AFFINE_SHEAR_VERTICAL,
 * options has to be a float specifying the angle.
 * </p>
 * @return mixed An affine transformation matrix (an array with keys
 * 0 to 5 and float values)
 * or false on failure.
 */
function imageaffinematrixget (int $type, $options): array|false {}

/**
 * Concatenate two affine transformation matrices
 * @link http://www.php.net/manual/en/function.imageaffinematrixconcat.php
 * @param array $matrix1 An affine transformation matrix (an array with keys
 * 0 to 5 and float values).
 * @param array $matrix2 An affine transformation matrix (an array with keys
 * 0 to 5 and float values).
 * @return mixed An affine transformation matrix (an array with keys
 * 0 to 5 and float values)
 * or false on failure.
 */
function imageaffinematrixconcat (array $matrix1, array $matrix2): array|false {}

/**
 * Get the interpolation method
 * @link http://www.php.net/manual/en/function.imagegetinterpolation.php
 * @param GdImage $image 
 * @return int the interpolation method.
 */
function imagegetinterpolation (GdImage $image): int {}

/**
 * Set the interpolation method
 * @link http://www.php.net/manual/en/function.imagesetinterpolation.php
 * @param GdImage $image 
 * @param int $method [optional] <p>
 * The interpolation method, which can be one of the following:
 * <p>
 * <br>
 * IMG_BELL: Bell filter.
 * <br>
 * IMG_BESSEL: Bessel filter.
 * <br>
 * IMG_BICUBIC: Bicubic interpolation.
 * <br>
 * IMG_BICUBIC_FIXED: Fixed point implementation of the bicubic interpolation.
 * <br>
 * IMG_BILINEAR_FIXED: Fixed point implementation of the bilinear interpolation (default (also on image creation)).
 * <br>
 * IMG_BLACKMAN: Blackman window function.
 * <br>
 * IMG_BOX: Box blur filter.
 * <br>
 * IMG_BSPLINE: Spline interpolation.
 * <br>
 * IMG_CATMULLROM: Cubic Hermite spline interpolation.
 * <br>
 * IMG_GAUSSIAN: Gaussian function.
 * <br>
 * IMG_GENERALIZED_CUBIC: Generalized cubic spline fractal interpolation.
 * <br>
 * IMG_HERMITE: Hermite interpolation.
 * <br>
 * IMG_HAMMING: Hamming filter.
 * <br>
 * IMG_HANNING: Hanning filter.
 * <br>
 * IMG_MITCHELL: Mitchell filter.
 * <br>
 * IMG_POWER: Power interpolation.
 * <br>
 * IMG_QUADRATIC: Inverse quadratic interpolation.
 * <br>
 * IMG_SINC: Sinc function.
 * <br>
 * IMG_NEAREST_NEIGHBOUR: Nearest neighbour interpolation.
 * <br>
 * IMG_WEIGHTED4: Weighting filter.
 * <br>
 * IMG_TRIANGLE: Triangle interpolation.
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function imagesetinterpolation (GdImage $image, int $method = null): bool {}

/**
 * Get or set the resolution of the image
 * @link http://www.php.net/manual/en/function.imageresolution.php
 * @param GdImage $image 
 * @param mixed $resolution_x [optional] The horizontal resolution in DPI.
 * @param mixed $resolution_y [optional] The vertical resolution in DPI.
 * @return mixed When used as getter,
 * it returns an indexed array of the horizontal and vertical resolution on
 * success, or false on failure.
 * When used as setter, it returns
 * true on success, or false on failure.
 */
function imageresolution (GdImage $image, $resolution_x = null, $resolution_y = null): array|bool {}


/**
 * gd.constants.types
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_AVIF', 256);

/**
 * gd.constants.types
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_GIF', 1);

/**
 * gd.constants.types
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_JPG', 2);

/**
 * gd.constants.types
 * <p>
 * This constant has the same value as IMG_JPG
 * </p>
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_JPEG', 2);

/**
 * gd.constants.types
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_PNG', 4);

/**
 * gd.constants.types
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_WBMP', 8);

/**
 * gd.constants.types
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_XPM', 16);

/**
 * gd.constants.types
 * Available as of PHP 7.0.10.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_WEBP', 32);

/**
 * gd.constants.types
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BMP', 64);
define ('IMG_TGA', 128);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_WEBP_LOSSLESS', 101);

/**
 * gd.constants.color
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_TILED', -5);

/**
 * gd.constants.color
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_STYLED', -2);

/**
 * gd.constants.color
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_BRUSHED', -3);

/**
 * gd.constants.color
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_STYLEDBRUSHED', -4);

/**
 * gd.constants.color
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_TRANSPARENT', -6);

/**
 * gd.constants.arc
 * <p>
 * This constant has the same value as IMG_ARC_PIE
 * </p>
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_ROUNDED', 0);

/**
 * gd.constants.arc
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_PIE', 0);

/**
 * gd.constants.arc
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_CHORD', 1);

/**
 * gd.constants.arc
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_NOFILL', 2);

/**
 * gd.constants.arc
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_EDGED', 4);

/**
 * gd.constants.gd2
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_GD2_RAW', 1);

/**
 * gd.constants.gd2
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_GD2_COMPRESSED', 2);

/**
 * gd.constants.flip
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FLIP_HORIZONTAL', 1);

/**
 * gd.constants.flip
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FLIP_VERTICAL', 2);

/**
 * gd.constants.flip
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FLIP_BOTH', 3);

/**
 * gd.constants.effect
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_REPLACE', 0);

/**
 * gd.constants.effect
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_ALPHABLEND', 1);

/**
 * gd.constants.effect
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_NORMAL', 2);

/**
 * gd.constants.effect
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_OVERLAY', 3);

/**
 * gd.constants.effect
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_MULTIPLY', 4);
define ('IMG_CROP_DEFAULT', 0);
define ('IMG_CROP_TRANSPARENT', 1);
define ('IMG_CROP_BLACK', 2);
define ('IMG_CROP_WHITE', 3);
define ('IMG_CROP_SIDES', 4);
define ('IMG_CROP_THRESHOLD', 5);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BELL', 1);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BESSEL', 2);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BILINEAR_FIXED', 3);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BICUBIC', 4);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BICUBIC_FIXED', 5);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BLACKMAN', 6);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BOX', 7);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BSPLINE', 8);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_CATMULLROM', 9);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_GAUSSIAN', 10);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_GENERALIZED_CUBIC', 11);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_HERMITE', 12);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_HAMMING', 13);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_HANNING', 14);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_MITCHELL', 15);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_POWER', 17);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_QUADRATIC', 18);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_SINC', 19);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_NEAREST_NEIGHBOUR', 16);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_WEIGHTED4', 21);

/**
 * gd.constants.interpolation
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_TRIANGLE', 20);

/**
 * gd.constants.affine
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_AFFINE_TRANSLATE', 0);

/**
 * gd.constants.affine
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_AFFINE_SCALE', 1);

/**
 * gd.constants.affine
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_AFFINE_ROTATE', 2);

/**
 * gd.constants.affine
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_AFFINE_SHEAR_HORIZONTAL', 3);

/**
 * gd.constants.affine
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_AFFINE_SHEAR_VERTICAL', 4);

/**
 * When the bundled version of GD is used this is 1 otherwise 
 * its set to 0.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_BUNDLED', 0);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_NEGATE', 0);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_GRAYSCALE', 1);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_BRIGHTNESS', 2);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_CONTRAST', 3);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_COLORIZE', 4);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_EDGEDETECT', 5);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_GAUSSIAN_BLUR', 7);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_SELECTIVE_BLUR', 8);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_EMBOSS', 6);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_MEAN_REMOVAL', 9);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_SMOOTH', 10);

/**
 * gd.constants.filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_PIXELATE', 11);

/**
 * gd.constants.filter
 * (Available as of PHP 7.4.0)
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_SCATTER', 12);

/**
 * The GD version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_VERSION', "2.3.3");

/**
 * The GD major version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_MAJOR_VERSION', 2);

/**
 * The GD minor version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_MINOR_VERSION', 3);

/**
 * The GD release version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_RELEASE_VERSION', 3);

/**
 * The GD "extra" version (beta/rc..) PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_EXTRA_VERSION', "");

/**
 * gd.constants.png-filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('PNG_NO_FILTER', 0);

/**
 * gd.constants.png-filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_NONE', 8);

/**
 * gd.constants.png-filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_SUB', 16);

/**
 * gd.constants.png-filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_UP', 32);

/**
 * gd.constants.png-filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_AVG', 64);

/**
 * gd.constants.png-filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_PAETH', 128);

/**
 * gd.constants.png-filter
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('PNG_ALL_FILTERS', 248);

// End of gd v.8.2.6
