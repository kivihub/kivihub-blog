## kivihub-blog#一个私人博客仓库

> 非学无以广才，非志无以成学。 —— 诫子书

#### GIT钩子配置

使用pre-commit钩子，每次提交前会自动更新README.md

```shell
cp hooks/pre-commit .git/hooks/
```

更新生成逻辑：
```shell
mvn clean package
cp target/pre-commit.jar hooks/
```

#### Hexo首次初始化
```bash
cd hexo/

brew install node

# 可选，npm install卡顿时设置
npm config set registry https://registry.npm.taobao.org
npm config set strict-ssl false

npm install -g hexo-cli
npm install
```

#### 仓库目录

| 目录                           | 分类           | 介绍                                |
|------------------------------|--------------|-----------------------------------|
| */blog*                      | **博客目录**     | 较零散知识，偏某个知识细节                     |
| */reference*                 | **引用目录**     | 博客中引用的文章存放目录                      |
| */src/main/java/repo/tools*  | **仓库工具**     | 自动生成README.md                     |
| */src/main/resources/readme* | **README片段** | 用于生成README.md的片段，如head.md，tail.md |