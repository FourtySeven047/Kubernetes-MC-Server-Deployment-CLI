package dev.thorben.cli.commands;

import dev.thorben.Main;
import dev.thorben.cli.Command;
import dev.thorben.cli.conversations.VanillaPodConfigurationConversation;
import dev.thorben.deployment.DeploymentManager;

public class VanillaDeploymentCommand implements Command {

    @Override
    public void execute(String[] args) {
        Main.initKubernetes();
        VanillaPodConfigurationConversation vanillaPodConfigurationConversation = new VanillaPodConfigurationConversation();
        vanillaPodConfigurationConversation.start();
        DeploymentManager.deploy(vanillaPodConfigurationConversation.getDeployment());
    }
}
