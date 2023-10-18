package com.fengxi.auth.utils;

import com.fengxi.auth.dto.GetPhoneParam;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Base64;

/**
 * 微信解密数据类
 *
 * @author liuqihong
 * @description: TODO
 * @title: AESUtil
 * @projectName FengXiDemo
 */
@Slf4j
public class AESUtil {
    public static String AESDecrypt(GetPhoneParam param){
        String decryptString = "";
        //解码经过 base64 编码的字符串
        byte[] sessionKeyByte = Base64.getDecoder().decode(param.getSessionKey());
        byte[] ivByte = Base64.getDecoder().decode(param.getIv());
        byte[] encryptDataByte = Base64.getDecoder().decode(param.getEncryptedData());

        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            //得到密钥
            Key key = new SecretKeySpec(sessionKeyByte, "AES");
            //AES 加密算法
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("AES");
            algorithmParameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
            byte[] bytes = cipher.doFinal(encryptDataByte);
            decryptString = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptString;
    }

}
