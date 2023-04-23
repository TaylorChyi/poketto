package xyz.taylorchyi.poketto.Markdown.Synchronizer;

import java.util.List;

import xyz.taylorchyi.poketto.Common.Pair;
import xyz.taylorchyi.poketto.File.MdHandler;

public class Synchronizer {

    public static void compareAndSyncFolders(String hexoBlogFolderPath, List<String> obsidianBlogFolderPaths) {
        Pair<List<String>, List<String>> result = MdHandler.compareMDFiles(hexoBlogFolderPath, obsidianBlogFolderPaths);
        MdHandler.deleteFilesFromFolder(result.first());
        MdHandler.copyFilesToFolder(result.second(), hexoBlogFolderPath);
    }


}
