package org.eclipse.php.server.ui.wizard;

import org.eclipse.php.server.internal.ui.ServerWizardFragment;
import org.eclipse.php.server.ui.*;
import org.eclipse.php.ui.wizards.CompositeFragment;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.php.ui.wizards.IControlHandler;
import org.eclipse.php.ui.wizards.WizardFragment;
import org.eclipse.swt.widgets.Composite;

/**
 * Basic composite fragment factory supplies factory methods for creating basic WizardFragment page and 
 * basic ServerRuntimeComposite.
 */
public class BasicCompositeFragmentFactory implements ICompositeFragmentFactory {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.server.apache.ui.wizard.ICompositeFragmentFactory#createWizardFragment()
	 */
	public WizardFragment createWizardFragment() {
		return new ServerWizardFragment();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.server.apache.ui.wizard.ICompositeFragmentFactory#createComposite(org.eclipse.swt.widgets.Composite, org.eclipse.php.server.apache.ui.IControlHandler)
	 */
	public CompositeFragment createComposite(Composite parent, IControlHandler controlHandler) {
		return new ServerCompositeFragment(parent, controlHandler, true);
	}

}
