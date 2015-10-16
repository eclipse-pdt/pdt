/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Yannick de Lange <yannickl88@gmail.com>
 *******************************************************************************/
package org.eclipse.php.internal.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.corext.util.DocumentUtils;
import org.eclipse.php.internal.core.search.Messages;

/**
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class OrganizeBuildParticipantFactory extends AbstractBuildParticipantType implements IExecutableExtension {

	private static final String UNUSED_MESSAGE = CoreMessages.getString("use_unused"); //$NON-NLS-1$

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project) throws CoreException {

		if (natureId != null) {
			return new OrganizeBuildParticipantParticipant();
		}
		return null;
	}

	private String natureId = null;

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		natureId = config.getAttribute("nature"); //$NON-NLS-1$
	}

	private static class OrganizeBuildParticipantParticipant implements IBuildParticipant {
		@Override
		public void build(IBuildContext context) throws CoreException {
			IModelElement element = context.getModelElement();
			if (LibraryFolderManager.getInstance().isInLibraryFolder(element.getResource())) {
				// skip syntax check for code inside library folders
				return;
			}
			final ModuleDeclaration moduleDeclaration = (ModuleDeclaration) context
					.get(IBuildContext.ATTR_MODULE_DECLARATION);

			if (moduleDeclaration != null) {
				// Run the validation visitor:
				try {
					UseStatement[] statements = ASTUtils.getUseStatements(moduleDeclaration,
							context.getContents().length);

					moduleDeclaration.traverse(new ImportValidationVisitor(context, statements));
				} catch (Exception e) {
				}
			}
		}
	}

	private static class ImportValidationVisitor extends PHPASTVisitor {
		private IBuildContext context;
		private IDocument doc;
		private UseStatement[] statements;
		private NamespaceDeclaration currentNamespace;

		public ImportValidationVisitor(IBuildContext context, UseStatement[] statements) {
			this.context = context;
			this.statements = statements;
			doc = new Document(new String(context.getContents()));
		}

		public boolean visit(NamespaceDeclaration n) throws Exception {
			currentNamespace = n;

			return super.visit(n);
		}

		public boolean visit(UseStatement s) throws Exception {
			if (this.statements.length == 0) {
				return super.visit(s);
			}

			String total;
			if (this.currentNamespace != null && this.currentNamespace.isBracketed()) {
				total = DocumentUtils.stripUseStatements(statements, doc, this.currentNamespace.sourceStart(),
						this.currentNamespace.sourceEnd());
			} else {
				total = DocumentUtils.stripUseStatements(statements, doc);
			}
			boolean multiPart = s.getParts().size() > 1;
			for (UsePart part : s.getParts()) {
				if (DocumentUtils.containsUseStatement(part, total)) {
					continue;
				}
				int sourceStart = multiPart ? part.getNamespace().sourceStart() : s.sourceStart();
				int sourceEnd = multiPart ? part.getNamespace().sourceEnd() : s.sourceEnd();
				int lineNumber = context.getLineTracker().getLineNumberOfOffset(sourceStart);

				DefaultProblem problem = new DefaultProblem(context.getFile().getName(),
						Messages.format(UNUSED_MESSAGE, part.getNamespace().getFullyQualifiedName()),
						PhpProblemIdentifier.USE_STATEMENTS, new String[0], ProblemSeverities.Warning, sourceStart,
						sourceEnd, lineNumber, -1);

				context.getProblemReporter().reportProblem(problem);
			}

			return super.visit(s);
		}
	}

}
