package update.file;

import dataclass.eco.*;

import java.io.*;
import java.util.*;

class FileRead {

	BufferedReader br;
	FileWriter fw;
	String tmp = "";
	String str;
	
	FileRead(){}
		
	/*1001->Apple TV->99->30->detail
      1002->Iphone6->649->20->detail*/
	//update file of product info
	public void updateProductInfoFile(ArrayList<Product> ap){
		String t = "";
		for(int k = 0; k < ap.size();k++){
			t += String.valueOf(ap.get(k).getPid()) + "->" + ap.get(k).getPd_name() + "->" + String.valueOf(ap.get(k).getPrice()) 
				+ "->" + String.valueOf((int)ap.get(k).getStock()) + "->" + ap.get(k).getDetail()+"->" +ap.get(k).getUrl()+"->"+ap.get(k).getCategory();
			if(k != ap.size()-1){ t += "\n";}
		}
		try{
			fw = new FileWriter("product.txt");
			fw.write(t);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fw.close();
			}catch(Exception e){}
		}
	}
		
	/*
	 3001->2001-3-2->20->Iphone6->Macbook
	 3002->2004-4-2->10->Apple Tv
	 */
	//update file of order info
	public void updateOrderInfoFile(ArrayList<OrderInfo> ao){
		String t = "";
		for(int k = 0 ; k < ao.size();k++){
			t += String.valueOf((int)ao.get(k).getOid()) + "->" + ao.get(k).getDate() + "->"
					+ String.valueOf(ao.get(k).getTotal()) + "->"+ ao.get(k).getUsername();
			for(int j = 0; j < ao.get(k).getProd().length; j++){
				t += "->" + ao.get(k).getProd()[j][0] + "->" + ao.get(k).getProd()[j][1];
			}
			if(k != ao.size()-1){ t += "\n";}
		}
		try{
			fw = new FileWriter("orderinfo.txt");
			fw.write(t);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fw.close();
			}catch(Exception e){}
		}
	}
	
	//update file of user info
	public void updateUserInfoFile(ArrayList<UserInfo> au){
		String t = "";
		for(int k = 0; k < au.size() ; k++){
			t += String.valueOf((int)au.get(k).getUid()) + "->" + au.get(k).getName() + "->" + au.get(k).getPassward() + "->" + Boolean.toString(au.get(k).isAdmin()) +"->"+ au.get(k).getAddress();
			if(au.get(k).getOid() == null) {
				if(k != au.size()-1){t +="\n";}
				break;
			}
			for(int i = 0; i < au.get(k).getOid().length;i++){
				t += "->" + String.valueOf((int)au.get(k).getOid()[i]);
			}
			if(k != au.size()-1){t += "\n";}
		}
		try{
			fw = new FileWriter("userinfo.txt");
			fw.write(t);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fw.close();
			}catch(Exception e){}
		}
		
	}
	
	//get product information from file and store them in ArrayList;
	public ArrayList<Product> getProductInfofromFile(){
		ArrayList<Product> al = new ArrayList<Product>();
		try{
			br = new BufferedReader(new FileReader("product.txt"));
			while((str = br.readLine()) != null) {
				String[] tmp = str.split("->");
				Product p = new Product();
				p.setPid(Integer.parseInt(tmp[0]));
				p.setPd_name(tmp[1]);
				p.setPrice(Double.parseDouble(tmp[2]));
				p.setStock(Integer.parseInt(tmp[3]));
				p.setDetail(tmp[4]);
				p.setUrl(tmp[5]);
				p.setCategory(tmp[6]);
				al.add(p);
			}
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		finally {
			try{
				br.close();
			} catch(Exception e) {}
		}
		return al;
	}

	//search product from file by pid	
	public Product searchProductbyPid(int pid){
		ArrayList<Product> ap = getProductInfofromFile();
		int k = -1;
		for(int i = 0; i < ap.size(); i++){
			if(ap.get(i).getPid() == pid){
				k = i;
				break;
			}			
		}
		if(k!=-1)
			return ap.get(k);
		else{
			Product p = new Product();
			p.setDetail(null);
			p.setPd_name(null);
			p.setPid(0);
			p.setPrice(0.0);
			p.setUrl(null);
			p.setCategory(null);
			p.setStock(0);
			return p;
		}
	}
	
	//search product from file by pd_name
	public Product searchProductbyPdname(String pd_name){
		ArrayList<Product> ap = getProductInfofromFile();
		int k = -1;
		for(int i = 0; i < ap.size(); i++){
			if(ap.get(i).getPd_name().equals(pd_name)){
				k = i;
				break;
			}			
		}
		if(k!=-1)
			return ap.get(k);
		else{
			Product p = new Product();
			p.setDetail(null);
			p.setPd_name(null);
			p.setPid(0);
			p.setPrice(0.0);
			p.setStock(0);
			p.setUrl(null);
			p.setCategory(null);
			return p;
		}
	}
	
	//search products by category
		public ArrayList<Product> searchbyCategory(String category){
			ArrayList<Product> ap = getProductInfofromFile();
			ArrayList<Product> nap = new ArrayList<Product>();
			for(int i = 0; i < ap.size();i++){
				if(ap.get(i).getCategory().equals(category)){
					Product p = new Product();
					p.setPid(ap.get(i).getPid());
					p.setPd_name(ap.get(i).getPd_name());
					p.setPrice(ap.get(i).getPrice());
					p.setStock(ap.get(i).getStock());
					p.setUrl(ap.get(i).getUrl());
					p.setDetail(ap.get(i).getDetail());
					p.setCategory(category);
					nap.add(p);
				}
			}
			return nap;
		}
		
	//delete product information and store them file by pid;
	public void deleteProductInfointoFilebyPid(int pid){
		ArrayList<Product> ap = getProductInfofromFile();
		ArrayList<Product> nap = new ArrayList<Product>();
		for(int i = 0; i < ap.size();i++){
			if(ap.get(i).getPid() == pid){
				continue;
			}
			nap.add(ap.get(i));
		}
		updateProductInfoFile(nap);
	}
		
	//add product info into file 
	public void addProductInfointoFile(String pd_name,double price,int stock, String detail,String url,String category){
		ArrayList<Product> ap = getProductInfofromFile();
		int pid = ap.get(ap.size()-1).getPid()+1;
		Product p = new Product();
		p.setPid(pid);
		p.setPd_name(pd_name);
		p.setPrice(price);
		p.setStock(stock);
		p.setDetail(detail);
		p.setCategory(category);
		p.setUrl(url);
		ap.add(p);
		updateProductInfoFile(ap);
	}
	
	//edit product info by searching pid of products;
	public void editProductInfoinFilebyPid(int pid,String pd_name,double price,int stock,String detail,String url,String category){
		ArrayList<Product> ap = getProductInfofromFile();
		ArrayList<Product> nap = new ArrayList<Product>();
		for(int i = 0 ; i < ap.size(); i++){
			if(ap.get(i).getPid() == pid){
				Product p = new Product();
				p.setPid(pid);
				p.setPd_name(pd_name);
				p.setPrice(price);
				p.setStock(stock);
				p.setDetail(detail);
				p.setCategory(category);
				p.setUrl(url);
				nap.add(p);
				continue;
			}
			nap.add(ap.get(i));
		}
		updateProductInfoFile(nap);
	}
	
	//edit product info by searching name of products;
	public void editProductInfoinFilebyPdname(String pd_name,double price,int stock,String detail,String url,String category){
		ArrayList<Product> ap = getProductInfofromFile();
		ArrayList<Product> nap = new ArrayList<Product>();
		for(int i = 0 ; i < ap.size(); i++){
			if(ap.get(i).getPd_name().equals(pd_name)){
				Product p = new Product();
				p.setPid(ap.get(i).getPid());
				p.setPd_name(pd_name);
				p.setPrice(price);
				p.setStock(stock);
				p.setDetail(detail);
				p.setCategory(category);
				p.setUrl(url);
				nap.add(p);
				continue;
			}
			nap.add(ap.get(i));
		}
		updateProductInfoFile(nap);
	}
		
	//get order information from file and store them in ArrayList;
	public ArrayList<OrderInfo> getOrderInfofromFile(){
		ArrayList<OrderInfo> al = new ArrayList<OrderInfo>();
		try{
			br = new BufferedReader(new FileReader("orderinfo.txt"));
			while((str = br.readLine()) != null) {
				String[] tmp = str.split("->");
				OrderInfo oi = new OrderInfo();
				oi.setOid(Integer.parseInt(tmp[0]));
				oi.setDate(tmp[1]);
				oi.setTotal(Double.parseDouble(tmp[2]));
				oi.setUsername(tmp[3]);
				String[][] s_arr = new String[(tmp.length-4)/2][2];
				int p = 0;
				for(int i = 4; i < tmp.length;i=i+2){
					s_arr[p][0] = tmp[i];
					s_arr[p][1] = tmp[i+1];
					p++;
				}
				oi.setProd(s_arr);
				al.add(oi);
			}
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		finally {
			try{
				br.close();
			} catch(Exception e) {}
		}
		return al;
	}
	
	//search order info by oid
	public OrderInfo searchOrderInfobyOid(int oid){
		ArrayList<OrderInfo> ao = getOrderInfofromFile();
		int k = -1;
		for(int i = 0; i < ao.size(); i++){
			if(ao.get(i).getOid() == oid){
				k = i;
				break;
			}			
		}
		if(k!=-1)
			return ao.get(k);
		else{
			OrderInfo oi = new OrderInfo();
			oi.setDate("0000-00-00");
			oi.setOid(0);
			oi.setProd(null);
			oi.setTotal(0.0);
			oi.setUsername(null);
			return oi;
		}
	}
		
	//delete order info by oid
	public void deleteOrderInfoinFilebyOid(int oid){
		ArrayList<OrderInfo> ao = getOrderInfofromFile();
		ArrayList<OrderInfo> nao = new ArrayList<OrderInfo>();
		ArrayList<UserInfo> au = getUserInfofromFile();
		String name ="";
		for(int i = 0; i < ao.size();i++){
			if(ao.get(i).getOid() == oid){
				name = ao.get(i).getUsername();
				continue;
			}
			nao.add(ao.get(i));
		}
		updateOrderInfoFile(nao);
		
		for(int j = 0; j < au.size();j++){
			if(au.get(j).getName().equals(name)){
				UserInfo u = au.get(j);
				int arr[] = u.getOid();
				int narr[] = new int[u.getOid().length-1];
				if(arr.length == 1)
				{
					arr = null;
				}else{
					int p = 0;
					for(int x = 0; x < arr.length;x++){
						if(arr[x] == oid){
							continue;
						}
						narr[p] = arr[x];
						p++;
					}
				}
				au.get(j).setOid(narr);
			}
			
		}
		
		updateUserInfoFile(au);
		
	}
		
	//add order info to file
	public void addOrderInfointoFile(String date, String[][] prod, double count,String name){
		ArrayList<OrderInfo> ao = getOrderInfofromFile();
		ArrayList<UserInfo> au = getUserInfofromFile();
		int oid = ao.get(ao.size()-1).getOid()+1;
		OrderInfo oi = new OrderInfo();
		oi.setOid(oid);
		oi.setDate(date);
		oi.setProd(prod);
		oi.setTotal(count);
		oi.setUsername(name);
		ao.add(oi);
		updateOrderInfoFile(ao);
		for(int i = 0 ; i < au.size();i++){
			UserInfo ui = au.get(i);
			if(ui.getName().equals(name)){
				if(ui.getOid() == null){
					int[] arr = new int[1];
					arr[0]=oid;
					ui.setOid(arr);
				}else{
					int[] ar = new int[ui.getOid().length+1];
					for(int j = 0 ;j <ui.getOid().length;j++){
						ar[j] = ui.getOid()[j];
					}
					ar[ui.getOid().length] = oid;
					ui.setOid(ar);
				}
			}
		}
		updateUserInfoFile(au);
	}	
	
	//edit order info of file by oid
	public void editOrderInfoinFilebyOid(int oid, String date, String[][] prod, double total,String username){
		ArrayList<OrderInfo> ao = getOrderInfofromFile();
		ArrayList<OrderInfo> nao = new ArrayList<OrderInfo>();
		for(int i = 0 ; i < ao.size(); i++){
			if(ao.get(i).getOid() == oid){
				OrderInfo oi = new OrderInfo();
				oi.setOid(oid);
				oi.setDate(date);
				oi.setProd(prod);
				oi.setTotal(total);
				oi.setUsername(username);
				nao.add(oi);
				continue;
			}
			nao.add(ao.get(i));
		}
		updateOrderInfoFile(nao);
	}
	
	//get user information from file and store them in ArrayList;
	public ArrayList<UserInfo> getUserInfofromFile(){
		ArrayList<UserInfo> al = new ArrayList<UserInfo>();
		try{
			br = new BufferedReader(new FileReader("userinfo.txt"));
			while((str = br.readLine()) != null) {
				String[] tmp = str.split("->");
				UserInfo ui = new UserInfo();
				ui.setUid(Integer.parseInt(tmp[0]));
				ui.setName(tmp[1]);
				ui.setPassward(tmp[2]);
				ui.setAdmin(Boolean.parseBoolean(tmp[3]));
				ui.setAddress(tmp[4]);
				int[] arr = new int[tmp.length-5];
				for(int i = 5;i < tmp.length;i++){
					arr[i-5] = Integer.parseInt(tmp[i]);
				}
				ui.setOid(arr);
				al.add(ui);
			}
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		finally {
			try{
				br.close();
			} catch(Exception e) {}
		}
		return al;
	}
	
	//search user info by user name 
	public UserInfo searchbyUsername(String name){
		ArrayList<UserInfo> au = getUserInfofromFile();
		UserInfo u = new UserInfo();
		for(int i = 0; i < au.size();i++){
			if(au.get(i).getName().equals(name)){
				u.setName(name);
				u.setAdmin(au.get(i).isAdmin());
				u.setPassward(au.get(i).getPassward());
				u.setUid(au.get(i).getUid());
				u.setOid(au.get(i).getOid());
				u.setAddress(au.get(i).getAddress());
				au.add(u);
				break;
			}		
		}
		return u;
	}
	
	//add user info and check whether it exists
	public boolean addUserinfo(String name, String passward,String address){
		ArrayList<UserInfo> au = getUserInfofromFile();
		if(searchbyUsername(name).getUid() == 0){
			int uid = au.get(au.size()-1).getUid()+1;
			UserInfo u = new UserInfo();
			u.setUid(uid);
			u.setName(name);
			u.setPassward(passward);
			u.setAdmin(false);
			u.setOid(null);
			u.setAddress(address);
			au.add(u);
			updateUserInfoFile(au);
			return true;
		}else
		{
			return false;
		}
	}
	
	//edit user information
	public void changeAdminbyUsername(String name,String passward,int[] oid , boolean isadmin,String address){
		ArrayList<UserInfo> au = getUserInfofromFile();
		for(int i = 0;i < au.size();i++){
			if(au.get(i).getName().equals(name)){
				UserInfo u = au.get(i);
				u.setPassward(passward);
				u.setAdmin(isadmin);
				u.setOid(oid);
				u.setAddress(address);
			}
		}
		updateUserInfoFile(au);
	}
	
	public static void main(String a[]) {
		FileRead fr = new FileRead();
		ArrayList<Product> ap = fr.getProductInfofromFile();
		ArrayList<OrderInfo> ao = fr.getOrderInfofromFile();
		ArrayList<UserInfo> au = fr.getUserInfofromFile();
		
		System.out.println(ao.get(0).getUsername());
		
		ArrayList<Product> nap = fr.searchbyCategory("electronic");
		System.out.println(nap.get(2).getPd_name());
		System.out.println(nap.get(2).getUrl());
		System.out.println(nap.get(2).getCategory());
		
		UserInfo u = fr.searchbyUsername("wangyunjun");
		
		System.out.println(u.getPassward());
		System.out.println(u.getAddress());
		
//		boolean flag = fr.addUserinfo("Nike","123123","detail111");
//		System.out.println(flag);
		
		
//		System.out.println(ap.get(0).getPd_name());
//		System.out.println(ao.get(1).getTotal());
//		System.out.println(ao.get(1).getProd()[0][0]);
		
//		System.out.println(au.get(0).getPassward());
//		System.out.println(au.get(0).getUid());
//		System.out.println(au.get(0).getOid()[1]);
//		
//		Product p = fr.searchProductbyPid(1004);
//		System.out.println(p.getPd_name());
//		System.out.println(p.getPrice());
		
//		Product pd = fr.searchProductbyPdname("Iphone7");
//		System.out.println(pd.getPd_name());
//		System.out.println(pd.getPrice());
//		
//		
//		OrderInfo oi = fr.searchOrderInfobyOid(3005);
//		System.out.println(oi.getProd());
//		System.out.println(oi.getDate());
		
		int[] arr = {3001};
		
		//fr.changeAdminbyUsername("jason", "1133", arr, true,"detail3333");
		
		String[][] st = new String[2][2];
		st[0][0] = "Ipad 2";
		st[0][1] = "1";
		st[1][0] = "Iphone6";
		st[1][1] = "3";
		
//		fr.addOrderInfointoFile("2015-3-1",st, 3000.13,"wangyunjun");
//		fr.deleteProductInfointoFilebyPid(1004);
//		fr.addProductInfointoFile("Macbook 17'", 1499, 19, "detail","macbook17.jpg","electronic");
//		fr.editProductInfoinFilebyPid(1003,"Macbook 13.4'", 1499, 20, "detail");
//		fr.editProductInfoinFilebyPdname("Macbook 13.4'", 1700, 19, "detail1");
//		fr.deleteOrderInfoinFilebyOid(3003);
//		fr.deleteOrderInfoinFilebyOid(3004);
//		fr.deleteOrderInfoinFilebyOid(3005);
//		fr.deleteOrderInfoinFilebyOid(3006);
//		
//		String[] st = new String[2];
//		st[0] = "Ipad 4";
//		st[1] = "Ipad mini";
//		fr.editOrderInfoinFilebyOid(3002, "2011-2-2",st , 29.03,"song");
	}
}
