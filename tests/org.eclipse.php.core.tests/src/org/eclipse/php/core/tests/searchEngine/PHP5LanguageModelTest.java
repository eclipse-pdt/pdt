package org.eclipse.php.core.tests.searchEngine;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHP5LanguageModelTest extends TestCase {

	private static IProject project;

	protected void setUp() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"PHPLanguageModelTests");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		PHPCoreTests.waitForIndexer();
		PHPCoreTests.waitForAutoBuild();
	}

	protected void tearDown() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public void testBcmath() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"bcsqrt", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	private IDLTKSearchScope createScope() {
		IScriptProject scriptProject = DLTKCore.create(project);

		IDLTKSearchScope scope = SearchEngine.createSearchScope(scriptProject);
		return scope;
	}

	public void testBz2() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"bzopen", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testCalendar() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"jdtogregorian", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testCtype() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"ctype_alnum", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testCurl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"curl_init", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testDate() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"getTimezone", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testDom() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"getTraceAsString", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testExif() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"read_exif_data", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testFilter() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"filter_input", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testFtp() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"ftp_connect", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testGd() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"gd_info", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testGettext() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"dgettext", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testHash() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"hash_file", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testIconv() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"iconv", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testIbm_db2() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"db2_connect", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testImap() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"imap_open", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testIntl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"asort", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testJson() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"json_encode", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testLdap() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"ldap_connect", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testMcrypt() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"mcrypt_cfb", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testMhash() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"mhash_keygen_s2k", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testMime_magic() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"mime_content_type", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testMysql() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"mysql_connect", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testMysqli() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"embedded_server_start", MatchRule.PREFIX, Modifiers.AccGlobal,
				0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testOci8() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"tell", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testOpenssl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"openssl_pkey_new", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testPcre() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"preg_match", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testPosix() {
		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"posix_getpid", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testPdo() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"pdo_drivers", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testPgsql() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"pg_connect", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testReflection() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"getClassNames", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testSession() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"session_module_name", MatchRule.PREFIX, Modifiers.AccGlobal,
				0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testSimpleXml() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"asXML", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testSoap() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"SoapClient", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testSockets() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"socket_select", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testSpl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"rewind", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testSqlite() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"queryExec", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testStandard() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"bin2hex", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testTidy() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"cleanRepair", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testTokenizer() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"token_name", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testXml() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"xml_set_object", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testXmlreader() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"setRelaxNGSchema", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testXmlwriter() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"setIndent", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testXsl() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"transformToUri", MatchRule.PREFIX, Modifiers.AccGlobal, 0,
				scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testZip() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"zip_entry_compressionmethod", MatchRule.PREFIX,
				Modifiers.AccGlobal, 0, scope, new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}

	public void testZlib() {

		IDLTKSearchScope scope = createScope();

		IMethod[] allFunctions = PhpModelAccess.getDefault().findMethods(
				"gzrewind", MatchRule.PREFIX, Modifiers.AccGlobal, 0, scope,
				new NullProgressMonitor());

		assert (allFunctions.length > 0);
	}
}
