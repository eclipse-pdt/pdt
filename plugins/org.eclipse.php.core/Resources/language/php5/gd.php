<?php

// Start of gd v.

/**
 * Retrieve information about the currently installed GD library
 * @link http://php.net/manual/en/function.gd-info.php
 * @return array an associative array.
 */
function gd_info () {}

/**
 * Draws an arc
 * @link http://php.net/manual/en/function.imagearc.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param start int
 * @param end int
 * @param color int
 * @return bool 
 */
function imagearc ($image, $cx, $cy, $width, $height, $start, $end, $color) {}

/**
 * Draw an ellipse
 * @link http://php.net/manual/en/function.imageellipse.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param color int
 * @return bool 
 */
function imageellipse ($image, $cx, $cy, $width, $height, $color) {}

/**
 * Draw a character horizontally
 * @link http://php.net/manual/en/function.imagechar.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param c string
 * @param color int
 * @return bool 
 */
function imagechar ($image, $font, $x, $y, $c, $color) {}

/**
 * Draw a character vertically
 * @link http://php.net/manual/en/function.imagecharup.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param c string
 * @param color int
 * @return bool 
 */
function imagecharup ($image, $font, $x, $y, $c, $color) {}

/**
 * Get the index of the color of a pixel
 * @link http://php.net/manual/en/function.imagecolorat.php
 * @param image resource
 * @param x int
 * @param y int
 * @return int the index of the color.
 */
function imagecolorat ($image, $x, $y) {}

/**
 * Allocate a color for an image
 * @link http://php.net/manual/en/function.imagecolorallocate.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int 
 */
function imagecolorallocate ($image, $red, $green, $blue) {}

/**
 * Copy the palette from one image to another
 * @link http://php.net/manual/en/function.imagepalettecopy.php
 * @param destination resource
 * @param source resource
 * @return void 
 */
function imagepalettecopy ($destination, $source) {}

/**
 * Create a new image from the image stream in the string
 * @link http://php.net/manual/en/function.imagecreatefromstring.php
 * @param data string
 * @return resource 
 */
function imagecreatefromstring ($data) {}

/**
 * Get the index of the closest color to the specified color
 * @link http://php.net/manual/en/function.imagecolorclosest.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int the index of the closest color, in the palette of the image, to
 */
function imagecolorclosest ($image, $red, $green, $blue) {}

/**
 * Get the index of the color which has the hue, white and blackness
 * @link http://php.net/manual/en/function.imagecolorclosesthwb.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int an integer with the index of the color which has
 */
function imagecolorclosesthwb ($image, $red, $green, $blue) {}

/**
 * De-allocate a color for an image
 * @link http://php.net/manual/en/function.imagecolordeallocate.php
 * @param image resource
 * @param color int
 * @return bool 
 */
function imagecolordeallocate ($image, $color) {}

/**
 * Get the index of the specified color or its closest possible alternative
 * @link http://php.net/manual/en/function.imagecolorresolve.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int a color index.
 */
function imagecolorresolve ($image, $red, $green, $blue) {}

/**
 * Get the index of the specified color
 * @link http://php.net/manual/en/function.imagecolorexact.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @return int the index of the specified color in the palette, or -1 if the
 */
function imagecolorexact ($image, $red, $green, $blue) {}

/**
 * Set the color for the specified palette index
 * @link http://php.net/manual/en/function.imagecolorset.php
 * @param image resource
 * @param index int
 * @param red int
 * @param green int
 * @param blue int
 * @return void 
 */
function imagecolorset ($image, $index, $red, $green, $blue) {}

/**
 * Define a color as transparent
 * @link http://php.net/manual/en/function.imagecolortransparent.php
 * @param image resource
 * @param color int[optional]
 * @return int 
 */
function imagecolortransparent ($image, $color = null) {}

/**
 * Find out the number of colors in an image's palette
 * @link http://php.net/manual/en/function.imagecolorstotal.php
 * @param image resource
 * @return int the number of colors in the specified image's palette or 0 for
 */
function imagecolorstotal ($image) {}

/**
 * Get the colors for an index
 * @link http://php.net/manual/en/function.imagecolorsforindex.php
 * @param image resource
 * @param index int
 * @return array an associative array with red, green, blue and alpha keys that
 */
function imagecolorsforindex ($image, $index) {}

/**
 * Copy part of an image
 * @link http://php.net/manual/en/function.imagecopy.php
 * @param dst_im resource
 * @param src_im resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param src_w int
 * @param src_h int
 * @return bool 
 */
function imagecopy ($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h) {}

/**
 * Copy and merge part of an image
 * @link http://php.net/manual/en/function.imagecopymerge.php
 * @param dst_im resource
 * @param src_im resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param src_w int
 * @param src_h int
 * @param pct int
 * @return bool 
 */
function imagecopymerge ($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h, $pct) {}

/**
 * Copy and merge part of an image with gray scale
 * @link http://php.net/manual/en/function.imagecopymergegray.php
 * @param dst_im resource
 * @param src_im resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param src_w int
 * @param src_h int
 * @param pct int
 * @return bool 
 */
function imagecopymergegray ($dst_im, $src_im, $dst_x, $dst_y, $src_x, $src_y, $src_w, $src_h, $pct) {}

/**
 * Copy and resize part of an image
 * @link http://php.net/manual/en/function.imagecopyresized.php
 * @param dst_image resource
 * @param src_image resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param dst_w int
 * @param dst_h int
 * @param src_w int
 * @param src_h int
 * @return bool 
 */
function imagecopyresized ($dst_image, $src_image, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h) {}

/**
 * Create a new palette based image
 * @link http://php.net/manual/en/function.imagecreate.php
 * @param width int
 * @param height int
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreate ($width, $height) {}

/**
 * Create a new true color image
 * @link http://php.net/manual/en/function.imagecreatetruecolor.php
 * @param width int
 * @param height int
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatetruecolor ($width, $height) {}

/**
 * Finds whether an image is a truecolor image
 * @link http://php.net/manual/en/function.imageistruecolor.php
 * @param image resource
 * @return bool true if the image is truecolor, false
 */
function imageistruecolor ($image) {}

/**
 * Convert a true color image to a palette image
 * @link http://php.net/manual/en/function.imagetruecolortopalette.php
 * @param image resource
 * @param dither bool
 * @param ncolors int
 * @return bool 
 */
function imagetruecolortopalette ($image, $dither, $ncolors) {}

/**
 * Set the thickness for line drawing
 * @link http://php.net/manual/en/function.imagesetthickness.php
 * @param image resource
 * @param thickness int
 * @return bool 
 */
function imagesetthickness ($image, $thickness) {}

/**
 * Draw a partial arc and fill it
 * @link http://php.net/manual/en/function.imagefilledarc.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param start int
 * @param end int
 * @param color int
 * @param style int
 * @return bool 
 */
function imagefilledarc ($image, $cx, $cy, $width, $height, $start, $end, $color, $style) {}

/**
 * Draw a filled ellipse
 * @link http://php.net/manual/en/function.imagefilledellipse.php
 * @param image resource
 * @param cx int
 * @param cy int
 * @param width int
 * @param height int
 * @param color int
 * @return bool 
 */
function imagefilledellipse ($image, $cx, $cy, $width, $height, $color) {}

/**
 * Set the blending mode for an image
 * @link http://php.net/manual/en/function.imagealphablending.php
 * @param image resource
 * @param blendmode bool
 * @return bool 
 */
function imagealphablending ($image, $blendmode) {}

/**
 * Set the flag to save full alpha channel information (as opposed to single-color transparency) when saving PNG images
 * @link http://php.net/manual/en/function.imagesavealpha.php
 * @param image resource
 * @param saveflag bool
 * @return bool 
 */
function imagesavealpha ($image, $saveflag) {}

/**
 * Allocate a color for an image
 * @link http://php.net/manual/en/function.imagecolorallocatealpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int 
 */
function imagecolorallocatealpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the specified color + alpha or its closest possible alternative
 * @link http://php.net/manual/en/function.imagecolorresolvealpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int a color index.
 */
function imagecolorresolvealpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the closest color to the specified color + alpha
 * @link http://php.net/manual/en/function.imagecolorclosestalpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int the index of the closest color in the palette.
 */
function imagecolorclosestalpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Get the index of the specified color + alpha
 * @link http://php.net/manual/en/function.imagecolorexactalpha.php
 * @param image resource
 * @param red int
 * @param green int
 * @param blue int
 * @param alpha int
 * @return int the index of the specified color+alpha in the palette of the
 */
function imagecolorexactalpha ($image, $red, $green, $blue, $alpha) {}

/**
 * Copy and resize part of an image with resampling
 * @link http://php.net/manual/en/function.imagecopyresampled.php
 * @param dst_image resource
 * @param src_image resource
 * @param dst_x int
 * @param dst_y int
 * @param src_x int
 * @param src_y int
 * @param dst_w int
 * @param dst_h int
 * @param src_w int
 * @param src_h int
 * @return bool 
 */
function imagecopyresampled ($dst_image, $src_image, $dst_x, $dst_y, $src_x, $src_y, $dst_w, $dst_h, $src_w, $src_h) {}

/**
 * Rotate an image with a given angle
 * @link http://php.net/manual/en/function.imagerotate.php
 * @param image resource
 * @param angle float
 * @param bgd_color int
 * @param ignore_transparent int[optional]
 * @return resource 
 */
function imagerotate ($image, $angle, $bgd_color, $ignore_transparent = null) {}

/**
 * Should antialias functions be used or not
 * @link http://php.net/manual/en/function.imageantialias.php
 * @param image resource
 * @param enabled bool
 * @return bool 
 */
function imageantialias ($image, $enabled) {}

/**
 * Set the tile image for filling
 * @link http://php.net/manual/en/function.imagesettile.php
 * @param image resource
 * @param tile resource
 * @return bool 
 */
function imagesettile ($image, $tile) {}

/**
 * Set the brush image for line drawing
 * @link http://php.net/manual/en/function.imagesetbrush.php
 * @param image resource
 * @param brush resource
 * @return bool 
 */
function imagesetbrush ($image, $brush) {}

/**
 * Set the style for line drawing
 * @link http://php.net/manual/en/function.imagesetstyle.php
 * @param image resource
 * @param style array
 * @return bool 
 */
function imagesetstyle ($image, array $style) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefrompng.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefrompng ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgif.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgif ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromjpeg.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromjpeg ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromwbmp.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromwbmp ($filename) {}

/**
 * Create a new image from file or URL
 * @link http://php.net/manual/en/function.imagecreatefromxbm.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromxbm ($filename) {}

/**
 * Create a new image from GD file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgd.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgd ($filename) {}

/**
 * Create a new image from GD2 file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgd2.php
 * @param filename string
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgd2 ($filename) {}

/**
 * Create a new image from a given part of GD2 file or URL
 * @link http://php.net/manual/en/function.imagecreatefromgd2part.php
 * @param filename string
 * @param srcX int
 * @param srcY int
 * @param width int
 * @param height int
 * @return resource an image resource identifier on success, false on errors.
 */
function imagecreatefromgd2part ($filename, $srcX, $srcY, $width, $height) {}

/**
 * Output a PNG image to either the browser or a file
 * @link http://php.net/manual/en/function.imagepng.php
 * @param image resource
 * @param filename string[optional]
 * @param quality int[optional]
 * @param filters int[optional]
 * @return bool 
 */
function imagepng ($image, $filename = null, $quality = null, $filters = null) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.imagegif.php
 * @param image resource
 * @param filename string[optional]
 * @return bool 
 */
function imagegif ($image, $filename = null) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.imagejpeg.php
 * @param image resource
 * @param filename string[optional]
 * @param quality int[optional]
 * @return bool 
 */
function imagejpeg ($image, $filename = null, $quality = null) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.imagewbmp.php
 * @param image resource
 * @param filename string[optional]
 * @param foreground int[optional]
 * @return bool 
 */
function imagewbmp ($image, $filename = null, $foreground = null) {}

/**
 * Output GD image to browser or file
 * @link http://php.net/manual/en/function.imagegd.php
 * @param image resource
 * @param filename string[optional]
 * @return bool 
 */
function imagegd ($image, $filename = null) {}

/**
 * Output GD2 image to browser or file
 * @link http://php.net/manual/en/function.imagegd2.php
 * @param image resource
 * @param filename string[optional]
 * @param chunk_size int[optional]
 * @param type int[optional]
 * @return bool 
 */
function imagegd2 ($image, $filename = null, $chunk_size = null, $type = null) {}

/**
 * Destroy an image
 * @link http://php.net/manual/en/function.imagedestroy.php
 * @param image resource
 * @return bool 
 */
function imagedestroy ($image) {}

/**
 * Apply a gamma correction to a GD image
 * @link http://php.net/manual/en/function.imagegammacorrect.php
 * @param image resource
 * @param inputgamma float
 * @param outputgamma float
 * @return bool 
 */
function imagegammacorrect ($image, $inputgamma, $outputgamma) {}

/**
 * Flood fill
 * @link http://php.net/manual/en/function.imagefill.php
 * @param image resource
 * @param x int
 * @param y int
 * @param color int
 * @return bool 
 */
function imagefill ($image, $x, $y, $color) {}

/**
 * Draw a filled polygon
 * @link http://php.net/manual/en/function.imagefilledpolygon.php
 * @param image resource
 * @param points array
 * @param num_points int
 * @param color int
 * @return bool 
 */
function imagefilledpolygon ($image, array $points, $num_points, $color) {}

/**
 * Draw a filled rectangle
 * @link http://php.net/manual/en/function.imagefilledrectangle.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool 
 */
function imagefilledrectangle ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Flood fill to specific color
 * @link http://php.net/manual/en/function.imagefilltoborder.php
 * @param image resource
 * @param x int
 * @param y int
 * @param border int
 * @param color int
 * @return bool 
 */
function imagefilltoborder ($image, $x, $y, $border, $color) {}

/**
 * Get font width
 * @link http://php.net/manual/en/function.imagefontwidth.php
 * @param font int
 * @return int the width of the pixel
 */
function imagefontwidth ($font) {}

/**
 * Get font height
 * @link http://php.net/manual/en/function.imagefontheight.php
 * @param font int
 * @return int the height of the pixel.
 */
function imagefontheight ($font) {}

/**
 * Enable or disable interlace
 * @link http://php.net/manual/en/function.imageinterlace.php
 * @param image resource
 * @param interlace int[optional]
 * @return int 1 if the interlace bit is set for the image, 0 otherwise.
 */
function imageinterlace ($image, $interlace = null) {}

/**
 * Draw a line
 * @link http://php.net/manual/en/function.imageline.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool 
 */
function imageline ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Load a new font
 * @link http://php.net/manual/en/function.imageloadfont.php
 * @param file string
 * @return int 
 */
function imageloadfont ($file) {}

/**
 * Draws a polygon
 * @link http://php.net/manual/en/function.imagepolygon.php
 * @param image resource
 * @param points array
 * @param num_points int
 * @param color int
 * @return bool 
 */
function imagepolygon ($image, array $points, $num_points, $color) {}

/**
 * Draw a rectangle
 * @link http://php.net/manual/en/function.imagerectangle.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool 
 */
function imagerectangle ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Set a single pixel
 * @link http://php.net/manual/en/function.imagesetpixel.php
 * @param image resource
 * @param x int
 * @param y int
 * @param color int
 * @return bool 
 */
function imagesetpixel ($image, $x, $y, $color) {}

/**
 * Draw a string horizontally
 * @link http://php.net/manual/en/function.imagestring.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param string string
 * @param color int
 * @return bool 
 */
function imagestring ($image, $font, $x, $y, $string, $color) {}

/**
 * Draw a string vertically
 * @link http://php.net/manual/en/function.imagestringup.php
 * @param image resource
 * @param font int
 * @param x int
 * @param y int
 * @param string string
 * @param color int
 * @return bool 
 */
function imagestringup ($image, $font, $x, $y, $string, $color) {}

/**
 * Get image width
 * @link http://php.net/manual/en/function.imagesx.php
 * @param image resource
 * @return int 
 */
function imagesx ($image) {}

/**
 * Get image height
 * @link http://php.net/manual/en/function.imagesy.php
 * @param image resource
 * @return int 
 */
function imagesy ($image) {}

/**
 * Draw a dashed line
 * @link http://php.net/manual/en/function.imagedashedline.php
 * @param image resource
 * @param x1 int
 * @param y1 int
 * @param x2 int
 * @param y2 int
 * @param color int
 * @return bool 
 */
function imagedashedline ($image, $x1, $y1, $x2, $y2, $color) {}

/**
 * Give the bounding box of a text using TrueType fonts
 * @link http://php.net/manual/en/function.imagettfbbox.php
 * @param size float
 * @param angle float
 * @param fontfile string
 * @param text string
 * @return array 
 */
function imagettfbbox ($size, $angle, $fontfile, $text) {}

/**
 * Write text to the image using TrueType fonts
 * @link http://php.net/manual/en/function.imagettftext.php
 * @param image resource
 * @param size float
 * @param angle float
 * @param x int
 * @param y int
 * @param color int
 * @param fontfile string
 * @param text string
 * @return array an array with 8 elements representing four points making the
 */
function imagettftext ($image, $size, $angle, $x, $y, $color, $fontfile, $text) {}

/**
 * Give the bounding box of a text using fonts via freetype2
 * @link http://php.net/manual/en/function.imageftbbox.php
 * @param size float
 * @param angle float
 * @param fontfile string
 * @param text string
 * @param extrainfo array[optional]
 * @return array 
 */
function imageftbbox ($size, $angle, $fontfile, $text, array $extrainfo = null) {}

/**
 * Write text to the image using fonts using FreeType 2
 * @link http://php.net/manual/en/function.imagefttext.php
 * @param image resource
 * @param size float
 * @param angle float
 * @param x int
 * @param y int
 * @param color int
 * @param fontfile string
 * @param text string
 * @param extrainfo array[optional]
 * @return array 
 */
function imagefttext ($image, $size, $angle, $x, $y, $color, $fontfile, $text, array $extrainfo = null) {}

/**
 * Return the image types supported by this PHP build
 * @link http://php.net/manual/en/function.imagetypes.php
 * @return int a bit-field corresponding to the image formats supported by the
 */
function imagetypes () {}

/**
 * Convert JPEG image file to WBMP image file
 * @link http://php.net/manual/en/function.jpeg2wbmp.php
 * @param jpegname string
 * @param wbmpname string
 * @param dest_height int
 * @param dest_width int
 * @param threshold int
 * @return bool 
 */
function jpeg2wbmp ($jpegname, $wbmpname, $dest_height, $dest_width, $threshold) {}

/**
 * Convert PNG image file to WBMP image file
 * @link http://php.net/manual/en/function.png2wbmp.php
 * @param pngname string
 * @param wbmpname string
 * @param dest_height int
 * @param dest_width int
 * @param threshold int
 * @return bool 
 */
function png2wbmp ($pngname, $wbmpname, $dest_height, $dest_width, $threshold) {}

/**
 * Output image to browser or file
 * @link http://php.net/manual/en/function.image2wbmp.php
 * @param image resource
 * @param filename string[optional]
 * @param threshold int[optional]
 * @return bool 
 */
function image2wbmp ($image, $filename = null, $threshold = null) {}

/**
 * Set the alpha blending flag to use the bundled libgd layering effects
 * @link http://php.net/manual/en/function.imagelayereffect.php
 * @param image resource
 * @param effect int
 * @return bool 
 */
function imagelayereffect ($image, $effect) {}

/**
 * Makes the colors of the palette version of an image more closely match the true color version
 * @link http://php.net/manual/en/function.imagecolormatch.php
 * @param image1 resource
 * @param image2 resource
 * @return bool 
 */
function imagecolormatch ($image1, $image2) {}

/**
 * Output XBM image to browser or file
 * @link http://php.net/manual/en/function.imagexbm.php
 * @param image resource
 * @param filename string
 * @param foreground int[optional]
 * @return bool 
 */
function imagexbm ($image, $filename, $foreground = null) {}

/**
 * Applies a filter to an image
 * @link http://php.net/manual/en/function.imagefilter.php
 * @param image resource
 * @param filtertype int
 * @param arg1 int[optional]
 * @param arg2 int[optional]
 * @param arg3 int[optional]
 * @param arg4 int[optional]
 * @return bool 
 */
function imagefilter ($image, $filtertype, $arg1 = null, $arg2 = null, $arg3 = null, $arg4 = null) {}

/**
 * Apply a 3x3 convolution matrix, using coefficient and offset
 * @link http://php.net/manual/en/function.imageconvolution.php
 * @param image resource
 * @param matrix array
 * @param div float
 * @param offset float
 * @return bool 
 */
function imageconvolution ($image, array $matrix, $div, $offset) {}


/**
 * Used as a return value by imagetypes
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_GIF', 1);

/**
 * Used as a return value by imagetypes
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_JPG', 2);

/**
 * Used as a return value by imagetypes
 * This constant has the same value as IMG_JPG
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_JPEG', 2);

/**
 * Used as a return value by imagetypes
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_PNG', 4);

/**
 * Used as a return value by imagetypes
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_WBMP', 8);

/**
 * Used as a return value by imagetypes
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_XPM', 16);

/**
 * Special color option which can be used in stead of color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_TILED', -5);

/**
 * Special color option which can be used in stead of color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_STYLED', -2);

/**
 * Special color option which can be used in stead of color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_BRUSHED', -3);

/**
 * Special color option which can be used in stead of color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_STYLEDBRUSHED', -4);

/**
 * Special color option which can be used in stead of color allocated with
 * imagecolorallocate or
 * imagecolorallocatealpha
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_COLOR_TRANSPARENT', -6);

/**
 * A style constant used by the imagefilledarc function.
 * This constant has the same value as IMG_ARC_PIE
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_ROUNDED', 0);

/**
 * A style constant used by the imagefilledarc function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_PIE', 0);

/**
 * A style constant used by the imagefilledarc function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_CHORD', 1);

/**
 * A style constant used by the imagefilledarc function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_NOFILL', 2);

/**
 * A style constant used by the imagefilledarc function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_ARC_EDGED', 4);

/**
 * A type constant used by the imagegd2 function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_GD2_RAW', 1);

/**
 * A type constant used by the imagegd2 function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_GD2_COMPRESSED', 2);

/**
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_REPLACE', 0);

/**
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_ALPHABLEND', 1);

/**
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_NORMAL', 2);

/**
 * Alpha blending effect used by the imagelayereffect function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_EFFECT_OVERLAY', 3);
define ('GD_BUNDLED', 1);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_NEGATE', 0);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_GRAYSCALE', 1);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_BRIGHTNESS', 2);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_CONTRAST', 3);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_COLORIZE', 4);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_EDGEDETECT', 5);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_GAUSSIAN_BLUR', 7);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_SELECTIVE_BLUR', 8);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_EMBOSS', 6);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_MEAN_REMOVAL', 9);

/**
 * Special GD filter used by the imagefilter function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('IMG_FILTER_SMOOTH', 10);

/**
 * The GD version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://php.net/manual/en/image.constants.php
 */
define ('GD_VERSION', "2.0.35");

/**
 * The GD major version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://php.net/manual/en/image.constants.php
 */
define ('GD_MAJOR_VERSION', 2);

/**
 * The GD minor version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://php.net/manual/en/image.constants.php
 */
define ('GD_MINOR_VERSION', 0);

/**
 * The GD release version PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://php.net/manual/en/image.constants.php
 */
define ('GD_RELEASE_VERSION', 35);

/**
 * The GD "extra" version (beta/rc..) PHP was compiled against.
 * (Available as of PHP 5.2.4)
 * @link http://php.net/manual/en/image.constants.php
 */
define ('GD_EXTRA_VERSION', "");

/**
 * A special PNG filter, used by the imagepng function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('PNG_NO_FILTER', 0);

/**
 * A special PNG filter, used by the imagepng function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_NONE', 8);

/**
 * A special PNG filter, used by the imagepng function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_SUB', 16);

/**
 * A special PNG filter, used by the imagepng function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_UP', 32);

/**
 * A special PNG filter, used by the imagepng function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_AVG', 64);

/**
 * A special PNG filter, used by the imagepng function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('PNG_FILTER_PAETH', 128);

/**
 * A special PNG filter, used by the imagepng function.
 * @link http://php.net/manual/en/image.constants.php
 */
define ('PNG_ALL_FILTERS', 248);

// End of gd v.
?>
