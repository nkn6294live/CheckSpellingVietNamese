package learn.nlp.nlplibrary.alphabet;

import learn.nlp.nlplibrary.common.CollectionCheckAble;
import learn.nlp.nlplibrary.common.XmlImportAble;
import java.util.HashMap;
import java.util.Map;

import learn.nlp.nlplibrary.xml.XmlNode;
import learn.nlp.nlplibrary.xml.XmlNodeList;


public class Alphabet implements XmlImportAble {

    public static CollectionCheckAble getCollectionCheckAble(XmlNode node) {
        if (node != null && node.getNodeType() == XmlNode.XmlNodeType.ELEMENT_NODE) {
            String typeSave = node.getAttributeValue("typesave");
            switch (typeSave) {
                case "array":
                    ArrayCheckAble arrayCheckAble = new ArrayCheckAble();
                    if (arrayCheckAble.importData(node)) {
                        return arrayCheckAble;
                    }
                    break;
                case "list":
                    ListCheckAble listCheckAble = new ListCheckAble();
                    if (listCheckAble.importData(node)) {
                        return listCheckAble;
                    }
                    break;
                case "map":
                    MapCheckAble mapCheckAble = new MapCheckAble();
                    if (mapCheckAble.importData(node)) {
                        return mapCheckAble;
                    }
                    break;
                default:
                    return null;
            }
        }
        return null;
    }

    public CollectionCheckAble getCollection(String key) {
        return collection.get(key);
    }
    
    public Alphabet() {
        this.collection = new HashMap<>();
    }

    protected final Map<String, CollectionCheckAble> collection;

    @Override
    public boolean importData(XmlNode node) {
        if (node.getNodeType() == XmlNode.XmlNodeType.ELEMENT_NODE) {
            collection.clear();
            XmlNodeList items = node.getChildNodes();
            for (XmlNode item : items) {
                String keyType = item.getAttributeValue("type");
                if (keyType != null) {
                    CollectionCheckAble collectionCheckAble = getCollectionCheckAble(item);
                    if (collectionCheckAble != null) {
                        collection.put(keyType, collectionCheckAble);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
