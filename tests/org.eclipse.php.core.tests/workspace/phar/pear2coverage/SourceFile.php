<?php
namespace PEAR2\Pyrus\Developer\CoverageAnalyzer {
class SourceFile
{
    protected $source;
    protected $path;
    protected $sourcepath;
    protected $coverage;
    protected $aggregator;
    protected $testpath;
    protected $linelinks;

    function __construct($path, Aggregator $agg, $testpath, $sourcepath)
    {
        $this->source = file($path);
        $this->path = $path;
        $this->sourcepath = $sourcepath;

        array_unshift($this->source, '');
        unset($this->source[0]); // make source array indexed by line number

        $this->aggregator = $agg;
        $this->testpath = $testpath;
        $this->setCoverage();
    }

    function setCoverage()
    {
        $this->coverage = $this->aggregator->retrieveCoverage($this->path);
    }

    function aggregator()
    {
        return $this->aggregator;
    }

    function testpath()
    {
        return $this->testpath;
    }

    function render(AbstractSourceDecorator $decorator = null)
    {
        if ($decorator === null) {
            $decorator = new DefaultSourceDecorator('.');
        }
        return $decorator->render($this);
    }

    function coverage($line)
    {
        if (!isset($this->coverage[$line])) {
            return false;
        }
        return $this->coverage[$line];
    }

    function coveragePercentage()
    {
        return $this->aggregator->coveragePercentage($this->path);
    }

    function name()
    {
        return $this->path;
    }

    function shortName()
    {
        return str_replace($this->sourcepath . DIRECTORY_SEPARATOR, '', $this->path);
    }

    function source()
    {
        return $this->source;
    }

    function coveredLines()
    {
        $info = $this->aggregator->coverageInfo($this->path);
        return $info[0];
    }

    function getLineLinks($line)
    {
        if (!isset($this->linelinks)) {
            $this->linelinks = $this->aggregator->retrieveLineLinks($this->path);
        }
        if (isset($this->linelinks[$line])) {
            return $this->linelinks[$line];
        }
        return false;
    }
}
}
?>
