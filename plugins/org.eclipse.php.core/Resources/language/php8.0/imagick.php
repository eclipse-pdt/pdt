<?php

// Start of imagick v.3.7.0

class ImagickException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ImagickDrawException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ImagickPixelIteratorException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ImagickPixelException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.imagickkernelexception.php
 */
class ImagickKernelException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.imagick.php
 */
class Imagick implements Stringable, Iterator, Traversable, Countable {
	const COLOR_BLACK = 11;
	const COLOR_BLUE = 12;
	const COLOR_CYAN = 13;
	const COLOR_GREEN = 14;
	const COLOR_RED = 15;
	const COLOR_YELLOW = 16;
	const COLOR_MAGENTA = 17;
	const COLOR_ALPHA = 18;
	const COLOR_FUZZ = 19;
	const IMAGICK_EXTNUM = 30700;
	const IMAGICK_EXTVER = "3.7.0";
	const QUANTUM_RANGE = 65535;
	const USE_ZEND_MM = 0;
	const COMPOSITE_DEFAULT = 54;
	const COMPOSITE_UNDEFINED = 0;
	const COMPOSITE_NO = 52;
	const COMPOSITE_ATOP = 2;
	const COMPOSITE_BLEND = 3;
	const COMPOSITE_BUMPMAP = 5;
	const COMPOSITE_CLEAR = 7;
	const COMPOSITE_COLORBURN = 8;
	const COMPOSITE_COLORDODGE = 9;
	const COMPOSITE_COLORIZE = 10;
	const COMPOSITE_COPYBLACK = 11;
	const COMPOSITE_COPYBLUE = 12;
	const COMPOSITE_COPY = 13;
	const COMPOSITE_COPYCYAN = 14;
	const COMPOSITE_COPYGREEN = 15;
	const COMPOSITE_COPYMAGENTA = 16;
	const COMPOSITE_COPYALPHA = 17;
	const COMPOSITE_COPYOPACITY = 17;
	const COMPOSITE_COPYRED = 18;
	const COMPOSITE_COPYYELLOW = 19;
	const COMPOSITE_DARKEN = 20;
	const COMPOSITE_DSTATOP = 28;
	const COMPOSITE_DST = 29;
	const COMPOSITE_DSTIN = 30;
	const COMPOSITE_DSTOUT = 31;
	const COMPOSITE_DSTOVER = 32;
	const COMPOSITE_DIFFERENCE = 22;
	const COMPOSITE_DISPLACE = 23;
	const COMPOSITE_DISSOLVE = 24;
	const COMPOSITE_EXCLUSION = 33;
	const COMPOSITE_HARDLIGHT = 34;
	const COMPOSITE_HUE = 36;
	const COMPOSITE_IN = 37;
	const COMPOSITE_LIGHTEN = 39;
	const COMPOSITE_LUMINIZE = 44;
	const COMPOSITE_MODULATE = 48;
	const COMPOSITE_MULTIPLY = 51;
	const COMPOSITE_OUT = 53;
	const COMPOSITE_OVER = 54;
	const COMPOSITE_OVERLAY = 55;
	const COMPOSITE_PLUS = 58;
	const COMPOSITE_REPLACE = 59;
	const COMPOSITE_SATURATE = 60;
	const COMPOSITE_SCREEN = 61;
	const COMPOSITE_SOFTLIGHT = 62;
	const COMPOSITE_SRCATOP = 63;
	const COMPOSITE_SRC = 64;
	const COMPOSITE_SRCIN = 65;
	const COMPOSITE_SRCOUT = 66;
	const COMPOSITE_SRCOVER = 67;
	const COMPOSITE_THRESHOLD = 68;
	const COMPOSITE_XOR = 70;
	const COMPOSITE_CHANGEMASK = 6;
	const COMPOSITE_LINEARLIGHT = 43;
	const COMPOSITE_DISTORT = 25;
	const COMPOSITE_BLUR = 4;
	const COMPOSITE_PEGTOPLIGHT = 56;
	const COMPOSITE_VIVIDLIGHT = 69;
	const COMPOSITE_PINLIGHT = 57;
	const COMPOSITE_LINEARDODGE = 42;
	const COMPOSITE_LINEARBURN = 41;
	const COMPOSITE_MATHEMATICS = 45;
	const COMPOSITE_MODULUSADD = 49;
	const COMPOSITE_MODULUSSUBTRACT = 50;
	const COMPOSITE_MINUSDST = 46;
	const COMPOSITE_DIVIDEDST = 26;
	const COMPOSITE_DIVIDESRC = 27;
	const COMPOSITE_MINUSSRC = 47;
	const COMPOSITE_DARKENINTENSITY = 21;
	const COMPOSITE_LIGHTENINTENSITY = 40;
	const COMPOSITE_HARDMIX = 35;
	const COMPOSITE_STEREO = 71;
	const COMPOSITE_FREEZE = 72;
	const COMPOSITE_INTERPOLATE = 73;
	const COMPOSITE_NEGATE = 74;
	const COMPOSITE_REFLECT = 75;
	const COMPOSITE_SOFTBURN = 76;
	const COMPOSITE_SOFTDODGE = 77;
	const COMPOSITE_STAMP = 78;
	const COMPOSITE_RMSE = 79;
	const COMPOSITE_SALIENCY_BLEND = 80;
	const COMPOSITE_SEAMLESS_BLEND = 81;
	const MONTAGEMODE_FRAME = 1;
	const MONTAGEMODE_UNFRAME = 2;
	const MONTAGEMODE_CONCATENATE = 3;
	const STYLE_NORMAL = 1;
	const STYLE_ITALIC = 2;
	const STYLE_OBLIQUE = 3;
	const STYLE_ANY = 4;
	const STYLE_BOLD = 5;
	const FILTER_UNDEFINED = 0;
	const FILTER_POINT = 1;
	const FILTER_BOX = 2;
	const FILTER_TRIANGLE = 3;
	const FILTER_HERMITE = 4;
	const FILTER_HANNING = 5;
	const FILTER_HANN = 5;
	const FILTER_HAMMING = 6;
	const FILTER_BLACKMAN = 7;
	const FILTER_GAUSSIAN = 8;
	const FILTER_QUADRATIC = 9;
	const FILTER_CUBIC = 10;
	const FILTER_CATROM = 11;
	const FILTER_MITCHELL = 12;
	const FILTER_LANCZOS = 22;
	const FILTER_BESSEL = 13;
	const FILTER_SINC = 14;
	const FILTER_KAISER = 16;
	const FILTER_WELSH = 17;
	const FILTER_WELCH = 17;
	const FILTER_PARZEN = 18;
	const FILTER_LAGRANGE = 21;
	const FILTER_SENTINEL = 32;
	const FILTER_BOHMAN = 19;
	const FILTER_BARTLETT = 20;
	const FILTER_JINC = 13;
	const FILTER_SINCFAST = 15;
	const FILTER_ROBIDOUX = 26;
	const FILTER_LANCZOSSHARP = 23;
	const FILTER_LANCZOS2 = 24;
	const FILTER_LANCZOS2SHARP = 25;
	const FILTER_ROBIDOUXSHARP = 27;
	const FILTER_COSINE = 28;
	const FILTER_SPLINE = 29;
	const FILTER_LANCZOSRADIUS = 30;
	const FILTER_CUBIC_SPLINE = 31;
	const IMGTYPE_UNDEFINED = 0;
	const IMGTYPE_BILEVEL = 1;
	const IMGTYPE_GRAYSCALE = 2;
	const IMGTYPE_GRAYSCALEALPHA = 3;
	const IMGTYPE_GRAYSCALEMATTE = 3;
	const IMGTYPE_PALETTE = 4;
	const IMGTYPE_PALETTEMATTE = 5;
	const IMGTYPE_PALETTEALPHA = 5;
	const IMGTYPE_TRUECOLOR = 6;
	const IMGTYPE_TRUECOLORALPHA = 7;
	const IMGTYPE_TRUECOLORMATTE = 7;
	const IMGTYPE_COLORSEPARATION = 8;
	const IMGTYPE_COLORSEPARATIONALPHA = 9;
	const IMGTYPE_COLORSEPARATIONMATTE = 9;
	const IMGTYPE_OPTIMIZE = 10;
	const IMGTYPE_PALETTEBILEVELALPHA = 11;
	const IMGTYPE_PALETTEBILEVELMATTE = 11;
	const RESOLUTION_UNDEFINED = 0;
	const RESOLUTION_PIXELSPERINCH = 1;
	const RESOLUTION_PIXELSPERCENTIMETER = 2;
	const COMPRESSION_UNDEFINED = 0;
	const COMPRESSION_NO = 16;
	const COMPRESSION_BZIP = 3;
	const COMPRESSION_FAX = 7;
	const COMPRESSION_GROUP4 = 8;
	const COMPRESSION_JPEG = 12;
	const COMPRESSION_JPEG2000 = 11;
	const COMPRESSION_LOSSLESSJPEG = 13;
	const COMPRESSION_LZW = 15;
	const COMPRESSION_RLE = 19;
	const COMPRESSION_ZIP = 20;
	const COMPRESSION_DXT1 = 4;
	const COMPRESSION_DXT3 = 5;
	const COMPRESSION_DXT5 = 6;
	const COMPRESSION_ZIPS = 21;
	const COMPRESSION_PIZ = 17;
	const COMPRESSION_PXR24 = 18;
	const COMPRESSION_B44 = 2;
	const COMPRESSION_B44A = 1;
	const COMPRESSION_LZMA = 14;
	const COMPRESSION_JBIG1 = 9;
	const COMPRESSION_JBIG2 = 10;
	const COMPRESSION_ZSTD = 22;
	const COMPRESSION_WEBP = 23;
	const COMPRESSION_DWAA = 24;
	const COMPRESSION_DWAB = 25;
	const COMPRESSION_BC7 = 26;
	const PAINT_POINT = 1;
	const PAINT_REPLACE = 2;
	const PAINT_FLOODFILL = 3;
	const PAINT_FILLTOBORDER = 4;
	const PAINT_RESET = 5;
	const GRAVITY_NORTHWEST = 1;
	const GRAVITY_NORTH = 2;
	const GRAVITY_NORTHEAST = 3;
	const GRAVITY_WEST = 4;
	const GRAVITY_CENTER = 5;
	const GRAVITY_EAST = 6;
	const GRAVITY_SOUTHWEST = 7;
	const GRAVITY_SOUTH = 8;
	const GRAVITY_SOUTHEAST = 9;
	const GRAVITY_FORGET = 0;
	const STRETCH_NORMAL = 1;
	const STRETCH_ULTRACONDENSED = 2;
	const STRETCH_EXTRACONDENSED = 3;
	const STRETCH_CONDENSED = 4;
	const STRETCH_SEMICONDENSED = 5;
	const STRETCH_SEMIEXPANDED = 6;
	const STRETCH_EXPANDED = 7;
	const STRETCH_EXTRAEXPANDED = 8;
	const STRETCH_ULTRAEXPANDED = 9;
	const STRETCH_ANY = 10;
	const ALIGN_UNDEFINED = 0;
	const ALIGN_LEFT = 1;
	const ALIGN_CENTER = 2;
	const ALIGN_RIGHT = 3;
	const DECORATION_NO = 1;
	const DECORATION_UNDERLINE = 2;
	const DECORATION_OVERLINE = 3;
	const DECORATION_LINETROUGH = 4;
	const DECORATION_LINETHROUGH = 4;
	const NOISE_UNIFORM = 1;
	const NOISE_GAUSSIAN = 2;
	const NOISE_MULTIPLICATIVEGAUSSIAN = 3;
	const NOISE_IMPULSE = 4;
	const NOISE_LAPLACIAN = 5;
	const NOISE_POISSON = 6;
	const NOISE_RANDOM = 7;
	const CHANNEL_UNDEFINED = 0;
	const CHANNEL_RED = 1;
	const CHANNEL_GRAY = 1;
	const CHANNEL_CYAN = 1;
	const CHANNEL_GREEN = 2;
	const CHANNEL_MAGENTA = 2;
	const CHANNEL_BLUE = 4;
	const CHANNEL_YELLOW = 4;
	const CHANNEL_ALPHA = 16;
	const CHANNEL_OPACITY = 16;
	const CHANNEL_BLACK = 8;
	const CHANNEL_INDEX = 32;
	const CHANNEL_ALL = 134217727;
	const CHANNEL_DEFAULT = 134217727;
	const CHANNEL_RGBA = 23;
	const CHANNEL_TRUEALPHA = 256;
	const CHANNEL_RGBS = 512;
	const CHANNEL_GRAY_CHANNELS = 1024;
	const CHANNEL_SYNC = 131072;
	const CHANNEL_READ_MASK = 64;
	const CHANNEL_WRITE_MASK = 128;
	const CHANNEL_META = 256;
	const CHANNEL_COMPOSITE_MASK = 512;
	const CHANNEL_COMPOSITES = 31;
	const METRIC_ABSOLUTEERRORMETRIC = 1;
	const METRIC_MEANABSOLUTEERROR = 3;
	const METRIC_MEANERRORPERPIXELMETRIC = 4;
	const METRIC_MEANSQUAREERROR = 5;
	const METRIC_PEAKABSOLUTEERROR = 7;
	const METRIC_PEAKSIGNALTONOISERATIO = 8;
	const METRIC_ROOTMEANSQUAREDERROR = 10;
	const METRIC_NORMALIZEDCROSSCORRELATIONERRORMETRIC = 6;
	const METRIC_FUZZERROR = 2;
	const METRIC_PERCEPTUALHASH_ERROR = 9;
	const METRIC_STRUCTURAL_SIMILARITY_ERROR = 11;
	const METRIC_STRUCTURAL_DISSIMILARITY_ERROR = 12;
	const PIXEL_CHAR = 1;
	const PIXELSTORAGE_CHAR = 1;
	const PIXEL_DOUBLE = 2;
	const PIXELSTORAGE_DOUBLE = 2;
	const PIXEL_FLOAT = 3;
	const PIXELSTORAGE_FLOAT = 3;
	const PIXEL_LONG = 4;
	const PIXELSTORAGE_LONG = 4;
	const PIXEL_QUANTUM = 6;
	const PIXELSTORAGE_QUANTUM = 6;
	const PIXEL_SHORT = 7;
	const PIXELSTORAGE_SHORT = 7;
	const EVALUATE_UNDEFINED = 0;
	const EVALUATE_ADD = 2;
	const EVALUATE_AND = 4;
	const EVALUATE_DIVIDE = 6;
	const EVALUATE_LEFTSHIFT = 11;
	const EVALUATE_MAX = 13;
	const EVALUATE_MIN = 16;
	const EVALUATE_MULTIPLY = 18;
	const EVALUATE_OR = 19;
	const EVALUATE_RIGHTSHIFT = 22;
	const EVALUATE_SET = 24;
	const EVALUATE_SUBTRACT = 26;
	const EVALUATE_XOR = 32;
	const EVALUATE_POW = 21;
	const EVALUATE_LOG = 12;
	const EVALUATE_THRESHOLD = 29;
	const EVALUATE_THRESHOLDBLACK = 28;
	const EVALUATE_THRESHOLDWHITE = 30;
	const EVALUATE_GAUSSIANNOISE = 8;
	const EVALUATE_IMPULSENOISE = 9;
	const EVALUATE_LAPLACIANNOISE = 10;
	const EVALUATE_MULTIPLICATIVENOISE = 17;
	const EVALUATE_POISSONNOISE = 20;
	const EVALUATE_UNIFORMNOISE = 31;
	const EVALUATE_COSINE = 5;
	const EVALUATE_SINE = 25;
	const EVALUATE_ADDMODULUS = 3;
	const EVALUATE_MEAN = 14;
	const EVALUATE_ABS = 1;
	const EVALUATE_EXPONENTIAL = 7;
	const EVALUATE_MEDIAN = 15;
	const EVALUATE_SUM = 27;
	const EVALUATE_ROOT_MEAN_SQUARE = 23;
	const EVALUATE_INVERSE_LOG = 33;
	const COLORSPACE_UNDEFINED = 0;
	const COLORSPACE_RGB = 21;
	const COLORSPACE_GRAY = 3;
	const COLORSPACE_TRANSPARENT = 24;
	const COLORSPACE_OHTA = 18;
	const COLORSPACE_XYZ = 26;
	const COLORSPACE_YCBCR = 27;
	const COLORSPACE_YCC = 28;
	const COLORSPACE_YIQ = 30;
	const COLORSPACE_YPBPR = 31;
	const COLORSPACE_YUV = 32;
	const COLORSPACE_CMYK = 2;
	const COLORSPACE_SRGB = 23;
	const COLORSPACE_HSB = 6;
	const COLORSPACE_HSL = 8;
	const COLORSPACE_HWB = 10;
	const COLORSPACE_LOG = 15;
	const COLORSPACE_CMY = 1;
	const COLORSPACE_LUV = 17;
	const COLORSPACE_HCL = 4;
	const COLORSPACE_LCH = 12;
	const COLORSPACE_LMS = 16;
	const COLORSPACE_LCHAB = 13;
	const COLORSPACE_LCHUV = 14;
	const COLORSPACE_SCRGB = 22;
	const COLORSPACE_HSI = 7;
	const COLORSPACE_HSV = 9;
	const COLORSPACE_HCLP = 5;
	const COLORSPACE_YDBDR = 29;
	const COLORSPACE_REC601YCBCR = 19;
	const COLORSPACE_REC709YCBCR = 20;
	const COLORSPACE_XYY = 25;
	const COLORSPACE_LINEARGRAY = 33;
	const COLORSPACE_DISPLAYP3 = 35;
	const COLORSPACE_ADOBE98 = 36;
	const COLORSPACE_PROPHOTO = 37;
	const COLORSPACE_JZAZBZ = 34;
	const VIRTUALPIXELMETHOD_UNDEFINED = 0;
	const VIRTUALPIXELMETHOD_BACKGROUND = 1;
	const VIRTUALPIXELMETHOD_EDGE = 3;
	const VIRTUALPIXELMETHOD_MIRROR = 4;
	const VIRTUALPIXELMETHOD_TILE = 6;
	const VIRTUALPIXELMETHOD_TRANSPARENT = 7;
	const VIRTUALPIXELMETHOD_MASK = 8;
	const VIRTUALPIXELMETHOD_BLACK = 9;
	const VIRTUALPIXELMETHOD_GRAY = 10;
	const VIRTUALPIXELMETHOD_WHITE = 11;
	const VIRTUALPIXELMETHOD_HORIZONTALTILE = 12;
	const VIRTUALPIXELMETHOD_VERTICALTILE = 13;
	const VIRTUALPIXELMETHOD_HORIZONTALTILEEDGE = 14;
	const VIRTUALPIXELMETHOD_VERTICALTILEEDGE = 15;
	const VIRTUALPIXELMETHOD_CHECKERTILE = 16;
	const VIRTUALPIXELMETHOD_DITHER = 2;
	const VIRTUALPIXELMETHOD_RANDOM = 5;
	const PREVIEW_UNDEFINED = 0;
	const PREVIEW_ROTATE = 1;
	const PREVIEW_SHEAR = 2;
	const PREVIEW_ROLL = 3;
	const PREVIEW_HUE = 4;
	const PREVIEW_SATURATION = 5;
	const PREVIEW_BRIGHTNESS = 6;
	const PREVIEW_GAMMA = 7;
	const PREVIEW_SPIFF = 8;
	const PREVIEW_DULL = 9;
	const PREVIEW_GRAYSCALE = 10;
	const PREVIEW_QUANTIZE = 11;
	const PREVIEW_DESPECKLE = 12;
	const PREVIEW_REDUCENOISE = 13;
	const PREVIEW_ADDNOISE = 14;
	const PREVIEW_SHARPEN = 15;
	const PREVIEW_BLUR = 16;
	const PREVIEW_THRESHOLD = 17;
	const PREVIEW_EDGEDETECT = 18;
	const PREVIEW_SPREAD = 19;
	const PREVIEW_SOLARIZE = 20;
	const PREVIEW_SHADE = 21;
	const PREVIEW_RAISE = 22;
	const PREVIEW_SEGMENT = 23;
	const PREVIEW_SWIRL = 24;
	const PREVIEW_IMPLODE = 25;
	const PREVIEW_WAVE = 26;
	const PREVIEW_OILPAINT = 27;
	const PREVIEW_CHARCOALDRAWING = 28;
	const PREVIEW_JPEG = 29;
	const RENDERINGINTENT_UNDEFINED = 0;
	const RENDERINGINTENT_SATURATION = 1;
	const RENDERINGINTENT_PERCEPTUAL = 2;
	const RENDERINGINTENT_ABSOLUTE = 3;
	const RENDERINGINTENT_RELATIVE = 4;
	const INTERLACE_UNDEFINED = 0;
	const INTERLACE_NO = 1;
	const INTERLACE_LINE = 2;
	const INTERLACE_PLANE = 3;
	const INTERLACE_PARTITION = 4;
	const INTERLACE_GIF = 5;
	const INTERLACE_JPEG = 6;
	const INTERLACE_PNG = 7;
	const FILLRULE_UNDEFINED = 0;
	const FILLRULE_EVENODD = 1;
	const FILLRULE_NONZERO = 2;
	const PATHUNITS_UNDEFINED = 0;
	const PATHUNITS_USERSPACE = 1;
	const PATHUNITS_USERSPACEONUSE = 2;
	const PATHUNITS_OBJECTBOUNDINGBOX = 3;
	const LINECAP_UNDEFINED = 0;
	const LINECAP_BUTT = 1;
	const LINECAP_ROUND = 2;
	const LINECAP_SQUARE = 3;
	const LINEJOIN_UNDEFINED = 0;
	const LINEJOIN_MITER = 1;
	const LINEJOIN_ROUND = 2;
	const LINEJOIN_BEVEL = 3;
	const RESOURCETYPE_UNDEFINED = 0;
	const RESOURCETYPE_AREA = 1;
	const RESOURCETYPE_DISK = 2;
	const RESOURCETYPE_FILE = 3;
	const RESOURCETYPE_MAP = 5;
	const RESOURCETYPE_MEMORY = 6;
	const RESOURCETYPE_TIME = 9;
	const RESOURCETYPE_THROTTLE = 8;
	const RESOURCETYPE_THREAD = 7;
	const RESOURCETYPE_WIDTH = 10;
	const RESOURCETYPE_HEIGHT = 4;
	const RESOURCETYPE_LISTLENGTH = 11;
	const DISPOSE_UNRECOGNIZED = 0;
	const DISPOSE_UNDEFINED = 0;
	const DISPOSE_NONE = 1;
	const DISPOSE_BACKGROUND = 2;
	const DISPOSE_PREVIOUS = 3;
	const INTERPOLATE_UNDEFINED = 0;
	const INTERPOLATE_AVERAGE = 1;
	const INTERPOLATE_BILINEAR = 5;
	const INTERPOLATE_INTEGER = 8;
	const INTERPOLATE_MESH = 9;
	const INTERPOLATE_SPLINE = 11;
	const INTERPOLATE_AVERAGE_9 = 2;
	const INTERPOLATE_AVERAGE_16 = 3;
	const INTERPOLATE_BLEND = 6;
	const INTERPOLATE_BACKGROUND_COLOR = 4;
	const INTERPOLATE_CATROM = 7;
	const INTERPOLATE_NEAREST_PIXEL = 10;
	const LAYERMETHOD_UNDEFINED = 0;
	const LAYERMETHOD_COALESCE = 1;
	const LAYERMETHOD_COMPAREANY = 2;
	const LAYERMETHOD_COMPARECLEAR = 3;
	const LAYERMETHOD_COMPAREOVERLAY = 4;
	const LAYERMETHOD_DISPOSE = 5;
	const LAYERMETHOD_OPTIMIZE = 6;
	const LAYERMETHOD_OPTIMIZEPLUS = 8;
	const LAYERMETHOD_OPTIMIZETRANS = 9;
	const LAYERMETHOD_COMPOSITE = 12;
	const LAYERMETHOD_OPTIMIZEIMAGE = 7;
	const LAYERMETHOD_REMOVEDUPS = 10;
	const LAYERMETHOD_REMOVEZERO = 11;
	const LAYERMETHOD_TRIMBOUNDS = 16;
	const ORIENTATION_UNDEFINED = 0;
	const ORIENTATION_TOPLEFT = 1;
	const ORIENTATION_TOPRIGHT = 2;
	const ORIENTATION_BOTTOMRIGHT = 3;
	const ORIENTATION_BOTTOMLEFT = 4;
	const ORIENTATION_LEFTTOP = 5;
	const ORIENTATION_RIGHTTOP = 6;
	const ORIENTATION_RIGHTBOTTOM = 7;
	const ORIENTATION_LEFTBOTTOM = 8;
	const DISTORTION_UNDEFINED = 0;
	const DISTORTION_AFFINE = 1;
	const DISTORTION_AFFINEPROJECTION = 2;
	const DISTORTION_ARC = 9;
	const DISTORTION_BILINEAR = 6;
	const DISTORTION_PERSPECTIVE = 4;
	const DISTORTION_PERSPECTIVEPROJECTION = 5;
	const DISTORTION_SCALEROTATETRANSLATE = 3;
	const DISTORTION_POLYNOMIAL = 8;
	const DISTORTION_POLAR = 10;
	const DISTORTION_DEPOLAR = 11;
	const DISTORTION_BARREL = 14;
	const DISTORTION_SHEPARDS = 16;
	const DISTORTION_SENTINEL = 18;
	const DISTORTION_RIGID_AFFINE = 19;
	const DISTORTION_BARRELINVERSE = 15;
	const DISTORTION_BILINEARFORWARD = 6;
	const DISTORTION_BILINEARREVERSE = 7;
	const DISTORTION_RESIZE = 17;
	const DISTORTION_CYLINDER2PLANE = 12;
	const DISTORTION_PLANE2CYLINDER = 13;
	const LAYERMETHOD_MERGE = 13;
	const LAYERMETHOD_FLATTEN = 14;
	const LAYERMETHOD_MOSAIC = 15;
	const ALPHACHANNEL_ACTIVATE = 1;
	const ALPHACHANNEL_ON = 10;
	const ALPHACHANNEL_SET = 13;
	const ALPHACHANNEL_UNDEFINED = 0;
	const ALPHACHANNEL_DISCRETE = 6;
	const ALPHACHANNEL_COPY = 4;
	const ALPHACHANNEL_DEACTIVATE = 5;
	const ALPHACHANNEL_EXTRACT = 8;
	const ALPHACHANNEL_OFF = 9;
	const ALPHACHANNEL_OPAQUE = 11;
	const ALPHACHANNEL_SHAPE = 14;
	const ALPHACHANNEL_TRANSPARENT = 15;
	const ALPHACHANNEL_ASSOCIATE = 2;
	const ALPHACHANNEL_DISSOCIATE = 7;
	const SPARSECOLORMETHOD_UNDEFINED = 0;
	const SPARSECOLORMETHOD_BARYCENTRIC = 1;
	const SPARSECOLORMETHOD_BILINEAR = 7;
	const SPARSECOLORMETHOD_POLYNOMIAL = 8;
	const SPARSECOLORMETHOD_SPEPARDS = 16;
	const SPARSECOLORMETHOD_VORONOI = 18;
	const SPARSECOLORMETHOD_INVERSE = 19;
	const SPARSECOLORMETHOD_MANHATTAN = 20;
	const DITHERMETHOD_UNDEFINED = 0;
	const DITHERMETHOD_NO = 1;
	const DITHERMETHOD_RIEMERSMA = 2;
	const DITHERMETHOD_FLOYDSTEINBERG = 3;
	const FUNCTION_UNDEFINED = 0;
	const FUNCTION_POLYNOMIAL = 3;
	const FUNCTION_SINUSOID = 4;
	const ALPHACHANNEL_BACKGROUND = 3;
	const FUNCTION_ARCSIN = 1;
	const FUNCTION_ARCTAN = 2;
	const ALPHACHANNEL_REMOVE = 12;
	const STATISTIC_GRADIENT = 1;
	const STATISTIC_MAXIMUM = 2;
	const STATISTIC_MEAN = 3;
	const STATISTIC_MEDIAN = 4;
	const STATISTIC_MINIMUM = 5;
	const STATISTIC_MODE = 6;
	const STATISTIC_NONPEAK = 7;
	const STATISTIC_STANDARD_DEVIATION = 9;
	const STATISTIC_ROOT_MEAN_SQUARE = 8;
	const STATISTIC_CONTRAST = 10;
	const MORPHOLOGY_CONVOLVE = 1;
	const MORPHOLOGY_CORRELATE = 2;
	const MORPHOLOGY_ERODE = 3;
	const MORPHOLOGY_DILATE = 4;
	const MORPHOLOGY_ERODE_INTENSITY = 5;
	const MORPHOLOGY_DILATE_INTENSITY = 6;
	const MORPHOLOGY_DISTANCE = 21;
	const MORPHOLOGY_OPEN = 8;
	const MORPHOLOGY_CLOSE = 9;
	const MORPHOLOGY_OPEN_INTENSITY = 10;
	const MORPHOLOGY_CLOSE_INTENSITY = 11;
	const MORPHOLOGY_SMOOTH = 12;
	const MORPHOLOGY_EDGE_IN = 13;
	const MORPHOLOGY_EDGE_OUT = 14;
	const MORPHOLOGY_EDGE = 15;
	const MORPHOLOGY_TOP_HAT = 16;
	const MORPHOLOGY_BOTTOM_HAT = 17;
	const MORPHOLOGY_HIT_AND_MISS = 18;
	const MORPHOLOGY_THINNING = 19;
	const MORPHOLOGY_THICKEN = 20;
	const MORPHOLOGY_VORONOI = 22;
	const MORPHOLOGY_ITERATIVE = 7;
	const KERNEL_UNITY = 1;
	const KERNEL_GAUSSIAN = 2;
	const KERNEL_DIFFERENCE_OF_GAUSSIANS = 3;
	const KERNEL_LAPLACIAN_OF_GAUSSIANS = 4;
	const KERNEL_BLUR = 5;
	const KERNEL_COMET = 6;
	const KERNEL_LAPLACIAN = 8;
	const KERNEL_SOBEL = 9;
	const KERNEL_FREI_CHEN = 10;
	const KERNEL_ROBERTS = 11;
	const KERNEL_PREWITT = 12;
	const KERNEL_COMPASS = 13;
	const KERNEL_KIRSCH = 14;
	const KERNEL_DIAMOND = 15;
	const KERNEL_SQUARE = 16;
	const KERNEL_RECTANGLE = 17;
	const KERNEL_OCTAGON = 18;
	const KERNEL_DISK = 19;
	const KERNEL_PLUS = 20;
	const KERNEL_CROSS = 21;
	const KERNEL_RING = 22;
	const KERNEL_PEAKS = 23;
	const KERNEL_EDGES = 24;
	const KERNEL_CORNERS = 25;
	const KERNEL_DIAGONALS = 26;
	const KERNEL_LINE_ENDS = 27;
	const KERNEL_LINE_JUNCTIONS = 28;
	const KERNEL_RIDGES = 29;
	const KERNEL_CONVEX_HULL = 30;
	const KERNEL_THIN_SE = 31;
	const KERNEL_SKELETON = 32;
	const KERNEL_CHEBYSHEV = 33;
	const KERNEL_MANHATTAN = 34;
	const KERNEL_OCTAGONAL = 35;
	const KERNEL_EUCLIDEAN = 36;
	const KERNEL_USER_DEFINED = 37;
	const KERNEL_BINOMIAL = 7;
	const DIRECTION_LEFT_TO_RIGHT = 2;
	const DIRECTION_RIGHT_TO_LEFT = 1;
	const NORMALIZE_KERNEL_NONE = 0;
	const NORMALIZE_KERNEL_VALUE = 8192;
	const NORMALIZE_KERNEL_CORRELATE = 65536;
	const NORMALIZE_KERNEL_PERCENT = 4096;
	const PIXELMASK_READ = 1;
	const PIXELMASK_WRITE = 2;
	const PIXELMASK_COMPOSITE = 4;
	const AUTO_THRESHOLD_KAPUR = 1;
	const AUTO_THRESHOLD_OTSU = 2;
	const AUTO_THRESHOLD_TRIANGLE = 3;
	const COMPLEX_OPERATOR_ADD = 1;
	const COMPLEX_OPERATOR_CONJUGATE = 2;
	const COMPLEX_OPERATOR_DIVIDE = 3;
	const COMPLEX_OPERATOR_MAGNITUDEPHASE = 4;
	const COMPLEX_OPERATOR_MULTIPLY = 5;
	const COMPLEX_OPERATOR_REALIMAGINARY = 6;
	const COMPLEX_OPERATOR_SUBTRACT = 7;
	const IMAGE_TYPE_BILEVEL = 1;
	const IMAGE_TYPE_GRAYSCALE = 2;
	const IMAGE_TYPE_GRAYSCALE_ALPHA = 3;
	const IMAGE_TYPE_PALETTE = 4;
	const IMAGE_TYPE_PALETTE_ALPHA = 5;
	const IMAGE_TYPE_TRUE_COLOR = 6;
	const IMAGE_TYPE_TRUE_COLOR_ALPHA = 7;
	const IMAGE_TYPE_COLOR_SEPARATION = 8;
	const IMAGE_TYPE_COLOR_SEPARATION_ALPHA = 9;
	const IMAGE_TYPE_OPTIMIZE = 10;
	const IMAGE_TYPE_PALETTE_BILEVEL_ALPHA = 11;


	/**
	 * Removes repeated portions of images to optimize
	 * @link http://www.php.net/manual/en/imagick.optimizeimagelayers.php
	 * @return bool true on success.
	 */
	public function optimizeImageLayers (): bool {}

	/**
	 * Returns the maximum bounding region between images
	 * @link http://www.php.net/manual/en/imagick.compareimagelayers.php
	 * @param int $method One of the layer method constants.
	 * @return Imagick true on success.
	 */
	public function compareImageLayers (int $method): Imagick {}

	/**
	 * Quickly fetch attributes
	 * @link http://www.php.net/manual/en/imagick.pingimageblob.php
	 * @param string $image A string containing the image.
	 * @return bool true on success.
	 */
	public function pingImageBlob (string $image): bool {}

	/**
	 * Get basic image attributes in a lightweight manner
	 * @link http://www.php.net/manual/en/imagick.pingimagefile.php
	 * @param resource $filehandle An open filehandle to the image.
	 * @param string $fileName [optional] Optional filename for this image.
	 * @return bool true on success.
	 */
	public function pingImageFile ($filehandle, string $fileName = null): bool {}

	/**
	 * Creates a vertical mirror image
	 * @link http://www.php.net/manual/en/imagick.transposeimage.php
	 * @return bool true on success.
	 */
	public function transposeImage (): bool {}

	/**
	 * Creates a horizontal mirror image
	 * @link http://www.php.net/manual/en/imagick.transverseimage.php
	 * @return bool true on success.
	 */
	public function transverseImage (): bool {}

	/**
	 * Remove edges from the image
	 * @link http://www.php.net/manual/en/imagick.trimimage.php
	 * @param float $fuzz By default target must match a particular pixel color exactly.
	 * However, in many cases two colors may differ by a small amount.
	 * The fuzz member of image defines how much tolerance is acceptable
	 * to consider two colors as the same. This parameter represents the variation
	 * on the quantum range.
	 * @return bool true on success.
	 */
	public function trimImage (float $fuzz): bool {}

	/**
	 * Applies wave filter to the image
	 * @link http://www.php.net/manual/en/imagick.waveimage.php
	 * @param float $amplitude The amplitude of the wave.
	 * @param float $length The length of the wave.
	 * @return bool true on success.
	 */
	public function waveImage (float $amplitude, float $length): bool {}

	/**
	 * @param float $amplitude
	 * @param float $length
	 * @param int $interpolate_method
	 */
	public function waveImageWithMethod (float $amplitude, float $length, int $interpolate_method): bool {}

	/**
	 * Adds vignette filter to the image
	 * @link http://www.php.net/manual/en/imagick.vignetteimage.php
	 * @param float $blackPoint The black point.
	 * @param float $whitePoint The white point
	 * @param int $x X offset of the ellipse
	 * @param int $y Y offset of the ellipse
	 * @return bool true on success.
	 */
	public function vignetteImage (float $blackPoint, float $whitePoint, int $x, int $y): bool {}

	/**
	 * Discards all but one of any pixel color
	 * @link http://www.php.net/manual/en/imagick.uniqueimagecolors.php
	 * @return bool true on success.
	 */
	public function uniqueImageColors (): bool {}

	/**
	 * Sets the image matte channel
	 * @link http://www.php.net/manual/en/imagick.setimagematte.php
	 * @param bool $matte True activates the matte channel and false disables it.
	 * @return bool true on success.
	 */
	public function setImageMatte (bool $matte): bool {}

	/**
	 * Adaptively resize image with data dependent triangulation
	 * @link http://www.php.net/manual/en/imagick.adaptiveresizeimage.php
	 * @param int $columns The number of columns in the scaled image.
	 * @param int $rows The number of rows in the scaled image.
	 * @param bool $bestfit [optional] Whether to fit the image inside a bounding box.
	 * @param bool $legacy [optional] 
	 * @return bool true on success.
	 */
	public function adaptiveResizeImage (int $columns, int $rows, bool $bestfit = null, bool $legacy = null): bool {}

	/**
	 * Simulates a pencil sketch
	 * @link http://www.php.net/manual/en/imagick.sketchimage.php
	 * @param float $radius The radius of the Gaussian, in pixels, not counting the center pixel
	 * @param float $sigma The standard deviation of the Gaussian, in pixels.
	 * @param float $angle Apply the effect along this angle.
	 * @return bool true on success.
	 */
	public function sketchImage (float $radius, float $sigma, float $angle): bool {}

	/**
	 * Creates a 3D effect
	 * @link http://www.php.net/manual/en/imagick.shadeimage.php
	 * @param bool $gray A value other than zero shades the intensity of each pixel.
	 * @param float $azimuth Defines the light source direction.
	 * @param float $elevation Defines the light source direction.
	 * @return bool true on success.
	 */
	public function shadeImage (bool $gray, float $azimuth, float $elevation): bool {}

	/**
	 * Returns the size offset
	 * @link http://www.php.net/manual/en/imagick.getsizeoffset.php
	 * @return int the size offset associated with the Imagick object.
	 */
	public function getSizeOffset (): int {}

	/**
	 * Sets the size and offset of the Imagick object
	 * @link http://www.php.net/manual/en/imagick.setsizeoffset.php
	 * @param int $columns The width in pixels.
	 * @param int $rows The height in pixels.
	 * @param int $offset The image offset.
	 * @return bool true on success.
	 */
	public function setSizeOffset (int $columns, int $rows, int $offset): bool {}

	/**
	 * Adds adaptive blur filter to image
	 * @link http://www.php.net/manual/en/imagick.adaptiveblurimage.php
	 * @param float $radius The radius of the Gaussian, in pixels, not counting the center pixel.
	 * Provide a value of 0 and the radius will be chosen automagically.
	 * @param float $sigma The standard deviation of the Gaussian, in pixels.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function adaptiveBlurImage (float $radius, float $sigma, int $channel = null): bool {}

	/**
	 * Enhances the contrast of a color image
	 * @link http://www.php.net/manual/en/imagick.contraststretchimage.php
	 * @param float $black_point The black point.
	 * @param float $white_point The white point.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Imagick::CHANNEL_ALL. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function contrastStretchImage (float $black_point, float $white_point, int $channel = null): bool {}

	/**
	 * Adaptively sharpen the image
	 * @link http://www.php.net/manual/en/imagick.adaptivesharpenimage.php
	 * @param float $radius The radius of the Gaussian, in pixels, not counting the center pixel. Use 0 for auto-select.
	 * @param float $sigma The standard deviation of the Gaussian, in pixels.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function adaptiveSharpenImage (float $radius, float $sigma, int $channel = null): bool {}

	/**
	 * Creates a high-contrast, two-color image
	 * @link http://www.php.net/manual/en/imagick.randomthresholdimage.php
	 * @param float $low The low point
	 * @param float $high The high point
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function randomThresholdImage (float $low, float $high, int $channel = null): bool {}

	/**
	 * @param float $x_rounding
	 * @param float $y_rounding
	 * @param float $stroke_width [optional]
	 * @param float $displace [optional]
	 * @param float $size_correction [optional]
	 */
	public function roundCornersImage (float $x_rounding, float $y_rounding, float $stroke_width = 10, float $displace = 5, float $size_correction = -6): bool {}

	/**
	 * Rounds image corners
	 * @link http://www.php.net/manual/en/imagick.roundcorners.php
	 * @param float $x_rounding x rounding
	 * @param float $y_rounding y rounding
	 * @param float $stroke_width [optional] stroke width
	 * @param float $displace [optional] image displace
	 * @param float $size_correction [optional] size correction
	 * @return bool true on success.
	 */
	public function roundCorners (float $x_rounding, float $y_rounding, float $stroke_width = null, float $displace = null, float $size_correction = null): bool {}

	/**
	 * Set the iterator position
	 * @link http://www.php.net/manual/en/imagick.setiteratorindex.php
	 * @param int $index The position to set the iterator to
	 * @return bool true on success.
	 */
	public function setIteratorIndex (int $index): bool {}

	/**
	 * Gets the index of the current active image
	 * @link http://www.php.net/manual/en/imagick.getiteratorindex.php
	 * @return int an integer containing the index of the image in the stack.
	 */
	public function getIteratorIndex (): int {}

	/**
	 * @param float $alpha
	 */
	public function setImageAlpha (float $alpha): bool {}

	/**
	 * @param ImagickDraw $settings
	 * @param float $angle
	 * @param string $caption
	 * @param int $method
	 */
	public function polaroidWithTextAndMethod (ImagickDraw $settings, float $angle, string $caption, int $method): bool {}

	/**
	 * Simulates a Polaroid picture
	 * @link http://www.php.net/manual/en/imagick.polaroidimage.php
	 * @param ImagickDraw $properties The polaroid properties
	 * @param float $angle The polaroid angle
	 * @return bool true on success.
	 */
	public function polaroidImage (ImagickDraw $properties, float $angle): bool {}

	/**
	 * Returns the named image property
	 * @link http://www.php.net/manual/en/imagick.getimageproperty.php
	 * @param string $name name of the property (for example Exif:DateTime)
	 * @return string a string containing the image property, false if a
	 * property with the given name does not exist.
	 */
	public function getImageProperty (string $name): string {}

	/**
	 * Sets an image property
	 * @link http://www.php.net/manual/en/imagick.setimageproperty.php
	 * @param string $name 
	 * @param string $value 
	 * @return bool true on success.
	 */
	public function setImageProperty (string $name, string $value): bool {}

	/**
	 * Deletes an image property
	 * @link http://www.php.net/manual/en/imagick.deleteimageproperty.php
	 * @param string $name The name of the property to delete.
	 * @return bool true on success.
	 */
	public function deleteImageProperty (string $name): bool {}

	/**
	 * Formats a string with image details
	 * @link http://www.php.net/manual/en/imagick.identifyformat.php
	 * @param string $embedText A string containing formatting sequences e.g. "Trim box: %@ number of unique colors: %k".
	 * @return mixed format or false on failure.
	 */
	public function identifyFormat (string $embedText): string {}

	/**
	 * Sets the image interpolate pixel method
	 * @link http://www.php.net/manual/en/imagick.setimageinterpolatemethod.php
	 * @param int $method The method is one of the Imagick::INTERPOLATE_&#42; constants
	 * @return bool true on success.
	 */
	public function setImageInterpolateMethod (int $method): bool {}

	/**
	 * Returns the interpolation method
	 * @link http://www.php.net/manual/en/imagick.getimageinterpolatemethod.php
	 * @return int the interpolate method on success.
	 */
	public function getImageInterpolateMethod (): int {}

	/**
	 * Stretches with saturation the image intensity
	 * @link http://www.php.net/manual/en/imagick.linearstretchimage.php
	 * @param float $blackPoint The image black point
	 * @param float $whitePoint The image white point
	 * @return bool true on success.
	 */
	public function linearStretchImage (float $blackPoint, float $whitePoint): bool {}

	/**
	 * Returns the image length in bytes
	 * @link http://www.php.net/manual/en/imagick.getimagelength.php
	 * @return int an int containing the current image size.
	 */
	public function getImageLength (): int {}

	/**
	 * Set image size
	 * @link http://www.php.net/manual/en/imagick.extentimage.php
	 * @param int $width The new width
	 * @param int $height The new height
	 * @param int $x X position for the new size
	 * @param int $y Y position for the new size
	 * @return bool true on success.
	 */
	public function extentImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * Gets the image orientation
	 * @link http://www.php.net/manual/en/imagick.getimageorientation.php
	 * @return int an int on success.
	 */
	public function getImageOrientation (): int {}

	/**
	 * Sets the image orientation
	 * @link http://www.php.net/manual/en/imagick.setimageorientation.php
	 * @param int $orientation One of the orientation constants
	 * @return bool true on success.
	 */
	public function setImageOrientation (int $orientation): bool {}

	/**
	 * Replaces colors in the image
	 * @link http://www.php.net/manual/en/imagick.clutimage.php
	 * @param Imagick $lookup_table Imagick object containing the color lookup table
	 * @param int $channel [optional] The Channeltype
	 * constant. When not supplied, default channels are replaced.
	 * @return bool true on success.
	 */
	public function clutImage (Imagick $lookup_table, int $channel = null): bool {}

	/**
	 * Returns the image properties
	 * @link http://www.php.net/manual/en/imagick.getimageproperties.php
	 * @param string $pattern [optional] The pattern for property names.
	 * @param bool $include_values [optional] Whether to return only property names. If false then only property names will be returned.
	 * @return array an array containing the image properties or property names.
	 */
	public function getImageProperties (string $pattern = null, bool $include_values = null): array {}

	/**
	 * Returns the image profiles
	 * @link http://www.php.net/manual/en/imagick.getimageprofiles.php
	 * @param string $pattern [optional] The pattern for profile names.
	 * @param bool $include_values [optional] Whether to return only profile names. If false then only profile names will be returned.
	 * @return array an array containing the image profiles or profile names.
	 */
	public function getImageProfiles (string $pattern = null, bool $include_values = null): array {}

	/**
	 * Distorts an image using various distortion methods
	 * @link http://www.php.net/manual/en/imagick.distortimage.php
	 * @param int $method The method of image distortion. See distortion constants
	 * @param array $arguments The arguments for this distortion method
	 * @param bool $bestfit Attempt to resize destination to fit distorted source
	 * @return bool true on success.
	 */
	public function distortImage (int $method, array $arguments, bool $bestfit): bool {}

	/**
	 * Writes an image to a filehandle
	 * @link http://www.php.net/manual/en/imagick.writeimagefile.php
	 * @param resource $filehandle Filehandle where to write the image.
	 * @param string $format [optional] The image format.
	 * The list of valid format specifiers depends on the compiled feature set of
	 * ImageMagick, and can be queried at runtime via Imagick::queryFormats.
	 * @return bool true on success.
	 */
	public function writeImageFile ($filehandle, string $format = null): bool {}

	/**
	 * Writes frames to a filehandle
	 * @link http://www.php.net/manual/en/imagick.writeimagesfile.php
	 * @param resource $filehandle Filehandle where to write the images.
	 * @param string $format [optional] The image format.
	 * The list of valid format specifiers depends on the compiled feature set of
	 * ImageMagick, and can be queried at runtime via Imagick::queryFormats.
	 * @return bool true on success.
	 */
	public function writeImagesFile ($filehandle, string $format = null): bool {}

	/**
	 * Reset image page
	 * @link http://www.php.net/manual/en/imagick.resetimagepage.php
	 * @param string $page The page definition. For example 7168x5147+0+0
	 * @return bool true on success.
	 */
	public function resetImagePage (string $page): bool {}

	/**
	 * Animates an image or images
	 * @link http://www.php.net/manual/en/imagick.animateimages.php
	 * @param string $x_server X server address
	 * @return bool true on success.
	 */
	public function animateImages (string $x_server): bool {}

	/**
	 * Sets font
	 * @link http://www.php.net/manual/en/imagick.setfont.php
	 * @param string $font Font name or a filename
	 * @return bool true on success.
	 */
	public function setFont (string $font): bool {}

	/**
	 * Gets font
	 * @link http://www.php.net/manual/en/imagick.getfont.php
	 * @return string the string containing the font name or false if not font is set.
	 */
	public function getFont (): string {}

	/**
	 * Sets point size
	 * @link http://www.php.net/manual/en/imagick.setpointsize.php
	 * @param float $point_size Point size
	 * @return bool true on success.
	 */
	public function setPointSize (float $point_size): bool {}

	/**
	 * Gets point size
	 * @link http://www.php.net/manual/en/imagick.getpointsize.php
	 * @return float a float containing the point size.
	 */
	public function getPointSize (): float {}

	/**
	 * Merges image layers
	 * @link http://www.php.net/manual/en/imagick.mergeimagelayers.php
	 * @param int $layer_method One of the Imagick::LAYERMETHOD_&#42; constants
	 * @return Imagick an Imagick object containing the merged image.
	 */
	public function mergeImageLayers (int $layer_method): Imagick {}

	/**
	 * Sets image alpha channel
	 * @link http://www.php.net/manual/en/imagick.setimagealphachannel.php
	 * @param int $mode One of the Imagick::ALPHACHANNEL_&#42; constants
	 * @return bool true on success.
	 */
	public function setImageAlphaChannel (int $mode): bool {}

	/**
	 * Changes the color value of any pixel that matches target
	 * @link http://www.php.net/manual/en/imagick.floodfillpaintimage.php
	 * @param mixed $fill ImagickPixel object or a string containing the fill color
	 * @param float $fuzz The amount of fuzz. For example, set fuzz to 10 and the color red at intensities of 100 and 102 respectively are now interpreted as the same color.
	 * @param mixed $target ImagickPixel object or a string containing the target color to paint
	 * @param int $x X start position of the floodfill
	 * @param int $y Y start position of the floodfill
	 * @param bool $invert If true paints any pixel that does not match the target color.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function floodfillPaintImage ($fill, float $fuzz, $target, int $x, int $y, bool $invert, int $channel = null): bool {}

	/**
	 * Changes the color value of any pixel that matches target
	 * @link http://www.php.net/manual/en/imagick.opaquepaintimage.php
	 * @param mixed $target ImagickPixel object or a string containing the color to change
	 * @param mixed $fill The replacement color
	 * @param float $fuzz The amount of fuzz. For example, set fuzz to 10 and the color red at intensities of 100 and 102 respectively are now interpreted as the same color.
	 * @param bool $invert If true paints any pixel that does not match the target color.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function opaquePaintImage ($target, $fill, float $fuzz, bool $invert, int $channel = null): bool {}

	/**
	 * Paints pixels transparent
	 * @link http://www.php.net/manual/en/imagick.transparentpaintimage.php
	 * @param mixed $target The target color to paint
	 * @param float $alpha The level of transparency: 1.0 is fully opaque and 0.0 is fully transparent.
	 * @param float $fuzz The amount of fuzz. For example, set fuzz to 10 and the color red at intensities of 100 and 102 respectively are now interpreted as the same color.
	 * @param bool $invert If true paints any pixel that does not match the target color.
	 * @return bool true on success.
	 */
	public function transparentPaintImage ($target, float $alpha, float $fuzz, bool $invert): bool {}

	/**
	 * Animates an image or images
	 * @link http://www.php.net/manual/en/imagick.liquidrescaleimage.php
	 * @param int $width The width of the target size
	 * @param int $height The height of the target size
	 * @param float $delta_x How much the seam can traverse on x-axis. 
	 * Passing 0 causes the seams to be straight.
	 * @param float $rigidity Introduces a bias for non-straight seams. This parameter is 
	 * typically 0.
	 * @return bool true on success.
	 */
	public function liquidRescaleImage (int $width, int $height, float $delta_x, float $rigidity): bool {}

	/**
	 * Enciphers an image
	 * @link http://www.php.net/manual/en/imagick.encipherimage.php
	 * @param string $passphrase The passphrase
	 * @return bool true on success.
	 */
	public function encipherImage (string $passphrase): bool {}

	/**
	 * Deciphers an image
	 * @link http://www.php.net/manual/en/imagick.decipherimage.php
	 * @param string $passphrase The passphrase
	 * @return bool true on success.
	 */
	public function decipherImage (string $passphrase): bool {}

	/**
	 * Sets the gravity
	 * @link http://www.php.net/manual/en/imagick.setgravity.php
	 * @param int $gravity The gravity property. Refer to the list of 
	 * gravity constants.
	 * @return bool 
	 */
	public function setGravity (int $gravity): bool {}

	/**
	 * Gets the gravity
	 * @link http://www.php.net/manual/en/imagick.getgravity.php
	 * @return int the gravity property. Refer to the list of 
	 * gravity constants.
	 */
	public function getGravity (): int {}

	/**
	 * Gets channel range
	 * @link http://www.php.net/manual/en/imagick.getimagechannelrange.php
	 * @param int $channel Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return array an array containing minima and maxima values of the channel(s).
	 */
	public function getImageChannelRange (int $channel): array {}

	/**
	 * Checks if the image has an alpha channel
	 * @link http://www.php.net/manual/en/imagick.getimagealphachannel.php
	 * @return bool true if the image has an alpha channel value and false if not,
	 * i.e. the image is RGB rather than RGBA
	 * or CMYK rather than CMYKA.
	 */
	public function getImageAlphaChannel (): bool {}

	/**
	 * Gets channel distortions
	 * @link http://www.php.net/manual/en/imagick.getimagechanneldistortions.php
	 * @param Imagick $reference Imagick object containing the reference image
	 * @param int $metric Refer to this list of metric type constants.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return float a float describing the channel distortion.
	 */
	public function getImageChannelDistortions (Imagick $reference, int $metric, int $channel = null): float {}

	/**
	 * Sets the image gravity
	 * @link http://www.php.net/manual/en/imagick.setimagegravity.php
	 * @param int $gravity The gravity property. Refer to the list of 
	 * gravity constants.
	 * @return bool 
	 */
	public function setImageGravity (int $gravity): bool {}

	/**
	 * Gets the image gravity
	 * @link http://www.php.net/manual/en/imagick.getimagegravity.php
	 * @return int the images gravity property. Refer to the list of 
	 * gravity constants.
	 */
	public function getImageGravity (): int {}

	/**
	 * Imports image pixels
	 * @link http://www.php.net/manual/en/imagick.importimagepixels.php
	 * @param int $x The image x position
	 * @param int $y The image y position
	 * @param int $width The image width
	 * @param int $height The image height
	 * @param string $map Map of pixel ordering as a string. This can be for example RGB.
	 * The value can be any combination or order of R = red, G = green, B = blue, A = alpha (0 is transparent), 
	 * O = opacity (0 is opaque), C = cyan, Y = yellow, M = magenta, K = black, I = intensity (for grayscale), P = pad.
	 * @param int $storage The pixel storage method. 
	 * Refer to this list of pixel constants.
	 * @param array $pixels The array of pixels
	 * @return bool true on success.
	 */
	public function importImagePixels (int $x, int $y, int $width, int $height, string $map, int $storage, array $pixels): bool {}

	/**
	 * Removes skew from the image
	 * @link http://www.php.net/manual/en/imagick.deskewimage.php
	 * @param float $threshold Deskew threshold
	 * @return bool 
	 */
	public function deskewImage (float $threshold): bool {}

	/**
	 * Segments an image
	 * @link http://www.php.net/manual/en/imagick.segmentimage.php
	 * @param int $COLORSPACE One of the COLORSPACE constants.
	 * @param float $cluster_threshold A percentage describing minimum number of pixels 
	 * contained in hexedra before it is considered valid.
	 * @param float $smooth_threshold Eliminates noise from the histogram.
	 * @param bool $verbose [optional] Whether to output detailed information about recognised classes.
	 * @return bool 
	 */
	public function segmentImage (int $COLORSPACE, float $cluster_threshold, float $smooth_threshold, bool $verbose = null): bool {}

	/**
	 * Interpolates colors
	 * @link http://www.php.net/manual/en/imagick.sparsecolorimage.php
	 * @param int $SPARSE_METHOD Refer to this list of sparse method constants
	 * @param array $arguments An array containing the coordinates. 
	 * The array is in format array(1,1, 2,45)
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function sparseColorImage (int $SPARSE_METHOD, array $arguments, int $channel = null): bool {}

	/**
	 * Remaps image colors
	 * @link http://www.php.net/manual/en/imagick.remapimage.php
	 * @param Imagick $replacement An Imagick object containing the replacement colors
	 * @param int $DITHER Refer to this list of dither method constants
	 * @return bool true on success.
	 */
	public function remapImage (Imagick $replacement, int $DITHER): bool {}

	/**
	 * @param int $width
	 * @param int $height
	 * @param float $threshold
	 */
	public function houghLineImage (int $width, int $height, float $threshold): bool {}

	/**
	 * Exports raw image pixels
	 * @link http://www.php.net/manual/en/imagick.exportimagepixels.php
	 * @param int $x X-coordinate of the exported area
	 * @param int $y Y-coordinate of the exported area
	 * @param int $width Width of the exported aread
	 * @param int $height Height of the exported area
	 * @param string $map Ordering of the exported pixels. For example "RGB". 
	 * Valid characters for the map are R, G, B, A, O, C, Y, M, K, I and P.
	 * @param int $STORAGE Refer to this list of pixel type constants
	 * @return array an array containing the pixels values.
	 */
	public function exportImagePixels (int $x, int $y, int $width, int $height, string $map, int $STORAGE): array {}

	/**
	 * The getImageChannelKurtosis purpose
	 * @link http://www.php.net/manual/en/imagick.getimagechannelkurtosis.php
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return array an array with kurtosis and skewness
	 * members.
	 */
	public function getImageChannelKurtosis (int $channel = null): array {}

	/**
	 * Applies a function on the image
	 * @link http://www.php.net/manual/en/imagick.functionimage.php
	 * @param int $function Refer to this list of function constants
	 * @param array $arguments Array of arguments to pass to this function.
	 * @param int $channel [optional] 
	 * @return bool true on success.
	 */
	public function functionImage (int $function, array $arguments, int $channel = null): bool {}

	/**
	 * Transforms an image to a new colorspace
	 * @link http://www.php.net/manual/en/imagick.transformimagecolorspace.php
	 * @param int $colorspace The colorspace the image should be transformed to, one of the COLORSPACE constants e.g. Imagick::COLORSPACE_CMYK.
	 * @return bool true on success.
	 */
	public function transformImageColorspace (int $colorspace): bool {}

	/**
	 * Replaces colors in the image
	 * @link http://www.php.net/manual/en/imagick.haldclutimage.php
	 * @param Imagick $clut Imagick object containing the Hald lookup image.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function haldClutImage (Imagick $clut, int $channel = null): bool {}

	/**
	 * Adjusts the levels of a particular image channel
	 * @link http://www.php.net/manual/en/imagick.autolevelimage.php
	 * @param int $channel [optional] Which channel should the auto-levelling should be done on.
	 * @return bool true on success.
	 */
	public function autoLevelImage (int $channel = null): bool {}

	/**
	 * Mutes the colors of the image
	 * @link http://www.php.net/manual/en/imagick.blueshiftimage.php
	 * @param float $factor [optional] 
	 * @return bool true on success.
	 */
	public function blueShiftImage (float $factor = null): bool {}

	/**
	 * Get image artifact
	 * @link http://www.php.net/manual/en/imagick.getimageartifact.php
	 * @param string $artifact The name of the artifact
	 * @return string the artifact value on success.
	 */
	public function getImageArtifact (string $artifact): ?string {}

	/**
	 * Set image artifact
	 * @link http://www.php.net/manual/en/imagick.setimageartifact.php
	 * @param string $artifact The name of the artifact
	 * @param string $value The value of the artifact
	 * @return bool true on success.
	 */
	public function setImageArtifact (string $artifact, string $value): bool {}

	/**
	 * Delete image artifact
	 * @link http://www.php.net/manual/en/imagick.deleteimageartifact.php
	 * @param string $artifact The name of the artifact to delete
	 * @return bool true on success.
	 */
	public function deleteImageArtifact (string $artifact): bool {}

	/**
	 * Gets the colorspace
	 * @link http://www.php.net/manual/en/imagick.getcolorspace.php
	 * @return int an integer which can be compared against COLORSPACE constants.
	 */
	public function getColorspace (): int {}

	/**
	 * Set colorspace
	 * @link http://www.php.net/manual/en/imagick.setcolorspace.php
	 * @param int $COLORSPACE One of the COLORSPACE constants
	 * @return bool true on success.
	 */
	public function setColorspace (int $COLORSPACE): bool {}

	/**
	 * Restricts the color range from 0 to the quantum depth.
	 * @link http://www.php.net/manual/en/imagick.clampimage.php
	 * @param int $channel [optional] 
	 * @return bool true on success or false on failure
	 */
	public function clampImage (int $channel = null): bool {}

	/**
	 * Takes all images from the current image pointer to the end of the image list and smushs them
	 * @link http://www.php.net/manual/en/imagick.smushimages.php
	 * @param bool $stack 
	 * @param int $offset 
	 * @return Imagick The new smushed image.
	 */
	public function smushImages (bool $stack, int $offset): Imagick {}

	/**
	 * The Imagick constructor
	 * @link http://www.php.net/manual/en/imagick.construct.php
	 * @param array|string|int|float|null $files [optional]
	 */
	public function __construct (array|string|int|float|null $files = null) {}

	/**
	 * Returns the image as a string
	 * @link http://www.php.net/manual/en/imagick.tostring.php
	 * @return string the string content on success or an empty string on failure.
	 */
	public function __toString (): string {}

	/**
	 * Get the number of images
	 * @link http://www.php.net/manual/en/imagick.count.php
	 * @param int $mode [optional] An unused argument. Currently there is a non-particularly well defined feature in PHP where calling count() on a countable object might (or might not) require this method to accept a parameter. This parameter is here to be conformant with the interface of countable, even though the param is not used.
	 * @return int the number of images.
	 */
	public function count (int $mode = null): int {}

	/**
	 * Returns a MagickPixelIterator
	 * @link http://www.php.net/manual/en/imagick.getpixeliterator.php
	 * @return ImagickPixelIterator an ImagickPixelIterator on success.
	 */
	public function getPixelIterator (): ImagickPixelIterator {}

	/**
	 * Get an ImagickPixelIterator for an image section
	 * @link http://www.php.net/manual/en/imagick.getpixelregioniterator.php
	 * @param int $x The x-coordinate of the region.
	 * @param int $y The y-coordinate of the region.
	 * @param int $columns The width of the region.
	 * @param int $rows The height of the region.
	 * @return ImagickPixelIterator an ImagickPixelIterator for an image section.
	 */
	public function getPixelRegionIterator (int $x, int $y, int $columns, int $rows): ImagickPixelIterator {}

	/**
	 * Reads image from filename
	 * @link http://www.php.net/manual/en/imagick.readimage.php
	 * @param string $filename 
	 * @return bool true on success.
	 */
	public function readImage (string $filename): bool {}

	/**
	 * Reads image from an array of filenames
	 * @link http://www.php.net/manual/en/imagick.readimages.php
	 * @param array $filenames 
	 * @return bool true on success.
	 */
	public function readImages (array $filenames): bool {}

	/**
	 * Reads image from a binary string
	 * @link http://www.php.net/manual/en/imagick.readimageblob.php
	 * @param string $image 
	 * @param string $filename [optional] 
	 * @return bool true on success.
	 */
	public function readImageBlob (string $image, string $filename = null): bool {}

	/**
	 * Sets the format of a particular image
	 * @link http://www.php.net/manual/en/imagick.setimageformat.php
	 * @param string $format String presentation of the image format. Format support
	 * depends on the ImageMagick installation.
	 * @return bool true on success.
	 */
	public function setImageFormat (string $format): bool {}

	/**
	 * Scales the size of an image
	 * @link http://www.php.net/manual/en/imagick.scaleimage.php
	 * @param int $cols 
	 * @param int $rows 
	 * @param bool $bestfit [optional] 
	 * @param bool $legacy [optional] 
	 * @return bool true on success.
	 */
	public function scaleImage (int $cols, int $rows, bool $bestfit = null, bool $legacy = null): bool {}

	/**
	 * Writes an image to the specified filename
	 * @link http://www.php.net/manual/en/imagick.writeimage.php
	 * @param string $filename [optional] Filename where to write the image. The extension of the filename
	 * defines the type of the file. 
	 * Format can be forced regardless of file extension using format: prefix,
	 * for example "jpg:test.png".
	 * @return bool true on success.
	 */
	public function writeImage (string $filename = null): bool {}

	/**
	 * Writes an image or image sequence
	 * @link http://www.php.net/manual/en/imagick.writeimages.php
	 * @param string $filename 
	 * @param bool $adjoin 
	 * @return bool true on success.
	 */
	public function writeImages (string $filename, bool $adjoin): bool {}

	/**
	 * Adds blur filter to image
	 * @link http://www.php.net/manual/en/imagick.blurimage.php
	 * @param float $radius Blur radius
	 * @param float $sigma Standard deviation
	 * @param int $channel [optional] The Channeltype
	 * constant. When not supplied, all channels are blurred.
	 * @return bool true on success.
	 */
	public function blurImage (float $radius, float $sigma, int $channel = null): bool {}

	/**
	 * Changes the size of an image
	 * @link http://www.php.net/manual/en/imagick.thumbnailimage.php
	 * @param int $columns Image width
	 * @param int $rows Image height
	 * @param bool $bestfit [optional] Whether to force maximum values
	 * @param bool $fill [optional] 
	 * @param bool $legacy [optional] 
	 * @return bool true on success.
	 */
	public function thumbnailImage (int $columns, int $rows, bool $bestfit = null, bool $fill = null, bool $legacy = null): bool {}

	/**
	 * Creates a crop thumbnail
	 * @link http://www.php.net/manual/en/imagick.cropthumbnailimage.php
	 * @param int $width The width of the thumbnail
	 * @param int $height The Height of the thumbnail
	 * @param bool $legacy [optional] 
	 * @return bool true on success.
	 */
	public function cropThumbnailImage (int $width, int $height, bool $legacy = null): bool {}

	/**
	 * Returns the filename of a particular image in a sequence
	 * @link http://www.php.net/manual/en/imagick.getimagefilename.php
	 * @return string a string with the filename of the image.
	 */
	public function getImageFilename (): string {}

	/**
	 * Sets the filename of a particular image
	 * @link http://www.php.net/manual/en/imagick.setimagefilename.php
	 * @param string $filename 
	 * @return bool true on success.
	 */
	public function setImageFilename (string $filename): bool {}

	/**
	 * Returns the format of a particular image in a sequence
	 * @link http://www.php.net/manual/en/imagick.getimageformat.php
	 * @return string a string containing the image format on success.
	 */
	public function getImageFormat (): string {}

	/**
	 * Returns the image mime-type
	 * @link http://www.php.net/manual/en/imagick.getimagemimetype.php
	 * @return string 
	 */
	public function getImageMimeType (): string {}

	/**
	 * Removes an image from the image list
	 * @link http://www.php.net/manual/en/imagick.removeimage.php
	 * @return bool true on success.
	 */
	public function removeImage (): bool {}

	/**
	 * Destroys the Imagick object
	 * @link http://www.php.net/manual/en/imagick.destroy.php
	 * @return bool true on success.
	 */
	public function destroy (): bool {}

	/**
	 * Clears all resources associated to Imagick object
	 * @link http://www.php.net/manual/en/imagick.clear.php
	 * @return bool true on success.
	 */
	public function clear (): bool {}

	/**
	 * Makes an exact copy of the Imagick object
	 * @link http://www.php.net/manual/en/imagick.clone.php
	 * @return Imagick A copy of the Imagick object is returned.
	 */
	public function clone (): Imagick {}

	/**
	 * Returns the image length in bytes
	 * @link http://www.php.net/manual/en/imagick.getimagesize.php
	 * @return int an int containing the current image size.
	 */
	public function getImageSize (): int {}

	/**
	 * Returns the image sequence as a blob
	 * @link http://www.php.net/manual/en/imagick.getimageblob.php
	 * @return string a string containing the image.
	 */
	public function getImageBlob (): string {}

	/**
	 * Returns all image sequences as a blob
	 * @link http://www.php.net/manual/en/imagick.getimagesblob.php
	 * @return string a string containing the images. On failure, throws
	 * ImagickException.
	 */
	public function getImagesBlob (): string {}

	/**
	 * Sets the Imagick iterator to the first image
	 * @link http://www.php.net/manual/en/imagick.setfirstiterator.php
	 * @return bool true on success.
	 */
	public function setFirstIterator (): bool {}

	/**
	 * Sets the Imagick iterator to the last image
	 * @link http://www.php.net/manual/en/imagick.setlastiterator.php
	 * @return bool true on success.
	 */
	public function setLastIterator (): bool {}

	public function resetIterator (): void {}

	/**
	 * Move to the previous image in the object
	 * @link http://www.php.net/manual/en/imagick.previousimage.php
	 * @return bool true on success.
	 */
	public function previousImage (): bool {}

	/**
	 * Moves to the next image
	 * @link http://www.php.net/manual/en/imagick.nextimage.php
	 * @return bool true on success.
	 */
	public function nextImage (): bool {}

	/**
	 * Checks if the object has a previous image
	 * @link http://www.php.net/manual/en/imagick.haspreviousimage.php
	 * @return bool true if the object has more images when traversing the list in the
	 * reverse direction, returns false if there are none.
	 */
	public function hasPreviousImage (): bool {}

	/**
	 * Checks if the object has more images
	 * @link http://www.php.net/manual/en/imagick.hasnextimage.php
	 * @return bool true if the object has more images when traversing the list in the
	 * forward direction, returns false if there are none.
	 */
	public function hasNextImage (): bool {}

	/**
	 * Set the iterator position
	 * @link http://www.php.net/manual/en/imagick.setimageindex.php
	 * @param int $index The position to set the iterator to
	 * @return bool true on success.
	 */
	public function setImageIndex (int $index): bool {}

	/**
	 * Gets the index of the current active image
	 * @link http://www.php.net/manual/en/imagick.getimageindex.php
	 * @return int an integer containing the index of the image in the stack.
	 */
	public function getImageIndex (): int {}

	/**
	 * Adds a comment to your image
	 * @link http://www.php.net/manual/en/imagick.commentimage.php
	 * @param string $comment The comment to add
	 * @return bool true on success.
	 */
	public function commentImage (string $comment): bool {}

	/**
	 * Extracts a region of the image
	 * @link http://www.php.net/manual/en/imagick.cropimage.php
	 * @param int $width The width of the crop
	 * @param int $height The height of the crop
	 * @param int $x The X coordinate of the cropped region's top left corner
	 * @param int $y The Y coordinate of the cropped region's top left corner
	 * @return bool true on success.
	 */
	public function cropImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * Adds a label to an image
	 * @link http://www.php.net/manual/en/imagick.labelimage.php
	 * @param string $label The label to add
	 * @return bool true on success.
	 */
	public function labelImage (string $label): bool {}

	/**
	 * Gets the width and height as an associative array
	 * @link http://www.php.net/manual/en/imagick.getimagegeometry.php
	 * @return array an array with the width/height of the image.
	 */
	public function getImageGeometry (): array {}

	/**
	 * Renders the ImagickDraw object on the current image
	 * @link http://www.php.net/manual/en/imagick.drawimage.php
	 * @param ImagickDraw $draw The drawing operations to render on the image.
	 * @return bool true on success.
	 */
	public function drawImage (ImagickDraw $draw): bool {}

	/**
	 * Sets the image compression quality
	 * @link http://www.php.net/manual/en/imagick.setimagecompressionquality.php
	 * @param int $quality The image compression quality as an integer
	 * @return bool true on success.
	 */
	public function setImageCompressionQuality (int $quality): bool {}

	/**
	 * Gets the current image's compression quality
	 * @link http://www.php.net/manual/en/imagick.getimagecompressionquality.php
	 * @return int integer describing the images compression quality
	 */
	public function getImageCompressionQuality (): int {}

	/**
	 * Sets the image compression
	 * @link http://www.php.net/manual/en/imagick.setimagecompression.php
	 * @param int $compression One of the COMPRESSION constants
	 * @return bool true on success.
	 */
	public function setImageCompression (int $compression): bool {}

	/**
	 * Gets the current image's compression type
	 * @link http://www.php.net/manual/en/imagick.getimagecompression.php
	 * @return int the compression constant
	 */
	public function getImageCompression (): int {}

	/**
	 * Annotates an image with text
	 * @link http://www.php.net/manual/en/imagick.annotateimage.php
	 * @param ImagickDraw $draw_settings The ImagickDraw object that contains settings for drawing the text
	 * @param float $x Horizontal offset in pixels to the left of text
	 * @param float $y Vertical offset in pixels to the baseline of text
	 * @param float $angle The angle at which to write the text
	 * @param string $text The string to draw
	 * @return bool true on success.
	 */
	public function annotateImage (ImagickDraw $draw_settings, float $x, float $y, float $angle, string $text): bool {}

	/**
	 * Composite one image onto another
	 * @link http://www.php.net/manual/en/imagick.compositeimage.php
	 * @param Imagick $composite_object Imagick object which holds the composite image
	 * @param int $composite 
	 * @param int $x The column offset of the composited image
	 * @param int $y The row offset of the composited image
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this list of channel constants.
	 * @return bool true on success.
	 */
	public function compositeImage (Imagick $composite_object, int $composite, int $x, int $y, int $channel = null): bool {}

	/**
	 * Control the brightness, saturation, and hue
	 * @link http://www.php.net/manual/en/imagick.modulateimage.php
	 * @param float $brightness 
	 * @param float $saturation 
	 * @param float $hue 
	 * @return bool true on success.
	 */
	public function modulateImage (float $brightness, float $saturation, float $hue): bool {}

	/**
	 * Gets the number of unique colors in the image
	 * @link http://www.php.net/manual/en/imagick.getimagecolors.php
	 * @return int an int, the number of unique colors in the image.
	 */
	public function getImageColors (): int {}

	/**
	 * Creates a composite image
	 * @link http://www.php.net/manual/en/imagick.montageimage.php
	 * @param ImagickDraw $draw The font name, size, and color are obtained from this object.
	 * @param string $tile_geometry The number of tiles per row and page (e.g. 6x4+0+0).
	 * @param string $thumbnail_geometry Preferred image size and border size of each thumbnail
	 * (e.g. 120x120+4+3).
	 * @param int $mode Thumbnail framing mode, see Montage Mode constants.
	 * @param string $frame Surround the image with an ornamental border (e.g. 15x15+3+3). The
	 * frame color is that of the thumbnail's matte color.
	 * @return Imagick Creates a composite image and returns it as a new Imagick object.
	 */
	public function montageImage (ImagickDraw $draw, string $tile_geometry, string $thumbnail_geometry, int $mode, string $frame): Imagick {}

	/**
	 * Identifies an image and fetches attributes
	 * @link http://www.php.net/manual/en/imagick.identifyimage.php
	 * @param bool $appendRawOutput [optional] If true then the raw output is appended to the array.
	 * @return array Identifies an image and returns the attributes. Attributes include
	 * the image width, height, size, and others.
	 */
	public function identifyImage (bool $appendRawOutput = null): array {}

	/**
	 * Changes the value of individual pixels based on a threshold
	 * @link http://www.php.net/manual/en/imagick.thresholdimage.php
	 * @param float $threshold 
	 * @param int $channel [optional] 
	 * @return bool true on success.
	 */
	public function thresholdImage (float $threshold, int $channel = null): bool {}

	/**
	 * Selects a threshold for each pixel based on a range of intensity
	 * @link http://www.php.net/manual/en/imagick.adaptivethresholdimage.php
	 * @param int $width Width of the local neighborhood.
	 * @param int $height Height of the local neighborhood.
	 * @param int $offset The mean offset
	 * @return bool true on success.
	 */
	public function adaptiveThresholdImage (int $width, int $height, int $offset): bool {}

	/**
	 * Forces all pixels below the threshold into black
	 * @link http://www.php.net/manual/en/imagick.blackthresholdimage.php
	 * @param mixed $threshold The threshold below which everything turns black
	 * @return bool true on success.
	 */
	public function blackThresholdImage ($threshold): bool {}

	/**
	 * Force all pixels above the threshold into white
	 * @link http://www.php.net/manual/en/imagick.whitethresholdimage.php
	 * @param mixed $threshold 
	 * @return bool true on success.
	 */
	public function whiteThresholdImage ($threshold): bool {}

	/**
	 * Append a set of images
	 * @link http://www.php.net/manual/en/imagick.appendimages.php
	 * @param bool $stack Whether to stack the images vertically.
	 * By default (or if false is specified) images are stacked left-to-right.
	 * If stack is true, images are stacked top-to-bottom.
	 * @return Imagick Imagick instance on success.
	 */
	public function appendImages (bool $stack): Imagick {}

	/**
	 * Simulates a charcoal drawing
	 * @link http://www.php.net/manual/en/imagick.charcoalimage.php
	 * @param float $radius The radius of the Gaussian, in pixels, not counting the center pixel
	 * @param float $sigma The standard deviation of the Gaussian, in pixels
	 * @return bool true on success.
	 */
	public function charcoalImage (float $radius, float $sigma): bool {}

	/**
	 * Enhances the contrast of a color image
	 * @link http://www.php.net/manual/en/imagick.normalizeimage.php
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function normalizeImage (int $channel = null): bool {}

	/**
	 * @param float $radius
	 * @param float $sigma
	 */
	public function oilPaintImageWithSigma (float $radius, float $sigma): bool {}

	/**
	 * Simulates an oil painting
	 * @link http://www.php.net/manual/en/imagick.oilpaintimage.php
	 * @param float $radius The radius of the circular neighborhood.
	 * @return bool true on success.
	 */
	public function oilPaintImage (float $radius): bool {}

	/**
	 * Reduces the image to a limited number of color level
	 * @link http://www.php.net/manual/en/imagick.posterizeimage.php
	 * @param int $levels 
	 * @param bool $dither 
	 * @return bool true on success.
	 */
	public function posterizeImage (int $levels, bool $dither): bool {}

	/**
	 * Creates a simulated 3d button-like effect
	 * @link http://www.php.net/manual/en/imagick.raiseimage.php
	 * @param int $width 
	 * @param int $height 
	 * @param int $x 
	 * @param int $y 
	 * @param bool $raise 
	 * @return bool true on success.
	 */
	public function raiseImage (int $width, int $height, int $x, int $y, bool $raise): bool {}

	/**
	 * Resample image to desired resolution
	 * @link http://www.php.net/manual/en/imagick.resampleimage.php
	 * @param float $x_resolution 
	 * @param float $y_resolution 
	 * @param int $filter 
	 * @param float $blur 
	 * @return bool true on success.
	 */
	public function resampleImage (float $x_resolution, float $y_resolution, int $filter, float $blur): bool {}

	/**
	 * Scales an image
	 * @link http://www.php.net/manual/en/imagick.resizeimage.php
	 * @param int $columns Width of the image
	 * @param int $rows Height of the image
	 * @param int $filter Refer to the list of filter constants.
	 * @param float $blur The blur factor where &gt; 1 is blurry, &lt; 1 is sharp.
	 * @param bool $bestfit [optional] Optional fit parameter.
	 * @param bool $legacy [optional] 
	 * @return bool true on success.
	 */
	public function resizeImage (int $columns, int $rows, int $filter, float $blur, bool $bestfit = null, bool $legacy = null): bool {}

	/**
	 * Offsets an image
	 * @link http://www.php.net/manual/en/imagick.rollimage.php
	 * @param int $x The X offset.
	 * @param int $y The Y offset.
	 * @return bool true on success.
	 */
	public function rollImage (int $x, int $y): bool {}

	/**
	 * Rotates an image
	 * @link http://www.php.net/manual/en/imagick.rotateimage.php
	 * @param mixed $background The background color
	 * @param float $degrees Rotation angle, in degrees. The rotation angle is interpreted as the
	 * number of degrees to rotate the image clockwise.
	 * @return bool true on success.
	 */
	public function rotateImage ($background, float $degrees): bool {}

	/**
	 * Scales an image with pixel sampling
	 * @link http://www.php.net/manual/en/imagick.sampleimage.php
	 * @param int $columns 
	 * @param int $rows 
	 * @return bool true on success.
	 */
	public function sampleImage (int $columns, int $rows): bool {}

	/**
	 * Applies a solarizing effect to the image
	 * @link http://www.php.net/manual/en/imagick.solarizeimage.php
	 * @param int $threshold 
	 * @return bool true on success.
	 */
	public function solarizeImage (int $threshold): bool {}

	/**
	 * Simulates an image shadow
	 * @link http://www.php.net/manual/en/imagick.shadowimage.php
	 * @param float $opacity 
	 * @param float $sigma 
	 * @param int $x 
	 * @param int $y 
	 * @return bool true on success.
	 */
	public function shadowImage (float $opacity, float $sigma, int $x, int $y): bool {}

	/**
	 * Sets the image background color
	 * @link http://www.php.net/manual/en/imagick.setimagebackgroundcolor.php
	 * @param mixed $background 
	 * @return bool true on success.
	 */
	public function setImageBackgroundColor ($background): bool {}

	/**
	 * @param int $channel
	 */
	public function setImageChannelMask (int $channel): int {}

	/**
	 * Sets the image composite operator
	 * @link http://www.php.net/manual/en/imagick.setimagecompose.php
	 * @param int $compose 
	 * @return bool true on success.
	 */
	public function setImageCompose (int $compose): bool {}

	/**
	 * Sets the image delay
	 * @link http://www.php.net/manual/en/imagick.setimagedelay.php
	 * @param int $delay The amount of time expressed in 'ticks' that the image should be
	 * displayed for. For animated GIFs there are 100 ticks per second, so a
	 * value of 20 would be 20/100 of a second aka 1/5th of a second.
	 * @return bool true on success.
	 */
	public function setImageDelay (int $delay): bool {}

	/**
	 * Sets the image depth
	 * @link http://www.php.net/manual/en/imagick.setimagedepth.php
	 * @param int $depth 
	 * @return bool true on success.
	 */
	public function setImageDepth (int $depth): bool {}

	/**
	 * Sets the image gamma
	 * @link http://www.php.net/manual/en/imagick.setimagegamma.php
	 * @param float $gamma 
	 * @return bool true on success.
	 */
	public function setImageGamma (float $gamma): bool {}

	/**
	 * Sets the image iterations
	 * @link http://www.php.net/manual/en/imagick.setimageiterations.php
	 * @param int $iterations The number of iterations the image should loop over. Set to '0' to loop
	 * continuously.
	 * @return bool true on success.
	 */
	public function setImageIterations (int $iterations): bool {}

	/**
	 * Sets the image matte color
	 * @link http://www.php.net/manual/en/imagick.setimagemattecolor.php
	 * @param mixed $matte 
	 * @return bool true on success.
	 */
	public function setImageMatteColor ($matte): bool {}

	/**
	 * Sets the page geometry of the image
	 * @link http://www.php.net/manual/en/imagick.setimagepage.php
	 * @param int $width 
	 * @param int $height 
	 * @param int $x 
	 * @param int $y 
	 * @return bool true on success.
	 */
	public function setImagePage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * @param string $filename
	 */
	public function setImageProgressMonitor (string $filename): bool {}

	/**
	 * Set a callback to be called during processing
	 * @link http://www.php.net/manual/en/imagick.setprogressmonitor.php
	 * @param callable $callback <p>
	 * The progress function to call. It should return true if image processing should continue, or false if it should be cancelled. The offset parameter indicates the progress and the span parameter indicates the total amount of work needed to be done.
	 * </p>
	 * bool
	 * callback
	 * mixedoffset
	 * mixed
	 * span
	 * <p>
	 * The values passed to the callback function are not consistent. In particular the span parameter can increase during image processing. Because of this calculating the percentage complete of an image operation is not trivial.
	 * </p>
	 * @return bool true on success.
	 */
	public function setProgressMonitor (callable $callback): bool {}

	/**
	 * Sets the image resolution
	 * @link http://www.php.net/manual/en/imagick.setimageresolution.php
	 * @param float $x_resolution 
	 * @param float $y_resolution 
	 * @return bool true on success.
	 */
	public function setImageResolution (float $x_resolution, float $y_resolution): bool {}

	/**
	 * Sets the image scene
	 * @link http://www.php.net/manual/en/imagick.setimagescene.php
	 * @param int $scene 
	 * @return bool true on success.
	 */
	public function setImageScene (int $scene): bool {}

	/**
	 * Sets the image ticks-per-second
	 * @link http://www.php.net/manual/en/imagick.setimagetickspersecond.php
	 * @param int $ticks_per_second The duration for which an image should be displayed expressed in ticks
	 * per second.
	 * @return bool true on success.
	 */
	public function setImageTicksPerSecond (int $ticks_per_second): bool {}

	/**
	 * Sets the image type
	 * @link http://www.php.net/manual/en/imagick.setimagetype.php
	 * @param int $image_type 
	 * @return bool true on success.
	 */
	public function setImageType (int $image_type): bool {}

	/**
	 * Sets the image units of resolution
	 * @link http://www.php.net/manual/en/imagick.setimageunits.php
	 * @param int $units 
	 * @return bool true on success.
	 */
	public function setImageUnits (int $units): bool {}

	/**
	 * Sharpens an image
	 * @link http://www.php.net/manual/en/imagick.sharpenimage.php
	 * @param float $radius 
	 * @param float $sigma 
	 * @param int $channel [optional] 
	 * @return bool true on success.
	 */
	public function sharpenImage (float $radius, float $sigma, int $channel = null): bool {}

	/**
	 * Shaves pixels from the image edges
	 * @link http://www.php.net/manual/en/imagick.shaveimage.php
	 * @param int $columns 
	 * @param int $rows 
	 * @return bool true on success.
	 */
	public function shaveImage (int $columns, int $rows): bool {}

	/**
	 * Creating a parallelogram
	 * @link http://www.php.net/manual/en/imagick.shearimage.php
	 * @param mixed $background The background color
	 * @param float $x_shear The number of degrees to shear on the x axis
	 * @param float $y_shear The number of degrees to shear on the y axis
	 * @return bool true on success.
	 */
	public function shearImage ($background, float $x_shear, float $y_shear): bool {}

	/**
	 * Splices a solid color into the image
	 * @link http://www.php.net/manual/en/imagick.spliceimage.php
	 * @param int $width 
	 * @param int $height 
	 * @param int $x 
	 * @param int $y 
	 * @return bool true on success.
	 */
	public function spliceImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * Fetch basic attributes about the image
	 * @link http://www.php.net/manual/en/imagick.pingimage.php
	 * @param string $filename The filename to read the information from.
	 * @return bool true on success.
	 */
	public function pingImage (string $filename): bool {}

	/**
	 * Reads image from open filehandle
	 * @link http://www.php.net/manual/en/imagick.readimagefile.php
	 * @param resource $filehandle 
	 * @param string $fileName [optional] 
	 * @return bool true on success.
	 */
	public function readImageFile ($filehandle, string $fileName = null): bool {}

	/**
	 * Displays an image
	 * @link http://www.php.net/manual/en/imagick.displayimage.php
	 * @param string $servername The X server name
	 * @return bool true on success.
	 */
	public function displayImage (string $servername): bool {}

	/**
	 * Displays an image or image sequence
	 * @link http://www.php.net/manual/en/imagick.displayimages.php
	 * @param string $servername The X server name
	 * @return bool true on success.
	 */
	public function displayImages (string $servername): bool {}

	/**
	 * Randomly displaces each pixel in a block
	 * @link http://www.php.net/manual/en/imagick.spreadimage.php
	 * @param float $radius 
	 * @return bool true on success.
	 */
	public function spreadImage (float $radius): bool {}

	/**
	 * @param float $radius
	 * @param int $interpolate_method
	 */
	public function spreadImageWithMethod (float $radius, int $interpolate_method): bool {}

	/**
	 * Swirls the pixels about the center of the image
	 * @link http://www.php.net/manual/en/imagick.swirlimage.php
	 * @param float $degrees 
	 * @return bool true on success.
	 */
	public function swirlImage (float $degrees): bool {}

	/**
	 * @param float $degrees
	 * @param int $interpolate_method
	 */
	public function swirlImageWithMethod (float $degrees, int $interpolate_method): bool {}

	/**
	 * Strips an image of all profiles and comments
	 * @link http://www.php.net/manual/en/imagick.stripimage.php
	 * @return bool true on success.
	 */
	public function stripImage (): bool {}

	/**
	 * Returns formats supported by Imagick
	 * @link http://www.php.net/manual/en/imagick.queryformats.php
	 * @param string $pattern [optional] 
	 * @return array an array containing the formats supported by Imagick.
	 */
	public static function queryFormats (string $pattern = null): array {}

	/**
	 * Returns the configured fonts
	 * @link http://www.php.net/manual/en/imagick.queryfonts.php
	 * @param string $pattern [optional] The query pattern
	 * @return array an array containing the configured fonts.
	 */
	public static function queryFonts (string $pattern = null): array {}

	/**
	 * Returns an array representing the font metrics
	 * @link http://www.php.net/manual/en/imagick.queryfontmetrics.php
	 * @param ImagickDraw $properties ImagickDraw object containing font properties
	 * @param string $text The text
	 * @param bool $multiline [optional] Multiline parameter. If left empty it is autodetected
	 * @return array a multi-dimensional array representing the font metrics.
	 */
	public function queryFontMetrics (ImagickDraw $properties, string $text, bool $multiline = null): array {}

	/**
	 * Hides a digital watermark within the image
	 * @link http://www.php.net/manual/en/imagick.steganoimage.php
	 * @param Imagick $watermark_wand 
	 * @param int $offset 
	 * @return Imagick true on success.
	 */
	public function steganoImage (Imagick $watermark_wand, int $offset): Imagick {}

	/**
	 * Adds random noise to the image
	 * @link http://www.php.net/manual/en/imagick.addnoiseimage.php
	 * @param int $noise_type The type of the noise. Refer to this list of
	 * noise constants.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function addNoiseImage (int $noise_type, int $channel = null): bool {}

	/**
	 * @param int $noise
	 * @param float $attenuate
	 * @param int $channel [optional]
	 */
	public function addNoiseImageWithAttenuate (int $noise, float $attenuate, int $channel = 134217727): bool {}

	/**
	 * Simulates motion blur
	 * @link http://www.php.net/manual/en/imagick.motionblurimage.php
	 * @param float $radius The radius of the Gaussian, in pixels, not counting the center pixel.
	 * @param float $sigma The standard deviation of the Gaussian, in pixels.
	 * @param float $angle Apply the effect along this angle.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * The channel argument affects only if Imagick is compiled against ImageMagick version
	 * 6.4.4 or greater.
	 * @return bool true on success.
	 */
	public function motionBlurImage (float $radius, float $sigma, float $angle, int $channel = null): bool {}

	/**
	 * Method morphs a set of images
	 * @link http://www.php.net/manual/en/imagick.morphimages.php
	 * @param int $number_frames The number of in-between images to generate.
	 * @return Imagick This method returns a new Imagick object on success.
	 * imagick.imagickexception.throw
	 */
	public function morphImages (int $number_frames): Imagick {}

	/**
	 * Scales an image proportionally to half its size
	 * @link http://www.php.net/manual/en/imagick.minifyimage.php
	 * @return bool true on success.
	 */
	public function minifyImage (): bool {}

	/**
	 * Transforms an image
	 * @link http://www.php.net/manual/en/imagick.affinetransformimage.php
	 * @param ImagickDraw $matrix The affine matrix
	 * @return bool true on success.
	 */
	public function affineTransformImage (ImagickDraw $matrix): bool {}

	/**
	 * Average a set of images
	 * @link http://www.php.net/manual/en/imagick.averageimages.php
	 * @return Imagick a new Imagick object on success.
	 */
	public function averageImages (): Imagick {}

	/**
	 * Surrounds the image with a border
	 * @link http://www.php.net/manual/en/imagick.borderimage.php
	 * @param mixed $bordercolor ImagickPixel object or a string containing the border color
	 * @param int $width Border width
	 * @param int $height Border height
	 * @return bool true on success.
	 */
	public function borderImage ($bordercolor, int $width, int $height): bool {}

	/**
	 * @param ImagickPixel|string $border_color
	 * @param int $width
	 * @param int $height
	 * @param int $composite
	 */
	public function borderImageWithComposite (ImagickPixel|string $border_color, int $width, int $height, int $composite): bool {}

	/**
	 * @param int $original_width
	 * @param int $original_height
	 * @param int $desired_width
	 * @param int $desired_height
	 * @param bool $legacy [optional]
	 */
	public static function calculateCrop (int $original_width, int $original_height, int $desired_width, int $desired_height, bool $legacy = ''): array {}

	/**
	 * Removes a region of an image and trims
	 * @link http://www.php.net/manual/en/imagick.chopimage.php
	 * @param int $width Width of the chopped area
	 * @param int $height Height of the chopped area
	 * @param int $x X origo of the chopped area
	 * @param int $y Y origo of the chopped area
	 * @return bool true on success.
	 */
	public function chopImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * Clips along the first path from the 8BIM profile
	 * @link http://www.php.net/manual/en/imagick.clipimage.php
	 * @return bool true on success.
	 */
	public function clipImage (): bool {}

	/**
	 * Clips along the named paths from the 8BIM profile
	 * @link http://www.php.net/manual/en/imagick.clippathimage.php
	 * @param string $pathname The name of the path
	 * @param bool $inside If true later operations take effect inside clipping path.
	 * Otherwise later operations take effect outside clipping path.
	 * @return bool true on success.
	 */
	public function clipPathImage (string $pathname, bool $inside): bool {}

	/**
	 * Clips along the named paths from the 8BIM profile, if present
	 * @link http://www.php.net/manual/en/imagick.clipimagepath.php
	 * @param string $pathname 
	 * @param string $inside 
	 * @return void 
	 */
	public function clipImagePath (string $pathname, string $inside): void {}

	/**
	 * Composites a set of images
	 * @link http://www.php.net/manual/en/imagick.coalesceimages.php
	 * @return Imagick a new Imagick object on success.
	 */
	public function coalesceImages (): Imagick {}

	/**
	 * Blends the fill color with the image
	 * @link http://www.php.net/manual/en/imagick.colorizeimage.php
	 * @param mixed $colorize ImagickPixel object or a string containing the colorize color
	 * @param mixed $opacity ImagickPixel object or an float containing the opacity value. 
	 * 1.0 is fully opaque and 0.0 is fully transparent.
	 * @param bool $legacy [optional] 
	 * @return bool true on success.
	 */
	public function colorizeImage ($colorize, $opacity, bool $legacy = null): bool {}

	/**
	 * Returns the difference in one or more images
	 * @link http://www.php.net/manual/en/imagick.compareimagechannels.php
	 * @param Imagick $image Imagick object containing the image to compare.
	 * @param int $channelType Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @param int $metricType One of the metric type constants.
	 * @return array Array consisting of new_wand and
	 * distortion.
	 */
	public function compareImageChannels (Imagick $image, int $channelType, int $metricType): array {}

	/**
	 * Compares an image to a reconstructed image
	 * @link http://www.php.net/manual/en/imagick.compareimages.php
	 * @param Imagick $compare An image to compare to.
	 * @param int $metric Provide a valid metric type constant. Refer to this
	 * list of metric constants.
	 * @return array an array containing a reconstructed image and the difference between images.
	 */
	public function compareImages (Imagick $compare, int $metric): array {}

	/**
	 * Change the contrast of the image
	 * @link http://www.php.net/manual/en/imagick.contrastimage.php
	 * @param bool $sharpen The sharpen value
	 * @return bool true on success.
	 */
	public function contrastImage (bool $sharpen): bool {}

	/**
	 * Combines one or more images into a single image
	 * @link http://www.php.net/manual/en/imagick.combineimages.php
	 * @param int $channelType Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return Imagick true on success.
	 */
	public function combineImages (int $channelType): Imagick {}

	/**
	 * Applies a custom convolution kernel to the image
	 * @link http://www.php.net/manual/en/imagick.convolveimage.php
	 * @param array $kernel The convolution kernel
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function convolveImage (array $kernel, int $channel = null): bool {}

	/**
	 * Displaces an image's colormap
	 * @link http://www.php.net/manual/en/imagick.cyclecolormapimage.php
	 * @param int $displace The amount to displace the colormap.
	 * @return bool true on success.
	 */
	public function cycleColormapImage (int $displace): bool {}

	/**
	 * Returns certain pixel differences between images
	 * @link http://www.php.net/manual/en/imagick.deconstructimages.php
	 * @return Imagick a new Imagick object on success.
	 */
	public function deconstructImages (): Imagick {}

	/**
	 * Reduces the speckle noise in an image
	 * @link http://www.php.net/manual/en/imagick.despeckleimage.php
	 * @return bool true on success.
	 */
	public function despeckleImage (): bool {}

	/**
	 * Enhance edges within the image
	 * @link http://www.php.net/manual/en/imagick.edgeimage.php
	 * @param float $radius The radius of the operation.
	 * @return bool true on success.
	 */
	public function edgeImage (float $radius): bool {}

	/**
	 * Returns a grayscale image with a three-dimensional effect
	 * @link http://www.php.net/manual/en/imagick.embossimage.php
	 * @param float $radius The radius of the effect
	 * @param float $sigma The sigma of the effect
	 * @return bool true on success.
	 */
	public function embossImage (float $radius, float $sigma): bool {}

	/**
	 * Improves the quality of a noisy image
	 * @link http://www.php.net/manual/en/imagick.enhanceimage.php
	 * @return bool true on success.
	 */
	public function enhanceImage (): bool {}

	/**
	 * Equalizes the image histogram
	 * @link http://www.php.net/manual/en/imagick.equalizeimage.php
	 * @return bool true on success.
	 */
	public function equalizeImage (): bool {}

	/**
	 * Applies an expression to an image
	 * @link http://www.php.net/manual/en/imagick.evaluateimage.php
	 * @param int $op The evaluation operator
	 * @param float $constant The value of the operator
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function evaluateImage (int $op, float $constant, int $channel = null): bool {}

	/**
	 * @param int $evaluate
	 */
	public function evaluateImages (int $evaluate): bool {}

	/**
	 * Merges a sequence of images
	 * @link http://www.php.net/manual/en/imagick.flattenimages.php
	 * @return Imagick true on success.
	 */
	public function flattenImages (): Imagick {}

	/**
	 * Creates a vertical mirror image
	 * @link http://www.php.net/manual/en/imagick.flipimage.php
	 * @return bool true on success.
	 */
	public function flipImage (): bool {}

	/**
	 * Creates a horizontal mirror image
	 * @link http://www.php.net/manual/en/imagick.flopimage.php
	 * @return bool true on success.
	 */
	public function flopImage (): bool {}

	/**
	 * Implements the discrete Fourier transform (DFT)
	 * @link http://www.php.net/manual/en/imagick.forwardfouriertransformimage.php
	 * @param bool $magnitude If true, return as magnitude / phase pair otherwise a real / imaginary image pair.
	 * @return bool true on success.
	 */
	public function forwardFourierTransformImage (bool $magnitude): bool {}

	/**
	 * Adds a simulated three-dimensional border
	 * @link http://www.php.net/manual/en/imagick.frameimage.php
	 * @param mixed $matte_color ImagickPixel object or a string representing the matte color
	 * @param int $width The width of the border
	 * @param int $height The height of the border
	 * @param int $inner_bevel The inner bevel width
	 * @param int $outer_bevel The outer bevel width
	 * @return bool true on success.
	 */
	public function frameImage ($matte_color, int $width, int $height, int $inner_bevel, int $outer_bevel): bool {}

	/**
	 * @param ImagickPixel|string $matte_color
	 * @param int $width
	 * @param int $height
	 * @param int $inner_bevel
	 * @param int $outer_bevel
	 * @param int $composite
	 */
	public function frameImageWithComposite (ImagickPixel|string $matte_color, int $width, int $height, int $inner_bevel, int $outer_bevel, int $composite): bool {}

	/**
	 * Evaluate expression for each pixel in the image
	 * @link http://www.php.net/manual/en/imagick.fximage.php
	 * @param string $expression The expression.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return Imagick true on success.
	 */
	public function fxImage (string $expression, int $channel = null): Imagick {}

	/**
	 * Gamma-corrects an image
	 * @link http://www.php.net/manual/en/imagick.gammaimage.php
	 * @param float $gamma The amount of gamma-correction.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function gammaImage (float $gamma, int $channel = null): bool {}

	/**
	 * Blurs an image
	 * @link http://www.php.net/manual/en/imagick.gaussianblurimage.php
	 * @param float $radius The radius of the Gaussian, in pixels, not counting the center pixel.
	 * @param float $sigma The standard deviation of the Gaussian, in pixels.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function gaussianBlurImage (float $radius, float $sigma, int $channel = null): bool {}

	/**
	 * Returns the image background color
	 * @link http://www.php.net/manual/en/imagick.getimagebackgroundcolor.php
	 * @return ImagickPixel an ImagickPixel set to the background color of the image.
	 */
	public function getImageBackgroundColor (): ImagickPixel {}

	/**
	 * Returns the chromaticy blue primary point
	 * @link http://www.php.net/manual/en/imagick.getimageblueprimary.php
	 * @return array Array consisting of "x" and "y" coordinates of point.
	 */
	public function getImageBluePrimary (): array {}

	/**
	 * Returns the image border color
	 * @link http://www.php.net/manual/en/imagick.getimagebordercolor.php
	 * @return ImagickPixel true on success.
	 */
	public function getImageBorderColor (): ImagickPixel {}

	/**
	 * Gets the depth for a particular image channel
	 * @link http://www.php.net/manual/en/imagick.getimagechanneldepth.php
	 * @param int $channel Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return int true on success.
	 */
	public function getImageChannelDepth (int $channel): int {}

	/**
	 * Compares image channels of an image to a reconstructed image
	 * @link http://www.php.net/manual/en/imagick.getimagechanneldistortion.php
	 * @param Imagick $reference Imagick object to compare to.
	 * @param int $channel Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @param int $metric One of the metric type constants.
	 * @return float true on success.
	 */
	public function getImageChannelDistortion (Imagick $reference, int $channel, int $metric): float {}

	/**
	 * Gets the mean and standard deviation
	 * @link http://www.php.net/manual/en/imagick.getimagechannelmean.php
	 * @param int $channel Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return array an array with "mean" and "standardDeviation"
	 * members.
	 */
	public function getImageChannelMean (int $channel): array {}

	/**
	 * Returns statistics for each channel in the image
	 * @link http://www.php.net/manual/en/imagick.getimagechannelstatistics.php
	 * @return array true on success.
	 */
	public function getImageChannelStatistics (): array {}

	/**
	 * Returns the color of the specified colormap index
	 * @link http://www.php.net/manual/en/imagick.getimagecolormapcolor.php
	 * @param int $index The offset into the image colormap.
	 * @return ImagickPixel true on success.
	 */
	public function getImageColormapColor (int $index): ImagickPixel {}

	/**
	 * Gets the image colorspace
	 * @link http://www.php.net/manual/en/imagick.getimagecolorspace.php
	 * @return int an integer which can be compared against COLORSPACE constants.
	 */
	public function getImageColorspace (): int {}

	/**
	 * Returns the composite operator associated with the image
	 * @link http://www.php.net/manual/en/imagick.getimagecompose.php
	 * @return int true on success.
	 */
	public function getImageCompose (): int {}

	/**
	 * Gets the image delay
	 * @link http://www.php.net/manual/en/imagick.getimagedelay.php
	 * @return int the image delay.
	 */
	public function getImageDelay (): int {}

	/**
	 * Gets the image depth
	 * @link http://www.php.net/manual/en/imagick.getimagedepth.php
	 * @return int The image depth.
	 */
	public function getImageDepth (): int {}

	/**
	 * Compares an image to a reconstructed image
	 * @link http://www.php.net/manual/en/imagick.getimagedistortion.php
	 * @param MagickWand $reference Imagick object to compare to.
	 * @param int $metric One of the metric type constants.
	 * @return float the distortion metric used on the image (or the best guess
	 * thereof).
	 */
	public function getImageDistortion ($reference, int $metric): float {}

	/**
	 * Gets the image disposal method
	 * @link http://www.php.net/manual/en/imagick.getimagedispose.php
	 * @return int the dispose method on success.
	 */
	public function getImageDispose (): int {}

	/**
	 * Gets the image gamma
	 * @link http://www.php.net/manual/en/imagick.getimagegamma.php
	 * @return float the image gamma on success.
	 */
	public function getImageGamma (): float {}

	/**
	 * Returns the chromaticy green primary point
	 * @link http://www.php.net/manual/en/imagick.getimagegreenprimary.php
	 * @return array an array with the keys "x" and "y" on success, throws an
	 * ImagickException on failure.
	 */
	public function getImageGreenPrimary (): array {}

	/**
	 * Returns the image height
	 * @link http://www.php.net/manual/en/imagick.getimageheight.php
	 * @return int the image height in pixels.
	 */
	public function getImageHeight (): int {}

	/**
	 * Gets the image histogram
	 * @link http://www.php.net/manual/en/imagick.getimagehistogram.php
	 * @return array the image histogram as an array of ImagickPixel objects.
	 */
	public function getImageHistogram (): array {}

	/**
	 * Gets the image interlace scheme
	 * @link http://www.php.net/manual/en/imagick.getimageinterlacescheme.php
	 * @return int the interlace scheme as an integer on success.
	 * imagick.imagickexception.throw
	 */
	public function getImageInterlaceScheme (): int {}

	/**
	 * Gets the image iterations
	 * @link http://www.php.net/manual/en/imagick.getimageiterations.php
	 * @return int the image iterations as an integer.
	 */
	public function getImageIterations (): int {}

	/**
	 * Returns the page geometry
	 * @link http://www.php.net/manual/en/imagick.getimagepage.php
	 * @return array the page geometry associated with the image in an array with the
	 * keys "width", "height", "x", and "y".
	 */
	public function getImagePage (): array {}

	/**
	 * Returns the color of the specified pixel
	 * @link http://www.php.net/manual/en/imagick.getimagepixelcolor.php
	 * @param int $x The x-coordinate of the pixel
	 * @param int $y The y-coordinate of the pixel
	 * @return ImagickPixel an ImagickPixel instance for the color at the coordinates given.
	 */
	public function getImagePixelColor (int $x, int $y): ImagickPixel {}

	/**
	 * @param int $x
	 * @param int $y
	 * @param ImagickPixel|string $color
	 */
	public function setImagePixelColor (int $x, int $y, ImagickPixel|string $color): ImagickPixel {}

	/**
	 * Returns the named image profile
	 * @link http://www.php.net/manual/en/imagick.getimageprofile.php
	 * @param string $name The name of the profile to return.
	 * @return string a string containing the image profile.
	 */
	public function getImageProfile (string $name): string {}

	/**
	 * Returns the chromaticity red primary point
	 * @link http://www.php.net/manual/en/imagick.getimageredprimary.php
	 * @return array the chromaticity red primary point as an array with the keys "x"
	 * and "y".
	 * imagick.imagickexception.throw
	 */
	public function getImageRedPrimary (): array {}

	/**
	 * Gets the image rendering intent
	 * @link http://www.php.net/manual/en/imagick.getimagerenderingintent.php
	 * @return int the image rendering intent.
	 */
	public function getImageRenderingIntent (): int {}

	/**
	 * Gets the image X and Y resolution
	 * @link http://www.php.net/manual/en/imagick.getimageresolution.php
	 * @return array the resolution as an array.
	 */
	public function getImageResolution (): array {}

	/**
	 * Gets the image scene
	 * @link http://www.php.net/manual/en/imagick.getimagescene.php
	 * @return int the image scene.
	 */
	public function getImageScene (): int {}

	/**
	 * Generates an SHA-256 message digest
	 * @link http://www.php.net/manual/en/imagick.getimagesignature.php
	 * @return string a string containing the SHA-256 hash of the file.
	 */
	public function getImageSignature (): string {}

	/**
	 * Gets the image ticks-per-second
	 * @link http://www.php.net/manual/en/imagick.getimagetickspersecond.php
	 * @return int the image ticks-per-second.
	 */
	public function getImageTicksPerSecond (): int {}

	/**
	 * Gets the potential image type
	 * @link http://www.php.net/manual/en/imagick.getimagetype.php
	 * @return int the potential image type.
	 * <p>
	 * <br>
	 * imagick::IMGTYPE_UNDEFINED
	 * <br>
	 * imagick::IMGTYPE_BILEVEL
	 * <br>
	 * imagick::IMGTYPE_GRAYSCALE
	 * <br>
	 * imagick::IMGTYPE_GRAYSCALEMATTE
	 * <br>
	 * imagick::IMGTYPE_PALETTE
	 * <br>
	 * imagick::IMGTYPE_PALETTEMATTE
	 * <br>
	 * imagick::IMGTYPE_TRUECOLOR
	 * <br>
	 * imagick::IMGTYPE_TRUECOLORMATTE
	 * <br>
	 * imagick::IMGTYPE_COLORSEPARATION
	 * <br>
	 * imagick::IMGTYPE_COLORSEPARATIONMATTE
	 * <br>
	 * imagick::IMGTYPE_OPTIMIZE
	 * </p>
	 */
	public function getImageType (): int {}

	/**
	 * Gets the image units of resolution
	 * @link http://www.php.net/manual/en/imagick.getimageunits.php
	 * @return int the image units of resolution.
	 */
	public function getImageUnits (): int {}

	/**
	 * Returns the virtual pixel method
	 * @link http://www.php.net/manual/en/imagick.getimagevirtualpixelmethod.php
	 * @return int the virtual pixel method on success.
	 */
	public function getImageVirtualPixelMethod (): int {}

	/**
	 * Returns the chromaticity white point
	 * @link http://www.php.net/manual/en/imagick.getimagewhitepoint.php
	 * @return array the chromaticity white point as an associative array with the keys
	 * "x" and "y".
	 */
	public function getImageWhitePoint (): array {}

	/**
	 * Returns the image width
	 * @link http://www.php.net/manual/en/imagick.getimagewidth.php
	 * @return int the image width.
	 */
	public function getImageWidth (): int {}

	/**
	 * Returns the number of images in the object
	 * @link http://www.php.net/manual/en/imagick.getnumberimages.php
	 * @return int the number of images associated with Imagick object.
	 */
	public function getNumberImages (): int {}

	/**
	 * Gets the image total ink density
	 * @link http://www.php.net/manual/en/imagick.getimagetotalinkdensity.php
	 * @return float the image total ink density of the image.
	 * imagick.imagickexception.throw
	 */
	public function getImageTotalInkDensity (): float {}

	/**
	 * Extracts a region of the image
	 * @link http://www.php.net/manual/en/imagick.getimageregion.php
	 * @param int $width The width of the extracted region.
	 * @param int $height The height of the extracted region.
	 * @param int $x X-coordinate of the top-left corner of the extracted region.
	 * @param int $y Y-coordinate of the top-left corner of the extracted region.
	 * @return Imagick Extracts a region of the image and returns it as a new wand.
	 */
	public function getImageRegion (int $width, int $height, int $x, int $y): Imagick {}

	/**
	 * Creates a new image as a copy
	 * @link http://www.php.net/manual/en/imagick.implodeimage.php
	 * @param float $radius The radius of the implode
	 * @return bool true on success.
	 */
	public function implodeImage (float $radius): bool {}

	/**
	 * @param float $radius
	 * @param int $pixel_interpolate_method
	 */
	public function implodeImageWithMethod (float $radius, int $pixel_interpolate_method): bool {}

	/**
	 * Implements the inverse discrete Fourier transform (DFT)
	 * @link http://www.php.net/manual/en/imagick.inversefouriertransformimage.php
	 * @param Imagick $complement The second image to combine with this one to form either the magnitude / phase or real / imaginary image pair.
	 * @param bool $magnitude If true, combine as magnitude / phase pair otherwise a real / imaginary image pair.
	 * @return bool true on success.
	 */
	public function inverseFourierTransformImage (Imagick $complement, bool $magnitude): bool {}

	/**
	 * Adjusts the levels of an image
	 * @link http://www.php.net/manual/en/imagick.levelimage.php
	 * @param float $blackPoint The image black point
	 * @param float $gamma The gamma value
	 * @param float $whitePoint The image white point
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function levelImage (float $blackPoint, float $gamma, float $whitePoint, int $channel = null): bool {}

	/**
	 * Scales an image proportionally 2x
	 * @link http://www.php.net/manual/en/imagick.magnifyimage.php
	 * @return bool true on success.
	 */
	public function magnifyImage (): bool {}

	/**
	 * Negates the colors in the reference image
	 * @link http://www.php.net/manual/en/imagick.negateimage.php
	 * @param bool $gray Whether to only negate grayscale pixels within the image.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To
	 * apply to more than one channel, combine channeltype constants using
	 * bitwise operators. Refer to this
	 * list of channel constants.
	 * @return bool true on success.
	 */
	public function negateImage (bool $gray, int $channel = null): bool {}

	/**
	 * Quickly pin-point appropriate parameters for image processing
	 * @link http://www.php.net/manual/en/imagick.previewimages.php
	 * @param int $preview Preview type. See Preview type constants
	 * @return bool true on success.
	 */
	public function previewImages (int $preview): bool {}

	/**
	 * Adds or removes a profile from an image
	 * @link http://www.php.net/manual/en/imagick.profileimage.php
	 * @param string $name 
	 * @param string $profile 
	 * @return bool true on success.
	 */
	public function profileImage (string $name, string $profile): bool {}

	/**
	 * Analyzes the colors within a reference image
	 * @link http://www.php.net/manual/en/imagick.quantizeimage.php
	 * @param int $numberColors 
	 * @param int $colorspace 
	 * @param int $treedepth 
	 * @param bool $dither 
	 * @param bool $measureError 
	 * @return bool true on success.
	 */
	public function quantizeImage (int $numberColors, int $colorspace, int $treedepth, bool $dither, bool $measureError): bool {}

	/**
	 * Analyzes the colors within a sequence of images
	 * @link http://www.php.net/manual/en/imagick.quantizeimages.php
	 * @param int $numberColors 
	 * @param int $colorspace 
	 * @param int $treedepth 
	 * @param bool $dither 
	 * @param bool $measureError 
	 * @return bool true on success.
	 */
	public function quantizeImages (int $numberColors, int $colorspace, int $treedepth, bool $dither, bool $measureError): bool {}

	/**
	 * Removes the named image profile and returns it
	 * @link http://www.php.net/manual/en/imagick.removeimageprofile.php
	 * @param string $name 
	 * @return string a string containing the profile of the image.
	 */
	public function removeImageProfile (string $name): string {}

	/**
	 * Separates a channel from the image
	 * @link http://www.php.net/manual/en/imagick.separateimagechannel.php
	 * @param int $channel Which 'channel' to return. For colorspaces other than RGB, you can still use the CHANNEL_RED, CHANNEL_GREEN, CHANNEL_BLUE constants to indicate the 1st, 2nd and 3rd channels.
	 * @return bool true on success.
	 */
	public function separateImageChannel (int $channel): bool {}

	/**
	 * Sepia tones an image
	 * @link http://www.php.net/manual/en/imagick.sepiatoneimage.php
	 * @param float $threshold 
	 * @return bool true on success.
	 */
	public function sepiaToneImage (float $threshold): bool {}

	/**
	 * Sets the image chromaticity blue primary point
	 * @link http://www.php.net/manual/en/imagick.setimageblueprimary.php
	 * @param float $x 
	 * @param float $y 
	 * @return bool true on success.
	 */
	public function setImageBluePrimary (float $x, float $y): bool {}

	/**
	 * Sets the image border color
	 * @link http://www.php.net/manual/en/imagick.setimagebordercolor.php
	 * @param mixed $border The border color
	 * @return bool true on success.
	 */
	public function setImageBorderColor ($border): bool {}

	/**
	 * Sets the depth of a particular image channel
	 * @link http://www.php.net/manual/en/imagick.setimagechanneldepth.php
	 * @param int $channel 
	 * @param int $depth 
	 * @return bool true on success.
	 */
	public function setImageChannelDepth (int $channel, int $depth): bool {}

	/**
	 * Sets the color of the specified colormap index
	 * @link http://www.php.net/manual/en/imagick.setimagecolormapcolor.php
	 * @param int $index 
	 * @param ImagickPixel $color 
	 * @return bool true on success.
	 */
	public function setImageColormapColor (int $index, ImagickPixel $color): bool {}

	/**
	 * Sets the image colorspace
	 * @link http://www.php.net/manual/en/imagick.setimagecolorspace.php
	 * @param int $colorspace One of the COLORSPACE constants
	 * @return bool true on success.
	 */
	public function setImageColorspace (int $colorspace): bool {}

	/**
	 * Sets the image disposal method
	 * @link http://www.php.net/manual/en/imagick.setimagedispose.php
	 * @param int $dispose 
	 * @return bool true on success.
	 */
	public function setImageDispose (int $dispose): bool {}

	/**
	 * Sets the image size
	 * @link http://www.php.net/manual/en/imagick.setimageextent.php
	 * @param int $columns 
	 * @param int $rows 
	 * @return bool true on success.
	 */
	public function setImageExtent (int $columns, int $rows): bool {}

	/**
	 * Sets the image chromaticity green primary point
	 * @link http://www.php.net/manual/en/imagick.setimagegreenprimary.php
	 * @param float $x 
	 * @param float $y 
	 * @return bool true on success.
	 */
	public function setImageGreenPrimary (float $x, float $y): bool {}

	/**
	 * Sets the image compression
	 * @link http://www.php.net/manual/en/imagick.setimageinterlacescheme.php
	 * @param int $interlace_scheme 
	 * @return bool true on success.
	 */
	public function setImageInterlaceScheme (int $interlace_scheme): bool {}

	/**
	 * Adds a named profile to the Imagick object
	 * @link http://www.php.net/manual/en/imagick.setimageprofile.php
	 * @param string $name 
	 * @param string $profile 
	 * @return bool true on success.
	 */
	public function setImageProfile (string $name, string $profile): bool {}

	/**
	 * Sets the image chromaticity red primary point
	 * @link http://www.php.net/manual/en/imagick.setimageredprimary.php
	 * @param float $x 
	 * @param float $y 
	 * @return bool true on success.
	 */
	public function setImageRedPrimary (float $x, float $y): bool {}

	/**
	 * Sets the image rendering intent
	 * @link http://www.php.net/manual/en/imagick.setimagerenderingintent.php
	 * @param int $rendering_intent 
	 * @return bool true on success.
	 */
	public function setImageRenderingIntent (int $rendering_intent): bool {}

	/**
	 * Sets the image virtual pixel method
	 * @link http://www.php.net/manual/en/imagick.setimagevirtualpixelmethod.php
	 * @param int $method 
	 * @return bool true on success.
	 */
	public function setImageVirtualPixelMethod (int $method): bool {}

	/**
	 * Sets the image chromaticity white point
	 * @link http://www.php.net/manual/en/imagick.setimagewhitepoint.php
	 * @param float $x 
	 * @param float $y 
	 * @return bool true on success.
	 */
	public function setImageWhitePoint (float $x, float $y): bool {}

	/**
	 * Adjusts the contrast of an image
	 * @link http://www.php.net/manual/en/imagick.sigmoidalcontrastimage.php
	 * @param bool $sharpen If true increase the contrast, if false decrease the contrast.
	 * @param float $alpha The amount of contrast to apply. 1 is very little, 5 is a significant amount, 20 is extreme.
	 * @param float $beta Where the midpoint of the gradient will be. This value should be in the range 0 to 1 - mutliplied by the quantum value for ImageMagick.
	 * @param int $channel [optional] Which color channels the contrast will be applied to.
	 * @return bool true on success.
	 */
	public function sigmoidalContrastImage (bool $sharpen, float $alpha, float $beta, int $channel = null): bool {}

	/**
	 * Composites two images
	 * @link http://www.php.net/manual/en/imagick.stereoimage.php
	 * @param Imagick $offset_wand 
	 * @return bool true on success.
	 */
	public function stereoImage (Imagick $offset_wand): bool {}

	/**
	 * Repeatedly tiles the texture image
	 * @link http://www.php.net/manual/en/imagick.textureimage.php
	 * @param Imagick $texture_wand Imagick object to use as texture image
	 * @return Imagick a new Imagick object that has the repeated texture applied.
	 */
	public function textureImage (Imagick $texture_wand): Imagick {}

	/**
	 * Applies a color vector to each pixel in the image
	 * @link http://www.php.net/manual/en/imagick.tintimage.php
	 * @param mixed $tint 
	 * @param mixed $opacity 
	 * @param bool $legacy [optional] 
	 * @return bool true on success.
	 */
	public function tintImage ($tint, $opacity, bool $legacy = null): bool {}

	/**
	 * Sharpens an image
	 * @link http://www.php.net/manual/en/imagick.unsharpmaskimage.php
	 * @param float $radius 
	 * @param float $sigma 
	 * @param float $amount 
	 * @param float $threshold 
	 * @param int $channel [optional] 
	 * @return bool true on success.
	 */
	public function unsharpMaskImage (float $radius, float $sigma, float $amount, float $threshold, int $channel = null): bool {}

	/**
	 * Returns a new Imagick object
	 * @link http://www.php.net/manual/en/imagick.getimage.php
	 * @return Imagick a new Imagick object with the current image sequence.
	 */
	public function getImage (): Imagick {}

	/**
	 * Adds new image to Imagick object image list
	 * @link http://www.php.net/manual/en/imagick.addimage.php
	 * @param Imagick $source The source Imagick object
	 * @return bool true on success.
	 */
	public function addImage (Imagick $source): bool {}

	/**
	 * Replaces image in the object
	 * @link http://www.php.net/manual/en/imagick.setimage.php
	 * @param Imagick $replace The replace Imagick object
	 * @return bool true on success.
	 */
	public function setImage (Imagick $replace): bool {}

	/**
	 * Creates a new image
	 * @link http://www.php.net/manual/en/imagick.newimage.php
	 * @param int $cols Columns in the new image
	 * @param int $rows Rows in the new image
	 * @param mixed $background The background color used for this image
	 * @param string $format [optional] Image format. This parameter was added in Imagick version 2.0.1.
	 * @return bool true on success.
	 */
	public function newImage (int $cols, int $rows, $background, string $format = null): bool {}

	/**
	 * Creates a new image
	 * @link http://www.php.net/manual/en/imagick.newpseudoimage.php
	 * @param int $columns columns in the new image
	 * @param int $rows rows in the new image
	 * @param string $pseudoString string containing pseudo image definition.
	 * @return bool true on success.
	 */
	public function newPseudoImage (int $columns, int $rows, string $pseudoString): bool {}

	/**
	 * Gets the object compression type
	 * @link http://www.php.net/manual/en/imagick.getcompression.php
	 * @return int the compression constant
	 */
	public function getCompression (): int {}

	/**
	 * Gets the object compression quality
	 * @link http://www.php.net/manual/en/imagick.getcompressionquality.php
	 * @return int integer describing the compression quality
	 */
	public function getCompressionQuality (): int {}

	/**
	 * Returns the ImageMagick API copyright as a string
	 * @link http://www.php.net/manual/en/imagick.getcopyright.php
	 * @return string a string containing the copyright notice of Imagemagick and
	 * Magickwand C API.
	 */
	public static function getCopyright (): string {}

	/**
	 * @param string $pattern [optional]
	 */
	public static function getConfigureOptions (string $pattern = '*'): array {}

	public static function getFeatures (): string {}

	/**
	 * The filename associated with an image sequence
	 * @link http://www.php.net/manual/en/imagick.getfilename.php
	 * @return string a string on success.
	 */
	public function getFilename (): string {}

	/**
	 * Returns the format of the Imagick object
	 * @link http://www.php.net/manual/en/imagick.getformat.php
	 * @return string the format of the image.
	 */
	public function getFormat (): string {}

	/**
	 * Returns the ImageMagick home URL
	 * @link http://www.php.net/manual/en/imagick.gethomeurl.php
	 * @return string a link to the imagemagick homepage.
	 */
	public static function getHomeURL (): string {}

	/**
	 * Gets the object interlace scheme
	 * @link http://www.php.net/manual/en/imagick.getinterlacescheme.php
	 * @return int Gets the wand interlace
	 * scheme.
	 */
	public function getInterlaceScheme (): int {}

	/**
	 * Returns a value associated with the specified key
	 * @link http://www.php.net/manual/en/imagick.getoption.php
	 * @param string $key The name of the option
	 * @return string a value associated with a wand and the specified key.
	 */
	public function getOption (string $key): string {}

	/**
	 * Returns the ImageMagick package name
	 * @link http://www.php.net/manual/en/imagick.getpackagename.php
	 * @return string the ImageMagick package name as a string.
	 */
	public static function getPackageName (): string {}

	/**
	 * Returns the page geometry
	 * @link http://www.php.net/manual/en/imagick.getpage.php
	 * @return array the page geometry associated with the Imagick object in
	 * an associative array with the keys "width", "height", "x", and "y",
	 * throwing ImagickException on error.
	 */
	public function getPage (): array {}

	/**
	 * Returns the ImageMagick quantum range
	 * @link http://www.php.net/manual/en/imagick.getquantum.php
	 * @return int 
	 */
	public static function getQuantum (): int {}

	public static function getHdriEnabled (): bool {}

	/**
	 * Gets the quantum depth
	 * @link http://www.php.net/manual/en/imagick.getquantumdepth.php
	 * @return array an array with "quantumDepthLong" and "quantumDepthString"
	 * members.
	 */
	public static function getQuantumDepth (): array {}

	/**
	 * Returns the Imagick quantum range
	 * @link http://www.php.net/manual/en/imagick.getquantumrange.php
	 * @return array an associative array containing the quantum range as an
	 * int ("quantumRangeLong") and as a 
	 * string ("quantumRangeString").
	 */
	public static function getQuantumRange (): array {}

	/**
	 * Returns the ImageMagick release date
	 * @link http://www.php.net/manual/en/imagick.getreleasedate.php
	 * @return string the ImageMagick release date as a string.
	 */
	public static function getReleaseDate (): string {}

	/**
	 * Returns the specified resource's memory usage
	 * @link http://www.php.net/manual/en/imagick.getresource.php
	 * @param int $type Refer to the list of resourcetype constants.
	 * @return int the specified resource's memory usage in megabytes.
	 */
	public static function getResource (int $type): int {}

	/**
	 * Returns the specified resource limit
	 * @link http://www.php.net/manual/en/imagick.getresourcelimit.php
	 * @param int $type One of the resourcetype constants.
	 * The unit depends on the type of the resource being limited.
	 * @return int the specified resource limit in megabytes.
	 */
	public static function getResourceLimit (int $type): int {}

	/**
	 * Gets the horizontal and vertical sampling factor
	 * @link http://www.php.net/manual/en/imagick.getsamplingfactors.php
	 * @return array an associative array with the horizontal and vertical sampling
	 * factors of the image.
	 */
	public function getSamplingFactors (): array {}

	/**
	 * Returns the size associated with the Imagick object
	 * @link http://www.php.net/manual/en/imagick.getsize.php
	 * @return array the size associated with the Imagick object as an array with the
	 * keys "columns" and "rows".
	 */
	public function getSize (): array {}

	/**
	 * Returns the ImageMagick API version
	 * @link http://www.php.net/manual/en/imagick.getversion.php
	 * @return array the ImageMagick API version as a string and as a number.
	 */
	public static function getVersion (): array {}

	/**
	 * Sets the object's default background color
	 * @link http://www.php.net/manual/en/imagick.setbackgroundcolor.php
	 * @param mixed $background 
	 * @return bool true on success.
	 */
	public function setBackgroundColor ($background): bool {}

	/**
	 * Sets the object's default compression type
	 * @link http://www.php.net/manual/en/imagick.setcompression.php
	 * @param int $compression The compression type. See the Imagick::COMPRESSION_&#42; constants.
	 * @return bool true on success.
	 */
	public function setCompression (int $compression): bool {}

	/**
	 * Sets the object's default compression quality
	 * @link http://www.php.net/manual/en/imagick.setcompressionquality.php
	 * @param int $quality An integer between 1 and 100, 1 = high compression, 100 low compression.
	 * @return bool true on success.
	 */
	public function setCompressionQuality (int $quality): bool {}

	/**
	 * Sets the filename before you read or write the image
	 * @link http://www.php.net/manual/en/imagick.setfilename.php
	 * @param string $filename 
	 * @return bool true on success.
	 */
	public function setFilename (string $filename): bool {}

	/**
	 * Sets the format of the Imagick object
	 * @link http://www.php.net/manual/en/imagick.setformat.php
	 * @param string $format 
	 * @return bool true on success.
	 */
	public function setFormat (string $format): bool {}

	/**
	 * Sets the image compression
	 * @link http://www.php.net/manual/en/imagick.setinterlacescheme.php
	 * @param int $interlace_scheme 
	 * @return bool true on success.
	 */
	public function setInterlaceScheme (int $interlace_scheme): bool {}

	/**
	 * Set an option
	 * @link http://www.php.net/manual/en/imagick.setoption.php
	 * @param string $key 
	 * @param string $value 
	 * @return bool true on success.
	 */
	public function setOption (string $key, string $value): bool {}

	/**
	 * Sets the page geometry of the Imagick object
	 * @link http://www.php.net/manual/en/imagick.setpage.php
	 * @param int $width 
	 * @param int $height 
	 * @param int $x 
	 * @param int $y 
	 * @return bool true on success.
	 */
	public function setPage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * Sets the limit for a particular resource
	 * @link http://www.php.net/manual/en/imagick.setresourcelimit.php
	 * @param int $type Refer to the list of resourcetype constants.
	 * @param int $limit One of the resourcetype constants.
	 * The unit depends on the type of the resource being limited.
	 * @return bool true on success.
	 */
	public static function setResourceLimit (int $type, int $limit): bool {}

	/**
	 * Sets the image resolution
	 * @link http://www.php.net/manual/en/imagick.setresolution.php
	 * @param float $x_resolution The horizontal resolution.
	 * @param float $y_resolution The vertical resolution.
	 * @return bool true on success.
	 */
	public function setResolution (float $x_resolution, float $y_resolution): bool {}

	/**
	 * Sets the image sampling factors
	 * @link http://www.php.net/manual/en/imagick.setsamplingfactors.php
	 * @param array $factors 
	 * @return bool true on success.
	 */
	public function setSamplingFactors (array $factors): bool {}

	/**
	 * Sets the size of the Imagick object
	 * @link http://www.php.net/manual/en/imagick.setsize.php
	 * @param int $columns 
	 * @param int $rows 
	 * @return bool true on success.
	 */
	public function setSize (int $columns, int $rows): bool {}

	/**
	 * Sets the image type attribute
	 * @link http://www.php.net/manual/en/imagick.settype.php
	 * @param int $image_type 
	 * @return bool true on success.
	 */
	public function setType (int $image_type): bool {}

	public function key (): int {}

	public function next () {}

	public function rewind () {}

	/**
	 * Checks if the current item is valid
	 * @link http://www.php.net/manual/en/imagick.valid.php
	 * @return bool true on success.
	 */
	public function valid (): bool {}

	/**
	 * Returns a reference to the current Imagick object
	 * @link http://www.php.net/manual/en/imagick.current.php
	 * @return Imagick self on success.
	 */
	public function current (): Imagick {}

	/**
	 * Change the brightness and/or contrast of an image
	 * @link http://www.php.net/manual/en/imagick.brightnesscontrastimage.php
	 * @param float $brightness 
	 * @param float $contrast 
	 * @param int $channel [optional] 
	 * @return bool true on success.
	 */
	public function brightnessContrastImage (float $brightness, float $contrast, int $channel = null): bool {}

	/**
	 * Apply color transformation to an image
	 * @link http://www.php.net/manual/en/imagick.colormatriximage.php
	 * @param array $color_matrix 
	 * @return bool true on success.
	 */
	public function colorMatrixImage (array $color_matrix): bool {}

	/**
	 * Selectively blur an image within a contrast threshold
	 * @link http://www.php.net/manual/en/imagick.selectiveblurimage.php
	 * @param float $radius 
	 * @param float $sigma 
	 * @param float $threshold 
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function selectiveBlurImage (float $radius, float $sigma, float $threshold, int $channel = null): bool {}

	/**
	 * Rotational blurs an image
	 * @link http://www.php.net/manual/en/imagick.rotationalblurimage.php
	 * @param float $angle The angle to apply the blur over.
	 * @param int $channel [optional] Provide any channel constant that is valid for your channel mode. To apply to more than one channel, combine channel constants using bitwise operators. imagick.default.channel.info
	 * @return bool true on success.
	 */
	public function rotationalBlurImage (float $angle, int $channel = null): bool {}

	/**
	 * Modifies image using a statistics function
	 * @link http://www.php.net/manual/en/imagick.statisticimage.php
	 * @param int $type 
	 * @param int $width 
	 * @param int $height 
	 * @param int $channel [optional] 
	 * @return bool true on success.
	 */
	public function statisticImage (int $type, int $width, int $height, int $channel = null): bool {}

	/**
	 * Searches for a subimage in the current image and returns a similarity image
	 * @link http://www.php.net/manual/en/imagick.subimagematch.php
	 * @param Imagick $Imagick 
	 * @param array $offset [optional] 
	 * @param float $similarity [optional] A new image that displays the amount of similarity at each pixel.
	 * @return Imagick 
	 */
	public function subimageMatch (Imagick $Imagick, array &$offset = null, float &$similarity = null): Imagick {}

	/**
	 * @param Imagick $image
	 * @param array|null[] $offset [optional]
	 * @param float|null $similarity [optional]
	 * @param float $threshold [optional]
	 * @param int $metric [optional]
	 */
	public function similarityImage (Imagick $image, array &$offset = null, float|null &$similarity = null, float $threshold = 0, int $metric = 0): Imagick {}

	/**
	 * Sets the ImageMagick registry entry named key to value
	 * @link http://www.php.net/manual/en/imagick.setregistry.php
	 * @param string $key 
	 * @param string $value 
	 * @return bool true on success.
	 */
	public static function setRegistry (string $key, string $value): bool {}

	/**
	 * Get a StringRegistry entry
	 * @link http://www.php.net/manual/en/imagick.getregistry.php
	 * @param string $key The entry to get.
	 * @return string 
	 */
	public static function getRegistry (string $key): string {}

	/**
	 * List all the registry settings
	 * @link http://www.php.net/manual/en/imagick.listregistry.php
	 * @return array An array containing the key/values from the registry.
	 */
	public static function listRegistry (): array {}

	/**
	 * Applies a user supplied kernel to the image according to the given morphology method.
	 * @link http://www.php.net/manual/en/imagick.morphology.php
	 * @param int $morphologyMethod Which morphology method to use one of the \Imagick::MORPHOLOGY_&#42; constants.
	 * @param int $iterations The number of iteration to apply the morphology function. A value of -1 means loop until no change found. How this is applied may depend on the morphology method. Typically this is a value of 1.
	 * @param ImagickKernel $ImagickKernel 
	 * @param int $channel [optional] 
	 * @return bool true on success.
	 */
	public function morphology (int $morphologyMethod, int $iterations, ImagickKernel $ImagickKernel, int $channel = null): bool {}

	/**
	 * @param bool $antialias
	 */
	public function setAntialias (bool $antialias): void {}

	public function getAntialias (): bool {}

	/**
	 * @param string $color_correction_collection
	 */
	public function colorDecisionListImage (string $color_correction_collection): bool {}

	public function optimizeImageTransparency (): void {}

	/**
	 * @param int|null $channel [optional]
	 */
	public function autoGammaImage (int|null $channel = null): void {}

	public function autoOrient (): void {}

	public function autoOrientate (): void {}

	/**
	 * @param Imagick $image
	 * @param int $composite_constant
	 * @param int $gravity
	 */
	public function compositeImageGravity (Imagick $image, int $composite_constant, int $gravity): bool {}

	/**
	 * @param float $radius
	 * @param float $strength
	 */
	public function localContrastImage (float $radius, float $strength): void {}

	public function identifyImageType (): int {}

	/**
	 * @param int $pixelmask
	 */
	public function getImageMask (int $pixelmask): ?Imagick {}

	/**
	 * @param Imagick $clip_mask
	 * @param int $pixelmask
	 */
	public function setImageMask (Imagick $clip_mask, int $pixelmask): void {}

	/**
	 * @param float $radius
	 * @param float $sigma
	 * @param float $lower_percent
	 * @param float $upper_percent
	 */
	public function cannyEdgeImage (float $radius, float $sigma, float $lower_percent, float $upper_percent): bool {}

	/**
	 * @param int $seed
	 */
	public static function setSeed (int $seed): void {}

	/**
	 * @param float $threshold
	 * @param float $softness
	 */
	public function waveletDenoiseImage (float $threshold, float $softness): bool {}

	/**
	 * @param int $width
	 * @param int $height
	 * @param float $color_distance
	 */
	public function meanShiftImage (int $width, int $height, float $color_distance): bool {}

	/**
	 * @param int $number_colors
	 * @param int $max_iterations
	 * @param float $tolerance
	 */
	public function kmeansImage (int $number_colors, int $max_iterations, float $tolerance): bool {}

	/**
	 * @param float $low_black
	 * @param float $low_white
	 * @param float $high_white
	 * @param float $high_black
	 */
	public function rangeThresholdImage (float $low_black, float $low_white, float $high_white, float $high_black): bool {}

	/**
	 * @param int $auto_threshold_method
	 */
	public function autoThresholdImage (int $auto_threshold_method): bool {}

	/**
	 * @param float $radius
	 * @param float $sigma
	 * @param float $intensity_sigma
	 * @param float $spatial_sigma
	 */
	public function bilateralBlurImage (float $radius, float $sigma, float $intensity_sigma, float $spatial_sigma): bool {}

	/**
	 * @param int $width
	 * @param int $height
	 * @param int $number_bins
	 * @param float $clip_limit
	 */
	public function claheImage (int $width, int $height, int $number_bins, float $clip_limit): bool {}

	/**
	 * @param string $expression
	 */
	public function channelFxImage (string $expression): Imagick {}

	/**
	 * @param ImagickPixel|string $start_color
	 * @param ImagickPixel|string $stop_color
	 */
	public function colorThresholdImage (ImagickPixel|string $start_color, ImagickPixel|string $stop_color): bool {}

	/**
	 * @param int $complex_operator
	 */
	public function complexImages (int $complex_operator): Imagick {}

	/**
	 * @param int $columns
	 * @param int $rows
	 * @param int $interpolate
	 */
	public function interpolativeResizeImage (int $columns, int $rows, int $interpolate): bool {}

	/**
	 * @param ImagickPixel|string $black_color
	 * @param ImagickPixel|string $white_color
	 * @param bool $invert
	 */
	public function levelImageColors (ImagickPixel|string $black_color, ImagickPixel|string $white_color, bool $invert): bool {}

	/**
	 * @param float $black_point
	 * @param float $gamma
	 * @param float $white_point
	 */
	public function levelizeImage (float $black_point, float $gamma, float $white_point): bool {}

	/**
	 * @param string $dither_format
	 */
	public function orderedDitherImage (string $dither_format): bool {}

	public function whiteBalanceImage (): bool {}

	/**
	 * @param string $option
	 */
	public function deleteOption (string $option): bool {}

	public function getBackgroundColor (): ImagickPixel {}

	/**
	 * @param string $pattern [optional]
	 */
	public function getImageArtifacts (string $pattern = '*'): array {}

	public function getImageKurtosis (): array {}

	public function getImageMean (): array {}

	public function getImageRange (): array {}

	public function getInterpolateMethod (): int {}

	/**
	 * @param string $pattern [optional]
	 */
	public function getOptions (string $pattern = '*'): array {}

	public function getOrientation (): int {}

	public function getResolution (): array {}

	public function getType (): int {}

	/**
	 * @param array[] $terms
	 */
	public function polynomialImage (array $terms): bool {}

	/**
	 * @param int $depth
	 */
	public function setDepth (int $depth): bool {}

	/**
	 * @param string $geometry
	 */
	public function setExtract (string $geometry): bool {}

	/**
	 * @param int $method
	 */
	public function setInterpolateMethod (int $method): bool {}

	/**
	 * @param int $orientation
	 */
	public function setOrientation (int $orientation): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.imagickdraw.php
 */
class ImagickDraw  {

	/**
	 * Resets the vector graphics
	 * @link http://www.php.net/manual/en/imagickdraw.resetvectorgraphics.php
	 * @return bool true on success.
	 */
	public function resetVectorGraphics (): bool {}

	/**
	 * Gets the text kerning
	 * @link http://www.php.net/manual/en/imagickdraw.gettextkerning.php
	 * @return float 
	 */
	public function getTextKerning (): float {}

	/**
	 * Sets the text kerning
	 * @link http://www.php.net/manual/en/imagickdraw.settextkerning.php
	 * @param float $kerning 
	 * @return bool true on success.
	 */
	public function setTextKerning (float $kerning): bool {}

	/**
	 * Gets the text interword spacing
	 * @link http://www.php.net/manual/en/imagickdraw.gettextinterwordspacing.php
	 * @return float 
	 */
	public function getTextInterwordSpacing (): float {}

	/**
	 * Sets the text interword spacing
	 * @link http://www.php.net/manual/en/imagickdraw.settextinterwordspacing.php
	 * @param float $spacing 
	 * @return bool true on success.
	 */
	public function setTextInterwordSpacing (float $spacing): bool {}

	/**
	 * Gets the text interword spacing
	 * @link http://www.php.net/manual/en/imagickdraw.gettextinterlinespacing.php
	 * @return float 
	 */
	public function getTextInterlineSpacing (): float {}

	/**
	 * Sets the text interline spacing
	 * @link http://www.php.net/manual/en/imagickdraw.settextinterlinespacing.php
	 * @param float $spacing 
	 * @return bool true on success.
	 */
	public function setTextInterlineSpacing (float $spacing): bool {}

	/**
	 * The ImagickDraw constructor
	 * @link http://www.php.net/manual/en/imagickdraw.construct.php
	 */
	public function __construct () {}

	/**
	 * Sets the fill color to be used for drawing filled objects
	 * @link http://www.php.net/manual/en/imagickdraw.setfillcolor.php
	 * @param ImagickPixel $fill_pixel ImagickPixel to use to set the color
	 * @return bool 
	 */
	public function setFillColor (ImagickPixel $fill_pixel): bool {}

	/**
	 * Sets the opacity to use when drawing using the fill color or fill texture
	 * @link http://www.php.net/manual/en/imagickdraw.setfillalpha.php
	 * @param float $opacity fill alpha
	 * @return bool 
	 */
	public function setFillAlpha (float $opacity): bool {}

	/**
	 * Sets the resolution
	 * @link http://www.php.net/manual/en/imagickdraw.setresolution.php
	 * @param float $x_resolution 
	 * @param float $y_resolution 
	 * @return bool true on success.
	 */
	public function setResolution (float $x_resolution, float $y_resolution): bool {}

	/**
	 * Sets the color used for stroking object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokecolor.php
	 * @param ImagickPixel $stroke_pixel the stroke color
	 * @return bool 
	 */
	public function setStrokeColor (ImagickPixel $stroke_pixel): bool {}

	/**
	 * Specifies the opacity of stroked object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokealpha.php
	 * @param float $opacity opacity
	 * @return bool 
	 */
	public function setStrokeAlpha (float $opacity): bool {}

	/**
	 * Sets the width of the stroke used to draw object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokewidth.php
	 * @param float $stroke_width stroke width
	 * @return bool 
	 */
	public function setStrokeWidth (float $stroke_width): bool {}

	/**
	 * Clears the ImagickDraw
	 * @link http://www.php.net/manual/en/imagickdraw.clear.php
	 * @return bool an ImagickDraw object.
	 */
	public function clear (): bool {}

	/**
	 * Draws a circle
	 * @link http://www.php.net/manual/en/imagickdraw.circle.php
	 * @param float $ox origin x coordinate
	 * @param float $oy origin y coordinate
	 * @param float $px perimeter x coordinate
	 * @param float $py perimeter y coordinate
	 * @return bool 
	 */
	public function circle (float $ox, float $oy, float $px, float $py): bool {}

	/**
	 * Draws text on the image
	 * @link http://www.php.net/manual/en/imagickdraw.annotation.php
	 * @param float $x The x coordinate where text is drawn
	 * @param float $y The y coordinate where text is drawn
	 * @param string $text The text to draw on the image
	 * @return bool 
	 */
	public function annotation (float $x, float $y, string $text): bool {}

	/**
	 * Controls whether text is antialiased
	 * @link http://www.php.net/manual/en/imagickdraw.settextantialias.php
	 * @param bool $antiAlias 
	 * @return bool 
	 */
	public function setTextAntialias (bool $antiAlias): bool {}

	/**
	 * Specifies the text code set
	 * @link http://www.php.net/manual/en/imagickdraw.settextencoding.php
	 * @param string $encoding the encoding name
	 * @return bool 
	 */
	public function setTextEncoding (string $encoding): bool {}

	/**
	 * Sets the fully-specified font to use when annotating with text
	 * @link http://www.php.net/manual/en/imagickdraw.setfont.php
	 * @param string $font_name 
	 * @return bool true on success.
	 */
	public function setFont (string $font_name): bool {}

	/**
	 * Sets the font family to use when annotating with text
	 * @link http://www.php.net/manual/en/imagickdraw.setfontfamily.php
	 * @param string $font_family the font family
	 * @return bool true on success.
	 */
	public function setFontFamily (string $font_family): bool {}

	/**
	 * Sets the font pointsize to use when annotating with text
	 * @link http://www.php.net/manual/en/imagickdraw.setfontsize.php
	 * @param float $pointsize the point size
	 * @return bool 
	 */
	public function setFontSize (float $pointsize): bool {}

	/**
	 * Sets the font style to use when annotating with text
	 * @link http://www.php.net/manual/en/imagickdraw.setfontstyle.php
	 * @param int $style One of the STYLE constant
	 * (imagick::STYLE_&#42;).
	 * @return bool 
	 */
	public function setFontStyle (int $style): bool {}

	/**
	 * Sets the font weight
	 * @link http://www.php.net/manual/en/imagickdraw.setfontweight.php
	 * @param int $font_weight 
	 * @return bool 
	 */
	public function setFontWeight (int $font_weight): bool {}

	/**
	 * Returns the font
	 * @link http://www.php.net/manual/en/imagickdraw.getfont.php
	 * @return string a string on success and false if no font is set.
	 */
	public function getFont (): string {}

	/**
	 * Returns the font family
	 * @link http://www.php.net/manual/en/imagickdraw.getfontfamily.php
	 * @return string the font family currently selected or false if font family is not set.
	 */
	public function getFontFamily (): string {}

	/**
	 * Returns the font pointsize
	 * @link http://www.php.net/manual/en/imagickdraw.getfontsize.php
	 * @return float the font size associated with the current ImagickDraw object.
	 */
	public function getFontSize (): float {}

	/**
	 * Returns the font style
	 * @link http://www.php.net/manual/en/imagickdraw.getfontstyle.php
	 * @return int a STYLE constant
	 * (imagick::STYLE_&#42;) associated with the ImagickDraw object 
	 * or 0 if no style is set.
	 */
	public function getFontStyle (): int {}

	/**
	 * Returns the font weight
	 * @link http://www.php.net/manual/en/imagickdraw.getfontweight.php
	 * @return int an integer on success and 0 if no weight is set.
	 */
	public function getFontWeight (): int {}

	/**
	 * Frees all associated resources
	 * @link http://www.php.net/manual/en/imagickdraw.destroy.php
	 * @return bool 
	 */
	public function destroy (): bool {}

	/**
	 * Draws a rectangle
	 * @link http://www.php.net/manual/en/imagickdraw.rectangle.php
	 * @param float $x1 x coordinate of the top left corner
	 * @param float $y1 y coordinate of the top left corner
	 * @param float $x2 x coordinate of the bottom right corner
	 * @param float $y2 y coordinate of the bottom right corner
	 * @return bool 
	 */
	public function rectangle (float $x1, float $y1, float $x2, float $y2): bool {}

	/**
	 * Draws a rounded rectangle
	 * @link http://www.php.net/manual/en/imagickdraw.roundrectangle.php
	 * @param float $x1 x coordinate of the top left corner
	 * @param float $y1 y coordinate of the top left corner
	 * @param float $x2 x coordinate of the bottom right
	 * @param float $y2 y coordinate of the bottom right
	 * @param float $rx x rounding
	 * @param float $ry y rounding
	 * @return bool 
	 */
	public function roundRectangle (float $x1, float $y1, float $x2, float $y2, float $rx, float $ry): bool {}

	/**
	 * Draws an ellipse on the image
	 * @link http://www.php.net/manual/en/imagickdraw.ellipse.php
	 * @param float $ox 
	 * @param float $oy 
	 * @param float $rx 
	 * @param float $ry 
	 * @param float $start 
	 * @param float $end 
	 * @return bool 
	 */
	public function ellipse (float $ox, float $oy, float $rx, float $ry, float $start, float $end): bool {}

	/**
	 * Skews the current coordinate system in the horizontal direction
	 * @link http://www.php.net/manual/en/imagickdraw.skewx.php
	 * @param float $degrees degrees to skew
	 * @return bool 
	 */
	public function skewX (float $degrees): bool {}

	/**
	 * Skews the current coordinate system in the vertical direction
	 * @link http://www.php.net/manual/en/imagickdraw.skewy.php
	 * @param float $degrees degrees to skew
	 * @return bool 
	 */
	public function skewY (float $degrees): bool {}

	/**
	 * Applies a translation to the current coordinate system
	 * @link http://www.php.net/manual/en/imagickdraw.translate.php
	 * @param float $x horizontal translation
	 * @param float $y vertical translation
	 * @return bool 
	 */
	public function translate (float $x, float $y): bool {}

	/**
	 * Draws a line
	 * @link http://www.php.net/manual/en/imagickdraw.line.php
	 * @param float $sx starting x coordinate
	 * @param float $sy starting y coordinate
	 * @param float $ex ending x coordinate
	 * @param float $ey ending y coordinate
	 * @return bool 
	 */
	public function line (float $sx, float $sy, float $ex, float $ey): bool {}

	/**
	 * Draws an arc
	 * @link http://www.php.net/manual/en/imagickdraw.arc.php
	 * @param float $sx Starting x ordinate of bounding rectangle
	 * @param float $sy starting y ordinate of bounding rectangle
	 * @param float $ex ending x ordinate of bounding rectangle
	 * @param float $ey ending y ordinate of bounding rectangle
	 * @param float $sd starting degrees of rotation
	 * @param float $ed ending degrees of rotation
	 * @return bool 
	 */
	public function arc (float $sx, float $sy, float $ex, float $ey, float $sd, float $ed): bool {}

	/**
	 * @param float $x
	 * @param float $y
	 * @param int $paint
	 */
	public function alpha (float $x, float $y, int $paint): bool {}

	/**
	 * Draws a polygon
	 * @link http://www.php.net/manual/en/imagickdraw.polygon.php
	 * @param array $coordinates multidimensional array like array( array( 'x' => 3, 'y' => 4 ), array( 'x' => 2, 'y' => 6 ) );
	 * @return bool true on success.
	 */
	public function polygon (array $coordinates): bool {}

	/**
	 * Draws a point
	 * @link http://www.php.net/manual/en/imagickdraw.point.php
	 * @param float $x point's x coordinate
	 * @param float $y point's y coordinate
	 * @return bool 
	 */
	public function point (float $x, float $y): bool {}

	/**
	 * Returns the text decoration
	 * @link http://www.php.net/manual/en/imagickdraw.gettextdecoration.php
	 * @return int a DECORATION constant
	 * (imagick::DECORATION_&#42;), and 0 if no decoration is set.
	 */
	public function getTextDecoration (): int {}

	/**
	 * Returns the code set used for text annotations
	 * @link http://www.php.net/manual/en/imagickdraw.gettextencoding.php
	 * @return string a string specifying the code set
	 * or false if text encoding is not set.
	 */
	public function getTextEncoding (): string {}

	/**
	 * Gets the font stretch to use when annotating with text
	 * @link http://www.php.net/manual/en/imagickdraw.getfontstretch.php
	 * @return int 
	 */
	public function getFontStretch (): int {}

	/**
	 * Sets the font stretch to use when annotating with text
	 * @link http://www.php.net/manual/en/imagickdraw.setfontstretch.php
	 * @param int $fontStretch One of the STRETCH constant
	 * (imagick::STRETCH_&#42;).
	 * @return bool 
	 */
	public function setFontStretch (int $fontStretch): bool {}

	/**
	 * Controls whether stroked outlines are antialiased
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokeantialias.php
	 * @param bool $stroke_antialias the antialias setting
	 * @return bool 
	 */
	public function setStrokeAntialias (bool $stroke_antialias): bool {}

	/**
	 * Specifies a text alignment
	 * @link http://www.php.net/manual/en/imagickdraw.settextalignment.php
	 * @param int $alignment One of the ALIGN constant
	 * (imagick::ALIGN_&#42;).
	 * @return bool 
	 */
	public function setTextAlignment (int $alignment): bool {}

	/**
	 * Specifies a decoration
	 * @link http://www.php.net/manual/en/imagickdraw.settextdecoration.php
	 * @param int $decoration One of the DECORATION constant
	 * (imagick::DECORATION_&#42;).
	 * @return bool 
	 */
	public function setTextDecoration (int $decoration): bool {}

	/**
	 * Specifies the color of a background rectangle
	 * @link http://www.php.net/manual/en/imagickdraw.settextundercolor.php
	 * @param ImagickPixel $under_color the under color
	 * @return bool 
	 */
	public function setTextUnderColor (ImagickPixel $under_color): bool {}

	/**
	 * Sets the overall canvas size
	 * @link http://www.php.net/manual/en/imagickdraw.setviewbox.php
	 * @param int $x1 left x coordinate
	 * @param int $y1 left y coordinate
	 * @param int $x2 right x coordinate
	 * @param int $y2 right y coordinate
	 * @return bool 
	 */
	public function setViewbox (int $x1, int $y1, int $x2, int $y2): bool {}

	/**
	 * Makes an exact copy of the specified ImagickDraw object
	 * @link http://www.php.net/manual/en/imagickdraw.clone.php
	 * @return ImagickDraw returns an exact copy of the specified ImagickDraw object.
	 */
	public function clone (): ImagickDraw {}

	/**
	 * Adjusts the current affine transformation matrix
	 * @link http://www.php.net/manual/en/imagickdraw.affine.php
	 * @param array $affine Affine matrix parameters
	 * @return bool 
	 */
	public function affine (array $affine): bool {}

	/**
	 * Draws a bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.bezier.php
	 * @param array $coordinates Multidimensional array like array( array( 'x' => 1, 'y' => 2 ), 
	 * array( 'x' => 3, 'y' => 4 ) )
	 * @return bool 
	 */
	public function bezier (array $coordinates): bool {}

	/**
	 * Composites an image onto the current image
	 * @link http://www.php.net/manual/en/imagickdraw.composite.php
	 * @param int $compose composition operator.
	 * One of the Composite Operator constant
	 * (imagick::COMPOSITE_&#42;).
	 * @param float $x x coordinate of the top left corner.
	 * @param float $y y coordinate of the top left corner.
	 * @param float $width width of the composition image.
	 * @param float $height height of the composition image.
	 * @param Imagick $compositeWand the Imagick object where composition image is taken from.
	 * @return bool true on success.
	 */
	public function composite (int $compose, float $x, float $y, float $width, float $height, Imagick $compositeWand): bool {}

	/**
	 * Draws color on image
	 * @link http://www.php.net/manual/en/imagickdraw.color.php
	 * @param float $x x coordinate of the paint
	 * @param float $y y coordinate of the paint
	 * @param int $paintMethod One of the PAINT constant
	 * (imagick::PAINT_&#42;).
	 * @return bool 
	 */
	public function color (float $x, float $y, int $paintMethod): bool {}

	/**
	 * Adds a comment
	 * @link http://www.php.net/manual/en/imagickdraw.comment.php
	 * @param string $comment The comment string to add to vector output stream
	 * @return bool 
	 */
	public function comment (string $comment): bool {}

	/**
	 * Obtains the current clipping path ID
	 * @link http://www.php.net/manual/en/imagickdraw.getclippath.php
	 * @return string a string containing the clip path ID or false if no clip path exists.
	 */
	public function getClipPath (): string {}

	/**
	 * Returns the current polygon fill rule
	 * @link http://www.php.net/manual/en/imagickdraw.getcliprule.php
	 * @return int a FILLRULE constant
	 * (imagick::FILLRULE_&#42;).
	 */
	public function getClipRule (): int {}

	/**
	 * Returns the interpretation of clip path units
	 * @link http://www.php.net/manual/en/imagickdraw.getclipunits.php
	 * @return int an integer on success.
	 */
	public function getClipUnits (): int {}

	/**
	 * Returns the fill color
	 * @link http://www.php.net/manual/en/imagickdraw.getfillcolor.php
	 * @return ImagickPixel an ImagickPixel object.
	 */
	public function getFillColor (): ImagickPixel {}

	/**
	 * Returns the opacity used when drawing
	 * @link http://www.php.net/manual/en/imagickdraw.getfillopacity.php
	 * @return float The opacity.
	 */
	public function getFillOpacity (): float {}

	/**
	 * Returns the fill rule
	 * @link http://www.php.net/manual/en/imagickdraw.getfillrule.php
	 * @return int a FILLRULE constant
	 * (imagick::FILLRULE_&#42;).
	 */
	public function getFillRule (): int {}

	/**
	 * Returns the text placement gravity
	 * @link http://www.php.net/manual/en/imagickdraw.getgravity.php
	 * @return int a GRAVITY constant
	 * (imagick::GRAVITY_&#42;) on success and 0 if no gravity is set.
	 */
	public function getGravity (): int {}

	/**
	 * Returns the current stroke antialias setting
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokeantialias.php
	 * @return bool true if antialiasing is on and false if it is off.
	 */
	public function getStrokeAntialias (): bool {}

	/**
	 * Returns the color used for stroking object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokecolor.php
	 * @return ImagickPixel an ImagickPixel object which describes the color.
	 */
	public function getStrokeColor (): ImagickPixel {}

	/**
	 * Returns an array representing the pattern of dashes and gaps used to stroke paths
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokedasharray.php
	 * @return array an array on success and empty array if not set.
	 */
	public function getStrokeDashArray (): array {}

	/**
	 * Returns the offset into the dash pattern to start the dash
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokedashoffset.php
	 * @return float a float representing the offset and 0 if it's not set.
	 */
	public function getStrokeDashOffset (): float {}

	/**
	 * Returns the shape to be used at the end of open subpaths when they are stroked
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokelinecap.php
	 * @return int a LINECAP constant
	 * (imagick::LINECAP_&#42;), or 0 if stroke linecap is not set.
	 */
	public function getStrokeLineCap (): int {}

	/**
	 * Returns the shape to be used at the corners of paths when they are stroked
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokelinejoin.php
	 * @return int a LINEJOIN constant
	 * (imagick::LINEJOIN_&#42;), or 0 if stroke line join is not set.
	 */
	public function getStrokeLineJoin (): int {}

	/**
	 * Returns the stroke miter limit
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokemiterlimit.php
	 * @return int an int describing the miter limit
	 * and 0 if no miter limit is set.
	 */
	public function getStrokeMiterLimit (): int {}

	/**
	 * Returns the opacity of stroked object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokeopacity.php
	 * @return float a float describing the opacity.
	 */
	public function getStrokeOpacity (): float {}

	/**
	 * Returns the width of the stroke used to draw object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.getstrokewidth.php
	 * @return float a float describing the stroke width.
	 */
	public function getStrokeWidth (): float {}

	/**
	 * Returns the text alignment
	 * @link http://www.php.net/manual/en/imagickdraw.gettextalignment.php
	 * @return int a ALIGN constant
	 * (imagick::ALIGN_&#42;), and 0 if no align is set.
	 */
	public function getTextAlignment (): int {}

	/**
	 * Returns the current text antialias setting
	 * @link http://www.php.net/manual/en/imagickdraw.gettextantialias.php
	 * @return bool true if text is antialiased and false if not.
	 */
	public function getTextAntialias (): bool {}

	/**
	 * Returns a string containing vector graphics
	 * @link http://www.php.net/manual/en/imagickdraw.getvectorgraphics.php
	 * @return string a string containing the vector graphics.
	 */
	public function getVectorGraphics (): string {}

	/**
	 * Returns the text under color
	 * @link http://www.php.net/manual/en/imagickdraw.gettextundercolor.php
	 * @return ImagickPixel an ImagickPixel object describing the color.
	 */
	public function getTextUnderColor (): ImagickPixel {}

	/**
	 * Adds a path element to the current path
	 * @link http://www.php.net/manual/en/imagickdraw.pathclose.php
	 * @return bool 
	 */
	public function pathClose (): bool {}

	/**
	 * Draws a cubic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetoabsolute.php
	 * @param float $x1 x coordinate of the first control point
	 * @param float $y1 y coordinate of the first control point
	 * @param float $x2 x coordinate of the second control point
	 * @param float $y2 y coordinate of the first control point
	 * @param float $x x coordinate of the curve end
	 * @param float $y y coordinate of the curve end
	 * @return bool 
	 */
	public function pathCurveToAbsolute (float $x1, float $y1, float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * Draws a cubic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetorelative.php
	 * @param float $x1 x coordinate of starting control point
	 * @param float $y1 y coordinate of starting control point
	 * @param float $x2 x coordinate of ending control point
	 * @param float $y2 y coordinate of ending control point
	 * @param float $x ending x coordinate
	 * @param float $y ending y coordinate
	 * @return bool 
	 */
	public function pathCurveToRelative (float $x1, float $y1, float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * Draws a quadratic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetoquadraticbezierabsolute.php
	 * @param float $x1 x coordinate of the control point
	 * @param float $y1 y coordinate of the control point
	 * @param float $x x coordinate of the end point
	 * @param float $y y coordinate of the end point
	 * @return bool 
	 */
	public function pathCurveToQuadraticBezierAbsolute (float $x1, float $y1, float $x, float $y): bool {}

	/**
	 * Draws a quadratic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetoquadraticbezierrelative.php
	 * @param float $x1 starting x coordinate
	 * @param float $y1 starting y coordinate
	 * @param float $x ending x coordinate
	 * @param float $y ending y coordinate
	 * @return bool 
	 */
	public function pathCurveToQuadraticBezierRelative (float $x1, float $y1, float $x, float $y): bool {}

	/**
	 * Draws a quadratic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetoquadraticbeziersmoothabsolute.php
	 * @param float $x ending x coordinate
	 * @param float $y ending y coordinate
	 * @return bool 
	 */
	public function pathCurveToQuadraticBezierSmoothAbsolute (float $x, float $y): bool {}

	/**
	 * Draws a quadratic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetoquadraticbeziersmoothrelative.php
	 * @param float $x ending x coordinate
	 * @param float $y ending y coordinate
	 * @return bool 
	 */
	public function pathCurveToQuadraticBezierSmoothRelative (float $x, float $y): bool {}

	/**
	 * Draws a cubic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetosmoothabsolute.php
	 * @param float $x2 x coordinate of the second control point
	 * @param float $y2 y coordinate of the second control point
	 * @param float $x x coordinate of the ending point
	 * @param float $y y coordinate of the ending point
	 * @return bool 
	 */
	public function pathCurveToSmoothAbsolute (float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * Draws a cubic Bezier curve
	 * @link http://www.php.net/manual/en/imagickdraw.pathcurvetosmoothrelative.php
	 * @param float $x2 x coordinate of the second control point
	 * @param float $y2 y coordinate of the second control point
	 * @param float $x x coordinate of the ending point
	 * @param float $y y coordinate of the ending point
	 * @return bool 
	 */
	public function pathCurveToSmoothRelative (float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * Draws an elliptical arc
	 * @link http://www.php.net/manual/en/imagickdraw.pathellipticarcabsolute.php
	 * @param float $rx x radius
	 * @param float $ry y radius
	 * @param float $x_axis_rotation x axis rotation
	 * @param bool $large_arc_flag large arc flag
	 * @param bool $sweep_flag sweep flag
	 * @param float $x x coordinate
	 * @param float $y y coordinate
	 * @return bool 
	 */
	public function pathEllipticArcAbsolute (float $rx, float $ry, float $x_axis_rotation, bool $large_arc_flag, bool $sweep_flag, float $x, float $y): bool {}

	/**
	 * Draws an elliptical arc
	 * @link http://www.php.net/manual/en/imagickdraw.pathellipticarcrelative.php
	 * @param float $rx x radius
	 * @param float $ry y radius
	 * @param float $x_axis_rotation x axis rotation
	 * @param bool $large_arc_flag large arc flag
	 * @param bool $sweep_flag sweep flag
	 * @param float $x x coordinate
	 * @param float $y y coordinate
	 * @return bool 
	 */
	public function pathEllipticArcRelative (float $rx, float $ry, float $x_axis_rotation, bool $large_arc_flag, bool $sweep_flag, float $x, float $y): bool {}

	/**
	 * Terminates the current path
	 * @link http://www.php.net/manual/en/imagickdraw.pathfinish.php
	 * @return bool 
	 */
	public function pathFinish (): bool {}

	/**
	 * Draws a line path
	 * @link http://www.php.net/manual/en/imagickdraw.pathlinetoabsolute.php
	 * @param float $x starting x coordinate
	 * @param float $y ending x coordinate
	 * @return bool 
	 */
	public function pathLineToAbsolute (float $x, float $y): bool {}

	/**
	 * Draws a line path
	 * @link http://www.php.net/manual/en/imagickdraw.pathlinetorelative.php
	 * @param float $x starting x coordinate
	 * @param float $y starting y coordinate
	 * @return bool 
	 */
	public function pathLineToRelative (float $x, float $y): bool {}

	/**
	 * Draws a horizontal line path
	 * @link http://www.php.net/manual/en/imagickdraw.pathlinetohorizontalabsolute.php
	 * @param float $x x coordinate
	 * @return bool 
	 */
	public function pathLineToHorizontalAbsolute (float $x): bool {}

	/**
	 * Draws a horizontal line
	 * @link http://www.php.net/manual/en/imagickdraw.pathlinetohorizontalrelative.php
	 * @param float $x x coordinate
	 * @return bool 
	 */
	public function pathLineToHorizontalRelative (float $x): bool {}

	/**
	 * Draws a vertical line
	 * @link http://www.php.net/manual/en/imagickdraw.pathlinetoverticalabsolute.php
	 * @param float $y y coordinate
	 * @return bool 
	 */
	public function pathLineToVerticalAbsolute (float $y): bool {}

	/**
	 * Draws a vertical line path
	 * @link http://www.php.net/manual/en/imagickdraw.pathlinetoverticalrelative.php
	 * @param float $y y coordinate
	 * @return bool 
	 */
	public function pathLineToVerticalRelative (float $y): bool {}

	/**
	 * Starts a new sub-path
	 * @link http://www.php.net/manual/en/imagickdraw.pathmovetoabsolute.php
	 * @param float $x x coordinate of the starting point
	 * @param float $y y coordinate of the starting point
	 * @return bool 
	 */
	public function pathMoveToAbsolute (float $x, float $y): bool {}

	/**
	 * Starts a new sub-path
	 * @link http://www.php.net/manual/en/imagickdraw.pathmovetorelative.php
	 * @param float $x target x coordinate
	 * @param float $y target y coordinate
	 * @return bool 
	 */
	public function pathMoveToRelative (float $x, float $y): bool {}

	/**
	 * Declares the start of a path drawing list
	 * @link http://www.php.net/manual/en/imagickdraw.pathstart.php
	 * @return bool 
	 */
	public function pathStart (): bool {}

	/**
	 * Draws a polyline
	 * @link http://www.php.net/manual/en/imagickdraw.polyline.php
	 * @param array $coordinates array of x and y coordinates: array( array( 'x' => 4, 'y' => 6 ), array( 'x' => 8, 'y' => 10 ) )
	 * @return bool true on success.
	 */
	public function polyline (array $coordinates): bool {}

	/**
	 * Terminates a clip path definition
	 * @link http://www.php.net/manual/en/imagickdraw.popclippath.php
	 * @return bool 
	 */
	public function popClipPath (): bool {}

	/**
	 * Terminates a definition list
	 * @link http://www.php.net/manual/en/imagickdraw.popdefs.php
	 * @return bool 
	 */
	public function popDefs (): bool {}

	/**
	 * Terminates a pattern definition
	 * @link http://www.php.net/manual/en/imagickdraw.poppattern.php
	 * @return bool true on success or false on failure
	 */
	public function popPattern (): bool {}

	/**
	 * Starts a clip path definition
	 * @link http://www.php.net/manual/en/imagickdraw.pushclippath.php
	 * @param string $clip_mask_id Clip mask Id
	 * @return bool 
	 */
	public function pushClipPath (string $clip_mask_id): bool {}

	/**
	 * Indicates that following commands create named elements for early processing
	 * @link http://www.php.net/manual/en/imagickdraw.pushdefs.php
	 * @return bool 
	 */
	public function pushDefs (): bool {}

	/**
	 * Indicates that subsequent commands up to a ImagickDraw::opPattern() command comprise the definition of a named pattern
	 * @link http://www.php.net/manual/en/imagickdraw.pushpattern.php
	 * @param string $pattern_id the pattern Id
	 * @param float $x x coordinate of the top-left corner
	 * @param float $y y coordinate of the top-left corner
	 * @param float $width width of the pattern
	 * @param float $height height of the pattern
	 * @return bool true on success or false on failure
	 */
	public function pushPattern (string $pattern_id, float $x, float $y, float $width, float $height): bool {}

	/**
	 * Renders all preceding drawing commands onto the image
	 * @link http://www.php.net/manual/en/imagickdraw.render.php
	 * @return bool true on success or false on failure
	 */
	public function render (): bool {}

	/**
	 * Applies the specified rotation to the current coordinate space
	 * @link http://www.php.net/manual/en/imagickdraw.rotate.php
	 * @param float $degrees degrees to rotate.
	 * @return bool 
	 */
	public function rotate (float $degrees): bool {}

	/**
	 * Adjusts the scaling factor
	 * @link http://www.php.net/manual/en/imagickdraw.scale.php
	 * @param float $x horizontal factor
	 * @param float $y vertical factor
	 * @return bool 
	 */
	public function scale (float $x, float $y): bool {}

	/**
	 * Associates a named clipping path with the image
	 * @link http://www.php.net/manual/en/imagickdraw.setclippath.php
	 * @param string $clip_mask the clipping path name
	 * @return bool 
	 */
	public function setClipPath (string $clip_mask): bool {}

	/**
	 * Set the polygon fill rule to be used by the clipping path
	 * @link http://www.php.net/manual/en/imagickdraw.setcliprule.php
	 * @param int $fill_rule One of the FILLRULE constant
	 * (imagick::FILLRULE_&#42;).
	 * @return bool 
	 */
	public function setClipRule (int $fill_rule): bool {}

	/**
	 * Sets the interpretation of clip path units
	 * @link http://www.php.net/manual/en/imagickdraw.setclipunits.php
	 * @param int $clip_units the number of clip units
	 * @return bool 
	 */
	public function setClipUnits (int $clip_units): bool {}

	/**
	 * Sets the opacity to use when drawing using the fill color or fill texture
	 * @link http://www.php.net/manual/en/imagickdraw.setfillopacity.php
	 * @param float $fillOpacity the fill opacity
	 * @return bool 
	 */
	public function setFillOpacity (float $fillOpacity): bool {}

	/**
	 * Sets the URL to use as a fill pattern for filling objects
	 * @link http://www.php.net/manual/en/imagickdraw.setfillpatternurl.php
	 * @param string $fill_url URL to use to obtain fill pattern.
	 * @return bool true on success or false on failure
	 */
	public function setFillPatternUrl (string $fill_url): bool {}

	/**
	 * Sets the fill rule to use while drawing polygons
	 * @link http://www.php.net/manual/en/imagickdraw.setfillrule.php
	 * @param int $fill_rule One of the FILLRULE constant
	 * (imagick::FILLRULE_&#42;).
	 * @return bool 
	 */
	public function setFillRule (int $fill_rule): bool {}

	/**
	 * Sets the text placement gravity
	 * @link http://www.php.net/manual/en/imagickdraw.setgravity.php
	 * @param int $gravity One of the GRAVITY constant
	 * (imagick::GRAVITY_&#42;).
	 * @return bool 
	 */
	public function setGravity (int $gravity): bool {}

	/**
	 * Sets the pattern used for stroking object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokepatternurl.php
	 * @param string $stroke_url stroke URL
	 * @return bool true on success.
	 */
	public function setStrokePatternUrl (string $stroke_url): bool {}

	/**
	 * Specifies the offset into the dash pattern to start the dash
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokedashoffset.php
	 * @param float $dash_offset dash offset
	 * @return bool 
	 */
	public function setStrokeDashOffset (float $dash_offset): bool {}

	/**
	 * Specifies the shape to be used at the end of open subpaths when they are stroked
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokelinecap.php
	 * @param int $linecap One of the LINECAP constant
	 * (imagick::LINECAP_&#42;).
	 * @return bool 
	 */
	public function setStrokeLineCap (int $linecap): bool {}

	/**
	 * Specifies the shape to be used at the corners of paths when they are stroked
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokelinejoin.php
	 * @param int $linejoin One of the LINEJOIN constant
	 * (imagick::LINEJOIN_&#42;).
	 * @return bool 
	 */
	public function setStrokeLineJoin (int $linejoin): bool {}

	/**
	 * Specifies the miter limit
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokemiterlimit.php
	 * @param int $miterlimit the miter limit
	 * @return bool 
	 */
	public function setStrokeMiterLimit (int $miterlimit): bool {}

	/**
	 * Specifies the opacity of stroked object outlines
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokeopacity.php
	 * @param float $stroke_opacity stroke opacity. 1.0 is fully opaque
	 * @return bool 
	 */
	public function setStrokeOpacity (float $stroke_opacity): bool {}

	/**
	 * Sets the vector graphics
	 * @link http://www.php.net/manual/en/imagickdraw.setvectorgraphics.php
	 * @param string $xml xml containing the vector graphics
	 * @return bool true on success or false on failure
	 */
	public function setVectorGraphics (string $xml): bool {}

	/**
	 * Destroys the current ImagickDraw in the stack, and returns to the previously pushed ImagickDraw
	 * @link http://www.php.net/manual/en/imagickdraw.pop.php
	 * @return bool true on success or false on failure
	 */
	public function pop (): bool {}

	/**
	 * Clones the current ImagickDraw and pushes it to the stack
	 * @link http://www.php.net/manual/en/imagickdraw.push.php
	 * @return bool true on success or false on failure
	 */
	public function push (): bool {}

	/**
	 * Specifies the pattern of dashes and gaps used to stroke paths
	 * @link http://www.php.net/manual/en/imagickdraw.setstrokedasharray.php
	 * @param array $dashArray array of floats
	 * @return bool true on success.
	 */
	public function setStrokeDashArray (array $dashArray): bool {}

	public function getOpacity (): float {}

	/**
	 * @param float $opacity
	 */
	public function setOpacity (float $opacity): bool {}

	public function getFontResolution (): array {}

	/**
	 * @param float $x
	 * @param float $y
	 */
	public function setFontResolution (float $x, float $y): bool {}

	public function getBorderColor (): ImagickPixel {}

	/**
	 * @param ImagickPixel|string $color
	 */
	public function setBorderColor (ImagickPixel|string $color): bool {}

	/**
	 * @param string $density
	 */
	public function setDensity (string $density): bool {}

	public function getDensity (): ?string {}

	public function getTextDirection (): int {}

	/**
	 * @param int $direction
	 */
	public function setTextDirection (int $direction): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.imagickpixeliterator.php
 */
class ImagickPixelIterator implements Iterator, Traversable {

	/**
	 * The ImagickPixelIterator constructor
	 * @link http://www.php.net/manual/en/imagickpixeliterator.construct.php
	 * @param Imagick $imagick
	 */
	public function __construct (Imagick $imagick) {}

	/**
	 * Clear resources associated with a PixelIterator
	 * @link http://www.php.net/manual/en/imagickpixeliterator.clear.php
	 * @return bool true on success.
	 */
	public function clear (): bool {}

	/**
	 * @param Imagick $imagick
	 */
	public static function getPixelIterator (Imagick $imagick): ImagickPixelIterator {}

	/**
	 * @param Imagick $imagick
	 * @param int $x
	 * @param int $y
	 * @param int $columns
	 * @param int $rows
	 */
	public static function getPixelRegionIterator (Imagick $imagick, int $x, int $y, int $columns, int $rows): ImagickPixelIterator {}

	/**
	 * Deallocates resources associated with a PixelIterator
	 * @link http://www.php.net/manual/en/imagickpixeliterator.destroy.php
	 * @return bool true on success.
	 */
	public function destroy (): bool {}

	/**
	 * Returns the current row of ImagickPixel objects
	 * @link http://www.php.net/manual/en/imagickpixeliterator.getcurrentiteratorrow.php
	 * @return array a row as an array of ImagickPixel objects that can themselves be iterated.
	 */
	public function getCurrentIteratorRow (): array {}

	/**
	 * Returns the current pixel iterator row
	 * @link http://www.php.net/manual/en/imagickpixeliterator.getiteratorrow.php
	 * @return int the integer offset of the row, throwing
	 * ImagickPixelIteratorException on error.
	 */
	public function getIteratorRow (): int {}

	/**
	 * Returns the next row of the pixel iterator
	 * @link http://www.php.net/manual/en/imagickpixeliterator.getnextiteratorrow.php
	 * @return array the next row as an array of ImagickPixel objects, throwing
	 * ImagickPixelIteratorException on error.
	 */
	public function getNextIteratorRow (): array {}

	/**
	 * Returns the previous row
	 * @link http://www.php.net/manual/en/imagickpixeliterator.getpreviousiteratorrow.php
	 * @return array the previous row as an array of ImagickPixelWand objects from the
	 * ImagickPixelIterator, throwing ImagickPixelIteratorException on error.
	 */
	public function getPreviousIteratorRow (): array {}

	public function key (): int {}

	public function next () {}

	public function rewind () {}

	public function current (): array {}

	/**
	 * Returns a new pixel iterator
	 * @link http://www.php.net/manual/en/imagickpixeliterator.newpixeliterator.php
	 * @param Imagick $wand 
	 * @return bool true on success. Throwing ImagickPixelIteratorException.
	 */
	public function newPixelIterator (Imagick $wand): bool {}

	/**
	 * Returns a new pixel iterator
	 * @link http://www.php.net/manual/en/imagickpixeliterator.newpixelregioniterator.php
	 * @param Imagick $wand 
	 * @param int $x 
	 * @param int $y 
	 * @param int $columns 
	 * @param int $rows 
	 * @return bool a new ImagickPixelIterator on success; on failure, throws
	 * ImagickPixelIteratorException.
	 */
	public function newPixelRegionIterator (Imagick $wand, int $x, int $y, int $columns, int $rows): bool {}

	/**
	 * Resets the pixel iterator
	 * @link http://www.php.net/manual/en/imagickpixeliterator.resetiterator.php
	 * @return bool true on success.
	 */
	public function resetIterator (): bool {}

	/**
	 * Sets the pixel iterator to the first pixel row
	 * @link http://www.php.net/manual/en/imagickpixeliterator.setiteratorfirstrow.php
	 * @return bool true on success.
	 */
	public function setIteratorFirstRow (): bool {}

	/**
	 * Sets the pixel iterator to the last pixel row
	 * @link http://www.php.net/manual/en/imagickpixeliterator.setiteratorlastrow.php
	 * @return bool true on success.
	 */
	public function setIteratorLastRow (): bool {}

	/**
	 * Set the pixel iterator row
	 * @link http://www.php.net/manual/en/imagickpixeliterator.setiteratorrow.php
	 * @param int $row 
	 * @return bool true on success.
	 */
	public function setIteratorRow (int $row): bool {}

	/**
	 * Syncs the pixel iterator
	 * @link http://www.php.net/manual/en/imagickpixeliterator.synciterator.php
	 * @return bool true on success.
	 */
	public function syncIterator (): bool {}

	public function valid (): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.imagickpixel.php
 */
class ImagickPixel  {

	/**
	 * The ImagickPixel constructor
	 * @link http://www.php.net/manual/en/imagickpixel.construct.php
	 * @param string|null $color [optional]
	 */
	public function __construct (string|null $color = null) {}

	/**
	 * Clears resources associated with this object
	 * @link http://www.php.net/manual/en/imagickpixel.clear.php
	 * @return bool true on success.
	 */
	public function clear (): bool {}

	/**
	 * Deallocates resources associated with this object
	 * @link http://www.php.net/manual/en/imagickpixel.destroy.php
	 * @return bool true on success.
	 */
	public function destroy (): bool {}

	/**
	 * Returns the color
	 * @link http://www.php.net/manual/en/imagickpixel.getcolor.php
	 * @param int $normalized [optional] <p>
	 * Normalize the color values. Possible values are 0,
	 * 1 or 2.
	 * </p>
	 * <table>
	 * List of possible values for normalized
	 * <table>
	 * <tr valign="top">
	 * <td>normalized</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * 0
	 * </td>
	 * <td>
	 * The RGB values are returned as ints in the range 0
	 * to 255 (inclusive.)
	 * The alpha value is returned as int and is either 0
	 * or 1.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * 1
	 * </td>
	 * <td>
	 * The RGBA values are returned as floats in the range 0
	 * to 1 (inclusive.)
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>
	 * 2
	 * </td>
	 * <td>
	 * The RGBA values are returned as ints in the range 0
	 * to 255 (inclusive.)
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * @return array An array of channel values. Throws ImagickPixelException on error.
	 */
	public function getColor (int $normalized = null): array {}

	/**
	 * Returns the color as a string
	 * @link http://www.php.net/manual/en/imagickpixel.getcolorasstring.php
	 * @return string the color of the ImagickPixel object as a string.
	 */
	public function getColorAsString (): string {}

	/**
	 * Returns the color count associated with this color
	 * @link http://www.php.net/manual/en/imagickpixel.getcolorcount.php
	 * @return int the color count as an integer on success, throws
	 * ImagickPixelException on failure.
	 */
	public function getColorCount (): int {}

	/**
	 * Returns the color of the pixel in an array as Quantum values
	 * @link http://www.php.net/manual/en/imagickpixel.getcolorquantum.php
	 * @return array an array with keys "r", "g",
	 * "b", "a".
	 */
	public function getColorQuantum (): array {}

	/**
	 * Gets the normalized value of the provided color channel
	 * @link http://www.php.net/manual/en/imagickpixel.getcolorvalue.php
	 * @param int $color The color to get the value of, specified as one of the Imagick color
	 * constants. This can be one of the RGB colors, CMYK colors, alpha and
	 * opacity e.g (Imagick::COLOR_BLUE, Imagick::COLOR_MAGENTA).
	 * @return float The value of the channel, as a normalized floating-point number, throwing
	 * ImagickPixelException on error.
	 */
	public function getColorValue (int $color): float {}

	/**
	 * Gets the quantum value of a color in the ImagickPixel
	 * @link http://www.php.net/manual/en/imagickpixel.getcolorvaluequantum.php
	 * @param int $color 
	 * @return mixed The quantum value of the color element. Float if ImageMagick was compiled with HDRI, otherwise an int.
	 */
	public function getColorValueQuantum (int $color): float {}

	/**
	 * Returns the normalized HSL color of the ImagickPixel object
	 * @link http://www.php.net/manual/en/imagickpixel.gethsl.php
	 * @return array the HSL value in an array with the keys "hue",
	 * "saturation", and "luminosity". Throws ImagickPixelException on failure.
	 */
	public function getHSL (): array {}

	/**
	 * Gets the colormap index of the pixel wand
	 * @link http://www.php.net/manual/en/imagickpixel.getindex.php
	 * @return int 
	 */
	public function getIndex (): int {}

	/**
	 * Check the distance between this color and another
	 * @link http://www.php.net/manual/en/imagickpixel.ispixelsimilar.php
	 * @param ImagickPixel $color The ImagickPixel object to compare this object against.
	 * @param float $fuzz The maximum distance within which to consider these colors as similar.
	 * The theoretical maximum for this value is the square root of three
	 * (1.732).
	 * @return bool true on success.
	 */
	public function isPixelSimilar (ImagickPixel $color, float $fuzz): bool {}

	/**
	 * Returns whether two colors differ by less than the specified distance
	 * @link http://www.php.net/manual/en/imagickpixel.ispixelsimilarquantum.php
	 * @param string $color 
	 * @param string $fuzz [optional] 
	 * @return bool 
	 */
	public function isPixelSimilarQuantum (string $color, string $fuzz = null): bool {}

	/**
	 * Check the distance between this color and another
	 * @link http://www.php.net/manual/en/imagickpixel.issimilar.php
	 * @param ImagickPixel $color The ImagickPixel object to compare this object against.
	 * @param float $fuzz The maximum distance within which to consider these colors as similar.
	 * The theoretical maximum for this value is the square root of three
	 * (1.732).
	 * @return bool true on success.
	 */
	public function isSimilar (ImagickPixel $color, float $fuzz): bool {}

	/**
	 * Sets the color
	 * @link http://www.php.net/manual/en/imagickpixel.setcolor.php
	 * @param string $color The color definition to use in order to initialise the
	 * ImagickPixel object.
	 * @return bool true if the specified color was set, false otherwise.
	 */
	public function setColor (string $color): bool {}

	/**
	 * Sets the color count associated with this color
	 * @link http://www.php.net/manual/en/imagickpixel.setcolorcount.php
	 * @param int $colorCount 
	 * @return bool true on success.
	 */
	public function setColorCount (int $colorCount): bool {}

	/**
	 * Sets the normalized value of one of the channels
	 * @link http://www.php.net/manual/en/imagickpixel.setcolorvalue.php
	 * @param int $color One of the Imagick color constants e.g. \Imagick::COLOR_GREEN or \Imagick::COLOR_ALPHA.
	 * @param float $value The value to set this channel to, ranging from 0 to 1.
	 * @return bool true on success.
	 */
	public function setColorValue (int $color, float $value): bool {}

	/**
	 * Sets the quantum value of a color element of the ImagickPixel
	 * @link http://www.php.net/manual/en/imagickpixel.setcolorvaluequantum.php
	 * @param int $color Which color element to set e.g. \Imagick::COLOR_GREEN.
	 * @param mixed $value The quantum value to set the color element to. This should be a float if ImageMagick was compiled with HDRI otherwise an int in the range 0 to Imagick::getQuantum().
	 * @return bool true on success.
	 */
	public function setColorValueQuantum (int $color, $value): bool {}

	/**
	 * Sets the normalized HSL color
	 * @link http://www.php.net/manual/en/imagickpixel.sethsl.php
	 * @param float $hue The normalized value for hue, described as a fractional arc
	 * (between 0 and 1) of the hue circle, where the zero value is
	 * red.
	 * @param float $saturation The normalized value for saturation, with 1 as full saturation.
	 * @param float $luminosity The normalized value for luminosity, on a scale from black at
	 * 0 to white at 1, with the full HS value at 0.5 luminosity.
	 * @return bool true on success.
	 */
	public function setHSL (float $hue, float $saturation, float $luminosity): bool {}

	/**
	 * Sets the colormap index of the pixel wand
	 * @link http://www.php.net/manual/en/imagickpixel.setindex.php
	 * @param int $index 
	 * @return bool true on success.
	 */
	public function setIndex (int $index): bool {}

	/**
	 * @param ImagickPixel $pixel
	 */
	public function setColorFromPixel (ImagickPixel $pixel): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.imagickkernel.php
 */
class ImagickKernel  {

	/**
	 * Attach another kernel to a kernel list
	 * @link http://www.php.net/manual/en/imagickkernel.addkernel.php
	 * @param ImagickKernel $ImagickKernel 
	 * @return void 
	 */
	public function addKernel (ImagickKernel $ImagickKernel): void {}

	/**
	 * Adds a Unity Kernel to the kernel list
	 * @link http://www.php.net/manual/en/imagickkernel.addunitykernel.php
	 * @param float $scale 
	 * @return void 
	 */
	public function addUnityKernel (float $scale): void {}

	/**
	 * Create a kernel from a builtin in kernel
	 * @link http://www.php.net/manual/en/imagickkernel.frombuiltin.php
	 * @param int $kernelType 
	 * @param string $kernelString A string that describes the parameters e.g. "4,2.5"
	 * @return ImagickKernel 
	 */
	public static function fromBuiltin (int $kernelType, string $kernelString): ImagickKernel {}

	/**
	 * Create a kernel from a 2d matrix of values
	 * @link http://www.php.net/manual/en/imagickkernel.frommatrix.php
	 * @param array $matrix 
	 * @param array $origin [optional] 
	 * @return ImagickKernel The generated ImagickKernel.
	 */
	public static function fromMatrix (array $matrix, array $origin = null): ImagickKernel {}

	/**
	 * Get the 2d matrix of values used in this kernel
	 * @link http://www.php.net/manual/en/imagickkernel.getmatrix.php
	 * @return array A matrix (2d array) of the values that represent the kernel.
	 */
	public function getMatrix (): array {}

	/**
	 * Scales a kernel list by the given amount
	 * @link http://www.php.net/manual/en/imagickkernel.scale.php
	 * @param float $scale 
	 * @param int $normalizeFlag [optional] Imagick::NORMALIZE_KERNEL_NONE
	 * Imagick::NORMALIZE_KERNEL_VALUE
	 * Imagick::NORMALIZE_KERNEL_CORRELATE
	 * Imagick::NORMALIZE_KERNEL_PERCENT
	 * @return void 
	 */
	public function scale (float $scale, int $normalizeFlag = null): void {}

	/**
	 * Separates a linked set of kernels and returns an array of ImagickKernels
	 * @link http://www.php.net/manual/en/imagickkernel.separate.php
	 * @return array 
	 */
	public function separate (): array {}

}
// End of imagick v.3.7.0
