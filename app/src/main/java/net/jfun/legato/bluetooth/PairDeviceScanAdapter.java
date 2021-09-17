package net.jfun.legato.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.jfun.legato.R;

import java.util.ArrayList;
import java.util.List;

public class PairDeviceScanAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;

    List<BluetoothDevice> mRowList;

    public PairDeviceScanAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;

        mRowList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mRowList.size();
    }

    @Override
    public BluetoothDevice getItem(int i) {
        return mRowList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addOneData(BluetoothDevice data){
        mRowList.add(data);
        notifyDataSetChanged();
    }

    public void addAllData(List<BluetoothDevice> datas){
        for (BluetoothDevice device : datas) {
            mRowList.add(device);
        }
        notifyDataSetChanged();
    }

    public void removeAllData(){
        mRowList = new ArrayList<>();
        notifyDataSetChanged();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        final BluetoothDevice data = mRowList.get(position);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_device, viewGroup, false);
            holder = new ViewHolder();

            holder.device_name = (TextView)convertView.findViewById(R.id.textview_name);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if (TextUtils.isEmpty(data.getName())){
            holder.device_name.setText(data.getAddress());
        } else {
            holder.device_name.setText(data.getName());
        }

        return convertView;
    }
    private class ViewHolder{

        private TextView device_name;
    }
}
