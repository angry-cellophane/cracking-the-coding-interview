package com.github.ka.cci.strings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
p 91, 6th edition
1.6
String Compression: Implement a method to perform basic string compression using the counts
of repeated characters. For example, the string aabcccccaaa would become a2b1c5a3. If the
"compressed" string would not become smaller than the original string, your method should return
the original string. You can assume the string has only uppercase and lowercase letters (a - z).
 */
public class StringCompression {

    /*
        if len(s) < 2 return s
        build compressed string
            var sb = new StringBuilder()
            count = 1
            for every char from 1 to len(s):
                if s[i] == s[i-1]:
                    count++
                else:
                    sb.append(count)
                    sb.append(s[i])
                    count = 1
            sb.append(count)
        return compressed if smaller otherwise original

        O(n), n == len(input) time
        O(n) space
     */
    static String compress(String input) {
        if (input == null || input.length() < 2) return input;
        var sb = new StringBuilder(input.length());
        sb.append(input.charAt(0));
        int count = 1;
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == input.charAt(i-1)) {
                count++;
            } else {
                sb.append(count);
                count = 1;
                sb.append(input.charAt(i));
            }
        }
        sb.append(count);

        return sb.length() <= input.length() ? sb.toString() : input;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void test(String input, String expected) {
        var actual = compress(input);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments("", ""),
                Arguments.arguments("a", "a"),
                Arguments.arguments("aaa", "a3"),
                Arguments.arguments("ab", "ab"),
                Arguments.arguments("aabccccceed", "a2b1c5e2d1"),
                Arguments.arguments("aabbbbbbbaaa", "a2b7a3")
        );
    }
}
