package dev.thorben.cli.commands;

import dev.thorben.cli.Command;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Available commands:");
        System.out.println("deploy vanilla  | Start configuration wizard for a new vanilla deployment");
        System.out.println("ps              | Print the status of all active deployments");
        System.out.println("config change   | Change configuration");
    }
}
