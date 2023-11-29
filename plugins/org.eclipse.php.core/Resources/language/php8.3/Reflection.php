<?php

// Start of Reflection v.8.3.0

class ReflectionException extends Exception implements Throwable, Stringable {

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

class Reflection  {

	/**
	 * {@inheritdoc}
	 * @param int $modifiers
	 */
	public static function getModifierNames (int $modifiers) {}

}

interface Reflector extends Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString (): string;

}

abstract class ReflectionFunctionAbstract implements Reflector, Stringable {

	public string $name;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function inNamespace () {}

	/**
	 * {@inheritdoc}
	 */
	public function isClosure () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDeprecated () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInternal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isUserDefined () {}

	/**
	 * {@inheritdoc}
	 */
	public function isGenerator () {}

	/**
	 * {@inheritdoc}
	 */
	public function isVariadic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isStatic () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureThis () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureScopeClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureCalledClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureUsedVariables (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 */
	public function getEndLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtensionName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFileName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNamespaceName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNumberOfParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNumberOfRequiredParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getShortName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStartLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStaticVariables () {}

	/**
	 * {@inheritdoc}
	 */
	public function returnsReference () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasReturnType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getReturnType () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasTentativeReturnType (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTentativeReturnType (): ?ReflectionType {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString (): string;

}

class ReflectionFunction extends ReflectionFunctionAbstract implements Stringable, Reflector {
	const IS_DEPRECATED = 2048;


	/**
	 * {@inheritdoc}
	 * @param Closure|string $function
	 */
	public function __construct (Closure|string $function) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function isAnonymous (): bool {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function isDisabled () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function invoke (mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param array $args
	 */
	public function invokeArgs (array $args) {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosure () {}

	/**
	 * {@inheritdoc}
	 */
	public function inNamespace () {}

	/**
	 * {@inheritdoc}
	 */
	public function isClosure () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDeprecated () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInternal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isUserDefined () {}

	/**
	 * {@inheritdoc}
	 */
	public function isGenerator () {}

	/**
	 * {@inheritdoc}
	 */
	public function isVariadic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isStatic () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureThis () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureScopeClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureCalledClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureUsedVariables (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 */
	public function getEndLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtensionName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFileName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNamespaceName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNumberOfParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNumberOfRequiredParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getShortName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStartLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStaticVariables () {}

	/**
	 * {@inheritdoc}
	 */
	public function returnsReference () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasReturnType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getReturnType () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasTentativeReturnType (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTentativeReturnType (): ?ReflectionType {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

}

final class ReflectionGenerator  {

	/**
	 * {@inheritdoc}
	 * @param Generator $generator
	 */
	public function __construct (Generator $generator) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExecutingLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExecutingFile () {}

	/**
	 * {@inheritdoc}
	 * @param int $options [optional]
	 */
	public function getTrace (int $options = 1) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFunction () {}

	/**
	 * {@inheritdoc}
	 */
	public function getThis () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExecutingGenerator () {}

}

class ReflectionParameter implements Stringable, Reflector {

	public string $name;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $function
	 * @param string|int $param
	 */
	public function __construct ($function = null, string|int $param) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPassedByReference () {}

	/**
	 * {@inheritdoc}
	 */
	public function canBePassedByValue () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeclaringFunction () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeclaringClass () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function getClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function isArray () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function isCallable () {}

	/**
	 * {@inheritdoc}
	 */
	public function allowsNull () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPosition () {}

	/**
	 * {@inheritdoc}
	 */
	public function isOptional () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDefaultValueAvailable () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDefaultValue () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDefaultValueConstant () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDefaultValueConstantName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isVariadic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPromoted (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

}

abstract class ReflectionType implements Stringable {

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function allowsNull () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ReflectionNamedType extends ReflectionType implements Stringable {

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isBuiltin () {}

	/**
	 * {@inheritdoc}
	 */
	public function allowsNull () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ReflectionUnionType extends ReflectionType implements Stringable {

	/**
	 * {@inheritdoc}
	 */
	public function getTypes (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function allowsNull () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ReflectionIntersectionType extends ReflectionType implements Stringable {

	/**
	 * {@inheritdoc}
	 */
	public function getTypes (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function allowsNull () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ReflectionMethod extends ReflectionFunctionAbstract implements Stringable, Reflector {
	const IS_STATIC = 16;
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_ABSTRACT = 64;
	const IS_FINAL = 32;


	public string $class;

	/**
	 * {@inheritdoc}
	 * @param object|string $objectOrMethod
	 * @param string|null $method [optional]
	 */
	public function __construct (object|string $objectOrMethod, ?string $method = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $method
	 */
	public static function createFromMethodName (string $method): static {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function isPublic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPrivate () {}

	/**
	 * {@inheritdoc}
	 */
	public function isProtected () {}

	/**
	 * {@inheritdoc}
	 */
	public function isAbstract () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFinal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isConstructor () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDestructor () {}

	/**
	 * {@inheritdoc}
	 * @param object|null $object [optional]
	 */
	public function getClosure (?object $object = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 * @param object|null $object
	 * @param mixed $args [optional]
	 */
	public function invoke (?object $object = null, mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 * @param object|null $object
	 * @param array $args
	 */
	public function invokeArgs (?object $object = null, array $args) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeclaringClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPrototype () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasPrototype (): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $accessible
	 */
	public function setAccessible (bool $accessible) {}

	/**
	 * {@inheritdoc}
	 */
	public function inNamespace () {}

	/**
	 * {@inheritdoc}
	 */
	public function isClosure () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDeprecated () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInternal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isUserDefined () {}

	/**
	 * {@inheritdoc}
	 */
	public function isGenerator () {}

	/**
	 * {@inheritdoc}
	 */
	public function isVariadic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isStatic () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureThis () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureScopeClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureCalledClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClosureUsedVariables (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 */
	public function getEndLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtensionName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFileName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNamespaceName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNumberOfParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNumberOfRequiredParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getParameters () {}

	/**
	 * {@inheritdoc}
	 */
	public function getShortName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStartLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStaticVariables () {}

	/**
	 * {@inheritdoc}
	 */
	public function returnsReference () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasReturnType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getReturnType () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasTentativeReturnType (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTentativeReturnType (): ?ReflectionType {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

}

class ReflectionClass implements Stringable, Reflector {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 64;
	const IS_FINAL = 32;
	const IS_READONLY = 65536;


	public string $name;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 * @param object|string $objectOrClass
	 */
	public function __construct (object|string $objectOrClass) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInternal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isUserDefined () {}

	/**
	 * {@inheritdoc}
	 */
	public function isAnonymous () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInstantiable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isCloneable () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFileName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStartLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getEndLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConstructor () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasMethod (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getMethod (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getMethods (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasProperty (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getProperty (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getProperties (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getConstants (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getReflectionConstants (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getReflectionConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterfaces () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterfaceNames () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInterface () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraits () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraitNames () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraitAliases () {}

	/**
	 * {@inheritdoc}
	 */
	public function isTrait () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEnum (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isAbstract () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFinal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadOnly (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function isInstance (object $object) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function newInstance (mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 */
	public function newInstanceWithoutConstructor () {}

	/**
	 * {@inheritdoc}
	 * @param array $args [optional]
	 */
	public function newInstanceArgs (array $args = array (
)) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentClass () {}

	/**
	 * {@inheritdoc}
	 * @param ReflectionClass|string $class
	 */
	public function isSubclassOf (ReflectionClass|string $class) {}

	/**
	 * {@inheritdoc}
	 */
	public function getStaticProperties () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param mixed $default [optional]
	 */
	public function getStaticPropertyValue (string $name, mixed $default = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param mixed $value
	 */
	public function setStaticPropertyValue (string $name, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDefaultProperties () {}

	/**
	 * {@inheritdoc}
	 */
	public function isIterable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isIterateable () {}

	/**
	 * {@inheritdoc}
	 * @param ReflectionClass|string $interface
	 */
	public function implementsInterface (ReflectionClass|string $interface) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtensionName () {}

	/**
	 * {@inheritdoc}
	 */
	public function inNamespace () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNamespaceName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getShortName () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

}

class ReflectionObject extends ReflectionClass implements Reflector, Stringable {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 64;
	const IS_FINAL = 32;
	const IS_READONLY = 65536;


	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function __construct (object $object) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInternal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isUserDefined () {}

	/**
	 * {@inheritdoc}
	 */
	public function isAnonymous () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInstantiable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isCloneable () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFileName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStartLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getEndLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConstructor () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasMethod (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getMethod (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getMethods (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasProperty (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getProperty (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getProperties (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getConstants (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getReflectionConstants (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getReflectionConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterfaces () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterfaceNames () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInterface () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraits () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraitNames () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraitAliases () {}

	/**
	 * {@inheritdoc}
	 */
	public function isTrait () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEnum (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isAbstract () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFinal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadOnly (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function isInstance (object $object) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function newInstance (mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 */
	public function newInstanceWithoutConstructor () {}

	/**
	 * {@inheritdoc}
	 * @param array $args [optional]
	 */
	public function newInstanceArgs (array $args = array (
)) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentClass () {}

	/**
	 * {@inheritdoc}
	 * @param ReflectionClass|string $class
	 */
	public function isSubclassOf (ReflectionClass|string $class) {}

	/**
	 * {@inheritdoc}
	 */
	public function getStaticProperties () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param mixed $default [optional]
	 */
	public function getStaticPropertyValue (string $name, mixed $default = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param mixed $value
	 */
	public function setStaticPropertyValue (string $name, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDefaultProperties () {}

	/**
	 * {@inheritdoc}
	 */
	public function isIterable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isIterateable () {}

	/**
	 * {@inheritdoc}
	 * @param ReflectionClass|string $interface
	 */
	public function implementsInterface (ReflectionClass|string $interface) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtensionName () {}

	/**
	 * {@inheritdoc}
	 */
	public function inNamespace () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNamespaceName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getShortName () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

}

class ReflectionProperty implements Stringable, Reflector {
	const IS_STATIC = 16;
	const IS_READONLY = 128;
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;


	public string $name;

	public string $class;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 * @param object|string $class
	 * @param string $property
	 */
	public function __construct (object|string $class, string $property) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 * @param object|null $object [optional]
	 */
	public function getValue (?object $object = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $objectOrValue
	 * @param mixed $value [optional]
	 */
	public function setValue (mixed $objectOrValue = null, mixed $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param object|null $object [optional]
	 */
	public function isInitialized (?object $object = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function isPublic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPrivate () {}

	/**
	 * {@inheritdoc}
	 */
	public function isProtected () {}

	/**
	 * {@inheritdoc}
	 */
	public function isStatic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadOnly (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isDefault () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPromoted (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeclaringClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 * @param bool $accessible
	 */
	public function setAccessible (bool $accessible) {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasType () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasDefaultValue (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getDefaultValue () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

}

class ReflectionClassConstant implements Stringable, Reflector {
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_FINAL = 32;


	public string $name;

	public string $class;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 * @param object|string $class
	 * @param string $constant
	 */
	public function __construct (object|string $class, string $constant) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getValue () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPublic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPrivate () {}

	/**
	 * {@inheritdoc}
	 */
	public function isProtected () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFinal (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeclaringClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

	/**
	 * {@inheritdoc}
	 */
	public function isEnumCase (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function hasType (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getType (): ?ReflectionType {}

}

class ReflectionExtension implements Stringable, Reflector {

	public string $name;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function __construct (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getVersion () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFunctions () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConstants () {}

	/**
	 * {@inheritdoc}
	 */
	public function getINIEntries () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClasses () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClassNames () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDependencies () {}

	/**
	 * {@inheritdoc}
	 */
	public function info () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPersistent () {}

	/**
	 * {@inheritdoc}
	 */
	public function isTemporary () {}

}

class ReflectionZendExtension implements Stringable, Reflector {

	public string $name;

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function __construct (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getVersion () {}

	/**
	 * {@inheritdoc}
	 */
	public function getAuthor () {}

	/**
	 * {@inheritdoc}
	 */
	public function getURL () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCopyright () {}

}

final class ReflectionReference  {

	/**
	 * {@inheritdoc}
	 * @param array $array
	 * @param string|int $key
	 */
	public static function fromArrayElement (array $array, string|int $key): ?ReflectionReference {}

	/**
	 * {@inheritdoc}
	 */
	public function getId (): string {}

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

}

class ReflectionAttribute implements Stringable, Reflector {
	const IS_INSTANCEOF = 2;


	/**
	 * {@inheritdoc}
	 */
	public function getName (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getTarget (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isRepeated (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getArguments (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function newInstance (): object {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	private function __clone (): void {}

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

}

class ReflectionEnum extends ReflectionClass implements Reflector, Stringable {
	const IS_IMPLICIT_ABSTRACT = 16;
	const IS_EXPLICIT_ABSTRACT = 64;
	const IS_FINAL = 32;
	const IS_READONLY = 65536;


	/**
	 * {@inheritdoc}
	 * @param object|string $objectOrClass
	 */
	public function __construct (object|string $objectOrClass) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasCase (string $name): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getCase (string $name): ReflectionEnumUnitCase {}

	/**
	 * {@inheritdoc}
	 */
	public function getCases (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function isBacked (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getBackingType (): ?ReflectionNamedType {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInternal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isUserDefined () {}

	/**
	 * {@inheritdoc}
	 */
	public function isAnonymous () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInstantiable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isCloneable () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFileName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStartLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getEndLine () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConstructor () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasMethod (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getMethod (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getMethods (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasProperty (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getProperty (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getProperties (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function hasConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getConstants (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $filter [optional]
	 */
	public function getReflectionConstants (?int $filter = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getReflectionConstant (string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterfaces () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInterfaceNames () {}

	/**
	 * {@inheritdoc}
	 */
	public function isInterface () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraits () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraitNames () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTraitAliases () {}

	/**
	 * {@inheritdoc}
	 */
	public function isTrait () {}

	/**
	 * {@inheritdoc}
	 */
	public function isEnum (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isAbstract () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFinal () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadOnly (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 * @param object $object
	 */
	public function isInstance (object $object) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function newInstance (mixed ...$args) {}

	/**
	 * {@inheritdoc}
	 */
	public function newInstanceWithoutConstructor () {}

	/**
	 * {@inheritdoc}
	 * @param array $args [optional]
	 */
	public function newInstanceArgs (array $args = array (
)) {}

	/**
	 * {@inheritdoc}
	 */
	public function getParentClass () {}

	/**
	 * {@inheritdoc}
	 * @param ReflectionClass|string $class
	 */
	public function isSubclassOf (ReflectionClass|string $class) {}

	/**
	 * {@inheritdoc}
	 */
	public function getStaticProperties () {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param mixed $default [optional]
	 */
	public function getStaticPropertyValue (string $name, mixed $default = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param mixed $value
	 */
	public function setStaticPropertyValue (string $name, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getDefaultProperties () {}

	/**
	 * {@inheritdoc}
	 */
	public function isIterable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isIterateable () {}

	/**
	 * {@inheritdoc}
	 * @param ReflectionClass|string $interface
	 */
	public function implementsInterface (ReflectionClass|string $interface) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtensionName () {}

	/**
	 * {@inheritdoc}
	 */
	public function inNamespace () {}

	/**
	 * {@inheritdoc}
	 */
	public function getNamespaceName () {}

	/**
	 * {@inheritdoc}
	 */
	public function getShortName () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

}

class ReflectionEnumUnitCase extends ReflectionClassConstant implements Reflector, Stringable {
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_FINAL = 32;


	/**
	 * {@inheritdoc}
	 * @param object|string $class
	 * @param string $constant
	 */
	public function __construct (object|string $class, string $constant) {}

	/**
	 * {@inheritdoc}
	 */
	public function getEnum (): ReflectionEnum {}

	/**
	 * {@inheritdoc}
	 */
	public function getValue (): UnitEnum {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPublic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPrivate () {}

	/**
	 * {@inheritdoc}
	 */
	public function isProtected () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFinal (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeclaringClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

	/**
	 * {@inheritdoc}
	 */
	public function isEnumCase (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function hasType (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getType (): ?ReflectionType {}

}

class ReflectionEnumBackedCase extends ReflectionEnumUnitCase implements Stringable, Reflector {
	const IS_PUBLIC = 1;
	const IS_PROTECTED = 2;
	const IS_PRIVATE = 4;
	const IS_FINAL = 32;


	/**
	 * {@inheritdoc}
	 * @param object|string $class
	 * @param string $constant
	 */
	public function __construct (object|string $class, string $constant) {}

	/**
	 * {@inheritdoc}
	 */
	public function getBackingValue (): string|int {}

	/**
	 * {@inheritdoc}
	 */
	public function getEnum (): ReflectionEnum {}

	/**
	 * {@inheritdoc}
	 */
	public function getValue (): UnitEnum {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPublic () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPrivate () {}

	/**
	 * {@inheritdoc}
	 */
	public function isProtected () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFinal (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getModifiers () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeclaringClass () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDocComment () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $name [optional]
	 * @param int $flags [optional]
	 */
	public function getAttributes (?string $name = NULL, int $flags = 0): array {}

	/**
	 * {@inheritdoc}
	 */
	public function isEnumCase (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function hasType (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getType (): ?ReflectionType {}

}

final class ReflectionFiber  {

	/**
	 * {@inheritdoc}
	 * @param Fiber $fiber
	 */
	public function __construct (Fiber $fiber) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFiber (): Fiber {}

	/**
	 * {@inheritdoc}
	 */
	public function getExecutingFile (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getExecutingLine (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	public function getCallable (): callable {}

	/**
	 * {@inheritdoc}
	 * @param int $options [optional]
	 */
	public function getTrace (int $options = 1): array {}

}
// End of Reflection v.8.3.0
