package org.eclipse.php.internal.core.search;

import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.search.AbstractSearchFactory;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.IMatchLocatorParser;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.dltk.core.search.matching.MatchLocator;

public class PHPSearchFactory extends AbstractSearchFactory {

	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new PHPMatchLocatorParser(locator);
	}

	public MatchLocator createMatchLocator(SearchPattern pattern, SearchRequestor requestor, IDLTKSearchScope scope, SubProgressMonitor monitor) {
		return new PHPMatchLocator(pattern, requestor, scope, monitor);
	}

}
