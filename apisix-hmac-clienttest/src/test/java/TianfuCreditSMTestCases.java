import cn.sichuancredit.apigateway.encryption.*;
import com.alibaba.fastjson.*;
import kong.unirest.*;
import org.apache.commons.codec.digest.*;
import org.junit.jupiter.api.*;

import java.util.*;

/**
 * 国密加密的天府信用通api
 * {@link TianfuCreditTestCases}
 */
public class TianfuCreditSMTestCases {

    @Test
    void test() throws Exception {
        // 按照实际修改为提供的信息
        String pub = TestCases.readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\tfcredit-bank-sm-pub-tf.txt");
        String priv = TestCases.readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\tfcredit-bank-sm-priv.txt");
        String orgcode = TestCases.readFile("D:\\code\\helloworlds\\apisix-hmac-clienttest\\tfcredit-bank-orgcode.txt");
        String url = "https://dev.sichuancredit.cn/xrtong/smv1/outerApi/product";

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
        // 随机生成Sm4密钥
        String sm4Key = MySmUtil.generateSm4Key();
        // 国密Sm2公钥加密Sm4秘钥
        String encryptKey = MySmUtil.sm2Encrypt(sm4Key, pub);
        String data = MySmUtil.sm4Encrypt(body.toJSONString(), sm4Key);
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
            // 国密Sm2私钥解密Sm4密码获得Sm4密钥
            String respSm4Key = MySmUtil.sm2Decrypt(resp.getEncryptKey(), priv);
            // 国密Sm4解密返回的数据
            String dataStr = MySmUtil.sm4Decrypt(resp.getData(), respSm4Key);
            System.out.println("请求返回：");
            System.out.println(dataStr);
        } else {
            System.err.println("Non 200 code " + result.getHeaders() + " " + result.getStatus() + " " + result.getBody());
        }
    }
}
