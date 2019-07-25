package application.util.tablemanagers;

import application.interfaces.TableManager;
import application.sql.daos.JobDAO;
import application.sql.entitys.work.Job;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JobTableManager implements TableManager<Job> {
    private static ObservableList<Job> jobList;
    private static ObservableList<Job> backupJobList;
    private static JobDAO jobDAO;

    public JobTableManager() {
        if(jobList == null) {
            jobList = FXCollections.observableArrayList();
        }
        if(backupJobList == null) {
            backupJobList = FXCollections.observableArrayList();
        }
        if(jobDAO == null) {
            jobDAO = new JobDAO();
        }
        fillData();
    }

    @Override
    public void fillData() {
        updateAll();
    }

    @Override
    public void makeBackupList() {
        backupJobList.clear();
        backupJobList.addAll(jobList);
    }

    @Override
    public void add(Job job) {
        jobList.add(job);
        backupJobList.add(job);
        jobDAO.add(job);
    }

    @Override
    public void updateAll() {
        jobList = FXCollections.observableArrayList(jobDAO.getAll());
        makeBackupList();
    }

    @Override
    public void update(Job job) {
        jobDAO.update(job);
        updateAll();
    }

    @Override
    public void delete(Job job) {
        jobList.remove(job);
        backupJobList.remove(job);
        jobDAO.delete(job);
    }

    @Override
    public ObservableList<Job> getObservableList() {
        return jobList;
    }

    @Override
    public ObservableList<Job> getBackupObservableList() {
        return backupJobList;
    }
}
