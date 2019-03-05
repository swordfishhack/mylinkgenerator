package com.mylink.mylinkgenerator.common;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * IdCharMapper For storing the Mapping values for a-zA-Z0-9
 */
public class IdCharMapper {

    public static final IdCharMapper idCharMapper = new IdCharMapper();

    private static HashMap<Character, Integer> char2IndexMap;
    private static List<Character> index2CharList;

    private IdCharMapper() {
        initChar2IndexMap();
        initIndexCharList();
    }

    /**
     * Method for storing charecters to its numerical value mapping
     */
    private void initChar2IndexMap() {
        char2IndexMap = new HashMap<>();

        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            char2IndexMap.put(c, i);
        }

        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i - 26);
            char2IndexMap.put(c, i);
        }

        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            char2IndexMap.put(c, i);
        }
    }

    /**
     * Method for storing charecters and retriving based on the Index
     */
    private void initIndexCharList(){
        index2CharList = new ArrayList<>();

        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            index2CharList.add(c);
        }

        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            index2CharList.add(c);
        }

        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            index2CharList.add(c);

        }
    }

    /**
     * This function is used to generate a unique Id
     * @param id
     * @return
     */
    public static String createKeyFromId(Long id){
        List<Integer> base62Value = convertBase10ToBase62Values(id);
        StringBuilder shortKey = new StringBuilder();

        for (int digit: base62Value) {
            shortKey.append(index2CharList.get(digit));
        }

        return shortKey.toString();

    }

    /**
     *
     * @param key
     * @return
     */
    public static Long createIdFromKey(String key){
        List<Character> base62Chars = new ArrayList<>();

        for (int i = 0; i < key.length(); ++i) {
            base62Chars.add(key.charAt(i));
        }

        Long id = convertBase62ToBase10Values(base62Chars);

        return id;
    }

    /**
     *
     * @param id
     * @return
     */
    private static List<Integer> convertBase10ToBase62Values(Long id){
        List<Integer> values = new LinkedList<>();

        while(id > 0) {
            int remainder = (int)(id % 62);
            ((LinkedList<Integer>) values).addFirst(remainder);
            //getting quotient
            id = id/62;
        }

        return values;
    }

    /**
     *
     * @param characters
     * @return
     */
    private static Long convertBase62ToBase10Values(List<Character> characters){
        long id = 0L;

        for (int i = 0, exp = characters.size() - 1; i < characters.size(); ++i, --exp) {
            int base10 = char2IndexMap.get(characters.get(i));
            id += (base10 * Math.pow(62.0, exp));
        }

        return id;
    }


}