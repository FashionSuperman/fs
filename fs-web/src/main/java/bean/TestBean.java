package bean;

import java.util.List;

public class TestBean {
	private String name;
	private List<Addr> addrs;
	private Addr addr;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Addr> getAddrs() {
		return addrs;
	}
	public void setAddrs(List<Addr> addrs) {
		this.addrs = addrs;
	}
	public Addr getAddr() {
		return addr;
	}
	public void setAddr(Addr addr) {
		this.addr = addr;
	}
	
	
}
