# Spring Aspect cheatsheet
放一些简单易用的spring aspect 语法手册。<br>
引入库， 参考[build.gradle](./build.gradle)

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
    定义满足某些定义条件的方法切面。Advice将在所有满足Pointcut的JoinPoint执行。比如Pointcut可以定义为
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
   

## PointCut常用示例

| 示例                                                         | 解释                                                   |
| ------------------------------------------------------------ | ------------------------------------------------------ |
| `within(com.xyz.service.*)`                                  | 执行com.xyz.service（不包括子包）的所有类的方法        |
| `within(com.xyz.service..*)`                                 | 执行com.xyz.service（包括子包）的所有类的方法          |
| `this(com.xyz.service.AccountService)`                       | 执行实现了AccountService的接口的类                     |
| `execution(* com.example.springaop.controller.*.*(..))`      | 执行                                                   |
| `execution(public * *(..))`                                  | 执行所有public方法                                     |
| `execution(* set*(..))`                                      | 执行所有方法名以set开始的方法                          |
| `execution(* com.xyz.service.AccountService.*(..))`          | 执行所有在com.xyz.service.AccountService类里面的方法。 |
| `execution(* com.xyz.service.*.*(..))`                       | 执行所有在com.xyz.service里面的方法（不包括子包）      |
| `execution(* com.xyz.service..*.*(..))`                      | 执行所有在com.xyz.service里面的方法(包括子包)          |
| `@within(org.springframework.transaction.annotation.Transactional)` | 某个类具有@Transactional注解                           |
| ` @annotation(org.springframework.transaction.annotation.Transactional)` | 某个方法具有@Transactional注解                         |

具体语法：
``
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
throws-pattern?)
``

### Advice使用示例
1. Before
方法开始执行前，可以使用`JoinPoint`获取当前执行方法的状态。后续几个都可以获取该参数。
```java
    @Before("execution(* com.xyz.myapp.dao.*.*(..))")
    public void doAccessCheck(JoinPoint jp) {
    
    }
```

2. AfterReturning <br>
   正常返回后：
   ```java
   @AfterReturning(
        pointcut="com.xyz.myapp.CommonPointcuts.dataAccessOperation()",
        returning="retVal")
    public void doAccessCheck(Object retVal) {
        // ...
    }
   ```
3. AfterThrowing <br>
   抛异常后：
    ```java
   @AfterThrowing(
        pointcut="com.xyz.myapp.CommonPointcuts.dataAccessOperation()",
        throwing="ex")
    public void doRecoveryActions(DataAccessException ex) {
        // ...
    } 
   ```   

4. Arround
环绕通知可以使用`ProceedingJoinPoint` 获取当前执行方法的信息。
```java
   @Around("com.xyz.myapp.CommonPointcuts.businessService()")
   public Object timing(ProceedingJoinPoint jp) throws Throwable {
        // start stopwatch
        Object retVal = jp.proceed(); 
        // stop stopwatch
        return retVal;
   }   
```

5. 给Advice传参数。
除了上面的JoinPoint可以拿到参数外，还可以用如下的方式拿到：
```java
@Before("com.xyz.myapp.CommonPointcuts.dataAccessOperation() && args(account,..)")
public void validateAccount(Account account) {
    // ...
}
```
上面例子中account是第一个参数。 当然dataAccessOperation指定的PointCut必须保证有至少一个参数。

6. Advice的顺序
按照下面顺序执行：
```java
@Around, @Before, @After, @AfterReturning, @AfterThrowing
```
不同Aspect的还可以通过@Order指定顺序



# 参考
1. [spring官网文档](https://docs.spring.io/spring-framework/docs/5.3.10/reference/html/core.html#aop)