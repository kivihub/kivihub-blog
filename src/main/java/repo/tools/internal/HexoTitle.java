package repo.tools.internal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import repo.tools.Hexo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repo.tools.Hexo.blogDir;
import static repo.tools.Hexo.repoDir;

public class HexoTitle {
    public static Pattern htmlPattern = Pattern.compile("src=\"/?pic/([^\"]+)");
    public static Pattern mdPattern = Pattern.compile("!\\[[^]]+]\\(pic/([^)]+)\\)");
    Hexo.Post post;
    File srcFile;
    File tarFile;
    String title;
    int moreAfterLine;

    public HexoTitle(Hexo.Post post, File srcFile, File tarFile, int moreAfterLine) throws IOException {
        this.srcFile = srcFile;
        this.tarFile = tarFile;
        this.moreAfterLine = moreAfterLine;
        this.title = FileUtils.readFileToString(new File(repoDir, "src/main/resources/hexo/title.yml"));
        this.post = post;
    }

    public HexoTitle FillTitle() {
        title = title.replace("{title}", tarFile.getName().replace(".md", ""));
        return this;
    }

    public HexoTitle FillDate() {
        String createAt = Cmd.Run(String.format("cd %s; git log --format='%%ai' --follow -- '%s' | tail -1;", repoDir.getAbsolutePath(), srcFile.getAbsolutePath()));
        title = title.replace("{date}", StringUtils.trim(createAt));
        return this;
    }

    public HexoTitle FillCate() {
        String relativePath = srcFile.getParentFile().getAbsolutePath().replace(blogDir.getAbsolutePath() + "/", "");
        relativePath = relativePath.replace("/", ", ");
        relativePath = relativePath.replaceAll("\\d+\\.", "");
        title = title.replace("{categories}", relativePath);
        return this;
    }

    // 封面图片
    public HexoTitle FillCover() throws IOException {
        String content = FileUtils.readFileToString(tarFile);
        String imagePath = "";

        // Step1: 获取文中第一个图片
        Matcher matcher = htmlPattern.matcher(content);
        if (matcher.find()) {
            imagePath = matcher.group(1);
        } else {
            matcher = mdPattern.matcher(content);
            if (matcher.find()) {
                imagePath = matcher.group(1);
            }
        }

        // Step2: 设置封面字段
        if (imagePath.isEmpty()) {
            title = title.replace("{firstPic}", "");
        } else {
            String thumbnailDir = String.format("/thumbnail/%s", tarFile.getName().replace(".md", ""));
            FileUtils.copyFileToDirectory(new File(tarFile.getAbsolutePath().replace(".md", "") + "/pic/" + imagePath), new File(post.sourceDir, thumbnailDir));

            String thumbnailPic = String.format("%s/%s", thumbnailDir, imagePath);
            title = title.replace("{firstPic}", thumbnailPic);
        }
        return this;
    }

    public void Done() throws IOException {
        List<String> lines = FileUtils.readLines(tarFile);
        String content;

        // Step1: add <more>
        if (lines.size() < moreAfterLine) {
            content = FileUtils.readFileToString(tarFile);
        } else {
            StringBuilder sb = new StringBuilder();
            boolean inCodeBlock = false;
            boolean append = false;
            for (int i = 0; i < lines.size(); i++) {
                sb.append(lines.get(i)).append("\n");
                if (lines.get(i).startsWith("```")) {
                    inCodeBlock = !inCodeBlock;
                }
                if (!inCodeBlock && i + 1 >= moreAfterLine && !append) {
                    sb.append("<!-- more -->\n");
                    append = true;
                }
            }
            content = sb.toString();
        }

        // Step2: fix picture path
//        content = content.replace("src=\"pic/", "src=\"/pic/");

        FileUtils.writeStringToFile(tarFile, title + "\n" + content);
    }
}
