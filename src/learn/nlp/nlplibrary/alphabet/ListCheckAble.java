package learn.nlp.nlplibrary.alphabet;

import learn.nlp.nlplibrary.common.CollectionCheckAble;
import learn.nlp.nlplibrary.common.XmlImportAble;
import java.util.ArrayList;
import java.util.List;

import learn.nlp.nlplibrary.util.CollectionUtil;
import learn.nlp.nlplibrary.xml.XmlNode;
import learn.nlp.nlplibrary.xml.XmlNodeList;

public final class ListCheckAble implements CollectionCheckAble, XmlImportAble {

    public ListCheckAble() {
        this.collection = new ArrayList<>();
    }

    public ListCheckAble(List<String> collection) {
        this.setCollection(collection);
    }

    public void setCollection(List<String> maps) {
        this.collection = maps;
        this.onDataChange(maps);
    }

    public List<String> getCollection() {
        return this.collection;
    }

    private List<String> collection;

    @Override
    public boolean isExited(String value) {
        return CollectionUtil.Contains(collection, value);
    }

    @Override
    public boolean importData(XmlNode node) {
        if (node.getNodeType() == XmlNode.XmlNodeType.ELEMENT_NODE) {
            collection.clear();
            XmlNodeList items = node.getChildNodes();
            for (XmlNode item : items) {
                if (item.isOnlyTextInner()) {
                    collection.add(item.getTextContent());
                }
            }
            this.onDataChange(this.collection);
            return true;
        }
        return false;
    }


    @Override
    public String getStartString(String string) {
        return null;
    }

    @Override
    public void onDataChange(Object data) {
    }
    
}
