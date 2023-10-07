<?php
/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
if (class_exists('PHPUnit_Util_Printer')) {
    class_alias('PHPUnit_Util_Printer', 'Printer');
    class_alias('PHPUnit_Framework_TestListener', 'TestListener');
    class_alias('PHPUnit_Framework_Test', 'Test');
    class_alias('PHPUnit_Framework_TestSuite', 'TestSuite');
    class_alias('PHPUnit_Framework_TestCase', 'TestCase');
    class_alias('PHPUnit_Framework_AssertionFailedError', 'AssertionFailedError');
    class_alias('PHPUnit_Framework_ExpectationFailedException', 'ExpectationFailedException');
    class_alias('PHPUnit_TextUI_ResultPrinter', 'TextPrinter');
    class_alias('PHPUnit_Framework_Warning', 'Warning');
    class_alias('PHPUnit_Framework_Error', 'Error_Error');
    class_alias('PHPUnit_Framework_Error_Warning', 'Error_Warning');
    class_alias('PHPUnit_Framework_Error_Notice', 'Error_Notice');
    class_alias('PHPUnit_Framework_Error_Deprecated', 'Error_Deprecated');
    list ($version) = explode('.', PHPUnit_Runner_Version::id());

    define('_RUNNER_VERSION', (int) $version);
} else {
    class_alias('PHPUnit\Util\Printer', 'Printer');
    class_alias('PHPUnit\Framework\TestListener', 'TestListener');
    class_alias('PHPUnit\Framework\Test', 'Test');
    class_alias('PHPUnit\Framework\TestSuite', 'TestSuite');
    class_alias('PHPUnit\Framework\TestCase', 'TestCase');
    class_alias('PHPUnit\Framework\Warning', 'Warning');
    class_alias('PHPUnit\Framework\AssertionFailedError', 'AssertionFailedError');
    class_alias('PHPUnit\Framework\ExpectationFailedException', 'ExpectationFailedException');
    class_alias('PHPUnit\TextUI\ResultPrinter', 'TextPrinter');
    class_alias('PHPUnit\Framework\Error\Error', 'Error_Error');
    class_alias('PHPUnit\Framework\Error\Warning', 'Error_Warning');
    class_alias('PHPUnit\Framework\Error\Notice', 'Error_Notice');
    class_alias('PHPUnit\Framework\Error\Deprecated', 'Error_Deprecated');
    list ($version) = explode('.', PHPUnit\Runner\Version::id());

    define('_RUNNER_VERSION', (int) $version);
}
if (class_exists('PHP_Timer')) {
    class_alias('PHP_Timer', 'Timer');
} elseif (class_exists('SebastianBergmann\Timer\Timer')) {
    class_alias('SebastianBergmann\Timer\Timer', 'Timer');
} elseif (class_exists('PHPUnit\SebastianBergmann\Timer\Timer')) {
    class_alias('PHPUnit\SebastianBergmann\Timer\Timer', 'Timer');
}
if (_RUNNER_VERSION >= 9) {
    $logger = <<<'LOGGER'
    class PHPUnitLogger extends \PHPUnit\TextUI\DefaultResultPrinter implements TestListener, \PHPUnit\TextUI\ResultPrinter
    {
        protected $loggers = array();
        public function printResult(\PHPUnit\Framework\TestResult $result): void
        {
        }
    
        /**
         * Handler for 'start class' event.
         */
        protected function startClass(string $name): void
        {
            $this->write($this->currentTestClassPrettified . "\n");
        }
    
        /**
         * Handler for 'on test' event.
         */
        protected function onTest(string $name, bool $success = true): void
        {
            if ($success) {
                $this->write(' [x] ');
            } else {
                $this->write(' [ ] ');
            }
    
            $this->write($name . "\n");
        }
    
        /**
         * Handler for 'end class' event.
         */
        protected function endClass(string $name): void
        {
            $this->write("\n");
        }
        public function __construct($out = null)
        {
            parent::__construct('php://stdout', true);
            class_alias('PHPUnit\Framework\ExceptionWrapper', 'ExceptionWrapper');
            class_alias('PHPUnit\Util\Blacklist', 'Blacklist');
            $this->loggers = array(
                new PHPUnitEclipseLogger()
            );
        }
        
        public function setAutoFlush(bool $autoFlush): void
        {
            parent::setAutoFlush($autoFlush);
            foreach ($this->loggers as $logger) {
                $logger->setAutoFlush($autoFlush);
            }
        }
        
        public function flush(): void
        {
            parent::flush();
            foreach ($this->loggers as $logger) {
                $logger->flush();
            }
        }
        
        public function incrementalFlush(): void
        {
            parent::incrementalFlush();
            foreach ($this->loggers as $logger) {
                $logger->incrementalFlush();
            }
        }
        
        public function addError(Test $test, Throwable $t, float $time): void
        {
            parent::addError($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addError($test, $t, $time);
            }
        }
        
        public function addWarning(Test $test, Warning $e, float $time): void
        {
            parent::addWarning($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addWarning($test, $e, $time);
            }
        }
        
        public function addFailure(Test $test, AssertionFailedError $e, float $time): void
        {
            parent::addFailure($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addFailure($test, $e, $time);
            }
        }
        
        public function addIncompleteTest(Test $test, Throwable $t, float $time): void
        {
            parent::addIncompleteTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addIncompleteTest($test, $t, $time);
            }
        }
        
        public function addRiskyTest(Test $test, Throwable $t, float $time): void
        {
            parent::addRiskyTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addRiskyTest($test, $t, $time);
            }
        }
        
        public function addSkippedTest(Test $test, Throwable $t, float $time): void
        {
            parent::addSkippedTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addSkippedTest($test, $t, $time);
            }
        }
        
        public function startTestSuite(TestSuite $suite): void
        {
            parent::startTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->startTestSuite($suite);
            }
        }
        
        public function endTestSuite(TestSuite $suite): void
        {
            parent::endTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->endTestSuite($suite);
            }
        }
        
        public function startTest(Test $test): void
        {
            parent::startTest($test);
        
            foreach ($this->loggers as $logger) {
                $logger->startTest($test);
            }
        }
        
        public function endTest(Test $test, float $time): void
        {
            parent::endTest($test, $time);
            foreach ($this->loggers as $logger) {
                $logger->endTest($test, $time);
            }
        }
    }
LOGGER;
} elseif (_RUNNER_VERSION >= 7) {
    $logger = <<<'LOGGER'
    class PHPUnitLogger extends TextPrinter implements TestListener
    {
        protected $loggers = array();
        public function __construct($out = null)
        {
            parent::__construct('php://stdout', true);
            class_alias('PHPUnit\Framework\ExceptionWrapper', 'ExceptionWrapper');
            class_alias('PHPUnit\Util\Blacklist', 'Blacklist');
            $this->loggers = array(
                new PHPUnitEclipseLogger()
            );
        }
        
        public function setAutoFlush(bool $autoFlush): void
        {
            parent::setAutoFlush($autoFlush);
            foreach ($this->loggers as $logger) {
                $logger->setAutoFlush($autoFlush);
            }
        }
        
        public function flush(): void
        {
            parent::flush();
            foreach ($this->loggers as $logger) {
                $logger->flush();
            }
        }
        
        public function incrementalFlush(): void
        {
            parent::incrementalFlush();
            foreach ($this->loggers as $logger) {
                $logger->incrementalFlush();
            }
        }
        
        public function addError(Test $test, Throwable $t, float $time): void
        {
            parent::addError($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addError($test, $t, $time);
            }
        }
        
        public function addWarning(Test $test, Warning $e, float $time): void
        {
            parent::addWarning($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addWarning($test, $e, $time);
            }
        }
        
        public function addFailure(Test $test, AssertionFailedError $e, float $time): void
        {
            parent::addFailure($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addFailure($test, $e, $time);
            }
        }
        
        public function addIncompleteTest(Test $test, Throwable $t, float $time): void
        {
            parent::addIncompleteTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addIncompleteTest($test, $t, $time);
            }
        }
        
        public function addRiskyTest(Test $test, Throwable $t, float $time): void
        {
            parent::addRiskyTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addRiskyTest($test, $t, $time);
            }
        }
        
        public function addSkippedTest(Test $test, Throwable $t, float $time): void
        {
            parent::addSkippedTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addSkippedTest($test, $t, $time);
            }
        }
        
        public function startTestSuite(TestSuite $suite): void
        {
            parent::startTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->startTestSuite($suite);
            }
        }
        
        public function endTestSuite(TestSuite $suite): void
        {
            parent::endTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->endTestSuite($suite);
            }
        }
        
        public function startTest(Test $test): void
        {
            parent::startTest($test);
            
            foreach ($this->loggers as $logger) {
                $logger->startTest($test);
            }
        }
        
        public function endTest(Test $test, float $time): void
        {
            parent::endTest($test, $time);
            foreach ($this->loggers as $logger) {
                $logger->endTest($test, $time);
            }
        }
    }
LOGGER;
} elseif (_RUNNER_VERSION == 6) {
    $logger = <<<'LOGGER'
    class PHPUnitLogger extends TextPrinter implements TestListener
    {
        protected $loggers = array();
        public function __construct($out = null)
        {
            parent::__construct('php://stdout', true);
            
            class_alias('PHPUnit\Framework\ExceptionWrapper', 'ExceptionWrapper');
            class_alias('PHPUnit\Util\Blacklist', 'Blacklist');
            
            $this->loggers = array(
                new PHPUnitEclipseLogger()
            );
        }
        
        public function setAutoFlush($autoFlush)
        {
            parent::setAutoFlush($autoFlush);
            foreach ($this->loggers as $logger) {
                $logger->setAutoFlush($autoFlush);
            }
        }
        
        public function flush()
        {
            parent::flush();
            foreach ($this->loggers as $logger) {
                $logger->flush();
            }
        }
        
        public function incrementalFlush()
        {
            parent::incrementalFlush();
            foreach ($this->loggers as $logger) {
                $logger->incrementalFlush();
            }
        }
        
        public function addError(Test $test, Exception $t, $time)
        {
            parent::addError($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addError($test, $t, $time);
            }
        }
        
        public function addWarning(Test $test, Warning $e, $time)
        {
            parent::addWarning($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addWarning($test, $e, $time);
            }
        }
        
        public function addFailure(Test $test, AssertionFailedError $e, $time)
        {
            parent::addFailure($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addFailure($test, $e, $time);
            }
        }
        
        public function addIncompleteTest(Test $test, Exception $t, $time)
        {
            parent::addIncompleteTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addIncompleteTest($test, $t, $time);
            }
        }
        
        public function addRiskyTest(Test $test, Exception $t, $time)
        {
            parent::addRiskyTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addRiskyTest($test, $t, $time);
            }
        }
        
        public function addSkippedTest(Test $test, Exception $t, $time)
        {
            parent::addSkippedTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addSkippedTest($test, $t, $time);
            }
        }
        
        public function startTestSuite(TestSuite $suite)
        {
            parent::startTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->startTestSuite($suite);
            }
        }
        
        public function endTestSuite(TestSuite $suite)
        {
            parent::endTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->endTestSuite($suite);
            }
        }
        
        public function startTest(Test $test)
        {
            parent::startTest($test);
            
            foreach ($this->loggers as $logger) {
                $logger->startTest($test);
            }
        }
        
        public function endTest(Test $test, $time)
        {
            parent::endTest($test, $time);
            foreach ($this->loggers as $logger) {
                $logger->endTest($test, $time);
            }
        }
    }
LOGGER;
} else {
    $logger = <<<'LOGGER'
    class PHPUnitLogger extends TextPrinter implements TestListener
    {
        protected $loggers = array();
        public function __construct($out = null)
        {
            parent::__construct('php://stdout', true);
            if (class_exists('PHPUnit_Framework_ExceptionWrapper')) {
                class_alias('PHPUnit_Framework_ExceptionWrapper', 'ExceptionWrapper');
            }
            $this->loggers = array(
                new PHPUnitEclipseLogger()
            );
        }
        
        public function setAutoFlush($autoFlush)
        {
            parent::setAutoFlush($autoFlush);
            foreach ($this->loggers as $logger) {
                $logger->setAutoFlush($autoFlush);
            }
        }
        
        public function flush()
        {
            parent::flush();
            foreach ($this->loggers as $logger) {
                $logger->flush();
            }
        }
        
        public function incrementalFlush()
        {
            parent::incrementalFlush();
            foreach ($this->loggers as $logger) {
                $logger->incrementalFlush();
            }
        }
        
        public function addError(Test $test, Exception $t, $time)
        {
            parent::addError($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addError($test, $t, $time);
            }
        }
        
        public function addWarning(Test $test, Warning $e, $time)
        {
            parent::addWarning($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addWarning($test, $e, $time);
            }
        }
        
        public function addFailure(Test $test, AssertionFailedError $e, $time)
        {
            parent::addFailure($test, $e, $time);
            foreach ($this->loggers as $logger) {
                $logger->addFailure($test, $e, $time);
            }
        }
        
        public function addIncompleteTest(Test $test, Exception $t, $time)
        {
            parent::addIncompleteTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addIncompleteTest($test, $t, $time);
            }
        }
        
        public function addRiskyTest(Test $test, Exception $t, $time)
        {
            parent::addRiskyTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addRiskyTest($test, $t, $time);
            }
        }
        
        public function addSkippedTest(Test $test, Exception $t, $time)
        {
            parent::addSkippedTest($test, $t, $time);
            foreach ($this->loggers as $logger) {
                $logger->addSkippedTest($test, $t, $time);
            }
        }
        
        public function startTestSuite(TestSuite $suite)
        {
            parent::startTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->startTestSuite($suite);
            }
        }
        
        public function endTestSuite(TestSuite $suite)
        {
            parent::endTestSuite($suite);
            foreach ($this->loggers as $logger) {
                $logger->endTestSuite($suite);
            }
        }
        
        public function startTest(Test $test)
        {
            parent::startTest($test);
            
            foreach ($this->loggers as $logger) {
                $logger->startTest($test);
            }
        }
        
        public function endTest(Test $test, $time)
        {
            parent::endTest($test, $time);
            foreach ($this->loggers as $logger) {
                $logger->endTest($test, $time);
            }
        }
    }
LOGGER;
}
// eval is evil but we need it to avoid syntax errors
eval($logger);

class PHPUnitEclipseLogger
{

    private $status;

    private $exception;

    private $time;

    private $warnings;

    private $varx;

    private $out;

    /**
     * data provider support - enumerates the test cases
     */
    private $dataProviderNumerator = - 1;

    public function __construct()
    {
        $this->cleanTest();

        $port = isset($_SERVER['PHPUNIT_PORT']) ? $_SERVER['PHPUNIT_PORT'] : 7478;
        $this->out = fsockopen('127.0.0.1', $port, $errno, $errstr, 5);
    }

    public function startTestSuite(TestSuite $suite)
    {
        $this->writeTest($suite, 'start');
    }

    public function startTest(Test $test)
    {
        ZendPHPUnitErrorHandlerTracer::getInstance()->start($test);
        $this->cleanTest();
        $this->writeTest($test, 'start', true);
    }

    public function addError(Test $test, $e, $time)
    {
        $this->status = 'error';
        $this->exception = $e;
    }

    public function addWarning(Test $test, Warning $e, $time)
    {
        $this->status = 'warning';
        $this->exception = $e;
    }

    public function addFailure(Test $test, AssertionFailedError $e, $time)
    {
        $this->status = 'fail';
        $this->exception = $e;
    }

    public function addIncompleteTest(Test $test, $e, $time)
    {
        $this->status = 'incomplete';
        $this->exception = $e;
    }

    public function addSkippedTest(Test $test, $e, $time)
    {
        $this->status = 'skip';
        $this->exception = $e;
    }

    public function endTest(Test $test, $time)
    {
        $this->warnings = ZendPHPUnitErrorHandlerTracer::getInstance()->stop();
        $this->time = $time;
        $this->writeTest($test, $this->status);
    }

    public function endTestSuite(TestSuite $suite)
    {
        $this->writeTest($suite, 'end');
    }

    public function addRiskyTest(Test $test, $e, $time)
    {}

    public function flush()
    {}

    public function getWrappedTrace($e)
    {
        if ($e->getPrevious() != null) {
            return $this->getWrappedTrace($e->getPrevious());
        }
        if (class_exists('ExceptionWrapper') && $e instanceof ExceptionWrapper) {
            return $e->getSerializableTrace();
        }
        return $e->getTrace();
    }

    public function getWrappedName($e)
    {
        if ($e->getPrevious() != null) {
            return $this->getWrappedName($e->getPrevious());
        }
        if (class_exists('ExceptionWrapper') && $e instanceof ExceptionWrapper) {
            return $e->getClassName();
        }
        return get_class($e);
    }

    private function cleanTest()
    {
        $this->status = 'pass';
        $this->exception = null;
        $this->warnings = array();
        $this->time = 0;
    }

    private function writeArray($array)
    {
        $result = $this->writeJson($this->encodeJson($array));
        return $result;
    }

    private function writeTest(Test $test, $event, $isTestCase = false)
    {
        // echo out test output
        if ($test instanceof TestCase) {
            $hasPerformed = false;
            if (method_exists($test, 'hasPerformedExpectationsOnOutput')) {
                $hasPerformed = $test->hasPerformedExpectationsOnOutput();
            } else {
                $hasPerformed = $test->hasExpectationOnOutput();
            }

            if (! $hasPerformed && $test->getActualOutput() != null) {
                // echo $test->getActualOutput();
            }
        }

        // write log
        $result = array(
            'event' => $event
        );
        if ($test instanceof TestSuite) {
            if ($isTestCase) { // skip test suite called inside startTestCase
                return;
            }
            if (preg_match("*::*", $test->getName()) != 0) { // if it is a dataprovider test suite
                                                             // $result['target'] = 'testsuite-dataprovider';
                $result['target'] = 'testsuite';
                if ($event == 'start')
                    $this->dataProviderNumerator = 0;
                elseif ($event == 'end')
                    $this->dataProviderNumerator = - 1;

                try {
                    $ex = explode('::', $test->getName(), 2);
                    $class = new ReflectionClass($ex[0]);
                    $name = $test->getName();
                    $file = $class->getFileName();
                    $line = $class->getStartLine();
                    $result['test'] = array(
                        'name' => $name,
                        'tests' => $test->count(),
                        'file' => $file,
                        'line' => $line
                    );

                    $method = $class->getMethod($ex[1]);
                    $result['test']['line'] = $method->getStartLine();
                } catch (ReflectionException $re) {
                    $name = $test->getName();
                    $result['test'] = array(
                        'name' => $name,
                        'tests' => $test->count()
                    );
                }
            } else {
                $result['target'] = 'testsuite';
                $this->dataProviderNumerator = - 1;
                try {
                    $class = new ReflectionClass($test->getName());
                    $name = $class->getName();
                    $file = $class->getFileName();
                    $line = $class->getStartLine();
                    $result['test'] = array(
                        'name' => $name,
                        'tests' => $test->count(),
                        'file' => $file,
                        'line' => $line
                    );
                } catch (ReflectionException $re) {
                    $name = $test->getName();
                    $result['test'] = array(
                        'name' => $name,
                        'tests' => $test->count()
                    );
                }
            }
        } else { // If we're dealing with TestCase
            $result['target'] = 'testcase';
            $result['time'] = $this->time;
            $class = new ReflectionClass($test);
            try {
                $method = $class->getMethod($test->getName());
                if ($this->dataProviderNumerator < 0) {
                    $method_name = $method->getName();
                } else {
                    $method_name = $method->getName() . "[" . $this->dataProviderNumerator . "]";
                    if ($event == 'start') {
                        $this->dataProviderNumerator ++;
                    }
                }
                $result['test'] = array(
                    'name' => $method_name,
                    'file' => $method->getFileName(),
                    'line' => $method->getStartLine()
                );
            } catch (ReflectionException $re) {
                $result['test'] = array(
                    'name' => $test->getName()
                );
            }
        }
        if ($this->exception !== null) {
            $message = $this->exception->getMessage();
            $diff = "";
            if ($this->exception instanceof ExpectationFailedException) {
                if (method_exists($this->exception, "getDescription")) {
                    $message = $this->exception->getDescription();
                } else if (method_exists($this->exception, "getMessage")) { // PHPUnit 3.6.3
                    $message = $this->exception->getMessage();
                }
                if (method_exists($this->exception, "getComparisonFailure") && $this->exception->getComparisonFailure() != null && method_exists($this->exception->getComparisonFailure(), "getDiff")) {
                    $diff = $this->exception->getComparisonFailure()->getDiff();
                }
            }
            $message = trim(preg_replace('/\s+/m', ' ', $message));
            $result += array(
                'exception' => array(
                    'message' => $message,
                    'diff' => $diff,
                    'class' => $this->getWrappedName($this->exception),
                    'file' => $this->exception->getFile(),
                    'line' => $this->exception->getLine(),
                    'trace' => filterTrace($this->getWrappedTrace($this->exception))
                )
            );
            if (! isset($result['exception']['file'])) {
                $result['exception']['filtered'] = true;
            }
        }
        if (! empty($this->warnings)) {
            $result += array(
                'warnings' => $this->warnings
            );
        }
        if (! $this->writeArray($result)) {
            die();
        }
    }

    private function writeJson($buffer)
    {
        if ($this->out && ! @feof($this->out)) {
            return @fwrite($this->out, "$buffer\n");
        }
    }

    private function escapeString($string)
    {
        return str_replace(array(
            "\\",
            "\"",
            '/',
            "\b",
            "\f",
            "\n",
            "\r",
            "\t"
        ), array(
            '\\\\',
            '\"',
            '\/',
            '\b',
            '\f',
            '\n',
            '\r',
            '\t'
        ), $string);
    }

    private function encodeJson($array)
    {
        $result = '';
        if (is_scalar($array))
            $array = array(
                $array
            );
        $first = true;
        foreach ($array as $key => $value) {
            if (! $first)
                $result .= ',';
            else
                $first = false;
            $result .= sprintf('"%s":', $this->escapeString($key));
            if (is_array($value) || is_object($value))
                $result .= sprintf('%s', $this->encodeJson($value));
            else
                $result .= sprintf('"%s"', $this->escapeString($value));
        }
        return '{' . $result . '}';
    }
}

class ZendPHPUnitErrorHandlerTracer extends ZendPHPUnitErrorHandler
{

    private static $ZendPHPUnitErrorHandlerTracer;

    /**
     *
     * @return ZendPHPUnitErrorHandlerTracer
     */
    public static function getInstance()
    {
        if (self::$ZendPHPUnitErrorHandlerTracer === null) {
            self::$ZendPHPUnitErrorHandlerTracer = new self();
        }
        return self::$ZendPHPUnitErrorHandlerTracer;
    }

    public static $errorCodes = array(
        E_ERROR => 'Error',
        E_WARNING => 'Warning',
        E_PARSE => 'Parsing Error',
        E_NOTICE => 'Notice',
        E_CORE_ERROR => 'Core Error',
        E_CORE_WARNING => 'Core Warning',
        E_COMPILE_ERROR => 'Compile Error',
        E_COMPILE_WARNING => 'Compile Warning',
        E_USER_ERROR => 'User Error',
        E_USER_WARNING => 'User Warning',
        E_USER_NOTICE => 'User Notice',
        E_STRICT => 'Runtime Notice',
        E_RECOVERABLE_ERROR => 'Recoverable Error',
        E_DEPRECATED => 'Deprecated',
        E_USER_DEPRECATED => 'User Deprecated'
    );

    protected $warnings;

    public function handle($errno, $errstr, $errfile, $errline)
    {
        parent::handle($errno, $errstr, $errfile, $errline);
        $warning = array(
            'code' => isset(self::$errorCodes[$errno]) ? self::$errorCodes[$errno] : $errno,
            'message' => $errstr,
            'file' => $errfile,
            'line' => $errline,
            'trace' => filterTrace(debug_backtrace()),
            'time' => is_callable(['Timer', 'resourceUsage']) ? Timer::resourceUsage() : ''
        );
        $return = false;
        switch ($errno) { // ignoring user abort
            case E_USER_ERROR:
            case E_RECOVERABLE_ERROR:
                throw new ZendPHPUnitUserErrorException($warning['message'], $errno);
        }
        $this->warnings[] = $warning;
        return $return;
    }

    public function start(Test $test)
    {
        $this->warnings = array();
        parent::start($test);
    }

    public function stop()
    {
        parent::stop();
        $return = $this->warnings;
        $this->warnings = array();
        return $return;
    }
}

class ZendPHPUnitErrorHandler
{

    private static $ZendPHPUnitErrorHandler;

    private $convertErrors = false;

    private $convertNotices = false;

    private $convertDeprecations = false;

    private $convertWarnings = false;

    private $test;

    /**
     *
     * @return ZendPHPUnitErrorHandler
     */
    public static function getInstance()
    {
        if (self::$ZendPHPUnitErrorHandler === null) {
            self::$ZendPHPUnitErrorHandler = new self();
        }
        return self::$ZendPHPUnitErrorHandler;
    }

    public function handle($errno, $errstr, $errfile, $errline)
    {
        if (! ($errno & error_reporting())) {
            return false;
        }
        // handle errors same as PHPUnit_Util_ErrorHandler
        if ($errfile === __FILE__ || (stripos($errfile, dirname(dirname(__FILE__))) === 0 && $errno !== E_USER_NOTICE)) {
            return true;
        } elseif ($errno === E_NOTICE || $errno === E_USER_NOTICE || $errno === E_STRICT) {
            if (! $this->convertNotices) {
                return false;
            }
            $exception = 'Error_Notice';
        } elseif ($errno == E_WARNING || $errno == E_USER_WARNING) {
            if (! $this->convertWarnings) {
                return FALSE;
            }

            $exception = 'Error_Warning';
        } elseif ($errno == E_NOTICE) {
            return FALSE;
        } elseif ($errno === E_DEPRECATED || $errno === E_USER_DEPRECATED) {
            if (! $this->convertDeprecations) {
                return false;
            }
            $exception = 'Error_Deprecated';
        } else {
            if (! $this->convertErrors) {
                return false;
            }
            $exception = 'Error_Error';
        }

        throw new $exception($errstr, $errno, $errfile, $errline);
    }

    public function start(Test $test)
    {
        $this->test = $test;
        set_error_handler(array(
            &$this,
            'handle'
        ));
        $ref = new ReflectionClass('Error_Notice');
        if ($ref->hasProperty('enabled')) {
            $this->convertErrors = true;
            $this->convertWarnings = Error_Warning::$enabled === true;
            $this->convertNotices = Error_Notice::$enabled === true;
            $this->convertDeprecations = Error_Deprecated::$enabled === true;
        } elseif ($test->getTestResultObject() != null) {
            $this->convertErrors = $test->getTestResultObject()->getConvertErrorsToExceptions();
            $this->convertWarnings = $test->getTestResultObject()->getConvertWarningsToExceptions();
            $this->convertNotices = $test->getTestResultObject()->getConvertNoticesToExceptions();
            $this->convertDeprecations = $test->getTestResultObject()->getConvertDeprecationsToExceptions();
        }
    }

    public function stop()
    {
        $this->test = null;
        restore_error_handler();
    }
}

class ZendPHPUnitUserErrorException extends Exception
{
}

function filterTrace($trace)
{
    $filteredTrace = array();

    $blacklist = null;
    if (class_exists('Blacklist')) {
        $blacklist = new Blacklist();
    }
    $prefix = false;
    if (defined('__PHPUNIT_PHAR_ROOT__')) {
        $prefix = __PHPUNIT_PHAR_ROOT__;
    }
    $script = realpath($GLOBALS['_SERVER']['SCRIPT_NAME']);
    foreach ($trace as $frame) {
        if (! isset($frame['file'])) {
            if (isset($frame['class']) && isset($frame['function'])) {
                try {
                    $class = new ReflectionClass($frame['class']);
                    $frame['file'] = $class->getFileName();
                    $method = $class->getMethod($frame['function']);
                    if (! isset($frame['line'])) {
                        $frame['line'] = $method->getStartLine();
                    }
                } catch (ReflectionException $re) {
                    continue;
                }
            }
        }
        if (($blacklist && $blacklist->isBlacklisted($frame['file'])) || ! ($prefix === false || strpos($frame['file'], $prefix) !== 0) || ! is_file($frame['file']) || $frame['file'] === $script) {
            continue;
        }
        $filteredFrame = array(
            'file' => $frame['file'],
            'line' => $frame['line'],
            'function' => $frame['function']
        );
        if (isset($frame['class'])) {
            $filteredFrame += array(
                'class' => $frame['class'],
                'type' => $frame['type']
            );
            $filteredTrace[] = $filteredFrame;
        }
    }
    return $filteredTrace;
}
