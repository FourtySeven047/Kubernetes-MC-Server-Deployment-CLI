package dev.thorben.deployment;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;

public class DeploymentManager {

    public static void deploy(V1Deployment deployment) {
        AppsV1Api apiInstance = new AppsV1Api();
        try {
            apiInstance.createNamespacedDeployment("default", deployment, null, null, null, null);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
