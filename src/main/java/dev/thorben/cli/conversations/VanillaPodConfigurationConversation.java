package dev.thorben.cli.conversations;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;
import dev.thorben.cli.conversations.steps.InputConversationStep;
import dev.thorben.cli.conversations.steps.TextConversationStep;
import dev.thorben.pods.VanillaPodBuilder;
import io.kubernetes.client.openapi.models.V1Pod;

import java.util.LinkedList;
import java.util.Queue;

public class VanillaPodConfigurationConversation extends Conversation  {

    private final VanillaPodBuilder builder = new VanillaPodBuilder();

    private final Queue<ConversationStep> conversationStack = new LinkedList<>();
    private boolean isRunning = false;

    public VanillaPodConfigurationConversation() {
        super();
        VanillaContainerConfigurationConversation containerConfigurationConversation = new VanillaContainerConfigurationConversation();
        conversationStack.add(new TextConversationStep(this, "Starting pod configuration wizard..."));
        conversationStack.add(new InputConversationStep(this, s -> {
            if (s.contains(" ")) {
                return false;
            } else {
                builder.setPodName(s);
                return true;
            }
        }, "Enter the name of your pod: "));
        conversationStack.add(new InputConversationStep(this, s -> {
            if (s.contains(" ")) {
                return false;
            } else {
                builder.addLabel("app", s);
                containerConfigurationConversation.start();
                builder.addContainer(containerConfigurationConversation.getContainer());
                return true;
            }
        }, "Enter the app-label of your pod: "));
    }

    @Override
    public void start() {
        isRunning = true;
        conversationStack.peek().printMessage();
    }

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
        System.out.println("Pod deployment is complete.");
    }

    public V1Pod getPod() {
        return builder.build();
    }
}
