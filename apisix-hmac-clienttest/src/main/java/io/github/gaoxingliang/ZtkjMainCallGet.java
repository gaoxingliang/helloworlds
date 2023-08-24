package io.github.gaoxingliang;

import com.google.common.base.*;
import kong.unirest.*;
import org.apache.http.*;

import java.security.*;
import java.util.*;

public class ZtkjMainCallGet {
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

        signedHeaders.put("X-Provider-ID", "C_26201110A");
        signedHeaders.put("Content-Type", "application/json");
        String body = "";

        String uri = "/persons/ent-ba/modules/entemployrelid";
        String date = SignUtil.getDate();

        Map<String, String> arg = new HashMap<>();
        arg.put("userIdCard", "50023519990912019201");
        arg.put("userName", "gaoxingliang");

        String signature = SignUtil.generateSignature(accessKey, secretKey, "GET", uri, arg, signedHeaders);

        String authHeader = String.format("hmac-auth-v1#%s#%s#%s#%s#%s", accessKey, signature, "hmac-sha256", date,
                Joiner.on(";").join(SignUtil.SIGNED_HEADERS));
        String bodyDigestHeader = Base64.getEncoder().encodeToString(SignUtil.sign(secretKey.getBytes(), body));

        String response = Unirest.get("http://devicbc.sichuancredit.cn:88" + uri)
                .headers(signedHeaders)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .header(SignUtil.BODY_DIGEST_HEADER, bodyDigestHeader)
                //.queryString(arg)
                .queryString("userIdCard", "50023519990912019201")
                .queryString("userName", "gaoxingliang")
                .asString().getBody();

        System.out.println(response);
    }
}