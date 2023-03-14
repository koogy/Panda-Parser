package fr.uparis.pandaparser.core.serve;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.serve.http.HttpPandaParserServer;
import fr.uparis.pandaparser.core.serve.watchdog.DirectoryWatcher;
import lombok.Getter;
import lombok.extern.java.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.uparis.pandaparser.config.Config.DEFAULT_CONTENT_DIR;

@Log
@Getter
public class Serve extends PandaParser {

    private final int port;
    private final ExecutorService watchDogExecutor = Executors.newSingleThreadExecutor();

    /**
     * Constructor.
     *
     * @param port the port
     */
    public Serve(String input, String output, String template, boolean watch, int jobs, ParserType type, int port) {
        super(input, output, template, watch, jobs, type);
        this.port = port;
    }

    @Override
    public void parse() {
        PandaParser.builder()
                .setInput(input).setOutput(output)
                .setJobs(jobs).isWatched(watch)
                .build().parse();
    }

    /**
     * Start the server
     */
    public void start() {
        try {
            this.parse();
            HttpPandaParserServer httpPandaParserServer = new HttpPandaParserServer(this.port, this.output + DEFAULT_CONTENT_DIR);
            watchDog();
            httpPandaParserServer.start();
            this.watchDogExecutor.shutdown();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Watch the directory for changes
     */
    private void watchDog() {
        this.watchDogExecutor.submit(new DirectoryWatcher(input, output));
        log.info("watch dog Started");
    }
}
