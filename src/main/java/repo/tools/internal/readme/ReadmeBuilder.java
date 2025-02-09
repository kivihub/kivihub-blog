package repo.tools.internal.readme;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author wangqiwei
 * @date 2021/01/23 10:33 AM
 */
public class ReadmeBuilder {
    private final Logger logger = LoggerFactory.getLogger(ReadmeBuilder.class);
    public final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,##0");
    private final File readmeFile;

    public ReadmeBuilder(File readmeFile) throws IOException {
        this.readmeFile = readmeFile.getCanonicalFile();
    }

    public void buildToc() throws IOException {
        StringBuilder sb = new StringBuilder();
        appendSection(sb, new File(readmeFile, "../src/main/resources/readme/head.md"));
        sb.append("\n---\n\n");
        appendTocBlog(sb, new File(readmeFile, "../blog"));
        sb.append("\n---\n\n");
        appendSection(sb, new File(readmeFile, "../src/main/resources/readme/tail.md"));
        FileUtils.writeStringToFile(readmeFile, sb.toString(), UTF_8);
        logger.info("Toc Build Success.");
    }

    private void appendSection(StringBuilder readmeContent, File file) throws IOException {
        readmeContent.append(FileUtils.readFileToString(file.getCanonicalFile(), UTF_8));
    }

    private void appendTocBlog(StringBuilder readmeContent, File tocGroupFile) throws IOException {
        Triple<Integer, Integer, String> triple = getTocContent(tocGroupFile);
        String title = String.format("%s(%s篇/%s字)\n\n", "## 博客目录", triple.getLeft(), DECIMAL_FORMAT.format(triple.getMiddle()));
        readmeContent.append(title).append(triple.getRight());
    }

    private Triple<Integer, Integer, String> getTocContent(File file) throws IOException {
        ArticleFilter articleFilter = new ArticleFilter();
        ArticleDisplayFormatter displayFormatter = new ArticleDisplayFormatter(readmeFile, articleFilter);

        String toc = new ArticleVisitor().setArticleSorter(new FileOrder()) // setArticleSorter
                .setArticleDisplayFormatter(displayFormatter) // set ArticleDisplayFormatter
                .setDirDisplayFormatter(displayFormatter) // set DirDisplayFormatter
                .setFileFilter(articleFilter) // set FileFilter
                .setIndentation("  ") //set Indentation
                .visit(file) // visit
                .getToc();

        return new ImmutableTriple<>(displayFormatter.articleNum, displayFormatter.totalWordsNum, toc);
    }
}
