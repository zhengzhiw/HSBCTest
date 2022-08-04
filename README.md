#金额记录器文档说明
##包、类说明
![图片名称](static/package_introduction.png)  
##启动指导
###开发工具中启动
使用idea打开项目，配置好maven、jdk后，导入项目jar包依赖之后，进入`src\main\java\currencyController\StartMain.java`运行main方法即可启动
###脚本文件启动
获取所有的货币金额：`http://localhost:8080/getAmount/all`
做题思路：
1.启动类之后，使用单例模式实例化一个虚拟银行，每次输入从银行抵扣金额
2.使用Timer定时打印银行余额



