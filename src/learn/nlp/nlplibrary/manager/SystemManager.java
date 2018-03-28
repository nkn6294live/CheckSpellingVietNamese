package learn.nlp.nlplibrary.manager;

public class SystemManager {

    public static SystemManager getInstance() {
        if(systemManager == null) {
            systemManager = new SystemManager();
        }
        return systemManager;
    }
    private SystemManager() {
    }
    
    private static SystemManager systemManager;
}
