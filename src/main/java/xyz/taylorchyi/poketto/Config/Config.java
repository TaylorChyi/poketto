package xyz.taylorchyi.poketto.Config;

public class Config {

    private HexoProject hexoProject;
    private ObsidianProject obsidianProject;
    private String downloadPath;

    public HexoProject getHexoProject() {
        return hexoProject;
    }

    public void setHexoProject(HexoProject hexoProject) {
        this.hexoProject = hexoProject;
    }

    public ObsidianProject getObsidianProject() {
        return obsidianProject;
    }

    public void setObsidianProject(ObsidianProject obsidianProject) {
        this.obsidianProject = obsidianProject;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }
}
