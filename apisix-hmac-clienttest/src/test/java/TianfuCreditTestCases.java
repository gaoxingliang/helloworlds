import cn.sichuancredit.apigateway.encryption.*;
import cn.sichuancredit.apigateway.encryption.tianfucredit.*;
import com.alibaba.fastjson.*;
import kong.unirest.*;
import org.apache.commons.codec.digest.*;
import org.apache.commons.lang3.*;
import org.junit.jupiter.api.*;

import java.nio.charset.*;
import java.util.*;

public class TianfuCreditTestCases {

    @Test
    void test() throws Exception {
        // 按照实际修改为提供的信息
        String pub = TestCases.readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\tfcredit-bank-pub.txt");
        String priv = TestCases.readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\tfcredit-bank-priv.txt");
        String orgcode = TestCases.readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\tfcredit-bank-orgcode.txt");
        String url = "https://dev.sichuancredit.cn/xrtong/v1/outerApi/product";

        // 请求参数
        TreeMap dataMap = new TreeMap();
        dataMap.put("apiCode", "PRODUCT-LIST");
        dataMap.put("financeInstitutionCode", orgcode);
        dataMap.put("userName", "userabc");
        dataMap.put("phone", "151980123456");
        dataMap.put("timestamp", System.currentTimeMillis() + "");
        StringBuilder dataMapString = new StringBuilder();
        dataMap.forEach((k,v) -> dataMapString.append(k).append("=").append(v).append("&"));
        dataMapString.deleteCharAt(dataMapString.length() - 1);
        // 签名只有这几个部分需要
        String sign = DigestUtils.md5Hex(dataMapString.toString()).toUpperCase();
        dataMap.put("sign", sign);
        // 融资产品无其他参数
        dataMap.put("content", new JSONObject());

        JSONObject body = new JSONObject();
        body.putAll(dataMap);
        String pwd = RandomStringUtils.randomAlphanumeric(16);
        byte [] encryptedByte = RsaKeyApi.pubEncrypt(pub, pwd.getBytes(), "RSA");
        byte [] aesData = AesKeyApi.encrypt(body.toJSONString().getBytes(StandardCharsets.UTF_8), pwd.getBytes());

        String encryptKey = Base64.getEncoder().encodeToString(encryptedByte);
        String data = Base64.getEncoder().encodeToString(aesData);
        // 请求参数map
        EncryptedData encryptedData = new EncryptedData();
        encryptedData.setData(data);
        encryptedData.setEncryptKey(encryptKey);
        String requestData = JSONObject.toJSONString(encryptedData);

        HttpResponse<String> result = Unirest.post(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("financeInstitutionCode", orgcode)
                .body(requestData)
                .asString();
        if (result.isSuccess()) {
            System.out.println( result.getHeaders() + " " + result.getStatus() + " " + result.getBody());
            EncryptedData resp = JSONObject.parseObject(result.getBody(), EncryptedData.class);

            // 解密返回：
            byte [] aesKey = RsaKeyApi.priDecrypt(priv, Base64.getDecoder().decode(resp.getEncryptKey()), "RSA");
            byte [] resultData = AesKeyApi.decrypt(Base64.getDecoder().decode(resp.getData()), aesKey);
            System.out.println("请求返回：");
            System.out.println(new String(resultData));
        } else {
            System.err.println("Non 200 code " + result.getHeaders() + " " + result.getStatus() + " " + result.getBody());
        }
    }
}
