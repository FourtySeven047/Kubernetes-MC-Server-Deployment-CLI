package dev.thorben.deployment;

import dev.thorben.configuration.Config;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;

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

    public static List<V1Deployment> listActiveDeployments() {
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            return apiInstance.listNamespacedDeployment(Config.readFromFile("namespace"), null, null, null, null, null, null, null, null, null, null).getItems();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
