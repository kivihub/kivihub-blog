package repo.tools.internal.hexo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repo.tools.internal.utils.Cmd;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static repo.tools.Hexo.REPO_DIR;


public class Posts {
    private final Logger logger = LoggerFactory.getLogger(Posts.class);
    // 文章摘要的展示行数
    public final int moreAfterLine;

    // 用于部署的目录
    private final File deployRoot;
    // {deployRoot}/public
    private final File publicDir;
    // {deployRoot}/source
    protected File sourceDir;
    // {deployRoot}/source/_posts
    private final File postDir;
    // {deployRoot}/source/blogTree
    private final File blogTreeDir;

    public Posts(int moreAfterLine) {
        this.moreAfterLine = moreAfterLine;

        this.deployRoot = new File(REPO_DIR.getParentFile(), REPO_DIR.getName() + "-deploy");
        this.publicDir = new File(deployRoot, "public");
        this.sourceDir = new File(deployRoot, "source");
        this.postDir = new File(sourceDir, "_posts");
        this.blogTreeDir = new File(sourceDir, "catalog");
    }

    public void Init() throws IOException {
        logger.info("Initializing posts...");
        if (deployRoot.exists()) {
            FileUtils.forceDelete(deployRoot);
        }
        FileUtils.copyDirectory(new File(REPO_DIR, "hexo"), deployRoot);
        logger.info("Initialize posts complete.\n");
    }

    public void AddPosts() {
        logger.info("Adding posts...");
        FileUtils.listFiles(REPO_DIR, new PostFilter(), TrueFileFilter.INSTANCE).forEach(this::AddPost);
        logger.info("Add posts complete.\n");
    }

    public void AddPost(File srcfile) {
        logger.debug("Adding post...");
        try {
            /// Step1: copy resources
            // copy .md to deployDir
            FileUtils.copyFileToDirectory(srcfile, postDir);
            File tarFile = new File(postDir, srcfile.getName());
            // copy .md使用到的图片
            List<String> pics = Util.getUsedPic(FileUtils.readFileToString(tarFile));
            File srcPicDir = new File(srcfile.getParentFile(), "pic");
            for (String pic : pics) {
                File srcPic = new File(srcPicDir, pic);
                if (srcPic.exists()) {
                    File postPicDir = new File(postDir, Util.removeIndexPrefix(Util.removeSuffix(tarFile.getName())) + "/pic");
                    FileUtils.copyFileToDirectory(srcPic, postPicDir);
                }
            }

            /// Step2: modify file name
            String newFileName = Util.removeIndexPrefix(srcfile.getName());
            tarFile.renameTo(new File(postDir, newFileName));
            tarFile = new File(postDir, newFileName);

            /// Step3: modify file content
            new PostContent(tarFile).resetImage() // reset image format
                    .insertMoreTag(moreAfterLine) // insert <more>
                    .Save();
            new PostTitle(this, tarFile).FillTitleName() // set title name
                    .FillCreateDate(srcfile.getAbsolutePath()) // set create date
                    .FillCategory(srcfile.getParent()) // set category
                    .FillCoverImage() // set cover image
                    .Save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("Add post complete.\n");
    }

    public void AddBlogTree() throws IOException {
        logger.info("Adding blogTree");
        new BlogTree().getBlogTree().buildTitle().Save(blogTreeDir);
        logger.info("Add blogTree complete.\n");
    }

    public void Generate() {
        logger.info("Generating posts...");
        Cmd.RunSilent(String.format("cd %s; hexo generate;", deployRoot.getAbsolutePath()));
        logger.info("Generation complete.\n");
    }

    public void PostProcess() throws IOException {
        logger.info("Post processing...");
        // Step1: 使用archives/index.html代替首页的index.html
        File originIndex = new File(publicDir, "index.html");
        originIndex.renameTo(new File(publicDir, "index_post.html"));
        File archiveIndex = new File(publicDir, "archives/index.html");
        FileUtils.copyFileToDirectory(archiveIndex, publicDir);

        // Step2: reset html title
        FileUtils.listFiles(publicDir, new SuffixFileFilter(".html"), TrueFileFilter.INSTANCE).forEach(file -> {
            try {
                String content = FileUtils.readFileToString(file);
                content = content.replaceFirst("<title>.*</title>", "<title>Kivi's Blog</title>");
                FileUtils.writeStringToFile(file, content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Step3: reset relative reference
        Map<String, String> mdPath = new HashMap<>();
        Util.listArticles(publicDir, file -> {
            String path = file.getParent().substring(publicDir.getAbsolutePath().length());
            String name = file.getParentFile().getName();
            mdPath.put(name, path);
        });
        Util.listArticles(publicDir, file -> {
            try {
                Util.replaceRelativeReference(file, mdPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Util.replaceRelativeReference(new File(publicDir, "catalog/index.html"), mdPath);
        Util.replaceCategoryReference(new File(publicDir, "catalog/index.html"));

        logger.info("Post processing complete.\n");
    }

    /**
     * 1. hexo server -s不生效，需要写完整--static</br>
     * 2. hexo server如果不加参数--static，就会直接在内存重新生成html（不落盘）
     */
    public void Server() {
        logger.info("Server starting...");
        Cmd.Run(String.format("cd %s; hexo server --static;", deployRoot.getAbsolutePath()), false);
    }

    public void Deploy() {
        logger.info("Deploying...");
        Cmd.Run(String.format("cd %s; hexo deploy;", deployRoot.getAbsolutePath()), false);
        logger.info("Deployment complete.\n");
    }


}
