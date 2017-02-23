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
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.php.ui.text.correction.IQuickFixProcessor;
import org.eclipse.php.ui.text.correction.IQuickFixProcessorExtension;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class QuickFixProcessor implements IQuickFixProcessor, IQuickFixProcessorExtension {

	@Override
	public IScriptCompletionProposal[] getCorrections(IInvocationContext context, IProblemLocation[] locations)
			throws CoreException {
		if (locations == null || locations.length == 0) {
			return null;
		}

		HashSet handledProblems = new HashSet(locations.length);
		ArrayList resultingCollections = new ArrayList();
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

	@Override
	public boolean hasCorrections(ISourceModule unit, int problemId) {
		return false;
	}

	private void process(IInvocationContext context, IProblemLocation problem, Collection proposals)
			throws CoreException {
		if (!(problem.getProblemIdentifier() instanceof PhpProblemIdentifier))
			return;

		PhpProblemIdentifier id = (PhpProblemIdentifier) problem.getProblemIdentifier();
		switch (id) {
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
		case AbstractMethodInAbstractClass:
		case AbstractMethodsInConcreteClass:
		case BodyForAbstractMethod:
		case MethodRequiresBody:
			return true;
		default:
			break;
		}
		return false;
	}

}
