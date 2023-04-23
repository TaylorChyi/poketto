package xyz.taylorchyi.poketto.Config;

import java.util.List;
import java.util.stream.Collectors;

public class ObsidianProject {
    private String basePath;
    private List<String> blogFolderPaths;

    // Getters and setters

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<String> getBlogFolderPaths() {
        return blogFolderPaths;
    }

    public void setBlogFolderPaths(List<String> blogFolderPaths) {
        this.blogFolderPaths = blogFolderPaths.stream()
                                          .map(blogFolderPath -> this.basePath + blogFolderPath)
                                          .collect(Collectors.toList());
    }
}
