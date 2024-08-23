package dev.thorben.cli.conversations;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;
import dev.thorben.cli.conversations.steps.InputConversationStep;
import dev.thorben.cli.conversations.steps.TextConversationStep;
import dev.thorben.configuration.Config;

import java.util.LinkedList;
import java.util.Queue;

public class ConfigFileConfigurationConversation extends Conversation {

    private final Queue<ConversationStep> conversationStack = new LinkedList<>();

    public ConfigFileConfigurationConversation() {
        super();
        conversationStack.add(new TextConversationStep(this, "Starting config file configuration wizard..."));
        conversationStack.add(new InputConversationStep(this, s -> {
            Config.writeToFile("kubeconfig_path", s);
            return true;
        }, "Enter the path to your kubeconfig file: "));
        conversationStack.add(new InputConversationStep(this, s -> {
            Config.writeToFile("architecture", s);
            return true;
        }, "Enter your cpu architecture (x64 | arm): "));
        conversationStack.add(new InputConversationStep(this, s -> {
            Config.writeToFile("namespace", s);
            return true;
        }, "Enter the namespace you want to deploy to: "));
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
