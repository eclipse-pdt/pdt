package org.eclipse.php.internal.formatter.core;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.formatter.core.profiles.ICodeFormatterPreferencesInitializer;

public class FormattingProfileRegistry {

	private static final String PROFILES_EXTENSION_POINT_ID = "profiles"; //$NON-NLS-1$

	private static final String PROFILE_PROPERTY = "profile"; //$NON-NLS-1$
	private static final String ID_PROPERTY = "id"; //$NON-NLS-1$
	private static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
	private static final String CLASS_PROPERTY = "class"; //$NON-NLS-1$

	private Map<String, FormattingProfile> map = new LinkedHashMap<>();

	public FormattingProfileRegistry() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(FormatterCorePlugin.PLUGIN_ID, PROFILES_EXTENSION_POINT_ID);
		for (int i = 0; i < elements.length; i++) {
			addProfile(elements[i]);
		}
	}

	private void addProfile(IConfigurationElement element) {
		if (!PROFILE_PROPERTY.equals(element.getName())) {
			return;
		}

		String id = element.getAttribute(ID_PROPERTY);
		String name = element.getAttribute(NAME_PROPERTY);
		if (element.getAttribute(CLASS_PROPERTY) != null) {
			try {
				ICodeFormatterPreferencesInitializer initializer = (ICodeFormatterPreferencesInitializer) element
						.createExecutableExtension(CLASS_PROPERTY);
				map.put(id, new FormattingProfile(id, name, initializer));
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

	public FormattingProfile getProfile(String id) {
		return map.get(id);
	}

	public Collection<FormattingProfile> getProfiles() {
		return map.values();
	}

}
