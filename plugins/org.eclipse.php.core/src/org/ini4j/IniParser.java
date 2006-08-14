/*
 * Copyright 2005 [ini4j] Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ini4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;

public class IniParser {
	public static final String COMMENTS = ";#"; //$NON-NLS-1$
	public static final String DEFAULT_SECTION_NAME = "_"; //$NON-NLS-1$
	public static final String DEFAULT_SERVICE = "org.ini4j.IniParser"; //$NON-NLS-1$
	public static final char OPERATOR = '=';
	public static final char SECTION_BEGIN = '[';

	public static final char SECTION_END = ']';
	public static final String SERVICE_ID = DEFAULT_SERVICE;

	public static IniParser newInstance() {
		return (IniParser) ServiceFinder.findService(SERVICE_ID, DEFAULT_SERVICE);
	}

	public void parse(final InputStream input, final IniHandler handler, final int mode) throws IOException, InvalidIniFormatException {
		parse(new InputStreamReader(input), handler, mode);
	}

	public void parse(final Reader input, final IniHandler handler, final int mode) throws IOException, InvalidIniFormatException {
		final LineNumberReader reader = new LineNumberReader(input);

		handler.startIni();

		String sectionName = DEFAULT_SECTION_NAME;
		handler.startSection(sectionName);

		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			line = line.trim();

			if (line.length() == 0 || COMMENTS.indexOf(line.charAt(0)) >= 0)
				continue;

			if (line.charAt(0) == SECTION_BEGIN) {
				if (sectionName != null)
					handler.endSection();

				if (line.charAt(line.length() - 1) != SECTION_END)
					parseError(line, reader.getLineNumber());

				sectionName = line.substring(1, line.length() - 1).trim();
				if ((mode & Ini.IGNORE_ESCAPE) == 0)
					sectionName = unescape(sectionName);

				if (sectionName.length() == 0)
					parseError(line, reader.getLineNumber());

				handler.startSection(sectionName);
			} else {
				if (sectionName == null)
					parseError(line, reader.getLineNumber());

				final int idx = line.indexOf(OPERATOR);

				if (idx <= 0)
					parseError(line, reader.getLineNumber());

				String name = line.substring(0, idx);
				if ((mode & Ini.IGNORE_ESCAPE) == 0)
					name = unescape(name);
				name = name.trim();

				String value = line.substring(idx + 1);
				if ((mode & Ini.IGNORE_ESCAPE) == 0)
					value = unescape(value);
				value = value.trim();
				if ((mode & Ini.STRIP_QUOTES) > 0)
					value = value.replaceAll("\"", ""); //$NON-NLS-1$ //$NON-NLS-2$

				if (name.length() == 0)
					parseError(line, reader.getLineNumber());

				handler.handleOption(name, value);
			}
		}

		if (sectionName != null)
			handler.endSection();

		handler.endIni();
	}

	public void parse(final URL input, final IniHandler handler, final int mode) throws IOException, InvalidIniFormatException {
		parse(input.openStream(), handler, mode);
	}

	protected void parseError(final String line, final int lineNumber) throws InvalidIniFormatException {
		throw new InvalidIniFormatException("parse error (at line: " + lineNumber + "): " + line); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void parseXML(final InputStream input, final IniHandler handler) throws IOException, InvalidIniFormatException {
		parseXML(new InputStreamReader(input), handler);
	}

	public void parseXML(final Reader input, final IniHandler handler) throws IOException, InvalidIniFormatException {
		class XML2Ini extends DefaultHandler {
			static final String ATTR_KEY = "key"; //$NON-NLS-1$
			static final String ATTR_VALUE = "value"; //$NON-NLS-1$
			static final String ATTR_VERSION = "version"; //$NON-NLS-1$
			static final String CURRENT_VERSION = "1.0"; //$NON-NLS-1$
			static final String TAG_INI = "ini"; //$NON-NLS-1$
			static final String TAG_OPTION = "option"; //$NON-NLS-1$

			static final String TAG_SECTION = "section"; //$NON-NLS-1$

			public void endElement(final String uri, final String localName, final String qname) {
				if (qname.equals(TAG_SECTION))
					handler.endSection();
			}

			public void startElement(final String uri, final String localName, final String qname, final Attributes attrs) throws SAXException {
				final String key = attrs.getValue(ATTR_KEY);

				if (qname.equals(TAG_INI)) {
					final String ver = attrs.getValue(ATTR_VERSION);

					if (ver == null || !ver.equals(CURRENT_VERSION))
						throw new SAXException("Missing or invalid 'version' attribute"); //$NON-NLS-1$
				} else {
					if (key == null)
						throw new SAXException("missing '" + ATTR_KEY + "' attribute"); //$NON-NLS-1$ //$NON-NLS-2$

					if (qname.equals(TAG_SECTION))
						handler.startSection(key);
					else if (qname.equals(TAG_OPTION))
						handler.handleOption(key, attrs.getValue(ATTR_VALUE));
					else
						throw new SAXException("Invalid element: " + qname); //$NON-NLS-1$
				}
			}
		}

		final XML2Ini xml2ini = new XML2Ini();

		try {
			SAXParserFactory.newInstance().newSAXParser().parse(new InputSource(input), xml2ini);
		} catch (final Exception x) {
			throw new InvalidIniFormatException(x);
		}
	}

	public void parseXML(final URL input, final IniHandler handler) throws IOException, InvalidIniFormatException {
		parseXML(input.openStream(), handler);
	}

	protected String unescape(final String line) {
		return Convert.unescape(line);
	}
}
