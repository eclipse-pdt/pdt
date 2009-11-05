<?php
namespace PEAR2\Pyrus\Developer\CoverageAnalyzer\Web {
use PEAR2\Pyrus\Developer\CoverageAnalyzer\Sqlite;
class Controller {
    protected $view;
    protected $sqlite;
    protected $rooturl;

    function __construct(View $view, $rooturl)
    {
        $this->view = $view;
        $view->setController($this);
        $this->rooturl = $rooturl;
    }

    function route()
    {
        if (!isset($_SESSION['fullpath'])) {
            if (isset($_POST['setdatabase'])) {
                if (file_exists($_POST['setdatabase'])) {
                    $this->sqlite = new \Sqlite3($_POST['setdatabase']);
                    $_SESSION['fullpath'] = $_POST['setdatabase'];
                    return $this->view->TOC($this->sqlite);
                }
            }
            return $this->getDatabase();
        } else {
            $this->sqlite = new \Sqlite3($_POST['setdatabase']);
            if (isset($_GET['test'])) {
                if ($_GET['test'] === 'TOC') {
                    return $this->view->testTOC($this->sqlite);
                }
                if (isset($_GET['file'])) {
                    return $this->view->fileCoverage($this->sqlite, $_GET['file'], $_GET['test']);
                }
                return $this->view->testTOC($this->sqlite, $_GET['test']);
            }
            if (isset($_GET['file'])) {
                if (isset($_GET['line'])) {
                    return $this->view->fileLineTOC($this->sqlite, $_GET['file'], $_GET['line']);
                }
                return $this->view->fileCoverage($this->sqlite, $_GET['file']);
            }
            return $this->view->TOC($this->sqlite);
        }
    }

    function getFileLink($file, $test = null, $line = null)
    {
        if ($line) {
            return $this->rooturl . '?file=' . urlencode($file) . '&line=' . $line;
        }
        if ($test) {
            return $this->rooturl . '?file=' . urlencode($file) . '&test=' . $test;
        }
        return $this->rooturl . '?file=' . urlencode($file);
    }

    function getTOCLink($test = false)
    {
        if ($test === false) {
            return $this->rooturl;
        }
        if ($test === true) {
            return $this->rooturl . '?test=TOC';
        }
        if ($test) {
            return $this->rooturl . '?test=' . urlencode($test);
        }
    }

    function getDatabase()
    {
        $this->sqlite = $this->view->getDatabase();
    }
}
}
?>
