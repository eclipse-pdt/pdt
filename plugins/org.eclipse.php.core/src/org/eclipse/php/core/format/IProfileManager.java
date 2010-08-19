package org.eclipse.php.core.format;

import org.eclipse.core.runtime.preferences.IScopeContext;

public interface IProfileManager {

	/**
	 * Get the profile for this profile id.
	 * 
	 * @param ID
	 *            The profile ID
	 * @return The profile with the given ID or <code>null</code>
	 */
	public abstract IProfile getProfile(String ID);

	/**
	 * Activate the selected profile, update all necessary options in
	 * preferences and save profiles to disk.
	 */
	public abstract void commitChanges(IScopeContext scopeContext);

	/**
	 * Get the currently selected profile.
	 * 
	 * @return The currently selected profile.
	 */
	public abstract IProfile getSelected();

	/**
	 * Set the selected profile. The profile must already be contained in this
	 * profile manager.
	 * 
	 * @param profile
	 *            The profile to select
	 */
	public abstract void setSelected(IProfile profile);

	/**
	 * Set the selected profile. The profile must already be contained in this
	 * profile manager.
	 * 
	 * @param profile
	 *            The profile to select
	 */
	public abstract void setSelected(String profileId);

	/**
	 * Check whether a user-defined profile in this profile manager already has
	 * this name.
	 * 
	 * @param name
	 *            The name to test for
	 * @return Returns <code>true</code> if a profile with the given name exists
	 */
	public abstract boolean containsName(String name);

}