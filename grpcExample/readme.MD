# 关于
一个完整的grpc client & server的例子
视频教程参考：
BUILD GRPC
https://www.youtube.com/watch?v=JFzAe9SvNaU&list=PLI5t0u6ye3FGXJMh5kU2RvN0xrul67p7R&index=1
<br>但是给了更加复杂的例子比如多包引用。

使用的grpc插件- https://github.com/os72/protoc-jar-maven-plugin

# how to 
1. 执行mvn即可生成对应的java代码和stub类。所有在com.example.grpc的都是代码生成的。
2. 测试的Server在[com.example.biz.GrpcServerClass](./src/main/java/com/example/biz/GrpcServerClass.java)
3. 测试的Client在[com.example.biz.GrpcClientClass](./src/main/java/com/example/biz/GrpcClientClass.java)