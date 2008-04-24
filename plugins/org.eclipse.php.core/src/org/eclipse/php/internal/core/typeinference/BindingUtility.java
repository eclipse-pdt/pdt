package org.eclipse.php.internal.core.typeinference;

import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.internal.core.SourceRefElement;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.wst.sse.core.internal.Logger;

/**
 * This utility allows to determine types of expressions represented in AST tree.
 * Results are cached until instance of this class is alive.
 */
public class BindingUtility {

	/** Default time limit for type inference evaluation */
	private static final int TIME_LIMIT = 500;

	private ISourceModule sourceModule;
	private ASTNode rootNode;
	private Map<SourceRange, IEvaluatedType> evaluatedTypesCache = new HashMap<SourceRange, IEvaluatedType>();
	private int timeLimit = TIME_LIMIT;

	/**
	 * Creates new instance of binding utility.
	 * @param sourceModule Source module of the file.
	 */
	public BindingUtility(ISourceModule sourceModule) {
		this.sourceModule = sourceModule;
		this.rootNode = SourceParserUtil.getModuleDeclaration(sourceModule, null);
	}

	/**
	 * Creates new instance of binding utility
	 * @param sourceModule Source module of the file.
	 * @param rootNode AST tree of the the file represented by the given source module.
	 */
	public BindingUtility(ISourceModule sourceModule, ASTNode rootNode) {
		this.sourceModule = sourceModule;
		this.rootNode = rootNode;
	}

	/**
	 * Sets new time limit in milliseconds for the type inference evaluation. Default value is 500ms.
	 * @param timeLimit
	 */
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * This method returns evaluated type for the given AST expression node.
	 * Returns cached type from previous evaluations (if exists).
	 *
	 * @param node AST node that needs to be evaluated.
	 * @return evaluated type.
	 *
	 * @throws IllegalArgumentException in case if context cannot be found for the given node.
	 * @throws NullPointerException if the given node is <code>null</code>.
	 */
	public IEvaluatedType getType(ASTNode node) {
		if (node == null) {
			throw new NullPointerException();
		}
		return getType(new SourceRange(node));
	}

	/**
	 * This method returns evaluated type for the given model element.
	 * Returns cached type from previous evaluations (if exists).
	 *
	 * @param element Model element.
	 * @return evaluated type.
	 *
	 * @throws IllegalArgumentException in case if context cannot be found for the given node.
	 * @throws NullPointerException if the given element is <code>null</code>.
	 * @throws ModelException
	 */
	public IEvaluatedType getType(SourceRefElement element) throws ModelException {
		if (element == null) {
			throw new NullPointerException();
		}
		ISourceModule elementModule = element.getSourceModule();
		if (!elementModule.equals(sourceModule)) {
			throw new IllegalArgumentException("Unknown model element");
		}
		return getType(new SourceRange(element.getSourceRange()));
	}

	/**
	 * This method returns evaluated type for the expression under the given offset and length.
	 * Returns cached type from previous evaluations (if exists).
	 *
	 * @param startOffset Starting offset of the expression.
	 * @param length Length of the expression.
	 * @return evaluated type.
	 *
	 * @throws IllegalArgumentException in case if context cannot be found for the given node.
	 */
	public IEvaluatedType getType(int startOffset, int length) {
		return getType(new SourceRange(startOffset, length));
	}

	protected IEvaluatedType getType(SourceRange sourceRange, IContext context, ASTNode node) {
		PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
		return typeInferencer.evaluateType(new ExpressionTypeGoal(context, node), timeLimit);
	}
	
	protected ContextFinder getContext(SourceRange sourceRange) {
		ContextFinder contextFinder = new ContextFinder(sourceRange);
		try {
			rootNode.traverse(contextFinder);
		} catch (Exception e) {
			Logger.logException(e);
			return null;
		}
		if (contextFinder.getNode() == null) {
			throw new IllegalArgumentException("AST node can not be found for the given source range: " + sourceRange);
		}
		return contextFinder;
	}
	
	protected IEvaluatedType getType(SourceRange sourceRange) {
		if (!evaluatedTypesCache.containsKey(sourceRange)) {
			ContextFinder contextFinder = getContext(sourceRange);
			evaluatedTypesCache.put(sourceRange, getType(sourceRange, contextFinder.getContext(), contextFinder.getNode()));
		}
		return evaluatedTypesCache.get(sourceRange);
	}
	
	/**
	 * This method returns model elements for the given AST expression node.
	 * This method uses cached evaluated type from previous evaluations (if exists).
	 *
	 * @param node AST node that needs to be evaluated.
	 * @return model element or <code>null</code> in case it couldn't be found
	 *
	 * @throws IllegalArgumentException in case if context cannot be found for the given node.
	 * @throws NullPointerException if the given node is <code>null</code>.
	 */
	public IModelElement[] getModelElement(ASTNode node) {
		if (node == null) {
			throw new NullPointerException();
		}
		return getModelElement(new SourceRange(node));
	}

	/**
	 * This method returns model elements for the given model element.
	 * This method uses cached evaluated type from previous evaluations (if exists).
	 *
	 * @param element Source Model element.
	 * @return model element or <code>null</code> in case it couldn't be found
	 *
	 * @throws IllegalArgumentException in case if context cannot be found for the given node.
	 * @throws NullPointerException if the given element is <code>null</code>.
	 * @throws ModelException
	 */
	public IModelElement[] getModelElement(SourceRefElement element) throws ModelException {
		if (element == null) {
			throw new NullPointerException();
		}
		ISourceModule elementModule = element.getSourceModule();
		if (!elementModule.equals(sourceModule)) {
			throw new IllegalArgumentException("Unknown model element");
		}
		return getModelElement(new SourceRange(element.getSourceRange()));
	}

	/**
	 * This method returns model elements for the expression under the given offset and length.
	 * This method uses cached evaluated type from previous evaluations (if exists).
	 *
	 * @param startOffset Starting offset of the expression.
	 * @param length Length of the expression.
	 * @return model element or <code>null</code> in case it couldn't be found
	 *
	 * @throws IllegalArgumentException in case if context cannot be found for the given node.
	 */
	public IModelElement[] getModelElement(int startOffset, int length) {
		return getModelElement(new SourceRange(startOffset, length));
	}
	
	protected IModelElement[] getModelElement(SourceRange sourceRange) {
		ContextFinder contextFinder = getContext(sourceRange);
		if (!evaluatedTypesCache.containsKey(sourceRange)) {
			evaluatedTypesCache.put(sourceRange, getType(sourceRange, contextFinder.getContext(), contextFinder.getNode()));
		}
		IEvaluatedType evaluatedType = evaluatedTypesCache.get(sourceRange);
		return PHPTypeInferenceUtils.getModelElements(evaluatedType, (BasicContext) contextFinder.getContext());
	}

	private class SourceRange {
		private final int offset;
		private final int length;

		public SourceRange(ISourceRange sourceRange) {
			offset = sourceRange.getOffset();
			length = sourceRange.getLength();
		}

		public SourceRange(int offset, int length){
			this.length= length;
			this.offset= offset;
		}

		public SourceRange(ASTNode node) {
			this(node.sourceStart(), node.sourceEnd() - node.sourceStart());
		}

		public int getLength() {
			return length;
		}

		public int getOffset() {
			return offset;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + length;
			result = prime * result + offset;
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			SourceRange other = (SourceRange) obj;
			if (length != other.length) {
				return false;
			}
			if (offset != other.offset) {
				return false;
			}
			return true;
		}

		public String toString() {
			return new StringBuilder("<offset=").append(offset).append(", length=").append(length).append(">").toString();
		}
	}

	/**
	 * Finds binding context for the given AST node for internal usages only.
	 */
	private class ContextFinder extends ASTVisitor {

		private SourceRange sourceRange;
		private IContext context;
		private ASTNode node;
		private Stack<IContext> contextStack = new Stack<IContext>();

		public ContextFinder(SourceRange sourceRange) {
			this.sourceRange = sourceRange;
		}

		/**
		 * Returns found context
		 * @return found context
		 */
		public IContext getContext() {
			return context;
		}

		/**
		 * Returns found AST node
		 * @return AST node
		 */
		public ASTNode getNode() {
			return node;
		}

		public boolean visitGeneral(ASTNode node) throws Exception {
			if (node.sourceStart() == sourceRange.offset && node.sourceEnd() - node.sourceStart() == sourceRange.getLength()) {
				context = contextStack.peek();
				this.node = node;
				return false;
			}
			return context == null;
		}

		public boolean visit(ModuleDeclaration node) throws Exception {
			contextStack.push(new BasicContext(sourceModule, node));
			return visitGeneral(node);
		}

		public boolean visit(TypeDeclaration node) throws Exception {
			contextStack.push(new InstanceContext((ISourceModuleContext) contextStack.peek(), new PHPClassType(node.getName())));
			return visitGeneral(node);
		}

		@SuppressWarnings("unchecked")
		public boolean visit(MethodDeclaration node) throws Exception {
			List<String> argumentsList = new LinkedList<String>();
			List<IEvaluatedType> argTypes = new LinkedList<IEvaluatedType>();
			List<Argument> args = node.getArguments();
			for (Argument a : args) {
				argumentsList.add(a.getName());
				argTypes.add(UnknownType.INSTANCE);
			}
			IContext parent = contextStack.peek();
			ModuleDeclaration rootNode = ((ISourceModuleContext)parent).getRootNode();
			contextStack.push(new MethodContext(parent, sourceModule, rootNode, node, argumentsList.toArray(new String[argumentsList.size()]), argTypes.toArray(new IEvaluatedType[argTypes.size()])));
			return visitGeneral(node);
		}

		public boolean endvisit(ModuleDeclaration node) throws Exception {
			contextStack.pop();
			return visitGeneral(node);
		}

		public boolean endvisit(TypeDeclaration node) throws Exception {
			contextStack.pop();
			return visitGeneral(node);
		}

		public boolean endvisit(MethodDeclaration node) throws Exception {
			contextStack.pop();
			endvisitGeneral(node);
			return true;
		}
	}
}
