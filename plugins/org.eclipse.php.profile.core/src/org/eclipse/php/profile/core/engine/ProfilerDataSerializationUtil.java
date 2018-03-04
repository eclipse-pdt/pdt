/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.engine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.php.internal.core.util.XMLWriter;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;
import org.eclipse.php.profile.core.PHPProfileCorePlugin;
import org.eclipse.php.profile.core.data.ProfilerCallTrace;
import org.eclipse.php.profile.core.data.ProfilerCallTraceLayer;
import org.eclipse.php.profile.core.data.ProfilerData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Profiler data serialization utility class.
 */
public class ProfilerDataSerializationUtil {

	private XMLWriter fXML;

	/**
	 * Used for serialization
	 * 
	 * @param XMLWriter
	 *            xml
	 */
	private ProfilerDataSerializationUtil(XMLWriter xml) {
		fXML = xml;
	}

	/**
	 * Used for deserialization
	 */
	private ProfilerDataSerializationUtil() {
	}

	private static String pack(byte[] arr) {
		StringBuilder buf = new StringBuilder();
		buf.append(arr.length).append(":"); //$NON-NLS-1$
		for (int i = 0; i < arr.length; ++i) {
			buf.append(arr[i]);
			if (i < arr.length - 1) {
				buf.append(","); //$NON-NLS-1$
			}
		}
		return buf.toString();
	}

	private static byte[] unpackByte(String buf) {
		byte[] arr = null;
		int idx = buf.indexOf(':');
		if (idx != -1) {
			int arr_len = Integer.parseInt(buf.substring(0, idx));
			String[] bytes = buf.substring(idx + 1).split(","); //$NON-NLS-1$
			if (bytes.length == arr_len) {
				arr = new byte[arr_len];
				for (int i = 0; i < arr_len; ++i) {
					arr[i] = Byte.parseByte(bytes[i]);
				}
			}
		}
		return arr;
	}

	// =========================================== Serialization methods
	// ========================================================== //

	public static void serialize(ProfilerDB[] profilerDBs, OutputStream out) {
		XMLWriter xml = null;
		try {
			xml = new XMLWriter(out);
			ProfilerDataSerializationUtil su = new ProfilerDataSerializationUtil(xml);

			xml.startTag("profilerDB", null); //$NON-NLS-1$
			for (int i = 0; i < profilerDBs.length; ++i) {
				HashMap<String, String> parameters = new HashMap<>();
				parameters.put("date", Long.toString(profilerDBs[i].getProfileDate().getTime())); //$NON-NLS-1$
				xml.startTag("profileSession", parameters); //$NON-NLS-1$
				su.serialize(profilerDBs[i].getProfilerData());
				xml.endTag("profileSession"); //$NON-NLS-1$
			}
			xml.endTag("profilerDB"); //$NON-NLS-1$

		} catch (UnsupportedEncodingException e) {
			PHPProfileCorePlugin.log(e);
		} finally {
			if (xml != null) {
				xml.flush();
				xml.close();
			}
		}
	}

	private void serialize(ProfilerData data) {
		if (data == null) {
			return;
		}
		serialize(data.getGlobalData());
		serialize(data.getCallTrace());

		ProfilerFileData[] fileData = data.getFiles();
		for (int i = 0; i < fileData.length; ++i) {
			serialize(fileData[i]);
		}
	}

	private void serialize(ProfilerCallTrace callTrace) {
		if (callTrace == null) {
			return;
		}
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("layers", Integer.toString(callTrace.getLayersCount())); //$NON-NLS-1$
		fXML.startTag("callTrace", parameters); //$NON-NLS-1$
		ProfilerCallTraceLayer[] layers = callTrace.getLayers();
		for (int i = 0; i < layers.length; ++i) {
			serialize(layers[i]);
		}
		fXML.endTag("callTrace"); //$NON-NLS-1$
	}

	private void serialize(ProfilerCallTraceLayer layer) {
		if (layer == null) {
			return;
		}
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("type", Integer.toString(layer.getType())); //$NON-NLS-1$
		parameters.put("line", Integer.toString(layer.getLineNumber())); //$NON-NLS-1$
		parameters.put("id", Integer.toString(layer.getCalledID())); //$NON-NLS-1$
		parameters.put("timestampS", Integer.toString(layer.getTimestampSeconds())); //$NON-NLS-1$
		parameters.put("timestampM", Integer.toString(layer.getTimestampMicroseconds())); //$NON-NLS-1$
		parameters.put("durationS", Integer.toString(layer.getDurationSeconds())); //$NON-NLS-1$
		parameters.put("durationM", Integer.toString(layer.getDurationMicroeconds())); //$NON-NLS-1$
		fXML.startTag("callTraceLayer", parameters); //$NON-NLS-1$
		fXML.endTag("callTraceLayer"); //$NON-NLS-1$
	}

	private void serialize(ProfilerGlobalData data) {
		if (data == null) {
			return;
		}
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("uri", data.getURI()); //$NON-NLS-1$
		parameters.put("originalURL", data.getOriginalURL()); //$NON-NLS-1$
		parameters.put("query", data.getQuery()); //$NON-NLS-1$
		parameters.put("options", data.getOptions()); //$NON-NLS-1$
		parameters.put("path", data.getPath()); //$NON-NLS-1$
		parameters.put("timeS", Integer.toString(data.getTimeSeconds())); //$NON-NLS-1$
		parameters.put("timeM", Integer.toString(data.getTimeMicroSeconds())); //$NON-NLS-1$
		parameters.put("dataSize", Integer.toString(data.getDataSize())); //$NON-NLS-1$
		parameters.put("files", Integer.toString(data.getFileCount())); //$NON-NLS-1$

		fXML.startTag("globalData", parameters); //$NON-NLS-1$
		String[] fileNames = data.getFileNames();
		for (int i = 0; i < fileNames.length; ++i) {
			fXML.printSimpleTag("file", fileNames[i]); //$NON-NLS-1$
		}
		fXML.endTag("globalData"); //$NON-NLS-1$
	}

	private void serialize(ProfilerFileData data) {
		if (data == null) {
			return;
		}
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("name", data.getName()); //$NON-NLS-1$
		parameters.put("local", data.getLocalName()); //$NON-NLS-1$
		parameters.put("functions", Integer.toString(data.getFunctionsCount())); //$NON-NLS-1$
		parameters.put("time", Double.toString(data.getTotalOwnTime())); //$NON-NLS-1$
		fXML.startTag("fileData", parameters); //$NON-NLS-1$

		ProfilerFunctionData[] functionData = data.getFunctions();
		for (int i = 0; i < functionData.length; ++i) {
			serialize(functionData[i]);
		}

		serialize(data.getCodeCoverageData());

		fXML.endTag("fileData"); //$NON-NLS-1$
	}

	private void serialize(ProfilerFunctionData data) {
		if (data == null) {
			return;
		}
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("file", data.getAbsoluteFileName()); //$NON-NLS-1$
		parameters.put("localFile", data.getLocalFileName()); //$NON-NLS-1$
		parameters.put("name", data.toString()); //$NON-NLS-1$
		parameters.put("line", Integer.toString(data.getLineNumber())); //$NON-NLS-1$
		parameters.put("id", Integer.toString(data.getID())); //$NON-NLS-1$
		parameters.put("ownS", Integer.toString(data.getOwnTimeSeconds())); //$NON-NLS-1$
		parameters.put("ownM", Integer.toString(data.getOwnTimeMicroseconds())); //$NON-NLS-1$
		parameters.put("totalS", Integer.toString(data.getTotalTimeSeconds())); //$NON-NLS-1$
		parameters.put("totalM", Integer.toString(data.getTotalTimeMicroseconds())); //$NON-NLS-1$
		parameters.put("calls", Integer.toString(data.getCallsCount())); //$NON-NLS-1$

		fXML.startTag("functionData", parameters); //$NON-NLS-1$
		fXML.endTag("functionData"); //$NON-NLS-1$
	}

	private void serialize(CodeCoverageData data) {
		if (data == null) {
			return;
		}
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("file", data.getFileName()); //$NON-NLS-1$
		parameters.put("localFile", data.getLocalFileName()); //$NON-NLS-1$
		parameters.put("linesNum", Integer.toString(data.getLinesNum())); //$NON-NLS-1$
		parameters.put("phpLinesNum", Integer.toString(data.getPHPLinesNum())); //$NON-NLS-1$
		parameters.put("coverageBitmask", pack(data.getCoverageBitmask())); //$NON-NLS-1$
		parameters.put("significanceBitmask", pack(data.getSignificanceBitmask())); //$NON-NLS-1$

		fXML.startTag("coverageData", parameters); //$NON-NLS-1$
		fXML.endTag("coverageData"); //$NON-NLS-1$
	}

	// =========================================== Deserialization methods
	// ========================================================== //

	public static ProfilerDB[] deserialize(InputStream in) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			// docBuilderFactory.setValidating(true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(in);

			ArrayList<ProfilerDB> profilerDBs = new ArrayList<>();
			NodeList l = doc.getElementsByTagName("profileSession"); //$NON-NLS-1$
			for (int i = 0; l.item(i) != null; ++i) {
				if (l.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) l.item(i);
					Date profileDate = new Date(Long.parseLong(e.getAttribute("date"))); //$NON-NLS-1$
					ProfilerDataSerializationUtil su = new ProfilerDataSerializationUtil();
					ProfilerDB db = new DefaultProfilerDB(su.deserializeProfilerData(e), profileDate);
					profilerDBs.add(db);
				}
			}
			return profilerDBs.toArray(new ProfilerDB[profilerDBs.size()]);

		} catch (Exception e) {
			// ProfilePlugin.log(e);
		}
		return null;
	}

	private ProfilerData deserializeProfilerData(Element rootElement) {
		Element e = (Element) rootElement.getElementsByTagName("globalData").item(0); //$NON-NLS-1$
		ProfilerGlobalData globalData = deserializeGlobalData(e);

		e = (Element) rootElement.getElementsByTagName("callTrace").item(0); //$NON-NLS-1$
		ProfilerCallTrace callTrace = deserializeCallTrace(e);

		ArrayList<ProfilerFileData> fileData = new ArrayList<>();
		NodeList l = rootElement.getElementsByTagName("fileData"); //$NON-NLS-1$
		for (int i = 0; l.item(i) != null; ++i) {
			fileData.add(deserializeFileData((Element) l.item(i), globalData));
		}

		return new ProfilerData(globalData, fileData, callTrace);
	}

	private ProfilerFileData deserializeFileData(Element rootElement, ProfilerGlobalData globalData) {
		String fileName = rootElement.getAttribute("name"); //$NON-NLS-1$
		String localFileName = rootElement.getAttribute("local"); //$NON-NLS-1$
		int functionsNum = Integer.parseInt(rootElement.getAttribute("functions")); //$NON-NLS-1$
		double totalOwnTime = Double.parseDouble(rootElement.getAttribute("time")); //$NON-NLS-1$

		ArrayList<ProfilerFunctionData> functionData = new ArrayList<>();
		NodeList l = rootElement.getElementsByTagName("functionData"); //$NON-NLS-1$
		for (int i = 0; l.item(i) != null; ++i) {
			functionData.add(deserializeFunctionData((Element) l.item(i)));
		}

		Element e = (Element) rootElement.getElementsByTagName("coverageData").item(0); //$NON-NLS-1$
		CodeCoverageData codeCoverage = deserializeCoverageData(e, globalData);

		ProfilerFileData data = new ProfilerFileData(fileName, localFileName, functionsNum, totalOwnTime, functionData);
		data.setCodeCoverageData(codeCoverage);
		return data;
	}

	private ProfilerFunctionData deserializeFunctionData(Element rootElement) {
		String fileName = rootElement.getAttribute("file"); //$NON-NLS-1$
		String localFileName = rootElement.getAttribute("localFile"); //$NON-NLS-1$
		String functionName = rootElement.getAttribute("name"); //$NON-NLS-1$
		int lineNumber = Integer.parseInt(rootElement.getAttribute("line")); //$NON-NLS-1$
		int id = Integer.parseInt(rootElement.getAttribute("id")); //$NON-NLS-1$
		int ownTimeSeconds = Integer.parseInt(rootElement.getAttribute("ownS")); //$NON-NLS-1$
		int ownTimeMicroSeconds = Integer.parseInt(rootElement.getAttribute("ownM")); //$NON-NLS-1$
		int totalTimeSeconds = Integer.parseInt(rootElement.getAttribute("totalS")); //$NON-NLS-1$
		int totalTimeMicroSeconds = Integer.parseInt(rootElement.getAttribute("totalM")); //$NON-NLS-1$
		int callsCount = Integer.parseInt(rootElement.getAttribute("calls")); //$NON-NLS-1$

		ProfilerFunctionData functionData = new ProfilerFunctionData(fileName, functionName, lineNumber, id,
				ownTimeSeconds, ownTimeMicroSeconds, totalTimeSeconds, totalTimeMicroSeconds, callsCount);
		functionData.setLocalFileName(localFileName);

		return functionData;
	}

	private ProfilerCallTrace deserializeCallTrace(Element rootElement) {
		int layersNum = Integer.parseInt(rootElement.getAttribute("layers")); //$NON-NLS-1$
		NodeList l = rootElement.getElementsByTagName("callTraceLayer"); //$NON-NLS-1$
		ArrayList<ProfilerCallTraceLayer> layers = new ArrayList<>();
		for (int i = 0; l.item(i) != null; ++i) {
			layers.add(deserializeCallTraceLayer((Element) l.item(i)));
		}
		ProfilerCallTrace callTrace = new ProfilerCallTrace(layers);
		callTrace.setLayersCount(layersNum);
		return callTrace;
	}

	private ProfilerCallTraceLayer deserializeCallTraceLayer(Element rootElement) {
		int type = Integer.parseInt(rootElement.getAttribute("type")); //$NON-NLS-1$
		int lineNumber = Integer.parseInt(rootElement.getAttribute("line")); //$NON-NLS-1$
		int id = Integer.parseInt(rootElement.getAttribute("id")); //$NON-NLS-1$
		int timestampSeconds = Integer.parseInt(rootElement.getAttribute("timestampS")); //$NON-NLS-1$
		int timestampMicroSeconds = Integer.parseInt(rootElement.getAttribute("timestampM")); //$NON-NLS-1$
		int durationSeconds = Integer.parseInt(rootElement.getAttribute("durationS")); //$NON-NLS-1$
		int durationMicroSeconds = Integer.parseInt(rootElement.getAttribute("durationM")); //$NON-NLS-1$

		return new ProfilerCallTraceLayer(type, lineNumber, id, timestampSeconds, timestampMicroSeconds,
				durationSeconds, durationMicroSeconds);
	}

	private ProfilerGlobalData deserializeGlobalData(Element rootElement) {
		String uri = rootElement.getAttribute("uri"); //$NON-NLS-1$
		String originalURL = rootElement.getAttribute("originalURL"); //$NON-NLS-1$
		String query = rootElement.getAttribute("query"); //$NON-NLS-1$
		String options = rootElement.getAttribute("options"); //$NON-NLS-1$
		String path = rootElement.getAttribute("path"); //$NON-NLS-1$
		int timeSeconds = Integer.parseInt(rootElement.getAttribute("timeS")); //$NON-NLS-1$
		int timeMicroseconds = Integer.parseInt(rootElement.getAttribute("timeM")); //$NON-NLS-1$
		int dataSize = Integer.parseInt(rootElement.getAttribute("dataSize")); //$NON-NLS-1$
		int filesNumber = Integer.parseInt(rootElement.getAttribute("files")); //$NON-NLS-1$

		ArrayList<String> files = new ArrayList<>();
		NodeList l = rootElement.getElementsByTagName("file"); //$NON-NLS-1$
		for (int i = 0; l.item(i) != null; ++i) {
			files.add(l.item(i).getFirstChild().getNodeValue());
		}
		return new ProfilerGlobalData(uri, originalURL, query, options, path, timeSeconds, timeMicroseconds, dataSize,
				filesNumber, files);
	}

	private CodeCoverageData deserializeCoverageData(Element rootElement, ProfilerGlobalData globalData) {
		if (rootElement == null) {
			return null;
		}
		String fileName = rootElement.getAttribute("file"); //$NON-NLS-1$
		String localFileName = rootElement.getAttribute("localFile"); //$NON-NLS-1$
		int linesNum = Integer.parseInt(rootElement.getAttribute("linesNum")); //$NON-NLS-1$
		int phpLinesNum = Integer.parseInt(rootElement.getAttribute("phpLinesNum")); //$NON-NLS-1$
		byte[] coverageBitmask = unpackByte(rootElement.getAttribute("coverageBitmask")); //$NON-NLS-1$
		byte[] significanceBitmask = unpackByte(rootElement.getAttribute("significanceBitmask")); //$NON-NLS-1$

		CodeCoverageData data = new CodeCoverageData(fileName, linesNum, coverageBitmask);
		data.setURL(globalData.getOriginalURL());
		data.setLocalFileName(localFileName);
		data.setPHPLinesNum(phpLinesNum);
		data.setSignificanceBitmask(significanceBitmask);
		return data;
	}
}
