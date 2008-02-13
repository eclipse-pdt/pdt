package org.eclipse.php.internal.core.typeinference;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.dltk.ti.types.RecursionTypeCall;

public class PHPTypeInferenceUtils {

	public static IEvaluatedType combineMultiType(Collection<IEvaluatedType> evaluaedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(evaluaedTypes);
		types.remove(null);
		if (types.size() > 1 && types.contains(RecursionTypeCall.INSTANCE)) {
			types.remove(RecursionTypeCall.INSTANCE);
		}
		MultiTypeType multiTypeType = new MultiTypeType();
		for (IEvaluatedType type : types) {
			multiTypeType.addType(type);
		}
		return multiTypeType;
	}

	public static IEvaluatedType combineTypes(Collection<IEvaluatedType> evaluaedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(evaluaedTypes);
		types.remove(null);
		if (types.size() > 1 && types.contains(RecursionTypeCall.INSTANCE)) {
			types.remove(RecursionTypeCall.INSTANCE);
		}
		return combineUniqueTypes(types.toArray(new IEvaluatedType[types.size()]));
	}

	private static IEvaluatedType combineUniqueTypes(IEvaluatedType[] types) {
		if (types.length == 0) {
			return UnknownType.INSTANCE;
		}
		if (types.length == 1) {
			return types[0];
		}
		return new AmbiguousType(types);
	}

	public static IEvaluatedType combineTypes(IEvaluatedType[] evaluaedTypes) {
		return combineTypes(Arrays.asList(evaluaedTypes));
	}
}
