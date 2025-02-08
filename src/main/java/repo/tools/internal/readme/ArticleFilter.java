package repo.tools.internal.readme;

import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.util.Locale;

class ArticleFilter implements IOFileFilter {
    @Override
    public boolean accept(File file) {
        String name = file.getName();
        if (name.toLowerCase(Locale.ROOT).endsWith(".todo") || name.toLowerCase(Locale.ROOT).contains("ignore") || file.getParentFile().getName().equals("pic")) {
            return false;
        }
        // 前缀为数字 or 后缀为md
        return name.matches("\\d+\\..*") || name.endsWith(".md");
    }

    @Override
    public boolean accept(File file, String s) {
        return false;
    }
}
