package varelim;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Dennis den Hollander
 * @author Tom Kamp
 */
public class Table2
{

    private final Table TABLE;
    private final ArrayList<Variable> HEADERS = new ArrayList();
    private BufferedWriter writer;

    public Table2(Table table, BufferedWriter writer)
    {
        this.writer = writer;
        this.TABLE = table;
        this.HEADERS.addAll(table.getParents());
        this.HEADERS.add(table.getNode());
    }

    public ArrayList<Variable> getHeaders()
    {
        return this.HEADERS;
    }

    public ArrayList<ProbRow> getTable()
    {
        return this.TABLE.getTable();
    }

    public Table2 multiply(Table2 other) throws IOException
    {
        ArrayList<ProbRow> newRows = new ArrayList();
        ArrayList<Variable> newHeaders = (ArrayList<Variable>) this.HEADERS.clone();
        for (ProbRow row1 : this.getTable())
        {
            for (ProbRow row2 : other.getTable())
            {
                for (Variable thisHeader : this.HEADERS)
                {
                    for (Variable otherHeader : other.HEADERS)
                    {
                        if (thisHeader.equals(otherHeader))
                        {
                            String thisVal = row1.getValues().get(this.HEADERS.indexOf(thisHeader));
                            String otherVal = row2.getValues().get(other.HEADERS.indexOf(otherHeader));
                            if (thisVal.equals(otherVal))
                            {
                                newRows.add(row1.multiply(row2, this.HEADERS, other.HEADERS));
//                                writer.newLine();
                                
                            }
                        }
                    }
                }
            }
        }

        /**
         * Filter node and parent back in table
         */
        Variable node = newHeaders.get(newHeaders.size() - 1);
        ArrayList<Variable> parents = new ArrayList();
        for (int i = 0; i < newHeaders.size() - 1; i++)
        {
            parents.add(newHeaders.get(i));
        }
        return new Table2(new Table(newRows, node, parents), writer);
    }

    public Table2 eliminate(Variable variable)
    {
        ArrayList<ProbRow> newRows = new ArrayList();
        ArrayList<Variable> newHeaders = (ArrayList<Variable>) this.HEADERS.clone();
        for (Variable header : this.HEADERS)
        {
            if (header.equals(variable))
            {
                int column = this.getHeaders().indexOf(header);
                newHeaders.remove(column);
                for (ProbRow row1 : this.getTable())
                {
                    row1.getValues().remove(column);
                    newRows.add(row1);
                }
            }
        }
        return new Table2(new Table(newRows, newHeaders), writer);
    }

    public Table2 merge()
    {
        ArrayList<ProbRow> tempVal = new ArrayList();
        ArrayList<ProbRow> newRows = new ArrayList();
        for (ProbRow row1 : this.getTable())
        {
            for (ProbRow row2 : this.getTable())
            {
                if (row1.getValues().equals(row2.getValues()) && !tempVal.contains(row1) && row1 != row2)
                {
                    tempVal.add(row1);
                    tempVal.add(row2);
                    newRows.add(row1.addRow(row2));
                }
            }
        }
        return new Table2(new Table(newRows, this.HEADERS), writer);
    }
}
