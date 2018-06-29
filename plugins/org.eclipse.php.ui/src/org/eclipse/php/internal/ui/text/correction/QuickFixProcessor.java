/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPProblemIdentifier;
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
		List<IScriptCompletionProposal> resultingCollections = new ArrayList<>();
		for (int i = 0; i < locations.length; i++) {
			IProblemLocation curr = locations[i];
			IProblemIdentifier id = curr.getProblemIdentifier();
			if (handledProblems.add(id)) {
				process(context, curr, resultingCollections);
			}
		}
		return resultingCollections.toArray(new IScriptCompletionProposal[resultingCollections.size()]);
	}

	private void process(IInvocationContext context, IProblemLocation problem,
			Collection<IScriptCompletionProposal> proposals) throws CoreException {
		if (!(problem.getProblemIdentifier() instanceof PHPProblemIdentifier)) {
			return;
		}

		PHPProblemIdentifier id = (PHPProblemIdentifier) problem.getProblemIdentifier();
		switch (id) {
		case UnusedImport:
		case DuplicateImport:
		case UnnecessaryImport:
			ReorgCorrectionsSubProcessor.removeImportStatementProposals(context, problem, proposals);
			break;
		case ImportNotFound:
			ReorgCorrectionsSubProcessor.removeImportStatementProposals(context, problem, proposals);
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
		if (!(identifier instanceof PHPProblemIdentifier)) {
			return false;
		}
		PHPProblemIdentifier problem = (PHPProblemIdentifier) identifier;
		switch (problem) {
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
