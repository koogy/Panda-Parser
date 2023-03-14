package fr.uparis.pandaparser.application.cmd;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.incremental.HistoryManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "build", description = "Translation of one or more Markdown files into HTML5 file(s)", mixinStandardHelpOptions = true)
public class BuildCommand implements Callable<Integer> {


    @Option(names = {"-i", "--input-dir"}, paramLabel = "INPUT_DIR", description = "Specify a tree to process different from the current directory.")
    private String input = Config.DEFAULT_INPUT;

    @Option(names = {"-o", "--output-dir"}, paramLabel = "OUTPUT_DIR", description = "Specify where the result will be stored")
    private String output = Config.DEFAULT_OUTPUT;

    @Option(names = {"-j", "--jobs"}, paramLabel = "NB_JOBS", description = "Disable incremental")
    private Integer jobs = Config.DEFAULT_MACHINE_JOB;

    @Option(names = {"-w", "--watch"}, description = "Monitors the source files and automatically recompiles the site at each modification, with recalculation")
    private boolean watched;

    @Option(names = {"--rebuild-all"}, description = "All files will be rebuild")
    private boolean rebuildAll;


    @Override
    public Integer call() {
        try {
            HistoryManager.setHistoryManagerInstance(input, rebuildAll);
            this.setUpPandaParser().parse();
            HistoryManager.getInstance().save();
            return Config.EXIT_SUCCESS;
        } catch (Exception exception) {
            return Config.EXIT_FAILURE;
        }
    }

    private PandaParser setUpPandaParser() {
        return PandaParser.builder()
                .setInput(input).setOutput(output)
                .setJobs(jobs).isWatched(watched)
                .build();
    }
}
