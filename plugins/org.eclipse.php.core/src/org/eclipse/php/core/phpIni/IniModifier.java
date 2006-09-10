/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.core.phpIni;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.ini4j.Ini;
import org.ini4j.IniParser;

/**
 * @author seva
 *
 */
public class IniModifier {
	static final String PARAM_INCLUDE_PATH = "include_path";
	static final String PATH_SEPARATOR = System.getProperty("os.name").toLowerCase().startsWith("windows") ? ";" : ":";
	static final String PHP_SECTION_NAME = "PHP";

	private static String getParameter(Ini parameters, String sectionName, String parameterName) {
		Ini.Section section = (Ini.Section) parameters.get(sectionName);
		if (section != null) {
			return (String) section.get(parameterName);
		}
		return null;
	}

	private static void setParameter(Ini parameters, String sectionName, String parameterName, String value) {
		((Ini.Section) parameters.get(sectionName)).put(parameterName, value);
	}

	public static File addIncludePath(final File phpIni, final IPath[] includePaths) {
		if (includePaths.length < 0) {
			return phpIni;
		}
		try {
			final File tempFile = createTempFile();
			if (tempFile == null)
				return null;
			final Ini parameters = new Ini();
			parameters.load(new FileInputStream(phpIni), Ini.IGNORE_ESCAPE);
			String sectionName = PHP_SECTION_NAME;
			String includePath = getParameter(parameters, PHP_SECTION_NAME, PARAM_INCLUDE_PATH);
			if (includePath == null) {
				includePath = getParameter(parameters, sectionName = IniParser.DEFAULT_SECTION_NAME, PARAM_INCLUDE_PATH);
			}
			final StringBuffer includePathBuffer;

			if (includePath != null) {
				includePathBuffer = new StringBuffer(includePath.replaceAll("\"", ""));
				includePathBuffer.append(PATH_SEPARATOR);
			} else
				includePathBuffer = new StringBuffer();

			for (int i = 0; i < includePaths.length; ++i)
				includePathBuffer.append(includePaths[i].toOSString()).append(PATH_SEPARATOR);
			includePathBuffer.insert(0, "\"");
			includePathBuffer.append("\"");
			setParameter(parameters, sectionName, PARAM_INCLUDE_PATH, includePathBuffer.toString());
			parameters.store(new FileOutputStream(tempFile), Ini.IGNORE_ESCAPE);
			return tempFile;
		} catch (final IOException e) {
		}
		return null;
	}

	public static File addIncludePath(final File phpIni, final IProject project) {
		return addIncludePath(phpIni, projectGetIncludePaths(project));
	}

	static File createTempFile() {
		try {
			final File tempFile = File.createTempFile("php.", ".ini");
			tempFile.deleteOnExit();
			return tempFile;
		} catch (final IOException e) {
		}
		return null;
	}

	public static File findPHPIni(final String phpExe) {
		// for now we'll use only the simpliest check - if the file is placed in the same dir.
		final IPath phpIniPath = new Path(phpExe).removeLastSegments(1).append("php.ini");
		final File phpIniFile = new File(phpIniPath.toOSString());
		if (!phpIniFile.canRead())
			return null;
		return phpIniFile;
	}

	static IPath[] projectGetIncludePaths(final IProject project) {
		final PHPProjectOptions options = PHPProjectOptions.forProject(project);
		final IIncludePathEntry[] entries = options.readRawIncludePath();
		final List paths = new /*<IPath>*/ArrayList(entries.length);
		for (int i = 0; i < entries.length; ++i) {
			final IPath path = entries[i].getPath();
			if (entries[i].getEntryKind() == IIncludePathEntry.IPE_LIBRARY) {
				if (entries[i].getContentKind() == IIncludePathEntry.K_BINARY) {
					// not implemented
				} else
					paths.add(path);
			} else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_PROJECT) {
				final IResource includeResource = entries[i].getResource();
				if (includeResource instanceof IProject)
					paths.add(includeResource.getLocation());
			} else if (entries[i].getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
				String variableName = path.toString();
				final int index = variableName.indexOf('/');
				String extension = ""; //$NON-NLS-1$
				if (index != -1) {
					if (index + 1 < variableName.length())
						extension = variableName.substring(index + 1);
					variableName = variableName.substring(0, index);
				}
				if (extension != "") {

				}
				IPath includePath = PHPProjectOptions.getIncludePathVariable(variableName);
				includePath = includePath.append(extension);
				extension = includePath.getFileExtension();
				if (extension != null && extension.equalsIgnoreCase("zip")) {
					// not implemented
				} else
					paths.add(includePath);
			}
		}
		return (IPath[]) paths.toArray(new IPath[paths.size()]);
	}

}
