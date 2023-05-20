package SiddhiAppComposites.Annotation.Common;

import java.util.Objects;

public class KeyValue<K,V> extends ICommonAnnotationComposite {
    // self sorting map would be the best
    private final K key;
    private final V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getSiddhiAppCompositeAsString() {
        return key.toString() + " = \"" + value.toString() + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyValue)) return false;
        KeyValue<?, ?> keyValue = (KeyValue<?, ?>) o;
        return Objects.equals(key, keyValue.key) && Objects.equals(value, keyValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
