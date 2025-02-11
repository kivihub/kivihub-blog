package repo.tools.internal.hexo;

import org.apache.commons.io.FileUtils;
import repo.tools.internal.readme.ReadmeBuilder;

import java.io.File;
import java.io.IOException;

import static repo.tools.Hexo.REPO_DIR;

public class BlogTree {
    private static final String TITLE_TPL = "src/main/resources/hexo/title_readme.yml";
    String blogTree;
    String title;

    public BlogTree getBlogTree() throws IOException {
        String content = FileUtils.readFileToString(new File(REPO_DIR, "README.md"));
        int start = content.indexOf(ReadmeBuilder.TREE_START_TAG);
        int end = content.indexOf(ReadmeBuilder.TREE_END_TAG);
        blogTree = content.substring(start, end);

        return this;
    }

    public BlogTree buildTitle() throws IOException {
        title = FileUtils.readFileToString(new File(REPO_DIR, TITLE_TPL));
        title = title.replace("{date}", Util.now());
        return this;
    }

    public void Save(File blogTreeDir) throws IOException {
        FileUtils.writeStringToFile(new File(blogTreeDir, "index.md"), title + "\n" + blogTree);
    }
}
