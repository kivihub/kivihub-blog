package repo.tools.internal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author wangqiwei
 * @date 2021/01/23 10:33 AM
 */
public class ReadmeBuilder {
    private final Logger logger = LoggerFactory.getLogger(ReadmeBuilder.class);
    private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,##0");
    private File readmeFile;

    public ReadmeBuilder(File readmeFile) throws IOException {
        this.readmeFile = readmeFile.getCanonicalFile();
    }

    public void buildToc() throws IOException {
        StringBuilder sb = new StringBuilder();
        appendSection(sb, new File(readmeFile, "../src/main/resources/readme/head.md"));
        sb.append("\n---\n");
        appendTocBlog(sb, new File(readmeFile, "../shared"), "#### 总结分享");
        sb.append("\n---\n");
        appendTocBlog(sb, new File(readmeFile, "../blog"), "#### 博客目录");
        sb.append("\n---\n");
        appendSection(sb, new File(readmeFile, "../src/main/resources/readme/tail.md"));
        FileUtils.writeStringToFile(readmeFile, sb.toString(), UTF_8);
        logger.info("Toc Build Success.");
    }

    private void appendSection(StringBuilder readmeContent, File file) throws IOException {
        readmeContent.append(FileUtils.readFileToString(file.getCanonicalFile(), UTF_8));
    }

    private void appendTocBlog(StringBuilder readmeContent, File tocGroupFile, String tocGroupName) {
        Triple<Integer, Integer, String> triple = getTocContent(tocGroupFile);
        String title = String.format("%s(%s篇/%s字)\n", tocGroupName, triple.getLeft(), DECIMAL_FORMAT.format(triple.getMiddle()));
        readmeContent.append(title).append(triple.getRight());
    }

    private Triple<Integer, Integer, String> getTocContent(File file) {
        ArticleFilter articleFilter = new ArticleFilter();
        ArticleDisplayFormatter displayFormatter = new ArticleDisplayFormatter(articleFilter);
        ArticleVisitor articleVisitor = new ArticleVisitor();
        articleVisitor.setArticleSorter(new FileOrder())
                .setArticleDisplayFormatter(displayFormatter)
                .setDirDisplayFormatter(displayFormatter)
                .setFileFilter(articleFilter)
                .setIndentation("    ")
                .visit(file);
        String toc = articleVisitor.getToc();
        return new ImmutableTriple<>(displayFormatter.articleNum, displayFormatter.totalWordsNum, toc);
    }

    class ArticleFilter implements IOFileFilter {
        @Override
        public boolean accept(File file) {
            String name = file.getName();
            if (name.toLowerCase(Locale.ROOT).endsWith(".todo") || name.toLowerCase(Locale.ROOT).contains("ignore")) {
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

    class ArticleDisplayFormatter implements Function<File, String> {
        ArticleFilter articleFilter;
        String rootPath;
        int articleNum = 0;
        int totalWordsNum = 0;

        ArticleDisplayFormatter(ArticleFilter articleFilter) {
            this.articleFilter = articleFilter;
            try {
                rootPath = readmeFile.getParentFile().getCanonicalPath();
            } catch (IOException e) {
                logger.error("", e);
            }
        }

        @Override
        public String apply(File file) {
            String displayName = "md".equals(FilenameUtils.getExtension(file.getName())) ?
                    FilenameUtils.getBaseName(file.getName()) : file.getName();
            try {
                file = file.getCanonicalFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (file.isFile()) {
                articleNum++;
                int articleWord = getMsWordsCount(file);
                totalWordsNum += articleWord;
                displayName += " (" + DECIMAL_FORMAT.format(articleWord) + "字)";
            } else {
                int fileCount = FileUtils.listFiles(file, articleFilter, TrueFileFilter.INSTANCE).size();
                displayName += " (" + fileCount + "篇)";
            }

            return String.format("- [%s](%s)", displayName, getRelativePath(file));
        }

        private String getRelativePath(File file) {
            return file.getAbsolutePath().substring(rootPath.length() + 1);
        }

        private int getMsWordsCount(File file) {
            String context;
            try {
                context = FileUtils.readFileToString(file, UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //中文单词
            String cn_words = context.replaceAll("[^(\\u4e00-\\u9fa5，。《》？；’‘：“”【】、）（……￥！·)]", "");
            int cn_words_count = cn_words.length();
            //非中文单词
            String non_cn_words = context.replaceAll("[^(a-zA-Z0-9`\\-=\';.,/~!@#$%^&*()_+|}{\":><?\\[\\])]", " ");
            int non_cn_words_count = 0;
            String[] ss = non_cn_words.split(" ");
            for (String s : ss) {
                if (s.trim().length() != 0) non_cn_words_count++;
            }
            return cn_words_count + non_cn_words_count;
        }
    }

    class FileOrder implements Comparator<File> {
        Pattern pattern = Pattern.compile("(\\d++)");

        @Override
        public int compare(File o1, File o2) {
            return getIndex(o1.getName()) - getIndex(o2.getName());
        }

        private int getIndex(String fileName) {
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            } else {
                return 0;
            }
        }
    }
}
