package com.tutorialkart.searchlistviewsample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
    private ArrayList<Company> companyList;
    private ArrayList<Company> _Company;
    private ValueFilter valueFilter;
    private LayoutInflater inflater;
    private ArrayList<Company> mStringFilterList;


    // コンストラクタやで
    public MyAdapter(Activity context, ArrayList<Company> _Company) {
        super();
        this.context = context;
        this._Company = _Company;
        mStringFilterList =  _Company;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }

    // メンバ変数のCompanyListにsetするやで
    public void setCompanyList(ArrayList<Company> CompanyList) {
        this.companyList = CompanyList;
    }

    // CompanyListの大きさをgetするやで
    @Override
    public int getCount() {
        return companyList.size();
    }

    //　そのpositionのCompanyListのNameをとってくる
    @Override
    public Object getItem(int position) {
        return companyList.get(position).getName();
    }

    // ListViewのitemをタップしても効かなくする
    @Override
    public boolean isEnabled(int position) {
        if (companyList.get(position).isEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long getItemId(int position) {
        return companyList.get(position).getId();
    }

    // クラスやで。多分データしか持てないクラスやで
    public class ViewHolder {
        TextView name, kana;
    }

    // getView()内で先ほど作成したListViewの行のレイアウトファイルを指定しTextViewに名前などを格納します。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.companyrow, null);
            holder.name = convertView.findViewById(R.id.name);
            holder.kana = convertView.findViewById(R.id.kana);

            convertView.setTag(holder);

            // 索引のrowは見た目を変更
            if (companyList.get(position).isEnabled() == false) {
                // 背景色を変更
                convertView.setBackgroundColor(Color.rgb(211, 211, 211));

                // TODO: 索引の行のkana欄を削除したいときのやり方がわからない

                // 行の高さを変えたいとき
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, 500);
//                convertView.setLayoutParams(params);
            }
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.name.setText("" + companyList.get(position).getName());
        holder.kana.setText("" + "" + companyList.get(position).getKana());

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
            FilterResults results = new FilterResults();

            // 検索欄に入力された文字が１文字以上なら処理に入る
            if(constraint!=null && constraint.length()>0){
                ArrayList<Company> filterList=new ArrayList<Company>();
                for(int i=0;i<mStringFilterList.size();i++){
                    if((mStringFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())
                       ||
                       (mStringFilterList.get(i).getKana().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        Company Company = new Company();
                        Company.setName(mStringFilterList.get(i).getName());
                        Company.setKana(mStringFilterList.get(i).getKana());
                        Company.setId(mStringFilterList.get(i).getId());
                        filterList.add(Company);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;

            // 何も入力されていないときの処理
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
            companyList = (ArrayList<Company>) results.values;
            notifyDataSetChanged();
        }
    }
}