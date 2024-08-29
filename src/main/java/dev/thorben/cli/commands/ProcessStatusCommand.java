package dev.thorben.cli.commands;

import dev.thorben.Main;
import dev.thorben.cli.Command;
import dev.thorben.deployment.DeploymentManager;

public class ProcessStatusCommand implements Command {

        @Override
        public void execute(String[] args) {
            Main.initKubernetes();
            DeploymentManager.printActiveDeployments();
        }
}
