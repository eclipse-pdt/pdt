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
package org.eclipse.php.internal.core.ast.rewrite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.UseStatement;
import org.eclipse.php.core.ast.nodes.UseStatementPart;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;

/**
 * The {@link ImportRewrite} helps updating imports following a import order and
 * on-demand imports threshold as configured by a project.
 * <p>
 * The import rewrite is created on a compilation unit and collects references
 * to types that are added or removed. When adding imports, e.g. using
 * {@link #addImport(String)}, the import rewrite evaluates if the type can be
 * imported and returns the a reference to the type that can be used in code.
 * This reference is either unqualified if the import could be added, or fully
 * qualified if the import failed due to a conflict with another element of the
 * same name.
 * </p>
 * <p>
 * On {@link #rewriteImports(IProgressMonitor)} the rewrite translates these
 * descriptions into text edits that can then be applied to the original source.
 * The rewrite infrastructure tries to generate minimal text changes and only
 * works on the import statements. It is possible to combine the result of an
 * import rewrite with the result of a
 * {@link org.eclipse.jdt.core.dom.rewrite.ASTRewrite} as long as no import
 * statements are modified by the AST rewrite.
 * </p>
 * <p>
 * The options controlling the import order and on-demand thresholds are:
 * <ul>
 * <li>{@link #setImportOrder(String[])} specifies the import groups and their
 * preferred order</li>
 * <li>{@link #setOnDemandImportThreshold(int)} specifies the number of imports
 * in a group needed for a on-demand import statement (star import)</li>
 * <li>{@link #setStaticOnDemandImportThreshold(int)} specifies the number of
 * static imports in a group needed for a on-demand import statement (star
 * import)</li>
 * </ul>
 * This class is not intended to be subclassed.
 * </p>
 * 
 * @since 5.0
 */
public final class ImportRewrite {

	/**
	 * A {@link ImportRewrite.ImportRewriteContext} can optionally be used in
	 * e.g.
	 * {@link ImportRewrite#addImport(String, ImportRewrite.ImportRewriteContext)}
	 * to give more information about the types visible in the scope. These
	 * types can be for example inherited inner types where it is unnecessary to
	 * add import statements for.
	 * 
	 * </p>
	 * <p>
	 * This class can be implemented by clients.
	 * </p>
	 */
	public static abstract class ImportRewriteContext {

		/**
		 * Result constant signaling that the given element is know in the
		 * context.
		 */
		public final static int RES_NAME_FOUND = 1;

		/**
		 * Result constant signaling that the given element is not know in the
		 * context.
		 */
		public final static int RES_NAME_UNKNOWN = 2;

		/**
		 * Result constant signaling that the given element is conflicting with
		 * an other element in the context.
		 */
		public final static int RES_NAME_CONFLICT = 3;

		/**
		 * Kind constant specifying that the element is a type import.
		 */
		public final static int KIND_TYPE = 1;

		/**
		 * Kind constant specifying that the element is a static field import.
		 */
		public final static int KIND_STATIC_FIELD = 2;

		/**
		 * Kind constant specifying that the element is a static method import.
		 */
		public final static int KIND_STATIC_METHOD = 3;

		/**
		 * Searches for the given element in the context and reports if the
		 * element is known ({@link #RES_NAME_FOUND}), unknown (
		 * {@link #RES_NAME_UNKNOWN}) or if its name conflicts (
		 * {@link #RES_NAME_CONFLICT}) with an other element.
		 * 
		 * @param qualifier
		 *            The qualifier of the element, can be package or the
		 *            qualified name of a type
		 * @param name
		 *            The simple name of the element; either a type, method or
		 *            field name or * for on-demand imports.
		 * @param kind
		 *            The kind of the element. Can be either {@link #KIND_TYPE},
		 *            {@link #KIND_STATIC_FIELD} or {@link #KIND_STATIC_METHOD}.
		 *            Implementors should be prepared for new, currently
		 *            unspecified kinds and return {@link #RES_NAME_UNKNOWN} by
		 *            default.
		 * @return Returns the result of the lookup. Can be either
		 *         {@link #RES_NAME_FOUND}, {@link #RES_NAME_UNKNOWN} or
		 *         {@link #RES_NAME_CONFLICT}.
		 */
		public abstract int findInContext(String qualifier, String name, int kind);
	}

	public static final String ENCLOSING_TYPE_SEPARATOR = NamespaceReference.NAMESPACE_DELIMITER;
	private static final char STATIC_PREFIX = 's';
	private static final char NORMAL_PREFIX = 'n';

	private final ImportRewriteContext defaultContext;

	private final ISourceModule compilationUnit;
	private final Program astRoot;
	private final int currentPosition;

	private final boolean restoreExistingImports;
	private final List<String> existingImports;

	private String[] importOrder;

	private List<String> addedImports;
	private List<String> removedImports;

	private String[] createdImports;

	private boolean filterImplicitImports;

	/**
	 * Creates a {@link ImportRewrite} from a {@link ISourceModule}. If
	 * <code>restoreExistingImports</code> is <code>true</code>, all existing
	 * imports are kept, and new imports will be inserted at best matching
	 * locations. If <code>restoreExistingImports</code> is <code>false</code>,
	 * the existing imports will be removed and only the newly added imports
	 * will be created.
	 * <p>
	 * Note that {@link #create(ISourceModule, boolean)} is more efficient than
	 * this method if an AST for the compilation unit is already available.
	 * </p>
	 * 
	 * @param cu
	 *            the compilation unit to create the imports for
	 * @param restoreExistingImports
	 *            specifies if the existing imports should be kept or removed.
	 * @return the created import rewriter.
	 * @throws JavaModelException
	 *             thrown when the compilation unit could not be accessed.
	 */
	public static ImportRewrite create(ISourceModule cu, int position, boolean restoreExistingImports)
			throws ModelException {
		if (cu == null) {
			throw new IllegalArgumentException("Compilation unit must not be null"); //$NON-NLS-1$
		}
		List<String> existingImport = null;
		if (restoreExistingImports) {
			existingImport = new ArrayList<String>();
			IModelElement[] imports = cu.getChildren();
			for (int i = 0; i < imports.length; i++) {
				IModelElement curr = imports[i];
				char prefix = NORMAL_PREFIX;
				existingImport.add(prefix + curr.getElementName());
			}
		}
		return new ImportRewrite(cu, null, position, existingImport);
	}

	/**
	 * Creates a {@link ImportRewrite} from a an AST ({@link Program}). The AST
	 * has to be created from a {@link ISourceModule}, that means
	 * {@link ASTParser#setSource(ISourceModule)} has been used when creating
	 * the AST. If <code>restoreExistingImports</code> is <code>true</code>, all
	 * existing imports are kept, and new imports will be inserted at best
	 * matching locations. If <code>restoreExistingImports</code> is
	 * <code>false</code>, the existing imports will be removed and only the
	 * newly added imports will be created.
	 * <p>
	 * Note that this method is more efficient than using
	 * {@link #create(ISourceModule, boolean)} if an AST is already available.
	 * </p>
	 * 
	 * @param astRoot
	 *            the AST root node to create the imports for
	 * @param restoreExistingImports
	 *            specifies if the existing imports should be kept or removed.
	 * @return the created import rewriter.
	 * @throws IllegalArgumentException
	 *             thrown when the passed AST is null or was not created from a
	 *             compilation unit.
	 */
	public static ImportRewrite create(Program astRoot, int position, boolean restoreExistingImports) {
		if (astRoot == null) {
			throw new IllegalArgumentException("AST must not be null"); //$NON-NLS-1$
		}
		ISourceModule typeRoot = astRoot.getSourceModule();
		List<String> existingImport = null;
		if (restoreExistingImports) {
			existingImport = new ArrayList<String>();
			List<UseStatement> imports = astRoot.getUseStatements(position);
			for (int i = 0; i < imports.size(); i++) {
				UseStatement useStatement = imports.get(i);
				for (UseStatementPart part : useStatement.parts()) {
					StringBuffer buf = new StringBuffer();
					buf.append(NORMAL_PREFIX).append(part.getName().getName());
					existingImport.add(buf.toString());
				}
			}
		}
		return new ImportRewrite((ISourceModule) typeRoot, astRoot, position, existingImport);
	}

	private ImportRewrite(ISourceModule cu, Program astRoot, int position, List<String> existingImports) {
		this.compilationUnit = cu;
		this.astRoot = astRoot; // might be null
		this.currentPosition = position;
		if (existingImports != null) {
			this.existingImports = existingImports;
			this.restoreExistingImports = !existingImports.isEmpty();
		} else {
			this.existingImports = new ArrayList<String>();
			this.restoreExistingImports = false;
		}
		this.filterImplicitImports = true;

		this.defaultContext = new ImportRewriteContext() {
			public int findInContext(String qualifier, String name, int kind) {
				return findInImports(qualifier, name, kind);
			}
		};
		this.addedImports = null; // Initialized on use
		this.removedImports = null; // Initialized on use
		this.createdImports = null;

		this.importOrder = CharOperation.NO_STRINGS;
	}

	/**
	 * Defines the import groups and order to be used by the
	 * {@link ImportRewrite}. Imports are added to the group matching their
	 * qualified name most. The empty group name groups all imports not matching
	 * any other group. Static imports are managed in separate groups. Static
	 * import group names are prefixed with a '#' character.
	 * 
	 * @param order
	 *            A list of strings defining the import groups. A group name
	 *            must be a valid package name or empty. If can be prefixed by
	 *            the '#' character for static import groups
	 */
	public void setImportOrder(String[] order) {
		if (order == null)
			throw new IllegalArgumentException("Order must not be null"); //$NON-NLS-1$
		this.importOrder = order;
	}

	/**
	 * The compilation unit for which this import rewrite was created for.
	 * 
	 * @return the compilation unit for which this import rewrite was created
	 *         for.
	 */
	public ISourceModule getProgram() {
		return this.compilationUnit;
	}

	/**
	 * Returns the default rewrite context that only knows about the imported
	 * types. Clients can write their own context and use the default context
	 * for the default behavior.
	 * 
	 * @return the default import rewrite context.
	 */
	public ImportRewriteContext getDefaultImportRewriteContext() {
		return this.defaultContext;
	}

	/**
	 * Specifies that implicit imports (types in default package, package
	 * <code>java.lang</code> or in the same package as the rewrite compilation
	 * unit should not be created except if necessary to resolve an on-demand
	 * import conflict. The filter is enabled by default.
	 * 
	 * @param filterImplicitImports
	 *            if set, implicit imports will be filtered.
	 */
	public void setFilterImplicitImports(boolean filterImplicitImports) {
		this.filterImplicitImports = filterImplicitImports;
	}

	private static int compareImport(char prefix, String qualifier, String name, String curr) {
		if (curr.charAt(0) != prefix || !curr.endsWith(name)) {
			return ImportRewriteContext.RES_NAME_UNKNOWN;
		}

		curr = curr.substring(1); // remove the prefix

		if (curr.length() == name.length()) {
			if (qualifier.length() == 0) {
				return ImportRewriteContext.RES_NAME_FOUND;
			}
			return ImportRewriteContext.RES_NAME_CONFLICT;
		}
		// at this place: curr.length > name.length

		int dotPos = curr.length() - name.length() - 1;
		if (curr.charAt(dotPos) != NamespaceReference.NAMESPACE_SEPARATOR) {
			return ImportRewriteContext.RES_NAME_UNKNOWN;
		}
		if (qualifier.length() != dotPos || !curr.startsWith(qualifier)) {
			return ImportRewriteContext.RES_NAME_CONFLICT;
		}
		return ImportRewriteContext.RES_NAME_FOUND;
	}

	/**
	 * Not API, package visibility as accessed from an anonymous type
	 */
	/* package */final int findInImports(String qualifier, String name, int kind) {
		boolean allowAmbiguity = (kind == ImportRewriteContext.KIND_STATIC_METHOD)
				|| (name.length() == 1 && name.charAt(0) == '*');
		List<String> imports = this.existingImports;
		char prefix = (kind == ImportRewriteContext.KIND_TYPE) ? NORMAL_PREFIX : STATIC_PREFIX;

		for (int i = imports.size() - 1; i >= 0; i--) {
			String curr = imports.get(i);
			int res = compareImport(prefix, qualifier, name, curr);
			if (res != ImportRewriteContext.RES_NAME_UNKNOWN) {
				if (!allowAmbiguity || res == ImportRewriteContext.RES_NAME_FOUND) {
					return res;
				}
			}
		}
		return ImportRewriteContext.RES_NAME_UNKNOWN;
	}

	/**
	 * Adds a new import to the rewriter's record and returns a type reference
	 * that can be used in the code. The type binding can only be an array or
	 * non-generic type.
	 * <p>
	 * No imports are added for types that are already known. If a import for a
	 * type is recorded to be removed, this record is discarded instead.
	 * </p>
	 * <p>
	 * The content of the compilation unit itself is actually not modified in
	 * any way by this method; rather, the rewriter just records that a new
	 * import has been added.
	 * </p>
	 * 
	 * @param qualifiedTypeName
	 *            the qualified type name of the type to be added
	 * @param context
	 *            an optional context that knows about types visible in the
	 *            current scope or <code>null</code> to use the default context
	 *            only using the available imports.
	 * @return returns a type to which the type binding can be assigned to. The
	 *         returned type contains is unqualified when an import could be
	 *         added or was already known. It is fully qualified, if an import
	 *         conflict prevented the import.
	 */
	public String addImport(String qualifiedTypeName, ImportRewriteContext context) {
		int angleBracketOffset = qualifiedTypeName.indexOf('<');
		if (angleBracketOffset != -1) {
			return internalAddImport(qualifiedTypeName.substring(0, angleBracketOffset), context)
					+ qualifiedTypeName.substring(angleBracketOffset);
		}
		int bracketOffset = qualifiedTypeName.indexOf('[');
		if (bracketOffset != -1) {
			return internalAddImport(qualifiedTypeName.substring(0, bracketOffset), context)
					+ qualifiedTypeName.substring(bracketOffset);
		}
		return internalAddImport(qualifiedTypeName, context);
	}

	/**
	 * Adds a new import to the rewriter's record and returns a type reference
	 * that can be used in the code. The type binding can only be an array or
	 * non-generic type.
	 * <p>
	 * No imports are added for types that are already known. If a import for a
	 * type is recorded to be removed, this record is discarded instead.
	 * </p>
	 * <p>
	 * The content of the compilation unit itself is actually not modified in
	 * any way by this method; rather, the rewriter just records that a new
	 * import has been added.
	 * </p>
	 * 
	 * @param qualifiedTypeName
	 *            the qualified type name of the type to be added
	 * @return returns a type to which the type binding can be assigned to. The
	 *         returned type contains is unqualified when an import could be
	 *         added or was already known. It is fully qualified, if an import
	 *         conflict prevented the import.
	 */
	public String addImport(String qualifiedTypeName) {
		return addImport(qualifiedTypeName, this.defaultContext);
	}

	private String internalAddImport(String fullTypeName, ImportRewriteContext context) {
		int idx = fullTypeName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
		String typeContainerName, typeName;
		if (idx != -1) {
			typeContainerName = fullTypeName.substring(0, idx);
			if (typeContainerName.length() == 0) {
				typeContainerName = ENCLOSING_TYPE_SEPARATOR;
			}
			typeName = fullTypeName.substring(idx + 1);
		} else {
			typeContainerName = ENCLOSING_TYPE_SEPARATOR;
			typeName = fullTypeName;
		}

		if (typeContainerName.length() == 0) {
			return fullTypeName;
		}

		if (context == null)
			context = this.defaultContext;

		int res = context.findInContext(typeContainerName, typeName, ImportRewriteContext.KIND_TYPE);
		if (res == ImportRewriteContext.RES_NAME_CONFLICT) {
			return fullTypeName;
		}
		if (res == ImportRewriteContext.RES_NAME_UNKNOWN) {
			addEntry(NORMAL_PREFIX + fullTypeName);
		}
		return typeName;
	}

	private void addEntry(String entry) {
		this.existingImports.add(entry);

		if (this.removedImports != null) {
			if (this.removedImports.remove(entry)) {
				return;
			}
		}

		if (this.addedImports == null) {
			this.addedImports = new ArrayList<String>();
		}
		this.addedImports.add(entry);
	}

	private boolean removeEntry(String entry) {
		if (this.existingImports.remove(entry)) {
			if (this.addedImports != null) {
				if (this.addedImports.remove(entry)) {
					return true;
				}
			}
			if (this.removedImports == null) {
				this.removedImports = new ArrayList<String>();
			}
			this.removedImports.add(entry);
			return true;
		}
		return false;
	}

	/**
	 * Records to remove a import. No remove is recorded if no such import
	 * exists or if such an import is recorded to be added. In that case the
	 * record of the addition is discarded.
	 * <p>
	 * The content of the compilation unit itself is actually not modified in
	 * any way by this method; rather, the rewriter just records that an import
	 * has been removed.
	 * </p>
	 * 
	 * @param qualifiedName
	 *            The import name to remove.
	 * @return <code>true</code> is returned of an import of the given name
	 *         could be found.
	 */
	public boolean removeImport(String qualifiedName) {
		return removeEntry(NORMAL_PREFIX + qualifiedName);
	}

	/**
	 * Converts all modifications recorded by this rewriter into an object
	 * representing the corresponding text edits to the source code of the
	 * rewrite's compilation unit. The compilation unit itself is not modified.
	 * <p>
	 * Calling this methods does not discard the modifications on record.
	 * Subsequence modifications are added to the ones already on record. If
	 * this method is called again later, the resulting text edit object will
	 * accurately reflect the net cumulative affect of all those changes.
	 * </p>
	 * 
	 * @param monitor
	 *            the progress monitor or <code>null</code>
	 * @return text edit object describing the changes to the document
	 *         corresponding to the changes recorded by this rewriter
	 * @throws CoreException
	 *             the exception is thrown if the rewrite fails.
	 */
	public final TextEdit rewriteImports(IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		try {
			monitor.beginTask("Updating uses", 2); //$NON-NLS-1$
			if (!hasRecordedChanges()) {
				this.createdImports = CharOperation.NO_STRINGS;
				return new MultiTextEdit();
			}

			Program usedAstRoot = this.astRoot;
			if (usedAstRoot == null) {
				ASTParser parser = ASTParser.newParser(this.compilationUnit);
				usedAstRoot = parser.createAST(SubMonitor.convert(monitor, 1));
			}

			ImportRewriteAnalyzer computer = new ImportRewriteAnalyzer(this.compilationUnit, usedAstRoot,
					this.currentPosition, this.importOrder, this.restoreExistingImports);
			computer.setFilterImplicitImports(this.filterImplicitImports);

			if (this.addedImports != null) {
				for (int i = 0; i < this.addedImports.size(); i++) {
					String curr = this.addedImports.get(i);
					computer.addImport(curr.substring(1), STATIC_PREFIX == curr.charAt(0));
				}
			}

			if (this.removedImports != null) {
				for (int i = 0; i < this.removedImports.size(); i++) {
					String curr = this.removedImports.get(i);
					computer.removeImport(curr.substring(1));
				}
			}

			TextEdit result = computer.getResultingEdits(SubMonitor.convert(monitor, 1));
			this.createdImports = computer.getCreatedImports();
			return result;
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		} finally {
			monitor.done();
		}
		return null;
	}

	/**
	 * Returns all new non-static imports created by the last invocation of
	 * {@link #rewriteImports(IProgressMonitor)} or <code>null</code> if these
	 * methods have not been called yet.
	 * <p>
	 * Note that this list doesn't need to be the same as the added imports (see
	 * {@link #getAddedImports()}) as implicit imports are not created and some
	 * imports are represented by on-demand imports instead.
	 * </p>
	 * 
	 * @return the created imports
	 */
	public String[] getCreatedImports() {
		return this.createdImports;
	}

	/**
	 * Returns all non-static imports that are recorded to be added.
	 * 
	 * @return the imports recorded to be added.
	 */
	public String[] getAddedImports() {
		return filterFromList(this.addedImports, NORMAL_PREFIX);
	}

	/**
	 * Returns all static imports that are recorded to be added.
	 * 
	 * @return the static imports recorded to be added.
	 */
	public String[] getAddedStaticImports() {
		return filterFromList(this.addedImports, STATIC_PREFIX);
	}

	/**
	 * Returns all non-static imports that are recorded to be removed.
	 * 
	 * @return the imports recorded to be removed.
	 */
	public String[] getRemovedImports() {
		return filterFromList(this.removedImports, NORMAL_PREFIX);
	}

	/**
	 * Returns all static imports that are recorded to be removed.
	 * 
	 * @return the static imports recorded to be removed.
	 */
	public String[] getRemovedStaticImports() {
		return filterFromList(this.removedImports, STATIC_PREFIX);
	}

	/**
	 * Returns <code>true</code> if imports have been recorded to be added or
	 * removed.
	 * 
	 * @return boolean returns if any changes to imports have been recorded.
	 */
	public boolean hasRecordedChanges() {
		return !this.restoreExistingImports || (this.addedImports != null && !this.addedImports.isEmpty())
				|| (this.removedImports != null && !this.removedImports.isEmpty());
	}

	private static String[] filterFromList(List<String> imports, char prefix) {
		if (imports == null) {
			return CharOperation.NO_STRINGS;
		}
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < imports.size(); i++) {
			String curr = imports.get(i);
			if (prefix == curr.charAt(0)) {
				res.add(curr.substring(1));
			}
		}
		return res.toArray(new String[res.size()]);
	}

}
