package dev.thorben.cli.conversations;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;

import java.util.function.Consumer;

public class TextConversationStep extends ConversationStep {

    private final Conversation conversation;
    private final String message;

    public TextConversationStep(Conversation conversation, String message) {
        this.conversation = conversation;
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
        conversation.next();
    }

    public void input(String input) {

    }
}
