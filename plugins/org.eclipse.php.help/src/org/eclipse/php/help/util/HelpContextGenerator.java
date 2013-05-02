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
/**
 * 
 */
package org.eclipse.php.help.util;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.help.IToc;
import org.eclipse.help.ITocContribution;
import org.eclipse.help.ITopic;
import org.eclipse.help.IUAElement;
import org.eclipse.help.internal.Topic;
import org.eclipse.help.internal.toc.TocFileProvider;

/**
 * Generate the Help context. Run this class as a JUnit4 Plug-in Test (in a
 * head-less mode) The class will generate a helpContexts.xml file and a help
 * context Java interface class to be placed in the appropriate plugins (e.g.
 * org.eclipse.php.help and org.eclipse.php.ui).
 * 
 * @author Shalom
 */

public class HelpContextGenerator {

	private static final String NEW_LINE = System.getProperty("line.separator"); //$NON-NLS-1$
	private static final String INVALID_LABEL_CHARS = "[^a-zA-Z$\\d]"; //$NON-NLS-1$
	private static final String INVALID_CHAR_REPLACEMENT = "_"; //$NON-NLS-1$
	private static final String PHP_CONTRIBUTOR_ID = "org.eclipse.php.help"; //$NON-NLS-1$
	private static final String JAVA_HELP_CONTEXT_TEMPLATE = "template.txt"; // This file should be located next to this class //$NON-NLS-1$
	private static final String JAVA_HELP_CONTEXT_NAME = "IPHPHelpContextIds.java"; // This will be generated next to this class //$NON-NLS-1$
	private static final String HELP_CONTEXT_FILE = "helpContexts.xml"; //$NON-NLS-1$
	private static final String DESCRIPTION_POSTFIX = " Help"; //$NON-NLS-1$
	private static final String CONTEXT_BLOCK = "\t<context id=\"%1$s\">" + NEW_LINE + "\t\t<description>%2$s</description>" + NEW_LINE + "\t\t<topic href=\"%3$s\" label=\"%4$s\"/>" + NEW_LINE + "\t</context>" + NEW_LINE; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	private static final String JAVA_CONSTANT_LINE = "public static final String %1$s = PREFIX + \"%2$s\"; [%3$s]" + NEW_LINE; //$NON-NLS-1$

	private LinkedHashSet<String> topics = new LinkedHashSet<String>();

	/*
	 * @Test
	 */
	public void run() {
		TocFileProvider tocProvider = new TocFileProvider();
		ITocContribution[] tocContributions = tocProvider
				.getTocContributions(null);
		ArrayList<ITocContribution> phpTocs = new ArrayList<ITocContribution>();
		for (ITocContribution contribution : tocContributions) {
			// Take only the non-primary TOC
			if (!contribution.isPrimary()
					&& PHP_CONTRIBUTOR_ID.equals(contribution
							.getContributorId())) {
				phpTocs.add(contribution);
			}
		}
		try {
			generateFiles(phpTocs);
		} catch (Exception e) {
			// Assert.assertFalse(true);
		}
	}

	/**
	 * Generate the files.
	 * 
	 * @param phpTocs
	 * @throws Exception
	 */
	private void generateFiles(ArrayList<ITocContribution> phpTocs)
			throws Exception {
		// Prepare the files
		File javaTemplateFile = getJavaTemplateFile();
		File helpContextFile = new File(javaTemplateFile.getParent(),
				HELP_CONTEXT_FILE);
		File newJavaFile = new File(javaTemplateFile.getParent(),
				JAVA_HELP_CONTEXT_NAME);
		StringBuilder helpContextBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NEW_LINE + "<!-- Auto generated using the HelpContextGenerator -->" + NEW_LINE + "<contexts>" + NEW_LINE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		StringBuilder newJavaFileBuilder = new StringBuilder();
		loadJavaTemplate(newJavaFileBuilder, javaTemplateFile);

		// Generate the code
		for (ITocContribution tocContribution : phpTocs) {
			IToc toc = tocContribution.getToc();
			IUAElement[] children = toc.getChildren();
			writeElements(children, helpContextBuilder, newJavaFileBuilder, 1);
		}

		newJavaFileBuilder.append('}');
		helpContextBuilder.append("</contexts>"); //$NON-NLS-1$
		// Write the generated files
		writeFile(helpContextFile, helpContextBuilder.toString());
		writeFile(newJavaFile, newJavaFileBuilder.toString());
	}

	/*
	 * Recursive elements writer that writes the elements into the string
	 * builders.
	 * 
	 * @param children
	 * 
	 * @param helpContextBuilder
	 * 
	 * @param newJavaFileBuilder
	 */
	private void writeElements(IUAElement[] children,
			StringBuilder helpContextBuilder, StringBuilder newJavaFileBuilder,
			int tocLevel) {
		for (IUAElement child : children) {
			if (child instanceof ITopic) {
				ITopic topic = ((ITopic) child);
				String href = topic.getHref();
				String label = topic.getLabel();
				// Remove any special markings from the label
				String labelAsKey = cleanLabel(label).toLowerCase();
				String labelAsModifier = labelAsKey;
				if (Character.isDigit(labelAsModifier.charAt(0))) {
					labelAsModifier = '_' + labelAsModifier;
				}
				if (topics.contains(labelAsModifier)) {
					// append the parent topic name to this label
					ITopic parent = (ITopic) ((Topic) topic).getParentElement();
					String parentLabel = cleanLabel(parent.getLabel())
							.toLowerCase();
					if (Character.isDigit(parentLabel.charAt(0))) {
						parentLabel = '_' + parentLabel;
					}
					labelAsModifier = parentLabel + labelAsModifier;
				}
				topics.add(labelAsModifier);
				for (int i = 0; i < tocLevel; i++) {
					newJavaFileBuilder.append('\t');
				}
				newJavaFileBuilder.append(String.format(JAVA_CONSTANT_LINE,
						labelAsModifier.toUpperCase(), labelAsKey, href));
				helpContextBuilder.append(String.format(CONTEXT_BLOCK,
						labelAsKey, label + DESCRIPTION_POSTFIX, href, label)); // id,
																				// description,
																				// href,
																				// label
				ITopic[] subtopics = topic.getSubtopics();
				if (subtopics != null && subtopics.length > 0) {
					writeElements(subtopics, helpContextBuilder,
							newJavaFileBuilder, tocLevel + 1);
				}
			}
		}
	}

	/*
	 * Remove any non-letter, $ or digit from the name.
	 */
	private String cleanLabel(String labelAsKey) {
		return labelAsKey.replaceAll(INVALID_LABEL_CHARS,
				INVALID_CHAR_REPLACEMENT);
	}

	/*
	 * Reads the java file template contents into the string builder.
	 * 
	 * @param newJavaFileBuilder
	 * 
	 * @param javaTemplateFile
	 * 
	 * @throws IOException
	 */
	private void loadJavaTemplate(StringBuilder newJavaFileBuilder,
			File javaTemplateFile) throws IOException {
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader(
				javaTemplateFile));
		while ((line = reader.readLine()) != null) {
			newJavaFileBuilder.append(line);
			newJavaFileBuilder.append(NEW_LINE);
		}
	}

	/*
	 * Write the file content.
	 * 
	 * @param file
	 * 
	 * @param toWrite
	 * 
	 * @throws IOException
	 */
	private void writeFile(File file, String toWrite) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		try {
			writer.write(toWrite);
			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/*
	 * Returns the Java template file.
	 * 
	 * @return The Java template file.
	 * 
	 * @throws IOException
	 */
	private File getJavaTemplateFile() throws IOException {
		URL url = getClass().getResource(JAVA_HELP_CONTEXT_TEMPLATE);
		// try the naive search
		File javaTemplateFile = new File(URLDecoder.decode(url.getFile(),
				"UTF-8")); //$NON-NLS-1$
		if (!javaTemplateFile.exists()) {
			// try to locate file in more deep search methods
			javaTemplateFile = new File(URLDecoder.decode(FileLocator
					.toFileURL(url).getFile(), "UTF-8")); //$NON-NLS-1$
		}
		String srcFilePath = javaTemplateFile
				.toString()
				.replace(
						File.separatorChar + "bin" + File.separatorChar, File.separatorChar + "src" + File.separatorChar); //$NON-NLS-1$ //$NON-NLS-2$
		javaTemplateFile = new File(srcFilePath);
		//		Assert.assertTrue("Could not locate the Java template file", javaTemplateFile.exists()); 
		return javaTemplateFile;
	}
}
