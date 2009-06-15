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
import java.io.InputStreamReader;
import java.util.ArrayList;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.core.tests.model.AbstractSingleProjectSearchTests;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.search.SearchTests;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * Description: This class tests {@link PHPStructuredTextPartitioner}
 * Each method checks a different situation in which a PHP partition can appear. Certain markers 
 * contained in the PHP markers are looked for each time. 
 * 
 * @author Alon Peled
 */
public class PHPPartitionerTests extends AbstractSingleProjectSearchTests {
	
	private static final String PROJECT_NAME = "partitioner";
	
	public PHPPartitionerTests(String name) {
		super(PHPCoreTests.PLUGIN_ID, name, PROJECT_NAME);
	}
	
	public static Suite suite() {
		return new Suite(SearchTests.class);
	}
	
	// Stores length of system line separator to compute the offset within the file
	private static final int endLine = System.getProperty("line.separator").length();

	// The markers looked for in the PHP partition
	private static final String[] phpLookUp = { "php", "echo", "PHP_Single_Comment", "PHP_Multi_Comment", "PHP_Doc", "Test quoted string partition", "1 F d, Y", "Running test" };

	/**
	 * Test PHP partition inside HTML
	 * @throws Exception
	 */
	
	public void testPartitionInHTML() throws Exception {
		ArrayList<String> matches = getPartitionType(phpLookUp, "phpPartitionerTestHTML.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT, (String) matches.get(i));
		}
	}

	/**
	 * Test PHP partition in a PHP-only file
	 * @throws Exception
	 */
	
	public void testPartitionStandalone() throws Exception {

		ArrayList<String> matches = getPartitionType(phpLookUp, "phpPartitionerTestPhp.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT, (String) matches.get(i));
		}
	}

	/**
	 * Test PHP partition in HTML when PHP is an HTML attribute key
	 * @throws Exception
	 */
	
	public void testPartitionPhpAsHTMLAttributeKey() throws Exception {

		ArrayList<String> matches = getPartitionType(phpLookUp, "phpPartitionerTestPhpAsHTMLAttributeKey.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT, (String) matches.get(i));
		}
	}

	/**
	 * Test PHP partition in HTML when PHP is an HTML attribute value
	 * @throws Exception
	 */
	
	public void testPartitionPhpAsHTMLAttributeValue() throws Exception {

		ArrayList<String> matches = getPartitionType(phpLookUp, "phpPartitionerTestPhpAsHTMLAttributeValue.php");
		for (int i = 0; i < matches.size(); i++) {
			Assert.assertEquals(PHPPartitionTypes.PHP_DEFAULT, (String) matches.get(i));
		}
	}

	/**
	 * This method invokes the partitioner, and returns the partition type of each marker
	 * @param markers Strings to be looked for in the file, which have the same partiotion type
	 * @param preferOpenPartitions
	 * @return a Vector of partition types, all are expected to be with the same type
	 * @throws Exception
	 */
	private ArrayList<String> getPartitionType(String[] markers, String testDataFile) throws Exception {
		// offset from beginning of stream
		int offset = 0;

		ArrayList<String> results = new ArrayList<String>();

		// init files
		IFile inFile = getProject(PROJECT_NAME).getFile(testDataFile);

		// open streams
		InputStreamReader inStream = new InputStreamReader(inFile.getContents());
		BufferedReader reader = new BufferedReader(inStream);

		final IStructuredModel modelForEdit = StructuredModelManager.getModelManager().getModelForEdit(inFile);
		try {
			final IStructuredDocument structuredDocument = modelForEdit.getStructuredDocument();

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
						ITypedRegion partition = structuredTextPartitioner.getPartition(offset + lineOffset);
						results.add(partition.getType());
					}
				}
				// update global offset
				offset += curLine.length() + endLine;
				curLine = reader.readLine();
			}
		} finally {
			if (modelForEdit != null) {
				modelForEdit.releaseFromEdit();
			}
		}
		return results;
	}
}