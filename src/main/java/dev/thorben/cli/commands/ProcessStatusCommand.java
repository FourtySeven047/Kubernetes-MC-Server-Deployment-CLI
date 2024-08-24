package dev.thorben.cli.commands;

import dev.thorben.cli.Command;
import dev.thorben.deployment.DeploymentManager;

public class ProcessStatusCommand implements Command {

        @Override
        public void execute(String[] args) {
            DeploymentManager.printActiveDeployments();
        }
}
