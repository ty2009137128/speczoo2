package com.graduation.util;

import com.graduation.model.AuthorityType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by Stephen on 5/22/2014.
 */
public class AuthorityUtil {
    public static boolean hasAuthority(int index, String authority) {
        if (authority == null || "".equals(authority)) {
            return false;
        }

        if (index >= authority.length()) {
            return false;
        }

        if (authority.charAt(index) == '1') {
            return true;
        } else {
            return false;
        }
    }

    public static List<Integer> getAuthorityTypeIndexes(String authority) {
        List<Integer> authorityTypeIndexes = new ArrayList<Integer>();

        if (authority != null && !"".equals(authority)) {
            for (int i = 0; i < authority.length(); i++) {
                if (authority.charAt(i) == '1') {
                    authorityTypeIndexes.add(i);
                }
            }
        }

        return authorityTypeIndexes;
    }
}
