package bg.tu_varna.sit.cs.f24621858.commands;

/**
 * Contract for all CLI commands supported by the raster graphics editor.
 *
 * <p>Each command encapsulates a single user action. Commands are instantiated
 * once at startup by {@link CommandParser}
 * and reused for every subsequent invocation of that command.
 *
 * <p>Commands communicate results by printing to {@link System#out}.
 * They must not throw checked exceptions to the caller — all error handling
 * is done internally with an appropriate printed message.
 *
 * @author Dmitro Dashchenko
 *
 */
public interface Command {
    /**
     * Executes this command with the provided arguments.
     *
     * @param args the arguments passed after the command keyword; never {@code null},
     *             but may be empty
     */
    void execute(String[] args);
}
