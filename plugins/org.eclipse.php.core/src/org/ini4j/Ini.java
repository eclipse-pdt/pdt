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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ini extends LinkedHashMap/*<String,Ini.Section>*/
{
	class BeanInvocationHandler extends AbstractBeanInvocationHandler {
		private final Map/*<String,Object>*/_sectionBeans = new HashMap/*<String, Object>*/();

		protected Object getPropertySpi(final String property, final Class/*<?>*/clazz) {
			Object o = _sectionBeans.get(property);

			if (o == null) {
				final Section section = (Section) get(property);

				if (section != null) {
					o = section.to(clazz);
					_sectionBeans.put(property, o);
				}
			}

			return o;
		}

		protected boolean hasPropertySpi(final String property) {
			return false;
		}

		protected void setPropertySpi(final String property, final Object value, final Class/*<?>*/clazz) {
			throw new UnsupportedOperationException("read only bean"); //$NON-NLS-1$
		}
	}
	class Builder implements IniHandler {
		private Section currentSection;

		public void endIni() {
			;
		}

		public void endSection() {
			currentSection = null;
		}

		public void handleOption(final String name, final String value) {
			currentSection.put(name, value);
		}

		public void startIni() {
			;
		}

		public void startSection(final String sectionName) {
			final Section s = (Section) get(sectionName);
			currentSection = s != null ? s : add(sectionName);
		}
	}
	public class Section extends LinkedHashMap/*<String,String>*/
	{
		class BeanInvocationHandler extends AbstractBeanInvocationHandler {
			protected Object getPropertySpi(final String property, final Class/*<?>*/clazz) {
				return fetch(property);
			}

			protected boolean hasPropertySpi(final String property) {
				return containsKey(property);
			}

			protected void setPropertySpi(final String property, final Object value, final Class/*<?>*/clazz) {
				put(property, value.toString());
			}
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -7065562990685327173L;
		private Map/*<Class,Object>*/_beans;

		private String _name;

		public Section(final String name) {
			super();
			_name = name;
		}

		public String fetch(final Object key) {
			String value = (String) get(key);

			if (value != null && value.indexOf(SUBST_CHAR) >= 0) {
				final StringBuffer buffer = new StringBuffer(value);
				resolve(buffer, this);
				value = buffer.toString();
			}
			return value;
		}

		public String getName() {
			return _name;
		}

		public synchronized/*<T> T*/Object to(final Class/*<T>*/clazz) {
			Object bean;

			if (_beans == null) {
				_beans = new HashMap/*<Class,Object>*/();
				bean = null;
			} else
				bean = _beans.get(clazz);

			if (bean == null) {
				bean = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { clazz }, new BeanInvocationHandler());
				_beans.put(clazz, bean);
			}
			return bean;
			//			return clazz.cast(bean);
		}
	}

	static public int FORCE_QUOTES = 1 << 2;
	static public int IGNORE_ESCAPE = 1;
	private static final String OPERATOR = " " + IniParser.OPERATOR + " "; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * 
	 */
	private static final long serialVersionUID = -2366249958968147183L;
	static public int STRIP_QUOTES = 1 << 1;
	private static final char SUBST_CHAR = '$';
	private static final String SUBST_BEGIN = SUBST_CHAR + "{"; //$NON-NLS-1$
	private static final int SUBST_BEGIN_LEN = SUBST_BEGIN.length();
	private static final String SUBST_END = "}"; //$NON-NLS-1$
	private static final int SUBST_END_LEN = SUBST_END.length();
	private static final String SUBST_ENVIRONMENT = "@env"; //$NON-NLS-1$

	private static final char SUBST_ESCAPE = '\\';

	private static final String SUBST_PROPERTY = "@prop"; //$NON-NLS-1$

	private static final char SUBST_SEPARATOR = '/';

	private Map/*<Class,Object>*/_beans;

	public Ini() {
		;
	}

	public Ini(final InputStream input, final int mode) throws IOException, InvalidIniFormatException {
		this();
		load(input, mode);
	}

	public Ini(final Reader input, final int mode) throws IOException, InvalidIniFormatException {
		this();
		load(input, mode);
	}

	public Ini(final URL input, final int mode) throws IOException, InvalidIniFormatException {
		this();
		load(input, mode);
	}

	public Section add(final String name) {
		final Section s = new Section(name);
		put(name, s);
		return s;
	}

	public void load(final InputStream input, final int mode) throws IOException, InvalidIniFormatException {
		load(new InputStreamReader(input), mode);
	}

	public void load(final Reader input, final int mode) throws IOException, InvalidIniFormatException {
		final Builder builder = new Builder();
		IniParser.newInstance().parse(input, builder, mode);
	}

	public void load(final URL input, final int mode) throws IOException, InvalidIniFormatException {
		final Builder builder = new Builder();
		IniParser.newInstance().parse(input, builder, mode);
	}

	public void loadFromXML(final InputStream input) throws IOException, InvalidIniFormatException {
		loadFromXML(new InputStreamReader(input));
	}

	public void loadFromXML(final Reader input) throws IOException, InvalidIniFormatException {
		final Builder builder = new Builder();
		IniParser.newInstance().parseXML(input, builder);
	}

	public void loadFromXML(final URL input) throws IOException, InvalidIniFormatException {
		final Builder builder = new Builder();
		IniParser.newInstance().parseXML(input, builder);
	}

	public Section remove(final Section section) {
		return (Section) remove(section.getName());
	}

	protected void resolve(final StringBuffer buffer, Section owner) {
		int begin = -1;
		int end = -1;

		for (int i = buffer.indexOf(SUBST_BEGIN); i >= 0; i = buffer.indexOf(SUBST_BEGIN, i + 1)) {
			if (i + 2 > buffer.length())
				break;

			if (i != 0 && buffer.charAt(i - 1) == SUBST_ESCAPE)
				continue;

			begin = i;

			end = buffer.indexOf(SUBST_END, i);

			if (end < 0)
				break;

			if (begin >= 0 && end > 0) {
				String var = buffer.substring(begin + SUBST_BEGIN_LEN, end);
				String group = null;
				final int sep = var.indexOf(SUBST_SEPARATOR);
				String value = null;

				if (sep > 0) {
					group = var.substring(0, sep);
					var = var.substring(sep + 1);
				}

				if (var != null)
					if (group == null)
						value = owner.fetch(var);
					else if (SUBST_ENVIRONMENT.equals(group))
						value = System.getenv(var);
					else if (SUBST_PROPERTY.equals(group))
						value = System.getProperty(var);
					else {
						owner = (Section) get(group);

						if (owner != null)
							value = owner.fetch(var);
					}

				if (value != null)
					buffer.replace(begin, end + SUBST_END_LEN, value);
			}
		}
	}

	public void store(final OutputStream output, final int mode) {
		store(new OutputStreamWriter(output), mode);
	}

	public void store(final Writer output, final int mode) {
		final PrintWriter pr = new PrintWriter(output);

		final Iterator i = values().iterator();
		Ini.Section s;
		for (; i.hasNext();)
		//        for(Ini.Section s : values())
		{
			s = (Ini.Section) i.next();
			String sectionName = s.getName();

			if (!sectionName.equals(IniParser.DEFAULT_SECTION_NAME)) {
				pr.print(IniParser.SECTION_BEGIN);
				if ((mode & IGNORE_ESCAPE) == 0)
					sectionName = Convert.escape(sectionName);
				pr.print(sectionName);
				pr.println(IniParser.SECTION_END);
			}

			final Iterator j = s.entrySet().iterator();
			Map.Entry e;
			for (; j.hasNext();)
			//            for(Map.Entry<String,String> e : s.entrySet())
			{
				e = (Map.Entry) j.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				if ((mode & IGNORE_ESCAPE) == 0) {
					key = Convert.escape(key);
					value = Convert.escape(value);
				}
				if ((mode & FORCE_QUOTES) > 0)
					value = "\"" + value + "\""; //$NON-NLS-1$ //$NON-NLS-2$
				pr.print(key);
				pr.print(OPERATOR);
				pr.print(value);
				pr.println();
			}

			pr.println();
		}
		pr.flush();
	}

	public void storeToXML(final OutputStream output) {
		storeToXML(new OutputStreamWriter(output));
	}

	public void storeToXML(final Writer output) {
		final PrintWriter pr = new PrintWriter(output);

		pr.println("<ini version='1.0'>"); //$NON-NLS-1$

		final Iterator i = values().iterator();
		Ini.Section s;
		for (; i.hasNext();)
		//        for(Ini.Section s : values())
		{
			s = (Ini.Section) i.next();
			pr.print(" <section key='"); //$NON-NLS-1$
			pr.print(s.getName());
			pr.println("'>"); //$NON-NLS-1$

			final Iterator j = s.entrySet().iterator();
			Map.Entry e;
			for (; j.hasNext();)
			//            for(Map.Entry<String,String> e : s.entrySet())
			{
				e = (Map.Entry) j.next();
				pr.print("  <option key='"); //$NON-NLS-1$
				pr.print((String) e.getKey());
				pr.print("' value='"); //$NON-NLS-1$
				pr.print((String) e.getValue());
				pr.println("'/>"); //$NON-NLS-1$
			}

			pr.println(" </section>"); //$NON-NLS-1$
		}

		pr.println("</ini>"); //$NON-NLS-1$
		pr.flush();
	}

	public/*<T> T*/Object to(final Class/*<T>*/clazz) {
		Object bean;

		if (_beans == null) {
			_beans = new HashMap/*<Class,Object>*/();
			bean = null;
		} else
			bean = _beans.get(clazz);

		if (bean == null) {
			bean = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { clazz }, new BeanInvocationHandler());
			_beans.put(clazz, bean);
		}
		return bean;
		//		return clazz.cast(bean);
	}

}
