# mybatis_learn
学习mybatis源码及应用

#1.第一模块

   ##Persistence与Persistence_test 自定义mybatis持久层框架及测试该框架
   
   ##1.1 使用端提供sqlMapConfig.xml(包含mapper.xml)
   
   ##1.2 自定义框架端根据xml路径获取字节输入流
   
   ##1.3 然后将输入流解析成configuration与MappedStatement(一个sql执行语句为一个MappedStatement),并将MappedStatement封装到configuration中
   
   ##1.4 通过SqlSessionFactroryBuilder调用DefaultSqlSessionFactory获取SqlSessionFactrory (以构造方法实现的)
   
   ##1.5 通过SqlSessionFactrory 调用DefaultSqlSession获取SqlSession (以构造方法实现的)
   
   ##1.6 通过动态代理及反射实现获取不同Mapper,这儿需要注意，Mapper中定义的方法名一定要与mapper.xml中的id一样，不然就找不到了
   
   ##1.7 实现query方法:
   
   ######对xml中的sql进行替换处理(将#{}或者${}替换成?)，这儿主要用了标记处理类进行标记处理
   
   ######根据sql获取预编译PreparedStatement对象，并对sql中的?进行占位符替换
   
   ######最后将查询出来的结果封装到需要返回的对象中
   
   #其中涉及到了工厂模式，代理模式
        
    
        
