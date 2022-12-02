# Spring Aspect cheatsheet
放一些简单易用的spring aspect 语法手册。


# Why？
就像之前写的btrace手册一样， 为了方便查找和实际操作，想分享一些关于写Aspect相关的cheatsheet。

## 概念
1. Aspect<br>
    作为某个关注点、横切面在许多类的模块化集合。比如事务管理`@Transactional`就是在企业应用中事务相关的的横切面。
2. Join Point <br>
    代表某个程序执行的点。在spring中代表某个方法的执行点。
3. Advice <br>
    在某个特殊的Join Point，Aspect采取的操作。包含不同的类型（around，before，after）。许多aop框架包括
    spring用拦截器来实现Advice，并且维护一个拦截器链来处理某个JointPoint的多个Advice。
4. Pointcut <br>
    定义满足某些定义条件的方法。Advice将在所有满足Pointcut的JoinPoint执行。比如Pointcut可以定义为
    在所有的Controller方法上执行（`within(com.example.controller.*)`）。本文也将提供更多的例子方便查找。
5. Introduction <br>
    以类为基础去添加新的方法或者属性。比如可以为某些bean引入一个属性`IsModified`来方便实现缓存。
6. Target object <br>
    等着被aspect增强的类对象（也叫`advised object`）。因为spring用代理来实现的，所以这个也叫被代理对象。
7. AOP proxy <br>`
    为了实现aspect，通过JDK动态代理,或者cglib代理实现的增强过的对象。
8. Weaving <br>
    将多个aspect和真正的应用内组合在一起的过程， 可以在编译时（aspectj compiler），加载时，运行时(spring aop)实现。

### Advice类型
1. Before <br>
2. AfterReturning <br>
3. AfterThrowing <br>
4. Around <br>
    around advice会有一个JointPoint参数可以用来获取当前执行方法的相关信息。这是最强的一类advice。它提供`proceed`方法来
   调用原有逻辑。
   

# 参考
1. [spring官网文档](https://docs.spring.io/spring-framework/docs/5.3.10/reference/html/core.html#aop)