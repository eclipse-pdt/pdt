<?php
/**
 * This script can be used for generating PHP model for PDT.
 * It builds PHP functions according to the loaded extensions in running PHP,
 * using complementary information gathered from PHP.net documentation
 *
 * @author Michael Spector <michael@zend.com>
 * @author Thierry Blind <thierryblind@msn.com>
 */

if (version_compare(phpversion(), "5.0.0") < 0) {
	die ("This script requires PHP 5.0.0 or higher!\n");
}

$splitFiles = true;
$phpdocDir = null;

preg_match('/^[^.]+\.[^.]+/', phpversion(), $matches);
echo "PHP version: {$matches[0]}\n";
$phpDir = "php".$matches[0];

// Parse arguments:
$argv = $_SERVER["argv"];
$argv0 = array_shift ($argv);
for ($i = 0; $i < count($argv); ++$i) {
	switch ($argv[$i]) {
		case "-nosplit":
			$splitFiles = false;
			break;

		case "-help":
			show_help();
			break;

		default:
			$phpdocDir = $argv[$i];
	}
}

if (!$phpdocDir) {
	show_help();
}

$functionsDoc = parse_phpdoc_functions ($phpdocDir);
$classesDoc = parse_phpdoc_classes ($phpdocDir);
$constantsDoc = parse_phpdoc_constants ($phpdocDir);

$processedFunctions = array();
$processedClasses = array();
$processedConstants = array();

@mkdir ($phpDir);

if (!$splitFiles) {
	begin_file_output();
}
$extensions = get_loaded_extensions();
foreach ($extensions as $extName) {
	if ($splitFiles) {
		begin_file_output();
	}
	print_extension (new ReflectionExtension ($extName));
	if ($splitFiles) {
		finish_file_output("{$phpDir}/{$extName}.php");
	}
}

if ($splitFiles) {
	begin_file_output();
}
$intFunctions = get_defined_functions();
foreach ($intFunctions["internal"] as $intFunction) {
	if (!@$processedFunctions[strtolower($intFunction)]) {
		print_function (new ReflectionFunction ($intFunction));
	}
}

$intClasses = array_merge (get_declared_classes(), get_declared_interfaces());
foreach ($intClasses as $intClass) {
	if (!@$processedClasses[strtolower($intClass)]) {
		print_class (new ReflectionClass ($intClass));
	}
}

print "\n";
$constants = get_defined_constants(true);
$intConstants = @$constants["internal"];
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
foreach ($intConstants as $name => $value) {
	if (!@$processedConstants[$name]) {
		print_constant ($name, $value);
	}
}

finish_file_output("{$phpDir}/basic.php");

// Create .list file
$fp = fopen ("{$phpDir}/.list", "w");
foreach (glob("{$phpDir}/*.php") as $f) {
	fwrite ($fp, basename($f));
	fwrite ($fp, "\n");
}
fclose($fp);

echo 'Finished...';

// === Functions ===
/**
 * Makes generic key from given function name
 * @param $name string Function name
 * @return string generic key
 */
function make_funckey_from_str ($name) {
	$name = str_replace ("->", "::", $name);
	$name = str_replace ("()", "", $name);
	$name = strtolower ($name);
	return $name;
}

/**
 * Replaces all invalid characters with '_' in PHP identifier
 * @param $name string PHP identifier
 * @param $isInPhpdoc boolean true when it's a phpdoc identifier, false otherwise
 * @return string PHP identifier with stripped invalid characters
 */
function clean_php_identifier ($name, $isInPhpdoc = false) {
	if ($isInPhpdoc) {
		$name = preg_replace('/[^\$\w\\_\|\[\]]+/', '_', $name);
	} else {
		$name = preg_replace('/[^\$\w\\_]+/', '_', $name);
	}
	return $name;
}

/**
 * Creates valid type hint identifier
 * @param $name string type hint identifier
 * @return string valid type hint identifier
 * @see https://docs.phpdoc.org/guides/types.html#primitives
 * @see http://php.net/manual/en/functions.arguments.php
 */
function clean_php_type_hint ($name) {
	switch (strtolower($name)) {
		case "boolean":
		case "true":
		case "false":
			$name = "bool";
			break;
		case "integer":
			$name = "int";
			break;
	}
	return $name;
}

/**
 * Makes generic key from given function reference
 * @param $name ReflectionMethod function reference
 * @return string generic key
 */
function make_funckey_from_ref ($ref) {
	if ($ref instanceof ReflectionMethod) {
		$funckey = strtolower($ref->getDeclaringClass()->getName())."::".strtolower($ref->getName());
	} else {
		$funckey = strtolower($ref->getName());
	}
	return $funckey;
}

/**
 * Splits text using regular expressions $regExpOpenTag and $regExpCloseTag as open and close tags.
 * When tags are nested, texts with uppermost tags are returned.
 * @param $regExpOpenTag string regular expression defining open tags
 * @param $regExpCloseTag string regular expression defining close tags
 * @param $text string text to parse
 * @return array list of texts delimited by open and close tags
 */
function parse_tags_content($regExpOpenTag, $regExpCloseTag, $text) {
	if (! preg_match_all ($regExpOpenTag, $text, $matches, PREG_OFFSET_CAPTURE)) {
		return array();
	}
	$openTags = $matches[0];
	if (! preg_match_all ($regExpCloseTag, $text, $matches, PREG_OFFSET_CAPTURE)) {
		return array();
	}
	$closeTags = $matches[0];

	$nbOpenTags = 0;
	$startContentPos = $endContentPos = - 1;
	$contents = array();

	while (count ($openTags) && count ($closeTags)) {
		if ($openTags[0][1] < $closeTags[0][1]) {
			$nbOpenTags ++;
			if ($nbOpenTags == 1) {
				$startContentPos = $openTags[0][1];
			}
			array_shift ($openTags);
		} else {
			$nbOpenTags --;
			if ($nbOpenTags < 0) {
				return array();
			}
			if ($nbOpenTags == 0) {
				$endContentPos = $closeTags[0][1] + strlen ($closeTags[0][0]);
				$contents[] = substr ($text, $startContentPos, $endContentPos - $startContentPos);
				$startContentPos = $endContentPos = - 1;
			}
			array_shift ($closeTags);
		}
	}

	if ($startContentPos >= 0 && count ($closeTags) == $nbOpenTags) {
		$lastTag = array_pop ($closeTags);
		$contents[] = substr ($text, $startContentPos, $lastTag[1] + strlen ($lastTag[0]) - $startContentPos);
		return $contents;
	}

	return array();
}

/**
 * Parses PHP documentation
 * @param $phpdocDir string PHP.net documentation directory
 * @return array Function information gathered from the PHP.net documentation by parsing XML files
 */
function parse_phpdoc_functions ($phpdocDir) {
	$xml_files = array_merge (
		glob ("{$phpdocDir}/reference/*/*/*.xml")
	);
	$functionsDoc = array();
	foreach ($xml_files as $xml_file) {
		$xml = load_xml ($xml_file);

		if (preg_match ('@<refentry.*?xml:id=["\'](.*?)["\'].*?>.*?<refname(?:\s(?:[^>]*?[^/>])?)?>(.*?)</refname>.*?<refpurpose(?:\s(?:[^>]*?[^/>])?)?>(.*?)</refpurpose>@s', $xml, $match)) {

			$refname = make_funckey_from_str ($match[2]);
			$functionsDoc[$refname]['id'] = $match[1];
			$functionsDoc[$refname]['quickref'] = trim($match[3]);

			if (preg_match ('@<refsect1\s+role=["\']description["\']>(.*?)</refsect1>@s', $xml, $match)) {
				$description = $match[1];
				$function_alias = null;
				$parameters = null;
				$has_object_style = false;
				if (preg_match ('@^(.*?)<classsynopsis(?:\s(?:[^>]*?[^/>])?)?>.*?<classname(?:\s(?:[^>]*?[^/>])?)?>(.*)</classname>.*?<methodsynopsis(?:\s(?:[^>]*?[^/>])?)?>.*?<type(?:\s(?:[^>]*?[^/>])?)?>(.*?)</type>.*?<methodname(?:\s(?:[^>]*?[^/>])?)?>(.*?)</methodname>(.*?)</methodsynopsis>.*?</classsynopsis>(.*)$@s', $description, $match)) {
					$functionsDoc[$refname]['classname'] = trim($match[2]);
					$functionsDoc[$refname]['returntype'] = trim($match[3]);
					$functionsDoc[$refname]['methodname'] = trim($match[4]);
					$parameters = $match[5];
					$description = $match[1].$match[6];
					$has_object_style = true;
				}
				if (preg_match ('@^(.*?)<classsynopsis(?:\s(?:[^>]*?[^/>])?)?>.*?<classname(?:\s(?:[^>]*?[^/>])?)?>(.*)</classname>.*?<constructorsynopsis(?:\s(?:[^>]*?[^/>])?)?>.*?<methodname(?:\s(?:[^>]*?[^/>])?)?>(.*?)</methodname>(.*?)</constructorsynopsis>.*?</classsynopsis>(.*)$@s', $description, $match)) {
					$functionsDoc[$refname]['classname'] = trim($match[2]);
					$functionsDoc[$refname]['methodname'] = trim($match[3]);
					$parameters = $match[4];
					$description = $match[1].$match[5];
					$has_object_style = true;
				}
				if (preg_match ('@<methodsynopsis(?:\s(?:[^>]*?[^/>])?)?>.*?<type(?:\s(?:[^>]*?[^/>])?)?>(.*?)</type>.*?<methodname(?:\s(?:[^>]*?[^/>])?)?>(.*?)</methodname>(.*?)</methodsynopsis>@s', $description, $match)) {
					if ($has_object_style) {
						$function_alias = trim($match[2]);
					} else {
						//For return type of simplexml_load_string and simplexml_load_file.
						if(preg_match ('@<refsect1\s+role=["\']returnvalues["\']>(.*?)</refsect1>@s', $xml, $match1)){
							$returnvalues = $match1[1];
							if(preg_match ('@<type(?:\s(?:[^>]*?[^/>])?)?>object</type> of class <type(?:\s(?:[^>]*?[^/>])?)?>(.*?)</type>@s', $returnvalues, $match1)){
								$functionsDoc[$refname]['returntype'] = trim($match1[1]);
							}else{
								$functionsDoc[$refname]['returntype'] = trim($match[1]);
							}
						}
						else{
							$functionsDoc[$refname]['returntype'] = trim($match[1]);
						}
						$functionsDoc[$refname]['methodname'] = trim($match[2]);
						$parameters = $match[3];
					}
				}
				if ($parameters) {
					if (preg_match_all ('@<methodparam\s*(.*?)>.*?<type(?:\s(?:[^>]*?[^/>])?)?>(.*?)</type>.*?<parameter\s*(.*?)>(.*?)</parameter>.*?</methodparam>@s', $parameters, $match)) {
						for ($i = 0; $i < count($match[0]); ++$i) {
							$parameter = array (
								'type' => trim($match[2][$i]),
								'name' => clean_php_identifier(trim($match[4][$i])),
							);
							if (preg_match ('@choice=[\'"]opt[\'"]@', $match[1][$i])) {
								$parameter['isoptional'] = true;
							}
							if (preg_match ('@role=[\'"]reference[\'"]@', $match[3][$i])) {
								$parameter['isreference'] = true;
							}
							$functionsDoc[$refname]['parameters'][] = $parameter;
						}
					}
				}
			}
			if (preg_match ('@<refsect1\s+role=["\']parameters["\']>(.*?)</refsect1>@s', $xml, $match)) {
				$parameters = $match[1];
				foreach (parse_tags_content ('@<varlistentry(?:\s(?:[^>]*?[^/>])?)?>@s', '@</varlistentry>@s', $parameters) as $parameter) {
					if (preg_match_all('@<varlistentry(?:\s(?:[^>]*?[^/>])?)?>.*?<parameter(?:\s(?:[^>]*?[^/>])?)?>(.*?)</parameter>.*?<listitem(?:\s(?:[^>]*?[^/>])?)?>(.*)</listitem>.*?</varlistentry>@s', $parameter, $match)) {
						for ($i = 0; $i < count($match[0]); $i++) {
							for ($j = 0; $j < count(@$functionsDoc[$refname]['parameters']); $j++) {
								if ($match[1][$i] == $functionsDoc[$refname]['parameters'][$j]['name']) {
									$functionsDoc[$refname]['parameters'][$j]['paramdoc'] = xml_to_phpdoc ($match[2][$i]);
									break;
								}
							}
						}
					}
				}
			}
			if (preg_match ('@<refsect1\s+role=["\']returnvalues["\']>(.*?)</refsect1>@s', $xml, $match)) {
				$returnvalues = $match[1];
				if (preg_match ('@<para(?:\s(?:[^>]*?[^/>])?)?>\s*(Returns)?(.*</para>)@s', $returnvalues, $match)) {
					$returndoc = $match[2];
					$pos = strpos ($returndoc, "</para>");
					if ($pos !== false) {
						// remove orphan "</para>" tag
						$returndoc = substr ($returndoc, 0, $pos) . substr ($returndoc, $pos + 7 /* = strlen("</para>") */);
					}
					$functionsDoc[$refname]['returndoc'] = xml_to_phpdoc ($returndoc);
				}
			}

			if (preg_match ('@&warn\.deprecated\.func-(.*?);@s', $xml, $match)) {
				$deprecatedSince = $match[1];
				$functionsDoc[$refname]['deprecated'] = 'Since ' . str_replace('-', '.',$deprecatedSince);
			}

			// Create information for function alias
			if ($function_alias) {
				$functionsDoc[$function_alias] = $functionsDoc[$refname];
			}
		}
	}
	return $functionsDoc;
}

/**
 * Parses PHP documentation
 * @param $phpdocDir string PHP.net documentation directory
 * @return array Class information gathered from the PHP.net documentation by parsing XML files
 */
function parse_phpdoc_classes ($phpdocDir) {
	$classesDoc = array();
	$xml_files = array_merge (
		glob ("{$phpdocDir}/reference/*/reference.xml"),
		glob ("{$phpdocDir}/reference/*/classes.xml"),
		glob ("{$phpdocDir}/language/*/*.xml"),
		glob ("{$phpdocDir}/language/*.xml")
	);

	global $fields_doc;

	foreach ($xml_files as $xml_file) {
		$xml = load_xml ($xml_file);
		if (preg_match ('@xml:id=["\'](.*?)["\']@', $xml, $match)) {
			$id = $match[1];
			if (preg_match_all ('@<titleabbrev(?:\s(?:[^>]*?[^/>])?)?>(.*?)</titleabbrev>@', $xml, $match)) {
				for ($i = 0; $i < count($match[0]); ++$i) {
					$class = $match[1][$i];
					$refname = strtolower ($class);
					$classesDoc[$refname]['id'] = $id;
					$classesDoc[$refname]['name'] = $class;

					if (preg_match ( '@<section(?:\s+[^>]+)?>(.*?)</section>@s', $xml, $match2 )) {
						if (preg_match_all ( '@<para(?:\s(?:[^>]*?[^/>])?)?>(.*?)</para>@s',  $match2 [1], $match3 )) {
							$doc =  xml_to_phpdoc ( $match3 [1][0] );
							for($i = 1; $i < count ( $match3 [1] ); ++ $i) {
								$doc .= "\n<p>" . xml_to_phpdoc ( $match3 [1][$i] ) . "</p>";
							}
						}
						$classesDoc [$refname] ['doc'] = $doc;
					}
					//pass over class fields here
					$fields_xml_file = array_merge (
						glob ("{$phpdocDir}/reference/*/" . $refname . ".xml")
					);
					if (count($fields_xml_file) == 0) {
						echo "Documentation for $refname not exists! \n";
						continue;
					}
					if($fields_xml_file[0] != null) {
						$xml_field_data = load_xml ($fields_xml_file[0]);
						if($xml_field_data != null) {

							if(preg_match_all ('@<fieldsynopsis(?:\s(?:[^>]*?[^/>])?)?>((\w|\W|\s)*?)</fieldsynopsis>@', $xml_field_data, $fieldsynopsis_list)) {
								foreach ($fieldsynopsis_list[1] as $fieldsynopsis) {

									if(preg_match_all('@<varname\s*linkend="'.$refname.'\.props\.(.*?)">(.*?)</varname>@', $fieldsynopsis, $varname)) {
										$field_name = $varname[2][0];
										$fields_doc[$refname][$field_name]['name'] = $field_name;
									}

									// <varlistentry xml:id="domdocument.props.formatoutput">
									//  <term><varname>formatOutput</varname></term>
									//  <listitem>
									//   <para>Nicely formats output with indentation and extra space.</para>
									//  </listitem>
									// </varlistentry>
									if (preg_match ('@<varlistentry.*?<term(?:\s(?:[^>]*?[^/>])?)?><varname(?:\s(?:[^>]*?[^/>])?)?>'.$field_name.'</varname></term>.*?<para(?:\s(?:[^>]*?[^/>])?)?>(.*?)</para>@s', $xml_field_data, $doc)) {
										if(preg_match ('@<emphasis(?:\s(?:[^>]*?[^/>])?)?>Deprecated</emphasis>@s', $doc[1], $deprecated)) {
											$fields_doc[$refname][$field_name]['deprecated'] = true;
										}else {
											$fields_doc[$refname][$field_name]['deprecated'] = false;
										}
										$fields_doc[$refname][$field_name]['doc'] = xml_to_phpdoc($doc[1]);
									}

									if(preg_match_all('@<modifier(?:\s(?:[^>]*?[^/>])?)?>(.*?)</modifier>@', $fieldsynopsis, $modifier_list)) {
										foreach ($modifier_list[1] as $current_modifier) {
											if($current_modifier == "readonly") {
												continue;
											}else {
												$modifier = $current_modifier;
												break;
											}
										}

										//go to the next field if not public ???
										if($modifier == "private") {
											continue;
										}
										$fields_doc[$refname][$field_name]['modifier'] = $modifier;
									}

									if(preg_match_all('@<type(?:\s(?:[^>]*?[^/>])?)?>(.*?)</type>@', $fieldsynopsis, $type)) {
										$field_type = $type[1][0];
										$fields_doc[$refname][$field_name]['type'] = $field_type;
									}
								}
							}
						}
					}

				}
			}
		}
	}
	return $classesDoc;
}

/**
 * Parses PHP documentation
 * @param $phpdocDir string PHP.net documentation directory
 * @return array Constant information gathered from the PHP.net documentation by parsing XML files
 */
function parse_phpdoc_constants ($phpdocDir) {
	$constantsDoc = array();
	exec ("find ".addslashes($phpdocDir)." -name \"*constants.xml\"", $xml_files);
	foreach ($xml_files as $xml_file) {
		$xml = load_xml ($xml_file);
		if (preg_match ('@xml:id=["\'](.*?)["\']@', $xml, $match)) {
			$id = $match[1];
			if (preg_match_all ('@<term(?:\s(?:[^>]*?[^/>])?)?>\s*<constant(?:\s(?:[^>]*?[^/>])?)?>([a-zA-Z_][a-zA-Z0-9_]*)</constant>.*?</term>.*?<listitem(?:\s(?:[^>]*?[^/>])?)?>(.*?)</listitem>@s', $xml, $match)) {
				for ($i = 0; $i < count($match[0]); ++$i) {
					$constant = $match[1][$i];
					$constantsDoc[$constant]['id'] = $id;
					$constantsDoc[$constant]['doc'] = xml_to_phpdoc($match[2][$i]);
				}
			}
			if (preg_match_all (
				'@<entry(?:\s(?:[^>]*?[^/>])?)?>\s*<constant(?:\s(?:[^>]*?[^/>])?)?>([a-zA-Z_][a-zA-Z0-9_]*)</constant>.*?</entry>\s*<entry(?:\s(?:[^>]*?[^/>])?)?>\d+</entry>\s*<entry(?:\s(?:[^>]*?[^/>])?)?>(.*?)</entry>@s', $xml, $match)
				|| preg_match_all ('@<entry(?:\s(?:[^>]*?[^/>])?)?>\s*<constant(?:\s(?:[^>]*?[^/>])?)?>([a-zA-Z_][a-zA-Z0-9_]*)</constant>.*?</entry>\s*<entry(?:\s(?:[^>]*?[^/>])?)?>(.*?)</entry>@s', $xml, $match)) {

				for ($i = 0; $i < count($match[0]); ++$i) {
					$constant = $match[1][$i];
					$constantsDoc[$constant]['id'] = $id;
					$constantsDoc[$constant]['doc'] = xml_to_phpdoc($match[2][$i]);
				}
			}
		}
	}
	return $constantsDoc;
}

/**
 * Prints ReflectionExtension in format of PHP code
 * @param $extRef ReflectionExtension object
 */
function print_extension ($extRef) {
	print "\n// Start of {$extRef->getName()} v.{$extRef->getVersion()}\n";

	// process classes:
	$classesRef = $extRef->getClasses();
	if (count ($classesRef) > 0) {
		foreach ($classesRef as $classRef) {
			print_class ($classRef);
		}
	}

	// process functions
	$funcsRef = $extRef->getFunctions();
	if (count ($funcsRef) > 0) {
		foreach ($funcsRef as $funcRef) {
			print_function ($funcRef);
		}
		print "\n";
	}

	// process constants
	$constsRef = $extRef->getConstants();
	if (count ($constsRef) > 0) {
		print_constants ($constsRef);
		print "\n";
	}

	print "// End of {$extRef->getName()} v.{$extRef->getVersion()}\n";
}

/**
 * Prints ReflectionClass in format of PHP code
 * @param $classRef ReflectionClass object
 * @param $tabs integer[optional] number of tabs for indentation
 */
function print_class ($classRef, $tabs = 0) {
	global $processedClasses;
	$processedClasses [strtolower($classRef->getName())] = true;

	print "\n";
	print_doccomment ($classRef, $tabs);
	print_tabs ($tabs);
	if ($classRef->isFinal()) print "final ";
	if (!$classRef->isInterface() && $classRef->isAbstract()) print "abstract ";

	print $classRef->isInterface() ? "interface " : "class ";
	print clean_php_identifier($classRef->getName())." ";

	// print out parent class
	$parentClassRef = $classRef->getParentClass();
	if ($parentClassRef) {
		print "extends {$parentClassRef->getName()} ";
	}

	// print out interfaces
	$interfacesRef = $classRef->getInterfaces();
	if (count ($interfacesRef) > 0) {
		print $classRef->isInterface() ? "extends " : "implements ";
		$i = 0;
		foreach ($interfacesRef as $interfaceRef) {
			if ($i++ > 0) {
				print ", ";
			}
			print "{$interfaceRef->getName()}";
		}
	}
	print " {\n";

	// process constants
	$constsRef = $classRef->getConstants();
	if (count ($constsRef) > 0) {
		print_class_constants ($constsRef, $tabs + 1);
		print "\n";
	}

	global $classesDoc;

	// process properties
	$propertiesRef = $classRef->getProperties();
	if (count ($propertiesRef) > 0) {
		foreach ($propertiesRef as $propertyRef) {
			print_property ($propertyRef, $tabs + 1);
			$printedFields[$propertyRef->getName()] = true;
		}
		print "\n";
	}

	$className = strtolower($classRef->getName());
	if($className == "DomDocument") {
		echo "DomDocument";
	}

	global $fields_doc;
	if (@$fields_doc[$className]) {
		$fields = @$fields_doc[$className];
		foreach ($fields as $field) {
			if(!$printedFields[$field['name']]) {

				//print doc here
				print("\n");
				$doc = $field['doc'];
				if ($doc) {
					print_tabs ($tabs + 1);
					print "/**\n";
					print_tabs ($tabs + 1);
					print " * ".newline_to_phpdoc($doc, $tabs + 1)."\n";
					print_tabs ($tabs + 1);
					if (clean_php_identifier ($field['type'], true) === $field['type']) {
						print " * @var " . $field['type'] . "\n";
					} else {
						print " * @var mixed\n";
					}
					if($field['deprecated'] == true) {
						print_tabs ($tabs + 1);
						print " * @deprecated "."\n";
					}

					print_Tabs ($tabs + 1);
					// http://www.php.net/manual/en/class.domdocument.php#domdocument.props.actualencoding
					$refname = strtolower($classRef->getName());
					$class_url = make_url ("class." . $refname);
					$field_name = strtolower($field['name']);
					$field_url = $class_url . '#' . $className . ".props." . $field_name;
					print " * @link {$field_url}\n";

					print_tabs ($tabs + 1);
					print " */\n";
				}

				print_tabs ($tabs + 1);
				print implode(' ', array($field['modifier']));
				print " ";
				print "\${$field['name']};\n";
			}
		}
	}

	// process methods
	$methodsRef = $classRef->getMethods();
	if (count ($methodsRef) > 0) {
		$printedMethods = array();
		$cleanName = clean_php_identifier($classRef->getName());
		foreach ($methodsRef as $methodRef) {
			$cleanMName = clean_php_identifier($methodRef->getName());
			// bug 415896
			if ($cleanMName == '__construct' && isset($printedMethods[$cleanName])) {
				continue;
			}
			$printedMethods[$cleanMName] = $methodRef;
			print_function ($methodRef, $tabs + 1);
		}
		print "\n";
	}
	print_tabs ($tabs);
	print "}\n";
}

/**
 * Prints ReflectionProperty in format of PHP code
 * @param $propertyRef ReflectionProperty object
 * @param $tabs integer[optional] number of tabs for indentation
 */
function print_property ($propertyRef, $tabs = 0) {
	print_doccomment ($propertyRef, $tabs);
	print_tabs ($tabs);
	print_modifiers ($propertyRef, array("abstract"));
	print "\${$propertyRef->getName()};\n";
}

function print_function ($functionRef, $tabs = 0) {
	global $functionsDoc;
	global $processedFunctions;

	$funckey = make_funckey_from_ref ($functionRef);
	$processedFunctions[$funckey] = true;

	print "\n";
	print_doccomment ($functionRef, $tabs);
	print_tabs ($tabs);
	if (!($functionRef instanceof ReflectionFunction)) {
		print_modifiers ($functionRef);
	}

	print "function ";
	if ($functionRef->returnsReference()) {
		print "&";
	}
	print "{$functionRef->getName()} (";
	$parameters = @$functionsDoc[$funckey]['parameters'];
	if ($parameters) {
		print_parameters ($parameters);
	} else {
		print_parameters_ref ($functionRef->getParameters());
	}
	if ($functionRef instanceof ReflectionMethod && $functionRef->isAbstract()) {
		print ");\n";
	} else {
		print ") {}\n";
	}
}

/**
 * Prints ReflectionParameter in format of PHP code
 * @param $parameters array information from PHP.net documentation
 */
function print_parameters ($parameters) {
	$i = 0;
	foreach ($parameters as $parameter) {
		if ($parameter['name'] != "...") {
			if ($i++ > 0) {
				print ", ";
			}
			$type = $parameter['type'];
			if ($type) {
				$type = clean_php_type_hint ($type);
			}
			// http://php.net/manual/en/functions.arguments.php
			if ($type) {
				$lowerType = strtolower ($type);
				if (class_exists ($type) && clean_php_identifier ($type) === $type) {
					print "{$type} ";
				} else if ((version_compare (phpversion(), "5.1.0") >= 0 && ($lowerType == "array"))
					|| (version_compare (phpversion(), "5.4.0") >= 0 && ($lowerType == "callable"))
					|| (version_compare (phpversion(), "7.0.0") >= 0 && ($lowerType == "bool" || $lowerType == "float" || $lowerType == "int" || $lowerType == "string"))) {
					print "{$lowerType} ";
				}
			}
			if (@$parameter['isreference']) {
				print "&";
			}
			print "\${$parameter['name']}";
			if (@$parameter['isoptional']) {
				if (@$parameter['defaultvalue']) {
					$value = $parameter['defaultvalue'];
					if (!is_numeric ($value)) {
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
 * @param $paramsRef ReflectionParameter[] array of objects
 */
function print_parameters_ref ($paramsRef) {
	$i = 0;
	foreach ($paramsRef as $paramRef) {
		if ($paramRef->isArray()) {
			print "array ";
		} else {
			if (($className = get_parameter_classname ($paramRef)) && clean_php_identifier ($className) === $className) {
				print "{$className} ";
			}
		}
		$name = $paramRef->getName() ? $paramRef->getName() : "var".($i+1);
		if ($name != "...") {
			if ($i++ > 0) {
				print ", ";
			}
			if ($paramRef->isPassedByReference()) {
				print "&";
			}
			if (version_compare(phpversion(), "5.6.0") >= 0 && $paramRef->isVariadic()) {
				print "...\${$name}";
				// variadic parameters do not support default values
				continue;
			}
			print "\${$name}";
			if ($paramRef->allowsNull()) {
				print " = null";
			} else if ($paramRef->isDefaultValueAvailable() || $paramRef->isOptional()) {
				$value = "null";
				if ($paramRef->isDefaultValueAvailable() ) {
					$value = $paramRef->getDefaultValue();
				}
				if (!is_numeric ($value) && $value != "null") {
					$value = "'{$value}'";
				}
				print " = {$value}";
			}
		}
	}
}

/**
 * Prints constants in format of PHP code
 * @param $constants array containing constants, where key is a name of constant
 * @param $tabs integer[optional] number of tabs for indentation
 */
function print_constants ($constants, $tabs = 0) {
	foreach ($constants as $name => $value) {
		print_constant ($name, $value, $tabs);
	}
}

function print_constant ($name, $value = null, $tabs = 0) {
	global $constantsDoc;
	global $processedConstants;
	$processedConstants [$name] = true;

	if ($value === null) {
		$value = @constant ($name);
	}
	$value = escape_const_value ($value);

	$doc = @$constantsDoc[$name]['doc'];
	if ($doc || @$constantsDoc[$name]['id']) {
		print "\n";
		print_tabs ($tabs);
		print "/**\n";
		print_tabs ($tabs);
		print " * ".newline_to_phpdoc($doc, $tabs)."\n";
		print_tabs ($tabs);
		print " * @link ".make_url($constantsDoc[$name]['id'])."\n";
		print_tabs ($tabs);
		print " */\n";
	}
	print_tabs ($tabs);
	print "define ('{$name}', {$value});\n";
}

function escape_const_value ($value) {
	if (is_resource($value)) {
		$value = "\"${value}\"";
	} else if (!is_numeric ($value) && !is_bool ($value) && $value !== null) {
		if($value=== '\\'){
			$value = '"'.addcslashes ($value, "\\\"\r\n\t").'"';
		}
		else{
			$value = '"'.addcslashes ($value, "\"\r\n\t").'"';
		}
	} else if ($value === null) {
		$value = "null";
	} else if ($value === false) {
		$value = "false";
	} else if ($value === true) {
		$value = "true";
	}
	return $value;
}

/**
 * Prints class constants in format of PHP code
 * @param $constants array containing constants, where key is a name of constant
 * @param $tabs integer[optional] number of tabs for indentation
 */
function print_class_constants ($constants, $tabs = 0) {
	foreach ($constants as $name => $value) {
		$value = escape_const_value ($value);
		print_tabs ($tabs);
		print "const {$name} = {$value};\n";
	}
}

/**
 * Prints modifiers of reflection object in format of PHP code
 * @param $ref Reflection some reflection object
 * @param $excludeModifierKeywords array exclude modifier keywords
 */
function print_modifiers ($ref, $excludeModifierKeywords = array()) {
	$modifiers = Reflection::getModifierNames ($ref->getModifiers());
	$modifiers = array_diff ($modifiers, $excludeModifierKeywords);
	if (count ($modifiers) > 0) {
		print implode(' ', $modifiers);
		print " ";
	}
}

/**
 * Makes PHP Manual URL from the given ID
 * @param $id PHP Element ID
 * @return string URL
 */
function make_url ($id) {
	return "http://www.php.net/manual/en/{$id}.php";
}

/**
 * Prints PHPDOC comment before specified reflection object
 * @param $ref Reflection some reflection object
 * @param $tabs integer[optional] number of tabs for indentation
 */
function print_doccomment ($ref, $tabs = 0) {
	global $functionsDoc;
	global $classesDoc;

	$docComment = $ref->getDocComment();
	if ($docComment) {
		print_tabs ($tabs);
		print "{$docComment}\n";
	}
	else if ($ref instanceof ReflectionClass) {
		$refname = strtolower($ref->getName());
		if (@$classesDoc[$refname]) {
			print_tabs ($tabs);
			print "/**\n";
			$doc = @$classesDoc[$refname]['doc'];
			if ($doc) {
				$doc = newline_to_phpdoc ($doc, $tabs);
				print_tabs ($tabs);
				print " * {$doc}\n";
			}
			if (@$classesDoc[$refname]['id']) {
				print_Tabs ($tabs);
				$url = make_url ("class." . $refname);
				print " * @link {$url}\n";
			}
			print_tabs ($tabs);
			print " */\n";
		}
	}
	else if ($ref instanceof ReflectionFunctionAbstract) {
		$funckey = make_funckey_from_ref ($ref);
		$returntype = @$functionsDoc[$funckey]['returntype'];
		$desc = @$functionsDoc[$funckey]['quickref'];
		$deprecated = @$functionsDoc[$funckey]['deprecated'];
		$returndoc = newline_to_phpdoc (@$functionsDoc[$funckey]['returndoc'], $tabs);

		$paramsRef = $ref->getParameters();
		$parameters = @$functionsDoc[$funckey]['parameters'];

		if ($desc || count ($paramsRef) > 0 || $parameters || $returntype) {
			print_tabs ($tabs);
			print "/**\n";
			if ($desc) {
				print_tabs ($tabs);
				print " * " . newline_to_phpdoc(xml_to_phpdoc($desc), $tabs) . "\n";
			}
			if (@$functionsDoc[$funckey]['id']) {
				print_tabs ($tabs);
				$url = make_url ($functionsDoc[$funckey]['id']);
				print " * @link {$url}\n";
			}
			if ($parameters) {
				foreach ($parameters as $parameter) {
					print_tabs ($tabs);
					if (clean_php_identifier ($parameter['type'], true) === $parameter['type']) {
						print " * @param {$parameter['type']} \${$parameter['name']}";
					} else {
						print " * @param mixed \${$parameter['name']}";
					}
					if (@$parameter['isoptional']) {
						print " [optional]";
					}
					$paramdoc = newline_to_phpdoc (@$parameter['paramdoc'], $tabs);
					print " {$paramdoc}";
					print "\n";
				}
			} else {
				$i = 0;
				foreach ($paramsRef as $paramRef) {
					print_tabs ($tabs);
					$name = $paramRef->getName() ? $paramRef->getName() : "var".++$i;
					print " * @param";
					if ($className = get_parameter_classname($paramRef)) {
						if (clean_php_identifier ($className, true) === $className) {
							print " {$className}";
						} else {
							print " mixed";
						}
						if ($paramRef->isArray()) {
							print "[]";
						}
					}
					print " \${$name}";
					if ($paramRef->isOptional()) {
						print " [optional]";
					}
					print "\n";
				}
			}
			if ($returntype) {
				print_tabs ($tabs);
				if (clean_php_identifier ($returntype, true) === $returntype) {
					print " * @return {$returntype} {$returndoc}\n";
				} else {
					print " * @return mixed {$returndoc}\n";
				}
			}
			if ($ref->isDeprecated() || $deprecated) {
				print_tabs ($tabs);
				print " * @deprecated {$deprecated}\n";
			}
			print_tabs ($tabs);
			print " */\n";
		}
	} else if ($ref instanceof ReflectionProperty) {
		//TODO complete phpdoc for fields detected by reflection
	}
}

/**
 * Converts XML entities to human readable string for PHPDOC
 * @param $str string
 * @return string
 */
function xml_to_phpdoc ($str) {
	$str = str_replace ("&return.success;", "Returns true on success or false on failure.", $str);
	$str = str_replace ("&return.void;", "", $str);
	$str = str_replace("&Alias;", "Alias:", $str);
	// rewrite all non-html entities like "&null;", "&true;", "&false;"
	// as "null", "true", "false"
	preg_match_all ('@&([a-zA-Z0-9._-]+);@s', $str, $matches);
	if ($matches) {
		foreach ($matches[1] as $v) {
			if (html_entity_decode ("&" . $v . ";") === "&" . $v . ";") {
				$str = str_replace ("&" . $v . ";", $v, $str);
			}
		}
	}
	$str = strip_tags_special ($str);
	$str = preg_replace ('/  +/', " ", $str);
	$str = preg_replace ('/[\r\n][\t ]/', "\n", $str);
	$str = trim ($str);
	return $str;
}

/**
 * Converts newlines to PHPDOC prefixes in the given string
 * @param $str string
 * @param $tabs integer[optional] number of tabs for indentation
 * @return string PHPDOC string
 */
function newline_to_phpdoc ($str, $tabs = 0) {
	$str = preg_replace ('@([\r\n]+\s*)+@', "\n".str_repeat("\t", $tabs)." * ", $str);
	return $str;
}

/**
 * Prints specified number of tabs
 * @param $tabs integer number of tabs to print
 */
function print_tabs ($tabs) {
	print str_repeat("\t", $tabs);
}

/**
 * Returns class name from given parameter reference, this method is a workaround
 * for the case when exception is thrown from getClass() when such classname does not exist.
 */
function get_parameter_classname(ReflectionParameter $paramRef) {
	try {
		if ($classRef = $paramRef->getClass()) {
			return $classRef->getName();
		}
	} catch (Exception $e) {
		if (preg_match('/Class (\w+) does not exist/', $e->getMessage(), $matches)) {
			return $matches[1];
		}
	}
	return null;
}

/**
 * Starts outputing to the new file
 */
function begin_file_output() {
	ob_start();
	print "<?php\n";
}

/**
 * Ends outputing, and dumps the output to the specified file
 * @param $filename File to dump the output
 */
function finish_file_output($filename) {
	//if (file_exists ($filename)) {
	//	rename ($filename, "{$filename}.bak");
	//}
	//print "? >";
	file_put_contents ($filename, ob_get_contents());
	ob_end_clean();
}

/**
 * Strips xml tags from the string like the standard strip_tags() function
 * would do, but also translates some of the docbook tags (such as tables
 * an paragraphs) to proper html tags
 * @param $str string
 * @return string
 */
function strip_tags_special ($str) {
	// first mask and translate the tags to preseve
	$str = preg_replace('@<(/?)(table|tgroup)(?:\s(?:[^>]*?[^/>])?)?>@', '###($1table)###', $str);
	$str = preg_replace('@<row(?:\s(?:[^>]*?[^/>])?)?>@', "###(tr valign=\"top\")###", $str);
	$str = str_replace("</row>", "###(/tr)###", $str);

	// Bug 522585 - Documentation of PHP function header() is misformatted
	$str = str_replace ("<![CDATA[", "###(pre)###", $str);
	$str = str_replace ("]]>", "###(/pre)###", $str);
	preg_match_all ('@(<\?php.*?\?>)@si', $str, $matches);
	if ($matches) {
		foreach ($matches[1] as $v) {
			$str = str_replace ($v, "###(code)###" . strtr (htmlspecialchars ($v), array(
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
	$str = strip_tags ($str);
	// and restore the translated ones
	$str = str_replace ("###(", "<", $str);
	$str = str_replace (")###", ">", $str);
	// convert some problematic php comment characters
	$str = strtr ($str, array(
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

function load_entities()
{
	global $phpdocDir;
	$result = array();
	$needExpand = [];
	$iterator = new RecursiveIteratorIterator(new RecursiveDirectoryIterator($phpdocDir));
	foreach ($iterator as $file) {
		if (! $file->isFile() || !in_array($file->getExtension(), array("xml", "ent"))) {
			continue;
		}
		preg_match_all('/<!ENTITY\s+([a-z0-9.]+)\s+\'(.*?)\'>/i', file_get_contents($file->getPathname()), $matches);
		if ($matches) {
			foreach ($matches[1] as $k => $v) {
				$result[$v] = $matches[2][$k];
			}
		}
	}
	$result['false'] = 'false';
	$result['true'] = 'true';
	$result['return.void'] = '';
	$result['return.success'] = 'Returns true on success or false on failure';

	$names = array();
	foreach (array_keys($result) as $v) {
		$names[] = '&' . $v . ';';
	}

	// XXX: fill nested values
	for ($i = 0; $i < 80; $i++) {
		array_map(function ($val) use($names, $result)
		{
			return str_replace($names, array_values($result), $val);
		}, $result);
	}

	return $result;
}

function load_xml($str) {
	static $entities_names = null, $entities_values = null;
	if (null === $entities_names) {
		$entities = load_entities();
		$entities_values = array_values($entities);
		$entities_names = array();
		foreach (array_keys($entities) as $v) {
			$entities_names[] = '&' . $v . ';';
		}
	}

	return str_replace($entities_names, $entities_values, file_get_contents($str));
}

/**
 * Prints usage help to the screen, and exits from program
 */
function show_help() {
	global $argv0;

	die (<<<EOF
USAGE: {$argv0} [options] <PHP.net documentation directory>

Where options are:

-help	Show this help.
-split	Split output to different files (one file per PHP extension).

EOF
	);
}
