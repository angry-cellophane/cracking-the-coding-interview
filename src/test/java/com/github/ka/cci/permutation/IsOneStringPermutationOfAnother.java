package com.github.ka.cci.permutation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
page 90, 6th edition

1.2 Check Permutation: Given two strings, write a method to decide if one is a permutation of the
other.
 */
public class IsOneStringPermutationOfAnother {

    /*
        if len(s1) != len(s2) return false
        var charMap1 = buildCharMapFor s1
        var charMap2 = buildCharMapFor s2

        return charMap1 == charMap2

        ---
        buildCharMapFor walks through a given string, char by char and returns a map of char -> number of occurrences in string
        e.g. abca -> [a:2, b:1, c:1]

        n = len(s1) or len(s2), they should be the same
        O(n) time
        O(n) space
     */
    static boolean isPermutation(String s1, String s2) {
        if (s1 == null && s2 != null || s1 != null && s2 == null) return false;
        if (s1.length() != s2.length()) return false;

        var charMap1 = buildCharMapFor(s1);
        var charMap2 = buildCharMapFor(s2);

        return charMap1.equals(charMap2);
    }

    static Map<Character, Integer> buildCharMapFor(String s) {
        var map = new HashMap<Character, Integer>();
        for (int i=0; i<s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        return map;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void test(String s1, String s2, boolean expected) {
        var actual = isPermutation(s1, s2);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments("", "", true),
                Arguments.arguments("a", "", false),
                Arguments.arguments("a", "a", true),
                Arguments.arguments("ab", "ba", true),
                Arguments.arguments("abc", "ba", false)
        );
    }
}
