<?php

// Start of openssl v.8.2.6

/**
 * A fully opaque class which replaces OpenSSL X.509 resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.opensslcertificate.php
 */
final class OpenSSLCertificate  {
}

/**
 * A fully opaque class which replaces OpenSSL X.509 CSR resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.opensslcertificatesigningrequest.php
 */
final class OpenSSLCertificateSigningRequest  {
}

/**
 * A fully opaque class which replaces OpenSSL key resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.opensslasymmetrickey.php
 */
final class OpenSSLAsymmetricKey  {
}

/**
 * Exports a certificate to file
 * @link http://www.php.net/manual/en/function.openssl-x509-export-to-file.php
 * @param OpenSSLCertificate|string $certificate 
 * @param string $output_filename 
 * @param bool $no_text [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_x509_export_to_file (OpenSSLCertificate|string $certificate, string $output_filename, bool $no_text = true): bool {}

/**
 * Exports a certificate as a string
 * @link http://www.php.net/manual/en/function.openssl-x509-export.php
 * @param OpenSSLCertificate|string $certificate 
 * @param string $output 
 * @param bool $no_text [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_x509_export (OpenSSLCertificate|string $certificate, string &$output, bool $no_text = true): bool {}

/**
 * Calculates the fingerprint, or digest, of a given X.509 certificate
 * @link http://www.php.net/manual/en/function.openssl-x509-fingerprint.php
 * @param OpenSSLCertificate|string $certificate 
 * @param string $digest_algo [optional] 
 * @param bool $binary [optional] 
 * @return string|false Returns a string containing the calculated certificate fingerprint as lowercase hexits unless binary is set to true in which case the raw binary representation of the message digest is returned.
 * <p>Returns false on failure.</p>
 */
function openssl_x509_fingerprint (OpenSSLCertificate|string $certificate, string $digest_algo = '"sha1"', bool $binary = false): string|false {}

/**
 * Checks if a private key corresponds to a certificate
 * @link http://www.php.net/manual/en/function.openssl-x509-check-private-key.php
 * @param OpenSSLCertificate|string $certificate 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @return bool Returns true if private_key is the private key that
 * corresponds to certificate, or false otherwise.
 */
function openssl_x509_check_private_key (OpenSSLCertificate|string $certificate, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key): bool {}

/**
 * Verifies digital signature of x509 certificate against a public key
 * @link http://www.php.net/manual/en/function.openssl-x509-verify.php
 * @param OpenSSLCertificate|string $certificate 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key 
 * @return int Returns 1 if the signature is correct, 0 if it is incorrect, and
 * -1 on error.
 */
function openssl_x509_verify (OpenSSLCertificate|string $certificate, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key): int {}

/**
 * Parse an X509 certificate and return the information as an array
 * @link http://www.php.net/manual/en/function.openssl-x509-parse.php
 * @param OpenSSLCertificate|string $certificate 
 * @param bool $short_names [optional] 
 * @return array|false The structure of the returned data is (deliberately) not
 * yet documented, as it is still subject to change.
 */
function openssl_x509_parse (OpenSSLCertificate|string $certificate, bool $short_names = true): array|false {}

/**
 * Verifies if a certificate can be used for a particular purpose
 * @link http://www.php.net/manual/en/function.openssl-x509-checkpurpose.php
 * @param OpenSSLCertificate|string $certificate 
 * @param int $purpose 
 * @param array $ca_info [optional] 
 * @param string|null $untrusted_certificates_file [optional] 
 * @return bool|int Returns true if the certificate can be used for the intended purpose,
 * false if it cannot, or -1 on error.
 */
function openssl_x509_checkpurpose (OpenSSLCertificate|string $certificate, int $purpose, array $ca_info = '[]', ?string $untrusted_certificates_file = null): bool|int {}

/**
 * Parse an X.509 certificate and return an object for
 * it
 * @link http://www.php.net/manual/en/function.openssl-x509-read.php
 * @param OpenSSLCertificate|string $certificate 
 * @return OpenSSLCertificate|false Returns an OpenSSLCertificate on success or false on failure.
 */
function openssl_x509_read (OpenSSLCertificate|string $certificate): OpenSSLCertificate|false {}

/**
 * Free certificate resource
 * @link http://www.php.net/manual/en/function.openssl-x509-free.php
 * @param OpenSSLCertificate $certificate 
 * @return void No value is returned.
 * @deprecated 1
 */
function openssl_x509_free (OpenSSLCertificate $certificate): void {}

/**
 * Exports a PKCS#12 Compatible Certificate Store File
 * @link http://www.php.net/manual/en/function.openssl-pkcs12-export-to-file.php
 * @param OpenSSLCertificate|string $certificate 
 * @param string $output_filename 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param string $passphrase 
 * @param array $options [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkcs12_export_to_file (OpenSSLCertificate|string $certificate, string $output_filename, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, string $passphrase, array $options = '[]'): bool {}

/**
 * Exports a PKCS#12 Compatible Certificate Store File to variable
 * @link http://www.php.net/manual/en/function.openssl-pkcs12-export.php
 * @param OpenSSLCertificate|string $certificate 
 * @param string $output 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param string $passphrase 
 * @param array $options [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkcs12_export (OpenSSLCertificate|string $certificate, string &$output, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, string $passphrase, array $options = '[]'): bool {}

/**
 * Parse a PKCS#12 Certificate Store into an array
 * @link http://www.php.net/manual/en/function.openssl-pkcs12-read.php
 * @param string $pkcs12 
 * @param array $certificates 
 * @param string $passphrase 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkcs12_read (string $pkcs12, array &$certificates, string $passphrase): bool {}

/**
 * Exports a CSR to a file
 * @link http://www.php.net/manual/en/function.openssl-csr-export-to-file.php
 * @param OpenSSLCertificateSigningRequest|string $csr 
 * @param string $output_filename 
 * @param bool $no_text [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_csr_export_to_file (OpenSSLCertificateSigningRequest|string $csr, string $output_filename, bool $no_text = true): bool {}

/**
 * Exports a CSR as a string
 * @link http://www.php.net/manual/en/function.openssl-csr-export.php
 * @param OpenSSLCertificateSigningRequest|string $csr 
 * @param string $output 
 * @param bool $no_text [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_csr_export (OpenSSLCertificateSigningRequest|string $csr, string &$output, bool $no_text = true): bool {}

/**
 * Sign a CSR with another certificate (or itself) and generate a certificate
 * @link http://www.php.net/manual/en/function.openssl-csr-sign.php
 * @param OpenSSLCertificateSigningRequest|string $csr 
 * @param OpenSSLCertificate|string|null $ca_certificate 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param int $days 
 * @param array|null $options [optional] 
 * @param int $serial [optional] 
 * @return OpenSSLCertificate|false Returns an OpenSSLCertificate on success, false on failure.
 */
function openssl_csr_sign (OpenSSLCertificateSigningRequest|string $csr, OpenSSLCertificate|string|null $ca_certificate, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, int $days, ?array $options = null, int $serial = null): OpenSSLCertificate|false {}

/**
 * Generates a CSR
 * @link http://www.php.net/manual/en/function.openssl-csr-new.php
 * @param array $distinguished_names 
 * @param OpenSSLAsymmetricKey $private_key 
 * @param array|null $options [optional] 
 * @param array|null $extra_attributes [optional] 
 * @return OpenSSLCertificateSigningRequest|false Returns the CSR or false on failure.
 */
function openssl_csr_new (array $distinguished_names, OpenSSLAsymmetricKey &$private_key, ?array $options = null, ?array $extra_attributes = null): OpenSSLCertificateSigningRequest|false {}

/**
 * Returns the subject of a CSR
 * @link http://www.php.net/manual/en/function.openssl-csr-get-subject.php
 * @param OpenSSLCertificateSigningRequest|string $csr 
 * @param bool $short_names [optional] 
 * @return array|false Returns an associative array with subject description, or false on failure.
 */
function openssl_csr_get_subject (OpenSSLCertificateSigningRequest|string $csr, bool $short_names = true): array|false {}

/**
 * Returns the public key of a CSR
 * @link http://www.php.net/manual/en/function.openssl-csr-get-public-key.php
 * @param OpenSSLCertificateSigningRequest|string $csr 
 * @param bool $short_names [optional] 
 * @return OpenSSLAsymmetricKey|false Returns an OpenSSLAsymmetricKey on success, or false on error.
 */
function openssl_csr_get_public_key (OpenSSLCertificateSigningRequest|string $csr, bool $short_names = true): OpenSSLAsymmetricKey|false {}

/**
 * Generates a new private key
 * @link http://www.php.net/manual/en/function.openssl-pkey-new.php
 * @param array|null $options [optional] 
 * @return OpenSSLAsymmetricKey|false Returns an OpenSSLAsymmetricKey instance for the pkey on success, or false on
 * error.
 */
function openssl_pkey_new (?array $options = null): OpenSSLAsymmetricKey|false {}

/**
 * Gets an exportable representation of a key into a file
 * @link http://www.php.net/manual/en/function.openssl-pkey-export-to-file.php
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $key 
 * @param string $output_filename 
 * @param string|null $passphrase [optional] 
 * @param array|null $options [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkey_export_to_file (OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $key, string $output_filename, ?string $passphrase = null, ?array $options = null): bool {}

/**
 * Gets an exportable representation of a key into a string
 * @link http://www.php.net/manual/en/function.openssl-pkey-export.php
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $key 
 * @param string $output 
 * @param string|null $passphrase [optional] 
 * @param array|null $options [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkey_export (OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $key, string &$output, ?string $passphrase = null, ?array $options = null): bool {}

/**
 * Extract public key from certificate and prepare it for use
 * @link http://www.php.net/manual/en/function.openssl-pkey-get-public.php
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key 
 * @return OpenSSLAsymmetricKey|false Returns an OpenSSLAsymmetricKey instance on success, or false on error.
 */
function openssl_pkey_get_public (OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key): OpenSSLAsymmetricKey|false {}

/**
 * Alias of openssl_pkey_get_public
 * @link http://www.php.net/manual/en/function.openssl-get-publickey.php
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key 
 * @return OpenSSLAsymmetricKey|false Returns an OpenSSLAsymmetricKey instance on success, or false on error.
 */
function openssl_get_publickey (OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key): OpenSSLAsymmetricKey|false {}

/**
 * Frees a private key
 * @link http://www.php.net/manual/en/function.openssl-pkey-free.php
 * @param OpenSSLAsymmetricKey $key 
 * @return void No value is returned.
 * @deprecated 1
 */
function openssl_pkey_free (OpenSSLAsymmetricKey $key): void {}

/**
 * Free key resource
 * @link http://www.php.net/manual/en/function.openssl-free-key.php
 * @param OpenSSLAsymmetricKey $key 
 * @return void No value is returned.
 * @deprecated 1
 */
function openssl_free_key (OpenSSLAsymmetricKey $key): void {}

/**
 * Get a private key
 * @link http://www.php.net/manual/en/function.openssl-pkey-get-private.php
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param string|null $passphrase [optional] 
 * @return OpenSSLAsymmetricKey|false Returns an OpenSSLAsymmetricKey instance on success, or false on error.
 */
function openssl_pkey_get_private (OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, ?string $passphrase = null): OpenSSLAsymmetricKey|false {}

/**
 * Alias of openssl_pkey_get_private
 * @link http://www.php.net/manual/en/function.openssl-get-privatekey.php
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param string|null $passphrase [optional] 
 * @return OpenSSLAsymmetricKey|false Returns an OpenSSLAsymmetricKey instance on success, or false on error.
 */
function openssl_get_privatekey (OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, ?string $passphrase = null): OpenSSLAsymmetricKey|false {}

/**
 * Returns an array with the key details
 * @link http://www.php.net/manual/en/function.openssl-pkey-get-details.php
 * @param OpenSSLAsymmetricKey $key 
 * @return array|false Returns an array with the key details on success or false on failure.
 * Returned array has indexes bits (number of bits),
 * key (string representation of the public key) and
 * type (type of the key which is one of
 * OPENSSL_KEYTYPE_RSA,
 * OPENSSL_KEYTYPE_DSA,
 * OPENSSL_KEYTYPE_DH,
 * OPENSSL_KEYTYPE_EC or -1 meaning unknown).
 * <p>Depending on the key type used, additional details may be returned. Note that 
 * some elements may not always be available.</p>
 */
function openssl_pkey_get_details (OpenSSLAsymmetricKey $key): array|false {}

/**
 * Generates a PKCS5 v2 PBKDF2 string
 * @link http://www.php.net/manual/en/function.openssl-pbkdf2.php
 * @param string $password Password from which the derived key is generated.
 * @param string $salt PBKDF2 recommends a crytographic salt of at least 64 bits (8 bytes).
 * @param int $key_length Length of desired output key.
 * @param int $iterations The number of iterations desired. NIST 
 * recommends at least 10,000.
 * @param string $digest_algo [optional] Optional hash or digest algorithm from openssl_get_md_methods. Defaults to SHA-1.
 * @return string|false Returns raw binary string or false on failure.
 */
function openssl_pbkdf2 (string $password, string $salt, int $key_length, int $iterations, string $digest_algo = '"sha1"'): string|false {}

/**
 * Verifies the signature of an S/MIME signed message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-verify.php
 * @param string $input_filename 
 * @param int $flags 
 * @param string|null $signers_certificates_filename [optional] 
 * @param array $ca_info [optional] 
 * @param string|null $untrusted_certificates_filename [optional] 
 * @param string|null $content [optional] 
 * @param string|null $output_filename [optional] 
 * @return bool|int Returns true if the signature is verified, false if it is not correct
 * (the message has been tampered with, or the signing certificate is invalid),
 * or -1 on error.
 */
function openssl_pkcs7_verify (string $input_filename, int $flags, ?string $signers_certificates_filename = null, array $ca_info = '[]', ?string $untrusted_certificates_filename = null, ?string $content = null, ?string $output_filename = null): bool|int {}

/**
 * Encrypt an S/MIME message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-encrypt.php
 * @param string $input_filename 
 * @param string $output_filename 
 * @param OpenSSLCertificate|array|string $certificate 
 * @param array|null $headers 
 * @param int $flags [optional] 
 * @param int $cipher_algo [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkcs7_encrypt (string $input_filename, string $output_filename, OpenSSLCertificate|array|string $certificate, ?array $headers, int $flags = null, int $cipher_algo = OPENSSL_CIPHER_AES_128_CBC): bool {}

/**
 * Sign an S/MIME message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-sign.php
 * @param string $input_filename 
 * @param string $output_filename 
 * @param OpenSSLCertificate|string $certificate 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param array|null $headers 
 * @param int $flags [optional] 
 * @param string|null $untrusted_certificates_filename [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkcs7_sign (string $input_filename, string $output_filename, OpenSSLCertificate|string $certificate, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, ?array $headers, int $flags = PKCS7_DETACHED, ?string $untrusted_certificates_filename = null): bool {}

/**
 * Decrypts an S/MIME encrypted message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-decrypt.php
 * @param string $input_filename 
 * @param string $output_filename 
 * @param OpenSSLCertificate|string $certificate 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string|null $private_key [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkcs7_decrypt (string $input_filename, string $output_filename, OpenSSLCertificate|string $certificate, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string|null $private_key = null): bool {}

/**
 * Export the PKCS7 file to an array of PEM certificates
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-read.php
 * @param string $data The string of data you wish to parse (p7b format).
 * @param array $certificates The array of PEM certificates from the p7b input data.
 * @return bool Returns true on success or false on failure.
 */
function openssl_pkcs7_read (string $data, array &$certificates): bool {}

/**
 * Verify a CMS signature
 * @link http://www.php.net/manual/en/function.openssl-cms-verify.php
 * @param string $input_filename The input file.
 * @param int $flags [optional] Flags to pass to cms_verify.
 * @param string|null $certificates [optional] A file with the signer certificate and optionally intermediate certificates.
 * @param array $ca_info [optional] An array containing self-signed certificate authority certificates.
 * @param string|null $untrusted_certificates_filename [optional] A file containing additional intermediate certificates.
 * @param string|null $content [optional] A file pointing to the content when signatures are detached.
 * @param string|null $pk7 [optional] 
 * @param string|null $sigfile [optional] A file to save the signature to.
 * @param int $encoding [optional] The encoding of the input file. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @return bool Returns true on success or false on failure.
 */
function openssl_cms_verify (string $input_filename, int $flags = null, ?string $certificates = null, array $ca_info = '[]', ?string $untrusted_certificates_filename = null, ?string $content = null, ?string $pk7 = null, ?string $sigfile = null, int $encoding = OPENSSL_ENCODING_SMIME): bool {}

/**
 * Encrypt a CMS message
 * @link http://www.php.net/manual/en/function.openssl-cms-encrypt.php
 * @param string $input_filename The file to be encrypted.
 * @param string $output_filename The output file.
 * @param OpenSSLCertificate|array|string $certificate Recipients to encrypt to.
 * @param array|null $headers Headers to include when S/MIME is used.
 * @param int $flags [optional] Flags to be passed to CMS_sign.
 * @param int $encoding [optional] An encoding to output. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @param int $cipher_algo [optional] A cypher to use.
 * @return bool Returns true on success or false on failure.
 */
function openssl_cms_encrypt (string $input_filename, string $output_filename, OpenSSLCertificate|array|string $certificate, ?array $headers, int $flags = null, int $encoding = OPENSSL_ENCODING_SMIME, int $cipher_algo = OPENSSL_CIPHER_AES_128_CBC): bool {}

/**
 * Sign a file
 * @link http://www.php.net/manual/en/function.openssl-cms-sign.php
 * @param string $input_filename The name of the file to be signed.
 * @param string $output_filename The name of the file to deposit the results.
 * @param OpenSSLCertificate|string $certificate The signing certificate.
 * See Key/Certificate parameters for a list of valid values.
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key The key associated with certificate.
 * See Key/Certificate parameters for a list of valid values.
 * @param array|null $headers An array of headers to be included in S/MIME output.
 * @param int $flags [optional] Flags to be passed to cms_sign.
 * @param int $encoding [optional] The encoding of the output file. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @param string|null $untrusted_certificates_filename [optional] Intermediate certificates to be included in the signature.
 * @return bool Returns true on success or false on failure.
 */
function openssl_cms_sign (string $input_filename, string $output_filename, OpenSSLCertificate|string $certificate, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, ?array $headers, int $flags = null, int $encoding = OPENSSL_ENCODING_SMIME, ?string $untrusted_certificates_filename = null): bool {}

/**
 * Decrypt a CMS message
 * @link http://www.php.net/manual/en/function.openssl-cms-decrypt.php
 * @param string $input_filename The name of a file containing encrypted content.
 * @param string $output_filename The name of the file to deposit the decrypted content.
 * @param OpenSSLCertificate|string $certificate The name of the file containing a certificate of the recipient.
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string|null $private_key [optional] The name of the file containing a PKCS#8 key.
 * @param int $encoding [optional] The encoding of the input file. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @return bool Returns true on success or false on failure.
 */
function openssl_cms_decrypt (string $input_filename, string $output_filename, OpenSSLCertificate|string $certificate, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string|null $private_key = null, int $encoding = OPENSSL_ENCODING_SMIME): bool {}

/**
 * Export the CMS file to an array of PEM certificates
 * @link http://www.php.net/manual/en/function.openssl-cms-read.php
 * @param string $input_filename 
 * @param array $certificates 
 * @return bool Returns true on success or false on failure.
 */
function openssl_cms_read (string $input_filename, array &$certificates): bool {}

/**
 * Encrypts data with private key
 * @link http://www.php.net/manual/en/function.openssl-private-encrypt.php
 * @param string $data 
 * @param string $encrypted_data 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param int $padding [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_private_encrypt (string $data, string &$encrypted_data, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, int $padding = OPENSSL_PKCS1_PADDING): bool {}

/**
 * Decrypts data with private key
 * @link http://www.php.net/manual/en/function.openssl-private-decrypt.php
 * @param string $data 
 * @param string $decrypted_data 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param int $padding [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_private_decrypt (string $data, string &$decrypted_data, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, int $padding = OPENSSL_PKCS1_PADDING): bool {}

/**
 * Encrypts data with public key
 * @link http://www.php.net/manual/en/function.openssl-public-encrypt.php
 * @param string $data 
 * @param string $encrypted_data 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key 
 * @param int $padding [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_public_encrypt (string $data, string &$encrypted_data, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key, int $padding = OPENSSL_PKCS1_PADDING): bool {}

/**
 * Decrypts data with public key
 * @link http://www.php.net/manual/en/function.openssl-public-decrypt.php
 * @param string $data 
 * @param string $decrypted_data 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key 
 * @param int $padding [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_public_decrypt (string $data, string &$decrypted_data, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key, int $padding = OPENSSL_PKCS1_PADDING): bool {}

/**
 * Return openSSL error message
 * @link http://www.php.net/manual/en/function.openssl-error-string.php
 * @return string|false Returns an error message string, or false if there are no more error
 * messages to return.
 */
function openssl_error_string (): string|false {}

/**
 * Generate signature
 * @link http://www.php.net/manual/en/function.openssl-sign.php
 * @param string $data 
 * @param string $signature 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param string|int $algorithm [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_sign (string $data, string &$signature, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, string|int $algorithm = OPENSSL_ALGO_SHA1): bool {}

/**
 * Verify signature
 * @link http://www.php.net/manual/en/function.openssl-verify.php
 * @param string $data 
 * @param string $signature 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key 
 * @param string|int $algorithm [optional] 
 * @return int|false Returns 1 if the signature is correct, 0 if it is incorrect, and
 * -1 or false on error.
 */
function openssl_verify (string $data, string $signature, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key, string|int $algorithm = OPENSSL_ALGO_SHA1): int|false {}

/**
 * Seal (encrypt) data
 * @link http://www.php.net/manual/en/function.openssl-seal.php
 * @param string $data 
 * @param string $sealed_data 
 * @param array $encrypted_keys 
 * @param array $public_key 
 * @param string $cipher_algo 
 * @param string $iv [optional] 
 * @return int|false Returns the length of the sealed data on success, or false on error.
 * If successful the sealed data is returned in
 * sealed_data, and the envelope keys in
 * encrypted_keys.
 */
function openssl_seal (string $data, string &$sealed_data, array &$encrypted_keys, array $public_key, string $cipher_algo, string &$iv = null): int|false {}

/**
 * Open sealed data
 * @link http://www.php.net/manual/en/function.openssl-open.php
 * @param string $data 
 * @param string $output 
 * @param string $encrypted_key 
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key 
 * @param string $cipher_algo 
 * @param string|null $iv [optional] 
 * @return bool Returns true on success or false on failure.
 */
function openssl_open (string $data, string &$output, string $encrypted_key, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, string $cipher_algo, ?string $iv = null): bool {}

/**
 * Gets available digest methods
 * @link http://www.php.net/manual/en/function.openssl-get-md-methods.php
 * @param bool $aliases [optional] 
 * @return array An array of available digest methods.
 */
function openssl_get_md_methods (bool $aliases = false): array {}

/**
 * Gets available cipher methods
 * @link http://www.php.net/manual/en/function.openssl-get-cipher-methods.php
 * @param bool $aliases [optional] 
 * @return array An array of available cipher methods.
 * Note that prior to OpenSSL 1.1.1, the cipher methods have been returned in
 * upper case and lower case spelling; as of OpenSSL 1.1.1 only the lower case
 * variants are returned.
 */
function openssl_get_cipher_methods (bool $aliases = false): array {}

/**
 * Gets list of available curve names for ECC
 * @link http://www.php.net/manual/en/function.openssl-get-curve-names.php
 * @return array|false An array of available curve names, or false on failure.
 */
function openssl_get_curve_names (): array|false {}

/**
 * Computes a digest
 * @link http://www.php.net/manual/en/function.openssl-digest.php
 * @param string $data 
 * @param string $digest_algo 
 * @param bool $binary [optional] 
 * @return string|false Returns the digested hash value on success or false on failure.
 */
function openssl_digest (string $data, string $digest_algo, bool $binary = false): string|false {}

/**
 * Encrypts data
 * @link http://www.php.net/manual/en/function.openssl-encrypt.php
 * @param string $data 
 * @param string $cipher_algo 
 * @param string $passphrase 
 * @param int $options [optional] 
 * @param string $iv [optional] 
 * @param string $tag [optional] 
 * @param string $aad [optional] 
 * @param int $tag_length [optional] 
 * @return string|false Returns the encrypted string on success or false on failure.
 */
function openssl_encrypt (string $data, string $cipher_algo, string $passphrase, int $options = null, string $iv = '""', string &$tag = null, string $aad = '""', int $tag_length = 16): string|false {}

/**
 * Decrypts data
 * @link http://www.php.net/manual/en/function.openssl-decrypt.php
 * @param string $data 
 * @param string $cipher_algo 
 * @param string $passphrase 
 * @param int $options [optional] 
 * @param string $iv [optional] 
 * @param string|null $tag [optional] 
 * @param string $aad [optional] 
 * @return string|false The decrypted string on success or false on failure.
 */
function openssl_decrypt (string $data, string $cipher_algo, string $passphrase, int $options = null, string $iv = '""', ?string $tag = null, string $aad = '""'): string|false {}

/**
 * Gets the cipher iv length
 * @link http://www.php.net/manual/en/function.openssl-cipher-iv-length.php
 * @param string $cipher_algo The cipher method, see openssl_get_cipher_methods for a list of potential values.
 * @return int|false Returns the cipher length on success, or false on failure.
 */
function openssl_cipher_iv_length (string $cipher_algo): int|false {}

/**
 * Gets the cipher key length
 * @link http://www.php.net/manual/en/function.openssl-cipher-key-length.php
 * @param string $cipher_algo The cipher method, see openssl_get_cipher_methods for a list of potential values.
 * @return int|false Returns the cipher length on success, or false on failure.
 */
function openssl_cipher_key_length (string $cipher_algo): int|false {}

/**
 * Computes shared secret for public value of remote DH public key and local DH key
 * @link http://www.php.net/manual/en/function.openssl-dh-compute-key.php
 * @param string $public_key 
 * @param OpenSSLAsymmetricKey $private_key 
 * @return string|false Returns shared secret on success or false on failure.
 */
function openssl_dh_compute_key (string $public_key, OpenSSLAsymmetricKey $private_key): string|false {}

/**
 * Computes shared secret for public value of remote and local DH or ECDH key
 * @link http://www.php.net/manual/en/function.openssl-pkey-derive.php
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key public_key is the public key for the derivation.
 * See Public/Private Key parameters for a list of valid values.
 * @param OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key private_key is the private key for the derivation.
 * See Public/Private Key parameters for a list of valid values.
 * @param int $key_length [optional] If not zero, will set the desired length of the derived secret.
 * @return string|false The derived secret on success or false on failure.
 */
function openssl_pkey_derive (OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $public_key, OpenSSLAsymmetricKey|OpenSSLCertificate|array|string $private_key, int $key_length = null): string|false {}

/**
 * Generate a pseudo-random string of bytes
 * @link http://www.php.net/manual/en/function.openssl-random-pseudo-bytes.php
 * @param int $length 
 * @param bool $strong_result [optional] 
 * @return string Returns the generated string of bytes.
 */
function openssl_random_pseudo_bytes (int $length, bool &$strong_result = null): string {}

/**
 * Generate a new signed public key and challenge
 * @link http://www.php.net/manual/en/function.openssl-spki-new.php
 * @param OpenSSLAsymmetricKey $private_key private_key should be set to a private key that was
 * previously generated by openssl_pkey_new (or
 * otherwise obtained from the other openssl_pkey family of functions).
 * The corresponding public portion of the key will be used to sign the
 * CSR.
 * @param string $challenge The challenge associated to associate with the SPKAC
 * @param int $digest_algo [optional] The digest algorithm. See openssl_get_md_method().
 * @return string|false Returns a signed public key and challenge string or false on failure.
 */
function openssl_spki_new (OpenSSLAsymmetricKey $private_key, string $challenge, int $digest_algo = OPENSSL_ALGO_MD5): string|false {}

/**
 * Verifies a signed public key and challenge
 * @link http://www.php.net/manual/en/function.openssl-spki-verify.php
 * @param string $spki Expects a valid signed public key and challenge
 * @return bool Returns true on success or false on failure.
 */
function openssl_spki_verify (string $spki): bool {}

/**
 * Exports a valid PEM formatted public key signed public key and challenge
 * @link http://www.php.net/manual/en/function.openssl-spki-export.php
 * @param string $spki Expects a valid signed public key and challenge
 * @return string|false Returns the associated PEM formatted public key or false on failure.
 */
function openssl_spki_export (string $spki): string|false {}

/**
 * Exports the challenge associated with a signed public key and challenge
 * @link http://www.php.net/manual/en/function.openssl-spki-export-challenge.php
 * @param string $spki Expects a valid signed public key and challenge
 * @return string|false Returns the associated challenge string or false on failure.
 */
function openssl_spki_export_challenge (string $spki): string|false {}

/**
 * Retrieve the available certificate locations
 * @link http://www.php.net/manual/en/function.openssl-get-cert-locations.php
 * @return array Returns an array with the available certificate locations.
 */
function openssl_get_cert_locations (): array {}


/**
 * 
 * @link http://www.php.net/manual/en/openssl.constversion.php
 * @var string
 */
define ('OPENSSL_VERSION_TEXT', "OpenSSL 1.1.1t  7 Feb 2023");

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constversion.php
 * @var int
 */
define ('OPENSSL_VERSION_NUMBER', 269488463);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.purpose-check.php
 * @var int
 */
define ('X509_PURPOSE_SSL_CLIENT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.purpose-check.php
 * @var int
 */
define ('X509_PURPOSE_SSL_SERVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.purpose-check.php
 * @var int
 */
define ('X509_PURPOSE_NS_SSL_SERVER', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.purpose-check.php
 * @var int
 */
define ('X509_PURPOSE_SMIME_SIGN', 4);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.purpose-check.php
 * @var int
 */
define ('X509_PURPOSE_SMIME_ENCRYPT', 5);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.purpose-check.php
 * @var int
 */
define ('X509_PURPOSE_CRL_SIGN', 6);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.purpose-check.php
 * @var int
 */
define ('X509_PURPOSE_ANY', 7);

/**
 * Used as default algorithm by openssl_sign and
 * openssl_verify.
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
define ('OPENSSL_ALGO_SHA1', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
define ('OPENSSL_ALGO_MD5', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
define ('OPENSSL_ALGO_MD4', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
define ('OPENSSL_ALGO_SHA224', 6);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
define ('OPENSSL_ALGO_SHA256', 7);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
define ('OPENSSL_ALGO_SHA384', 8);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
define ('OPENSSL_ALGO_SHA512', 9);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.signature-algos.php
 * @var int
 */
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
define ('OPENSSL_CMS_DETACHED', 64);
define ('OPENSSL_CMS_TEXT', 1);
define ('OPENSSL_CMS_NOINTERN', 16);
define ('OPENSSL_CMS_NOVERIFY', 32);
define ('OPENSSL_CMS_NOCERTS', 2);
define ('OPENSSL_CMS_NOATTR', 256);
define ('OPENSSL_CMS_BINARY', 128);
define ('OPENSSL_CMS_NOSIGS', 12);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.padding.php
 * @var int
 */
define ('OPENSSL_PKCS1_PADDING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.padding.php
 * @var int
 */
define ('OPENSSL_SSLV23_PADDING', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.padding.php
 * @var int
 */
define ('OPENSSL_NO_PADDING', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.padding.php
 * @var int
 */
define ('OPENSSL_PKCS1_OAEP_PADDING', 4);
define ('OPENSSL_DEFAULT_STREAM_CIPHERS', "ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-AES256-GCM-SHA384:DHE-RSA-AES128-GCM-SHA256:DHE-DSS-AES128-GCM-SHA256:kEDH+AESGCM:ECDHE-RSA-AES128-SHA256:ECDHE-ECDSA-AES128-SHA256:ECDHE-RSA-AES128-SHA:ECDHE-ECDSA-AES128-SHA:ECDHE-RSA-AES256-SHA384:ECDHE-ECDSA-AES256-SHA384:ECDHE-RSA-AES256-SHA:ECDHE-ECDSA-AES256-SHA:DHE-RSA-AES128-SHA256:DHE-RSA-AES128-SHA:DHE-DSS-AES128-SHA256:DHE-RSA-AES256-SHA256:DHE-DSS-AES256-SHA:DHE-RSA-AES256-SHA:AES128-GCM-SHA256:AES256-GCM-SHA384:AES128:AES256:HIGH:!SSLv2:!aNULL:!eNULL:!EXPORT:!DES:!MD5:!RC4:!ADH");

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_RC2_40', 0);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_RC2_128', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_RC2_64', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_DES', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_3DES', 4);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_AES_128_CBC', 5);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_AES_192_CBC', 6);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.ciphers.php
 * @var int
 */
define ('OPENSSL_CIPHER_AES_256_CBC', 7);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.key-types.php
 * @var int
 */
define ('OPENSSL_KEYTYPE_RSA', 0);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.key-types.php
 * @var int
 */
define ('OPENSSL_KEYTYPE_DSA', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.key-types.php
 * @var int
 */
define ('OPENSSL_KEYTYPE_DH', 2);

/**
 * This constant is only available when PHP is compiled with OpenSSL 0.9.8+.
 * @link http://www.php.net/manual/en/openssl.key-types.php
 * @var int
 */
define ('OPENSSL_KEYTYPE_EC', 3);

/**
 * If OPENSSL_RAW_DATA is set in the
 * openssl_encrypt or openssl_decrypt,
 * the returned data is returned as-is.
 * When it is not specified, Base64 encoded data is returned to the caller.
 * @link http://www.php.net/manual/en/openssl.constants.other.php
 * @var bool
 */
define ('OPENSSL_RAW_DATA', 1);

/**
 * By default encryption operations are padded using standard block
 * padding and the padding is checked and removed when decrypting.
 * If OPENSSL_ZERO_PADDING is set in the
 * openssl_encrypt or openssl_decrypt
 * options then no padding is performed, the total
 * amount of data encrypted or decrypted must then be a multiple of the
 * block size or an error will occur.
 * @link http://www.php.net/manual/en/openssl.constants.other.php
 * @var bool
 */
define ('OPENSSL_ZERO_PADDING', 2);
define ('OPENSSL_DONT_ZERO_PAD_KEY', 4);

/**
 * Whether SNI support is available or not.
 * @link http://www.php.net/manual/en/openssl.constsni.php
 * @var string
 */
define ('OPENSSL_TLSEXT_SERVER_NAME', 1);

/**
 * Indicates that encoding is DER (Distinguished Encoding Rules).
 * @link http://www.php.net/manual/en/openssl.constants.other.php
 * @var int
 */
define ('OPENSSL_ENCODING_DER', 0);

/**
 * Indicates that encoding is S/MIME.
 * @link http://www.php.net/manual/en/openssl.constants.other.php
 * @var int
 */
define ('OPENSSL_ENCODING_SMIME', 1);

/**
 * Indicates that encoding is PEM (Privacy-Enhanced Mail).
 * @link http://www.php.net/manual/en/openssl.constants.other.php
 * @var int
 */
define ('OPENSSL_ENCODING_PEM', 2);

// End of openssl v.8.2.6
