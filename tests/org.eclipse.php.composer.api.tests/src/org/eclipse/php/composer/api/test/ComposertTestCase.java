/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.RepositoryPackage;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.Dependencies;
import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.php.composer.api.collection.Repositories;
import org.eclipse.php.composer.api.collection.Scripts;
import org.eclipse.php.composer.api.objects.*;
import org.eclipse.php.composer.api.objects.Script.HandlerValue;
import org.eclipse.php.composer.api.repositories.*;

import junit.framework.TestCase;

public abstract class ComposertTestCase extends TestCase {

	// dummy package contents
	protected static String NAME = "gossi/test";
	protected static String DESCRIPTION = "really dump description";
	protected static String[] KEYWORDS = new String[] { "stub", "oop" };
	protected static String LICENSE = "MIT";
	protected static String LICENSE_EPL = "EPL";
	protected static String TYPE = "library";
	protected static String HOMEPAGE = "http://pdt-extensions.org";
	protected static String MINIMUM_STABILITY = "dev";
	protected static String TARGET_DIR = "test/";

	// persons
	protected static String PERSON1 = "Robert Gruendler";
	protected static String PERSON1_EMAIL = "r.gruendler@gmail.com";
	protected static String PERSON1_ROLE = "owner";

	protected static String PERSON2 = "Thomas Gossmann";
	protected static String PERSON2_HOMEPAGE = "http://gos.si";
	protected static String PERSON2_ROLE = "contributor";

	// autoload
	protected static String NAMESPACE = "gossi";
	protected static String NAMESPACE2 = "Monolog";
	protected static String NAMESPACE3 = "UniqueGlobalClass";
	protected static String NAMESPACE_PATH1 = "src/gossi";
	protected static String NAMESPACE_PATH2 = "lib/";
	protected static String NAMESPACE_PATH3 = "etc/";
	protected static String[] AUTOLOAD_MAP = new String[] { NAMESPACE_PATH1, NAMESPACE_PATH2, "Something.php" };
	protected static String[] AUTOLOAD_FILES = new String[] { "src/MyLibrary/functions.php" };

	// dependencies
	protected static String PHP = "php";
	protected static String PHP_VERSION = ">=5.3.2";
	protected static String PHPUNIT = "phpunit/phpunit";
	protected static String PHPUNIT_VERSION = "3.7.9";

	// config
	protected static String BIN_DIR = "bin/";
	protected static boolean NOTIFY_ON_INSTALL = true;
	protected static int PROCESS_TIMEOUT = 300;
	protected static String VENDOR_DIR = "vendor/";
	protected static String[] GITHUB_PROTOCOLS = new String[] { "git", "http" };

	// support
	protected static String EMAIL = "test@mail.com";
	protected static String IRC = "irc://freenode.org/pdt";
	protected static String SOURCE = "https://github.com/pulse00/Composer-Java-Bindings";
	protected static String ISSUES = "https://github.com/pulse00/Composer-Java-Bindings/issues";
	protected static String FORUM = "https://groups.google.com/forum/?fromgroups=#!forum/pdt-extensions-platform";
	protected static String WIKI = "https://github.com/pdt-eg/Core-Plugin/wiki";

	// repositories
	protected static String COMPOSER_URL = "https://packagist.org/packages.json";
	protected static String PEAR_URL = "http://pear2.php.net";
	protected static String PEAR_ALIAS = "foobar";
	protected static String SVN_URL = "https://svn.github.com";
	protected static String TRUNK = "trunk/";
	protected static String BRANCHES = "branches/";
	protected static String TAGS = "tags/";
	protected static String VCS_URL = "https://github.com/Seldaek/monolog";

	protected static String SMARTY = "smarty/smarty";
	protected static String SMARTY_VERSION = "3.1.7";
	protected static String SMARTY_ZIP = "http://www.smarty.net/files/Smarty-3.1.7.zip";
	protected static String SMARTY_DIST = "zip";
	protected static String SMARTY_SVN = "http://smarty-php.googlecode.com/svn/";
	protected static String SMARTY_SOURCE = "svn";
	protected static String SMARTY_REFERENCE = "tags/Smarty_3_1_7/distribution/";

	// scripts
	protected static String PRE_INSTALL_CMD = "pre\\install::cmd()";
	protected static String POST_INSTALL_CMD = "post\\install::cmd()";
	protected static String POST_INSTALL_TEST = "phpunit";
	protected static String PRE_UPDATE_CMD = "pre\\update::cmd()";
	protected static String POST_UPDATE_CMD = "post\\update::cmd()";
	protected static String PRE_PACKAGE_INSTALL = "pre\\package::install()";
	protected static String POST_PACKAGE_INSTALL = "post\\package::install()";
	protected static String PRE_PACKAGE_UPDATE = "pre\\package::update()";
	protected static String POST_PACKAGE_UPDATE = "post\\package::update()";
	protected static String PRE_PACKAGE_UNINSTALL = "pre\\package::uninstall()";
	protected static String POST_PACKAGE_UNINSTALL = "post\\package::uninstall()";

	protected Reader loadFile(String name) throws URISyntaxException {
		return new InputStreamReader(getClass().getResourceAsStream("/Resources/" + name));
	}

	protected ComposerPackage createDummyPackage() {
		ComposerPackage phpPackage = new ComposerPackage();

		// name
		phpPackage.setName(NAME);

		// description
		phpPackage.setDescription(DESCRIPTION);

		// type
		phpPackage.setType(TYPE);

		// homepage
		phpPackage.setHomepage(HOMEPAGE);

		// target-dir
		phpPackage.setTargetDir(TARGET_DIR);

		// minimum-stability
		phpPackage.setMinimumStability(MINIMUM_STABILITY);

		// keywords
		JsonArray keywords = phpPackage.getKeywords();
		for (String keyword : KEYWORDS) {
			keywords.add(keyword);
		}

		// authors
		Person robert = new Person();
		robert.setName(PERSON1);
		robert.setEmail(PERSON1_EMAIL);
		robert.setRole(PERSON1_ROLE);

		phpPackage.getAuthors().add(robert);

		Person gossi = new Person();
		gossi.setName(PERSON2);
		gossi.setHomepage(PERSON2_HOMEPAGE);
		gossi.setRole(PERSON2_ROLE);

		phpPackage.getAuthors().add(gossi);

		// license
		phpPackage.getLicense().add(LICENSE);
		phpPackage.getLicense().add(LICENSE_EPL);

		// autoload
		createAutoload(phpPackage);

		// dependencies
		createDependencies(phpPackage);

		// repositories
		createRepositories(phpPackage);

		// support
		createSupport(phpPackage);

		// config
		createConfig(phpPackage);

		// scripts
		createScripts(phpPackage);

		// extras
		Extra extra = phpPackage.getExtra();
		extra.set("just", "something");

		return phpPackage;
	}

	private void createAutoload(ComposerPackage phpPackage) {
		Autoload autoload = phpPackage.getAutoload();

		// psr0
		Namespace gossi = new Namespace();
		gossi.setNamespace(NAMESPACE);
		gossi.add(NAMESPACE_PATH1);
		autoload.getPsr0().add(gossi);

		Namespace monolog = new Namespace();
		monolog.setNamespace(NAMESPACE2);
		monolog.add(NAMESPACE_PATH1);
		monolog.add(NAMESPACE_PATH2);
		autoload.getPsr0().add(monolog);

		Namespace uniqueClass = new Namespace();
		uniqueClass.setNamespace(NAMESPACE3);
		autoload.getPsr0().add(uniqueClass);

		Namespace empty = new Namespace();
		empty.setNamespace("");
		empty.add(NAMESPACE_PATH3);
		autoload.getPsr0().add(empty);

		// classmap
		JsonArray map = autoload.getClassMap();
		for (String m : AUTOLOAD_MAP) {
			map.add(m);
		}

		// files
		JsonArray files = autoload.getFiles();
		for (String f : AUTOLOAD_FILES) {
			files.add(f);
		}

	}

	private void createDependencies(ComposerPackage phpPackage) {
		Dependencies require = phpPackage.getRequire();
		VersionedPackage php = new VersionedPackage();
		php.setName(PHP);
		php.setVersion(PHP_VERSION);
		require.add(php);

		Dependencies requireDev = phpPackage.getRequireDev();
		VersionedPackage phpUnit = new VersionedPackage();
		phpUnit.setName(PHPUNIT);
		phpUnit.setVersion(PHPUNIT_VERSION);
		requireDev.add(phpUnit);
	}

	private void createSupport(ComposerPackage phpPackage) {
		Support support = phpPackage.getSupport();
		support.setEmail(EMAIL);
		support.setIssues(ISSUES);
		support.setSource(SOURCE);
		support.setWiki(WIKI);
		support.setForum(FORUM);
		support.setIrc(IRC);
	}

	private void createConfig(ComposerPackage phpPackage) {
		Config config = phpPackage.getConfig();
		config.setBinDir(BIN_DIR);
		config.setNotifyOnInstall(NOTIFY_ON_INSTALL);
		config.setProcessTimeout(PROCESS_TIMEOUT);
		config.setVendorDir(VENDOR_DIR);

		JsonArray githubProtocols = new JsonArray();
		for (String p : GITHUB_PROTOCOLS) {
			githubProtocols.add(p);
		}
		config.setGithubProtocols(githubProtocols);
	}

	private void addCmd(Scripts scripts, String script, String handler) {
		if (!scripts.has(script)) {
			Script s = new Script();
			s.setScript(script);
			scripts.add(s);
		}

		scripts.get(script).add(new HandlerValue(handler));
	}

	private String getCmd(Scripts scripts, String script, int index) {
		if (!scripts.has(script) || index < 0 || index >= scripts.get(script).size()) {
			return null;
		}

		return scripts.get(script).get(index).getAsString();
	}

	private void createScripts(ComposerPackage phpPackage) {
		Scripts scripts = phpPackage.getScripts();

		addCmd(scripts, "pre-install-cmd", PRE_INSTALL_CMD);
		addCmd(scripts, "post-install-cmd", POST_INSTALL_CMD);
		addCmd(scripts, "post-install-cmd", POST_INSTALL_TEST);
		addCmd(scripts, "pre-update-cmd", PRE_UPDATE_CMD);
		addCmd(scripts, "post-update-cmd", POST_UPDATE_CMD);

		addCmd(scripts, "pre-package-install", PRE_PACKAGE_INSTALL);
		addCmd(scripts, "post-package-install", POST_PACKAGE_INSTALL);

		addCmd(scripts, "pre-package-update", PRE_PACKAGE_UPDATE);
		addCmd(scripts, "post-package-update", POST_PACKAGE_UPDATE);

		addCmd(scripts, "pre-package-uninstall", PRE_PACKAGE_UNINSTALL);
		addCmd(scripts, "post-package-uninstall", POST_PACKAGE_UNINSTALL);
	}

	private void createRepositories(ComposerPackage phpPackage) {
		Repositories repos = phpPackage.getRepositories();

		ComposerRepository composerRepo = new ComposerRepository();
		composerRepo.setUrl(COMPOSER_URL);
		repos.add(composerRepo);

		JsonObject options = composerRepo.getOptions();
		JsonObject ssl = new JsonObject();
		ssl.set("verify_peer", true);
		options.set("ssl", ssl);

		SubversionRepository svnRepo = new SubversionRepository();
		svnRepo.setUrl(SVN_URL); // haha
		svnRepo.setTrunkPath(TRUNK);
		svnRepo.setTagsPath(TAGS);
		svnRepo.setBranchesPath(BRANCHES);
		repos.add(svnRepo);

		PearRepository pearRepo = new PearRepository();
		pearRepo.setUrl(PEAR_URL);
		pearRepo.setVendorAlias(PEAR_ALIAS);
		repos.add(pearRepo);

		PackageRepository pkgRepo = new PackageRepository();
		repos.add(pkgRepo);

		RepositoryPackage pkg = pkgRepo.getPackage();
		pkg.setName(SMARTY);
		pkg.setVersion(SMARTY_VERSION);

		Distribution dist = pkg.getDist();
		dist.setType(SMARTY_DIST);
		dist.setUrl(SMARTY_ZIP);

		Source src = pkg.getSource();
		src.setType(SMARTY_SOURCE);
		src.setUrl(SMARTY_SVN);
		src.setReference(SMARTY_REFERENCE);

		VcsRepository vcsRepo = new VcsRepository();
		vcsRepo.setUrl(VCS_URL);
		repos.add(vcsRepo);
	}

	protected void doTestComposerPackage(ComposerPackage phpPackage) {
		assertNotNull(phpPackage);

		assertEquals(NAME, phpPackage.getName());
		assertEquals(TYPE, phpPackage.getType());
		assertEquals(DESCRIPTION, phpPackage.getDescription());

		assertEquals(KEYWORDS[0], phpPackage.getKeywords().get(0));
		assertEquals(HOMEPAGE, phpPackage.getHomepage());

		assertEquals(TARGET_DIR, phpPackage.getTargetDir());
		assertEquals(MINIMUM_STABILITY, phpPackage.getMinimumStability());

		assertNotNull("Authors not NULL", phpPackage.getAuthors());
		assertEquals(2, phpPackage.getAuthors().size());

		Person robert = phpPackage.getAuthors().get(0);
		assertEquals(PERSON1, robert.getName());
		assertEquals(PERSON1_EMAIL, robert.getEmail());
		assertEquals(PERSON1_ROLE, robert.getRole());

		Person gossi = phpPackage.getAuthors().get(1);
		assertEquals(PERSON2, gossi.getName());
		assertEquals(PERSON2_HOMEPAGE, gossi.getHomepage());
		assertEquals(PERSON2_ROLE, gossi.getRole());

		assertEquals(2, phpPackage.getLicense().size());
		assertEquals(LICENSE, phpPackage.getLicense().get(0));
		assertEquals(LICENSE_EPL, phpPackage.getLicense().get(1));

		assertEquals(KEYWORDS.length, phpPackage.getKeywords().size());
	}

	protected void doTestDependencies(ComposerPackage phpPackage) {
		assertNotNull(phpPackage.getRequire());
		assertEquals(1, phpPackage.getRequire().size());

		Dependencies require = phpPackage.getRequire();

		for (VersionedPackage dep : require) {
			assertNotNull(dep.getName());
			assertNotNull(dep.getVersion());
		}

		assertNotNull(phpPackage.getRequireDev());
	}

	protected void doTestAutoload(ComposerPackage phpPackage) {
		Autoload al = phpPackage.getAutoload();

		assertNotNull(al);

		// psr0 tests
		Psr psr = al.getPsr0();

		assertEquals(4, psr.size());
		assertEquals(1, psr.get(NAMESPACE).size());
		assertEquals(NAMESPACE_PATH1, psr.get(NAMESPACE).getFirst());

		assertEquals(2, psr.get(NAMESPACE2).size());
		assertEquals(NAMESPACE_PATH2, psr.get(NAMESPACE2).getPaths().get(1));

		assertEquals(1, psr.get(NAMESPACE3).size());
		assertEquals("", psr.get(NAMESPACE3).getFirst());

		assertNotNull(psr.get(""));
		assertEquals(NAMESPACE_PATH3, psr.get("").getFirst());

		// classmap
		assertTrue(al.hasClassMap());
		JsonArray classMap = al.getClassMap();

		assertEquals(3, classMap.size());

		// files
		assertTrue(al.hasFiles());
		JsonArray files = al.getFiles();

		assertEquals(1, files.size());
	}

	protected void doTestSupport(ComposerPackage phpPackage) {
		Support support = phpPackage.getSupport();

		assertEquals(EMAIL, support.getEmail());
		assertEquals(IRC, support.getIrc());
		assertEquals(ISSUES, support.getIssues());
		assertEquals(FORUM, support.getForum());
		assertEquals(WIKI, support.getWiki());
		assertEquals(SOURCE, support.getSource());
	}

	protected void doTestScripts(ComposerPackage phpPackage) {
		Scripts scripts = phpPackage.getScripts();

		assertEquals(PRE_INSTALL_CMD, getCmd(scripts, "pre-install-cmd", 0));
		assertNotNull(scripts.get("post-install-cmd"));
		assertEquals(2, scripts.get("post-install-cmd").size());
		assertEquals(POST_INSTALL_CMD, getCmd(scripts, "post-install-cmd", 0));
		assertEquals(POST_INSTALL_TEST, getCmd(scripts, "post-install-cmd", 1));
		assertEquals(PRE_UPDATE_CMD, getCmd(scripts, "pre-update-cmd", 0));
		assertEquals(POST_UPDATE_CMD, getCmd(scripts, "post-update-cmd", 0));

		assertEquals(PRE_PACKAGE_INSTALL, getCmd(scripts, "pre-package-install", 0));
		assertEquals(POST_PACKAGE_INSTALL, getCmd(scripts, "post-package-install", 0));

		assertEquals(PRE_PACKAGE_UPDATE, getCmd(scripts, "pre-package-update", 0));
		assertEquals(POST_PACKAGE_UPDATE, getCmd(scripts, "post-package-update", 0));

		assertEquals(PRE_PACKAGE_UNINSTALL, getCmd(scripts, "pre-package-uninstall", 0));
		assertEquals(POST_PACKAGE_UNINSTALL, getCmd(scripts, "post-package-uninstall", 0));
	}

	protected void doTestConfig(ComposerPackage phpPackage) {
		Config config = phpPackage.getConfig();

		assertNotNull(config);
		assertEquals(VENDOR_DIR, config.getVendorDir());
		assertEquals(BIN_DIR, config.getBinDir());
		assertEquals(PROCESS_TIMEOUT, (int) config.getProcessTimeout());
		assertEquals(GITHUB_PROTOCOLS.length, config.getGithubProtocols().size());
		assertEquals(NOTIFY_ON_INSTALL, config.getNotifyOnInstall());
	}

	protected void doTestRepositories(ComposerPackage phpPackage) {
		Repositories repos = phpPackage.getRepositories();

		assertNotNull(repos);
		assertTrue(repos.get(0) instanceof ComposerRepository);
		assertTrue(repos.get(1) instanceof SubversionRepository);
		assertTrue(repos.get(2) instanceof PearRepository);
		assertTrue(repos.get(3) instanceof PackageRepository);
		assertTrue(repos.get(4) instanceof VcsRepository);

		// composer repository
		ComposerRepository composer = (ComposerRepository) repos.get(0);
		assertEquals(COMPOSER_URL, composer.getUrl());
		assertTrue(composer.getOptions() instanceof JsonObject);

		JsonObject options = composer.getOptions();
		assertTrue(options.has("ssl"));
		assertTrue(options.isObject("ssl"));

		JsonObject ssl = options.getAsObject("ssl");
		assertTrue(ssl.has("verify_peer"));
		assertTrue(ssl.getAsBoolean("verify_peer"));

		// subversion repository
		SubversionRepository subversion = (SubversionRepository) repos.get(1);
		assertEquals(SVN_URL, subversion.getUrl());
		assertEquals(TRUNK, subversion.getTrunkPath());
		assertEquals(BRANCHES, subversion.getBranchesPath());
		assertEquals(TAGS, subversion.getTagsPath());

		// pear repository
		PearRepository pear = (PearRepository) repos.get(2);
		assertEquals(PEAR_URL, pear.getUrl());
		assertEquals(PEAR_ALIAS, pear.getVendorAlias());

		// package repository
		PackageRepository pkgRepo = (PackageRepository) repos.get(3);
		assertNotNull(pkgRepo);

		RepositoryPackage pkg = pkgRepo.getPackage();
		assertNotNull(pkg);

		assertEquals(SMARTY, pkg.getName());
		assertEquals(SMARTY_VERSION, pkg.getVersion());

		assertNotNull(pkg.getDist());
		assertTrue(pkg.getDist() instanceof Distribution);
		assertEquals(SMARTY_ZIP, pkg.getDist().getUrl());
		assertEquals(SMARTY_DIST, pkg.getDist().getType());

		assertNotNull(pkg.getSource());
		assertTrue(pkg.getSource() instanceof Source);
		assertEquals(SMARTY_SVN, pkg.getSource().getUrl());
		assertEquals(SMARTY_SOURCE, pkg.getSource().getType());
		assertEquals(SMARTY_REFERENCE, pkg.getSource().getReference());
	}
}
