/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.codeassist;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.hierarchy.TypeHierarchy;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.core.compiler.ast.nodes.PHPModuleDeclaration;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestor;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.scope.ScopeParser;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.exceptions.ResourceAlreadyExists;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * This companion is shared between different completion contexts, and it can be
 * used for caching information gathered by resource-intensive operations.
 * 
 * @author michael
 */
public class CompletionCompanion {

	/**
	 * Cache for calculated return types by document position
	 */
	private Map<Integer, IType[]> rhTypesCache = new HashMap<>();

	/**
	 * Cache for calculated super type hierarchy
	 */
	private Map<IType, ITypeHierarchy> superHierarchyCache = new HashMap<>();
	private IStructuredDocument document;
	private int offset;
	private String currentNamespaceName;
	private ISourceRange currentNamespaceRange;
	private PHPVersion phpVersion;
	private ISourceModule sourceModule;
	private IStructuredDocumentRegion structuredDocumentRegion;
	private ITextRegionCollection regionCollection;
	private IPHPScriptRegion phpScriptRegion;
	private String partitionType;
	private PHPModuleDeclaration phpModuleDeclaration;
	private ICompletionScope scope;

	private static class FakeTypeHierarchy extends TypeHierarchy {
		public FakeTypeHierarchy() {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=494388
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=498339
			// We must initialize the internal properties to avoid NPEs
			// when using this class methods.
			initialize(1);
		}
	}

	public CompletionCompanion(CompletionRequestor requestor, IModuleSource moduleSource, int offset) {
		this.offset = offset;
		this.sourceModule = (ISourceModule) moduleSource.getModelElement();
		this.phpVersion = ProjectOptions.getPHPVersion(getSourceModule().getScriptProject().getProject());
		try {
			this.document = determineDocument(sourceModule, requestor);
			if (this.document == null) {
				return;
			}
			structuredDocumentRegion = determineStructuredDocumentRegion(document, offset);
			if (structuredDocumentRegion == null) {
				return;
			}
			regionCollection = determineRegionCollection(document, structuredDocumentRegion, offset);
			if (regionCollection == null) {
				return;
			}
			phpScriptRegion = determinePHPRegion(document, regionCollection, offset);
			if (phpScriptRegion == null) {
				return;
			}
			partitionType = determinePartitionType(regionCollection, phpScriptRegion, offset);
			if (partitionType != null) {
				determineScope();
				determineNamespace();
			}
		} catch (ResourceAlreadyExists | IOException | CoreException | BadLocationException e) {
			Logger.logException(e);
		}
	}

	/**
	 * Returns document associated with the editor where code assist was requested
	 * 
	 * @return document
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	public final IStructuredDocument getDocument() {
		return document;
	}

	/**
	 * Returns the relevant region collection of the place in PHP code where
	 * completion was requested
	 * 
	 * @return text region collection
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	public final ITextRegionCollection getRegionCollection() {
		return regionCollection;
	}

	/**
	 * Returns the PHP script region of PHP code where completion was requested
	 * 
	 * @return php script region (see {@link IPHPScriptRegion})
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	public final IPHPScriptRegion getPHPScriptRegion() {
		return phpScriptRegion;
	}

	/**
	 * Returns partition type of the code where cursor is located.
	 * 
	 * @return partition type (see {@link PHPRegionTypes})
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	public final String getPartitionType() {
		return partitionType;
	}

	public final IStructuredDocumentRegion getStructuredDocumentRegion() {
		return structuredDocumentRegion;
	}

	/**
	 * Returns PHP version of the file where code assist was requested
	 * 
	 * @return PHP version
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	public final PHPVersion getPHPVersion() {
		return phpVersion;
	}

	/**
	 * Returns offset of the cursor position when code assist was invoked
	 * 
	 * @return offset
	 */
	public final int getOffset() {
		return offset;
	}

	/**
	 * Determines the document associated with the editor where code assist has been
	 * invoked.
	 * 
	 * @param module
	 *            Source module ({@link ISourceModule})
	 * @param requestor
	 *            Completion requestor ({@link CompletionRequestor})
	 * @return structured document or <code>null</code> if it couldn't be found
	 * @throws CoreException
	 * @throws IOException
	 * @throws ResourceAlreadyExists
	 */
	private IStructuredDocument determineDocument(ISourceModule module, CompletionRequestor requestor)
			throws ResourceAlreadyExists, IOException, CoreException {
		IStructuredDocument document = null;

		if (requestor instanceof IPHPCompletionRequestor) {
			IDocument d = ((IPHPCompletionRequestor) requestor).getDocument();
			if (d instanceof IStructuredDocument) {
				document = (IStructuredDocument) d;
			}
		}
		if (document == null) {
			IStructuredModel structuredModel = null;
			try {
				IFile file = (IFile) module.getResource();
				if (file != null) {
					if (file.exists()) {
						structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(file);
						if (structuredModel != null) {
							document = structuredModel.getStructuredDocument();
						} else {
							document = StructuredModelManager.getModelManager().createStructuredDocumentFor(file);
						}
					} else {
						document = StructuredModelManager.getModelManager().createNewStructuredDocumentFor(file);
						document.set(module.getSource());
					}
				}
			} finally {
				if (structuredModel != null) {
					structuredModel.releaseFromRead();
				}
			}
		}

		return document;
	}

	/**
	 * Determines the structured document region of the place in PHP code where
	 * completion was requested
	 * 
	 * @return structured document region or <code>null</code> in case it could not
	 *         be determined
	 */
	private IStructuredDocumentRegion determineStructuredDocumentRegion(IStructuredDocument document, int offset) {

		IStructuredDocumentRegion sdRegion = null;

		int lastOffset = offset;
		// find the structured document region:
		while (sdRegion == null && lastOffset >= 0) {
			sdRegion = document.getRegionAtCharacterOffset(lastOffset);
			lastOffset--;
		}

		return sdRegion;
	}

	/**
	 * Determines the relevant region collection of the place in PHP code where
	 * completion was requested
	 * 
	 * @return text region collection or <code>null</code> in case it could not be
	 *         determined
	 */
	private ITextRegionCollection determineRegionCollection(IStructuredDocument document,
			IStructuredDocumentRegion sdRegion, int offset) {
		ITextRegionCollection regionCollection = sdRegion;

		ITextRegion textRegion = determineTextRegion(document, sdRegion, offset);
		if (textRegion instanceof ITextRegionContainer) {
			regionCollection = (ITextRegionContainer) textRegion;
		}
		return regionCollection;
	}

	/**
	 * Determines the text region from the text region collection and offset
	 * 
	 * @param regionCollection
	 * @param offset
	 */
	private ITextRegion determineTextRegion(IStructuredDocument document, ITextRegionCollection regionCollection,
			int offset) {
		ITextRegion textRegion;
		// in case we are at the end of the document, asking for completion
		if (offset == document.getLength()) {
			textRegion = regionCollection.getLastRegion();
		} else {
			textRegion = regionCollection.getRegionAtCharacterOffset(offset);
		}
		return textRegion;
	}

	/**
	 * Determines the PHP script region of PHP code where completion was requested
	 * 
	 * @return php script region or <code>null</code> in case it could not be
	 *         determined
	 */
	private IPHPScriptRegion determinePHPRegion(IStructuredDocument document, ITextRegionCollection regionCollection,
			int offset) {
		ITextRegion textRegion = determineTextRegion(document, regionCollection, offset);
		IPHPScriptRegion phpScriptRegion = null;

		if (textRegion != null) {
			if (textRegion.getType() == PHPRegionContext.PHP_OPEN) {
				return null;
			} else if (textRegion.getType() == PHPRegionContext.PHP_CLOSE) {
				if (regionCollection.getStartOffset(textRegion) == offset) {
					textRegion = regionCollection.getRegionAtCharacterOffset(offset - 1);
				} else {
					return null;
				}
			}
		}

		if (textRegion instanceof IPHPScriptRegion) {
			phpScriptRegion = (IPHPScriptRegion) textRegion;
		}

		return phpScriptRegion;
	}

	/**
	 * Determines the partition type of the code where cursor is located.
	 * 
	 * @param regionCollection
	 *            Text region collection
	 * @param phpScriptRegion
	 *            PHP script region
	 * @param offset
	 * @return partition type (see {@link PHPRegionTypes})
	 * @throws BadLocationException
	 */
	private String determinePartitionType(ITextRegionCollection regionCollection, IPHPScriptRegion phpScriptRegion,
			int offset) throws BadLocationException {
		int internalOffset = getOffset(offset, regionCollection, phpScriptRegion);
		String partitionType = phpScriptRegion.getPartition(internalOffset);

		// if we are at the begining of multi-line comment or docBlock then we
		// should get completion.
		if (partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT || partitionType == PHPPartitionTypes.PHP_DOC) {
			String regionType = phpScriptRegion.getPHPToken(internalOffset).getType();
			if (PHPPartitionTypes.isPHPMultiLineCommentStartRegion(regionType)
					|| PHPPartitionTypes.isPHPDocStartRegion(regionType)) {
				if (phpScriptRegion.getPHPToken(internalOffset).getStart() == internalOffset) {
					partitionType = phpScriptRegion.getPartition(internalOffset - 1);
				}
			}
		}
		return partitionType;
	}

	private void determineNamespace() {
		ICompletionScope parent = scope.findParent(Type.NAMESPACE);

		if (parent != null) {
			currentNamespaceName = parent.getName();
			currentNamespaceRange = parent;
		} else {
			currentNamespaceRange = scope.findParent(Type.FILE);
		}
	}

	/*
	 * Returns the file where code assist was requested
	 * 
	 * @return source module
	 * 
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	@NonNull
	public final ISourceModule getSourceModule() {
		return sourceModule;
	}

	/**
	 * Calculates type for the left hand part in expression enclosed by given
	 * statement text.
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * 1. If statement text contains &quot;$a-&gt;foo()-&gt;&quot; the result will be the return type of method 'foo'
	 * 2. If statement text contains &quot;A::&quot; the result will be class 'A'
	 * 3. etc...
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param context
	 *            Completion context
	 * @return right hand type(s) for the expression that encloses current offset
	 */
	public IType[] getLeftHandType(ICompletionContext context) {
		AbstractCompletionContext aContext = (AbstractCompletionContext) context;
		if (!rhTypesCache.containsKey(offset)) {

			TextSequence statementText = aContext.getStatementText();
			int triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementText.length());
			triggerEnd = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, triggerEnd, true);
			triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, triggerEnd);

			rhTypesCache.put(offset, CodeAssistUtils.getTypesFor(getSourceModule(), statementText, triggerEnd, offset));
		}
		return rhTypesCache.get(offset);
	}

	public IType[] getLeftHandType(ICompletionContext context, boolean isType) {
		AbstractCompletionContext aContext = (AbstractCompletionContext) context;
		if (!rhTypesCache.containsKey(offset)) {
			TextSequence statementText = aContext.getStatementText();
			int triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementText.length());
			triggerEnd = PHPTextSequenceUtilities.readIdentifierStartIndex(statementText, triggerEnd, true);
			triggerEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, triggerEnd);

			if (isType) {
				rhTypesCache.put(offset,
						CodeAssistUtils.getTypesFor(getSourceModule(), statementText, triggerEnd, offset));
			} else {
				rhTypesCache.put(offset,
						CodeAssistUtils.getTraitsFor(getSourceModule(), statementText, triggerEnd, offset));
			}
		}
		return rhTypesCache.get(offset);
	}

	/**
	 * Calculates super type hierarchy
	 * 
	 * @throws ModelException
	 */
	public ITypeHierarchy getSuperTypeHierarchy(IType type, IProgressMonitor monitor) throws ModelException {
		if (!PHPToolkitUtil.isFromPHPProject(type)) {
			return new FakeTypeHierarchy();
		}
		if (!superHierarchyCache.containsKey(type)) {
			superHierarchyCache.put(type, type.newSupertypeHierarchy(monitor));
		}
		return superHierarchyCache.get(type);
	}

	private int getOffset(int offset, ITextRegionCollection regionCollection, IPHPScriptRegion phpScriptRegion) {
		int result = offset - regionCollection.getStartOffset() - phpScriptRegion.getStart() - 1;
		if (result < 0) {
			result = 0;
		}
		return result;
	}

	/**
	 * Returns PHP token under offset
	 * 
	 * @return PHP token
	 * @throws BadLocationException
	 */
	public final ITextRegion getPHPToken() throws BadLocationException {
		return getPHPToken(offset);
	}

	public ITextRegion getPHPToken(int offset) throws BadLocationException {
		return phpScriptRegion.getPHPToken(getOffset(offset, regionCollection, phpScriptRegion));
	}

	public boolean isGlobalNamespace() {
		return getCurrentNamespace() == null;
	}

	public String getCurrentNamespace() {
		return currentNamespaceName;
	}

	public ISourceRange getCurrentNamespaceRange() {
		return currentNamespaceRange;
	}

	public PHPModuleDeclaration getModuleDeclaration() {
		if (phpModuleDeclaration == null) {
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(getSourceModule(), null);
			if (moduleDeclaration instanceof PHPModuleDeclaration) {
				phpModuleDeclaration = (PHPModuleDeclaration) moduleDeclaration;
			}
		}

		return phpModuleDeclaration;
	}

	private void determineScope() {
		ScopeParser scopeParser = new ScopeParser(document);
		scope = scopeParser.parse(offset);
	}

	@SuppressWarnings("null")
	@NonNull
	public ICompletionScope getScope() {
		if (scope == null) {
			determineScope();
			currentNamespaceRange = scope;
		}

		return scope;
	}
}
