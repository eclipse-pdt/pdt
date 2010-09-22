package org.eclipse.php.core.tests.performance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class Util {

	public static void copyInputStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
	}

	public static void unzip(File file, String targetDirectory)
			throws Exception {

		System.out.println("Extracting: " + file);

		ZipFile zipFile = new ZipFile(file);
		Enumeration<?> entries = zipFile.entries();

		String stripName = file.getName();
		int i = stripName.lastIndexOf('.');
		if (i > 0) {
			stripName = stripName.substring(0, i);
		}

		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();

			String entryName = entry.getName();

			if (entryName.startsWith(stripName + "/")
					|| entryName.startsWith(stripName + "\\")) {
				entryName = entryName.substring(stripName.length() + 1);
			}

			if (entry.isDirectory()) {
				File dir = new File(targetDirectory, entryName);
				dir.mkdirs();
				continue;
			}
			copyInputStream(zipFile.getInputStream(entry),
					new BufferedOutputStream(new FileOutputStream(new File(
							targetDirectory, entryName))));
		}
		zipFile.close();
	}

	public static File downloadFile(String fileURL, String targetDirectory)
			throws Exception {

		System.out.println("Retrieving: " + fileURL);

		String cacheDir = System.getProperty("cacheDirectory");
		if (cacheDir == null) {
			cacheDir = System.getProperty("java.io.tmpdir") + File.separator
					+ "pdt-test-cache";
		}
		new File(cacheDir).mkdirs();

		URL url = new URL(fileURL);
		String fileName = url.getFile().substring(
				url.getPath().lastIndexOf('/') + 1);

		File cachedFile = new File(cacheDir, fileName);
		if (!cachedFile.exists()) {

			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = new BufferedInputStream(urlConnection
					.getInputStream());
			FileOutputStream outputStream = new FileOutputStream(cachedFile);
			byte[] bytes = new byte[8192];
			int count = inputStream.read(bytes);
			while (count != -1 && count <= bytes.length) {
				outputStream.write(bytes, 0, count);
				count = inputStream.read(bytes);
			}
			if (count != -1) {
				outputStream.write(bytes, 0, count);
			}
			inputStream.close();
			outputStream.close();

		} else {
			System.out.println("File: " + cachedFile.getAbsolutePath()
					+ " already exists");
		}

		return cachedFile;
	}

	public static void downloadAndExtract(String fileURL, String targetDirectory)
			throws Exception {
		File file = downloadFile(fileURL, targetDirectory);
		unzip(file, targetDirectory);
	}

	public static Program createProgramFromSource(IFile file) throws Exception {
		ISourceModule source = DLTKCore.createSourceModuleFrom(file);
		return createProgramFromSource(source);
	}

	public static Program createProgramFromSource(ISourceModule source)
			throws Exception {
		IProject project = source.getScriptProject().getProject();
		PHPVersion version;
		if (project != null) {
			version = ProjectOptions.getPhpVersion(project);
		} else {
			version = ProjectOptions.getDefaultPhpVersion();
		}
		ASTParser newParser = ASTParser.newParser(version,
				(ISourceModule) source);
		return newParser.createAST(null);
	}

}
