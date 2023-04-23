package xyz.taylorchyi.poketto.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.taylorchyi.poketto.Common.Pair;

public class MdHandler {

    public static void main(String[] args) {
        String directoryPath = "指定文件夹的路径"; // 替换为您要搜索的文件夹路径
        
        try {
            List<String> mdFilePaths = getMDFilePaths(directoryPath);
            // 输出所有找到的.md文件路径
            mdFilePaths.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
/**
 * 此函数返回给定目录路径中所有扩展名为 .md 的常规文件的文件路径列表。
 * 
 * @param directoryPath 目录路径是一个字符串，表示需要搜索markdown文件的目录路径。
 * @return 该方法返回一个 String 对象列表，这些对象是在指定目录及其子目录中找到的所有 Markdown 文件（.md 扩展名）的文件路径。
 */
    private static List<String> getMDFilePaths(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            List<String> mdFilePaths = new ArrayList<>();
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".md"))
                    .forEach(path -> mdFilePaths.add(path.toString()));

            return mdFilePaths;
        }
    }

/**
 * 该函数比较两个文件夹并返回第一个文件夹中存在但第二个文件夹中不存在的文件列表。
 * 
 * @param folder1 要比较的第一个文件夹中的文件路径列表。
 * @param folder2 不幸的是，提供的代码片段中缺少 folder2 的参数。您能否提供 folder2 的参数，以便我更好地帮助您？
 * @return `compareFolders` 方法返回一个 `List<String>`，其中包含存在于 `folder1` 但不存在于 `folder2` 中的所有文件的文件路径。
 */
    private static List<String> compareFolders(List<String> folder1, List<String> folder2) {
        Set<String> folder1Filenames = folder1.stream()
                .map(path -> Paths.get(path).getFileName().toString())
                .collect(Collectors.toSet());

        Set<String> folder2Filenames = folder2.stream()
                .map(path -> Paths.get(path).getFileName().toString())
                .collect(Collectors.toSet());

        folder1Filenames.removeAll(folder2Filenames);

        List<String> result = folder1.stream()
                .filter(path -> folder1Filenames.contains(Paths.get(path).getFileName().toString()))
                .collect(Collectors.toList());

        return result;
    }

    public static Pair<List<String>, List<String>> compareMDFiles(String BlogFolderPath, List<String> StorageFolderPaths) {
        Pair<List<String>, List<String>> result;

        try {
            List<String> taylorMDFilePaths = getMDFilePaths(BlogFolderPath);
            List<String> obsidianMDFilePaths = new ArrayList<String>();
            
            for (String StorageFolderPath : StorageFolderPaths) {
                obsidianMDFilePaths.addAll(getMDFilePaths(StorageFolderPath));
            }

            List<String> taylorNotInObsidian = compareFolders(taylorMDFilePaths, obsidianMDFilePaths);
            List<String> obsidianNotInTaylor = compareFolders(obsidianMDFilePaths, taylorMDFilePaths);

            System.out.println("TaylorBlogFolder中不存在于ObsidianFolder的.md文件：");
            taylorNotInObsidian.forEach(System.out::println);

            System.out.println("\nObsidianFolder中不存在于TaylorBlogFolder的.md文件：");
            obsidianNotInTaylor.forEach(System.out::println);

            result = new Pair<>(taylorNotInObsidian, obsidianNotInTaylor);

        } catch (IOException e) {
            e.printStackTrace();
            result = new Pair<>(Collections.emptyList(), Collections.emptyList());
        }

        return result;
    }

    public static void deleteFilesFromFolder(List<String> filesToDelete) {
        filesToDelete.forEach(file -> {
            try {
                Files.deleteIfExists(Paths.get(file));
                System.out.println("已删除文件：" + file);
            } catch (IOException e) {
                System.err.println("无法删除文件：" + file);
                e.printStackTrace();
            }
        });
    }

    public static void copyFilesToFolder(List<String> filesToCopy, String targetFolder) {
        filesToCopy.forEach(file -> {
            Path source = Paths.get(file);
            Path target = Paths.get(targetFolder, source.getFileName().toString());
            try {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("已复制文件：" + source + " 到 " + target);
            } catch (IOException e) {
                System.err.println("无法复制文件：" + source);
                e.printStackTrace();
            }
        });
    }
}
