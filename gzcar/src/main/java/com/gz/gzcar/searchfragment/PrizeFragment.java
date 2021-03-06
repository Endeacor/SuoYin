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

import com.bigkoo.pickerview.TimePickerView;
import com.gz.gzcar.Database.FreeInfoTable;
import com.gz.gzcar.MyApplication;
import com.gz.gzcar.R;
import com.gz.gzcar.utils.T;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Endeavor on 2016/8/8.
 * <p/>
 * 收费记录
 */
public class PrizeFragment extends Fragment {


    @Bind(R.id.et_money_carnumber)
    EditText mCarNumber;
    @Bind(R.id.et_money_starttime)
    TextView mStartTime;
    @Bind(R.id.et_money_endtime)
    TextView mEndTime;
    @Bind(R.id.btn_money_search)
    Button mSearch;
    @Bind(R.id.money_recyclerview)
    RecyclerView rcy;
    @Bind(R.id.tv_money)
    TextView mMoney;
    private TimePickerView pvTime;
    private TimePickerView pvTime2;
    private DbManager db = x.getDb(MyApplication.daoConfig);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private List<FreeInfoTable> allData;
    private MyAdapter myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_money, container, false);

        ButterKnife.bind(this, view);
        return view;
    }


    private void initViews() {

        //时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.ALL);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                mStartTime.setText(getTime(date));
            }
        });

        pvTime2 = new TimePickerView(getActivity(), TimePickerView.Type.ALL);
        pvTime2.setTime(new Date());
        pvTime2.setCyclic(false);
        pvTime2.setCancelable(true);
        //时间选择后回调
        pvTime2.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                mEndTime.setText(getTime(date));
            }
        });

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
                        List<FreeInfoTable> all = db.selector(FreeInfoTable.class).findAll();
                        allData.clear();
                        allData.addAll(all);
//                        Toast.makeText(getContext(),"all="+all.size()+";;allData="+allData.size(),Toast.LENGTH_SHORT).show();

                        myAdapter.notifyDataSetChanged();
                        sumMoney();

                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcy.setLayoutManager(lm);
        myAdapter = new MyAdapter();
        rcy.setAdapter(myAdapter);
        sumMoney();
    }

    private void initdata() {
//        addData();
        try {
            allData = db.selector(FreeInfoTable.class).findAll();
        } catch (DbException e) {
            T.showShort(getContext(), "全部查询异常");
            e.printStackTrace();
        }

    }

    private void addData() {
        try {
            FreeInfoTable free = new FreeInfoTable();
            free.setCarNumber("晋A1234");
            free.setMoney(15.25);
            free.setParkTime("75分钟");
            free.setType("临时车");
            free.setInTime(dateFormat.parse("2016-2-4 13:15"));
            free.setOutTime(dateFormat.parse("2016-2-4 14:35"));
            db.save(free);

            FreeInfoTable free1 = new FreeInfoTable();
            free1.setCarNumber("晋A6546");
            free1.setMoney(10.25);
            free1.setParkTime("15分钟");
            free1.setType("临时车");
            free1.setInTime(dateFormat.parse("2016-2-4 13:15"));
            free1.setOutTime(dateFormat.parse("2016-2-4 13:35"));
            db.save(free1);

            FreeInfoTable free2 = new FreeInfoTable();
            free2.setCarNumber("晋A8989");
            free2.setMoney(15.05);
            free2.setParkTime("11分钟");
            free2.setType("临时车");
            free2.setInTime(dateFormat.parse("2016-2-4 13:15"));
            free2.setOutTime(dateFormat.parse("2016-2-4 13:25"));
            db.save(free2);

            FreeInfoTable free3 = new FreeInfoTable();
            free3.setCarNumber("晋A6575");
            free3.setMoney(15.00);
            free3.setParkTime("20分钟");
            free3.setType("临时车");
            free3.setInTime(dateFormat.parse("2016-2-4 13:05"));
            free3.setOutTime(dateFormat.parse("2016-2-4 13:25"));
            db.save(free3);

            FreeInfoTable free4 = new FreeInfoTable();
            free4.setCarNumber("晋A1111");
            free4.setMoney(5.00);
            free4.setParkTime("5分钟");
            free4.setType("临时车");
            free4.setInTime(dateFormat.parse("2016-2-4 15:15"));
            free4.setOutTime(dateFormat.parse("2016-2-4 15:20"));
            db.save(free4);
        } catch (ParseException e) {
            T.showShort(getContext(), "时间转化异常");
            e.printStackTrace();
        } catch (DbException e) {
            T.showShort(getContext(), "数据添加异常");
            e.printStackTrace();
        }
    }

    @OnClick({R.id.et_money_starttime, R.id.et_money_endtime, R.id.btn_money_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_money_starttime:
                pvTime.show();
                break;
            case R.id.et_money_endtime:
                pvTime2.show();
                break;

            case R.id.btn_money_search:

                String carNum = mCarNumber.getText().toString().trim();
                String start = mStartTime.getText().toString().trim();
                String end = mEndTime.getText().toString().trim();

                if (TextUtils.isEmpty(carNum)) {
                    try {
                        List<FreeInfoTable> all = db.selector(FreeInfoTable.class)
                                .where("in_time", ">", dateFormat.parse(start))
                                .and("out_time", "<", dateFormat.parse(end))
//                            .and("car_no","=",carNum)
                                .findAll();

                        Log.e("mm", "all.size==" + allData.size());

                        if (all.size()>0){
                            allData.clear();
                            allData.addAll(all);
                            myAdapter.notifyDataSetChanged();
                            sumMoney();
                        }else{
                            T.showShort(getContext(),"未查到相关数据");
                            allData.clear();
                            allData = db.selector(FreeInfoTable.class).findAll();
                            myAdapter.notifyDataSetChanged();
                            sumMoney();
                        }

                    } catch (DbException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        List<FreeInfoTable> all = db.selector(FreeInfoTable.class)
                                .where("in_time", ">", dateFormat.parse(start))
                                .and("out_time", "<", dateFormat.parse(end))
                                .and("car_number", "=", carNum)
                                .findAll();

                        Log.e("mm", "all.size==" + allData.size());

                        if (all.size()>0){
                            allData.clear();
                            allData.addAll(all);
                            myAdapter.notifyDataSetChanged();
                            sumMoney();
                        }else{
                            T.showShort(getContext(),"未查到相关数据");
                            allData.clear();
                            allData = db.selector(FreeInfoTable.class).findAll();
                            myAdapter.notifyDataSetChanged();
                            sumMoney();
                        }

                    } catch (DbException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }




                break;
        }
    }

    private void sumMoney(){

        double toteMoney = 0;
        for (int i = 0; i < allData.size(); i++) {

            double money = allData.get(i).getMoney();
            toteMoney+=money;
        }
        mMoney.setText("合计金额:"+toteMoney);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = View.inflate(getActivity(), R.layout.search_car_info, false);
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_search_free, parent, false);
            MyHolder myHolder = new MyHolder(itemView);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            FreeInfoTable free = allData.get(position);
            holder.mId.setText(position+1+"");
            holder.mCarNum.setText(free.getCarNumber());
            holder.mMoney.setText(free.getMoney()+"");
            holder.mParkingtime.setText(free.getParkTime());
            holder.mType.setText(free.getType());
            Date inTime = free.getInTime();
            if (inTime!=null){

                holder.mInTime.setText(dateFormat.format(inTime));
            }
            if (free.getOutTime()!=null){

                holder.mOuttime.setText(dateFormat.format(free.getOutTime()));
            }

            free = null;
        }

        @Override
        public int getItemCount() {
            return allData.size();
        }

    }


    class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_free_id)
        TextView mId;
        @Bind(R.id.item_free_num)
        TextView mCarNum;
        @Bind(R.id.item_free_type)
        TextView mType;
        @Bind(R.id.item_free_intime)
        TextView mInTime;
        @Bind(R.id.item_free_outtime)
        TextView mOuttime;
        @Bind(R.id.item_free_parkingtime)
        TextView mParkingtime;
        @Bind(R.id.item_free_money)
        TextView mMoney;

        public MyHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


    public String getTime(Date date) {

        return dateFormat.format(date);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initdata();
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
