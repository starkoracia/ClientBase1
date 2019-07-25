package application.util.comparators;

import application.sql.entitys.work.Job;
import application.util.combobox.ComboBoxAutoCompletioner;

public class JobComparator implements ComboBoxAutoCompletioner.AutoCompleteComparator<Job> {
    @Override
    public boolean matches(String typedText, Job objectToCompare) {
        return objectToCompare.getName().toLowerCase().contains(typedText.toLowerCase())
                || objectToCompare.getAmount().contains(typedText);
    }

    public static JobComparator getComparator() {
        return new JobComparator();
    }
}
