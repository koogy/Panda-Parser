package fr.uparis.pandaparser.application;


import fr.uparis.pandaparser.application.cmd.BuildCommand;
import fr.uparis.pandaparser.application.cmd.ServeCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

/**
 * Application - Main Class
 *
 * @author panda-parser group
 * @version 1.0.0
 * @see BuildCommand
 * @since Fev 2022
 */
@Command(name = "panda-parser", customSynopsis = {
        "panda-parser [CMD]... [OPTION]... [ARGUMENT]...",},
        subcommands = {HelpCommand.class, BuildCommand.class, ServeCommand.class},
        mixinStandardHelpOptions = true,
        version = "panda-parser version 1.0.0"
)
public class Application {

    private Application() {
    }

    /**
     * main method
     *
     * @param args args
     */
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new Application());
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
