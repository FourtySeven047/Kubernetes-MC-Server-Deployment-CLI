package dev.thorben.containers;

import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1EnvVar;
import io.kubernetes.client.openapi.models.V1ResourceRequirements;

public class VanillaContainerBuilder {

    private final String version;
    private final String cpu;
    private final String memory;
    private final Integer port;

    public VanillaContainerBuilder(String version, String cpu, String memory, Integer port) {
        this.version = version;
        this.cpu = cpu;
        this.memory = memory;
        this.port = port;
    }

    public V1Container build() {
        V1Container container = new V1Container();
        container.setImage("fourtyseven/minecraft_server");
        container.setName("minecraft-server:" + version);

        V1ResourceRequirements resources = new V1ResourceRequirements();
        resources.putLimitsItem("cpu", new Quantity(cpu)); // 1
        resources.putLimitsItem("memory", new Quantity(memory)); // 1Gi
        container.setResources(resources);

        container.addEnvItem(new V1EnvVar().name("version").value(version));

        V1ContainerPort containerPort = new V1ContainerPort();
        containerPort.setContainerPort(25565);
        containerPort.setHostPort(port);
        container.addPortsItem(containerPort);

        container.addPortsItem(containerPort);

        return container;
    }
}
