package bg.tu_varna.sit.cs.f24621858.image;

import bg.tu_varna.sit.cs.f24621858.commands.CommandParser;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

import java.util.Scanner;

/**
 * Entry point for the Raster Graphics Editor.
 *
 * <p>Starts a Read-Eval-Print loop (REPL) that reads commands from standard
 * input line-by-line and dispatches them through {@link CommandParser}.
 *
 * <p>Unhandled runtime exceptions from commands are caught here so that a
 * single bad command never crashes the entire program — an error message is
 * printed instead and the loop continues.
 */
public class Main {

    private static final String BANNER =
                    "╔══════════════════════════════════════╗\n" +
                    "║     Raster Graphics Editor  v1.0     ║\n" +
                    "║  Supported formats: PBM, PGM, PPM    ║\n" +
                    "╚══════════════════════════════════════╝\n" +
                    "Type 'help' for a list of commands.\n";

    public static void main(String[] args) {
        System.out.println(BANNER);

        SessionManager sessionManager = new SessionManager();
        CommandParser  parser         = new CommandParser(sessionManager);
        Scanner        scanner        = new Scanner(System.in);

        while (true) {
            System.out.print("> ");

            // Handle EOF (e.g. Ctrl+D on Linux / Ctrl+Z on Windows, or piped input)
            if (!scanner.hasNextLine()) {
                System.out.println("\nExiting the program...");
                break;
            }

            String line = scanner.nextLine();

            // Skip blank lines silently
            if (line == null || line.isBlank()) continue;

            try {
                parser.parse(line);
            } catch (RuntimeException e) {
                // Catch unexpected runtime errors so the REPL stays alive
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
