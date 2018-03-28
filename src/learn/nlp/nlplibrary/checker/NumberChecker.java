package learn.nlp.nlplibrary.checker;

import learn.nlp.nlplibrary.util.CollectionUtil;

public class NumberChecker implements Checker {

    public static final Character[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static final Character[] DELIMETER = {'.', ','};

    @Override
    public boolean isInvalid(String string) {
        if (string == null) {
            return false;
        }
        char[] chars = string.toCharArray();
        if(CollectionUtil.Contains(DELIMETER, chars[0]) || CollectionUtil.Contains(DELIMETER, chars[chars.length - 1])) {
            return false;
        }
        int count = 0;
        for (char c : chars) {
            if (CollectionUtil.Contains(DELIMETER, c)) {
                if (count++ >= 2) {
                    return false;
                }
            } else if (!CollectionUtil.Contains(NUMBERS, c)) {
                return false;
            }
        }
        return true;
    }
    //100VND, 100$... => chua check.
}
