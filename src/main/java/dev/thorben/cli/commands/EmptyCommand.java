package dev.thorben.cli.commands;

import dev.thorben.cli.Command;

public class EmptyCommand implements Command {

        @Override
        public void execute(String[] args) {
            System.out.println("Command not recognized. Exiting...");
        }
}
