/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.php.internal.debug.core.xdebug.dbgp.DBGpLogger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Handle Init and Response DBGp Responses Init and Engine Child only Response,
 * status attributes and Error code child only status entries occur when a
 * program suspends or a status request is made
 * 
 */
public class DBGpResponse {

	/*
	 * Example Init <initfileuri=
	 * "file:///C%3A%5Cudata-eclipse%5Cphpide025%5CtestXdebug%5CPhpCode%5Ctestcase3.php"
	 * language="PHP" protocol_version="1.0" appid="2116"
	 * idekey="ECLIPSE_XDEBUG11623014568921"> <engine version="2.0.0RC2-dev">
	 * <![CDATA[Xdebug]]> </engine> <author> <![CDATA[Derick Rethans]]>
	 * </author> <url> <![CDATA[http://xdebug.org]]> </url> <copyright>
	 * <![CDATA[Copyright (c) 2002-2006 by Derick Rethans]]> </copyright>
	 * </init>
	 */

	/*
	 * Example Responses <response command="property_get" transaction_id="95"
	 * status="break" reason="ok"> <error code="300"> <message> <![CDATA[can not
	 * get property]]> </message> </error> </response>
	 * 
	 * <response command="stop" transaction_id="32" status="stopped"
	 * reason="ok"></response>
	 * 
	 * <response command="breakpoint_set" transaction_id="1"
	 * id="49240001"></response> <response command="feature_set"
	 * transaction_id="19" feature="max_depth" success="1"></response>
	 */

	/*
	 * Example stream
	 * 
	 * <stream type="stdout"encoding="base64"><![CDATA[PGh0bWw+
	 * DQo8aGVhZD4NCjx0aXRsZT5HdWVzc2luZyBHYW1lPC90aXRsZT4NCjwvaGVhZD4NCjxib2R5Pg0KPGZvcm0gYWN0aW9uPScnIG1ldGhvZD0ncG9zdCc
	 * +PGlucHV0IHR5cGU9J2hpZGRlbicgdmFsdWU9JzUnIG5hbWU9J3R4dE51bWJlcic+
	 * PGlucHV0IHR5cGU9J2hpZGRlbicgdmFsdWU9JzAnIG5hbWU9J3R4dFRyaWVzJz5QbGVhc2UgR3Vlc3MgQSBOdW1iZXIgKDEgLSA1MCk8aW5wdXQgdHlwZT0ndGV4dCcgbmFtZT0ndHh0R3Vlc3MnIHNpemU9JzEwJz48aW5wdXQgdHlwZT0nc3VibWl0JyB2YWx1ZT0nU3VibWl0JyBuYW1lPSdTdWJtaXQnPjwvZm9ybT48YnIgLz4gPCEtLSA1LS0hPjwvYm9keT4NCjwvaHRtbD4
	 * =]]></stream>
	 */

	/*
	 * status codes {"", "starting", "stopping", "stopped", "running", "break"};
	 */
	public static final String STATUS_STARTING = "starting"; //$NON-NLS-1$
	public static final String STATUS_STOPPING = "stopping"; //$NON-NLS-1$
	public static final String STATUS_STOPPED = "stopped"; //$NON-NLS-1$
	public static final String STATUS_RUNNING = "running"; //$NON-NLS-1$
	public static final String STATUS_BREAK = "break"; //$NON-NLS-1$

	/*
	 * reason codes {"ok", "error", "aborted", "exception"};
	 */

	public static final String REASON_OK = "ok"; //$NON-NLS-1$
	public static final String REASON_ERROR = "error"; //$NON-NLS-1$
	public static final String REASON_ABORTED = "aborted"; //$NON-NLS-1$
	public static final String REASON_EXCEPTION = "exception"; //$NON-NLS-1$

	private DocumentBuilder db;
	private Document doc;
	private Node parent;

	// type
	public static final int PARSE_FAILURE = 0;
	public static final int INIT = 1;
	public static final int RESPONSE = 2;
	public static final int STREAM = 3;
	public static final int PROXY_INIT = 4;
	public static final int PROXY_ERROR = 5;

	public static final int UNKNOWN_TYPE = 99;

	int type;

	// init attributes
	private String idekey;
	private String session;
	private String threadId;
	private String engineVersion = ""; //$NON-NLS-1$
	private EngineTypes engineType = EngineTypes.other;
	private String fileUri;

	// Response attributes
	private String id;
	private String command;

	// Status Response attributes
	private String status;
	private String reason;

	// error responses

	/*
	 * Error codes, eg
	 * 
	 * { 0, "no error" }, { 1, "parse error in command" }, { 2,
	 * "duplicate arguments in command" }, { 3, "invalid or missing options" },
	 * { 4, "unimplemented command" }, { 5, "command is not available" }, { 100,
	 * "can not open file" }, { 101, "stream redirect failed" }, { 200,
	 * "breakpoint could not be set" }, { 201,
	 * "breakpoint type is not supported" }, { 202, "invalid breakpoint line" },
	 * { 203, "no code on breakpoint line" }, { 204, "invalid breakpoint state"
	 * }, { 205, "no such breakpoint" }, { 206, "error evaluating code" }, {
	 * 207, "invalid expression" }, { 300, "can not get property" }, { 301,
	 * "stack depth invalid" }, { 302, "context invalid" }, { 900,
	 * "encoding not supported" }, { 998,
	 * "an internal exception in the debugger" }, { 999, "unknown error" },
	 */
	public static final int ERROR_OK = 0;
	public static final int ERROR_CANT_GET_PROPERTY = 300;

	// own internal error codes
	public static final int ERROR_UNKNOWN_ERROR_CODE = 10000;
	public static final int ERROR_UNKNOWN_TYPE = 10001;
	public static final int ERROR_PARSE_FAILURE = 10002;
	public static final int ERROR_INVALID_RESPONSE = 10003;

	int errorCode;
	String errorMessage;

	// stream data
	private String streamType;
	private String streamData;

	private byte[] rawXML;

	public DBGpResponse() {
		DocumentBuilderFactory dbFact = DocumentBuilderFactory.newInstance();
		try {
			db = dbFact.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			DBGpLogger.logException(null, this, e);
		}
	}

	public void parseResponse(byte[] xmlResponse) {
		rawXML = xmlResponse;
		if (db != null && xmlResponse != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponse);
			parseResponse(bais);
		} else {
			type = PARSE_FAILURE;
			errorCode = ERROR_PARSE_FAILURE;
		}
	}

	private void parseResponse(InputStream is) {
		id = null;
		command = null;
		type = UNKNOWN_TYPE;
		errorCode = ERROR_UNKNOWN_TYPE;

		try {
			doc = db.parse(is);
			parent = doc.getFirstChild();
			String nodeName = parent.getNodeName();
			if (nodeName.equals("response")) { //$NON-NLS-1$
				parseResponseType();
			} else if (nodeName.equals("init")) { //$NON-NLS-1$
				parseInitType();
			} else if (nodeName.equals("stream")) { //$NON-NLS-1$
				parseStreamType();
			} else if (nodeName.equals("proxyinit")) { //$NON-NLS-1$
				parseProxyInitType();
			} else if (nodeName.equals("proxyerror")) { //$NON-NLS-1$
				parseProxyErrorType();
			}

		} catch (SAXException e) {
			DBGpLogger.logException(null, this, e);
			type = PARSE_FAILURE;
			errorCode = ERROR_PARSE_FAILURE;
		} catch (IOException e) {
			DBGpLogger.logException(null, this, e);
			type = PARSE_FAILURE;
			errorCode = ERROR_PARSE_FAILURE;
		}
		// System.out.println("wait for me");
	}

	private void parseStreamType() {
		type = STREAM;
		streamType = getTopAttribute("type"); //$NON-NLS-1$
		Node Child = parent.getFirstChild();
		if (Child != null) {
			streamData = Child.getNodeValue();
		}

		if (streamType.length() != 0) {
			errorCode = ERROR_OK;
		} else {
			errorCode = ERROR_INVALID_RESPONSE;
		}

	}

	private void parseProxyInitType() {
		type = PROXY_INIT;
		idekey = getTopAttribute("idekey"); //$NON-NLS-1$
		// caller can retrieve address, port
		getErrorInformation(false);

	}

	private void parseProxyErrorType() {
		type = PROXY_ERROR;
		getErrorInformation(false);

	}

	private void parseInitType() {
		// get the init information
		type = INIT;
		idekey = getTopAttribute("idekey"); //$NON-NLS-1$
		threadId = getTopAttribute("thread"); //$NON-NLS-1$
		session = getTopAttribute("session"); //$NON-NLS-1$
		if (session.trim().length() == 0) {
			session = null;
		}
		fileUri = getTopAttribute("fileuri"); //$NON-NLS-1$
		// engine may not be the first child so you will need to search
		// for it.
		NodeList nodes = parent.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals("engine")) { //$NON-NLS-1$
				engineVersion = getAttribute(node, "version"); //$NON-NLS-1$
				NodeList moreNodes = node.getChildNodes();
				if (moreNodes != null && moreNodes.getLength() > 0) {
					String engineTypeStr = moreNodes.item(0).getNodeValue();
					if (engineTypeStr != null) {
						try {
							engineType = EngineTypes.valueOf(engineTypeStr);
						} catch (IllegalArgumentException e) {
							engineType = EngineTypes.other;
						}
					}

				}
				i = nodes.getLength();
			}
		}
		if (idekey.length() != 0 && fileUri.length() != 0) {
			errorCode = ERROR_OK;
		} else {
			errorCode = ERROR_INVALID_RESPONSE;
		}
	}

	private void parseResponseType() {
		type = RESPONSE;
		id = getTopAttribute("transaction_id"); //$NON-NLS-1$
		command = getTopAttribute("command"); //$NON-NLS-1$
		status = getTopAttribute("status"); //$NON-NLS-1$
		reason = getTopAttribute("reason"); //$NON-NLS-1$
		getErrorInformation(true);
	}

	private void getErrorInformation(boolean checkID) {
		// get the error information
		Node errNode = parent.getFirstChild();
		if (errNode != null && errNode.getNodeName().equals("error")) { //$NON-NLS-1$
			String errVal = getAttribute(errNode, "code"); //$NON-NLS-1$
			try {
				errorCode = Integer.parseInt(errVal);
			} catch (NumberFormatException nfe) {
				errorCode = ERROR_UNKNOWN_ERROR_CODE;
			}
			Node msgNode = errNode.getFirstChild();
			if (msgNode != null) {
				Node dataNode = msgNode.getFirstChild();
				if (dataNode != null) {
					errorMessage = dataNode.getNodeValue();
				}
			}
		} else {
			errorCode = ERROR_OK;
			if (checkID && (id == null || id.length() == 0)) {
				errorCode = ERROR_INVALID_RESPONSE;
			}
		}
	}

	public Node getParentNode() {
		return doc.getFirstChild();
	}

	public String getTopAttribute(String attrName) {
		return getAttribute(parent, attrName);
	}

	public static String getAttribute(Node node, String attrName) {
		String attrValue = ""; //$NON-NLS-1$
		if (node != null && node.hasAttributes()) {
			NamedNodeMap attrs = node.getAttributes();
			Node attribute = attrs.getNamedItem(attrName);
			if (attribute != null)
				attrValue = attribute.getNodeValue();
		}
		return attrValue;
	}

	public String getCommand() {
		return command;
	}

	public String getId() {
		return id;
	}

	public String getReason() {
		return reason;
	}

	public String getStatus() {
		return status;
	}

	public int getType() {
		return type;
	}

	public String getFileUri() {
		return fileUri;
	}

	public String getIdekey() {
		return idekey;
	}

	public String getSession() {
		return session;
	}

	public String getThreadId() {
		return threadId;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	/**
	 * this will either be null or base64 encoded. It needs to be decoded.
	 * 
	 * @return encoded stream data or null.
	 */
	public String getStreamData() {
		return streamData;
	}

	public String getStreamType() {
		return streamType;
	}

	public byte[] getRawXML() {
		return rawXML;
	}

	public EngineTypes getEngineType() {
		return engineType;
	}
}
