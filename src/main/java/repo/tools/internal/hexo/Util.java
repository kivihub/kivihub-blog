package repo.tools.internal.hexo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static Pattern htmlPattern = Pattern.compile("src=\"/?pic/([^\"]+)");
    public static Pattern mdPattern = Pattern.compile("!\\[[^]]+]\\(pic/([^)]+)\\)");

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
}
