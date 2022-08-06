package com.flutterwave.middleware.notification.utils;

import android.util.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


@Component
public class AlgorithmUtils {
    public static String DES_PaddingMode = "DES/ECB/NoPadding";
    private static final String DES_Algorithm = "DES";
    public static String TripleDES_PaddingMode = "DESede/ECB/NoPadding";
    private static final String TripleDES_Algorithm = "DESede";
    public static String DESCBC_PaddingMode = "DES/CBC/NoPadding";
    public static String TripleDESCBC_PaddingMode = "DESede/CBC/NoPadding";
    public static String AES_PaddingMode = "AES/ECB/NoPadding";
    private static final String AES_Algorithm = "AES";

    private AlgorithmUtils() {
    }

    public static byte[] simpleEncryptDes(byte[] data, byte[] key) {
        if (data != null && key != null) {
            if (key.length != 8 && key.length != 16 && key.length != 24) {
                return null;
            } else {
                return key.length == 8 ? encryptDES(data, key) : encrypt3DES(data, key);
            }
        } else {
            return null;
        }
    }

    public static byte[] simpleDecryptDes(byte[] data, byte[] key) {
        if (data != null && key != null) {
            if (key.length != 8 && key.length != 16 && key.length != 24) {
                return null;
            } else {
                return key.length == 8 ? decryptDES(data, key) : decrypt3DES(data, key);
            }
        } else {
            return null;
        }
    }

    public static byte[] simpleEncryptDesCBC(byte[] data, byte[] key, byte[] iv) {
        if (data != null && key != null && iv != null) {
            if (key.length != 8 && key.length != 16 && key.length != 24 && iv.length != 8) {
                return null;
            } else {
                return key.length == 8 ? encryptDESCBC(data, key, iv) : encrypt3DESCBC(data, key, iv);
            }
        } else {
            return null;
        }
    }

    public static byte[] simpleDecryptDesCBC(byte[] data, byte[] key, byte[] iv) {
        if (data != null && key != null && iv != null) {
            if (key.length != 8 && key.length != 16 && key.length != 24 && iv.length != 8) {
                return null;
            } else {
                return key.length == 8 ? decryptDESCBC(data, key, iv) : decrypt3DESCBC(data, key, iv);
            }
        } else {
            return null;
        }
    }

    public static byte[] encryptDES(byte[] data, byte[] key) {
        return data != null && key != null ? DESTemplate(data, key, "DES", DES_PaddingMode, true) : null;
    }

    public static byte[] decryptDES(byte[] data, byte[] key) {
        return data != null && key != null ? DESTemplate(data, key, "DES", DES_PaddingMode, false) : null;
    }

    public static byte[] encrypt3DES(byte[] data, byte[] key) {
        if (data != null && key != null) {
            byte[] newKey = formatDESKey(key);
            return newKey == null ? null : DESTemplate(data, newKey, "DESede", TripleDES_PaddingMode, true);
        } else {
            return null;
        }
    }

    public static byte[] decrypt3DES(byte[] data, byte[] key) {
        if (data != null && key != null) {
            byte[] newKey = formatDESKey(key);
            return newKey == null ? null : DESTemplate(data, newKey, "DESede", TripleDES_PaddingMode, false);
        } else {
            return null;
        }
    }

    public static byte[] encryptDESCBC(byte[] data, byte[] key, byte[] iv) {
        return data != null && key != null && iv != null ? DESCBCTemplate(data, key, "DES", DESCBC_PaddingMode, true, iv) : null;
    }

    public static byte[] decryptDESCBC(byte[] data, byte[] key, byte[] iv) {
        return data != null && key != null && iv != null ? DESCBCTemplate(data, key, "DES", DESCBC_PaddingMode, false, iv) : null;
    }

    public static byte[] encrypt3DESCBC(byte[] data, byte[] key, byte[] iv) {
        if (data != null && key != null && iv != null) {
            byte[] newKey = formatDESKey(key);
            return newKey == null ? null : DESCBCTemplate(data, newKey, "DESede", TripleDESCBC_PaddingMode, true, iv);
        } else {
            return null;
        }
    }

    public static byte[] decrypt3DESCBC(byte[] data, byte[] key, byte[] iv) {
        if (data != null && key != null && iv != null) {
            byte[] newKey = formatDESKey(key);
            return newKey == null ? null : DESCBCTemplate(data, newKey, "DESede", TripleDESCBC_PaddingMode, false, iv);
        } else {
            return null;
        }
    }

    public static byte[] formatDESKey(byte[] key) {
        if (key == null) {
            return null;
        } else if (key.length != 8 && key.length != 16 && key.length != 24) {
            return null;
        } else if (key.length != 8 && key.length != 24) {
            byte[] newKey = new byte[24];
            System.arraycopy(key, 0, newKey, 0, 16);
            System.arraycopy(key, 0, newKey, 16, 8);
            return newKey;
        } else {
            return key;
        }
    }

    public static byte[] encryptAES(byte[] data, byte[] key) {
        if (data != null && key != null) {
            return key.length != 16 && key.length != 24 && key.length != 32 ? null : AESTemplet(data, key, "AES", AES_PaddingMode, true);
        } else {
            return null;
        }
    }

    public static byte[] decryptAES(byte[] data, byte[] key) {
        if (data != null && key != null) {
            return key.length != 16 && key.length != 24 && key.length != 32 ? null : AESTemplet(data, key, "AES", AES_PaddingMode, false);
        } else {
            return null;
        }
    }

    public static byte[] encryptByRsaPublickey(byte[] unencryptdata, RSAPublicKey publicKey, RsaPadding padding) {
        if (unencryptdata != null && unencryptdata.length != 0 && publicKey != null && padding != null) {
            String paddingtype = getRsaPaddingString(padding);
            if (paddingtype == null) {
                return null;
            } else {
                int keylen = publicKey.getModulus().bitLength() / 8;
                Cipher cipher = null;
                byte[] encryptdata = null;

                try {
                    cipher = Cipher.getInstance(paddingtype);
                    cipher.init(1, publicKey);
                    byte[][] splitarray = mSplitByteArray(unencryptdata, keylen - 11);
                    encryptdata = new byte[keylen * splitarray.length];

                    for(int i = 0; i < splitarray.length; ++i) {
                        byte[] tmp = cipher.doFinal(splitarray[i]);
                        System.arraycopy(tmp, 0, encryptdata, i * keylen, tmp.length);
                    }
                } catch (NoSuchPaddingException | NoSuchAlgorithmException var10) {
                    var10.printStackTrace();
                } catch (InvalidKeyException var11) {
                    var11.printStackTrace();
                } catch (IllegalBlockSizeException var12) {
                    var12.printStackTrace();
                    var12.printStackTrace();
                } catch (BadPaddingException var13) {
                    var13.printStackTrace();
                }

                return encryptdata;
            }
        } else {
            return null;
        }
    }

    public static byte[] encryptByRsaPublickey(byte[] unencryptdata, RSAPublicKey publicKey) {
        return encryptByRsaPublickey(unencryptdata, publicKey, RsaPadding.RSA_PKCS1_PADDING);
    }

    public static byte[] decryptByRsaPrivatekey(byte[] encryptdata, RSAPrivateKey privateKey, RsaPadding padding) {
        if (encryptdata != null && encryptdata.length != 0 && privateKey != null && padding != null) {
            String paddingtype = getRsaPaddingString(padding);
            if (paddingtype == null) {
                return null;
            } else {
                int keylen = privateKey.getModulus().bitLength() / 8;
                Cipher cipher = null;
                byte[] decryptdata = null;

                try {
                    cipher = Cipher.getInstance(paddingtype);
                    cipher.init(2, privateKey);
                    byte[][] splitarray = mSplitByteArray(encryptdata, keylen);
                    byte[] decrypttmp = new byte[keylen * splitarray.length];
                    int tmplen = 0;

                    for(int i = 0; i < splitarray.length; ++i) {
                        byte[] tmp = cipher.doFinal(splitarray[i]);
                        System.arraycopy(tmp, 0, decrypttmp, tmplen, tmp.length);
                        tmplen += tmp.length;
                    }

                    decryptdata = new byte[tmplen];
                    System.arraycopy(decrypttmp, 0, decryptdata, 0, tmplen);
                } catch (NoSuchPaddingException | NoSuchAlgorithmException var12) {
                    var12.printStackTrace();
                } catch (InvalidKeyException var13) {
                    var13.printStackTrace();
                } catch (IllegalBlockSizeException var14) {
                    var14.printStackTrace();
                } catch (BadPaddingException var15) {
                    var15.printStackTrace();
                }

                return decryptdata;
            }
        } else {
            return null;
        }
    }

    public static byte[] decryptByRsaPrivatekey(byte[] encryptdata, RSAPrivateKey privateKey) {
        return decryptByRsaPrivatekey(encryptdata, privateKey, RsaPadding.RSA_PKCS1_PADDING);
    }

    public static RSAPublicKey mLoadRsaPubKeyFromString(String pkString) {
        RSAPublicKey publicKey = null;

        try {
            byte[] buffer = Base64.decode(pkString, 0);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        } catch (InvalidKeySpecException var6) {
            var6.printStackTrace();
        }

        return publicKey;
    }

    public static RSAPrivateKey mLoadRsaPriKeyFromString(String pkString) {
        RSAPrivateKey privateKey = null;

        try {
            byte[] buffer = Base64.decode(pkString, 0);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        } catch (InvalidKeySpecException var6) {
            var6.printStackTrace();
        }

        return privateKey;
    }

    public static RSAPublicKey mLoadRsaPubKeyFromFile(String filepath) {
        String keystr = LoadKeyFromFile(filepath);
        return keystr == null ? null : mLoadRsaPubKeyFromString(keystr);
    }

    public static RSAPrivateKey mLoadRsaPriKeyFromFile(String filepath) {
        String keystr = LoadKeyFromFile(filepath);
        return keystr == null ? null : mLoadRsaPriKeyFromString(keystr);
    }

    private static Object[][] mSplitObjArray(Object[] objs, int size) {
        if (objs != null && size > 0) {
            int margin = objs.length % size;
            int num = objs.length / size;
            int extlen = margin == 0 ? 0 : 1;
            Object[][] finalarray = new Object[num + extlen][];

            for(int i = 0; i < num; ++i) {
                finalarray[i] = new Object[size];
                System.arraycopy(objs, i * size, finalarray[i], 0, size);
            }

            if (margin != 0) {
                finalarray[num] = new Object[margin];
                System.arraycopy(objs, num * size, finalarray[num], 0, margin);
            }

            return finalarray;
        } else {
            return (Object[][])null;
        }
    }

    private static byte[][] mSplitByteArray(byte[] bytearray, int size) {
        if (bytearray != null && bytearray.length != 0 && size > 0) {
            Byte[] bigbytearray = new Byte[bytearray.length];
            System.arraycopy(bytearray, 0, bigbytearray, 0, bigbytearray.length);
            Object[][] objresult = mSplitObjArray(bigbytearray, size);
            if (objresult != null && objresult.length != 0) {
                byte[][] byteresult = new byte[objresult.length][];

                for(int i = 0; i < byteresult.length; ++i) {
                    byteresult[i] = new byte[objresult[i].length];
                    System.arraycopy(objresult[i], 0, byteresult[i], 0, byteresult.length);
                }

                return byteresult;
            } else {
                return (byte[][])null;
            }
        } else {
            return (byte[][])null;
        }
    }

    private static String LoadKeyFromFile(String filepath) {
        if (filepath != null && filepath.length() != 0) {
            File file = new File(filepath);
            if (file.exists() && file.isFile()) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while((line = br.readLine()) != null) {
                        if (line.charAt(0) != '-') {
                            sb.append(line);
                            sb.append('\r');
                        }
                    }

                    br.close();
                    return sb.toString();
                } catch (FileNotFoundException var5) {
                    var5.printStackTrace();
                } catch (IOException var6) {
                    var6.printStackTrace();
                }

                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static String getRsaPaddingString(RsaPadding padding) {
        if (padding == null) {
            return null;
        } else {
            String paddingtype;
            if (padding == RsaPadding.RSA_PKCS1_PADDING) {
                paddingtype = "RSA/ECB/PKCS1Padding";
            } else {
                paddingtype = "RSA/None/NoPadding";
            }

            return paddingtype;
        }
    }

    private static byte[] DESTemplate(byte[] data, byte[] key, String algorithm, String paddingMode, boolean isEncrypt) {
        byte[] data_tmp = new byte[(data.length + 7) / 8 * 8];
        System.arraycopy(data, 0, data_tmp, 0, data.length);

        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(paddingMode);
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? 1 : 2, keySpec, random);
            return cipher.doFinal(data_tmp);
        } catch (Throwable var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public static byte[] DESCBCTemplate(byte[] data, byte[] keyBytes, String algorithm, String paddingMode, boolean isEncrypt, byte[] iv) {
        byte[] data_tmp = new byte[(data.length + 7) / 8 * 8];
        System.arraycopy(data, 0, data_tmp, 0, data.length);

        try {
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algorithm);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(paddingMode);
            cipher.init(isEncrypt ? 1 : 2, key, new IvParameterSpec(iv));
            return cipher.doFinal(data_tmp);
        } catch (Throwable var11) {
            var11.printStackTrace();
            return null;
        }
    }

    private static byte[] AESTemplet(byte[] data, byte[] key, String algorithm, String paddingMode, boolean isEncrypt) {
        int block_len = key.length;
        byte[] data_tmp = new byte[(data.length + (block_len - 1)) / block_len * block_len];
        System.arraycopy(data, 0, data_tmp, 0, data.length);

        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(paddingMode);
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? 1 : 2, keySpec, random);
            return cipher.doFinal(data);
        } catch (Throwable var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public static byte[] xor(byte[] data1, byte[] data2) {
        if (data1 != null && data2 != null) {
            int minLen = data1.length > data2.length ? data2.length : data1.length;
            byte[] ret = new byte[minLen];

            for(int i = 0; i < minLen; ++i) {
                ret[i] = (byte)(data1[i] ^ data2[i]);
            }

            return ret;
        } else {
            return null;
        }
    }

    public static enum RsaPadding {
        RSA_NO_PADDING,
        RSA_PKCS1_PADDING;

        private RsaPadding() {
        }
    }
}

