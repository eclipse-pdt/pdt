package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.InstanceContext;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.MethodContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class ASTUtils {

	/**
	 * Finds minimal ast node, that covers given position
	 * 
	 * @param unit
	 * @param position
	 * @return
	 */
	public static ASTNode findMinimalNode(ModuleDeclaration unit, int start, int end) {

		class Visitor extends ASTVisitor {

			ASTNode result = null;
			int start, end;

			public Visitor(int start, int end) {
				this.start = start;
				this.end = end;
			}

			public ASTNode getResult() {
				return result;
			}

			public boolean visitGeneral(ASTNode s) throws Exception {
				int realStart = s.sourceStart();
				int realEnd = s.sourceEnd();
				if (s instanceof Block) {
					realStart = realEnd = -42; // never select on blocks
					// ssanders: BEGIN - Modify narrowing logic
				} else if (s instanceof TypeDeclaration) {
					TypeDeclaration declaration = (TypeDeclaration) s;
					realStart = declaration.sourceStart();
					realEnd = declaration.sourceEnd();
				} else if (s instanceof MethodDeclaration) {
					MethodDeclaration declaration = (MethodDeclaration) s;
					realStart = declaration.sourceStart();
					realEnd = declaration.sourceEnd();
				}
				if (realStart <= start && realEnd >= end) {
					if (result != null) {
						if ((s.sourceStart() >= result.sourceStart()) && (s.sourceEnd() <= result.sourceEnd()))
							result = s;
					} else {
						result = s;
					}
					// ssanders: END
					if (DLTKCore.DEBUG_SELECTION)
						System.out.println("Found " + s.getClass().getName()); //$NON-NLS-1$
				}
				return true;
			}

		}

		Visitor visitor = new Visitor(start, end);

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getResult();
	}

	/**
	 * Finds minimal ast node, that covers given position
	 * 
	 * @param unit
	 * @param position
	 * @return
	 */
	public static ASTNode findMaximalNodeEndingAt(ModuleDeclaration unit, final int boundaryOffset) {

		class Visitor extends ASTVisitor {
			ASTNode result = null;

			public ASTNode getResult() {
				return result;
			}

			public boolean visitGeneral(ASTNode s) throws Exception {
				if (s.sourceStart() < 0 || s.sourceEnd() < 0) {
					return true;
				}
				int sourceEnd = s.sourceEnd();
				if (Math.abs(sourceEnd - boundaryOffset) <= 0) {
					result = s;
				}
				return true;
			}

		}

		Visitor visitor = new Visitor();

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return visitor.getResult();
	}

	/**
	 * Finds type inference context for the given AST node.
	 * 
	 * @param sourceModule Source module element
	 * @param unit Module decalaration AST node 
	 * @param target AST node to find context for
	 */
	public static IContext findContext(final ISourceModule sourceModule, final ModuleDeclaration unit, final ASTNode target) {

		class Visitor extends ASTVisitor {

			private IContext context;
			private Stack<IContext> contextStack = new Stack<IContext>();

			/**
			 * Returns found context
			 * @return found context
			 */
			public IContext getContext() {
				return context;
			}

			public boolean visitGeneral(ASTNode node) throws Exception {
				if (node == target) {
					context = contextStack.peek();
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
				ModuleDeclaration rootNode = ((ISourceModuleContext) parent).getRootNode();
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

		Visitor visitor = new Visitor();

		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return visitor.getContext();
	}
}
