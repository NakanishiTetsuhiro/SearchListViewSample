package com.tutorialkart.searchlistviewsample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by tetsuhiro on 2017/12/30.
 */

public class MyAdapter extends BaseAdapter implements Filterable {
    // メンバ変数を宣言
    private Context context;
    private LayoutInflater layoutInflater = null;
    private ArrayList<Food> foodList;
    private ArrayList<Food> _Food;
    private ValueFilter valueFilter;
    private LayoutInflater inflater;
    private ArrayList<Food> mStringFilterList;


    // コンストラクタやで
    public MyAdapter(Activity context, ArrayList<Food> _Food) {
        super();
        this.context = context;
        this._Food = _Food;
        mStringFilterList =  _Food;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }

    // メンバ変数のfoodListにsetするやで
    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    // foodListの大きさをgetするやで
    @Override
    public int getCount() {
        return foodList.size();
    }

    //　そのpositionのfoodListのNameをとってくる
    // positionってなんだ？
    @Override
    public Object getItem(int position) {
//        return foodList.get(position);
        return foodList.get(position).getName();
    }

    // ListViewのitemをタップしても効かなくする
    @Override
    public boolean isEnabled(int position) {
        if (foodList.get(position).getEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    // positionってなんだ？
    @Override
    public long getItemId(int position) {
        return foodList.get(position).getId();
    }

    // クラスやで。多分データしか持てないクラスやで
    public class ViewHolder {
        TextView name, kana;
    }

    // getView()内で先ほど作成したListViewの行のレイアウトファイルを指定しTextViewに名前や値段を格納します。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 最初のコードもあとから書いたコードもどっちもうごく。。。

        // なんか最初に書いてあったコード
////        convertView = layoutInflater.inflate(R.layout.foodrow,parent,false);
//        convertView = inflater.inflate(R.layout.foodrow, null);
//        ((TextView)convertView.findViewById(R.id.name)).setText(foodList.get(position).getName());
//        ((TextView)convertView.findViewById(R.id.kana)).setText(String.valueOf(foodList.get(position).getKana()));
//        return convertView;

        // あとから書いたコード
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.foodrow, null);
            holder.name = convertView.findViewById(R.id.name);
            holder.kana = convertView.findViewById(R.id.kana);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.name.setText("" + foodList.get(position).getName());
        holder.kana.setText("" + "" + foodList.get(position).getKana());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    // ListViewのitemをフィルタリングするためのクラス
    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<Food> filterList=new ArrayList<Food>();
                for(int i=0;i<mStringFilterList.size();i++){
                    if((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())
                       ||
                       (mStringFilterList.get(i).getKana().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Food food = new Food();
                        food.setName(mStringFilterList.get(i).getName());
                        food.setKana(mStringFilterList.get(i).getKana());
                        food.setId(mStringFilterList.get(i).getId());
                        filterList.add(food);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=mStringFilterList.size();
                results.values=mStringFilterList;
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            foodList = (ArrayList<Food>) results.values;
            notifyDataSetChanged();
        }
    }
}