package xyz.taylorchyi.poketto.File;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xyz.taylorchyi.poketto.Clipboard.CopyToClipboard;

public class FileTreeGenerator {

    public static void main(String[] args) {
        String path = "/Users/taylor/WorkSpace/poketto/src/main";
        CopyFileTree(path);
    }

    public static void printFileTree(String path) {
        List<String> fileTree = generateFileTree(path);
        fileTree.forEach(System.out::println);
    }

    public static void CopyFileTree(String path) {
        List<String> fileTree = generateFileTree(path);
        String fileTreeString = String.join("\n", fileTree);
        CopyToClipboard.copyToClipboard(fileTreeString);
    }

    public static List<String> generateFileTree(String path) {
        File root = new File(path);
        List<String> fileTree = new ArrayList<>();
        generateFileTreeHelper(root, "", fileTree, true);
        return fileTree;
    }

    private static void generateFileTreeHelper(File file, String prefix, List<String> fileTree, boolean isLast) {
        String fileName = (isLast ? "└── " : "├── ") + file.getName();
        fileTree.add(prefix + fileName);

        if (file.isDirectory()) {
            String newPrefix = prefix + (isLast ? "    " : "│   ");
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    generateFileTreeHelper(files[i], newPrefix, fileTree, i == files.length - 1);
                }
            }
        }
    }
}

