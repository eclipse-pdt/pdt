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
package org.eclipse.php.internal.ui.text.correction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.php.internal.core.compiler.ast.parser.PhpProblemIdentifier;
import org.eclipse.php.internal.ui.text.correction.proposals.AbstractCorrectionProposal;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.php.ui.text.correction.IQuickFixProcessor;

public class QuickFixProcessor implements IQuickFixProcessor {

	@Override
	public IScriptCompletionProposal[] getCorrections(IInvocationContext context, IProblemLocation[] locations)
			throws CoreException {
		if (locations == null || locations.length == 0) {
			return null;
		}

		HashSet<IProblemIdentifier> handledProblems = new HashSet<>(locations.length);
		ArrayList<AbstractCorrectionProposal> resultingCollections = new ArrayList<>();
		for (int i = 0; i < locations.length; i++) {
			IProblemLocation curr = locations[i];
			IProblemIdentifier id = curr.getProblemIdentifier();
			if (handledProblems.add(id)) {
				process(context, curr, resultingCollections);
			}
		}
		return (IScriptCompletionProposal[]) resultingCollections
				.toArray(new IScriptCompletionProposal[resultingCollections.size()]);
	}

	private void process(IInvocationContext context, IProblemLocation problem,
			Collection<AbstractCorrectionProposal> proposals) throws CoreException {
		if (!(problem.getProblemIdentifier() instanceof PhpProblemIdentifier))
			return;

		PhpProblemIdentifier id = (PhpProblemIdentifier) problem.getProblemIdentifier();
		switch (id) {
		case UnusedImport:
		case DuplicateImport:
		case UnnecessaryImport:
			ReorgCorrectionsSubProcessor.removeImportStatementProposals(context, problem, proposals);
			break;
		case ImportNotFound:
			ReorgCorrectionsSubProcessor.removeImportStatementProposals(context, problem, proposals);
			break;
		case FirstClassMustMatchFileName:
			ReorgCorrectionsSubProcessor.getWrongTypeNameProposals(context, problem, proposals);
			break;
		case UnexpectedNamespaceDeclaration:
			ReorgCorrectionsSubProcessor.getWrongNamespaceDeclNameProposals(context, problem, proposals);
			break;
		case ClassExtendFinalClass:
			ModifierCorrectionSubProcessor.addNonAccessibleReferenceProposal(context, problem, proposals,
					ModifierCorrectionSubProcessor.TO_NON_FINAL, IProposalRelevance.REMOVE_FINAL_MODIFIER);
			break;
		case AbstractMethodInAbstractClass:
		case BodyForAbstractMethod:
			ModifierCorrectionSubProcessor.addAbstractMethodProposals(context, problem, proposals);
			break;
		case AbstractMethodsInConcreteClass:
			ModifierCorrectionSubProcessor.addAbstractTypeProposals(context, problem, proposals);
			break;
		case MethodRequiresBody:
			ModifierCorrectionSubProcessor.addMethodRequiresBodyProposals(context, problem, proposals);
			break;
		case UndefinedType:
			UnresolvedElementsSubProcessor.getTypeProposals(context, problem, proposals);
			break;
		case AbstractMethodMustBeImplemented:
			LocalCorrectionsSubProcessor.addUnimplementedMethodsProposals(context, problem, proposals);
			break;
		case SuperclassMustBeAClass:
			LocalCorrectionsSubProcessor.getInterfaceExtendsClassProposals(context, problem, proposals);
			break;
		default:
			return;
		}
	}

	@Override
	public boolean hasCorrections(ISourceModule unit, IProblemIdentifier identifier) {
		if (!(identifier instanceof PhpProblemIdentifier))
			return false;
		PhpProblemIdentifier problem = (PhpProblemIdentifier) identifier;
		switch (problem) {
		case FirstClassMustMatchFileName:
		case UnexpectedNamespaceDeclaration:
		case AbstractMethodInAbstractClass:
		case AbstractMethodsInConcreteClass:
		case BodyForAbstractMethod:
		case MethodRequiresBody:
		case AbstractMethodMustBeImplemented:
		case ClassExtendFinalClass:
		case DuplicateImport:
		case ImportNotFound:
		case SuperclassMustBeAClass:
		case UndefinedType:
		case UnnecessaryImport:
		case UnusedImport:
			return true;
		default:
			break;
		}
		return false;
	}

}
