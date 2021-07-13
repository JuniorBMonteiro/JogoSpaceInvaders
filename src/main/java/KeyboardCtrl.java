import javafx.scene.input.KeyCode;

/**
 * Represents the basic game character
 * @author Bernardo Copstein and Rafael Copstein
 * @author José Junior Borges Monteiro
 */
public interface KeyboardCtrl {
    void OnInput(KeyCode keyCode, boolean isPressed);
}
