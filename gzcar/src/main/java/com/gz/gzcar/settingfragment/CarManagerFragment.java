package com.gz.gzcar.settingfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gz.gzcar.Database.CarInfoTable;
import com.gz.gzcar.MyApplication;
import com.gz.gzcar.R;
import com.gz.gzcar.settings.CarAdd;
import com.gz.gzcar.settings.CarUpdate;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Endeavor on 2016/8/8.
 * <p/>
 * 车辆管理
 */
public class CarManagerFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.carnumber)
    EditText mCarNumber;
    @Bind(R.id.cartype)
    EditText mCarType;
    @Bind(R.id.setting_carmanager_recyclerview)
    RecyclerView rcy;
    private Button add;
    private Button update;
    private Button delete;
    private DbManager db = x.getDb(MyApplication.daoConfig);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private List<CarInfoTable> allData;
    private MyAdapter myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car_manager, container, false);
        add = (Button) view.findViewById(R.id.car_add);
        update = (Button) view.findViewById(R.id.car_update);
        delete = (Button) view.findViewById(R.id.car_delete);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {

        mCarNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String carNum = mCarNumber.getText().toString().trim();
                String type = mCarType.getText().toString().trim();

                if (!TextUtils.isEmpty(carNum)) {
                    if (!TextUtils.isEmpty(type)) {
                        try {
                            List<CarInfoTable> all = db.selector(CarInfoTable.class)
                                    .where("car_no", "=", carNum)
                                    .and("car_type", "=", type)
                                    .findAll();
                            allData.clear();
                            allData.addAll(all);
                            myAdapter.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            List<CarInfoTable> all = db.selector(CarInfoTable.class)
                                    .where("car_no", "=", carNum)
                                    .findAll();
                            allData.clear();
                            allData.addAll(all);
                            myAdapter.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(!TextUtils.isEmpty(type)){
                    try {
                        List<CarInfoTable> all = db.selector(CarInfoTable.class)
                                .where("car_type", "=", type)
                                .findAll();
                        allData.clear();
                        allData.addAll(all);
                        myAdapter.notifyDataSetChanged();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        List<CarInfoTable> all = db.selector(CarInfoTable.class).findAll();
                        allData.clear();
                        allData.addAll(all);
                        myAdapter.notifyDataSetChanged();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
//晋A8888    临时车
            }
        });
        mCarType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String carNum = mCarNumber.getText().toString().trim();
                String type = mCarType.getText().toString().trim();

                if (!TextUtils.isEmpty(type)) {
                    if (!TextUtils.isEmpty(carNum)) {
                        try {
                            List<CarInfoTable> all = db.selector(CarInfoTable.class)
                                    .where("car_no", "=", carNum)
                                    .and("car_type", "=", type)
                                    .findAll();
                            allData.clear();
                            allData.addAll(all);
                            myAdapter.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            List<CarInfoTable> all = db.selector(CarInfoTable.class)
                                    .where("car_type", "=", type)
                                    .findAll();
                            allData.clear();
                            allData.addAll(all);
//                        Toast.makeText(getContext(),"all="+all.size()+";;allData="+allData.size(),Toast.LENGTH_SHORT).show();

                            myAdapter.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (!TextUtils.isEmpty(carNum)) {
                    try {
                        List<CarInfoTable> all = db.selector(CarInfoTable.class)
                                .where("car_no", "=", carNum)
                                .findAll();
                        allData.clear();
                        allData.addAll(all);
//                        Toast.makeText(getContext(),"all="+all.size()+";;allData="+allData.size(),Toast.LENGTH_SHORT).show();

                        myAdapter.notifyDataSetChanged();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        List<CarInfoTable> all = db.selector(CarInfoTable.class).findAll();
                        allData.clear();
                        allData.addAll(all);
                        myAdapter.notifyDataSetChanged();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        add.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);


        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcy.setLayoutManager(lm);
        myAdapter = new MyAdapter();
        rcy.setAdapter(myAdapter);

    }

    private void initData() {

        try {
//            CarInfoTable mInfo = new CarInfoTable();
//            mInfo.setCar_color("白色");
//            mInfo.setCar_no("晋A1438");
//            mInfo.setCar_type("探亲车");
//
//            db.save(mInfo);

            allData = db.selector(CarInfoTable.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.car_add:
                startActivityForResult(new Intent(getActivity(), CarAdd.class), 438);
                break;
            case R.id.car_update:
                startActivity(new Intent(getActivity(), CarUpdate.class));
                break;
            case R.id.car_delete:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("mm", "onActivityResult...");
        Log.e("mm","requestCode=="+requestCode);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = View.inflate(getActivity(), R.layout.search_car_info, false);
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_setting_carmanager, parent, false);
            MyHolder myHolder = new MyHolder(itemView);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            CarInfoTable carInfo = allData.get(position);
            holder.mId.setText(position + 1 + "");
            holder.mCarNumber.setText(carInfo.getCar_no());
            holder.mType.setText(carInfo.getCar_type());
            holder.mCarWei.setText(carInfo.getPerson_address());
            holder.mPerson.setText(carInfo.getPerson_name());
            holder.mPhone.setText(carInfo.getPerson_tel());
            holder.mAddress.setText(carInfo.getPerson_address());

            Date start_date = allData.get(position).getStart_date();
            Date stop_date = allData.get(position).getStop_date();
            if (start_date != null) {

                holder.mStartDate.setText(dateFormat.format(start_date));
            }
            if (stop_date != null) {

                holder.mEndDate.setText(dateFormat.format(stop_date));
            }

        }

        @Override
        public int getItemCount() {
            return allData.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_setting_carmanager_id)
        TextView mId;
        @Bind(R.id.item_setting_carmanager_carnumber)
        TextView mCarNumber;
        @Bind(R.id.item_setting_carmanager_type)
        TextView mType;
        @Bind(R.id.item_setting_carmanager_carwei)
        TextView mCarWei;
        @Bind(R.id.item_setting_carmanager_person)
        TextView mPerson;
        @Bind(R.id.item_setting_carmanager_phone)
        TextView mPhone;
        @Bind(R.id.item_setting_carmanager_address)
        TextView mAddress;
        @Bind(R.id.item_setting_carmanager_startdate)
        TextView mStartDate;
        @Bind(R.id.item_setting_carmanager_enddate)
        TextView mEndDate;

        public MyHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
