package varelim;

import java.util.ArrayList;

/**
 * Class to represent the Table Object consisting of probability rows
 * 
 * @author Marcel de Korte, Moira Berens, Djamari Oetringer, Abdullahi Ali, Leonieke van den Bulk
 */

public class Table {

	private ArrayList<ProbRow> TABLE;
	private Variable NODE;
	private ArrayList<Variable> PARENTS;
	
	/**
	 * Constructor of the class.
	 * @param table made out of probability rows (ProbRows)
	 * @param node belonging to the current probability table
	 * @param parents belonging to the current probability table
	 */
	public Table(ArrayList<ProbRow> table, Variable node, ArrayList<Variable> parents) {
		this.TABLE = table;
		this.NODE = node;
		this.PARENTS = parents;
	}
        
        public Table(ArrayList<ProbRow> table, ArrayList<Variable> parents) {
            this.TABLE = table;
            this.PARENTS = parents;
        }

	/**
	 * Getter of the table made out of ProbRows
	 * @return table
	 */
	public ArrayList<ProbRow> getTable() {
		return TABLE;
	}
	
	 /**
	 * Getter of the node that belongs to the probability table
	 * @return the node
	 */
	public Variable getNode() {
		return NODE;
	}

	/**
	 * Getter of the parents that belong to the probability table
	 * @return the parents
	 */
	public ArrayList<Variable> getParents() {
		return PARENTS;
	}

	/**
	  * Gets the i'th element from the ArrayList of ProbRows
	  * @param i index
	  * @return i'th ProbRow in Table
	  */
	public ProbRow get(int i) {
		return TABLE.get(i);
	}
	
	/**
	 * Returns the size of the Table (amount of probability rows)
	 * @return size of Table
	 */
	public int size() {
		return TABLE.size();
	}
}
