package gaoxingliang.github.com;

import com.google.common.base.*;
import kong.unirest.*;
import org.apache.http.*;

import java.security.*;
import java.util.*;

public class Main {
    /**
     * args[0] : accessKey
     * args[1] : secretKey
     *
     * @param args
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String accessKey = args[0];
        String secretKey = args[1];

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Provider-ID", "B_181524");
        String body = "{\n" +
                "    \"type\":\"3\",\n" +
                "    \"service_url\" :\"/csi/riskname/list/search/V4\",\n" +
                "    \"req_data\" :{\n" +
                "        \"key\":\"中国工商银行股份有限公司\",\n" +
                "        \"keyType\":\"32\",\n" +
                "        \"model\":\"46\"\n" +
                "    },\n" +
                "    \"source\":\"3\"\n" +
                "}";

        String uri = "/enterprises/customized/modules/rawdata";
        String date = SignUtil.getDate();

        String signature = SignUtil.generateSignature(accessKey, secretKey, "POST", uri, null, headers);

        String authHeader = String.format("hmac-auth-v1#%s#%s#%s#%s#%s", accessKey, signature, "hmac-sha256", date,
                Joiner.on(";").join(SignUtil.SIGNED_HEADERS));


        String response = Unirest.post("http://devicbc.sichuancredit.cn:88" + uri)
                .headers(headers)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .asString().getBody();

        System.out.println(response);
    }
}