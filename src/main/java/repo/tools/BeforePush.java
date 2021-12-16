package repo.tools;

import repo.tools.internal.ReadmeBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author wangqiwei
 * @date 2021/03/04 15:13
 */
public class BeforePush {
    public static void main(String[] args) throws IOException {
        new ReadmeBuilder(new File("README.md")).buildToc();
    }
}
