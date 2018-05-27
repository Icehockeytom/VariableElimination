package varelim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class to read in a network, add queries and observed variables, and
 * eliminate variables.
 *
 * @author Marcel de Korte, Moira Berens, Djamari Oetringer, Abdullahi Ali,
 * Leonieke van den Bulk
 */
public class Main
{
    // The network to be read in ( format from http://www.bnlearn.com/bnrepository/ )

    private final static String NETWORK_NAME = "earthquake.bif";

    public static void main(String[] args) throws IOException
    {

        // Read in the network
        Networkreader reader = null;
        BufferedWriter writer = null;

            File logFile = new File("C://Users//Casper//Downloads//log.txt");
            writer = new BufferedWriter(new FileWriter(logFile));
            reader = new Networkreader(NETWORK_NAME, writer);
           
      
      
        // Get the variables and probabilities of the network
        ArrayList<Variable> Vs = reader.getVs();
        ArrayList<Table> Ps = reader.getPs();
        ArrayList<Table2> Ps2 = new ArrayList();
        for (Table t : Ps)
        {
            Ps2.add(new Table2(t, writer));
        }

        // Print variables and probabilities
        reader.printNetwork(Vs, Ps);

        // Ask user for query and heuristic
        // reader.askForQuery(); 
        // Turn this on if you want to experiment with different heuristics 
        // for bonus points (you need to implement the heuristics yourself)
        //reader.askForHeuristic();
        //String heuristic = reader.getHeuristic();
        // Variable Q = reader.getQueriedVariable(); 
        // Ask user for observed variables 
        reader.askForObservedVariables();
        ArrayList<Variable> O = reader.getObservedVariables();

        // Print the query and observed variables
        // reader.printQueryAndObserved(Q, O); 
        /**
         * Algorithm implementation for variable elimination
         *
         * @author Dennis den Hollander | s4776658
         * @author Tom Kamp | s4760921
         */
        // variableElimination(Vs, Ps, O, Q);
        Ps2 = reduceObserved(O.get(0), Ps2);
        Table2 t = Ps2.get(2).multiply(Ps2.get(0));
        t.eliminate(Vs.get(0));
        t = t.merge();
        System.out.println();
        for (ProbRow r : t.getTable())
        {
            System.out.println(r);
        }
                writer.close();    
    }

    public static ArrayList<Table2> reduceObserved(Variable v, ArrayList<Table2> Fs)
    {
        for (Table2 factor : Fs)
        {
            ArrayList<ProbRow> copyTable = (ArrayList<ProbRow>) factor.getTable().clone();
            for (ProbRow row : copyTable)
            {
                for (Variable header : factor.getHeaders())
                {
                    if (header.getName().equals(v.getName()))
                    {
                        String thisVal = v.getValue();
                        String otherVal = row.getValues().get(factor.getHeaders().indexOf(v));
                        if (!(thisVal.equals(otherVal)))
                        {
                            factor.getTable().remove(row);
                        }
                    }
                }
            }
        }
        return Fs;
    }

}
