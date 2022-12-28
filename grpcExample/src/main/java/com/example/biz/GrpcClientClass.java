package com.example.biz;

import com.example.grpc.user.*;
import io.grpc.*;

public class GrpcClientClass {
    public static void main(String[] args) {
        ManagedChannel mc = ManagedChannelBuilder.forAddress("localhost", 7070)
                .usePlaintext().build();
        UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(mc);
        UserOuterClass.APIResponse resp = stub.login(UserOuterClass.LoginRequest.newBuilder().setUsername("hello").setPassword("hello").build());
        System.out.println("Got response " + resp);
        mc.shutdown();

    }
}
