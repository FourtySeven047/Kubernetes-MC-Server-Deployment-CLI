package dev.thorben.cli.commands;

import dev.thorben.cli.Command;
import dev.thorben.system.Config;

public class ChangeConfigCommand implements Command {

        @Override
        public void execute(String[] args) {
            Config.reconfigure();
        }
}
