package com.example.battleship.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Gravatar {
    private static final String gravatarDomen = "https://www.gravatar.com/avatar/";
    private static final String gravatarImageSizeTag = "?s=";
    private static final int defImagePxSize = 600;

    public static String getGravatarProfileImageUrl(String email) {
        final String emailHex = MD5Util.md5Hex(email);
        String gravatarImageUrl = "";
        if (!TextUtils.isEmpty(emailHex)) {
            gravatarImageUrl = gravatarDomen + emailHex + gravatarImageSizeTag + defImagePxSize;
        }
        return gravatarImageUrl;
    }

    private static class MD5Util {
        private static String hex(byte[] array) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                stringBuffer.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return stringBuffer.toString();
        }

        private static String md5Hex(String message) {
            try {
                MessageDigest messageDigest =
                        MessageDigest.getInstance("MD5");
                return hex(messageDigest.digest(message.getBytes("CP1252")));
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
            }
            return null;
        }
    }
}
