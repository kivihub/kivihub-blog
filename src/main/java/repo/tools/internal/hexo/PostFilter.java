package repo.tools.internal.hexo;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;

public class PostFilter implements IOFileFilter {
    @Override
    public boolean accept(File file) {
        return file.toPath().toString().contains("kivihub-blog/blog") && file.getName().endsWith(".md");
    }

    @Override
    public boolean accept(File file, String s) {
        return false;
    }
}