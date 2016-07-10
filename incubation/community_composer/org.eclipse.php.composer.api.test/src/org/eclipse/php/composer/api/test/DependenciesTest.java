/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.test;

import org.junit.Test;

import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.Dependencies;

import junit.framework.TestCase;

public class DependenciesTest extends TestCase {

	@Test
	public Dependencies testAdd() {
		Dependencies deps = new Dependencies();
		
		VersionedPackage pkg = new VersionedPackage();
		pkg.setName("gossi/ldap");
		pkg.setVersion("1.2.3");
		
		deps.add(pkg);
		
		assertEquals(1, deps.size());
		
		return deps;
	}
	
	@Test
	public void testAddAll() {
		Dependencies deps1 = testAdd();
		Dependencies deps2 = new Dependencies();
		
		VersionedPackage pkg = new VersionedPackage();
		pkg.setName("phpunit/phpunit");
		pkg.setVersion("1.2.4");
		
		deps2.add(pkg);
		
		pkg = new VersionedPackage();
		pkg.setName("symfony/symfony");
		pkg.setVersion("2.2.2");
		
		deps2.add(pkg);
		
		assertEquals(2, deps2.size());
		
		deps1.addAll(deps2);
		
		assertEquals(3, deps1.size());
	}
}
