package io.wowcollector.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static <T> List<List<T>> getChunks(int size, List<T> items) {
        List<List<T>> result = new ArrayList<>();
        List<T> chunk = new ArrayList<>();
        result.add(chunk);
        int count = 0;
        for (T item : items) {
            if (count == size) {
                result.add(chunk);
                chunk = new ArrayList<>();
                count = 0;
            }

            chunk.add(item);
            count++;
        }
        return result;
    }
}
