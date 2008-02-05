package org.eclipse.php.internal.core.search;

import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.ISearchFactory;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.core.search.indexing.SourceIndexerRequestor;
import org.eclipse.dltk.core.search.matching.MatchLocator;

public class PHPSearchFactory implements ISearchFactory {

	public MatchLocator createMatchLocator(SearchPattern pattern, SearchRequestor requestor, IDLTKSearchScope scope, SubProgressMonitor monitor) {
		return new MatchLocator(pattern, requestor, scope, monitor);
	}

	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new PHPMatchLocatorParser(locator);
	}

	public DLTKSearchParticipant createSearchParticipant() {
		return new DLTKSearchParticipant();
	}

	public SourceIndexerRequestor createSourceRequestor() {
		return new SourceIndexerRequestor();
	}

}
