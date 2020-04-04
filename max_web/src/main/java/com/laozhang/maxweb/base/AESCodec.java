package com.laozhang.maxweb.base;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;

public class AESCodec {
	private final static String IV = "0102030405060708";
    private final static String ALGORITHM = "AES/ECB/PKCS5Padding";
    public final static String key = "xZtgbhfoiaxqeyGo";

    private static final char[] BASE64ENCODECHARS = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', '+', '/' };

    private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57,
        58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28,
        29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
        51, -1, -1, -1, -1, -1 };

    /**
     * @param srcData
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String srcData, String key) throws SecurityException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] plaintext = null;
            plaintext = srcData.getBytes();
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            if (ALGORITHM.contains("ECB")) {
                cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            } else {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            }
            byte[] encrypted = cipher.doFinal(plaintext);
            return encode(encrypted);
        } catch (Exception e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * @param encryData String
     * @param key String
     * @return String
     * @throws Exception
     */
    public static String desEncrypt(String encryData, String key) throws Exception {
        try {
            byte[] encryDataByte = decode(encryData);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            if (ALGORITHM.contains("ECB")) {
                cipher.init(Cipher.DECRYPT_MODE, keyspec);
            } else {
                IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            }
            byte[] original = cipher.doFinal(encryDataByte);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字节数组编码为字符串
     * 
     * @param data
     */
    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1 = 0;
        int b2 = 0;
        int b3 = 0;

        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(BASE64ENCODECHARS[b1 >>> 2]);
                sb.append(BASE64ENCODECHARS[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(BASE64ENCODECHARS[b1 >>> 2]);
                sb.append(BASE64ENCODECHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(BASE64ENCODECHARS[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(BASE64ENCODECHARS[b1 >>> 2]);
            sb.append(BASE64ENCODECHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(BASE64ENCODECHARS[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(BASE64ENCODECHARS[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * 将base64字符串解码为字节数组
     * 
     * @param str
     */
    public static byte[] decode(String str) {
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1 = 0;
        int b2 = 0;
        int b3 = 0;
        int b4 = 0;

        while (i < len) {

            /* b1 */
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }

            /* b2 */
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            /* b3 */
            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            /* b4 */
            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            buf.write((int) (((b3 & 0x03) << 6) | b4));
        }
        return buf.toByteArray();
    }
    
    public static String unicodeEncoding(String str) {
        if (str == null||str.trim().length()==0) {
            return "";
        }
        StringBuffer unicodeBytes = new StringBuffer();
        for (int byteIndex = 0; byteIndex < str.length(); byteIndex++) {
            //字符-Unicode-16进制字符串形式返回
            String hexB = Integer.toHexString(str.charAt(byteIndex));
            unicodeBytes.append("\\u");
            //一定补足4个16进制位--即2个字节
            if (hexB.length() <= 2) {
                unicodeBytes.append("00");
            }
            unicodeBytes.append(hexB);
        }
        return unicodeBytes.toString();
    }
}
