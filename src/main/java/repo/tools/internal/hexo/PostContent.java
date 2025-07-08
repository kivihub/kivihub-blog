package repo.tools.internal.hexo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostContent {
    private final File tarFile;
    private String content;

    public PostContent(File tarFile) throws IOException {
        this.tarFile = tarFile;
        this.content = FileUtils.readFileToString(tarFile);
    }

    /**
     * 处理图片格式：html → markdown。
     * <p>
     * hexo generate不处理img的路径，致使图片不显示
     * </p>
     * 参见<a href="https://hexo.io/zh-cn/docs/asset-folders#%E4%BD%BF%E7%94%A8-Markdown-%E5%B5%8C%E5%85%A5%E5%9B%BE%E7%89%87">使用-Markdown-嵌入图片</a>
     */
    public PostContent resetImage() throws IOException {
        content = Util.replaceImageTags(content);
        return this;
    }

    public PostContent insertMoreTag(int moreAfterLine) throws IOException {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(content);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();

        if (lines.size() >= moreAfterLine) {
            StringBuilder sb = new StringBuilder();
            boolean inCodeBlock = false;
            boolean inTable = false;
            boolean append = false;
            for (int i = 0; i < lines.size(); i++) {
                sb.append(lines.get(i)).append("\n");
                if (lines.get(i).startsWith("```")) {
                    inCodeBlock = !inCodeBlock;
                }
                inTable = lines.get(i).startsWith("| ");
                if (!inTable && !inCodeBlock && i + 1 >= moreAfterLine && !append) {
                    sb.append("<!-- more -->\n");
                    append = true;
                }
            }
            content = sb.toString();
        }
        return this;
    }

    public void Save() throws IOException {
        FileUtils.writeStringToFile(tarFile, content);
    }
}
