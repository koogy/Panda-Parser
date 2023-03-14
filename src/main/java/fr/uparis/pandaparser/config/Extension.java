package fr.uparis.pandaparser.config;

/**
 * Extension
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
public enum Extension {
    MD(".md"), TOML(".toml"), HTML(".html");

    private final String extensionName;

    Extension(String extension) {
        this.extensionName = extension;
    }

    /**
     * @return format de extension
     */
    public String getExtensionName() {
        return extensionName;
    }
}
