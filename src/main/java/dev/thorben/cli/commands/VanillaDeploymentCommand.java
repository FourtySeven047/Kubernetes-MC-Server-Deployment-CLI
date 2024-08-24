package dev.thorben.cli.commands;

import dev.thorben.cli.Command;
import dev.thorben.cli.conversations.VanillaPodConfigurationConversation;
import dev.thorben.deployment.DeploymentManager;

public class VanillaDeploymentCommand implements Command {

    @Override
    public void execute(String[] args) {
        VanillaPodConfigurationConversation vanillaPodConfigurationConversation = new VanillaPodConfigurationConversation();
        vanillaPodConfigurationConversation.start();
        DeploymentManager.deploy(vanillaPodConfigurationConversation.getDeployment());
    }
}
