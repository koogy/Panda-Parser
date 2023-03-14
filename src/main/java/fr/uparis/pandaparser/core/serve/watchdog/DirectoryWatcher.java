package fr.uparis.pandaparser.core.serve.watchdog;

import fr.uparis.pandaparser.core.build.PandaParser;
import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Watchdog for directory.
 */
@Log
public class DirectoryWatcher implements Callable<String> {

    private final String inputDirectory;
    private final String output;
    private final Map<WatchKey, Path> folders = new ConcurrentHashMap<>();
    private WatchService watchService;


    /**
     * Construct a new {@code DirectoryWatcher} for the given directory.
     *
     * @param inputDirectory the directory to watch
     * @param output         the output directory
     */
    public DirectoryWatcher(String inputDirectory, String output) {
        this.inputDirectory = inputDirectory;
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        Path path = Path.of(this.inputDirectory);
        registerFolder(path);
        path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        return this.watch();
    }

    /**
     * Watch the directory.
     *
     * @return the output directory
     */
    private String watch() {
        try {
            WatchKey watchKey;
            while ((watchKey = this.watchService.take()) != null) {
                watchKey.pollEvents().forEach(event -> {
                    if (!event.context().toString().contains("~")) {
                        PandaParser.builder()
                                .setInput(inputDirectory)
                                .setOutput(output)
                                .build()
                                .parse();
                    }
                });
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return "end - watch";
    }

    /**
     * Register the given directory, and all its subdirectories, with the WatchService.
     */
    private void registerFolder(final Path folder) throws IOException {
        // register directory and subdirectories
        Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                WatchKey key = dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                log.info("registering folder " + dir);
                folders.put(key, dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
