package org.eclipse.php.ui.wizards;

import org.eclipse.swt.widgets.Composite;

/**
 * A WizardFragment that always hold a Composite.
 * 
 */
public abstract class CompositeWizardFragment extends WizardFragment {

	/**
	 * Constructs a new CompositeWizardFragment.
	 * 
	 */
	public CompositeWizardFragment() {
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.wst.server.ui.wizard.WizardFragment#hasComposite()
	 */
	public boolean hasComposite() {
		return true;
	}

	/**
	 * Returns the composite that was last created by this fragment.
	 * @return A Composite (Null if the createComposite was not called yet)
	 */
	public abstract Composite getComposite();
}