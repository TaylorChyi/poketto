package xyz.taylorchyi.poketto.Config;

import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigManager {
    private final HexoProject hexoProject;
    private final ObsidianProject obsidianProject;
    private final String downloadPath;

    public ConfigManager() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(Paths.get(Constants.CONFIG_FILE_PATH).toFile(), Config.class);
        this.hexoProject = config.getHexoProject();
        this.obsidianProject = config.getObsidianProject();
        this.downloadPath = config.getDownloadPath();
    }

    public HexoProject getHexoProject() {
        return hexoProject;
    }

    public ObsidianProject getObsidianProject() {
        return obsidianProject;
    }

    public String getDownloadPath() {
        return  downloadPath;
    }

}
