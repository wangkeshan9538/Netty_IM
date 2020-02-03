# Netty_IM
介绍 ： 一个基于vue netty ，使用websocket协议的即时聊天系统  ， site：http://47.105.88.240:8080/

1.  前端在front 目录下，建议在 vue ui下打包
2.  后端打包之后 启动命令 java -jar -Dfront_dir=E:\\code\\Netty_IM\\front\\im_ui\\dist netty_im-1.0.jar 

启动参数：
-Dfront_dir   --前端打包地址

启动后直接打 http://host  即可访问，登录后 在线页面中 **王柯杉**用户是长久在线，对他说话可以反馈issue到log中

主要功能基本完成，待完善或可继续探索的方向：
1. 暂时没有设置注册，所以登录账号密码不持久保存，关闭页面即失效且从内存中删除用户信息，
2. ssl 的支持，
3. 可以做群聊功能
4. 可以对整个系统做一下压力测试，看能承受多少人同时在线

