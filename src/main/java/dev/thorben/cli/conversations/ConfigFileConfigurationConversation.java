package dev.thorben.cli.conversations;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;
import dev.thorben.cli.conversations.steps.InputConversationStep;
import dev.thorben.cli.conversations.steps.TextConversationStep;
import dev.thorben.configuration.Config;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ConfigFileConfigurationConversation extends Conversation {

    private final Queue<ConversationStep> conversationStack = new LinkedList<>();

    public ConfigFileConfigurationConversation() {
        super();
        conversationStack.add(new TextConversationStep(this, "Starting config file configuration wizard..."));
        conversationStack.add(new InputConversationStep(this, s -> {
            try {
                Config.writeToFile("kubeconfig_path", s);
                return true;
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return false;
        }, "Enter the path to your kubeconfig file: "));
        conversationStack.add(new InputConversationStep(this, s -> {
            if (s.contains("x64") || s.contains("arm")) {
                try {
                    Config.writeToFile("architecture", s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }, "Enter your cpu architecture (x64 | arm): "));
    }

    @Override
    public void start() {
        conversationStack.peek().printMessage();
    }

    @Override
    public void next() {
        conversationStack.poll();
        if (!conversationStack.isEmpty()) {
            conversationStack.peek().printMessage();
        } else {
            stop();
        }
    }

    @Override
    public void stop() {
        System.out.println("Configuration complete! You are good to go.");
    }
}
