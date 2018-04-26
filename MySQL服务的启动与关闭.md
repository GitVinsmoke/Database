# <font color="red">MySQL服务的启动与关闭</font>

#### 查看并启动MySQL服务

通过Windows的服务管理器查看。“开始”——“运行”，输入"services.msc"，回车。弹出Windows的服务管理器，然后就可以看见服务名为"MySQL"的服务项了，其右边标明“正在运行”就说明服务已经启动了。如图：

![MySQL服务.PNG](https://img-blog.csdn.net/20180405185901200)

如果没有启动，则可以在服务里启动。也可以通过另一种方式来启动它。用管理员身份运行命令提示符（避免拒绝访问），输入"net start mysql57"就可以启动MySQL服务了（这里是你的MySQL名称，我的是MySQL57），停止服务通过输入"net stop MySQL57"。

![捕获.PNG](https://img-blog.csdn.net/20180405185913235)

#### 连接MySQL服务器

1. 先在命令行（管理员身份）中进入MySQL的安装目录下的bin目录。

2. 在输入连接mysql服务器的命令：mysql -h 主机名 -u 用户名 -p 用户密码，用户密码在下一步才输入。如图：

	![连接服务.PNG](https://img-blog.csdn.net/20180405185925225)

3. 接下来便可以对数据库进行相关的操作了，如：

	![显示数据库.PNG](https://img-blog.csdn.net/2018040518593564)

