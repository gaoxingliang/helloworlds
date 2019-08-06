<%--
  Created by IntelliJ IDEA.
  User: edward.gao
  Date: 2019/8/6
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>测试表单</title>
  </head>
  <body>
    <form action="/param1" method="post">
      用户名：<input type="text" name="username"><br/>
      密码：<input type="text" name="password"><br/>
      <br/>
      <input type="submit" value="提  交 到param1 自己从Req里面获取">
    </form>


    <form action="/param2" method="post">
      用户名：<input type="text" name="username"><br/>
      密码：<input type="text" name="password"><br/>
      <br/>
      <input type="submit" value="提  交 到param2 直接使用同名参数">
    </form>

    <form action="/param3" method="post">
      用户名：<input type="text" name="YourUsername"><br/>
      密码：<input type="text" name="password"><br/>
      <br/>
      <input type="submit" value="提  交 到param3 使用@RequestParam来指定UI发过来的名字">
    </form>

    <form action="/param4" method="post">
      用户名：<input type="text" name="username"><br/>
      密码：<input type="text" name="password"><br/>
      <br/>
      <input type="submit" value="提  交 到param4 使用模型传参">
    </form>

  </body>
</html>
