package dev.thorben.cli;

import dev.thorben.cli.commands.*;
import dev.thorben.system.Config;

public class CommandFactory {

    public static Command getCommand(String[] args) {
        if (args.length == 0) {
            return new EmptyCommand();
        }

        if (args[0].equals("deploy") && args.length > 1) {
            System.out.println("No deployment type provided. Exiting...");
            if (args[1].equals("vanilla")) {
                return new VanillaDeploymentCommand();
            }
        } else if (args[0].equals("config") && args.length > 1) {
            if (args[1].equals("change")) {
                Config.reconfigure();
                return new ChangeConfigCommand();
            }
        } else if (args[0].equals("ps")) {
            return new ProcessStatusCommand();
        } else if (args[0].equals("help")) {
            return new HelpCommand();
        } else if (args[0].equals("delete") && args.length > 1) {
            if (args[1].equals("deployment")) {
                return new DeleteDeploymentCommand();
            }
        }
        return new EmptyCommand();
    }
}
