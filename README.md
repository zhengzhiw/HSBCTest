###开发工具中启动
使用idea打开项目，配置好maven、jdk后，刷新maven导入项目jar包依赖之后，进入`src\main\java\currencyController\StartMain.java`运行main方法即可启动
<br>做题思路：
<br>1.启动类之后，使用单例模式实例化一个虚拟银行，每次输入从银行抵扣金额
<br>2.使用Timer定时打印银行余额
<br>3.提供接口:currency表示币种;amount表示金额;count表示一次性存款次数
    获取所有的货币金额：`http://localhost:8090/currencyBank/all`
    保存金额：`http://localhost:8090/currencyBank/save?currency=HKD&amount=100`
    提供服务发送事件:`http://localhost:8090/{currency}/{amount: [0-9]+}/send/{count:[0-9]+}`