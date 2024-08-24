package dev.thorben.cli.conversations;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;
import dev.thorben.cli.conversations.steps.InputConversationStep;
import dev.thorben.cli.conversations.steps.TextConversationStep;
import dev.thorben.pods.VanillaPodBuilder;
import io.kubernetes.client.openapi.models.V1Deployment;

import java.util.LinkedList;
import java.util.Queue;

public class VanillaPodConfigurationConversation extends Conversation  {

    private final VanillaPodBuilder builder = new VanillaPodBuilder();
    private final Queue<ConversationStep> conversationStack = new LinkedList<>();
    private V1Deployment result;

    public VanillaPodConfigurationConversation() {
        super();
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
            // if (!StringUtils.isNumeric(s)) return false;
            // int amount = Integer.parseInt(s);
            // if (amount > 5 || amount < 1) return false;
            //for (int i = 0; i < amount; i++) {
            //}
            VanillaContainerConfigurationConversation containerConversation = new VanillaContainerConfigurationConversation();
            containerConversation.start();
            builder.addContainer(containerConversation.getContainer());
            return true;
        }, "Please enter the amount of containers you want to add to the pod (1): "));
        conversationStack.add(new TextConversationStep(this, "Pod configuration has been finished."));
    }

    @Override
    public void start() {
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
        result = builder.build();
        System.out.println("Pod deployment is complete.");
    }

    public V1Deployment getDeployment() {
        return result;
    }
}
