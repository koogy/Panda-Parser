package fr.uparis.pandaparser.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Regroup all RegExOperations */
public class RegExUtils {
    /**
     * private constructor
     */
    private RegExUtils() {
    }

    /**
     * Replace all pattern matcher with the replacement
     *
     * @param text              text to modify
     * @param regularExpression use the regularExpression to match pattern in the fileContent
     * @param replacement       replace all matcher "regularExpression" with the "replacement"
     * @return String file content with all matcher "regularExpression" replace by "replacement"
     */
    public static String usePatternToReplace(String text, String regularExpression, String replacement) {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll(replacement);
    }

    public static String convertInclude(String text) {
        return usePatternToReplace(text, "(\\{\\{ +include +\")([^\"]*)(\" +}})", "{% include \"$2\" %}");
    }

    public static String removeMetadataDot(String text) {
        return usePatternToReplace(text, "(\\{\\{ +metadata.)([^\"]*)( +}})", "{{ $2 }}");
    }

    public static String removeHeaderFromContent(String text) {
        return usePatternToReplace(text, "(?:\\+{3})((?:.|\\n)*?)(?:\\+{3})", "");
    }

    public static String getMetadataContent(String text) {
        Pattern pattern = Pattern.compile("(?:\\+{3})((?:.|\\n)*?)(?:\\+{3})");
        Matcher m = pattern.matcher(text);
        String data = "";
        while (m.find()) {
            data = m.group(1);
        }
        return data;
    }


}
