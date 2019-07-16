package application.util.comparators;

import application.sql.entitys.work.Client;
import application.util.combobox.ComboBoxAutoCompletioner;

public class ClientComparator implements ComboBoxAutoCompletioner.AutoCompleteComparator<Client> {
    @Override
    public boolean matches(String typedText, Client objectToCompare) {
        return objectToCompare.getName().toLowerCase().contains(typedText.toLowerCase())
                || objectToCompare.getMobileNumber().contains(typedText);
    }

    public static ClientComparator getComparator() {
        return new ClientComparator();
    }
}
