<?php
/*namespace:*/namespace #namespace_name#;
/*:namespace*/
/*php_content:*/
/*requires:*/require_once('#require_location#');
/*:requires*/
/*use:*/use #use_full_name#;
/*:use*/
/*class:*/
/*namespace_in_file:*/namespace #namespace_name#;
/*:namespace_in_file*/
/*requires_in_file:*/require_once('#require_location#');
/*:requires_in_file*/
/*use_in_file:*/use #use_full_name#;
/*:use_in_file*/
#default_phpdoc#
#abstract_var# #final_var# #element_type# #class_name# /*superclass:*/extends #superclass_name#/*:superclass*/ /*implements:*/implements /*interfaces:*/#interface_name#/*:interfaces*//*:implements*/{
  #todo_text#
  /*usetrait:*/use /*traits:*/#trait_name#/*:traits*/{
  
  }/*:usetrait*/
  /*functions:*/
  /*func_phpdoc:*/
  /**
   /*func_phpdoc_params:*/*#func_phpdoc_param# 
   /*:func_phpdoc_params*/ #func_phpdoc_see#*//*:func_phpdoc*/
  #func_modifier# #func_static_modifier# function #func_name#(/*func_params:*/#func_param_name#/*:func_params*/) {
      #func_parent_call#
      #todo_text#
  }/*:functions*/
}
/*:class*/
/*:php_content*/