package varelim;

import java.util.ArrayList;
/**
 * Represents a row of probabilities
 * 
 * @author Marcel de Korte, Moira Berens, Djamari Oetringer, Abdullahi Ali, Leonieke van den Bulk
 */
public class ProbRow {
	private double PROB;
	private ArrayList<String> VALUES;
	private Variable NODE;
	private ArrayList<Variable> PARENTS;

	/**
	 * Constructor
	 * @param node a variable 
	 * @param prob probability belonging to this row of values of the node
	 * @param values values of all the variables (node+parents) in the row, of which the value of the node itself is always last
	 * @param parents the parent variables
	 */
	public ProbRow(Variable node, double prob, ArrayList<String> values, ArrayList<Variable> parents) {
		this.PROB = prob;
		this.VALUES = values;
		this.NODE = node;
		this.PARENTS = parents;
	}
        
        public ProbRow(double prob, ArrayList<String> values) {
            this.PROB = prob;
            this.VALUES = values;
        }

	/**
	 * Check whether two have ProbRows have the same parents
	 * @param pr a probability row
         * @param v - variable which is to be eliminated
	 * @return True if this probRow and pr have the same parents
	 */
        public boolean sameParentsValues(ProbRow pr, Variable v) {
            this.VALUES.remove(this.PARENTS.indexOf(v));
            pr.VALUES.remove(this.PARENTS.indexOf(v));
            return this.VALUES.equals(pr.VALUES);
        }

	/**
	 * Getter of the probability
	 * @return the probability of the node.
	 */
	public double getProb() {
		return PROB;
	}

	/**
	 * Getter of the node.
	 * @return node given the probabilities.
	 */
	public Variable getNode() {
		return NODE;
	}
	
	/**
	 * Getter of the values.
	 * @return 'ArrayList of String' of values of the probability row
	 */
	public ArrayList<String> getValues() {
		return VALUES;
	}
	
	/**
	 * Transform probabilities to string.
         * @return toString 
	 */
        @Override
	public String toString() {
		String valuesString = "";
		for(int i = 0; i < VALUES.size()-1; i++){
			valuesString = valuesString + VALUES.get(i) + ", ";
		}
		valuesString = valuesString + VALUES.get(VALUES.size()-1);
		return valuesString + " | " + Double.toString(PROB);
	}
	
	/**
	 * Getter of the parents.
	 * @return the parents of the node given the probabilities.
	 */
	public ArrayList<Variable> getParents(){
		return PARENTS;
	}

    public ProbRow multiply(ProbRow otherRow, ArrayList<Variable> headers, ArrayList<Variable> otherHeaders) {
        ArrayList<Variable> newParents = new ArrayList();
        ArrayList<Integer> columnsToBeRemoved = new ArrayList();
        
        double newProb = this.PROB * otherRow.PROB;

        /**
         * Check if some headers appear more than one time
         * and if so, store the column of the header
         */
        newParents.addAll(headers);
        for (Variable parent : otherHeaders) {
            if (!(newParents.contains(parent))) {
                newParents.add(parent);
            }
            else if (newParents.contains(parent)) {
                int column = headers.indexOf(parent);
                columnsToBeRemoved.add(column);
            } 
            else if (!newParents.contains(this.getNode())) {
                newParents.add(this.getNode());
            }
        }
        
        /**
         * Initialize all the values for the new table
         * and eliminate the columns that were superfluous
         */
        ArrayList<String> newValues = new ArrayList(otherRow.getValues());
        for (int column : columnsToBeRemoved) {
            newValues.remove(column);
        }
        newValues.addAll(this.VALUES);

        return new ProbRow(newProb, newValues);
    }
    
    public ProbRow addRow (ProbRow otherRow) {
        double newamount = this.getProb() + otherRow.getProb();
        return new ProbRow(newamount,this.getValues());
    }
}