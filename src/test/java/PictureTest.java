import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PictureTest {
    public static void main(String[] args) throws IOException {
        File root = new File(".");
        File blog = new File("blog/");
        Collection<File> files = FileUtils.listFiles(blog, new String[]{"md", "todo"}, true);
        List<File> fileList = new ArrayList<>(files);
        fileList.sort(Comparator.comparing(File::getAbsolutePath));
        for (File file : fileList) {
            do {
                boolean changed = movePIC(root, file);
                if (!changed) {
                    break;
                }
            } while (movePIC(root, file));
        }
    }

    private static boolean movePIC(File root, File file) throws IOException {
        System.out.println("1. ReadFile：" + file.getAbsolutePath());
        String content = FileUtils.readFileToString(file);
        String retContent = doMovePIC(content, new Move(root, new File(file.getParentFile(), "pic")));
        if (!StringUtils.equals(retContent, content)) {
            FileUtils.writeStringToFile(file, retContent);
            return true;
        }
        return false;
    }


    private static String doMovePIC(String content, Function<String, Boolean> move) {
        String regex = "!\\[[^]]*]\\(([^)]+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String path = matcher.group(1);
            if (!path.contains("resources/picture/")) {
                System.out.println("\tSkip Picture: " + path);
                continue;
            }
            System.out.println("2. Process Picture: " + path);
            String pngName = path.substring(path.lastIndexOf("/") + 1);
            System.out.println("\tPicName: " + pngName);
            if (move.apply(pngName)) {
                return content.replaceAll("\\([^)]+" + pngName + "\\)", "(pic/" + pngName + ")");
            }
        }

        String regex2 = "<img src=\"([^\"]+)";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(content);
        while (matcher2.find()) {
            String path = matcher2.group(1);
            if (!path.contains("resources/picture/")) {
                System.out.println("\tSkip Picture: " + path);
                continue;
            }
            System.out.println("2. Process Picture: " + path);
            String pngName = path.substring(path.lastIndexOf("/") + 1);
            System.out.println("\tPicName: " + pngName);
            if (move.apply(pngName)) {
                return content.replaceAll("src=\"[^\"]+" + pngName, "src=\"pic/" + pngName);
            }
        }

        return content;
    }

    static class Move implements Function<String, Boolean> {
        File sourceDir = new File("src/main/resources/picture");
        File target;
        File root;

        public Move(File root, File target) {
            this.target = target;
            this.root = root;
        }

        @Override
        public Boolean apply(String picName) {
            File picFile = new File(sourceDir, picName);
            File testTarget = new File(target, picFile.getName());
            try {
                if (testTarget.exists()) {
                    System.out.printf("3. already move %s → %s\n", picFile.getCanonicalPath(), target.getPath());
                    return true;
                }
                if (picFile.exists()) {
                    FileUtils.moveFileToDirectory(picFile, target, true);
                    System.out.printf("3. move %s → %s\n", picFile.getCanonicalPath(), target.getPath());
                    return true;
                }

                Path file = Files.walk(root.toPath()).filter(p -> p.toFile().isFile() && p.getFileName().toString().equals(picName)).findFirst().orElse(null);
                if (file != null) {
                    System.out.println("File found at: " + file.toAbsolutePath());
                    FileUtils.copyFileToDirectory(file.toFile(), target);
                    return true;
                } else {
                    System.out.println("File not found");
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }
    }
}
