package mgr;

import java.util.List;
import java.util.Scanner;

import entity.Lecture;
import facade.DataEngineInterface;

public class GuestMgr implements DataEngineInterface {

	private static final String[] labels = { "이름", "휴대폰 번호", "주소" };
	Scanner scan = new Scanner(System.in);
	private Manager mgr = new Manager();

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return labels.length;
	}

	@Override
	public String[] getColumnNames() {
		// TODO Auto-generated method stub
		return labels;
	}

	@Override
	public void readAll(String filename) {
		// TODO Auto-generated method stub
		mgr.readAll("onlineLecture.txt", new Factory() {
			public Manageable create() {
				return new Lecture();
			}
		});
	}

	@Override
	public List<Manageable> search(String kwd) {
		if (kwd.equals(""))
			return mgr.mList;
		return mgr.findAll(kwd);
	}

	@Override
	public void addNewItem(String[] uiTexts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String[] uiTexts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String kwd) {
		// TODO Auto-generated method stub

	}

}
