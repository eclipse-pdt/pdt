<?php
include_once 'Person.php';

class Customer extends Person {
	private $custId;
	private $creditType;
	private $cardNum;
	private static $customerId = 1;
	
	/**
	 * @return unknown
	 */
	public static function getPersonCount() {
		return $this->personCount;
	}
	
	/**
	 * @param unknown_type $personCount
	 */
	public static function setPersonCount($personCount) {
		$this->personCount = $personCount;
	}
	
	// constructor
	public function __construct($newFirstName, $newLastName, $newAge, $newGender, $newCreditType, $newCardNum) {
		parent::__construct ( $newFirstName, $newLastName, $newAge, $newGender );
		$this->custId = Customer::$customerId;
		$this->setCreditType ( $newCreditType );
		$this->setCardNum ( $newCardNum );
		Customer::$customerId ++;
	}
	//methods
	public function getCustId() {
		return $this->custId;
	}
	public function setCreditType($newCreditType) {
		if ($newCreditType == "Visa" || $newCreditType == "MasterCard")
			$this->creditType = $newCreditType; else
			$this->creditType = "Only Visa or MasterCard Exepted!";
	}
	public function getCreditType() {
		return $this->creditType;
	}
	public function setCardNum($newCardNum) {
		if (strlen ( $newCardNum ) == 8)
			$this->cardNum = $newCardNum; else
			$this->cardNum = ("Only 8 digits Card Number!");
	}
	public function getCardNum() {
		return $this->cardNum;
	}
	public function printData() {
		parent::printData ();
		echo ("<br><b>Custumer Id:</b>" . $this->getCustId () . "<br><b>Card Type:</b>" . $this->getCreditType () . "<br><b>Card No:</b>" . $this->getCardNum ());
	}
} 