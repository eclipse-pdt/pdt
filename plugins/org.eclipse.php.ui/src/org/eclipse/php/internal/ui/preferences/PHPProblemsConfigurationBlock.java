/*******************************************************************************
 * Copyright (c) 2017 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.compiler.problem.DefaultProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPProblemsConfigurationBlock extends OptionsConfigurationBlock {

	private Composite fieldEditorParent;
	private Combo[] fields;
	private PixelConverter pixelConverter;
	private static SortedMap<String, Category> model;

	public PHPProblemsConfigurationBlock(IStatusChangeListener context, IProject project,
			IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);
	}

	private static class Category {
		private String id;

		private String name;

		private int priority;

		private List<ConcreteProblem> problems = new ArrayList<ConcreteProblem>();

		public Category(String id, String name, int priority) {
			this.id = id;
			this.name = name;
			this.priority = priority;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Category && ((Category) obj).id.equals(id)) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return id.hashCode();
		}

	}

	private static class ConcreteProblem {
		private IProblemIdentifier identifier;

		private String name;

		private String qualifier;

		public ConcreteProblem(IProblemIdentifier identifier, String name) {
			this.name = name;
			this.identifier = identifier;
			this.qualifier = PHPCorePlugin.getDefault().getProblemPreferences().getPreferenceQualifier(identifier);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof ConcreteProblem && ((ConcreteProblem) obj).identifier == identifier) {
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return identifier.hashCode();
		}
	}

	private static void loadModel() {
		List<Category> categories = new ArrayList<>();
		for (IConfigurationElement element : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PreferenceConstants.PROBLEM_CONFIGURATION_EXTENSION_POINT)) {
			if (PreferenceConstants.PROBLEM_CONFIGURATION_CATEGORY.equals(element.getName())) {
				categories.add(new Category(element.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_ID),
						element.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_NAME),
						Integer.valueOf(element.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_PRIORITY))));
			}
		}
		categories.sort(new Comparator<Category>() {

			@Override
			public int compare(Category o1, Category o2) {
				return Integer.compare(o1.priority, o2.priority);
			}
		});
		model = new TreeMap<String, Category>();
		categories.forEach((v) -> model.put(v.id, v));
		for (IConfigurationElement element : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PreferenceConstants.PROBLEM_CONFIGURATION_EXTENSION_POINT)) {
			if (PreferenceConstants.PROBLEM_CONFIGURATION_PROBLEM.equals(element.getName())) {
				Category category = model.get(element.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_CATEGORY));
				if (category == null) {
					PHPUiPlugin.logErrorMessage("Category not found: " //$NON-NLS-1$
							+ element.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_CATEGORY));
					continue;
				}
				for (IConfigurationElement identifier : element
						.getChildren(PreferenceConstants.PROBLEM_CONFIGURATION_IDENTIFIER)) {
					String id = new StringBuilder(element.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_CLASS))
							.append('#').append(identifier.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_ID))
							.toString();
					IProblemIdentifier ident = DefaultProblemIdentifier.decode(id);
					category.problems.add(new ConcreteProblem(ident,
							identifier.getAttribute(PreferenceConstants.PROBLEM_CONFIGURATION_NAME)));
				}
			}

		}
	}

	private static Key[] getKeys() {
		loadModel();
		List<Key> res = new ArrayList<>();
		for (Category cat : model.values()) {
			for (ConcreteProblem problem : cat.problems) {
				res.add(new Key(problem.qualifier, PHPCoreConstants.SEVERITY));
			}
		}
		return res.toArray(new Key[res.size()]);
	}

	@Override
	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		String title = PHPUIMessages.PHPProblemsConfigurationBlock_needsbuild_title;
		String message;
		if (workspaceSettings) {
			message = PHPUIMessages.PHPProblemsConfigurationBlock_needsfullbuild_message;
		} else {
			message = PHPUIMessages.PHPProblemsConfigurationBlock_needsprojectbuild_message;
		}
		return new String[] { title, message };
	}

	protected String[] getSeverityLabels() {
		return new String[] { PHPUIMessages.PHPProblemsConfigurationBlock_error,
				PHPUIMessages.PHPProblemsConfigurationBlock_warning, PHPUIMessages.PHPProblemsConfigurationBlock_info,
				PHPUIMessages.PHPProblemsConfigurationBlock_ignore };
	}

	protected String[] getSeverityValues() {
		return new String[] { ProblemSeverity.ERROR.toString(), ProblemSeverity.WARNING.toString(),
				ProblemSeverity.INFO.toString(), ProblemSeverity.IGNORE.toString() };
	}

	public boolean hasProjectSpecificOptions(IProject project) {
		if (project != null) {
			return PHPCorePlugin.getDefault().getProblemPreferences().hasProjectSettings(project);
		}

		return false;
	}

	public void useProjectSpecificSettings(boolean enable) {
		if (enable) {
			new ProjectScope(fProject).getNode(PHPCorePlugin.ID).node(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID)
					.putBoolean(PHPCoreConstants.ENABLED, true);
		} else {
			new ProjectScope(fProject).getNode(PHPCorePlugin.ID).node(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID)
					.remove(PHPCoreConstants.ENABLED);
		}
		super.useProjectSpecificSettings(enable);
	}

	private Composite createFields(Composite parent) {
		Composite fieldEditorParentWrap = new Composite(parent, SWT.NULL);
		GridLayout wrapLayout = new GridLayout();
		wrapLayout.marginHeight = 0;
		wrapLayout.marginWidth = 0;
		fieldEditorParentWrap.setLayout(wrapLayout);
		fieldEditorParentWrap.setFont(parent.getFont());

		fieldEditorParent = new Composite(fieldEditorParentWrap, SWT.NULL);
		fieldEditorParent.setLayout(new GridLayout());
		fieldEditorParent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fieldEditorParent.setFont(parent.getFont());

		final Label horizontalLine = new Label(fieldEditorParent, SWT.SEPARATOR | SWT.HORIZONTAL);
		horizontalLine.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		horizontalLine.setFont(fieldEditorParent.getFont());

		fields = new Combo[fAllKeys.length];
		int i = 0;
		for (Category category : model.values()) {
			if (category.problems.size() == 0) {
				continue;
			}
			ExpandableComposite group = createGroup(1, fieldEditorParent, category.name);
			Composite inner = new Composite(group, SWT.NONE);
			inner.setFont(parent.getFont());
			inner.setLayout(new GridLayout(3, false));
			inner.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setClient(inner);

			for (ConcreteProblem v : category.problems) {
				Combo combo = fields[i] = addComboBox(inner, v.name, new Key(v.qualifier, PHPCoreConstants.SEVERITY),
						getSeverityValues(), getSeverityLabels(), 0);
				Label object = (Label) fLabels.get(combo);
				object.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));
				i++;
			}
		}

		return fieldEditorParentWrap;
	}

	protected ExpandableComposite createGroup(int numColumns, Composite parent, String label) {
		ExpandableComposite excomposite = new ExpandableComposite(parent, SWT.NONE,
				ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT);
		excomposite.setText(label);
		excomposite.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		excomposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
		excomposite.setExpanded(true);
		excomposite.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				expandedStateChanged((ExpandableComposite) e.getSource());
			}
		});

		return excomposite;
	}

	@Override
	protected Control createContents(Composite parent) {
		if (pixelConverter == null) {
			pixelConverter = new PixelConverter(parent);
		}

		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new FillLayout(SWT.VERTICAL));

		createFields(content);

		return content;
	}

	@Override
	protected void validateSettings(Key changedKey, String oldValue, String newValue) {

	}
}
