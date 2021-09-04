package com.github.ka.cci.strings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you
cannot use additional data structures?
p 90, 6th edition
 */
public class StringHasUniqueChars {

    /*
        sort chars in s
        from 1 to s.length()-1:
            if (s[i] == s[i-1]) return false;
        return true

        O(n logn) time
        O(1) space
     */
    static boolean isUniqueOnlySorting(String s) {
        if (s == null || s.length() == 0) return true;

        var chars = s.toCharArray();
        Arrays.sort(chars);

        for (int i=1; i<chars.length; i++) {
            if (chars[i] == chars[i-1]) return false;
        }

        return true;
    }

    /*
        var seen = new set;
        for every char in s:
            if (seen.contains(char)) return false;

            seen.add(char);
        return true

        O(n) time
        O(n) space
     */
    static boolean isUniqueOnlySet(String s) {
        if (s == null || s.length() == 0) return true;

        var seen = new HashSet<Character>(s.length());
        for (int i=0; i<s.length(); i++) {
            if (seen.contains(s.charAt(i))) {
                return false;
            }

            seen.add(s.charAt(i));
        }

        return true;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testWithSorting(String input, boolean expected) {
        var actual = isUniqueOnlySorting(input);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testWithHashSet(String input, boolean expected) {
        var actual = isUniqueOnlySet(input);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments("", true),
                Arguments.arguments("a", true),
                Arguments.arguments("ab", true),
                Arguments.arguments("abca", false),
                Arguments.arguments("abcc", false),
                Arguments.arguments("aabc", false)
        );
    }
}
