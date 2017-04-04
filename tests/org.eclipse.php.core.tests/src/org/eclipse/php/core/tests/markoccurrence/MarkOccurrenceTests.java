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
package org.eclipse.php.core.tests.markoccurrence;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.TestUtils;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.AfterList;
import org.eclipse.php.core.tests.runner.PDTTList.BeforeList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.core.search.IOccurrencesFinder;
import org.eclipse.php.internal.core.search.IOccurrencesFinder.OccurrenceLocation;
import org.eclipse.php.internal.core.search.OccurrencesFinderFactory;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class MarkOccurrenceTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	protected static final char OFFSET_CHAR = '|';

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<PHPVersion, String[]>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/markoccurrence/php5" });
		TESTS.put(PHPVersion.PHP5_3,
				new String[] { "/workspace/markoccurrence/php5", "/workspace/markoccurrence/php53" });
		TESTS.put(PHPVersion.PHP5_4, new String[] { "/workspace/markoccurrence/php5", "/workspace/markoccurrence/php53",
				"/workspace/markoccurrence/php54" });
		TESTS.put(PHPVersion.PHP5_5, new String[] { "/workspace/markoccurrence/php5", "/workspace/markoccurrence/php53",
				"/workspace/markoccurrence/php54", "/workspace/markoccurrence/php55" });
		TESTS.put(PHPVersion.PHP5_6, new String[] { "/workspace/markoccurrence/php5", "/workspace/markoccurrence/php53",
				"/workspace/markoccurrence/php54", "/workspace/markoccurrence/php56" });
		TESTS.put(PHPVersion.PHP7_0, new String[] { "/workspace/markoccurrence/php5", "/workspace/markoccurrence/php53",
				"/workspace/markoccurrence/php54", "/workspace/markoccurrence/php56" });
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/markoccurrence/php5", "/workspace/markoccurrence/php53",
						"/workspace/markoccurrence/php54", "/workspace/markoccurrence/php56",
						"/workspace/markoccurrence/php71" });
	};

	protected IProject project;
	protected IFile testFile;
	protected final PHPVersion phpVersion;

	@BeforeList
	public void setUpSuite() throws Exception {
		project = TestUtils.createProject("MarkOccurrenceTests_" + phpVersion.toString());
		TestUtils.setProjectPhpVersion(project, phpVersion);
	}

	@AfterList
	public void tearDownSuite() throws Exception {
		TestUtils.deleteProject(project);
	}

	@Test
	public void occurrences(String fileName) throws Exception {
		final PdttFile pdttFile = new PdttFile(fileName);
		pdttFile.applyPreferences();
		compareProposals(pdttFile.getFile());
	}

	@After
	public void after() throws CoreException {
		if (testFile != null) {
			testFile.delete(true, null);
			testFile = null;
		}
	}

	public MarkOccurrenceTests(PHPVersion version, String[] fileNames) {
		phpVersion = version;
	}

	/**
	 * Creates test file with the specified content and calculates the offset at
	 * OFFSET_CHAR. Offset character itself is stripped off.
	 * 
	 * @param data
	 *            File data
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected void compareProposals(String data) throws Exception {

		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}
		List<Integer> starts = new ArrayList<Integer>();
		int startIndex = -1;
		while ((startIndex = data.indexOf('%', startIndex + 1)) >= 0) {
			starts.add(startIndex);
		}
		if (starts.size() % 2 != 0) {
			throw new IllegalArgumentException("% must be paired");
		}
		List<Integer> newStarts = new ArrayList<Integer>();
		for (int i = 0; i < starts.size(); i++) {
			int oldstart = starts.get(i) - i;
			if (starts.get(i) > offset) {
				oldstart--;
			}
			newStarts.add(oldstart);
		}
		// replace the offset character
		data = data.replaceAll("%", "");

		offset = data.lastIndexOf(OFFSET_CHAR);
		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);
		testFile = TestUtils.createFile(project, "test.php", data);
		TestUtils.waitForIndexer();
		Program astRoot = createProgramFromSource(testFile);
		ASTNode selectedNode = NodeFinder.perform(astRoot, offset, 0);
		OccurrenceLocation[] locations = null;
		if (selectedNode != null && (selectedNode instanceof Identifier || (isScalarButNotInString(selectedNode)))) {
			int type = PhpElementConciliator.concile(selectedNode);
			if (markOccurrencesOfType(type)) {
				IOccurrencesFinder finder = OccurrencesFinderFactory.getOccurrencesFinder(type);
				if (finder != null) {
					if (finder.initialize(astRoot, selectedNode) == null) {
						locations = finder.getOccurrences();
					}
				}
			}
		}
		compareProposals(locations, newStarts);
		// return locations;
	}

	/**
	 * Returns is the occurrences of the type should be marked.
	 * 
	 * @param type
	 *            One of the {@link PhpElementConciliator} constants integer
	 *            type.
	 * @return True, if the type occurrences should be marked; False, otherwise.
	 */
	public static boolean markOccurrencesOfType(int type) {
		switch (type) {
		case PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE:
		case PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE:
		case PhpElementConciliator.CONCILIATOR_FUNCTION:
		case PhpElementConciliator.CONCILIATOR_CLASSNAME:
		case PhpElementConciliator.CONCILIATOR_CONSTANT:
		case PhpElementConciliator.CONCILIATOR_CLASS_MEMBER:
			return true;
		case PhpElementConciliator.CONCILIATOR_UNKNOWN:
		case PhpElementConciliator.CONCILIATOR_PROGRAM:
		default:
			return false;
		}
	}

	/**
	 * Checks whether or not the node is a scalar and return true only if the
	 * scalar is not part of a string
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isScalarButNotInString(ASTNode node) {
		return (node.getType() == ASTNode.SCALAR) && (node.getParent().getType() != ASTNode.QUOTE);
	}

	public Program createProgramFromSource(IFile file) throws Exception {
		ISourceModule source = DLTKCore.createSourceModuleFrom(file);
		return createProgramFromSource(source);
	}

	public Program createProgramFromSource(ISourceModule source) throws Exception {
		PHPVersion version;
		if (project != null) {
			version = ProjectOptions.getPHPVersion(project);
		} else {
			version = ProjectOptions.getDefaultPHPVersion();
		}
		ASTParser newParser = ASTParser.newParser(version, (ISourceModule) source);
		return newParser.createAST(null);
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	public static void compareProposals(OccurrenceLocation[] proposals, List<Integer> starts) throws Exception {
		// String[] lines = pdttFile.getExpected().split("\n");
		proposals = olderLocations(proposals);
		boolean proposalsEqual = true;
		if (proposals == null && starts.size() == 0) {
			// proposalsEqual = true;
		} else if (proposals != null && proposals.length == starts.size() / 2) {
			for (int i = 0; i < proposals.length; i++) {
				if (!(proposals[i].getOffset() == starts.get(i * 2)
						&& (proposals[i].getOffset() + proposals[i].getLength()) == starts.get(i * 2 + 1))) {
					proposalsEqual = false;
					break;
				}
			}
		} else {
			proposalsEqual = false;
		}

		if (!proposalsEqual) {
			StringBuilder errorBuf = new StringBuilder();
			errorBuf.append("\nEXPECTED COMPLETIONS LIST:\n-----------------------------\n");
			for (int i = 0; i < starts.size() / 2; i++) {
				errorBuf.append('[').append(starts.get(i * 2)).append(',')
						.append(starts.get(i * 2 + 1) - starts.get(i * 2)).append(']').append("\n");
			}
			errorBuf.append("\nACTUAL COMPLETIONS LIST:\n-----------------------------\n");
			for (OccurrenceLocation p : proposals) {
				errorBuf.append('[').append(p.getOffset()).append(',').append(p.getLength()).append(']').append("\n");
			}
			fail(errorBuf.toString());
		}
	}

	private static OccurrenceLocation[] olderLocations(OccurrenceLocation[] proposals) {
		if (proposals == null) {
			return new OccurrenceLocation[0];
		}
		List<OccurrenceLocation> result = new ArrayList<OccurrenceLocation>();

		for (int i = 0; i < proposals.length; i++) {
			result.add(proposals[i]);
		}
		Collections.sort(result, new Comparator<OccurrenceLocation>() {

			public int compare(OccurrenceLocation o1, OccurrenceLocation o2) {

				return o1.getOffset() - o2.getOffset();
			}
		});
		return result.toArray(new OccurrenceLocation[result.size()]);
	}
}
