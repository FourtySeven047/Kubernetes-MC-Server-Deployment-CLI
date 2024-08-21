package dev.thorben;

import dev.thorben.cli.conversations.VanillaPodConfigurationConversation;
import dev.thorben.configuration.Config;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ApiException {
        Config.init();
        //ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("/etc/rancher/k3s/k3s.yaml"))).build();
        try {
            ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(Config.readFromFile("kube-config_path")))).build();
            Configuration.setDefaultApiClient(client);

            VanillaPodConfigurationConversation vanillaPodConfigurationConversation = new VanillaPodConfigurationConversation();
            vanillaPodConfigurationConversation.start();

            AppsV1Api apiInstance = new AppsV1Api();
            apiInstance.createNamespacedDeployment("default", vanillaPodConfigurationConversation.getDeployment(), null, null, null, null);
        } catch (ParseException e) {
            System.out.println("An error occurred while connecting to the Kubernetes cluster. Please check your configuration file and verify the path of your kubeconfig file.");
        }
    }
}