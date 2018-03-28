package learn.nlp.nlplibrary.vietnamespelling;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;

import learn.nlp.nlplibrary.checker.CheckerManager;
import learn.nlp.nlplibrary.checker.NumberChecker;
import learn.nlp.nlplibrary.xml.XmlDocument;
import learn.nlp.nlplibrary.xml.XmlNode;

import org.xml.sax.SAXException;

public final class VietNamSimpleChecker extends CheckerManager {

    public final static Character[] START_ABLE_CHARACTER = {
        '\"', '\'', '{', '(', '`', '[', '‘', '’', '“'
    };
    public final static String START_STRING = "…<\"\'{(,`[‘“*”";
    public final static String END_STRING = ">\"\',})`];:’.”!?…*“！";
    public final static Character[] END_ABLE_CHARACTER = {
        '\"', '\'', '}', ')', '`', ']', ';', ':', '‘', '’', ',', '.', '”', '!', '?'
    };

    public VietNamSimpleChecker(VietNamAlphabet alphabet) {
        super();
        this.alphabet = alphabet;
        this.init();
    }

    public VietNamSimpleChecker(InputStream inputStream) {
        super();
        this.loadAlphabet(inputStream);
        this.init();
    }

    private void init() {
        this.add(new SingleCharChecker());
//        this.cachedData = new TreeMap<>();
        this.numberChecker = new NumberChecker();
        if (this.alphabet == null) {
            this.alphabet = new VietNamAlphabet();
        }
        this.vietNamWordChecker = new VietNamWordChecker(alphabet);
        this.add(this.numberChecker);
        this.add(this.vietNamWordChecker);
    }

    @Override
    public String preProcessing(String string) {
        String str = string.trim();
        char[] chars = str.toCharArray();
        int length = chars.length;
        if (length == 0) {
            return "";
        }
        if(length == 1) {
            if(START_STRING.contains(chars[0] + "") || END_STRING.contains(chars[0] + "")) {
                return "";
            } else {
                return str;
            }
        }
        int startIndex = -1;
        for (int index = 0; index < length; index++) {
            if (!START_STRING.contains("" + chars[index])) {
                startIndex = index;
                break;
            }
        }
        if(startIndex < 0) {
            return "";
        }
        int endIndex = length;
        for (int index = length - 1; index >= startIndex; index--) {
            char c = chars[index];
            if (!END_STRING.contains("" + c)) {
                endIndex = index + 1;
                break;
            }
        }
        if (endIndex == startIndex) {
            return "";
        } else {
            return str.substring(startIndex, endIndex);
        }
    }

    public void loadCache(InputStream inputStream) {
        // Load work checked history
    }

    public void SaveCache(OutputStream outputStream) {
        //Save cache current to file.
    }

    public boolean loadAlphabet(InputStream inputStream) {
        try {
            XmlDocument document = XmlDocument.CreatXml(inputStream);
            this.alphabet = VietNamAlphabet.getAlphabet(document.getRootNode());
            this.vietNamWordChecker.setAlphabet(this.alphabet);
            return true;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            return false;
        }
    }

    public boolean loadAlphabet(String fileName) {
        try {
            XmlDocument document = XmlDocument.CreatXml(new FileInputStream(fileName));
            this.alphabet = VietNamAlphabet.getAlphabet(document.getRootNode());
            this.vietNamWordChecker.setAlphabet(this.alphabet);
            return true;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            return false;
        }
    }

    public boolean loadAlphabet(XmlNode node) {
        try {
            this.alphabet = VietNamAlphabet.getAlphabet(node);
            this.vietNamWordChecker.setAlphabet(this.alphabet);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void setAlphabet(VietNamAlphabet alphabet) {
        this.alphabet = alphabet;
        this.vietNamWordChecker.setAlphabet(alphabet);
    }

    private VietNamAlphabet alphabet;
//    private Map<Character, Map<Integer, Set<String>>> cachedData;
    private VietNamWordChecker vietNamWordChecker;
    private NumberChecker numberChecker;
}
