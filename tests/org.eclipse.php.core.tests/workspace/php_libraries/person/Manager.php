<?php
include_once 'Employee.php';
class Manager extends Employee {
	private $carType;
	//constractors
	public function __construct($newFirstName, $newLastName, $newAge, $newGender, $newSalary, $newDept, $newCarType) {
		parent::__construct ( $newFirstName, $newLastName, $newAge, $newGender, $newSalary, $newDept );
		$this->setCarType ( $newCarType );
	}
	
	//Methods
	public function setCarType($newCarType) {
		if (strlen ( $newCarType ) > 2)
			$this->carType = $newCarType; else
			$this->carType = ("Not A known Car!");
	}
	public function getCarType() {
		return $this->carType;
	}
	
	public function printData() {
		parent::printData ();
		echo ("<br><b>Car type:</b>" + $this->getCarType ());
	}
}
?>