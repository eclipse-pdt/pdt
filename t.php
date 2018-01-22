<?php

class F {
  public function F() { echo 'F'; }
  public function __construct() { echo 'X'; }
}

new F();
