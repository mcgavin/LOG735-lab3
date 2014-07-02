package Succursale;

public class Canal {

	private int flux;
	private boolean record;
	
	public Canal(int flux,boolean record){
		this.setFlux(flux);
		this.setRecord(record);
		
	}

	public int getFlux() {
		return flux;
	}

	public void setFlux(int flux) {
		this.flux = flux;
	}

	public boolean isRecord() {
		return record;
	}

	public void setRecord(boolean record) {
		this.record = record;
	}

	public void addFlux(int argent) {
		flux = flux + argent;	
	}
}
