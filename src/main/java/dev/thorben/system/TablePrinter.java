package dev.thorben.system;

import de.vandermeer.asciitable.AsciiTable;

import java.util.Collection;

public class TablePrinter {

    AsciiTable table = new AsciiTable();

    public TablePrinter(Collection<String> columns) {
        table.addRule();
        table.addRow(columns);
        table.addRule();
    }

    public void addRow(Collection<String> row) {
        table.addRow(row);
        table.addRule();
    }

    public void print() {
        System.out.println(table.render());
    }
}
