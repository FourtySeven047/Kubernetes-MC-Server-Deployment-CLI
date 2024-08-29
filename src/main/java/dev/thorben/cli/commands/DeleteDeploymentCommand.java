package dev.thorben.cli.commands;

import dev.thorben.Main;
import dev.thorben.cli.Command;
import dev.thorben.deployment.DeploymentManager;
import io.kubernetes.client.openapi.models.V1Deployment;

import java.util.Scanner;

public class DeleteDeploymentCommand implements Command {

    @Override
    public void execute(String[] args) {
        Main.initKubernetes();
        V1Deployment deployment = DeploymentManager.selectActiveDeployment();
        if (deployment == null) {
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure you want to delete the deployment " + deployment.getMetadata().getName() + "? (y/n) ");
        String confirmation = scanner.nextLine();
        if (!confirmation.equalsIgnoreCase("y")) {
            System.out.println("Deletion aborted.");
            return;
        }
        DeploymentManager.deleteDeployment(deployment.getMetadata().getNamespace(), deployment.getMetadata().getName());
    }
}
