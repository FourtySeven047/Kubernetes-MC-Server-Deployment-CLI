package dev.thorben.cli;

import java.util.ArrayList;
import java.util.List;

public abstract class Conversation {

    protected final List<ConversationStep> steps = new ArrayList<>();

    public abstract void start();
    public abstract void next();
    public abstract void stop();
}
