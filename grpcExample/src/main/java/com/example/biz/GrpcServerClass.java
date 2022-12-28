package com.example.biz;

import io.grpc.*;

public class GrpcServerClass {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(7070)
                .addService(new MyUserServiceImpl())
                .build();
        server.start();
        System.out.println("Server start at 7070");
        server.awaitTermination();
    }
}
