package io.github.gaoxingliang;

import com.google.common.base.*;
import kong.unirest.*;
import org.apache.http.*;

import java.security.*;
import java.util.*;

public class HmacGetRequestTest {
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

        signedHeaders.put("X-Provider-ID", "C_18141A");
        signedHeaders.put("Content-Type", "application/json");
        String body = "";

        String uri = "/enterprises/ent-ba/modules/legalpersoncheck";
        String date = SignUtil.getDate();
        Map<String, Object> qp = new TreeMap<>();
        qp.put("id", "511111199011110005");
        qp.put("name", "张三");
        qp.put("phone", "17728897200");
        String signature = SignUtil.generateSignature(accessKey, secretKey, "GET", uri, qp, signedHeaders);

        String authHeader = String.format("hmac-auth-v1#%s#%s#%s#%s#%s", accessKey, signature, "hmac-sha256", date,
                Joiner.on(";").join(SignUtil.SIGNED_HEADERS));
        String bodyDigestHeader = Base64.getEncoder().encodeToString(SignUtil.sign(secretKey.getBytes(), body));

        String response = Unirest.get("http://devicbc.sichuancredit.cn:88" + uri)
                .queryString(qp)
                .headers(signedHeaders)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .header(SignUtil.BODY_DIGEST_HEADER, bodyDigestHeader)
                .asString().getBody();

        System.out.println(response);
    }
}