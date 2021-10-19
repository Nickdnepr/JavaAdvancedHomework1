package com.learning;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandManager {

    private Scanner scanner;
    private FileManager fileManager;

    public CommandManager() {
        init();
    }

    private void init() {
        scanner = new Scanner(System.in);
        fileManager = new FileManager();
        String initialPath = requestInput("Input start path");
        try {
            fileManager.cd(initialPath);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid path. Pointer is not initialized");
        }
        run();
    }

    private void run() {
        String command;
        while (!(command = requestInput("Input next command")).equals("exit")) {
            try {
                String[] splitCmd = command.split(" ");
                String commandBody = splitCmd[0];
                String commandArgument = splitCmd.length > 1 ? splitCmd[1] : null;

                if (commandBody.equals("cd")) {
                    fileManager.cd(commandArgument);
                } else if (commandBody.equals("pwd")) {
                    fileManager.pwd();
                } else if (commandBody.equals("ls")) {
                    fileManager.ls();
                } else if (commandBody.equals("cat")) {

                } else{
                    System.out.println("Invalid command");
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        stop();
    }

    private void stop() {
        scanner.close();
    }

    private String requestInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
