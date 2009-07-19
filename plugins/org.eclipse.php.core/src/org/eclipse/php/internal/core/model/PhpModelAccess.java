package org.eclipse.php.internal.core.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;

public class PhpModelAccess extends ModelAccess {

	private static final PhpModelAccess instance = new PhpModelAccess();

	public static PhpModelAccess getDefault() {
		return instance;
	}

	public IField[] findIncludes(String name, IDLTKSearchScope scope) {

		List<IField> result = new LinkedList<IField>();
		findElements(IModelElement.IMPORT_DECLARATION, name, MatchRule.EXACT,
				0, scope, result);
		return (IField[]) result.toArray(new IField[result.size()]);
	}
}
