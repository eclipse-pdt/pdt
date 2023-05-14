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
 * @param mixed $certificate 
 * @param string $output_filename Path to the output file.
 * @param bool $no_text [optional] note.openssl.param-notext
 * @return bool true on success or false on failure
 */
function openssl_x509_export_to_file ($certificate, string $output_filename, bool $no_text = null): bool {}

/**
 * Exports a certificate as a string
 * @link http://www.php.net/manual/en/function.openssl-x509-export.php
 * @param mixed $certificate 
 * @param string $output On success, this will hold the PEM.
 * @param bool $no_text [optional] note.openssl.param-notext
 * @return bool true on success or false on failure
 */
function openssl_x509_export ($certificate, string &$output, bool $no_text = null): bool {}

/**
 * Calculates the fingerprint, or digest, of a given X.509 certificate
 * @link http://www.php.net/manual/en/function.openssl-x509-fingerprint.php
 * @param mixed $certificate 
 * @param string $digest_algo [optional] The digest method or hash algorithm to use, e.g. "sha256", one of openssl_get_md_methods.
 * @param bool $binary [optional] When set to true, outputs raw binary data. false outputs lowercase hexits.
 * @return mixed a string containing the calculated certificate fingerprint as lowercase hexits unless binary is set to true in which case the raw binary representation of the message digest is returned.
 * <p>
 * Returns false on failure.
 * </p>
 */
function openssl_x509_fingerprint ($certificate, string $digest_algo = null, bool $binary = null): string|false {}

/**
 * Checks if a private key corresponds to a certificate
 * @link http://www.php.net/manual/en/function.openssl-x509-check-private-key.php
 * @param mixed $certificate The certificate.
 * @param mixed $private_key The private key.
 * @return bool true if private_key is the private key that
 * corresponds to certificate, or false otherwise.
 */
function openssl_x509_check_private_key ($certificate, $private_key): bool {}

/**
 * Verifies digital signature of x509 certificate against a public key
 * @link http://www.php.net/manual/en/function.openssl-x509-verify.php
 * @param mixed $certificate 
 * @param mixed $public_key <p>
 * OpenSSLAsymmetricKey - a key, returned by openssl_get_publickey
 * </p>
 * <p>
 * string - a PEM formatted key, example, "-----BEGIN PUBLIC KEY-----
 * MIIBCgK..."
 * </p>
 * @return int 1 if the signature is correct, 0 if it is incorrect, and
 * -1 on error.
 */
function openssl_x509_verify ($certificate, $public_key): int {}

/**
 * Parse an X509 certificate and return the information as an array
 * @link http://www.php.net/manual/en/function.openssl-x509-parse.php
 * @param mixed $certificate X509 certificate. See Key/Certificate parameters for a list of valid values.
 * @param bool $short_names [optional] short_names controls how the data is indexed in the
 * array - if short_names is true (the default) then
 * fields will be indexed with the short name form, otherwise, the long name
 * form will be used - e.g.: CN is the shortname form of commonName.
 * @return mixed The structure of the returned data is (deliberately) not
 * yet documented, as it is still subject to change.
 */
function openssl_x509_parse ($certificate, bool $short_names = null): array|false {}

/**
 * Verifies if a certificate can be used for a particular purpose
 * @link http://www.php.net/manual/en/function.openssl-x509-checkpurpose.php
 * @param mixed $certificate The examined certificate.
 * @param int $purpose <table>
 * openssl_x509_checkpurpose purposes
 * <table>
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>X509_PURPOSE_SSL_CLIENT</td>
 * <td>Can the certificate be used for the client side of an SSL
 * connection?</td>
 * </tr>
 * <tr valign="top">
 * <td>X509_PURPOSE_SSL_SERVER</td>
 * <td>Can the certificate be used for the server side of an SSL
 * connection?</td>
 * </tr>
 * <tr valign="top">
 * <td>X509_PURPOSE_NS_SSL_SERVER</td>
 * <td>Can the cert be used for Netscape SSL server?</td>
 * </tr>
 * <tr valign="top">
 * <td>X509_PURPOSE_SMIME_SIGN</td>
 * <td>Can the cert be used to sign S/MIME email?</td>
 * </tr>
 * <tr valign="top">
 * <td>X509_PURPOSE_SMIME_ENCRYPT</td>
 * <td>Can the cert be used to encrypt S/MIME email?</td>
 * </tr>
 * <tr valign="top">
 * <td>X509_PURPOSE_CRL_SIGN</td>
 * <td>Can the cert be used to sign a certificate revocation list
 * (CRL)?</td>
 * </tr>
 * <tr valign="top">
 * <td>X509_PURPOSE_ANY</td>
 * <td>Can the cert be used for Any/All purposes?</td>
 * </tr>
 * </table>
 * </table>
 * These options are not bitfields - you may specify one only!
 * @param array $ca_info [optional] ca_info should be an array of trusted CA files/dirs
 * as described in Certificate
 * Verification.
 * @param mixed $untrusted_certificates_file [optional] If specified, this should be the name of a PEM encoded file holding
 * certificates that can be used to help verify the certificate, although
 * no trust is placed in the certificates that come from that file.
 * @return mixed true if the certificate can be used for the intended purpose,
 * false if it cannot, or -1 on error.
 */
function openssl_x509_checkpurpose ($certificate, int $purpose, array $ca_info = null, $untrusted_certificates_file = null): int|bool {}

/**
 * Parse an X.509 certificate and return an object for
 * it
 * @link http://www.php.net/manual/en/function.openssl-x509-read.php
 * @param mixed $certificate X509 certificate. See Key/Certificate parameters for a list of valid values.
 * @return mixed an OpenSSLCertificate on success or false on failure.
 */
function openssl_x509_read ($certificate): OpenSSLCertificate|false {}

/**
 * Free certificate resource
 * @link http://www.php.net/manual/en/function.openssl-x509-free.php
 * @param OpenSSLCertificate $certificate 
 * @return void 
 * @deprecated 
 */
function openssl_x509_free (OpenSSLCertificate $certificate): void {}

/**
 * Exports a PKCS#12 Compatible Certificate Store File
 * @link http://www.php.net/manual/en/function.openssl-pkcs12-export-to-file.php
 * @param mixed $certificate 
 * @param string $output_filename Path to the output file.
 * @param mixed $private_key Private key component of PKCS#12 file.
 * See Public/Private Key parameters for a list of valid values.
 * @param string $passphrase Encryption password for unlocking the PKCS#12 file.
 * @param array $options [optional] Optional array, other keys will be ignored.
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>"extracerts"</td>
 * <td>array of extra certificates or a single certificate to be included in the PKCS#12 file.</td>
 * </tr>
 * <tr valign="top">
 * <td>"friendlyname"</td>
 * <td>string to be used for the supplied certificate and key</td>
 * </tr>
 * </table>
 * @return bool true on success or false on failure
 */
function openssl_pkcs12_export_to_file ($certificate, string $output_filename, $private_key, string $passphrase, array $options = null): bool {}

/**
 * Exports a PKCS#12 Compatible Certificate Store File to variable
 * @link http://www.php.net/manual/en/function.openssl-pkcs12-export.php
 * @param mixed $certificate 
 * @param string $output On success, this will hold the PKCS#12.
 * @param mixed $private_key Private key component of PKCS#12 file.
 * See Public/Private Key parameters for a list of valid values.
 * @param string $passphrase Encryption password for unlocking the PKCS#12 file.
 * @param array $options [optional] Optional array, other keys will be ignored.
 * <table>
 * <tr valign="top">
 * <td>Key</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>"extracerts"</td>
 * <td>array of extra certificates or a single certificate to be included in the PKCS#12 file.</td>
 * </tr>
 * <tr valign="top">
 * <td>"friendlyname"</td>
 * <td>string to be used for the supplied certificate and key</td>
 * </tr>
 * </table>
 * @return bool true on success or false on failure
 */
function openssl_pkcs12_export ($certificate, string &$output, $private_key, string $passphrase, array $options = null): bool {}

/**
 * Parse a PKCS#12 Certificate Store into an array
 * @link http://www.php.net/manual/en/function.openssl-pkcs12-read.php
 * @param string $pkcs12 The certificate store contents, not its file name.
 * @param array $certificates On success, this will hold the Certificate Store Data.
 * @param string $passphrase Encryption password for unlocking the PKCS#12 file.
 * @return bool true on success or false on failure
 */
function openssl_pkcs12_read (string $pkcs12, array &$certificates, string $passphrase): bool {}

/**
 * Exports a CSR to a file
 * @link http://www.php.net/manual/en/function.openssl-csr-export-to-file.php
 * @param mixed $csr 
 * @param string $output_filename Path to the output file.
 * @param bool $no_text [optional] note.openssl.param-notext
 * @return bool true on success or false on failure
 */
function openssl_csr_export_to_file ($csr, string $output_filename, bool $no_text = null): bool {}

/**
 * Exports a CSR as a string
 * @link http://www.php.net/manual/en/function.openssl-csr-export.php
 * @param mixed $csr 
 * @param string $output on success, this string will contain the PEM encoded CSR
 * @param bool $no_text [optional] note.openssl.param-notext
 * @return bool true on success or false on failure
 */
function openssl_csr_export ($csr, string &$output, bool $no_text = null): bool {}

/**
 * Sign a CSR with another certificate (or itself) and generate a certificate
 * @link http://www.php.net/manual/en/function.openssl-csr-sign.php
 * @param mixed $csr A CSR previously generated by openssl_csr_new.
 * It can also be the path to a PEM encoded CSR when specified as
 * file://path/to/csr or an exported string generated
 * by openssl_csr_export.
 * @param mixed $ca_certificate The generated certificate will be signed by ca_certificate.
 * If ca_certificate is null, the generated certificate
 * will be a self-signed certificate.
 * @param mixed $private_key private_key is the private key that corresponds to
 * ca_certificate.
 * @param int $days days specifies the length of time for which the
 * generated certificate will be valid, in days.
 * @param mixed $options [optional] You can finetune the CSR signing by options.
 * See openssl_csr_new for more information about
 * options.
 * @param int $serial [optional] An optional the serial number of issued certificate. If not specified
 * it will default to 0.
 * @return mixed an OpenSSLCertificate on success, false on failure.
 */
function openssl_csr_sign ($csr, $ca_certificate, $private_key, int $days, $options = null, int $serial = null): OpenSSLCertificate|false {}

/**
 * Generates a CSR
 * @link http://www.php.net/manual/en/function.openssl-csr-new.php
 * @param array $distinguished_names The Distinguished Name or subject fields to be used in the certificate.
 * @param OpenSSLAsymmetricKey $private_key private_key should be set to a private key that was
 * previously generated by openssl_pkey_new (or
 * otherwise obtained from the other openssl_pkey family of functions).
 * The corresponding public portion of the key will be used to sign the
 * CSR.
 * @param mixed $options [optional] By default, the information in your system openssl.conf
 * is used to initialize the request; you can specify a configuration file
 * section by setting the config_section_section key of
 * options. You can also specify an alternative
 * openssl configuration file by setting the value of the
 * config key to the path of the file you want to use.
 * The following keys, if present in options
 * behave as their equivalents in the openssl.conf, as
 * listed in the table below.
 * <table>
 * Configuration overrides
 * <table>
 * <tr valign="top">
 * <td>options key</td>
 * <td>type</td>
 * <td>openssl.conf equivalent</td>
 * <td>description</td>
 * </tr>
 * <tr valign="top">
 * <td>digest_alg</td>
 * <td>string</td>
 * <td>default_md</td>
 * <td>Digest method or signature hash, usually one of openssl_get_md_methods</td>
 * </tr>
 * <tr valign="top">
 * <td>x509_extensions</td>
 * <td>string</td>
 * <td>x509_extensions</td>
 * <td>Selects which extensions should be used when creating an x509
 * certificate</td>
 * </tr>
 * <tr valign="top">
 * <td>req_extensions</td>
 * <td>string</td>
 * <td>req_extensions</td>
 * <td>Selects which extensions should be used when creating a CSR</td>
 * </tr>
 * <tr valign="top">
 * <td>private_key_bits</td>
 * <td>int</td>
 * <td>default_bits</td>
 * <td>Specifies how many bits should be used to generate a private
 * key</td>
 * </tr>
 * <tr valign="top">
 * <td>private_key_type</td>
 * <td>int</td>
 * <td>none</td>
 * <td>Specifies the type of private key to create. This can be one
 * of OPENSSL_KEYTYPE_DSA,
 * OPENSSL_KEYTYPE_DH,
 * OPENSSL_KEYTYPE_RSA or
 * OPENSSL_KEYTYPE_EC.
 * The default value is OPENSSL_KEYTYPE_RSA.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>encrypt_key</td>
 * <td>bool</td>
 * <td>encrypt_key</td>
 * <td>Should an exported key (with passphrase) be encrypted?</td>
 * </tr>
 * <tr valign="top">
 * <td>encrypt_key_cipher</td>
 * <td>int</td>
 * <td>none</td>
 * <td>
 * One of cipher constants.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>curve_name</td>
 * <td>string</td>
 * <td>none</td>
 * <td>
 * One of openssl_get_curve_names.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>config</td>
 * <td>string</td>
 * <td>N/A</td>
 * <td>
 * Path to your own alternative openssl.conf file.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param mixed $extra_attributes [optional] extra_attributes is used to specify additional
 * configuration options for the CSR. Both distinguished_names and
 * extra_attributes are associative arrays whose keys are
 * converted to OIDs and applied to the relevant part of the request.
 * @return mixed the CSR or false on failure.
 */
function openssl_csr_new (array $distinguished_names, OpenSSLAsymmetricKey &$private_key, $options = null, $extra_attributes = null): OpenSSLCertificateSigningRequest|false {}

/**
 * Returns the subject of a CSR
 * @link http://www.php.net/manual/en/function.openssl-csr-get-subject.php
 * @param mixed $csr 
 * @param bool $short_names [optional] shortnames controls how the data is indexed in the
 * array - if shortnames is true (the default) then
 * fields will be indexed with the short name form, otherwise, the long name
 * form will be used - e.g.: CN is the shortname form of commonName.
 * @return mixed an associative array with subject description, or false on failure.
 */
function openssl_csr_get_subject ($csr, bool $short_names = null): array|false {}

/**
 * Returns the public key of a CSR
 * @link http://www.php.net/manual/en/function.openssl-csr-get-public-key.php
 * @param mixed $csr 
 * @param bool $short_names [optional] This parameter is ignored
 * @return mixed an OpenSSLAsymmetricKey on success, or false on error.
 */
function openssl_csr_get_public_key ($csr, bool $short_names = null): OpenSSLAsymmetricKey|false {}

/**
 * Generates a new private key
 * @link http://www.php.net/manual/en/function.openssl-pkey-new.php
 * @param mixed $options [optional] You can finetune the key generation (such as specifying the number of
 * bits) using options. See
 * openssl_csr_new for more information about
 * options.
 * @return mixed an OpenSSLAsymmetricKey instance for the pkey on success, or false on
 * error.
 */
function openssl_pkey_new ($options = null): OpenSSLAsymmetricKey|false {}

/**
 * Gets an exportable representation of a key into a file
 * @link http://www.php.net/manual/en/function.openssl-pkey-export-to-file.php
 * @param mixed $key 
 * @param string $output_filename Path to the output file.
 * @param mixed $passphrase [optional] The key can be optionally protected by a
 * passphrase.
 * @param mixed $options [optional] options can be used to fine-tune the export
 * process by specifying and/or overriding options for the openssl
 * configuration file. See openssl_csr_new for more
 * information about options.
 * @return bool true on success or false on failure
 */
function openssl_pkey_export_to_file ($key, string $output_filename, $passphrase = null, $options = null): bool {}

/**
 * Gets an exportable representation of a key into a string
 * @link http://www.php.net/manual/en/function.openssl-pkey-export.php
 * @param mixed $key 
 * @param string $output 
 * @param mixed $passphrase [optional] The key is optionally protected by passphrase.
 * @param mixed $options [optional] options can be used to fine-tune the export
 * process by specifying and/or overriding options for the openssl
 * configuration file. See openssl_csr_new for more
 * information about options.
 * @return bool true on success or false on failure
 */
function openssl_pkey_export ($key, string &$output, $passphrase = null, $options = null): bool {}

/**
 * Extract public key from certificate and prepare it for use
 * @link http://www.php.net/manual/en/function.openssl-pkey-get-public.php
 * @param mixed $public_key <p>
 * public_key can be one of the following:
 * <p>
 * <br>an OpenSSLAsymmetricKey instance
 * <br>a string having the format
 * file://path/to/file.pem. The named file must
 * contain a PEM encoded certificate/public key (it may contain both).
 * <br>A PEM formatted public key.
 * </p>
 * </p>
 * @return mixed an OpenSSLAsymmetricKey instance on success, or false on error.
 */
function openssl_pkey_get_public ($public_key): OpenSSLAsymmetricKey|false {}

/**
 * Alias: openssl_pkey_get_public
 * @link http://www.php.net/manual/en/function.openssl-get-publickey.php
 * @param mixed $public_key
 */
function openssl_get_publickey ($public_key = null): OpenSSLAsymmetricKey|false {}

/**
 * Frees a private key
 * @link http://www.php.net/manual/en/function.openssl-pkey-free.php
 * @param OpenSSLAsymmetricKey $key Resource holding the key.
 * @return void 
 * @deprecated 
 */
function openssl_pkey_free (OpenSSLAsymmetricKey $key): void {}

/**
 * Free key resource
 * @link http://www.php.net/manual/en/function.openssl-free-key.php
 * @param OpenSSLAsymmetricKey $key 
 * @return void 
 * @deprecated 
 */
function openssl_free_key (OpenSSLAsymmetricKey $key): void {}

/**
 * Get a private key
 * @link http://www.php.net/manual/en/function.openssl-pkey-get-private.php
 * @param mixed $private_key <p>
 * private_key can be one of the following:
 * <p>
 * <br>a string having the format
 * file://path/to/file.pem. The named file must
 * contain a PEM encoded certificate/private key (it may contain both).
 * <br>A PEM formatted private key.
 * </p>
 * </p>
 * @param mixed $passphrase [optional] The optional parameter passphrase must be used
 * if the specified key is encrypted (protected by a passphrase).
 * @return mixed an OpenSSLAsymmetricKey instance on success, or false on error.
 */
function openssl_pkey_get_private ($private_key, $passphrase = null): OpenSSLAsymmetricKey|false {}

/**
 * Alias: openssl_pkey_get_private
 * @link http://www.php.net/manual/en/function.openssl-get-privatekey.php
 * @param mixed $private_key
 * @param ?string|null $passphrase [optional]
 */
function openssl_get_privatekey ($private_key = null?string|null , $passphrase = null): OpenSSLAsymmetricKey|false {}

/**
 * Returns an array with the key details
 * @link http://www.php.net/manual/en/function.openssl-pkey-get-details.php
 * @param OpenSSLAsymmetricKey $key Resource holding the key.
 * @return mixed an array with the key details on success or false on failure.
 * Returned array has indexes bits (number of bits),
 * key (string representation of the public key) and
 * type (type of the key which is one of
 * OPENSSL_KEYTYPE_RSA,
 * OPENSSL_KEYTYPE_DSA,
 * OPENSSL_KEYTYPE_DH,
 * OPENSSL_KEYTYPE_EC or -1 meaning unknown).
 * <p>
 * Depending on the key type used, additional details may be returned. Note that 
 * some elements may not always be available.
 * </p>
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
 * @return mixed raw binary string or false on failure.
 */
function openssl_pbkdf2 (string $password, string $salt, int $key_length, int $iterations, string $digest_algo = null): string|false {}

/**
 * Verifies the signature of an S/MIME signed message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-verify.php
 * @param string $input_filename Path to the message.
 * @param int $flags flags can be used to affect how the signature is
 * verified - see PKCS7 constants
 * for more information.
 * @param mixed $signers_certificates_filename [optional] If the signers_certificates_filename is specified, it should be a
 * string holding the name of a file into which the certificates of the
 * persons that signed the messages will be stored in PEM format.
 * @param array $ca_info [optional] If the ca_info is specified, it should hold
 * information about the trusted CA certificates to use in the verification
 * process - see certificate
 * verification for more information about this parameter.
 * @param mixed $untrusted_certificates_filename [optional] If the untrusted_certificates_filename is specified, it is the filename
 * of a file containing a bunch of certificates to use as untrusted CAs.
 * @param mixed $content [optional] You can specify a filename with content that will
 * be filled with the verified data, but with the signature information
 * stripped.
 * @param mixed $output_filename [optional] 
 * @return mixed true if the signature is verified, false if it is not correct
 * (the message has been tampered with, or the signing certificate is invalid),
 * or -1 on error.
 */
function openssl_pkcs7_verify (string $input_filename, int $flags, $signers_certificates_filename = null, array $ca_info = null, $untrusted_certificates_filename = null, $content = null, $output_filename = null): int|bool {}

/**
 * Encrypt an S/MIME message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-encrypt.php
 * @param string $input_filename 
 * @param string $output_filename 
 * @param mixed $certificate Either a lone X.509 certificate, or an array of X.509 certificates.
 * @param mixed $headers <p>
 * headers is an array of headers that
 * will be prepended to the data after it has been encrypted.
 * </p>
 * <p>
 * headers can be either an associative array
 * keyed by header name, or an indexed array, where each element contains
 * a single header line.
 * </p>
 * @param int $flags [optional] flags can be used to specify options that affect
 * the encoding process - see PKCS7
 * constants.
 * @param int $cipher_algo [optional] One of cipher constants.
 * @return bool true on success or false on failure
 */
function openssl_pkcs7_encrypt (string $input_filename, string $output_filename, $certificate, $headers, int $flags = null, int $cipher_algo = null): bool {}

/**
 * Sign an S/MIME message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-sign.php
 * @param string $input_filename The input file you are intending to digitally sign.
 * @param string $output_filename The file which the digital signature will be written to.
 * @param mixed $certificate The X.509 certificate used to digitally sign input_filename.
 * See Key/Certificate parameters for a list of valid values.
 * @param mixed $private_key private_key is the private key corresponding to certificate.
 * See Public/Private Key parameters for a list of valid values.
 * @param mixed $headers headers is an array of headers that
 * will be prepended to the data after it has been signed (see
 * openssl_pkcs7_encrypt for more information about
 * the format of this parameter).
 * @param int $flags [optional] flags can be used to alter the output - see PKCS7 constants.
 * @param mixed $untrusted_certificates_filename [optional] untrusted_certificates_filename specifies the name of a file containing
 * a bunch of extra certificates to include in the signature which can for
 * example be used to help the recipient to verify the certificate that you used.
 * @return bool true on success or false on failure
 */
function openssl_pkcs7_sign (string $input_filename, string $output_filename, $certificate, $private_key, $headers, int $flags = null, $untrusted_certificates_filename = null): bool {}

/**
 * Decrypts an S/MIME encrypted message
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-decrypt.php
 * @param string $input_filename 
 * @param string $output_filename The decrypted message is written to the file specified by
 * output_filename.
 * @param mixed $certificate 
 * @param mixed $private_key [optional] 
 * @return bool true on success or false on failure
 */
function openssl_pkcs7_decrypt (string $input_filename, string $output_filename, $certificate, $private_key = null): bool {}

/**
 * Export the PKCS7 file to an array of PEM certificates
 * @link http://www.php.net/manual/en/function.openssl-pkcs7-read.php
 * @param string $data The string of data you wish to parse (p7b format).
 * @param array $certificates The array of PEM certificates from the p7b input data.
 * @return bool true on success or false on failure
 */
function openssl_pkcs7_read (string $data, array &$certificates): bool {}

/**
 * Verify a CMS signature
 * @link http://www.php.net/manual/en/function.openssl-cms-verify.php
 * @param string $input_filename The input file.
 * @param int $flags [optional] Flags to pass to cms_verify.
 * @param mixed $certificates [optional] A file with the signer certificate and optionally intermediate certificates.
 * @param array $ca_info [optional] An array containing self-signed certificate authority certificates.
 * @param mixed $untrusted_certificates_filename [optional] A file containing additional intermediate certificates.
 * @param mixed $content [optional] A file pointing to the content when signatures are detached.
 * @param mixed $pk7 [optional] 
 * @param mixed $sigfile [optional] A file to save the signature to.
 * @param int $encoding [optional] The encoding of the input file. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @return bool true on success or false on failure
 */
function openssl_cms_verify (string $input_filename, int $flags = null, $certificates = null, array $ca_info = null, $untrusted_certificates_filename = null, $content = null, $pk7 = null, $sigfile = null, int $encoding = null): bool {}

/**
 * Encrypt a CMS message
 * @link http://www.php.net/manual/en/function.openssl-cms-encrypt.php
 * @param string $input_filename The file to be encrypted.
 * @param string $output_filename The output file.
 * @param mixed $certificate Recipients to encrypt to.
 * @param mixed $headers Headers to include when S/MIME is used.
 * @param int $flags [optional] Flags to be passed to CMS_sign.
 * @param int $encoding [optional] An encoding to output. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @param int $cipher_algo [optional] A cypher to use.
 * @return bool true on success or false on failure
 */
function openssl_cms_encrypt (string $input_filename, string $output_filename, $certificate, $headers, int $flags = null, int $encoding = null, int $cipher_algo = null): bool {}

/**
 * Sign a file
 * @link http://www.php.net/manual/en/function.openssl-cms-sign.php
 * @param string $input_filename The name of the file to be signed.
 * @param string $output_filename The name of the file to deposit the results.
 * @param mixed $certificate The signing certificate.
 * See Key/Certificate parameters for a list of valid values.
 * @param mixed $private_key The key associated with certificate.
 * See Key/Certificate parameters for a list of valid values.
 * @param mixed $headers An array of headers to be included in S/MIME output.
 * @param int $flags [optional] Flags to be passed to cms_sign.
 * @param int $encoding [optional] The encoding of the output file. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @param mixed $untrusted_certificates_filename [optional] Intermediate certificates to be included in the signature.
 * @return bool true on success or false on failure
 */
function openssl_cms_sign (string $input_filename, string $output_filename, $certificate, $private_key, $headers, int $flags = null, int $encoding = null, $untrusted_certificates_filename = null): bool {}

/**
 * Decrypt a CMS message
 * @link http://www.php.net/manual/en/function.openssl-cms-decrypt.php
 * @param string $input_filename The name of a file containing encrypted content.
 * @param string $output_filename The name of the file to deposit the decrypted content.
 * @param mixed $certificate The name of the file containing a certificate of the recipient.
 * @param mixed $private_key [optional] The name of the file containing a PKCS#8 key.
 * @param int $encoding [optional] The encoding of the input file. One of OPENSSL_ENCODING_SMIME,
 * OPENSSL_ENCODING_DER or OPENSSL_ENCODING_PEM.
 * @return bool true on success or false on failure
 */
function openssl_cms_decrypt (string $input_filename, string $output_filename, $certificate, $private_key = null, int $encoding = null): bool {}

/**
 * Export the CMS file to an array of PEM certificates
 * @link http://www.php.net/manual/en/function.openssl-cms-read.php
 * @param string $input_filename 
 * @param array $certificates 
 * @return bool true on success or false on failure
 */
function openssl_cms_read (string $input_filename, array &$certificates): bool {}

/**
 * Encrypts data with private key
 * @link http://www.php.net/manual/en/function.openssl-private-encrypt.php
 * @param string $data 
 * @param string $encrypted_data 
 * @param mixed $private_key 
 * @param int $padding [optional] padding can be one of
 * OPENSSL_PKCS1_PADDING,
 * OPENSSL_NO_PADDING.
 * @return bool true on success or false on failure
 */
function openssl_private_encrypt (string $data, string &$encrypted_data, $private_key, int $padding = null): bool {}

/**
 * Decrypts data with private key
 * @link http://www.php.net/manual/en/function.openssl-private-decrypt.php
 * @param string $data 
 * @param string $decrypted_data 
 * @param mixed $private_key private_key must be the private key corresponding that
 * was used to encrypt the data.
 * @param int $padding [optional] padding can be one of
 * OPENSSL_PKCS1_PADDING,
 * OPENSSL_SSLV23_PADDING,
 * OPENSSL_PKCS1_OAEP_PADDING,
 * OPENSSL_NO_PADDING.
 * @return bool true on success or false on failure
 */
function openssl_private_decrypt (string $data, string &$decrypted_data, $private_key, int $padding = null): bool {}

/**
 * Encrypts data with public key
 * @link http://www.php.net/manual/en/function.openssl-public-encrypt.php
 * @param string $data 
 * @param string $encrypted_data This will hold the result of the encryption.
 * @param mixed $public_key The public key.
 * @param int $padding [optional] padding can be one of
 * OPENSSL_PKCS1_PADDING,
 * OPENSSL_SSLV23_PADDING,
 * OPENSSL_PKCS1_OAEP_PADDING,
 * OPENSSL_NO_PADDING.
 * @return bool true on success or false on failure
 */
function openssl_public_encrypt (string $data, string &$encrypted_data, $public_key, int $padding = null): bool {}

/**
 * Decrypts data with public key
 * @link http://www.php.net/manual/en/function.openssl-public-decrypt.php
 * @param string $data 
 * @param string $decrypted_data 
 * @param mixed $public_key public_key must be the public key corresponding that
 * was used to encrypt the data.
 * @param int $padding [optional] padding can be one of
 * OPENSSL_PKCS1_PADDING,
 * OPENSSL_NO_PADDING.
 * @return bool true on success or false on failure
 */
function openssl_public_decrypt (string $data, string &$decrypted_data, $public_key, int $padding = null): bool {}

/**
 * Return openSSL error message
 * @link http://www.php.net/manual/en/function.openssl-error-string.php
 * @return mixed an error message string, or false if there are no more error
 * messages to return.
 */
function openssl_error_string (): string|false {}

/**
 * Generate signature
 * @link http://www.php.net/manual/en/function.openssl-sign.php
 * @param string $data The string of data you wish to sign
 * @param string $signature If the call was successful the signature is returned in
 * signature.
 * @param mixed $private_key <p>
 * OpenSSLAsymmetricKey - a key, returned by openssl_get_privatekey
 * </p>
 * <p>
 * string - a PEM formatted key
 * </p>
 * @param mixed $algorithm [optional] <p>
 * int - one of these Signature Algorithms.
 * </p>
 * <p>
 * string - a valid string returned by openssl_get_md_methods example, "sha256WithRSAEncryption" or "sha384".
 * </p>
 * @return bool true on success or false on failure
 */
function openssl_sign (string $data, string &$signature, $private_key, $algorithm = null): bool {}

/**
 * Verify signature
 * @link http://www.php.net/manual/en/function.openssl-verify.php
 * @param string $data The string of data used to generate the signature previously
 * @param string $signature A raw binary string, generated by openssl_sign or similar means
 * @param mixed $public_key <p>
 * OpenSSLAsymmetricKey - a key, returned by openssl_get_publickey
 * </p>
 * <p>
 * string - a PEM formatted key, example, "-----BEGIN PUBLIC KEY-----
 * MIIBCgK..."
 * </p>
 * @param mixed $algorithm [optional] <p>
 * int - one of these Signature Algorithms.
 * </p>
 * <p>
 * string - a valid string returned by openssl_get_md_methods example, "sha1WithRSAEncryption" or "sha512".
 * </p>
 * @return mixed 1 if the signature is correct, 0 if it is incorrect, and
 * -1 or false on error.
 */
function openssl_verify (string $data, string $signature, $public_key, $algorithm = null): int|false {}

/**
 * Seal (encrypt) data
 * @link http://www.php.net/manual/en/function.openssl-seal.php
 * @param string $data The data to seal.
 * @param string $sealed_data The sealed data.
 * @param array $encrypted_keys Array of encrypted keys.
 * @param array $public_key Array of OpenSSLAsymmetricKey instances containing public keys.
 * @param string $cipher_algo The cipher method.
 * The default value ('RC4') is considered insecure.
 * It is strongly recommended to explicitly specify a secure cipher method.
 * @param string $iv [optional] The initialization vector.
 * @return mixed the length of the sealed data on success, or false on error.
 * If successful the sealed data is returned in
 * sealed_data, and the envelope keys in
 * encrypted_keys.
 */
function openssl_seal (string $data, string &$sealed_data, array &$encrypted_keys, array $public_key, string $cipher_algo, string &$iv = null): int|false {}

/**
 * Open sealed data
 * @link http://www.php.net/manual/en/function.openssl-open.php
 * @param string $data 
 * @param string $output If the call is successful the opened data is returned in this
 * parameter.
 * @param string $encrypted_key 
 * @param mixed $private_key 
 * @param string $cipher_algo The cipher method.
 * The default value ('RC4') is considered insecure.
 * It is strongly recommended to explicitly specify a secure cipher method.
 * @param mixed $iv [optional] The initialization vector.
 * @return bool true on success or false on failure
 */
function openssl_open (string $data, string &$output, string $encrypted_key, $private_key, string $cipher_algo, $iv = null): bool {}

/**
 * Gets available digest methods
 * @link http://www.php.net/manual/en/function.openssl-get-md-methods.php
 * @param bool $aliases [optional] Set to true if digest aliases should be included within the
 * returned array.
 * @return array An array of available digest methods.
 */
function openssl_get_md_methods (bool $aliases = null): array {}

/**
 * Gets available cipher methods
 * @link http://www.php.net/manual/en/function.openssl-get-cipher-methods.php
 * @param bool $aliases [optional] Set to true if cipher aliases should be included within the
 * returned array.
 * @return array An array of available cipher methods.
 * Note that prior to OpenSSL 1.1.1, the cipher methods have been returned in
 * upper case and lower case spelling; as of OpenSSL 1.1.1 only the lower case
 * variants are returned.
 */
function openssl_get_cipher_methods (bool $aliases = null): array {}

/**
 * Gets list of available curve names for ECC
 * @link http://www.php.net/manual/en/function.openssl-get-curve-names.php
 * @return mixed An array of available curve names, or false on failure.
 */
function openssl_get_curve_names (): array|false {}

/**
 * Computes a digest
 * @link http://www.php.net/manual/en/function.openssl-digest.php
 * @param string $data The data.
 * @param string $digest_algo The digest method to use, e.g. "sha256", see openssl_get_md_methods for a list of available digest methods.
 * @param bool $binary [optional] Setting to true will return as raw output data, otherwise the return
 * value is binhex encoded.
 * @return mixed the digested hash value on success or false on failure.
 */
function openssl_digest (string $data, string $digest_algo, bool $binary = null): string|false {}

/**
 * Encrypts data
 * @link http://www.php.net/manual/en/function.openssl-encrypt.php
 * @param string $data The plaintext message data to be encrypted.
 * @param string $cipher_algo The cipher method. For a list of available cipher methods, use openssl_get_cipher_methods.
 * @param string $passphrase The passphrase. If the passphrase is shorter than expected, it is silently padded with
 * NUL characters; if the passphrase is longer than expected, it is
 * silently truncated.
 * @param int $options [optional] options is a bitwise disjunction of the flags
 * OPENSSL_RAW_DATA and
 * OPENSSL_ZERO_PADDING.
 * @param string $iv [optional] A non-NULL Initialization Vector.
 * @param string $tag [optional] The authentication tag passed by reference when using AEAD cipher mode (GCM or CCM).
 * @param string $aad [optional] Additional authenticated data.
 * @param int $tag_length [optional] The length of the authentication tag. Its value can be between 4 and 16 for GCM mode.
 * @return mixed the encrypted string on success or false on failure.
 */
function openssl_encrypt (string $data, string $cipher_algo, string $passphrase, int $options = null, string $iv = null, string &$tag = null, string $aad = null, int $tag_length = null): string|false {}

/**
 * Decrypts data
 * @link http://www.php.net/manual/en/function.openssl-decrypt.php
 * @param string $data The encrypted message to be decrypted.
 * @param string $cipher_algo The cipher method. For a list of available cipher methods, use 
 * openssl_get_cipher_methods.
 * @param string $passphrase The key.
 * @param int $options [optional] options can be one of
 * OPENSSL_RAW_DATA,
 * OPENSSL_ZERO_PADDING.
 * @param string $iv [optional] A non-NULL Initialization Vector.
 * @param mixed $tag [optional] <p>
 * The authentication tag in AEAD cipher mode. If it is incorrect, the authentication fails and the function returns false.
 * </p>
 * The length of the tag is not checked by the function.
 * It is the caller's responsibility to ensure that the length of the tag
 * matches the length of the tag retrieved when openssl_encrypt
 * has been called. Otherwise the decryption may succeed if the given tag only
 * matches the start of the proper tag.
 * @param string $aad [optional] Additional authenticated data.
 * @return mixed The decrypted string on success or false on failure.
 */
function openssl_decrypt (string $data, string $cipher_algo, string $passphrase, int $options = null, string $iv = null, $tag = null, string $aad = null): string|false {}

/**
 * Gets the cipher iv length
 * @link http://www.php.net/manual/en/function.openssl-cipher-iv-length.php
 * @param string $cipher_algo The cipher method, see openssl_get_cipher_methods for a list of potential values.
 * @return mixed the cipher length on success, or false on failure.
 */
function openssl_cipher_iv_length (string $cipher_algo): int|false {}

/**
 * Gets the cipher key length
 * @link http://www.php.net/manual/en/function.openssl-cipher-key-length.php
 * @param string $cipher_algo The cipher method, see openssl_get_cipher_methods for a list of potential values.
 * @return mixed the cipher length on success, or false on failure.
 */
function openssl_cipher_key_length (string $cipher_algo): int|false {}

/**
 * Computes shared secret for public value of remote DH public key and local DH key
 * @link http://www.php.net/manual/en/function.openssl-dh-compute-key.php
 * @param string $public_key DH Public key of the remote party.
 * @param OpenSSLAsymmetricKey $private_key A local DH private key, corresponding to the public key to be shared with the remote party.
 * @return mixed shared secret on success or false on failure.
 */
function openssl_dh_compute_key (string $public_key, OpenSSLAsymmetricKey $private_key): string|false {}

/**
 * Computes shared secret for public value of remote and local DH or ECDH key
 * @link http://www.php.net/manual/en/function.openssl-pkey-derive.php
 * @param mixed $public_key public_key is the public key for the derivation.
 * See Public/Private Key parameters for a list of valid values.
 * @param mixed $private_key private_key is the private key for the derivation.
 * See Public/Private Key parameters for a list of valid values.
 * @param int $key_length [optional] If not zero, will set the desired length of the derived secret.
 * @return mixed The derived secret on success or false on failure.
 */
function openssl_pkey_derive ($public_key, $private_key, int $key_length = null): string|false {}

/**
 * Generate a pseudo-random string of bytes
 * @link http://www.php.net/manual/en/function.openssl-random-pseudo-bytes.php
 * @param int $length The length of the desired string of bytes. Must be a positive integer less than or equal to 2147483647. PHP will
 * try to cast this parameter to a non-null integer to use it.
 * @param bool $strong_result [optional] If passed into the function, this will hold a bool value that determines
 * if the algorithm used was "cryptographically strong", e.g., safe for usage with GPG, 
 * passwords, etc. true if it did, otherwise false
 * @return string the generated string of bytes.
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
 * @return mixed a signed public key and challenge string or false on failure.
 */
function openssl_spki_new (OpenSSLAsymmetricKey $private_key, string $challenge, int $digest_algo = null): string|false {}

/**
 * Verifies a signed public key and challenge
 * @link http://www.php.net/manual/en/function.openssl-spki-verify.php
 * @param string $spki Expects a valid signed public key and challenge
 * @return bool true on success or false on failure
 */
function openssl_spki_verify (string $spki): bool {}

/**
 * Exports a valid PEM formatted public key signed public key and challenge
 * @link http://www.php.net/manual/en/function.openssl-spki-export.php
 * @param string $spki Expects a valid signed public key and challenge
 * @return mixed the associated PEM formatted public key or false on failure.
 */
function openssl_spki_export (string $spki): string|false {}

/**
 * Exports the challenge associated with a signed public key and challenge
 * @link http://www.php.net/manual/en/function.openssl-spki-export-challenge.php
 * @param string $spki Expects a valid signed public key and challenge
 * @return mixed the associated challenge string or false on failure.
 */
function openssl_spki_export_challenge (string $spki): string|false {}

/**
 * Retrieve the available certificate locations
 * @link http://www.php.net/manual/en/function.openssl-get-cert-locations.php
 * @return array an array with the available certificate locations.
 */
function openssl_get_cert_locations (): array {}


/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_VERSION_TEXT', "OpenSSL 1.1.1t  7 Feb 2023");

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_VERSION_NUMBER', 269488463);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('X509_PURPOSE_SSL_CLIENT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('X509_PURPOSE_SSL_SERVER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('X509_PURPOSE_NS_SSL_SERVER', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('X509_PURPOSE_SMIME_SIGN', 4);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('X509_PURPOSE_SMIME_ENCRYPT', 5);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('X509_PURPOSE_CRL_SIGN', 6);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('X509_PURPOSE_ANY', 7);

/**
 * Used as default algorithm by openssl_sign and
 * openssl_verify.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_SHA1', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_MD5', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_MD4', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_SHA224', 6);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_SHA256', 7);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_SHA384', 8);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_SHA512', 9);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ALGO_RMD160', 10);

/**
 * When signing a message, use cleartext signing with the MIME
 * type "multipart/signed". This is the default
 * if you do not specify any flags to
 * openssl_pkcs7_sign.
 * If you turn this option off, the message will be signed using
 * opaque signing, which is more resistant to translation by mail relays
 * but cannot be read by mail agents that do not support S/MIME.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_DETACHED', 64);

/**
 * Adds text/plain content type headers to encrypted/signed
 * message. If decrypting or verifying, it strips those headers from
 * the output - if the decrypted or verified message is not of MIME type
 * text/plain then an error will occur.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_TEXT', 1);

/**
 * When verifying a message, certificates (if
 * any) included in the message are normally searched for the
 * signing certificate. With this option only the
 * certificates specified in the extracerts
 * parameter of openssl_pkcs7_verify are
 * used. The supplied certificates can still be used as
 * untrusted CAs however.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_NOINTERN', 16);

/**
 * Do not verify the signers certificate of a signed
 * message.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_NOVERIFY', 32);

/**
 * Do not chain verification of signers certificates: that is
 * don't use the certificates in the signed message as untrusted CAs.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_NOCHAIN', 8);

/**
 * When signing a message the signer's certificate is normally
 * included - with this option it is excluded. This will reduce the
 * size of the signed message but the verifier must have a copy of the
 * signers certificate available locally (passed using the
 * extracerts to
 * openssl_pkcs7_verify for example).
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_NOCERTS', 2);

/**
 * Normally when a message is signed, a set of attributes are
 * included which include the signing time and the supported symmetric
 * algorithms. With this option they are not included.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_NOATTR', 256);

/**
 * Normally the input message is converted to "canonical" format
 * which is effectively using CR and LF
 * as end of line: as required by the S/MIME specification. When this
 * option is present, no translation occurs. This is useful when
 * handling binary data which may not be in MIME format.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_BINARY', 128);

/**
 * Don't try and verify the signatures on a message
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('PKCS7_NOSIGS', 4);

/**
 * When signing a message, use cleartext signing with the MIME
 * type "multipart/signed". This is the default
 * if you do not specify any flags to
 * openssl_cms_sign.
 * If you turn this option off, the message will be signed using
 * opaque signing, which is more resistant to translation by mail relays
 * but cannot be read by mail agents that do not support S/MIME.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_DETACHED', 64);

/**
 * Adds text/plain content type headers to encrypted/signed
 * message. If decrypting or verifying, it strips those headers from
 * the output - if the decrypted or verified message is not of MIME type
 * text/plain then an error will occur.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_TEXT', 1);

/**
 * When verifying a message, certificates (if
 * any) included in the message are normally searched for the
 * signing certificate. With this option only the
 * certificates specified in the untrusted_certificates_filename
 * parameter of openssl_cms_verify are
 * used. The supplied certificates can still be used as
 * untrusted CAs however.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_NOINTERN', 16);

/**
 * Do not verify the signers certificate of a signed
 * message.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_NOVERIFY', 32);

/**
 * When signing a message the signer's certificate is normally
 * included - with this option it is excluded. This will reduce the
 * size of the signed message but the verifier must have a copy of the
 * signers certificate available locally (passed using the
 * untrusted_certificates_filename to
 * openssl_cms_verify for example).
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_NOCERTS', 2);

/**
 * Normally when a message is signed, a set of attributes are
 * included which include the signing time and the supported symmetric
 * algorithms. With this option they are not included.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_NOATTR', 256);

/**
 * Normally the input message is converted to "canonical" format
 * which is effectively using CR and LF
 * as end of line: as required by the CMS specification. When this
 * option is present, no translation occurs. This is useful when
 * handling binary data which may not be in CMS format.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_BINARY', 128);

/**
 * Don't try and verify the signatures on a message
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CMS_NOSIGS', 12);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_PKCS1_PADDING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_SSLV23_PADDING', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_NO_PADDING', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_PKCS1_OAEP_PADDING', 4);
define ('OPENSSL_DEFAULT_STREAM_CIPHERS', "ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-AES256-GCM-SHA384:DHE-RSA-AES128-GCM-SHA256:DHE-DSS-AES128-GCM-SHA256:kEDH+AESGCM:ECDHE-RSA-AES128-SHA256:ECDHE-ECDSA-AES128-SHA256:ECDHE-RSA-AES128-SHA:ECDHE-ECDSA-AES128-SHA:ECDHE-RSA-AES256-SHA384:ECDHE-ECDSA-AES256-SHA384:ECDHE-RSA-AES256-SHA:ECDHE-ECDSA-AES256-SHA:DHE-RSA-AES128-SHA256:DHE-RSA-AES128-SHA:DHE-DSS-AES128-SHA256:DHE-RSA-AES256-SHA256:DHE-DSS-AES256-SHA:DHE-RSA-AES256-SHA:AES128-GCM-SHA256:AES256-GCM-SHA384:AES128:AES256:HIGH:!SSLv2:!aNULL:!eNULL:!EXPORT:!DES:!MD5:!RC4:!ADH");

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_RC2_40', 0);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_RC2_128', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_RC2_64', 2);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_DES', 3);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_3DES', 4);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_AES_128_CBC', 5);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_AES_192_CBC', 6);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_CIPHER_AES_256_CBC', 7);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_KEYTYPE_RSA', 0);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_KEYTYPE_DSA', 1);

/**
 * 
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_KEYTYPE_DH', 2);

/**
 * This constant is only available when PHP is compiled with OpenSSL 0.9.8+.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_KEYTYPE_EC', 3);

/**
 * If OPENSSL_RAW_DATA is set in the
 * openssl_encrypt or openssl_decrypt,
 * the returned data is returned as-is.
 * When it is not specified, Base64 encoded data is returned to the caller.
 * @link http://www.php.net/manual/en/openssl.constants.php
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
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ZERO_PADDING', 2);
define ('OPENSSL_DONT_ZERO_PAD_KEY', 4);

/**
 * Whether SNI support is available or not.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_TLSEXT_SERVER_NAME', 1);

/**
 * Indicates that encoding is DER (Distinguished Encoding Rules).
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ENCODING_DER', 0);

/**
 * Indicates that encoding is S/MIME.
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ENCODING_SMIME', 1);

/**
 * Indicates that encoding is PEM (Privacy-Enhanced Mail).
 * @link http://www.php.net/manual/en/openssl.constants.php
 */
define ('OPENSSL_ENCODING_PEM', 2);

// End of openssl v.8.2.6
