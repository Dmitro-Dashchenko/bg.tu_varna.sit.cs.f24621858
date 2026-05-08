package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;

public class ExitCommand implements Command {

    @Override
    public void execute(String[] args) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
