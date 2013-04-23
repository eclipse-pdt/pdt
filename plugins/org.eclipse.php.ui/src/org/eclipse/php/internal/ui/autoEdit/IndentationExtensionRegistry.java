package org.eclipse.php.internal.ui.autoEdit;

import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.format.IIndentationStrategyExtension1;

public class IndentationExtensionRegistry {

	private static IndentationExtensionRegistry instance;

	private SortedMap<Integer, IIndentationStrategyExtension1> extensions = new TreeMap<Integer, IIndentationStrategyExtension1>();
	private static final String EXTENSION_ID = "org.eclipse.php.core.indentationStrategy";

	private IndentationExtensionRegistry() {

		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_ID);

		// first registered extension wins...
		for (IConfigurationElement element : elements) {

			try {
				IIndentationStrategyExtension1 extension = (IIndentationStrategyExtension1) element
						.createExecutableExtension("class");
				Integer priority = Integer.parseInt(element
						.getAttribute("priority"));
				extensions.put(priority, extension);
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
			}
		}
	}

	public static IndentationExtensionRegistry getInstance() {
		if (instance == null) {
			instance = new IndentationExtensionRegistry();
		}

		return instance;
	}

	public SortedMap<Integer, IIndentationStrategyExtension1> getExtensions() {
		return extensions;
	}

	public boolean hasExtensions() {
		return extensions.size() > 0;
	}
}
