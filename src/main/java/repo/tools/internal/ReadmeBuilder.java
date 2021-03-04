package repo.tools.internal;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author wangqiwei
 * @date 2021/01/23 10:33 AM
 */
public class ReadmeBuilder {
    private File rootPath;
    private File README;
    private String content;
    private StringBuilder retContent;
    private int docNum = 0;

    public ReadmeBuilder(File readmeFile) {
        README = readmeFile;
        try {
            rootPath = README.getCanonicalFile().getParentFile();
            content = FileUtils.readFileToString(README, UTF_8);
            retContent = new StringBuilder(content);
        } catch (IOException e) {// ignore
        }
    }

    public void buildToc() {
        String token = "#### 博客目录";
        File blogDir = new File(rootPath, "blog");
        String toc = getTocContent(blogDir, 0);

        int i = retContent.indexOf(token);
        if (i != -1) {
            retContent = new StringBuilder(retContent.substring(0, i + token.length()));
        }
        retContent.append(" (总计:" + docNum + "篇)\n" + toc);

        flushDisk();
    }

    private String getTocContent(File file, int depth) {
        List<File> fileList = orderedFileList(file.listFiles(name -> name.getName().matches("\\d+\\..*")
                || name.getName().endsWith(".md")));
        StringBuilder sb = new StringBuilder();
        for (File item : fileList) {
            sb.append(StringUtils.repeat("\t", depth) + "- " + buildRelativePath(item) + "\n");
            if (item.isDirectory()) {
                sb.append(getTocContent(item, depth + 1));
            } else {
                docNum++;
            }
        }
        return sb.toString();
    }

    private String buildRelativePath(File file) {
        String tpl = "[%s](%s)";
        String displayName;
        int index;
        if ((index = file.getName().indexOf(".md")) != -1) {
            displayName = file.getName().substring(0, index);
        } else {
            displayName = file.getName();
        }
        String path = file.getAbsolutePath().substring(rootPath.toString().length() + 1);
        return String.format(tpl, displayName, path);
    }

    private List<File> orderedFileList(File[] files) {
        List<File> retList = new ArrayList<>(Arrays.asList(files));
        retList.sort(new FileOrder());
        return retList;
    }

    private void flushDisk() {
        try {
            FileUtils.writeStringToFile(README, retContent.toString(), UTF_8);
        } catch (IOException e) { // ignore
        }
    }

    static class FileOrder implements Comparator<File> {
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
