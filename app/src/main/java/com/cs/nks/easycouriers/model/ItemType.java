package com.cs.nks.easycouriers.model;

import java.util.ArrayList;


/**
 * Created by Abhishek on 16/02/17.
 */

public class ItemType {

    public String id;
    public String itemType;

    public ItemType(String itemType, String id) {
        this.id = id;
        this.itemType = itemType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return itemType;
    }

    public static ArrayList<ItemType> fillItemTypes() {
        ArrayList<ItemType> al_itemType = new ArrayList<>();
        al_itemType.add(new ItemType("--Choose your Item Type--", "0"));
        al_itemType.add(new ItemType("Customer Owned", "1"));
        al_itemType.add(new ItemType("IDC Rental", "2"));
        al_itemType.add(new ItemType("Local Rental", "3"));
        al_itemType.add(new ItemType("New Product", "4"));
        return al_itemType;

    }

    public static String getItemTypeByPosition(int position){
        ArrayList<ItemType> al_itemType =fillItemTypes();
        return al_itemType.get(position).toString();
    }




    public static ArrayList<BranchLocation> fillKilns() {
        ArrayList<BranchLocation> list_branch=new ArrayList<>();

        list_branch.add(new BranchLocation("address 1", "", "28.578050", "77.173140"));
        list_branch.add(new BranchLocation("address 2", "", "28.633110", "77.282650"));
        list_branch.add(new BranchLocation("address 3", "", "28.646820", "77.288190"));
        list_branch.add(new BranchLocation("address 4", "", "28.637817", "77.243148"));
        list_branch.add(new BranchLocation("address 5", "", "28.633550", "77.139240"));
        list_branch.add(new BranchLocation("address 6", "", "28.685630", "77.169840"));
        return list_branch;

    }
    public static ArrayList<BranchLocation> fillWorkerType() {

        final ArrayList<BranchLocation> TypeofWorker = new ArrayList<>();
        TypeofWorker.add(new BranchLocation("Moulders", "", "28.578050", "77.173140"));
        TypeofWorker.add(new BranchLocation("Loaders", "", "28.633110", "77.282650"));
        TypeofWorker.add(new BranchLocation("Bullock cart drivers", "", "28.646820", "77.288190"));
        TypeofWorker.add(new BranchLocation("Fireman", "", "28.637817", "77.243148"));
        TypeofWorker.add(new BranchLocation("Supervisors/Managers", "", "28.633550", "77.139240"));
        return TypeofWorker;

    }


    public static ArrayList<BranchLocation> fillLaborType() {
        final ArrayList<BranchLocation> TypeofLabor = new ArrayList<>();
        TypeofLabor.add(new BranchLocation("Bonded Labor", "", "28.578050", "77.173140"));
        TypeofLabor.add(new BranchLocation("Child Labor", "", "28.633110", "77.282650"));
        TypeofLabor.add(new BranchLocation("Informal Labor", "", "28.646820", "77.288190"));
        return TypeofLabor;

    }
}
