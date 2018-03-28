package learn.nlp.nlplibrary.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import learn.nlp.nlplibrary.vietnamespelling.VietNamAlphabet;
import learn.nlp.nlplibrary.vietnamespelling.VietNamSimpleChecker;
import learn.nlp.nlplibrary.xml.XmlDocument;

import org.xml.sax.SAXException;

public class SystemFactory {

    public static SystemFactory getInstance() {
        if (factory == null) {
            factory = new SystemFactory();
        }
        return factory;
    }

    private SystemFactory() {
        this.init();
    }

    public VietNamAlphabet getVietNamAlphabet() {
        return this.vietNamAlphabet;
    }

    public VietNamSimpleChecker getVietNamChecker() {
        return this.vietNameChecker;
    }

    public boolean loadAlphabet(InputStream inputStream) {
        try {
            XmlDocument document = XmlDocument.CreatXml(inputStream);
            this.vietNamAlphabet = VietNamAlphabet.getAlphabet(document.getRootNode());
            this.vietNameChecker.setAlphabet(vietNamAlphabet);
            return true;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            return false;
        }
    }

    public boolean loadAlphabet(String fileName) {
        try {
            XmlDocument document = XmlDocument.CreatXml(new FileInputStream(fileName));
            this.vietNamAlphabet = VietNamAlphabet.getAlphabet(document.getRootNode());
            if(this.vietNameChecker == null) {
                this.vietNameChecker = new VietNamSimpleChecker(vietNamAlphabet);
            } else {
                this.vietNameChecker.setAlphabet(vietNamAlphabet);
            }
            return true;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            return false;
        } 
    }

    private void init() {
        this.initVietNamAlphabet();
    }
    
    private void initVietNamAlphabet() {
        this.vietNamAlphabet = new VietNamAlphabet();
    }

    private static SystemFactory factory;
    private VietNamAlphabet vietNamAlphabet;
    private VietNamSimpleChecker vietNameChecker;
}
