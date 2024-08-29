package dev.thorben.cli.conversations;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;
import dev.thorben.cli.conversations.steps.InputConversationStep;
import dev.thorben.cli.conversations.steps.TextConversationStep;
import dev.thorben.containers.VanillaContainerBuilder;
import dev.thorben.services.minecraft.Vanilla;
import io.kubernetes.client.openapi.models.V1Container;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.Queue;

public class VanillaContainerConfigurationConversation extends Conversation {

    private final VanillaContainerBuilder builder = new VanillaContainerBuilder();

    private final Queue<ConversationStep> conversationStack = new LinkedList<>();

    public VanillaContainerConfigurationConversation() {
        super();
        conversationStack.add(new TextConversationStep(this, "Starting container configuration wizard..."));
        conversationStack.add(new InputConversationStep(this, s -> !s.contains(" "), "Enter the name of your container: "));
        conversationStack.add(new InputConversationStep(this, s -> {
            if (Vanilla.isValidVersion(s)) {
                builder.setVersion(s);
                return true;
            }
            return false;
        }, "Please enter your desired minecraft version: "));
        conversationStack.add(new InputConversationStep(this, s -> {
            if (StringUtils.isNumeric(s) && Integer.parseInt(s) > 0 && Integer.parseInt(s) < 65536) {
                builder.setPort(Integer.parseInt(s));
                return true;
            }
            return false;
        }, "Please enter the port you want to assign: "));
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
        System.out.println("Container configuration complete.");
    }

    public V1Container getContainer() {
        return builder.build();
    }
}
