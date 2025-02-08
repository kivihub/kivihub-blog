## 仓库配置
### Hexo配置
首次克隆本仓库后执行如下命令

```bash
cd hexo/

brew install node

# 可选，npm install卡顿时设置
npm config set registry https://registry.npm.taobao.org
npm config set strict-ssl false

npm install -g hexo-cli
npm install
```

### GIT钩子配置
配置pre-commit的Git钩子后，Git Commit时会自动更新README.md内容。

1）首次克隆本仓库后执行如下命令

```shell
cp hooks/pre-commit .git/hooks/  # hooks/pre-commit脚本会调用hooks/pre-commit.jar
```

2）Git钩子源码更新后，执行如下命令使其生效：

```shell
mvn clean package
cp target/pre-commit.jar hooks/
```

---
*非学无以广才，非志无以成学。 —— 诫子书*