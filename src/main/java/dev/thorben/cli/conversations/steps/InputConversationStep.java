package dev.thorben.cli.conversations.steps;

import dev.thorben.cli.Conversation;
import dev.thorben.cli.ConversationStep;

import java.util.Scanner;
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
        System.out.print(message);
        startInputScanner();
    }

    @Override
    public void startInputScanner() {
        Scanner scanner = new Scanner(System.in);
        if (onInput.apply(scanner.nextLine())) {
            conversation.next();
            return;
        }
        System.out.print("Invalid Input. Try again: ");
        startInputScanner();
    }
}
