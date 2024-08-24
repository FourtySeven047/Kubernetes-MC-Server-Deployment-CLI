package dev.thorben.deployment;

import dev.thorben.configuration.Config;
import io.kubernetes.client.Copy;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;

import java.nio.file.Path;
import java.util.List;

public class DeploymentManager {

    public static void deploy(V1Deployment deployment) {
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            apiInstance.createNamespacedDeployment(Config.readFromFile("namespace"), deployment, null, null, null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<V1Deployment> getActiveDeployments() {
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            //V1Pod pod = (V1Pod) apiInstance.listNamespacedDeployment(Config.readFromFile("namespace"), null, null, null, null, null, null, null, null, null, null).getItems().get(0);
            return apiInstance.listNamespacedDeployment(Config.readFromFile("namespace"), null, null, null, null, null, null, null, null, null, null).getItems();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void printActiveDeployments() {
        if (getActiveDeployments().isEmpty()) {
            System.out.println("No deployments found. Exiting...");
            return;
        }
        getActiveDeployments().forEach(deployment -> {
            System.out.println("Deployment: " + deployment.getMetadata().getName());
            deployment.getSpec().getTemplate().getSpec().getContainers().forEach(container -> System.out.println("\t" + container.getName()));
        });
    }

    // This will be supported in the future
    public static void addFileToDeployment(String namespace, String pod, String container, Path srcPath, Path destPath) {
        try {
            Copy copy = new Copy();
            copy.copyFileToPod(namespace, pod, container, srcPath, destPath);
        } catch (Exception e) {
            System.out.println("An error occurred while copying the file to the pod.");
        }
    }
}
