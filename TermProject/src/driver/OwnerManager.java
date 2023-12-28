package driver;

import facade.DataEngineInterface;

import java.sql.ResultSet;

public class OwnerManager implements DataEngineInterface {
    @Override
    public int getColumnCount(String tableName) {
        return 0;
    }

    @Override
    public String[] getColumnNames(String tableName) {
        return new String[0];
    }

    @Override
    public void readAll(String entityName) {

    }

    @Override
    public <T> ResultSet search(T e, String tableName) {
        return null;
    }

    @Override
    public ResultSet searchByKeyword(String keyword, String tableName) {
        return null;
    }

    @Override
    public <T extends Manageable> void addNewItem(T e, String[] uiTexts) {

    }

    @Override
    public <T extends Manageable> void update(T e, String name, String[] info) {

    }

    @Override
    public void remove(String[] info) {

    }
}
