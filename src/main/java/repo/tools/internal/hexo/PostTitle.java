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
        String create = "";
        try (BufferedReader reader = Files.newBufferedReader(tarFile.toPath())) {
            Pattern pattern = Pattern.compile("<!--\\s*date:\\s*([\\d\\. :]+)\\s*-->");
            Matcher matcher = pattern.matcher(reader.readLine());
            if (matcher.find()) {
                create = matcher.group(1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (create.isEmpty()) {
            create = Cmd.RunSilent(String.format("cd %s; git log --format='%%ai' --follow -- '%s' | tail -1;", REPO_DIR.getAbsolutePath(), srcFilePath));
        }

        title = title.replace("{date}", StringUtils.trim(create));
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
