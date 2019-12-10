package com.cs.nks.easycouriers.survey;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.BranchLocation;
import com.cs.nks.easycouriers.model.DamageDetail;
import com.cs.nks.easycouriers.model.ItemType;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.Utility;

import java.util.ArrayList;


public class Adapter_ReportBondedLabor extends RecyclerView.Adapter<Adapter_ReportBondedLabor.UserViewHolder> {

    private static int uploadedImagePos;
    ArrayList<BranchLocation> KilnsTypeList;
    ArrayList<BranchLocation> WorkerTypeList;
    ArrayList<BranchLocation> LaborTypeList;
    //  private ArrayAdapter<ItemType> dataAdapter;
    Fragment fragment;
    private Context mContext;
    private ArrayList<DamageDetail> damageDetailList;
     AlertDialog alertDialog1;

    public Adapter_ReportBondedLabor(Context context, ArrayList<DamageDetail> damageDetailList, Fragment fragment) {
        this.mContext = context;
        this.damageDetailList = damageDetailList;
        this.KilnsTypeList = ItemType.fillKilns();
        this.WorkerTypeList = ItemType.fillWorkerType();
        this.LaborTypeList = ItemType.fillLaborType();
        this.fragment = fragment;



     /*   dataAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, itemTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_row_1, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.itencount.setText(String.valueOf(position + 1));


        holder.type_worker.setText(damageDetailList.get(position).getType_worker());
        holder.type_labor.setText(damageDetailList.get(position).getType_labor());
        holder.type_BrickKilns.setText(damageDetailList.get(position).getType_BrickKilns());
        holder.et_workAssigned.setText(damageDetailList.get(position).getWorkAssigned());
        holder.et_name.setText(damageDetailList.get(position).getName());
        holder.et_age.setText(damageDetailList.get(position).getAge());
        holder.et_gender.setText(damageDetailList.get(position).getGender());
        holder.et_Address.setText(damageDetailList.get(position).getAddress());
        holder.et_comments.setText(damageDetailList.get(position).getComments());


       /* if (damageDetailList.get(position).isRemoveIconVisible()) {
            holder.imgvw_remove.setVisibility(View.VISIBLE);
        } else {
            holder.imgvw_remove.setVisibility(View.GONE);
        }
*/

      /*  if (!damageDetailList.get(position).getUploadedPhotoUrl().equals("")) {
            holder.imgvwUploadedPic.setVisibility(View.VISIBLE);
            final String Image_Link = SOAP_API_Client.URL_EP2 + "/upload/" + damageDetailList.get(position).getUploadedPhotoUrl();
            Glide
                    .with(mContext)
                    .load(Image_Link)     // Url of the picture
                    .into(holder.imgvwUploadedPic);
        } else {
            holder.imgvwUploadedPic.setVisibility(View.INVISIBLE);
        }*/
    }

    @Override
    public int getItemCount() {
        return damageDetailList.size();
    }

    public void addItemToList() {

        damageDetailList.add(DamageDetail.addDamageDetail());
        notifyDataSetChanged();
        ((ReportBondedLabor) fragment).ScrollRecyclerviewToBottom(damageDetailList.size() - 1);
    }

    public ArrayList<DamageDetail> returnData() {
        if (validation() == 1) {
            return damageDetailList;
        } else return null;
    }

    public void setUploadedImageURL(String uploadedPhotoURL) {
        damageDetailList.get(uploadedImagePos).setUploadedPhotoUrl(uploadedPhotoURL);
        notifyDataSetChanged();
    }

    public int validation() {
        int val = 0;

       /* for (int i = 0; i < damageDetailList.size(); i++) {

            int count = i + 1;
            if (damageDetailList.get(i).getItemDesc().equals("")) {
                Toast.makeText(mContext,
                        "Please enter item description of item " + count, Toast.LENGTH_LONG).show();
                val = 0;
                break;
            } else if (damageDetailList.get(i).getDamageDesc().equals("")) {
                Toast.makeText(mContext,
                        "Please enter damage description of item " + count, Toast.LENGTH_LONG)
                        .show();
                val = 0;
                break;
            } else if (damageDetailList.get(i).getSpinnerSelectPos() == 0) {
                Toast.makeText(mContext,
                        "Please choose Item Type of item " + count, Toast.LENGTH_LONG)
                        .show();
                val = 0;
                break;
            } else {
                val = 1;

            }

        }*/
        return 1;

    }

    public void showDialogNew(String dialog_title, final Context context, ArrayList<BranchLocation> list, final int Adapetr_position, final int type) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context.getApplicationContext());
       alertDialog1 = dialogBuilder.create();
      //  LayoutInflater inflater = fragment.getActivity().getLayoutInflater();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText autoText_TimeZone = (EditText) dialogView.findViewById(R.id.autoText_TimeZone);
        final ListView listvw = (ListView) dialogView.findViewById(R.id.listview);


        ArrayAdapter<BranchLocation> adapter = new ArrayAdapter<BranchLocation>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listvw.setAdapter(adapter);

        listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                BranchLocation spinner_ = (BranchLocation) listvw.getItemAtPosition(position);
                String txt = spinner_.get_text();
                String Branch_id = spinner_.get_id();
                //  editText.setText(txt);

                if (type == 1) {
                    damageDetailList.get(Adapetr_position).setType_worker(txt);
                } else if (type == 2) {
                    damageDetailList.get(Adapetr_position).setType_labor(txt);
                } else if (type == 3) {
                    damageDetailList.get(Adapetr_position).setType_BrickKilns(txt);
                }

                try {
                    alertDialog1.dismiss();
                } catch (Exception e) {
                    e.getMessage();
                }


            }
        });


        dialogBuilder.setTitle(dialog_title);
        if (!alertDialog1.isShowing()) {
            alertDialog1.show();
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public EditText type_worker, type_labor, et_workAssigned, type_BrickKilns, et_name,
                et_age, et_gender, et_Address, et_comments;
        // public Spinner spinner;
        //ImageView imgvw_remove, uploadPic;

        TextView itencount;
        Button uploadPic;
       //,imgvwUploadedPic;

        public UserViewHolder(View itemView) {
            super(itemView);
            itencount = itemView.findViewById(R.id.itencount);

            type_worker = itemView.findViewById(R.id.et_worker);//dd
            type_labor = itemView.findViewById(R.id.type_labor); //dd
            type_BrickKilns = itemView.findViewById(R.id.et_BrickKilns); //dd

            et_workAssigned = itemView.findViewById(R.id.et_workAssigned);
            et_name = itemView.findViewById(R.id.et_name);
            et_age = itemView.findViewById(R.id.et_age);
            et_gender = itemView.findViewById(R.id.et_gender);
            et_Address = itemView.findViewById(R.id.et_Address);
            et_comments = itemView.findViewById(R.id.et_comments);
            uploadPic = itemView.findViewById(R.id.btn_upload);

            // imgvwUploadedPic.setVisibility(View.GONE);


           /* imgvwUploadedPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  *//*  Intent i = new Intent(mContext, FullscreenWebViewNew.class);
                    i.putExtra("url", SOAP_API_Client.URL_EP2 + "/upload/" + damageDetailList.get(getAdapterPosition()).getUploadedPhotoUrl());
                    i.putExtra("FileName", damageDetailList.get(getAdapterPosition()).getUploadedPhotoUrl());
                    mContext.startActivity(i);*//*
                }
            });
*/
            et_workAssigned.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    damageDetailList.get(getAdapterPosition()).setWorkAssigned(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            et_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    damageDetailList.get(getAdapterPosition()).setName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            et_age.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    damageDetailList.get(getAdapterPosition()).setAge(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            et_gender.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    damageDetailList.get(getAdapterPosition()).setGender(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            et_Address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    damageDetailList.get(getAdapterPosition()).setAddress(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            et_comments.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    damageDetailList.get(getAdapterPosition()).setComments(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


           /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int SpinnerPosition, long id) {
                    damageDetailList.get(getAdapterPosition()).setSpinnerSelectPos(SpinnerPosition);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            imgvw_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    damageDetailList.remove(getAdapterPosition());
                    // notifyItemRemoved(getAdapterPosition());
                    // notifyItemRangeChanged(getAdapterPosition(), damageDetailList.size());
                    notifyDataSetChanged();
                }
            });*/

            uploadPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (new ConnectionDetector(mContext).isConnectingToInternet()) {
                        uploadedImagePos = getAdapterPosition();


                        ((ReportBondedLabor) fragment).opendilogforattachfileandimage();

                    } else {
                        Toast.makeText(mContext, Utility.NO_INTERNET, Toast.LENGTH_LONG).show();
                    }
                }
            });

            type_worker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // showDialogNew("Type of Worker", fragment.getActivity(), WorkerTypeList, getAdapterPosition(), 1);
                    ((ReportBondedLabor) fragment).  showDialogNew("Type of Worker", fragment.getActivity(), WorkerTypeList, getAdapterPosition(), 1);

                }
            });
            type_labor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  showDialogNew("Type of Labor", fragment.getActivity(), LaborTypeList, getAdapterPosition(), 2);

                    ((ReportBondedLabor) fragment).  showDialogNew("Type of Labor", fragment.getActivity(), LaborTypeList, getAdapterPosition(), 2);
                }
            });
            type_BrickKilns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  showDialogNew("Center", fragment.getActivity(), KilnsTypeList, getAdapterPosition(), 3);

                    ((ReportBondedLabor) fragment).  showDialogNew("Assigned Kilns", fragment.getActivity(), KilnsTypeList, getAdapterPosition(), 3);
                }
            });


        }
    }


    void setData( int type,int Adapetr_position,String txt){
        if (type == 1) {
            damageDetailList.get(Adapetr_position).setType_worker(txt);
        } else if (type == 2) {
            damageDetailList.get(Adapetr_position).setType_labor(txt);
        } else if (type == 3) {
            damageDetailList.get(Adapetr_position).setType_BrickKilns(txt);
        }
        notifyDataSetChanged();
    }
}