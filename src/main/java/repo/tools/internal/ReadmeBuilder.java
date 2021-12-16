package repo.tools.internal;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author wangqiwei
 * @date 2021/01/23 10:33 AM
 */
public class ReadmeBuilder {
    private Logger logger = LoggerFactory.getLogger(ReadmeBuilder.class);
    private File readmeFile;
    private String readmeContent;

    public ReadmeBuilder(File readmeFile) throws IOException {
        this.readmeFile = readmeFile.getCanonicalFile();
        this.readmeContent = FileUtils.readFileToString(this.readmeFile, UTF_8);
    }

    public void buildToc() throws IOException {
        String token = "#### 博客目录";
        int index = readmeContent.indexOf(token);
        if (index == -1) {
            return;
        }

        Entry<Integer, String> tocEntry = getTocContent(new File(readmeFile, "../blog"));
        readmeContent = readmeContent.substring(0, index + token.length()) +
                " (总计:" + tocEntry.getKey() + "篇)\n"
                + tocEntry.getValue();
        FileUtils.writeStringToFile(readmeFile, readmeContent, UTF_8);
        logger.info("Toc Build Success.");
    }

    private Entry<Integer, String> getTocContent(File file) {
        ArticleDisplayFormatter displayFormatter = new ArticleDisplayFormatter();
        ArticleVisitor articleVisitor = new ArticleVisitor();
        articleVisitor.setArticleSorter(new FileOrder())
                .setArticleDisplayFormatter(displayFormatter)
                .setDirDisplayFormatter(displayFormatter)
                .setFileFilter(new ArticleFilter())
                .visit(file);
        String toc = articleVisitor.getToc();
        return new AbstractMap.SimpleEntry(displayFormatter.articleNum, toc);
    }

    class ArticleFilter implements FileFilter {
        @Override
        public boolean accept(File file) {
            String name = file.getName();
            if (name.endsWith(".todo")) {
                return false;
            }
            return name.matches("\\d+\\..*") || name.endsWith(".md");
        }
    }

    class ArticleDisplayFormatter implements Function<File, String> {
        String rootPath;
        int articleNum = 0;

        ArticleDisplayFormatter() {
            try {
                rootPath = readmeFile.getParentFile().getCanonicalPath();
            } catch (IOException e) {
                logger.error("", e);
            }
        }

        @Override
        public String apply(File file) {
            if (file.isFile()) {
                articleNum++;
            }

            String tpl = "- [%s](%s)";
            String displayName;
            int index;
            if ((index = file.getName().indexOf(".md")) != -1) {
                displayName = file.getName().substring(0, index);
            } else {
                displayName = file.getName();
            }
            String path = file.getAbsolutePath().substring(rootPath.length() + 1);
            return String.format(tpl, displayName, path);
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
