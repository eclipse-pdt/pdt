<?php
include_once 'Employee.php';
class Manager extends Employee {
	private $carType;
	//constractors
	function __construct($newFirstName, $newLastName, $newAge, $newGender, $newSalary, $newDept, $newCarType) {
		parent::__construct ( $newFirstName, $newLastName, $newAge, $newGender, $newSalary, $newDept );
		$this->setCarType ( $newCarType );
	}
	
	//Methods
	function setCarType($newCarType) {
		if (strlen ( $newCarType ) > 2)
			$this->carType = $newCarType; else
			$this->carType = ("Not A known Car!");
	}
	function getCarType() {
		return $this->carType;
	}
	
	function printData() {
		parent::printData ();
		echo ("<br><b>Car type:</b>" + $this->getCarType ());
	}

}
?>