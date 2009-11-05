<?php
namespace PEAR2\Pyrus\Developer\CoverageAnalyzer\SourceFile {
use PEAR2\Pyrus\Developer\CoverageAnalyzer\Aggregator,
    PEAR2\Pyrus\Developer\CoverageAnalyzer\AbstractSourceDecorator;
class PerTest extends \PEAR2\Pyrus\Developer\CoverageAnalyzer\SourceFile
{
    protected $testname;

    function __construct($path, Aggregator $agg, $testpath, $sourcepath, $testname)
    {
        $this->testname = $testname;
        parent::__construct($path, $agg, $testpath, $sourcepath);
    }

    function setCoverage()
    {
        $this->coverage = $this->aggregator->retrieveCoverageByTest($this->path, $this->testname);
    }

    function coveredLines()
    {
        $info = $this->aggregator->coverageInfoByTest($this->path, $this->testname);
        return $info[0];
    }

    function render(AbstractSourceDecorator $decorator = null)
    {
        if ($decorator === null) {
            $decorator = new DefaultSourceDecorator('.');
        }
        return $decorator->render($this, $this->testname);
    }

    function coveragePercentage()
    {
        return $this->aggregator->coveragePercentage($this->path, $this->testname);
    }
}
}
?>
