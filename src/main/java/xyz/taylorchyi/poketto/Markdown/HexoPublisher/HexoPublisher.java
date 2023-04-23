package xyz.taylorchyi.poketto.Markdown.HexoPublisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import xyz.taylorchyi.poketto.Config.ConfigManager;
import xyz.taylorchyi.poketto.Config.HexoProject;
import xyz.taylorchyi.poketto.Config.ObsidianProject;
import xyz.taylorchyi.poketto.Markdown.FrontMatter.FrontMatterAdder;
import xyz.taylorchyi.poketto.Markdown.Synchronizer.Synchronizer;

public class HexoPublisher {
    public static void main(String[] args) {

        try {
            ConfigManager configManager = new ConfigManager();

            HexoProject hexoProject = configManager.getHexoProject();
            ObsidianProject obsidianProject = configManager.getObsidianProject();

            String hexoBlogFolderPath = hexoProject.getBlogFolderPath();

            List<String> obsidianBlogFolderPaths = obsidianProject.getBlogFolderPaths();

            Synchronizer.compareAndSyncFolders(hexoBlogFolderPath, obsidianBlogFolderPaths);

            FrontMatterAdder.addFrontMatterToFilesInFolder(hexoBlogFolderPath);

            String hexoBasePath = hexoProject.getBasePath();
            publishHexoProject(hexoBasePath);

        } catch (IOException e) {
            System.err.println("无法读取配置文件");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
            e.printStackTrace();
        }
    }

    public static void publishHexoProject(String hexoProjectPath) throws IOException, InterruptedException {
        ProcessBuilder generateProcessBuilder = new ProcessBuilder("hexo", "generate");
        generateProcessBuilder.directory(new File(hexoProjectPath));
        generateProcessBuilder.redirectErrorStream(true);

        Process generateProcess = generateProcessBuilder.start();
        printProcessOutput(generateProcess);
        generateProcess.waitFor();

        ProcessBuilder deployProcessBuilder = new ProcessBuilder("hexo", "deploy");
        deployProcessBuilder.directory(new File(hexoProjectPath));
        deployProcessBuilder.redirectErrorStream(true);

        Process deployProcess = deployProcessBuilder.start();
        printProcessOutput(deployProcess);
        deployProcess.waitFor();
    }

    private static void printProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
