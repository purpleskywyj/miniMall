package dataclass.eco;

public class OrderInfo {
	
	private int oid;
	private String date;
	private String[][] prod;
	private double total;
	private String username;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String[][] getProd() {
		return prod;
	}
	public void setProd(String[][] prod) {
		this.prod = prod;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
