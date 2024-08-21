package dev.thorben.configuration;

import dev.thorben.cli.conversations.ConfigFileConfigurationConversation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Config {

    public static void init() throws IOException {
        if (!fileExists()) {
            createFile();
            ConfigFileConfigurationConversation configFileConfigurationConversation = new ConfigFileConfigurationConversation();
            configFileConfigurationConversation.start();
        }
    }

    private static void createFile() throws IOException {
        File file = new File("/etc/mc-server-deployer");
        if (file.mkdir()) {
            FileWriter myWriter = new FileWriter("/etc/mc-server-deployer/config.json");
            JSONObject obj = new JSONObject();
            obj.put("kubeconfig_path", "/etc/rancher/k3s/k3s.yaml");
            obj.put("architecture", "x64");
            myWriter.write(obj.toJSONString());
            System.out.println("Configuration directory has been created successfully.");
        } else {
            System.out.println("Failed to create configuration directory. Are you root?");
        }
    }

    public static void writeToFile(String key, String value) throws IOException, ParseException {
        if (fileExists()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("/etc/mc-server-deployer/config.json"));
            JSONObject jsonObject = (JSONObject) obj;
            jsonObject.replace(key, value);
            FileWriter myWriter = new FileWriter("/etc/mc-server-deployer/config.json");
            myWriter.write(jsonObject.toJSONString());
            myWriter.close();
        }
    }

    public static String readFromFile(String key) throws IOException, ParseException {
        if (fileExists()) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("/etc/mc-server-deployer/config.json"));
            JSONObject jsonObject = (JSONObject) obj;
            return (String) jsonObject.get(key);
        }
        createFile();
        return readFromFile(key);
    }

    private static boolean fileExists() {
        File file = new File("/etc/mc-server-deployer/config.json");
        return file.exists() && !file.isDirectory();
    }
}
