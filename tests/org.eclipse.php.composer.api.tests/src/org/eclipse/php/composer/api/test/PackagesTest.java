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

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.php.composer.api.ComposerConstants;
import org.eclipse.php.composer.api.MinimalPackage;
import org.eclipse.php.composer.api.RepositoryPackage;
import org.eclipse.php.composer.api.packages.*;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.TestCase;

public class PackagesTest extends TestCase {

	private final static int TIMEOUT = 60;

	private CountDownLatch counter;
	private Object asyncResult;
	private String asyncQuery;
	private int asyncCounter;
	private int asyncAborts;

	@Rule
	public final RetryRule retry = new RetryRule(2, 3);

	@Override
	public void setUp() {
		counter = new CountDownLatch(1);
		asyncResult = null;
		asyncQuery = "";
		asyncCounter = 0;
		asyncAborts = 0;
	}

	@Test
	public void testAsyncDownloader() {
		try {
			AsyncDownloader downloader = new AsyncDownloader(ComposerConstants.PHAR_URL);
			downloader.addDownloadListener(new DownloadListenerAdapater() {
				@Override
				public void dataReceived(InputStream content, String url) {
					asyncResult = content;
					counter.countDown();
				}

				@Override
				public void errorOccured(Exception e) {
					e.printStackTrace();
				}
			});

			downloader.download();

			counter.await(TIMEOUT, TimeUnit.SECONDS);

			assertNotNull(asyncResult);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testComposerDownload() {
		try {
			PharDownloader downloader = new PharDownloader();
			InputStream content = downloader.download();
			assertNotNull(content);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAsyncComposerDownload() {
		try {
			AsyncPharDownloader downloader = new AsyncPharDownloader();
			downloader.addDownloadListener(new DownloadListenerAdapater() {
				@Override
				public void dataReceived(InputStream content, String url) {
					asyncResult = content;
					counter.countDown();
				}

				@Override
				public void errorOccured(Exception e) {
					e.printStackTrace();
				}
			});
			downloader.download();

			counter.await(TIMEOUT, TimeUnit.SECONDS);

			assertNotNull(asyncResult);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPackageDownloader() {
		try {
			PackagistDownloader downloader = new PackagistDownloader();
			RepositoryPackage pkg = downloader.loadPackage("gossi/ldap");
			assertNotNull(pkg);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAsyncPackageDownloader() {
		try {
			AsyncPackagistDownloader downloader = new AsyncPackagistDownloader();
			downloader.addPackageListener(new PackageListenerInterface() {
				@Override
				public void packageLoaded(RepositoryPackage repositoryPackage) {
					asyncResult = repositoryPackage;
					counter.countDown();
				}

				@Override
				public void aborted(String url) {
				}

				@Override
				public void errorOccured(Exception e) {
					e.printStackTrace();
					fail();
				}
			});
			String pkg = "symfony/symfony";
			downloader.loadPackage(pkg);

			counter.await(TIMEOUT, TimeUnit.SECONDS);

			assertNotNull(asyncResult);
			assertEquals(pkg, ((RepositoryPackage) asyncResult).getName());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPackage() {

		try {
			RepositoryPackage resource = RepositoryPackage.fromPackagist("react/react");

			assertNotNull(resource);
			assertEquals("react/react", resource.getName());
			assertEquals("Nuclear Reactor written in PHP.", resource.getDescription());
			assertNotNull(resource.getVersions());
			assertTrue(resource.getVersions().size() > 1);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSearch() {

		try {
			assertSearchResult("html");
			assertSearchResult("react");
			assertSearchResult("foo bar");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	protected void assertSearchResult(String query) throws Exception {

		PackageSearch downloader = new PackagistSearch();
		downloader.setPageLimit(1);
		List<MinimalPackage> packages = downloader.search(query);

		assertNotNull(packages);
		assertTrue(packages.size() > 0);

		for (MinimalPackage phpPackage : packages) {
			assertNotNull(phpPackage);
			assertNotNull(phpPackage.getName());
			assertNotNull(phpPackage.getDescription());
		}
	}

	@Test
	public void testAsyncSearch() {
		try {
			AsyncPackageSearch downloader = new AsyncPackagistSearch();
			downloader.addPackageSearchListener(new PackageSearchListenerInterface() {
				@Override
				public void packagesFound(List<MinimalPackage> packages, String query, SearchResult result) {
					asyncResult = packages;
					asyncQuery = query;
					asyncCounter++;

					counter.countDown();
				}

				@Override
				public void aborted(String url) {
				}

				@Override
				public void errorOccured(Exception e) {
				}
			});
			String query = "gossi/ldap";
			downloader.search(query);

			counter.await(TIMEOUT, TimeUnit.SECONDS);

			assertNotNull(asyncResult);
			assertEquals(query, asyncQuery);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAsyncSearchWithPages() {
		try {
			final int pages = 3;
			AsyncPackageSearch downloader = new AsyncPackagistSearch();
			downloader.addPackageSearchListener(new PackageSearchListenerInterface() {
				@Override
				public void packagesFound(List<MinimalPackage> packages, String query, SearchResult result) {
					asyncResult = packages;
					asyncQuery = query;
					asyncCounter++;
					//
					// if (asyncCounter == pages) {
					// counter.countDown();
					// }
				}

				@Override
				public void aborted(String url) {
				}

				@Override
				public void errorOccured(Exception e) {
				}
			});
			String query = "symfony";
			downloader.setPageLimit(pages);
			downloader.search(query);

			counter.await(TIMEOUT, TimeUnit.SECONDS);

			assertNotNull(asyncResult);
			assertEquals(query, asyncQuery);
			assertEquals(pages, asyncCounter);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAsyncSearchAbortNDownload() {
		try {
			final int pages = 1;
			AsyncPackageSearch downloader = new AsyncPackagistSearch();
			downloader.addPackageSearchListener(new PackageSearchListenerInterface() {
				@Override
				public void packagesFound(List<MinimalPackage> packages, String query, SearchResult result) {
					asyncResult = packages;
					asyncQuery = query;
					asyncCounter++;

					if (asyncCounter == pages) {
						counter.countDown();
					}
				}

				@Override
				public void aborted(String url) {
					// System.out.println("testAsyncSearchAbortNDownload aborted
					// on: " + url);
				}

				@Override
				public void errorOccured(Exception e) {
					e.printStackTrace();
				}
			});
			String query = "symfony";
			downloader.setPageLimit(pages);
			downloader.search(query);
			downloader.abort();
			query = "zend";
			downloader.search(query);

			counter.await(TIMEOUT, TimeUnit.SECONDS);

			assertNotNull(asyncResult);
			assertEquals(query, asyncQuery);
			assertEquals(pages, asyncCounter);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAsyncAbort() {
		try {
			AsyncPackageSearch downloader = new AsyncPackagistSearch();
			downloader.addPackageSearchListener(new PackageSearchListenerInterface() {
				@Override
				public void packagesFound(List<MinimalPackage> packages, String query, SearchResult result) {
					asyncResult = packages;
					asyncQuery = query;
					asyncCounter++;

					if (asyncAborts == 1 && asyncCounter == 1) {
						counter.countDown();
					}
				}

				@Override
				public void aborted(String url) {
					asyncAborts++;

					if (asyncAborts == 1 && asyncCounter == 1) {
						counter.countDown();
					}
				}

				@Override
				public void errorOccured(Exception e) {
				}
			});
			downloader.setPageLimit(1);
			synchronized (downloader) {
				downloader.search("test");
				downloader.abort();
			}
			downloader.search("symfony");

			counter.await(TIMEOUT, TimeUnit.SECONDS);
			assertEquals("Aborts: ", 1, asyncAborts);
			assertEquals("Searches:", 1, asyncCounter);
			assertEquals("symfony", asyncQuery);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
