package dev.thorben.cli.commands;

import dev.thorben.cli.Command;

public class EmptyCommand implements Command {

        @Override
        public void execute(String[] args) {
            System.out.println("Command not recognized. Run the help command to see all available commands. Exiting...");
        }
}
