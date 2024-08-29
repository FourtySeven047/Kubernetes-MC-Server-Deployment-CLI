package dev.thorben.cli.commands;

import dev.thorben.cli.Command;
import dev.thorben.system.TablePrinter;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        TablePrinter tablePrinter = new TablePrinter(java.util.Arrays.asList("Command", "Description"));
        tablePrinter.addRow(java.util.Arrays.asList("deploy vanilla", "Start configuration wizard for a new vanilla deployment"));
        tablePrinter.addRow(java.util.Arrays.asList("ps", "Print the status of all active deployments"));
        tablePrinter.addRow(java.util.Arrays.asList("config change", "Change configuration"));
        tablePrinter.addRow(java.util.Arrays.asList("delete deployment", "Delete a deployment"));
        tablePrinter.print();
    }
}
