package mechanic.interfaces;

/**
 * Меткость.
 * Всё что может промахнуться при атаке, имеет показатель (accuracy) меткость.
 */
public interface Accuracy {

    Double getAccuracy();

    void setAccuracy(Double value);
}
