package dev.thorben;

import dev.thorben.cli.conversations.VanillaPodConfigurationConversation;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ApiException {
        System.out.println("Hello world!");

        ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("/etc/rancher/k3s/k3s.yaml"))).build();
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api();
        V1PodList list =
                api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }

        V1Deployment deployment = new V1Deployment()
                .apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(new V1ObjectMeta().name("minecraft-deployment"))
                .spec(new V1DeploymentSpec()
                        .replicas(3)
                        .selector(new V1LabelSelector()
                                .putMatchLabelsItem("app", "minecraft"))
                        .template(new V1PodTemplateSpec()
                                .metadata(new V1ObjectMeta()
                                        .putLabelsItem("app", "minecraft"))
                                .spec(new V1PodSpec()
                                        .containers(java.util.Collections.singletonList(new V1Container()
                                                .name("minecraft-container")
                                                .image("fourtyseven/minecraft_server_arm:latest")
                                                .ports(java.util.Collections.singletonList(new V1ContainerPort()
                                                        .containerPort(25565))))))));

        AppsV1Api apiInstance = new AppsV1Api();
        V1Deployment result = apiInstance.createNamespacedDeployment("default", deployment, null, null, null, null);
        System.out.println("Deployment created: " + result.getMetadata().getName());

        System.out.print("Hello Afterworld");
    }
}