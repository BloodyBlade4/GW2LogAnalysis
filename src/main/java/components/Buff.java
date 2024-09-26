package components;

import java.util.List;
import java.util.ArrayList;

public class Buff {
	//int id = 0;
	List<Integer> ids = new ArrayList<Integer>();;
	Boolean isActive = false;
	String displayName = "";
	
	public Buff() {}
	
	public Buff(int id, String name) {
		//this.id = id;
		ids.add(id);
		this.displayName = name;
	}
	
	public Buff(List<Integer> ids, String name) {
		this.ids = ids;
		this.displayName = name;
	}
	
	public List<Integer> getIds() {
		return this.ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Boolean containsId(int id) {
		return ids.contains(id);
	}
	public void addId(int id) {
		this.ids.add(id);
	}
	/*
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	*/
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
