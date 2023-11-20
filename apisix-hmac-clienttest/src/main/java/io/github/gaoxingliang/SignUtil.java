package io.github.gaoxingliang;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.charset.*;
import java.security.*;
import java.text.*;
import java.util.*;

public class SignUtil {
    public static final String RBAC_TOKEN_HEADER = "X-RBAC-TOKEN";
    public static final String SIGNATURE_HEADER = "X-HMAC-SIGNATURE";
    public static final String DATE_HEADER = "DATE";
    public static final String ALGORITHM_HEADER = "X-HMAC-ALGORITHM";
    public static final String ACCESSKEY_HEADER = "X-HMAC-ACCESS-KEY";
    public static final String BODY_DIGEST_HEADER = "X-HMAC-DIGEST";
    public static final String SIGNED_HEADERS_HEADER = "X-HMAC-SIGNED-HEADERS";

    public static final List<String> SIGNED_HEADERS;
    static {
        //SIGNED_HEADERS = Arrays.asList("Host", "X-Provider-ID", "User-Agent");
        SIGNED_HEADERS = Arrays.asList("Content-Type", "X-Provider-ID");
        Collections.sort(SIGNED_HEADERS, String.CASE_INSENSITIVE_ORDER);
    }

    public static String getDate() {
        return getDate(System.currentTimeMillis());
    }

    public static String getDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(time));
    }


    public static String generateSignature(String accessKey, String secretKey, String requestMethod, String canonicalUri, Map<String, ?> args,
                                             Map<String, String> headers) throws NoSuchAlgorithmException,
            InvalidKeyException {
        String canonicalQueryString = "";

        if (StringUtils.isEmpty(canonicalUri)) {
            canonicalUri = "/";
        }

        if (args != null && !args.isEmpty()) {
            List<String> keys = new ArrayList<>(args.keySet());
            Collections.sort(keys);
            List<String> queryTab = new ArrayList<>();
            for (String key : keys) {
                Object value = args.get(key);
                if (value != null ) {
                    queryTab.add(key + "=" + value);
                } else {
                    queryTab.add(key);
                }
            }
            canonicalQueryString = String.join("&", queryTab);
        }

        String date = getDate();

        List<String> signingStringItems = new ArrayList<>(Arrays.asList(
                requestMethod,
                canonicalUri,
                canonicalQueryString,
                accessKey,
                date
        ));

        for (String header : SIGNED_HEADERS) {
            String canonicalHeader = headers.getOrDefault(header, "");
            signingStringItems.add(header + ":" + canonicalHeader);
        }

        String signingString = String.join("\n", signingStringItems) + "\n";

        byte[] signatureBytes = sign(secretKey.getBytes(), signingString);
        String generatedSignature = Base64.encodeBase64String(signatureBytes);

        return generatedSignature;
    }


    public static byte[] sign(byte[] key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        return mac.doFinal(Optional.ofNullable(data).orElse("").getBytes(StandardCharsets.UTF_8));
    }
}
