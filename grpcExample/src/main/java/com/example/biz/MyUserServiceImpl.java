package com.example.biz;

import com.example.grpc.common.vo.*;
import com.example.grpc.user.*;
import io.grpc.stub.*;

public class MyUserServiceImpl extends UserGrpc.UserImplBase {
    @Override
    public void login(UserOuterClass.LoginRequest request, StreamObserver<UserOuterClass.APIResponse> responseObserver) {
        System.out.println("LoginRequest " + request.getUsername() + " " + request.getPassword());
        UserOuterClass.APIResponse.Builder b = UserOuterClass.APIResponse.newBuilder();
        if (request.getUsername().equals(request.getPassword())) {
            b.setResponseCode(0);
            b.setResponsemessage("ok");
        } else {
            b.setResponseCode(1);
            b.setResponsemessage("FAIL");
        }
        responseObserver.onNext(b.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(Vo.Empty request, StreamObserver<UserOuterClass.APIResponse> responseObserver) {
    }
}
