package dev.thorben.pods;

import io.kubernetes.client.openapi.models.*;

import java.util.ArrayList;
import java.util.List;

public class VanillaPodBuilder {

    private String podName;
    List<V1Container> containers = new ArrayList<>();

    public VanillaPodBuilder() {

    }

    public void addContainer(V1Container container) {
        containers.add(container);
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public V1Deployment build() {
        if (containers.isEmpty()) {
            System.out.print("Building process stopped. No containers have been added to the pod.");
            return null;
        }
        V1Deployment deployment = new V1Deployment()
                .apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(new V1ObjectMeta().name(podName))
                .spec(new V1DeploymentSpec()
                        .replicas(1)
                        .selector(new V1LabelSelector()
                                .putMatchLabelsItem("app", "minecraft"))
                        .template(new V1PodTemplateSpec()
                                .metadata(new V1ObjectMeta()
                                        .putLabelsItem("app", "minecraft"))
                                .spec(new V1PodSpec().containers(containers))));

        //AppsV1Api apiInstance = new AppsV1Api();
        return deployment; //apiInstance.createNamespacedDeployment("default", deployment, null, null, null, null);
    }
}
