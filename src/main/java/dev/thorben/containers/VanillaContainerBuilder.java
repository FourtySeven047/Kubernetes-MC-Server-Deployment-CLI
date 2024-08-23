package dev.thorben.containers;

import dev.thorben.configuration.Config;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1EnvVar;

public class VanillaContainerBuilder {

    private String name = "minecraft-server";
    private String version = "1.12.2";
    private Integer port = 25564;
    private String image;

    public VanillaContainerBuilder(String name, String version, Integer port) {
        this.name = name;
        this.version = version;
        this.port = port;

        if (Config.readFromFile("architecture").equals("arm")) {
            this.image = "fourtyseven/minecraft_server_arm:latest";
        } else {
            this.image = "fourtyseven/minecraft_server:latest";
        }
    }

    public VanillaContainerBuilder() {
        if (Config.readFromFile("architecture").equals("arm")) {
            this.image = "fourtyseven/minecraft_server_arm:latest";
        } else {
            this.image = "fourtyseven/minecraft_server:latest";
        }
    }

    public V1Container build() {
        return new V1Container()
                .name(name)
                .image(image)
                .addEnvItem(new V1EnvVar()
                        .name("version")
                        .value(version))
                .ports(java.util.Collections.singletonList(new V1ContainerPort()
                        .containerPort(25565)
                        .hostPort(port)));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Integer getPort() {
        return port;
    }
}
