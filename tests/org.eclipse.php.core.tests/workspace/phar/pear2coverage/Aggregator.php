<?php
namespace PEAR2\Pyrus\Developer\CoverageAnalyzer {
class Aggregator
{
    protected $codepath;
    protected $testpath;
    protected $sqlite;
    public $totallines = 0;
    public $totalcoveredlines = 0;

    /**
     * @var string $testpath Location of .phpt files
     * @var string $codepath Location of code whose coverage we are testing
     */
    function __construct($testpath, $codepath, $db = ':memory:')
    {
        $newcodepath = realpath($codepath);
        if (!$newcodepath) {
            if (!strpos($codepath, '://') || !file_exists($codepath)) {
                // stream wrapper not found
                throw new Exception('Can not find code path $codepath');
            }
        } else {
            $codepath = $newcodepath;
        }
        $this->sqlite = new Sqlite($db, $codepath, $testpath);
        $this->codepath = $codepath;
        $this->sqlite->begin();
        echo "Scanning for xdebug coverage files...";
        $files = $this->scan($testpath);
        echo "done\n";
        $infostring = '';
        echo "Parsing xdebug results\n";
        if (count($files)) {
            foreach ($files as $testid => $xdebugfile) {
                if (!file_exists(str_replace('.xdebug', '.phpt', $xdebugfile))) {
                    echo "\nWARNING: outdated .xdebug file $xdebugfile, delete this relic\n";
                    continue;
                }
                $id = $this->sqlite->addTest(str_replace('.xdebug', '.phpt', $xdebugfile));
                echo '(' . $testid . ' of ' . count($files) . ') ' . $xdebugfile;
                $this->retrieveXdebug($xdebugfile, $id);
                echo "done\n";
            }
            echo "done\n";
            $this->sqlite->updateTotalCoverage();
            $this->sqlite->commit();
        } else {
            echo "done (no modified xdebug files)\n";
        }
    }

    function retrieveLineLinks($file)
    {
        return $this->sqlite->retrieveLineLinks($file);
    }

    function retrievePaths()
    {
        return $this->sqlite->retrievePaths();
    }

    function retrievePathsForTest($test)
    {
        return $this->sqlite->retrievePathsForTest($test);
    }

    function retrieveTestPaths()
    {
        return $this->sqlite->retrieveTestPaths();
    }

    function coveragePercentage($sourcefile, $testfile = null)
    {
        return $this->sqlite->coveragePercentage($sourcefile, $testfile);
    }

    function coverageInfo($path)
    {
        return $this->sqlite->retrievePathCoverage($path);
    }

    function coverageInfoByTest($path, $test)
    {
        return $this->sqlite->retrievePathCoverageByTest($path, $test);
    }

    function retrieveCoverage($path)
    {
        return $this->sqlite->retrieveCoverage($path);
    }

    function retrieveCoverageByTest($path, $test)
    {
        return $this->sqlite->retrieveCoverageByTest($path, $test);
    }

    function retrieveXdebug($path, $testid)
    {
        $source = '$xdebug = ' . file_get_contents($path) . ";\n";
        eval($source);
        $this->sqlite->addCoverage(str_replace('.xdebug', '.phpt', $path), $testid, $xdebug);
    }

    function scan($testpath)
    {
        $a = $testpath;
        $testpath = realpath($testpath);
        if (!$testpath) {
            throw new Exception('Unable to process path' . $a);
        }
        $testpath = str_replace('\\', '/', $testpath);
        $this->testpath = $testpath;

        // first get a list of all directories
        $dirs = $globdirs = array();
        $index = 0;
        $dir = $testpath;
        do {
            $globdirs = glob($dir . '/*', GLOB_ONLYDIR);
            if ($globdirs) {
                $dirs = array_merge($dirs, $globdirs);
                $dir = $dirs[$index++];
            } else {
                while (!isset($dirs[$index++]) && $index <= count($dirs));
                if (isset($dirs[$index])) {
                    $dir = $dirs[$index];
                }
            }
        } while ($index <= count($dirs));

        // then find all code coverage files
        $xdebugs = array();
        foreach ($dirs as $dir) {
            $globbie = glob($dir . '/*.xdebug');
            $xdebugs = array_merge($xdebugs, $globbie);
        }
        $xdebugs = array_unique($xdebugs);
        $modified = array();
        $unmodified = array();
        foreach ($xdebugs as $path) {
            if ($this->sqlite->unChangedXdebug($path)) {
                $unmodified[$path] = true;
                continue;
            }
            $modified[] = $path;
        }
        $xdebugs = $modified;
        sort($xdebugs);
        // index from 1
        array_unshift($xdebugs, '');
        unset($xdebugs[0]);
        $test = array_flip($xdebugs);
        foreach ($this->sqlite->retrieveTestPaths() as $path) {
            $xdebugpath = str_replace('.phpt', '.xdebug', $path);
            if (isset($test[$xdebugpath]) || isset($unmodified[$xdebugpath])) {
                continue;
            }
            // remove outdated tests
            echo "Removing results from $xdebugpath\n";
            $this->sqlite->removeOldTest($path, $xdebugpath);
        }
        return $xdebugs;
    }

    function render($toPath)
    {
        $decorator = new DefaultSourceDecorator($toPath, $this->testpath, $this->codepath);
        echo "Generating project coverage data...";
        $coverage = $this->sqlite->retrieveProjectCoverage();
        echo "done\n";
        $decorator->renderSummary($this, $this->retrievePaths(), $this->codepath, false, $coverage[1], 
                                  $coverage[0]);
        $a = $this->codepath;
        echo "[Step 2 of 2] Rendering per-test coverage...";
        $decorator->renderTestCoverage($this, $this->testpath, $a);
        echo "done\n";
    }
}
}
?>
