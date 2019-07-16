package application.sql;

import application.sql.entitys.authenticate.User;
import application.sql.connectors.DBConnector;

import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator extends DBConnector {

    private static final String DROP_TABLES = "";

    private static final String CREATE_CLIENT_TABLE = "create table if not exists clients\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tname varchar(255) not null,\n" +
            "\tmobile_number varchar(255) not null,\n" +
            "\temail varchar(255) null,\n" +
            "\tfind_recommendation varchar(255) null,\n" +
            "\tannotation varchar(255) null,\n" +
            "\tconstraint clients_pk\n" +
            "\t\tprimary key (id)\n" +
            ");\n";

    private static final String CREATE_EMPLOYEE_TABLE = "create table if not exists employees\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tname varchar(100) not null,\n" +
            "\tlogin varchar(100) not null,\n" +
            "\temail varchar(100) not null,\n" +
            "\tmobile varchar(100) not null,\n" +
            "\tpost int not null,\n" +
            "\tconstraint employees_pk\n" +
            "\t\tprimary key (id)\n" +
            ");";

    private static final String CREATE_PAYMENT_ARTICLE_TABLE = "create table if not exists payments_articles\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tpayment_article varchar(100) not null,\n" +
            "\tis_in_come_payment boolean not null,\n" +
            "\tconstraint payments_articles_pk\n" +
            "\t\tprimary key (id)\n" +
            ");";

    private static final String CREATE_PAYMENT_TABLE = "create table if not exists payments\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tpayment_date_time TIMESTAMP not null,\n" +
            "\tamount varchar(255) not null,\n" +
            "\tfinal_balance varchar(255) not null,\n" +
            "\tpayment_client int null,\n" +
            "\tcomment varchar(255) not null,\n" +
            "\tpayment_article int not null,\n" +
            "\tpayment_employee int not null,\n" +
            "\tpayment_order int null,\n" +
            "\tis_in_come_payment boolean not null,\n" +
            "\tconstraint payments_pk\n" +
            "\t\tprimary key (id),\n" +
            "\tconstraint payments_clients_id_fk\n" +
            "\t\tforeign key (payment_client) references clients (id)\n" +
            "\t\t\ton update cascade,\n" +
            "\tconstraint payments_payments_articles_id_fk\n" +
            "\t\tforeign key (payment_article) references payments_articles (id)\n" +
            "\t\t\ton update cascade,\n" +
            "\tconstraint payments_employees_id_fk\n" +
            "\t\tforeign key (payment_employee) references employees (id)\n" +
            "\t\t\ton update cascade,\n" +
            "\tconstraint payments_orders_id_fk\n" +
            "\t\tforeign key (payment_order) references orders (id)\n" +
            ");";

    private static final String CREATE_ORDER_STATUS_GROUPS_TABLE = "create table if not exists order_status_groups\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tname varchar(255) not null,\n" +
            "\tconstraint order_status_groups_pk\n" +
            "\t\tprimary key (id)\n" +
            ");\n";

    private static final String CREATE_ORDER_STATUSES_TABLE = "create table if not exists order_statuses\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tname varchar(255) not null,\n" +
            "\tgroup_id int not null,\n" +
            "\tconstraint order_statuses_pk\n" +
            "\t\tprimary key (id),\n" +
            "\tconstraint order_statuses_order_status_groups_id_fk\n" +
            "\t\tforeign key (group_id) references order_status_groups (id)\n" +
            ");";

    private static final String CREATE_TYPES_OF_JOBS_TABLE = "create table if not exists jobs\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tname varchar(255) not null,\n" +
            "\tamount varchar(255) not null,\n" +
            "\twarranty int not null,\n" +
            "\tconstraint jobs_pk\n" +
            "\t\tprimary key (id)\n" +
            ");";

    private static final String CREATE_JOB_AND_MATERIALS_TABLE = "create table if not exists job_and_materials\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tjob_id int not null,\n" +
            "\tcost_price varchar(255) not null,\n" +
            "\tdiscount varchar(255) not null,\n" +
            "\tdoer int not null,\n" +
            "\tcomment varchar(255) null,\n" +
            "\tnumber_of int not null,\n" +
            "\tconstraint job_and_materials_pk\n" +
            "\t\tprimary key (id),\n" +
            "\tconstraint job_and_materials_employees_id_fk\n" +
            "\t\tforeign key (doer) references employees (id),\n" +
            "\tconstraint job_and_materials_jobs_id_fk\n" +
            "\t\tforeign key (job_id) references jobs (id)\n" +
            ");";

    private static final String CREATE_ORDERS_TABLE = "create table if not exists orders\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tstatus_id int not null,\n" +
            "\tpaid boolean not null,\n" +
            "\tclient_id int not null,\n" +
            "\tproduct_type varchar(255) null,\n" +
            "\tbrand_name varchar(255) null,\n" +
            "\tmodel varchar(255) null,\n" +
            "\tmalfunction varchar(255) null,\n" +
            "\tappearance varchar(255) null,\n" +
            "\tequipment varchar(255) null,\n" +
            "\tacceptor_note varchar(255) null,\n" +
            "\testimated_price varchar(255) not null,\n" +
            "\tquickly boolean not null,\n" +
            "\tdeadline timestamp not null,\n" +
            "\tprepayment varchar(255) not null,\n" +
            "\tmanager_id int null,\n" +
            "\tdoer_id int null,\n" +
            "\tdoer_note varchar(255) null,\n" +
            "\trecommendation varchar(255) null,\n" +
            "\tconstraint orders_pk\n" +
            "\t\tprimary key (id),\n" +
            "\tconstraint orders_clients_id_fk\n" +
            "\t\tforeign key (client_id) references clients (id),\n" +
            "\tconstraint orders_doer_id__fk\n" +
            "\t\tforeign key (doer_id) references employees (id),\n" +
            "\tconstraint orders_manager_id_fk\n" +
            "\t\tforeign key (manager_id) references employees (id),\n" +
            "\tconstraint orders_order_statuses_id_fk\n" +
            "\t\tforeign key (status_id) references order_statuses (id)\n" +
            ");";

    private static final String CREATE_ORDERS_JOB_AND_MATERIALS_TABLE = "create table if not exists orders_job_and_materials\n" +
            "(\n" +
            "    orders_id            int not null,\n" +
            "    job_and_materials_id int not null,\n" +
            "    constraint orders_job_materials_pk\n" +
            "        primary key (orders_id, job_and_materials_id),\n" +
            "    constraint orders_id_fk\n" +
            "        foreign key (orders_id) REFERENCES orders (id),\n" +
            "    constraint job_and_materials_fk\n" +
            "        foreign key (job_and_materials_id) REFERENCES job_and_materials (id)\n" +
            ");";

    public TableCreator(User user) {
        super(user.getLogin());
    }

    public void createAllTables() {
        toConnect();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_EMPLOYEE_TABLE);
            statement.executeUpdate(CREATE_CLIENT_TABLE);
            statement.executeUpdate(CREATE_PAYMENT_ARTICLE_TABLE);
            statement.executeUpdate(CREATE_ORDER_STATUS_GROUPS_TABLE);
            statement.executeUpdate(CREATE_ORDER_STATUSES_TABLE);
            statement.executeUpdate(CREATE_TYPES_OF_JOBS_TABLE);
            statement.executeUpdate(CREATE_JOB_AND_MATERIALS_TABLE);
            statement.executeUpdate(CREATE_ORDERS_TABLE);
            statement.executeUpdate(CREATE_PAYMENT_TABLE);
            statement.executeUpdate(CREATE_ORDERS_JOB_AND_MATERIALS_TABLE);
            System.out.println("Client tables is Created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (isConnect()) {
                closeConnect();
            }
        }
    }
}
