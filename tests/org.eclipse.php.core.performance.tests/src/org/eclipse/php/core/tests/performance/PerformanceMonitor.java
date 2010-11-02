package org.eclipse.php.core.tests.performance;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import junit.framework.Assert;

import org.h2.jdbcx.JdbcConnectionPool;
import org.osgi.framework.Bundle;

/**
 * This monitor is created per testing plug-in for storing various performance
 * information on tests execution. This class isn't thread safe, as only one
 * test should be performed at a time in a single testing plug-in.
 * 
 * @author Michael
 * 
 */
public class PerformanceMonitor {

	private static final int AVG_SAMPLES = 5;
	private static final int HISTORY_SIZE = 50;

	private JdbcConnectionPool pool;
	private int executionId;

	public PerformanceMonitor(Bundle bundle) throws Exception {
		Class.forName("org.h2.Driver");

		File resultsDir = new File(System.getProperty("user.home")
				+ File.separator + ".perf_results" + File.separator
				+ bundle.getSymbolicName() + bundle.getVersion());
		if (!resultsDir.exists()) {
			resultsDir.mkdirs();
		}

		pool = JdbcConnectionPool.create("jdbc:h2:"
				+ new File(resultsDir, "db").getAbsolutePath(), "test", "");
		createSchema();
		executionId = getExecutionId();

		compact();
	}

	/**
	 * Global destruction method, which should be called when testing plug-in
	 * stops.
	 */
	public void dispose() {
		try {
			pool.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSchema() throws SQLException {
		Connection connection = pool.getConnection();
		try {
			String sql = "CREATE TABLE IF NOT EXISTS TESTS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR NOT NULL);\n"
					+ "CREATE TABLE IF NOT EXISTS EXECUTIONS(ID INT AUTO_INCREMENT PRIMARY KEY, DATE TIMESTAMP NOT NULL);\n"
					+ "CREATE TABLE IF NOT EXISTS RESULTS(EXECUTION_ID INT NOT NULL, TEST_ID INT NOT NULL, TIME LONG NOT NULL, FOREIGN KEY(EXECUTION_ID) REFERENCES EXECUTIONS(ID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(TEST_ID) REFERENCES TESTS(ID) ON UPDATE CASCADE ON DELETE CASCADE);\n";
			Statement statement = connection.createStatement();
			try {
				statement.executeUpdate(sql);
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
	}

	private void compact() throws SQLException {
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM EXECUTIONS WHERE ID IN (SELECT ID FROM (SELECT ROWNUM() AS R,ID FROM (SELECT ID FROM EXECUTIONS ORDER BY DATE DESC)) WHERE R > ?);");
			statement.setInt(1, HISTORY_SIZE);
			try {
				statement.executeUpdate();
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
	}

	private int getExecutionId() throws Exception {
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO EXECUTIONS(DATE) VALUES(?);",
					Statement.RETURN_GENERATED_KEYS);
			try {
				statement.setTimestamp(1,
						new Timestamp(System.currentTimeMillis()));
				statement.executeUpdate();
				ResultSet result = statement.getGeneratedKeys();
				try {
					result.next();
					return result.getInt(1);
				} finally {
					result.close();
				}
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
	}

	private int getTestId(String testId) throws Exception {
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT ID FROM TESTS WHERE NAME=?;");
			statement.setString(1, testId);
			try {
				ResultSet result = statement.executeQuery();
				try {
					if (result.next()) {
						return result.getInt(1);
					}
				} finally {
					result.close();
				}
			} finally {
				statement.close();
			}

			statement = connection.prepareStatement(
					"INSERT INTO TESTS(NAME) VALUES(?);",
					Statement.RETURN_GENERATED_KEYS);
			try {
				statement.setString(1, testId);
				statement.executeUpdate();
				ResultSet result = statement.getGeneratedKeys();
				try {
					result.next();
					return result.getInt(1);
				} finally {
					result.close();
				}
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
	}

	private void writeResult(int testId, long time) throws SQLException {
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO RESULTS(EXECUTION_ID, TEST_ID, TIME) VALUES(?,?,?);");
			try {
				statement.setInt(1, executionId);
				statement.setInt(2, testId);
				statement.setLong(3, time);
				statement.executeUpdate();
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
	}

	/**
	 * Returns average time of running a test.
	 * 
	 * @param testId
	 *            Test ID
	 * @param samples
	 *            Number of invocations to take into account
	 * @return average time
	 * @throws SQLException
	 */
	private long getAverage(int testId, int samples) throws SQLException {
		Connection connection = pool.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT COUNT(*),AVG(TIME) FROM ("
							+ "SELECT R.TIME FROM RESULTS AS R JOIN TESTS AS T ON R.TEST_ID = T.ID AND T.ID=? "
							+ "JOIN EXECUTIONS AS E ON R.EXECUTION_ID = E.ID "
							+ "ORDER BY E.DATE DESC LIMIT ?);");
			statement.setInt(1, testId);
			statement.setInt(2, samples);
			try {
				ResultSet result = statement.executeQuery();
				try {
					if (result.next()) {
						if (result.getInt(1) == samples) {
							return result.getLong(2);
						}
					}
				} finally {
					result.close();
				}
			} finally {
				statement.close();
			}
		} finally {
			connection.close();
		}
		return -1;
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
	public void execute(String id, Operation operation, int times, int threshold)
			throws Exception {
		int testId = getTestId(id);

		long testTimeSum = 0;
		for (int i = 0; i < times; ++i) {
			long testStart = System.currentTimeMillis();
			operation.run();
			testTimeSum += System.currentTimeMillis() - testStart;
		}
		long testAverage = testTimeSum / times;

		long savedAverage = getAverage(testId, AVG_SAMPLES);
		if (savedAverage != 0 && savedAverage != -1
				&& testAverage > savedAverage) {

			long diff = testAverage - savedAverage;
			if (diff * 100 / savedAverage > threshold) {

				Assert.fail("Average execution time (" + testAverage
						+ "ms) is greater by more than " + threshold
						+ "% than the saved average (" + savedAverage + "ms)");
			}
		}

		writeResult(testId, testAverage);
	}

	public interface Operation {
		public void run() throws Exception;
	}
}
