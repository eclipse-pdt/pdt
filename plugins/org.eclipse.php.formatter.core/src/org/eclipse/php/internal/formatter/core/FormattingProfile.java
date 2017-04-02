package org.eclipse.php.internal.formatter.core;

import org.eclipse.php.formatter.core.profiles.ICodeFormatterPreferencesInitializer;

public class FormattingProfile {

	private String id;
	private String name;
	private ICodeFormatterPreferencesInitializer clazz;

	public FormattingProfile(String id, String name, ICodeFormatterPreferencesInitializer clazz) {
		this.id = id;
		this.name = name;
		this.clazz = clazz;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ICodeFormatterPreferencesInitializer getImplementation() {
		return clazz;
	}

}
