package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.commands.general.*;
import bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandParser {

    private final SessionManager sessionManager;

    // --- commands ---
    private final Command loadCommand;
    private final Command addCommand;
    private final Command closeCommand;
    private final Command saveCommand;
    private final Command saveAsCommand;
    private final Command helpCommand;
    private final Command exitCommand;
    private final Command sessionInfoCommand;
    private final Command switchCommand;
    private final Command grayscaleCommand;
    private final Command monochromeCommand;
    private final Command negativeCommand;
    private final Command rotateCommand;
    private final Command undoCommand;
    private final Command collageCommand;

    public CommandParser(SessionManager sessionManager) {
        this.sessionManager = sessionManager;

        loadCommand        = new LoadCommand(sessionManager);
        addCommand         = new AddCommand(sessionManager);
        closeCommand       = new CloseCommand(sessionManager);
        saveCommand        = new SaveCommand(sessionManager);
        saveAsCommand      = new SaveAsCommand(sessionManager);
        helpCommand        = new HelpCommand();
        exitCommand        = new ExitCommand();
        sessionInfoCommand = new SessionInfoCommand(sessionManager);
        switchCommand      = new SwitchCommand(sessionManager);
        grayscaleCommand   = new GrayscaleCommand(sessionManager);
        monochromeCommand  = new MonochromeCommand(sessionManager);
        negativeCommand    = new NegativeCommand(sessionManager);
        rotateCommand      = new RotateCommand(sessionManager);
        undoCommand        = new UndoCommand(sessionManager);
        collageCommand     = new CollageCommand(sessionManager);
    }

    public void parse(String line) {
        if (line == null || line.isBlank()) return;

        String[] tokens = divide(line.trim().toLowerCase());
        if (tokens.length == 0) return;

        String cmd  = tokens[0].trim();
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

        try{
        switch (cmd) {
            case "load":
                loadCommand.execute(args);
                break;

            case "add":
                addCommand.execute(args);
                break;

            case "close":
                closeCommand.execute(args);
                break;

            case "save":
                if (args.length > 0 && args[0].equalsIgnoreCase("as")) {
                    saveAsCommand.execute(Arrays.copyOfRange(args, 1, args.length));
                } else {
                    saveCommand.execute(args);
                }
                break;

            case "session":
                if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
                    sessionInfoCommand.execute(new String[0]);
                } else {
                    System.out.println("Unknown session sub-command. Did you mean 'session info'?");
                }
                break;

            case "switch":
                switchCommand.execute(args);
                break;

            case "grayscale":
                grayscaleCommand.execute(args);
                break;

            case "monochrome":
                monochromeCommand.execute(args);
                break;

            case "negative":
                negativeCommand.execute(args);
                break;

            case "rotate":
                rotateCommand.execute(args);
                break;

            case "undo":
                undoCommand.execute(args);
                break;

            case "collage":
                collageCommand.execute(args);
                break;

            case "help":
                helpCommand.execute(args);
                break;

            case "exit":
                exitCommand.execute(args);
                break;

                default:
                    System.out.println("Unknown command: \"" + cmd + "\". Type 'help' for a list of commands.");
                    break;
            }
        } catch(SessionNullPointerException e) {
                System.out.println(e.getMessage());
        }
    }

    private String[] divide(String line) {
        String[] tokens = line.split(" ");
        
        if(Objects.equals(tokens[0].toLowerCase(), "load") || Objects.equals(tokens[0].toLowerCase(), "add")){
            String[] tokensArray = line.split("\"");

            List<String> tokensList = new ArrayList<>(Arrays.asList(tokensArray));

            tokensArray = tokensList.stream().map(String::trim).filter(token -> !token.isBlank()).toArray(String[]::new);
        
            tokens = tokensArray;
        }
        
        return tokens;
    }
}
