package dev.thorben.cli.commands;

import dev.thorben.cli.Command;
import dev.thorben.deployment.DeploymentManager;
import io.kubernetes.client.openapi.models.V1Deployment;

public class DeleteDeploymentCommand implements Command {

    @Override
    public void execute(String[] args) {
        V1Deployment deployment = DeploymentManager.selectActiveDeployment();
        if (deployment == null) {
            return;
        }
        DeploymentManager.deleteDeployment(deployment.getMetadata().getNamespace(), deployment.getMetadata().getName());
    }
}
