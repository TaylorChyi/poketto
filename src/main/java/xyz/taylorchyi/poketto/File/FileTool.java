package xyz.taylorchyi.poketto.File;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTool {
    public static String extractFileNameWithoutExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(0, lastDotIndex);
        }
    }

    public static String extractFileNameWithoutExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(0, lastDotIndex);
        }
    }

    public static String extractFileNameWithoutPrefixPath(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastSeparatorIndex = fileName.lastIndexOf(File.separator);
        if (lastSeparatorIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(lastSeparatorIndex, fileName.length());
        }
    }

    public static String extractFileNameWithoutPrefixPath(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int lastSeparatorIndex = fileName.lastIndexOf(File.separator);
        if (lastSeparatorIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(lastSeparatorIndex, fileName.length());
        }
    }
}
