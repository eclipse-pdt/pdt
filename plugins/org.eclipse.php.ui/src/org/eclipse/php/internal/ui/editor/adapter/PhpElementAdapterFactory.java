package org.eclipse.php.internal.ui.editor.adapter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.php.internal.ui.actions.filters.GenericActionFilter;
import org.eclipse.ui.IActionFilter;

/**
 * This adapter factory class is used to create a GenericActionFilter
 * when performing a right-click within the editor
 * @author yaronm
 */
public class PhpElementAdapterFactory implements IAdapterFactory {

	private static Map adapterType2Object = new HashMap(4);
	static {
		adapterType2Object.put(IActionFilter.class, new GenericActionFilter());
	}

	public PhpElementAdapterFactory() {
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		return adapterType2Object.get(adapterType);
	}

	public Class[] getAdapterList() {
		Class[] classArray = new Class[adapterType2Object.size()];
		adapterType2Object.entrySet().toArray(classArray);
		return classArray;
	}
}
