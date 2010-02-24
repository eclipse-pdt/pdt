<?php

/**
 * Loads a PHP extension at runtime
 * @link http://www.php.net/manual/en/function.dl.php
 * @param library string <p>
 * This parameter is only the filename of the
 * extension to load which also depends on your platform. For example,
 * the sockets extension (if compiled
 * as a shared module, not the default!) would be called 
 * sockets.so on Unix platforms whereas it is called
 * php_sockets.dll on the Windows platform.
 * </p>
 * <p>
 * The directory where the extension is loaded from depends on your
 * platform:
 * </p>
 * <p>
 * Windows - If not explicitly set in the &php.ini;, the extension is
 * loaded from C:\php4\extensions\ (PHP4) or 
 * C:\php5\ (PHP5) by default.
 * </p>
 * <p>
 * Unix - If not explicitly set in the &php.ini;, the default extension
 * directory depends on
 * whether PHP has been built with --enable-debug
 * or not
 * @return int Returns true on success or false on failure. If the functionality of loading modules is not available
 * or has been disabled (either by setting
 * enable_dl off or by enabling &safemode;
 * in &php.ini;) an E_ERROR is emitted
 * and execution is stopped. If dl fails because the
 * specified library couldn't be loaded, in addition to false an
 * E_WARNING message is emitted.
 */
function dl ($library) {}

define ('__FILE__', null);
define ('__LINE__', null);
define ('__CLASS__', null);
define ('__FUNCTION__', null);
define ('__METHOD__', null);
define ('__DIR__', null);
define ('__NAMESPACE__', null);
?>
