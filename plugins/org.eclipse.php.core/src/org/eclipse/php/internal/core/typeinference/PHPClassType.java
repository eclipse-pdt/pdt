/*******************************************************************************
 * Copyright (c) 2009, 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.IClassType;
import org.eclipse.dltk.ti.types.ClassType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.DNFTypeReference;
import org.eclipse.php.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.typeinference.evaluators.PHPTraitType;
import org.eclipse.php.internal.core.typeinference.evaluators.TypeReferenceEvaluator;

/**
 * This evaluated type represents PHP class or interface
 * 
 * @author michael
 */
public class PHPClassType extends ClassType implements IClassType {

	private String namespace;
	private String typeName;
	private boolean global = false;

	/**
	 * Constructs evaluated type for PHP class or interface. The type name can
	 * contain namespace part (namespace name must be real, and not point to the
	 * alias or subnamespace under current namespace)
	 */
	public PHPClassType(String typeName) {
		if (typeName == null) {
			throw new IllegalArgumentException();
		}

		int i = typeName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
		// detect the namespace prefix:
		// global namespace case
		if (i == -1) {
			this.typeName = typeName;
		} else if (i == 0) {
			this.typeName = typeName.substring(1, typeName.length());
			global = true;
		} else if (i > 0) {
			// check is global namespace
			if (typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
				// make the type name fully qualified:
				typeName = new StringBuilder(NamespaceReference.NAMESPACE_DELIMITER).append(typeName).toString();
				i += 1;
			}
			this.namespace = typeName.substring(0, i);
			this.typeName = typeName;
		}
	}

	/**
	 * Constructs evaluated type for PHP class or interface that was declared
	 * under some namespace
	 */
	public PHPClassType(String namespace, String typeName) {
		if (namespace == null || typeName == null) {
			throw new IllegalArgumentException();
		}

		if (namespace.length() == 0 || namespace.equals(NamespaceReference.NAMESPACE_DELIMITER)) {
			global = true;
			this.namespace = ""; // $NON-NLS-1$
		} else if (namespace.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			this.namespace = namespace;
		} else {
			// make the namespace fully qualified
			this.namespace = new StringBuilder(NamespaceReference.NAMESPACE_DELIMITER).append(namespace).toString();
		}
		this.typeName = new StringBuilder(this.namespace).append(NamespaceReference.NAMESPACE_DELIMITER)
				.append(typeName).toString();
	}

	/**
	 * Returns fully qualified type name (including namespace)
	 * 
	 * @return type name
	 */
	@Override
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Returns namespace name part of this type or <code>null</code> if the type
	 * is not declared under some namespace
	 * 
	 * @return namespace name part or null
	 */
	public String getNamespace() {
		return namespace;
	}

	public boolean isGlobal() {
		return global;
	}

	@Override
	public boolean subtypeOf(IEvaluatedType type) {
		return false;
	}

	@Override
	public String getModelKey() {
		return typeName;
	}

	/**
	 * Creates evaluated type for the given class name. If class name contains
	 * namespace parts, the fully qualified name is resolved.
	 * 
	 * @param typeName
	 *            Type name
	 * @param sourceModule
	 *            Source module where the type was referenced
	 * @param offset
	 *            Offset in file here the type was referenced
	 * @return evaluated type
	 * @see TypeReferenceEvaluator#init()
	 */
	public static PHPClassType fromTypeName(String typeName, ISourceModule sourceModule, int offset) {
		String namespace = PHPModelUtils.extractNamespaceName(typeName, sourceModule, offset);
		String elementName = PHPModelUtils.extractElementName(typeName);
		if (namespace != null && typeName != null && sourceModule != null && elementName != null) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=515844
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=527153
			elementName = PHPModelUtils.getRealName(namespace, typeName, sourceModule, offset, elementName);
		}
		final ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		if (PHPModelUtils.isInUseTraitStatement(moduleDeclaration, offset)) {

			if (namespace != null) {
				return new PHPTraitType(namespace, elementName);
			}
			return new PHPTraitType(elementName);
		} else {

			if (namespace != null) {
				return new PHPClassType(namespace, elementName);
			}
			return new PHPClassType(elementName);
		}
	}

	/**
	 * @see TypeReferenceEvaluator#init()
	 */
	public static PHPClassType fromTraitName(String typeName, ISourceModule sourceModule, int offset) {
		String namespace = PHPModelUtils.extractNamespaceName(typeName, sourceModule, offset);
		String elementName = PHPModelUtils.extractElementName(typeName);
		if (namespace != null && typeName != null && sourceModule != null && elementName != null) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=515844
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=527153
			elementName = PHPModelUtils.getRealName(namespace, typeName, sourceModule, offset, elementName);
		}

		if (namespace != null) {
			return new PHPTraitType(namespace, elementName);
		}
		return new PHPTraitType(elementName);
	}

	/**
	 * Creates evaluated type from the given IType.
	 * 
	 * @param type
	 * @return evaluated type
	 */
	public static PHPClassType fromIType(IType type) {
		String elementName = type.getElementName();
		try {
			if (PHPFlags.isTrait(type.getFlags())) {
				IType namespace = type.getDeclaringType();
				if (namespace != null) {
					return new PHPTraitType(namespace.getElementName(), elementName);
				}
				return new PHPTraitType(elementName);
			}
		} catch (ModelException e) {
		}
		IType namespace = type.getDeclaringType();
		if (namespace != null) {
			return new PHPClassType(namespace.getElementName(), elementName);
		}
		return new PHPClassType(elementName);
	}

	/**
	 * Create evaluated type object from the given name reference.
	 * 
	 * @param name
	 * @return evaluated type
	 */
	public static IEvaluatedType fromSimpleReference(SimpleReference name) {
		if (name instanceof DNFTypeReference) {
			return fromDNFType((DNFTypeReference) name);
		}
		String typeName = name instanceof FullyQualifiedReference
				? ((FullyQualifiedReference) name).getFullyQualifiedName()
				: name.getName();
		IEvaluatedType simpleType = PHPSimpleTypes.fromString(typeName);
		if (simpleType != null) {
			return simpleType;
		}
		return new PHPClassType(typeName);
	}

	private static IEvaluatedType fromDNFType(DNFTypeReference name) {
		List<IEvaluatedType> list = new ArrayList<>();
		for (TypeReference ref : name.getReferences()) {
			list.add(fromSimpleReference(ref));

		}
		IEvaluatedType[] arr = list.toArray(new IEvaluatedType[0]);
		if (name.getType() == DNFTypeReference.T_INTERSECTION) {
			return new IntersectionType(arr);
		} else {

			return new AmbiguousType(arr);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	@Override
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
		PHPClassType other = (PHPClassType) obj;
		if (namespace == null) {
			if (other.namespace != null) {
				return false;
			}
		} else if (!namespace.equals(other.namespace)) {
			return false;
		}
		if (typeName == null) {
			if (other.typeName != null) {
				return false;
			}
		} else if (!typeName.equals(other.typeName)) {
			return false;
		}
		return true;
	}
}
