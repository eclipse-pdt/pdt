<?php
class Person {
	// class and instance-object attributes	private $id;
	private $firstName;
	private $lastName;
	private $age;
	private $gender;
	private static $personId = 1;
	public static $personCount = 0;
	// constractor	function __construct($newFirstName, $newLastName, $newAge, $newGender) {
		$this->id = Person::$personId;
		$this->setFirstName ( $newFirstName );
		$this->setLastName ( $newLastName );
		$this->setAge ( $newAge );
		$this->setGender ( $newGender );
		Person::$personId ++;
		Person::$personCount ++;
	}
	// class methods	function getId() {
		return $this->id;
	}
	function setFirstName($newFirstName) {
		if (strlen ( $newFirstName ) > 0)
			$this->firstName = $newFirstName; else
			$this->firstName = "First name must be longer then zero !!!";
	}
	function getFirstName() {
		return $this->firstName;
	}
	function setLastName($newLastName) {
		if (strlen ( $newLastName ) > 0)
			$this->lastName = $newLastName; else
			$this->lastName = "Last name must be longer then zero !!!";
	}
	function getLastName() {
		return $this->lastName;
	}
	function setAge($newAge) {
		if ($newAge > 1)
			$this->age = $newAge; else
			$this->age = "Age must be greater then 1 year";
	}
	function getAge() {
		return $this->age;
	}
	function setGender($newGender) {
		if ($newGender == "male" || $newGender == "female")
			$this->gender = $newGender; else
			$this->gender = "Gender must be male or female";
	}
	function getGender() {
		return $this->gender;
	}
	function printData() {
		echo ("<hr><b>Id:</b>" . $this->getId () . "<br><b> First Name:</b>" . $this->getFirstName () . "<br><b> Last Name:</b>" . $this->getLastName () . "<br><b> Age:</b>" . $this->getAge () . "<br><b> Gender:</b>" . $this->getGender ());
	}

} // end class?>