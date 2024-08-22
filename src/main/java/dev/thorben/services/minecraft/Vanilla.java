package dev.thorben.services.minecraft;

import java.util.ArrayList;

public enum Vanilla {

    V1_21_1("1.21.1"),
    V1_21("1.21.0"),
    V1_20_6("1.20.6"),
    V1_20_5("1.20.5"),
    V1_20_4("1.20.4"),
    V1_20_3("1.20.3"),
    V1_20_2("1.20.2"),
    V1_20_1("1.20.1"),
    V1_20("1.20.0"),
    V1_19_4("1.19.4"),
    V1_19_3("1.19.3"),
    V1_19_2("1.19.2"),
    V1_19_1("1.19.1"),
    V1_19("1.19.0"),
    V1_18_2("1.18.2"),
    V1_18_1("1.18.1"),
    V1_18("1.18.0");

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
