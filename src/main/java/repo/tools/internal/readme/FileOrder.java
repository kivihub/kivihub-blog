package repo.tools.internal.readme;

import java.io.File;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
