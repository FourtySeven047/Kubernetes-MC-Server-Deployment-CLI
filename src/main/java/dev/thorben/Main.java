package dev.thorben;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import dev.thorben.cli.conversations.VanillaContainerConfigurationConversation;
import dev.thorben.core.ApplicationLoop;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        /*
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);

        dockerClient.listContainersCmd().exec().forEach(container -> {
            System.out.println(container.getId());
        });

         */
        VanillaContainerConfigurationConversation conversation = new VanillaContainerConfigurationConversation();
        ApplicationLoop applicationLoop = new ApplicationLoop();
        applicationLoop.addExecutable(conversation);
        applicationLoop.start();


        System.out.print("Hello Afterworld");
    }
}