package application.interfaces;

import javafx.collections.ObservableList;

public interface TableManager<Entity> {

    void fillData();

    void makeBackupList();

    void add(Entity entity);

    void updateAll();

    void update(Entity entity);

    void delete(Entity entity);

    ObservableList<Entity> getObservableList();

    ObservableList<Entity> getBackupObservableList();

}
