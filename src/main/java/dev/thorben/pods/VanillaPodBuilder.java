package dev.thorben.pods;

import io.kubernetes.client.openapi.models.V1Pod;

public class VanillaPodBuilder {

    public VanillaPodBuilder() {
        System.out.println("VanillaPodBuilder created");
    }

    public V1Pod build() {
        return new V1Pod();
    }
}
