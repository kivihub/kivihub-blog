package repo.tools.internal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repo.tools.Hexo.blogDir;
import static repo.tools.Hexo.repoDir;

public class HexoTitle {
    File srcFile;
    File tarFile;
    String title;
    int moreAfterLine;

    public HexoTitle(File srcFile, File tarFile, int moreAfterLine) throws IOException {
        this.srcFile = srcFile;
        this.tarFile = tarFile;
        this.moreAfterLine = moreAfterLine;
        this.title = FileUtils.readFileToString(new File(repoDir, "src/main/resources/hexo/title.yaml"));
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

    public HexoTitle FillCover() throws IOException {
        String content = FileUtils.readFileToString(tarFile);
        String imagePath = "";

        Pattern pattern = Pattern.compile("src=\"/?pic/([^\"]+)");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            imagePath = "/pic/" + matcher.group(1);
            System.out.println("首个图片路径是: " + imagePath);
        } else {
            pattern = Pattern.compile("!\\[[^]]+]\\(pic/([^)]+)\\)");
            matcher = pattern.matcher(content);
            if (matcher.find()) {
                imagePath = "/pic/" + matcher.group(1);
                System.out.println("首个图片路径是: " + imagePath);
            }
        }


        title = title.replace("{firstPic}", imagePath);
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
                if (!inCodeBlock && i+1 >= moreAfterLine && !append) {
                    sb.append("<!-- more -->\n");
                    append = true;
                }
            }
            content = sb.toString();
        }

        // Step2: fix picture path
        content = content.replace("src=\"pic/", "src=\"/pic/");

        FileUtils.writeStringToFile(tarFile, title + "\n" + content);
    }
}
