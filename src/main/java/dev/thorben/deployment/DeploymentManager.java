package dev.thorben.deployment;

import dev.thorben.system.Config;
import dev.thorben.system.ErrorHandling;
import dev.thorben.system.TablePrinter;
import io.kubernetes.client.Copy;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DeploymentManager {

    public static void deploy(V1Deployment deployment) {
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            apiInstance.createNamespacedDeployment(Config.readFromFile("namespace"), deployment, null, null, null, null);
        } catch (Exception e) {
            ErrorHandling.handle("An error occurred while deploying the pod:", e);
        }
    }

    public static void deleteDeployment(String namespace, String deploymentName) {
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            apiInstance.deleteNamespacedDeployment(deploymentName, namespace, null, null, null, null, null, null);
        } catch (Exception e) {
            ErrorHandling.handle("An error occurred while deleting the deployment:", e);
            return;
        }
        System.out.println("Deployment " + deploymentName + " has been deleted successfully.");
    }

    public static List<V1Deployment> getActiveDeployments() {
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            return apiInstance.listNamespacedDeployment(Config.readFromFile("namespace"), null, null, null, null, null, null, null, null, null, null).getItems();
        } catch (Exception e) {
            ErrorHandling.handle("An error occurred while fetching the active deployments:", e);
        }
        return new ArrayList<>();
    }

    public static void printActiveDeployments() {
        if (!hasActiveDeployments()) {
            System.out.println("No deployments found. Exiting...");
            return;
        }
        TablePrinter tablePrinter = new TablePrinter(List.of("Deployment", "Created", "Running Containers", "Id"));
        getActiveDeployments().forEach(deployment -> {
            tablePrinter.addRow(List.of(
                    deployment.getMetadata().getName(),
                    deployment.getMetadata().getCreationTimestamp().toString(),
                    deployment.getStatus().getReplicas() + "/" + deployment.getSpec().getReplicas(),
                    deployment.getMetadata().getUid()
            ));
            tablePrinter.print();
        });
    }

    public static V1Deployment selectActiveDeployment() {
        if (getActiveDeployments().isEmpty()) {
            System.out.println("No deployments found. Exiting...");
            return null;
        }
        System.out.println("Active Deployments:");
        TablePrinter tablePrinter = new TablePrinter(List.of("Number", "Deployment", "Created", "Running Containers", "Id"));
        HashMap<String, V1Deployment> deploymentHashMap = new HashMap<>();
        final int[] i = {0};
        getActiveDeployments().forEach(deployment -> {
            tablePrinter.addRow(List.of(
                    String.valueOf(i[0]),
                    deployment.getMetadata().getName(),
                    deployment.getMetadata().getCreationTimestamp().toString(),
                    deployment.getStatus().getReplicas() + "/" + deployment.getSpec().getReplicas(),
                    deployment.getMetadata().getUid()
            ));
            deploymentHashMap.put(String.valueOf(i[0]), deployment);
            i[0]++;
        });
        tablePrinter.print();
        System.out.print("Please select a deployment by entering the corresponding number: ");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
        if (deploymentHashMap.containsKey(selection)) {
            return deploymentHashMap.get(selection);
        } else {
            System.out.println("Invalid selection. Exiting...");
            return null;
        }
    }

    private static boolean hasActiveDeployments() {
        return !getActiveDeployments().isEmpty() && getActiveDeployments() != null && !getActiveDeployments().contains(null);
    }

    // This will be supported in the future
    public static void addFileToDeployment(String namespace, String pod, String container, Path srcPath, Path destPath) {
        try {
            Copy copy = new Copy();
            copy.copyFileToPod(namespace, pod, container, srcPath, destPath);
        } catch (Exception e) {
            ErrorHandling.handle("An error occurred while copying the file to the pod:", e);
        }
    }
}
