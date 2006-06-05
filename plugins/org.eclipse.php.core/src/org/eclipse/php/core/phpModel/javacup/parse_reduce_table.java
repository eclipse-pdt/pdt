/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.phpModel.javacup;



/** This class represents the complete "reduce-goto" table of the parser.
 *  It has one row for each state in the parse machines, and a column for
 *  each terminal symbol.  Each entry contains a state number to shift to
 *  as the last step of a reduce. 
 *
 * @see     java_cup.parse_reduce_row
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public class parse_reduce_table {
 
  /*-----------------------------------------------------------*/
  /*--- Constructor(s) ----------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Simple constructor.  Note: all terminals, non-terminals, and productions 
   *  must already have been entered, and the viable prefix recognizer should
   *  have been constructed before this is called.
   */
  public parse_reduce_table()
    {
      /* determine how many states we are working with */
      _num_states = lalr_state.number();

      /* allocate the array and fill it in with empty rows */
      under_state = new parse_reduce_row[_num_states];
      for (int i=0; i<_num_states; i++)
	under_state[i] = new parse_reduce_row();
    }

   
  /*-----------------------------------------------------------*/
  /*--- (Access to) Instance Variables ------------------------*/
  /*-----------------------------------------------------------*/

  /** How many rows/states in the machine/table. */
  protected int _num_states;

  /** How many rows/states in the machine/table. */
  public int num_states() {return _num_states;}

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Actual array of rows, one per state */
  public  parse_reduce_row[] under_state;
 
  /*-----------------------------------------------------------*/
  /*--- General Methods ---------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Convert to a string. */
  public String toString()
    {
      String result;
      lalr_state goto_st;
      int cnt;

      result = "-------- REDUCE_TABLE --------\n";
      for (int row = 0; row < num_states(); row++)
	{
	  result += "From state #" + row + "\n";
	  cnt = 0;
	  for (int col = 0; col < under_state[row].size(); col++)
	    {
	      /* pull out the table entry */
	      goto_st = under_state[row].under_non_term[col];

	      /* if it has action in it, print it */
	      if (goto_st != null)
		{
		  result += " [non term " + col + "->"; 
		  result += "state " + goto_st.index() + "]";

		  /* end the line after the 3rd one */
		  cnt++;
		  if (cnt == 3)
		    {
		      result += "\n";
		      cnt = 0;
		    }
		}
	    }
          /* finish the line if we haven't just done that */
	  if (cnt != 0) result += "\n";
	}
      result += "-----------------------------";

      return result;
    }

  /*-----------------------------------------------------------*/

}

