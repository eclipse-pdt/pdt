package org.eclipse.php.internal.core.ti;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.php.internal.core.ti.types.AmbiguousType;
import org.eclipse.php.internal.core.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.ti.types.RecursionType;
import org.eclipse.php.internal.core.ti.types.UnknownType;

public class TIUtils {

	public static IEvaluatedType combineTypes(Collection<IEvaluatedType> evaluatedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(evaluatedTypes);
		types.remove(null);
		if (types.size() > 1 && types.contains(RecursionType.INSTANCE)) {
			types.remove(RecursionType.INSTANCE);
		}
		return combineUniqueTypes((IEvaluatedType[]) types.toArray(new IEvaluatedType[types.size()]));
	}

	private static IEvaluatedType combineUniqueTypes(IEvaluatedType[] types) {
		if (types.length == 0)
			return UnknownType.INSTANCE;
		if (types.length == 1)
			return types[0];
		return new AmbiguousType(types);
	}

	public static IEvaluatedType combineTypes(IEvaluatedType[] evaluaedTypes) {
		return combineTypes(Arrays.asList(evaluaedTypes));
	}
}
