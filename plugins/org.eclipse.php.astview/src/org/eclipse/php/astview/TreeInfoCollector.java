/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.astview;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;

/**
 *
 */
public class TreeInfoCollector {
	

	class NodeCounter extends ApplyAll {

		public int numberOfNodes;

		@Override
		protected boolean apply(ASTNode node) {
			this.numberOfNodes++;
			return true;
		}
		
	}
		
	private final Program fRoot;

	public TreeInfoCollector(Program root) {
		fRoot= root;
	}

	public int getSize() {
		return 4;
	}
	
	public int getNumberOfNodes() {
		NodeCounter counter= new NodeCounter();
		fRoot.accept(counter);
		return counter.numberOfNodes;
	}
	

}
