package cn.sichuancredit.apigateway.encryption.tianfucredit;

import org.apache.commons.codec.binary.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;

/**
 * Aes加密解密
 *
 * @author hanbo
 * @version 1.0
 * @date 2020/11/2
 */
public class AesKeyApi {


    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 加密/解密算法 /工作模式/填充模式
     * Java 6支持PKCS5PADDING填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     * 模式说明：
     * 算法/模式/填充                16字节加密后数据长度        不满16字节加密后长度
     * AES/CBC/NoPadding             16                          不支持
     * AES/CBC/PKCS5Padding          32                          16
     * AES/CBC/ISO10126Padding       32                          16
     * AES/CFB/NoPadding             16                          原始数据长度
     * AES/CFB/PKCS5Padding          32                          16
     * AES/CFB/ISO10126Padding       32                          16
     * AES/ECB/NoPadding             16                          不支持
     * AES/ECB/PKCS5Padding          32                          16
     * AES/ECB/ISO10126Padding       32                          16
     * AES/OFB/NoPadding             16                          原始数据长度
     * AES/OFB/PKCS5Padding          32                          16
     * AES/OFB/ISO10126Padding       32                          16
     * AES/PCBC/NoPadding            16                          不支持
     * AES/PCBC/PKCS5Padding         32                          16
     * AES/PCBC/ISO10126Padding      32                          16
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 功能描述：转换密钥
     *
     * @param key 二进制密钥
     * @return /
     * @throws Exception /
     */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化DES密钥材料
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 功能描述：解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     * @throws Exception /
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        // 实例化
        // 使用PKCS7Padding填充方式，按如下方式实现
        /// Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 功能描述：解密
     *
     * @param base64Data 待解密Base64编码数据
     * @param key        Base64编码密钥
     * @return String 解密数据
     * @throws Exception /
     */
    public static String decrypt(String base64Data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        /// 实例化
        //  使用PKCS7Padding填充方式，按如下方式实现
        //  Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        byte[] decodeBase64 = Base64.decodeBase64(base64Data);
        byte[] doFinal = cipher.doFinal(decodeBase64);
        return new String(doFinal);
    }

    /**
     * 功能描述：加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后数据
     * @throws Exception /
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        // 实例化
        // 使用PKCS7Padding填充方式，按如下方式实现
        /// Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 功能描述：解密
     *
     * @param data      待解密Base64编码数据
     * @param keyBase64 编码密钥
     * @return String 解密数据
     * @throws Exception /
     */
    public static String decryptBase64(String data, String keyBase64) throws Exception {
        byte[] key = Base64.decodeBase64(keyBase64);
        //还原密钥
        Key k = toKey(key);
        /// 实例化
        //  使用PKCS7Padding填充方式，按如下方式实现
        //  Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        byte[] decodeBase64 = Base64.decodeBase64(data);
        byte[] doFinal = cipher.doFinal(decodeBase64);
        return new String(doFinal);
    }

    /**
     * 功能描述：加密
     *
     * @param data      待加密数据
     * @param keyBase64 Base64加密后的密钥
     * @return String Base64加密后数据
     * @throws Exception /
     */
    public static String encryptBase64(String data, String keyBase64) throws Exception {
        byte[] key = Base64.decodeBase64(keyBase64);
        //还原密钥
        Key k = toKey(key);
        /// 实例化
        //  使用PKCS7Padding填充方式，按如下方式实现
        //  Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        byte[] final1 = cipher.doFinal(data.getBytes());
        //base64加密编码
        byte[] base64 = Base64.encodeBase64(final1);
        return new String(base64);
    }

    /**
     * 功能描述：生成密钥
     *
     * @return byte[] 二进制密钥
     * @throws Exception /
     */
    public static byte[] initKey() throws Exception {
        //实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES要求密钥长度为128位、192位、256位
        kg.init(128);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 功能描述：生成密钥
     *
     * @return String Base64加密后密钥
     * @throws Exception /
     */
    public static String initBase64Key() throws Exception {
        //实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES要求密钥长度为128位、192位、256位
        kg.init(128);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获得密钥的二进制编码形式
        byte[] encodedKey = secretKey.getEncoded();
        //Base64对密钥进行加密
        byte[] base64 = Base64.encodeBase64(encodedKey);
        return new String(base64);
    }
}
