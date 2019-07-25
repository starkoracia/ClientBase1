package application.util;

import application.sql.entitys.work.JobAndMaterials;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListCloner {

    public static List<JobAndMaterials> cloneSerializeList(List<JobAndMaterials> listForClone) {

        List<JobAndMaterials> clonedList;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            //сохраняем состояние кота Васьки в поток и закрываем его(поток)
            ous.writeObject(listForClone);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            //создаём кота для опытов и инициализируем его состояние Васькиным
            clonedList = (List<JobAndMaterials>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            clonedList = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            clonedList = null;
        }
        return clonedList;
    }
}
