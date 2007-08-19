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
 * Handle Init and Response DBGp Responses
 * Init and Engine Child only
 * Response, status attributes and Error code child only
 * status entries occur when a program suspends or a status request is made
 *
 */
public class DBGpResponse {

	/*
	 * Example Init
	 * <init fileuri="file:///C%3A%5Cudata-eclipse%5Cphpide025%5CtestXdebug%5CPhpCode%5Ctestcase3.php" language="PHP" protocol_version="1.0" appid="2116" idekey="ECLIPSE_XDEBUG11623014568921">
	 *    <engine version="2.0.0RC2-dev">
	 *       <![CDATA[Xdebug]]>
	 *    </engine>
	 *    <author>
	 *       <![CDATA[Derick Rethans]]>
	 *    </author>
	 *    <url>
	 *       <![CDATA[http://xdebug.org]]>
	 *    </url>
	 *    <copyright>
	 *       <![CDATA[Copyright (c) 2002-2006 by Derick Rethans]]>
	 *    </copyright>
	 * </init>
	 */

	/*
	 * Example Responses
	 *  <response command="property_get" transaction_id="95" status="break" reason="ok">
	 *     <error code="300">
	 *        <message>
	 *           <![CDATA[can not get property]]>
	 *        </message>
	 *     </error>
	 *  </response>
	 *  
	 *  <response command="stop" transaction_id="32" status="stopped" reason="ok"></response>
	 *  
	 *  <response command="breakpoint_set" transaction_id="1" id="49240001"></response>
	 *  <response command="feature_set" transaction_id="19" feature="max_depth" success="1"></response>
	 *
	 */

	/*
	 * Example stream
	 * 
	 * <stream type="stdout" encoding="base64"><![CDATA[PGh0bWw+DQo8aGVhZD4NCjx0aXRsZT5HdWVzc2luZyBHYW1lPC90aXRsZT4NCjwvaGVhZD4NCjxib2R5Pg0KPGZvcm0gYWN0aW9uPScnIG1ldGhvZD0ncG9zdCc+PGlucHV0IHR5cGU9J2hpZGRlbicgdmFsdWU9JzUnIG5hbWU9J3R4dE51bWJlcic+PGlucHV0IHR5cGU9J2hpZGRlbicgdmFsdWU9JzAnIG5hbWU9J3R4dFRyaWVzJz5QbGVhc2UgR3Vlc3MgQSBOdW1iZXIgKDEgLSA1MCk8aW5wdXQgdHlwZT0ndGV4dCcgbmFtZT0ndHh0R3Vlc3MnIHNpemU9JzEwJz48aW5wdXQgdHlwZT0nc3VibWl0JyB2YWx1ZT0nU3VibWl0JyBuYW1lPSdTdWJtaXQnPjwvZm9ybT48YnIgLz4gPCEtLSA1LS0hPjwvYm9keT4NCjwvaHRtbD4=]]></stream>
	 * 
	 */

	/*
	 * status codes
	 * {"", "starting", "stopping", "stopped", "running", "break"};
	 */
	public static final String STATUS_STARTING = "starting";
	public static final String STATUS_STOPPING = "stopping";
	public static final String STATUS_STOPPED = "stopped";
	public static final String STATUS_RUNNING = "running";
	public static final String STATUS_BREAK = "break";

	/*
	 * reason codes
	 * {"ok", "error", "aborted", "exception"}; 
	 */

	public static final String REASON_OK = "ok";
	public static final String REASON_ERROR = "error";
	public static final String REASON_ABORTED = "aborted";
	public static final String REASON_EXCEPTION = "exception";

	/*
	 * Error codes, eg

	 {   0, "no error" },
	 {   1, "parse error in command" },
	 {   2, "duplicate arguments in command" },
	 {   3, "invalid or missing options" },
	 {   4, "unimplemented command" },
	 {   5, "command is not available" },
	 { 100, "can not open file" },
	 { 101, "stream redirect failed" },
	 { 200, "breakpoint could not be set" },
	 { 201, "breakpoint type is not supported" },
	 { 202, "invalid breakpoint line" },
	 { 203, "no code on breakpoint line" },
	 { 204, "invalid breakpoint state" },
	 { 205, "no such breakpoint" },
	 { 206, "error evaluating code" },
	 { 207, "invalid expression" },
	 { 300, "can not get property" },
	 { 301, "stack depth invalid" },
	 { 302, "context invalid" },
	 { 900, "encoding not supported" },
	 { 998, "an internal exception in the debugger" },
	 { 999, "unknown error" },

	 *  
	 */
	public static final int ERROR_OK = 0;
	public static final int ERROR_CANT_GET_PROPERTY = 300;
	public static final int ERROR_UNKNOWN_ERROR_CODE = 10000;
	public static final int ERROR_UNKNOWN_TYPE = 10001;
	public static final int ERROR_PARSE_FAILURE = 10002;

	private DocumentBuilder db;
	private Document doc;
	private Node parent;
	private String rawXML;

	//type
	public static final int PARSE_FAILURE = 0;
	public static final int INIT = 1;
	public static final int RESPONSE = 2;
	public static final int STREAM = 3;
	public static final int UNKNOWN_TYPE = 99;

	int type;

	//init attributes
	private String idekey;
	private String session;
	private String engineVersion;
	private String fileUri;

	// Response attributes
	private String id;
	private String command;

	// Status Response attributes
	private String status;
	private String reason;

	// error responses
	int errorCode;
	String errorMessage;

	// stream data
	private String streamType;
	private String streamData;

	public DBGpResponse() {
		DocumentBuilderFactory dbFact = DocumentBuilderFactory.newInstance();
		try {
			db = dbFact.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			DBGpLogger.logException(null, this, e);
		}
	}

	/*
	public void parseResponse(String xmlResponse) {
	   rawXML = xmlResponse;
	   errorCode = ERROR_OK;
	   type = UNKNOWN_TYPE;
	   if (db != null) {
	      ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponse.getBytes());
	      parseResponse(bais);
	   }
	   else {
	      type = PARSE_FAILURE;
	   }
	}
	*/

	public void parseResponse(byte[] xmlResponse) {
		//rawXML = xmlResponse;
		errorCode = ERROR_OK;
		type = UNKNOWN_TYPE;
		if (db != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(xmlResponse);
			parseResponse(bais);
		} else {
			type = PARSE_FAILURE;
		}
	}

	public void parseResponse(InputStream is) {
		id = null;
		command = null;
		try {
			doc = db.parse(is);
			parent = doc.getFirstChild();
			String nodeName = parent.getNodeName();
			if (nodeName.equals("response")) {
				parseResponse();
			} else if (nodeName.equals("init")) {
				parseInit();
			} else if (nodeName.equals("stream")) {
				parseStream();
			}
		} catch (SAXException e) {
			DBGpLogger.logException(null, this, e);
			type = PARSE_FAILURE;
		} catch (IOException e) {
			DBGpLogger.logException(null, this, e);
			type = PARSE_FAILURE;
		}
		//System.out.println("wait for me");
	}

	private void parseStream() {
		type = STREAM;
		streamType = getTopAttribute("type");
		String encoding = getTopAttribute("Encoding"); // don't use yet, assume it is base64
		Node Child = parent.getFirstChild();
		if (Child != null) {
			String data = Child.getNodeValue();
			streamData = new String(Base64.decode(data.getBytes()));
		}
	}

	private void parseInit() {
		// get the init information
		type = INIT;
		idekey = getTopAttribute("idekey");
		session = getTopAttribute("session");
		if (session.trim().length() == 0) {
			session = null;
		}
		fileUri = getTopAttribute("fileuri");
		// engine may not be the first child so you will need to search
		// for it.
		NodeList nodes = parent.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals("engine")) {
				engineVersion = getAttribute(node, "version");
				i = nodes.getLength();
			}
		}
	}

	private void parseResponse() {
		type = RESPONSE;
		id = getTopAttribute("transaction_id");
		command = getTopAttribute("command");
		status = getTopAttribute("status");
		reason = getTopAttribute("reason");

		// get the error information
		Node errNode = parent.getFirstChild();
		if (errNode != null && errNode.getNodeName().equals("error")) {
			String errVal = getAttribute(errNode, "code");
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
		}
	}

	public Node getParentNode() {
		return doc.getFirstChild();
	}

	public String getTopAttribute(String attrName) {
		return getAttribute(parent, attrName);
	}

	public static String getAttribute(Node node, String attrName) {
		String attrValue = "";
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

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getRawXML() {
		return rawXML;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public String getStreamData() {
		return streamData;
	}

	public String getStreamType() {
		return streamType;
	}
}
