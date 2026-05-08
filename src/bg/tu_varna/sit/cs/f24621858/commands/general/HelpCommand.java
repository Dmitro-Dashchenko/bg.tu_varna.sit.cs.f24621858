package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;

public class HelpCommand implements Command {

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
        System.out.println("collage <dir> <img1> <img2> <out>  creates a collage");
        System.out.println("help                      prints this information");
        System.out.println("exit                      exits the program");
    }
}
