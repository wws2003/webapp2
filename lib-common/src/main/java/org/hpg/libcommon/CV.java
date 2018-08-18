package org.hpg.libcommon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Class for conversions
 *
 * @author wws2003
 */
public class CV {

    /**
     * Split string to list of sub elements (null check included)
     *
     * @param <T>
     * @param str
     * @param delimeter
     * @param convertFunc
     * @return
     */
    public static final <T> List<T> splitStringToList(String str, String delimeter, Function<String, T> convertFunc) {
        if (CH.isEmpty(str)) {
            return new ArrayList();
        }
        return Arrays.asList(str.split(delimeter))
                .stream()
                .map(convertFunc)
                .collect(Collectors.toList());
    }

    /**
     * Split CSV string to list of substrings (null check included)
     *
     * @param <T>
     * @param csvString
     * @param convertFunc
     * @return
     */
    public static final <T> List<T> csvToList(String csvString, Function<String, T> convertFunc) {
        return splitStringToList(csvString, ",", convertFunc);
    }

    /**
     * Split CSV string to list of substrings (null check included)
     *
     * @param csvString
     * @return
     */
    public static final List<String> csvToList(String csvString) {
        return csvToList(csvString, Function.identity());
    }
}
