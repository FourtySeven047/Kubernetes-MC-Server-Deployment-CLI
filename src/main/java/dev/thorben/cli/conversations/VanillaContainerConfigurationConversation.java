package dev.thorben.cli.conversations;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;
import dev.thorben.cli.conversations.steps.InputConversationStep;
import dev.thorben.cli.conversations.steps.TextConversationStep;
import dev.thorben.containers.VanillaContainerBuilder;
import dev.thorben.core.Executable;
import dev.thorben.services.minecraft.Vanilla;
import io.kubernetes.client.openapi.models.V1Container;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

public class VanillaContainerConfigurationConversation extends Conversation implements Executable {

    private final VanillaContainerBuilder builder = new VanillaContainerBuilder();

    private final Queue<ConversationStep> conversationStack = new LinkedList<>();

    private boolean isRunning = false;

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
        conversationStack.add(new InputConversationStep(this, new Function<String, Boolean>() {
            @Override
            public Boolean apply(String s) {
                if (StringUtils.isNumeric(s)) {
                    builder.setMemory(s);
                    return true;
                }
                return false;
            }
        }, "Please enter the amount of RAM you want to allocate to the container in Megabytes: "));
        conversationStack.add(new InputConversationStep(this, new Function<String, Boolean>() {
            @Override
            public Boolean apply(String s) {
                if (StringUtils.isNumeric(s)) {
                    builder.setCpu(s);
                    return true;
                }
                return false;
            }
        }, "Please enter the amount of CPU cores you want to allocate to the container: "));
        conversationStack.add(new InputConversationStep(this, new Function<String, Boolean> () {
            @Override
            public Boolean apply(String s) {
                if (StringUtils.isNumeric(s) && Integer.parseInt(s) > 0 && Integer.parseInt(s) < 65536) {
                    builder.setPort(Integer.parseInt(s));
                    return true;
                }
                return false;
            }
        }, "Please enter the port you want to assign: "));
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
        System.out.println("Container configuration complete.");
    }

    public V1Container getContainer() {
        return builder.build();
    }

    @Override
    public void execute() {
        start();
    }

    @Override
    public void processInput(String input) {
        conversationStack.peek().startInputScanner();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
