<?php

// Start of openssl v.8.3.0

final class OpenSSLCertificate  {
}

final class OpenSSLCertificateSigningRequest  {
}

final class OpenSSLAsymmetricKey  {
}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param string $output_filename
 * @param bool $no_text [optional]
 */
function openssl_x509_export_to_file (OpenSSLCertificate|string $certificate, string $output_filename, bool $no_text = true): bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param mixed $output
 * @param bool $no_text [optional]
 */
function openssl_x509_export (OpenSSLCertificate|string $certificate, &$output = null, bool $no_text = true): bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param string $digest_algo [optional]
 * @param bool $binary [optional]
 */
function openssl_x509_fingerprint (OpenSSLCertificate|string $certificate, string $digest_algo = 'sha1', bool $binary = false): string|false {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param mixed $private_key
 */
function openssl_x509_check_private_key (OpenSSLCertificate|string $certificate, $private_key = null): bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param mixed $public_key
 */
function openssl_x509_verify (OpenSSLCertificate|string $certificate, $public_key = null): int {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param bool $short_names [optional]
 */
function openssl_x509_parse (OpenSSLCertificate|string $certificate, bool $short_names = true): array|false {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param int $purpose
 * @param array $ca_info [optional]
 * @param string|null $untrusted_certificates_file [optional]
 */
function openssl_x509_checkpurpose (OpenSSLCertificate|string $certificate, int $purpose, array $ca_info = array (
), ?string $untrusted_certificates_file = NULL): int|bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 */
function openssl_x509_read (OpenSSLCertificate|string $certificate): OpenSSLCertificate|false {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate $certificate
 * @deprecated 
 */
function openssl_x509_free (OpenSSLCertificate $certificate): void {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param string $output_filename
 * @param mixed $private_key
 * @param string $passphrase
 * @param array $options [optional]
 */
function openssl_pkcs12_export_to_file (OpenSSLCertificate|string $certificate, string $output_filename, $private_key = null, string $passphrase, array $options = array (
)): bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificate|string $certificate
 * @param mixed $output
 * @param mixed $private_key
 * @param string $passphrase
 * @param array $options [optional]
 */
function openssl_pkcs12_export (OpenSSLCertificate|string $certificate, &$output = null, $private_key = null, string $passphrase, array $options = array (
)): bool {}

/**
 * {@inheritdoc}
 * @param string $pkcs12
 * @param mixed $certificates
 * @param string $passphrase
 */
function openssl_pkcs12_read (string $pkcs12, &$certificates = null, string $passphrase): bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificateSigningRequest|string $csr
 * @param string $output_filename
 * @param bool $no_text [optional]
 */
function openssl_csr_export_to_file (OpenSSLCertificateSigningRequest|string $csr, string $output_filename, bool $no_text = true): bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificateSigningRequest|string $csr
 * @param mixed $output
 * @param bool $no_text [optional]
 */
function openssl_csr_export (OpenSSLCertificateSigningRequest|string $csr, &$output = null, bool $no_text = true): bool {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificateSigningRequest|string $csr
 * @param OpenSSLCertificate|string|null $ca_certificate
 * @param mixed $private_key
 * @param int $days
 * @param array|null $options [optional]
 * @param int $serial [optional]
 */
function openssl_csr_sign (OpenSSLCertificateSigningRequest|string $csr, OpenSSLCertificate|string|null $ca_certificate = null, $private_key = null, int $days, ?array $options = NULL, int $serial = 0): OpenSSLCertificate|false {}

/**
 * {@inheritdoc}
 * @param array $distinguished_names
 * @param mixed $private_key
 * @param array|null $options [optional]
 * @param array|null $extra_attributes [optional]
 */
function openssl_csr_new (array $distinguished_names, &$private_key = null, ?array $options = NULL, ?array $extra_attributes = NULL): OpenSSLCertificateSigningRequest|false {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificateSigningRequest|string $csr
 * @param bool $short_names [optional]
 */
function openssl_csr_get_subject (OpenSSLCertificateSigningRequest|string $csr, bool $short_names = true): array|false {}

/**
 * {@inheritdoc}
 * @param OpenSSLCertificateSigningRequest|string $csr
 * @param bool $short_names [optional]
 */
function openssl_csr_get_public_key (OpenSSLCertificateSigningRequest|string $csr, bool $short_names = true): OpenSSLAsymmetricKey|false {}

/**
 * {@inheritdoc}
 * @param array|null $options [optional]
 */
function openssl_pkey_new (?array $options = NULL): OpenSSLAsymmetricKey|false {}

/**
 * {@inheritdoc}
 * @param mixed $key
 * @param string $output_filename
 * @param string|null $passphrase [optional]
 * @param array|null $options [optional]
 */
function openssl_pkey_export_to_file ($key = null, string $output_filename, ?string $passphrase = NULL, ?array $options = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $key
 * @param mixed $output
 * @param string|null $passphrase [optional]
 * @param array|null $options [optional]
 */
function openssl_pkey_export ($key = null, &$output = null, ?string $passphrase = NULL, ?array $options = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $public_key
 */
function openssl_pkey_get_public ($public_key = null): OpenSSLAsymmetricKey|false {}

/**
 * {@inheritdoc}
 * @param mixed $public_key
 */
function openssl_get_publickey ($public_key = null): OpenSSLAsymmetricKey|false {}

/**
 * {@inheritdoc}
 * @param OpenSSLAsymmetricKey $key
 * @deprecated 
 */
function openssl_pkey_free (OpenSSLAsymmetricKey $key): void {}

/**
 * {@inheritdoc}
 * @param OpenSSLAsymmetricKey $key
 * @deprecated 
 */
function openssl_free_key (OpenSSLAsymmetricKey $key): void {}

/**
 * {@inheritdoc}
 * @param mixed $private_key
 * @param string|null $passphrase [optional]
 */
function openssl_pkey_get_private ($private_key = null, ?string $passphrase = NULL): OpenSSLAsymmetricKey|false {}

/**
 * {@inheritdoc}
 * @param mixed $private_key
 * @param string|null $passphrase [optional]
 */
function openssl_get_privatekey ($private_key = null, ?string $passphrase = NULL): OpenSSLAsymmetricKey|false {}

/**
 * {@inheritdoc}
 * @param OpenSSLAsymmetricKey $key
 */
function openssl_pkey_get_details (OpenSSLAsymmetricKey $key): array|false {}

/**
 * {@inheritdoc}
 * @param string $password
 * @param string $salt
 * @param int $key_length
 * @param int $iterations
 * @param string $digest_algo [optional]
 */
function openssl_pbkdf2 (string $password, string $salt, int $key_length, int $iterations, string $digest_algo = 'sha1'): string|false {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param int $flags
 * @param string|null $signers_certificates_filename [optional]
 * @param array $ca_info [optional]
 * @param string|null $untrusted_certificates_filename [optional]
 * @param string|null $content [optional]
 * @param string|null $output_filename [optional]
 */
function openssl_pkcs7_verify (string $input_filename, int $flags, ?string $signers_certificates_filename = NULL, array $ca_info = array (
), ?string $untrusted_certificates_filename = NULL, ?string $content = NULL, ?string $output_filename = NULL): int|bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param string $output_filename
 * @param mixed $certificate
 * @param array|null $headers
 * @param int $flags [optional]
 * @param int $cipher_algo [optional]
 */
function openssl_pkcs7_encrypt (string $input_filename, string $output_filename, $certificate = null, ?array $headers = null, int $flags = 0, int $cipher_algo = 5): bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param string $output_filename
 * @param OpenSSLCertificate|string $certificate
 * @param mixed $private_key
 * @param array|null $headers
 * @param int $flags [optional]
 * @param string|null $untrusted_certificates_filename [optional]
 */
function openssl_pkcs7_sign (string $input_filename, string $output_filename, OpenSSLCertificate|string $certificate, $private_key = null, ?array $headers = null, int $flags = 64, ?string $untrusted_certificates_filename = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param string $output_filename
 * @param mixed $certificate
 * @param mixed $private_key [optional]
 */
function openssl_pkcs7_decrypt (string $input_filename, string $output_filename, $certificate = null, $private_key = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $certificates
 */
function openssl_pkcs7_read (string $data, &$certificates = null): bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param int $flags [optional]
 * @param string|null $certificates [optional]
 * @param array $ca_info [optional]
 * @param string|null $untrusted_certificates_filename [optional]
 * @param string|null $content [optional]
 * @param string|null $pk7 [optional]
 * @param string|null $sigfile [optional]
 * @param int $encoding [optional]
 */
function openssl_cms_verify (string $input_filename, int $flags = 0, ?string $certificates = NULL, array $ca_info = array (
), ?string $untrusted_certificates_filename = NULL, ?string $content = NULL, ?string $pk7 = NULL, ?string $sigfile = NULL, int $encoding = 1): bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param string $output_filename
 * @param mixed $certificate
 * @param array|null $headers
 * @param int $flags [optional]
 * @param int $encoding [optional]
 * @param int $cipher_algo [optional]
 */
function openssl_cms_encrypt (string $input_filename, string $output_filename, $certificate = null, ?array $headers = null, int $flags = 0, int $encoding = 1, int $cipher_algo = 5): bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param string $output_filename
 * @param OpenSSLCertificate|string $certificate
 * @param mixed $private_key
 * @param array|null $headers
 * @param int $flags [optional]
 * @param int $encoding [optional]
 * @param string|null $untrusted_certificates_filename [optional]
 */
function openssl_cms_sign (string $input_filename, string $output_filename, OpenSSLCertificate|string $certificate, $private_key = null, ?array $headers = null, int $flags = 0, int $encoding = 1, ?string $untrusted_certificates_filename = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param string $output_filename
 * @param mixed $certificate
 * @param mixed $private_key [optional]
 * @param int $encoding [optional]
 */
function openssl_cms_decrypt (string $input_filename, string $output_filename, $certificate = null, $private_key = NULL, int $encoding = 1): bool {}

/**
 * {@inheritdoc}
 * @param string $input_filename
 * @param mixed $certificates
 */
function openssl_cms_read (string $input_filename, &$certificates = null): bool {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $encrypted_data
 * @param mixed $private_key
 * @param int $padding [optional]
 */
function openssl_private_encrypt (string $data, &$encrypted_data = null, $private_key = null, int $padding = 1): bool {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $decrypted_data
 * @param mixed $private_key
 * @param int $padding [optional]
 */
function openssl_private_decrypt (string $data, &$decrypted_data = null, $private_key = null, int $padding = 1): bool {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $encrypted_data
 * @param mixed $public_key
 * @param int $padding [optional]
 */
function openssl_public_encrypt (string $data, &$encrypted_data = null, $public_key = null, int $padding = 1): bool {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $decrypted_data
 * @param mixed $public_key
 * @param int $padding [optional]
 */
function openssl_public_decrypt (string $data, &$decrypted_data = null, $public_key = null, int $padding = 1): bool {}

/**
 * {@inheritdoc}
 */
function openssl_error_string (): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $signature
 * @param mixed $private_key
 * @param string|int $algorithm [optional]
 */
function openssl_sign (string $data, &$signature = null, $private_key = null, string|int $algorithm = 1): bool {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param string $signature
 * @param mixed $public_key
 * @param string|int $algorithm [optional]
 */
function openssl_verify (string $data, string $signature, $public_key = null, string|int $algorithm = 1): int|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $sealed_data
 * @param mixed $encrypted_keys
 * @param array $public_key
 * @param string $cipher_algo
 * @param mixed $iv [optional]
 */
function openssl_seal (string $data, &$sealed_data = null, &$encrypted_keys = null, array $public_key, string $cipher_algo, &$iv = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param mixed $output
 * @param string $encrypted_key
 * @param mixed $private_key
 * @param string $cipher_algo
 * @param string|null $iv [optional]
 */
function openssl_open (string $data, &$output = null, string $encrypted_key, $private_key = null, string $cipher_algo, ?string $iv = NULL): bool {}

/**
 * {@inheritdoc}
 * @param bool $aliases [optional]
 */
function openssl_get_md_methods (bool $aliases = false): array {}

/**
 * {@inheritdoc}
 * @param bool $aliases [optional]
 */
function openssl_get_cipher_methods (bool $aliases = false): array {}

/**
 * {@inheritdoc}
 */
function openssl_get_curve_names (): array|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param string $digest_algo
 * @param bool $binary [optional]
 */
function openssl_digest (string $data, string $digest_algo, bool $binary = false): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param string $cipher_algo
 * @param string $passphrase
 * @param int $options [optional]
 * @param string $iv [optional]
 * @param mixed $tag [optional]
 * @param string $aad [optional]
 * @param int $tag_length [optional]
 */
function openssl_encrypt (string $data, string $cipher_algo, string $passphrase, int $options = 0, string $iv = '', &$tag = NULL, string $aad = '', int $tag_length = 16): string|false {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param string $cipher_algo
 * @param string $passphrase
 * @param int $options [optional]
 * @param string $iv [optional]
 * @param string|null $tag [optional]
 * @param string $aad [optional]
 */
function openssl_decrypt (string $data, string $cipher_algo, string $passphrase, int $options = 0, string $iv = '', ?string $tag = NULL, string $aad = ''): string|false {}

/**
 * {@inheritdoc}
 * @param string $cipher_algo
 */
function openssl_cipher_iv_length (string $cipher_algo): int|false {}

/**
 * {@inheritdoc}
 * @param string $cipher_algo
 */
function openssl_cipher_key_length (string $cipher_algo): int|false {}

/**
 * {@inheritdoc}
 * @param string $public_key
 * @param OpenSSLAsymmetricKey $private_key
 */
function openssl_dh_compute_key (string $public_key, OpenSSLAsymmetricKey $private_key): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $public_key
 * @param mixed $private_key
 * @param int $key_length [optional]
 */
function openssl_pkey_derive ($public_key = null, $private_key = null, int $key_length = 0): string|false {}

/**
 * {@inheritdoc}
 * @param int $length
 * @param mixed $strong_result [optional]
 */
function openssl_random_pseudo_bytes (int $length, &$strong_result = NULL): string {}

/**
 * {@inheritdoc}
 * @param OpenSSLAsymmetricKey $private_key
 * @param string $challenge
 * @param int $digest_algo [optional]
 */
function openssl_spki_new (OpenSSLAsymmetricKey $private_key, string $challenge, int $digest_algo = 2): string|false {}

/**
 * {@inheritdoc}
 * @param string $spki
 */
function openssl_spki_verify (string $spki): bool {}

/**
 * {@inheritdoc}
 * @param string $spki
 */
function openssl_spki_export (string $spki): string|false {}

/**
 * {@inheritdoc}
 * @param string $spki
 */
function openssl_spki_export_challenge (string $spki): string|false {}

/**
 * {@inheritdoc}
 */
function openssl_get_cert_locations (): array {}

define ('OPENSSL_VERSION_TEXT', "OpenSSL 3.1.4 24 Oct 2023");
define ('OPENSSL_VERSION_NUMBER', 806355008);
define ('X509_PURPOSE_SSL_CLIENT', 1);
define ('X509_PURPOSE_SSL_SERVER', 2);
define ('X509_PURPOSE_NS_SSL_SERVER', 3);
define ('X509_PURPOSE_SMIME_SIGN', 4);
define ('X509_PURPOSE_SMIME_ENCRYPT', 5);
define ('X509_PURPOSE_CRL_SIGN', 6);
define ('X509_PURPOSE_ANY', 7);
define ('OPENSSL_ALGO_SHA1', 1);
define ('OPENSSL_ALGO_MD5', 2);
define ('OPENSSL_ALGO_MD4', 3);
define ('OPENSSL_ALGO_SHA224', 6);
define ('OPENSSL_ALGO_SHA256', 7);
define ('OPENSSL_ALGO_SHA384', 8);
define ('OPENSSL_ALGO_SHA512', 9);
define ('OPENSSL_ALGO_RMD160', 10);
define ('PKCS7_DETACHED', 64);
define ('PKCS7_TEXT', 1);
define ('PKCS7_NOINTERN', 16);
define ('PKCS7_NOVERIFY', 32);
define ('PKCS7_NOCHAIN', 8);
define ('PKCS7_NOCERTS', 2);
define ('PKCS7_NOATTR', 256);
define ('PKCS7_BINARY', 128);
define ('PKCS7_NOSIGS', 4);
define ('PKCS7_NOOLDMIMETYPE', 1024);
define ('OPENSSL_CMS_DETACHED', 64);
define ('OPENSSL_CMS_TEXT', 1);
define ('OPENSSL_CMS_NOINTERN', 16);
define ('OPENSSL_CMS_NOVERIFY', 32);
define ('OPENSSL_CMS_NOCERTS', 2);
define ('OPENSSL_CMS_NOATTR', 256);
define ('OPENSSL_CMS_BINARY', 128);
define ('OPENSSL_CMS_NOSIGS', 12);
define ('OPENSSL_CMS_OLDMIMETYPE', 1024);
define ('OPENSSL_PKCS1_PADDING', 1);
define ('OPENSSL_NO_PADDING', 3);
define ('OPENSSL_PKCS1_OAEP_PADDING', 4);
define ('OPENSSL_DEFAULT_STREAM_CIPHERS', "ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-AES256-GCM-SHA384:DHE-RSA-AES128-GCM-SHA256:DHE-DSS-AES128-GCM-SHA256:kEDH+AESGCM:ECDHE-RSA-AES128-SHA256:ECDHE-ECDSA-AES128-SHA256:ECDHE-RSA-AES128-SHA:ECDHE-ECDSA-AES128-SHA:ECDHE-RSA-AES256-SHA384:ECDHE-ECDSA-AES256-SHA384:ECDHE-RSA-AES256-SHA:ECDHE-ECDSA-AES256-SHA:DHE-RSA-AES128-SHA256:DHE-RSA-AES128-SHA:DHE-DSS-AES128-SHA256:DHE-RSA-AES256-SHA256:DHE-DSS-AES256-SHA:DHE-RSA-AES256-SHA:AES128-GCM-SHA256:AES256-GCM-SHA384:AES128:AES256:HIGH:!SSLv2:!aNULL:!eNULL:!EXPORT:!DES:!MD5:!RC4:!ADH");
define ('OPENSSL_CIPHER_RC2_40', 0);
define ('OPENSSL_CIPHER_RC2_128', 1);
define ('OPENSSL_CIPHER_RC2_64', 2);
define ('OPENSSL_CIPHER_DES', 3);
define ('OPENSSL_CIPHER_3DES', 4);
define ('OPENSSL_CIPHER_AES_128_CBC', 5);
define ('OPENSSL_CIPHER_AES_192_CBC', 6);
define ('OPENSSL_CIPHER_AES_256_CBC', 7);
define ('OPENSSL_KEYTYPE_RSA', 0);
define ('OPENSSL_KEYTYPE_DSA', 1);
define ('OPENSSL_KEYTYPE_DH', 2);
define ('OPENSSL_KEYTYPE_EC', 3);
define ('OPENSSL_RAW_DATA', 1);
define ('OPENSSL_ZERO_PADDING', 2);
define ('OPENSSL_DONT_ZERO_PAD_KEY', 4);
define ('OPENSSL_TLSEXT_SERVER_NAME', 1);
define ('OPENSSL_ENCODING_DER', 0);
define ('OPENSSL_ENCODING_SMIME', 1);
define ('OPENSSL_ENCODING_PEM', 2);

// End of openssl v.8.3.0
