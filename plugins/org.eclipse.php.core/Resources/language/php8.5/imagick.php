<?php

// Start of imagick v.3.7.0

class ImagickException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ImagickDrawException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ImagickPixelIteratorException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ImagickPixelException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ImagickKernelException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

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
	 * {@inheritdoc}
	 */
	public function optimizeImageLayers (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $metric
	 */
	public function compareImageLayers (int $metric): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param string $image
	 */
	public function pingImageBlob (string $image): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $filehandle
	 * @param string|null $filename [optional]
	 */
	public function pingImageFile (mixed $filehandle = null, ?string $filename = NULL): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function transposeImage (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function transverseImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $fuzz
	 */
	public function trimImage (float $fuzz): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $amplitude
	 * @param float $length
	 */
	public function waveImage (float $amplitude, float $length): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $amplitude
	 * @param float $length
	 * @param int $interpolate_method
	 */
	public function waveImageWithMethod (float $amplitude, float $length, int $interpolate_method): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $black_point
	 * @param float $white_point
	 * @param int $x
	 * @param int $y
	 */
	public function vignetteImage (float $black_point, float $white_point, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function uniqueImageColors (): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $matte
	 */
	public function setImageMatte (bool $matte): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 * @param bool $bestfit [optional]
	 * @param bool $legacy [optional]
	 */
	public function adaptiveResizeImage (int $columns, int $rows, bool $bestfit = false, bool $legacy = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param float $angle
	 */
	public function sketchImage (float $radius, float $sigma, float $angle): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $gray
	 * @param float $azimuth
	 * @param float $elevation
	 */
	public function shadeImage (bool $gray, float $azimuth, float $elevation): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getSizeOffset (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 * @param int $offset
	 */
	public function setSizeOffset (int $columns, int $rows, int $offset): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param int $channel [optional]
	 */
	public function adaptiveBlurImage (float $radius, float $sigma, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $black_point
	 * @param float $white_point
	 * @param int $channel [optional]
	 */
	public function contrastStretchImage (float $black_point, float $white_point, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param int $channel [optional]
	 */
	public function adaptiveSharpenImage (float $radius, float $sigma, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $low
	 * @param float $high
	 * @param int $channel [optional]
	 */
	public function randomThresholdImage (float $low, float $high, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x_rounding
	 * @param float $y_rounding
	 * @param float $stroke_width [optional]
	 * @param float $displace [optional]
	 * @param float $size_correction [optional]
	 */
	public function roundCornersImage (float $x_rounding, float $y_rounding, float $stroke_width = 10, float $displace = 5, float $size_correction = -6): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x_rounding
	 * @param float $y_rounding
	 * @param float $stroke_width [optional]
	 * @param float $displace [optional]
	 * @param float $size_correction [optional]
	 */
	public function roundCorners (float $x_rounding, float $y_rounding, float $stroke_width = 10, float $displace = 5, float $size_correction = -6): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function setIteratorIndex (int $index): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getIteratorIndex (): int {}

	/**
	 * {@inheritdoc}
	 * @param float $alpha
	 */
	public function setImageAlpha (float $alpha): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickDraw $settings
	 * @param float $angle
	 * @param string $caption
	 * @param int $method
	 */
	public function polaroidWithTextAndMethod (ImagickDraw $settings, float $angle, string $caption, int $method): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickDraw $settings
	 * @param float $angle
	 */
	public function polaroidImage (ImagickDraw $settings, float $angle): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getImageProperty (string $name): string {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $value
	 */
	public function setImageProperty (string $name, string $value): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function deleteImageProperty (string $name): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 */
	public function identifyFormat (string $format): string {}

	/**
	 * {@inheritdoc}
	 * @param int $method
	 */
	public function setImageInterpolateMethod (int $method): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageInterpolateMethod (): int {}

	/**
	 * {@inheritdoc}
	 * @param float $black_point
	 * @param float $white_point
	 */
	public function linearStretchImage (float $black_point, float $white_point): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageLength (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 */
	public function extentImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageOrientation (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $orientation
	 */
	public function setImageOrientation (int $orientation): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $lookup_table
	 * @param int $channel [optional]
	 */
	public function clutImage (Imagick $lookup_table, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern [optional]
	 * @param bool $include_values [optional]
	 */
	public function getImageProperties (string $pattern = '*', bool $include_values = true): array {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern [optional]
	 * @param bool $include_values [optional]
	 */
	public function getImageProfiles (string $pattern = '*', bool $include_values = true): array {}

	/**
	 * {@inheritdoc}
	 * @param int $distortion
	 * @param array $arguments
	 * @param bool $bestfit
	 */
	public function distortImage (int $distortion, array $arguments, bool $bestfit): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $filehandle
	 * @param string|null $format [optional]
	 */
	public function writeImageFile (mixed $filehandle = null, ?string $format = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $filehandle
	 * @param string|null $format [optional]
	 */
	public function writeImagesFile (mixed $filehandle = null, ?string $format = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $page
	 */
	public function resetImagePage (string $page): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $x_server
	 */
	public function animateImages (string $x_server): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $font
	 */
	public function setFont (string $font): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getFont (): string {}

	/**
	 * {@inheritdoc}
	 * @param float $point_size
	 */
	public function setPointSize (float $point_size): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getPointSize (): float {}

	/**
	 * {@inheritdoc}
	 * @param int $layermethod
	 */
	public function mergeImageLayers (int $layermethod): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param int $alphachannel
	 */
	public function setImageAlphaChannel (int $alphachannel): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $fill_color
	 * @param float $fuzz
	 * @param ImagickPixel|string $border_color
	 * @param int $x
	 * @param int $y
	 * @param bool $invert
	 * @param int|null $channel [optional]
	 */
	public function floodfillPaintImage (ImagickPixel|string $fill_color, float $fuzz, ImagickPixel|string $border_color, int $x, int $y, bool $invert, ?int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $target_color
	 * @param ImagickPixel|string $fill_color
	 * @param float $fuzz
	 * @param bool $invert
	 * @param int $channel [optional]
	 */
	public function opaquePaintImage (ImagickPixel|string $target_color, ImagickPixel|string $fill_color, float $fuzz, bool $invert, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $target_color
	 * @param float $alpha
	 * @param float $fuzz
	 * @param bool $invert
	 */
	public function transparentPaintImage (ImagickPixel|string $target_color, float $alpha, float $fuzz, bool $invert): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param float $delta_x
	 * @param float $rigidity
	 */
	public function liquidRescaleImage (int $width, int $height, float $delta_x, float $rigidity): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $passphrase
	 */
	public function encipherImage (string $passphrase): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $passphrase
	 */
	public function decipherImage (string $passphrase): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $gravity
	 */
	public function setGravity (int $gravity): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getGravity (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $channel
	 */
	public function getImageChannelRange (int $channel): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageAlphaChannel (): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $reference_image
	 * @param int $metric
	 * @param int $channel [optional]
	 */
	public function getImageChannelDistortions (Imagick $reference_image, int $metric, int $channel = 134217727): float {}

	/**
	 * {@inheritdoc}
	 * @param int $gravity
	 */
	public function setImageGravity (int $gravity): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageGravity (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $x
	 * @param int $y
	 * @param int $width
	 * @param int $height
	 * @param string $map
	 * @param int $pixelstorage
	 * @param array $pixels
	 */
	public function importImagePixels (int $x, int $y, int $width, int $height, string $map, int $pixelstorage, array $pixels): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $threshold
	 */
	public function deskewImage (float $threshold): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $colorspace
	 * @param float $cluster_threshold
	 * @param float $smooth_threshold
	 * @param bool $verbose [optional]
	 */
	public function segmentImage (int $colorspace, float $cluster_threshold, float $smooth_threshold, bool $verbose = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $sparsecolormethod
	 * @param array $arguments
	 * @param int $channel [optional]
	 */
	public function sparseColorImage (int $sparsecolormethod, array $arguments, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $replacement
	 * @param int $dither_method
	 */
	public function remapImage (Imagick $replacement, int $dither_method): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param float $threshold
	 */
	public function houghLineImage (int $width, int $height, float $threshold): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $x
	 * @param int $y
	 * @param int $width
	 * @param int $height
	 * @param string $map
	 * @param int $pixelstorage
	 */
	public function exportImagePixels (int $x, int $y, int $width, int $height, string $map, int $pixelstorage): array {}

	/**
	 * {@inheritdoc}
	 * @param int $channel [optional]
	 */
	public function getImageChannelKurtosis (int $channel = 134217727): array {}

	/**
	 * {@inheritdoc}
	 * @param int $function
	 * @param array $parameters
	 * @param int $channel [optional]
	 */
	public function functionImage (int $function, array $parameters, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $colorspace
	 */
	public function transformImageColorspace (int $colorspace): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $clut
	 * @param int $channel [optional]
	 */
	public function haldClutImage (Imagick $clut, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $channel [optional]
	 */
	public function autoLevelImage (int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $factor [optional]
	 */
	public function blueShiftImage (float $factor = 1.5): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $artifact
	 */
	public function getImageArtifact (string $artifact): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string $artifact
	 * @param string|null $value
	 */
	public function setImageArtifact (string $artifact, ?string $value = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $artifact
	 */
	public function deleteImageArtifact (string $artifact): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getColorspace (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $colorspace
	 */
	public function setColorspace (int $colorspace): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $channel [optional]
	 */
	public function clampImage (int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $stack
	 * @param int $offset
	 */
	public function smushImages (bool $stack, int $offset): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param array|string|int|float|null $files [optional]
	 */
	public function __construct (array|string|int|float|null $files = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function count (int $mode = 0): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getPixelIterator (): ImagickPixelIterator {}

	/**
	 * {@inheritdoc}
	 * @param int $x
	 * @param int $y
	 * @param int $columns
	 * @param int $rows
	 */
	public function getPixelRegionIterator (int $x, int $y, int $columns, int $rows): ImagickPixelIterator {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function readImage (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $filenames
	 */
	public function readImages (array $filenames): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $image
	 * @param string|null $filename [optional]
	 */
	public function readImageBlob (string $image, ?string $filename = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 */
	public function setImageFormat (string $format): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 * @param bool $bestfit [optional]
	 * @param bool $legacy [optional]
	 */
	public function scaleImage (int $columns, int $rows, bool $bestfit = false, bool $legacy = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 */
	public function writeImage (?string $filename = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param bool $adjoin
	 */
	public function writeImages (string $filename, bool $adjoin): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param int $channel [optional]
	 */
	public function blurImage (float $radius, float $sigma, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int|null $columns
	 * @param int|null $rows
	 * @param bool $bestfit [optional]
	 * @param bool $fill [optional]
	 * @param bool $legacy [optional]
	 */
	public function thumbnailImage (?int $columns = null, ?int $rows = null, bool $bestfit = false, bool $fill = false, bool $legacy = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param bool $legacy [optional]
	 */
	public function cropThumbnailImage (int $width, int $height, bool $legacy = false): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageFilename (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function setImageFilename (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageFormat (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageMimeType (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function removeImage (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function destroy (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clear (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clone (): Imagick {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageSize (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageBlob (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getImagesBlob (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function setFirstIterator (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function setLastIterator (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function resetIterator (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function previousImage (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function nextImage (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function hasPreviousImage (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function hasNextImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function setImageIndex (int $index): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageIndex (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $comment
	 */
	public function commentImage (string $comment): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 */
	public function cropImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $label
	 */
	public function labelImage (string $label): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageGeometry (): array {}

	/**
	 * {@inheritdoc}
	 * @param ImagickDraw $drawing
	 */
	public function drawImage (ImagickDraw $drawing): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $quality
	 */
	public function setImageCompressionQuality (int $quality): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageCompressionQuality (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $compression
	 */
	public function setImageCompression (int $compression): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageCompression (): int {}

	/**
	 * {@inheritdoc}
	 * @param ImagickDraw $settings
	 * @param float $x
	 * @param float $y
	 * @param float $angle
	 * @param string $text
	 */
	public function annotateImage (ImagickDraw $settings, float $x, float $y, float $angle, string $text): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $composite_image
	 * @param int $composite
	 * @param int $x
	 * @param int $y
	 * @param int $channel [optional]
	 */
	public function compositeImage (Imagick $composite_image, int $composite, int $x, int $y, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $brightness
	 * @param float $saturation
	 * @param float $hue
	 */
	public function modulateImage (float $brightness, float $saturation, float $hue): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageColors (): int {}

	/**
	 * {@inheritdoc}
	 * @param ImagickDraw $settings
	 * @param string $tile_geometry
	 * @param string $thumbnail_geometry
	 * @param int $monatgemode
	 * @param string $frame
	 */
	public function montageImage (ImagickDraw $settings, string $tile_geometry, string $thumbnail_geometry, int $monatgemode, string $frame): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param bool $append_raw_output [optional]
	 */
	public function identifyImage (bool $append_raw_output = false): array {}

	/**
	 * {@inheritdoc}
	 * @param float $threshold
	 * @param int $channel [optional]
	 */
	public function thresholdImage (float $threshold, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $offset
	 */
	public function adaptiveThresholdImage (int $width, int $height, int $offset): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $threshold_color
	 */
	public function blackThresholdImage (ImagickPixel|string $threshold_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $threshold_color
	 */
	public function whiteThresholdImage (ImagickPixel|string $threshold_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $stack
	 */
	public function appendImages (bool $stack): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 */
	public function charcoalImage (float $radius, float $sigma): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $channel [optional]
	 */
	public function normalizeImage (int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 */
	public function oilPaintImageWithSigma (float $radius, float $sigma): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 */
	public function oilPaintImage (float $radius): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $levels
	 * @param bool $dither
	 */
	public function posterizeImage (int $levels, bool $dither): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 * @param bool $raise
	 */
	public function raiseImage (int $width, int $height, int $x, int $y, bool $raise): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x_resolution
	 * @param float $y_resolution
	 * @param int $filter
	 * @param float $blur
	 */
	public function resampleImage (float $x_resolution, float $y_resolution, int $filter, float $blur): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 * @param int $filter
	 * @param float $blur
	 * @param bool $bestfit [optional]
	 * @param bool $legacy [optional]
	 */
	public function resizeImage (int $columns, int $rows, int $filter, float $blur, bool $bestfit = false, bool $legacy = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $x
	 * @param int $y
	 */
	public function rollImage (int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $background_color
	 * @param float $degrees
	 */
	public function rotateImage (ImagickPixel|string $background_color, float $degrees): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 */
	public function sampleImage (int $columns, int $rows): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $threshold
	 */
	public function solarizeImage (int $threshold): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $opacity
	 * @param float $sigma
	 * @param int $x
	 * @param int $y
	 */
	public function shadowImage (float $opacity, float $sigma, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $background_color
	 */
	public function setImageBackgroundColor (ImagickPixel|string $background_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $channel
	 */
	public function setImageChannelMask (int $channel): int {}

	/**
	 * {@inheritdoc}
	 * @param int $compose
	 */
	public function setImageCompose (int $compose): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $delay
	 */
	public function setImageDelay (int $delay): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $depth
	 */
	public function setImageDepth (int $depth): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $gamma
	 */
	public function setImageGamma (float $gamma): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $iterations
	 */
	public function setImageIterations (int $iterations): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $matte_color
	 */
	public function setImageMatteColor (ImagickPixel|string $matte_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 */
	public function setImagePage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function setImageProgressMonitor (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function setProgressMonitor (callable $callback): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x_resolution
	 * @param float $y_resolution
	 */
	public function setImageResolution (float $x_resolution, float $y_resolution): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $scene
	 */
	public function setImageScene (int $scene): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $ticks_per_second
	 */
	public function setImageTicksPerSecond (int $ticks_per_second): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $image_type
	 */
	public function setImageType (int $image_type): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $units
	 */
	public function setImageUnits (int $units): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param int $channel [optional]
	 */
	public function sharpenImage (float $radius, float $sigma, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 */
	public function shaveImage (int $columns, int $rows): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $background_color
	 * @param float $x_shear
	 * @param float $y_shear
	 */
	public function shearImage (ImagickPixel|string $background_color, float $x_shear, float $y_shear): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 */
	public function spliceImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function pingImage (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $filehandle
	 * @param string|null $filename [optional]
	 */
	public function readImageFile (mixed $filehandle = null, ?string $filename = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $servername
	 */
	public function displayImage (string $servername): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $servername
	 */
	public function displayImages (string $servername): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 */
	public function spreadImage (float $radius): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param int $interpolate_method
	 */
	public function spreadImageWithMethod (float $radius, int $interpolate_method): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $degrees
	 */
	public function swirlImage (float $degrees): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $degrees
	 * @param int $interpolate_method
	 */
	public function swirlImageWithMethod (float $degrees, int $interpolate_method): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function stripImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern [optional]
	 */
	public static function queryFormats (string $pattern = '*'): array {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern [optional]
	 */
	public static function queryFonts (string $pattern = '*'): array {}

	/**
	 * {@inheritdoc}
	 * @param ImagickDraw $settings
	 * @param string $text
	 * @param bool|null $multiline [optional]
	 */
	public function queryFontMetrics (ImagickDraw $settings, string $text, ?bool $multiline = NULL): array {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $watermark
	 * @param int $offset
	 */
	public function steganoImage (Imagick $watermark, int $offset): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param int $noise
	 * @param int $channel [optional]
	 */
	public function addNoiseImage (int $noise, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $noise
	 * @param float $attenuate
	 * @param int $channel [optional]
	 */
	public function addNoiseImageWithAttenuate (int $noise, float $attenuate, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param float $angle
	 * @param int $channel [optional]
	 */
	public function motionBlurImage (float $radius, float $sigma, float $angle, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $number_frames
	 */
	public function morphImages (int $number_frames): Imagick {}

	/**
	 * {@inheritdoc}
	 */
	public function minifyImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickDraw $settings
	 */
	public function affineTransformImage (ImagickDraw $settings): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function averageImages (): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $border_color
	 * @param int $width
	 * @param int $height
	 */
	public function borderImage (ImagickPixel|string $border_color, int $width, int $height): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $border_color
	 * @param int $width
	 * @param int $height
	 * @param int $composite
	 */
	public function borderImageWithComposite (ImagickPixel|string $border_color, int $width, int $height, int $composite): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $original_width
	 * @param int $original_height
	 * @param int $desired_width
	 * @param int $desired_height
	 * @param bool $legacy [optional]
	 */
	public static function calculateCrop (int $original_width, int $original_height, int $desired_width, int $desired_height, bool $legacy = false): array {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 */
	public function chopImage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clipImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $pathname
	 * @param bool $inside
	 */
	public function clipPathImage (string $pathname, bool $inside): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $pathname
	 * @param bool $inside
	 */
	public function clipImagePath (string $pathname, bool $inside): void {}

	/**
	 * {@inheritdoc}
	 */
	public function coalesceImages (): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $colorize_color
	 * @param ImagickPixel|string|false $opacity_color
	 * @param bool|null $legacy [optional]
	 */
	public function colorizeImage (ImagickPixel|string $colorize_color, ImagickPixel|string|false $opacity_color, ?bool $legacy = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $reference
	 * @param int $channel
	 * @param int $metric
	 */
	public function compareImageChannels (Imagick $reference, int $channel, int $metric): array {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $reference
	 * @param int $metric
	 */
	public function compareImages (Imagick $reference, int $metric): array {}

	/**
	 * {@inheritdoc}
	 * @param bool $sharpen
	 */
	public function contrastImage (bool $sharpen): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $colorspace
	 */
	public function combineImages (int $colorspace): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param array $kernel
	 * @param int $channel [optional]
	 */
	public function convolveImage (array $kernel, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $displace
	 */
	public function cycleColormapImage (int $displace): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function deconstructImages (): Imagick {}

	/**
	 * {@inheritdoc}
	 */
	public function despeckleImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 */
	public function edgeImage (float $radius): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 */
	public function embossImage (float $radius, float $sigma): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function enhanceImage (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function equalizeImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $evaluate
	 * @param float $constant
	 * @param int $channel [optional]
	 */
	public function evaluateImage (int $evaluate, float $constant, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $evaluate
	 */
	public function evaluateImages (int $evaluate): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function flattenImages (): Imagick {}

	/**
	 * {@inheritdoc}
	 */
	public function flipImage (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function flopImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $magnitude
	 */
	public function forwardFourierTransformImage (bool $magnitude): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $matte_color
	 * @param int $width
	 * @param int $height
	 * @param int $inner_bevel
	 * @param int $outer_bevel
	 */
	public function frameImage (ImagickPixel|string $matte_color, int $width, int $height, int $inner_bevel, int $outer_bevel): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $matte_color
	 * @param int $width
	 * @param int $height
	 * @param int $inner_bevel
	 * @param int $outer_bevel
	 * @param int $composite
	 */
	public function frameImageWithComposite (ImagickPixel|string $matte_color, int $width, int $height, int $inner_bevel, int $outer_bevel, int $composite): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 * @param int $channel [optional]
	 */
	public function fxImage (string $expression, int $channel = 134217727): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param float $gamma
	 * @param int $channel [optional]
	 */
	public function gammaImage (float $gamma, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param int $channel [optional]
	 */
	public function gaussianBlurImage (float $radius, float $sigma, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageBackgroundColor (): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageBluePrimary (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageBorderColor (): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 * @param int $channel
	 */
	public function getImageChannelDepth (int $channel): int {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $reference
	 * @param int $channel
	 * @param int $metric
	 */
	public function getImageChannelDistortion (Imagick $reference, int $channel, int $metric): float {}

	/**
	 * {@inheritdoc}
	 * @param int $channel
	 */
	public function getImageChannelMean (int $channel): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageChannelStatistics (): array {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function getImageColormapColor (int $index): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageColorspace (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageCompose (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageDelay (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageDepth (): int {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $reference
	 * @param int $metric
	 */
	public function getImageDistortion (Imagick $reference, int $metric): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageDispose (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageGamma (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageGreenPrimary (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageHeight (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageHistogram (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageInterlaceScheme (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageIterations (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImagePage (): array {}

	/**
	 * {@inheritdoc}
	 * @param int $x
	 * @param int $y
	 */
	public function getImagePixelColor (int $x, int $y): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 * @param int $x
	 * @param int $y
	 * @param ImagickPixel|string $color
	 */
	public function setImagePixelColor (int $x, int $y, ImagickPixel|string $color): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getImageProfile (string $name): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageRedPrimary (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageRenderingIntent (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageResolution (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageScene (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageSignature (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageTicksPerSecond (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageType (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageUnits (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageVirtualPixelMethod (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageWhitePoint (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageWidth (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNumberImages (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageTotalInkDensity (): float {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 */
	public function getImageRegion (int $width, int $height, int $x, int $y): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 */
	public function implodeImage (float $radius): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param int $pixel_interpolate_method
	 */
	public function implodeImageWithMethod (float $radius, int $pixel_interpolate_method): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $complement
	 * @param bool $magnitude
	 */
	public function inverseFourierTransformImage (Imagick $complement, bool $magnitude): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $black_point
	 * @param float $gamma
	 * @param float $white_point
	 * @param int $channel [optional]
	 */
	public function levelImage (float $black_point, float $gamma, float $white_point, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function magnifyImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $gray
	 * @param int $channel [optional]
	 */
	public function negateImage (bool $gray, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $preview
	 */
	public function previewImages (int $preview): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string|null $profile
	 */
	public function profileImage (string $name, ?string $profile = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $number_colors
	 * @param int $colorspace
	 * @param int $tree_depth
	 * @param bool $dither
	 * @param bool $measure_error
	 */
	public function quantizeImage (int $number_colors, int $colorspace, int $tree_depth, bool $dither, bool $measure_error): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $number_colors
	 * @param int $colorspace
	 * @param int $tree_depth
	 * @param bool $dither
	 * @param bool $measure_error
	 */
	public function quantizeImages (int $number_colors, int $colorspace, int $tree_depth, bool $dither, bool $measure_error): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function removeImageProfile (string $name): string {}

	/**
	 * {@inheritdoc}
	 * @param int $channel
	 */
	public function separateImageChannel (int $channel): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $threshold
	 */
	public function sepiaToneImage (float $threshold): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function setImageBluePrimary (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $border_color
	 */
	public function setImageBorderColor (ImagickPixel|string $border_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $channel
	 * @param int $depth
	 */
	public function setImageChannelDepth (int $channel, int $depth): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param ImagickPixel|string $color
	 */
	public function setImageColormapColor (int $index, ImagickPixel|string $color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $colorspace
	 */
	public function setImageColorspace (int $colorspace): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $dispose
	 */
	public function setImageDispose (int $dispose): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 */
	public function setImageExtent (int $columns, int $rows): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function setImageGreenPrimary (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $interlace
	 */
	public function setImageInterlaceScheme (int $interlace): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $profile
	 */
	public function setImageProfile (string $name, string $profile): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function setImageRedPrimary (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $rendering_intent
	 */
	public function setImageRenderingIntent (int $rendering_intent): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $method
	 */
	public function setImageVirtualPixelMethod (int $method): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function setImageWhitePoint (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $sharpen
	 * @param float $alpha
	 * @param float $beta
	 * @param int $channel [optional]
	 */
	public function sigmoidalContrastImage (bool $sharpen, float $alpha, float $beta, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $offset_image
	 */
	public function stereoImage (Imagick $offset_image): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $texture
	 */
	public function textureImage (Imagick $texture): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $tint_color
	 * @param ImagickPixel|string $opacity_color
	 * @param bool $legacy [optional]
	 */
	public function tintImage (ImagickPixel|string $tint_color, ImagickPixel|string $opacity_color, bool $legacy = false): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param float $amount
	 * @param float $threshold
	 * @param int $channel [optional]
	 */
	public function unsharpMaskImage (float $radius, float $sigma, float $amount, float $threshold, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getImage (): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $image
	 */
	public function addImage (Imagick $image): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $image
	 */
	public function setImage (Imagick $image): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 * @param ImagickPixel|string $background_color
	 * @param string $format [optional]
	 */
	public function newImage (int $columns, int $rows, ImagickPixel|string $background_color, string $format = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 * @param string $pseudo_format
	 */
	public function newPseudoImage (int $columns, int $rows, string $pseudo_format): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getCompression (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getCompressionQuality (): int {}

	/**
	 * {@inheritdoc}
	 */
	public static function getCopyright (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern [optional]
	 */
	public static function getConfigureOptions (string $pattern = '*'): array {}

	/**
	 * {@inheritdoc}
	 */
	public static function getFeatures (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFormat (): string {}

	/**
	 * {@inheritdoc}
	 */
	public static function getHomeURL (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterlaceScheme (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public function getOption (string $key): string {}

	/**
	 * {@inheritdoc}
	 */
	public static function getPackageName (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPage (): array {}

	/**
	 * {@inheritdoc}
	 */
	public static function getQuantum (): int {}

	/**
	 * {@inheritdoc}
	 */
	public static function getHdriEnabled (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public static function getQuantumDepth (): array {}

	/**
	 * {@inheritdoc}
	 */
	public static function getQuantumRange (): array {}

	/**
	 * {@inheritdoc}
	 */
	public static function getReleaseDate (): string {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public static function getResource (int $type): int {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 */
	public static function getResourceLimit (int $type): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getSamplingFactors (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize (): array {}

	/**
	 * {@inheritdoc}
	 */
	public static function getVersion (): array {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $background_color
	 */
	public function setBackgroundColor (ImagickPixel|string $background_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $compression
	 */
	public function setCompression (int $compression): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $quality
	 */
	public function setCompressionQuality (int $quality): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function setFilename (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $format
	 */
	public function setFormat (string $format): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $interlace
	 */
	public function setInterlaceScheme (int $interlace): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $value
	 */
	public function setOption (string $key, string $value): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $x
	 * @param int $y
	 */
	public function setPage (int $width, int $height, int $x, int $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 * @param int $limit
	 */
	public static function setResourceLimit (int $type, int $limit): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x_resolution
	 * @param float $y_resolution
	 */
	public function setResolution (float $x_resolution, float $y_resolution): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $factors
	 */
	public function setSamplingFactors (array $factors): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 */
	public function setSize (int $columns, int $rows): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $imgtype
	 */
	public function setType (int $imgtype): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function key (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function current (): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param float $brightness
	 * @param float $contrast
	 * @param int $channel [optional]
	 */
	public function brightnessContrastImage (float $brightness, float $contrast, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $color_matrix
	 */
	public function colorMatrixImage (array $color_matrix): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param float $threshold
	 * @param int $channel [optional]
	 */
	public function selectiveBlurImage (float $radius, float $sigma, float $threshold, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $angle
	 * @param int $channel [optional]
	 */
	public function rotationalBlurImage (float $angle, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $type
	 * @param int $width
	 * @param int $height
	 * @param int $channel [optional]
	 */
	public function statisticImage (int $type, int $width, int $height, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $image
	 * @param array|null $offset [optional]
	 * @param float|null $similarity [optional]
	 * @param float $threshold [optional]
	 * @param int $metric [optional]
	 */
	public function subimageMatch (Imagick $image, ?array &$offset = NULL, ?float &$similarity = NULL, float $threshold = 0.0, int $metric = 0): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $image
	 * @param array|null $offset [optional]
	 * @param float|null $similarity [optional]
	 * @param float $threshold [optional]
	 * @param int $metric [optional]
	 */
	public function similarityImage (Imagick $image, ?array &$offset = NULL, ?float &$similarity = NULL, float $threshold = 0.0, int $metric = 0): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $value
	 */
	public static function setRegistry (string $key, string $value): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	public static function getRegistry (string $key): string {}

	/**
	 * {@inheritdoc}
	 */
	public static function listRegistry (): array {}

	/**
	 * {@inheritdoc}
	 * @param int $morphology
	 * @param int $iterations
	 * @param ImagickKernel $kernel
	 * @param int $channel [optional]
	 */
	public function morphology (int $morphology, int $iterations, ImagickKernel $kernel, int $channel = 134217727): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $antialias
	 */
	public function setAntialias (bool $antialias): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getAntialias (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $color_correction_collection
	 */
	public function colorDecisionListImage (string $color_correction_collection): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function optimizeImageTransparency (): void {}

	/**
	 * {@inheritdoc}
	 * @param int|null $channel [optional]
	 */
	public function autoGammaImage (?int $channel = 134217727): void {}

	/**
	 * {@inheritdoc}
	 */
	public function autoOrient (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function autoOrientate (): void {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $image
	 * @param int $composite_constant
	 * @param int $gravity
	 */
	public function compositeImageGravity (Imagick $image, int $composite_constant, int $gravity): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $strength
	 */
	public function localContrastImage (float $radius, float $strength): void {}

	/**
	 * {@inheritdoc}
	 */
	public function identifyImageType (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $pixelmask
	 */
	public function getImageMask (int $pixelmask): ?Imagick {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $clip_mask
	 * @param int $pixelmask
	 */
	public function setImageMask (Imagick $clip_mask, int $pixelmask): void {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param float $lower_percent
	 * @param float $upper_percent
	 */
	public function cannyEdgeImage (float $radius, float $sigma, float $lower_percent, float $upper_percent): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $seed
	 */
	public static function setSeed (int $seed): void {}

	/**
	 * {@inheritdoc}
	 * @param float $threshold
	 * @param float $softness
	 */
	public function waveletDenoiseImage (float $threshold, float $softness): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param float $color_distance
	 */
	public function meanShiftImage (int $width, int $height, float $color_distance): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $number_colors
	 * @param int $max_iterations
	 * @param float $tolerance
	 */
	public function kmeansImage (int $number_colors, int $max_iterations, float $tolerance): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $low_black
	 * @param float $low_white
	 * @param float $high_white
	 * @param float $high_black
	 */
	public function rangeThresholdImage (float $low_black, float $low_white, float $high_white, float $high_black): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $auto_threshold_method
	 */
	public function autoThresholdImage (int $auto_threshold_method): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $radius
	 * @param float $sigma
	 * @param float $intensity_sigma
	 * @param float $spatial_sigma
	 */
	public function bilateralBlurImage (float $radius, float $sigma, float $intensity_sigma, float $spatial_sigma): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $width
	 * @param int $height
	 * @param int $number_bins
	 * @param float $clip_limit
	 */
	public function claheImage (int $width, int $height, int $number_bins, float $clip_limit): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $expression
	 */
	public function channelFxImage (string $expression): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $start_color
	 * @param ImagickPixel|string $stop_color
	 */
	public function colorThresholdImage (ImagickPixel|string $start_color, ImagickPixel|string $stop_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $complex_operator
	 */
	public function complexImages (int $complex_operator): Imagick {}

	/**
	 * {@inheritdoc}
	 * @param int $columns
	 * @param int $rows
	 * @param int $interpolate
	 */
	public function interpolativeResizeImage (int $columns, int $rows, int $interpolate): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $black_color
	 * @param ImagickPixel|string $white_color
	 * @param bool $invert
	 */
	public function levelImageColors (ImagickPixel|string $black_color, ImagickPixel|string $white_color, bool $invert): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $black_point
	 * @param float $gamma
	 * @param float $white_point
	 */
	public function levelizeImage (float $black_point, float $gamma, float $white_point): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $dither_format
	 */
	public function orderedDitherImage (string $dither_format): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function whiteBalanceImage (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $option
	 */
	public function deleteOption (string $option): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getBackgroundColor (): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern [optional]
	 */
	public function getImageArtifacts (string $pattern = '*'): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageKurtosis (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageMean (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getImageRange (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterpolateMethod (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern [optional]
	 */
	public function getOptions (string $pattern = '*'): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getOrientation (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getResolution (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getType (): int {}

	/**
	 * {@inheritdoc}
	 * @param array $terms
	 */
	public function polynomialImage (array $terms): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $depth
	 */
	public function setDepth (int $depth): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $geometry
	 */
	public function setExtract (string $geometry): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $method
	 */
	public function setInterpolateMethod (int $method): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $orientation
	 */
	public function setOrientation (int $orientation): bool {}

}

class ImagickDraw  {

	/**
	 * {@inheritdoc}
	 */
	public function resetVectorGraphics (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextKerning (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $kerning
	 */
	public function setTextKerning (float $kerning): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextInterwordSpacing (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $spacing
	 */
	public function setTextInterwordSpacing (float $spacing): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextInterlineSpacing (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $spacing
	 */
	public function setTextInterlineSpacing (float $spacing): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $fill_color
	 */
	public function setFillColor (ImagickPixel|string $fill_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $alpha
	 */
	public function setFillAlpha (float $alpha): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $resolution_x
	 * @param float $resolution_y
	 */
	public function setResolution (float $resolution_x, float $resolution_y): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $color
	 */
	public function setStrokeColor (ImagickPixel|string $color): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $alpha
	 */
	public function setStrokeAlpha (float $alpha): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $width
	 */
	public function setStrokeWidth (float $width): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clear (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $origin_x
	 * @param float $origin_y
	 * @param float $perimeter_x
	 * @param float $perimeter_y
	 */
	public function circle (float $origin_x, float $origin_y, float $perimeter_x, float $perimeter_y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 * @param string $text
	 */
	public function annotation (float $x, float $y, string $text): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $antialias
	 */
	public function setTextAntialias (bool $antialias): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $encoding
	 */
	public function setTextEncoding (string $encoding): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $font_name
	 */
	public function setFont (string $font_name): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $font_family
	 */
	public function setFontFamily (string $font_family): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $point_size
	 */
	public function setFontSize (float $point_size): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $style
	 */
	public function setFontStyle (int $style): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $weight
	 */
	public function setFontWeight (int $weight): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getFont (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFontFamily (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFontSize (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getFontStyle (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getFontWeight (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function destroy (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $top_left_x
	 * @param float $top_left_y
	 * @param float $bottom_right_x
	 * @param float $bottom_right_y
	 */
	public function rectangle (float $top_left_x, float $top_left_y, float $bottom_right_x, float $bottom_right_y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $top_left_x
	 * @param float $top_left_y
	 * @param float $bottom_right_x
	 * @param float $bottom_right_y
	 * @param float $rounding_x
	 * @param float $rounding_y
	 */
	public function roundRectangle (float $top_left_x, float $top_left_y, float $bottom_right_x, float $bottom_right_y, float $rounding_x, float $rounding_y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $origin_x
	 * @param float $origin_y
	 * @param float $radius_x
	 * @param float $radius_y
	 * @param float $angle_start
	 * @param float $angle_end
	 */
	public function ellipse (float $origin_x, float $origin_y, float $radius_x, float $radius_y, float $angle_start, float $angle_end): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $degrees
	 */
	public function skewX (float $degrees): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $degrees
	 */
	public function skewY (float $degrees): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function translate (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $start_x
	 * @param float $start_y
	 * @param float $end_x
	 * @param float $end_y
	 */
	public function line (float $start_x, float $start_y, float $end_x, float $end_y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $start_x
	 * @param float $start_y
	 * @param float $end_x
	 * @param float $end_y
	 * @param float $start_angle
	 * @param float $end_angle
	 */
	public function arc (float $start_x, float $start_y, float $end_x, float $end_y, float $start_angle, float $end_angle): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 * @param int $paint
	 */
	public function alpha (float $x, float $y, int $paint): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $coordinates
	 */
	public function polygon (array $coordinates): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function point (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextDecoration (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextEncoding (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFontStretch (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $stretch
	 */
	public function setFontStretch (int $stretch): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $enabled
	 */
	public function setStrokeAntialias (bool $enabled): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $align
	 */
	public function setTextAlignment (int $align): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $decoration
	 */
	public function setTextDecoration (int $decoration): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $under_color
	 */
	public function setTextUnderColor (ImagickPixel|string $under_color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $left_x
	 * @param int $top_y
	 * @param int $right_x
	 * @param int $bottom_y
	 */
	public function setViewbox (int $left_x, int $top_y, int $right_x, int $bottom_y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clone (): ImagickDraw {}

	/**
	 * {@inheritdoc}
	 * @param array $affine
	 */
	public function affine (array $affine): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $coordinates
	 */
	public function bezier (array $coordinates): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $composite
	 * @param float $x
	 * @param float $y
	 * @param float $width
	 * @param float $height
	 * @param Imagick $image
	 */
	public function composite (int $composite, float $x, float $y, float $width, float $height, Imagick $image): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 * @param int $paint
	 */
	public function color (float $x, float $y, int $paint): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $comment
	 */
	public function comment (string $comment): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getClipPath (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getClipRule (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getClipUnits (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getFillColor (): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 */
	public function getFillOpacity (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getFillRule (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getGravity (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeAntialias (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeColor (): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeDashArray (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeDashOffset (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeLineCap (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeLineJoin (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeMiterLimit (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeOpacity (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getStrokeWidth (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextAlignment (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextAntialias (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getVectorGraphics (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextUnderColor (): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 */
	public function pathClose (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x1
	 * @param float $y1
	 * @param float $x2
	 * @param float $y2
	 * @param float $x
	 * @param float $y
	 */
	public function pathCurveToAbsolute (float $x1, float $y1, float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x1
	 * @param float $y1
	 * @param float $x2
	 * @param float $y2
	 * @param float $x
	 * @param float $y
	 */
	public function pathCurveToRelative (float $x1, float $y1, float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x1
	 * @param float $y1
	 * @param float $x_end
	 * @param float $y
	 */
	public function pathCurveToQuadraticBezierAbsolute (float $x1, float $y1, float $x_end, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x1
	 * @param float $y1
	 * @param float $x_end
	 * @param float $y
	 */
	public function pathCurveToQuadraticBezierRelative (float $x1, float $y1, float $x_end, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function pathCurveToQuadraticBezierSmoothAbsolute (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function pathCurveToQuadraticBezierSmoothRelative (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x2
	 * @param float $y2
	 * @param float $x
	 * @param float $y
	 */
	public function pathCurveToSmoothAbsolute (float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x2
	 * @param float $y2
	 * @param float $x
	 * @param float $y
	 */
	public function pathCurveToSmoothRelative (float $x2, float $y2, float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $rx
	 * @param float $ry
	 * @param float $x_axis_rotation
	 * @param bool $large_arc
	 * @param bool $sweep
	 * @param float $x
	 * @param float $y
	 */
	public function pathEllipticArcAbsolute (float $rx, float $ry, float $x_axis_rotation, bool $large_arc, bool $sweep, float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $rx
	 * @param float $ry
	 * @param float $x_axis_rotation
	 * @param bool $large_arc
	 * @param bool $sweep
	 * @param float $x
	 * @param float $y
	 */
	public function pathEllipticArcRelative (float $rx, float $ry, float $x_axis_rotation, bool $large_arc, bool $sweep, float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function pathFinish (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function pathLineToAbsolute (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function pathLineToRelative (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 */
	public function pathLineToHorizontalAbsolute (float $x): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 */
	public function pathLineToHorizontalRelative (float $x): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $y
	 */
	public function pathLineToVerticalAbsolute (float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $y
	 */
	public function pathLineToVerticalRelative (float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function pathMoveToAbsolute (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function pathMoveToRelative (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function pathStart (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $coordinates
	 */
	public function polyline (array $coordinates): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function popClipPath (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function popDefs (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function popPattern (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $clip_mask_id
	 */
	public function pushClipPath (string $clip_mask_id): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function pushDefs (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern_id
	 * @param float $x
	 * @param float $y
	 * @param float $width
	 * @param float $height
	 */
	public function pushPattern (string $pattern_id, float $x, float $y, float $width, float $height): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function render (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $degrees
	 */
	public function rotate (float $degrees): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function scale (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $clip_mask
	 */
	public function setClipPath (string $clip_mask): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $fillrule
	 */
	public function setClipRule (int $fillrule): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $pathunits
	 */
	public function setClipUnits (int $pathunits): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $opacity
	 */
	public function setFillOpacity (float $opacity): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $fill_url
	 */
	public function setFillPatternUrl (string $fill_url): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $fillrule
	 */
	public function setFillRule (int $fillrule): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $gravity
	 */
	public function setGravity (int $gravity): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $stroke_url
	 */
	public function setStrokePatternUrl (string $stroke_url): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $dash_offset
	 */
	public function setStrokeDashOffset (float $dash_offset): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $linecap
	 */
	public function setStrokeLineCap (int $linecap): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $linejoin
	 */
	public function setStrokeLineJoin (int $linejoin): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $miterlimit
	 */
	public function setStrokeMiterLimit (int $miterlimit): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $opacity
	 */
	public function setStrokeOpacity (float $opacity): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $xml
	 */
	public function setVectorGraphics (string $xml): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function pop (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function push (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $dashes
	 */
	public function setStrokeDashArray (array $dashes): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getOpacity (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $opacity
	 */
	public function setOpacity (float $opacity): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getFontResolution (): array {}

	/**
	 * {@inheritdoc}
	 * @param float $x
	 * @param float $y
	 */
	public function setFontResolution (float $x, float $y): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getBorderColor (): ImagickPixel {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $color
	 */
	public function setBorderColor (ImagickPixel|string $color): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $density
	 */
	public function setDensity (string $density): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getDensity (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getTextDirection (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $direction
	 */
	public function setTextDirection (int $direction): bool {}

}

class ImagickPixelIterator implements Iterator, Traversable {

	/**
	 * {@inheritdoc}
	 * @param Imagick $imagick
	 */
	public function __construct (Imagick $imagick) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear (): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $imagick
	 */
	public static function getPixelIterator (Imagick $imagick): ImagickPixelIterator {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $imagick
	 * @param int $x
	 * @param int $y
	 * @param int $columns
	 * @param int $rows
	 */
	public static function getPixelRegionIterator (Imagick $imagick, int $x, int $y, int $columns, int $rows): ImagickPixelIterator {}

	/**
	 * {@inheritdoc}
	 */
	public function destroy (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getCurrentIteratorRow (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getIteratorRow (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getNextIteratorRow (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getPreviousIteratorRow (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function key (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function current (): array {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $imagick
	 */
	public function newPixelIterator (Imagick $imagick): bool {}

	/**
	 * {@inheritdoc}
	 * @param Imagick $imagick
	 * @param int $x
	 * @param int $y
	 * @param int $columns
	 * @param int $rows
	 */
	public function newPixelRegionIterator (Imagick $imagick, int $x, int $y, int $columns, int $rows): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function resetIterator (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function setIteratorFirstRow (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function setIteratorLastRow (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $row
	 */
	public function setIteratorRow (int $row): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function syncIterator (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function valid (): bool {}

}

class ImagickPixel  {

	/**
	 * {@inheritdoc}
	 * @param string|null $color [optional]
	 */
	public function __construct (?string $color = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function destroy (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $normalized [optional]
	 */
	public function getColor (int $normalized = 0): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getColorAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getColorCount (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getColorQuantum (): array {}

	/**
	 * {@inheritdoc}
	 * @param int $color
	 */
	public function getColorValue (int $color): float {}

	/**
	 * {@inheritdoc}
	 * @param int $color
	 */
	public function getColorValueQuantum (int $color): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getHSL (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getIndex (): int {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $color
	 * @param float $fuzz
	 */
	public function isPixelSimilar (ImagickPixel|string $color, float $fuzz): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $color
	 * @param float $fuzz_quantum_range_scaled_by_square_root_of_three
	 */
	public function isPixelSimilarQuantum (ImagickPixel|string $color, float $fuzz_quantum_range_scaled_by_square_root_of_three): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel|string $color
	 * @param float $fuzz_quantum_range_scaled_by_square_root_of_three
	 */
	public function isSimilar (ImagickPixel|string $color, float $fuzz_quantum_range_scaled_by_square_root_of_three): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $color
	 */
	public function setColor (string $color): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $color_count
	 */
	public function setColorCount (int $color_count): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $color
	 * @param float $value
	 */
	public function setColorValue (int $color, float $value): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $color
	 * @param float $value
	 */
	public function setColorValueQuantum (int $color, float $value): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $hue
	 * @param float $saturation
	 * @param float $luminosity
	 */
	public function setHSL (float $hue, float $saturation, float $luminosity): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $index
	 */
	public function setIndex (float $index): bool {}

	/**
	 * {@inheritdoc}
	 * @param ImagickPixel $pixel
	 */
	public function setColorFromPixel (ImagickPixel $pixel): bool {}

}

class ImagickKernel  {

	/**
	 * {@inheritdoc}
	 * @param ImagickKernel $kernel
	 */
	public function addKernel (ImagickKernel $kernel): void {}

	/**
	 * {@inheritdoc}
	 * @param float $scale
	 */
	public function addUnityKernel (float $scale): void {}

	/**
	 * {@inheritdoc}
	 * @param int $kernel
	 * @param string $shape
	 */
	public static function fromBuiltin (int $kernel, string $shape): ImagickKernel {}

	/**
	 * {@inheritdoc}
	 * @param array $matrix
	 * @param array|null $origin
	 */
	public static function fromMatrix (array $matrix, ?array $origin = null): ImagickKernel {}

	/**
	 * {@inheritdoc}
	 */
	public function getMatrix (): array {}

	/**
	 * {@inheritdoc}
	 * @param float $scale
	 * @param int|null $normalize_kernel [optional]
	 */
	public function scale (float $scale, ?int $normalize_kernel = NULL): void {}

	/**
	 * {@inheritdoc}
	 */
	public function separate (): array {}

}
// End of imagick v.3.7.0
