<?php

// Start of bcmath v.8.3.0

/**
 * {@inheritdoc}
 * @param string $num1
 * @param string $num2
 * @param int|null $scale [optional]
 */
function bcadd (string $num1, string $num2, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num1
 * @param string $num2
 * @param int|null $scale [optional]
 */
function bcsub (string $num1, string $num2, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num1
 * @param string $num2
 * @param int|null $scale [optional]
 */
function bcmul (string $num1, string $num2, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num1
 * @param string $num2
 * @param int|null $scale [optional]
 */
function bcdiv (string $num1, string $num2, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num1
 * @param string $num2
 * @param int|null $scale [optional]
 */
function bcmod (string $num1, string $num2, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num
 * @param string $exponent
 * @param string $modulus
 * @param int|null $scale [optional]
 */
function bcpowmod (string $num, string $exponent, string $modulus, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num
 * @param string $exponent
 * @param int|null $scale [optional]
 */
function bcpow (string $num, string $exponent, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num
 * @param int|null $scale [optional]
 */
function bcsqrt (string $num, ?int $scale = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $num1
 * @param string $num2
 * @param int|null $scale [optional]
 */
function bccomp (string $num1, string $num2, ?int $scale = NULL): int {}

/**
 * {@inheritdoc}
 * @param int|null $scale [optional]
 */
function bcscale (?int $scale = NULL): int {}

// End of bcmath v.8.3.0
