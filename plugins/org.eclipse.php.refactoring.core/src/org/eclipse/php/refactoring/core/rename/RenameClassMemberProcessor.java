/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.*;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.*;
import org.eclipse.ltk.internal.core.refactoring.resource.ResourceProcessors;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.typeinference.TraitUtils.ITraitMember;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.RefactoringPlugin;
import org.eclipse.php.refactoring.core.rename.logic.RenameClassMember;
import org.eclipse.php.refactoring.core.utils.RefactoringUtility;

/**
 * Class member rename processor
 * 
 * @author Roy, 2007
 */
@SuppressWarnings("restriction")
public class RenameClassMemberProcessor extends AbstractRenameProcessor<IFile>
		implements ITextUpdating {

	private static final String RENAME_IS_PROCESSING = PhpRefactoringCoreMessages
			.getString("RenameClassPropertyProcessor.2"); //$NON-NLS-1$
	private static final String CREATING_MODIFICATIONS_LABEL = PhpRefactoringCoreMessages
			.getString("RenameClassPropertyProcessor.3"); //$NON-NLS-1$
	private static final String ID_RENAME_CLASS_MEMBER = "php.refactoring.ui.rename.classProperty"; //$NON-NLS-1$
	protected static final String ATTRIBUTE_TEXTUAL_MATCHES = "textual"; //$NON-NLS-1$
	public static final String RENAME_CLASS_MEMBER_PROCESSOR_NAME = PhpRefactoringCoreMessages
			.getString("RenameClassPropertyProcessor.5"); //$NON-NLS-1$

	/**
	 * The original identifier node we want to rename
	 */
	private ASTNode identifier = null;

	/**
	 * holds whether or not we want to change also the in-lined text
	 */
	private boolean isUpdateTextualMatches;

	/**
	 * True if need to show the override method question
	 */
	public boolean showShouldOverrideMessage = false;
	private ITypeBinding typeBinding;

	/**
	 * Constructor for direct request
	 * 
	 * @param operatedFile
	 * @param locateNode
	 */
	public RenameClassMemberProcessor(IFile operatedFile, ASTNode locateNode) {
		super(operatedFile);
		this.identifier = locateNode;
		showShouldOverrideMessage = true;
		typeBinding = getCurrentType();
	}

	/**
	 * Derive the change
	 */
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange rootChange = new CompositeChange(
				PhpRefactoringCoreMessages
						.getString("RenameClassPropertyProcessor.7")); //$NON-NLS-1$
		rootChange.markAsSynthetic();
		try {
			pm.beginTask(RenameClassMemberProcessor.RENAME_IS_PROCESSING,
					participantFiles.size());
			pm.setTaskName(RenameClassMemberProcessor.CREATING_MODIFICATIONS_LABEL);

			if (pm.isCanceled())
				throw new OperationCanceledException();

			// get target parameters
			final String newElementName = getNewElementName();

			// go over the files and check for global variable usage
			for (Entry<IFile, Program> entry : participantFiles.entrySet()) {
				final IFile file = entry.getKey();
				final Program program = entry.getValue();
				final RenameClassMember rename = new RenameClassMember(file,
						getCurrentElementName(), newElementName,
						getUpdateTextualMatches(), typeBinding, getParent(
								identifier).getType(), identifier);

				// aggregate the changes identifiers
				try {
					program.accept(rename);
				} catch (Exception e) {
					RefactoringPlugin.logException(e);
				}

				if (pm.isCanceled())
					throw new OperationCanceledException();

				pm.worked(1);

				if (rename.hasChanges()) {
					TextFileChange change = acquireChange(file, program);
					rename.updateChange(change);
					rootChange.add(change);
				}
			}
			return rootChange;

		} catch (Exception e) {
			RefactoringPlugin.logException(e);
			return rootChange;
		} finally {
			pm.done();
		}
	}

	public Object[] getElements() {
		return new Object[] { identifier };
	}

	public String getIdentifier() {
		return RenameClassMemberProcessor.ID_RENAME_CLASS_MEMBER;
	}

	public String getProcessorName() {
		return RenameClassMemberProcessor.RENAME_CLASS_MEMBER_PROCESSOR_NAME;
	}

	public Object getNewElement() {
		return getNewElementName();
	}

	public String getCurrentElementName() {
		if (identifier instanceof Variable) {
			Identifier id = (Identifier) ((Variable) identifier).getName();
			return id.getName();
		}
		if (identifier instanceof Identifier) {
			return ((Identifier) identifier).getName();
		}

		if (identifier instanceof FunctionDeclaration) {
			Expression name = ((FunctionDeclaration) identifier)
					.getFunctionName();

			return ((Identifier) name).getName();
		}

		if (identifier instanceof MethodDeclaration) {
			Expression name = ((MethodDeclaration) identifier).getFunction()
					.getFunctionName();

			return ((Identifier) name).getName();
		}

		return identifier.toString();
	}

	public boolean canEnableTextUpdating() {
		return true;
	}

	public String getCurrentElementQualifier() {
		return identifier.toString();
	}

	public boolean getUpdateTextualMatches() {
		return isUpdateTextualMatches;
	}

	public void setUpdateTextualMatches(boolean update) {
		isUpdateTextualMatches = update;
	}

	public ITypeBinding getCurrentType() {
		if (identifier instanceof Identifier) {
			if (identifier.getParent().getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
				FullyQualifiedTraitMethodReference reference = (FullyQualifiedTraitMethodReference) identifier
						.getParent();
				return reference.getClassName().resolveTypeBinding();
			} else if (identifier.getParent().getType() == ASTNode.TRAIT_ALIAS) {
				TraitAlias traitAlias = (TraitAlias) identifier.getParent();
				List<NamespaceName> nameList = ((TraitUseStatement) traitAlias
						.getParent().getParent()).getTraitList();
				String memberName = null;
				if (identifier == traitAlias.getFunctionName()) {
					Expression expression = traitAlias.getTraitMethod();
					if (expression.getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
						FullyQualifiedTraitMethodReference fqtm = (FullyQualifiedTraitMethodReference) expression;
						memberName = fqtm.getFunctionName().getName();
					} else {
						memberName = ((Identifier) expression).getName();
					}
				} else {
					memberName = ((Identifier) identifier).getName();
				}
				for (NamespaceName namespaceName : nameList) {
					ITypeBinding typeBinding = namespaceName
							.resolveTypeBinding();
					if (typeBinding != null && typeBinding.isTrait()
							&& typeBinding.getPHPElement() != null) {
						try {
							IModelElement[] members = ((IType) typeBinding
									.getPHPElement()).getChildren();
							for (IModelElement modelElement : members) {
								if (modelElement.getElementName().equals(
										memberName)
										|| modelElement.getElementName()
												.equals("$" + memberName)) { //$NON-NLS-1$
									return typeBinding;
								}
							}
						} catch (ModelException e) {
						}

					}
				}
			}
		}
		if (identifier instanceof Expression) {
			ASTNode parent = identifier.getParent();

			Dispatch dispatch = getDispatch(parent);
			if (dispatch != null) {
				return dispatch.getDispatcher().resolveTypeBinding();
			}

			TypeDeclaration type = RefactoringUtility.getType(identifier);
			if (type != null) {
				return type.resolveTypeBinding();
			}

			StaticDispatch staticDispatch = getStaticDispatch(parent);

			if (staticDispatch != null) {
				return staticDispatch.getClassName().resolveTypeBinding();
			}
		}

		if (identifier instanceof FieldsDeclaration) {
			TypeDeclaration type = RefactoringUtility.getType(identifier);
			if (type != null) {
				return type.resolveTypeBinding();
			}
		}

		if (identifier instanceof FunctionDeclaration) {
			TypeDeclaration type = RefactoringUtility.getType(identifier);
			if (type != null) {
				return type.resolveTypeBinding();
			}
		}

		if (identifier instanceof MethodDeclaration) {
			TypeDeclaration type = RefactoringUtility.getType(identifier);
			if (type != null) {
				return type.resolveTypeBinding();
			}
		}

		if (identifier instanceof ConstantDeclaration) {
			TypeDeclaration type = RefactoringUtility.getType(identifier);
			if (type != null) {
				return type.resolveTypeBinding();
			}
		}
		return null;
	}

	private Dispatch getDispatch(ASTNode node) {
		if (node == null) {
			return null;
		}

		ASTNode model = node;
		while (!(model instanceof Dispatch)) {
			if (node == null) {
				return null;
			}
			ASTNode parent = model.getParent();
			if (parent == model) {
				return null;
			}
			model = parent;
			if (model instanceof Program || model == null) {
				return null;
			}
		}

		return (Dispatch) model;
	}

	private StaticDispatch getStaticDispatch(ASTNode node) {
		if (node == null) {
			return null;
		}

		ASTNode model = node;
		while (!(model instanceof StaticDispatch)) {
			if (node == null) {
				return null;
			}
			ASTNode parent = model.getParent();
			if (parent == model) {
				return null;
			}
			model = parent;
			if (model instanceof Program || model == null) {
				return null;
			}
		}

		return (StaticDispatch) model;
	}

	@Override
	protected void collectReferences(Program program, IProgressMonitor pm) {
		final ArrayList<IResource> list = new ArrayList<IResource>();

		if (identifier instanceof Identifier) {
			if (identifier.getParent().getType() == ASTNode.FULLY_QUALIFIED_TRAIT_METHOD_REFERENCE) {
				list.add(this.identifier.getProgramRoot().getSourceModule()
						.getResource());
			} else if (identifier.getParent().getType() == ASTNode.TRAIT_ALIAS) {
				list.add(this.identifier.getProgramRoot().getSourceModule()
						.getResource());
			}
		}
		try {
			IModelElement[] elements = this.identifier.getProgramRoot()
					.getSourceModule().codeSelect(identifier.getStart(), 0);
			for (IModelElement modelElement : elements) {
				if (modelElement instanceof ITraitMember) {
					ITraitMember tm = (ITraitMember) modelElement;
					list.add(tm.getHostType().getResource());
				}
			}
		} catch (ModelException e1) {
		}
		IScriptProject project = this.identifier.getProgramRoot()
				.getSourceModule().getScriptProject();

		IDLTKSearchScope scope = SearchEngine.createSearchScope(project,
				getSearchFlags(false));

		ASTNode node = getParent(identifier);

		SearchPattern pattern = null;
		if (node instanceof Variable || node instanceof FieldsDeclaration) {
			pattern = SearchPattern.createPattern(
					"$" + getCurrentElementName(), IDLTKSearchConstants.FIELD, //$NON-NLS-1$
					IDLTKSearchConstants.ALL_OCCURRENCES,
					SearchPattern.R_ERASURE_MATCH,
					PHPLanguageToolkit.getDefault());
			SearchEngine engine = new SearchEngine();
			try {
				engine.search(pattern, new SearchParticipant[] { SearchEngine
						.getDefaultSearchParticipant() }, scope,
						new SearchRequestor() {
							@Override
							public void acceptSearchMatch(SearchMatch match)
									throws CoreException {

								IModelElement element = (IModelElement) match
										.getElement();
								list.add(element.getResource());

							}
						}, new NullProgressMonitor());
			} catch (CoreException e) {
			}
		}

		if (node instanceof ConstantDeclaration
				|| node instanceof StaticConstantAccess) {
			pattern = SearchPattern.createPattern(getCurrentElementName(),
					IDLTKSearchConstants.FIELD,
					IDLTKSearchConstants.ALL_OCCURRENCES,
					SearchPattern.R_ERASURE_MATCH,
					PHPLanguageToolkit.getDefault());
			SearchEngine engine = new SearchEngine();
			try {
				engine.search(pattern, new SearchParticipant[] { SearchEngine
						.getDefaultSearchParticipant() }, scope,
						new SearchRequestor() {
							@Override
							public void acceptSearchMatch(SearchMatch match)
									throws CoreException {

								IModelElement element = (IModelElement) match
										.getElement();
								list.add(element.getResource());
							}
						}, new NullProgressMonitor());
			} catch (CoreException e) {
			}
		}

		int matchMode = SearchPattern.R_EXACT_MATCH
				| SearchPattern.R_ERASURE_MATCH;

		if (node instanceof FunctionDeclaration
				|| node instanceof MethodDeclaration
				|| node instanceof FunctionName) {
			pattern = SearchPattern.createPattern(getCurrentElementName(),
					IDLTKSearchConstants.METHOD,
					IDLTKSearchConstants.ALL_OCCURRENCES, matchMode,
					PHPLanguageToolkit.getDefault());
			SearchEngine engine = new SearchEngine();
			try {
				engine.search(pattern, new SearchParticipant[] { SearchEngine
						.getDefaultSearchParticipant() }, scope,
						new SearchRequestor() {
							@Override
							public void acceptSearchMatch(SearchMatch match)
									throws CoreException {
								IModelElement element = (IModelElement) match
										.getElement();
								list.add(element.getResource());
							}
						}, new NullProgressMonitor());
			} catch (CoreException e) {
			}
		}

		for (Iterator<IResource> it = list.iterator(); it.hasNext();) {
			IResource file = it.next();
			if (file instanceof IFile) {
				try {
					participantFiles.put((IFile) file,
							RefactoringUtility.getProgramForFile((IFile) file));
				} catch (Exception e) {
				}
			}
		}
	}

	private ASTNode getParent(ASTNode identifier) {
		ASTNode node;
		if (identifier instanceof Identifier) {
			node = identifier.getParent();
		} else {
			node = identifier;
		}

		if (node instanceof Variable
				&& node.getParent() instanceof FunctionName) {
			return node.getParent();
		}
		return node;

	}

	public RefactoringStatus getRefactoringStatus(IFile key, Program program) {

		int type = PhpElementConciliator.concile(identifier);

		identifier.getParent();
		if (type == PhpElementConciliator.CONCILIATOR_CLASS_MEMBER) {
			final TypeDeclaration host = RefactoringUtility.getType(identifier);
			if (host != null
					&& PhpElementConciliator.classMemeberAlreadyExists(host,
							getNewElementName(), identifier.getParent()
									.getType())) {
				final String message = MessageFormat
						.format("A same class member with name {0} already exist in the same class scope", //$NON-NLS-1$
								new Object[] { getNewElementName() });
				return RefactoringStatus.createWarningStatus(message);
			}
		}

		return new RefactoringStatus();
	}

	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		String[] affectedNatures = ResourceProcessors
				.computeAffectedNatures(resource);
		RenameArguments fRenameArguments = new RenameArguments(
				getNewElementName(), false);
		return ParticipantManager.loadRenameParticipants(status, this,
				identifier, fRenameArguments, null, affectedNatures,
				sharedParticipants);
	}

	class RenameClassMemberParticipant extends RenameParticipant {

		public RefactoringStatus checkConditions(IProgressMonitor pm,
				CheckConditionsContext context)
				throws OperationCanceledException {
			try {
				return getProcessor().checkFinalConditions(pm, context);
			} catch (CoreException e) {
				return new RefactoringStatus();
			}
		}

		public Change createChange(IProgressMonitor pm) throws CoreException,
				OperationCanceledException {
			return getProcessor().createChange(pm);
		}

		public String getName() {
			return getProcessorName();
		}

		@SuppressWarnings("unchecked")
		protected boolean initialize(Object element) {
			try {
				final RenameClassMemberArguments arguments = (RenameClassMemberArguments) getArguments();
				((AbstractRenameProcessor<IFile>) getProcessor())
						.setNewElementName(arguments.getNewName());
				getProcessor()
						.checkInitialConditions(new NullProgressMonitor());
			} catch (Exception e) {
				return false;
			}
			return true;
		}

	}

	private class RenameClassMemberArguments extends
			org.eclipse.ltk.core.refactoring.participants.RenameArguments {
		public RenameClassMemberArguments(String newName,
				boolean updateReferences) {
			super(newName, updateReferences);
		}
	}
}
