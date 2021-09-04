package com.github.ka.cci.permutation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/*
 * page 71, 6th edition
 *
 * Design an algorithm to print all permutations of a string. For simplicity, assume all characters are unique.
Consider a test string abcdefg.
Case "a" -->  {"a"}
Case "ab" --> {"ab", "ba"}
Case "abc" --> ?
 */
public class StringPermutationTest {

    /*
        var total = [];
        for every char in s:
            var newTotal = []
            for every word in total:
                at every position in word:
                    var newWord = copyof(word)
                    put char at position
                    add newWord in newTotal
            total = newTotal
        return total

        n - number of chars
        time - O(n ^ 3)
     */

    static List<String> permutations(String s) {
        if (s == null || s.length() == 0) return List.of();

        var total = new ArrayList<String>();
        for (int i=0; i<s.length(); i++) {
            var c = s.charAt(i);
            var newTotal = new ArrayList<String>();
            for (var word: total) {
                for (int j=0; j<word.length(); j++) {
                    var newWord = word.substring(0, j) + c + word.substring(j);
                    newTotal.add(newWord);
                }
                newTotal.add(word + c);
            }
            if (total.isEmpty()) {
                newTotal.add("" + c);
            }
            total = newTotal;
        }

        return total;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void test(String input, Set<String> expected) {
        var actual = permutations(input);

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        var actualHashset = new HashSet<>(actual);
        System.out.println("expected = " + expected + ", actual = " + actualHashset);
        assertEquals(expected, new HashSet<>(actualHashset));
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments("", Set.of()),
                Arguments.arguments("a", Set.of("a")),
                Arguments.arguments("ab", Set.of("ab", "ba")),
                Arguments.arguments("abc", Set.of("cab", "acb", "abc", "cba", "bca", "bac"))
        );
    }
}
