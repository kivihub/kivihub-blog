package repo.tools;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repo.tools.internal.Cmd;
import repo.tools.internal.HexoTitle;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static repo.tools.internal.HexoTitle.htmlPattern;
import static repo.tools.internal.HexoTitle.mdPattern;

public class Hexo {
    public static File repoDir = new File(System.getProperty("user.dir"));
    public static File blogDir = new File(repoDir, "blog");
    public static int moreAfterLine = 5;

    public static void main(String[] args) throws IOException {
        Post post = new Post(repoDir);
        post.Clear();
        FileUtils.listFiles(repoDir, new PostFilter(), TrueFileFilter.INSTANCE).forEach(post::AddPost);
        post.Generate();
        post.PostProcess();
        post.Server();
//        post.Deploy();
    }

    public static class PostFilter implements IOFileFilter {
        @Override
        public boolean accept(File file) {
            return file.toPath().toString().contains("kivihub-blog/blog") && file.getName().endsWith(".md");
        }

        @Override
        public boolean accept(File file, String s) {
            return false;
        }
    }

    public static class Post {
        public File hexoDir;
        public File postDir;
        public File sourceDir;
        public File deployRoot;
        public Set<String> pic = new HashSet<>();

        public Post(File repoDir) {
            hexoDir = new File(repoDir.getParentFile(), "hexo");
            postDir = new File(hexoDir, "source/_posts");
            sourceDir = new File(hexoDir, "source");
            deployRoot = new File(repoDir.getParentFile(), "hexo");
        }

        public void Clear() throws IOException {
            if (hexoDir.exists()) {
                FileUtils.forceDelete(hexoDir);
                System.out.println("Clear Hexo Dir.");
            }
            FileUtils.copyDirectoryToDirectory(new File(repoDir, "hexo"), repoDir.getParentFile());
        }

        public void AddPost(File srcfile) {
            try {
                FileUtils.copyFileToDirectory(srcfile, postDir);
                File tarFile = new File(postDir, srcfile.getName());

                // 去除数字前缀
                String newFileName = removeIndexPrefix(srcfile.getName());
                tarFile.renameTo(new File(postDir, newFileName));
                tarFile = new File(postDir, newFileName);

                // 处理图片格式：html → markdown。
                // 因为html的<img>中的路径在渲染为html时，不会修改其path，导致图片无法正常显示。
                // 详见：https://hexo.io/zh-cn/docs/asset-folders#%E4%BD%BF%E7%94%A8-Markdown-%E5%B5%8C%E5%85%A5%E5%9B%BE%E7%89%87
                String content = FileUtils.readFileToString(tarFile);
                content = replaceImageTags(content);
                FileUtils.writeStringToFile(tarFile, content);

                // 移动使用到的图片
                List<String> pics = getUsedPic(FileUtils.readFileToString(tarFile));
                File srcPicDir = new File(srcfile.getParentFile(), "pic");
                for (String pic : pics) {
                    File srcPic = new File(srcPicDir, pic);
                    if (srcPic.exists()) {
                        File postPicDir = new File(postDir, tarFile.getName().replace(".md", "") + "/pic");
                        FileUtils.copyFileToDirectory(srcPic, postPicDir);
                    }
                }

                // 修改Markdown，适配hexo
                HexoTitle title = new HexoTitle(this, srcfile, tarFile, moreAfterLine);
                title.FillTitle().FillDate().FillCate().FillCover().Done();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private String replaceImageTags(String content) {
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

        private List<String> getUsedPic(String content) {
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

        public String removeIndexPrefix(String name) {
            return name.replaceFirst("\\d+\\.", "");
        }


        public void Generate() {
            Cmd.Run(String.format("cd %s; hexo generate;", hexoDir.getAbsolutePath()));
        }

        /**
         * 1. hexo server -s不生效，需要写完整--static</br>
         * 2. hexo server如果不加参数--static，就会直接在内存重新生成html（不落盘）
         */
        public void Server() {
            Cmd.Run(String.format("cd %s; hexo server --static;", hexoDir.getAbsolutePath()));
        }

        public void Deploy() {
            // --setup ?
            Cmd.Run(String.format("cd %s; hexo deploy;", hexoDir.getAbsolutePath()));
        }

        public void PostProcess() throws IOException {
            File publicDir = new File(deployRoot, "public");

            // Step1: 使用archives/index.html代替首页的index.html
            File originIndex = new File(publicDir, "index.html");
            originIndex.renameTo(new File(publicDir, "index_post.html"));
            File archiveIndex = new File(publicDir, "archives/index.html");
            FileUtils.copyFileToDirectory(archiveIndex, publicDir);

            FileUtils.listFiles(publicDir, new IOFileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith(".html");
                }

                @Override
                public boolean accept(File file, String s) {
                    return false;
                }
            }, TrueFileFilter.INSTANCE).forEach(file -> {
                try {
                    String content = FileUtils.readFileToString(file);
                    // Step2: reset html title
                    content = content.replaceFirst("<title>.*</title>", "<title>Kivi's Blog</title>");

                    // Step3: sort categories
                    content = sortCategories(content);

                    FileUtils.writeStringToFile(file, content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        private String sortCategories(String htmlContent) {
            // 解析 HTML
            Document doc = Jsoup.parse(htmlContent);
            // 通过 "分类" 标签找到后续的 menu-list
            Element menuLabel = doc.selectFirst("h3.menu-label:contains(分类)");
            if (menuLabel == null) {
                return htmlContent;
            }
            Element menuList = menuLabel.nextElementSibling();

            // 提取元素，并将其从 DOM 移除
            Elements sortedLinks = new Elements();
            Elements links = menuList.select("> li");
            for (Element link : links) {
                sortedLinks.add(link.clone());  // 克隆节点以避免修改原始文档
                link.remove();  // 从原始位置移除
            }

            // 按名字排序
            Collator collator = Collator.getInstance(Locale.CHINA);
            Collections.sort(sortedLinks, Comparator.comparing(element -> element.select(".level-item").first().text(), collator));

            // 将排序后的元素插入原来的位置
            for (Element sortedLink : sortedLinks) {
                menuList.appendChild(sortedLink);
            }
            return doc.toString();
        }

    }
}

