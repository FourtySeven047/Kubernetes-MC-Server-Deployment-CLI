package dev.thorben.core;

import jakarta.ws.rs.core.Link;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ApplicationLoop {

    private final Queue<Executable> executables;
    private Executable currentTask;
    private boolean started = false;

    public ApplicationLoop() {
        executables = new LinkedList<>();
    }

    public void addExecutable(Executable executable) {
        executables.add(executable);
    }

    public void start() {
        System.out.println("Starting application loop.");
        loop();
        started = true;
    }

    public void stop() {
        System.out.println("Stopping application loop");
    }

    private void loop() {
        if (executables.isEmpty()) {
            System.out.println("No executables to run.");
            return;
        } else {
            currentTask = executables.peek();
            currentTask.execute();
        }
        while (true) {
            Scanner scanner = new Scanner(System.in);
            currentTask.processInput(scanner.nextLine());
            if (currentTask.isRunning()) return;
            else {
                currentTask = executables.poll();
                currentTask.execute();
            }
            if (executables.isEmpty()) {
                System.out.println("No more executables to run.");
                break;
            }
        }
    }

    public void addItem(String item) {
        System.out.println("Adding item: " + item);
    }
}
