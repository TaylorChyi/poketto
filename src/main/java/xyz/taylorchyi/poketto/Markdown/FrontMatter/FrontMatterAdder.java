package xyz.taylorchyi.poketto.Markdown.FrontMatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import xyz.taylorchyi.poketto.Config.ConfigManager;
import xyz.taylorchyi.poketto.Config.HexoProject;
import xyz.taylorchyi.poketto.File.FileTool;


public class FrontMatterAdder {
    public static void main(String[] args) {
        try {
            ConfigManager configManager = new ConfigManager();

            HexoProject hexoProject = configManager.getHexoProject();
            String hexoBlogFolderPath = hexoProject.getBlogFolderPath();
            addFrontMatterToFilesInFolder(hexoBlogFolderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addFrontMatterToFilesInFolder(String folderPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".md"))
                .forEach(path -> {
                    try {
                        processFile(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
    }

    public static boolean containsFrontMatter(String content) {
        String frontMatterPattern = "\\A---\\s*title:\\s*[^\\n]+\\s*date:\\s*[^\\n]+\\s*tags:(\\s*-(\\s*[^\\n]+))*\\s*---";
        Pattern pattern = Pattern.compile(frontMatterPattern, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }


    public static void processFile(Path filePath) throws IOException {
        String content = new String(Files.readAllBytes(filePath));
        if (containsFrontMatter(content)) {
            System.out.println("File already has frontmatter: " + filePath);
            return;
        }
        String[] lines = content.split("\n");
        String title = FileTool.extractFileNameWithoutExtension(filePath);
        String tags = "";
        String date = "";

        for (String line : lines) {
            if (line.startsWith("标签： ")) {
                tags = String.join("\n- ", line.substring(4).split("#"));
            } else if (line.startsWith("时间： ")) {
                date = line.substring(4);
            }
            if (tags != "" && date != "") {
                break;
            }
        }

        if (date.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(new Date());
        }
        
        String updatedContent = "---\n" + 
                                "title: " + title + "\n" +
                                "date: " + date + "\n" +
                                "tags: " + tags + "\n" +
                                "---\n\n" + 
                                content;
        Files.write(filePath, updatedContent.getBytes());
        System.out.println("Frontmatter added to file: " + filePath);
    }
    
}
