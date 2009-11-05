<?php
namespace PEAR2\Pyrus\Developer\CoverageAnalyzer\Web {
/**
 * Takes a source file and outputs HTML source highlighting showing the
 * number of hits on each line, highlights un-executed lines in red
 */
class View
{
    protected $savePath;
    protected $testPath;
    protected $sourcePath;
    protected $source;
    protected $controller;

    function getDatabase()
    {
        $output = new \XMLWriter;
        if (!$output->openUri('php://output')) {
            throw new Exception('Cannot open output - this should never happen');
        }
        $output->startElement('html');
         $output->startElement('head');
          $output->writeElement('title', 'Enter a path to the database');
         $output->endElement();
         $output->startElement('body');
          $output->writeElement('h2', 'Please enter the path to a coverage database');
          $output->startElement('form');
           $output->writeAttribute('name', 'getdatabase');
           $output->writeAttribute('method', 'POST');
           $output->writeAttribute('action', $this->controller->getTOCLink());
           $output->startElement('input');
            $output->writeAttribute('type', 'text');
            $output->writeAttribute('name', 'setdatabase');
           $output->endElement();
           $output->startElement('input');
            $output->writeAttribute('type', 'submit');
           $output->endElement();
          $output->endElement();
         $output->endElement();
        $output->endElement();
        $output->endDocument();
    }

    function setController($controller)
    {
        $this->controller = $controller;
    }

    function TOC($sqlite)
    {
        $coverage = $sqlite->retrieveProjectCoverage();
        $this->renderSummary($sqlite, $sqlite->retrievePaths(), false, $coverage[1], $covered[0]);
    }

    function testTOC($sqlite)
    {
        $this->renderTestSummary($sqlite);
    }

    function fileLineTOC($sqlite, $file, $line)
    {
        
    }

    function fileCoverage($sqlite, $file, $test = null)
    {
        
    }

    function mangleFile($path, $istest = false)
    {
        return $this->controller->getFileLink($path, $istest);
    }

    function mangleTestFile($path)
    {
        return $this->controller->getTOClink($path);
    }

    function getLineLink($name, $line)
    {
        return $this->controller->getFileLink($name, null, $line);
    }

    function renderLineSummary($name, $line, $testpath, $tests)
    {
        $output = new \XMLWriter;
        if (!$output->openUri($this->getLinePath($name, $line))) {
            throw new Exception('Cannot render ' . $name . ' line ' . $line . ', opening XML failed');
        }
        $output->setIndentString(' ');
        $output->setIndent(true);
        $output->startElement('html');
        $output->startElement('head');
        $output->writeElement('title', 'Tests covering line ' . $line . ' of ' . $name);
        $output->startElement('link');
        $output->writeAttribute('href', 'cover.css');
        $output->writeAttribute('rel', 'stylesheet');
        $output->writeAttribute('type', 'text/css');
        $output->endElement();
        $output->endElement();
        $output->startElement('body');
        $output->writeElement('h2', 'Tests covering line ' . $line . ' of ' . $name);
        $output->startElement('p');
        $output->startElement('a');
        $output->writeAttribute('href', 'index.html');
        $output->text('Aggregate Code Coverage for all tests');
        $output->endElement();
        $output->endElement();
        $output->startElement('p');
        $output->startElement('a');
        $output->writeAttribute('href', $this->mangleFile($name));
        $output->text('File ' . $name . ' code coverage');
        $output->endElement();
        $output->endElement();
        $output->startElement('ul');
        foreach ($tests as $testfile) {
            $output->startElement('li');
            $output->startElement('a');
            $output->writeAttribute('href', $this->mangleTestFile($testfile));
            $output->text(str_replace($testpath . '/', '', $testfile));
            $output->endElement();
            $output->endElement();
        }
        $output->endElement();
        $output->endElement();
        $output->endDocument();
    }

    /**
     * @param PEAR2\Pyrus\Developer\CodeCoverage\SourceFile $source
     * @param string $istest path to test file this is covering, or false for aggregate
     */
    function render(SourceFile $source, $istest = false)
    {
        $output = new \XMLWriter;
        if (!$output->openUri($this->manglePath($source->name(), $istest))) {
            throw new Exception('Cannot render ' . $source->name() . ', opening XML failed');
        }
        $output->setIndent(false);
        $output->startElement('html');
        $output->text("\n ");
        $output->startElement('head');
        $output->text("\n  ");
        if ($istest) {
            $output->writeElement('title', 'Code Coverage for ' . $source->shortName() . ' in ' . $istest);
        } else {
            $output->writeElement('title', 'Code Coverage for ' . $source->shortName());
        }
        $output->text("\n  ");
        $output->startElement('link');
        $output->writeAttribute('href', 'cover.css');
        $output->writeAttribute('rel', 'stylesheet');
        $output->writeAttribute('type', 'text/css');
        $output->endElement();
        $output->text("\n  ");
        $output->endElement();
        $output->text("\n ");
        $output->startElement('body');
        $output->text("\n ");
        if ($istest) {
            $output->writeElement('h2', 'Code Coverage for ' . $source->shortName() . ' in ' . $istest);
        } else {
            $output->writeElement('h2', 'Code Coverage for ' . $source->shortName());
        }
        $output->text("\n ");
        $output->writeElement('h3', 'Coverage: ' . $source->coveragePercentage() . '%');
        $output->text("\n ");
        $output->startElement('p');
        $output->startElement('a');
        $output->writeAttribute('href', 'index.html');
        $output->text('Aggregate Code Coverage for all tests');
        $output->endElement();
        $output->endElement();
        $output->startElement('pre');
        foreach ($source->source() as $num => $line) {
            $coverage = $source->coverage($num);

            $output->startElement('span');
            $output->writeAttribute('class', 'ln');
            $output->text(str_pad($num, 8, ' ', STR_PAD_LEFT));
            $output->endElement();

            if ($coverage === false) {
                $output->text(str_pad(': ', 13, ' ', STR_PAD_LEFT) . $line);
                continue;
            }

            $output->startElement('span');
            if ($coverage < 1) {
                $output->writeAttribute('class', 'nc');
                $output->text('           ');
            } else {
                $output->writeAttribute('class', 'cv');
                if (!$istest) {
                    $output->startElement('a');
                    $output->writeAttribute('href', $this->getLineLink($source->name(), $num));
                }
                $output->text(str_pad($coverage, 10, ' ', STR_PAD_LEFT) . ' ');
                if (!$istest) {
                    $output->endElement();
                    $this->renderLineSummary($source->name(), $num, $source->testpath(),
                                             $source->getLineLinks($num));
                }
            }

            $output->text(': ' .  $line);
            $output->endElement();
        }
        $output->endElement();
        $output->text("\n ");
        $output->endElement();
        $output->text("\n ");
        $output->endElement();
        $output->endDocument();
    }

    function renderSummary(Aggregator $agg, array $results, $istest = false, $total = 1, $covered = 1)
    {
        $output = new \XMLWriter;
        if (!$output->openUri('php://output')) {
            throw new Exception('Cannot render test summary, opening XML failed');
        }
        $output->setIndentString(' ');
        $output->setIndent(true);
        $output->startElement('html');
        $output->startElement('head');
        if ($istest) {
            $output->writeElement('title', 'Code Coverage Summary [' . $istest . ']');
        } else {
            $output->writeElement('title', 'Code Coverage Summary');
        }
        $output->startElement('link');
        $output->writeAttribute('href', 'cover.css');
        $output->writeAttribute('rel', 'stylesheet');
        $output->writeAttribute('type', 'text/css');
        $output->endElement();
        $output->endElement();
        $output->startElement('body');
        if ($istest) {
            $output->writeElement('h2', 'Code Coverage Files for test ' . $istest);
        } else {
            $output->writeElement('h2', 'Code Coverage Files');
            $output->writeElement('h3', 'Total lines: ' . $total . ', covered lines: ' . $covered);
            $percent = 0;
            if ($total > 0) {
                $percent = round(($covered / $total) * 100);
            }
            $output->startElement('p');
            if ($percent < 50) {
                $output->writeAttribute('class', 'bad');
            } elseif ($percent < 75) {
                $output->writeAttribute('class', 'ok');
            } else {
                $output->writeAttribute('class', 'good');
            }
            $output->text($percent . '% code coverage');
            $output->endElement();
        }
        $output->startElement('p');
        $output->startElement('a');
        $output->writeAttribute('href', $this->controller->getTOCLink(true));
        $output->text('Code Coverage per PHPT test');
        $output->endElement();
        $output->endElement();
        $output->startElement('ul');
        foreach ($results as $i => $name) {
            $source = new SourceFile($name, $agg, $this->testPath, $this->sourcePath);
            $output->startElement('li');
            $percent = $source->coveragePercentage();
            $output->startElement('span');
            if ($percent < 50) {
                $output->writeAttribute('class', 'bad');
            } elseif ($percent < 75) {
                $output->writeAttribute('class', 'ok');
            } else {
                $output->writeAttribute('class', 'good');
            }
            $output->text(' Coverage: ' . str_pad($percent . '%', 4, ' ', STR_PAD_LEFT));
            $output->endElement();
            $output->startElement('a');
            $output->writeAttribute('href', $this->mangleFile($name, $istest));
            $output->text($source->shortName());
            $output->endElement();
            $output->endElement();
        }
        $output->endElement();
        $output->endElement();
        $output->endDocument();
    }

    function renderTestSummary(Aggregator $agg)
    {
        $output = new \XMLWriter;
        if (!$output->openUri('php://output')) {
                throw new Exception('Cannot render tests summary, opening XML failed');
        }
        $output->setIndentString(' ');
        $output->setIndent(true);
        $output->startElement('html');
        $output->startElement('head');
        $output->writeElement('title', 'Test Summary');
        $output->startElement('link');
        $output->writeAttribute('href', 'cover.css');
        $output->writeAttribute('rel', 'stylesheet');
        $output->writeAttribute('type', 'text/css');
        $output->endElement();
        $output->endElement();
        $output->startElement('body');
        $output->writeElement('h2', 'Tests Executed, click for code coverage summary');
        $output->startElement('p');
        $output->startElement('a');
        $output->writeAttribute('href', $this->controller->getTOClink());
        $output->text('Aggregate Code Coverage for all tests');
        $output->endElement();
        $output->endElement();
        $output->startElement('ul');
        foreach ($agg->retrieveTestPaths() as $test) {
            $output->startElement('li');
            $output->startElement('a');
            $output->writeAttribute('href', $this->mangleTestFile($test));
            $output->text(str_replace($agg->testpath . '/', '', $test));
            $output->endElement();
            $output->endElement();
        }
        $output->endElement();
        $output->endElement();
        $output->endDocument();
    }

    function renderTestCoverage(Aggregator $agg, $testpath, $test)
    {
        $reltest = str_replace($testpath . '/', '', $test);
        $output = new \XMLWriter;
        if (!$output->openUri('php://output')) {
            throw new Exception('Cannot render test ' . $reltest . ' coverage, opening XML failed');
        }
            $output->setIndentString(' ');
            $output->setIndent(true);
            $output->startElement('html');
            $output->startElement('head');
            $output->writeElement('title', 'Code Coverage Summary for test ' . $reltest);
            $output->startElement('link');
            $output->writeAttribute('href', 'cover.css');
            $output->writeAttribute('rel', 'stylesheet');
            $output->writeAttribute('type', 'text/css');
            $output->endElement();
            $output->endElement();
            $output->startElement('body');
            $output->writeElement('h2', 'Code Coverage Files for test ' . $reltest);
            $output->startElement('ul');
            $paths = $agg->retrievePathsForTest($test);
            foreach ($paths as $name) {
                echo '.';
                $source = new SourceFile\PerTest($name, $agg, $testpath, $basePath, $test);
                $this->render($source, $reltest);
                $output->startElement('li');
                $percent = $source->coveragePercentage();
                $output->startElement('span');
                if ($percent < 50) {
                    $output->writeAttribute('class', 'bad');
                } elseif ($percent < 75) {
                    $output->writeAttribute('class', 'ok');
                } else {
                    $output->writeAttribute('class', 'good');
                }
                $output->text(' Coverage: ' . str_pad($source->coveragePercentage() . '%', 4, ' ', STR_PAD_LEFT));
                $output->endElement();
                $output->startElement('a');
                $output->writeAttribute('href', $this->mangleFile($name, $reltest));
                $output->text($source->shortName());
                $output->endElement();
                $output->endElement();
            }
            echo "done\n";
            $output->endElement();
            $output->endElement();
            $output->endDocument();
        }
        echo "done\n";
    }
}
}
?>
