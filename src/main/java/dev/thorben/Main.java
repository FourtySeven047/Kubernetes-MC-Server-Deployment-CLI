package dev.thorben;

import dev.thorben.cli.conversations.VanillaPodConfigurationConversation;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ApiException {
        System.out.println("Hello world!");

        ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("/etc/rancher/k3s/k3s.yaml"))).build();
        Configuration.setDefaultApiClient(client);

        VanillaPodConfigurationConversation vanillaPodConfigurationConversation = new VanillaPodConfigurationConversation();
        vanillaPodConfigurationConversation.start();

        AppsV1Api apiInstance = new AppsV1Api();
        apiInstance.createNamespacedDeployment("default", vanillaPodConfigurationConversation.getDeployment(), null, null, null, null);
    }
}