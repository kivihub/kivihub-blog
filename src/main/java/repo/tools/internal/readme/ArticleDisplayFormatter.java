package repo.tools.internal.readme;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;
import static repo.tools.internal.readme.ReadmeBuilder.DECIMAL_FORMAT;

class ArticleDisplayFormatter implements Function<File, String> {
    public int articleNum = 0;
    public int totalWordsNum = 0;

    private final Logger logger = LoggerFactory.getLogger(ArticleDisplayFormatter.class);
    private final ArticleFilter articleFilter;
    private final String rootPath;

    ArticleDisplayFormatter(File readmeFile, ArticleFilter articleFilter) throws IOException {
        this.articleFilter = articleFilter;
        this.rootPath = readmeFile.getParentFile().getCanonicalPath();
    }

    @Override
    public String apply(File file) {
        String displayName = "md".equals(FilenameUtils.getExtension(file.getName())) ? FilenameUtils.getBaseName(file.getName()) : file.getName();
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

        return String.format("- [%s](%s)", displayName, getRelativePath(file).replaceAll("\\\\", "/"));
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
            if (!s.trim().isEmpty()) non_cn_words_count++;
        }
        return cn_words_count + non_cn_words_count;
    }
}
