<?php
namespace PEAR2\Pyrus\Developer\CoverageAnalyzer {
class Sqlite
{
    protected $db;
    protected $totallines = 0;
    protected $coveredlines = 0;
    protected $pathCovered = array();
    protected $pathTotal = array();
    public $codepath;
    public $testpath;

    function __construct($path = ':memory:', $codepath = null, $testpath = null)
    {
        $this->db = new \Sqlite3($path);

        $sql = 'SELECT version FROM analyzerversion';
        if (@$this->db->querySingle($sql) == '2.1.0') {
            $this->codepath = $this->db->querySingle('SELECT codepath FROM paths');
            $this->testpath = $this->db->querySingle('SELECT testpath FROM paths');
            return;
        }
        if (!$codepath || !$testpath) {
            throw new Exception('Both codepath and testpath must be set in ' .
                                'order to initialize a coverage database');
        }
        $this->codepath = $codepath;
        $this->testpath = $testpath;
        // restart the database
        echo "Upgrading database to version 2.1.0\n";
        $this->db->exec('
            DROP TABLE coverage;
            DROP TABLE files;
            DROP TABLE tests;
            DROP TABLE coverage_per_file;
            DROP TABLE xdebugs;
            DROP TABLE analyzerversion;
            VACUUM;');

        $this->db->exec('BEGIN');

        $query = '
          CREATE TABLE coverage (
            files_id integer NOT NULL,
            tests_id integer NOT NULL,
            linenumber INTEGER NOT NULL,
            iscovered BOOL NOT NULL,
            issource BOOL NOT NULL,
            PRIMARY KEY (files_id, tests_id, linenumber)
          );
          CREATE INDEX coverage_files_id on coverage (files_id);
          CREATE INDEX coverage_tests_id on coverage (tests_id, issource);
          CREATE INDEX coverage_tests_id2 on coverage (tests_id, files_id, issource);
          CREATE INDEX coverage_linenumber on coverage (files_id, linenumber);
          CREATE INDEX coverage_issource on coverage (issource);

          CREATE TABLE coverage_per_file (
            files_id integer NOT NULL,
            linenumber INTEGER NOT NULL,
            coverage INTEGER NOT NULL,
            PRIMARY KEY (files_id, linenumber)
          );
          CREATE INDEX coverage_per_file_linenumber on coverage_per_file (linenumber);
          ';
        $worked = $this->db->exec($query);
        if (!$worked) {
            @$this->db->exec('ROLLBACK');
            $error = $this->db->lastErrorMsg();
            throw new Exception('Unable to create Code Coverage SQLite3 database: ' . $error);
        }

        $query = '
          CREATE TABLE files (
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            filepath TEXT(500) NOT NULL,
            filepathmd5 TEXT(32) NOT NULL,
            issource BOOL NOT NULL,
            UNIQUE (filepath)
          );
          CREATE INDEX files_issource on files (issource);
          ';
        $worked = $this->db->exec($query);
        if (!$worked) {
            @$this->db->exec('ROLLBACK');
            $error = $this->db->lastErrorMsg();
            throw new Exception('Unable to create Code Coverage SQLite3 database: ' . $error);
        }

        $query = '
          CREATE TABLE xdebugs (
            xdebugpath TEXT(500) NOT NULL,
            xdebugpathmd5 TEXT(32) NOT NULL,
            PRIMARY KEY (xdebugpath)
          );';
        $worked = $this->db->exec($query);
        if (!$worked) {
            @$this->db->exec('ROLLBACK');
            $error = $this->db->lastErrorMsg();
            throw new Exception('Unable to create Code Coverage SQLite3 database: ' . $error);
        }

        $query = '
          CREATE TABLE tests (
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            testpath TEXT(500) NOT NULL,
            testpathmd5 TEXT(32) NOT NULL,
            UNIQUE (testpath)
          );';
        $worked = $this->db->exec($query);
        if (!$worked) {
            @$this->db->exec('ROLLBACK');
            $error = $this->db->lastErrorMsg();
            throw new Exception('Unable to create Code Coverage SQLite3 database: ' . $error);
        }

        $query = '
          CREATE TABLE analyzerversion (
            version TEXT(5) NOT NULL
          );

          INSERT INTO analyzerversion VALUES("2.0.0");

          CREATE TABLE paths (
            codepath TEXT NOT NULL,
            testpath TEXT NOT NULL,
          );
          
          INSERT INTO paths ("' . $this->db->escapeString($codepath) . '", "' .
          $this->db->escapeString($testpath). '")';
        $worked = $this->db->exec($query);
        if (!$worked) {
            @$this->db->exec('ROLLBACK');
            $error = $this->db->lastErrorMsg();
            throw new Exception('Unable to create Code Coverage SQLite3 database: ' . $error);
        }
        $this->db->exec('COMMIT');
    }

    function retrieveLineLinks($file)
    {
        $id = $this->getFileId($file);
        $query = 'SELECT t.testpath, c.linenumber
            FROM
                coverage c, tests t
            WHERE
                c.files_id=' . $id . ' AND t.id=c.tests_id' ;
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve line links for ' . $file .
                                ' line #' . $line .  ': ' . $error);
        }

        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_ASSOC)) {
            $ret[$res['linenumber']][] = $res['testpath'];
        }
        return $ret;
    }

    function retrieveTestPaths()
    {
        $query = 'SELECT testpath from tests ORDER BY testpath';
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve test paths :' . $error);
        }
        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $ret[] = $res[0];
        }
        return $ret;
    }

    function retrievePathsForTest($test, $all = 0)
    {
        $id = $this->getTestId($test);
        if ($all) {
            $query = 'SELECT DISTINCT filepath
                FROM coverage c, files
                WHERE c.tests_id=' . $id . '
                    AND files.id=c.files_id
                GROUP BY c.files_id
                ORDER BY filepath';
        } else {
            $query = 'SELECT DISTINCT filepath
                FROM coverage c, files
                WHERE c.tests_id=' . $id . '
                    AND c.issource=1
                    AND files.id=c.files_id
                GROUP BY c.files_id
                ORDER BY filepath';
        }
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve file paths for test ' . $test . ':' . $error);
        }
        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $ret[] = $res[0];
        }
        return $ret;
    }

    function retrievePaths($all = 0)
    {
        if ($all) {
            $query = 'SELECT filepath from files ORDER BY filepath';
        } else {
            $query = 'SELECT filepath from files WHERE issource=1 ORDER BY filepath';
        }
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve file paths :' . $error);
        }
        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $ret[] = $res[0];
        }
        return $ret;
    }

    function coveragePercentage($sourcefile, $testfile = null)
    {
        if ($testfile) {
            $coverage = $this->retrievePathCoverageByTest($sourcefile, $testfile);
        } else {
            $coverage = $this->retrievePathCoverage($sourcefile);
        }
        return round(($coverage[0] / $coverage[1]) * 100);
    }

    function retrieveProjectCoverage()
    {
        if ($this->totallines) {
            return array($this->coveredlines, $this->totallines);
        }
        $query = 'SELECT COUNT(linenumber),filepath FROM coverage_per_file, files
                WHERE files.id=coverage_per_file.files_id
                GROUP BY files_id';
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve coverage for ' . $path.  ': ' . $error);
        }
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $this->pathTotal[$res[1]] = $res[0];
            $this->totallines += $res[0];
        }

        $query = 'SELECT COUNT(linenumber),filepath FROM coverage_per_file, files
                WHERE coverage > 0 AND files.id=coverage_per_file.files_id
                GROUP BY files_id';
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve coverage for ' . $path.  ': ' . $error);
        }
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $this->pathCovered[$res[1]] = $res[0];
            $this->coveredlines += $res[0];
        }

        return array($this->coveredlines, $this->totallines);
    }

    function retrievePathCoverage($path)
    {
        if (!$this->totallines) {
            // set up the cache
            $this->retrieveProjectCoverage();
        }
        if (!isset($this->pathCovered[$path])) {
            return array(0, 0);
        }
        return array($this->pathCovered[$path], $this->pathTotal[$path]);
    }

    function retrievePathCoverageByTest($path, $test)
    {
        $id = $this->getFileId($path);
        $testid = $this->getTestId($test);

        $query = 'SELECT COUNT(linenumber)
            FROM coverage
            WHERE issource=1 AND files_id=' . $id . ' AND tests_id=' . $testid;
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve path coverage for ' . $path .
                                ' in test ' . $test . ': ' . $error);
        }

        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $total = $res[0];
        }

        $query = 'SELECT COUNT(linenumber)
            FROM coverage
            WHERE issource=1 AND iscovered AND files_id=' . $id. ' AND tests_id=' . $testid;
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve path coverage for ' . $path .
                                ' in test ' . $test . ': ' . $error);
        }

        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $covered = $res[0];
        }
        return array($covered, $total);
    }

    function retrieveCoverageByTest($path, $test)
    {
        $id = $this->getFileId($path);
        $testid = $this->getTestId($test);

        $query = 'SELECT iscovered as coverage, linenumber FROM coverage
                    WHERE issource=1 AND files_id=' . $id . ' AND tests_id=' . $testid;
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve test ' . $test .
                                ' coverage for ' . $path.  ': ' . $error);
        }

        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_ASSOC)) {
            $ret[$res['linenumber']] = $res['coverage'];
        }
        return $ret;
    }

    function getFileId($path)
    {
        $query = 'SELECT id FROM files WHERE filepath=:filepath';
        $stmt = $this->db->prepare($query);
        $stmt->bindValue(':filepath', $path);
        if (!($result = $stmt->execute())) {
            throw new Exception('Unable to retrieve file ' . $path . ' id from database');
        }
        while ($id = $result->fetchArray(SQLITE3_NUM)) {
            return $id[0];
        }
        throw new Exception('Unable to retrieve file ' . $path . ' id from database');
    }

    function getTestId($path)
    {
        $query = 'SELECT id FROM tests WHERE testpath=:filepath';
        $stmt = $this->db->prepare($query);
        $stmt->bindValue(':filepath', $path);
        if (!($result = $stmt->execute())) {
            throw new Exception('Unable to retrieve test file ' . $path . ' id from database');
        }
        while ($id = $result->fetchArray(SQLITE3_NUM)) {
            return $id[0];
        }
        throw new Exception('Unable to retrieve test file ' . $path . ' id from database');
    }

    function removeOldTest($testpath, $xdebugpath)
    {
        try {
            $id = $this->getTestId($testpath);
        } catch (\Exception $e) {
            // get a unique ID
            return $this->db->querySingle('SELECT COUNT(id) from tests')+1;
        }
        $this->db->exec('DELETE FROM tests WHERE id=' . $id);
        $this->db->exec('DELETE FROM coverage WHERE tests_id=' . $id);
        $this->db->exec('DELETE FROM xdebugs WHERE xdebugpath="' . $this->db->escapeString($xdebugpath) . '"');
        return $id;
    }

    function addTest($testpath)
    {
        $id = $this->removeOldTest($testpath, str_replace('.phpt', '.xdebug', $testpath));
        $query = 'INSERT INTO tests
            (testpath, testpathmd5)
            VALUES(:testpath, :md5)';
        $stmt = $this->db->prepare($query);
        $stmt->bindValue(':testpath', $testpath);
        $md5 = md5_file($testpath);
        $stmt->bindValue(':md5', $md5);
        $stmt->execute();
        $id = $this->db->lastInsertRowID();

        $query = 'INSERT INTO xdebugs
            (xdebugpath, xdebugpathmd5)
            VALUES(:testpath, :md5)';
        $stmt = $this->db->prepare($query);
        $stmt->bindValue(':testpath', str_replace('.phpt', '.xdebug', $testpath));
        $md5 = md5_file(str_replace('.phpt', '.xdebug', $testpath));
        $stmt->bindValue(':md5', $md5);
        $stmt->execute();
        return $id;
    }

    function unChangedXdebug($path)
    {
        $query = 'SELECT xdebugpathmd5 FROM xdebugs
                    WHERE xdebugpath=:path';
        $stmt = $this->db->prepare($query);
        $stmt->bindValue(':path', $path);
        $result = $stmt->execute();
        if (!$result) {
            return false;
        }
        $md5 = 0;
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $md5 = $res[0];
        }
        if (!$md5) {
            return false;
        }
        if ($md5 == md5_file($path)) {
            return true;
        }
        return false;
    }

    function addFile($filepath, $issource = 0)
    {
        $query = 'SELECT id FROM files WHERE filepath=:filepath';
        $stmt = $this->db->prepare($query);
        $stmt->bindParam(':filepath', $filepath);
        if (!($result = $stmt->execute())) {
            throw new Exception('Unable to add file ' . $filepath . ' to database');
        }
        while ($id = $result->fetchArray(SQLITE3_NUM)) {
            $query = 'UPDATE files SET filepathmd5=:md5 WHERE filepath=:filepath';
            $stmt = $this->db->prepare($query);
            $stmt->bindParam(':filepath', $filepath);
            $md5 = md5_file($filepath);
            $stmt->bindParam(':md5', $md5);
            if (!$stmt->execute()) {
                throw new Exception('Unable to update file ' . $filepath . ' md5 in database');
            }
            return $id[0];
        }
        $stmt->clear();
        $query = 'INSERT INTO files
            (filepath, filepathmd5, issource)
            VALUES(:testpath, :md5, :issource)';
        $stmt = $this->db->prepare($query);
        $stmt->bindValue(':testpath', $filepath);
        $md5 = md5_file($filepath);
        $stmt->bindValue(':md5', $md5);
        $stmt->bindValue(':issource', $issource);
        if (!$stmt->execute()) {
            throw new Exception('Unable to add file ' . $filepath . ' to database');
        }
        return $this->db->lastInsertRowID();
    }

    function getTotalCoverage($file, $linenumber)
    {
        $query = 'SELECT coveragecount FROM coverage_per_file
                    WHERE files_id=' . $this->getFileId($file) . ' AND linenumber=' . $linenumber;
        $result = $this->db->query($query);
        if (!$result) {
            return false;
        }
        $coverage = 0;
        while ($res = $result->fetchArray(SQLITE3_NUM)) {
            $coverage = $res[0];
        }
        return $coverage;
    }

    function retrieveCoverage($path)
    {
        $id = $this->getFileId($path);
        $query = 'SELECT coverage, linenumber FROM coverage_per_file
                    WHERE files_id=' . $id . '
                    ORDER BY linenumber ASC';
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve coverage for ' . $path.  ': ' . $error);
        }

        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_ASSOC)) {
            $ret[$res['linenumber']] = $res['coverage'];
        }
        return $ret;
    }

    /**
     * This is used to get the coverage which is then inserted into our
     * intermediate coverage_per_file table to speed things up at rendering
     */
    function retrieveSlowCoverage($id)
    {
        $query = 'SELECT SUM(iscovered) as coverage, linenumber FROM coverage WHERE files_id=' . $id . '
                    GROUP BY linenumber';
        $result = $this->db->query($query);
        if (!$result) {
            $error = $this->db->lastErrorMsg();
            throw new Exception('Cannot retrieve coverage for ' . $path.  ': ' . $error);
        }

        $ret = array();
        while ($res = $result->fetchArray(SQLITE3_ASSOC)) {
            $ret[$res['linenumber']] = $res['coverage'];
        }
        return $ret;
    }

    function updateTotalCoverage()
    {
        $query = 'DELETE FROM coverage_per_file';
        $this->db->exec($query);
        echo "Updating coverage per-file intermediate table\n";
        foreach ($this->retrievePaths() as $path) {
            echo ".";
            $id = $this->getFileId($path);
            foreach ($this->retrieveSlowCoverage($id) as $linenumber => $coverage) {
                $query = 'INSERT INTO coverage_per_file
                    (files_id, coverage, linenumber)
                    VALUES(' . $id . ',' . $coverage . ',' . $linenumber .')';
                $this->db->exec($query);
            }
        }
        echo "done\n";
    }

    function addCoverage($testpath, $testid, $xdebug)
    {
        foreach ($xdebug as $path => $results) {
            if (!file_exists($path)) {
                continue;
            }
            if (strpos($path, $this->codepath) !== 0) {
                $issource = 0;
            } else {
                if (strpos($path, $this->testpath) === 0) {
                    $issource = 0;
                } else {
                    $issource = 1;
                }
            }
            echo ".";
            $id = $this->addFile($path, $issource);
            foreach ($results as $line => $info) {
                if ($info > 0) {
                    $res = 1;
                } else {
                    $res = 0;
                }
                $query = 'INSERT INTO coverage
                    (files_id, tests_id, linenumber, iscovered, issource)
                    VALUES(' . $id . ', ' . $testid . ', ' . $line . ', ' . $res . ',' . $issource . ')';

                $worked = $this->db->exec($query);
                if (!$worked) {
                    $error = $this->db->lastErrorMsg();
                    throw new Exception('Cannot add coverage for test ' . $testpath .
                                        ', covered file ' . $path . ': ' . $error);
                }
            }
        }
    }

    function begin()
    {
        $this->db->exec('BEGIN');
    }

    function commit()
    {
        $this->db->exec('COMMIT');
    }

    /**
     * Retrieve a list of .phpt tests that either have been modified,
     * or the files they access have been modified
     * @return array
     */
    function getModifiedTests()
    {
        $modifiedPaths = array();
        $modifiedTests = array();
        $paths = $this->retrievePaths(1);
        echo "Scanning ", count($paths), " source files";
        foreach ($paths as $path) {
            echo '.';
            $query = '
                SELECT id, filepathmd5 FROM files where filepath="' .
                $this->db->escapeString($path) . '"';
            $result = $this->db->query($query);
            while ($res = $result->fetchArray(SQLITE3_ASSOC)) {
                if (md5_file($path) == $res['filepathmd5']) {
                    break;
                }
                $modifiedPaths[] = $path;
                // file is modified, get a list of tests that execute this file
                $query = '
                    SELECT t.testpath
                    FROM coverage c, tests t
                    WHERE
                        c.files_id=' . $res['id'] . '
                        AND t.id=c.tests_id';
                $result2 = $this->db->query($query);
                while ($res = $result2->fetchArray(SQLITE3_NUM)) {
                    $modifiedTests[$res[0]] = true;
                }
                break;
            }
        }
        echo "done\n";
        echo count($modifiedPaths), ' modified files resulting in ',
            count($modifiedTests), " modified tests\n";
        $paths = $this->retrieveTestPaths();
        echo "Scanning ", count($paths), " test paths";
        foreach ($paths as $path) {
            echo '.';
            $query = '
                SELECT id, testpathmd5 FROM tests where testpath="' .
                $this->db->escapeString($path) . '"';
            $result = $this->db->query($query);
            while ($res = $result->fetchArray(SQLITE3_ASSOC)) {
                if (md5_file($path) != $res['testpathmd5']) {
                    $modifiedTests[$path] = true;
                }
            }
        }
        echo "done\n";
        echo count($modifiedTests), " tests should be re-run\n";
        return array_keys($modifiedTests);
    }
}
}
?>