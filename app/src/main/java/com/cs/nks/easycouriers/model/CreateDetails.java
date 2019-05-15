package com.cs.nks.easycouriers.model;

public class CreateDetails {

	private String cretateid;
	private String description;
	private String locationcrate;
	private String unique_crate_id;
	private int pos;
	private int selectim;
	private int missim;
	public  CreateDetails(){
		
	}
	public CreateDetails(String crname, String descStringname, String locaStringcraename, int pos, int missim, int selectim, String unique_crate_idm)
	{
		this.cretateid=crname;
		this.description=descStringname;
		this.locationcrate=locaStringcraename;
		this.pos=pos;
		this.missim=missim;
		this.selectim=selectim;
		this.unique_crate_id=unique_crate_idm;
				
	}
	public String getCretateid() {
		return cretateid;
	}
	public String getUnique_crate_id() {
		return unique_crate_id;
	}
	public void setUnique_crate_id(String unique_crate_id) {
		this.unique_crate_id = unique_crate_id;
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
	public int getSelectim() {
		return selectim;
	}
	public void setSelectim(int selectim) {
		this.selectim = selectim;
	}
	public int getMissim() {
		return missim;
	}
	public void setMissim(int missim) {
		this.missim = missim;
	}
	
}
