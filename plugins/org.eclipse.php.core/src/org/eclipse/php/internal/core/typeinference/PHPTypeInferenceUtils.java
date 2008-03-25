package org.eclipse.php.internal.core.typeinference;

import java.util.*;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;

public class PHPTypeInferenceUtils {

	public static IEvaluatedType combineMultiType(Collection<IEvaluatedType> evaluatedTypes) {
		MultiTypeType multiTypeType = new MultiTypeType();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type == null) {
				type = PHPSimpleTypes.NULL;
			}
			multiTypeType.addType(type);
		}
		return multiTypeType;
	}

	private static Collection<IEvaluatedType> resolveAmbiguousTypes(Collection<IEvaluatedType> evaluatedTypes) {
		List<IEvaluatedType> resolved = new LinkedList<IEvaluatedType>();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type instanceof AmbiguousType) {
				AmbiguousType ambType = (AmbiguousType) type;
				resolved.addAll(resolveAmbiguousTypes(Arrays.asList(ambType.getPossibleTypes())));
			} else {
				resolved.add(type);
			}
		}
		return resolved;
	}

	public static IEvaluatedType combineTypes(Collection<IEvaluatedType> evaluatedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(resolveAmbiguousTypes(evaluatedTypes));
		if (types.contains(null)) {
			types.remove(null);
			types.add(PHPSimpleTypes.NULL);
		}
		if (types.size() == 0) {
			return UnknownType.INSTANCE;
		}
		if (types.size() == 1) {
			return types.iterator().next();
		}
		return new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
	}

	/**
	 * Returns all model elements for the given class type. If current file is not <code>null</code> this method tries to find
	 * only type declared in the very file.
	 * @param instanceType Evaluated type of the class
	 * @param currentModule Current file module
	 * @return
	 */
	public static IType[] getTypes(IEvaluatedType instanceType, ISourceModule currentModule) {
		Set<IType> types = new HashSet<IType>();

		if (instanceType instanceof PHPClassType) {
			PHPClassType classType = (PHPClassType) instanceType;
			IModelElement[] elements = PHPMixinModel.getInstance().getClass(classType.getTypeName());
			for (IModelElement e : elements) {
				types.add((IType) e);
			}
		} else if (instanceType instanceof AmbiguousType) {
			AmbiguousType ambiguousType = (AmbiguousType) instanceType;
			for (IEvaluatedType type : ambiguousType.getPossibleTypes()) {
				if (type instanceof PHPClassType) {
					PHPClassType classType = (PHPClassType) type;
					IModelElement[] elements = PHPMixinModel.getInstance().getClass(classType.getTypeName());
					for (IModelElement e : elements) {
						types.add((IType) e);
					}
				}
			}
		}

		if (currentModule != null) {
			IType typeFromSameFile = null;
			for (IType type : types) {
				if (type.getSourceModule().equals(currentModule)) {
					typeFromSameFile = type;
					break;
				}
			}
			// If type from the same file was found  - use it
			if (typeFromSameFile != null) {
				return new IType[] { typeFromSameFile };
			}
		}

		return types.toArray(new IType[types.size()]);
	}
}
