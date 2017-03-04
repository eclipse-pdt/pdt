/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.dialogs;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.core.search.indexing.IIndexConstants;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.corext.util.OpenTypeHistory;
import org.eclipse.dltk.internal.corext.util.TypeFilter;
import org.eclipse.dltk.internal.corext.util.TypeInfoRequestorAdapter;
import org.eclipse.dltk.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.internal.ui.dialogs.TextFieldNavigationHandler;
import org.eclipse.dltk.internal.ui.search.DLTKSearchScopeFactory;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetFilterActionGroup;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.IInterpreterInstallType;
import org.eclipse.dltk.launching.LibraryLocation;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.ui.DLTKUILanguageManager;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.dialogs.ITypeInfoFilterExtension;
import org.eclipse.dltk.ui.dialogs.ITypeInfoImageProvider;
import org.eclipse.dltk.ui.dialogs.ITypeSelectionComponent;
import org.eclipse.dltk.ui.dialogs.TypeSelectionExtension;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.contentassist.BoldStylerProvider;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.search.PHPSearchTypeNameMatch;
import org.eclipse.php.internal.ui.PHPUILanguageToolkit;
import org.eclipse.php.internal.ui.corext.util.Strings;
import org.eclipse.php.internal.ui.corext.util.TypeInfoFilter;
import org.eclipse.php.internal.ui.util.PHPTypeNameMatchLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SearchPattern;

/**
 * Shows a list of Java types to the user with a text entry field for a string
 * pattern used to filter the list of types.
 * 
 * @since 5.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FilteredTypesSelectionDialog extends FilteredItemsSelectionDialog implements ITypeSelectionComponent {

	private static final String DIALOG_SETTINGS = "org.eclipse.php.internal.ui.dialogs.FilteredTypesSelectionDialog"; //$NON-NLS-1$

	private static final String WORKINGS_SET_SETTINGS = "WorkingSet"; //$NON-NLS-1$

	private WorkingSetFilterActionGroup fFilterActionGroup;

	private final TypeItemLabelProvider fTypeInfoLabelProvider;

	private String fTitle;

	private IDLTKSearchScope fSearchScope;

	private boolean fAllowScopeSwitching;

	private final int fElementKinds;

	private final ITypeInfoFilterExtension fFilterExtension;

	private final TypeSelectionExtension fExtension;

	private ISelectionStatusValidator fValidator;

	private final TypeInfoUtil fTypeInfoUtil;

	private static boolean fgFirstTime = true;

	private final TypeItemsComparator fTypeItemsComparator;

	private int fTypeFilterVersion = 0;

	private IDLTKLanguageToolkit fToolkit;

	private TypeItemsFilter fFilter;

	/**
	 * Creates new FilteredTypesSelectionDialog instance
	 * 
	 * @param parent
	 *            shell to parent the dialog on
	 * @param multi
	 *            <code>true</code> if multiple selection is allowed
	 * @param context
	 *            context used to execute long-running operations associated
	 *            with this dialog
	 * @param scope
	 *            scope used when searching for types
	 * @param elementKinds
	 *            flags defining nature of searched elements; the only valid
	 *            values are: <code>IJavaSearchConstants.TYPE</code>
	 *            <code>IJavaSearchConstants.ANNOTATION_TYPE</code>
	 *            <code>IJavaSearchConstants.INTERFACE</code>
	 *            <code>IJavaSearchConstants.ENUM</code>
	 *            <code>IJavaSearchConstants.CLASS_AND_INTERFACE</code>
	 *            <code>IJavaSearchConstants.CLASS_AND_ENUM</code>. Please note
	 *            that the bitwise OR combination of the elementary constants is
	 *            not supported.
	 */
	public FilteredTypesSelectionDialog(Shell parent, boolean multi, IRunnableContext context, IDLTKSearchScope scope,
			int elementKinds) {
		this(parent, multi, context, scope, elementKinds, null);
	}

	/**
	 * Creates new FilteredTypesSelectionDialog instance.
	 * 
	 * @param shell
	 *            shell to parent the dialog on
	 * @param multi
	 *            <code>true</code> if multiple selection is allowed
	 * @param context
	 *            context used to execute long-running operations associated
	 *            with this dialog
	 * @param scope
	 *            scope used when searching for types. If the scope is
	 *            <code>null</code>, then workspace is scope is used as default,
	 *            and the user can choose a working set as scope.
	 * @param elementKinds
	 *            flags defining nature of searched elements; the only valid
	 *            values are: <code>IJavaSearchConstants.TYPE</code>
	 *            <code>IJavaSearchConstants.ANNOTATION_TYPE</code>
	 *            <code>IJavaSearchConstants.INTERFACE</code>
	 *            <code>IJavaSearchConstants.ENUM</code>
	 *            <code>IJavaSearchConstants.CLASS_AND_INTERFACE</code>
	 *            <code>IJavaSearchConstants.CLASS_AND_ENUM</code>. Please note
	 *            that the bitwise OR combination of the elementary constants is
	 *            not supported.
	 * @param extension
	 *            an extension of the standard type selection dialog; See
	 *            {@link TypeSelectionExtension}
	 */
	public FilteredTypesSelectionDialog(Shell shell, boolean multi, IRunnableContext context, IDLTKSearchScope scope,
			int elementKinds, TypeSelectionExtension extension) {
		super(shell, multi);

		this.fToolkit = PHPLanguageToolkit.getDefault();

		setSelectionHistory(new TypeSelectionHistory());

		if (scope == null) {
			fAllowScopeSwitching = true;
			scope = SearchEngine.createWorkspaceScope(fToolkit);
		}
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
		// IJavaHelpContextIds.TYPE_SELECTION_DIALOG2);

		fElementKinds = elementKinds;
		fExtension = extension;
		fFilterExtension = (extension == null) ? null : extension.getFilterExtension();
		fSearchScope = scope;

		if (extension != null) {
			fValidator = extension.getSelectionValidator();
		}

		fTypeInfoUtil = new TypeInfoUtil(extension != null ? extension.getImageProvider() : null);

		fTypeInfoLabelProvider = new TypeItemLabelProvider();

		setListLabelProvider(fTypeInfoLabelProvider);
		setListSelectionLabelDecorator(fTypeInfoLabelProvider);
		setDetailsLabelProvider(new TypeItemDetailsLabelProvider(fTypeInfoUtil));

		fTypeItemsComparator = new TypeItemsComparator();
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		fTitle = title;
	}

	/**
	 * Adds or replaces subtitle of the dialog
	 * 
	 * @param text
	 *            the new subtitle for this dialog
	 */
	private void setSubtitle(String text) {
		if (text == null || text.length() == 0) {
			getShell().setText(fTitle);
		} else {
			getShell().setText(Messages.format(DLTKUIMessages.FilteredTypeSelectionDialog_titleFormat,
					new String[] { fTitle, text }));
		}
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = DLTKUIPlugin.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS);

		if (settings == null) {
			settings = DLTKUIPlugin.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS);
		}

		return settings;
	}

	@Override
	protected void storeDialog(IDialogSettings settings) {
		super.storeDialog(settings);

		if (fFilterActionGroup != null) {
			XMLMemento memento = XMLMemento.createWriteRoot("workingSet"); //$NON-NLS-1$
			fFilterActionGroup.saveState(memento);
			fFilterActionGroup.dispose();
			StringWriter writer = new StringWriter();
			try {
				memento.save(writer);
				settings.put(WORKINGS_SET_SETTINGS, writer.getBuffer().toString());
			} catch (IOException e) {
				// don't do anything. Simply don't store the settings
				DLTKUIPlugin.log(e);
			}
		}
	}

	@Override
	protected void restoreDialog(IDialogSettings settings) {
		super.restoreDialog(settings);

		fTypeInfoLabelProvider.setContainerInfo(true);

		if (fAllowScopeSwitching) {
			String setting = settings.get(WORKINGS_SET_SETTINGS);
			if (setting != null) {
				try {
					IMemento memento = XMLMemento.createReadRoot(new StringReader(setting));
					fFilterActionGroup.restoreState(memento);
				} catch (WorkbenchException e) {
					// don't do anything. Simply don't restore the settings
					DLTKUIPlugin.log(e);
				}
			}
			IWorkingSet ws = fFilterActionGroup.getWorkingSet();
			if (ws == null || (ws.isAggregateWorkingSet() && ws.isEmpty())) {
				setSearchScope(SearchEngine.createWorkspaceScope(fToolkit));
				setSubtitle(null);
			} else {
				setSearchScope(DLTKSearchScopeFactory.getInstance().createSearchScope(ws, true, fToolkit));
				setSubtitle(ws.getLabel());
			}
		}

		TypeNameMatch[] types = OpenTypeHistory.getInstance(this.getUIToolkit()).getTypeInfos();

		for (int i = 0; i < types.length; i++) {
			TypeNameMatch type = types[i];
			accessedHistoryItem(type);
		}
	}

	@Override
	protected void fillViewMenu(IMenuManager menuManager) {
		super.fillViewMenu(menuManager);

		if (fAllowScopeSwitching) {
			fFilterActionGroup = new WorkingSetFilterActionGroup(getShell(), DLTKUIPlugin.getActivePage(),
					new IPropertyChangeListener() {

						@Override
						public void propertyChange(PropertyChangeEvent event) {
							IWorkingSet ws = (IWorkingSet) event.getNewValue();
							if (ws == null || (ws.isAggregateWorkingSet() && ws.isEmpty())) {
								setSearchScope(SearchEngine.createWorkspaceScope(fToolkit));
								setSubtitle(null);
							} else {
								setSearchScope(
										DLTKSearchScopeFactory.getInstance().createSearchScope(ws, true, fToolkit));
								setSubtitle(ws.getLabel());
							}

							applyFilter();
						}
					});
			fFilterActionGroup.fillViewMenu(menuManager);
		}

	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		Control addition = null;

		if (fExtension != null) {

			addition = fExtension.createContentArea(parent);
			if (addition != null) {
				GridData gd = new GridData(GridData.FILL_HORIZONTAL);
				gd.horizontalSpan = 2;
				addition.setLayoutData(gd);

			}

			fExtension.initialize(this);
		}

		return addition;
	}

	@Override
	protected void setResult(List newResult) {

		List resultToReturn = new ArrayList();

		for (int i = 0; i < newResult.size(); i++) {
			if (newResult.get(i) instanceof TypeNameMatch) {
				IType type = ((TypeNameMatch) newResult.get(i)).getType();
				if (type.exists()) {
					// items are added to history in the
					// org.eclipse.ui.dialogs.FilteredItemsSelectionDialog#computeResult()
					// method
					resultToReturn.add(type);
				} else {
					TypeNameMatch typeInfo = (TypeNameMatch) newResult.get(i);
					IProjectFragment root = typeInfo.getProjectFragment();
					ScriptElementLabels labels = PHPUILanguageToolkit.getInstance().getScriptElementLabels();
					String containerName = labels.getElementLabel(root, ScriptElementLabels.ALL_FULLY_QUALIFIED);
					String message = Messages.format(DLTKUIMessages.FilteredTypesSelectionDialog_dialogMessage,
							new String[] { typeInfo.getFullyQualifiedName(), containerName });
					MessageDialog.openError(getShell(), fTitle, message);
					getSelectionHistory().remove(typeInfo);
				}
			}
		}

		super.setResult(resultToReturn);
	}

	@Override
	public void create() {
		super.create();
		Control patternControl = getPatternControl();
		if (patternControl instanceof Text) {
			TextFieldNavigationHandler.install((Text) patternControl);
		}
	}

	@Override
	public int open() {
		if (getInitialPattern() == null) {
			IWorkbenchWindow window = DLTKUIPlugin.getActiveWorkbenchWindow();
			if (window != null) {
				ISelection selection = window.getSelectionService().getSelection();
				if (selection instanceof ITextSelection) {
					String text = ((ITextSelection) selection).getText();
					if (text != null) {
						text = text.trim();
						if (text.length() > 0) {
							setInitialPattern(text, FULL_SELECTION);
						}
					}
				}
			}
		}
		return super.open();
	}

	/**
	 * Sets a new validator.
	 * 
	 * @param validator
	 *            the new validator
	 */
	public void setValidator(ISelectionStatusValidator validator) {
		fValidator = validator;
	}

	@Override
	protected ItemsFilter createFilter() {
		fFilter = new TypeItemsFilter(getUIToolkit(), fSearchScope, fElementKinds, fFilterExtension);
		return fFilter;
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider provider, ItemsFilter itemsFilter,
			IProgressMonitor progressMonitor) throws CoreException {
		TypeItemsFilter typeSearchFilter = (TypeItemsFilter) itemsFilter;
		TypeSearchRequestor requestor = new TypeSearchRequestor(provider, typeSearchFilter);
		String packPattern = typeSearchFilter.getPackagePattern();
		if (packPattern != null) {
			packPattern = packPattern.replace(IIndexConstants.TYPE_SEPARATOR, NamespaceReference.NAMESPACE_SEPARATOR);
		}
		String typePattern = typeSearchFilter.getNamePattern();

		progressMonitor.setTaskName(DLTKUIMessages.FilteredTypesSelectionDialog_searchJob_taskName);

		IType[] types = new ModelAccess().findTypes(packPattern, typePattern,
				ModelAccess.convertSearchRule(itemsFilter.getMatchRule()), 0, Modifiers.AccNameSpace,
				typeSearchFilter.getSearchScope(), progressMonitor);
		if (types != null) {
			for (IType type : types) {
				requestor.acceptTypeNameMatch(new PHPSearchTypeNameMatch(type, type.getFlags()));
			}
		} else {
			SearchEngine engine = new SearchEngine((WorkingCopyOwner) null);

			/*
			 * Setting the filter into match everything mode avoids filtering
			 * twice by the same pattern (the search engine only provides
			 * filtered matches). For the case when the pattern is a camel case
			 * pattern with a terminator, the filter is not set to match
			 * everything mode because jdt.core's SearchPattern does not support
			 * that case.
			 */
			int matchRule = typeSearchFilter.getMatchRule();
			if (matchRule == SearchPattern.RULE_CAMELCASE_MATCH) {
				// If the pattern is empty, the RULE_BLANK_MATCH will be chosen,
				// so
				// we don't have to check the pattern length
				char lastChar = typePattern.charAt(typePattern.length() - 1);

				if (lastChar == '<' || lastChar == ' ') {
					typePattern = typePattern.substring(0, typePattern.length() - 1);
				} else {
					typeSearchFilter.setMatchEverythingMode(true);
				}
			} else {
				typeSearchFilter.setMatchEverythingMode(true);
			}

			try {
				engine.searchAllTypeNames(packPattern == null ? null : packPattern.toCharArray(),
						typeSearchFilter.getPackageFlags(), typePattern.toCharArray(), matchRule,
						typeSearchFilter.getElementKind(), typeSearchFilter.getSearchScope(), requestor,
						IDLTKSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, progressMonitor);
			} finally {
				typeSearchFilter.setMatchEverythingMode(false);
			}
		}
	}

	@Override
	protected Comparator getItemsComparator() {
		return fTypeItemsComparator;
	}

	@Override
	public String getElementName(Object item) {
		TypeNameMatch type = (TypeNameMatch) item;
		return fTypeInfoUtil.getText(type);
	}

	@Override
	protected IStatus validateItem(Object item) {

		if (item == null)
			return new Status(IStatus.ERROR, DLTKUIPlugin.getPluginId(), IStatus.ERROR, "", null); //$NON-NLS-1$

		if (fValidator != null) {
			IType type = ((TypeNameMatch) item).getType();
			if (!type.exists())
				return new Status(IStatus.ERROR, DLTKUIPlugin.getPluginId(), IStatus.ERROR,
						Messages.format(DLTKUIMessages.FilteredTypesSelectionDialog_error_type_doesnot_exist,
								((TypeNameMatch) item).getFullyQualifiedName()),
						null);
			Object[] elements = { type };
			return fValidator.validate(elements);
		} else
			return new Status(IStatus.OK, DLTKUIPlugin.getPluginId(), IStatus.OK, "", null); //$NON-NLS-1$
	}

	/**
	 * Sets search scope used when searching for types.
	 * 
	 * @param scope
	 *            the new scope
	 */
	private void setSearchScope(IDLTKSearchScope scope) {
		fSearchScope = scope;
	}

	/*
	 * We only have to ensure history consistency here since the search engine
	 * takes care of working copies.
	 */
	private static class ConsistencyRunnable implements IRunnableWithProgress {
		private IDLTKUILanguageToolkit tookit;

		ConsistencyRunnable(IDLTKLanguageToolkit toolkit) {
			this.tookit = DLTKUILanguageManager.getLanguageToolkit(toolkit.getNatureId());
		}

		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			if (fgFirstTime) {
				// Join the initialize after load job.
				IJobManager manager = Job.getJobManager();
				manager.join(DLTKUIPlugin.PLUGIN_ID, monitor);
			}
			OpenTypeHistory history = OpenTypeHistory.getInstance(tookit);
			if (fgFirstTime || history.isEmpty()) {
				if (history.needConsistencyCheck()) {
					monitor.beginTask(DLTKUIMessages.TypeSelectionDialog_progress_consistency, 100);
					refreshSearchIndices(SubMonitor.convert(monitor, 90));
					history.checkConsistency(SubMonitor.convert(monitor, 10));
				} else {
					refreshSearchIndices(monitor);
				}
				monitor.done();
				fgFirstTime = false;
			} else {
				history.checkConsistency(monitor);
			}
		}

		public static boolean needsExecution(IDLTKLanguageToolkit toolkit) {
			OpenTypeHistory history = OpenTypeHistory
					.getInstance(DLTKUILanguageManager.getLanguageToolkit(toolkit.getNatureId()));
			return fgFirstTime || history.isEmpty() || history.needConsistencyCheck();
		}

		private void refreshSearchIndices(IProgressMonitor monitor) throws InvocationTargetException {
			try {
				new SearchEngine().searchAllTypeNames(null, 0,
						// make sure we search a concrete name. This is faster
						// according to Kent
						"_______________".toCharArray(), //$NON-NLS-1$
						SearchPattern.RULE_EXACT_MATCH | SearchPattern.RULE_CASE_SENSITIVE, IDLTKSearchConstants.TYPE,
						SearchEngine.createWorkspaceScope(tookit.getCoreToolkit()), new NopTypeNameRequestor(),
						IDLTKSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, monitor);
			} catch (ModelException e) {
				throw new InvocationTargetException(e);
			}
		}
	}

	@Override
	public void reloadCache(boolean checkDuplicates, IProgressMonitor monitor) {
		IProgressMonitor remainingMonitor;
		if (ConsistencyRunnable.needsExecution(fToolkit)) {
			monitor.beginTask(DLTKUIMessages.TypeSelectionDialog_progress_consistency, 10);
			try {
				ConsistencyRunnable runnable = new ConsistencyRunnable(fToolkit);
				runnable.run(SubMonitor.convert(monitor, 1));
			} catch (InvocationTargetException e) {
				ExceptionHandler.handle(e, DLTKUIMessages.TypeSelectionDialog_error3Title,
						DLTKUIMessages.TypeSelectionDialog_error3Message);
				close();
				return;
			} catch (InterruptedException e) {
				// cancelled by user
				close();
				return;
			}
			remainingMonitor = SubMonitor.convert(monitor, 9);
		} else {
			remainingMonitor = monitor;
		}
		super.reloadCache(checkDuplicates, remainingMonitor);
		monitor.done();
	}

	@Override
	public void triggerSearch() {
		fTypeFilterVersion++;
		applyFilter();
	}

	/**
	 * A <code>LabelProvider</code> for (the table of) types.
	 */
	private class TypeItemLabelProvider extends LabelProvider implements ILabelDecorator, IStyledLabelProvider {

		private boolean fContainerInfo;
		private LocalResourceManager fImageManager;

		private BoldStylerProvider fBoldStylerProvider;
		private PHPTypeNameMatchLabelProvider fLabelProvider;

		private Styler fBoldQualifierStyler;

		/**
		 * Construct a new <code>TypeItemLabelProvider</code>. F
		 */
		public TypeItemLabelProvider() {
			fImageManager = new LocalResourceManager(JFaceResources.getResources());
			fLabelProvider = new PHPTypeNameMatchLabelProvider(PHPTypeNameMatchLabelProvider.SHOW_TYPE_ONLY,
					DLTKUILanguageManager.getLanguageToolkit(fToolkit.getNatureId()));
		}

		public void setContainerInfo(boolean containerInfo) {
			fContainerInfo = containerInfo;
			fireLabelProviderChanged(new LabelProviderChangedEvent(this));
		}

		@Override
		public void dispose() {
			super.dispose();
			fImageManager.dispose();
			if (fBoldStylerProvider != null) {
				fBoldStylerProvider.dispose();
				fBoldStylerProvider = null;
			}
		}

		@Override
		public Image getImage(Object element) {
			if (!(element instanceof TypeNameMatch)) {
				return super.getImage(element);
			}
			ImageDescriptor contributedImageDescriptor = fTypeInfoUtil.getContributedImageDescriptor(element);
			if (contributedImageDescriptor == null) {
				return fLabelProvider.getImage(element);
			} else {
				return fImageManager.createImage(contributedImageDescriptor);
			}
		}

		@Override
		public String getText(Object element) {
			if (!(element instanceof TypeNameMatch)) {
				return super.getText(element);
			}
			if (fContainerInfo && isDuplicateElement(element)) {
				return fTypeInfoUtil.getFullyQualifiedText((TypeNameMatch) element);
			}
			if (!fContainerInfo && isDuplicateElement(element)) {
				return fTypeInfoUtil.getQualifiedText((TypeNameMatch) element);
			}
			return fTypeInfoUtil.getText(element);
		}

		@Override
		public Image decorateImage(Image image, Object element) {
			return null;
		}

		@Override
		public String decorateText(String text, Object element) {
			if (!(element instanceof TypeNameMatch)) {
				return null;
			}

			if (fContainerInfo && isDuplicateElement(element)) {
				return fTypeInfoUtil.getFullyQualifiedText((TypeNameMatch) element);
			}

			return fTypeInfoUtil.getQualifiedText((TypeNameMatch) element);
		}

		@Override
		public StyledString getStyledText(Object element) {
			String text = getText(element);
			StyledString string = new StyledString(text);

			int index = text.indexOf(ScriptElementLabels.CONCAT_STRING);

			final String namePattern = fFilter != null ? fFilter.getNamePattern() : null;
			if (namePattern != null && !"*".equals(namePattern)) { //$NON-NLS-1$
				String typeName = index == -1 ? text : text.substring(0, index);
				int[] matchingRegions = org.eclipse.php.internal.ui.util.SearchPattern.getMatchingRegions(namePattern,
						typeName, fFilter.getMatchRule());
				Strings.markMatchingRegions(string, 0, matchingRegions, getBoldStylerProvider().getBoldStyler());
			}

			if (index != -1) {
				string.setStyle(index, text.length() - index, StyledString.QUALIFIER_STYLER);
				final String packagePattern = fFilter != null ? fFilter.getPackagePattern() : null;
				if (packagePattern != null && !"*".equals(packagePattern)) { //$NON-NLS-1$
					index = index + ScriptElementLabels.CONCAT_STRING.length();
					int endIndex = text.indexOf(ScriptElementLabels.CONCAT_STRING, index);
					String packageName;
					if (endIndex == -1)
						packageName = text.substring(index);
					else
						packageName = text.substring(index, endIndex);
					int[] matchingRegions = org.eclipse.php.internal.ui.util.SearchPattern
							.getMatchingRegions(packagePattern, packageName, fFilter.getPackageFlags());
					Strings.markMatchingRegions(string, index, matchingRegions, getBoldQualifierStyler());
				}
			}
			return string;
		}

		private BoldStylerProvider getBoldStylerProvider() {
			if (fBoldStylerProvider == null) {
				fBoldStylerProvider = new BoldStylerProvider(getDialogArea().getFont());
			}
			return fBoldStylerProvider;
		}

		private Styler getBoldQualifierStyler() {
			if (fBoldQualifierStyler == null) {
				fBoldQualifierStyler = new Styler() {
					@Override
					public void applyStyles(TextStyle textStyle) {
						StyledString.QUALIFIER_STYLER.applyStyles(textStyle);
						getBoldStylerProvider().getBoldStyler().applyStyles(textStyle);
					}
				};
			}
			return fBoldQualifierStyler;
		}

	}

	/**
	 * A <code>LabelProvider</code> for the label showing type details.
	 */
	private class TypeItemDetailsLabelProvider extends LabelProvider {

		private PHPTypeNameMatchLabelProvider fLabelProvider;

		private final TypeInfoUtil fTypeInfoUtil;

		public TypeItemDetailsLabelProvider(TypeInfoUtil typeInfoUtil) {
			fTypeInfoUtil = typeInfoUtil;

			fLabelProvider = new PHPTypeNameMatchLabelProvider(PHPTypeNameMatchLabelProvider.SHOW_TYPE_CONTAINER_ONLY,
					DLTKUILanguageManager.getLanguageToolkit(fToolkit.getNatureId()));
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof TypeNameMatch) {
				return fLabelProvider.getImage((element));
			}

			return super.getImage(element);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof TypeNameMatch) {
				return fTypeInfoUtil.getQualificationText((TypeNameMatch) element);
			}

			return super.getText(element);
		}
	}

	private class TypeInfoUtil {

		private final ITypeInfoImageProvider fProviderExtension;

		private final TypeInfoRequestorAdapter fAdapter = new TypeInfoRequestorAdapter();

		private final Map fLib2Name = new HashMap();

		private final String[] fInstallLocations;

		private final String[] fVMNames;

		public TypeInfoUtil(ITypeInfoImageProvider extension) {
			fProviderExtension = extension;
			List<String> locations = new ArrayList<String>();
			List<String> labels = new ArrayList<String>();
			IInterpreterInstallType[] installs = ScriptRuntime.getInterpreterInstallTypes(fToolkit.getNatureId());
			for (int i = 0; i < installs.length; i++) {
				processInterpreterInstallType(installs[i], locations, labels);
			}
			fInstallLocations = locations.toArray(new String[locations.size()]);
			fVMNames = labels.toArray(new String[labels.size()]);

		}

		private void processInterpreterInstallType(IInterpreterInstallType installType, List<String> locations,
				List<String> labels) {
			if (installType != null) {
				IInterpreterInstall[] installs = installType.getInterpreterInstalls();
				boolean isMac = Platform.OS_MACOSX.equals(Platform.getOS());
				final String HOME_SUFFIX = "/Home"; //$NON-NLS-1$
				for (int i = 0; i < installs.length; i++) {
					String label = getFormattedLabel(installs[i].getName());
					LibraryLocation[] libLocations = installs[i].getLibraryLocations();
					if (libLocations != null) {
						processLibraryLocation(libLocations, label);
					} else {
						String filePath = installs[i].getInstallLocation().toOSString();
						if (filePath != null) {
							// on MacOS X install locations end in an additional
							// "/Home" segment; remove it
							if (isMac && filePath.endsWith(HOME_SUFFIX))
								filePath = filePath.substring(0, filePath.length() - HOME_SUFFIX.length() + 1);
							locations.add(filePath);
							labels.add(label);
						}
					}
				}
			}
		}

		private void processLibraryLocation(LibraryLocation[] libLocations, String label) {
			for (int l = 0; l < libLocations.length; l++) {
				LibraryLocation location = libLocations[l];
				fLib2Name.put(location.getLibraryPath().toOSString(), label);
			}
		}

		private String getFormattedLabel(String name) {
			return Messages.format(DLTKUIMessages.FilteredTypesSelectionDialog_library_name_format, name);
		}

		public String getText(Object element) {
			return ((TypeNameMatch) element).getSimpleTypeName();
		}

		public String getQualifiedText(TypeNameMatch type) {
			StringBuilder result = new StringBuilder();
			result.append(type.getSimpleTypeName());
			String containerName = type.getTypeContainerName();
			result.append(ScriptElementLabels.CONCAT_STRING);
			if (containerName.length() > 0) {
				result.append(containerName);
			} else {
				result.append("(global namespace)"); //$NON-NLS-1$
			}
			return result.toString();
		}

		public String getFullyQualifiedText(TypeNameMatch type) {
			StringBuilder result = new StringBuilder();
			result.append(type.getSimpleTypeName());
			String containerName = type.getTypeContainerName();
			if (containerName.length() > 0) {
				result.append(ScriptElementLabels.CONCAT_STRING);
				result.append(containerName);
			}
			result.append(ScriptElementLabels.CONCAT_STRING);
			result.append(getContainerName(type));
			return result.toString();
		}

		public String getQualificationText(TypeNameMatch type) {
			StringBuilder result = new StringBuilder();
			String containerName = type.getTypeContainerName();
			if (containerName.length() > 0) {
				result.append(containerName);
				result.append(ScriptElementLabels.CONCAT_STRING);
			}
			result.append(getContainerName(type));
			return result.toString();
		}

		public ImageDescriptor getContributedImageDescriptor(Object element) {
			TypeNameMatch type = (TypeNameMatch) element;
			if (fProviderExtension != null) {
				fAdapter.setMatch(type);
				return fProviderExtension.getImageDescriptor(fAdapter);
			}
			return null;
		}

		private String getContainerName(TypeNameMatch type) {
			IProjectFragment root = type.getProjectFragment();
			if (root.isExternal()) {
				String name = root.getPath().toOSString();
				for (int i = 0; i < fInstallLocations.length; i++) {
					if (name.startsWith(fInstallLocations[i])) {
						return fVMNames[i];
					}
				}
				String lib = (String) fLib2Name.get(name);
				if (lib != null)
					return lib;
			}
			StringBuffer buf = new StringBuffer();
			ScriptElementLabels labels = DLTKUILanguageManager.getLanguageToolkit(fToolkit.getNatureId())
					.getScriptElementLabels();
			labels.getProjectFragmentLabel(root, ScriptElementLabels.ROOT_QUALIFIED | ScriptElementLabels.ROOT_VARIABLE,
					buf);
			return buf.toString();
		}
	}

	/**
	 * Filters types using pattern, scope, element kind and filter extension.
	 */
	private class TypeItemsFilter extends ItemsFilter {

		private boolean fMatchEverything = false;

		private final int fMyTypeFilterVersion = fTypeFilterVersion;

		private final TypeInfoFilter fTypeInfoFilter;

		/**
		 * Creates instance of TypeItemsFilter
		 * 
		 * @param scope
		 * @param elementKind
		 * @param extension
		 */
		public TypeItemsFilter(IDLTKUILanguageToolkit uiToolkit, IDLTKSearchScope scope, int elementKind,
				ITypeInfoFilterExtension extension) {
			super(new TypeSearchPattern());
			String pattern = patternMatcher.getPattern();
			fTypeInfoFilter = new TypeInfoFilter(uiToolkit, pattern, scope, elementKind, extension,
					DLTKLanguageManager.getSearchPatternProcessor(PHPLanguageToolkit.getDefault(), true));
		}

		@Override
		public boolean isSubFilter(ItemsFilter filter) {
			if (!(filter instanceof TypeItemsFilter))
				return false;
			TypeItemsFilter typeItemsFilter = (TypeItemsFilter) filter;
			if (fMyTypeFilterVersion != typeItemsFilter.getMyTypeFilterVersion())
				return false;

			// Caveat: This method is defined the wrong way 'round in
			// FilteredItemsSelectionDialog!
			// WRONG (has reverse meaning!): return
			// fTypeInfoFilter.isSubFilter(filter.getPattern());
			return typeItemsFilter.fTypeInfoFilter.isSubFilter(fTypeInfoFilter.getText());
		}

		@Override
		public boolean equalsFilter(ItemsFilter iFilter) {
			if (!(iFilter instanceof TypeItemsFilter))
				return false;
			TypeItemsFilter typeItemsFilter = (TypeItemsFilter) iFilter;
			if (!getPattern().equals(typeItemsFilter.getPattern()))
				return false;
			if (getSearchScope() != typeItemsFilter.getSearchScope())
				return false;
			if (fMyTypeFilterVersion != typeItemsFilter.getMyTypeFilterVersion())
				return false;
			return true;
		}

		public int getElementKind() {
			return fTypeInfoFilter.getElementKind();
		}

		public IDLTKSearchScope getSearchScope() {
			return fTypeInfoFilter.getSearchScope();
		}

		public int getMyTypeFilterVersion() {
			return fMyTypeFilterVersion;
		}

		public String getNamePattern() {
			return fTypeInfoFilter.getNamePattern();
		}

		public String getPackagePattern() {
			return fTypeInfoFilter.getPackagePattern();
		}

		public int getPackageFlags() {
			return fTypeInfoFilter.getPackageFlags();
		}

		public boolean matchesRawNamePattern(TypeNameMatch type) {
			return fTypeInfoFilter.matchesRawNamePattern(type);
		}

		public boolean matchesFilterExtension(TypeNameMatch type) {
			return fTypeInfoFilter.matchesFilterExtension(type);
		}

		/**
		 * Set filter to "match everything" mode.
		 * 
		 * @param matchEverything
		 *            if <code>true</code>, {@link #matchItem(Object)} always
		 *            returns true. If <code>false</code>, the filter is
		 *            enabled.
		 */
		public void setMatchEverythingMode(boolean matchEverything) {
			this.fMatchEverything = matchEverything;
		}

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}

		@Override
		public boolean matchItem(Object item) {
			if (fMatchEverything)
				return true;

			TypeNameMatch type = (TypeNameMatch) item;
			return fTypeInfoFilter.matchesHistoryElement(type);
		}

		@Override
		public boolean matchesRawNamePattern(Object item) {
			TypeNameMatch type = (TypeNameMatch) item;
			return matchesRawNamePattern(type);
		}

		@Override
		public int getMatchRule() {
			return fTypeInfoFilter.getSearchFlags();
		}

		@Override
		public String getPattern() {
			return fTypeInfoFilter.getText();
		}

		@Override
		public boolean isCamelCasePattern() {
			return fTypeInfoFilter.isCamelCasePattern();
		}

	}

	/**
	 * Extends functionality of SearchPatterns
	 */
	private static class TypeSearchPattern extends SearchPattern {

		private String fPattern;

		@Override
		public void setPattern(String stringPattern) {
			fPattern = stringPattern;
		}

		@Override
		public String getPattern() {
			return fPattern;
		}

	}

	/**
	 * A <code>TypeSearchRequestor</code> collects matches filtered using
	 * <code>TypeItemsFilter</code>. The attached content provider is filled on
	 * the basis of the collected entries (instances of
	 * <code>TypeNameMatch</code>).
	 */
	private class TypeSearchRequestor extends TypeNameMatchRequestor {
		private volatile boolean fStop;

		private final AbstractContentProvider fContentProvider;

		private final TypeItemsFilter fTypeItemsFilter;
		Set addedNames = new HashSet();

		public TypeSearchRequestor(AbstractContentProvider contentProvider, TypeItemsFilter typeItemsFilter) {
			super();
			fContentProvider = contentProvider;
			fTypeItemsFilter = typeItemsFilter;
		}

		@Override
		public void acceptTypeNameMatch(TypeNameMatch match) {
			if (fStop)
				return;
			if (new TypeFilter(DLTKUILanguageManager.getLanguageToolkit(fToolkit.getNatureId())).isFiltered(match))
				return;
			if (!addedNames.contains(match.getTypeQualifiedName())) {
				addedNames.add(match.getTypeQualifiedName());
				if (fTypeItemsFilter.matchesFilterExtension(match))
					fContentProvider.add(match, fTypeItemsFilter);
			}
		}
	}

	/**
	 * Compares TypeItems is used during sorting
	 */
	private class TypeItemsComparator implements Comparator {

		private final Map fLib2Name = new HashMap();

		private final String[] fInstallLocations;

		private final String[] fVMNames;

		/**
		 * Creates new instance of TypeItemsComparator
		 */
		public TypeItemsComparator() {
			List<String> locations = new ArrayList<String>();
			List<String> labels = new ArrayList<String>();
			IInterpreterInstallType[] installs = ScriptRuntime.getInterpreterInstallTypes();
			for (int i = 0; i < installs.length; i++) {
				processVMInstallType(installs[i], locations, labels);
			}
			fInstallLocations = locations.toArray(new String[locations.size()]);
			fVMNames = labels.toArray(new String[labels.size()]);
		}

		private void processVMInstallType(IInterpreterInstallType installType, List<String> locations,
				List<String> labels) {
			if (installType != null) {
				IInterpreterInstall[] installs = installType.getInterpreterInstalls();
				boolean isMac = Platform.OS_MACOSX.equals(Platform.getOS());
				final String HOME_SUFFIX = "/Home"; //$NON-NLS-1$
				for (int i = 0; i < installs.length; i++) {
					String label = getFormattedLabel(installs[i].getName());
					LibraryLocation[] libLocations = installs[i].getLibraryLocations();
					if (libLocations != null) {
						processLibraryLocation(libLocations, label);
					} else {
						String filePath = installs[i].getInstallLocation().toOSString();
						// on MacOS X install locations end in an additional
						// "/Home" segment; remove it
						if (isMac && filePath.endsWith(HOME_SUFFIX))
							filePath = filePath.substring(0, filePath.length() - HOME_SUFFIX.length() + 1);
						locations.add(filePath);
						labels.add(label);
					}
				}
			}
		}

		private void processLibraryLocation(LibraryLocation[] libLocations, String label) {
			for (int l = 0; l < libLocations.length; l++) {
				LibraryLocation location = libLocations[l];
				fLib2Name.put(location.getLibraryPath().toString(), label);
			}
		}

		private String getFormattedLabel(String name) {
			return NLS.bind(DLTKUIMessages.FilteredTypesSelectionDialog_library_name_format, name);
		}

		@Override
		public int compare(Object left, Object right) {

			TypeNameMatch leftInfo = (TypeNameMatch) left;
			TypeNameMatch rightInfo = (TypeNameMatch) right;

			int result = compareName(leftInfo.getSimpleTypeName(), rightInfo.getSimpleTypeName());
			if (result != 0)
				return result;
			result = compareTypeContainerName(leftInfo.getTypeContainerName(), rightInfo.getTypeContainerName());
			if (result != 0)
				return result;

			int leftCategory = getElementTypeCategory(leftInfo);
			int rightCategory = getElementTypeCategory(rightInfo);
			if (leftCategory < rightCategory)
				return -1;
			if (leftCategory > rightCategory)
				return +1;
			return compareContainerName(leftInfo, rightInfo);
		}

		private int compareName(String leftString, String rightString) {
			int result = leftString.compareToIgnoreCase(rightString);
			if (result != 0 || rightString.length() == 0) {
				return result;
			} else if (Strings.isLowerCase(leftString.charAt(0)) && !Strings.isLowerCase(rightString.charAt(0))) {
				return +1;
			} else if (Strings.isLowerCase(rightString.charAt(0)) && !Strings.isLowerCase(leftString.charAt(0))) {
				return -1;
			} else {
				return leftString.compareTo(rightString);
			}
		}

		private int compareTypeContainerName(String leftString, String rightString) {
			int leftLength = leftString.length();
			int rightLength = rightString.length();
			if (leftLength == 0 && rightLength > 0)
				return -1;
			if (leftLength == 0 && rightLength == 0)
				return 0;
			if (leftLength > 0 && rightLength == 0)
				return +1;
			return compareName(leftString, rightString);
		}

		private int compareContainerName(TypeNameMatch leftType, TypeNameMatch rightType) {
			return getContainerName(leftType).compareTo(getContainerName(rightType));
		}

		private String getContainerName(TypeNameMatch type) {
			IProjectFragment root = type.getProjectFragment();
			if (root.isExternal()) {
				String name = root.getPath().toOSString();
				for (int i = 0; i < fInstallLocations.length; i++) {
					if (name.startsWith(fInstallLocations[i])) {
						return fVMNames[i];
					}
				}
				String lib = (String) fLib2Name.get(name);
				if (lib != null)
					return lib;
			}
			StringBuffer buf = new StringBuffer();
			ScriptElementLabels labels = getUIToolkit().getScriptElementLabels();
			labels.getProjectFragmentLabel(root, ScriptElementLabels.ROOT_QUALIFIED | ScriptElementLabels.ROOT_VARIABLE,
					buf);
			return buf.toString();
		}

		private int getElementTypeCategory(TypeNameMatch type) {
			try {
				if (type.getProjectFragment().getKind() == IProjectFragment.K_SOURCE)
					return 0;
			} catch (ModelException e) {
				DLTKUIPlugin.log(e);
			}
			return 1;
		}
	}

	private IDLTKUILanguageToolkit getUIToolkit() {
		return DLTKUILanguageManager.getLanguageToolkit(fToolkit.getNatureId());
	}

	/**
	 * Extends the <code>SelectionHistory</code>, providing support for
	 * <code>OpenTypeHistory</code>.
	 */
	protected class TypeSelectionHistory extends SelectionHistory {

		/**
		 * Creates new instance of TypeSelectionHistory
		 */

		public TypeSelectionHistory() {
			super();
		}

		@Override
		public synchronized void accessed(Object object) {
			super.accessed(object);
		}

		@Override
		public synchronized boolean remove(Object element) {
			OpenTypeHistory.getInstance(getUIToolkit()).remove((TypeNameMatch) element);
			return super.remove(element);
		}

		@Override
		public void load(IMemento memento) {
			TypeNameMatch[] types = OpenTypeHistory.getInstance(getUIToolkit()).getTypeInfos();

			for (int i = 0; i < types.length; i++) {
				TypeNameMatch type = types[i];
				accessed(type);
			}
		}

		@Override
		public void save(IMemento memento) {
			persistHistory();
		}

		/**
		 * Stores contents of the local history into persistent history
		 * container.
		 */
		private synchronized void persistHistory() {
			if (getReturnCode() == OK) {
				Object[] items = getHistoryItems();
				for (int i = 0; i < items.length; i++) {
					OpenTypeHistory.getInstance(getUIToolkit()).accessed((TypeNameMatch) items[i]);
				}
			}
		}

		@Override
		protected Object restoreItemFromMemento(IMemento element) {
			return null;
		}

		@Override
		protected void storeItemToMemento(Object item, IMemento element) {

		}

	}

}
