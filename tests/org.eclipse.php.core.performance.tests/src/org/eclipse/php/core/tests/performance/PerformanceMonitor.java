/*******************************************************************************
 * Copyright (c) 2009,2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Michal Niewrzal
 *     Michele Locati
 *******************************************************************************/
package org.eclipse.php.core.tests.performance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocValuesType;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortedNumericSortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Assert;
import org.osgi.framework.Bundle;

/**
 * This monitor is created per testing plug-in for storing various performance
 * information on tests execution. This class isn't thread safe, as only one
 * test should be performed at a time in a single testing plug-in.
 */
public class PerformanceMonitor {

	private static final int AVG_SAMPLES = 5;
	private static final int HISTORY_SIZE = 50;

	private static final FieldType STORED_SORTED_LONG_FIELD_TYPE = new FieldType();
	static {
		STORED_SORTED_LONG_FIELD_TYPE.setDocValuesType(DocValuesType.SORTED_NUMERIC);
		STORED_SORTED_LONG_FIELD_TYPE.setStored(true);
		STORED_SORTED_LONG_FIELD_TYPE.freeze();
	}

	private static final FieldType STORED_LONG_FIELD_TYPE = new FieldType();
	static {
		STORED_LONG_FIELD_TYPE.setDocValuesType(DocValuesType.NUMERIC);
		STORED_LONG_FIELD_TYPE.setStored(true);
		STORED_LONG_FIELD_TYPE.freeze();
	}

	private Analyzer analyzer = null;
	private IndexWriter executionsWriter = null;
	private IndexWriter resultsWriter = null;
	private IndexReader executionsReader = null;
	private IndexReader resultsReader = null;
	private IndexSearcher executionsSearcher = null;
	private IndexSearcher resultsSearcher = null;
	private long executionId;

	public PerformanceMonitor(Bundle bundle) throws Exception {
		String baseDir = System.getProperty("user.home") + File.separator + ".perf_results_lucene" + File.separator
				+ bundle.getSymbolicName() + bundle.getVersion();
		File executionsDir = new File(baseDir + File.separator + "executions");
		File resultsDir = new File(baseDir + File.separator + "results");
		if (!executionsDir.exists()) {
			executionsDir.mkdirs();
		}
		if (!resultsDir.exists()) {
			resultsDir.mkdirs();
		}
		this.analyzer = new SimpleAnalyzer();
		this.executionsWriter = new IndexWriter(FSDirectory.open(executionsDir.toPath()),
				new IndexWriterConfig(this.analyzer));
		this.resultsWriter = new IndexWriter(FSDirectory.open(resultsDir.toPath()),
				new IndexWriterConfig(this.analyzer));
		this.executionsReader = DirectoryReader.open(this.executionsWriter);
		this.resultsReader = DirectoryReader.open(this.resultsWriter);
		this.executionsSearcher = new IndexSearcher(this.executionsReader);
		this.resultsSearcher = new IndexSearcher(this.resultsReader);
		this.executionId = System.currentTimeMillis();
		for (;;) {
			if (this.executionsSearcher.search(LongPoint.newExactQuery("executionId", this.executionId),
					1).scoreDocs.length == 0) {
				break;
			}
			this.executionId = Math.max(this.executionId + 1, System.currentTimeMillis());
		}
		this.executionsWriter.addDocument(new ExecutionDocument(this.executionId));
		this.executionsWriter.flush();
		this.compact();
	}

	/**
	 * Global destruction method, which should be called when testing plug-in stops.
	 */
	public void dispose() {
		this.resultsSearcher = null;
		if (this.resultsReader != null) {
			try {
				this.resultsReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.resultsReader = null;
			}
		}
		if (this.executionsReader != null) {
			try {
				this.executionsReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.executionsReader = null;
			}
		}
		if (this.resultsWriter != null) {
			try {
				this.resultsWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.resultsWriter = null;
			}
		}
		if (this.executionsWriter != null) {
			try {
				this.executionsWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.executionsWriter = null;
			}
		}
		if (this.analyzer != null) {
			this.analyzer.close();
			this.analyzer = null;
		}
	}

	public interface Operation {
		public void run() throws Exception;
	}

	private void compact() throws IOException {
		Sort sort = new Sort(new SortedNumericSortField("executionId", SortField.Type.LONG, true));
		TopDocs executions = this.executionsSearcher.search(new MatchAllDocsQuery(), HISTORY_SIZE + 1, sort, false,
				false);
		if (executions.scoreDocs.length > HISTORY_SIZE) {
			Document firstExecutionToDelete = this.executionsReader.document(executions.scoreDocs[HISTORY_SIZE].doc);
			long deleteFromExecutionID = firstExecutionToDelete.getField("executionId").numericValue().longValue();
			Query deleteQuery = LongPoint.newRangeQuery("executionId", Long.MIN_VALUE, deleteFromExecutionID);
			this.resultsWriter.deleteDocuments(deleteQuery);
			this.executionsWriter.deleteDocuments(deleteQuery);
			this.resultsWriter.flush();
			this.executionsWriter.flush();
		}
	}

	/**
	 * Executes test operation, and measure execution time
	 * 
	 * @param id
	 *            Test identifier within the bounds of testing plug-in.
	 * @param operation
	 *            Operation to test
	 * @param times
	 *            How many times the operation will be executed for calculating
	 *            average execution time
	 * @param threshold
	 *            Threshold in percents
	 * @throws Exception
	 */
	public void execute(String id, Operation operation, int times, int threshold) throws Exception {
		long testTimeSum = 0;
		for (int i = 0; i < times; ++i) {
			long testStart = System.currentTimeMillis();
			operation.run();
			testTimeSum += System.currentTimeMillis() - testStart;
		}
		long testAverage = testTimeSum / times;

		long savedAverage = this.getAverage(id, AVG_SAMPLES);
		if (savedAverage != 0 && savedAverage != -1 && testAverage > savedAverage) {
			long diff = testAverage - savedAverage;
			if (diff * 100 / savedAverage > threshold) {
				Assert.fail("Average execution time (" + testAverage + "ms) is greater by more than " + threshold
						+ "% than the saved average (" + savedAverage + "ms)");
			}
		}

		this.writeResult(id, testAverage);
	}

	private void writeResult(String testId, long time) throws IOException {
		ResultDocument newDoc = new ResultDocument(this.executionId, testId, time);
		this.resultsWriter.addDocument(newDoc);
		this.resultsWriter.flush();
	}

	/**
	 * Returns average time of running a test.
	 * 
	 * @param testId
	 *            Test ID
	 * @param samples
	 *            Number of invocations to take into account
	 * @return average time
	 * @throws IOException
	 */
	private long getAverage(String testId, int samples) throws IOException {
		Query query = new TermQuery(new Term("testName", testId));
		Sort sort = new Sort(new SortedNumericSortField("executionId", SortField.Type.LONG, true));
		TopDocs topDocs = this.resultsSearcher.search(query, samples, sort, false, false);

		if (topDocs.scoreDocs.length != samples) {
			return -1;
		}
		long totalTime = 0;
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			long thisValue = this.resultsReader.document(scoreDoc.doc).getField("time").numericValue().longValue();
			totalTime += thisValue;
		}
		return totalTime / samples;
	}

	private class CustomDocument implements Iterable<IndexableField> {
		protected final List<IndexableField> fields = new ArrayList<>();

		protected class StoredSortedLongField extends Field {
			public StoredSortedLongField(String name, long value) {
				super(name, STORED_SORTED_LONG_FIELD_TYPE);
				fieldsData = Long.valueOf(value);
			}
		}

		protected class StoredLongField extends Field {
			public StoredLongField(String name, long value) {
				super(name, STORED_LONG_FIELD_TYPE);
				fieldsData = Long.valueOf(value);
			}
		}

		@Override
		public Iterator<IndexableField> iterator() {
			return fields.iterator();
		}
	}

	private class ExecutionDocument extends CustomDocument {
		public ExecutionDocument(long executionId) {
			this.fields.add(new StoredSortedLongField("executionId", executionId));
		}

	}

	private class ResultDocument extends CustomDocument {
		public ResultDocument(long executionId, String testName, long time) {
			this.fields.add(new StoredSortedLongField("executionId", executionId));
			this.fields.add(new StringField("testName", testName, Field.Store.YES));
			this.fields.add(new StoredLongField("time", time));
		}
	}
}
