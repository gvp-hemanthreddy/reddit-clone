package com.hemanth.redditclone.util;

import com.github.slugify.Slugify;

import java.security.SecureRandom;

public class Utils {
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();
    private static final Slugify slg = new Slugify();

    public static String makeId(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(characters.charAt(random.nextInt(characters.length())));
        return sb.toString();
    }

    public static String slugify(String str) {
        return slg.slugify(str);
    }
}
