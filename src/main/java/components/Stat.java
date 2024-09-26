package components;


public class Stat { 
	Boolean isActive = false;
	String objName;
	String desc = "";
	
	public Stat() {}
	public Stat(String ObjName) {
		this.objName = ObjName;
	}
	public Stat(Boolean IsActive, String ObjName) {
		this.isActive = IsActive;
		this.objName = ObjName;
	}
	public Stat(String ObjName, String Desc) {
		this.objName = ObjName;
		this.desc = Desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
	public void setDesc(String Desc) {
		this.desc = Desc;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public void setObjName(String name) {
		this.objName = name;
	}
	
	
	public Boolean getIsActive() {
		return this.isActive;
	}
	public String getObjName() {
		return this.objName;
	}
}
