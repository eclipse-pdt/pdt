<?php

// Start of gd v.7.3.0

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
 * <td>boolean value. true
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
 * <td>T1Lib Support</td>
 * <td>boolean value. true
 * if T1Lib support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>GIF Read Support</td>
 * <td>boolean value. true
 * if support for reading GIF
 * images is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>GIF Create Support</td>
 * <td>boolean value. true
 * if support for creating GIF
 * images is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>JPEG Support</td>
 * <td>boolean value. true
 * if JPEG support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>PNG Support</td>
 * <td>boolean value. true
 * if PNG support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>WBMP Support</td>
 * <td>boolean value. true
 * if WBMP support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>XBM Support</td>
 * <td>boolean value. true
 * if XBM support is included.</td>
 * </tr>
 * <tr valign="top">
 * <td>WebP Support</td>
 * <td>boolean value. true
 * if WebP support is included.</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * Previous to PHP 5.3.0, the JPEG Support attribute was named
 * JPG Support.
 * </p>
 */
function gd_info () {}

/**
 * Draws an arc
 * @link http://www.php.net/manual/en/function.imagearc.php
 * @param resource $image 
 * @param int $cx x-coordinate of the center.
 * @param int $cy y-coordinate of the center.
 * @param int $width The arc width.
 * @param int $height The arc height.
 * @param int $start The arc start angle, in degrees.
 * @param int $end The arc end angle, in degrees.
 * 0° is located at the three-o'clock position, and the arc is drawn
 * clockwise.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagearc ($image, int $cx, int $cy, int $width, int $height, int $start, int $end, int $color) {}

/**
 * Draw an ellipse
 * @link http://www.php.net/manual/en/function.imageellipse.php
 * @param resource $image 
 * @param int $cx x-coordinate of the center.
 * @param int $cy y-coordinate of the center.
 * @param int $width The ellipse width.
 * @param int $height The ellipse height.
 * @param int $color The color of the ellipse. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imageellipse ($image, int $cx, int $cy, int $width, int $height, int $color) {}

/**
 * Draw a character horizontally
 * @link http://www.php.net/manual/en/function.imagechar.php
 * @param resource $image 
 * @param int $font 
 * @param int $x x-coordinate of the start.
 * @param int $y y-coordinate of the start.
 * @param string $c The character to draw.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagechar ($image, int $font, int $x, int $y, string $c, int $color) {}

/**
 * Draw a character vertically
 * @link http://www.php.net/manual/en/function.imagecharup.php
 * @param resource $image 
 * @param int $font 
 * @param int $x x-coordinate of the start.
 * @param int $y y-coordinate of the start.
 * @param string $c The character to draw.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagecharup ($image, int $font, int $x, int $y, string $c, int $color) {}

/**
 * Get the index of the color of a pixel
 * @link http://www.php.net/manual/en/function.imagecolorat.php
 * @param resource $image 
 * @param int $x x-coordinate of the point.
 * @param int $y y-coordinate of the point.
 * @return int the index of the color or false on failure.
 */
function imagecolorat ($image, int $x, int $y) {}

/**
 * Allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolorallocate.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int A color identifier or false if the allocation failed.
 */
function imagecolorallocate ($image, int $red, int $green, int $blue) {}

/**
 * Copy the palette from one image to another
 * @link http://www.php.net/manual/en/function.imagepalettecopy.php
 * @param resource $destination The destination image resource.
 * @param resource $source The source image resource.
 * @return void 
 */
function imagepalettecopy ($destination, $source) {}

/**
 * Create a new image from the image stream in the string
 * @link http://www.php.net/manual/en/function.imagecreatefromstring.php
 * @param string $image A string containing the image data.
 * @return resource An image resource will be returned on success. false is returned if
 * the image type is unsupported, the data is not in a recognised format,
 * or the image is corrupt and cannot be loaded.
 */
function imagecreatefromstring (string $image) {}

/**
 * Get the index of the closest color to the specified color
 * @link http://www.php.net/manual/en/function.imagecolorclosest.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int the index of the closest color, in the palette of the image, to
 * the specified one
 */
function imagecolorclosest ($image, int $red, int $green, int $blue) {}

/**
 * Get the index of the color which has the hue, white and blackness
 * @link http://www.php.net/manual/en/function.imagecolorclosesthwb.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int an integer with the index of the color which has 
 * the hue, white and blackness nearest the given color.
 */
function imagecolorclosesthwb ($image, int $red, int $green, int $blue) {}

/**
 * De-allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolordeallocate.php
 * @param resource $image 
 * @param int $color The color identifier.
 * @return bool true on success or false on failure
 */
function imagecolordeallocate ($image, int $color) {}

/**
 * Get the index of the specified color or its closest possible alternative
 * @link http://www.php.net/manual/en/function.imagecolorresolve.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int a color index.
 */
function imagecolorresolve ($image, int $red, int $green, int $blue) {}

/**
 * Get the index of the specified color
 * @link http://www.php.net/manual/en/function.imagecolorexact.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @return int the index of the specified color in the palette, or -1 if the
 * color does not exist.
 */
function imagecolorexact ($image, int $red, int $green, int $blue) {}

/**
 * Set the color for the specified palette index
 * @link http://www.php.net/manual/en/function.imagecolorset.php
 * @param resource $image 
 * @param int $index An index in the palette.
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha [optional] Value of alpha component.
 * @return void 
 */
function imagecolorset ($image, int $index, int $red, int $green, int $blue, int $alpha = null) {}

/**
 * Define a color as transparent
 * @link http://www.php.net/manual/en/function.imagecolortransparent.php
 * @param resource $image 
 * @param int $color [optional] gd.identifier.color
 * @return int The identifier of the new (or current, if none is specified)
 * transparent color is returned. If color
 * is not specified, and the image has no transparent color, the
 * returned identifier will be -1.
 */
function imagecolortransparent ($image, int $color = null) {}

/**
 * Find out the number of colors in an image's palette
 * @link http://www.php.net/manual/en/function.imagecolorstotal.php
 * @param resource $image 
 * @return int the number of colors in the specified image's palette or 0 for
 * truecolor images.
 */
function imagecolorstotal ($image) {}

/**
 * Get the colors for an index
 * @link http://www.php.net/manual/en/function.imagecolorsforindex.php
 * @param resource $image 
 * @param int $index The color index.
 * @return array an associative array with red, green, blue and alpha keys that
 * contain the appropriate values for the specified color index.
 */
function imagecolorsforindex ($image, int $index) {}

/**
 * Copy part of an image
 * @link http://www.php.net/manual/en/function.imagecopy.php
 * @param resource $dst_im Destination image link resource.
 * @param resource $src_im Source image link resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $src_w Source width.
 * @param int $src_h Source height.
 * @return bool true on success or false on failure
 */
function imagecopy ($dst_im, $src_im, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_w, int $src_h) {}

/**
 * Copy and merge part of an image
 * @link http://www.php.net/manual/en/function.imagecopymerge.php
 * @param resource $dst_im Destination image link resource.
 * @param resource $src_im Source image link resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $src_w Source width.
 * @param int $src_h Source height.
 * @param int $pct The two images will be merged according to pct
 * which can range from 0 to 100. When pct = 0,
 * no action is taken, when 100 this function behaves identically
 * to imagecopy for pallete images, except for
 * ignoring alpha components, while it implements alpha transparency
 * for true colour images.
 * @return bool true on success or false on failure
 */
function imagecopymerge ($dst_im, $src_im, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_w, int $src_h, int $pct) {}

/**
 * Copy and merge part of an image with gray scale
 * @link http://www.php.net/manual/en/function.imagecopymergegray.php
 * @param resource $dst_im Destination image link resource.
 * @param resource $src_im Source image link resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $src_w Source width.
 * @param int $src_h Source height.
 * @param int $pct The src_im will be changed to grayscale according 
 * to pct where 0 is fully grayscale and 100 is 
 * unchanged. When pct = 100 this function behaves 
 * identically to imagecopy for pallete images, except for
 * ignoring alpha components, while
 * it implements alpha transparency for true colour images.
 * @return bool true on success or false on failure
 */
function imagecopymergegray ($dst_im, $src_im, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_w, int $src_h, int $pct) {}

/**
 * Copy and resize part of an image
 * @link http://www.php.net/manual/en/function.imagecopyresized.php
 * @param resource $dst_image Destination image link resource.
 * @param resource $src_image Source image link resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $dst_w Destination width.
 * @param int $dst_h Destination height.
 * @param int $src_w Source width.
 * @param int $src_h Source height.
 * @return bool true on success or false on failure
 */
function imagecopyresized ($dst_image, $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $dst_w, int $dst_h, int $src_w, int $src_h) {}

/**
 * Create a new palette based image
 * @link http://www.php.net/manual/en/function.imagecreate.php
 * @param int $width The image width.
 * @param int $height The image height.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreate (int $width, int $height) {}

/**
 * Create a new true color image
 * @link http://www.php.net/manual/en/function.imagecreatetruecolor.php
 * @param int $width Image width.
 * @param int $height Image height.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatetruecolor (int $width, int $height) {}

/**
 * Finds whether an image is a truecolor image
 * @link http://www.php.net/manual/en/function.imageistruecolor.php
 * @param resource $image 
 * @return bool true if the image is truecolor, false
 * otherwise.
 */
function imageistruecolor ($image) {}

/**
 * Convert a true color image to a palette image
 * @link http://www.php.net/manual/en/function.imagetruecolortopalette.php
 * @param resource $image 
 * @param bool $dither Indicates if the image should be dithered - if it is true then
 * dithering will be used which will result in a more speckled image but
 * with better color approximation.
 * @param int $ncolors Sets the maximum number of colors that should be retained in the palette.
 * @return bool true on success or false on failure
 */
function imagetruecolortopalette ($image, bool $dither, int $ncolors) {}

/**
 * Converts a palette based image to true color
 * @link http://www.php.net/manual/en/function.imagepalettetotruecolor.php
 * @param resource $src 
 * @return bool true if the convertion was complete, or if the source image already 
 * is a true color image, otherwise false is returned.
 */
function imagepalettetotruecolor ($src) {}

/**
 * Set the thickness for line drawing
 * @link http://www.php.net/manual/en/function.imagesetthickness.php
 * @param resource $image 
 * @param int $thickness Thickness, in pixels.
 * @return bool true on success or false on failure
 */
function imagesetthickness ($image, int $thickness) {}

/**
 * Draw a partial arc and fill it
 * @link http://www.php.net/manual/en/function.imagefilledarc.php
 * @param resource $image 
 * @param int $cx x-coordinate of the center.
 * @param int $cy y-coordinate of the center.
 * @param int $width The arc width.
 * @param int $height The arc height.
 * @param int $start The arc start angle, in degrees.
 * @param int $end The arc end angle, in degrees.
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
function imagefilledarc ($image, int $cx, int $cy, int $width, int $height, int $start, int $end, int $color, int $style) {}

/**
 * Draw a filled ellipse
 * @link http://www.php.net/manual/en/function.imagefilledellipse.php
 * @param resource $image 
 * @param int $cx x-coordinate of the center.
 * @param int $cy y-coordinate of the center.
 * @param int $width The ellipse width.
 * @param int $height The ellipse height.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilledellipse ($image, int $cx, int $cy, int $width, int $height, int $color) {}

/**
 * Set the blending mode for an image
 * @link http://www.php.net/manual/en/function.imagealphablending.php
 * @param resource $image 
 * @param bool $blendmode Whether to enable the blending mode or not. On true color images 
 * the default value is true otherwise the default value is false
 * @return bool true on success or false on failure
 */
function imagealphablending ($image, bool $blendmode) {}

/**
 * Set the flag to save full alpha channel information (as opposed to single-color transparency) when saving PNG images
 * @link http://www.php.net/manual/en/function.imagesavealpha.php
 * @param resource $image 
 * @param bool $saveflag Whether to save the alpha channel or not. Default to false.
 * @return bool true on success or false on failure
 */
function imagesavealpha ($image, bool $saveflag) {}

/**
 * Allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolorallocatealpha.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return int A color identifier or false if the allocation failed.
 */
function imagecolorallocatealpha ($image, int $red, int $green, int $blue, int $alpha) {}

/**
 * Get the index of the specified color + alpha or its closest possible alternative
 * @link http://www.php.net/manual/en/function.imagecolorresolvealpha.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return int a color index.
 */
function imagecolorresolvealpha ($image, int $red, int $green, int $blue, int $alpha) {}

/**
 * Get the index of the closest color to the specified color + alpha
 * @link http://www.php.net/manual/en/function.imagecolorclosestalpha.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return int the index of the closest color in the palette.
 */
function imagecolorclosestalpha ($image, int $red, int $green, int $blue, int $alpha) {}

/**
 * Get the index of the specified color + alpha
 * @link http://www.php.net/manual/en/function.imagecolorexactalpha.php
 * @param resource $image 
 * @param int $red Value of red component.
 * @param int $green Value of green component.
 * @param int $blue Value of blue component.
 * @param int $alpha A value between 0 and 127.
 * 0 indicates completely opaque while 
 * 127 indicates completely transparent.
 * @return int the index of the specified color+alpha in the palette of the
 * image, or -1 if the color does not exist in the image's palette.
 */
function imagecolorexactalpha ($image, int $red, int $green, int $blue, int $alpha) {}

/**
 * Copy and resize part of an image with resampling
 * @link http://www.php.net/manual/en/function.imagecopyresampled.php
 * @param resource $dst_image Destination image link resource.
 * @param resource $src_image Source image link resource.
 * @param int $dst_x x-coordinate of destination point.
 * @param int $dst_y y-coordinate of destination point.
 * @param int $src_x x-coordinate of source point.
 * @param int $src_y y-coordinate of source point.
 * @param int $dst_w Destination width.
 * @param int $dst_h Destination height.
 * @param int $src_w Source width.
 * @param int $src_h Source height.
 * @return bool true on success or false on failure
 */
function imagecopyresampled ($dst_image, $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $dst_w, int $dst_h, int $src_w, int $src_h) {}

/**
 * Captures a window
 * @link http://www.php.net/manual/en/function.imagegrabwindow.php
 * @param int $window_handle The HWND window ID.
 * @param int $client_area [optional] Include the client area of the application window.
 * @return resource an image resource identifier on success, false on failure.
 */
function imagegrabwindow (int $window_handle, int $client_area = null) {}

/**
 * Captures the whole screen
 * @link http://www.php.net/manual/en/function.imagegrabscreen.php
 * @return resource an image resource identifier on success, false on failure.
 */
function imagegrabscreen () {}

/**
 * Rotate an image with a given angle
 * @link http://www.php.net/manual/en/function.imagerotate.php
 * @param resource $image 
 * @param float $angle Rotation angle, in degrees. The rotation angle is interpreted as the
 * number of degrees to rotate the image anticlockwise.
 * @param int $bgd_color Specifies the color of the uncovered zone after the rotation
 * @param int $ignore_transparent [optional] If set and non-zero, transparent colors are ignored (otherwise kept).
 * @return resource an image resource for the rotated image, or false on failure.
 */
function imagerotate ($image, float $angle, int $bgd_color, int $ignore_transparent = null) {}

/**
 * Flips an image using a given mode
 * @link http://www.php.net/manual/en/function.imageflip.php
 * @param resource $image 
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
function imageflip ($image, int $mode) {}

/**
 * Should antialias functions be used or not
 * @link http://www.php.net/manual/en/function.imageantialias.php
 * @param resource $image 
 * @param bool $enabled Whether to enable antialiasing or not.
 * @return bool true on success or false on failure
 */
function imageantialias ($image, bool $enabled) {}

/**
 * Crop an image to the given rectangle
 * @link http://www.php.net/manual/en/function.imagecrop.php
 * @param resource $image 
 * @param array $rect The cropping rectangle as array with keys
 * x, y, width and
 * height.
 * @return resource Return cropped image resource on success or false on failure.
 */
function imagecrop ($image, array $rect) {}

/**
 * Crop an image automatically using one of the available modes
 * @link http://www.php.net/manual/en/function.imagecropauto.php
 * @param resource $image 
 * @param int $mode [optional] <p>
 * One of the following constants:
 * </p>
 * <p>
 * IMG_CROP_DEFAULT
 * <br>
 * Attempts to use IMG_CROP_TRANSPARENT and if it
 * fails it falls back to IMG_CROP_SIDES.
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
 * @param int $color [optional] <p>
 * Either an RGB color value or a palette index.
 * </p>
 * <p>
 * Used only in IMG_CROP_THRESHOLD mode.
 * </p>
 * @return resource a cropped image resource on success or false on failure.
 * If no cropping would occur, or the complete image would be cropped, that is
 * treated as failure, i.e. imagecrop returns false.
 */
function imagecropauto ($image, int $mode = null, float $threshold = null, int $color = null) {}

/**
 * Scale an image using the given new width and height
 * @link http://www.php.net/manual/en/function.imagescale.php
 * @param resource $image 
 * @param int $new_width The width to scale the image to.
 * @param int $new_height [optional] <p>
 * The height to scale the image to. If omitted or negative, the aspect
 * ratio will be preserved.
 * </p>
 * <p>
 * You should always provide the height if using PHP 5.5.18 or earlier, or
 * PHP 5.6.2 or earlier, as the aspect ratio calculation was incorrect.
 * </p>
 * @param int $mode [optional] One of IMG_NEAREST_NEIGHBOUR,
 * IMG_BILINEAR_FIXED,
 * IMG_BICUBIC,
 * IMG_BICUBIC_FIXED or anything else (will use two
 * pass).
 * IMG_WEIGHTED4 is not yet supported.
 * @return resource Return the scaled image resource on success or false on failure.
 */
function imagescale ($image, int $new_width, int $new_height = null, int $mode = null) {}

/**
 * Return an image containing the affine transformed src image, using an optional clipping area
 * @link http://www.php.net/manual/en/function.imageaffine.php
 * @param resource $image 
 * @param array $affine Array with keys 0 to 5.
 * @param array $clip [optional] Array with keys "x", "y", "width" and "height".
 * @return resource Return affined image resource on success or false on failure.
 */
function imageaffine ($image, array $affine, array $clip = null) {}

/**
 * Concatenate two affine transformation matrices
 * @link http://www.php.net/manual/en/function.imageaffinematrixconcat.php
 * @param array $m1 An affine transformation matrix (an array with keys
 * 0 to 5 and float values).
 * @param array $m2 An affine transformation matrix (an array with keys
 * 0 to 5 and float values).
 * @return array An affine transformation matrix (an array with keys
 * 0 to 5 and float values)
 * or false on failure.
 */
function imageaffinematrixconcat (array $m1, array $m2) {}

/**
 * Get an affine transformation matrix
 * @link http://www.php.net/manual/en/function.imageaffinematrixget.php
 * @param int $type One of the IMG_AFFINE_&#42; constants.
 * @param mixed $options [optional] <p>
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
 * @return array An affine transformation matrix (an array with keys
 * 0 to 5 and float values)
 * or false on failure.
 */
function imageaffinematrixget (int $type, $options = null) {}

/**
 * Set the interpolation method
 * @link http://www.php.net/manual/en/function.imagesetinterpolation.php
 * @param resource $image 
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
function imagesetinterpolation ($image, int $method = null) {}

/**
 * Set the tile image for filling
 * @link http://www.php.net/manual/en/function.imagesettile.php
 * @param resource $image 
 * @param resource $tile The image resource to be used as a tile.
 * @return bool true on success or false on failure
 */
function imagesettile ($image, $tile) {}

/**
 * Set the brush image for line drawing
 * @link http://www.php.net/manual/en/function.imagesetbrush.php
 * @param resource $image 
 * @param resource $brush An image resource.
 * @return bool true on success or false on failure
 */
function imagesetbrush ($image, $brush) {}

/**
 * Set the style for line drawing
 * @link http://www.php.net/manual/en/function.imagesetstyle.php
 * @param resource $image 
 * @param array $style An array of pixel colors. You can use the 
 * IMG_COLOR_TRANSPARENT constant to add a 
 * transparent pixel.
 * Note that style must not be an empty array.
 * @return bool true on success or false on failure
 */
function imagesetstyle ($image, array $style) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefrompng.php
 * @param string $filename Path to the PNG image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefrompng (string $filename) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromwebp.php
 * @param string $filename Path to the WebP image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromwebp (string $filename) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgif.php
 * @param string $filename Path to the GIF image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgif (string $filename) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromjpeg.php
 * @param string $filename Path to the JPEG image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromjpeg (string $filename) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromwbmp.php
 * @param string $filename Path to the WBMP image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromwbmp (string $filename) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromxbm.php
 * @param string $filename Path to the XBM image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromxbm (string $filename) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromxpm.php
 * @param string $filename Path to the XPM image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromxpm (string $filename) {}

/**
 * Create a new image from GD file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd.php
 * @param string $filename Path to the GD file.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgd (string $filename) {}

/**
 * Create a new image from GD2 file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd2.php
 * @param string $filename Path to the GD2 image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgd2 (string $filename) {}

/**
 * Create a new image from a given part of GD2 file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd2part.php
 * @param string $filename Path to the GD2 image.
 * @param int $srcX x-coordinate of source point.
 * @param int $srcY y-coordinate of source point.
 * @param int $width Source width.
 * @param int $height Source height.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgd2part (string $filename, int $srcX, int $srcY, int $width, int $height) {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefrombmp.php
 * @param string $filename Path to the BMP image.
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefrombmp (string $filename) {}

/**
 * Output a PNG image to either the browser or a file
 * @link http://www.php.net/manual/en/function.imagepng.php
 * @param resource $image 
 * @param mixed $to [optional] <p>The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.</p>
 * <p>
 * null is invalid if the quality and
 * filters arguments are not used.
 * </p>
 * @param int $quality [optional] Compression level: from 0 (no compression) to 9. The current default is 6.
 * For more information see the zlib manual.
 * @param int $filters [optional] Allows reducing the PNG file size. It is a bitmask field which may be
 * set to any combination of the PNG_FILTER_XXX 
 * constants. PNG_NO_FILTER or 
 * PNG_ALL_FILTERS may also be used to respectively
 * disable or activate all filters.
 * @return bool true on success or false on failure
 */
function imagepng ($image, $to = null, int $quality = null, int $filters = null) {}

/**
 * Output a WebP image to browser or file
 * @link http://www.php.net/manual/en/function.imagewebp.php
 * @param resource $image 
 * @param mixed $to [optional] The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.
 * @param int $quality [optional] quality ranges from 0 (worst
 * quality, smaller file) to 100 (best quality, biggest file).
 * @return bool true on success or false on failure
 */
function imagewebp ($image, $to = null, int $quality = null) {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagegif.php
 * @param resource $image 
 * @param mixed $to [optional] The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.
 * @return bool true on success or false on failure
 */
function imagegif ($image, $to = null) {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagejpeg.php
 * @param resource $image 
 * @param mixed $to [optional] <p>The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.</p>
 * <p>
 * To skip this argument in order to provide the 
 * quality parameter, use null.
 * </p>
 * @param int $quality [optional] quality is optional, and ranges from 0 (worst
 * quality, smaller file) to 100 (best quality, biggest file). The 
 * default is the default IJG quality value (about 75).
 * @return bool true on success or false on failure
 */
function imagejpeg ($image, $to = null, int $quality = null) {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagewbmp.php
 * @param resource $image 
 * @param mixed $to [optional] The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.
 * @param int $foreground [optional] You can set the foreground color with this parameter by setting an
 * identifier obtained from imagecolorallocate.
 * The default foreground color is black.
 * @return bool true on success or false on failure
 */
function imagewbmp ($image, $to = null, int $foreground = null) {}

/**
 * Output GD image to browser or file
 * @link http://www.php.net/manual/en/function.imagegd.php
 * @param resource $image 
 * @param mixed $to [optional] The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.
 * @return bool true on success or false on failure
 */
function imagegd ($image, $to = null) {}

/**
 * Output GD2 image to browser or file
 * @link http://www.php.net/manual/en/function.imagegd2.php
 * @param resource $image 
 * @param mixed $to [optional] The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.
 * @param int $chunk_size [optional] Chunk size.
 * @param int $type [optional] Either IMG_GD2_RAW or 
 * IMG_GD2_COMPRESSED. Default is 
 * IMG_GD2_RAW.
 * @return bool true on success or false on failure
 */
function imagegd2 ($image, $to = null, int $chunk_size = null, int $type = null) {}

/**
 * Output a BMP image to browser or file
 * @link http://www.php.net/manual/en/function.imagebmp.php
 * @param resource $image 
 * @param mixed $to [optional] <p>The path or an open stream resource (which is automatically being closed after this function returns) to save the file to. If not set or null, the raw image stream will be outputted directly.</p>
 * <p>
 * null is invalid if the compressed arguments is
 * not used.
 * </p>
 * @param bool $compressed [optional] Whether the BMP should be compressed with run-length encoding (RLE), or not.
 * @return bool true on success or false on failure
 */
function imagebmp ($image, $to = null, bool $compressed = null) {}

/**
 * Destroy an image
 * @link http://www.php.net/manual/en/function.imagedestroy.php
 * @param resource $image 
 * @return bool true on success or false on failure
 */
function imagedestroy ($image) {}

/**
 * Apply a gamma correction to a GD image
 * @link http://www.php.net/manual/en/function.imagegammacorrect.php
 * @param resource $image 
 * @param float $inputgamma The input gamma.
 * @param float $outputgamma The output gamma.
 * @return bool true on success or false on failure
 */
function imagegammacorrect ($image, float $inputgamma, float $outputgamma) {}

/**
 * Flood fill
 * @link http://www.php.net/manual/en/function.imagefill.php
 * @param resource $image 
 * @param int $x x-coordinate of start point.
 * @param int $y y-coordinate of start point.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefill ($image, int $x, int $y, int $color) {}

/**
 * Draw a filled polygon
 * @link http://www.php.net/manual/en/function.imagefilledpolygon.php
 * @param resource $image 
 * @param array $points An array containing the x and y
 * coordinates of the polygons vertices consecutively.
 * @param int $num_points Total number of vertices, which must be at least 3.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilledpolygon ($image, array $points, int $num_points, int $color) {}

/**
 * Draw a filled rectangle
 * @link http://www.php.net/manual/en/function.imagefilledrectangle.php
 * @param resource $image 
 * @param int $x1 x-coordinate for point 1.
 * @param int $y1 y-coordinate for point 1.
 * @param int $x2 x-coordinate for point 2.
 * @param int $y2 y-coordinate for point 2.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilledrectangle ($image, int $x1, int $y1, int $x2, int $y2, int $color) {}

/**
 * Flood fill to specific color
 * @link http://www.php.net/manual/en/function.imagefilltoborder.php
 * @param resource $image 
 * @param int $x x-coordinate of start.
 * @param int $y y-coordinate of start.
 * @param int $border The border color. gd.identifier.color
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagefilltoborder ($image, int $x, int $y, int $border, int $color) {}

/**
 * Get font width
 * @link http://www.php.net/manual/en/function.imagefontwidth.php
 * @param int $font 
 * @return int the pixel width of the font.
 */
function imagefontwidth (int $font) {}

/**
 * Get font height
 * @link http://www.php.net/manual/en/function.imagefontheight.php
 * @param int $font 
 * @return int the pixel height of the font.
 */
function imagefontheight (int $font) {}

/**
 * Enable or disable interlace
 * @link http://www.php.net/manual/en/function.imageinterlace.php
 * @param resource $image 
 * @param int $interlace [optional] If non-zero, the image will be interlaced, else the interlace bit is
 * turned off.
 * @return int 1 if the interlace bit is set for the image, 0 otherwise.
 */
function imageinterlace ($image, int $interlace = null) {}

/**
 * Draw a line
 * @link http://www.php.net/manual/en/function.imageline.php
 * @param resource $image 
 * @param int $x1 x-coordinate for first point.
 * @param int $y1 y-coordinate for first point.
 * @param int $x2 x-coordinate for second point.
 * @param int $y2 y-coordinate for second point.
 * @param int $color The line color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imageline ($image, int $x1, int $y1, int $x2, int $y2, int $color) {}

/**
 * Load a new font
 * @link http://www.php.net/manual/en/function.imageloadfont.php
 * @param string $file <p>
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
 * @return int The font identifier which is always bigger than 5 to avoid conflicts with
 * built-in fonts or false on errors.
 */
function imageloadfont (string $file) {}

/**
 * Draws a polygon
 * @link http://www.php.net/manual/en/function.imagepolygon.php
 * @param resource $image 
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
 * @param int $num_points Total number of points (vertices).
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagepolygon ($image, array $points, int $num_points, int $color) {}

/**
 * Draws an open polygon
 * @link http://www.php.net/manual/en/function.imageopenpolygon.php
 * @param resource $image 
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
 * @param int $num_points Total number of points (vertices).
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imageopenpolygon ($image, array $points, int $num_points, int $color) {}

/**
 * Draw a rectangle
 * @link http://www.php.net/manual/en/function.imagerectangle.php
 * @param resource $image 
 * @param int $x1 Upper left x coordinate.
 * @param int $y1 Upper left y coordinate
 * 0, 0 is the top left corner of the image.
 * @param int $x2 Bottom right x coordinate.
 * @param int $y2 Bottom right y coordinate.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagerectangle ($image, int $x1, int $y1, int $x2, int $y2, int $color) {}

/**
 * Set a single pixel
 * @link http://www.php.net/manual/en/function.imagesetpixel.php
 * @param resource $image 
 * @param int $x x-coordinate.
 * @param int $y y-coordinate.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagesetpixel ($image, int $x, int $y, int $color) {}

/**
 * Draw a string horizontally
 * @link http://www.php.net/manual/en/function.imagestring.php
 * @param resource $image 
 * @param int $font 
 * @param int $x x-coordinate of the upper left corner.
 * @param int $y y-coordinate of the upper left corner.
 * @param string $string The string to be written.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagestring ($image, int $font, int $x, int $y, string $string, int $color) {}

/**
 * Draw a string vertically
 * @link http://www.php.net/manual/en/function.imagestringup.php
 * @param resource $image 
 * @param int $font 
 * @param int $x x-coordinate of the bottom left corner.
 * @param int $y y-coordinate of the bottom left corner.
 * @param string $string The string to be written.
 * @param int $color gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagestringup ($image, int $font, int $x, int $y, string $string, int $color) {}

/**
 * Get image width
 * @link http://www.php.net/manual/en/function.imagesx.php
 * @param resource $image 
 * @return int Return the width of the image or false on 
 * errors.
 */
function imagesx ($image) {}

/**
 * Get image height
 * @link http://www.php.net/manual/en/function.imagesy.php
 * @param resource $image 
 * @return int Return the height of the image or false on 
 * errors.
 */
function imagesy ($image) {}

/**
 * Set the clipping rectangle
 * @link http://www.php.net/manual/en/function.imagesetclip.php
 * @param resource $im 
 * @param int $x1 The x-coordinate of the upper left corner.
 * @param int $y1 The y-coordinate of the upper left corner.
 * @param int $x2 The x-coordinate of the lower right corner.
 * @param int $y2 The y-coordinate of the lower right corner.
 * @return bool true on success or false on failure
 */
function imagesetclip ($im, int $x1, int $y1, int $x2, int $y2) {}

/**
 * Get the clipping rectangle
 * @link http://www.php.net/manual/en/function.imagegetclip.php
 * @param resource $im 
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
function imagegetclip ($im) {}

/**
 * Draw a dashed line
 * @link http://www.php.net/manual/en/function.imagedashedline.php
 * @param resource $image 
 * @param int $x1 Upper left x coordinate.
 * @param int $y1 Upper left y coordinate 0, 0 is the top left corner of the image.
 * @param int $x2 Bottom right x coordinate.
 * @param int $y2 Bottom right y coordinate.
 * @param int $color The fill color. gd.identifier.color
 * @return bool true on success or false on failure
 */
function imagedashedline ($image, int $x1, int $y1, int $x2, int $y2, int $color) {}

/**
 * Give the bounding box of a text using TrueType fonts
 * @link http://www.php.net/manual/en/function.imagettfbbox.php
 * @param float $size The font size in points.
 * @param float $angle Angle in degrees in which text will be measured.
 * @param string $fontfile 
 * @param string $text The string to be measured.
 * @return array imagettfbbox returns an array with 8
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
function imagettfbbox (float $size, float $angle, string $fontfile, string $text) {}

/**
 * Write text to the image using TrueType fonts
 * @link http://www.php.net/manual/en/function.imagettftext.php
 * @param resource $image 
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
 * @param string $fontfile 
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
 * @return array an array with 8 elements representing four points making the
 * bounding box of the text. The order of the points is lower left, lower 
 * right, upper right, upper left. The points are relative to the text
 * regardless of the angle, so "upper left" means in the top left-hand 
 * corner when you see the text horizontally.
 * Returns false on error.
 */
function imagettftext ($image, float $size, float $angle, int $x, int $y, int $color, string $fontfile, string $text) {}

/**
 * Give the bounding box of a text using fonts via freetype2
 * @link http://www.php.net/manual/en/function.imageftbbox.php
 * @param float $size The font size in points.
 * @param float $angle Angle in degrees in which text will be 
 * measured.
 * @param string $fontfile The name of the TrueType font file (can be a URL). Depending on
 * which version of the GD library that PHP is using, it may attempt to
 * search for files that do not begin with a leading '/' by appending
 * '.ttf' to the filename and searching along a library-defined font path.
 * @param string $text The string to be measured.
 * @param array $extrainfo [optional] <table>
 * Possible array indexes for extrainfo
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
 * @return array imageftbbox returns an array with 8
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
 */
function imageftbbox (float $size, float $angle, string $fontfile, string $text, array $extrainfo = null) {}

/**
 * Write text to the image using fonts using FreeType 2
 * @link http://www.php.net/manual/en/function.imagefttext.php
 * @param resource $image 
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
 * @param string $fontfile <p>
 * The path to the TrueType font you wish to use.
 * </p>
 * <p>
 * Depending on which version of the GD library PHP is using, when
 * fontfile does not begin with a leading
 * / then .ttf will be appended
 * to the filename and the library will attempt to search for that
 * filename along a library-defined font path.
 * </p>
 * <p>
 * When using versions of the GD library lower than 2.0.18, a space character,
 * rather than a semicolon, was used as the 'path separator' for different font files.
 * Unintentional use of this feature will result in the warning message:
 * Warning: Could not find/open font. For these affected versions, the
 * only solution is moving the font to a path which does not contain spaces.
 * </p>
 * <p>
 * In many cases where a font resides in the same directory as the script using it
 * the following trick will alleviate any include problems.
 * <pre>
 * <code>&lt;?php
 * &#47;&#47; Set the enviroment variable for GD
 * putenv('GDFONTPATH=' . realpath('.'));
 * &#47;&#47; Name the font to be used (note the lack of the .ttf extension)
 * $font = 'SomeFont';
 * ?&gt;</code>
 * </pre>
 * </p>
 * @param string $text Text to be inserted into image.
 * @param array $extrainfo [optional] <table>
 * Possible array indexes for extrainfo
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
 * @return array This function returns an array defining the four points of the box, starting in the lower left and moving counter-clockwise:
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
 */
function imagefttext ($image, float $size, float $angle, int $x, int $y, int $color, string $fontfile, string $text, array $extrainfo = null) {}

/**
 * Return the image types supported by this PHP build
 * @link http://www.php.net/manual/en/function.imagetypes.php
 * @return int a bit-field corresponding to the image formats supported by the
 * version of GD linked into PHP. The following bits are returned,
 * IMG_BMP |
 * IMG_GIF | IMG_JPG |
 * IMG_PNG | IMG_WBMP | 
 * IMG_XPM | IMG_WEBP.
 */
function imagetypes () {}

/**
 * Convert JPEG image file to WBMP image file
 * @link http://www.php.net/manual/en/function.jpeg2wbmp.php
 * @param string $jpegname Path to JPEG file.
 * @param string $wbmpname Path to destination WBMP file.
 * @param int $dest_height Destination image height.
 * @param int $dest_width Destination image width.
 * @param int $threshold Threshold value, between 0 and 8 (inclusive).
 * @return bool true on success or false on failure
 * @deprecated 
 */
function jpeg2wbmp (string $jpegname, string $wbmpname, int $dest_height, int $dest_width, int $threshold) {}

/**
 * Convert PNG image file to WBMP image file
 * @link http://www.php.net/manual/en/function.png2wbmp.php
 * @param string $pngname Path to PNG file.
 * @param string $wbmpname Path to destination WBMP file.
 * @param int $dest_height Destination image height.
 * @param int $dest_width Destination image width.
 * @param int $threshold Threshold value, between 0 and 8 (inclusive).
 * @return bool true on success or false on failure
 * @deprecated 
 */
function png2wbmp (string $pngname, string $wbmpname, int $dest_height, int $dest_width, int $threshold) {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.image2wbmp.php
 * @param resource $image 
 * @param string $filename [optional] Path to the saved file. If not given, the raw image stream will be
 * output directly.
 * @param int $foreground [optional] You can set the foreground color with this parameter by setting an
 * identifier obtained from imagecolorallocate.
 * The default foreground color is black.
 * @return bool true on success or false on failure
 * @deprecated 
 */
function image2wbmp ($image, string $filename = null, int $foreground = null) {}

/**
 * Set the alpha blending flag to use layering effects
 * @link http://www.php.net/manual/en/function.imagelayereffect.php
 * @param resource $image 
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
function imagelayereffect ($image, int $effect) {}

/**
 * Output an XBM image to browser or file
 * @link http://www.php.net/manual/en/function.imagexbm.php
 * @param resource $image 
 * @param string $filename <p>The path to save the file to. If not set or null, the raw image stream will be outputted directly.</p>
 * <p>
 * The filename (without the .xbm extension) is also
 * used for the C identifiers of the XBM, whereby non
 * alphanumeric characters of the current locale are substituted by
 * underscores. If filename is set to null,
 * image is used to build the C identifiers.
 * </p>
 * @param int $foreground [optional] You can set the foreground color with this parameter by setting an
 * identifier obtained from imagecolorallocate.
 * The default foreground color is black. All other colors are treated as
 * background.
 * @return bool true on success or false on failure
 */
function imagexbm ($image, string $filename, int $foreground = null) {}

/**
 * Makes the colors of the palette version of an image more closely match the true color version
 * @link http://www.php.net/manual/en/function.imagecolormatch.php
 * @param resource $image1 A truecolor image link resource.
 * @param resource $image2 A palette image link resource pointing to an image that has the same
 * size as image1.
 * @return bool true on success or false on failure
 */
function imagecolormatch ($image1, $image2) {}

/**
 * Applies a filter to an image
 * @link http://www.php.net/manual/en/function.imagefilter.php
 * @param resource $image 
 * @param int $filtertype <p>
 * filtertype can be one of the following:
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
 * of the image. Use arg1 to set the level of
 * brightness. The range for the brightness is -255 to 255.
 * <br>
 * IMG_FILTER_CONTRAST: Changes the contrast of
 * the image. Use arg1 to set the level of
 * contrast.
 * <br>
 * IMG_FILTER_COLORIZE: Like
 * IMG_FILTER_GRAYSCALE, except you can specify the
 * color. Use arg1, arg2 and
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
 * Use arg1 to set the level of smoothness.
 * <br>
 * IMG_FILTER_PIXELATE: Applies pixelation effect 
 * to the image, use arg1 to set the block size 
 * and arg2 to set the pixelation effect mode.
 * </p>
 * </p>
 * @param int $arg1 [optional] <br>
 * IMG_FILTER_BRIGHTNESS: Brightness level.
 * <br>
 * IMG_FILTER_CONTRAST: Contrast level.
 * <br>
 * IMG_FILTER_COLORIZE: Value of red component.
 * <br>
 * IMG_FILTER_SMOOTH: Smoothness level.
 * <br>
 * IMG_FILTER_PIXELATE: Block size in pixels.
 * @param int $arg2 [optional] <br>
 * IMG_FILTER_COLORIZE: Value of green component.
 * <br>
 * IMG_FILTER_PIXELATE: Whether to use advanced pixelation 
 * effect or not (defaults to false).
 * @param int $arg3 [optional] <br>
 * IMG_FILTER_COLORIZE: Value of blue component.
 * @param int $arg4 [optional] <br>
 * IMG_FILTER_COLORIZE: Alpha channel, A value 
 * between 0 and 127. 0 indicates completely opaque while 127 indicates 
 * completely transparent.
 * @return bool true on success or false on failure
 */
function imagefilter ($image, int $filtertype, int $arg1 = null, int $arg2 = null, int $arg3 = null, int $arg4 = null) {}

/**
 * Apply a 3x3 convolution matrix, using coefficient and offset
 * @link http://www.php.net/manual/en/function.imageconvolution.php
 * @param resource $image 
 * @param array $matrix A 3x3 matrix: an array of three arrays of three floats.
 * @param float $div The divisor of the result of the convolution, used for normalization.
 * @param float $offset Color offset.
 * @return bool true on success or false on failure
 */
function imageconvolution ($image, array $matrix, float $div, float $offset) {}

/**
 * Get or set the resolution of the image
 * @link http://www.php.net/manual/en/function.imageresolution.php
 * @param resource $image 
 * @param int $res_x [optional] The horizontal resolution in DPI.
 * @param int $res_y [optional] The vertical resolution in DPI.
 * @return mixed When used as getter (that is without the optional parameters), it returns
 * true on success, or false on failure.
 * When used as setter (that is with one or both optional parameters given),
 * it returns an indexed array of the horizontal and vertical resolution on
 * success, or false on failure.
 */
function imageresolution ($image, int $res_x = null, int $res_y = null) {}


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
 * Available as of PHP 5.6.25 and PHP 7.0.10, respectively.
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_WEBP', 32);

/**
 * gd.constants.types
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_BMP', 64);

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
define ('GD_BUNDLED', 1);

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
 * (Available as of PHP 5.3.0)
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_PIXELATE', 11);

/**
 * The GD version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_VERSION', "2.0.35");

/**
 * The GD major version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_MAJOR_VERSION', 2);

/**
 * The GD minor version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_MINOR_VERSION', 0);

/**
 * The GD release version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://www.php.net/manual/en/image.constants.php
 */
define ('GD_RELEASE_VERSION', 35);

/**
 * The GD "extra" version (beta/rc..) PHP was compiled against.
 * (Available as of PHP 5.2.4)
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

// End of gd v.7.3.0
