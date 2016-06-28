/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.core.internal.Messages;
import org.eclipse.php.composer.core.internal.model.Package;
import org.eclipse.php.composer.core.internal.model.Repository;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;
import org.eclipse.php.composer.core.model.ComposerRoot;
import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.core.model.IRepository;
import org.eclipse.php.composer.core.model.RepositoryType;
import org.eclipse.php.composer.core.utils.ComposerProperties;
import org.eclipse.php.composer.core.utils.EnvironmentUtils;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.phpIni.PHPINIUtil;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Composer utility class. It provides ability to manage (resolve, update) and
 * search for dependencies.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
@SuppressWarnings("restriction")
public class ComposerService {

	public enum Status {
		NO_PHP_EXEC,

		NOT_COMPOSER,

		NO_COMPOSER_PHAR,

		OK,

		ERROR
	}

	public static final String COMPOSER_JSON = "composer.json"; //$NON-NLS-1$
	public static final String VENDOR = "vendor"; //$NON-NLS-1$

	public static final String COMPOSER_LOCK = "composer.lock"; //$NON-NLS-1$

	private static final String PHP_INI = "php.ini"; //$NON-NLS-1$

	private static final String GETCOMPOSER_ORG = "https://getcomposer.org/installer"; //$NON-NLS-1$
	private static final String WORKING_DIR = "-d"; //$NON-NLS-1$
	private static final String INSTALL_DIR = "--install-dir="; //$NON-NLS-1$

	private static final String URL_SPACE = "%20"; //$NON-NLS-1$

	private static final String VERSIONS_TAG = "versions"; //$NON-NLS-1$

	private static final String[] TESTS_EXCLUDE_PATHS = new String[] { "%s/**/test*/**" }; //$NON-NLS-1$

	private IContainer root;
	// private ILogDevice log;
	private Throwable error;

	private ICommandExecutor commandExecutor;

	public ComposerService(IContainer root, ICommandExecutor commandExecutor) {
		super();
		this.root = root;
		this.commandExecutor = commandExecutor;
	}

	public ComposerService(IContainer root) {
		this(root, null);
	}

	public Status resolve(IProgressMonitor monitor, String... additionalArgs) {
		List<String> args = new ArrayList<String>();
		args.add(ComposerCLICommands.INSTALL.getParameter());
		args.addAll(Arrays.asList(additionalArgs));
		args.add(ComposerCLIParams.NO_PROGRESS.getParameter());
		args.add(ComposerCLIParams.ANSI.getParameter());
		return performAction(monitor, args.toArray(new String[0]));
	}

	public Status update(IProgressMonitor monitor, String... additionalArgs) {
		List<String> args = new ArrayList<String>();
		args.add(ComposerCLICommands.UPDATE.getParameter());
		args.addAll(Arrays.asList(additionalArgs));
		args.add(ComposerCLIParams.NO_PROGRESS.getParameter());
		args.add(ComposerCLIParams.ANSI.getParameter());
		return performAction(monitor, args.toArray(new String[0]));
	}

	public Status dumpAutoload(IProgressMonitor monitor, String... additionalArgs) {
		List<String> args = new ArrayList<String>();
		args.add(ComposerCLICommands.DUMP_AUTOLOAD.getParameter());
		args.addAll(Arrays.asList(additionalArgs));
		args.add(ComposerCLIParams.ANSI.getParameter());
		return performAction(monitor, args.toArray(new String[0]));
	}

	public static Status isAvailable(IContainer root) {
		if (!isComposerProject(root)) {
			return Status.NOT_COMPOSER;
		}
		String composerPhar = ComposerPreferences.get(ComposerPreferences.COMPOSER_PHAR_NODE);
		if (!ComposerPreferences.getDefaultComposerPhar().equals(composerPhar) && !new File(composerPhar).exists()) {
			return Status.NO_COMPOSER_PHAR;
		}
		PHPexeItem phpExec = getPhpExec();
		if (phpExec != null && phpExec.getExecutable() != null
				&& !new File(phpExec.getExecutable().getAbsolutePath()).exists()) {
			return Status.NO_PHP_EXEC;
		}
		return Status.OK;
	}

	public List<IPackage> search(String pattern, int page, IProgressMonitor monitor) {
		List<String> params = convertPatternIntoArguments(pattern, page);
		params.add(0, ComposerCLICommands.SEARCH.getParameter());
		params.add(ComposerCLIParams.NO_INTERACTION.toString());
		Status result = executeCommand(commandExecutor, monitor, params.toArray(new String[params.size()]));
		if (result == Status.OK) {
			return parsePackages(commandExecutor.getCommandOutput());
		}
		return Collections.emptyList();
	}

	private List<String> convertPatternIntoArguments(String pattern, int page) {
		if (pattern == null || pattern.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> arguments = new ArrayList<String>();
		arguments.add(pattern.replaceAll(" ", URL_SPACE) + "&page=" + page); //$NON-NLS-1$ //$NON-NLS-2$
		arguments.addAll(Arrays.asList(pattern.split(" "))); //$NON-NLS-1$
		return arguments;
	}

	public IPackage show(String name, IProgressMonitor monitor) {
		Status result = executeCommand(commandExecutor, monitor, ComposerCLICommands.SHOW.getParameter(), name,
				ComposerCLIParams.NO_INTERACTION.getParameter());
		if (result == Status.OK) {
			return parsePackageDetails(commandExecutor.getCommandOutput(), name);
		}
		return new Package(name);
	}

	public void addComposer(IProgressMonitor monitor) {
		ComposerRoot model = new ComposerRoot();
		model.getNameProperty().setValue(root.getName(), false);
		IRepository packagistRepo = new Repository();
		packagistRepo.setType(RepositoryType.COMPOSER);
		packagistRepo.setUrl("https://packagist.org/"); //$NON-NLS-1$
		model.getRepositoriesProperty().addRepository(packagistRepo, false);
		try {
			String composerJson = ComposerJacksonMapper.getMapper().writeValueAsString(model);
			IFile composerFile = root.getFile(new Path(COMPOSER_JSON));
			if (!composerFile.exists()) {
				ByteArrayInputStream is = new ByteArrayInputStream(composerJson.getBytes());
				composerFile.create(is, true, monitor);
			}
		} catch (CoreException e) {
			ComposerCorePlugin.logError(e);
		} catch (JsonProcessingException e) {
			ComposerCorePlugin.logError(e);
		}
	}

	public Throwable getError() {
		return error;
	}

	public IContainer getRoot() {
		return root;
	}

	public static IContainer getVendor(IContainer root) {
		return root.getFolder(new Path(VENDOR));
	}

	public static String getComposerPhar(String phpExec) throws CoreException {
		String path = ComposerPreferences.get(ComposerPreferences.COMPOSER_PHAR_NODE);

		downloadComposerPhar(new File(path), phpExec);

		return path;
	}

	public static PHPexeItem getPhpExec() {
		String phpExecName = ComposerPreferences.get(ComposerPreferences.PHP_EXEC_NODE);
		PHPexeItem[] items = PHPexes.getInstance().getCLIItems();
		if (phpExecName != null) {
			for (PHPexeItem item : items) {
				if (item.getName().equals(phpExecName)) {
					return item;
				}
			}
		}
		if (items.length > 0) {
			for (int i = items.length - 1; i >= 0; i--) {
				PHPexeItem item = items[i];
				String path = item.getExecutable().getAbsolutePath();
				if (path != null) {
					return item;
				}
			}
		}
		return null;
	}

	public static String[] getPHPExecNames() {
		PHPexeItem[] items = PHPexes.getInstance().getCLIItems();
		String[] result = new String[items.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = items[i].getName();
		}
		return result;
	}

	public static void downloadComposerPhar(File dest, String phpExec) throws CoreException {
		try {
			if (dest.exists()) {
				boolean update = ComposerPreferences.getBoolean(ComposerPreferences.COMPOSER_PHAR_NODE, true);
				if (!update)
					return;

				long expDate = parseExpDate(dest) * 1000;
				if (System.currentTimeMillis() < expDate)
					return;
			}
			if (dest.exists()) {
				dest.delete();
			}

			File composerPhar = new File(dest, ComposerPreferences.COMPOSER_PHAR);
			String script = HttpHelper.executeGetRequest(GETCOMPOSER_ORG, null, null, 200);
			if (script != null) {
				File scriptFile = new File(getTemp(), "composertemp.php"); //$NON-NLS-1$
				if (!scriptFile.exists()) {
					scriptFile.createNewFile();
				}
				FileOutputStream outStream = new FileOutputStream(scriptFile);
				outStream.write(script.getBytes());
				outStream.close();

				String phpIni = PHPINIUtil.findPHPIni(phpExec).getAbsolutePath();
				List<String> command = new ArrayList<String>();
				command.add(phpExec);
				if (phpIni != null) {
					command.add("-c"); //$NON-NLS-1$
					command.add(phpIni);
				}
				command.add(scriptFile.getCanonicalPath());
				command.add("--"); //$NON-NLS-1$
				command.add(INSTALL_DIR + dest.getParentFile().getAbsolutePath());

				ProcessBuilder processBuilder = new ProcessBuilder(command);
				processBuilder.redirectErrorStream(true);
				PHPLaunchUtilities.appendLibrarySearchPathEnv(processBuilder.environment(),
						new File(phpExec).getParentFile());

				Process p = processBuilder.start();
				String output = IOUtils.toString(p.getInputStream());

				try {
					int result = p.waitFor();
					if (result != 0) {
						composerPhar.delete();
						throw new CoreException(new org.eclipse.core.runtime.Status(IStatus.ERROR,
								ComposerCorePlugin.PLUGIN_ID,
								NLS.bind(Messages.ComposerService_Error_Downloading_Composer_Phar, result, output)));
					}
				} catch (InterruptedException e) {
					ComposerCorePlugin.logError(e);
				} finally {
					scriptFile.delete();
				}
			}
		} catch (IOException e) {
			ComposerCorePlugin.logError(e);
		}
	}

	public static boolean isComposerProject(IContainer root) {
		return root.getFile(new Path(COMPOSER_JSON)).exists();
	}

	public static boolean isInstalled(IContainer root) {
		return isComposerProject(root) && root.getFile(new Path(COMPOSER_LOCK)).exists();
	}

	public static boolean isComposerJson(IResource resource) {
		if (resource == null) {
			return false;
		}
		return resource.getName().equals(COMPOSER_JSON);
	}

	/**
	 * Excludes test paths from project build path. This step removes test
	 * sources from parsing and indexing and will save memory and CPU time.
	 * 
	 * @param project
	 *            the project
	 */
	public static void excludeTestPaths(IProject project) {
		try {
			IScriptProject scriptProject = DLTKCore.create(project);
			IBuildpathEntry[] rawBuildpath = scriptProject.getRawBuildpath();

			ComposerRoot root = null;
			try {
				root = ComposerRoot.parse(project);
			} catch (IOException e) {
				ComposerCorePlugin.logError(e);
			}
			ComposerProperties properties = new ComposerProperties(root);
			String vendorDir = properties.getVendorDir();
			List<IPath> excludedPaths = new ArrayList<IPath>(TESTS_EXCLUDE_PATHS.length);
			for (String path : TESTS_EXCLUDE_PATHS) {
				excludedPaths.add(new Path(String.format(path, vendorDir)));
			}

			for (int i = 0; i < rawBuildpath.length; i++) {
				IBuildpathEntry entry = rawBuildpath[i];
				if (entry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
					if (entry.getPath().equals(project.getProject().getFullPath())) {
						rawBuildpath[i] = DLTKCore.newSourceEntry(entry.getPath(), excludedPaths.toArray(new IPath[0]));
					}
				}
			}

			scriptProject.setRawBuildpath(rawBuildpath, null);
		} catch (ModelException e) {
			ComposerCorePlugin.logError(e);
		}
	}

	private Status performAction(IProgressMonitor monitor, String... command) {
		// CommandExecutor cmd = new CommandExecutor();
		// cmd.setOutputLogDevice(log);
		// cmd.setErrorLogDevice(log);
		return executeCommand(commandExecutor, monitor, command);
	}

	private Status executeCommand(ICommandExecutor cmd, IProgressMonitor monitor, String... action) {
		error = null;
		if (!isComposerProject(root)) {
			return Status.NOT_COMPOSER;
		}

		PHPexeItem phpExec = getPhpExec();
		if (phpExec == null) {
			return Status.NO_PHP_EXEC;
		}
		File phpExe = phpExec.getExecutable();
		if (phpExe == null || !phpExe.exists()) {
			return Status.NO_PHP_EXEC;
		}
		String execPath = phpExe.getAbsolutePath();

		String composerPhar;
		try {
			composerPhar = getComposerPhar(execPath);
		} catch (CoreException e) {
			error = e;
			return Status.NO_COMPOSER_PHAR;
		}
		File composerFile = new File(composerPhar);
		if (!composerFile.exists()) {
			error = new Exception(Messages.ComposerService_No_Composer_Phar);
			return Status.NO_COMPOSER_PHAR;
		}
		// we need to execute command from the directory where .phar is located,
		// as there is a bug where the absolute path to .phar file cannot exceed
		// 130 characters
		cmd.setWorkingDirectory(composerFile.getParent());

		String workingDir = root.getLocation().toOSString();

		List<String> command = new ArrayList<String>();
		command.add(execPath);
		File iniPath = phpExec.getINILocation();
		if (iniPath == null) {
			File iniFile = new File(phpExec.getExecutable().getParentFile(), PHP_INI);
			if (iniFile.exists()) {
				iniPath = iniFile;
			}
		}
		if (iniPath != null) {
			command.add("-n"); //$NON-NLS-1$
			command.add("-c"); //$NON-NLS-1$
			command.add(iniPath.getAbsolutePath());
		}
		command.add(ComposerPreferences.COMPOSER_PHAR);
		command.addAll(Arrays.asList(action));
		command.add(WORKING_DIR);
		command.add(workingDir);
		cmd.setCommand(command);

		Map<String, String> env = new HashMap<String, String>();
		PHPLaunchUtilities.appendLibrarySearchPathEnv(env, phpExe.getParentFile());
		for (Map.Entry<String, String> entry : env.entrySet()) {
			cmd.setEnvironmentVar(entry.getKey(), entry.getValue());
		}

		try {
			addJGitAndPHPPathToPathEnvVar(cmd);
			addJGitClasspathToEnv(cmd);
			addJavaHomeToEnv(cmd);

			if (cmd.run(monitor) != 0) {
				String errorDetails = cmd.getCommandError();
				if (errorDetails == null || errorDetails.trim().isEmpty()) {
					errorDetails = cmd.getCommandOutput();
				}
				ComposerCorePlugin.logError(new Exception(errorDetails));
				error = new Exception(MessageFormat.format(Messages.ComposerService_CmdExecError, command));
				return Status.ERROR;
			}

		} catch (IOException e) {
			error = e;
			ComposerCorePlugin.logError(e);
			return Status.ERROR;
		}
		return Status.OK;
	}

	private void addJGitAndPHPPathToPathEnvVar(ICommandExecutor cmd) throws IOException {
		boolean isPathSet = false;

		String phpPath = getPhpPath();
		if (!phpPath.isEmpty()) {
			phpPath += File.pathSeparatorChar;
		}

		for (String envKey : System.getenv().keySet()) {
			String envValue = System.getenv(envKey);
			if ("PATH".equalsIgnoreCase(envKey)) { //$NON-NLS-1$
				if (envValue == null) {
					envValue = ""; //$NON-NLS-1$
				}
				if (!envValue.isEmpty()) {
					envValue += File.pathSeparatorChar;
				}
				envValue += getJGitExecutablePath();
				envValue = phpPath + envValue;
				isPathSet = true;
			}
			cmd.setEnvironmentVar(envKey, envValue);
		}
		if (!isPathSet) {
			// PATH was not available in the environment of the parent process
			cmd.setEnvironmentVar("PATH", phpPath + getJGitExecutablePath()); //$NON-NLS-1$
		}
	}

	private void addJGitClasspathToEnv(ICommandExecutor cmd) throws IOException {
		cmd.setEnvironmentVar("JGIT_CLASSPATH", createJGitClasspath()); //$NON-NLS-1$
	}

	private void addJavaHomeToEnv(ICommandExecutor cmd) {
		cmd.setEnvironmentVar("JAVA_HOME", System.getProperty("java.home")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private String getJGitExecutablePath() throws IOException {
		// Bundle bundle = Platform.getBundle("org.eclipse.jgit.pgm");
		// //$NON-NLS-1$
		// URL jgitExecURL = FileLocator.find(bundle, new
		// Path(getJGitExecutableName()), null);
		// URL resolvedURL = FileLocator.resolve(jgitExecURL);
		// File jgitExecFile = new File(resolvedURL.getFile());
		// return jgitExecFile.getParent();
		return "";
	}

	private String getPhpPath() {
		PHPexeItem phpExec = getPhpExec();
		if (phpExec == null) {
			return ""; //$NON-NLS-1$
		}
		File file = phpExec.getExecutable();
		if (file == null) {
			return ""; //$NON-NLS-1$
		}
		return file.getParent();
	}

	private String getJGitExecutableName() {
		return EnvironmentUtils.isWindows() ? "git.bat" : "git"; //$NON-NLS-1$ //$NON-NLS-2$

	}

	private String createJGitClasspath() throws IOException {
		return createClasspath("org.eclipse.jgit.pgm", //$NON-NLS-1$
				"org.eclipse.jgit", //$NON-NLS-1$
				"org.eclipse.jgit.http.apache", //$NON-NLS-1$
				"org.eclipse.jgit.ui", //$NON-NLS-1$
				"org.kohsuke.args4j", //$NON-NLS-1$
				"com.jcraft.jsch", //$NON-NLS-1$
				"org.slf4j.api", //$NON-NLS-1$
				"org.apache.httpcomponents.httpcore", //$NON-NLS-1$
				"org.apache.httpcomponents.httpclient", // $NON-NLS-2$
				"org.apache.commons.logging"); //$NON-NLS-1$
	}

	private String createClasspath(String... bundles) throws IOException {
		StringBuilder builder = new StringBuilder();
		for (String bundleName : bundles) {
			if (builder.length() > 0) {
				builder.append(File.pathSeparatorChar);
			}
			builder.append(getBundleClasspathLocation(bundleName));
		}
		return builder.toString();
	}

	private String getBundleClasspathLocation(String bundleName) throws IOException {
		// Bundle bundle = Platform.getBundle(bundleName);
		// File file = FileLocator.getBundleFile(bundle);
		// if (file.isDirectory() && Platform.inDevelopmentMode()) {
		// file = new File(file, "bin"); //$NON-NLS-1$
		// }
		return "";// file.getAbsolutePath();
	}

	private static IPackage parsePackageDetails(String rawOutput, String name) {
		String[] lines = rawOutput.split("\n"); //$NON-NLS-1$
		IPackage result = new Package(name);
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith(VERSIONS_TAG)) {
				String[] segments = line.split(":"); //$NON-NLS-1$
				if (segments.length > 1) {
					String versionsString = segments[1].trim();
					List<String> versions = new ArrayList<String>();
					String[] versionSegments = versionsString.split(","); //$NON-NLS-1$
					for (String versionString : versionSegments) {
						versions.add(versionString.trim());
					}
					result.setVersions(versions);
					return result;
				}
			}
		}
		return result;
	}

	private static List<IPackage> parsePackages(String rawOutput) {
		String[] lines = rawOutput.split("\n"); //$NON-NLS-1$
		List<IPackage> result = new ArrayList<IPackage>();
		for (String line : lines) {
			IPackage p = parsePackage(line);
			if (p != null && !result.contains(p)) {
				result.add(p);
			}
		}
		return result;
	}

	private static IPackage parsePackage(String line) {
		if (line == null || line.isEmpty()) {
			return null;
		}

		int spaceInLine = line.indexOf(' ');
		if (spaceInLine == -1) {
			return new Package(line.trim());
		}
		String name = line.substring(0, spaceInLine).trim();
		if (spaceInLine == line.length() - 1) {
			return new Package(name);
		}
		return new Package(name, line.substring(spaceInLine + 1, line.length()));
	}

	private static File getTemp() {
		String tempDir = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
		return new File(tempDir);
	}

	private static long parseExpDate(File dest) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(dest));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("define") && line.contains("COMPOSER_DEV_WARNING_TIME")) { //$NON-NLS-1$ //$NON-NLS-2$
					int start = line.indexOf(","); //$NON-NLS-1$
					int stop = line.lastIndexOf(")"); //$NON-NLS-1$
					String val = line.substring(start + 1, stop);
					return Long.parseLong(val.trim());
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return -1;
	}

}
