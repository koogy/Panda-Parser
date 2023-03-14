package fr.uparis.pandaparser.utils;


import fr.uparis.pandaparser.config.Extension;
import fr.uparis.pandaparser.core.build.site.StaticFileType;
import lombok.NonNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FilesUtils class, help methods to manipulate files.
 *
 * @author pada-parser group
 * @version 1.0.0
 * @since Fev 2022
 */
public class FilesUtils {

    /**
     * private constructor
     */
    private FilesUtils() {
    }


    /**
     * Read the content of a file
     *
     * @param filePath the file name we want to read
     * @return fileContent
     */
    public static String getFileContent(@NonNull final String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.lines().forEach(line -> sb.append(line).append("\n"));
        }
        if (sb.isEmpty())
            return "";
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Create html file from content
     *
     * @param filePath file path
     * @param content  text to write in file
     */
    public static void createFileFromContent(@NonNull final String filePath, @NonNull final String content) throws IOException {
        File file = new File(filePath);
        createDirectoryIfNotExiste(file.getParentFile().getPath());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    /**
     * Create directory if not existe
     */
    public static void createDirectoryIfNotExiste(final String path) throws IOException {
        File directory = new File(path);
        if (!directory.exists())
            Files.createDirectory(Path.of(path));
    }


    /**
     * List files within a directory.
     * <p>
     * try-with-resources statement to  make sure the stream will be closed right
     * after the stream operations are completed.
     * </p>
     *
     * @param directory directory
     * @return set of files path
     * @throws IOException if the directory doesn't exist.
     */
    public static Set<String> getAllFilesFromDirectory(@NonNull final String directory) throws IOException {
        return getAllFilesFromDirectoryRec(directory);
    }

    public static Set<String> getAllFilesFromDirectoryRec(String directory) throws IOException {
        Set<String> files = new HashSet<>();
        try (Stream<Path> stream = Files.list(Paths.get(directory))) {
            Map<Boolean, List<Path>> directoryAndFilesPartition = stream
                    .collect(Collectors.partitioningBy(Files::isDirectory));
            files.addAll(directoryAndFilesPartition.get(false).stream()
                    .map(Path::toString).collect(Collectors.toSet()));
            for (Path path : directoryAndFilesPartition.get(true)) {
                files.addAll(getAllFilesFromDirectoryRec(path.toString()));
            }
        }
        return files;
    }

    /**
     * List specific files within a directory.
     *
     * @param directory directory path
     * @param extension extension (.txt, .md, .html, ...)
     * @return set of file paths
     * @throws IOException if the directory doesn't exist.
     */
    public static Set<String> getAllFilesFromDirectory(@NonNull final String directory, @NonNull Extension extension) throws IOException {
        return getAllFilesFromDirectory(directory)
                .stream().filter(file -> file.endsWith(extension.getExtensionName()))
                .collect(Collectors.toSet());
    }

    /**
     * List static files within a directory.
     *
     * @param directory directory path
     * @return set of file paths
     * @throws IOException if the directory doesn't exist.
     */
    public static Set<String> getAllStaticFilesFromDirectory(@NonNull final String directory) throws IOException {
        return getAllFilesFromDirectory(directory)
                .stream().filter(StaticFileType::isStatic)
                .collect(Collectors.toSet());
    }

    /**
     * List static files within a directory.
     *
     * @param input  directory source path
     * @param output directory destination path
     * @throws IOException if the directory doesn't exist.
     */
    public static void copyFileFromInputToOutput(final String input, final String output) throws IOException {
        Files.copy(Paths.get(input), new FileOutputStream(output));
    }

    /**
     * Get file name from path
     *
     * @param filePath path to the file.
     * @return file name
     */
    public static String getFileName(@NonNull final String filePath) {
        return Path.of(filePath).getFileName().toString();
    }

    /**
     * get html filename from md filename.
     * example: file.md -> file.html
     *
     * @param mdFilename html filename
     * @return html filename
     */
    public static String getHtmlFilenameFromMdFile(@NonNull final String mdFilename) {
        return mdFilename.substring(0, mdFilename.length() - Extension.MD.getExtensionName().length())
                + Extension.HTML.getExtensionName();
    }

    public static String getFileExtension(@NonNull final String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }


    /**
     * get last modification time of input file
     *
     * @param filePath path to the file.
     * @return last modified time
     * @throws IOException if the directory doesn't exist.
     */
    public static long getFileLastModificationDate(@NonNull final String filePath) throws IOException {
        return Files.readAttributes(Path.of(filePath), BasicFileAttributes.class).lastModifiedTime().toMillis();
    }


    /**
     * Replace all pattern matcher with the replacement
     *
     * @param fileContent       fileContent
     * @param regularExpression use the regularExpression to match pattern in the fileContent
     * @param replacement       replace all matcher "regularExpression" with the "replacement"
     * @return String file content with all matcher "regularExpression" replace by "replacement"
     */
    public static String usePatternToReplace(String fileContent, String regularExpression, String replacement) {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(fileContent);
        return matcher.replaceAll(replacement);
    }
}


