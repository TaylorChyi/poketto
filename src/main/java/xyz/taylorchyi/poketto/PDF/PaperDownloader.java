package xyz.taylorchyi.poketto.PDF;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PaperDownloader {

    private static final String CROSSREF_API_URL = "https://doi.org/";
    private static final String SCI_HUB_URL = "https://sci-hub.se/";

    public static void main(String[] args) {
        List<String> dois = List.of("10.1007/s10703-020-00243-7", "10.1007/978-3-319-94496-8_8");
        downloadPapers(dois, "downloads");
    }

    public static void downloadPapers(List<String> dois, String downloadFolder) {
        OkHttpClient client = new OkHttpClient();
        for (String doi : dois) {
            try {
                // 获取论文标题
                String url = CROSSREF_API_URL + doi;
                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute()) {
                    String location = response.header("Location");
                    if (location != null) {
                        // 使用Sci-Hub下载论文PDF
                        url = SCI_HUB_URL + location;
                        request = new Request.Builder().url(url).build();
                        try (Response pdfResponse = client.newCall(request).execute()) {
                            InputStream inputStream = pdfResponse.body().byteStream();
                            Path outputPath = Paths.get(downloadFolder, doi.replace("/", "_") + ".pdf");
                            try (OutputStream outputStream = new FileOutputStream(outputPath.toFile())) {
                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                System.out.println("Downloaded PDF: " + outputPath);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to download PDF for DOI: " + doi);
                e.printStackTrace();
            }
        }
    }
}

