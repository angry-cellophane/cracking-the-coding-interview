package com.github.ka.cci.strings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
p 91, 6th edition
1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palin-
drome. A palindrome is a word or phrase that is the same forwards and backwards. A permutation
is a rea rrangement of letters. The palindrome does not need to be limited to just dictionary words.
 */
public class IsPalindromePermutation {

    /*
        build map of occurrences by char from s
        check if there's no more than 1 char with an odd number of occurrences
     */
    static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return true;

        var occurrencesByChar = new HashMap<Character, Integer>();
        for (int i=0; i<s.length(); i++) {
            var c = s.charAt(i);
            if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
                occurrencesByChar.put(c, occurrencesByChar.getOrDefault(c, 0) + 1);
            }
        }

        return occurrencesByChar.entrySet().stream()
                .filter(e -> e.getValue() % 2 == 1)
                .count() < 2;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testWithHashSet(String input, boolean expected) {
        var actual = isPalindrome(input);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments("", true),
                Arguments.arguments("a", true),
                Arguments.arguments("ab", false),
                Arguments.arguments("abca bc", true),
                Arguments.arguments("taco cat", true),
                Arguments.arguments("taco bcat", false)
        );
    }
}
