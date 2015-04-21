package dataclass.eco;


public class UserInfo {
	
	private int uid;
	private String name;
	private String passward;
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private boolean isAdmin;
	private int[] oid;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassward() {
		return passward;
	}
	public void setPassward(String passward) {
		this.passward = passward;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public int[] getOid() {
		return oid;
	}
	public void setOid(int[] oid) {
		this.oid = oid;
	}
	 
	
}
