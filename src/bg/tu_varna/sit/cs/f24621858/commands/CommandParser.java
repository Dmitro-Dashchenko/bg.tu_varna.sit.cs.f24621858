package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.commands.general.*;
import bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.*;

import java.util.*;

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

    private final Map<String,Command> commands;

    /**
     * Constructs a {@code CommandParser} and initialises all command instances.
     *
     * @param sessionManager the application-wide session manager shared by all
     *                       commands; must not be {@code null}
     */
    public CommandParser(SessionManager sessionManager) {
        this.sessionManager = sessionManager;

        commands = fillCommandMap();
    }

    private Map<String, Command> fillCommandMap(){
        Map<String, Command> commandMap = new HashMap<>();

        commandMap.put("load", new LoadCommand(sessionManager));
        commandMap.put("add", new AddCommand(sessionManager));
        commandMap.put("close", new CloseCommand(sessionManager));
        commandMap.put("save", new SaveCommand(sessionManager));
        commandMap.put("save as", new SaveAsCommand(sessionManager));
        commandMap.put("help", new HelpCommand());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("session info", new SessionInfoCommand(sessionManager));
        commandMap.put("switch", new SwitchCommand(sessionManager));
        commandMap.put("grayscale", new GrayscaleCommand(sessionManager));
        commandMap.put("monochrome", new MonochromeCommand(sessionManager));
        commandMap.put("negative", new NegativeCommand(sessionManager));
        commandMap.put("rotate", new RotateCommand(sessionManager));
        commandMap.put("undo", new UndoCommand(sessionManager));
        commandMap.put("collage", new CollageCommand(sessionManager));

        return commandMap;
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

            switch(cmd){
                case "save":
                    if (args.length > 0 && args[0].equalsIgnoreCase("as")){
                        cmd += " " + args[0].toLowerCase();
                        args = Arrays.copyOfRange(args, 1, args.length);
                    }
                    break;
                case "session":
                    if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
                        cmd += " " + args[0].toLowerCase();
                        args = new String[0];
                    } else {
                        System.out.println("Unknown session sub-command. Did you mean 'session info'?");
                    }
                    break;
            }

            if(!commands.containsKey(cmd)){
                System.out.println("Unknown command: \"" + cmd + "\". Type 'help' for a list of commands.");
                return;
            }

            commands.get(cmd).execute(args);

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
