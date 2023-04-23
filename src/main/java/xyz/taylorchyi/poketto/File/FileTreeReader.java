package xyz.taylorchyi.poketto.File;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTreeReader {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("java", "py", "js", "c", "cpp", "cs", "rb", "php", "go");
    
    public static void main(String[] args) {
        String path = "/Users/taylor/WorkSpace/poketto/";

        Map<String, String> filesContent = new HashMap<>();
        FileTreeGenerator.printFileTree(path);
        System.out.println("==================================");
        try {
            readFilesContent(new File(path), filesContent);
            filesContent.forEach((filePath, content) -> {
                System.out.println("File: " + filePath);
                System.out.println("Content: ");
                System.out.println(content);
                System.out.println("==================================");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFilesContent(File folder, Map<String, String> filesContent) throws IOException {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readFilesContent(file, filesContent);
                } else {
                    String fileName = file.getName();
                    int lastDotIndex = fileName.lastIndexOf('.');
                    if (lastDotIndex > 0) {
                        String fileExtension = fileName.substring(lastDotIndex + 1).toLowerCase();
                        if (ALLOWED_EXTENSIONS.contains(fileExtension)) {
                            Path path = Paths.get(file.getAbsolutePath());
                            String content = new String(Files.readAllBytes(path));
                            filesContent.put(file.getAbsolutePath(), content);
                        }
                    }
                }
            }
        }
    }
}

