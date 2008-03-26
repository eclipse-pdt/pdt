package org.eclipse.php.internal.ui.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.ui.corext.dom.NodeFinder;

/**
 * A base class for all the occurrence finders.
 * 
 * @author shalom
 */
public abstract class AbstractOccurrencesFinder extends AbstractVisitor implements IOccurrencesFinder {

	protected static final String BASE_DESCRIPTION = "Occurrance of ''{0}''";
	protected static final String BASE_WRITE_DESCRIPTION = "Write occurrance of ''{0}''";
	protected static final String BRACKETS = "()";

	protected List<OccurrenceLocation> fResult;
	protected String fDescription;
	protected Program fASTRoot;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#initialize(org.eclipse.php.internal.core.ast.nodes.Program, int, int)
	 */
	public String initialize(Program root, int offset, int length) {
		return initialize(root, NodeFinder.perform(root, offset, length));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getOccurrences()
	 */
	public OccurrenceLocation[] getOccurrences() {
		fResult = new ArrayList<OccurrenceLocation>();
		findOccurrences();
		if (fResult.isEmpty())
			return null;

		return fResult.toArray(new OccurrenceLocation[fResult.size()]);
	}

	/**
	 * Find and add all the occurrences.
	 * Extending finders must implement this method to fill the results list.
	 * Note that this method should not be called directly. It is being called by the {@link #getOccurrences()}.
	 * 
	 * @see #getOccurrences()
	 */
	protected abstract void findOccurrences();

	/**
	 * Returns the type of this occurrence.
	 * 
	 * @param node The {@link ASTNode} to check.
	 * @return The occurrence type (one of {@link IOccurrencesFinder} type constants)
	 * @see IOccurrencesFinder
	 */
	protected abstract int getOccurrenceType(ASTNode node);

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getASTRoot()
	 */
	public Program getASTRoot() {
		return fASTRoot;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getJobLabel()
	 */
	public String getJobLabel() {
		return "OccurrencesFinder_job_label";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getSearchKind()
	 */
	public int getSearchKind() {
		return IOccurrencesFinder.K_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getUnformattedPluralLabel()
	 */
	public String getUnformattedPluralLabel() {
		return "OccurrencesFinder_label_plural";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getUnformattedSingularLabel()
	 */
	public String getUnformattedSingularLabel() {
		return "OccurrencesFinder_label_singular";
	}
}
