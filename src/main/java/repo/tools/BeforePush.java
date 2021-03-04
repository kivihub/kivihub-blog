package repo.tools;

import repo.tools.internal.ReadmeBuilder;

import java.io.File;

/**
 * @author wangqiwei
 * @date 2021/03/04 15:13
 */
public class BeforePush {
    public static void main(String[] args) {
        ReadmeBuilder readmeBuilder = new ReadmeBuilder(new File("README.md"));
        readmeBuilder.buildToc();
    }
}
