package org.eclipse.php.project.ui.wizards;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.ui.wizards.BasicPHPWizardPageExtended;

public class PHPWizardPagesRegistry {

	private static final String EXTENSION_POINT = "org.eclipse.php.ui.phpWizardPages"; //$NON-NLS-1$
	private static final String PAGE_ELEMENT = "page"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String TARGET_ID_ATTRIBUTE = "targetId"; //$NON-NLS-1$
	
	private Map pages = new HashMap();
	private static PHPWizardPagesRegistry instance = new PHPWizardPagesRegistry();
	
	private PHPWizardPagesRegistry() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT); //$NON-NLS-1$
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (PAGE_ELEMENT.equals(element.getName())) {
				String targetId = element.getAttribute(TARGET_ID_ATTRIBUTE);
				List elementsList = (List)pages.get(targetId);
				if (elementsList == null) {
					elementsList = new LinkedList();
				}
				elementsList.add(element);
				pages.put(targetId, elementsList);
			}
		}
	}
	
	/**
	 * Returns all pages contributed to the Wizard with specified ID through extension point <code>org.eclipse.php.ui.phpWizardPages</code>. 
	 * @param id Wizard id
	 * @return Array of {@link BasicPHPWizardPageExtended} pages, or <code>null</code> if no pages where contributed.
	 */
	public static BasicPHPWizardPageExtended[] getPages(String id) {
		final List elementsList = (List) instance.pages.get(id);
		if (elementsList != null) {
			final List pagesList = new LinkedList();
			SafeRunner.run(new SafeRunnable("Error creating pages for extension point " + EXTENSION_POINT) {
				public void run() throws Exception {
					Iterator i = elementsList.iterator();
					while (i.hasNext()) {
						IConfigurationElement element = (IConfigurationElement)i.next();
						pagesList.add(element.createExecutableExtension(CLASS_ATTRIBUTE));
					}
				}
			});
			return (BasicPHPWizardPageExtended[]) pagesList.toArray(new BasicPHPWizardPageExtended[pagesList.size()]);
		}
		return null;
	}
}
