package repo.tools.internal.readme;

import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

/**
 * @author wangqiwei
 * @date 2021/12/16 23:12
 */
public class ArticleVisitor {
    private final StringBuilder toc = new StringBuilder();
    private String indentation = "\t";
    private Comparator<File> comparator = new NameFileComparator();
    private Function<File, String> articleDisplayFormatter = File::getName;
    private Function<File, String> dirDisplayFormatter = File::getName;
    private FileFilter fileFilter = TrueFileFilter.INSTANCE;

    public String getToc() {
        return toc.toString();
    }

    public ArticleVisitor visit(File dir) {
        try {
            doVisit(dir, -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void doVisit(File file, int depth) throws IOException {
        if (file.isFile()) {
            String articleDisplay = articleDisplayFormatter.apply(file);
            appendToToc(articleDisplay, depth);
        } else {
            String dirDisplay = dirDisplayFormatter.apply(file);
            if (depth != -1) appendToToc(dirDisplay, depth);
            File[] childFiles = file.getCanonicalFile().listFiles(fileFilter);
            assert childFiles != null;
            Arrays.sort(childFiles, comparator);
            for (File child : childFiles) {
                doVisit(child, depth + 1);
            }
        }
    }

    private void appendToToc(String displayName, int depth) {
        String indentation = StringUtils.repeat(this.indentation, depth);
        toc.append(indentation).append(displayName).append("\n");
    }

    public ArticleVisitor setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
        return this;
    }

    public ArticleVisitor setArticleSorter(Comparator<File> comparator) {
        this.comparator = comparator;
        return this;
    }

    public ArticleVisitor setDirDisplayFormatter(Function<File, String> formatter) {
        this.dirDisplayFormatter = formatter;
        return this;
    }

    public ArticleVisitor setArticleDisplayFormatter(Function<File, String> articleDisplayFormatter) {
        this.articleDisplayFormatter = articleDisplayFormatter;
        return this;
    }

    public ArticleVisitor setIndentation(String indentation) {
        this.indentation = indentation;
        return this;
    }
}
