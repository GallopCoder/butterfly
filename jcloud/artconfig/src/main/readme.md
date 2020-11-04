config server IP:PORT/{name}/{profile}/{label}/**    获取配置文件信息

新建的配置文件名称规范为：clientId-profile.properties|yml

只有符合规范的文件才可以被springcloud远程调用，

clientId：指该模块在注册中心注册的ID，即spring.application.name的名称

profile：指在什么环境下的配置文件，dev是开发环境，pro是正式环境