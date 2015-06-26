/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.builder;

import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.organize.DocumentUtils;
import org.eclipse.php.core.organize.UseStatementBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

/**
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class OrganizeBuildParticipantFactory
		extends AbstractBuildParticipantType implements IExecutableExtension {

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {
		if (natureId != null) {
			return new OrganizeBuildParticipantParticipant();
		}
		return null;
	}

	private String natureId = null;

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		natureId = config.getAttribute("nature"); //$NON-NLS-1$
	}

	private static class OrganizeBuildParticipantParticipant
			implements IBuildParticipant {
		@Override
		public void build(IBuildContext context) throws CoreException {
			// Current file is being built:
			ISourceModule sourceModule = context.getSourceModule();
			// Get file AST:
			ModuleDeclaration declaration = SourceParserUtil
					.getModuleDeclaration(sourceModule);
			// Run the validation visitor:
			try {
				declaration.traverse(new ImportValidationVisitor(context));
			} catch (Exception e) {
			}
		}
	}

	private static class ImportValidationVisitor extends PHPASTVisitor {
		private IBuildContext context;
		private IDocument doc;

		public ImportValidationVisitor(IBuildContext context) {
			this.context = context;
			try {
				InputStreamReader in = new InputStreamReader(
						context.getFile().getContents());
				StringBuilder sb = new StringBuilder();
				final char[] buffer = new char[1024];

				while (true) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					sb.append(buffer, 0, rsz);
				}

				doc = new Document(sb.toString());
			} catch (CoreException e) {
			} catch (IOException e) {
			}
		}

		public boolean visit(UseStatement s) throws Exception {
			UseStatementBlock block = DocumentUtils.findUseStatmentsBlock(doc);

			if (block.end == -1) {
				return super.visit(s);
			}

			String total = doc.get().substring(block.end);

			for (UsePart part : s.getParts()) {
				if (DocumentUtils.containsUseStatementForClass(part, total)) {
					continue;
				}
				String warning = "Unused import '"
						+ part.getNamespace().getFullyQualifiedName()
						+ "', consider removing.";
				int lineNumber = context.getLineTracker()
						.getLineNumberOfOffset(s.sourceStart());

				DefaultProblem problem = new DefaultProblem(
						context.getFile().getName(), warning,
						PhpProblemIdentifier.NULL, new String[0],
						ProblemSeverities.Warning, s.sourceStart(),
						s.sourceEnd(), lineNumber, -1);

				context.getProblemReporter().reportProblem(problem);
			}

			return super.visit(s);
		}
	}

}
