package repo.tools.internal.hexo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import repo.tools.internal.utils.Cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repo.tools.Hexo.REPO_DIR;
import static repo.tools.internal.hexo.Util.*;

public class PostTitle {
    private static final String TITLE_TPL = "src/main/resources/hexo/title_post.yml";

    private final Posts posts;
    private final File tarFile;
    private String title;

    public PostTitle(Posts posts, File tarFile) throws IOException {
        this.posts = posts;
        this.tarFile = tarFile;
        this.title = FileUtils.readFileToString(new File(REPO_DIR, TITLE_TPL));
    }

    public PostTitle FillTitleName() {
        title = title.replace("{title}", tarFile.getName().replace(".md", ""));
        return this;
    }

    public PostTitle FillCreateDate(String srcFilePath) {
        String firstLine;
        try (BufferedReader reader = Files.newBufferedReader(tarFile.toPath())) {
            firstLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String displayTime = "";

        // 指定日期
        Matcher matcher = Pattern.compile("<!--\\s*date:\\s*([\\d. :]+)\\s*-->").matcher(firstLine);
        if (matcher.find()) {
            displayTime = matcher.group(1);
        }

        // 指定最近变更时间
        if (Pattern.compile("<!--\\s*date:\\s*modify\\s*-->").matcher(firstLine).find()) {
            displayTime = Cmd.RunSilent(String.format("cd %s; git log --format='%%ai' --follow -- '%s' | head -1;", REPO_DIR.getAbsolutePath(), srcFilePath));
        }

        // 默认为创建时间
        if (displayTime.isEmpty()) {
            displayTime = Cmd.RunSilent(String.format("cd %s; git log --format='%%ai' --follow -- '%s' | tail -1;", REPO_DIR.getAbsolutePath(), srcFilePath));
        }

        title = title.replace("{date}", StringUtils.trim(displayTime));
        return this;
    }

    public PostTitle FillCategory(String srcDirPath) {
        String relativePath = srcDirPath.replace(new File(REPO_DIR, "blog").getAbsolutePath() + "/", "");
        relativePath = relativePath.replace("/", ", ");
        relativePath = relativePath.replaceAll("\\d+\\.", "");
        title = title.replace("{categories}", relativePath);
        return this;
    }

    public PostTitle FillCoverImage() throws IOException {
        String content = FileUtils.readFileToString(tarFile);
        String imageRelativePath = "";

        // Step1: 获取文中第一个图片
        Matcher matcher = htmlPattern.matcher(content);
        if (matcher.find()) {
            imageRelativePath = matcher.group(1);
        } else {
            matcher = mdPattern.matcher(content);
            if (matcher.find()) {
                imageRelativePath = matcher.group(1);
            }
        }

        // Step2: 设置封面字段
        if (imageRelativePath.isEmpty()) {
            title = title.replace("{firstPic}", "");
        } else {
            String thumbnailDirStr = String.format("/thumbnail/%s", Util.removeSuffix(tarFile.getName()));
            File thumbnailDir = new File(posts.sourceDir, thumbnailDirStr);
            File usedPic = new File(removeSuffix(tarFile.getAbsolutePath()) + "/pic/" + imageRelativePath);
            FileUtils.copyFileToDirectory(usedPic, thumbnailDir);

            String thumbnailPic = String.format("%s/%s", thumbnailDirStr, imageRelativePath);
            title = title.replace("{firstPic}", thumbnailPic);
        }
        return this;
    }

    public void Save() throws IOException {
        FileUtils.writeStringToFile(tarFile, title + "\n" + FileUtils.readFileToString(tarFile));
    }
}
