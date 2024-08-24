package dev.thorben.system;

import dev.thorben.cli.conversations.ConfigFileConfigurationConversation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Config {

    public static void init() {
        if (!fileExists()) {
            createFile();
            ConfigFileConfigurationConversation configFileConfigurationConversation = new ConfigFileConfigurationConversation();
            configFileConfigurationConversation.start();
        }
    }

    public static void reconfigure() {
        ConfigFileConfigurationConversation configFileConfigurationConversation = new ConfigFileConfigurationConversation();
        configFileConfigurationConversation.start();
    }

    private static void createFile() {
        File directory = new File("/etc/mc-server-deployer");
        if (!directory.exists()) {
            directory.mkdir();
        }
        try {
            FileWriter createFile = new FileWriter("/etc/mc-server-deployer/config.json");
            createFile.write("");
            createFile.close();
            JSONObject obj = new JSONObject();
            obj.put("kubeconfig_path", "/etc/rancher/k3s/k3s.yaml");
            obj.put("architecture", "x64");
            obj.put("namespace", "minecraft");
            FileWriter myWriter = new FileWriter("/etc/mc-server-deployer/config.json");
            myWriter.write(obj.toJSONString());
            myWriter.close();
            System.out.println("Configuration directory has been created successfully.");
        } catch (IOException e) {
            ErrorHandling.handle("An error occurred while creating the configuration file. Please check and verify the configuration file:", e);
        }
    }

    public static void writeToFile(String key, String value) {
        try {
            if (fileExists()) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader("/etc/mc-server-deployer/config.json"));
                JSONObject jsonObject = (JSONObject) obj;
                jsonObject.replace(key, value);
                FileWriter myWriter = new FileWriter("/etc/mc-server-deployer/config.json");
                myWriter.write(jsonObject.toJSONString());
                myWriter.close();
            }
        } catch (IOException | ParseException e) {
            ErrorHandling.handle("An error occurred while creating the configuration file. Please check and verify the configuration file:", e);
        }
    }

    public static String readFromFile(String key) {
        try {
            if (fileExists()) {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader("/etc/mc-server-deployer/config.json"));
                JSONObject jsonObject = (JSONObject) obj;
                return (String) jsonObject.get(key);
            }
        } catch (IOException | ParseException e) {
            ErrorHandling.handle("An error occurred while creating the configuration file. Please check and verify the configuration file:", e);
        }
        return "";
    }

    private static boolean fileExists() {
        File file = new File("/etc/mc-server-deployer/config.json");
        return file.exists() && !file.isDirectory();
    }
}
