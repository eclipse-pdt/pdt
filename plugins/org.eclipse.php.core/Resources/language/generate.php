<?php
/**
 * This script can be used for generating PHP model for PDT.
 * It builds PHP functions according to the loaded extensions in running PHP,
 * using complementary information gathered from PHP.net documentation
 *
 * <b>This script was formatted using PDT's PSR-2 built-in profile.</b>
 *
 * @author Michael Spector <michael@zend.com>
 * @author Thierry Blind <thierryblind@msn.com>
 * @author Dawid Pakuła <zulus@w3des.net>
 */
if (version_compare(phpversion(), "5.0.0") < 0) {
    die("This script requires PHP 5.0.0 or higher!\n");
}

define('DOCBOOK_NS', 'http://docbook.org/ns/docbook');
define('PHPDOC_NS', 'http://php.net/ns/phpdoc');
$typeTransforms = [
    'GdFont' => [
        'since' => '8.1.0',
        'previous' => 'int',
        'phpdoc' => false
    ],
    'IMAP\Connection' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'LDAP\Connection' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'LDAP\ResultEntry' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'LDAP\Result' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'PgSql\Connection' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'PgSql\Result' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'PgSql\Lob' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],

    'PSpell\Dictionary' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'PSpell\Config' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'FTP\Connection' => [
        'since' => '8.1.0',
        'previous' => 'resource',
        'phpdoc' => false
    ],
    'true' => [
        'since' => '8.2.0',
        'previous' => 'int',
        'phpdoc' => true
    ],
    'false' => [
        'since' => '8.2.0',
        'previous' => 'int',
        'phpdoc' => true
    ],
    'never' => [
        'since' => '8.1.0',
        'previous' => 'mixed',
        'phpdoc' => true
    ],
    'resource' => [
        'since' => null,
        'phpdoc' => true,
        'previous' => null
    ],
    'MagickWand' => [
        'since' => null,
        'phpdoc' => true,
        'previous' => null
    ],
    'integer' => [
        'since' => null,
        'phpdoc' => true,
        'previous' => null
    ]

];
/**
 * Hack to rewrite some PHPDoc return type(s) for a given function or method.
 * Necessary to overcome some limitations from the official PHP documentation.
 *
 * @param $funckey string
 *            function key created by make_funckey_from_ref($ref)
 * @param $returnTypes string
 *            current PHPDoc type(s)
 * @return string new PHPDoc type(s)
 * @see make_funckey_from_ref($ref)
 */
function rewrite_phpdoc_return_types($funckey, $returnTypes)
{
    if ($funckey == 'mysqli::query') {
        if ($returnTypes == 'mixed') {
            $returnTypes = 'mysqli_result|bool';
        }
    }
    if ($funckey == 'domxpath::query') {
        if ($returnTypes == 'DOMNodeList') {
            $returnTypes = 'DOMNodeList|DOMNode[]';
        }
    }
    return $returnTypes;
}

if (! function_exists('enum_exists')) {

    function enum_exists()
    {
        return false;
    }
}

$splitFiles = true;
$phpdocDir = null;
$phpDir = null;

preg_match('/^[^.]+\.[^.]+/', phpversion(), $matches);
echo "PHP version: {$matches[0]}\n";

// Parse arguments:
$argv = $_SERVER["argv"];
$argv0 = array_shift($argv);
for ($i = 0; $i < count($argv); ++ $i) {
    switch ($argv[$i]) {
        case "-nosplit":
            $splitFiles = false;
            break;

        case "-help":
            show_help();
            break;

        default:
            if ($phpdocDir === null) {
                $phpdocDir = $argv[$i];
            } elseif ($phpDir === null) {
                $phpDir = $argv[$i];
            } else {
                show_help();
            }
            break;
    }
}

if ((string) $phpdocDir === '' || ! is_file($phpdocDir)) {
    show_help();
}
if ((string) $phpDir === '') {
    $phpDir = dirname(__FILE__) . "/php" . $matches[0];
}

$manualXML = new DOMDocument();
$manualXML->load($phpdocDir);

$functionsDoc = parse_phpdoc_functions($manualXML);
$classesDoc = parse_phpdoc_classes($manualXML);
$constantsDoc = parse_phpdoc_constants($manualXML);

$processedFunctions = array();
$processedClasses = array();
$processedConstants = array();

$handleNamespaces = version_compare(phpversion(), "5.3.0") >= 0;
$currentNamespace = '';
$addGlobalNSPrefix = '';
$countNamespacesInCurrentFile = 0;

if (! is_dir($phpDir)) {
    if (! mkdir($phpDir)) {
        echo "Failed to create output directory.";
        exit(1);
    }
}

if (! $splitFiles) {
    begin_file_output();
    open_namespace('');
}
$extensions = get_loaded_extensions();
foreach ($extensions as $extName) {
    if ($splitFiles) {
        begin_file_output();
    }
    print_extension(new ReflectionExtension($extName));
    if ($splitFiles) {
        finish_file_output("{$phpDir}/{$extName}.php");
    }
}

if ($splitFiles) {
    begin_file_output();
    open_namespace('');
} elseif ($currentNamespace !== '') {
    close_namespace();
    open_namespace('');
}
$intFunctions = get_defined_functions();
foreach ($intFunctions["internal"] as $intFunction) {
    $intFunctionLower = strtolower($intFunction);
    if (! isset($intFunctionLower) || ! $processedFunctions[$intFunctionLower]) {
        print_function(new ReflectionFunction($intFunction));
    }
}

$intClasses = array_merge(get_declared_classes(), get_declared_interfaces());
foreach ($intClasses as $intClass) {
    $intClassLower = strtolower($intClass);
    if (! isset($processedClasses[$intClassLower]) || ! $processedClasses[$intClassLower]) {
        if (version_compare(phpversion(), "8.1.0") >= 0 && enum_exists($intClassLower)) {
            print_class(new ReflectionEnum($intClass));
        } else {
            print_class(new ReflectionClass($intClass));
        }
    }
}
if (version_compare(phpversion(), "5.4.0") >= 0) {
    $intTraits = array_merge(get_declared_traits());
    foreach ($intTraits as $intTrait) {
        $intTraitLower = strtolower($intTrait);
        if (! isset($processedClasses[$intTraitLower]) || ! $processedClasses[$intTraitLower]) {
            print_class(new ReflectionClass($intTrait));
        }
    }
}

print "\n";
// put all constants in global namespace
if ($currentNamespace !== '') {
    close_namespace();
    open_namespace('');
}

$constants = get_defined_constants(true);
$intConstants = isset($constants["internal"]) ? $constants["internal"] : array();
// add magic constants:
$intConstants['__FILE__'] = null;
$intConstants['__LINE__'] = null;
$intConstants['__CLASS__'] = null;
$intConstants['__FUNCTION__'] = null;
$intConstants['__METHOD__'] = null;
if (version_compare(phpversion(), "5.3.0") >= 0) {
    $intConstants['__DIR__'] = null;
    $intConstants['__NAMESPACE__'] = null;
}
if (version_compare(phpversion(), "5.4.0") >= 0) {
    $intConstants['__TRAIT__'] = null;
}
foreach ($intConstants as $name => $value) {
    if (! isset($processedConstants[$name]) || ! $processedConstants[$name]) {
        print_constant($name, $value);
    }
}

close_namespace($countNamespacesInCurrentFile == 1);
finish_file_output("{$phpDir}/basic.php");

// Create .list file
$fp = fopen("{$phpDir}/.list", "w");
foreach (glob("{$phpDir}/*.php") as $f) {
    fwrite($fp, basename($f));
    fwrite($fp, "\n");
}
fclose($fp);

echo 'Finished...';

// === Functions ===
/**
 * Makes generic key from given function name
 *
 * @param $name string
 *            Function name
 * @return string generic key
 */
function make_funckey_from_str($name)
{
    $name = str_replace("->", "::", $name);
    $name = str_replace("()", "", $name);
    $name = strtolower($name);
    return $name;
}

function check_initializer($value)
{
    if ($value === null) {
        return null;
    }
    if (defined($value)) {
        if (strpos($value, '::') !== false) {
            return '\\' . $value;
        }
        return $value;
    }
    if (!is_numeric($value)) {
        return var_export($value, true);
    }
    return $value;
}

/**
 * Returns a PHP type suited to be checked by class_exists() or
 * suited to be displayed as a function type hint or as a PHPDoc type.
 * Takes care of nullable type annotations.
 *
 * @param string $name
 *            PHP identifier to transform
 * @param boolean $isInPhpdoc
 *            should the result be suited to be inserted in a PHPDoc or not
 * @return string formatted PHP type
 */
function build_php_type($name, bool $isInPhpdoc = false, bool $real = false)
{
    if ($name == null || $name == '') {
        return null;
    }

    $nullable = false;
    if ($name[0] === '?') {
        $nullable = true;
        $name = substr($name, 1);
    }
    global $currentNamespace;
    $parsed = parse_php_type($name);

    try {
        $parsed = php_prefix_when_necessary($parsed, $currentNamespace != '' ? '\\' : '', $real);
        $name = print_php_type($parsed);
        if ($nullable) {
            return $isInPhpdoc ? $name . '|null' : '?' . $name;
        }

        return $name;
    } catch (\InvalidArgumentException $e) {
        return null;
    }
}

function print_php_type($parsed)
{
    $list = [];
    foreach ($parsed['parts'] as $v) {
        if (! is_string($v)) {
            $v = print_php_type($v);
            if ($v) {
                $list[] = '(' . $v . ')';
            }

        } else {
            $list[] = $v;
        }
    }
    $list = array_unique($list);
    if (count($list) == 0) {
        return null;
    }
    return implode($parsed['join'], $list);
}

function php_type_transform($name, bool $real): ?string {
    global $typeTransforms;
    if (!isset($typeTransforms[$name])) {
        return $name;
    }
    if ($typeTransforms[$name]['since'] == null || version_compare(phpversion(), $typeTransforms[$name]['since']) < 0) {
        if (!$typeTransforms[$name]['phpdoc'] || $real) {
            if ($typeTransforms[$name]['previous'] == null) {
                throw new \InvalidArgumentException();
            } else {
                return php_type_transform($typeTransforms[$name]['previous'], $real);
            }
        }
    }

    return $name;
}

function php_prefix_when_necessary(array $parsed, string $addTypePrefix, bool $real)
{
    global $typeTransforms;

    $list = [];
    foreach ($parsed['parts'] as $k => $v) {
        if (is_string($v)) {
            $v = php_type_transform($v, $real);
            if ($v[0] == '\\') {
                // ignore
            } elseif (strpos($v, '\\') !== false) {
                $v = $addTypePrefix . $v;
            } elseif (class_exists($addTypePrefix . $v) || interface_exists($addTypePrefix . $v) || enum_exists($addTypePrefix . $v)) {
                $v = $addTypePrefix . $v;
            }
            $list[] = $v;
        } else {
            $v = php_prefix_when_necessary($v, $addTypePrefix, $real);
            if ($v) {
                $list [] =$v;
            }
        }
    }
    $parsed['parts'] = $list;

    return $parsed;
}

function parse_php_type(string $name): array
{
    $group = 0;
    $buffer = '';
    $res = [
        'join' => '|',
        'parts' => []
    ];

    foreach (mb_str_split($name) as $char) {
        switch ($char) {
            case '(':
                if ($group > 0) {
                    $buffer .= $char;
                }
                $group ++;
                break;
            case ')':
                $group --;
                if ($group > 0) {
                    $buffer .= $char;
                } else {
                    $res['parts'][] = parse_php_type(trim($buffer));
                    $buffer = '';
                }
                break;
            case '|':
            case '&':
                if ($group == 0) {
                    $res['join'] = $char;
                    if ($buffer) {
                        $res['parts'][] = $buffer;
                        $buffer = '';
                    }
                } else {
                    $buffer .= $char;
                }
                break;
            case ' ':
                break;
            default:
                $buffer .= $char;
        }
    }
    if ($buffer) {
        $res['parts'][] = $buffer;
    }
    return $res;
}

/**
 * Replaces all invalid characters with '_' in PHP identifier.
 * Can also remove dollar signs when asked to do so.
 *
 * @param $name string
 *            PHP identifier
 * @param $isInPhpdoc boolean
 *            true when it's a phpdoc identifier, false otherwise
 * @param $removeDollars boolean
 *            true when dollar signs should be removed, false otherwise
 * @return string PHP identifier with stripped invalid characters
 */
function clean_php_identifier($name, $isInPhpdoc = false, $removeDollars = false)
{
    global $handleNamespaces;

    if ($handleNamespaces) {
        if ($isInPhpdoc) {
            $name = preg_replace('/[^?\\\\$\\w_|[\\]]+/', '_', $name);
        } else {
            $name = preg_replace('/[^?\\\\$\\w_|&?]+/', '_', $name);
        }
    } else {
        if ($isInPhpdoc) {
            $name = preg_replace('/[^$\\w_|[\\]]+/', '_', $name);
        } else {
            $name = preg_replace('/[^$\\w_|&?]+/', '_', $name);
        }
    }
    if ($removeDollars) {
        $name = str_replace('$', '', $name);
    }
    return $name;
}

/**
 * Makes generic key from given function reference
 *
 * @param $ref ReflectionMethod
 *            function reference
 * @return string generic key
 */
function make_funckey_from_ref($ref)
{
    if ($ref instanceof ReflectionMethod) {
        $funckey = strtolower($ref->getDeclaringClass()->getName()) . "::" . strtolower($ref->getName());
    } else {
        $funckey = strtolower($ref->getName());
    }
    return $funckey;
}

/**
 * Splits text using regular expressions $regExpOpenTag and $regExpCloseTag as open and close tags.
 * When tags are nested, texts with uppermost tags are returned.
 *
 * @param $regExpOpenTag string
 *            regular expression defining open tags
 * @param $regExpCloseTag string
 *            regular expression defining close tags
 * @param $text string
 *            text to parse
 * @return array list of texts delimited by open and close tags
 */
function parse_tags_content($regExpOpenTag, $regExpCloseTag, $text)
{
    if (! preg_match_all($regExpOpenTag, $text, $matches, PREG_OFFSET_CAPTURE)) {
        return array();
    }
    $openTags = $matches[0];
    if (! preg_match_all($regExpCloseTag, $text, $matches, PREG_OFFSET_CAPTURE)) {
        return array();
    }
    $closeTags = $matches[0];

    $nbOpenTags = 0;
    $startContentPos = $endContentPos = - 1;
    $contents = array();

    while (count($openTags) && count($closeTags)) {
        if ($openTags[0][1] < $closeTags[0][1]) {
            $nbOpenTags ++;
            if ($nbOpenTags == 1) {
                $startContentPos = $openTags[0][1];
            }
            array_shift($openTags);
        } else {
            $nbOpenTags --;
            if ($nbOpenTags < 0) {
                return array();
            }
            if ($nbOpenTags == 0) {
                $endContentPos = $closeTags[0][1] + strlen($closeTags[0][0]);
                $contents[] = substr($text, $startContentPos, $endContentPos - $startContentPos);
                $startContentPos = $endContentPos = - 1;
            }
            array_shift($closeTags);
        }
    }

    if ($startContentPos >= 0 && count($closeTags) == $nbOpenTags) {
        $lastTag = array_pop($closeTags);
        $contents[] = substr($text, $startContentPos, $lastTag[1] + strlen($lastTag[0]) - $startContentPos);
        return $contents;
    }

    return array();
}

function parse_phpdoc_type(?DOMElement $type, bool $nest = false): ?string
{
    if ($type == null) {
        return null;
    }
    if ($type->hasAttribute('class')) {

        $parts = [];
        foreach ($type->getElementsByTagName('type') as $sub) {
            $parts[] = parse_phpdoc_type($sub, true);
        }

        if (count($parts) == 1) {
            return $parts[0];
        } elseif (count($parts) == 2 && ! $nest && $parts[1] == 'null') {
            return '?' . $parts[0];
        }
        $res = implode($type->getAttribute('class') == 'union' ? '|' : '&', $parts);
        if ($nest) {
            return '(' . $res . ')';
        }
        return $res;
    }
    return (string) $type->nodeValue;
}



function prepare_xpath(DOMDocument $dom): DOMXPath
{
    $search = new DOMXpath($dom);
    $search->registerNamespace('docbook', DOCBOOK_NS);
    $search->registerNamespace('phpdoc', PHPDOC_NS);

    return $search;
}

function collect_parts(DOMNodeList $list): array
{
    $res = [];
    foreach ($list as $el) {
        $str = $el->ownerDocument->saveXML($el);
        $chunk = substr($str, strlen($el->tagName) + 2, strlen($str) - 5 - 2 * strlen($el->tagName));
        if (strpos($chunk, 'xmlns') === 0) {
            $chunk = substr($chunk, strpos($chunk, '>'));
        }
        $res[] = xml_to_phpdoc(trim($chunk));
    }
    return $res;
}

/**
 * Parses PHP documentation
 *
 * @param SimpleXMLElement $dom
 * @return array Function information gathered from the PHP.net documentation by parsing XML files
 */
function parse_phpdoc_functions(DOMDocument $dom)
{
    $functionsDoc = array();
    $aliases = [];
    $search = prepare_xpath($dom);

    foreach ($search->query('//docbook:refentry') as $xml) {

        $refnameDiv = $xml->getElementsByTagName('refnamediv')->item(0);
        foreach ($refnameDiv->getElementsByTagName('refname') as $refXML) {
            $refname = $refXML->nodeValue;

            $refname = strtolower($refname);

            $id = $xml->getAttribute('xml:id');
            $deprecatedQuery = $search->query('docbook:refsynopsisdiv//docbook:emphasis[text()=\'DEPRECATED\']', $xml);

            $functionDoc = [
                'id' => $id,
                'quickref' => trim($refnameDiv->getElementsByTagName('refpurpose')->item(0)->nodeValue),
                'deprecated' => $deprecatedQuery->count() > 0
            ];

            foreach ($search->query('docbook:refsect1', $xml) as $sec) {

                if ($sec->getAttribute('role') == 'description') {

                    $methodInfo = null;

                    if ($sec->getElementsByTagName('methodsynopsis')->count()) {
                        $methodInfo = $sec->getElementsByTagName('methodsynopsis');
                    } else if ($sec->getElementsByTagName('constructorsynopsis')->count()) {
                        $methodInfo = $sec->getElementsByTagName('constructorsynopsis');
                    }
                    if (! $methodInfo) {
                        $aliased = $search->query('docbook:simpara/docbook:function', $sec);
                        if ($aliased->count()) {
                            $aliases[$refname] = $functionDoc;
                            $aliases[$refname]['alias'] = strtolower($aliased->item(0)->nodeValue);
                        }

                        continue;
                    }
                    $functionsDoc[$refname] = $functionDoc;

                    if ($methodInfo->count() == 1) {
                        $methodInfo = $methodInfo->item(0);
                    } elseif ($methodInfo->count() > 1) {
                        $correct = null;

                        foreach ($methodInfo as $el) {

                            if (strtolower($el->getElementsByTagName('methodname')->item(0)->nodeValue) == $refname) {
                                $correct = $el;

                                break;
                            }
                        }
                        $methodInfo = $correct ?? $methodInfo->item(0);
                    }

                    $methodName = $methodInfo->getElementsByTagName('methodname')->item(0)->nodeValue;
                    if (strpos((string) $methodName, '::') !== false) {
                        list ($functionsDoc[$refname]['classname'], $functionsDoc[$refname]['methodname']) = explode('::', $methodName);
                    } else {
                        $functionsDoc[$refname]['classname'] = null;
                        $functionsDoc[$refname]['methodname'] = $methodName;
                    }

                    $returnType = $methodInfo->getElementsByTagName('type');
                    if ($returnType->count()) {
                        $functionsDoc[$refname]['returntype'] = parse_phpdoc_type($returnType->item(0));
                    } elseif ($methodInfo->tagName != 'constructorsynopsis') {
                        $functionsDoc[$refname]['returntype'] = 'void';
                    }

                    $functionsDoc[$refname]['parameters'] = [];
                    if ($methodInfo->getElementsByTagName('void')->count() == 0) {
                        foreach ($search->query('docbook:methodparam', $methodInfo) as $param) {
                            $paramName = $param->getElementsByTagName('parameter')->item(0);
                            $paramInitializer = $param->getElementsByTagName('initializer');
                            $paramDef = [
                                'name' => $paramName->nodeValue,
                                'type' => parse_phpdoc_type($param->getElementsByTagName('type')->item(0)),
                                'isoptional' => $param->getAttribute('choice') == 'opt',
                                'isreference' => $paramName->getAttribute('role') == 'reference',
                                'isvariadic' => $param->getAttribute('rep') == 'repeat',
                                'initializer' => check_initializer($paramInitializer->count() ? $paramInitializer->item(0)->nodeValue : null)
                            ];
                            $functionsDoc[$refname]['parameters'][$paramDef['name']] = $paramDef;
                        }
                    }
                }

                if ($sec->getAttribute('role') == 'parameters') {
                    $list = $search->query('docbook:variablelist/docbook:varlistentry', $sec);
                    if ($list->count() == 0) {
                        $list = $search->query('*/docbook:variablelist/docbook:varlistentry', $sec);
                    }
                    foreach ($search->query('docbook:variablelist/docbook:varlistentry', $sec) as $var) {
                        if ($search->query('docbook:term/docbook:parameter', $var)->item(0) == null) {
                            continue;
                        }

                        $paramName = $search->query('docbook:term/docbook:parameter', $var)->item(0)->nodeValue;
                        if (! isset($functionsDoc[$refname]['parameters'][$paramName])) {
                            continue;
                        }
                        $doc = $search->query('docbook:listitem//docbook:para', $var);
                        if ($doc->count() == 0) {
                            $doc = $search->query('docbook:listitem//docbook:simpara', $var);
                        }

                        $functionsDoc[$refname]['parameters'][$paramName]['paramdoc'] = xml_para_to_phpdoc($doc);
                    }
                }
                if ($sec->getAttribute('role') == 'returnvalues') {
                    $doc = $search->query('.//docbook:para', $sec);
                    if ($doc->count() == 0) {
                        $doc = $search->query('.//docbook:simpara', $sec);
                    }

                    $functionsDoc[$refname]['returndoc'] = xml_para_to_phpdoc($doc);
                }
            }
        }
    }

    foreach ($aliases as $name => $target) {
        $functionsDoc[$name] = array_merge($functionsDoc[$target['alias']], $target);
    }

    return $functionsDoc;
}

/**
 * Parses PHP documentation
 *
 * @param $phpdocDir string
 *            PHP.net documentation directory
 * @return array Class information gathered from the PHP.net documentation by parsing XML files
 */
function parse_phpdoc_classes(DOMDocument $dom)
{
    global $fields_doc;

    $search = prepare_xpath($dom);
    $classesDoc = array();

    foreach ($search->query('//docbook:varlistentry[@xml:id]') as $fieldDoc) {
        $id = $fieldDoc->getAttribute('xml:id');

        $doc = xml_para_to_phpdoc($search->query('.//docbook:listitem/docbook:para', $fieldDoc));
        $deprecated = $search->query('.//docbook:listitem/docbook:warning/docbook:emphasis[text()=\'DEPRECATED\']', $fieldDoc)->count() > 0;
        $term = $fieldDoc->getElementsByTagName('term')->item(0);
        if ($term->getElementsByTagName('varname')->count() == 0) {
            continue;
        }

        $fields_doc[$id] = [
            'doc' => $doc,
            'const' => $term->getElementsByTagName('varname')->count() == 0,
            'name' => $term->getElementsByTagName('varname')->item(0)->nodeValue,
            'deprecated' => $deprecated
        ];
    }

    foreach ($search->query('//phpdoc:classref|//phpdoc:exceptionref') as $xml) {

        $id = $xml->getAttribute('xml:id');

        $class = $xml->getElementsByTagName('titleabbrev')->item(0)->nodeValue;
        $refname = strtolower($class);
        $classesDoc[$refname] = [
            'id' => $id,
            'name' => $class,
            'fields' => []
        ];

        foreach ($search->query('docbook:partintro/docbook:section', $xml) as $sec) {
            if (strpos($sec->getAttribute('xml:id'), '.intro')) {
                $classesDoc[$refname]['doc'] = xml_para_to_phpdoc($search->query('.//docbook:para', $sec));
            }
        }

        $fields_doc[$refname] = [];
        foreach ($search->query('.//docbook:fieldsynopsis', $xml) as $field) {
            $varname = $field->getElementsByTagName('varname')->item(0);
            $initializer = $field->getElementsByTagName('initializer');
            $fieldName = $varname->nodeValue;

            if (strpos($fieldName, '->') !== false) {
                $fieldName = explode('->', $fieldName)[1];
            }
            if (strpos($fieldName, '::') !== false) {
                $fieldName = explode('::', $fieldName)[1];
            }
            $fieldId = $varname->getAttribute('linkend');

            // $deprecatedQuery = $search->query('docbook:refsynopsisdiv//docbook:emphasis[text()=\'DEPRECATED\']', $xml);
            $fieldDef = [
                'name' => $fieldName,
                'type' => parse_phpdoc_type($field->getElementsByTagName('type')->item(0)),
                'initializer' => check_initializer($initializer->count() ? $initializer->item(0)->nodeValue : null),
                'deprecated' => $deprecated,
                'modifiers' => []
            ];
            foreach ($field->getElementsByTagName('modifier') as $mod) {
                $fieldDef['modifiers'][] = $mod->nodeValue;
            }
            if (in_array('private', $fieldDef['modifiers'])) {
                continue;
            }
            if (in_array('const', $fieldDef['modifiers'])) {
                continue; // skip constants
            }
            $fieldDef['modifier'] = clean_php_field_modifiers($fieldDef['modifiers']);
            // $fields_doc[$refname][$fieldId] = $fieldDef;
            if (! isset($fields_doc[$fieldId])) {
                // sometimes description is in different place
                $desc = $search->query("//docbook:refentry[@xml:id='$fieldId']/docbook:refnamediv/docbook:refpurpose");
                if ($desc->count()) {
                    $fields_doc[$fieldId] = [
                        'doc' => $desc->item(0)->nodeValue,
                        'deprecated' => false
                    ];
                } else {
                    $fields_doc[$fieldId] = [
                        'doc' => null,
                        'deprecated' => false
                    ];
                }
            }
            $classesDoc[$refname]['fields'][$fieldName] = array_merge($fields_doc[$fieldId], $fieldDef);
        }
    }

    return $classesDoc;
}

function clean_php_field_modifiers(array $modifiers)
{
    $filtered = [];
    foreach ($modifiers as $mod) {
        if ($mod == 'readonly' && version_compare(phpversion(), "8.1.0") < 0) {
            continue;
        }
        $filtered[] = $mod;
    }

    return implode(' ', $filtered);
}

/**
 * Parses PHP documentation
 *
 * @param $phpdocDir string
 *            PHP.net documentation directory
 * @return array Constant information gathered from the PHP.net documentation by parsing XML files
 */
function parse_phpdoc_constants(DOMDocument $dom)
{
    $search = prepare_xpath($dom);
    $constantsDoc = array();
    foreach ($search->query('//docbook:varlistentry') as $xml) {
        $term = $xml->getElementsByTagName('term')->item(0);
        if ($term == null) {
            continue;
        }
        if ($term->getElementsByTagName('constant')->count() == 0) {
            continue;
        }
        $id = $xml->getAttribute('xml:id');
        if (! $id) {
            continue;
        }
        $name = $term->getElementsByTagName('constant')->item(0)->nodeValue;
        $type = parse_phpdoc_type($term->getElementsByTagName('type')->item(0));
        $deprecated = $search->query('.//docbook:listitem/docbook:warning/docbook:emphasis[text()=\'DEPRECATED\']', $xml)->count() > 0;
        $doc = $search->query('.//docbook:listitem//docbook:para', $xml);
        if ($doc->count() == 0) {
            $doc = $search->query('.//docbook:listitem//docbook:simpara', $xml);
        }
        $doc = xml_para_to_phpdoc($doc);
        $linkend = $search->query('.//docbook:link[@linkend]', $xml);
        if ($linkend->count()) {
            $prevId = $id;
            $id = $linkend->item(0)->getAttribute('linkend');
            $targetLink = $search->query('//docbook:appendix[@xml:id=\'' . $id . '\']//docbook:constant[text()=\'' . $name . '\']')->item(0);
            if ($targetLink && $targetLink->parentNode->tagName == 'entry') {
                $doc = $targetLink->parentNode->nextElementSibling->nodeValue;
            }
        } else {
            $tmp = $xml;
            $names = [];
            while ($tmp instanceof DOMElement) {

                if ($tmp->tagName == 'appendix' || $tmp->tagName == 'section' || $tmp->tagName == 'phpdoc:exceptionref' || $tmp->tagName == 'phpdoc:classref' || $tmp->tagName == 'sect1') {

                    if ($tmp->getAttribute('xml:id')) {
                        $id = $tmp->getAttribute('xml:id');
                        break;
                    }
                }

                $tmp = $tmp->parentNode;
            }
        }
        $constantsDoc[$name] = [
            'id' => $id,
            'doc' => $doc,
            'deprecated' => $deprecated,
            'type' => $type
        ];
    }

    return $constantsDoc;
}

/**
 * Prints ReflectionExtension in format of PHP code
 *
 * @param $extRef ReflectionExtension
 *            object
 */
function print_extension($extRef)
{
    global $splitFiles;
    global $countNamespacesInCurrentFile;
    global $currentNamespace;

    if ($splitFiles) {
        print "\n// Start of {$extRef->getName()} v.{$extRef->getVersion()}\n";
        open_namespace('');
    }

    // process classes:
    $classesRef = $extRef->getClasses();
    if (count($classesRef) > 0) {
        foreach ($classesRef as $classRef) {
            print_class($classRef);
        }
    }

    // process functions
    $funcsRef = $extRef->getFunctions();
    if (count($funcsRef) > 0) {
        foreach ($funcsRef as $funcRef) {
            print_function($funcRef);
        }
        print "\n";
    }

    // put all constants in global namespace
    if ($currentNamespace !== '') {
        close_namespace();
        open_namespace('');
    }

    // process constants
    $constsRef = $extRef->getConstants();
    if (count($constsRef) > 0) {
        print_constants($constsRef);
        print "\n";
    }

    if ($splitFiles) {
        close_namespace($countNamespacesInCurrentFile == 1);
        print "// End of {$extRef->getName()} v.{$extRef->getVersion()}\n";
    }
}

/**
 * Prints ReflectionClass in format of PHP code
 *
 * @param $classRef ReflectionClass
 *            object
 * @param $tabs integer
 *            [optional] number of tabs for indentation
 */
function print_class($classRef, $tabs = 0)
{
    global $processedClasses;
    global $currentNamespace;
    global $addGlobalNSPrefix;
    global $handleNamespaces;
    $classRefName = $classRef->getName();
    $className = strtolower($classRefName);
    $processedClasses[$className] = true;

    print "\n";
    if ($handleNamespaces) {
        $lastBackslashIdx = strrpos($classRefName, '\\');
        if ($lastBackslashIdx !== false) {
            $newNamespace = substr($classRefName, 0, $lastBackslashIdx);
            if (strcasecmp($currentNamespace, $newNamespace) != 0) {
                close_namespace();
                open_namespace($newNamespace);
            }
        } elseif ($currentNamespace !== '') {
            close_namespace();
            open_namespace('');
        }
    } else {
        $lastBackslashIdx = false;
    }
    print_doccomment($classRef, $tabs);

    if (version_compare(phpversion(), "8.0.0") >= 0) {
        foreach ($classRef->getAttributes() as $attr) {
            print_tabs($tabs);
            print '#[';
            print build_php_type($attr->getName(), false, true);
            if (count($attr->getArguments())) {
                print '(';
                foreach ($attr->getArguments() as $arg) {
                    var_export($arg);
                    print ', ';
                }
                print ')';
            }

            print "]\n";
        }
    }
    print_tabs($tabs);
    if ($classRef->isFinal())
        print "final ";
    if (version_compare(phpversion(), "8.2.0") >= 0 && $classRef->isReadOnly()) {
        print 'readonly ';
    }
    if (! $classRef->isInterface() && $classRef->isAbstract())
        print "abstract ";
    if ($classRef->isInterface()) {
        print 'interface ';
    } elseif ($classRef->isTrait()) {
        print 'trait ';
    } else if ($classRef instanceof ReflectionEnum) {
        print 'enum ';
    } else {
        print 'class ';
    }

    if ($lastBackslashIdx !== false) {
        print clean_php_identifier(substr($classRef->getName(), $lastBackslashIdx + 1)) . " ";
    } else {
        print clean_php_identifier($classRef->getName()) . " ";
    }

    // print out parent class
    $parentClassRef = $classRef->getParentClass();
    if ($parentClassRef) {
        print "extends {$addGlobalNSPrefix}{$parentClassRef->getName()} ";
    }

    // print out interfaces
    $interfacesRef = $classRef->getInterfaces();
    if (count($interfacesRef) > 0) {
        print $classRef->isInterface() ? "extends " : "implements ";
        $i = 0;
        foreach ($interfacesRef as $interfaceRef) {
            if ($i ++ > 0) {
                print ", ";
            }
            print "{$addGlobalNSPrefix}{$interfaceRef->getName()}";
        }
    }
    print " {\n";

    // process constants
    $constsRef = $classRef->getConstants();
    if (count($constsRef) > 0) {
        print_class_constants($classRefName, $constsRef, $tabs + 1);
        print "\n";
    }

    global $classesDoc;

    $printedFields = array();

    // process properties
    $propertiesRef = $classRef->getProperties();
    $classfieldsDoc = [];
    if (isset($classesDoc[$className]['fields'])) {
        $classfieldsDoc = $classesDoc[$className]['fields'];
    }

    global $fields_doc;
    if (count($classfieldsDoc) || count($propertiesRef)) {
        foreach ($propertiesRef as $propertyRef) {
            if ($propertyRef->getDeclaringClass()->getName() != $classRef->getName()) {
                unset($classfieldsDoc[$propertyRef->getName()]);
                continue;
            }
            if (! isset($classfieldsDoc[$propertyRef->getName()])) {
                $classfieldsDoc[$propertyRef->getName()] = [
                    'name' => $propertyRef->getName(),
                    'type' => null,
                    'doc' => null,
                    'modifier' => null,
                    'deprecated' => false,
                    'initializer' => null,
                    'id' => null
                ];
            }
            if (! $classfieldsDoc[$propertyRef->getName()]['doc']) {
                $classfieldsDoc[$propertyRef->getName()]['doc'] = $propertyRef->getDocComment();
            }
            if (! $classfieldsDoc[$propertyRef->getName()]['type'] && $propertyRef->getType()) {
                $classfieldsDoc[$propertyRef->getName()]['type'] = build_php_type($propertyRef->getType()->__toString(), false, false);
            }
            if (! $classfieldsDoc[$propertyRef->getName()] && $propertyRef->hasDefaultValue()) {
                $classfieldsDoc[$propertyRef->getName()]['initializer'] = var_export($propertyRef->getDefaultValue(), true);
            }
            if (! $classfieldsDoc[$propertyRef->getName()]['modifier']) {
                $mods = [];
                if ($propertyRef->isPublic()) {
                    $mods[] = 'public';
                }
                if ($propertyRef->isProtected()) {
                    $mods[] = 'protected';
                }
                if (is_callable([
                    $propertyRef,
                    'isReadOnly'
                ]) && $propertyRef->isReadOnly()) {
                    $mods[] = 'readonly';
                }
                if ($propertyRef->isStatic()) {
                    $mods[] = 'static';
                }
                $classfieldsDoc[$propertyRef->getName()]['modifier'] = clean_php_field_modifiers($mods);
            }
        }
        foreach ($classfieldsDoc as $field) {
            if (! key_exists($field['name'], $printedFields) && isset($field['modifier']) && trim($field['modifier']) !== '') {

                // print doc here
                print("\n");
                $doc = $field['doc'] ?? '{@inheritdoc}';
                if ($doc) {
                    print_tabs($tabs + 1);
                    print "/**\n";
                    print_tabs($tabs + 1);
                    print " * " . newline_to_phpdoc($doc, $tabs + 1) . "\n";
                    print_tabs($tabs + 1);

                    print " * @var " . build_php_type($field['type'], true, false) . "\n";

                    if ($field['deprecated'] == true) {
                        print_tabs($tabs + 1);
                        print " * @deprecated " . "\n";
                    }

                    print_Tabs($tabs + 1);
                    // http://www.php.net/manual/en/class.domdocument.php#domdocument.props.actualencoding
                    $refname = strtolower($classRef->getName());
                    $class_url = make_url("class." . $refname);
                    $field_name = strtolower($field['name']);
                    $field_url = $class_url . '#' . $className . ".props." . $field_name;
                    print " * @link {$field_url}\n";

                    print_tabs($tabs + 1);
                    print " */\n";
                }

                print_tabs($tabs + 1);
                print implode(' ', array(
                    $field['modifier']
                ));
                print " ";
                if ($field['type']) {
                    print build_php_type($field['type'], false, true) . ' ';
                }
                print "\${$field['name']};\n";
            }
        }
    }

    // process methods
    $methodsRef = $classRef->getMethods();
    if (count($methodsRef) > 0) {
        $printedMethods = array();
        $cleanName = clean_php_identifier($classRef->getName());
        foreach ($methodsRef as $methodRef) {
            $cleanMName = clean_php_identifier($methodRef->getName());
            // bug 415896
            if ($cleanMName == '__construct' && isset($printedMethods[$cleanName])) {
                continue;
            }
            $printedMethods[$cleanMName] = $methodRef;
            print_function($methodRef, $tabs + 1, true);
        }
        print "\n";
    }
    print_tabs($tabs);
    print "}\n";
}

function print_function($functionRef, $tabs = 0, $isMethod = false)
{
    global $functionsDoc;
    global $processedFunctions;
    global $currentNamespace;
    global $addGlobalNSPrefix;
    global $handleNamespaces;
    $funckey = make_funckey_from_ref($functionRef);
    $processedFunctions[$funckey] = true;

    print "\n";
    if (! $isMethod && $handleNamespaces) {
        $lastBackslashIdx = strrpos($funckey, '\\');
        if ($lastBackslashIdx !== false) {
            $newNamespace = substr($funckey, 0, $lastBackslashIdx);
            if (strcasecmp($currentNamespace, $newNamespace) != 0) {
                close_namespace();
                open_namespace($newNamespace);
            }
        } elseif ($currentNamespace !== '') {
            close_namespace();
            open_namespace('');
        }
    } else {
        $lastBackslashIdx = false;
    }
    print_doccomment($functionRef, $tabs);
    print_tabs($tabs);
    if (! ($functionRef instanceof ReflectionFunction)) {
        print_modifiers($functionRef);
    }

    print "function ";
    if ($functionRef->returnsReference()) {
        print "&";
    }
    if ($lastBackslashIdx !== false) {
        print substr($functionRef->getName(), $lastBackslashIdx + 1) . " (";
    } else {
        print "{$functionRef->getName()} (";
    }

    $parameters = isset($functionsDoc[$funckey]['parameters']) ? $functionsDoc[$funckey]['parameters'] : null;

    if ($parameters) {
        print_parameters($parameters);
    } else {
        print_parameters_ref($functionRef->getParameters());
    }
    print ')';
    $type = null;
    if (! empty($functionsDoc[$funckey]['returntype'])) {
        $type = build_php_type($functionsDoc[$funckey]['returntype'], false, true);
    }
    if (!$type && $functionRef->getReturnType() != null) {
        $type = build_php_type($functionRef->getReturnType()->__toString(), false, true);
    }
    if ($type) {
        print ': ' . $type;
    }
    if ($functionRef instanceof ReflectionMethod && $functionRef->isAbstract()) {
        print ";\n";
    } else {
        print " {}\n";
    }
}

/**
 * Prints ReflectionParameter in format of PHP code
 *
 * @param $parameters array
 *            information from PHP.net documentation
 */
function print_parameters($parameters)
{
    global $addGlobalNSPrefix;
    $i = 0;
    foreach ($parameters as $parameter) {

        if ($parameter['name'] != "...") {
            if ($i ++ > 0) {
                print ", ";
            }
            $type = build_php_type($parameter['type'], false, true);
            // http://php.net/manual/en/functions.arguments.php
            if ($type) {
                print $type . ' ';
            }
            if (isset($parameter['isreference']) && $parameter['isreference']) {
                print "&";
            }
            if (! isset($parameter['name'])) {}
            if ($parameter['isvariadic'] && $i == count($parameters)) {
                print '...';
            }
            print "\${$parameter['name']}";

            if (isset($parameter['isoptional']) && $parameter['isoptional']) {
                if (isset($parameter['initializer']) && $parameter['initializer']) {
                    print ' = ' . $parameter['initializer'];
                } elseif (isset($parameter['defaultvalue']) && $parameter['defaultvalue']) {
                    $value = $parameter['defaultvalue'];
                    if (! is_numeric($value)) {
                        $value = "'{$value}'";
                    }
                    print " = {$value}";
                } else {
                    print " = null";
                }
            }
        }
    }
}

/**
 * Prints ReflectionParameter in format of PHP code
 *
 * @param $paramsRef ReflectionParameter[]
 *            array of objects
 */
function print_parameters_ref($paramsRef)
{
    global $addGlobalNSPrefix;
    $i = 0;
    foreach ($paramsRef as $paramRef) {
        if ($i ++ > 0) {
            print ", ";
        }

        if ($paramRef->getType() != null) {
            print build_php_type($paramRef->getType()->__toString(), false, true) . ' ';
        }

        $name = $paramRef->getName() ? $paramRef->getName() : "var" . ($i + 1);
        if ($name != "...") {

            if ($paramRef->isPassedByReference()) {
                print "&";
            }
            if (version_compare(phpversion(), "5.6.0") >= 0 && $paramRef->isVariadic() && count($paramsRef) == $i) {
                print "...\${$name}";
                // variadic parameters do not support default values
                continue;
            }
            print "\${$name}";
            if ($paramRef->isDefaultValueAvailable() || $paramRef->isOptional()) {
                $value = null;
                if ($paramRef->isDefaultValueAvailable()) {
                    $value = $paramRef->getDefaultValue();
                }
                $value = var_export($value, true);

                print " = {$value}";
            } elseif ($paramRef->allowsNull()) {
                print " = null";
            }
        }
    }
}

/**
 * Prints constants in format of PHP code
 *
 * @param $constants array
 *            containing constants, where key is a name of constant
 * @param $tabs integer
 *            [optional] number of tabs for indentation
 */
function print_constants($constants, $tabs = 0)
{
    foreach ($constants as $name => $value) {
        print_constant($name, $value, $tabs);
    }
}

function print_constant($name, $value = null, $tabs = 0)
{
    global $constantsDoc;
    global $processedConstants;
    $processedConstants[$name] = true;

    if ($value === null) {
        try {
            $value = @constant($name);
        } catch (Throwable $e) {}
    }
    $value = escape_const_value($value);

    $doc = isset($constantsDoc[$name]['doc']) ? $constantsDoc[$name]['doc'] : null;
    $id = isset($constantsDoc[$name]['id']) ? $constantsDoc[$name]['id'] : null;
    $type = isset($constantsDoc[$name]['type']) ? $constantsDoc[$name]['type'] : null;
    $deprecated = isset($constantsDoc[$name]['deprecated']) ? $constantsDoc[$name]['deprecated'] : null;
    if ($doc || $id) {
        print "\n";
        print_tabs($tabs);
        print "/**\n";
        print_tabs($tabs);
        print " * " . newline_to_phpdoc($doc, $tabs) . "\n";
        if ($id) {
            print_tabs($tabs);
            print " * @link " . make_url($id) . "\n";
        }
        if ($type) {
            print_tabs($tabs);
            print " * @var " . $type . "\n";
        }
        if ($deprecated) {
            print_tabs($tabs);
            print " * @deprecated\n";
        }
        print_tabs($tabs);
        print " */\n";
    }
    print_tabs($tabs);
    print "define ('{$name}', {$value});\n";
}

function escape_const_value($value)
{
    if (is_resource($value)) {
        $value = "\"${value}\"";
    } elseif (is_array($value)) {
        $value = var_export($value, true);
    } elseif (! is_numeric($value) && ! is_bool($value) && $value !== null) {
        if ($value === '\\') {
            $value = '"' . addcslashes($value, "\\\"\r\n\t") . '"';
        } else {
            $value = '"' . addcslashes($value, "\"\r\n\t") . '"';
        }
    } elseif ($value === null) {
        $value = "null";
    } elseif ($value === false) {
        $value = "false";
    } elseif ($value === true) {
        $value = "true";
    }
    return $value;
}

/**
 * Prints class constants in format of PHP code
 *
 * @param $constants array
 *            containing constants, where key is a name of constant
 * @param $tabs integer
 *            [optional] number of tabs for indentation
 */
function print_class_constants($refname, $constants, $tabs = 0)
{
    global $constantsDoc;

    foreach ($constants as $name => $value) {
        if (isset($constantsDoc[$refname . '::' . $name])) {

            if ($constantsDoc[$refname . '::' . $name]['type'] || $constantsDoc[$refname . '::' . $name]['doc'] || $constantsDoc[$refname . '::' . $name]['deprecated']) {
                print_tabs($tabs);
                print "/**\n";

                if ($constantsDoc[$refname . '::' . $name]['doc']) {
                    print_tabs($tabs);
                    print " * " . newline_to_phpdoc($constantsDoc[$refname . '::' . $name]['doc'], $tabs) . "\n";
                }
                if ($constantsDoc[$refname . '::' . $name]['type']) {
                    print_tabs($tabs);
                    print " * @var " . $constantsDoc[$refname . '::' . $name]['type'] . "\n";
                }
                if ($constantsDoc[$refname . '::' . $name]['deprecated']) {
                    print_tabs($tabs);
                    print " * @deprecated\n";
                }
            }
        }
        $value = escape_const_value($value);

        print_tabs($tabs);
        print "const {$name} = {$value};\n";
    }
}

/**
 * Prints modifiers of reflection object in format of PHP code
 *
 * @param $ref Reflection
 *            some reflection object
 * @param $excludeModifierKeywords array
 *            exclude modifier keywords
 */
function print_modifiers($ref, $excludeModifierKeywords = array())
{
    $modifiers = Reflection::getModifierNames($ref->getModifiers());
    $modifiers = array_diff($modifiers, $excludeModifierKeywords);
    if (count($modifiers) > 0) {
        print implode(' ', $modifiers);
        print " ";
    }
}

/**
 * Makes PHP Manual URL from the given ID
 *
 * @param $id string
 *            PHP Element ID
 * @return string URL
 */
function make_url($id)
{
    // Handle namespaced classes:
    $id = str_replace('\\', '-', $id);

    return "http://www.php.net/manual/en/{$id}.php";
}

/**
 * Prints PHPDOC comment before specified reflection object
 *
 * @param $ref Reflection
 *            some reflection object
 * @param $tabs integer
 *            [optional] number of tabs for indentation
 */
function print_doccomment($ref, $tabs = 0, array $docs = [])
{
    global $functionsDoc;
    global $classesDoc;
    global $addGlobalNSPrefix;

    $docComment = $ref->getDocComment();

    if ($docComment) {
        print_tabs($tabs);
        print "{$docComment}\n";
    } elseif ($ref instanceof ReflectionClass) {
        $refname = strtolower($ref->getName());

        if (isset($classesDoc[$refname]) && $classesDoc[$refname]) {
            print_tabs($tabs);
            print "/**\n";
            $doc = isset($classesDoc[$refname]['doc']) ? $classesDoc[$refname]['doc'] : null;
            if ($doc) {
                $doc = newline_to_phpdoc($doc, $tabs);
                print_tabs($tabs);
                print " * {$doc}\n";
            }
            if (isset($classesDoc[$refname]['id']) && $classesDoc[$refname]['id']) {
                print_Tabs($tabs);
                $url = make_url("class." . $refname);
                print " * @link {$url}\n";
            }
            print_tabs($tabs);
            print " */\n";
        }
    } elseif ($ref instanceof ReflectionFunctionAbstract) {
        $funckey = make_funckey_from_ref($ref);

        $returntype = isset($functionsDoc[$funckey]['returntype']) ? $functionsDoc[$funckey]['returntype'] : null;
        $desc = isset($functionsDoc[$funckey]['quickref']) ? $functionsDoc[$funckey]['quickref'] : null;
        $deprecated = isset($functionsDoc[$funckey]['deprecated']) ? $functionsDoc[$funckey]['deprecated'] : null;
        $returndoc = newline_to_phpdoc(isset($functionsDoc[$funckey]['returndoc']) ? $functionsDoc[$funckey]['returndoc'] : null, $tabs);

        $paramsRef = $ref->getParameters();
        $parameters = isset($functionsDoc[$funckey]['parameters']) ? $functionsDoc[$funckey]['parameters'] : null;
        $desc = $desc ?? '{@inheritdoc}';
        if ($desc || count($paramsRef) > 0 || $parameters || $returntype) {
            print_tabs($tabs);
            print "/**\n";
            if ($desc) {
                print_tabs($tabs);
                print " * " . newline_to_phpdoc(xml_to_phpdoc($desc), $tabs) . "\n";
            }
            if (isset($functionsDoc[$funckey]['id']) && $functionsDoc[$funckey]['id']) {
                print_tabs($tabs);
                $url = make_url($functionsDoc[$funckey]['id']);
                print " * @link {$url}\n";
            }
            if ($parameters) {
                foreach ($parameters as $parameter) {
                    print_tabs($tabs);

                    if ($parameter['type']) {
                        $printType = build_php_type($parameter['type'], true, false) ?? 'mixed';
                        print " * @param {$printType} \${$parameter['name']}";
                    } else {
                        print " * @param mixed \${$parameter['name']}";
                    }
                    if (isset($parameter['isoptional']) && $parameter['isoptional']) {
                        print " [optional]";
                    }
                    $paramdoc = newline_to_phpdoc(isset($parameter['paramdoc']) ? $parameter['paramdoc'] : null, $tabs);
                    print " {$paramdoc}";
                    print "\n";
                }
            } else {
                $i = 0;
                foreach ($paramsRef as $paramRef) {
                    print_tabs($tabs);
                    $name = $paramRef->getName() ? $paramRef->getName() : "var" . ++ $i;
                    print " * @param";

                    if ($paramRef->getType()) {
                        print ' ' . build_php_type($paramRef->getType()->__toString(), true, false) ?? 'mixed';
                    } else {
                        print ' mixed';
                    }

                    print " \${$name}";
                    if ($paramRef->isOptional()) {
                        print " [optional]";
                    }
                    print "\n";
                }
            }
            if ($returntype) {
                print_tabs($tabs);
                $returntype = rewrite_phpdoc_return_types($funckey, $returntype);
                if ($returntype) {
                    $printType = build_php_type($returntype, true, false);
                    print " * @return {$printType} {$returndoc}\n";
                } else {
                    print " * @return mixed {$returndoc}\n";
                }
            }
            if ($ref->isDeprecated() || $deprecated) {
                print_tabs($tabs);
                print " * @deprecated {$deprecated}\n";
            }
            print_tabs($tabs);
            print " */\n";
        }
    } elseif ($ref instanceof ReflectionProperty) {
        // TODO complete phpdoc for fields detected by reflection
    }
}

function xml_para_to_phpdoc(?DOMNodeList $list): ?string
{
    if ($list == null) {
        return null;
    }
    $row = 0;
    $parts = [];
    $par = $list->count() > 0 && $list->item(0)->tagName != 'simpara';
    foreach (collect_parts($list) as $el) {
        $parts[] = $row ++ == 0 || ! $par ? $el : '<p>' . $el . '</p>';
    }

    return implode("\n", $parts);
}

/**
 * Converts XML entities to human readable string for PHPDOC
 *
 * @param $str string
 * @return string
 */
function xml_to_phpdoc($str)
{
    $str = str_replace("&return.success;", "Returns true on success or false on failure.", $str);
    $str = str_replace("&return.void;", "", $str);
    $str = str_replace("&Alias;", "Alias:", $str);
    // rewrite all non-html entities like "&null;", "&true;", "&false;"
    // as "null", "true", "false"
    preg_match_all('@&([a-zA-Z0-9._-]+);@s', $str, $matches);
    if ($matches) {
        foreach (array_unique($matches[1]) as $v) {
            if (html_entity_decode("&" . $v . ";") === "&" . $v . ";") {
                $str = str_replace("&" . $v . ";", $v, $str);
            }
        }
    }
    $str = strip_tags_special($str);
    $str = preg_replace('/  +/', " ", $str);
    $str = preg_replace('/[\r\n][\t ]/', "\n", $str);
    $str = trim($str);
    return $str;
}

/**
 * Converts newlines to PHPDOC prefixes in the given string
 *
 * @param $str string
 * @param $tabs integer
 *            [optional] number of tabs for indentation
 * @return string PHPDOC string
 */
function newline_to_phpdoc($str, $tabs = 0)
{
    $str = preg_replace('@([\r\n]+\s*)+@', "\n" . str_repeat("\t", $tabs) . " * ", $str);
    return $str;
}

/**
 * Prints specified number of tabs
 *
 * @param $tabs integer
 *            number of tabs to print
 */
function print_tabs($tabs)
{
    print str_repeat("\t", $tabs);
}

/**
 * Starts outputing to the new file
 */
function begin_file_output()
{
    global $countNamespacesInCurrentFile;
    $countNamespacesInCurrentFile = 0;
    ob_start();
    print "<?php\n";
}

/**
 * Ends outputing, and dumps the output to the specified file
 *
 * @param $filename string
 *            File to dump the output
 */
function finish_file_output($filename)
{
    global $countNamespacesInCurrentFile;
    $countNamespacesInCurrentFile = 0;
    // if (file_exists ($filename)) {
    // rename ($filename, "{$filename}.bak");
    // }
    // print "? >";
    file_put_contents($filename, ob_get_contents());
    ob_end_clean();
}

/**
 * Strips xml tags from the string like the standard strip_tags() function
 * would do, but also translates some of the docbook tags (such as tables
 * an paragraphs) to proper html tags
 *
 * @param $str string
 * @return string
 */
function strip_tags_special($str)
{
    // first mask and translate the tags to preserve
    $str = preg_replace('@<(/?)(table|tgroup)(?:\s(?:[^>]*?[^/>])?)?>@', '###($1table)###', $str);
    $str = preg_replace('@<row(?:\s(?:[^>]*?[^/>])?)?>@', "###(tr valign=\"top\")###", $str);
    $str = str_replace("</row>", "###(/tr)###", $str);

    // Bug 522585 - Documentation of PHP function header() is misformatted
    $str = str_replace("<![CDATA[", "###(pre)###", $str);
    $str = str_replace("]]>", "###(/pre)###", $str);
    preg_match_all('@(<\?php.*?\?>)@si', $str, $matches);
    if ($matches) {
        foreach (array_unique($matches[1]) as $v) {
            $str = str_replace($v, "###(code)###" . strtr(htmlspecialchars($v), array(
                "/" => "&#47;",
                "*" => "&#42;",
                "#" => "&#35;"
            )) . "###(/code)###", $str);
        }
    }

    // handle gracefully inner tags <entry>, </entry>
    // or inner tag with attributes like <entry align="center">
    $str = preg_replace('@<(/?)entry(?:\s(?:[^>]*?[^/>])?)?>@', '###($1td)###', $str);
    // handle gracefully inner tags <para>, </para>
    // or inner tag with attributes like <para xmlns="http://docbook.org/ns/docbook">
    $str = preg_replace('@<(/?)para(?:\s(?:[^>]*?[^/>])?)?>@', '###($1p)###', $str);
    // handle gracefully inner lists <*list>, </*list>
    $str = preg_replace('@<(/?).*?list(?:\s(?:[^>]*?[^/>])?)?>@', '###($1p)###', $str);
    // handle gracefully inner tags <listitem>
    $str = preg_replace('@<listitem(?:\s(?:[^>]*?[^/>])?)?>@', '###(br)###', $str);
    // now strip the remaining tags
    $str = strip_tags($str);
    // and restore the translated ones
    $str = str_replace("###(", "<", $str);
    $str = str_replace(")###", ">", $str);
    // convert some problematic php comment characters
    $str = strtr($str, array(
        "*" => "&#42;"
    ));

    // remove useless surrounding <p></p> tags...
    preg_match('@^(?:\s*<p>)+(.*?)(?:</p>\s*)+$@s', $str, $matches);
    if ($matches) {
        // ...only if there are no remaining <p> or </p> tags
        if (! preg_match('@</?p>@', $matches[1])) {
            $str = $matches[1];
        }
    }

    return $str;
}

function open_namespace($namespace)
{
    global $handleNamespaces;
    global $currentNamespace;
    global $addGlobalNSPrefix;
    global $countNamespacesInCurrentFile;
    if (! $handleNamespaces) {
        return;
    }
    $countNamespacesInCurrentFile ++;
    // Buffer the namespace declaration and its content
    // so they can both be dropped when the namespaced content is empty.
    ob_start();
    if ($namespace !== '') {
        $currentNamespace = $namespace;
        $addGlobalNSPrefix = '\\';
        print "\nnamespace {$currentNamespace} {\n\n";
    } else {
        $currentNamespace = '';
        $addGlobalNSPrefix = '';
        print "\nnamespace {\n\n";
    }
    ob_start();
}

/**
 * NB: setting $removeEnglobingNamespaceDeclaration to true should
 * only be used when there's a single namespace declaration in currently generated PHP file
 * and when this single namespace declaration is specifically a global namespace declaration.
 *
 * @param boolean $removeEnglobingNamespaceDeclaration
 */
function close_namespace($removeEnglobingNamespaceDeclaration = false)
{
    global $handleNamespaces;
    global $currentNamespace;
    global $addGlobalNSPrefix;
    global $countNamespacesInCurrentFile;
    if (! $handleNamespaces) {
        return;
    }
    if (trim(ob_get_contents()) !== '') {
        $currentNamespace = '';
        $addGlobalNSPrefix = '';
        if ($removeEnglobingNamespaceDeclaration) {
            // discard namespace declaration
            $countNamespacesInCurrentFile --;
            // keep content of namespace block...
            $innerContent = ob_get_flush();
            // ...but drop surrounding namespace declaration
            ob_end_clean();
            print $innerContent;
        } else {
            ob_end_flush();
            print "\n}\n\n";
            ob_end_flush();
        }
    } else {
        // discard namespace declaration
        $countNamespacesInCurrentFile --;
        ob_end_clean();
        ob_end_clean();
    }
}

/**
 * Prints usage help to the screen, and exits from program
 */
function show_help()
{
    global $argv0;

    die(<<<EOF
    USAGE: {$argv0} [options] <phpdocDir> [<phpDir>]
    
    Where:
      -help       Show this help.
      -nosplit    Do not split output to different files.
      <manual.xml> Compiled version of http://svn.php.net/repository/phpdoc/en/trunk, use doc-base/configure.php
      <phpDir>    The output directory. If not specified we'll use ./php<php-version>
    EOF);
}
