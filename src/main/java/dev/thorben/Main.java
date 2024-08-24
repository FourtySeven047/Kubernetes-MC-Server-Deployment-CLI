package dev.thorben;

import dev.thorben.cli.conversations.VanillaPodConfigurationConversation;
import dev.thorben.configuration.Config;
import dev.thorben.deployment.DeploymentManager;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws Exception {
        Config.init();
        //ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("/etc/rancher/k3s/k3s.yaml"))).build();
        ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(Config.readFromFile("kubeconfig_path")))).build();
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api();
        List<V1Namespace> namespaces = api.listNamespace(null, null, null, null, null, null, null, null, null, null).getItems();
        namespaces.stream().filter(namespace -> namespace.getMetadata().getName().equals(Config.readFromFile("namespace"))).findFirst().or(() -> {
            try {
                V1Namespace v1Namespace = new V1Namespace();
                V1ObjectMeta metadata = new V1ObjectMeta();
                metadata.setName(Config.readFromFile("namespace"));
                v1Namespace.setMetadata(metadata);
                v1Namespace.setApiVersion("v1");
                v1Namespace.setKind("Namespace");
                api.createNamespace(v1Namespace, null, null, null, null);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        });
        if (args.length == 0) {
            System.out.println("No arguments provided. Exiting...");
            return;
        }
        if (args[0].equals("deploy") && args.length > 1) {
            if (args[1].equals("vanilla")) {
                VanillaPodConfigurationConversation vanillaPodConfigurationConversation = new VanillaPodConfigurationConversation();
                vanillaPodConfigurationConversation.start();
                DeploymentManager.deploy(vanillaPodConfigurationConversation.getDeployment());
                return;
            }
            System.out.println("Deployment type not recognized. Exiting...");
        } else if (args[0].equals("config") && args.length > 1) {
            if (args[1].equals("change")) {
                Config.reconfigure();
                return;
            }
            return;
        } else if (args[0].equals("ps")) {
            DeploymentManager.printActiveDeployments();
            return;
        }
        System.out.println("Command not recognized. Exiting...");
    }
}