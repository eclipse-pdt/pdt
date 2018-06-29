/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.packages;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.php.composer.api.RepositoryPackage;
import org.eclipse.php.composer.api.json.JsonParser;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.api.repositories.PackageRepository;

class PackageHelper {

	static RepositoryPackage getPackage(InputStream resource) throws Exception {
		InputStreamReader reader = new InputStreamReader(resource);

		PackageRepository repo = new PackageRepository(reader);
		return repo.getPackage();
	}

	static SearchResult getSearchResult(InputStream resource) {
		InputStreamReader reader = new InputStreamReader(resource);

		JsonParser parser = new JsonParser();
		try {
			return new SearchResult(parser.parse(reader));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new SearchResult(new Object());
	}
}
