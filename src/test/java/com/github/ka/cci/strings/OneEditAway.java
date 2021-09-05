package com.github.ka.cci.strings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
p 91, 6th edition
One Away: There are three types of edits that can be performed on strings: insert a character,
remove a character, or replace a character. Given two strings, write a function to check if they are
one edit (or zero edits) away.
 */
public class OneEditAway {

    /*
        n - length of s1
        m - length of s1

        Check if the same, if so return true
            s1.equals(s2)
            O(Math.min(n, m))

        Check if one char can be replaced to make strings same
            Walk through strings comparing elements at the same position in strings
            if more than 1 diff return false
            else return true
        Check if one char can be added to make strings same
            if abs(len(s1) - len(s2)) > 1 then not possible
            Walk through strings
            if s1[i] != s2[i] check if s1[i+1] == s2[i] or s1[i] == s2[i+1] considering i can be the last element
            if more than 1 diffs return false
            else return true
        Check if one char can be deleted to make strings same
            Walk through strings
            if s1[i] != s2[i] check if s1[i+1] == s2[i] or s1[i] == s2[i+1] considering i can be the last element

        adding element == deleting element
     */
    static boolean isOneEditAway(String s1, String s2) {
        if (s1 == null && s2 != null || s1 != null && s2 == null) return false;
        if (s1 == null && s2 == null) return true;

        // not equal, not possible to add/delete or replace element to make equal
        // there's always +1 additional element in one of the strings
        if (Math.abs(s1.length() - s2.length()) > 1) return false;

        if (s1.equals(s2)) return true;
        if (canMakeEqualWithReplacement(s1, s2)) return true;
        if (canMakeEqualByAdding(s1, s2)) return true;

        return false;
    }

    static boolean canMakeEqualWithReplacement(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        int diffs = 0;
        for (int i=0; i<s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diffs++;
            }
            if (diffs > 1) {
                return false;
            }
        }

        return true;
    }

    // deletion and addition an element are the same operations
    static boolean canMakeEqualByAdding(String s1, String s2) {
        if (Math.abs(s1.length() - s2.length()) != 1) return false;

        int i=0;
        int j=0;
        while (i < s1.length() && j < s2.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            } else {
                if (i + 1 < s1.length() && s1.charAt(i + 1) == s2.charAt(j)) {
                    i++;
                } else if (j + 1 < s2.length() && s1.charAt(i) == s2.charAt(j + 1)) {
                    j++;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testWithHashSet(String s1, String s2, boolean expected) {
        var actual = isOneEditAway(s1, s2);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments("", "", true),
                Arguments.arguments("pale", "ple", true),
                Arguments.arguments("pales", "pale", true),
                Arguments.arguments("pale", "bale", true),
                Arguments.arguments("pale", "bake", false),
                Arguments.arguments("pale", "palc", true),
                Arguments.arguments("pale", "palee", true),
                Arguments.arguments("", "palee", false),
                Arguments.arguments("pale", "ke", false)
        );
    }
}
