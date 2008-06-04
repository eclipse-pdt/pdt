package org.eclipse.php.internal.core.callhierarchy;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.ICallProcessor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.MethodReferenceMatch;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPCallProcessor implements ICallProcessor {

	public final static int GENERICS_AGNOSTIC_MATCH_RULE = SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE | SearchPattern.R_ERASURE_MATCH;

	private SearchEngine searchEngine = new SearchEngine();

	public Map<SimpleReference, IModelElement> process(final IModelElement parent, final IModelElement element, IDLTKSearchScope scope, IProgressMonitor monitor) {
		
		final Map<SimpleReference, IModelElement> elements = new HashMap<SimpleReference, IModelElement>();
		
		SearchRequestor requestor = new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match) {
				if ((match.getAccuracy() != SearchMatch.A_ACCURATE)) {
					return;
				}
				if (match.isInsideDocComment()) {
					return;
				}
				if (match instanceof MethodReferenceMatch) {
					MethodReferenceMatch methodRefMatch = (MethodReferenceMatch) match; 
					IMethod method = (IMethod) match.getElement();
					PHPCallExpression callExpression = (PHPCallExpression) methodRefMatch.getNode();
					ASTNode receiver = callExpression.getReceiver();
					
					// check that the method call receiver has the same type as original element class:
					if (element.getParent() instanceof IType && receiver != null) {
						IType declaringType = (IType) element.getParent();
						IEvaluatedType resolvedType = PHPTypeInferenceUtils.resolveExpression(method.getSourceModule(), receiver);
						if (resolvedType.getTypeName().equalsIgnoreCase(declaringType.getElementName())) {
							SimpleReference ref = new SimpleReference(match.getOffset(), match.getOffset() + match.getLength(), ""); //$NON-NLS-1$
							elements.put(ref, method);
						}
					} else if (receiver == null) { // if this the original element is function, receiver must be null
						SimpleReference ref = new SimpleReference(match.getOffset(), match.getOffset() + match.getLength(), ""); //$NON-NLS-1$
						elements.put(ref, method);
					}
				}
			}
		};

		SearchPattern pattern = SearchPattern.createPattern(element, IDLTKSearchConstants.REFERENCES, GENERICS_AGNOSTIC_MATCH_RULE, scope.getLanguageToolkit());
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, requestor, monitor);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return elements;
	}
}
