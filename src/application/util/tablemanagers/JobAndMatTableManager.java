package application.util.tablemanagers;

import application.interfaces.TableManager;
import application.sql.daos.JobAndMaterialsDAO;
import application.sql.entitys.work.JobAndMaterials;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JobAndMatTableManager implements TableManager<JobAndMaterials> {

    private static ObservableList<JobAndMaterials> jobAndMatList;
    private static ObservableList<JobAndMaterials> backupJobAndMatList;
    private static JobAndMaterialsDAO jobAndMatDAO;

    public JobAndMatTableManager() {
        if(jobAndMatList == null) {
            jobAndMatList = FXCollections.observableArrayList();
        }
        if(backupJobAndMatList == null) {
            backupJobAndMatList = FXCollections.observableArrayList();
        }
        if(jobAndMatDAO == null) {
            jobAndMatDAO = new JobAndMaterialsDAO();
        }
        fillData();
    }

    @Override
    public void fillData() {
        updateAll();
    }

    @Override
    public void makeBackupList() {
        backupJobAndMatList.clear();
        backupJobAndMatList.addAll(jobAndMatList);
    }

    @Override
    public void add(JobAndMaterials jobAndMat) {
        jobAndMatList.add(jobAndMat);
        backupJobAndMatList.add(jobAndMat);
        jobAndMatDAO.add(jobAndMat);
    }

    @Override
    public void updateAll() {
        jobAndMatList = FXCollections.observableArrayList(jobAndMatDAO.getAll());
        makeBackupList();
    }

    @Override
    public void update(JobAndMaterials jobAndMat) {
        jobAndMatDAO.update(jobAndMat);
        updateAll();
    }

    @Override
    public void delete(JobAndMaterials jobAndMat) {
        jobAndMatList.remove(jobAndMat);
        backupJobAndMatList.remove(jobAndMat);
        jobAndMatDAO.delete(jobAndMat);
    }

    @Override
    public ObservableList<JobAndMaterials> getObservableList() {
        return jobAndMatList;
    }

    @Override
    public ObservableList<JobAndMaterials> getBackupObservableList() {
        return backupJobAndMatList;
    }
}
