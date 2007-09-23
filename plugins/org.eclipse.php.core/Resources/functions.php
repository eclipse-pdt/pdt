<?php
/**
 * This script can be used for generiting PHP model for PDT.
 * It builds PHP functions according to the loaded extensions in running PHP,
 * using complementary information gathered from PHP.net documentation
 *
 * @author Michael Spector <michael@zend.com>
 */

if (version_compare(phpversion(), "5.0.0") < 0) {
	die ("This script requires PHP 5.0.0 or higher!\n");
}

$argv = $_SERVER["argv"];
$argv0 = array_shift ($argv);
$phpdoc_dir = array_shift ($argv);
if (!$phpdoc_dir) {
	die ("USAGE: $argv0 <PHP.net documentation directory>\n");
}

$functions = parse_phpdoc_functions ($phpdoc_dir);

print "<?php\n";
$extensions = get_loaded_extensions();
foreach ($extensions as $extName) {	
	print_extension (new ReflectionExtension ($extName));
}
print "?>";

// === Functions ===
/**
 * Makes generic key from given function name
 * @param name string Function name
 * @return string generic key
 */
function make_funckey_from_str ($name) {
	$name = str_replace ("->", "::", $name);
	$name = str_replace ("()", "", $name);
	$name = strtolower ($name);
	return $name;
}

/**
 * Makes generic key from given function reference
 * @param name ReflectionMethod function reference
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
 * Parses PHP documentation
 * @param phpdoc_dir string PHP.net documentation directory
 * @return array Function information gathered from the PHP.net documentation by parsing XML files
 */
function parse_phpdoc_functions ($phpdoc_dir) {
	$xml_files = array_merge (
		glob ("{$phpdoc_dir}/en/reference/*/functions/*.xml"), 
		glob ("{$phpdoc_dir}/en/reference/*/functions/*/*.xml") 
	);
	foreach ($xml_files as $xml_file) {
		$xml = file_get_contents ($xml_file);

		if (preg_match ('@<refentry.*?xml:id=["\'](.*?)["\'].*?>.*?<refname>(.*?)</refname>.*?<refpurpose>(.*?)</refpurpose>@s', $xml, $match)) {

			$refname = make_funckey_from_str ($match[2]);
			$functions[$refname]['id'] = $match[1];
			$functions[$refname]['quickref'] = trim($match[3]);

			if (preg_match ('@<refsect1\s+role=["\']description["\']>(.*?)</refsect1>@s', $xml, $match)) {
				$description = $match[1];
				$function_alias = null;
				$parameters = null;
				$has_object_style = false;
				if (preg_match ('@^(.*?)<classsynopsis>.*?<classname>(.*)</classname>.*?<methodsynopsis>.*?<type>(.*?)</type>.*?<methodname>(.*?)</methodname>(.*?)</methodsynopsis>.*?</classsynopsis>(.*)$@s', $description, $match)) {
					$functions[$refname]['classname'] = trim($match[2]);
					$functions[$refname]['returntype'] = trim($match[3]);
					$functions[$refname]['methodname'] = trim($match[4]);
					$parameters = $match[5];
					$description = $match[1].$match[6];
					$has_object_style = true;
				}
				if (preg_match ('@<methodsynopsis>.*?<type>(.*?)</type>.*?<methodname>(.*?)</methodname>(.*?)</methodsynopsis>@s', $description, $match)) {
					if ($has_object_style) {
						$function_alias = trim($match[2]);
					} else {
						$functions[$refname]['returntype'] = trim($match[1]);
						$functions[$refname]['methodname'] = trim($match[2]);
						$parameters = $match[3];
					}
				}
				if ($parameters) {
					if (preg_match_all ('@<methodparam\s*(.*?)>.*?<type>(.*?)</type>.*?<parameter\s*(.*?)>(.*?)</parameter>.*?</methodparam>@s', $parameters, $match)) {
						for ($i = 0; $i < count($match[0]); ++$i) {
							$parameter = array (
								'type' => trim($match[2][$i]),
								'name' => trim($match[4][$i]),
							);
							if (preg_match ('@choice=[\'"]opt[\'"]@', $match[1][$i])) {
								$parameter['isoptional'] = true;
							}
							if (preg_match ('@role=[\'"]reference[\'"]@', $match[3][$i])) {
								$parameter['isreference'] = true;
							}
							$functions[$refname]['parameters'][] = $parameter;
						}
					}
				}
			}
			if (preg_match ('@<refsect1\s+role=["\']parameters["\']>(.*?)</refsect1>@s', $xml, $match)) {
				$parameters = $match[1];
			}
			if (preg_match ('@<refsect1\s+role=["\']returnvalues["\']>(.*?)</refsect1>@s', $xml, $match)) {
				$returnvalues = $match[1];
				if (preg_match ('@<para>\s*Returns(.*)@', $returnvalues, $match)) {
					$functions[$refname]['returndoc'] = xml_to_phpdoc ($match[1]);
				}
			}

			// Create information for function alias
			if ($function_alias) {
				$functions[$function_alias] = $functions[$refname];
			}
		}
	}
	return $functions;
}

/**
 * Prints ReflectionExtension in format of PHP code
 * @param extRef ReflectionExtension object
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
 * @param classRef ReflectionClass object
 * @param tabs integer[optional] number of tabs for indentation
 */
function print_class ($classRef, $tabs = 0) {
	print "\n";
	print_doccomment ($classRef, $tabs);
	print_tabs ($tabs);
	print_modifiers ($classRef);

	print $classRef->isInterface() ? "interface " : "class ";
	print "{$classRef->getName()} ";
	
	// print out parent class
	$parentClassRef = $classRef->getParentClass();
	if ($parentClassRef) {
		print "extends {$parentClassRef->getName()} ";
	}

	// print out interfaces
	$interfacesRef = $classRef->getInterfaces();
	if (count ($interfacesRef) > 0) {
		print "implements ";
		$i = 0;
		foreach ($interfacesRef as $interfaceRef) {
			if ($i++ > 0) {
				print ", ";
			}
			print "{$interfaceRef->getName()}";
		}
	}
	print " {\n";

	// process properties
	$propertiesRef = $classRef->getProperties();
	if (count ($propertiesRef) > 0) {
		foreach ($propertiesRef as $propertyRef) {
			print_property ($propertyRef, $tabs + 1);
		}
		print "\n";
	}
	
	// process methods
	$methodsRef = $classRef->getMethods();
	if (count ($methodsRef) > 0) {
		foreach ($methodsRef as $methodRef) {
			print_function ($methodRef, $tabs + 1);
		}
		print "\n";
	}
	print_tabs ($tabs);
	print "}\n";
}

/**
 * Prints ReflectionProperty in format of PHP code
 * @param propertyRef ReflectionProperty object
 * @param tabs integer[optional] number of tabs for indentation
 */
function print_property ($propertyRef, $tabs = 0) {
	print_doccomment ($propertyRef, $tabs);
	print_tabs ($tabs);
	print_modifiers ($propertyRef);
	print "\${$propertyRef->getName()};\n";
}

function print_function ($functionRef, $tabs = 0) {
	global $functions;

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
	$funckey = make_funckey_from_ref ($functionRef);
	$parameters = $functions[$funckey]['parameters'];
	if ($parameters) {
		print_parameters ($parameters);
	} else {
		print_parameters_ref ($functionRef->getParameters());
	}
	print ") {}\n";
}


/**
 * Prints ReflectionParameter in format of PHP code
 * @param parameters array information from PHP.net documentation
 */
function print_parameters ($parameters) {
	$i = 0;
	foreach ($parameters as $parameter) {
		if ($parameter['name'] != "...") {
			if ($i++ > 0) {
				print ", ";
			}
			/*
			if ($parameter['type']) {
				print "{$parameter['type']} ";
			}
			*/
			if ($parameter['isreference']) {
				print "&";
			}
			print "\${$parameter['name']}";
			if ($parameter['isoptional']) {
				if ($parameter['defaultvalue']) {
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
 * @param paramsRef ReflectionParameter[] array of objects
 */
function print_parameters_ref ($paramsRef) {
	$i = 0;
	foreach ($paramsRef as $paramRef) {
		if ($paramRef->isArray()) {
			print "array ";
		} else if ($classRef = $paramRef->getClass()) {
			print "{$classRef->getName()} ";
		}
		$name = $paramRef->getName() ? $paramRef->getName() : "var".($i+1);
		if ($name != "...") {
			if ($i++ > 0) {
				print ", ";
			}
			if ($paramRef->isPassedByReference()) {
				print "&";
			}
			print "\${$name}";
			if ($paramRef->allowsNull()) {
				print " = null";
			} else if ($paramRef->isDefaultValueAvailable()) {
				$value = $paramRef->getDefaultValue();
				if (!is_numeric ($value)) {
					$value = "'{$value}'";
				}
				print " = {$value}";
			}
		}
	}
}

/**
 * Prints constants in format of PHP code
 * @param constants array containing constants, where key is a name of constant
 * @param tabs integer[optional] number of tabs for indentation
 */
function print_constants ($constants, $tabs = 0) {
	foreach ($constants as $name => $value) {
		if (!is_numeric ($value)) {
			$value = "'{$value}'";
		}
		print_tabs ($tabs);
		print "define ('{$name}', {$value});\n";
	}
}

/**
 * Prints modifiers of reflection object in format of PHP code
 * @param ref Reflection some reflection object
 */
function print_modifiers ($ref) {
	$modifiers = Reflection::getModifierNames ($ref->getModifiers());
	if (count ($modifiers) > 0) {
		print implode(' ', $modifiers);
		print " ";
	}
}

/**
 * Prints PHPDOC comment before specified reflection object
 * @param ref Reflection some reflection object
 * @param tabs integer[optional] number of tabs for indentation
 */
function print_doccomment ($ref, $tabs = 0) {
	global $functions;

	$docComment = $ref->getDocComment();
	if ($docComment) {
		print_tabs ($tabs);
		print "{$docComment}\n";
	}
	else if ($ref instanceof ReflectionFunctionAbstract) {
		$funckey = make_funckey_from_ref ($ref);
		$returntype = $functions[$funckey]['returntype'];
		$desc = $functions[$funckey]['quickref'];
		$returndoc = newline_to_phpdoc ($functions[$funckey]['returndoc'], $tabs);

		$paramsRef = $ref->getParameters();
		$parameters = $functions[$funckey]['parameters'];

		if ($desc || count ($paramsRef) > 0 || $parameters || $returntype) {
			print_tabs ($tabs);
			print "/**\n";
			if ($desc) {
				print_tabs ($tabs);
				print " * {$desc}\n";
			}
			if ($functions[$funckey]['id']) {
				print_tabs ($tabs);
				print " * @url http://php.net/manual/en/{$functions[$funckey]['id']}.php\n";
			}
			if ($parameters) {
				foreach ($parameters as $parameter) {
					print_tabs ($tabs);
					print " * @param {$parameter['name']} {$parameter['type']}";
					if ($parameter['isoptional']) {
						print "[optional]";
					}
					print "\n";
				}
			} else {
				$i = 0;
				foreach ($paramsRef as $paramRef) {
					print_tabs ($tabs);
					$name = $paramRef->getName() ? $paramRef->getName() : "var".++$i;
					print " * @param {$name}";
					if ($classRef = $paramRef->getClass()) {
						print " {$classRef->getName()}";
						if ($paramRef->isArray()) {
							print "[]";
						}
					}
					if ($paramRef->isOptional()) {
						print "[optional]";
					}
					print "\n";
				}
			}
			if ($returntype) {
				print_tabs ($tabs);
				print " * @return {$returntype}{$returndoc}\n";
			}
			print_tabs ($tabs);
			print " */\n";
		}
	}
}

/**
 * Converts XML entities to human readable string for PHPDOC
 * @param str string
 * @return string
 */
function xml_to_phpdoc ($str) {
	$str = str_replace ("&true;", "true", $str);
	$str = str_replace ("&false;", "false", $str);
	$str = strip_tags ($str);
	return $str;
}

/**
 * Converts newlines to PHPDOC prefixes in the given string
 * @param str string
 * @param tabs integer[optional] number of tabs for indentation
 * @return string PHPDOC string
 */
function newline_to_phpdoc ($str, $tabs = 0) {
	$str = preg_replace ("@[\r\n]@", "\n".str_repeat("\t", $tabs)." * ", $str);
	return $str;
}

/**
 * Prints specified number of tabs
 * @param tabs integer number of tabs to print
 */
function print_tabs ($tabs) {
	print str_repeat("\t", $tabs);
}

?>
