/**
 * 
 */
package org.eclipse.php.internal.core.util.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.ui.preferences.IWorkingCopyManager;
import org.osgi.service.prefs.BackingStoreException;

public final class Key {

	private String fQualifier;
	private String fKey;

	public Key(String qualifier, String key) {
		fQualifier = qualifier;
		fKey = key;
	}

	public String getName() {
		return fKey;
	}

	private IEclipsePreferences getNode(IScopeContext context, IWorkingCopyManager manager) {
		IEclipsePreferences node = context.getNode(fQualifier);
		if (manager != null) {
			return manager.getWorkingCopy(node);
		}
		return node;
	}

	public String getStoredValue(IScopeContext context, IWorkingCopyManager manager) {
		// fixed bug 197125
		// check if the node preferences existence before get its keys
		IEclipsePreferences node = getNode(context, manager);
		try {
			if (node.nodeExists("")) { //$NON-NLS-1$
				return node.get(fKey, null);
			}
		} catch (BackingStoreException e) {
		}
		return null;
	}

	public String getStoredValue(IScopeContext[] lookupOrder, boolean ignoreTopScope, IWorkingCopyManager manager) {
		for (int i = ignoreTopScope ? 1 : 0; i < lookupOrder.length; i++) {
			String value = getStoredValue(lookupOrder[i], manager);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	public void setStoredValue(IScopeContext context, String value, IWorkingCopyManager manager) {
		if (value != null) {
			getNode(context, manager).put(fKey, value);
		} else {
			getNode(context, manager).remove(fKey);
		}
	}

	public void setStoredValue(IScopeContext[] context, String value, IWorkingCopyManager manager) {
		if (value != null) {
			getNode(context[0], manager).put(fKey, value);
		} else {
			getNode(context[0], manager).remove(fKey);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return fQualifier + '/' + fKey;
	}

	public String getQualifier() {
		return fQualifier;
	}

}