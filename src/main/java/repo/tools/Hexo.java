package repo.tools;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import repo.tools.internal.Cmd;
import repo.tools.internal.HexoTitle;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

                // 移动图片
                File srcPicDir = new File(srcfile.getParentFile(), "pic");
                if (srcPicDir.exists() && !pic.contains(srcPicDir.getPath())) {
                    FileUtils.copyDirectoryToDirectory(srcPicDir, this.sourceDir);
                    pic.add(srcPicDir.getPath());
                }

                // 修改Markdown，适配hexo
                HexoTitle title = new HexoTitle(srcfile, tarFile, moreAfterLine);
                title.FillTitle().FillDate().FillCate().FillCover().Done();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

