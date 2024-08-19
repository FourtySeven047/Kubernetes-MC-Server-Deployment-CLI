package dev.thorben.services.minecraft;

import java.util.ArrayList;

public enum Vanilla {

    V1_12_2("1.12.2");

    public final String version;
    private static final ArrayList<String> versions;

    static {
        versions = new ArrayList<>();
        for (Vanilla vanilla : Vanilla.values()) {
            versions.add(vanilla.version);
        }
    }

    Vanilla(String version) {
        this.version = version;
    }

    public static boolean isValidVersion(String version) {
        return versions.contains(version);
    }
}
