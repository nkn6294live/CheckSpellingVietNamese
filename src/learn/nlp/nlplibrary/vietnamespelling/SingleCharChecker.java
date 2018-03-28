package learn.nlp.nlplibrary.vietnamespelling;

import learn.nlp.nlplibrary.checker.Checker;
import learn.nlp.nlplibrary.util.CollectionUtil;

public class SingleCharChecker implements Checker {

    public final static String[] ERROR_CHAR = {"j", "w", "z"};
    
    @Override
    public boolean isInvalid(String string) {
        if(string == null) {
            return false;
        }
        if(string.trim().length() == 1) {
            return !CollectionUtil.Contains(ERROR_CHAR, string);
        }
        return false;
    }
    
}
