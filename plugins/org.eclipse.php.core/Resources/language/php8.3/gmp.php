<?php

// Start of gmp v.8.3.0

class GMP  {

	/**
	 * {@inheritdoc}
	 * @param string|int $num [optional]
	 * @param int $base [optional]
	 */
	public function __construct (string|int $num = 0, int $base = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

}

/**
 * {@inheritdoc}
 * @param string|int $num
 * @param int $base [optional]
 */
function gmp_init (string|int $num, int $base = 0): GMP {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $word_size [optional]
 * @param int $flags [optional]
 */
function gmp_import (string $data, int $word_size = 1, int $flags = 17): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param int $word_size [optional]
 * @param int $flags [optional]
 */
function gmp_export (GMP|string|int $num, int $word_size = 1, int $flags = 17): string {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_intval (GMP|string|int $num): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param int $base [optional]
 */
function gmp_strval (GMP|string|int $num, int $base = 10): string {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_add (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_sub (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_mul (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 * @param int $rounding_mode [optional]
 */
function gmp_div_qr (GMP|string|int $num1, GMP|string|int $num2, int $rounding_mode = 0): array {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 * @param int $rounding_mode [optional]
 */
function gmp_div_q (GMP|string|int $num1, GMP|string|int $num2, int $rounding_mode = 0): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 * @param int $rounding_mode [optional]
 */
function gmp_div_r (GMP|string|int $num1, GMP|string|int $num2, int $rounding_mode = 0): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 * @param int $rounding_mode [optional]
 */
function gmp_div (GMP|string|int $num1, GMP|string|int $num2, int $rounding_mode = 0): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_mod (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_divexact (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_neg (GMP|string|int $num): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_abs (GMP|string|int $num): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_fact (GMP|string|int $num): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_sqrt (GMP|string|int $num): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_sqrtrem (GMP|string|int $num): array {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param int $nth
 */
function gmp_root (GMP|string|int $num, int $nth): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param int $nth
 */
function gmp_rootrem (GMP|string|int $num, int $nth): array {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param int $exponent
 */
function gmp_pow (GMP|string|int $num, int $exponent): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param GMP|string|int $exponent
 * @param GMP|string|int $modulus
 */
function gmp_powm (GMP|string|int $num, GMP|string|int $exponent, GMP|string|int $modulus): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_perfect_square (GMP|string|int $num): bool {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_perfect_power (GMP|string|int $num): bool {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param int $repetitions [optional]
 */
function gmp_prob_prime (GMP|string|int $num, int $repetitions = 10): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_gcd (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_gcdext (GMP|string|int $num1, GMP|string|int $num2): array {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_lcm (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_invert (GMP|string|int $num1, GMP|string|int $num2): GMP|false {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_jacobi (GMP|string|int $num1, GMP|string|int $num2): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_legendre (GMP|string|int $num1, GMP|string|int $num2): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_kronecker (GMP|string|int $num1, GMP|string|int $num2): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_cmp (GMP|string|int $num1, GMP|string|int $num2): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_sign (GMP|string|int $num): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $seed
 */
function gmp_random_seed (GMP|string|int $seed): void {}

/**
 * {@inheritdoc}
 * @param int $bits
 */
function gmp_random_bits (int $bits): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $min
 * @param GMP|string|int $max
 */
function gmp_random_range (GMP|string|int $min, GMP|string|int $max): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_and (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_or (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_com (GMP|string|int $num): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_xor (GMP|string|int $num1, GMP|string|int $num2): GMP {}

/**
 * {@inheritdoc}
 * @param GMP $num
 * @param int $index
 * @param bool $value [optional]
 */
function gmp_setbit (GMP $num, int $index, bool $value = true): void {}

/**
 * {@inheritdoc}
 * @param GMP $num
 * @param int $index
 */
function gmp_clrbit (GMP $num, int $index): void {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 * @param int $index
 */
function gmp_testbit (GMP|string|int $num, int $index): bool {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param int $start
 */
function gmp_scan0 (GMP|string|int $num1, int $start): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param int $start
 */
function gmp_scan1 (GMP|string|int $num1, int $start): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_popcount (GMP|string|int $num): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 */
function gmp_hamdist (GMP|string|int $num1, GMP|string|int $num2): int {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $num
 */
function gmp_nextprime (GMP|string|int $num): GMP {}

/**
 * {@inheritdoc}
 * @param GMP|string|int $n
 * @param int $k
 */
function gmp_binomial (GMP|string|int $n, int $k): GMP {}

define ('GMP_ROUND_ZERO', 0);
define ('GMP_ROUND_PLUSINF', 1);
define ('GMP_ROUND_MINUSINF', 2);
define ('GMP_VERSION', "6.3.0");
define ('GMP_MSW_FIRST', 1);
define ('GMP_LSW_FIRST', 2);
define ('GMP_LITTLE_ENDIAN', 4);
define ('GMP_BIG_ENDIAN', 8);
define ('GMP_NATIVE_ENDIAN', 16);

// End of gmp v.8.3.0
