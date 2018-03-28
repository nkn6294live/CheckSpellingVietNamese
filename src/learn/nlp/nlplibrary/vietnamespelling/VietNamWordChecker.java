package learn.nlp.nlplibrary.vietnamespelling;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import learn.nlp.nlplibrary.checker.Checker;
import learn.nlp.nlplibrary.util.CollectionUtil;
import learn.nlp.nlplibrary.xml.XmlDocument;
import learn.nlp.nlplibrary.xml.XmlNode;

import org.xml.sax.SAXException;

public class VietNamWordChecker implements Checker {

    static {
        cachedData = new TreeMap<>();
    }
    
    public static Map<Character, Map<Integer, Set<String>>> getCached() {
        return cachedData;
    }
    
    private static Map<Character, Map<Integer, Set<String>>> cachedData;

    public VietNamWordChecker(VietNamAlphabet alphabet) {
        this.alphabet = alphabet;
    }

    public VietNamWordChecker(XmlNode node) {
        this.alphabet = VietNamAlphabet.getAlphabet(node);
    }

    public VietNamWordChecker(InputStream inputStream) throws IOException, SAXException, ParserConfigurationException {
        XmlDocument xmlDocument = XmlDocument.CreatXml(inputStream);
        XmlNode node = xmlDocument.getRootNode();
        this.alphabet = VietNamAlphabet.getAlphabet(node);
    }

    public VietNamWordChecker(String fileName) throws IOException, SAXException, ParserConfigurationException {
        XmlDocument xmlDocument = XmlDocument.CreatXml(new FileInputStream(fileName));
        XmlNode node = xmlDocument.getRootNode();
        this.alphabet = VietNamAlphabet.getAlphabet(node);
    }

    @Override
    public boolean isInvalid(String string) {
        if(CollectionUtil.isExited(cachedData, string)) {
//            System.out.println("Exit from cache:" + string);
            return true;
        }
        if(this.alphabet.CheckSpelling(string)) {
            CollectionUtil.Insert(cachedData, string);
            return true;
        }
        return false;
    }

    public void setAlphabet(VietNamAlphabet alphabet) {
        if (!this.alphabet.equals(alphabet)) {
            this.alphabet = alphabet;
            VietNamWordChecker.cachedData.clear();
        }
    }

    public VietNamAlphabet getAlphabet() {
        return this.alphabet;
    }
    private VietNamAlphabet alphabet;
}
