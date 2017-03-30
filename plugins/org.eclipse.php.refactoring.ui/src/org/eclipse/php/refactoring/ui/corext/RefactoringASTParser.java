/*******************************************************************************
 * Copyright (c) 2009, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.corext;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.editor.ASTProvider;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.php.ui.editor.SharedASTProvider;

public class RefactoringASTParser {

	private ASTParser fParser;

	public RefactoringASTParser(PHPVersion phpVersion, boolean useShortTags) {
		fParser = ASTParser.newParser(phpVersion, useShortTags);
	}

	public Program parse(ISourceModule typeRoot, boolean resolveBindings) throws Exception {
		return parse(typeRoot, resolveBindings, null);
	}

	public Program parse(ISourceModule typeRoot, boolean resolveBindings, IProgressMonitor pm) throws Exception {
		return parse(typeRoot, null, resolveBindings, pm);
	}

	public Program parse(ISourceModule typeRoot, WorkingCopyOwner owner, boolean resolveBindings, IProgressMonitor pm)
			throws Exception {
		return parse(typeRoot, owner, resolveBindings, false, false, pm);
	}

	public Program parse(ISourceModule typeRoot, WorkingCopyOwner owner, boolean resolveBindings,
			boolean statementsRecovery, boolean bindingsRecovery, IProgressMonitor pm) throws Exception {
		// fParser.setResolveBindings(resolveBindings);
		// fParser.setStatementsRecovery(statementsRecovery);
		// fParser.setBindingsRecovery(bindingsRecovery);
		fParser.setSource(typeRoot.getSourceAsCharArray());
		// if (owner != null)
		// fParser.setWorkingCopyOwner(owner);
		// fParser.setCompilerOptions(getCompilerOptions(typeRoot));
		Program result = (Program) fParser.createAST(pm);
		return result;
	}

	/**
	 * @param newCuSource
	 *            the source
	 * @param originalCu
	 *            the compilation unit to get the name and project from
	 * @param resolveBindings
	 *            whether bindings are to be resolved
	 * @param statementsRecovery
	 *            whether statements recovery should be enabled
	 * @param pm
	 *            an {@link IProgressMonitor}, or <code>null</code>
	 * @return the parsed Program
	 * @throws Exception
	 */
	public Program parse(String newCuSource, ISourceModule originalCu, boolean resolveBindings,
			boolean statementsRecovery, IProgressMonitor pm) throws Exception {
		// fParser.setResolveBindings(resolveBindings);
		// fParser.setStatementsRecovery(statementsRecovery);
		fParser.setSource(newCuSource.toCharArray());
		// fParser.setUnitName(originalCu.getElementName());
		// fParser.setProject(originalCu.getScriptProject());
		// fParser.setCompilerOptions(getCompilerOptions(originalCu));
		Program newCUNode = (Program) fParser.createAST(pm);
		return newCUNode;
	}

	/**
	 * @param newCfSource
	 *            the source
	 * @param originalCf
	 *            the class file to get the name and project from
	 * @param resolveBindings
	 *            whether bindings are to be resolved
	 * @param statementsRecovery
	 *            whether statements recovery should be enabled
	 * @param pm
	 *            an {@link IProgressMonitor}, or <code>null</code>
	 * @return the parsed Program
	 */
	// public Program parse(String newCfSource, IClassFile originalCf, boolean
	// resolveBindings, boolean statementsRecovery, IProgressMonitor pm) {
	// fParser.setResolveBindings(resolveBindings);
	// fParser.setStatementsRecovery(statementsRecovery);
	// fParser.setSource(newCfSource.toCharArray());
	// String cfName= originalCf.getElementName();
	// fParser.setUnitName(cfName.substring(0, cfName.length() - 6) +
	// JavaModelUtil.DEFAULT_CU_SUFFIX);
	// fParser.setProject(originalCf.getScriptProject());
	// fParser.setCompilerOptions(getCompilerOptions(originalCf));
	// Program newCUNode= (Program) fParser.createAST(pm);
	// return newCUNode;
	// }

	/**
	 * Tries to get the shared AST from the ASTProvider. If the shared AST is
	 * not available, parses the type root with a RefactoringASTParser that uses
	 * settings similar to the ASTProvider.
	 * 
	 * @param typeRoot
	 *            the type root
	 * @param resolveBindings
	 *            whether bindings are to be resolved if a new AST needs to be
	 *            created
	 * @param pm
	 *            an {@link IProgressMonitor}, or <code>null</code>
	 * @return the parsed Program
	 * @throws Exception
	 */
	public static Program parseWithASTProvider(ISourceModule typeRoot, boolean resolveBindings, IProgressMonitor pm)
			throws Exception {
		Program cuNode = null;
		try {
			cuNode = SharedASTProvider.getAST(typeRoot, SharedASTProvider.WAIT_ACTIVE_ONLY, pm);
		} catch (ModelException e) {
			RefactoringUIPlugin.log(e);
		} catch (IOException e) {
			RefactoringUIPlugin.log(e);
		}
		if (cuNode != null) {
			return cuNode;
		} else {
			return new RefactoringASTParser(PHPVersion.getLatestVersion(), true).parse(typeRoot, null, resolveBindings,
					ASTProvider.SHARED_AST_STATEMENT_RECOVERY, ASTProvider.SHARED_BINDING_RECOVERY, pm);
		}
	}

	public static ISourceModule getCompilationUnit(ASTNode node) {
		ASTNode root = node.getRoot();
		if (root instanceof Program) {
			return ((Program) root).getSourceModule();
		}
		return null;
	}

	/**
	 * Returns the compiler options used for creating the refactoring AST.
	 * <p>
	 * Turns all errors and warnings into ignore and disables task tags. The
	 * customizable set of compiler options only contains additional Eclipse
	 * options. The standard JDK compiler options can't be changed anyway.
	 * 
	 * @param element
	 *            an element (not the Java model)
	 * @return compiler options
	 */
	public static Map<String, String> getCompilerOptions(IModelElement element) {
		IScriptProject project = element.getScriptProject();
		Map<String, String> options = project.getOptions(true);
		for (Iterator<String> iter = options.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = (String) options.get(key);
			if (DLTKCore.ERROR.equals(value) || DLTKCore.WARNING.equals(value)) {
				// System.out.println("Ignoring - " + key);
				options.put(key, DLTKCore.IGNORE);
			}
		}
		// options.put(DLTKCore.COMPILER_TASK_TAGS, ""); //$NON-NLS-1$
		return options;
	}
}
