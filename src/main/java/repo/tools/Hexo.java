package repo.tools;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import repo.tools.internal.Cmd;
import repo.tools.internal.HexoTitle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repo.tools.internal.HexoTitle.htmlPattern;
import static repo.tools.internal.HexoTitle.mdPattern;

public class Hexo {
    public static File repoDir = new File(System.getProperty("user.dir"));
    public static File blogDir = new File(repoDir, "blog");
    public static int moreAfterLine = 5;

    public static void main(String[] args) throws IOException {
        Post post = new Post(repoDir);
        post.Clear();
        FileUtils.listFiles(repoDir, new PostFilter(), TrueFileFilter.INSTANCE).forEach(post::AddPost);
        post.Generate();
        post.Server();
//        post.Deploy();
    }

    public static class PostFilter implements IOFileFilter {
        @Override
        public boolean accept(File file) {
            return file.toPath().toString().contains("kivihub-blog/blog") && file.getName().endsWith(".md");
        }

        @Override
        public boolean accept(File file, String s) {
            return false;
        }
    }

    public static class Post {
        public File hexoDir;
        public File postDir;
        public File sourceDir;
        public Set<String> pic = new HashSet<>();

        public Post(File repoDir) {
            hexoDir = new File(repoDir.getParentFile(), "hexo");
            postDir = new File(hexoDir, "source/_posts");
            sourceDir = new File(hexoDir, "source");
        }

        public void Clear() throws IOException {
            if (hexoDir.exists()) {
                FileUtils.forceDelete(hexoDir);
                System.out.println("Clear Hexo Dir.");
            }
            FileUtils.copyDirectoryToDirectory(new File(repoDir, "hexo"), repoDir.getParentFile());
        }

        public void AddPost(File srcfile) {
            try {
                FileUtils.copyFileToDirectory(srcfile, postDir);
                File tarFile = new File(postDir, srcfile.getName());

                // 去除数字前缀
                String newFileName = removeIndexPrefix(srcfile.getName());
                tarFile.renameTo(new File(postDir, newFileName));
                tarFile = new File(postDir, newFileName);

                // 移动使用到的图片
                List<String> pics = getUsedPic(FileUtils.readFileToString(tarFile));
                File srcPicDir = new File(srcfile.getParentFile(), "pic");
                for (String pic : pics) {
                    File srcPic = new File(srcPicDir, pic);
                    if (srcPic.exists()) {
                        File postPicDir = new File(postDir, tarFile.getName().replace(".md", "") + "/pic");
                        FileUtils.copyFileToDirectory(srcPic, postPicDir);
                    }
                }

                // 修改Markdown，适配hexo
                HexoTitle title = new HexoTitle(this, srcfile, tarFile, moreAfterLine);
                title.FillTitle().FillDate().FillCate().FillCover().Done();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private List<String> getUsedPic(String content) {
            List<String> pics = new ArrayList<>();
            Matcher matcher = htmlPattern.matcher(content);
            while (matcher.find()) {
                pics.add(matcher.group(1));
            }

            matcher = mdPattern.matcher(content);
            while (matcher.find()) {
                pics.add(matcher.group(1));
            }
            return pics;
        }

        public String removeIndexPrefix(String name) {
            return name.replaceFirst("\\d+\\.", "");
        }


        public void Generate() {
            Cmd.Run(String.format("cd %s; hexo generate;", hexoDir.getAbsolutePath()));
        }

        public void Server() {
            Cmd.Run(String.format("cd %s; hexo server;", postDir.getAbsolutePath()));
        }

        public void Deploy() {
            Cmd.Run(String.format("cd %s; hexo deploy;", hexoDir.getAbsolutePath()));
        }
    }
}

