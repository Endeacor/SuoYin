package com.gz.gzcar.searchfragment;


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
import android.widget.Toast;

import com.gz.gzcar.Database.CarInfoTable;
import com.gz.gzcar.MyApplication;
import com.gz.gzcar.R;
import com.gz.gzcar.utils.T;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Endeavor on 2016/8/8.
 * <p/>
 * 车辆管理
 */
public class CarInfoFragment extends Fragment {

    @Bind(R.id.et_search_car)
    EditText mCarNumber;
    @Bind(R.id.btn_search_car)
    Button mSearchButton;
    private DbManager db = x.getDb(MyApplication.daoConfig);
    private RecyclerView rcy;
    private View view;
    private List<CarInfoTable> allData;
    private MyAdapter myAdapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_car, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_search_car)
    public void onClick() {
        String carNum = mCarNumber.getText().toString().trim();
        Toast.makeText(getActivity(), "carNum111=" + carNum, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(carNum) && carNum.length() > 1) {
            try {
//                Toast.makeText(getActivity(), "carNum=" + carNum, Toast.LENGTH_SHORT).show();
                List<CarInfoTable> carNumList = db.selector(CarInfoTable.class).where("car_no", "=", carNum).findAll();
                allData.clear();
                allData.addAll(carNumList);
                Toast.makeText(getContext(), "carNumList=" + carNumList.size() + ";;allData=" + allData.size(), Toast.LENGTH_SHORT).show();

                myAdapter.notifyDataSetChanged();
            } catch (DbException e) {
                T.showShort(getActivity(), "查询异常");
//                Toast.makeText(getActivity(), "查询异常", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            T.showShort(getActivity(),"请输入正确的车牌号码");
//            Toast.makeText(getActivity(), "请输入正确的车牌号码", Toast.LENGTH_SHORT).show();
        }
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
//                Toast.makeText(getActivity(),"carNum111="+carNum,Toast.LENGTH_SHORT).show();
                if (carNum.length() == 0) {
                    try {
                        List<CarInfoTable> all = db.selector(CarInfoTable.class).findAll();
                        allData.clear();
                        allData.addAll(all);
//                        Toast.makeText(getContext(),"all="+all.size()+";;allData="+allData.size(),Toast.LENGTH_SHORT).show();

                        myAdapter.notifyDataSetChanged();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        rcy = (RecyclerView) view.findViewById(R.id.search_car_info_recyclerview);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcy.setLayoutManager(lm);
        myAdapter = new MyAdapter();
        rcy.setAdapter(myAdapter);

    }

    private void initData() {
//        try {
//            db.dropTable(CarInfoTable.class);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        addData();
        try {
            allData = db.selector(CarInfoTable.class).findAll();
//            allData = db.selector(CarInfoTable.class).where()
            Log.e("my", "list:" + allData.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void addData() {

        CarInfoTable mInfo = new CarInfoTable();
        mInfo.setCar_color("白色");
        mInfo.setCar_no("晋A8888");
        mInfo.setCar_type("临时车");
        try {
            db.save(mInfo);
            Toast.makeText(getActivity(), "mInfo...", Toast.LENGTH_SHORT).show();
        } catch (DbException e) {
            e.printStackTrace();
        }
        CarInfoTable mInfo1 = new CarInfoTable();
        mInfo1.setCar_color("绿色");
        mInfo1.setCar_no("晋A6666");
        mInfo1.setCar_type("临时车");
        try {
            db.save(mInfo1);
            Toast.makeText(getActivity(), "mInfo1...", Toast.LENGTH_SHORT).show();
        } catch (DbException e) {
            e.printStackTrace();
        }
        CarInfoTable mInfo2 = new CarInfoTable();
        mInfo2.setCar_color("红色");
        mInfo2.setCar_no("晋A3333");
        mInfo2.setCar_type("临时车");
        try {
            db.save(mInfo2);
            Toast.makeText(getActivity(), "mInfo2...", Toast.LENGTH_SHORT).show();
        } catch (DbException e) {
            e.printStackTrace();
        }

        try {
            List<CarInfoTable> list = db.selector(CarInfoTable.class).findAll();
            Log.e("my", "list:" + list.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = View.inflate(getActivity(), R.layout.search_car_info, false);
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_search_car_info, parent, false);
            MyHolder myHolder = new MyHolder(itemView);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            holder.carNum.setText(allData.get(position).getCar_no());
            holder.cartype.setText(allData.get(position).getCar_type());
            holder.carwei.setText(allData.get(position).getPerson_address());
            holder.person.setText(allData.get(position).getPerson_name());
            holder.phone.setText(allData.get(position).getPerson_tel());
            holder.address.setText(allData.get(position).getPerson_address());
            Date start_date = allData.get(position).getStart_date();
            Date stop_date = allData.get(position).getStop_date();
            if (start_date!=null){

                holder.startTime.setText(dateFormat.format(start_date));
            }
            if (stop_date!=null){

                holder.endTime.setText(dateFormat.format(stop_date));
            }

        }

        @Override
        public int getItemCount() {
            return allData.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView carNum;
        private TextView cartype;
        private TextView carwei;
        private TextView person;
        private TextView phone;
        private TextView address;
        private TextView startTime;
        private TextView endTime;

        public MyHolder(View itemView) {
            super(itemView);

            carNum = (TextView) itemView.findViewById(R.id.search_carinfo_carnum);
            cartype = (TextView) itemView.findViewById(R.id.search_carinfo_cartype);
            carwei = (TextView) itemView.findViewById(R.id.search_carinfo_carwei);
            person = (TextView) itemView.findViewById(R.id.search_carinfo_person);
            phone = (TextView) itemView.findViewById(R.id.search_carinfo_phone);
            address = (TextView) itemView.findViewById(R.id.search_carinfo_address);
            startTime = (TextView) itemView.findViewById(R.id.search_carinfo_starttime);
            endTime = (TextView) itemView.findViewById(R.id.search_carinfo_endtime);
        }
    }
}
