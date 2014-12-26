package de.truschin.helloworldpaas;

public class ServiceResponse<A> {
	private A obj;
	private String MAC;
	
	public ServiceResponse(A obj, String sb) {
		super();
		this.obj = obj;
		MAC = sb;
	}
	
	public A getObj() {
		return obj;
	}
	public String getMAC() {
		return MAC;
	}
	
	
}
