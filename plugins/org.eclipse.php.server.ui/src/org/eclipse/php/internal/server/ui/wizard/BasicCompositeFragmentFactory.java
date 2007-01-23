package org.eclipse.php.internal.server.ui.wizard;

import org.eclipse.php.internal.server.ui.*;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.WizardFragment;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * Basic composite fragment factory supplies factory methods for creating basic WizardFragment page and 
 * basic ServerRuntimeComposite.
 */
public class BasicCompositeFragmentFactory implements ICompositeFragmentFactory {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.server.apache.ui.wizard.ICompositeFragmentFactory#createWizardFragment()
	 */
	public WizardFragment createWizardFragment() {
		return new ServerWizardFragment();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.server.apache.ui.wizard.ICompositeFragmentFactory#createComposite(org.eclipse.swt.widgets.Composite, org.eclipse.php.internal.server.apache.ui.IControlHandler)
	 */
	public CompositeFragment createComposite(Composite parent, IControlHandler controlHandler) {
		return new ServerCompositeFragment(parent, controlHandler, true);
	}

}
