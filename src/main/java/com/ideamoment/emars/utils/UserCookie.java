package com.ideamoment.emars.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class UserCookie {

    private final static Logger LOG = LoggerFactory.getLogger(UserCookie.class);

    private static final String ENCRYPT_KEY = "A1B2C3D4A1B2C3D4A1B2C3D4A1B2C3D4";

    public static final String USERID = "userId";
    public static final String USERNAME = "userName";
    public static final String VERSION = "version";
    public static final String EXPIRE = "expire";

    public static final int EXPIRE_TIME = 3600;

    public static String generateCookieValue(Map<String, Object> userInfo) {
        String content = userInfo2String(userInfo);
        String result = encrypt(content, ENCRYPT_KEY);

        return result;
    }

    public static Map<String, Object> parseCookieValue(String cookieValue) {
        String content = decrypt(cookieValue, ENCRYPT_KEY);
        return string2UserInfo(content);
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param encryptKey  加密密钥
     * @return
     */
    public static String encrypt(String content, String encryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            //防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(encryptKey.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param content  待解密内容
     * @param decryptKey 解密密钥
     * @return
     */
    public static String decrypt(String content, String decryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(decryptKey.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));
            return new String(result); // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String userInfo2String(Map<String, Object> userInfo) {
        LOG.info("userInfo={}",userInfo);
        Long userId = Long.valueOf(String.valueOf(userInfo.get("userId")));
        String userName = (String)userInfo.get("userName");
        try {
            userName = Base64Utils.encode(userName.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String version = (String)userInfo.get("version");
        Long tenantId =null;
        if(userInfo.get("tenantId") != null){
            tenantId = Long.valueOf(String.valueOf(userInfo.get("tenantId")));
        }

        if(userId == null
                || userName == null
                || version == null) {
            throw new RuntimeException("Data error");
        }

        String result = version + "#" + userId.toString() + "#" + userName + "#" + EXPIRE_TIME;
        return result;
    }

    public static Map<String, Object> string2UserInfo(String content) {
        String[] chunks = content.split("#");

        String version = chunks[0];
        Long userId = new Long(chunks[1]);
        String userName = chunks[2];
        try {
            userName = new String(Base64Utils.decode(userName.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Integer expireTime = new Integer(chunks[3]);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(USERID, userId);
        result.put(USERNAME, userName);
        result.put(VERSION, version);
        result.put(EXPIRE, expireTime);

        return result;
    }
}
