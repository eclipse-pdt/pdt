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
 * @return array Returns an associative array.
 * <p><table>
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
 * </table></p>
 */
function gd_info (): array {}

/**
 * Load a new font
 * @link http://www.php.net/manual/en/function.imageloadfont.php
 * @param string $filename 
 * @return GdFont|false Returns an GdFont instance, or false on failure.
 */
function imageloadfont (string $filename): GdFont|false {}

/**
 * Set the style for line drawing
 * @link http://www.php.net/manual/en/function.imagesetstyle.php
 * @param GdImage $image 
 * @param array $style 
 * @return bool Returns true on success or false on failure.
 */
function imagesetstyle (GdImage $image, array $style): bool {}

/**
 * Create a new true color image
 * @link http://www.php.net/manual/en/function.imagecreatetruecolor.php
 * @param int $width 
 * @param int $height 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatetruecolor (int $width, int $height): GdImage|false {}

/**
 * Finds whether an image is a truecolor image
 * @link http://www.php.net/manual/en/function.imageistruecolor.php
 * @param GdImage $image 
 * @return bool Returns true if the image is truecolor, false
 * otherwise.
 */
function imageistruecolor (GdImage $image): bool {}

/**
 * Convert a true color image to a palette image
 * @link http://www.php.net/manual/en/function.imagetruecolortopalette.php
 * @param GdImage $image 
 * @param bool $dither 
 * @param int $num_colors 
 * @return bool Returns true on success or false on failure.
 */
function imagetruecolortopalette (GdImage $image, bool $dither, int $num_colors): bool {}

/**
 * Converts a palette based image to true color
 * @link http://www.php.net/manual/en/function.imagepalettetotruecolor.php
 * @param GdImage $image 
 * @return bool Returns true if the convertion was complete, or if the source image already 
 * is a true color image, otherwise false is returned.
 */
function imagepalettetotruecolor (GdImage $image): bool {}

/**
 * Makes the colors of the palette version of an image more closely match the true color version
 * @link http://www.php.net/manual/en/function.imagecolormatch.php
 * @param GdImage $image1 
 * @param GdImage $image2 
 * @return bool Returns true on success or false on failure.
 */
function imagecolormatch (GdImage $image1, GdImage $image2): bool {}

/**
 * Set the thickness for line drawing
 * @link http://www.php.net/manual/en/function.imagesetthickness.php
 * @param GdImage $image 
 * @param int $thickness 
 * @return bool Returns true on success or false on failure.
 */
function imagesetthickness (GdImage $image, int $thickness): bool {}

/**
 * Draw a filled ellipse
 * @link http://www.php.net/manual/en/function.imagefilledellipse.php
 * @param GdImage $image 
 * @param int $center_x 
 * @param int $center_y 
 * @param int $width 
 * @param int $height 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagefilledellipse (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $color): bool {}

/**
 * Draw a partial arc and fill it
 * @link http://www.php.net/manual/en/function.imagefilledarc.php
 * @param GdImage $image 
 * @param int $center_x 
 * @param int $center_y 
 * @param int $width 
 * @param int $height 
 * @param int $start_angle 
 * @param int $end_angle 
 * @param int $color 
 * @param int $style 
 * @return bool Returns true on success or false on failure.
 */
function imagefilledarc (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $start_angle, int $end_angle, int $color, int $style): bool {}

/**
 * Set the blending mode for an image
 * @link http://www.php.net/manual/en/function.imagealphablending.php
 * @param GdImage $image 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function imagealphablending (GdImage $image, bool $enable): bool {}

/**
 * Whether to retain full alpha channel information when saving images
 * @link http://www.php.net/manual/en/function.imagesavealpha.php
 * @param GdImage $image 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function imagesavealpha (GdImage $image, bool $enable): bool {}

/**
 * Set the alpha blending flag to use layering effects
 * @link http://www.php.net/manual/en/function.imagelayereffect.php
 * @param GdImage $image 
 * @param int $effect 
 * @return bool Returns true on success or false on failure.
 */
function imagelayereffect (GdImage $image, int $effect): bool {}

/**
 * Allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolorallocatealpha.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @param int $alpha 
 * @return int|false A color identifier or false if the allocation failed.
 */
function imagecolorallocatealpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int|false {}

/**
 * Get the index of the specified color + alpha or its closest possible alternative
 * @link http://www.php.net/manual/en/function.imagecolorresolvealpha.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @param int $alpha 
 * @return int Returns a color index.
 */
function imagecolorresolvealpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * Get the index of the closest color to the specified color + alpha
 * @link http://www.php.net/manual/en/function.imagecolorclosestalpha.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @param int $alpha 
 * @return int Returns the index of the closest color in the palette.
 */
function imagecolorclosestalpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * Get the index of the specified color + alpha
 * @link http://www.php.net/manual/en/function.imagecolorexactalpha.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @param int $alpha 
 * @return int Returns the index of the specified color+alpha in the palette of the
 * image, or -1 if the color does not exist in the image's palette.
 */
function imagecolorexactalpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * Copy and resize part of an image with resampling
 * @link http://www.php.net/manual/en/function.imagecopyresampled.php
 * @param GdImage $dst_image 
 * @param GdImage $src_image 
 * @param int $dst_x 
 * @param int $dst_y 
 * @param int $src_x 
 * @param int $src_y 
 * @param int $dst_width 
 * @param int $dst_height 
 * @param int $src_width 
 * @param int $src_height 
 * @return bool Returns true on success or false on failure.
 */
function imagecopyresampled (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $dst_width, int $dst_height, int $src_width, int $src_height): bool {}

/**
 * Rotate an image with a given angle
 * @link http://www.php.net/manual/en/function.imagerotate.php
 * @param GdImage $image 
 * @param float $angle 
 * @param int $background_color 
 * @param bool $ignore_transparent [optional] 
 * @return GdImage|false Returns an image object for the rotated image, or false on failure.
 */
function imagerotate (GdImage $image, float $angle, int $background_color, bool $ignore_transparent = false): GdImage|false {}

/**
 * Set the tile image for filling
 * @link http://www.php.net/manual/en/function.imagesettile.php
 * @param GdImage $image 
 * @param GdImage $tile 
 * @return bool Returns true on success or false on failure.
 */
function imagesettile (GdImage $image, GdImage $tile): bool {}

/**
 * Set the brush image for line drawing
 * @link http://www.php.net/manual/en/function.imagesetbrush.php
 * @param GdImage $image 
 * @param GdImage $brush 
 * @return bool Returns true on success or false on failure.
 */
function imagesetbrush (GdImage $image, GdImage $brush): bool {}

/**
 * Create a new palette based image
 * @link http://www.php.net/manual/en/function.imagecreate.php
 * @param int $width 
 * @param int $height 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreate (int $width, int $height): GdImage|false {}

/**
 * Return the image types supported by this PHP build
 * @link http://www.php.net/manual/en/function.imagetypes.php
 * @return int Returns a bit-field corresponding to the image formats supported by the
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
 * @param string $data 
 * @return GdImage|false An image object will be returned on success. false is returned if
 * the image type is unsupported, the data is not in a recognised format,
 * or the image is corrupt and cannot be loaded.
 */
function imagecreatefromstring (string $data): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromavif.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromavif (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgif.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromgif (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromjpeg.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromjpeg (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefrompng.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefrompng (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromwebp.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromwebp (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromxbm.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromxbm (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromxpm.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromxpm (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromwbmp.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromwbmp (string $filename): GdImage|false {}

/**
 * Create a new image from GD file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromgd (string $filename): GdImage|false {}

/**
 * Create a new image from GD2 file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd2.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromgd2 (string $filename): GdImage|false {}

/**
 * Create a new image from a given part of GD2 file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromgd2part.php
 * @param string $filename 
 * @param int $x 
 * @param int $y 
 * @param int $width 
 * @param int $height 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromgd2part (string $filename, int $x, int $y, int $width, int $height): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefrombmp.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefrombmp (string $filename): GdImage|false {}

/**
 * Create a new image from file or URL
 * @link http://www.php.net/manual/en/function.imagecreatefromtga.php
 * @param string $filename 
 * @return GdImage|false Returns an image object on success, false on errors.
 */
function imagecreatefromtga (string $filename): GdImage|false {}

/**
 * Output an XBM image to browser or file
 * @link http://www.php.net/manual/en/function.imagexbm.php
 * @param GdImage $image 
 * @param string|null $filename 
 * @param int|null $foreground_color [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagexbm (GdImage $image, ?string $filename, ?int $foreground_color = null): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imageavif.php
 * @param GdImage $image 
 * @param resource|string|null $file [optional] 
 * @param int $quality [optional] 
 * @param int $speed [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imageavif (GdImage $image, $file = null, int $quality = -1, int $speed = -1): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagegif.php
 * @param GdImage $image 
 * @param resource|string|null $file [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagegif (GdImage $image, $file = null): bool {}

/**
 * Output a PNG image to either the browser or a file
 * @link http://www.php.net/manual/en/function.imagepng.php
 * @param GdImage $image 
 * @param resource|string|null $file [optional] 
 * @param int $quality [optional] 
 * @param int $filters [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagepng (GdImage $image, $file = null, int $quality = -1, int $filters = -1): bool {}

/**
 * Output a WebP image to browser or file
 * @link http://www.php.net/manual/en/function.imagewebp.php
 * @param GdImage $image 
 * @param resource|string|null $file [optional] 
 * @param int $quality [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagewebp (GdImage $image, $file = null, int $quality = -1): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagejpeg.php
 * @param GdImage $image 
 * @param resource|string|null $file [optional] 
 * @param int $quality [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagejpeg (GdImage $image, $file = null, int $quality = -1): bool {}

/**
 * Output image to browser or file
 * @link http://www.php.net/manual/en/function.imagewbmp.php
 * @param GdImage $image 
 * @param resource|string|null $file [optional] 
 * @param int|null $foreground_color [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagewbmp (GdImage $image, $file = null, ?int $foreground_color = null): bool {}

/**
 * Output GD image to browser or file
 * @link http://www.php.net/manual/en/function.imagegd.php
 * @param GdImage $image 
 * @param string|null $file [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagegd (GdImage $image, ?string $file = null): bool {}

/**
 * Output GD2 image to browser or file
 * @link http://www.php.net/manual/en/function.imagegd2.php
 * @param GdImage $image 
 * @param string|null $file [optional] 
 * @param int $chunk_size [optional] 
 * @param int $mode [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagegd2 (GdImage $image, ?string $file = null, int $chunk_size = 128, int $mode = IMG_GD2_RAW): bool {}

/**
 * Output a BMP image to browser or file
 * @link http://www.php.net/manual/en/function.imagebmp.php
 * @param GdImage $image 
 * @param resource|string|null $file [optional] 
 * @param bool $compressed [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagebmp (GdImage $image, $file = null, bool $compressed = true): bool {}

/**
 * Destroy an image
 * @link http://www.php.net/manual/en/function.imagedestroy.php
 * @param GdImage $image 
 * @return bool Returns true on success or false on failure.
 */
function imagedestroy (GdImage $image): bool {}

/**
 * Allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolorallocate.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @return int|false A color identifier or false if the allocation failed.
 */
function imagecolorallocate (GdImage $image, int $red, int $green, int $blue): int|false {}

/**
 * Copy the palette from one image to another
 * @link http://www.php.net/manual/en/function.imagepalettecopy.php
 * @param GdImage $dst 
 * @param GdImage $src 
 * @return void No value is returned.
 */
function imagepalettecopy (GdImage $dst, GdImage $src): void {}

/**
 * Get the index of the color of a pixel
 * @link http://www.php.net/manual/en/function.imagecolorat.php
 * @param GdImage $image 
 * @param int $x 
 * @param int $y 
 * @return int|false Returns the index of the color or false on failure.
 */
function imagecolorat (GdImage $image, int $x, int $y): int|false {}

/**
 * Get the index of the closest color to the specified color
 * @link http://www.php.net/manual/en/function.imagecolorclosest.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @return int Returns the index of the closest color, in the palette of the image, to
 * the specified one
 */
function imagecolorclosest (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * Get the index of the color which has the hue, white and blackness
 * @link http://www.php.net/manual/en/function.imagecolorclosesthwb.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @return int Returns an integer with the index of the color which has 
 * the hue, white and blackness nearest the given color.
 */
function imagecolorclosesthwb (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * De-allocate a color for an image
 * @link http://www.php.net/manual/en/function.imagecolordeallocate.php
 * @param GdImage $image 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagecolordeallocate (GdImage $image, int $color): bool {}

/**
 * Get the index of the specified color or its closest possible alternative
 * @link http://www.php.net/manual/en/function.imagecolorresolve.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @return int Returns a color index.
 */
function imagecolorresolve (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * Get the index of the specified color
 * @link http://www.php.net/manual/en/function.imagecolorexact.php
 * @param GdImage $image 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @return int Returns the index of the specified color in the palette, or -1 if the
 * color does not exist.
 */
function imagecolorexact (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * Set the color for the specified palette index
 * @link http://www.php.net/manual/en/function.imagecolorset.php
 * @param GdImage $image 
 * @param int $color 
 * @param int $red 
 * @param int $green 
 * @param int $blue 
 * @param int $alpha [optional] 
 * @return false|null The function returns null on success, or false on failure.
 */
function imagecolorset (GdImage $image, int $color, int $red, int $green, int $blue, int $alpha = null): ?false {}

/**
 * Get the colors for an index
 * @link http://www.php.net/manual/en/function.imagecolorsforindex.php
 * @param GdImage $image 
 * @param int $color 
 * @return array Returns an associative array with red, green, blue and alpha keys that
 * contain the appropriate values for the specified color index.
 */
function imagecolorsforindex (GdImage $image, int $color): array {}

/**
 * Apply a gamma correction to a GD image
 * @link http://www.php.net/manual/en/function.imagegammacorrect.php
 * @param GdImage $image 
 * @param float $input_gamma 
 * @param float $output_gamma 
 * @return bool Returns true on success or false on failure.
 */
function imagegammacorrect (GdImage $image, float $input_gamma, float $output_gamma): bool {}

/**
 * Set a single pixel
 * @link http://www.php.net/manual/en/function.imagesetpixel.php
 * @param GdImage $image 
 * @param int $x 
 * @param int $y 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagesetpixel (GdImage $image, int $x, int $y, int $color): bool {}

/**
 * Draw a line
 * @link http://www.php.net/manual/en/function.imageline.php
 * @param GdImage $image 
 * @param int $x1 
 * @param int $y1 
 * @param int $x2 
 * @param int $y2 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imageline (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draw a dashed line
 * @link http://www.php.net/manual/en/function.imagedashedline.php
 * @param GdImage $image 
 * @param int $x1 
 * @param int $y1 
 * @param int $x2 
 * @param int $y2 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagedashedline (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draw a rectangle
 * @link http://www.php.net/manual/en/function.imagerectangle.php
 * @param GdImage $image 
 * @param int $x1 
 * @param int $y1 
 * @param int $x2 
 * @param int $y2 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagerectangle (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draw a filled rectangle
 * @link http://www.php.net/manual/en/function.imagefilledrectangle.php
 * @param GdImage $image 
 * @param int $x1 
 * @param int $y1 
 * @param int $x2 
 * @param int $y2 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagefilledrectangle (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * Draws an arc
 * @link http://www.php.net/manual/en/function.imagearc.php
 * @param GdImage $image 
 * @param int $center_x 
 * @param int $center_y 
 * @param int $width 
 * @param int $height 
 * @param int $start_angle 
 * @param int $end_angle 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagearc (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $start_angle, int $end_angle, int $color): bool {}

/**
 * Draw an ellipse
 * @link http://www.php.net/manual/en/function.imageellipse.php
 * @param GdImage $image 
 * @param int $center_x 
 * @param int $center_y 
 * @param int $width 
 * @param int $height 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imageellipse (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $color): bool {}

/**
 * Flood fill to specific color
 * @link http://www.php.net/manual/en/function.imagefilltoborder.php
 * @param GdImage $image 
 * @param int $x 
 * @param int $y 
 * @param int $border_color 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagefilltoborder (GdImage $image, int $x, int $y, int $border_color, int $color): bool {}

/**
 * Flood fill
 * @link http://www.php.net/manual/en/function.imagefill.php
 * @param GdImage $image 
 * @param int $x 
 * @param int $y 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagefill (GdImage $image, int $x, int $y, int $color): bool {}

/**
 * Find out the number of colors in an image's palette
 * @link http://www.php.net/manual/en/function.imagecolorstotal.php
 * @param GdImage $image 
 * @return int Returns the number of colors in the specified image's palette or 0 for
 * truecolor images.
 */
function imagecolorstotal (GdImage $image): int {}

/**
 * Define a color as transparent
 * @link http://www.php.net/manual/en/function.imagecolortransparent.php
 * @param GdImage $image 
 * @param int|null $color [optional] 
 * @return int The identifier of the new (or current, if none is specified)
 * transparent color is returned. If color
 * is null, and the image has no transparent color, the
 * returned identifier will be -1.
 */
function imagecolortransparent (GdImage $image, ?int $color = null): int {}

/**
 * Enable or disable interlace
 * @link http://www.php.net/manual/en/function.imageinterlace.php
 * @param GdImage $image 
 * @param bool|null $enable [optional] 
 * @return bool Returns true if the interlace bit is set for the image, false otherwise.
 */
function imageinterlace (GdImage $image, ?bool $enable = null): bool {}

/**
 * Draws a polygon
 * @link http://www.php.net/manual/en/function.imagepolygon.php
 * @param GdImage $image 
 * @param array $points 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagepolygon (GdImage $image, array $points, int $color): bool {}

/**
 * Draws an open polygon
 * @link http://www.php.net/manual/en/function.imageopenpolygon.php
 * @param GdImage $image 
 * @param array $points 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imageopenpolygon (GdImage $image, array $points, int $color): bool {}

/**
 * Draw a filled polygon
 * @link http://www.php.net/manual/en/function.imagefilledpolygon.php
 * @param GdImage $image 
 * @param array $points 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagefilledpolygon (GdImage $image, array $points, int $color): bool {}

/**
 * Get font width
 * @link http://www.php.net/manual/en/function.imagefontwidth.php
 * @param GdFont|int $font 
 * @return int Returns the pixel width of the font.
 */
function imagefontwidth (GdFont|int $font): int {}

/**
 * Get font height
 * @link http://www.php.net/manual/en/function.imagefontheight.php
 * @param GdFont|int $font 
 * @return int Returns the pixel height of the font.
 */
function imagefontheight (GdFont|int $font): int {}

/**
 * Draw a character horizontally
 * @link http://www.php.net/manual/en/function.imagechar.php
 * @param GdImage $image 
 * @param GdFont|int $font 
 * @param int $x 
 * @param int $y 
 * @param string $char 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagechar (GdImage $image, GdFont|int $font, int $x, int $y, string $char, int $color): bool {}

/**
 * Draw a character vertically
 * @link http://www.php.net/manual/en/function.imagecharup.php
 * @param GdImage $image 
 * @param GdFont|int $font 
 * @param int $x 
 * @param int $y 
 * @param string $char 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagecharup (GdImage $image, GdFont|int $font, int $x, int $y, string $char, int $color): bool {}

/**
 * Draw a string horizontally
 * @link http://www.php.net/manual/en/function.imagestring.php
 * @param GdImage $image 
 * @param GdFont|int $font 
 * @param int $x 
 * @param int $y 
 * @param string $string 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagestring (GdImage $image, GdFont|int $font, int $x, int $y, string $string, int $color): bool {}

/**
 * Draw a string vertically
 * @link http://www.php.net/manual/en/function.imagestringup.php
 * @param GdImage $image 
 * @param GdFont|int $font 
 * @param int $x 
 * @param int $y 
 * @param string $string 
 * @param int $color 
 * @return bool Returns true on success or false on failure.
 */
function imagestringup (GdImage $image, GdFont|int $font, int $x, int $y, string $string, int $color): bool {}

/**
 * Copy part of an image
 * @link http://www.php.net/manual/en/function.imagecopy.php
 * @param GdImage $dst_image 
 * @param GdImage $src_image 
 * @param int $dst_x 
 * @param int $dst_y 
 * @param int $src_x 
 * @param int $src_y 
 * @param int $src_width 
 * @param int $src_height 
 * @return bool Returns true on success or false on failure.
 */
function imagecopy (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height): bool {}

/**
 * Copy and merge part of an image
 * @link http://www.php.net/manual/en/function.imagecopymerge.php
 * @param GdImage $dst_image 
 * @param GdImage $src_image 
 * @param int $dst_x 
 * @param int $dst_y 
 * @param int $src_x 
 * @param int $src_y 
 * @param int $src_width 
 * @param int $src_height 
 * @param int $pct 
 * @return bool Returns true on success or false on failure.
 */
function imagecopymerge (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height, int $pct): bool {}

/**
 * Copy and merge part of an image with gray scale
 * @link http://www.php.net/manual/en/function.imagecopymergegray.php
 * @param GdImage $dst_image 
 * @param GdImage $src_image 
 * @param int $dst_x 
 * @param int $dst_y 
 * @param int $src_x 
 * @param int $src_y 
 * @param int $src_width 
 * @param int $src_height 
 * @param int $pct 
 * @return bool Returns true on success or false on failure.
 */
function imagecopymergegray (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height, int $pct): bool {}

/**
 * Copy and resize part of an image
 * @link http://www.php.net/manual/en/function.imagecopyresized.php
 * @param GdImage $dst_image 
 * @param GdImage $src_image 
 * @param int $dst_x 
 * @param int $dst_y 
 * @param int $src_x 
 * @param int $src_y 
 * @param int $dst_width 
 * @param int $dst_height 
 * @param int $src_width 
 * @param int $src_height 
 * @return bool Returns true on success or false on failure.
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
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
 * @param int $x1 The x-coordinate of the upper left corner.
 * @param int $y1 The y-coordinate of the upper left corner.
 * @param int $x2 The x-coordinate of the lower right corner.
 * @param int $y2 The y-coordinate of the lower right corner.
 * @return bool Returns true on success or false on failure.
 */
function imagesetclip (GdImage $image, int $x1, int $y1, int $x2, int $y2): bool {}

/**
 * Get the clipping rectangle
 * @link http://www.php.net/manual/en/function.imagegetclip.php
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
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
 * @param float $size 
 * @param float $angle 
 * @param string $font_filename 
 * @param string $string 
 * @param array $options [optional] 
 * @return array|false imageftbbox returns an array with 8
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
 * <p>The points are relative to the text regardless of the
 * angle, so "upper left" means in the top left-hand 
 * corner seeing the text horizontally.</p>
 * <p>On failure, false is returned.</p>
 */
function imageftbbox (float $size, float $angle, string $font_filename, string $string, array $options = '[]'): array|false {}

/**
 * Write text to the image using fonts using FreeType 2
 * @link http://www.php.net/manual/en/function.imagefttext.php
 * @param GdImage $image 
 * @param float $size 
 * @param float $angle 
 * @param int $x 
 * @param int $y 
 * @param int $color 
 * @param string $font_filename 
 * @param string $text 
 * @param array $options [optional] 
 * @return array|false This function returns an array defining the four points of the box, starting in the lower left and moving counter-clockwise:
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
 * <p>On failure, false is returned.</p>
 */
function imagefttext (GdImage $image, float $size, float $angle, int $x, int $y, int $color, string $font_filename, string $text, array $options = '[]'): array|false {}

/**
 * Give the bounding box of a text using TrueType fonts
 * @link http://www.php.net/manual/en/function.imagettfbbox.php
 * @param float $size 
 * @param float $angle 
 * @param string $font_filename 
 * @param string $string 
 * @param array $options [optional] 
 * @return array|false imagettfbbox returns an array with 8
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
 * <p>The points are relative to the text regardless of the
 * angle, so "upper left" means in the top left-hand 
 * corner seeing the text horizontally.</p>
 */
function imagettfbbox (float $size, float $angle, string $font_filename, string $string, array $options = '[]'): array|false {}

/**
 * Write text to the image using TrueType fonts
 * @link http://www.php.net/manual/en/function.imagettftext.php
 * @param GdImage $image 
 * @param float $size 
 * @param float $angle 
 * @param int $x 
 * @param int $y 
 * @param int $color 
 * @param string $font_filename 
 * @param string $text 
 * @param array $options [optional] 
 * @return array|false Returns an array with 8 elements representing four points making the
 * bounding box of the text. The order of the points is lower left, lower 
 * right, upper right, upper left. The points are relative to the text
 * regardless of the angle, so "upper left" means in the top left-hand 
 * corner when you see the text horizontally.
 * Returns false on error.
 */
function imagettftext (GdImage $image, float $size, float $angle, int $x, int $y, int $color, string $font_filename, string $text, array $options = '[]'): array|false {}

/**
 * Applies a filter to an image
 * @link http://www.php.net/manual/en/function.imagefilter.php
 * @param GdImage $image 
 * @param int $filter 
 * @param array|int|float|bool $args 
 * @return bool Returns true on success or false on failure.
 */
function imagefilter (GdImage $image, int $filter, array|int|float|bool ...$args): bool {}

/**
 * Apply a 3x3 convolution matrix, using coefficient and offset
 * @link http://www.php.net/manual/en/function.imageconvolution.php
 * @param GdImage $image 
 * @param array $matrix 
 * @param float $divisor 
 * @param float $offset 
 * @return bool Returns true on success or false on failure.
 */
function imageconvolution (GdImage $image, array $matrix, float $divisor, float $offset): bool {}

/**
 * Flips an image using a given mode
 * @link http://www.php.net/manual/en/function.imageflip.php
 * @param GdImage $image 
 * @param int $mode 
 * @return bool Returns true on success or false on failure.
 */
function imageflip (GdImage $image, int $mode): bool {}

/**
 * Should antialias functions be used or not
 * @link http://www.php.net/manual/en/function.imageantialias.php
 * @param GdImage $image 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function imageantialias (GdImage $image, bool $enable): bool {}

/**
 * Crop an image to the given rectangle
 * @link http://www.php.net/manual/en/function.imagecrop.php
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
 * @param array $rectangle The cropping rectangle as array with keys
 * x, y, width and
 * height.
 * @return GdImage|false Return cropped image object on success or false on failure.
 */
function imagecrop (GdImage $image, array $rectangle): GdImage|false {}

/**
 * Crop an image automatically using one of the available modes
 * @link http://www.php.net/manual/en/function.imagecropauto.php
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
 * @param int $mode [optional] One of the following constants:
 * @param float $threshold [optional] Specifies the tolerance in percent to be used while comparing the image
 * color and the color to crop. The method used to calculate the color
 * difference is based on the color distance in the RGB(a) cube.
 * <p>Used only in IMG_CROP_THRESHOLD mode.</p>
 * @param int $color [optional] Either an RGB color value or a palette index.
 * <p>Used only in IMG_CROP_THRESHOLD mode.</p>
 * @return GdImage|false Returns a cropped image object on success or false on failure.
 * If the complete image was cropped, imagecrop returns false.
 */
function imagecropauto (GdImage $image, int $mode = IMG_CROP_DEFAULT, float $threshold = 0.5, int $color = -1): GdImage|false {}

/**
 * Scale an image using the given new width and height
 * @link http://www.php.net/manual/en/function.imagescale.php
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
 * @param int $width The width to scale the image to.
 * @param int $height [optional] The height to scale the image to. If omitted or negative, the aspect
 * ratio will be preserved.
 * @param int $mode [optional] One of IMG_NEAREST_NEIGHBOUR,
 * IMG_BILINEAR_FIXED,
 * IMG_BICUBIC,
 * IMG_BICUBIC_FIXED or anything else (will use two
 * pass).
 * IMG_WEIGHTED4 is not yet supported.
 * @return GdImage|false Return the scaled image object on success or false on failure.
 */
function imagescale (GdImage $image, int $width, int $height = -1, int $mode = IMG_BILINEAR_FIXED): GdImage|false {}

/**
 * Return an image containing the affine transformed src image, using an optional clipping area
 * @link http://www.php.net/manual/en/function.imageaffine.php
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
 * @param array $affine Array with keys 0 to 5.
 * @param array|null $clip [optional] Array with keys "x", "y", "width" and "height"; or null.
 * @return GdImage|false Return affined image object on success or false on failure.
 */
function imageaffine (GdImage $image, array $affine, ?array $clip = null): GdImage|false {}

/**
 * Get an affine transformation matrix
 * @link http://www.php.net/manual/en/function.imageaffinematrixget.php
 * @param int $type One of the IMG_AFFINE_&#42; constants.
 * @param array|float $options If type is IMG_AFFINE_TRANSLATE
 * or IMG_AFFINE_SCALE,
 * options has to be an array with keys x
 * and y, both having float values.
 * <p>If type is IMG_AFFINE_ROTATE,
 * IMG_AFFINE_SHEAR_HORIZONTAL or IMG_AFFINE_SHEAR_VERTICAL,
 * options has to be a float specifying the angle.</p>
 * @return array|false An affine transformation matrix (an array with keys
 * 0 to 5 and float values)
 * or false on failure.
 */
function imageaffinematrixget (int $type, array|float $options): array|false {}

/**
 * Concatenate two affine transformation matrices
 * @link http://www.php.net/manual/en/function.imageaffinematrixconcat.php
 * @param array $matrix1 An affine transformation matrix (an array with keys
 * 0 to 5 and float values).
 * @param array $matrix2 An affine transformation matrix (an array with keys
 * 0 to 5 and float values).
 * @return array|false An affine transformation matrix (an array with keys
 * 0 to 5 and float values)
 * or false on failure.
 */
function imageaffinematrixconcat (array $matrix1, array $matrix2): array|false {}

/**
 * Get the interpolation method
 * @link http://www.php.net/manual/en/function.imagegetinterpolation.php
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
 * @return int Returns the interpolation method.
 */
function imagegetinterpolation (GdImage $image): int {}

/**
 * Set the interpolation method
 * @link http://www.php.net/manual/en/function.imagesetinterpolation.php
 * @param GdImage $image 
 * @param int $method [optional] 
 * @return bool Returns true on success or false on failure.
 */
function imagesetinterpolation (GdImage $image, int $method = IMG_BILINEAR_FIXED): bool {}

/**
 * Get or set the resolution of the image
 * @link http://www.php.net/manual/en/function.imageresolution.php
 * @param GdImage $image A GdImage object, returned by one of the image creation functions,
 * such as imagecreatetruecolor.
 * @param int|null $resolution_x [optional] The horizontal resolution in DPI.
 * @param int|null $resolution_y [optional] The vertical resolution in DPI.
 * @return array|bool When used as getter,
 * it returns an indexed array of the horizontal and vertical resolution on
 * success, or false on failure.
 * When used as setter, it returns
 * true on success, or false on failure.
 */
function imageresolution (GdImage $image, ?int $resolution_x = null, ?int $resolution_y = null): array|bool {}


/**
 * >
 * Used as a return value by imagetypes
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_AVIF', 256);

/**
 * >
 * Used as a return value by imagetypes
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_GIF', 1);

/**
 * >
 * Used as a return value by imagetypes
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_JPG', 2);

/**
 * This constant has the same value as IMG_JPG
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_JPEG', 2);

/**
 * >
 * Used as a return value by imagetypes
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_PNG', 4);

/**
 * >
 * Used as a return value by imagetypes
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_WBMP', 8);

/**
 * >
 * Used as a return value by imagetypes
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_XPM', 16);

/**
 * >
 * Used as a return value by imagetypes
 * Available as of PHP 7.0.10.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_WEBP', 32);

/**
 * >
 * Used as a return value by imagetypes
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BMP', 64);
define ('IMG_TGA', 128);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_WEBP_LOSSLESS', 101);

/**
 * >
 * Special color option which can be used instead of a color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_COLOR_TILED', -5);

/**
 * >
 * Special color option which can be used instead of a color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_COLOR_STYLED', -2);

/**
 * >
 * Special color option which can be used instead of a color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_COLOR_BRUSHED', -3);

/**
 * >
 * Special color option which can be used instead of a color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_COLOR_STYLEDBRUSHED', -4);

/**
 * >
 * Special color option which can be used instead of a color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_COLOR_TRANSPARENT', -6);

/**
 * This constant has the same value as IMG_ARC_PIE
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_ARC_ROUNDED', 0);

/**
 * >
 * A style constant used by the imagefilledarc function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_ARC_PIE', 0);

/**
 * >
 * A style constant used by the imagefilledarc function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_ARC_CHORD', 1);

/**
 * >
 * A style constant used by the imagefilledarc function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_ARC_NOFILL', 2);

/**
 * >
 * A style constant used by the imagefilledarc function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_ARC_EDGED', 4);

/**
 * >
 * A type constant used by the imagegd2 function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_GD2_RAW', 1);

/**
 * >
 * A type constant used by the imagegd2 function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_GD2_COMPRESSED', 2);

/**
 * >
 * Used together with imageflip, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FLIP_HORIZONTAL', 1);

/**
 * >
 * Used together with imageflip, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FLIP_VERTICAL', 2);

/**
 * >
 * Used together with imageflip, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FLIP_BOTH', 3);

/**
 * >
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_EFFECT_REPLACE', 0);

/**
 * >
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_EFFECT_ALPHABLEND', 1);

/**
 * >
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_EFFECT_NORMAL', 2);

/**
 * >
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_EFFECT_OVERLAY', 3);

/**
 * >
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_EFFECT_MULTIPLY', 4);
define ('IMG_CROP_DEFAULT', 0);
define ('IMG_CROP_TRANSPARENT', 1);
define ('IMG_CROP_BLACK', 2);
define ('IMG_CROP_WHITE', 3);
define ('IMG_CROP_SIDES', 4);
define ('IMG_CROP_THRESHOLD', 5);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BELL', 1);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BESSEL', 2);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BILINEAR_FIXED', 3);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BICUBIC', 4);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BICUBIC_FIXED', 5);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BLACKMAN', 6);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BOX', 7);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_BSPLINE', 8);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_CATMULLROM', 9);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_GAUSSIAN', 10);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_GENERALIZED_CUBIC', 11);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_HERMITE', 12);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_HAMMING', 13);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_HANNING', 14);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_MITCHELL', 15);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_POWER', 17);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_QUADRATIC', 18);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_SINC', 19);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_NEAREST_NEIGHBOUR', 16);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_WEIGHTED4', 21);

/**
 * >
 * Used together with imagesetinterpolation, available as of PHP 5.5.0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_TRIANGLE', 20);

/**
 * >
 * An affine transformation type constant used by the imageaffinematrixget function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_AFFINE_TRANSLATE', 0);

/**
 * >
 * An affine transformation type constant used by the imageaffinematrixget function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_AFFINE_SCALE', 1);

/**
 * >
 * An affine transformation type constant used by the imageaffinematrixget function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_AFFINE_ROTATE', 2);

/**
 * >
 * An affine transformation type constant used by the imageaffinematrixget function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_AFFINE_SHEAR_HORIZONTAL', 3);

/**
 * >
 * An affine transformation type constant used by the imageaffinematrixget function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_AFFINE_SHEAR_VERTICAL', 4);

/**
 * When the bundled version of GD is used this is 1 otherwise 
 * its set to 0.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('GD_BUNDLED', 0);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_NEGATE', 0);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_GRAYSCALE', 1);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_BRIGHTNESS', 2);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_CONTRAST', 3);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_COLORIZE', 4);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_EDGEDETECT', 5);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_GAUSSIAN_BLUR', 7);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_SELECTIVE_BLUR', 8);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_EMBOSS', 6);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_MEAN_REMOVAL', 9);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_SMOOTH', 10);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_PIXELATE', 11);

/**
 * >
 * Special GD filter used by the imagefilter function.
 * (Available as of PHP 7.4.0)
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('IMG_FILTER_SCATTER', 12);

/**
 * The GD version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var string
 */
define ('GD_VERSION', "2.3.3");

/**
 * The GD major version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('GD_MAJOR_VERSION', 2);

/**
 * The GD minor version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('GD_MINOR_VERSION', 3);

/**
 * The GD release version PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('GD_RELEASE_VERSION', 3);

/**
 * The GD "extra" version (beta/rc..) PHP was compiled against.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var string
 */
define ('GD_EXTRA_VERSION', "");

/**
 * >
 * A special PNG filter, used by the imagepng function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('PNG_NO_FILTER', 0);

/**
 * >
 * A special PNG filter, used by the imagepng function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('PNG_FILTER_NONE', 8);

/**
 * >
 * A special PNG filter, used by the imagepng function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('PNG_FILTER_SUB', 16);

/**
 * >
 * A special PNG filter, used by the imagepng function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('PNG_FILTER_UP', 32);

/**
 * >
 * A special PNG filter, used by the imagepng function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('PNG_FILTER_AVG', 64);

/**
 * >
 * A special PNG filter, used by the imagepng function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('PNG_FILTER_PAETH', 128);

/**
 * >
 * A special PNG filter, used by the imagepng function.
 * @link http://www.php.net/manual/en/image.constants.php
 * @var int
 */
define ('PNG_ALL_FILTERS', 248);

// End of gd v.8.2.6
