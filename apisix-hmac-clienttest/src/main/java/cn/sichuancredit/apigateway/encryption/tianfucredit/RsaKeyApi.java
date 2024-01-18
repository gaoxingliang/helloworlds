package cn.sichuancredit.apigateway.encryption.tianfucredit;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import java.util.*;

/**
 * Rsa加密解密工具
 *
 * @author hanbo
 * @version 1.0
 * @date 2020/11/2
 */
public class RsaKeyApi {
    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
        'c', 'd', 'e', 'f'};

    /**
     * 功能描述：随机生成密钥对
     *
     * @param keyType 秘钥对类型RSA\RSA2
     * @return /
     * @throws NoSuchAlgorithmException /
     */
    public static Map<String, String> genKeyPair(String keyType) throws NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>(2);
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyType);
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到公钥字符串
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64(privateKey.getEncoded()));
        map.put("pubKey", publicKeyString);
        map.put("priKey", privateKeyString);
        return map;
    }

    /**
     * 公钥加密过程
     *
     * @param pubKey        公钥
     * @param plainTextData 明文数据
     * @param keyType       加密类型RSA\RSA2
     * @return /
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] pubEncrypt(String pubKey, byte[] plainTextData, String keyType) throws Exception {
        if (pubKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        try {
            //获取公钥
            byte[] buffer = Base64.decodeBase64(pubKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(keyType);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            // 使用默认RSA
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(plainTextData);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥加密过程
     *
     * @param priKey        私钥
     * @param plainTextData 明文数据
     * @param keyType       加密类型RSA\RSA2
     * @return /
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] priEncrypt(String priKey, byte[] plainTextData, String keyType) throws Exception {
        if (priKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        try {
            byte[] buffer = Base64.decodeBase64(priKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(keyType);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            // 使用默认RSA
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(plainTextData);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param priKey     私钥
     * @param cipherData 密文数据
     * @param keyType    加密类型RSA\RSA2
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] priDecrypt(String priKey, byte[] cipherData, String keyType) throws Exception {
        if (priKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        try {
            byte[] buffer = Base64.decodeBase64(priKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(keyType);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            // 使用默认RSA
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(cipherData);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥解密过程
     *
     * @param pubKey     公钥
     * @param cipherData 密文数据
     * @param keyType    加密类型RSA\RSA2
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] pubDecrypt(String pubKey, byte[] cipherData, String keyType) throws Exception {
        if (pubKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        try {
            byte[] buffer = Base64.decodeBase64(pubKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(keyType);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            // 使用默认RSA
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(cipherData);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @param keyType      加密类型RSA\RSA2
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr, String keyType) throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(keyType);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 私钥数据字符串
     * @param keyType       加密类型RSA\RSA2
     * @throws Exception 加载私钥时产生的异常
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr, String keyType) throws Exception {
        try {
            byte[] buffer = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(keyType);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }


    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            // 取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }
}
