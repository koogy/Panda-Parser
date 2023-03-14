package fr.uparis.pandaparser.application.cmd;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.build.incremental.HistoryManager;
import fr.uparis.pandaparser.core.serve.Serve;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "serve", description = "Compile le site puis lance un serveur HTTP sur le port 8080 par défaut.", mixinStandardHelpOptions = true)
public class ServeCommand implements Callable<Integer> {

    @Option(names = {"-i", "--input-dir"}, paramLabel = "INPUT_DIR", description = "Permet de spécifier une arborescence à traiter différente du répertoire courant.")
    private String input = Config.DEFAULT_INPUT;

    @Option(names = {"-o", "--output-dir"}, paramLabel = "OUTPUT_DIR", description = "Permet de spécifier où le résultat sera stocké ")
    private String output = Config.DEFAULT_OUTPUT;

    @Option(names = {"-j", "--jobs"}, paramLabel = "NB_JOBS", description = "Désactive l’incrémentalité")
    private Integer jobs = Config.DEFAULT_MACHINE_JOB;

    @Option(names = {"-w", "--watch"}, description = "Surveille les fichiers sources et recompile automatiquement le site à chaque modification, avec recalcul")
    private boolean watched;

    @Option(names = {"-p", "--port"}, description = "Spécifie un autre port que 8080")
    private Integer port = Config.DEFAULT_PORT;


    @Override
    public Integer call() {
        try {
            HistoryManager.setHistoryManagerInstance(input, false);
            Serve serve = new Serve(input, output, null, watched, jobs, ParserType.SERVE, port);
            serve.start();
            HistoryManager.getInstance().save();
            return Config.EXIT_SUCCESS;
        } catch (Exception e) {
            return Config.EXIT_FAILURE;
        }
    }
}
