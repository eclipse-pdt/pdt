<?php

// Start of xlswriter v.1.5.4

namespace Vtiful\Kernel {

class Exception extends \Exception implements \Throwable, \Stringable {
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
 * Create xlsx files and set cells and output xlsx files
 * @link http://www.php.net/manual/en/class.vtiful-kernel-excel.php
 */
class Excel  {
	const SKIP_NONE = 0;
	const SKIP_EMPTY_ROW = 1;
	const SKIP_HIDDEN_ROW = 8;
	const SKIP_EMPTY_CELLS = 2;
	const SKIP_EMPTY_VALUE = 256;
	const GRIDLINES_HIDE_ALL = 0;
	const GRIDLINES_SHOW_ALL = 3;
	const GRIDLINES_SHOW_PRINT = 2;
	const GRIDLINES_SHOW_SCREEN = 1;
	const PAPER_DEFAULT = 0;
	const PAPER_LETTER = 1;
	const PAPER_LETTER_SMALL = 2;
	const PAPER_TABLOID = 3;
	const PAPER_LEDGER = 4;
	const PAPER_LEGAL = 5;
	const PAPER_STATEMENT = 6;
	const PAPER_EXECUTIVE = 7;
	const PAPER_A3 = 8;
	const PAPER_A4 = 9;
	const PAPER_A4_SMALL = 10;
	const PAPER_A5 = 11;
	const PAPER_B4 = 12;
	const PAPER_B5 = 13;
	const PAPER_FOLIO = 14;
	const PAPER_QUARTO = 15;
	const PAPER_NOTE = 18;
	const PAPER_ENVELOPE_9 = 19;
	const PAPER_ENVELOPE_10 = 20;
	const PAPER_ENVELOPE_11 = 21;
	const PAPER_ENVELOPE_12 = 22;
	const PAPER_ENVELOPE_14 = 23;
	const PAPER_C_SIZE_SHEET = 24;
	const PAPER_D_SIZE_SHEET = 25;
	const PAPER_E_SIZE_SHEET = 26;
	const PAPER_ENVELOPE_DL = 27;
	const PAPER_ENVELOPE_C3 = 28;
	const PAPER_ENVELOPE_C4 = 29;
	const PAPER_ENVELOPE_C5 = 30;
	const PAPER_ENVELOPE_C6 = 31;
	const PAPER_ENVELOPE_C65 = 32;
	const PAPER_ENVELOPE_B4 = 33;
	const PAPER_ENVELOPE_B5 = 34;
	const PAPER_ENVELOPE_B6 = 35;
	const PAPER_ENVELOPE_1 = 36;
	const PAPER_MONARCH = 37;
	const PAPER_ENVELOPE_2 = 38;
	const PAPER_FANFOLD = 39;
	const PAPER_GERMAN_STD_FANFOLD = 40;
	const PAPER_GERMAN_LEGAL_FANFOLD = 41;
	const TYPE_INT = 2;
	const TYPE_DOUBLE = 4;
	const TYPE_STRING = 1;
	const TYPE_TIMESTAMP = 8;

	private $config;
	private $fileName;
	private $read_row_type;


	/**
	 * Vtiful\Kernel\Excel constructor
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.construct.php
	 * @param mixed $config
	 */
	public function __construct ($config = null) {}

	public function close () {}

	/**
	 * Vtiful\Kernel\Excel fileName
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.filename.php
	 * @param mixed $file_name
	 * @param mixed $sheet_name [optional]
	 */
	public function fileName ($file_name = null, $sheet_name = null) {}

	/**
	 * Vtiful\Kernel\Excel addSheet
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.addSheet.php
	 * @param mixed $sheet_name
	 */
	public function addSheet ($sheet_name = null) {}

	/**
	 * @param mixed $sheet_name
	 */
	public function existSheet ($sheet_name = null) {}

	/**
	 * @param mixed $sheet_name
	 */
	public function checkoutSheet ($sheet_name = null) {}

	/**
	 * @param mixed $sheet_name
	 */
	public function activateSheet ($sheet_name = null) {}

	/**
	 * Vtiful\Kernel\Excel constMemory
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.constMemory.php
	 * @param mixed $file_name
	 * @param mixed $sheet_name [optional]
	 * @param mixed $use_zip64 [optional]
	 */
	public function constMemory ($file_name = null, $sheet_name = null, $use_zip64 = null) {}

	/**
	 * Vtiful\Kernel\Excel header
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.header.php
	 * @param mixed $header
	 * @param mixed $format_handle [optional]
	 */
	public function header ($header = null, $format_handle = null) {}

	/**
	 * Vtiful\Kernel\Excel data
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.data.php
	 * @param mixed $data
	 */
	public function data ($data = null) {}

	/**
	 * Vtiful\Kernel\Excel output
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.output.php
	 */
	public function output () {}

	/**
	 * Vtiful\Kernel\Excel getHandle
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.getHandle.php
	 */
	public function getHandle () {}

	/**
	 * Vtiful\Kernel\Excel autoFilter
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.autoFilter.php
	 * @param mixed $range
	 */
	public function autoFilter ($range = null) {}

	/**
	 * Vtiful\Kernel\Excel insertText
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.insertText.php
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $data
	 * @param mixed $format [optional]
	 * @param mixed $format_handle [optional]
	 */
	public function insertText ($row = null, $column = null, $data = null, $format = null, $format_handle = null) {}

	/**
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $rich_strings
	 * @param mixed $format_handle [optional]
	 */
	public function insertRichText ($row = null, $column = null, $rich_strings = null, $format_handle = null) {}

	/**
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $timestamp
	 * @param mixed $format [optional]
	 * @param mixed $format_handle [optional]
	 */
	public function insertDate ($row = null, $column = null, $timestamp = null, $format = null, $format_handle = null) {}

	/**
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $chart_resource
	 */
	public function insertChart ($row = null, $column = null, $chart_resource = null) {}

	/**
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $url
	 * @param mixed $text [optional]
	 * @param mixed $tool_tip [optional]
	 * @param mixed $format [optional]
	 */
	public function insertUrl ($row = null, $column = null, $url = null, $text = null, $tool_tip = null, $format = null) {}

	/**
	 * Vtiful\Kernel\Excel insertImage
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.insertImage.php
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $image
	 * @param mixed $width [optional]
	 * @param mixed $height [optional]
	 */
	public function insertImage ($row = null, $column = null, $image = null, $width = null, $height = null) {}

	/**
	 * Vtiful\Kernel\Excel insertFormula
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.insertFormula.php
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $formula
	 * @param mixed $format_handle [optional]
	 */
	public function insertFormula ($row = null, $column = null, $formula = null, $format_handle = null) {}

	/**
	 * @param mixed $row
	 * @param mixed $column
	 * @param mixed $comment
	 */
	public function insertComment ($row = null, $column = null, $comment = null) {}

	public function showComment () {}

	/**
	 * Vtiful\Kernel\Excel mergeCells
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.mergeCells.php
	 * @param mixed $range
	 * @param mixed $data
	 * @param mixed $format_handle [optional]
	 */
	public function mergeCells ($range = null, $data = null, $format_handle = null) {}

	/**
	 * Vtiful\Kernel\Excel setColumn
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.setColumn.php
	 * @param mixed $format_handle
	 * @param mixed $range
	 * @param mixed $width
	 */
	public function setColumn ($format_handle = null, $range = null, $width = null) {}

	/**
	 * Vtiful\Kernel\Excel setRow
	 * @link http://www.php.net/manual/en/vtiful-kernel-excel.setRow.php
	 * @param mixed $format_handle
	 * @param mixed $range
	 * @param mixed $height
	 */
	public function setRow ($format_handle = null, $range = null, $height = null) {}

	public function getCurrentLine () {}

	/**
	 * @param mixed $row
	 */
	public function setCurrentLine ($row = null) {}

	/**
	 * @param mixed $format_handle
	 */
	public function defaultFormat ($format_handle = null) {}

	/**
	 * @param mixed $row
	 * @param mixed $column
	 */
	public function freezePanes ($row = null, $column = null) {}

	/**
	 * @param mixed $password [optional]
	 */
	public function protection ($password = null) {}

	/**
	 * @param mixed $range
	 * @param mixed $validation_resource
	 */
	public function validation ($range = null, $validation_resource = null) {}

	/**
	 * @param mixed $scale
	 */
	public function zoom ($scale = null) {}

	/**
	 * @param mixed $option
	 */
	public function gridline ($option = null) {}

	/**
	 * @param mixed $paper
	 */
	public function setPaper ($paper = null) {}

	/**
	 * @param mixed $left
	 * @param mixed $right
	 * @param mixed $top
	 * @param mixed $bottom
	 */
	public function setMargins ($left = null, $right = null, $top = null, $bottom = null) {}

	public function setPortrait () {}

	public function setLandscape () {}

	/**
	 * @param mixed $scale [optional]
	 */
	public function setPrintScale ($scale = null) {}

	public function setCurrentSheetHide () {}

	public function setCurrentSheetIsFirst () {}

	/**
	 * @param mixed $index
	 */
	public static function columnIndexFromString ($index = null) {}

	/**
	 * @param mixed $index
	 */
	public static function stringFromColumnIndex ($index = null) {}

	/**
	 * @param mixed $index
	 */
	public static function timestampFromDateDouble ($index = null) {}

	/**
	 * @param mixed $zs_file_name
	 */
	public function openFile ($zs_file_name = null) {}

	/**
	 * @param mixed $zs_sheet_name [optional]
	 * @param mixed $zl_flag [optional]
	 */
	public function openSheet ($zs_sheet_name = null, $zl_flag = null) {}

	/**
	 * @param mixed $fp
	 * @param mixed $delimiter_str [optional]
	 * @param mixed $enclosure_str [optional]
	 * @param mixed $escape_str [optional]
	 */
	public function putCSV ($fp = null, $delimiter_str = null, $enclosure_str = null, $escape_str = null) {}

	/**
	 * @param mixed $callback
	 * @param mixed $fp
	 * @param mixed $delimiter_str [optional]
	 * @param mixed $enclosure_str [optional]
	 * @param mixed $escape_str [optional]
	 */
	public function putCSVCallback ($callback = null, $fp = null, $delimiter_str = null, $enclosure_str = null, $escape_str = null) {}

	public function sheetList () {}

	/**
	 * @param mixed $zv_type_t
	 */
	public function setType ($zv_type_t = null) {}

	/**
	 * @param mixed $zv_type_t
	 */
	public function setGlobalType ($zv_type_t = null) {}

	/**
	 * @param mixed $zv_skip_t
	 */
	public function setSkipRows ($zv_skip_t = null) {}

	public function getSheetData () {}

	/**
	 * @param mixed $zv_type_t [optional]
	 */
	public function nextRow ($zv_type_t = null) {}

	/**
	 * @param mixed $fci
	 * @param mixed $sheet_name [optional]
	 */
	public function nextCellCallback ($fci = null, $sheet_name = null) {}

}

/**
 * Create a cell format object
 * @link http://www.php.net/manual/en/class.vtiful-kernel-format.php
 */
class Format  {
	const UNDERLINE_SINGLE = 1;
	const UNDERLINE_DOUBLE  = 2;
	const UNDERLINE_SINGLE_ACCOUNTING = 3;
	const UNDERLINE_DOUBLE_ACCOUNTING = 4;
	const FORMAT_ALIGN_LEFT = 1;
	const FORMAT_ALIGN_CENTER = 2;
	const FORMAT_ALIGN_RIGHT = 3;
	const FORMAT_ALIGN_FILL = 4;
	const FORMAT_ALIGN_JUSTIFY = 5;
	const FORMAT_ALIGN_CENTER_ACROSS = 6;
	const FORMAT_ALIGN_DISTRIBUTED = 7;
	const FORMAT_ALIGN_VERTICAL_TOP = 8;
	const FORMAT_ALIGN_VERTICAL_BOTTOM = 9;
	const FORMAT_ALIGN_VERTICAL_CENTER = 10;
	const FORMAT_ALIGN_VERTICAL_JUSTIFY = 11;
	const FORMAT_ALIGN_VERTICAL_DISTRIBUTED = 12;
	const COLOR_BLACK = 16777216;
	const COLOR_BLUE = 255;
	const COLOR_BROWN = 8388608;
	const COLOR_CYAN = 65535;
	const COLOR_GRAY = 8421504;
	const COLOR_GREEN = 32768;
	const COLOR_LIME = 65280;
	const COLOR_MAGENTA = 16711935;
	const COLOR_NAVY = 128;
	const COLOR_ORANGE = 16737792;
	const COLOR_PINK = 16711935;
	const COLOR_PURPLE = 8388736;
	const COLOR_RED = 16711680;
	const COLOR_SILVER = 12632256;
	const COLOR_WHITE = 16777215;
	const COLOR_YELLOW = 16776960;
	const PATTERN_NONE = 0;
	const PATTERN_SOLID = 1;
	const PATTERN_MEDIUM_GRAY = 2;
	const PATTERN_DARK_GRAY = 3;
	const PATTERN_LIGHT_GRAY = 4;
	const PATTERN_DARK_HORIZONTAL = 5;
	const PATTERN_DARK_VERTICAL = 6;
	const PATTERN_DARK_DOWN = 7;
	const PATTERN_DARK_UP = 8;
	const PATTERN_DARK_GRID = 9;
	const PATTERN_DARK_TRELLIS = 10;
	const PATTERN_LIGHT_HORIZONTAL = 11;
	const PATTERN_LIGHT_VERTICAL = 12;
	const PATTERN_LIGHT_DOWN = 13;
	const PATTERN_LIGHT_UP = 14;
	const PATTERN_LIGHT_GRID = 15;
	const PATTERN_LIGHT_TRELLIS = 16;
	const PATTERN_GRAY_125 = 17;
	const PATTERN_GRAY_0625 = 18;
	const BORDER_NONE = 0;
	const BORDER_THIN = 1;
	const BORDER_MEDIUM = 2;
	const BORDER_DASHED = 3;
	const BORDER_DOTTED = 4;
	const BORDER_THICK = 5;
	const BORDER_DOUBLE = 6;
	const BORDER_HAIR = 7;
	const BORDER_MEDIUM_DASHED = 8;
	const BORDER_DASH_DOT = 9;
	const BORDER_MEDIUM_DASH_DOT = 10;
	const BORDER_DASH_DOT_DOT = 11;
	const BORDER_MEDIUM_DASH_DOT_DOT = 12;
	const BORDER_SLANT_DASH_DOT = 13;


	/**
	 * @param mixed $handle
	 */
	public function __construct ($handle = null) {}

	public function wrap () {}

	/**
	 * Vtiful\Kernel\Format bold
	 * @link http://www.php.net/manual/en/vtiful-kernel-format.bold.php
	 */
	public function bold () {}

	/**
	 * Vtiful\Kernel\Format italic
	 * @link http://www.php.net/manual/en/vtiful-kernel-format.italic.php
	 */
	public function italic () {}

	/**
	 * @param mixed $style
	 */
	public function border ($style = null) {}

	/**
	 * @param mixed $top
	 * @param mixed $right
	 * @param mixed $bottom
	 * @param mixed $left
	 */
	public function borderOfTheFourSides ($top = null, $right = null, $bottom = null, $left = null) {}

	/**
	 * @param mixed $color
	 */
	public function borderColor ($color = null) {}

	/**
	 * @param mixed $top_color
	 * @param mixed $right_color
	 * @param mixed $bottom_color
	 * @param mixed $left_color
	 */
	public function borderColorOfTheFourSides ($top_color = null, $right_color = null, $bottom_color = null, $left_color = null) {}

	/**
	 * Vtiful\Kernel\Format align
	 * @link http://www.php.net/manual/en/vtiful-kernel-format.align.php
	 * @param mixed $style
	 */
	public function align (...$style) {}

	/**
	 * @param mixed $format
	 */
	public function number ($format = null) {}

	/**
	 * @param mixed $color
	 */
	public function fontColor ($color = null) {}

	/**
	 * @param mixed $font
	 */
	public function font ($font = null) {}

	/**
	 * @param mixed $size
	 */
	public function fontSize ($size = null) {}

	public function strikeout () {}

	/**
	 * Vtiful\Kernel\Format underline
	 * @link http://www.php.net/manual/en/vtiful-kernel-format.underline.php
	 * @param mixed $style
	 */
	public function underline ($style = null) {}

	public function unlocked () {}

	public function toResource () {}

	/**
	 * @param mixed $pattern
	 * @param mixed $color
	 */
	public function background ($pattern = null, $color = null) {}

}

class Chart  {
	const CHART_BAR = 4;
	const CHART_BAR_STACKED = 5;
	const CHART_BAR_STACKED_PERCENT = 6;
	const CHART_AREA = 1;
	const CHART_AREA_STACKED = 2;
	const CHART_AREA_STACKED_PERCENT = 3;
	const CHART_LINE = 11;
	const CHART_COLUMN = 7;
	const CHART_COLUMN_STACKED = 8;
	const CHART_COLUMN_STACKED_PERCENT = 9;
	const CHART_DOUGHNUT = 10;
	const CHART_PIE = 14;
	const CHART_SCATTER = 15;
	const CHART_SCATTER_STRAIGHT = 16;
	const CHART_SCATTER_STRAIGHT_WITH_MARKERS = 17;
	const CHART_SCATTER_SMOOTH = 18;
	const CHART_SCATTER_SMOOTH_WITH_MARKERS = 19;
	const CHART_RADAR = 20;
	const CHART_RADAR_WITH_MARKERS = 21;
	const CHART_RADAR_FILLED = 22;
	const CHART_LEGEND_NONE = 0;
	const CHART_LEGEND_RIGHT = 1;
	const CHART_LEGEND_LEFT = 2;
	const CHART_LEGEND_TOP = 3;
	const CHART_LEGEND_BOTTOM = 4;
	const CHART_LEGEND_OVERLAY_RIGHT = 6;
	const CHART_LEGEND_OVERLAY_LEFT = 7;
	const CHART_LINE_STACKED = 12;
	const CHART_LINE_STACKED_PERCENT = 13;


	/**
	 * @param mixed $handle
	 * @param mixed $type
	 */
	public function __construct ($handle = null, $type = null) {}

	/**
	 * @param mixed $value
	 * @param mixed $categories [optional]
	 */
	public function series ($value = null, $categories = null) {}

	/**
	 * @param mixed $value
	 */
	public function seriesName ($value = null) {}

	/**
	 * @param mixed $style
	 */
	public function style ($style = null) {}

	/**
	 * @param mixed $name
	 */
	public function axisNameY ($name = null) {}

	/**
	 * @param mixed $name
	 */
	public function axisNameX ($name = null) {}

	/**
	 * @param mixed $title
	 */
	public function title ($title = null) {}

	/**
	 * @param mixed $type
	 */
	public function legendSetPosition ($type = null) {}

	public function toResource () {}

}

class Validation  {
	const TYPE_INTEGER = 1;
	const TYPE_INTEGER_FORMULA = 2;
	const TYPE_DECIMAL = 3;
	const TYPE_DECIMAL_FORMULA = 4;
	const TYPE_LIST = 5;
	const TYPE_LIST_FORMULA = 6;
	const TYPE_DATE = 7;
	const TYPE_DATE_FORMULA = 8;
	const TYPE_DATE_NUMBER = 9;
	const TYPE_TIME = 10;
	const TYPE_TIME_FORMULA = 11;
	const TYPE_TIME_NUMBER = 12;
	const TYPE_LENGTH = 13;
	const TYPE_LENGTH_FORMULA = 14;
	const TYPE_CUSTOM_FORMULA = 15;
	const TYPE_ANY = 16;
	const CRITERIA_BETWEEN = 1;
	const CRITERIA_NOT_BETWEEN = 2;
	const CRITERIA_EQUAL_TO = 3;
	const CRITERIA_NOT_EQUAL_TO = 4;
	const CRITERIA_GREATER_THAN = 5;
	const CRITERIA_LESS_THAN = 6;
	const CRITERIA_GREATER_THAN_OR_EQUAL_TO = 7;
	const CRITERIA_LESS_THAN_OR_EQUAL_TO = 8;
	const ERROR_TYPE_STOP = 0;
	const ERROR_TYPE_WARNING = 1;
	const ERROR_TYPE_INFORMATION = 2;


	public function __construct () {}

	/**
	 * @param mixed $type
	 */
	public function validationType ($type = null) {}

	/**
	 * @param mixed $type
	 */
	public function criteriaType ($type = null) {}

	/**
	 * @param mixed $ignore_blank
	 */
	public function ignoreBlank ($ignore_blank = null) {}

	/**
	 * @param mixed $show_input
	 */
	public function showInput ($show_input = null) {}

	/**
	 * @param mixed $show_error
	 */
	public function showError ($show_error = null) {}

	/**
	 * @param mixed $error_type
	 */
	public function errorType ($error_type = null) {}

	/**
	 * @param mixed $dropdown
	 */
	public function dropdown ($dropdown = null) {}

	/**
	 * @param mixed $value_number
	 */
	public function valueNumber ($value_number = null) {}

	/**
	 * @param mixed $value_formula
	 */
	public function valueFormula ($value_formula = null) {}

	/**
	 * @param mixed $value_list
	 */
	public function valueList ($value_list = null) {}

	/**
	 * @param mixed $timestamp
	 */
	public function valueDatetime ($timestamp = null) {}

	/**
	 * @param mixed $minimum_number
	 */
	public function minimumNumber ($minimum_number = null) {}

	/**
	 * @param mixed $minimum_formula
	 */
	public function minimumFormula ($minimum_formula = null) {}

	/**
	 * @param mixed $timestamp
	 */
	public function minimumDatetime ($timestamp = null) {}

	/**
	 * @param mixed $maximum_number
	 */
	public function maximumNumber ($maximum_number = null) {}

	/**
	 * @param mixed $maximum_formula
	 */
	public function maximumFormula ($maximum_formula = null) {}

	/**
	 * @param mixed $maximum_datetime
	 */
	public function maximumDatetime ($maximum_datetime = null) {}

	/**
	 * @param mixed $input_title
	 */
	public function inputTitle ($input_title = null) {}

	/**
	 * @param mixed $input_message
	 */
	public function inputMessage ($input_message = null) {}

	/**
	 * @param mixed $error_titile
	 */
	public function errorTitle ($error_titile = null) {}

	/**
	 * @param mixed $error_message
	 */
	public function errorMessage ($error_message = null) {}

	public function toResource () {}

}

class RichString  {

	/**
	 * @param mixed $text
	 * @param mixed $format_handle [optional]
	 */
	public function __construct ($text = null, $format_handle = null) {}

}


}


namespace {

function xlswriter_get_version () {}

function xlswriter_get_author () {}


}

// End of xlswriter v.1.5.4
