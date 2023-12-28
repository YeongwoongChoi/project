package facade;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import driver.Manageable;
import entity.Customer;

public interface DataEngineInterface {
    int getColumnCount(final String tableName);
    String[] getColumnNames(final String tableName);
    // 파일에서 Manager로 데이터를 모두 읽어들임
    void readAll(String entityName);


    <T> ResultSet search(T e, final String tableName);
    ResultSet searchByKeyword(final String keyword, String tableName);

    <T extends Manageable> void addNewItem(T e, String[] uiTexts);
    // UI 테이블의 행에 있는 데이터를 스트링 배열로 받아와서 해당 객체 수정
    <T extends Manageable> void update(T e, String name, String [] info);
    // UI 테이블의 행의 첫번째 데이터를 키로 받아와 해당 객체를 찾아 삭제
    void remove(String [] info);
}