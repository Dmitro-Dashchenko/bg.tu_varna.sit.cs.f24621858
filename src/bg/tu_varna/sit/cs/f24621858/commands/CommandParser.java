package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.commands.general.*;
import bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Tokenizes a raw input line and dispatches the result to the appropriate
 * {@link Command} implementation.
 *
 * <p>Two-word command prefixes are handled specially.
 *
 * <p>Quoted strings (wrapped in {@code "}) are treated as a single token,
 * allowing file paths that contain spaces:
 * <pre>
 *   load "C:\My Images\photo.ppm"
 * </pre>
 * The surrounding quotes are stripped before the token is passed to the command.
 *
 * <p>All command keywords are matched case-insensitively.
 *
 * <p>Supported commands and their keyword mappings:
 * <pre>
 *   load, add, close, save, session, switch, grayscale,
 *   monochrome, negative, rotate,undo, collage, help, exit.
 * </pre>
 *
 *@author Dmitro Dashchenko
 *
 */
public class CommandParser {

    private final SessionManager sessionManager;

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

    /**
     * Constructs a {@code CommandParser} and initialises all command instances.
     *
     * @param sessionManager the application-wide session manager shared by all
     *                       commands; must not be {@code null}
     */
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

    /**
     * Parses and executes a single line of user input.
     *
     * <p>Blank or {@code null} lines are silently ignored.
     * Unknown command keywords produce an error message suggesting {@code help}.
     *
     * @param line raw input line as typed by the user; may be {@code null} or blank
     */
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

    /**
     * Splits {@code line} into tokens, treating double-quoted substrings as
     * single tokens and stripping the surrounding quotes.
     *
     * @param line the trimmed input line
     * @return array of tokens; never {@code null}, may be empty
     */
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
