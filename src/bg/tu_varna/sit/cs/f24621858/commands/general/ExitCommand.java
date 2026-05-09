package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;

/**
 * Terminates the application.
 *
 * @author Dmitro Dashchenko
 *
 */
public class ExitCommand implements Command {

    /**
     *<p>Prints a farewell message and calls {@link System#exit(int)} with status
     * code {@code 0}.  Any unsaved changes in open sessions are discarded — it is
     * the user's responsibility to {@code save} before exiting.
     *
     * @param args ignored — {@code session info} takes no arguments
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
