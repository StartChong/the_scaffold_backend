# [Medasis_Backend]主要技术框架

## 框架模块组成

- medasis-commons   通用模块
- medasis-dao 持久层模块：主要与数据库进行交互
- medasis-service  业务层模块：控制业务
- medasis-threeparty 第三方集成模块：集成第三方的组件
- medasis-api 控制层模块：控制业务逻辑

## 后端主要技术框架：

|     框架名      | 版本号 |           官网           |                             描述                             |
| :-------------: | :----: | :----------------------: | :----------------------------------------------------------: |
|   Spring Boot   | 2.6.6  |    https://spring.io/    | Spring Boot 可以轻松创建可以“直接运行”的独立的、生产级的基于 Spring 的应用程序。 |
| Spring Security | 5.6.3  |    https://spring.io/    | Spring Security 是一个功能强大且高度可定制的身份验证和访问控制框架。 |
|       JWT       | 0.9.1  |     https://jwt.io/      |            JWT 是凭证，可以授予对资源的访问权限。            |
|     Swagger     | 3.0.0  |   https://swagger.io/    |                Swagger 在线后端接口文档生成。                |
|     Mongodb     | 2.6.7  | https://www.mongodb.com/ |            用于在Spring框架中集成Mongodb相关操作             |
|      Redis      | 2.6.4  |    https://redis.com/    |             用于在Spring框架中集成Redis相关操作              |
|    OpenSlide    | 3.4.1  |  https://openslide.org/  | OpenSlide 是一个 C 库，它提供了一个简单的接口来读取整个幻灯片图像 |
|      Minio      | 8.3.4  | http://www.minio.org.cn/ | MinIO 是在 GNU Affero 通用公共许可证 v3.0 下发布的高性能对象存储 |
|                 |        |                          |                                                              |

## 数据库：

| 框架名  |  版本号  |                        官网                         |                             描述                             |
| :-----: | :------: | :-------------------------------------------------: | :----------------------------------------------------------: |
|   JDK   |   1.8    | https://www.oracle.com/java/technologies/downloads/ |                          开发工具包                          |
| MongoDB |  5.0.6   |              https://www.mongodb.com/               | MongoDB是一个基于分布式文件存储的数据库。由[C++](https://baike.baidu.com/item/C%2B%2B)语言编写。具有高性能，易部署，易使用的特点。 |
|  Redis  | 5.0.14.1 |                 https://redis.com/                  | 程字典服务，是一个开源的使用ANSI [C语言](https://baike.baidu.com/item/C语言)编写、支持网络、可基于内存亦可持久化的日志型、Key-Value[数据库](https://baike.baidu.com/item/数据库/103728)，并提供多种语言的API。 |

## Git分支规范【参与者必读】

### 版本管理中主要有以下几种类型的分支：

**master、dev、feature、release、hotfix**

- **其中master和dev分支只能有一个**
- **feature branch会有多个，代码版本开发过程中每个模块或开发某个功能点建的分支；**

- **release branch为发布某个版本建的分支，一般指对外发布的版本；**

- **hotfix branch指的是针对发布的release版本进行bug修复而建立的分支，修复完以后代码需要merge到dev上;**

**重点**：将项目拉到本地环境后，git上有master分支和dev分支

### **开发时注意点：**

在全局dev上建立自己dev分支，最终代码合并到自己的dev分支上，格式为：dev-名字-版本，如dev-lichong-v1

在开发模块工作时，你需要在自己的dev分支上建立自己的feature分支，格式为：feature-功能-xx，如feature-user-lichong

在修复模块bug时，你需要在自己的dev分支上建立自己的hotfix分支，格式为：hotfix-功能-xx，如hotfix-user-lichong

开发修改完毕后，先自测，通过后合并到自己的dev分支上，然后用自己的dev分支拉取全局dev分支(dev-medasis-xx)解决冲突，解决冲突后提交，使用全局dev合并自己dev上的代码。

### **在提交代码时注意点：**

不要提交未改动的代码和配置文件，通过设置

\# idea设置gitignore

file -> settings  -> file Types 

\>  ignored Files and Folders 点击加号 添加需要忽略的
\>
\> target
\> *.iml
\> .idea
\> *.log

然后再到IDEA上选择改动的文件进行提交，保障自己修改的文件提交。
注:别将.log文件提交，将log文件放到别的changelist去忽略
### 提交注释commit内容规定:

1. 提交的是修改文件，内容为xx update 修改了xxx文件，如:lichong update 修改了user相关业务文件
2. 提交的是新增文件，内容为xx add 修改了xxx文件，如:lichong add 新增了user相关业务文件
3. 提交的是删除文件，内容为xx delete 删除了xxx文件，如:lichong delete 删除了user相关业务文件
4. 提交的是bug修改文件，内容为xx bugfix 修改了xxx的bug，如:lichong bugfix 修改了用户登录失败的的bug

<u>若需要改动配置文件，请联系管理人员，确认后再进行更改和提交。</u>
*注：*每天最好提交一次代码（拉取dev分支代码解决冲突后提交至feature分支）

