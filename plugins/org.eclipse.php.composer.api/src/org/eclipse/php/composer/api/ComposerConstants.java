/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api;

public class ComposerConstants {

	public static final String COMPOSER_JSON = "composer.json"; //$NON-NLS-1$
	public static final String SEARCH_URL = "https://packagist.org/search.json?q=%s"; //$NON-NLS-1$
	public static final String PACKAGE_URL = "https://packagist.org/packages/%s.json"; //$NON-NLS-1$
	public static final String PHAR_URL = "https://getcomposer.org/composer.phar"; //$NON-NLS-1$
	public static final String STABLE = "stable"; //$NON-NLS-1$
	public static final String RC = "RC"; //$NON-NLS-1$
	public static final String BETA = "beta"; //$NON-NLS-1$
	public static final String ALPHA = "alpha"; //$NON-NLS-1$
	public static final String DEV = "dev"; //$NON-NLS-1$
	public static final String[] STABILITIES = { STABLE, RC, BETA, ALPHA, DEV };
	public static final String[] CONSTRAINTS = { "~", ">", ">=", "<", "<=", "!=" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	public final static String VENDOR_DIR_DEFAULT = "vendor"; //$NON-NLS-1$
	public final static String BIN_DIR_DEFAULT = VENDOR_DIR_DEFAULT + "/bin"; //$NON-NLS-1$

	public final static String PROBLEM_MARKER_TYPE = "org.eclipse.php.composer.core.composermarker"; //$NON-NLS-1$

	/**
	 * Types taken from: https://github.com/composer/installers
	 * 
	 * Want your type here? Fork this plugin, add your type here and send a pull
	 * request. You are reading this on github? Click the edit button above.
	 **/
	public static final String[] TYPES = new String[] { "library", "project", "metapackage", "composer-plugin", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"agl-module", "annotatecms-module", "annotatecms-component", "annotatecms-service", "cakephp-plugin", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			"codeigniter-library", "codeigniter-third-party", "codeigniter-module", "concrete5-block", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"concrete5-package", "concrete5-theme", "croogo-plugin", "croogo-theme", "drupal-module", "drupal-theme", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			"drupal-profile", "drupal-drush", "fuel-module", "fuel-package", "joomla-component", "joomla-module", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			"joomla-template", "joomla-plugin", "joomla-library", "keeko-app", "keeko-module", "kohana-module", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			"laravel-library", "lithium-library", "lithium-source", "magento-library", "magento-skin", "magento-theme", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			"mako-package", "mediawiki-extension", "modulework-module", "oxid-module", "phpbb-extension", "phpbb-style", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			"phpbb-language", "ppi-module", "silverstripe-module", "silverstripe-theme", "symfony1-plugin", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			"symfony-bundle", "typo3-flow-package", "typo3-flow-framework", "typo3-flow-plugin", "typo3-flow-site", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			"typo3-flow-boilerplate", "typo3-flow-build", "typo3-cms-extension", "wordpress-plugin", "wordpress-theme", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			"yii-extension", "yii-module", "zend-library", "zend-module", "zend-extra" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	/**
	 * Taken from http://www.tldrlegal.com
	 */
	public static final String[] LICENSES = new String[] { "Academic Free License v1.1 (AFL-1.1)", //$NON-NLS-1$
			"Academic Free License v1.2 (AFL-1.2)", "Academic Free License v2.0 (AFL-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Academic Free License v2.1 (AFL-2.1)", "Academic Free License v3.0 (AFL-3.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Adaptive Public License 1.0 (APL-1.0)", "Aladdin Free Public License (Aladdin)", //$NON-NLS-1$ //$NON-NLS-2$
			"ANTLR Software Rights Notice (ANTLR-PD)", "Apache License 1.0 (Apache-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Apache License 1.1 (Apache-1.1)", "Apache License 2.0 (Apache-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Apple Public Source License 1.0 (APSL-1.0)", "Apple Public Source License 1.1 (APSL-1.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"Apple Public Source License 1.2 (APSL-1.2)", "Apple Public Source License 2.0 (APSL-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Artistic License 1.0 (Artistic-1.0)", "Artistic License 2.0 (Artistic-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Attribution Assurance License (AAL)", "BitTorrent Open Source License v1.0 (BitTorrent-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"BitTorrent Open Source License v1.1 (BitTorrent-1.1)", "Boost Software License 1.0 (BSL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"BSD 3-Clause \"Clear\" License (BSD-3-Clause-Clear)", "BSD 2-clause \"Simplified\" License (BSD-2-Clause)", //$NON-NLS-1$ //$NON-NLS-2$
			"BSD 2-clause FreeBSD License (BSD-2-Clause-FreeBSD)", "BSD 2-clause NetBSD License (BSD-2-Clause-NetBSD)", //$NON-NLS-1$ //$NON-NLS-2$
			"BSD 3-clause \"New\" or \"Revised\" License (BSD-3-Clause)", //$NON-NLS-1$
			"BSD 4-clause \"Original\" or \"Old\" License (BSD-4-Clause)", //$NON-NLS-1$
			"BSD-4-Clause (University of California-Specific) (BSD-4-Clause-UC)", //$NON-NLS-1$
			"CeCILL Free Software License Agreement v1.0 (CECILL-1.0)", //$NON-NLS-1$
			"CeCILL Free Software License Agreement v1.1 (CECILL-1.1)", //$NON-NLS-1$
			"CeCILL Free Software License Agreement v2.0 (CECILL-2.0)", //$NON-NLS-1$
			"CeCILL-B Free Software License Agreement (CECILL-B)", //$NON-NLS-1$
			"CeCILL-C Free Software License Agreement (CECILL-C)", "Clarified Artistic License (ClArtistic)", //$NON-NLS-1$ //$NON-NLS-2$
			"CNRI Python License (CNRI-Python)", //$NON-NLS-1$
			"CNRI Python Open Source GPL Compatible License Agreement (CNRI-Python-GPL-Compatible)", //$NON-NLS-1$
			"Common Development and Distribution License 1.0 (CDDL-1.0)", //$NON-NLS-1$
			"Common Development and Distribution License 1.1 (CDDL-1.1)", //$NON-NLS-1$
			"Common Public Attribution License 1.0 (CPAL-1.0)", "Common Public License 1.0 (CPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Computer Associates Trusted Open Source License 1.1 (CATOSL-1.1)", //$NON-NLS-1$
			"Condor Public License v1.1 (Condor-1.1)", "Creative Commons Attribution 1.0 (CC-BY-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Creative Commons Attribution 2.0 (CC-BY-2.0)", "Creative Commons Attribution 2.5 (CC-BY-2.5)", //$NON-NLS-1$ //$NON-NLS-2$
			"Creative Commons Attribution 3.0 (CC-BY-3.0)", //$NON-NLS-1$
			"Creative Commons Attribution No Derivatives 1.0 (CC-BY-ND-1.0)", //$NON-NLS-1$
			"Creative Commons Attribution No Derivatives 2.0 (CC-BY-ND-2.0)", //$NON-NLS-1$
			"Creative Commons Attribution No Derivatives 2.5 (CC-BY-ND-2.5)", //$NON-NLS-1$
			"Creative Commons Attribution No Derivatives 3.0 (CC-BY-ND-3.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial 1.0 (CC-BY-NC-1.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial 2.0 (CC-BY-NC-2.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial 2.5 (CC-BY-NC-2.5)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial 3.0 (CC-BY-NC-3.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial No Derivatives 1.0 (CC-BY-NC-ND-1.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial No Derivatives 2.0 (CC-BY-NC-ND-2.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial No Derivatives 2.5 (CC-BY-NC-ND-2.5)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial No Derivatives 3.0 (CC-BY-NC-ND-3.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial Share Alike 1.0 (CC-BY-NC-SA-1.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial Share Alike 2.0 (CC-BY-NC-SA-2.0)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial Share Alike 2.5 (CC-BY-NC-SA-2.5)", //$NON-NLS-1$
			"Creative Commons Attribution Non Commercial Share Alike 3.0 (CC-BY-NC-SA-3.0)", //$NON-NLS-1$
			"Creative Commons Attribution Share Alike 1.0 (CC-BY-SA-1.0)", //$NON-NLS-1$
			"Creative Commons Attribution Share Alike 2.0 (CC-BY-SA-2.0)", //$NON-NLS-1$
			"Creative Commons Attribution Share Alike 2.5 (CC-BY-SA-2.5)", //$NON-NLS-1$
			"Creative Commons Attribution Share Alike 3.0 (CC-BY-SA-3.0)", //$NON-NLS-1$
			"Creative Commons Zero v1.0 Universal (CC0-1.0)", "CUA Office Public License v1.0 (CUA-OPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Do What The F*ck You Want To Public License (WTFPL)", "Eclipse Public License 1.0 (EPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"eCos license version 2.0 (eCos-2.0)", "Educational Community License v1.0 (ECL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Educational Community License v2.0 (ECL-2.0)", "Eiffel Forum License v1.0 (EFL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Eiffel Forum License v2.0 (EFL-2.0)", "Entessa Public License v1.0 (Entessa)", //$NON-NLS-1$ //$NON-NLS-2$
			"Erlang Public License v1.1 (ErlPL-1.1)", "EU DataGrid Software License (EUDatagrid)", //$NON-NLS-1$ //$NON-NLS-2$
			"European Union Public License 1.0 (EUPL-1.0)", "European Union Public License 1.1 (EUPL-1.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"Fair License (Fair)", "Frameworx Open License 1.0 (Frameworx-1.0)", "Freetype Project License (FTL)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"GNU Affero General Public License v3.0 (AGPL-3.0)", "GNU Free Documentation License v1.1 (GFDL-1.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"GNU Free Documentation License v1.2 (GFDL-1.2)", "GNU Free Documentation License v1.3 (GFDL-1.3)", //$NON-NLS-1$ //$NON-NLS-2$
			"GNU General Public License v1.0 only (GPL-1.0)", "GNU General Public License v1.0 or later (GPL-1.0+)", //$NON-NLS-1$ //$NON-NLS-2$
			"GNU General Public License v2.0 only (GPL-2.0)", "GNU General Public License v2.0 or later (GPL-2.0+)", //$NON-NLS-1$ //$NON-NLS-2$
			"GNU General Public License v2.0 w/Autoconf exception (GPL-2.0-with-autoconf-exception)", //$NON-NLS-1$
			"GNU General Public License v2.0 w/Bison exception (GPL-2.0-with-bison-exception)", //$NON-NLS-1$
			"GNU General Public License v2.0 w/Classpath exception (GPL-2.0-with-classpath-exception)", //$NON-NLS-1$
			"GNU General Public License v2.0 w/Font exception (GPL-2.0-with-font-exception)", //$NON-NLS-1$
			"GNU General Public License v2.0 w/GCC Runtime Library exception (GPL-2.0-with-GCC-exception)", //$NON-NLS-1$
			"GNU General Public License v3.0 only (GPL-3.0)", "GNU General Public License v3.0 or later (GPL-3.0+)", //$NON-NLS-1$ //$NON-NLS-2$
			"GNU General Public License v3.0 w/Autoconf exception (GPL-3.0-with-autoconf-exception)", //$NON-NLS-1$
			"GNU General Public License v3.0 w/GCC Runtime Library exception (GPL-3.0-with-GCC-exception)", //$NON-NLS-1$
			"GNU Lesser General Public License v2.1 only (LGPL-2.1)", //$NON-NLS-1$
			"GNU Lesser General Public License v2.1 or later (LGPL-2.1+)", //$NON-NLS-1$
			"GNU Lesser General Public License v3.0 only (LGPL-3.0)", //$NON-NLS-1$
			"GNU Lesser General Public License v3.0 or later (LGPL-3.0+)", //$NON-NLS-1$
			"GNU Library General Public License v2 only (LGPL-2.0)", //$NON-NLS-1$
			"GNU Library General Public License v2 or later (LGPL-2.0+)", "gSOAP Public License v1.3b (gSOAP-1.3b)", //$NON-NLS-1$ //$NON-NLS-2$
			"Historic Permission Notice and Disclaimer (HPND)", "IBM Public License v1.0 (IPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Imlib2 License (Imlib2)", "Independent JPEG Group License (IJG)", "Intel Open Source License (Intel)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"IPA Font License (IPA)", "ISC License (ISC)", "JSON License (JSON)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"LaTeX Project Public License 1.3a (LPPL-1.3a)", "LaTeX Project Public License v1.0 (LPPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"LaTeX Project Public License v1.1 (LPPL-1.1)", "LaTeX Project Public License v1.2 (LPPL-1.2)", //$NON-NLS-1$ //$NON-NLS-2$
			"LaTeX Project Public License v1.3c (LPPL-1.3c)", "libpng License (Libpng)", //$NON-NLS-1$ //$NON-NLS-2$
			"Lucent Public License v1.02 (LPL-1.02)", "Lucent Public License Version 1.0 (LPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Microsoft Public License (MS-PL)", "Microsoft Reciprocal License (MS-RL)", "MirOS Licence (MirOS)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"MIT License (MIT)", "Motosoto License (Motosoto)", "Mozilla Public License 1.0 (MPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"Mozilla Public License 1.1 (MPL-1.1)", "Mozilla Public License 2.0 (MPL-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Mozilla Public License 2.0 (no copyleft exception) (MPL-2.0-no-copyleft-exception)", //$NON-NLS-1$
			"Multics License (Multics)", "NASA Open Source Agreement 1.3 (NASA-1.3)", "Naumen Public License (Naumen)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"Net Boolean Public License v1 (NBPL-1.0)", "Nethack General Public License (NGPL)", //$NON-NLS-1$ //$NON-NLS-2$
			"Netizen Open Source License (NOSL)", "Netscape Public License v1.0 (NPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Netscape Public License v1.1 (NPL-1.1)", "Nokia Open Source License (Nokia)", //$NON-NLS-1$ //$NON-NLS-2$
			"Non-Profit Open Software License 3.0 (NPOSL-3.0)", "NTP License (NTP)", //$NON-NLS-1$ //$NON-NLS-2$
			"OCLC Research Public License 2.0 (OCLC-2.0)", "ODC Open Database License v1.0 (ODbL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"ODC Public Domain Dedication &amp; License 1.0 (PDDL-1.0)", "Open Group Test Suite License (OGTSL)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open LDAP Public License  2.2.2 (OLDAP-2.2.2)", "Open LDAP Public License v1.1 (OLDAP-1.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open LDAP Public License v1.2 (OLDAP-1.2)", "Open LDAP Public License v1.3 (OLDAP-1.3)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open LDAP Public License v1.4 (OLDAP-1.4)", //$NON-NLS-1$
			"Open LDAP Public License v2.0 (or possibly 2.0A and 2.0B) (OLDAP-2.0)", //$NON-NLS-1$
			"Open LDAP Public License v2.0.1 (OLDAP-2.0.1)", "Open LDAP Public License v2.1 (OLDAP-2.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open LDAP Public License v2.2 (OLDAP-2.2)", "Open LDAP Public License v2.2.1 (OLDAP-2.2.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open LDAP Public License v2.3 (OLDAP-2.3)", "Open LDAP Public License v2.4 (OLDAP-2.4)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open LDAP Public License v2.5 (OLDAP-2.5)", "Open LDAP Public License v2.6 (OLDAP-2.6)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open LDAP Public License v2.7 (OLDAP-2.7)", "Open Public License v1.0 (OPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open Software License 1.0 (OSL-1.0)", "Open Software License 2.0 (OSL-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Open Software License 2.1 (OSL-2.1)", "Open Software License 3.0 (OSL-3.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"OpenLDAP Public License v2.8 (OLDAP-2.8)", "OpenSSL License (OpenSSL)", "PHP License v3.0 (PHP-3.0)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"PHP LIcense v3.01 (PHP-3.01)", "PostgreSQL License (PostgreSQL)", "Python License 2.0 (Python-2.0)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"Q Public License 1.0 (QPL-1.0)", "RealNetworks Public Source License v1.0 (RPSL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Reciprocal Public License 1.5 (RPL-1.5)", "Red Hat eCos Public License v1.1 (RHeCos-1.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"Ricoh Source Code Public License (RSCPL)", "Ruby License (Ruby)", "Sax Public Domain Notice (SAX-PD)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"SGI Free Software License B v1.0 (SGI-B-1.0)", "SGI Free Software License B v1.1 (SGI-B-1.1)", //$NON-NLS-1$ //$NON-NLS-2$
			"SGI Free Software License B v2.0 (SGI-B-2.0)", "SIL Open Font License 1.0 (OFL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"SIL Open Font License 1.1 (OFL-1.1)", "Simple Public License 2.0 (SimPL-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Sleepycat License (Sleepycat)", "Standard ML of New Jersey License (SMLNJ)", //$NON-NLS-1$ //$NON-NLS-2$
			"SugarCRM Public License v1.1.3 (SugarCRM-1.1.3)", "Sun Industry Standards Source License (SISSL)", //$NON-NLS-1$ //$NON-NLS-2$
			"Sun Public License v1.0 (SPL-1.0)", "Sybase Open Watcom Public License 1.0 (Watcom-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"University of Illinois/NCSA Open Source License (NCSA)", "Vovida Software License v1.0 (VSL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"W3C Software and Notice License (W3C)", "wxWindows Library License (WXwindows)", "X.Net License (Xnet)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"X11 License (X11)", "XFree86 License 1.1 (XFree86-1.1)", "Yahoo! Public License v1.0 (YPL-1.0)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"Yahoo! Public License v1.1 (YPL-1.1)", "Zimbra Public License v1.3 (Zimbra-1.3)", "zlib License (Zlib)", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"Zope Public License 1.1 (ZPL-1.1)", "Zope Public License 2.0 (ZPL-2.0)", //$NON-NLS-1$ //$NON-NLS-2$
			"Zope Public License 2.1 (ZPL-2.1)" }; //$NON-NLS-1$
}
