package repo.tools;

import org.apache.commons.lang3.ArrayUtils;
import repo.tools.internal.hexo.Posts;

import java.io.File;
import java.io.IOException;

public class Hexo {
    public static final File REPO_DIR = new File(System.getProperty("user.dir"));
    private static final int MORE_AFTER_LINE = 5;

    public static void main(String[] args) throws IOException {
        boolean deploy = false;
        if (args != null && args.length > 0) {
            deploy = ArrayUtils.contains(args, "-deploy");
        }

        Posts post = new Posts(MORE_AFTER_LINE);
        post.Init();
        post.AddPosts();
        post.AddBlogTree();
        post.Generate();
        post.PostProcess();
        if (deploy) {
            post.Deploy();
        } else {
            post.Server();
        }
    }
}

