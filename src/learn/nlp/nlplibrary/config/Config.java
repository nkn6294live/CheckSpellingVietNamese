package learn.nlp.nlplibrary.config;

import java.util.Set;

public interface Config {
    public String getConfigValue(String key);
    public boolean addConfig(String key, String value);
    public Set<String> getKeys();
}
