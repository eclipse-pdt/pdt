package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.internal.core.SourceMethod;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * TODO namespace,precedence and alias
 * 
 * @author zhaozw
 * 
 */
public class TraitUtils {

	public static UseTrait parse(IType type) {
		final UseTrait useTrait = new UseTrait();
		final ISourceModule sourceModule = type.getSourceModule();
		try {
			final ISourceRange sourceRange = type.getSourceRange();
			ModuleDeclaration moduleDeclaration = SourceParserUtil
					.getModuleDeclaration(sourceModule);
			moduleDeclaration.traverse(new PHPASTVisitor() {
				public boolean visit(TraitUseStatement s) throws Exception {
					if (s.sourceStart() > sourceRange.getOffset()
							&& s.sourceEnd() < sourceRange.getOffset()
									+ sourceRange.getLength()) {
						parse(sourceModule, sourceRange.getOffset(), s,
								useTrait);
					}
					return false;
				}
			});
		} catch (ModelException e1) {
		} catch (Exception e) {
		}
		return useTrait;
	}

	public static UseTrait parse(ISourceModule sourceModule, int offset,
			TraitUseStatement statement, UseTrait useTrait) {
		if (useTrait == null) {
			useTrait = new UseTrait();
		}
		for (TypeReference typeReference : statement.getTraitList()) {
			useTrait.getTraits().add(
					PHPModelUtils.getFullName(typeReference.getName(),
							sourceModule, offset));
		}

		for (TraitStatement traitStatement : statement.getTsList()) {
			if (traitStatement instanceof TraitAliasStatement) {
				TraitAliasStatement new_name = (TraitAliasStatement) traitStatement;
				Expression traitMethod = new_name.getAlias().getTraitMethod();
				if (traitMethod instanceof SimpleReference) {
					SimpleReference simpleReference = (SimpleReference) traitMethod;
					TraitAliasObject ta = new TraitAliasObject();
					// ta.traitName = PHPModelUtils.getFullName(
					// simpleReference.getName(), sourceModule, offset);
					ta.traitMethodName = simpleReference.getName();
					ta.newMethodVisibility = new_name.getAlias().getModifier();
					if (new_name.getAlias().getMethodName() != null) {
						ta.newMethodName = new_name.getAlias().getMethodName()
								.getName();
					}
					useTrait.getTraitAliases().add(ta);
					useTrait.getAliasMap().put(ta.traitMethodName, ta);
				} else if (traitMethod instanceof FullyQualifiedTraitMethodReference) {
					FullyQualifiedTraitMethodReference simpleReference = (FullyQualifiedTraitMethodReference) traitMethod;

					TraitAliasObject ta = new TraitAliasObject();
					ta.traitName = PHPModelUtils.getFullName(simpleReference
							.getClassName().getName(), sourceModule, offset);
					ta.traitMethodName = simpleReference.getFunctionName();
					ta.newMethodVisibility = new_name.getAlias().getModifier();
					if (new_name.getAlias().getMethodName() != null) {
						ta.newMethodName = new_name.getAlias().getMethodName()
								.getName();
					}
					useTrait.getTraitAliases().add(ta);
					useTrait.getAliasMap().put(ta.traitMethodName, ta);
				}

			} else if (traitStatement instanceof TraitPrecedenceStatement) {
				TraitPrecedenceStatement new_name = (TraitPrecedenceStatement) traitStatement;
				TraitPrecedenceObject tpo = new TraitPrecedenceObject();
				tpo.traitName = PHPModelUtils.getFullName(new_name
						.getPrecedence().getMethodReference().getClassName()
						.getName(), sourceModule, offset);
				tpo.traitMethodName = new_name.getPrecedence()
						.getMethodReference().getFunctionName();

				for (TypeReference typeReference : new_name.getPrecedence()
						.getTrList()) {
					tpo.insteadofTraitNameList.add(typeReference.getName());
				}
				useTrait.getPrecedenceMap().put(tpo.traitMethodName, tpo);

			}
		}
		return useTrait;
	}

	public static IField[] getTraitFields(IType type, Set<String> nameSet) {
		UseTrait useTrait = parse(type);
		List<IField> fieldList = new ArrayList<IField>();
		Set<String> traitNameSet = new HashSet<String>();
		for (String trait : useTrait.getTraits()) {
			IType[] traitTypes = PhpModelAccess.getDefault().findTraits(trait,
					MatchRule.EXACT, 0, 0, createSearchScope(type), null);
			for (IType traitType : traitTypes) {
				String traitName = PHPModelUtils.getFullName(traitType);
				if (!trait.equals(traitName)) {
					continue;
				}
				if (traitNameSet.contains(traitName)) {
					continue;
				} else {
					traitNameSet.add(traitName);
				}
				IField[] fields;
				try {
					fields = PHPModelUtils.getTypeField(traitType, "", false);
					// fields = traitType.getFields();
					for (IField field : fields) {
						field = getFieldWrapper(useTrait, field, type);
						if (field == null) {
							continue;
						}
						String elementName = field.getElementName();
						if (elementName.startsWith("$")) {
							elementName = elementName.substring(1);
						}
						if (!nameSet.contains(elementName)) {
							fieldList.add(field);
						}
					}
				} catch (ModelException e) {
				}
			}
		}
		return fieldList.toArray(new IField[fieldList.size()]);
	}

	private static IField getFieldWrapper(UseTrait useTrait, IField field,
			IType type) {
		// TraitAliasObject tao = useTrait.getAliasMap().get(
		// field.getElementName());
		// TraitPrecedenceObject tpo = useTrait.getPrecedenceMap().get(
		// field.getElementName());
		// if (tpo != null) {
		// String fullName = PHPModelUtils.getFullName(field
		// .getDeclaringType());
		// if (!fullName.equals(tpo.traitName) || tao == null
		// || tao.newMethodName == null) {
		// return null;
		// }
		// } else {
		//
		// }
		// if (tao != null) {
		// field = new FieldWrapper(field, tao.newMethodVisibility,
		// tao.newMethodName);
		// }
		// return field;
		String fieldName = field.getElementName();
		if (fieldName.startsWith("$")) {
			fieldName = fieldName.substring(1);
		}
		TraitAliasObject tao = useTrait.getAliasMap().get(fieldName);
		TraitPrecedenceObject tpo = useTrait.getPrecedenceMap().get(fieldName);
		String fullName = PHPModelUtils.getFullName(field.getDeclaringType());
		if (tao != null
				&& (tao.traitName == null || fullName.equals(tao.traitName))) {
			field = new FieldWrapper(field, tao.newMethodVisibility,
					tao.newMethodName, type);
			return field;
		}
		if (tpo != null) {
			if (!fullName.equals(tpo.traitName)) {
				return null;
			}
			// if (!fullName.equals(tpo.traitName)
			// && (tao == null || (fullName.equals(tao.traitName)
			// && tao.newMethodName != null && !fullName
			// .equals(tao.newMethodName)))) {
			// return null;
			// }
		} else {

		}
		return field;
	}

	public static IMethod[] getTraitMethods(IType type, Set<String> nameSet) {
		UseTrait useTrait = parse(type);
		List<IMethod> fieldList = new ArrayList<IMethod>();
		Set<String> traitNameSet = new HashSet<String>();
		for (String trait : useTrait.getTraits()) {
			IType[] traitTypes = PhpModelAccess.getDefault().findTraits(trait,
					MatchRule.EXACT, 0, 0, createSearchScope(type), null);
			for (IType traitType : traitTypes) {
				String traitName = PHPModelUtils.getFullName(traitType);
				if (!trait.equals(traitName)) {
					continue;
				}
				if (traitNameSet.contains(traitName)) {
					continue;
				} else {
					traitNameSet.add(traitName);
				}
				IMethod[] methods;
				try {
					methods = PHPModelUtils.getTypeMethod(traitType, "", false);
					// methods = traitType.getMethods();
					for (IMethod method : methods) {
						method = getMethodWrapper(useTrait, method, type);
						if (method == null) {
							continue;
						}
						String elementName = method.getElementName();
						if (!nameSet.contains(elementName)) {
							fieldList.add(method);
						}
					}
				} catch (ModelException e) {
				}
			}
		}
		return fieldList.toArray(new IMethod[fieldList.size()]);
	}

	private static IMethod getMethodWrapper(UseTrait useTrait, IMethod method,
			IType type) {
		TraitAliasObject tao = useTrait.getAliasMap().get(
				method.getElementName());
		TraitPrecedenceObject tpo = useTrait.getPrecedenceMap().get(
				method.getElementName());
		String fullName = PHPModelUtils.getFullName(method.getDeclaringType());
		if (tao != null
				&& (tao.traitName == null || fullName.equals(tao.traitName))) {
			method = new MethodWrapper(method, tao.newMethodVisibility,
					tao.newMethodName, type);
			return method;
		}
		if (tpo != null) {
			if (!fullName.equals(tpo.traitName)) {
				return null;
			}
			// if (!fullName.equals(tpo.traitName)
			// && (tao == null || (fullName.equals(tao.traitName)
			// && tao.newMethodName != null && !fullName
			// .equals(tao.newMethodName)))) {
			// return null;
			// }
		} else {

		}
		return method;
	}

	/**
	 * Creates search scope
	 */
	public static IDLTKSearchScope createSearchScope(IType type) {
		ISourceModule sourceModule = type.getSourceModule();
		IScriptProject scriptProject = sourceModule.getScriptProject();
		if (scriptProject != null) {
			return SearchEngine.createSearchScope(scriptProject);
		}
		IProjectFragment projectFragment = (IProjectFragment) sourceModule
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		if (projectFragment != null) {
			return SearchEngine.createSearchScope(projectFragment);
		}
		return SearchEngine.createSearchScope(sourceModule);
	}

	public static interface ITraitMember {
		public String getRealName();

		public IType getHostType();

		public boolean useAlias();
	}

	private static class FieldWrapper extends SourceField implements
			ITraitMember {

		private int flags = -1;
		private String name;
		private IMember member;
		private IType type;

		public FieldWrapper(IMember member, int flags, String name, IType type) {
			super((ModelElement) member.getParent(), member.getElementName());
			this.member = member;
			if (!name.startsWith("$")) {
				name = "$" + name;
			}
			this.name = name;
			this.type = type;
			try {
				this.flags = member.getFlags();
				if (flags != -1) {
					if (PHPFlags.isPrivate(this.flags)) {
						this.flags = this.flags ^ Modifiers.AccPrivate;
					} else if (PHPFlags.isProtected(this.flags)) {
						this.flags = this.flags ^ Modifiers.AccProtected;
					} else if (PHPFlags.isPublic(this.flags)) {
						this.flags = this.flags ^ Modifiers.AccPublic;
					}
					this.flags = this.flags | flags;
				}
			} catch (ModelException e) {
			}

		}

		public int getFlags() throws ModelException {
			if (flags != -1) {
				return flags;
			}
			return member.getFlags();
		}

		public ISourceRange getNameRange() throws ModelException {
			return member.getNameRange();
		}

		public ISourceRange getSourceRange() throws ModelException {
			return member.getSourceRange();
		}

		@Override
		public String getElementName() {
			if (name != null) {
				return name;
			}
			return member.getElementName();
		}

		public String getRealName() {
			return member.getElementName();
		}

		public IType getHostType() {
			return type;
		}

		public boolean useAlias() {
			return name != null && !name.equals(member.getElementName());
		}

	}

	private static class MethodWrapper extends SourceMethod implements
			ITraitMember {

		private int flags = -1;
		private String name;
		private IMethod member;
		private IType type;

		public MethodWrapper(IMethod member, int flags, String name, IType type) {
			super((ModelElement) member.getParent(), member.getElementName());
			this.member = member;
			this.name = name;
			this.type = type;
			try {
				this.flags = member.getFlags();
				if (flags != -1) {
					if (PHPFlags.isPrivate(this.flags)) {
						this.flags = this.flags ^ Modifiers.AccPrivate;
					} else if (PHPFlags.isProtected(this.flags)) {
						this.flags = this.flags ^ Modifiers.AccProtected;
					} else if (PHPFlags.isPublic(this.flags)) {
						this.flags = this.flags ^ Modifiers.AccPublic;
					}
					this.flags = this.flags | flags;
				}
			} catch (ModelException e) {
			}

		}

		public int getFlags() throws ModelException {
			if (flags != -1) {
				return flags;
			}
			return member.getFlags();
		}

		public ISourceRange getNameRange() throws ModelException {
			return member.getNameRange();
		}

		public ISourceRange getSourceRange() throws ModelException {
			return member.getSourceRange();
		}

		@Override
		public String getElementName() {
			if (name != null) {
				return name;
			}
			return member.getElementName();
		}

		public String getRealName() {
			return member.getElementName();
		}

		public IType getHostType() {
			return type;
		}

		public IParameter[] getParameters() throws ModelException {
			return member.getParameters();
		}

		@Override
		public String[] getParameterNames() throws ModelException {
			return member.getParameterNames();
		}

		public boolean isConstructor() throws ModelException {
			return false;
		}

		public boolean useAlias() {
			return name != null && !name.equals(member.getElementName());
		}
	}

}
