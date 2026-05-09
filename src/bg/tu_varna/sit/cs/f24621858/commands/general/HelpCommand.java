package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;

/**
 * Prints a concise reference of all commands supported by the raster graphics editor.
 *
 * <p>The output lists every command with its syntax and a one-line description.
 * This command is always available regardless of whether a session is active.
 *
 *@author Dmitro Dashchenko
 *
 */
public class HelpCommand implements Command {

    /**
     * Prints the full command reference to standard output.
     *
     * @param args ignored — {@code help} takes no arguments
     */
    @Override
    public void execute(String[] args) {
        System.out.println("The following commands are supported:");
        System.out.println("load \"file\" \"file2\"         ...   opens file(s) in a new session");
        System.out.println("add  \"file\"               adds an image to the current session");
        System.out.println("close                     closes the current session");
        System.out.println("save                      saves all images in the current session");
        System.out.println("save as \"file\"            saves the first image to a new file");
        System.out.println("session info              shows info about the current session");
        System.out.println("switch <session>          switches to another session by ID");
        System.out.println("grayscale                 converts colour images to greyscale");
        System.out.println("monochrome                converts images to black and white");
        System.out.println("negative                  inverts pixel values");
        System.out.println("rotate left|right         rotates images 90 degrees");
        System.out.println("undo                      removes the last queued transformation");
        System.out.println("collage <dir> <img1> <img2> <collage name>  creates a collage");
        System.out.println("help                      prints this information");
        System.out.println("exit                      exits the program");
    }
}
