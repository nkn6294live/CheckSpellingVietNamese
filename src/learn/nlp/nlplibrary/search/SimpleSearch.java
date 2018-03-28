package learn.nlp.nlplibrary.search;

import java.util.ArrayList;
import java.util.List;

public class SimpleSearch {

    public SimpleSearch() {

    }

    public static int search(String key, String source) {
        String[] keys = key.split(" ");
        String[] sources = source.split(" ");
        int count = 0;
        for (String element : sources) {
            if (count >= keys.length) {
                break;
            }
            if (element.equals(keys[count])) {
                count++;
            }
        }
        return count;
    }

    public static List<String> search(String key, String[] sources) {
        int maxItem = key.split(" ").length;
        List<String> strings = new ArrayList<>();
        for (String item : sources) {
            if (search(key, item) == maxItem) {
                strings.add(item);
            }
        }
        return strings;
    }

    public static List<Integer> searchByIndex(String key, String[] sources) {
        int maxItem = key.split(" ").length;
        List<Integer> strings = new ArrayList<>();
        for (int i = 0; i < sources.length; i++) {
            String item = sources[i];
            if (search(key, item) == maxItem) {
                strings.add(i);
            }
        }
        return strings;
    }
}
