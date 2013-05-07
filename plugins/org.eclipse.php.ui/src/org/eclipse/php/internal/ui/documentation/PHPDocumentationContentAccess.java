/*******************************************************************************
 * Copyright (c) 2009, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.documentation;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.util.MethodOverrideTester;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.MagicMemberUtil;
import org.eclipse.php.internal.core.util.MagicMemberUtil.MagicField;
import org.eclipse.php.internal.core.util.MagicMemberUtil.MagicMember;
import org.eclipse.php.internal.core.util.MagicMemberUtil.MagicMethod;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.util.SuperTypeHierarchyCache;

/**
 * Helper to get the content of a Javadoc comment as HTML.
 * 
 * <p>
 * <strong>This is work in progress. Parts of this will later become API through
 * {@link JavadocContentAccess}</strong>
 * </p>
 * 
 * @since 3.4
 */
@SuppressWarnings({ "restriction", "unchecked", "rawtypes" })
public class PHPDocumentationContentAccess {

	private static final Pattern INLINE_LINK_PATTERN = Pattern
			.compile("\\{@link[\\s]+[^\\}]*\\}"); //$NON-NLS-1$

	private static final String BLOCK_TAG_START = "<dl>"; //$NON-NLS-1$
	private static final String BLOCK_TAG_END = "</dl>"; //$NON-NLS-1$

	private static final String BlOCK_TAG_ENTRY_START = "<dd>"; //$NON-NLS-1$
	private static final String BlOCK_TAG_ENTRY_END = "</dd>"; //$NON-NLS-1$

	private static final String PARAM_NAME_START = "<b>"; //$NON-NLS-1$
	private static final String PARAM_NAME_END = "</b> "; //$NON-NLS-1$
	private static final int PARAMETER_TYPE_TYPE = 1;
	private static final int PARAMETER_NAME_TYPE = 2;
	private static final int PARAMETER_DESCRIPTION_TYPE = 3;

	/**
	 * Implements the "Algorithm for Inheriting Method Comments" as specified
	 * for <a href=
	 * "http://java.sun.com/j2se/1.4.2/docs/tooldocs/solaris/javadoc.html#inheritingcomments"
	 * >1.4.2</a>, <a href=
	 * "http://java.sun.com/j2se/1.5.0/docs/tooldocs/windows/javadoc.html#inheritingcomments"
	 * >1.5</a>, and <a href=
	 * "http://java.sun.com/javase/6/docs/technotes/tools/windows/javadoc.html#inheritingcomments"
	 * >1.6</a>.
	 * 
	 * <p>
	 * Unfortunately, the implementation is broken in Javadoc implementations
	 * since 1.5, see <a
	 * href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6376959">Sun's
	 * bug</a>.
	 * </p>
	 * 
	 * <p>
	 * We adhere to the spec.
	 * </p>
	 */
	private static abstract class InheritDocVisitor {
		public static final Object STOP_BRANCH = new Object() {
			public String toString() {
				return "STOP_BRANCH";} //$NON-NLS-1$
		};
		public static final Object CONTINUE = new Object() {
			public String toString() {
				return "CONTINUE";} //$NON-NLS-1$
		};

		/**
		 * Visits a type and decides how the visitor should proceed.
		 * 
		 * @param currType
		 *            the current type
		 * @return <ul>
		 *         <li>{@link #STOP_BRANCH} to indicate that no Javadoc has been
		 *         found and visiting super types should stop here</li>
		 *         <li>{@link #CONTINUE} to indicate that no Javadoc has been
		 *         found and visiting super types should continue</li>
		 *         <li>an {@link Object} or <code>null</code>, to indicate that
		 *         visiting should be cancelled immediately. The returned value
		 *         is the result of
		 *         {@link #visitInheritDoc(IType, ITypeHierarchy)}</li>
		 *         </ul>
		 * @throws ModelException
		 *             unexpected problem
		 * @see #visitInheritDoc(IType, ITypeHierarchy)
		 */
		public abstract Object visit(IType currType) throws ModelException;

		/**
		 * Visits the super types of the given <code>currentType</code>.
		 * 
		 * @param currentType
		 *            the starting type
		 * @param typeHierarchy
		 *            a super type hierarchy that contains
		 *            <code>currentType</code>
		 * @return the result from a call to {@link #visit(IType)}, or
		 *         <code>null</code> if none of the calls returned a result
		 * @throws ModelException
		 *             unexpected problem
		 */
		public Object visitInheritDoc(IType currentType,
				ITypeHierarchy typeHierarchy) throws ModelException {
			ArrayList visited = new ArrayList();
			visited.add(currentType);
			// Object result = visitInheritDocInterfaces(visited, currentType,
			// typeHierarchy);
			// if (result != InheritDocVisitor.CONTINUE)
			// return result;
			Object result;
			IType[] superClasses = typeHierarchy.getSuperclass(currentType);
			for (IType superClass : superClasses) {
				while (superClass != null && !visited.contains(superClass)) {
					result = visit(superClass);
					if (result == InheritDocVisitor.STOP_BRANCH) {
						return null;
					} else if (result == InheritDocVisitor.CONTINUE) {
						visited.add(superClass);
						result = visitInheritDocInterfaces(visited, superClass,
								typeHierarchy);
						if (result != InheritDocVisitor.CONTINUE)
							return result;
						else
							superClasses = typeHierarchy
									.getSuperclass(superClass);
					} else {
						return result;
					}
				}
			}
			return null;
		}

		/**
		 * Visits the super interfaces of the given type in the given hierarchy,
		 * thereby skipping already visited types.
		 * 
		 * @param visited
		 *            set of visited types
		 * @param currentType
		 *            type whose super interfaces should be visited
		 * @param typeHierarchy
		 *            type hierarchy (must include <code>currentType</code>)
		 * @return the result, or {@link #CONTINUE} if no result has been found
		 * @throws ModelException
		 *             unexpected problem
		 */
		private Object visitInheritDocInterfaces(ArrayList visited,
				IType currentType, ITypeHierarchy typeHierarchy)
				throws ModelException {
			ArrayList toVisitChildren = new ArrayList();
			IType[] superInterfaces = typeHierarchy.getSuperclass(currentType);
			for (int i = 0; i < superInterfaces.length; i++) {
				IType superInterface = superInterfaces[i];
				if (visited.contains(superInterface))
					continue;
				visited.add(superInterface);
				Object result = visit(superInterface);
				if (result == InheritDocVisitor.STOP_BRANCH) {
					// skip
				} else if (result == InheritDocVisitor.CONTINUE) {
					toVisitChildren.add(superInterface);
				} else {
					return result;
				}
			}
			for (Iterator iter = toVisitChildren.iterator(); iter.hasNext();) {
				IType child = (IType) iter.next();
				Object result = visitInheritDocInterfaces(visited, child,
						typeHierarchy);
				if (result != InheritDocVisitor.CONTINUE)
					return result;
			}
			return InheritDocVisitor.CONTINUE;
		}
	}

	private static class JavadocLookup {
		private static final JavadocLookup NONE = new JavadocLookup(null) {
			public CharSequence getInheritedMainDescription(IMethod method) {
				return null;
			}

			public CharSequence getInheritedParamDescription(IMethod method,
					int i) {
				return null;
			}

			public CharSequence getInheritedReturnDescription(IMethod method) {
				return null;
			}

			public CharSequence getInheritedExceptionDescription(
					IMethod method, String name) {
				return null;
			}
		};

		private static interface DescriptionGetter {
			/**
			 * Returns a Javadoc tag description or <code>null</code>.
			 * 
			 * @param contentAccess
			 *            the content access
			 * @return the description, or <code>null</code> if none
			 * @throws ModelException
			 *             unexpected problem
			 */
			CharSequence getDescription(
					PHPDocumentationContentAccess contentAccess)
					throws ModelException;
		}

		private final IType fStartingType;
		private final HashMap fContentAccesses;

		private ITypeHierarchy fTypeHierarchy;
		private MethodOverrideTester fOverrideTester;

		private JavadocLookup(IType startingType) {
			fStartingType = startingType;
			fContentAccesses = new HashMap();
		}

		/**
		 * For the given method, returns the main description from an overridden
		 * method.
		 * 
		 * @param method
		 *            a method
		 * @return the description that replaces the
		 *         <code>{&#64;inheritDoc}</code> tag, or <code>null</code> if
		 *         none could be found
		 */
		public CharSequence getInheritedMainDescription(IMethod method) {
			return getInheritedDescription(method, new DescriptionGetter() {
				public CharSequence getDescription(
						PHPDocumentationContentAccess contentAccess) {
					return contentAccess.getMainDescription();
				}
			});
		}

		/**
		 * For the given method, returns the @param tag description for the
		 * given parameter from an overridden method.
		 * 
		 * @param method
		 *            a method
		 * @param paramIndex
		 *            the index of the parameter
		 * @return the description that replaces the
		 *         <code>{&#64;inheritDoc}</code> tag, or <code>null</code> if
		 *         none could be found
		 */
		public CharSequence getInheritedParamDescription(IMethod method,
				final int paramIndex) {
			return getInheritedDescription(method, new DescriptionGetter() {
				public CharSequence getDescription(
						PHPDocumentationContentAccess contentAccess)
						throws ModelException {
					return contentAccess
							.getInheritedParamDescription(paramIndex);
				}
			});
		}

		/**
		 * For the given method, returns the @param tag description for the
		 * given parameter from an overridden method.
		 * 
		 * @param method
		 *            a method
		 * @param paramIndex
		 *            the index of the parameter
		 * @return the description that replaces the
		 *         <code>{&#64;inheritDoc}</code> tag, or <code>null</code> if
		 *         none could be found
		 */
		public CharSequence getInheritedParamType(IMethod method,
				final int paramIndex) {
			return getInheritedDescription(method, new DescriptionGetter() {
				public CharSequence getDescription(
						PHPDocumentationContentAccess contentAccess)
						throws ModelException {
					return contentAccess.getInheritedParamType(paramIndex);
				}
			});
		}

		/**
		 * For the given method, returns the @return tag description from an
		 * overridden method.
		 * 
		 * @param method
		 *            a method
		 * @return the description that replaces the
		 *         <code>{&#64;inheritDoc}</code> tag, or <code>null</code> if
		 *         none could be found
		 */
		public CharSequence getInheritedReturnDescription(IMethod method) {
			return getInheritedDescription(method, new DescriptionGetter() {
				public CharSequence getDescription(
						PHPDocumentationContentAccess contentAccess) {
					return contentAccess.getReturnDescription();
				}
			});
		}

		/**
		 * For the given method, returns the @throws/@exception tag description
		 * for the given exception from an overridden method.
		 * 
		 * @param method
		 *            a method
		 * @param simpleName
		 *            the simple name of an exception
		 * @return the description that replaces the
		 *         <code>{&#64;inheritDoc}</code> tag, or <code>null</code> if
		 *         none could be found
		 */
		public CharSequence getInheritedExceptionDescription(IMethod method,
				final String simpleName) {
			return getInheritedDescription(method, new DescriptionGetter() {
				public CharSequence getDescription(
						PHPDocumentationContentAccess contentAccess) {
					return contentAccess.getExceptionDescription(simpleName);
				}
			});
		}

		private CharSequence getInheritedDescription(final IMethod method,
				final DescriptionGetter descriptionGetter) {
			try {
				return (CharSequence) new InheritDocVisitor() {
					public Object visit(IType currType) throws ModelException {
						IMethod overridden = getOverrideTester()
								.findOverriddenMethodInType(currType, method);
						if (overridden == null)
							return InheritDocVisitor.CONTINUE;

						PHPDocumentationContentAccess contentAccess = getJavadocContentAccess(overridden);
						if (contentAccess == null) {
							// if (overridden.getOpenable().getBuffer() == null)
							// {
							// return InheritDocVisitor.CONTINUE;
							// } else {
							// return InheritDocVisitor.CONTINUE;
							// }
							return InheritDocVisitor.CONTINUE;
						}

						CharSequence overriddenDescription = descriptionGetter
								.getDescription(contentAccess);
						if (overriddenDescription != null)
							return overriddenDescription;
						else
							return InheritDocVisitor.CONTINUE;
					}
				}.visitInheritDoc(method.getDeclaringType(), getTypeHierarchy());
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
			return null;
		}

		/**
		 * @param method
		 *            the method
		 * @return the Javadoc content access for the given method, or
		 *         <code>null</code> if no Javadoc could be found in source
		 * @throws ModelException
		 *             unexpected problem
		 */
		private PHPDocumentationContentAccess getJavadocContentAccess(
				IMethod method) throws ModelException {
			Object cached = fContentAccesses.get(method);
			if (cached != null)
				return (PHPDocumentationContentAccess) cached;
			if (fContentAccesses.containsKey(method))
				return null;

			// IBuffer buf = method.getOpenable().getBuffer();
			// if (buf == null) { // no source attachment found
			// fContentAccesses.put(method, null);
			// return null;
			// }

			PHPDocBlock javadoc = PHPModelUtils.getDocBlock(method);
			if (javadoc == null) {
				fContentAccesses.put(method, null);
				return null;
			}

			PHPDocumentationContentAccess contentAccess = new PHPDocumentationContentAccess(
					method, javadoc, this);
			fContentAccesses.put(method, contentAccess);
			return contentAccess;
		}

		private ITypeHierarchy getTypeHierarchy() throws ModelException {
			if (fTypeHierarchy == null)
				fTypeHierarchy = SuperTypeHierarchyCache
						.getTypeHierarchy(fStartingType);
			return fTypeHierarchy;
		}

		private MethodOverrideTester getOverrideTester() throws ModelException {
			if (fOverrideTester == null)
				fOverrideTester = SuperTypeHierarchyCache
						.getMethodOverrideTester(fStartingType);
			return fOverrideTester;
		}
	}

	private final IMember fMember;
	/**
	 * The method, or <code>null</code> if {@link #fMember} is not a method
	 * where {@inheritDoc} could work.
	 */
	private final IMethod fMethod;
	private final PHPDocBlock fJavadoc;
	private final JavadocLookup fJavadocLookup;

	private StringBuffer fBuf;
	private StringBuffer fMainDescription;
	private StringBuffer fReturnDescription;
	private StringBuffer[] fParamDescriptions;
	private StringBuffer[] fParamTypes;
	private HashMap<String, StringBuffer> fExceptionDescriptions;

	private PHPDocumentationContentAccess(IMethod method, PHPDocBlock javadoc,
			JavadocLookup lookup) {
		fMember = method;
		fMethod = method;
		fJavadoc = javadoc;
		fJavadocLookup = lookup;
	}

	private PHPDocumentationContentAccess(IMember member, PHPDocBlock javadoc) {
		fMember = member;
		fMethod = null;
		fJavadoc = javadoc;
		fJavadocLookup = JavadocLookup.NONE;
	}

	/**
	 * Gets an IMember's Javadoc comment content from the source or Javadoc
	 * attachment and renders the tags and links in HTML. Returns
	 * <code>null</code> if the member does not contain a Javadoc comment or if
	 * no source is available.
	 * 
	 * @param member
	 *            the member to get the Javadoc of
	 * @param useAttachedJavadoc
	 *            if <code>true</code> Javadoc will be extracted from attached
	 *            Javadoc if there's no source
	 * @return the Javadoc comment content in HTML or <code>null</code> if the
	 *         member does not have a Javadoc comment or if no source is
	 *         available
	 * @throws ModelException
	 *             is thrown when the element's Javadoc can not be accessed
	 */
	public static String getHTMLContent(IMember member) throws ModelException {
		return getHTMLContentFromSource(member);
	}

	private static StringBuffer createSuperMethodReferences(final IMethod method)
			throws ModelException {
		IType type = method.getDeclaringType();
		ITypeHierarchy hierarchy = SuperTypeHierarchyCache
				.getTypeHierarchy(type);
		final MethodOverrideTester tester = SuperTypeHierarchyCache
				.getMethodOverrideTester(type);

		final ArrayList<IMethod> superInterfaceMethods = new ArrayList<IMethod>();
		final IMethod[] superClassMethod = { null };
		new InheritDocVisitor() {
			public Object visit(IType currType) throws ModelException {
				IMethod overridden = tester.findOverriddenMethodInType(
						currType, method);
				if (overridden == null)
					return InheritDocVisitor.CONTINUE;

				if (PHPFlags.isInterface(currType.getFlags()))
					superInterfaceMethods.add(overridden);
				else
					superClassMethod[0] = overridden;

				return STOP_BRANCH;
			}
		}.visitInheritDoc(type, hierarchy);

		boolean hasSuperInterfaceMethods = superInterfaceMethods.size() != 0;
		if (!hasSuperInterfaceMethods && superClassMethod[0] == null)
			return null;

		StringBuffer buf = new StringBuffer();
		buf.append("<div>"); //$NON-NLS-1$
		if (hasSuperInterfaceMethods) {
			buf.append("<b>"); //$NON-NLS-1$
			buf.append(PHPDocumentationMessages.JavaDoc2HTMLTextReader_specified_by_section);
			buf.append("</b> "); //$NON-NLS-1$
			for (Iterator<IMethod> iter = superInterfaceMethods.iterator(); iter
					.hasNext();) {
				IMethod overridden = (IMethod) iter.next();
				buf.append(createMethodInTypeLinks(overridden));
				if (iter.hasNext())
					buf.append(ScriptElementLabels.COMMA_STRING);
			}
		}
		if (superClassMethod[0] != null) {
			if (hasSuperInterfaceMethods)
				buf.append(ScriptElementLabels.COMMA_STRING);
			buf.append("<b>"); //$NON-NLS-1$
			buf.append(PHPDocumentationMessages.JavaDoc2HTMLTextReader_overrides_section);
			buf.append("</b> "); //$NON-NLS-1$
			buf.append(createMethodInTypeLinks(superClassMethod[0]));
		}
		buf.append("</div>"); //$NON-NLS-1$
		return buf;
	}

	private static String createMethodInTypeLinks(IMethod overridden) {
		CharSequence methodLink = createSimpleMemberLink(overridden);
		CharSequence typeLink = createSimpleMemberLink(overridden
				.getDeclaringType());
		String methodInType = MessageFormat.format(
				PHPDocumentationMessages.JavaDoc2HTMLTextReader_method_in_type,
				new Object[] { methodLink, typeLink });
		return methodInType;
	}

	private static CharSequence createSimpleMemberLink(IMember member) {
		StringBuffer buf = new StringBuffer();
		buf.append("<a href='"); //$NON-NLS-1$
		try {
			String uri = PHPElementLinks.createURI(
					PHPElementLinks.PHPDOC_SCHEME, member);
			buf.append(uri);
		} catch (URISyntaxException e) {
			PHPUiPlugin.log(e);
		}
		buf.append("'>"); //$NON-NLS-1$
		ScriptElementLabels.getDefault().getElementLabel(member, 0, buf);
		buf.append("</a>"); //$NON-NLS-1$
		return buf;
	}

	private static String getHTMLContentFromSource(IMember member)
			throws ModelException {
		return javadoc2HTML(member);
	}

	private static PHPDocBlock getJavadocNode(IMember member) {
		if (member instanceof IType) {
			return PHPModelUtils.getDocBlock((IType) member);
		}
		if (member instanceof IMethod) {
			PHPDocBlock result = PHPModelUtils.getDocBlock((IMethod) member);
			if (result == null && member instanceof FakeConstructor) {
				FakeConstructor fc = (FakeConstructor) member;
				result = PHPModelUtils.getDocBlock((IType) fc.getParent());
			}
			return result;
		}
		if (member instanceof IField) {
			return PHPModelUtils.getDocBlock((IField) member);
		}
		return null;
	}

	private static String javadoc2HTML(IMember member) {
		PHPDocBlock javadoc = getJavadocNode(member);

		if (javadoc == null) {
			MagicMember magicMember = getMagicMember(member);
			if (magicMember != null) {
				return getHTMLForMagicMember(member, magicMember);
			}
			javadoc = new PHPDocBlock(0, 0, null, null, new PHPDocTag[0]);
		}
		if (canInheritJavadoc(member)) {
			IMethod method = (IMethod) member;
			IType declaringType = method.getDeclaringType();
			JavadocLookup lookup;
			if (declaringType == null) {
				lookup = JavadocLookup.NONE;
			} else {
				lookup = new JavadocLookup(method.getDeclaringType());
			}
			return new PHPDocumentationContentAccess(method, javadoc, lookup)
					.toHTML();
		}
		return new PHPDocumentationContentAccess(member, javadoc).toHTML();
	}

	private static String getHTMLForMagicMember(IMember member,
			MagicMember magicMember) {

		StringBuffer fBuf = new StringBuffer();

		if (appendBuiltinDoc(member, fBuf)) {
			return fBuf.toString();
		}
		if (magicMember instanceof MagicField) {
			MagicField magicField = (MagicField) magicMember;
			fBuf.append(magicField.desc);
			fBuf.append(BLOCK_TAG_START);
			fBuf.append("<dt>"); //$NON-NLS-1$
			fBuf.append("Type"); //$NON-NLS-1$
			fBuf.append("</dt>"); //$NON-NLS-1$
			fBuf.append(BlOCK_TAG_ENTRY_START);
			fBuf.append("&nbsp"); //$NON-NLS-1$
			fBuf.append(magicField.type);
			fBuf.append(BlOCK_TAG_ENTRY_END);
			fBuf.append(BLOCK_TAG_END);
		} else {
			MagicMethod magicMethod = (MagicMethod) magicMember;
			fBuf.append(magicMethod.desc);
			fBuf.append(BLOCK_TAG_START);

			if (magicMethod.parameterNames != null
					&& magicMethod.parameterNames.length > 0) {

				fBuf.append("<dt>"); //$NON-NLS-1$
				fBuf.append(PHPDocumentationMessages.JavaDoc2HTMLTextReader_parameters_section);
				fBuf.append("</dt>"); //$NON-NLS-1$
				for (int i = 0; i < magicMethod.parameterNames.length; i++) {
					fBuf.append(BlOCK_TAG_ENTRY_START);
					fBuf.append("&nbsp"); //$NON-NLS-1$
					String parameterName = magicMethod.parameterNames[i];
					String parameterType = magicMethod.parameterTypes[i];
					if (parameterType != null) {
						fBuf.append(PARAM_NAME_START);
						fBuf.append(parameterType);
						fBuf.append(PARAM_NAME_END);
					}
					fBuf.append(PARAM_NAME_START);
					fBuf.append(parameterName);
					fBuf.append(PARAM_NAME_END);
					fBuf.append(BlOCK_TAG_ENTRY_END);
				}
			}

			if (magicMethod.returnType != null) {

				fBuf.append("<dt>"); //$NON-NLS-1$
				fBuf.append(PHPDocumentationMessages.JavaDoc2HTMLTextReader_returns_section);
				fBuf.append("</dt>"); //$NON-NLS-1$
				fBuf.append(BlOCK_TAG_ENTRY_START);
				fBuf.append("&nbsp"); //$NON-NLS-1$
				fBuf.append(magicMethod.returnType);
				fBuf.append(BlOCK_TAG_ENTRY_END);
			}
			fBuf.append(BLOCK_TAG_END);
		}
		return fBuf.toString();
	}

	private static MagicMember getMagicMember(IMember member) {
		if (member instanceof IMethod || member instanceof IField) {
			IType type = null;
			if (member instanceof IMethod) {
				type = ((IMethod) member).getDeclaringType();
				PHPDocBlock doc = PHPModelUtils.getDocBlock(type);
				if (doc != null) {
					Pattern WHITESPACE_SEPERATOR = Pattern.compile("\\s+"); //$NON-NLS-1$
					final PHPDocTag[] tags = doc.getTags();
					for (PHPDocTag docTag : tags) {
						final int tagKind = docTag.getTagKind();
						if (tagKind == PHPDocTag.METHOD) {
							// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.method.pkg.html
							final String[] split = WHITESPACE_SEPERATOR
									.split(docTag.getValue().trim());
							if (split.length < 2) {
								continue;
							}

							if (MagicMemberUtil.removeParenthesis(split)
									.equals(member.getElementName())) {
								return MagicMemberUtil.getMagicMethod(docTag
										.getValue());
							} else if (MagicMemberUtil
									.removeParenthesis2(split).equals(
											member.getElementName())) {
								return MagicMemberUtil.getMagicMethod2(docTag
										.getValue());
							}
						}
					}
				}
			} else {
				type = ((IField) member).getDeclaringType();
				PHPDocBlock doc = PHPModelUtils.getDocBlock(type);
				if (doc != null) {
					Pattern WHITESPACE_SEPERATOR = Pattern.compile("\\s+"); //$NON-NLS-1$
					final PHPDocTag[] tags = doc.getTags();
					for (PHPDocTag docTag : tags) {
						final int tagKind = docTag.getTagKind();
						if (tagKind == PHPDocTag.PROPERTY
								|| tagKind == PHPDocTag.PROPERTY_READ
								|| tagKind == PHPDocTag.PROPERTY_WRITE) {
							// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.property.pkg.html
							final String[] split = WHITESPACE_SEPERATOR
									.split(docTag.getValue().trim());
							if (split.length < 2) {
								continue;
							}

							if (split[1].equals(member.getElementName())) {
								return MagicMemberUtil.getMagicField(docTag
										.getValue());
							}
						}
					}
				}
			}
		}

		return null;
	}

	private static boolean canInheritJavadoc(IMember member) {
		if (member instanceof IMethod && member.getScriptProject().exists()) {
			/*
			 * Exists test catches ExternalJavaProject, in which case no
			 * hierarchy can be built.
			 */
			try {
				return !((IMethod) member).isConstructor();
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
		return false;
	}

	private String toHTML() {
		fBuf = new StringBuffer();

		if (appendBuiltinDoc(fMember, fBuf)) {
			return fBuf.toString();
		}

		// After first loop, non-null entries in the following two lists are
		// missing and need to be inherited:
		List<String> parameterNames = initParameterNames();
		List<String> exceptionNames = new ArrayList<String>();

		PHPDocTag deprecatedTag = null;
		PHPDocTag returnTag = null;
		PHPDocTag namespaceTag = null;
		List<PHPDocTag> parameters = new ArrayList<PHPDocTag>();
		List<PHPDocTag> exceptions = new ArrayList<PHPDocTag>();
		List<PHPDocTag> versions = new ArrayList<PHPDocTag>();
		List<PHPDocTag> authors = new ArrayList<PHPDocTag>();
		List<PHPDocTag> sees = new ArrayList<PHPDocTag>();
		List<PHPDocTag> since = new ArrayList<PHPDocTag>();
		List<PHPDocTag> rest = new ArrayList<PHPDocTag>();
		String shortDescription = fJavadoc.getShortDescription();
		String longDescription = fJavadoc.getLongDescription();
		PHPDocTag[] tags = fJavadoc.getTags();
		for (PHPDocTag tag : tags) {
			if (PHPDocTag.PARAM == tag.getTagKind()) {
				parameters.add(tag);
				SimpleReference[] fragments = tag.getReferences();
				if (fragments.length == 0) {
					if (parameterNames.size() > parameters.indexOf(tag))
						parameterNames.set(parameters.indexOf(tag), null);
				}
				for (SimpleReference reference : fragments) {
					String name = reference.getName();
					if (reference instanceof TypeReference) {
						// parameterTypes.add(name);
					} else if (reference instanceof VariableReference) {
						int paramIndex = parameterNames.indexOf(name);
						if (paramIndex != -1) {
							parameterNames.set(paramIndex, null);
						}
					}
				}
			} else if (PHPDocTag.RETURN == tag.getTagKind()) {
				if (returnTag == null)
					returnTag = tag; // the Javadoc tool only shows the first
				// return tag

			} else if (PHPDocTag.NAMESPACE == tag.getTagKind()) {
				if (namespaceTag == null)
					namespaceTag = tag;

			} else if (PHPDocTag.THROWS == tag.getTagKind()) {
				exceptions.add(tag);
				SimpleReference[] fragments = tag.getReferences();
				if (fragments.length > 0) {
					Object first = fragments[0];
					if (first instanceof TypeReference) {
						exceptionNames.add(((TypeReference) first).getName());
					}
				}

			} else if (PHPDocTag.SINCE == tag.getTagKind()) {
				since.add(tag);
			} else if (PHPDocTag.VERSION == tag.getTagKind()) {
				versions.add(tag);
			} else if (PHPDocTag.AUTHOR == tag.getTagKind()) {
				authors.add(tag);
			} else if (PHPDocTag.SEE == tag.getTagKind()) {
				sees.add(tag);
			} else if (PHPDocTag.DEPRECATED == tag.getTagKind()) {
				if (deprecatedTag == null)
					deprecatedTag = tag; // the Javadoc tool only shows the
				// first deprecated tag
			} else {
				rest.add(tag);
			}
		}

		if (deprecatedTag != null)
			handleDeprecatedTag(deprecatedTag);
		if (shortDescription != null && shortDescription.length() > 0)
			fBuf.append(shortDescription);
		if (longDescription != null && longDescription.length() > 0) {
			fBuf.append("<p>"); //$NON-NLS-1$
			longDescription = longDescription.replaceAll("\r\n", "<p>"); //$NON-NLS-1$ //$NON-NLS-2$
			longDescription = longDescription.replaceAll("\n\r", "<p>"); //$NON-NLS-1$ //$NON-NLS-2$
			longDescription = longDescription.replaceAll("\n", "<p>"); //$NON-NLS-1$ //$NON-NLS-2$
			longDescription = longDescription.replaceAll("\r", "<p>"); //$NON-NLS-1$ //$NON-NLS-2$
			fBuf.append(longDescription);
		} else if (fMethod != null) {
			CharSequence inherited = fJavadocLookup
					.getInheritedMainDescription(fMethod);
			handleInherited(inherited);
		}
		handleInlineLinks();

		CharSequence[] parameterDescriptions = new CharSequence[parameterNames
				.size()];
		CharSequence[] parameterTypes = new CharSequence[parameterNames.size()];
		boolean hasInheritedParameters = inheritParameterDescriptions(
				parameterNames, parameterDescriptions, parameterTypes);
		boolean hasParameters = parameters.size() > 0 || hasInheritedParameters;

		CharSequence returnDescription = null;
		if (returnTag == null)
			returnDescription = fJavadocLookup
					.getInheritedReturnDescription(fMethod);
		boolean hasReturnTag = returnTag != null || returnDescription != null;

		CharSequence[] exceptionDescriptions = new CharSequence[exceptionNames
				.size()];
		boolean hasInheritedExceptions = inheritExceptionDescriptions(
				exceptionNames, exceptionDescriptions);
		boolean hasExceptions = exceptions.size() > 0 || hasInheritedExceptions;
		boolean hasNamespace = namespaceTag != null;

		if (hasParameters
				|| hasReturnTag
				|| hasExceptions
				|| hasNamespace
				|| versions.size() > 0
				|| authors.size() > 0
				|| since.size() > 0
				|| sees.size() > 0
				|| rest.size() > 0
				|| (fBuf.length() > 0 && (parameterDescriptions.length > 0 || exceptionDescriptions.length > 0))) {
			handleSuperMethodReferences();
			fBuf.append(BLOCK_TAG_START);
			handleParameterTags(parameters, parameterNames, parameterTypes,
					parameterDescriptions);
			handleReturnTag(returnTag, returnDescription);
			handleNamespaceTag(namespaceTag);
			handleExceptionTags(exceptions, exceptionNames,
					exceptionDescriptions);
			handleBlockTags(
					PHPDocumentationMessages.JavaDoc2HTMLTextReader_since_section,
					since);
			handleBlockTags(
					PHPDocumentationMessages.JavaDoc2HTMLTextReader_version_section,
					versions);
			handleBlockTags(
					PHPDocumentationMessages.JavaDoc2HTMLTextReader_author_section,
					authors);
			handleBlockTags(
					PHPDocumentationMessages.JavaDoc2HTMLTextReader_see_section,
					sees);
			handleBlockTags(rest);
			fBuf.append(BLOCK_TAG_END);

		} else if (fBuf.length() > 0) {
			handleSuperMethodReferences();
		}

		String result = fBuf.toString();
		fBuf = null;
		return result;
	}

	private void handleInlineLinks() {
		Matcher m = INLINE_LINK_PATTERN.matcher(fBuf);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String[] strs = m.group().split("[\\s]+", 3); //$NON-NLS-1$
			String url = removeLastRightCurlyBrace(strs[1]);
			if (url.toLowerCase().startsWith("http")) { //$NON-NLS-1$
				String description = ""; //$NON-NLS-1$
				if (strs.length == 3) {
					description = removeLastRightCurlyBrace(strs[2]);
				} else {
					description = url;
				}
				String link = "<a href=\"" + url + "\">" + description + "</a>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				// m.appendReplacement(sb, link);
				sb.append(fBuf.toString().replace(m.group(), link));
			} else {
				sb.append(fBuf.toString()
						.replace(
								m.group(),
								handleLink(Arrays.asList(new TypeReference(0,
										0, url)))));
				// try {
				// m.appendReplacement(
				// sb,
				// handleLink(
				// Arrays.asList(new TypeReference(0, 0, url)))
				// .toString());
				// } catch (Exception e) {
				// }
			}
		}
		// m.appendTail(sb);
		// fBuf = sb;
		fBuf.append(sb);
		sb = null;
	}

	private String removeLastRightCurlyBrace(String str) {
		if (str.endsWith("}")) { //$NON-NLS-1$
			return str.substring(0, str.length() - 1);
		}
		return str;
	}

	private void handleDeprecatedTag(PHPDocTag tag) {
		fBuf.append("<p><b>"); //$NON-NLS-1$
		fBuf.append(PHPDocumentationMessages.JavaDoc2HTMLTextReader_deprecated_section);
		fBuf.append("</b> <i>"); //$NON-NLS-1$
		handleContentElements(tag);
		fBuf.append("</i><p>"); //$NON-NLS-1$
	}

	private void handleSuperMethodReferences() {
		if (fMethod != null && fMethod.getDeclaringType() != null) {
			try {
				StringBuffer superMethodReferences = createSuperMethodReferences(fMethod);
				if (superMethodReferences != null)
					fBuf.append(superMethodReferences);
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
	}

	private List<String> initParameterNames() {
		if (fMethod != null) {
			try {
				return new ArrayList<String>(Arrays.asList(fMethod
						.getParameterNames()));
			} catch (ModelException e) {
				PHPUiPlugin.log(e);
			}
		}
		return Collections.EMPTY_LIST;
	}

	private boolean inheritParameterDescriptions(List<String> parameterNames,
			CharSequence[] parameterDescriptions, CharSequence[] parameterTypes) {
		boolean hasInheritedParameters = false;
		if (fMethod != null && fMethod.getDeclaringType() == null) {
			return hasInheritedParameters;
		}
		for (int i = 0; i < parameterNames.size(); i++) {
			String name = (String) parameterNames.get(i);
			if (name != null) {
				parameterDescriptions[i] = fJavadocLookup
						.getInheritedParamDescription(fMethod, i);
				parameterTypes[i] = fJavadocLookup.getInheritedParamType(
						fMethod, i);
				if (parameterDescriptions[i] != null)
					hasInheritedParameters = true;
			}
		}
		return hasInheritedParameters;
	}

	private boolean inheritExceptionDescriptions(List<String> exceptionNames,
			CharSequence[] exceptionDescriptions) {
		boolean hasInheritedExceptions = false;
		if (fMethod != null && fMethod.getDeclaringType() == null) {
			return hasInheritedExceptions;
		}
		for (int i = 0; i < exceptionNames.size(); i++) {
			String name = (String) exceptionNames.get(i);
			if (name != null) {
				exceptionDescriptions[i] = fJavadocLookup
						.getInheritedExceptionDescription(fMethod, name);
				if (exceptionDescriptions[i] != null)
					hasInheritedExceptions = true;
			}
		}
		return hasInheritedExceptions;
	}

	CharSequence getMainDescription() {
		if (fMainDescription == null) {
			fMainDescription = new StringBuffer();
			fBuf = fMainDescription;

			String shortDescription = fJavadoc.getShortDescription();
			if (shortDescription != null && shortDescription.length() > 0) {
				fMainDescription.append(shortDescription);
			}
			fBuf = null;
		}
		return fMainDescription.length() > 0 ? fMainDescription : null;
	}

	CharSequence getReturnDescription() {
		if (fReturnDescription == null) {
			fReturnDescription = new StringBuffer();
			fBuf = fReturnDescription;

			PHPDocTag[] tags = fJavadoc.getTags();
			for (PHPDocTag tag : tags) {
				if (PHPDocTag.RETURN == tag.getTagKind()) {
					handleContentElements(tag);
					break;
				}
			}

			fBuf = null;
		}
		return fReturnDescription.length() > 0 ? fReturnDescription : null;
	}

	CharSequence getInheritedParamDescription(int paramIndex)
			throws ModelException {
		if (fMethod != null) {
			String[] parameterNames = fMethod.getParameterNames();
			if (paramIndex >= parameterNames.length) {
				return null;
			}
			if (fParamDescriptions == null) {
				fParamDescriptions = new StringBuffer[parameterNames.length];
			} else {
				StringBuffer description = fParamDescriptions[paramIndex];
				if (description != null) {
					return description.length() > 0 ? description : null;
				}
			}

			StringBuffer description = new StringBuffer();
			fParamDescriptions[paramIndex] = description;
			fBuf = description;

			String paramName = parameterNames[paramIndex];
			String info = getParameterInfo(fJavadoc, paramName,
					PARAMETER_DESCRIPTION_TYPE);
			if (info != null)
				description.append(info);
			fBuf = null;
			return description.length() > 0 ? description : null;
		}
		return null;
	}

	CharSequence getInheritedParamType(int paramIndex) throws ModelException {
		if (fMethod != null) {
			String[] parameterNames = fMethod.getParameterNames();
			if (paramIndex >= parameterNames.length) {
				return null;
			}
			if (fParamTypes == null) {
				fParamTypes = new StringBuffer[parameterNames.length];
			} else {
				StringBuffer typeName = fParamTypes[paramIndex];
				if (typeName != null) {
					return typeName.length() > 0 ? typeName : null;
				}
			}

			StringBuffer typeName = new StringBuffer();
			fParamTypes[paramIndex] = typeName;
			fBuf = typeName;

			String paramName = parameterNames[paramIndex];
			String info = getParameterInfo(fJavadoc, paramName,
					PARAMETER_TYPE_TYPE);
			if (info != null)
				typeName.append(info);

			fBuf = null;
			return typeName.length() > 0 ? typeName : null;
		}
		return null;
	}

	CharSequence getExceptionDescription(String simpleName) {
		if (fMethod != null) {
			if (fExceptionDescriptions == null) {
				fExceptionDescriptions = new HashMap();
			} else {
				StringBuffer description = (StringBuffer) fExceptionDescriptions
						.get(simpleName);
				if (description != null) {
					return description.length() > 0 ? description : null;
				}
			}

			StringBuffer description = new StringBuffer();
			fExceptionDescriptions.put(simpleName, description);
			fBuf = description;

			List tags = Arrays.asList(fJavadoc.getTags());
			for (Iterator iter = tags.iterator(); iter.hasNext();) {
				PHPDocTag tag = (PHPDocTag) iter.next();
				if (PHPDocTag.THROWS == tag.getTagKind()) {
					List fragments = Arrays.asList(tag.getReferences());
					if (fragments.size() > 0) {
						Object first = fragments.get(0);
						if (first instanceof Identifier) {
							String name = ((Identifier) first).getName();
							if (name.equals(simpleName)) {
								if (fragments.size() > 1)
									handleContentElements(tag);
								break;
							}
						}
					}
				}
			}

			fBuf = null;
			return description.length() > 0 ? description : null;
		}
		return null;
	}

	private void handleContentElements(PHPDocTag tag) {
		fBuf.append(tag.getValue());
	}

	private boolean handleInherited(CharSequence inherited) {
		if (inherited == null)
			return false;

		fBuf.append(inherited);
		return true;
	}

	private void handleBlockTags(String title, List tags) {
		if (tags.isEmpty())
			return;

		handleBlockTagTitle(title);

		for (Iterator iter = tags.iterator(); iter.hasNext();) {
			PHPDocTag tag = (PHPDocTag) iter.next();
			fBuf.append(BlOCK_TAG_ENTRY_START);
			if (PHPDocTag.SEE == tag.getTagKind()) {
				handleSeeTag(tag);
			} else {
				handleContentElements(tag);
			}
			fBuf.append(BlOCK_TAG_ENTRY_END);
		}
	}

	private void handleReturnTag(PHPDocTag tag, CharSequence returnDescription) {
		if (tag == null && returnDescription == null)
			return;

		handleBlockTagTitle(PHPDocumentationMessages.JavaDoc2HTMLTextReader_returns_section);
		fBuf.append(BlOCK_TAG_ENTRY_START);
		doWorkAround();

		if (tag != null)
			handleContentElements(tag);
		else
			fBuf.append(returnDescription);
		fBuf.append(BlOCK_TAG_ENTRY_END);
	}

	private void handleNamespaceTag(PHPDocTag tag) {
		if (tag == null)
			return;

		handleBlockTagTitle(PHPDocumentationMessages.JavaDoc2HTMLTextReader_namespace_section);
		fBuf.append(BlOCK_TAG_ENTRY_START);
		doWorkAround();

		if (tag != null)
			handleContentElements(tag);
		fBuf.append(BlOCK_TAG_ENTRY_END);
	}

	private void handleBlockTags(List tags) {
		for (Iterator iter = tags.iterator(); iter.hasNext();) {
			PHPDocTag tag = (PHPDocTag) iter.next();
			if (tag.getTagKind() == PHPDocTag.VAR) {
				handleBlockTagTitle("Type"); //$NON-NLS-1$
			} else {
				handleBlockTagTitle(PHPDocTag.getTagKind(tag.getTagKind()));
			}
			fBuf.append(BlOCK_TAG_ENTRY_START);
			doWorkAround();
			if (tag.getTagKind() == PHPDocTag.LINK) {
				handleLinkTag(tag);
			} else {
				handleContentElements(tag);
			}
			fBuf.append(BlOCK_TAG_ENTRY_END);
		}
	}

	private void handleBlockTagTitle(String title) {
		fBuf.append("<dt>"); //$NON-NLS-1$
		fBuf.append(title);
		fBuf.append("</dt>"); //$NON-NLS-1$
	}

	private void handleSeeTag(PHPDocTag tag) {
		fBuf.append(handleLink(Arrays.asList(tag.getReferences())));
	}

	private void handleExceptionTags(List tags, List exceptionNames,
			CharSequence[] exceptionDescriptions) {
		if (tags.size() == 0 && containsOnlyNull(exceptionNames))
			return;

		handleBlockTagTitle(PHPDocumentationMessages.JavaDoc2HTMLTextReader_throws_section);

		for (Iterator iter = tags.iterator(); iter.hasNext();) {
			PHPDocTag tag = (PHPDocTag) iter.next();
			fBuf.append(BlOCK_TAG_ENTRY_START);
			doWorkAround();
			handleThrowsTag(tag);
			fBuf.append(BlOCK_TAG_ENTRY_END);
		}
		for (int i = 0; i < exceptionDescriptions.length; i++) {
			CharSequence description = exceptionDescriptions[i];
			String name = (String) exceptionNames.get(i);
			if (name != null) {
				fBuf.append(BlOCK_TAG_ENTRY_START);
				if (description != null) {
					fBuf.append(ScriptElementLabels.CONCAT_STRING);
					fBuf.append(description);
				}
				fBuf.append(BlOCK_TAG_ENTRY_END);
			}
		}
	}

	private void handleThrowsTag(PHPDocTag tag) {
		List<SimpleReference> fragments = Arrays.asList(tag.getReferences());
		int size = fragments.size();
		if (size > 0) {
			String exceptionName = ""; //$NON-NLS-1$
			if (fragments.get(0) instanceof TypeReference) {
				exceptionName = fragments.get(0).getName().trim();
				fBuf.append(exceptionName);
			}
			String value = tag.getValue().trim();
			String description = value.substring(exceptionName.length());
			if (description.length() > 0) {
				fBuf.append(ScriptElementLabels.CONCAT_STRING);
				fBuf.append(description.trim());
			}
		}
	}

	private void handleParameterTags(List tags, List parameterNames,
			CharSequence[] parameterTypes, CharSequence[] parameterDescriptions) {
		if (tags.size() == 0 && containsOnlyNull(parameterNames))
			return;

		handleBlockTagTitle(PHPDocumentationMessages.JavaDoc2HTMLTextReader_parameters_section);

		for (Iterator iter = tags.iterator(); iter.hasNext();) {
			PHPDocTag tag = (PHPDocTag) iter.next();
			fBuf.append(BlOCK_TAG_ENTRY_START);
			doWorkAround();
			handleParamTag(tag);
			fBuf.append(BlOCK_TAG_ENTRY_END);
		}
		for (int i = 0; i < parameterDescriptions.length; i++) {
			CharSequence description = parameterDescriptions[i];
			String name = (String) parameterNames.get(i);
			if (name != null) {
				fBuf.append(BlOCK_TAG_ENTRY_START);
				doWorkAround();
				if (parameterTypes[i] != null) {
					fBuf.append(PARAM_NAME_START);
					fBuf.append(parameterTypes[i]);
					fBuf.append(PARAM_NAME_END);
				}
				fBuf.append(PARAM_NAME_END);
				fBuf.append(PARAM_NAME_START);
				fBuf.append(name);
				fBuf.append(PARAM_NAME_END);
				if (description != null)
					fBuf.append(description);
				fBuf.append(BlOCK_TAG_ENTRY_END);
			}
		}
	}

	private void handleParamTag(PHPDocTag tag) {
		if (tag.getReferences().length == 0) {
			fBuf.append(tag.getValue());
			return;
		}
		String parameterName = getParameterInfo(tag, PARAMETER_NAME_TYPE);
		String parameterType = getParameterInfo(tag, PARAMETER_TYPE_TYPE);
		String description = getParameterInfo(tag, PARAMETER_DESCRIPTION_TYPE);
		fBuf.append(BlOCK_TAG_ENTRY_START);
		if (parameterType != null) {
			fBuf.append(PARAM_NAME_START);
			fBuf.append(parameterType);
			fBuf.append(PARAM_NAME_END);
		}
		fBuf.append(PARAM_NAME_START);
		fBuf.append(parameterName);
		fBuf.append(PARAM_NAME_END);
		if (description != null)
			fBuf.append(description);
		fBuf.append(BlOCK_TAG_ENTRY_END);
	}

	private void handleLinkTag(PHPDocTag tag) {
		fBuf.append("<a href="); //$NON-NLS-1$
		fBuf.append(tag.getValue());
		fBuf.append(">").append(tag.getValue()).append("</a>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private StringBuffer handleLink(List fragments) {
		StringBuffer sb = new StringBuffer();
		int fs = fragments.size();
		if (fs > 0) {
			Object first = fragments.get(0);
			String refTypeName = null;
			String refMemberName = null;
			String[] refMethodParamTypes = null;
			String[] refMethodParamNames = null;
			if (first instanceof TypeReference) {
				TypeReference type = (TypeReference) first;
				String name = type.getName();

				int typeNameEnd = name.indexOf("::"); //$NON-NLS-1$
				if (typeNameEnd == -1) {
					refTypeName = name;
				} else {
					refTypeName = name.substring(0, typeNameEnd);

					int argsListStart = name.indexOf('(', typeNameEnd);
					if (argsListStart == -1) {
						refMemberName = name.substring(typeNameEnd
								+ "::".length()); //$NON-NLS-1$
					} else {
						refMemberName = name.substring(
								typeNameEnd + "::".length(), argsListStart); //$NON-NLS-1$

						int argsListEnd = name.indexOf(argsListStart, ')');
						if (argsListEnd == -1) {
							refMethodParamTypes = new String[0];
						} else {
							String argsList = name.substring(argsListStart
									+ ")".length(), argsListEnd); //$NON-NLS-1$
							String[] args = argsList.split(","); //$NON-NLS-1$
							for (int i = 0; i < args.length; i++) {
								args[i] = args[i].trim();
							}
							refMethodParamTypes = args;
						}
					}
				}

			} // else if (first instanceof MemberRef) {
				// MemberRef memberRef = (MemberRef) first;
				// Name qualifier = memberRef.getQualifier();
				// refTypeName = qualifier == null ? "" :
				// qualifier.getFullyQualifiedName();
				// refMemberName = memberRef.getName().getIdentifier();
				// } else if (first instanceof MethodRef) {
				// MethodRef methodRef = (MethodRef) first;
				// Name qualifier = methodRef.getQualifier();
				// refTypeName = qualifier == null ? "" :
				// qualifier.getFullyQualifiedName();
				// refMemberName = methodRef.getName().getIdentifier();
				// List params = methodRef.parameters();
				// int ps = params.size();
				// refMethodParamTypes = new String[ps];
				// refMethodParamNames = new String[ps];
				// for (int i = 0; i < ps; i++) {
				// MethodRefParameter param = (MethodRefParameter) params
				// .get(i);
				// refMethodParamTypes[i] = ASTNodes.asString(param.getType());
				// SimpleName paramName = param.getName();
				// if (paramName != null)
				// refMethodParamNames[i] = paramName.getIdentifier();
				// }
				// }

			if (refTypeName != null) {
				sb.append("<a href='"); //$NON-NLS-1$
				try {
					String scheme = PHPElementLinks.PHPDOC_SCHEME;
					String uri = PHPElementLinks.createURI(scheme, fMember,
							refTypeName, refMemberName, refMethodParamTypes);
					sb.append(uri);
				} catch (URISyntaxException e) {
					PHPUiPlugin.log(e);
				}
				sb.append("'>"); //$NON-NLS-1$
				if (fs > 1) {
					// if (fs == 2 && fragments.get(1) instanceof TextElement) {
					// String text= removeLeadingWhitespace(((TextElement)
					// fragments.get(1)).getText());
					// if (text.length() != 0)
					// handleText(text);
					// else
					// //throws
					// }
					// handleContentElements(fragments.subList(1, fs));
				} else {
					sb.append(refTypeName);
					if (refMemberName != null) {
						if (refTypeName.length() > 0) {
							sb.append("::"); //$NON-NLS-1$
						}
						sb.append(refMemberName);
						if (refMethodParamTypes != null) {
							sb.append('(');
							for (int i = 0; i < refMethodParamTypes.length; i++) {
								String pType = refMethodParamTypes[i];
								sb.append(pType);
								String pName = refMethodParamNames[i];
								if (pName != null) {
									sb.append(' ').append(pName);
								}
								if (i < refMethodParamTypes.length - 1) {
									sb.append(", "); //$NON-NLS-1$
								}
							}
							sb.append(')');
						}
					}
				}
				sb.append("</a>"); //$NON-NLS-1$
			} else {
				// handleContentElements(fragments);
			}
		}
		return sb;
	}

	private boolean containsOnlyNull(List parameterNames) {
		for (Iterator iter = parameterNames.iterator(); iter.hasNext();) {
			if (iter.next() != null)
				return false;
		}
		return true;
	}

	private String getParameterInfo(PHPDocBlock phpDoc, String paramName,
			int infoType) {
		for (PHPDocTag tag : phpDoc.getTags(PHPDocTag.PARAM)) {
			String name = getParameterInfo(tag, PARAMETER_NAME_TYPE);
			if (name != null && name.equals(paramName)) {
				return getParameterInfo(tag, infoType);
			}
			continue;
		}
		return null;
	}

	private String getParameterInfo(PHPDocTag tag, int infoType) {
		if (tag.getTagKind() != PHPDocTag.PARAM) {
			return null;
		}
		SimpleReference typeRef = null;
		SimpleReference variableRef = null;
		String value = tag.getValue();
		if (tag.getReferences().length != 2) {
			return null;
		}
		for (SimpleReference reference : tag.getReferences()) {
			if (reference instanceof TypeReference) {
				typeRef = reference;
			} else if (reference instanceof VariableReference) {
				variableRef = reference;
			}
		}
		if (infoType == PARAMETER_DESCRIPTION_TYPE) {
			int typeRefIndex = value.indexOf(typeRef.getName());
			int variableRefIndex = value.indexOf(variableRef.getName());
			int lastRefIndex = typeRefIndex > variableRefIndex ? typeRefIndex
					+ typeRef.getName().length() : variableRefIndex
					+ variableRef.getName().length();
			return value.substring(lastRefIndex).trim();
		} else if (infoType == PARAMETER_TYPE_TYPE) {
			return typeRef.getName();
		} else if (infoType == PARAMETER_NAME_TYPE) {
			return variableRef.getName();
		}
		return null;
	}

	private static boolean appendBuiltinDoc(IMember element, StringBuffer buf) {
		String builtinDoc = BuiltinDoc.getString(element.getElementName());
		if (builtinDoc.length() > 0) {
			buf.append(builtinDoc);
			return true;
		}
		return false;
	}

	// Work aorund for Bug 320709
	// PHPDoc tooltips are not sized according to their contents
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=320709
	private void doWorkAround() {
		fBuf.append("&nbsp"); //$NON-NLS-1$
	}

}
