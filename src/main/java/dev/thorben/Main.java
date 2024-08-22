package dev.thorben;

import dev.thorben.cli.conversations.VanillaPodConfigurationConversation;
import dev.thorben.configuration.Config;
import dev.thorben.deployment.DeploymentManager;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Config.init();
        //ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("/etc/rancher/k3s/k3s.yaml"))).build();
        try {
            ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(Config.readFromFile("kubeconfig_path")))).build();
            Configuration.setDefaultApiClient(client);
        } catch (ParseException e) {
            System.out.println("An error occurred while connecting to the Kubernetes cluster. Please check your configuration file and verify the path of your kubeconfig file.");
            return;
        }
        if (args.length == 0) {
            System.out.println("No arguments provided. Exiting...");
            return;
        }
        if (args[0].equals("deploy")) {
            if (args[1].equals("vanilla")) {
                VanillaPodConfigurationConversation vanillaPodConfigurationConversation = new VanillaPodConfigurationConversation();
                vanillaPodConfigurationConversation.start();
                DeploymentManager.deploy(vanillaPodConfigurationConversation.getDeployment());
                return;
            }
            System.out.println("Deployment type not recognized. Exiting...");
        }

        System.out.println("Command not recognized. Exiting...");
    }
}