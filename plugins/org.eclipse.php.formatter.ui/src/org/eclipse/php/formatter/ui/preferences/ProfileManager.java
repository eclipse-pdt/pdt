/*******************************************************************************
 * Copyright (c) 2013, 2014 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.ui.preferences;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.core.format.IProfile;
import org.eclipse.php.core.format.IProfileManager;
import org.eclipse.php.formatter.core.CodeFormatterConstants;
import org.eclipse.php.formatter.core.profiles.CodeFormatterPreferences;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.formatter.ui.Logger;
import org.eclipse.php.internal.formatter.core.FormatterCorePlugin;
import org.eclipse.php.internal.formatter.core.FormattingProfile;
import org.eclipse.php.internal.formatter.core.FormattingProfileRegistry;
import org.eclipse.php.internal.ui.util.Messages;

/**
 * The model for the set of profiles which are available in the workbench.
 */
public class ProfileManager extends Observable implements IProfileManager {

	/**
	 * A prefix which is prepended to every ID of a user-defined profile, in
	 * order to differentiate it from a built-in profile.
	 */
	private final static String ID_PREFIX = "_"; //$NON-NLS-1$

	/**
	 * Represents a profile with a unique ID, a name and a map containing the
	 * code formatter settings.
	 */
	public static abstract class Profile implements Comparable<Object>, IProfile {

		public abstract String getName();

		public abstract Profile rename(String name, ProfileManager manager);

		public abstract Map<String, Object> getSettings();

		public abstract void setSettings(Map<String, Object> settings);

		public boolean hasEqualSettings(Map<String, Object> otherMap, List<String> allKeys) {
			Map<?, ?> settings = getSettings();
			for (Iterator<String> iter = allKeys.iterator(); iter.hasNext();) {
				String key = iter.next();
				Object other = otherMap.get(key);
				Object curr = settings.get(key);
				if (other == null) {
					if (curr != null) {
						return false;
					}
				} else if (!other.equals(curr)) {
					return false;
				}
			}
			return true;
		}

		public abstract boolean isProfileToSave();

		@Override
		public abstract String getID();

		public boolean isSharedProfile() {
			return false;
		}

		public boolean isBuiltInProfile() {
			return false;
		}
	}

	/**
	 * Represents a built-in profile. The state of a built-in profile cannot be
	 * changed after instantiation.
	 */
	public final static class BuiltInProfile extends Profile {
		private final String fName;
		private final String fID;
		private final Map<String, Object> fSettings;
		private final int fOrder;

		protected BuiltInProfile(String ID, String name, Map<String, Object> settings, int order) {
			fName = name;
			fID = ID;
			fSettings = settings;
			fOrder = order;
		}

		@Override
		public String getName() {
			return fName;
		}

		@Override
		public Profile rename(String name, ProfileManager manager) {
			final String trimmed = name.trim();
			CustomProfile newProfile = new CustomProfile(trimmed, fSettings);
			manager.addProfile(newProfile);
			return newProfile;
		}

		@Override
		public Map<String, Object> getSettings() {
			return fSettings;
		}

		@Override
		public void setSettings(Map<String, Object> settings) {
		}

		@Override
		public String getID() {
			return fID;
		}

		@Override
		public final int compareTo(Object o) {
			if (o instanceof BuiltInProfile) {
				return fOrder - ((BuiltInProfile) o).fOrder;
			}
			return -1;
		}

		@Override
		public boolean isProfileToSave() {
			return false;
		}

		@Override
		public boolean isBuiltInProfile() {
			return true;
		}

	}

	/**
	 * Represents a user-defined profile. A custom profile can be modified after
	 * instantiation.
	 */
	public static class CustomProfile extends Profile {
		private String fName;
		private Map<String, Object> fSettings;
		protected ProfileManager fManager;

		public CustomProfile(String name, Map<String, Object> settings) {
			fName = name;
			fSettings = settings;
		}

		@Override
		public String getName() {
			return fName;
		}

		@Override
		public Profile rename(String name, ProfileManager manager) {
			final String trimmed = name.trim();
			if (trimmed.equals(getName()))
				return this;

			String oldID = getID(); // remember old id before changing name
			fName = trimmed;

			manager.profileRenamed(this, oldID);
			return this;
		}

		@Override
		public Map<String, Object> getSettings() {
			return fSettings;
		}

		@Override
		public void setSettings(Map<String, Object> settings) {
			if (settings == null)
				throw new IllegalArgumentException();
			fSettings = settings;
			if (fManager != null) {
				fManager.profileChanged(this);
			}
		}

		@Override
		public String getID() {
			return ID_PREFIX + fName;
		}

		public void setManager(ProfileManager profileManager) {
			fManager = profileManager;
		}

		public ProfileManager getManager() {
			return fManager;
		}

		@Override
		public int compareTo(Object o) {
			if (o instanceof SharedProfile) {
				return -1;
			}
			if (o instanceof CustomProfile) {
				return getName().compareToIgnoreCase(((Profile) o).getName());
			}
			return 1;
		}

		@Override
		public boolean isProfileToSave() {
			return true;
		}

	}

	public final static class SharedProfile extends CustomProfile {

		public SharedProfile(String oldName, Map<String, Object> options) {
			super(oldName, options);
		}

		@Override
		public Profile rename(String name, ProfileManager manager) {
			CustomProfile profile = new CustomProfile(name.trim(), getSettings());

			manager.profileReplaced(this, profile);
			return profile;
		}

		@Override
		public String getID() {
			return SHARED_PROFILE;
		}

		@Override
		public final int compareTo(Object o) {
			return 1;
		}

		@Override
		public boolean isProfileToSave() {
			return false;
		}

		@Override
		public boolean isSharedProfile() {
			return true;
		}
	}

	/**
	 * The possible events for observers listening to this class.
	 */
	public final static int SELECTION_CHANGED_EVENT = 1;
	public final static int PROFILE_DELETED_EVENT = 2;
	public final static int PROFILE_RENAMED_EVENT = 3;
	public final static int PROFILE_CREATED_EVENT = 4;
	public final static int SETTINGS_CHANGED_EVENT = 5;

	/**
	 * The key of the preference where the selected profile is stored.
	 */
	private final static String PROFILE_KEY = CodeFormatterConstants.FORMATTER_PROFILE;

	public final static String SHARED_PROFILE = "org.eclipse.php.formatter.ui.default.shared"; //$NON-NLS-1$

	/**
	 * A map containing the available profiles, using the IDs as keys.
	 */
	private final Map<String, Profile> fProfiles;

	/**
	 * The available profiles, sorted by name.
	 */
	private final List<Profile> fProfilesByName;

	/**
	 * The currently selected profile.
	 */
	private Profile fSelected;

	/**
	 * All keys appearing in a profile, sorted alphabetically
	 */
	private final static List<String> fKeys;

	private static final Map<String, Object> EMPTY_MAP = Collections.emptyMap();;

	static {
		fKeys = new ArrayList<>(CodeFormatterPreferences.getDefaultPreferences().getMap().keySet());
		Collections.sort(fKeys);
	}

	/**
	 * Create and initialize a new profile manager.
	 * 
	 * @param profiles
	 *            Initial custom profiles (List of type
	 *            <code>CustomProfile</code>)
	 */
	public ProfileManager(List<Profile> profiles, IScopeContext context) {
		fProfiles = new HashMap<>();
		fProfilesByName = new ArrayList<>();

		addBuiltinProfiles(fProfiles, fProfilesByName);

		for (final Iterator<Profile> iter = profiles.iterator(); iter.hasNext();) {
			final CustomProfile profile = (CustomProfile) iter.next();
			profile.setManager(this);
			fProfiles.put(profile.getID(), profile);
			fProfilesByName.add(profile);
		}

		Collections.sort(fProfilesByName);

		IScopeContext instanceScope = InstanceScope.INSTANCE;
		String profileId = instanceScope.getNode(FormatterCorePlugin.PLUGIN_ID).get(PROFILE_KEY, null);
		if (profileId == null) {
			// request from bug 129427
			profileId = DefaultScope.INSTANCE.getNode(FormatterCorePlugin.PLUGIN_ID).get(PROFILE_KEY, null);
		}

		Profile profile = fProfiles.get(profileId);
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=530660
		// Assert.isNotNull(profile);
		if (profile == null) {
			Logger.log(Logger.ERROR, "ProfileManager: could not load profile " + profileId); //$NON-NLS-1$
		}

		fSelected = profile;

		if (fSelected != null && context.getName() == ProjectScope.SCOPE && hasProjectSpecificSettings(context)) {
			Map<String, Object> map = readFromPreferenceStore(context, profile);
			if (map != null) {
				Profile matching = null;

				String projProfileId = context.getNode(FormatterCorePlugin.PLUGIN_ID).get(PROFILE_KEY, null);
				if (projProfileId != null) {
					Profile curr = fProfiles.get(projProfileId);
					if (curr != null && (curr.isBuiltInProfile() || curr.hasEqualSettings(map, getKeys()))) {
						matching = curr;
					}
				} else {
					// old version: look for similar
					for (final Iterator<Profile> iter = fProfilesByName.iterator(); iter.hasNext();) {
						Profile curr = iter.next();
						if (curr.hasEqualSettings(map, getKeys())) {
							matching = curr;
							break;
						}
					}
				}
				if (matching == null) {
					String name;
					if (projProfileId != null && !fProfiles.containsKey(projProfileId)) {
						name = Messages.format(FormatterMessages.ProfileManager_unmanaged_profile_with_name,
								projProfileId.substring(ID_PREFIX.length()));
					} else {
						name = FormatterMessages.ProfileManager_unmanaged_profile;
					}
					// current settings do not correspond to any profile ->
					// create a 'team' profile
					SharedProfile shared = new SharedProfile(name, map);
					shared.setManager(this);
					fProfiles.put(shared.getID(), shared);
					fProfilesByName.add(shared); // add last
					matching = shared;
				}
				fSelected = matching;
			}
		}
	}

	/**
	 * Notify observers with a message. The message must be one of the
	 * following:
	 * 
	 * @param message
	 *            Message to send out
	 * 
	 * @see #SELECTION_CHANGED_EVENT
	 * @see #PROFILE_DELETED_EVENT
	 * @see #PROFILE_RENAMED_EVENT
	 * @see #PROFILE_CREATED_EVENT
	 * @see #SETTINGS_CHANGED_EVENT
	 */
	protected void notifyObservers(int message) {
		setChanged();
		notifyObservers(Integer.valueOf(message));
	}

	public static boolean hasProjectSpecificSettings(IScopeContext context) {
		IEclipsePreferences prefs = context.getNode(FormatterCorePlugin.PLUGIN_ID);
		return prefs.get(CodeFormatterConstants.FORMATTER_PROFILE, null) != null;
	}

	/**
	 * Only to read project specific settings to find out to what profile it
	 * matches.
	 * 
	 * @param context
	 *            The project context
	 */
	public Map<String, Object> readFromPreferenceStore(IScopeContext context, Profile workspaceProfile) {
		final Map<String, Object> profileOptions = new HashMap<>();
		IEclipsePreferences prefs = context.getNode(FormatterCorePlugin.PLUGIN_ID);

		boolean hasValues = false;

		for (final Iterator<String> keyIter = fKeys.iterator(); keyIter.hasNext();) {
			final String key = keyIter.next();
			Object val = prefs.get(key, null);
			if (val != null) {
				hasValues = true;
			} else {
				val = workspaceProfile.getSettings().get(key);
			}
			profileOptions.put(key, val);
		}

		if (!hasValues) {
			return null;
		}

		return profileOptions;
	}

	private boolean updatePreferences(IEclipsePreferences prefs, List<String> keys,
			Map<String, Object> profileOptions) {
		boolean hasChanges = false;
		for (final Iterator<String> keyIter = keys.iterator(); keyIter.hasNext();) {
			final String key = keyIter.next();
			final String oldVal = prefs.get(key, null);
			final String val = (String) profileOptions.get(key);
			if (val == null) {
				if (oldVal != null) {
					prefs.remove(key);
					hasChanges = true;
				}
			} else if (!val.equals(oldVal)) {
				prefs.put(key, val);
				hasChanges = true;
			}
		}
		return hasChanges;
	}

	/**
	 * Update all formatter settings with the settings of the specified profile.
	 * 
	 * @param profile
	 *            The profile to write to the preference store
	 */
	private void writeToPreferenceStore(Profile profile, IScopeContext context) {
		final Map<String, Object> profileOptions = profile.getSettings();

		final IEclipsePreferences prefs = context.getNode(FormatterCorePlugin.PLUGIN_ID);
		updatePreferences(prefs, fKeys, profileOptions);

		if (context.getName() == InstanceScope.SCOPE) {
			prefs.put(PROFILE_KEY, profile.getID());
		} else if (context.getName() == ProjectScope.SCOPE && !profile.isSharedProfile()) {
			prefs.put(PROFILE_KEY, profile.getID());
		}
	}

	/**
	 * Add all the built-in profiles to the map and to the list. PHP Default is
	 * loaded first and then profiles registered through
	 * org.eclipse.php.formatter.ui.profiles extension point
	 * 
	 * @param profiles
	 *            The map to add the profiles to
	 * @param profilesByName
	 *            List of profiles by
	 */
	private void addBuiltinProfiles(Map<String, Profile> profiles, List<Profile> profilesByName) {
		int order = 1;
		String builtinPostFix = FormatterMessages.ProfileManager_built_in_postfix;
		// final Profile phpProfile = new BuiltInProfile(PHP_PROFILE,
		// FormatterMessages.ProfileManager_php_conventions_profile_name +
		// builtinPostFix, getPhpSettings(),
		// order);
		// profiles.put(phpProfile.getID(), phpProfile);
		// profilesByName.add(phpProfile);

		Collection<FormattingProfile> elements = new FormattingProfileRegistry().getProfiles();
		for (FormattingProfile profile : elements) {
			CodeFormatterPreferences preferences = profile.getImplementation().initValues();
			final Profile extensionProfile = new BuiltInProfile(profile.getId(), profile.getName() + builtinPostFix,
					preferences.getMap(), ++order);
			profiles.put(extensionProfile.getID(), extensionProfile);
			profilesByName.add(extensionProfile);
		}
	}

	/**
	 * @return Returns the settings for the PHP Conventions profile.
	 */
	public static Map<String, Object> getPHPSettings() {
		final Map<String, Object> options = CodeFormatterPreferences.getDefaultPreferences().getMap();

		return options;
	}

	/**
	 * @return Returns the default settings.
	 */
	public static Map<String, Object> getDefaultSettings() {
		return getPHPSettings();
	}

	/**
	 * @return All keys appearing in a profile, sorted alphabetically.
	 */
	public static List<String> getKeys() {
		return fKeys;
	}

	/**
	 * Get an immutable list as view on all profiles, sorted alphabetically.
	 * Unless the set of profiles has been modified between the two calls, the
	 * sequence is guaranteed to correspond to the one returned by
	 * <code>getSortedNames</code>.
	 * 
	 * @return a list of elements of type <code>Profile</code>
	 * 
	 * @see #getSortedDisplayNames()
	 */
	public List<Profile> getSortedProfiles() {
		return Collections.unmodifiableList(fProfilesByName);
	}

	/**
	 * Get the names of all profiles stored in this profile manager, sorted
	 * alphabetically. Unless the set of profiles has been modified between the
	 * two calls, the sequence is guaranteed to correspond to the one returned
	 * by <code>getSortedProfiles</code>.
	 * 
	 * @return All names, sorted alphabetically
	 * @see #getSortedProfiles()
	 */
	public String[] getSortedDisplayNames() {
		final String[] sortedNames = new String[fProfilesByName.size()];
		int i = 0;
		for (final Iterator<Profile> iter = fProfilesByName.iterator(); iter.hasNext();) {
			Profile curr = iter.next();
			sortedNames[i++] = curr.getName();
		}
		return sortedNames;
	}

	/**
	 * Get the profile for this profile id.
	 * 
	 * @param ID
	 *            The profile ID
	 * @return The profile with the given ID or <code>null</code>
	 */
	@Override
	public Profile getProfile(String ID) {
		return fProfiles.get(ID);
	}

	/**
	 * Activate the selected profile, update all necessary options in
	 * preferences and save profiles to disk.
	 */
	@Override
	public void commitChanges(IScopeContext scopeContext) {
		if (fSelected != null) {
			writeToPreferenceStore(fSelected, scopeContext);
		}
	}

	public void clearAllSettings(IScopeContext context) {
		final IEclipsePreferences prefs = context.getNode(FormatterCorePlugin.PLUGIN_ID);
		updatePreferences(prefs, fKeys, EMPTY_MAP);

		prefs.remove(PROFILE_KEY);
	}

	/**
	 * Get the currently selected profile.
	 * 
	 * @return The currently selected profile.
	 */
	@Override
	public Profile getSelected() {
		return fSelected;
	}

	/**
	 * Set the selected profile. The profile must already be contained in this
	 * profile manager.
	 * 
	 * @param profile
	 *            The profile to select
	 */
	@Override
	public void setSelected(IProfile profile) {
		setSelected(profile.getID());
	}

	/**
	 * Set the selected profile. The profile must already be contained in this
	 * profile manager.
	 * 
	 * @param profile
	 *            The profile to select
	 */
	@Override
	public void setSelected(String profileId) {
		final Profile newSelected = fProfiles.get(profileId);
		if (newSelected != null) {
			fSelected = newSelected;
			notifyObservers(SELECTION_CHANGED_EVENT);
		}
	}

	/**
	 * Check whether a user-defined profile in this profile manager already has
	 * this name.
	 * 
	 * @param name
	 *            The name to test for
	 * @return Returns <code>true</code> if a profile with the given name exists
	 */
	@Override
	public boolean containsName(String name) {
		for (final Iterator<Profile> iter = fProfilesByName.iterator(); iter.hasNext();) {
			Profile curr = iter.next();
			if (name.equals(curr.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add a new custom profile to this profile manager.
	 * 
	 * @param profile
	 *            The profile to add
	 */
	public void addProfile(CustomProfile profile) {
		profile.setManager(this);
		final CustomProfile oldProfile = (CustomProfile) fProfiles.get(profile.getID());
		if (oldProfile != null) {
			fProfiles.remove(oldProfile.getID());
			fProfilesByName.remove(oldProfile);
			oldProfile.setManager(null);
		}
		fProfiles.put(profile.getID(), profile);
		fProfilesByName.add(profile);
		Collections.sort(fProfilesByName);
		fSelected = profile;
		notifyObservers(PROFILE_CREATED_EVENT);
	}

	/**
	 * Delete the currently selected profile from this profile manager. The next
	 * profile in the list is selected.
	 * 
	 * @return true if the profile has been successfully removed, false
	 *         otherwise.
	 */
	public boolean deleteSelected() {
		if (!(fSelected instanceof CustomProfile))
			return false;

		Profile removedProfile = fSelected;

		int index = fProfilesByName.indexOf(removedProfile);

		fProfiles.remove(removedProfile.getID());
		fProfilesByName.remove(removedProfile);

		((CustomProfile) removedProfile).setManager(null);

		if (index >= fProfilesByName.size())
			index--;
		fSelected = fProfilesByName.get(index);

		if (!removedProfile.isSharedProfile()) {
			updateProfilesWithName(removedProfile.getID(), null, false);
		}

		notifyObservers(PROFILE_DELETED_EVENT);
		return true;
	}

	public void profileRenamed(CustomProfile profile, String oldID) {
		fProfiles.remove(oldID);
		fProfiles.put(profile.getID(), profile);

		if (!profile.isSharedProfile()) {
			updateProfilesWithName(oldID, profile, false);
		}

		Collections.sort(fProfilesByName);
		notifyObservers(PROFILE_RENAMED_EVENT);
	}

	public void profileReplaced(CustomProfile oldProfile, CustomProfile newProfile) {
		fProfiles.remove(oldProfile.getID());
		fProfiles.put(newProfile.getID(), newProfile);
		fProfilesByName.remove(oldProfile);
		fProfilesByName.add(newProfile);
		Collections.sort(fProfilesByName);

		if (!oldProfile.isSharedProfile()) {
			updateProfilesWithName(oldProfile.getID(), null, false);
		}

		setSelected(newProfile);
		notifyObservers(PROFILE_CREATED_EVENT);
		notifyObservers(SELECTION_CHANGED_EVENT);
	}

	public void profileChanged(CustomProfile profile) {
		if (!profile.isSharedProfile()) {
			updateProfilesWithName(profile.getID(), profile, true);
		}

		notifyObservers(SETTINGS_CHANGED_EVENT);
	}

	private void updateProfilesWithName(String oldName, Profile newProfile, boolean applySettings) {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			IScopeContext projectScope = new ProjectScope(projects[i]);
			IEclipsePreferences node = projectScope.getNode(FormatterCorePlugin.PLUGIN_ID);
			String profileId = node.get(PROFILE_KEY, null);
			if (oldName.equals(profileId)) {
				if (newProfile == null) {
					node.remove(PROFILE_KEY);
				} else {
					if (applySettings) {
						writeToPreferenceStore(newProfile, projectScope);
					} else {
						node.put(PROFILE_KEY, newProfile.getID());
					}
				}
			}
		}

		IScopeContext instanceScope = InstanceScope.INSTANCE;
		final IEclipsePreferences uiPrefs = instanceScope.getNode(FormatterCorePlugin.PLUGIN_ID);
		if (newProfile != null && oldName.equals(uiPrefs.get(PROFILE_KEY, null))) {
			writeToPreferenceStore(newProfile, instanceScope);
		}
	}

	public Profile getDefaultProfile() {
		String profileId = DefaultScope.INSTANCE.getNode(FormatterCorePlugin.PLUGIN_ID).get(PROFILE_KEY, null);
		if (profileId == null) {
			return null;
		}
		return getProfile(profileId);
	}

}
