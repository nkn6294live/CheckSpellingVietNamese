package learn.nlp.nlplibrary.vietnamespelling;

import java.io.FileInputStream;
import java.io.IOException;

import learn.nlp.nlplibrary.common.CollectionCheckAble;

import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import learn.nlp.nlplibrary.alphabet.Alphabet;
import learn.nlp.nlplibrary.alphabet.CollectionCheckAbleWithGetStruct;
import learn.nlp.nlplibrary.alphabet.StructStringAble;
import learn.nlp.nlplibrary.xml.XmlDocument;
import learn.nlp.nlplibrary.xml.XmlNode;

import org.xml.sax.SAXException;

public class VietNamAlphabet extends Alphabet implements StructStringAble {

    public static VietNamAlphabet getAlphabet(XmlNode node) {
        VietNamAlphabet alphabet = new VietNamAlphabet();
        if (node != null) {
            alphabet.importData(node);
        }
        return alphabet;
    }

    public static VietNamAlphabet getAlphabet(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
        VietNamAlphabet alphabet = new VietNamAlphabet();
        XmlDocument document = XmlDocument.CreatXml(inputStream);
        alphabet.importData(document.getRootNode());
        return alphabet;
    }

    public static VietNamAlphabet getAlphabet(String filePath) throws SAXException, IOException, ParserConfigurationException {
        VietNamAlphabet alphabet = new VietNamAlphabet();
        XmlDocument document = XmlDocument.CreatXml(new FileInputStream(filePath));
        alphabet.importData(document.getRootNode());
        return alphabet;
    }

    public VietNamAlphabet() {
        super();
        this.charAll = null;
    }

    public CollectionCheckAble getVowel() {
        return this.getCollection("vowel");
    }

    public CollectionCheckAble getConsonant() {
        return this.getCollection("consonant");
    }

    public CollectionCheckAble getCompound() {
        return this.getCollection("compound");
    }

    public CollectionCheckAble getRule() {
        return this.getCollection("rules");
    }

    public CollectionCheckAble getSyllable() {
        return this.getCollection("syllable");
    }

    public boolean CheckSpelling(String str) {
        //check from cache before.
        return this.getRule().isExited(getAllChar().getStructString(str));
    }

    public CollectionCheckAbleWithGetStruct getAllChar() {
        if (this.charAll == null) {
            this.charAll = this.initCollectionCheckAble();
        }
        return this.charAll;
    }

    private CollectionCheckAbleWithGetStruct initCollectionCheckAble() {
//            this.charAll = return new CollectionCheckAbleExpand(this.getVowel(), this.getConsonant(), this.getCompound(), this.getSyllable());
        CollectionCheckAbleWithGetStruct collection = new CollectionCheckAbleWithGetStruct();
        for (String key : this.collection.keySet()) {
            collection.addOrReplace(this.collection.get(key), key);
        }
//        collection.addOrReplace(this.getSyllable(), "syllable");
//        collection.addOrReplace(this.getCompound(), "compound");
//        collection.addOrReplace(this.getConsonant(), "consonant");
//        collection.addOrReplace(this.getVowel(), "vowel");
        collection.setCheckInAllCollection(true);
        return collection;
    }

    private CollectionCheckAbleWithGetStruct charAll;

    @Override
    public String getStructString(String string) {
        return getAllChar().getStructString(string);

    }
}
