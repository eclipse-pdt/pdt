/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.document.partitioner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * Description: This class tests {@link PHPStructuredTextPartitioner} Each
 * method checks a different situation in which a PHP partition can appear.
 * Certain markers contained in the PHP markers are looked for each time.
 * 
 * @author Alon Peled
 */
public class PHPPartitionerTests extends TestCase {

	private static final String PROJECT_NAME = "partitioner";

	public PHPPartitionerTests(String name) {
		super(name);
	}

	//
	// public static Test suite() {
	//
	// TestSuite suite = new TestSuite("Auto Code Assist Tests");
	// return new Suite(PHPPartitionerTests.class);
	// }

	// Stores length of system line separator to compute the offset within the
	// file
	private static final int endLine = System.getProperty("line.separator")
			.length();

	// The markers looked for in the PHP partition
	private static final String[] phpLookUp = { "php", "echo",
			"PHP_Single_Comment", "PHP_Multi_Comment", "PHP_Doc",
			"Test quoted string partition", "1 F d, Y", "Running test" };

	protected static IProject project;
	protected static IFile testFile;

	public File getSourceWorkspacePath() {
		return new File(getPluginDirectoryPath(), "workspace");
	}

	/**
	 * Returns the OS path to the directory that contains this plugin.
	 */
	protected File getPluginDirectoryPath() {
		try {
			URL platformURL = Platform.getBundle(PHPCoreTests.PLUGIN_ID)
					.getEntry("/");
			return new File(FileLocator.toFileURL(platformURL).getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the IWorkspace this test suite is running on.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static IWorkspaceRoot getWorkspaceRoot() {
		return getWorkspace().getRoot();
	}

	/**
	 * Copy the given source directory (and all its contents) to the given
	 * target directory.
	 */
	protected void copyDirectory(File source, File target) throws IOException {
		if (!target.exists()) {
			target.mkdirs();
		}
		File[] files = source.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			File sourceChild = files[i];
			String name = sourceChild.getName();
			if (name.equals("CVS") || name.equals(".svn"))
				continue;
			File targetChild = new File(target, name);
			if (sourceChild.isDirectory()) {
				copyDirectory(sourceChild, targetChild);
			} else {
				copy(sourceChild, targetChild);
			}
		}
	}

	/**
	 * Copy file from src (path to the original file) to dest (path to the
	 * destination file).
	 */
	public static void copy(File src, File dest) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		byte[] buffer = new byte[12 * 1024];
		int read;

		try {
			in = new FileInputStream(src);

			try {
				out = new FileOutputStream(dest);

				while ((read = in.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	protected void setUp() throws Exception {
		// copy files in project from source workspace to target workspace
		final File sourceWorkspacePath = getSourceWorkspacePath();
		final File targetWorkspacePath = getWorkspaceRoot().getLocation()
				.toFile();

		copyDirectory(new File(sourceWorkspacePath, PROJECT_NAME), new File(
				targetWorkspacePath, PROJECT_NAME));

		project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJECT_NAME);
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
	}

	protected void tearDown() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	/**
	 * Test PHP partition inside HTML
	 * 
	 * @throws Exception
	 */

	public void testPartitionInHTML() throws Exception {
		ArrayList<String> matches = getPartitionType(phpLookUp,
				"phpPartitionerTestHTML.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT,
					(String) matches.get(i));
		}
	}

	/**
	 * Test PHP partition in a PHP-only file
	 * 
	 * @throws Exception
	 */

	public void testPartitionStandalone() throws Exception {

		ArrayList<String> matches = getPartitionType(phpLookUp,
				"phpPartitionerTestPhp.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT,
					(String) matches.get(i));
		}
	}

	/**
	 * Test PHP partition in HTML when PHP is an HTML attribute key
	 * 
	 * @throws Exception
	 */

	public void testPartitionPhpAsHTMLAttributeKey() throws Exception {

		ArrayList<String> matches = getPartitionType(phpLookUp,
				"phpPartitionerTestPhpAsHTMLAttributeKey.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT,
					(String) matches.get(i));
		}
	}

	/**
	 * Test PHP partition in HTML when PHP is an HTML attribute value
	 * 
	 * @throws Exception
	 */

	public void testPartitionPhpAsHTMLAttributeValue() throws Exception {

		ArrayList<String> matches = getPartitionType(phpLookUp,
				"phpPartitionerTestPhpAsHTMLAttributeValue.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT,
					(String) matches.get(i));
		}
	}

	/**
	 * This method invokes the partitioner, and returns the partition type of
	 * each marker
	 * 
	 * @param markers
	 *            Strings to be looked for in the file, which have the same
	 *            partiotion type
	 * @param preferOpenPartitions
	 * @return a Vector of partition types, all are expected to be with the same
	 *         type
	 * @throws Exception
	 */
	private ArrayList<String> getPartitionType(String[] markers,
			String testDataFile) throws Exception {
		// offset from beginning of stream
		int offset = 0;

		ArrayList<String> results = new ArrayList<String>();

		// init files
		IFile inFile = project.getFile(testDataFile);

		// open streams
		InputStreamReader inStream = new InputStreamReader(inFile.getContents());
		BufferedReader reader = new BufferedReader(inStream);

		final IStructuredModel modelForEdit = StructuredModelManager
				.getModelManager().getModelForEdit(inFile);
		try {
			final IStructuredDocument structuredDocument = modelForEdit
					.getStructuredDocument();

			// create the partitioner
			final PHPStructuredTextPartitioner structuredTextPartitioner = new PHPStructuredTextPartitioner();
			structuredTextPartitioner.connect(structuredDocument);

			// go over the file, one line at a time, and search for the markers
			String curLine = reader.readLine();
			while (curLine != null) {
				for (int i = 0; i < markers.length; i++) {
					int lineOffset = curLine.indexOf(markers[i]);
					// if marker was found in current line, get partition type
					if (lineOffset != -1) {
						ITypedRegion partition = structuredTextPartitioner
								.getPartition(offset + lineOffset);
						results.add(partition.getType());
					}
				}
				// update global offset
				offset += curLine.length() + endLine;
				curLine = reader.readLine();
			}
		} finally {
			reader.close();

			if (modelForEdit != null) {
				modelForEdit.releaseFromEdit();
			}
		}
		return results;
	}
}