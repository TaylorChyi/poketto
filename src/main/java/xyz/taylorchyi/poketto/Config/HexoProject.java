package xyz.taylorchyi.poketto.Config;

public class HexoProject {
    private String basePath;
    private String blogFolderPath;

    // Getters and setters

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBlogFolderPath() {
        return blogFolderPath;
    }

    public void setBlogFolderPath(String blogFolderPath) {
        this.blogFolderPath = this.basePath + blogFolderPath;
    }
}
