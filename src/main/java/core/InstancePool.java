package core;

import java.util.HashMap;

public class InstancePool {

    static InstancePool instance;

    private HashMap<String, Object> instancePool;

    private InstancePool() {
        this.instancePool = new HashMap<>();
        this.registerInstance(this);
    }


    public static InstancePool getInstance() {
        if (instance == null) {
            instance = new InstancePool();
            return instance;
        }

        return instance;
    }

    public <T> T getInstance(Class<T> classType) {
        return (T) instancePool.get(classType.getTypeName());
    }

    public <T> T registerInstance(T instance) {
        return (T) instancePool.put(instance.getClass().getTypeName(), instance);
    }
}
