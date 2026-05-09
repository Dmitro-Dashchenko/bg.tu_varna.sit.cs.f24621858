package bg.tu_varna.sit.cs.f24621858.image;

import bg.tu_varna.sit.cs.f24621858.commands.CommandParser;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

import java.util.Scanner;

/**
 * Entry point for the Raster Graphics Editor.
 *
 * <p>The editor is a console application that operates on
 * Netpbm image files
 * (PBM, PGM, PPM in both ASCII and binary variants).
 *
 * <p>Unhandled {@link RuntimeException}s from any command are caught here so
 * that a single bad command never crashes the editor — an error message is
 * printed and the REPL continues.
 *
 * <p>Supported commands (see {@code help} for a full list):
 * <pre>
 *   load, add, close, save, save as, session info, switch,
 *   grayscale, monochrome, negative, rotate, undo, collage, help, exit
 * </pre>
 *
 * @author Dmitro Dashchenko
 *
 * @see CommandParser
 * @see SessionManager
 */
public class Main {


    private static final String BANNER =
                    /** Welcome banner displayed once on startup. */
                    "╔══════════════════════════════════════╗\n" +
                    "║     Raster Graphics Editor  v1.0     ║\n" +
                    "║  Supported formats: PBM, PGM, PPM    ║\n" +
                    "╚══════════════════════════════════════╝\n" +
                    "Type 'help' for a list of commands.\n";

    /**
     * Application entry point.
     * Initialises the {@link SessionManager} and {@link CommandParser},
     * prints the welcome banner, then enters the REPL loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println(BANNER);

        SessionManager sessionManager = new SessionManager();
        CommandParser  parser         = new CommandParser(sessionManager);
        Scanner        scanner        = new Scanner(System.in);

        while (true) {
            System.out.print("> ");

            if (!scanner.hasNextLine()) {
                System.out.println("\nExiting the program...");
                break;
            }

            String line = scanner.nextLine();

            if (line == null || line.isBlank()) continue;

            try {
                parser.parse(line);
            } catch (RuntimeException e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
