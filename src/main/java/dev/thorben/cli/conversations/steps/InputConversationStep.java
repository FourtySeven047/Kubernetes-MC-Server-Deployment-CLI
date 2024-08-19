package dev.thorben.cli.conversations.steps;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;

import java.util.function.Function;

public class InputConversationStep extends ConversationStep {

    private final Conversation conversation;
    Function<String, Boolean> onInput;
    private final String message;

    public InputConversationStep(Conversation conversation, Function<String, Boolean> onInput, String message) {
        this.conversation = conversation;
        this.onInput = onInput;
        this.message = message;
    }

    @Override
    public void printMessage() {
        System.out.println("\n" + message);
    }

    @Override
    public void input(String input) {
        if(onInput.apply(input)) {
            conversation.next();
            return;
        }
        System.out.print("\nInvalid Input. Try again: ");
    }
}
