package fr.uparis.pandaparser.config;

import java.io.File;

public class TestConfig {

    public static final String INPUT_TEST_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "builder-test";
    public static final String OUTPUT_TEST_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test-output";
    public static final String MD_FILE = INPUT_TEST_DIR + File.separator + Config.DEFAULT_CONTENT_DIR + Config.DEFAULT_INDEX_NAME;

    public static final String INPUT_UNIT_MD_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "md-file-test";
    public static final String EMPTY_MD_TEST = INPUT_UNIT_MD_DIR + File.separator + "EmptyMarkDown.md";
    public static final String BASIC_MD_TEST = INPUT_UNIT_MD_DIR + File.separator + "BasicMarkDown.md";
    public static final String LOREM_IPSUM_MD_TEST = INPUT_UNIT_MD_DIR + File.separator + "LoremIpsumMarkDown.md";

    public static final String TEMPLATE_TEST_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "template-test";
    public static final String TEMPLATE_TEST_CONTENT_DIR = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "template-test" + File.separator + "content";
    public static final String EMPTY_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "empty-template.html";
    public static final String INCLUDE_P_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "include-P-template.html";
    public static final String METADATA_TITLE_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "metadata-title-template.html";
    public static final String METADATA_DATE_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "metadata-date-template.html";
    public static final String SIMPLE_TEXT_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "simple-text-template.html";
    public static final String NOT_EXISTING_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "not-existing-template.html";
    public static final String LANDING_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "landing-template.html";
    public static final String ARRAY_TEMPLATE_TEST = TEMPLATE_TEST_DIR + File.separator + "array-template.html";
    public static final String BAKERY_MD_TEST = TEMPLATE_TEST_CONTENT_DIR + File.separator + "bakery.md";
    public static final String DICTIONARY_MD_TEST = TEMPLATE_TEST_CONTENT_DIR + File.separator + "dictionary_test.md";
    public static final String ARRAY_MD_TEST = TEMPLATE_TEST_CONTENT_DIR + File.separator + "array_test.md";


    public static final String TEST_MP3_PATH = "" + File.separator + "static" + File.separator + "01.Ren'ai_Circulation.mp3";
    public static final String TEST_JPG_PATH = "" + File.separator + "static" + File.separator + "koharu.jpg";
    public static final String TEST_CSS_PATH = "" + File.separator + "static" + File.separator + "exampleTest.css";
    public static final String TEST_MD_STATIC_PATH = "" + File.separator + "static" + File.separator + "exampleTest.md";
    public static final String TEST_HISTORY_FILE_PATH = INPUT_TEST_DIR + "historyFile.ser";

    public static final String JAVA_EXTENSION_FILE = "NotStaticFile.java";
    public static final String NOT_EXISTING_DIR = "not-existing-dir";
    public static final String NOT_EXISTING_FILE = "not-existing-file.md";
    public static final String NOT_EXISTING_TEMPLATE = "not-existing-template.html";
}
