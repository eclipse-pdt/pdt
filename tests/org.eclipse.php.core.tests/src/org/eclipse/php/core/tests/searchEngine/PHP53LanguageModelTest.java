/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.searchEngine;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PHP53LanguageModelTest {

	private static IProject project;

	@BeforeClass
	public static void setUp() throws Exception {
		project = TestUtils.createProject("PHPLanguageModelTests");
		ResourcesPlugin.getWorkspace().getRoot().getProject();
		TestUtils.setProjectPhpVersion(project, PHPVersion.PHP5_3);
		TestUtils.waitForIndexer();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Test
	public void bcmath() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("bcsqrt", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	private IDLTKSearchScope createScope() {
		IScriptProject scriptProject = DLTKCore.create(project);

		IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
		return scope;
	}

	@Test
	public void bz2() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("bzopen", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void calendar() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("jdtogregorian", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void ctype() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("ctype_alnum", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void curl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("curl_init", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void date() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("getTimezone", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void dom() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("getTraceAsString", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void exif() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("read_exif_data", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void filter() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("filter_input", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void ftp() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("ftp_connect", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void gd() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("gd_info", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void gettext() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("dgettext", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void hash() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("hash_file", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void iconv() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("iconv", MatchRule.PREFIX, Modifiers.AccGlobal,
				0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void ibm_db2() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("db2_connect", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void imap() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("imap_open", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void intl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("asort", MatchRule.PREFIX, Modifiers.AccGlobal,
				0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void json() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("json_encode", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void ldap() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("ldap_connect", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void mcrypt() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("mcrypt_cfb", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void mhash() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("mhash_keygen_s2k", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void mime_magic() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("mime_content_type", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void mysql() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("mysql_connect", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void mysqli() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("embedded_server_start", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void oci8() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("tell", MatchRule.PREFIX, Modifiers.AccGlobal,
				0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void openssl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("openssl_pkey_new", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void pcre() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("preg_match", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void posix() {
		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("posix_getpid", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void pdo() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("pdo_drivers", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void pgsql() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("pg_connect", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void reflection() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("getClassNames", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void session() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("session_module_name", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void simpleXml() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("asXML", MatchRule.PREFIX, Modifiers.AccGlobal,
				0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void soap() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("SoapClient", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void sockets() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("socket_select", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void spl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("rewind", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void sqlite() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("queryExec", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void standard() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("bin2hex", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void tidy() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("cleanRepair", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void tokenizer() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("token_name", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void xml() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("xml_set_object", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void xmlreader() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("setRelaxNGSchema", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void xmlwriter() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("setIndent", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void xsl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("transformToUri", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void zip() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("zip_entry_compressionmethod",
				MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	@Test
	public void zlib() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods("gzrewind", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}
}
