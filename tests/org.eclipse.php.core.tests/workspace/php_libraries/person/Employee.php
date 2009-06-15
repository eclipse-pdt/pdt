<?php
include_once 'Person.php';
class Employee extends Person {
	private $empId;
	private $salary;
	private $dept;
	private static $employeeId = 1;
	
	// costractors	public function __construct($newFirstName, $newLastName, $newAge, $newGender, $newSalary, $newDept) {
		parent::__construct ( $newFirstName, $newLastName, $newAge, $newGender );
		$this->empId = Employee::$employeeId;
		$this->setSalary ( $newSalary );
		$this->setDept ( $newDept );
		Employee::$employeeId ++;
	}
	
	// methods	public function getEmpId() {
		return $this->empId;
	}
	public function setSalary($newSalary) {
		if ($newSalary > 2500)
			$this->salary = $newSalary; else
			$this->salary = "Minimum salary is 2500 NIS !!";
	}
	public function getSalary() {
		return $this->salary;
	}
	public function setDept($newDept) {
		if ($newDept > 1)
			$this->dept = $newDept; else
			$this->dept = "Department Doesn't exist !!";
	}
	public function getDept() {
		return $this->dept;
	}
	public function printData() {
		parent::printData ();
		echo ("<br><b>Emp Id:</b>" . $this->getEmpId () . "<br><b>Salary:</b>" . $this->getSalary () . "<br><b>Works in Department:</b>" . $this->getDept ());
	}
} 
