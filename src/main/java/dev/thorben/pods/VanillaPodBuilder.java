package dev.thorben.pods;

import io.kubernetes.client.openapi.models.*;

public class VanillaPodBuilder {

    private final V1Pod pod = new V1Pod();
    V1PodSpec spec = new V1PodSpec();
    V1ObjectMeta meta = new V1ObjectMeta();

    public VanillaPodBuilder() {

    }

    public void addContainer(V1Container container) {
        spec.addContainersItem(container);
    }

    public void setPodName(String name) {
        meta.setName(name);
    }

    public void addLabel(String key, String value) {
        meta.putLabelsItem(key, value);
    }

    public V1Pod build() {
        pod.setSpec(spec);
        pod.setMetadata(meta);

        return pod;
    }
}
