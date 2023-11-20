package driver;

import facade.DataEngineInterface;

import java.util.List;

public class CustomerManager implements DataEngineInterface {

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public String[] getColumnNames() {
        return new String[0];
    }

    @Override
    public void readAll(String filename) {

    }

    @Override
    public List<? extends Manageable> search(String kwd) {
        return null;
    }

    @Override
    public void addNewItem(String[] uiTexts) {

    }

    @Override
    public void update(String[] uiTexts) {

    }

    @Override
    public void remove(String kwd) {

    }
}
