package org.eclipse.php.internal.ui.search;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.AbstractSourceModule;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.ClassName;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.internal.core.ast.nodes.IBinding;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Include;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Statement;
import org.eclipse.php.internal.ui.corext.ASTNodes;

public class IncludeOccurrencesFinder extends AbstractOccurrencesFinder {

	private static final String INCLUDE_POINT_OF = "Required by ''{0}()''";
	public static final String ID = "RequireFinder"; //$NON-NLS-1$
	private IModelElement source;
	private IBinding binding;
	private Include includeNode;
	private IType[] types;
	private IMethod[] methods;

	/**
	 * @param root the AST root
	 * @param node the selected node
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;

		this.includeNode = getIncludeExpression(node);
		if (this.includeNode != null) {
			binding = includeNode.resolveBinding();
			source = binding.getPHPElement();
			if (source != null) {
				AbstractSourceModule module = (AbstractSourceModule) source;
				try {
					this.types = module.getTypes();
					this.methods = module.getMethods();
					return null;
				} catch (ModelException e) {
					fDescription = "MethodExitsFinder_occurrence_exit_description";
					return fDescription;
				}
			}

		}
		fDescription = "MethodExitsFinder_occurrence_exit_description";
		return fDescription;
	}

	private final Include getIncludeExpression(ASTNode node) {
		boolean isInclude = (node != null && node.getType() == ASTNode.INCLUDE);
		if (isInclude) {
			return (Include) node;
		}
		ASTNode parent = ASTNodes.getParent(node, Include.class);
		return (parent != null && parent.getType() == ASTNode.INCLUDE) ? (Include) parent : null;
	}

	protected void findOccurrences() {
		fDescription = Messages.format(INCLUDE_POINT_OF, this.source.getElementName());
		getASTRoot().accept(this);
		int offset = includeNode.getStart();
		int length = includeNode.getLength();
		fResult.add(new OccurrenceLocation(offset, length, getOccurrenceType(null), fDescription));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		return IOccurrencesFinder.K_OCCURRENCE;
	}

	public String getElementName() {
		return binding.getName();
	}

	public String getID() {
		return ID;
	}

	public String getJobLabel() {
		return "RncludeFinder_job_label";
	}

	public int getSearchKind() {
		return IOccurrencesFinder.K_EXIT_POINT_OCCURRENCE;
	}

	public String getUnformattedPluralLabel() {
		return "IncludeFinder_label_plural";
	}

	public String getUnformattedSingularLabel() {
		return "IncludeFinder_label_singular";
	}

	@Override
	public boolean visit(ClassName className) {
		Expression className2 = className.getName();
		if (className2.getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) className2;
			String name = id.getName();
			for (IType type : types) {
				if (type.getElementName().equals(name))
					fResult.add(new OccurrenceLocation(className.getStart(), className.getLength(), getOccurrenceType(null), fDescription));
			}
		}
		return false;
	}

	@Override
	public boolean visit(FunctionInvocation functionInvocation) {
		Expression functionName2 = functionInvocation.getFunctionName().getName();
		if (functionName2.getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) functionName2;
			String name = id.getName();
			for (IMethod method : methods) {
				if (method.getElementName().equals(name))
					fResult.add(new OccurrenceLocation(functionInvocation.getStart(), functionInvocation.getLength(), getOccurrenceType(null), fDescription));
			}
		}
		return true;
	}
}
