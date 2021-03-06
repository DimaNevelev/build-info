package utils

import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils

/**
 * @author Aviad Shikloshi
 */
class PropertyFileAggregator {

    /**
     * Merge the default BuildInfo and the specific test config file
     *
     * @param {@link ConfigObject} groovy object that represents the test config properties file
     * @return {@link Properties} Object
     */
    static Properties aggregateBuildProperties(ConfigObject config) {
        Properties aggregatedProperties = new Properties()
        String filePath = this.getClass().getResource("/conf/defaultBuildInfo.properties").path
        aggregatedProperties.load(new FileInputStream(filePath))
        Map flat = config.getProperty("buildInfoProperties").flatten()
        aggregatedProperties.putAll(flat)

        aggregatedProperties
    }

    /**
     * Create buildInfo properties file under the machine temp directory, with the unique random name
     *
     * @param config {@link Properties} representing the test config properties file
     * @return path to the property file
     */
    static String toFile(Properties buildProperties){
        File tempFile = File.createTempFile("buildInfo", ".properties");
        FileOutputStream stream = new FileOutputStream(tempFile);
        try {
            buildProperties.store(stream, StringUtils.EMPTY);
        } finally {
            IOUtils.closeQuietly(stream);
        }

        tempFile.path
    }
}
