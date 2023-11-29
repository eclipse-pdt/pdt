<?php

// Start of gd v.8.3.0

final class GdImage  {
}

final class GdFont  {
}

/**
 * {@inheritdoc}
 */
function gd_info (): array {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imageloadfont (string $filename): GdFont|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param array $style
 */
function imagesetstyle (GdImage $image, array $style): bool {}

/**
 * {@inheritdoc}
 * @param int $width
 * @param int $height
 */
function imagecreatetruecolor (int $width, int $height): GdImage|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imageistruecolor (GdImage $image): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param bool $dither
 * @param int $num_colors
 */
function imagetruecolortopalette (GdImage $image, bool $dither, int $num_colors): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imagepalettetotruecolor (GdImage $image): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image1
 * @param GdImage $image2
 */
function imagecolormatch (GdImage $image1, GdImage $image2): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $thickness
 */
function imagesetthickness (GdImage $image, int $thickness): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $center_x
 * @param int $center_y
 * @param int $width
 * @param int $height
 * @param int $color
 */
function imagefilledellipse (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $center_x
 * @param int $center_y
 * @param int $width
 * @param int $height
 * @param int $start_angle
 * @param int $end_angle
 * @param int $color
 * @param int $style
 */
function imagefilledarc (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $start_angle, int $end_angle, int $color, int $style): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param bool $enable
 */
function imagealphablending (GdImage $image, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param bool $enable
 */
function imagesavealpha (GdImage $image, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $effect
 */
function imagelayereffect (GdImage $image, int $effect): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 * @param int $alpha
 */
function imagecolorallocatealpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 * @param int $alpha
 */
function imagecolorresolvealpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 * @param int $alpha
 */
function imagecolorclosestalpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 * @param int $alpha
 */
function imagecolorexactalpha (GdImage $image, int $red, int $green, int $blue, int $alpha): int {}

/**
 * {@inheritdoc}
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
 */
function imagecopyresampled (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $dst_width, int $dst_height, int $src_width, int $src_height): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param float $angle
 * @param int $background_color
 */
function imagerotate (GdImage $image, float $angle, int $background_color): GdImage|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param GdImage $tile
 */
function imagesettile (GdImage $image, GdImage $tile): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param GdImage $brush
 */
function imagesetbrush (GdImage $image, GdImage $brush): bool {}

/**
 * {@inheritdoc}
 * @param int $width
 * @param int $height
 */
function imagecreate (int $width, int $height): GdImage|false {}

/**
 * {@inheritdoc}
 */
function imagetypes (): int {}

/**
 * {@inheritdoc}
 * @param string $data
 */
function imagecreatefromstring (string $data): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromavif (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromgif (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromjpeg (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefrompng (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromwebp (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromxbm (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromxpm (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromwbmp (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromgd (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromgd2 (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $x
 * @param int $y
 * @param int $width
 * @param int $height
 */
function imagecreatefromgd2part (string $filename, int $x, int $y, int $width, int $height): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefrombmp (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function imagecreatefromtga (string $filename): GdImage|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param string|null $filename
 * @param int|null $foreground_color [optional]
 */
function imagexbm (GdImage $image, ?string $filename = null, ?int $foreground_color = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param mixed $file [optional]
 * @param int $quality [optional]
 * @param int $speed [optional]
 */
function imageavif (GdImage $image, $file = NULL, int $quality = -1, int $speed = -1): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param mixed $file [optional]
 */
function imagegif (GdImage $image, $file = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param mixed $file [optional]
 * @param int $quality [optional]
 * @param int $filters [optional]
 */
function imagepng (GdImage $image, $file = NULL, int $quality = -1, int $filters = -1): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param mixed $file [optional]
 * @param int $quality [optional]
 */
function imagewebp (GdImage $image, $file = NULL, int $quality = -1): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param mixed $file [optional]
 * @param int $quality [optional]
 */
function imagejpeg (GdImage $image, $file = NULL, int $quality = -1): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param mixed $file [optional]
 * @param int|null $foreground_color [optional]
 */
function imagewbmp (GdImage $image, $file = NULL, ?int $foreground_color = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param string|null $file [optional]
 */
function imagegd (GdImage $image, ?string $file = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param string|null $file [optional]
 * @param int $chunk_size [optional]
 * @param int $mode [optional]
 */
function imagegd2 (GdImage $image, ?string $file = NULL, int $chunk_size = 128, int $mode = 1): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param mixed $file [optional]
 * @param bool $compressed [optional]
 */
function imagebmp (GdImage $image, $file = NULL, bool $compressed = true): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imagedestroy (GdImage $image): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 */
function imagecolorallocate (GdImage $image, int $red, int $green, int $blue): int|false {}

/**
 * {@inheritdoc}
 * @param GdImage $dst
 * @param GdImage $src
 */
function imagepalettecopy (GdImage $dst, GdImage $src): void {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x
 * @param int $y
 */
function imagecolorat (GdImage $image, int $x, int $y): int|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 */
function imagecolorclosest (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 */
function imagecolorclosesthwb (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $color
 */
function imagecolordeallocate (GdImage $image, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 */
function imagecolorresolve (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $red
 * @param int $green
 * @param int $blue
 */
function imagecolorexact (GdImage $image, int $red, int $green, int $blue): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $color
 * @param int $red
 * @param int $green
 * @param int $blue
 * @param int $alpha [optional]
 */
function imagecolorset (GdImage $image, int $color, int $red, int $green, int $blue, int $alpha = 0): ?false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $color
 */
function imagecolorsforindex (GdImage $image, int $color): array {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param float $input_gamma
 * @param float $output_gamma
 */
function imagegammacorrect (GdImage $image, float $input_gamma, float $output_gamma): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x
 * @param int $y
 * @param int $color
 */
function imagesetpixel (GdImage $image, int $x, int $y, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x1
 * @param int $y1
 * @param int $x2
 * @param int $y2
 * @param int $color
 */
function imageline (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x1
 * @param int $y1
 * @param int $x2
 * @param int $y2
 * @param int $color
 */
function imagedashedline (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x1
 * @param int $y1
 * @param int $x2
 * @param int $y2
 * @param int $color
 */
function imagerectangle (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x1
 * @param int $y1
 * @param int $x2
 * @param int $y2
 * @param int $color
 */
function imagefilledrectangle (GdImage $image, int $x1, int $y1, int $x2, int $y2, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $center_x
 * @param int $center_y
 * @param int $width
 * @param int $height
 * @param int $start_angle
 * @param int $end_angle
 * @param int $color
 */
function imagearc (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $start_angle, int $end_angle, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $center_x
 * @param int $center_y
 * @param int $width
 * @param int $height
 * @param int $color
 */
function imageellipse (GdImage $image, int $center_x, int $center_y, int $width, int $height, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x
 * @param int $y
 * @param int $border_color
 * @param int $color
 */
function imagefilltoborder (GdImage $image, int $x, int $y, int $border_color, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x
 * @param int $y
 * @param int $color
 */
function imagefill (GdImage $image, int $x, int $y, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imagecolorstotal (GdImage $image): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int|null $color [optional]
 */
function imagecolortransparent (GdImage $image, ?int $color = NULL): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param bool|null $enable [optional]
 */
function imageinterlace (GdImage $image, ?bool $enable = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param array $points
 * @param int $num_points_or_color
 * @param int|null $color [optional]
 */
function imagepolygon (GdImage $image, array $points, int $num_points_or_color, ?int $color = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param array $points
 * @param int $num_points_or_color
 * @param int|null $color [optional]
 */
function imageopenpolygon (GdImage $image, array $points, int $num_points_or_color, ?int $color = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param array $points
 * @param int $num_points_or_color
 * @param int|null $color [optional]
 */
function imagefilledpolygon (GdImage $image, array $points, int $num_points_or_color, ?int $color = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GdFont|int $font
 */
function imagefontwidth (GdFont|int $font): int {}

/**
 * {@inheritdoc}
 * @param GdFont|int $font
 */
function imagefontheight (GdFont|int $font): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param GdFont|int $font
 * @param int $x
 * @param int $y
 * @param string $char
 * @param int $color
 */
function imagechar (GdImage $image, GdFont|int $font, int $x, int $y, string $char, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param GdFont|int $font
 * @param int $x
 * @param int $y
 * @param string $char
 * @param int $color
 */
function imagecharup (GdImage $image, GdFont|int $font, int $x, int $y, string $char, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param GdFont|int $font
 * @param int $x
 * @param int $y
 * @param string $string
 * @param int $color
 */
function imagestring (GdImage $image, GdFont|int $font, int $x, int $y, string $string, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param GdFont|int $font
 * @param int $x
 * @param int $y
 * @param string $string
 * @param int $color
 */
function imagestringup (GdImage $image, GdFont|int $font, int $x, int $y, string $string, int $color): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $dst_image
 * @param GdImage $src_image
 * @param int $dst_x
 * @param int $dst_y
 * @param int $src_x
 * @param int $src_y
 * @param int $src_width
 * @param int $src_height
 */
function imagecopy (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $dst_image
 * @param GdImage $src_image
 * @param int $dst_x
 * @param int $dst_y
 * @param int $src_x
 * @param int $src_y
 * @param int $src_width
 * @param int $src_height
 * @param int $pct
 */
function imagecopymerge (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height, int $pct): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $dst_image
 * @param GdImage $src_image
 * @param int $dst_x
 * @param int $dst_y
 * @param int $src_x
 * @param int $src_y
 * @param int $src_width
 * @param int $src_height
 * @param int $pct
 */
function imagecopymergegray (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $src_width, int $src_height, int $pct): bool {}

/**
 * {@inheritdoc}
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
 */
function imagecopyresized (GdImage $dst_image, GdImage $src_image, int $dst_x, int $dst_y, int $src_x, int $src_y, int $dst_width, int $dst_height, int $src_width, int $src_height): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imagesx (GdImage $image): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imagesy (GdImage $image): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $x1
 * @param int $y1
 * @param int $x2
 * @param int $y2
 */
function imagesetclip (GdImage $image, int $x1, int $y1, int $x2, int $y2): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imagegetclip (GdImage $image): array {}

/**
 * {@inheritdoc}
 * @param float $size
 * @param float $angle
 * @param string $font_filename
 * @param string $string
 * @param array $options [optional]
 */
function imageftbbox (float $size, float $angle, string $font_filename, string $string, array $options = array (
)): array|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param float $size
 * @param float $angle
 * @param int $x
 * @param int $y
 * @param int $color
 * @param string $font_filename
 * @param string $text
 * @param array $options [optional]
 */
function imagefttext (GdImage $image, float $size, float $angle, int $x, int $y, int $color, string $font_filename, string $text, array $options = array (
)): array|false {}

/**
 * {@inheritdoc}
 * @param float $size
 * @param float $angle
 * @param string $font_filename
 * @param string $string
 * @param array $options [optional]
 */
function imagettfbbox (float $size, float $angle, string $font_filename, string $string, array $options = array (
)): array|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param float $size
 * @param float $angle
 * @param int $x
 * @param int $y
 * @param int $color
 * @param string $font_filename
 * @param string $text
 * @param array $options [optional]
 */
function imagettftext (GdImage $image, float $size, float $angle, int $x, int $y, int $color, string $font_filename, string $text, array $options = array (
)): array|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $filter
 * @param mixed $args [optional]
 */
function imagefilter (GdImage $image, int $filter, ...$args): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param array $matrix
 * @param float $divisor
 * @param float $offset
 */
function imageconvolution (GdImage $image, array $matrix, float $divisor, float $offset): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $mode
 */
function imageflip (GdImage $image, int $mode): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param bool $enable
 */
function imageantialias (GdImage $image, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param array $rectangle
 */
function imagecrop (GdImage $image, array $rectangle): GdImage|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $mode [optional]
 * @param float $threshold [optional]
 * @param int $color [optional]
 */
function imagecropauto (GdImage $image, int $mode = 0, float $threshold = 0.5, int $color = -1): GdImage|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $width
 * @param int $height [optional]
 * @param int $mode [optional]
 */
function imagescale (GdImage $image, int $width, int $height = -1, int $mode = 3): GdImage|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param array $affine
 * @param array|null $clip [optional]
 */
function imageaffine (GdImage $image, array $affine, ?array $clip = NULL): GdImage|false {}

/**
 * {@inheritdoc}
 * @param int $type
 * @param mixed $options
 */
function imageaffinematrixget (int $type, $options = null): array|false {}

/**
 * {@inheritdoc}
 * @param array $matrix1
 * @param array $matrix2
 */
function imageaffinematrixconcat (array $matrix1, array $matrix2): array|false {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 */
function imagegetinterpolation (GdImage $image): int {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int $method [optional]
 */
function imagesetinterpolation (GdImage $image, int $method = 3): bool {}

/**
 * {@inheritdoc}
 * @param GdImage $image
 * @param int|null $resolution_x [optional]
 * @param int|null $resolution_y [optional]
 */
function imageresolution (GdImage $image, ?int $resolution_x = NULL, ?int $resolution_y = NULL): array|bool {}

define ('IMG_AVIF', 256);
define ('IMG_GIF', 1);
define ('IMG_JPG', 2);
define ('IMG_JPEG', 2);
define ('IMG_PNG', 4);
define ('IMG_WBMP', 8);
define ('IMG_XPM', 16);
define ('IMG_WEBP', 32);
define ('IMG_BMP', 64);
define ('IMG_TGA', 128);
define ('IMG_WEBP_LOSSLESS', 101);
define ('IMG_COLOR_TILED', -5);
define ('IMG_COLOR_STYLED', -2);
define ('IMG_COLOR_BRUSHED', -3);
define ('IMG_COLOR_STYLEDBRUSHED', -4);
define ('IMG_COLOR_TRANSPARENT', -6);
define ('IMG_ARC_ROUNDED', 0);
define ('IMG_ARC_PIE', 0);
define ('IMG_ARC_CHORD', 1);
define ('IMG_ARC_NOFILL', 2);
define ('IMG_ARC_EDGED', 4);
define ('IMG_GD2_RAW', 1);
define ('IMG_GD2_COMPRESSED', 2);
define ('IMG_FLIP_HORIZONTAL', 1);
define ('IMG_FLIP_VERTICAL', 2);
define ('IMG_FLIP_BOTH', 3);
define ('IMG_EFFECT_REPLACE', 0);
define ('IMG_EFFECT_ALPHABLEND', 1);
define ('IMG_EFFECT_NORMAL', 2);
define ('IMG_EFFECT_OVERLAY', 3);
define ('IMG_EFFECT_MULTIPLY', 4);
define ('IMG_CROP_DEFAULT', 0);
define ('IMG_CROP_TRANSPARENT', 1);
define ('IMG_CROP_BLACK', 2);
define ('IMG_CROP_WHITE', 3);
define ('IMG_CROP_SIDES', 4);
define ('IMG_CROP_THRESHOLD', 5);
define ('IMG_BELL', 1);
define ('IMG_BESSEL', 2);
define ('IMG_BILINEAR_FIXED', 3);
define ('IMG_BICUBIC', 4);
define ('IMG_BICUBIC_FIXED', 5);
define ('IMG_BLACKMAN', 6);
define ('IMG_BOX', 7);
define ('IMG_BSPLINE', 8);
define ('IMG_CATMULLROM', 9);
define ('IMG_GAUSSIAN', 10);
define ('IMG_GENERALIZED_CUBIC', 11);
define ('IMG_HERMITE', 12);
define ('IMG_HAMMING', 13);
define ('IMG_HANNING', 14);
define ('IMG_MITCHELL', 15);
define ('IMG_POWER', 17);
define ('IMG_QUADRATIC', 18);
define ('IMG_SINC', 19);
define ('IMG_NEAREST_NEIGHBOUR', 16);
define ('IMG_WEIGHTED4', 21);
define ('IMG_TRIANGLE', 20);
define ('IMG_AFFINE_TRANSLATE', 0);
define ('IMG_AFFINE_SCALE', 1);
define ('IMG_AFFINE_ROTATE', 2);
define ('IMG_AFFINE_SHEAR_HORIZONTAL', 3);
define ('IMG_AFFINE_SHEAR_VERTICAL', 4);
define ('GD_BUNDLED', 0);
define ('IMG_FILTER_NEGATE', 0);
define ('IMG_FILTER_GRAYSCALE', 1);
define ('IMG_FILTER_BRIGHTNESS', 2);
define ('IMG_FILTER_CONTRAST', 3);
define ('IMG_FILTER_COLORIZE', 4);
define ('IMG_FILTER_EDGEDETECT', 5);
define ('IMG_FILTER_GAUSSIAN_BLUR', 7);
define ('IMG_FILTER_SELECTIVE_BLUR', 8);
define ('IMG_FILTER_EMBOSS', 6);
define ('IMG_FILTER_MEAN_REMOVAL', 9);
define ('IMG_FILTER_SMOOTH', 10);
define ('IMG_FILTER_PIXELATE', 11);
define ('IMG_FILTER_SCATTER', 12);
define ('GD_VERSION', "2.3.3");
define ('GD_MAJOR_VERSION', 2);
define ('GD_MINOR_VERSION', 3);
define ('GD_RELEASE_VERSION', 3);
define ('GD_EXTRA_VERSION', "");
define ('PNG_NO_FILTER', 0);
define ('PNG_FILTER_NONE', 8);
define ('PNG_FILTER_SUB', 16);
define ('PNG_FILTER_UP', 32);
define ('PNG_FILTER_AVG', 64);
define ('PNG_FILTER_PAETH', 128);
define ('PNG_ALL_FILTERS', 248);

// End of gd v.8.3.0
