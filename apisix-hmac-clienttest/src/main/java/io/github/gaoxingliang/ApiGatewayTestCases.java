package io.github.gaoxingliang;

import cn.hutool.core.io.*;
import cn.sichuancredit.apigateway.encryption.*;
import com.alibaba.fastjson.*;
import kong.unirest.HttpResponse;
import kong.unirest.*;
import org.apache.http.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class ApiGatewayTestCases {

    public static final String endpoint = "http://devicbc.sichuancredit.cn:88";
    private static String token, privKey, pubKey;

    // args: args[0] username   args[1] password
    public static void main(String[] args) throws Exception {

        String rbacToken = getRbacToken(endpoint + "/auth/token", "data", args[0], args[1]);
        System.out.println(rbacToken);
        token = rbacToken;

        // 加密数据
        privKey = FileUtil.readString(new File("D:\\temp\\priv.txt"), Charset.forName("UTF-8")).trim();
        pubKey = FileUtil.readString(new File("D:\\temp\\pub.txt"), Charset.forName("UTF-8")).trim();

        testScale();
        testBenefitPerson();
    }

    public static void testBenefitPerson() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "四川征信有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/beneficiaries", params);
    }

    public static void testScale() {
        Map<String, Object> params = new HashMap<>();
        params.put("enterprise", "四川征信有限公司");
        getRequest("/v2/enterprises/ent-ba/modules/scale", params);
    }

    public static void getRequest(String path, Map<String, Object> params) {
        // GET请求示例
        System.out.println("Get 请求开始-----------");
        HttpResponse<String> response = Unirest.get(endpoint + path)
                .header(HttpHeaders.AUTHORIZATION, token)
                .queryString(params)
                .asString();
        System.out.println("收到回复" + response.getBody() + " headers:" + response.getHeaders() + " status:" + response.getStatus());
        if (response.getStatus() == 200) {
            // 解密
            EncryptedData responseEncryptedData = JSONObject.parseObject(response.getBody(), EncryptedData.class);
            String sm4Key2 = MySmUtil.sm2Decrypt(responseEncryptedData.getEncryptKey(), privKey);
            // 国密Sm4解密返回的数据
            String dataStr = MySmUtil.sm4Decrypt(responseEncryptedData.getData(), sm4Key2);
            System.out.println("Sm解密数据为: " + dataStr);
        }  else {
            System.err.println("Non 200 return code");
            System.exit(1);
        }
    }


    public static String getRbacToken(String url, String appid, String u, String p) throws Exception {
        JSONObject d = new JSONObject();
        d.put("appid", appid);
        d.put("username", u);
        d.put("password", p);

        HttpResponse<String> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(d.toString())
                .asString();
        if (response.getStatus() != 200) {
            throw new Exception("Invalid rbac response : " + response.getBody());
        }

        com.alibaba.fastjson2.JSONObject resp = com.alibaba.fastjson2.JSONObject.parse(response.getBody());

        System.out.println(response.getBody());
        String t = resp.getString("rbac_token");
        if (t == null) {
            throw new IllegalArgumentException("No token found in rbac response");
        }

        return t;
    }
}
