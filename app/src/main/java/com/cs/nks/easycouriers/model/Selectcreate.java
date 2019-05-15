
package com.cs.nks.easycouriers.model;

public class Selectcreate {

	private String cretateid;
	private String unique_crate_id;
	private String description;
	private String locationcrate;
	private int pos;
	private String found_miss;
	private int missim;
	private int  selectim;
	public  Selectcreate(){
		
	}
	public Selectcreate(String crname, String descStringname, String locaStringcraename, int pos , String found_miss, int missim , int selectim, String unique_crate_idm)
	{
		this.cretateid=crname;
		this.description=descStringname;
		this.locationcrate=locaStringcraename;
		this.pos=pos;
		this.found_miss=found_miss;
		this.missim=missim ;
		this.selectim=selectim;
		this.unique_crate_id=unique_crate_idm;
				
	}
	public String getUnique_crate_id() {
		return unique_crate_id;
	}
	public void setUnique_crate_id(String unique_crate_id) {
		this.unique_crate_id = unique_crate_id;
	}
	public String getCretateid() {
		return cretateid;
	}
	public void setCretateid(String cretateid) {
		this.cretateid = cretateid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocationcrate() {
		return locationcrate;
	}
	public void setLocationcrate(String locationcrate) {
		this.locationcrate = locationcrate;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public String getFound_miss() {
		return found_miss;
	}
	public void setFound_miss(String found_miss) {
		this.found_miss = found_miss;
	}
	public int getMissim() {
		return missim;
	}
	public void setMissim(int missim) {
		this.missim = missim;
	}
	public int getSelectim() {
		return selectim;
	}
	public void setSelectim(int selectim) {
		this.selectim = selectim;
	}
	
}
