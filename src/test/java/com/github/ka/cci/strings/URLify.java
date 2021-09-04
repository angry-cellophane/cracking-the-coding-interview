package com.github.ka.cci.strings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
page 90, 6th edition
1.3
URLify: Write a method to replace all spaces in a string with '%20: You may assume that the string
has sufficient space at the end to hold the additional characters, and that you are given the "true"
length of the string. (Note: If implementing in Java, please use a character array so that you can
perform this operation in place.)
 */
public class URLify {

    /*
        walk through chars
        if char is space shift all chars after by 2 to the right

        O(n^2) time
        O(1) space
     */
    static void urlify(int size, char[] chars) {
        if (size == 0 || chars == null || chars.length == 0) return;

        for (int i=0; i<size; i++) {
            if (chars[i] != ' ') continue;

            for (int j=size - 1; j>i; j--) {
                chars[j+2] = chars[j];
            }
            size +=2;

            chars[i] = '%';
            chars[i+1] = '2';
            chars[i+2] = '0';
            i += 2;
        }
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testWithHashSet(int size, char[] input, char[] expected) {
        urlify(size, input);

        System.out.println("expected = " + Arrays.toString(expected) + ", actual = " + Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments(0, new char[0], new char[0]),
                Arguments.arguments(3, new char[]{'a', 'b', 'c'}, new char[]{'a', 'b', 'c'}),
                Arguments.arguments(3, new char[]{'a', ' ', 'c', 0, 0}, new char[]{'a', '%', '2', '0', 'c'}),
                Arguments.arguments(3, new char[]{'a', 'b', ' ', 0, 0}, new char[]{'a', 'b', '%', '2', '0'}),
                Arguments.arguments(3, new char[]{' ', 'b', 'c', 0, 0}, new char[]{'%', '2', '0', 'b', 'c'})
        );
    }
}
