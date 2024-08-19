package dev.thorben.containers;

import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1EnvVar;
import io.kubernetes.client.openapi.models.V1ResourceRequirements;

public class VanillaContainerBuilder {

    private String version;
    private String cpu;
    private String memory;
    private Integer port;

    public VanillaContainerBuilder(String version, String cpu, String memory, Integer port) {
        this.version = version;
        this.cpu = cpu;
        this.memory = memory;
        this.port = port;
    }

    public VanillaContainerBuilder() {

    }

    public V1Container build() {
        V1Container container = new V1Container();
        container.setImage("fourtyseven/minecraft_server");
        container.setName("minecraft-server:" + version);

        V1ResourceRequirements resources = new V1ResourceRequirements();
        resources.putLimitsItem("cpu", new Quantity(cpu)); // 1
        resources.putLimitsItem("memory", new Quantity(memory + "Mi")); // 1Gi
        container.setResources(resources);

        container.addEnvItem(new V1EnvVar().name("version").value(version));

        V1ContainerPort containerPort = new V1ContainerPort();
        containerPort.setContainerPort(25565);
        containerPort.setHostPort(port);
        container.addPortsItem(containerPort);

        container.addPortsItem(containerPort);

        return container;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public String getCpu() {
        return cpu;
    }

    public String getMemory() {
        return memory;
    }

    public Integer getPort() {
        return port;
    }

    public boolean isComplete() {
        return version != null && cpu != null && memory != null && port != null;
    }
}
