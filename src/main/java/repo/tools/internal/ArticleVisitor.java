package repo.tools.internal;

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
    private String indentation = "\t";
    private Comparator<File> comparator = new NameFileComparator();
    private Function<File, String> articleDisplayFormatter = s -> s.getName();
    private Function<File, String> dirDisplayFormatter = s -> s.getName();
    private FileFilter fileFilter = TrueFileFilter.INSTANCE;
    private StringBuilder toc = new StringBuilder();

    public void visit(File dir) {
        try {
            doVisit(dir, -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getToc() {
        return toc.toString();
    }

    private void doVisit(File file, int depth) throws IOException {
        if (file.isFile()) {
            String articleDisplay = articleDisplayFormatter.apply(file);
            appendToToc(articleDisplay, depth);
        } else {
            String dirDisplay = dirDisplayFormatter.apply(file);
            if (depth != -1) appendToToc(dirDisplay, depth);
            File[] childFiles = file.getCanonicalFile().listFiles(fileFilter);
            Arrays.sort(childFiles, comparator);
            for (File child : childFiles) {
                doVisit(child, depth + 1);
            }
        }
    }

    private void appendToToc(String displayName, int depth) {
        String indentation = StringUtils.repeat(this.indentation, depth);
        toc.append(indentation + displayName + "\n");
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
