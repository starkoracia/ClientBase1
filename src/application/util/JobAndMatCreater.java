package application.util;

import application.sql.entitys.work.Employee;
import application.sql.entitys.work.Job;
import application.sql.entitys.work.JobAndMaterials;
import application.util.tablemanagers.JobTableManager;

public class JobAndMatCreater {

    public JobAndMatCreater() {
    }

    public static JobAndMaterials create(Job job, Employee doer) {
        return new JobAndMaterials(job, doer);
    }
}
