package io.github.gaoxingliang;

import cn.hutool.core.io.*;
import cn.sichuancredit.apigateway.encryption.*;
import com.alibaba.fastjson.*;
import kong.unirest.HttpResponse;
import kong.unirest.*;
import org.apache.http.*;

import java.io.*;
import java.nio.charset.*;

public class ApiGatewayTest {

    // args: args[0] username   args[1] password
    public static void main(String[] args) throws Exception {

        String rbacToken = getRbacToken("http://devicbc.sichuancredit.cn:88/auth/token", "apigateway", args[0], args[1]);
        System.out.println(rbacToken);

        // issue request
        String body = "{\n" +
                "    \"type\":\"7\",\n" +
                "    \"service_url\" :\"/EFRS/INFOSERVICE2/companydetails/V3\",\n" +
                "    \"req_data\" :{\n" +
                "        \"key\":\"阿里巴巴(中国)有限公司\",\n" +
                "        \"keyType\":\"32\",\n" +
                "        \"model\":\"ENTINFO\"\n" +
                "    },\n" +
                "    \"source\":\"3\"\n" +
                "}";

        // 加密数据
        String privKey = FileUtil.readString(new File("D:\\temp\\priv.txt"), Charset.forName("UTF-8")).trim();
        String pubKey = FileUtil.readString(new File("D:\\temp\\pub.txt"), Charset.forName("UTF-8")).trim();


        // 随机生成Sm4密钥
        String sm4Key = MySmUtil.generateSm4Key();
        // 国密Sm2公钥加密Sm4秘钥
        String encryptKey = MySmUtil.sm2Encrypt(sm4Key, pubKey);
        // Sm4加密传输数据
        String data = MySmUtil.sm4Encrypt(body, sm4Key);
        // 请求参数map
        EncryptedData encryptedData = new EncryptedData();
        encryptedData.setData(data);
        encryptedData.setEncryptKey(encryptKey);
        String requestData = JSONObject.toJSONString(encryptedData);
        System.out.println("加密后："  + requestData);

        HttpResponse<String> response = Unirest.post("http://devicbc.sichuancredit.cn:88/v2/enterprises/customized/modules/rawdata")
                .header(HttpHeaders.AUTHORIZATION, rbacToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(requestData)
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
        }
    }


    public static String getRbacToken(String url, String appid, String u, String p) throws Exception {
        JSONObject d = new JSONObject();
        d.put("appid", appid);
        d.put("username", u);
        d.put("password", p);
        d.put("authType", 1);

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
