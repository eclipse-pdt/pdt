/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.phpElementData;

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.phpModel.parser.php4.CompletionLexer4;
import org.eclipse.php.internal.core.phpModel.parser.php4.PHP4DefaultParserClient;
import org.eclipse.php.internal.core.phpModel.parser.php4.PhpParser4;
import org.eclipse.php.internal.core.phpModel.parser.php5.CompletionLexer5;
import org.eclipse.php.internal.core.phpModel.parser.php5.PHP5DefaultParserClient;
import org.eclipse.php.internal.core.phpModel.parser.php5.PhpParser5;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.core.preferences.TaskPatternsProvider;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;
import org.eclipse.php.internal.core.project.properties.handlers.UseAspTagsHandler;

public final class PHPFileDataUtilities {

	private PHPFileDataUtilities() {
	}

	/**
	 * return the code data (class or function) that wrap the position.
	 *
	 * @param fileData The file data to search in.
	 * @param position The position in the document.
	 * @return return the code data (class or function) that wrap the position or null.
	 */
	public static CodeData getCodeData(PHPFileData fileData, int position) {
		if (position < 0 || fileData == null) {
			return null;
		}
		// check if the position is in one of the classes.
		PHPClassData[] classes = fileData.getClasses();
		PHPClassData classRv = null;
		PHPFunctionData functionRv = null;
		for (int i = 0; i < classes.length; i++) {
			if (contains(classes[i].getUserData(), position)) {
				if (classRv == null) {
					classRv = classes[i];
				} else {
					// if the old class we found includes the new one then the
					// new one is more relevant
					if (contains(classRv.getUserData(), classes[i].getUserData().getStartPosition())) {
						classRv = classes[i];
						functionRv = null;
					} else {
						continue;
					}
				}
				// the position is in the class check if it is in one of its methods
				PHPFunctionData[] functions = classes[i].getFunctions();
				for (int j = 0; j < functions.length; j++) {
					if (contains(functions[j].getUserData(), position)) {
						if (functionRv == null) {
							functionRv = functions[j];
						} else {
							// if the old function we found includes the new one
							// then the new one is more relevant
							if (contains(functionRv.getUserData(), functions[j].getUserData().getStartPosition())) {
								functionRv = functions[j];
							}
						}
					}
				}
			}
		}

		// check if the position is in one of the functions.
		PHPFunctionData[] functions = fileData.getFunctions();
		for (int j = 0; j < functions.length; j++) {
			if (contains(functions[j].getUserData(), position)) {
				if (functionRv == null) {
					functionRv = functions[j];
				} else {
					// if the old function we found includes the new one then
					// the new one is more relevant
					if (contains(functionRv.getUserData(), functions[j].getUserData().getStartPosition())) {
						functionRv = functions[j];
					}
				}
			}
		}
		if (functionRv != null) {
			return functionRv;
		}
		if (classRv != null) {
			return classRv;
		}
		return null;
	}

	public static PHPClassData getContainerClassDada(PHPFileData fileData, int position) {
		if (position < 0 || fileData == null) {
			return null;
		}
		PHPClassData[] classes = fileData.getClasses();
		for (int i = 0; i < classes.length; i++) {
			if (contains(classes[i].getUserData(), position)) {
				return classes[i];
			}
		}
		return null;
	}

	private static boolean contains(UserData userData, int position) {
		return position > userData.getStartPosition() && position <= userData.getEndPosition();
	}

	/**
	 * Returns the position of the nearest php open tag position.
	 */
	public static int getPHPStart(int offset, PHPFileData fileData) {
		PHPBlock[] phpBlocks = fileData.getPHPBlocks();
		for (int i = phpBlocks.length - 1; i >= 0; i--) {
			if (phpBlocks[i].getPHPStartTag().getStartPosition() < offset) {
				return phpBlocks[i].getPHPStartTag().getStartPosition();
			}
		}
		return 0;
	}

	public final static String getVariableType(String fileName, String variableName, int position, int line, IPhpModel model, boolean showObjectsFromOtherFiles) {
		final PHPFileData fileData = model.getFileData(fileName);
		
		// if it is the first time (that we build the model) there is no such model yet...
		if (fileData == null) {
			return null;
		}
		
		return getVariableType(fileData, variableName, position, line, model, showObjectsFromOtherFiles);
	}

	public static String getVariableType(PHPFileData fileData, String variableName, int position, int line, IPhpModel model, boolean showObjectsFromOtherFiles) {
		String className;
		if ("$this".equals(variableName)) {
			PHPClassData classData = getContainerClassDada(fileData, position);
			if (classData != null) {
				className = classData.getName();
			} else {
				className = ""; // we are not inside a class.
			}
		} else {
			int currentLine = line + 1;
			PHPCodeContext context = ModelSupport.createContext(fileData, position);
			className = model.getVariableType(fileData.getName(), context, variableName, currentLine, showObjectsFromOtherFiles);
		}
		return className;
	}

	private static final IPreferenceStore store = PHPCorePlugin.getDefault().getPreferenceStore();
	private static final PreferencesSupport preferencesSupport = new PreferencesSupport(PHPCorePlugin.ID, store);
	
	public static PHPFileData getFileData(Reader reader) {
		Pattern[] tasksPatterns = TaskPatternsProvider.getInstance().getPetternsForWorkspace();
		IProject project = null;
		boolean useAspTags = UseAspTagsHandler.useAspTagsAsPhp(project);
		String phpVersion = preferencesSupport.getPreferencesValue(Keys.PHP_VERSION, PHPVersion.PHP5, project);

		return getFileData(reader, "tmp", 0, phpVersion, tasksPatterns, useAspTags);
	}

	public static PHPFileData getFileData(Reader reader, String fileName, long lastModified, String phpVersion, Pattern[] tasksPatterns, boolean useAspTagsAsPhp) {
		PhpParser phpParser = null;
		ParserClient client = null;

		PHPUserModel model = new PHPUserModel();
		try {
			CompletionLexer lexer = null;
			if (phpVersion == PHPVersion.PHP5) {
				lexer = new CompletionLexer5(reader);
				phpParser = new PhpParser5();
				client = new PHP5DefaultParserClient(model, null);
			} else {
				lexer = new CompletionLexer4(reader);
				phpParser = new PhpParser4();
				client = new PHP4DefaultParserClient(model, null);
			}
			lexer.setUseAspTagsAsPhp(useAspTagsAsPhp);
			lexer.setParserClient(client);
			lexer.setTasksPatterns(tasksPatterns);

			phpParser.setScanner(lexer);
			phpParser.setParserClient(client);

			client.startParsing(fileName);

			phpParser.parse();

		} catch (Exception e) {
			Logger.logException(e);

		} finally {

			try {
				if (client != null && phpParser != null) {
					client.finishParsing(phpParser.getLength(), phpParser.getCurrentLine(), lastModified);
				}

			} catch (Exception ex) {
				Logger.logException(ex);

			} finally {
				try {
					reader.close();
				} catch (IOException exception) {
					Logger.logException(exception);
				}
			}
		}
		;
		PHPFileData fileData = model.getFileData(fileName);
		return fileData;
	}

}