package repo.tools.internal.hexo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Logger log = LoggerFactory.getLogger(Util.class);
    public static Pattern htmlPattern = Pattern.compile("src=\"/?pic/([^\"]+)");
    public static Pattern mdPattern = Pattern.compile("!\\[[^]]+]\\(pic/([^)]+)\\)");

    public static void listArticles(File publicDir, Consumer<File> consumer) {
        FileUtils.listFiles(publicDir, new AbstractFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().equals("index.html") && !file.getParent().equals(publicDir.getAbsolutePath());
            }
        }, new AbstractFileFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (dir.getAbsolutePath().equals(publicDir.getAbsolutePath())) {
                    return name.matches("\\d+");
                }
                return true;
            }
        }).forEach(consumer);
    }

    public static void replaceRelativeReference(File file, Map<String, String> mdPath) throws IOException {
        String content = FileUtils.readFileToString(file);

        String mdRef = "<a href=\"([^\"]+\\.md)\"";
        Pattern pattern = Pattern.compile(mdRef);
        Matcher matcher = pattern.matcher(content);
        boolean replace = false;
        while (matcher.find()) {
            String match = matcher.group(1).replace("+", "%2B");
            String mdRelPath = URLDecoder.decode(match, "UTF-8");
            String mdName = mdRelPath.substring(mdRelPath.lastIndexOf("/") + 1);
            String target = mdPath.get(removeIndexPrefix(removeSuffix(mdName)));
            if (target == null) {
                log.warn("{} refer invalid file {}", file.getParent(), mdRelPath);
                continue;
            }

            content = StringUtils.replaceOnce(content, matcher.group(1), target);
            replace = true;
        }
        if (replace) {
            log.debug("Replace markdown relative reference {}", file.getPath());
            FileUtils.writeStringToFile(file, content);
        }
    }

    public static void replaceCategoryReference(File file) throws IOException {
        String content = FileUtils.readFileToString(file);

        String categoryRef = "<a href=\"(blog/([^\"]+))\"";
        Pattern pattern = Pattern.compile(categoryRef);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String match = matcher.group(2);
            String catePath = URLDecoder.decode(match, "UTF-8");
            String[] paths = catePath.split("/");
            for (int i = 0; i < paths.length; i++) {
                paths[i] = removeIndexPrefix(paths[i]);
            }

            content = StringUtils.replaceOnce(content, matcher.group(1), "/categories/" + StringUtils.join(paths, "/"));
        }

        FileUtils.writeStringToFile(file, content);
    }

    public static String replaceImageTags(String content) {
        String imgTagRegex = "<img[^>]*src=\"([^\"]*)\"[^>]*alt=\"([^\"]*)\"[^>]*>";
        Pattern pattern = Pattern.compile(imgTagRegex);
        Matcher matcher = pattern.matcher(content);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String imagePath = matcher.group(1);
            if (!imagePath.startsWith("pic/")) {
                continue;
            }
            String imageName = matcher.group(2);
            String replacement = String.format("![%s](%s)", imageName, imagePath);
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static List<String> getUsedPic(String content) {
        List<String> pics = new ArrayList<>();
        Matcher matcher = htmlPattern.matcher(content);
        while (matcher.find()) {
            pics.add(matcher.group(1));
        }

        matcher = mdPattern.matcher(content);
        while (matcher.find()) {
            pics.add(matcher.group(1));
        }
        return pics;
    }

    public static String removeIndexPrefix(String name) {
        return name.replaceFirst("\\d+\\.", "");
    }

    public static String removeSuffix(String filename) {
        return filename.substring(0, filename.lastIndexOf("."));
    }

    public static String now() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.ofHours(8));

        return now.format(formatter);
    }
}
