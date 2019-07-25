package application.util;

import application.sql.entitys.work.Employee;
import application.sql.entitys.work.Job;
import application.sql.entitys.work.JobAndMaterials;
import application.util.tablemanagers.JobTableManager;

public class JobAndMatCreater {
    private static JobTableManager jobTableManager;

    public JobAndMatCreater() {
        if(jobTableManager == null) {
            jobTableManager = new JobTableManager();
        }
    }

    public static JobAndMaterials create(Job job, Employee doer) {
        if(job.getId() == null) {
            jobTableManager.add(job);
        }
        return new JobAndMaterials(job, doer);
    }
}
