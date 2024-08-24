package dev.thorben;

import dev.thorben.cli.CommandFactory;
import dev.thorben.system.Config;
import dev.thorben.system.ErrorHandling;

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
    public static void main(String[] args) {
        //ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("/etc/rancher/k3s/k3s.yaml"))).build();
        Config.init();
        try {
            ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(Config.readFromFile("kubeconfig_path")))).build();
            Configuration.setDefaultApiClient(client);
        } catch (Exception e) {
            ErrorHandling.handle("An error occurred while loading the kubeconfig file:", e);
            return;
        }

        try {
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
                    ErrorHandling.handle("An error occurred while creating the namespace:", e);
                }
                return Optional.empty();
            });
        } catch (ApiException e) {
            ErrorHandling.handle("An error occurred while the namespace creation:", e);
            return;
        }

        CommandFactory.getCommand(args).execute(args);
    }
}