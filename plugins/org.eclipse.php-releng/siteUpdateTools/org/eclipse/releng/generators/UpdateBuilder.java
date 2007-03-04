/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.releng.generators;


import java.io.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

import javax.xml.parsers.*;

import org.eclipse.osgi.framework.util.Headers;
import org.osgi.framework.BundleException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Utility class to convert buildmanifest.properties files into fetch.xml files.
 * Arguments to this class are: (note "-Location" means the token listed (including the part before the dash
 * 		is a full path and filename as the argument).
 * 
 * 	-directory directory.txt-Location
 * 		Optional: Location (including filename) of directory.txt. This will be parsed to find the
 * 			tag to use instead of buildid for a plugin. The key is "plugin@PLUGINID" where PLUGINID
 * 			is the id of a plugin. The first token (separated by ',') is used as the tag to add into
 * 			fourth part of the version of the plugin if not already set.
 * 
 * 	-cats cats.properties-Location
 * 		Optional: Location (including filename) of cats.properties. This will be parsed to find which categories
 * 			a feature should be in. If this file is specified, then only features listed in this file will
 * 			be added to a category. (This is important because site.xml's should only list the features that
 * 			are selectable, subfeatures that are included should not be added to the site.xml). If this file
 * 			is not specified, then all features will go into a category. They will go into the new default category
 * 			either specified through the -catXML or the default Eclipse one that will created.
 * 
 * 			The key is "feature@FEATUREID" where FEATUREID is the id of the feature. The tokens listed will be 
 * 			separated by ','. Each token will be a different category. There is a special token, "..." which
 * 			means it should go into the new default category (see -catXML).
 * 
 * 	-catxml cat.xml-Location
 * 		Optional: Location (including filename) of cat.xml. This is the xml for the new default category created
 * 			if needed. It needs to be in the form needed in site.xml and start with "<category-def label="
 * 			All substrings of the form "{buildid}" will be replaced with the buildid for this run.
 * 
 * 			If not specified and the default category is needed, then the standard Eclipse default category
 * 			format will be used.
 * 
 * 	-root directory
 * 		Required: Directory of where the plugins/features directories are found to be added to the site.xml.
 * 			Typically this would be something like {workingdir}/eclipse.
 * 
 * 	-build buildid
 * 		Required: The build id to use. This will be used in two places. One, if "-add4thPart" is set, will be the
 * 			fourth part of the versions of the plugins and features (unless overridden through the directory.txt).
 * 			Second, it will be used in cat definition whereever {buildid} is found in the definition for the
 * 			new default category, if one is needed.
 * 
 * 	-site siteDirectory
 * 		Required: The directory of where the site will be built up. When completed, this directory will contain
 * 			the new site.xml, and the appropriate features/plugins directories containing the necessary jars.
 * 
 * 	-sitexml site.xml-Location
 * 		Required: The original site.xml to be updated and copied into the new siteDirectory.
 * 
 * 	-add4thPart
 * 		Optional: If specified then the versions of the features/plugins will be modified in the fourth part
 * 			to include the buildid/tag-from-directory.txt, if the 4th part is not already set.  
 * 
 * 
 * 	-size
 *  	Optional: If specified then the plugin download and installed sizes will be updated into the feature.xml.
 */
public class UpdateBuilder extends AbstractApplication {

	
protected static final String DEFAULT_CATNAME = "...";
	protected String directoryLocation;

	protected String categoriesLocation;
	
	protected boolean add4thPart;
	
	protected boolean setSizes;

	protected String rootLocation;

	protected String siteLocation;

	protected String siteXMLLocation;
	
	protected String catXMLLocation;
	
	protected String catXMLText;
	protected String catName;

	protected String buildNumber;

	protected Properties directory;
	
	protected Properties categories;

	protected Map plugins;

	protected List features;

	protected Manifest man;
	private byte[] defaultManifestBytes = "Manifest-Version: 1.0\n".getBytes("UTF8");

	class PluginData {
		String tag;
		long downloadSize;
		long installedSize;
	}
	
	class FeatureData {

		FeatureData(String id, String version, String os, String ws, String arch, String jar) {
			this.jar = jar;
			this.id = id;
			this.version = version;
			this.os = os;
			this.ws = ws;
			this.arch = arch;
		}

		String jar;

		String id;

		String version;

		String os;

		String ws;

		String arch;

	}

	public UpdateBuilder() throws Exception {
		plugins = new HashMap();
		features = new ArrayList();
		// create common jar manifest
		
		

		ByteArrayInputStream mis = new ByteArrayInputStream(defaultManifestBytes);

		man = new Manifest(mis);
	}

	public static void main(String[] args) throws Exception {
		UpdateBuilder modifier = new UpdateBuilder();
		modifier.run(args);
	}

	public void run() {
		try {
			directory = readDirectory();
			categories = readCats();
			modifyPlugins();
			modifyFeatures();
			writeSiteXML();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void modifyFeatures() throws Exception {
		File root = new File(rootLocation, "features");
		String[] features = root.list();
		if (features == null)
			System.out.println("Could not find features.");
		for (int i = 0; i < features.length; i++) {
			File elementRoot = new File(root, features[i]);
			File element = new File(elementRoot, "feature.xml");
			if (!element.exists()) {
				System.out.println("Could not find descriptor for: " + features[i]);
			} else {
				modifyFeature(element);
			}
		}
	}

	protected void modifyPlugins() throws Exception {
		File root = new File(rootLocation, "plugins");
		String[] plugins = root.list();

		if (plugins == null)
			System.out.println("Could not find plugins.");
		for (int i = 0; i < plugins.length; i++) {
			File elementRoot = new File(root, plugins[i]);
			if (elementRoot.isDirectory())
				modifyPluginFolder(plugins[i], elementRoot);
			else if (plugins[i].endsWith(".jar")) {
				modifyPluginJar(plugins[i], elementRoot);
			}
		}

	}

	/*
	 * Process a plugin that is stored as a folder.
	 * @param plugins
	 * @param i
	 * @param elementRoot
	 * @throws Exception
	 * 
	 * @since 1.2.0
	 */
	private void modifyPluginFolder(String pluginName, File elementRoot) throws Exception {
		File bundleElement = new File(elementRoot, File.separator + "META-INF" + File.separator + "MANIFEST.MF");
		File pluginElement = new File(elementRoot, "plugin.xml");
		boolean found = (bundleElement.exists() && pluginElement.exists());
		File element = new File(elementRoot, "plugin.xml");
		if (element.exists()) {
			modifyPlugin(element, "plugin", found);
			found = true;
		}
		element = new File(elementRoot, File.separator + "META-INF" + File.separator + "MANIFEST.MF");
		if (element.exists()) {
			modifyPlugin(element, "bundle", found);
		}
		element = new File(elementRoot, "fragment.xml");
		if (element.exists()) {
			modifyPlugin(element, "fragment", found);
			found = true;
		}

		if (!found)
			System.out.println("Could not find descriptor for: " + pluginName);
	}

	/*
	 * Process a plugin that is a jar. It is assumed to be a MANIFEST format only. Won't look at plugin.xml
	 * @param pluginJarName
	 * @param jarfile
	 * @throws Exception
	 * 
	 * @since 1.2.0
	 */
	private void modifyPluginJar(String pluginJarName, File jarfile) throws Exception {
		JarFile jzip = null; 
		try {
			jzip = new JarFile(jarfile, false);
			ZipEntry man = jzip.getEntry(JarFile.MANIFEST_NAME);
			if (man != null) {				
				String id = pluginJarName.substring(0, pluginJarName.indexOf("_"));
				String tag = getTag((String) directory.get("bundle@" + id));

				if (tag.equals("none"))
					tag = buildNumber;

				tag = verifyQualifier(tag);
				PluginData pdata = (PluginData) plugins.get(id);
				if (pdata == null) {
					plugins.put(id, pdata = new PluginData());
				}
				
				// Now fixup the Manifest.
				// load MANIFEST.MF
				StringBuffer xml = readInputStream(jzip.getInputStream(man));
				try {
					jzip.close();
				} catch (IOException e) {
				}
				jzip = null;
				
				boolean changedFile = false;
				
				Headers headers = null;

				try {
					headers = Headers.parseManifest(new ByteArrayInputStream(xml.toString().getBytes()));

				} catch (BundleException e) {
					e.printStackTrace();
					return;
				}

				//String version = descriptor.getName()+";bundle-version=\"["+headers.get("Bundle-version").toString();
				String version = headers.get("Bundle-version").toString();
				String vtitle = "Bundle-Version: " + version;

				//	 fix up version
				int start = scan(xml, 0, vtitle);

				if (this.add4thPart) {
					String[] va = versionToArray(version);
					if (va[3].equals("")) {
						va[3] = tag;
						version = arrayToVersion(va);
						int end = start + vtitle.length();
						xml = xml.replace(start, end, "Bundle-Version: " + version);
						changedFile = true;
					}
				}

				long[] sizes;
				if (changedFile) {
					jarfile = renameFile(jarfile, id, version);
					sizes = writeJar(jarfile, xml.toString(), "plugins");
					pdata.tag = tag;
				} else {
					sizes = new long[2];
					sizes[DOWNLOAD_SIZE_INDEX] = sizes[INSTALLED_SIZE_INDEX] = jarfile.length();
					// Now need to copy the file to site build area.
					BufferedInputStream jarin = null;
					BufferedOutputStream jarout = null;
					try {
						jarin = new BufferedInputStream(new FileInputStream(jarfile));
						File destinationFolder = new File(siteLocation, "plugins");
						destinationFolder.mkdirs();
						jarout = new BufferedOutputStream(new FileOutputStream(new File(destinationFolder, jarfile.getName())));
						int in;
						while ((in = jarin.read()) != -1)
							jarout.write(in);
					} finally {
						if (jarin != null) {
							try {
								jarin.close();
							} catch (IOException e) {
							}
						}
						if (jarout != null) {
							try {
								jarout.close();
							} catch (IOException e) {
							}
						}						
					}
				}
				System.out.println("Updated manifest for " + jarfile.getAbsolutePath());
				pdata.installedSize = sizes[DOWNLOAD_SIZE_INDEX];
				pdata.downloadSize = sizes[INSTALLED_SIZE_INDEX];
				return;
				
			} else
				System.out.println("Could not find manifest for: " + pluginJarName);
		} finally {
			if (jzip != null) {
				try {
					jzip.close();
				} catch (IOException e) {
				}
			}
		}
	}

	protected String getTag(String value) {
		String[] values = getArrayFromString(value);
		if (values == null || values.length == 0)
			return "none";
		else
			return values[0];
	}

	/**
	 * Convert a list of tokens into an array. The list separator has to be specified.
	 */
	public static String[] getArrayFromString(String list, String separator) {
		if (list == null || list.trim().equals(""))
			return new String[0];
		ArrayList result = new ArrayList();
		for (StringTokenizer tokens = new StringTokenizer(list, separator); tokens.hasMoreTokens();) {
			String token = tokens.nextToken().trim();
			if (!token.equals(""))
				result.add(token);
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * convert a list of comma-separated tokens into an array
	 */
	public static String[] getArrayFromString(String list) {
		return getArrayFromString(list, ",");
	}

	protected void modifyFeature(File descriptor) throws Exception {
		String name = descriptor.getParentFile().getName();
		String id = name.substring(0, name.indexOf("_"));
		fixFeatureXml(descriptor, id, verifyQualifier(buildNumber));
	}

	protected File renameFolder(File oldName, String id, String version) {
		File newName = new File(oldName.getParent(), id + "_" + version);
		boolean success = oldName.renameTo(newName);
		String prefix = success ? "Success on  " : "Failed on ";
		System.out.println(prefix + "renaming " + oldName.getAbsolutePath() + " to " + newName.getAbsolutePath());
		return newName;
	}
	
	protected File renameFile(File oldName, String id, String version) {
		String oldFName = oldName.getName();
		int iext = oldFName.lastIndexOf('.');
		String newFName;
		if (iext != -1) {
			newFName = id + "_" + version + oldFName.substring(iext);
		} else
			newFName = id + "_" + version;
		File newName = new File(oldName.getParent(), newFName);
		boolean success = oldName.renameTo(newName);
		String prefix = success ? "Success on  " : "Failed on ";
		System.out.println(prefix + "renaming " + oldName.getAbsolutePath() + " to " + newName.getAbsolutePath());
		return newName;
	}

	private void fixFeatureXml(File descriptor, String featureId, String featureTag) throws Exception {

		File featureFolder = descriptor.getParentFile();
		
		// load feature.xml
		StringBuffer xml = readFile(descriptor);
		
		boolean changedFile = false;

		// store feature config information
		String os = getAttributeValue("feature", "os", descriptor);
		String ws = getAttributeValue("feature", "ws", descriptor);
		String arch = getAttributeValue("feature", "arch", descriptor);

		// fix up feature version
		int start = scan(xml, 0, "<feature");
		start = scan(xml, start, "version");

		/*
		 * HACK verify that this is the version attribute by checking for an equals sign within the next few spaces. Not sure why xml parser not being
		 * used here.
		 */
		int equalsIndex = scan(xml, start, "=");
		if ((equalsIndex - (start + 7)) > 3)
			start = scan(xml, equalsIndex, "version");

		start = scan(xml, start, "\"");
		int end = scan(xml, start + 1, "\"");
		String version = xml.substring(start + 1, end);
		String[] va;
		if (this.add4thPart) {
			va = versionToArray(version);
			if (va[3].equals("")) {
				va[3] = featureTag;
				version = arrayToVersion(va);
				xml = xml.replace(start + 1, end, version);
				changedFile = true;
			}
		}

		String featureVersion = version;

		// go through the includes elements and fix up feature versions
		int includesStart = scan(xml, start + 1, "<includes");
		while (includesStart != -1) {
			start = scan(xml, includesStart, "version");

			/*
			 * HACK verify that this is the version attribute by checking for an equals sign within the next few spaces. Not sure why xml parser not
			 * being used here.
			 */
			equalsIndex = scan(xml, start, "=");
			if ((equalsIndex - (start + 7)) > 3)
				start = scan(xml, equalsIndex, "version");

			start = scan(xml, start, "\"");
			end = scan(xml, start + 1, "\"");
			version = xml.substring(start + 1, end);

			if (this.add4thPart) {
				va = versionToArray(version);
				if (va[3].equals("")) {
					va[3] = featureTag;
					version = arrayToVersion(va);
					xml = xml.replace(start + 1, end, version);
					changedFile = true;
				}
			}

			includesStart = scan(xml, start + 1, "<includes");
		}

		// go through the plugin elements and fix up their version
		int pluginStart = scan(xml, start + 1, "<plugin");
		while (pluginStart != -1) {
			start = scan(xml, pluginStart, "id");
			start = scan(xml, start, "\"");
			end = scan(xml, start + 1, "\"");
			String id = xml.substring(start + 1, end);
			PluginData pdata = (PluginData) plugins.get(id);
			if (pdata != null) {
				start = scan(xml, end, "version");

				/*
				 * HACK verify that this is the version attribute by checking for an equals sign within the next few spaces. Not sure why xml parser
				 * not being used here.
				 */
				equalsIndex = scan(xml, start, "=");
				if ((equalsIndex - (start + 7)) > 3)
					start = scan(xml, equalsIndex, "version");

				start = scan(xml, start, "\"");
				end = scan(xml, start + 1, "\"");
				version = xml.substring(start + 1, end);

				if (this.add4thPart && pdata.tag != null) {
					va = versionToArray(version);
					if (va[3].equals("")) {
						va[3] = pdata.tag;
						version = arrayToVersion(va);
						xml = xml.replace(start + 1, end, version);
						changedFile = true;
					}
				}
				
				if (setSizes) {
					// Set the sizes too.
					start = scan(xml, pluginStart, "download-size");
					start = scan(xml, start, "\"");
					end = scan(xml, start + 1, "\"");
					xml = xml.replace(start + 1, end, String.valueOf((pdata.downloadSize+512)/1024));
					
					start = scan(xml, pluginStart, "install-size");
					start = scan(xml, start, "\"");
					end = scan(xml, start + 1, "\"");
					xml = xml.replace(start + 1, end, String.valueOf((pdata.installedSize+512)/1024));
					changedFile = true;
				}

			}
			pluginStart = scan(xml, pluginStart + 7, "<plugin");
		}

		if (changedFile) {
			// write it out to the descriptor location
			char[] outbuf = xml.toString().toCharArray();
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(descriptor));
			try {
				writer.write(outbuf);
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
			featureFolder = renameFolder(featureFolder, featureId, featureVersion);
		}
		writeJAR(featureFolder, "features");

		features.add(new FeatureData(featureId, featureVersion, os, ws, arch, featureFolder.getName() + ".jar"));
	}

	private StringBuffer readFile(File target) throws IOException {
		return readInputStream(new FileInputStream(target));
	}

	/*
	 * @param fis
	 * @return
	 * @throws IOException
	 * 
	 * @since 1.2.0
	 */
	private StringBuffer readInputStream(InputStream is) throws IOException {
		InputStreamReader reader = new InputStreamReader(is);
		StringBuffer result = new StringBuffer();
		char[] buf = new char[4096];
		int count;
		try {
			count = reader.read(buf, 0, buf.length);
			while (count != -1) {
				result.append(buf, 0, count);
				count = reader.read(buf, 0, buf.length);
			}
		} finally {
			try {
				is.close();
				reader.close();
			} catch (IOException e) {
			}
		}
		return result;
	}

	protected void modifyPlugin(File descriptor, String type, boolean pluginandbundle) throws Exception {
		String name = descriptor.getParentFile().getName();
		if (type.equals("bundle")) {
			File parentDir = descriptor.getParentFile();
			name = parentDir.getParentFile().getName();
		}
		String id = name.substring(0, name.indexOf("_"));
		String tag = getTag((String) directory.get(type + "@" + id));

		if (tag.equals("none"))
			tag = buildNumber;

		tag = verifyQualifier(tag);
		PluginData pdata = (PluginData) plugins.get(id);
		if (pdata == null) {
			plugins.put(id, pdata = new PluginData());
		}
		
		if (type.equals("bundle")) {
			if (fixBundle(descriptor, id, tag, type, pluginandbundle, pdata))
				pdata.tag = tag;
		} else {
			if (fixPluginXml(descriptor, id, tag, type, pluginandbundle, pdata))
				pdata.tag = tag;
		}
	}

	private final static int INSTALLED_SIZE_INDEX = 0;
	private final static int DOWNLOAD_SIZE_INDEX = 1;
	
	private boolean fixPluginXml(File descriptor, String id, String qualifier, String type, boolean pluginandbundle, PluginData pdata) throws Exception {

		// load plugin.xml or fragment.xml
		StringBuffer xml = readFile(descriptor);
		
		boolean changedFile = false;
		File pluginFolder = descriptor.getParentFile();

		//	 fix up version
		int start = scan(xml, 0, "<" + type);
		if (start > -1) {
			start = scan(xml, start, "version");

			if (start > -1) {
				/*
				 * HACK verify that this is the version attribute by checking for an equals sign within the next few spaces. Not sure why xml parser
				 * not being used here.
				 */
				int equalsIndex = scan(xml, start, "=");
				if ((equalsIndex - (start + 7)) > 3)
					start = scan(xml, equalsIndex, "version");
				if (start > -1) {
					start = scan(xml, start, "\"");
					if (start > -1) {
						int end = scan(xml, start + 1, "\"");
						if (end > -1) {

							/* HACK If the plugin.xml doesn't have a version string, refer to the Manifest */

							String version = xml.substring(start + 1, end);

							if (this.add4thPart) {
								String[] va = versionToArray(version);
								if (va[3].equals("")) {
									va[3] = qualifier;
									version = arrayToVersion(va);
									xml = xml.replace(start + 1, end, version);
									changedFile = true;
								}
							}

							

							if (changedFile) {
								// write it out to the descriptor location
								char[] outbuf = xml.toString().toCharArray();
								OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(descriptor));
								try {
									writer.write(outbuf);
								} finally {
									try {
										writer.close();
									} catch (IOException e) {
									}
								}
							}
							System.out.println("Updated " + type + ".xml for " + descriptor.getAbsolutePath());
							if (!(pluginandbundle)) {
								if (changedFile) {
									pluginFolder = renameFolder(pluginFolder, id, version);
								}
								long[] sizes = writeJAR(pluginFolder, "plugins");
								pdata.downloadSize = sizes[DOWNLOAD_SIZE_INDEX];
								pdata.installedSize = sizes[INSTALLED_SIZE_INDEX];
								return changedFile;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean fixBundle(File descriptor, String id, String qualifier, String type, boolean pluginandbundle, PluginData pdata) throws Exception {

		// load plugin.xml or fragment.xml or MANIFEST.MF
		StringBuffer xml = readFile(descriptor);

		File bundleFolder = descriptor.getParentFile().getParentFile();	// i.e. bundleFolder/manifest/manifestfile
		boolean changedFile = false;
		
		Headers headers = null;

		try {
			headers = Headers.parseManifest(new FileInputStream(new File(descriptor, "")));

		} catch (BundleException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//String version = descriptor.getName()+";bundle-version=\"["+headers.get("Bundle-version").toString();
		String version = headers.get("Bundle-version").toString();
		String vtitle = "Bundle-Version: " + version;

		//	 fix up version
		int start = scan(xml, 0, vtitle);

		if (this.add4thPart) {
			String[] va = versionToArray(version);
			if (va[3].equals("")) {
				va[3] = qualifier;
				version = arrayToVersion(va);
				int end = start + vtitle.length();
				xml = xml.replace(start, end, "Bundle-Version: " + version);
				changedFile = true;
			}
		}


		// write it out to the descriptor location
		descriptor = descriptor.getAbsoluteFile();

		if (changedFile) {
			char[] outbuf = xml.toString().toCharArray();
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(descriptor));
			try {
				writer.write(outbuf);
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
		System.out.println("Updated " + type + ".xml for " + descriptor.getAbsolutePath());
		if (changedFile) {
			bundleFolder = renameFolder(bundleFolder, id, version);
		}
		long[] sizes = writeJAR(bundleFolder, "plugins");
		pdata.installedSize = sizes[DOWNLOAD_SIZE_INDEX];
		pdata.downloadSize = sizes[INSTALLED_SIZE_INDEX];
		return changedFile;
	}


	private final static long[] EMPTY_JAR = new long[] {0,0};
	
	/*
	 * Rewrite the jar file, replacing the manifest with the new manifest, into the rootFolder. It will use the same name as the incoming jar.
	 * Return the size of the jar created. (In this case transfer size and installed size would be the same).
	 * This is used only for Manifest jars. It is assumed that you will not have a jarred plugin that IS NOT a Manifest jar.
	 * That is really old style mixing with new style which we don't expect to happen.
	 */
	private long[] writeJar(File pluginJar, String replacementManifest, String rootFolder) throws Exception {
		ZipInputStream jin = null;
		ZipOutputStream jos = null;
		File outputjar = null;
		long[] size = new long[2];
		try {
			jin = new ZipInputStream(new FileInputStream(pluginJar));
			File destinationFolder = new File(siteLocation, rootFolder);
			destinationFolder.mkdirs();
			outputjar = new File(destinationFolder, pluginJar.getName());
			jos = new ZipOutputStream(new FileOutputStream(outputjar));
			
			System.out.println("Writing " + outputjar.getAbsolutePath());
			for(ZipEntry jentry = jin.getNextEntry(); jentry != null; jentry = jin.getNextEntry()) {
				if (JarFile.MANIFEST_NAME.equals(jentry.getName())) {
					writeJarEntry(jentry.getName(), new ByteArrayInputStream(replacementManifest.getBytes()), jentry.getTime(), jos);
				} else
					writeJarEntry(jentry.getName(), jin, jentry.getTime(), jos);
			}
		} finally {
			try {
				jin.close();
			} catch (IOException e) {
			}
			if (jos != null) {
				try {
					jos.close();
					size[DOWNLOAD_SIZE_INDEX] = size[INSTALLED_SIZE_INDEX] = outputjar.length(); 
				} catch (IOException e) {
				}
			}
		}
		return size;
	}
	/*
	 * Write a jar file from the given folder. Return the original, unzipped size and the final file size.
	 * of the folder, including subdirectories.
	 */
	private long[] writeJAR(File pluginFolder, String rootFolder) throws Exception {
		String[] list = pluginFolder.list();
		if (list == null) {
			System.out.println("No files found in: " + pluginFolder.getAbsolutePath());
			return EMPTY_JAR;
		}
		File destination = new File(siteLocation, rootFolder);
		destination.mkdirs();

		String jarName = pluginFolder.getName() + ".jar";
		JarOutputStream jos = null;

		long[] size = new long[] {0, 0};
		File jarFile = null;
		try {
			jarFile = new File(destination, jarName);
			System.out.println("Writing " + jarFile.getAbsolutePath());

			String testManifest = pluginFolder.getAbsolutePath() + File.separator + "META-INF" + File.separator + "MANIFEST.MF";
			File ManifestFile = new File(testManifest);

			if (!ManifestFile.exists()) {
				size[INSTALLED_SIZE_INDEX]+=defaultManifestBytes.length;
				jos = new JarOutputStream(new FileOutputStream(jarFile), man);
			} else {
				//InputStream isManifest = new FileInputStream(testManifest);
				// Manifest manifest = new Manifest(isManifest);
				jos = new JarOutputStream(new FileOutputStream(jarFile));

			}
			size[INSTALLED_SIZE_INDEX] += writeJAREntries(jos, pluginFolder, 0);
		} finally {
			if (jos != null)
				try {
					jos.close();
					size[DOWNLOAD_SIZE_INDEX] = jarFile.length(); 
				} catch (IOException e) {
				}
		}
		return size;
	}

	protected void writeSiteXML() throws Exception {
		StringWriter extraInfo = new StringWriter();
		PrintWriter writer = new PrintWriter(extraInfo);

		try {
			for (Iterator iter = features.iterator(); iter.hasNext();) {
				FeatureData data = (FeatureData) iter.next();
				String[] cats = categories != null ? getArrayFromString(categories.getProperty("feature@" + data.id)) : null;
				if (cats == null || cats.length > 0) {
					// We have some cats for this feature, so we will add it to the site.xml.
					writer.println();
					writer.print("\t");
					writer.print("<feature url=\"features/");
					writer.print(data.jar);

					//fix for #38154
					writer.print("\" patch=\"");
					writer.print("false");

					writer.print("\" id=\"");
					writer.print(data.id);
					writer.print("\" version=\"");
					writer.print(data.version);

					//add os, ws and arch attributes if they exist
					if (data.os != null) {
						writer.print("\" os=\"");
						writer.print(data.os);
					}
					if (data.ws != null) {
						writer.print("\" ws=\"");
						writer.print(data.ws);
					}
					if (data.arch != null) {
						writer.print("\" arch=\"");
						writer.print(data.arch);
					}
					writer.println("\">");
					if (cats != null) {
						for (int i = 0; i < cats.length; i++) {
							printCategoryReference(writer, cats[i]);
						}
					} else {
						printCategoryReference(writer, DEFAULT_CATNAME);
					}
					writer.print("\t");
					writer.println("</feature>");
				}
			}

		} finally {
			if (writer != null) {
				writer.println();
				writer.flush();
				writer.close();
			}
		}

		// read site.xml
		File originalXML = new File(siteXMLLocation);
		StringBuffer site = readFile(originalXML);
		int pos = scan(site, 0, "<category-def");
		if (pos == -1) {
			pos = scan(site, 0, "</site");
		}
		if (pos == -1) {
			System.out.println("Error writing site.xml.  Initial file and new file are both probably invalid");
			pos = 0;
		} else
			pos = scanLineStart(site, pos);

		// Insert features either before the first <category-def or, if there are
		// no category-def, then before the /site.
		site.insert(pos, extraInfo.toString());

		if (needDefaultCat()) {
			// Insert category-def before the /site
			pos = scan(site, 0, "</site");
			if (pos == -1) {
				System.out.println("Error writing site.xml.  Initial file and new file are both probably invalid");
			} else {
				site.insert(scanLineStart(site, pos), getDefaultCat());
			}
		}

		File destination = new File(siteLocation, "site.xml");
		FileOutputStream fos = new FileOutputStream(destination);
		try {
			PrintWriter pw = new PrintWriter(fos);
			pw.write(site.toString());
			pw.flush();
			pw.close();
		} finally {
			fos.close();
		}
	}


	protected void printCategoryReference(PrintWriter writer, String cat) throws IOException {
		writer.print("\t\t");
		writer.print("<category name=\"");
		if (!DEFAULT_CATNAME.equals(cat))
			writer.print(cat);	// Specific one.
		else
			writer.print(getDefaultCatName());	// Default one
		writer.println("\"/>");
	}

	protected boolean needDefaultCat() {
		return catName != null;
	}
	
	protected String getDefaultCatName() throws IOException {
		if (catName == null) {
			getDefaultCat();	// This builds the default cat and sets the cat name.
		}
		return catName;
	}
	
	protected String getDefaultCat() throws IOException {
		if (catXMLText == null) {
			if (catXMLLocation != null) {
				StringBuffer catXMLTextBuffer = readFile(new File(catXMLLocation));
				int i = 0;
				while ((i = catXMLTextBuffer.indexOf("{buildid}", i)) != -1) {
					catXMLTextBuffer.replace(i, i+9, buildNumber);
					i+=buildNumber.length();
				}
				// Now need to find the name.
				i = catXMLTextBuffer.indexOf("name=\"");
				if (i != -1) {
					i+=6;
					int endlabel = catXMLTextBuffer.indexOf("\"", i);
					catName = catXMLTextBuffer.substring(i, endlabel);
				} else
					catName = buildNumber;	// This is wrong, but we need something. Should of had a name="\
				// Now make sure there is a new line at the end so that it doesn't cause
				// an insert in a line where it is added, instead it should insert a new line.
				StringWriter categoryExtraInfo = new StringWriter();
				PrintWriter categoryWriter = new PrintWriter(categoryExtraInfo);
				categoryWriter.println(catXMLTextBuffer.toString());
				catXMLText = categoryExtraInfo.toString();
			} else {
				// create the default.
				StringWriter categoryExtraInfo = new StringWriter();
				PrintWriter categoryWriter = new PrintWriter(categoryExtraInfo);
				catName = buildNumber;
				categoryWriter.println();
				categoryWriter.print("\t");
				categoryWriter.print("<category-def label=\"Eclipse SDK ");
				categoryWriter.print(buildNumber);
				categoryWriter.print("\" name=\"");
				categoryWriter.print(buildNumber);
				categoryWriter.println("\">");
				categoryWriter.print("\t\t");
				categoryWriter.println("<description>");
				categoryWriter.print("\t\t\tThis category contains the various features of the ");
				categoryWriter.print(buildNumber);
				categoryWriter.println(" build of Eclipse SDK.");
				categoryWriter.print("\t\t");
				categoryWriter.println("</description>");
				categoryWriter.print("\t");
				categoryWriter.println("</category-def>");
				categoryWriter.println();
				catXMLText = categoryExtraInfo.toString();
			}
		}
		return catXMLText;
	}
	
	/*
	 * write the folder to the jar, and return the accumlated size of the folder.
	 */
	protected long writeJAREntries(JarOutputStream jos, File folder, int rootMembers) throws Exception {
		File[] list = folder.listFiles();
		if (list == null) {
			System.out.println("No files found in: " + folder.getAbsolutePath());
			return 0;
		}
		
		long size = 0;
		String prefix = "";
		File temp = folder;
		for (int i = 0; i < rootMembers; i++) {
			prefix = temp.getName() + "/" + prefix;
			temp = temp.getParentFile();
		}
		rootMembers++;
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				size += writeJAREntries(jos, list[i], rootMembers);
				continue;
			}
			size+=list[i].length();
			FileInputStream is = new FileInputStream(list[i]);
			try {
				writeJarEntry(prefix + list[i].getName(), is, list[i].lastModified(), jos);
			} finally {
				if (is != null)
					try {
						is.close();
					} catch (IOException e) {
					}
				is = null;
			}
		}
		
		return size;
	}

	private void writeJarEntry(String entryName, InputStream is, long lastModified, ZipOutputStream jos) throws Exception {

		ZipEntry jarEntry = new ZipEntry(entryName);
		jarEntry.setTime(lastModified);
		byte[] buf = new byte[4096];
		int count;
		jos.putNextEntry(jarEntry);
		count = is.read(buf);
		while (count != -1) {
			jos.write(buf, 0, count);
			count = is.read(buf);
		}
	}

	private String arrayToVersion(String[] a) {
		return a[0] + "." + a[1] + "." + a[2] + "." + a[3];
	}

	private String[] versionToArray(String s) {
		String[] a = new String[] { "0", "0", "0", ""};
		StringTokenizer t = new StringTokenizer(s, ".");
		for (int i = 0; i < 4 && t.hasMoreTokens(); i++) {
			a[i] = t.nextToken();
		}
		return a;
	}

	private int scan(StringBuffer buf, int start, String target) {
		return scan(buf, start, new String[] { target});
	}

	private int scan(StringBuffer buf, int start, String[] targets) {
		for (int i = start; i < buf.length(); i++) {
			for (int j = 0; j < targets.length; j++) {
				if (i < buf.length() - targets[j].length()) {
					String match = buf.substring(i, i + targets[j].length());
					if (targets[j].equals(match))
						return i;
				}
			}
		}
		return -1;
	}
	
	private int scanLineStart(StringBuffer buf, int start) {
		// Scan backwards for start line so that we can insert there instead
		// of at current pos (which could be in middle of line after some tabs).
		// Find either '\n', or '\r'. This will be the start of the line.
		while (--start > 0 && buf.charAt(start) != '\n' && buf.charAt(start) != '\r');
		return ++start;
	}

	protected Properties readDirectory() throws IOException {
		Properties result = new Properties();
		if (directoryLocation != null) {
			File file = new File(directoryLocation);
			InputStream is = new FileInputStream(file);
			try {
				result.load(is);
			} finally {
				is.close();
			}
		}
		return result;
	}
	
	protected Properties readCats() throws IOException {
		if (categoriesLocation != null) {
			Properties result = new Properties();
			File file = new File(categoriesLocation);
			InputStream is = new FileInputStream(file);
			try {
				result.load(is);
			} finally {
				is.close();
			}
			return result;
		}
		return null;
	}	

	protected void processCommandLine(List commands) {
		// looks for param/arg-like commands

		// Full path and name of directory.txt file
		String[] arguments = getArguments(commands, "-directory");
		if (arguments != null)
			this.directoryLocation = arguments[0]; // only consider one location

		// Full path and name of categories.properties file
		arguments = getArguments(commands, "-cats");
		if (arguments != null)
			this.categoriesLocation = arguments[0]; // only consider one location
		
		// Full path and name of cat.xml file
		arguments = getArguments(commands, "-catxml");
		if (arguments != null)
			this.catXMLLocation = arguments[0]; // only consider one location
		

		// Path to unzipped eclipse directory example - c:\eclipse
		arguments = getArguments(commands, "-root");
		this.rootLocation = arguments[0]; // only consider one location

		// String used to name the build. Only appears in the Category-def name
		arguments = getArguments(commands, "-build");
		this.buildNumber = arguments[0]; // only consider one location

		// Path to the sites root directory. IE the directory that will contain the
		// plugins and featues directories for the geneated update site.
		arguments = getArguments(commands, "-site");
		this.siteLocation = arguments[0]; // only consider one location

		// Ful path and name of the site.xml file.
		arguments = getArguments(commands, "-sitexml");
		this.siteXMLLocation = arguments[0];

		// Include if you want to generate 4 part version numbers.
		// Leave off if you want to generate 3 part persion numbers.
		this.add4thPart = commands.contains("-add4thPart");
		
		// Include if you want the download/installed sizes set.
		this.setSizes = commands.contains("-size");

	}

	private String verifyQualifier(String s) {
		char[] chars = s.trim().toCharArray();
		boolean whitespace = false;
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isLetterOrDigit(chars[i])) {
				chars[i] = '-';
				whitespace = true;
			}
		}
		return whitespace ? new String(chars) : s;
	}

	private String getAttributeValue(String elementName, String attributeName, File file) {

		class Handler extends DefaultHandler {

			String element;

			String attribute;

			String value;

			Handler(String element, String attribute) {
				this.element = element;
				this.attribute = attribute;
			}

			//  Start Element Event Handler
			public void startElement(String uri, String local, String qName, Attributes atts) {

				String result = atts.getValue(attribute);

				if (qName.equals(element)) {
					value = result;
				}
			}
		}

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser parser;

		try {
			Handler handler = new Handler(elementName, attributeName);
			parser = saxParserFactory.newSAXParser();
			parser.parse(file, handler);
			return handler.value;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
