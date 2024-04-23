package io.github.gaoxingliang;

import com.google.common.base.*;
import kong.unirest.*;
import org.apache.http.*;

import java.security.*;
import java.util.*;

public class HmacPostRequestTest {
    /**
     * args[0] : accessKey
     * args[1] : secretKey
     *
     * @param args
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String accessKey = args[0];
        String secretKey = args[1];

        Map<String, String> signedHeaders = new HashMap<>();

        signedHeaders.put("X-Provider-ID", "TEST");
        signedHeaders.put("Content-Type", "application/json");
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

        String uri = "/enterprises/customized/modules/rawdata";
        String date = SignUtil.getDate();

        String signature = SignUtil.generateSignature(accessKey, secretKey, "POST", uri, null, signedHeaders);

        String authHeader = String.format("hmac-auth-v1#%s#%s#%s#%s#%s", accessKey, signature, "hmac-sha256", date,
                Joiner.on(";").join(SignUtil.SIGNED_HEADERS));
        String bodyDigestHeader = Base64.getEncoder().encodeToString(SignUtil.sign(secretKey.getBytes(), body));

        String response = Unirest.post("http://devicbc.sichuancredit.cn:88" + uri)
                .headers(signedHeaders)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .header(SignUtil.BODY_DIGEST_HEADER, bodyDigestHeader)
                .body(body)
                .asString().getBody();

        System.out.println(response);
    }
}