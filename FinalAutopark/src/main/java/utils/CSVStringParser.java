package utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CSVStringParser {

    public static String[] parseCSVString(String csvString) {
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < csvString.length(); i++) {
            if (csvString.charAt(i) == '"' && stack.isEmpty()) {
                stack.add(csvString.charAt(i));
                continue;
            }
            if (!stack.isEmpty()) {
                if (csvString.charAt(i) == ',') {
                    sb.append(".");
                    continue;
                }
                if (csvString.charAt(i) == '"') {
                    stack.remove();
                    continue;
                }
            }
            sb.append(csvString.charAt(i));
        }

        return sb.toString().split(",");
    }
}
