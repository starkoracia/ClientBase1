package application.util.textfield;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.Collection;

public class AutoCompletionTFBindingImpl<T> extends AutoCompletionTextFieldBinding<T> {

    public AutoCompletionTFBindingImpl(TextField textField, Callback<ISuggestionRequest, Collection<T>> suggestionProvider) {
        super(textField, suggestionProvider);
    }

    public AutoCompletionTFBindingImpl(TextField textField, Callback<ISuggestionRequest, Collection<T>> suggestionProvider, StringConverter<T> converter) {
        super(textField, suggestionProvider, converter);
    }

    @Override
    public void showPopup() {
        super.showPopup();
    }

    @Override
    public void hidePopup() {
        super.hidePopup();
    }

    @Override
    public void fireAutoCompletion(T completion) {
        super.fireAutoCompletion(completion);
    }
}
