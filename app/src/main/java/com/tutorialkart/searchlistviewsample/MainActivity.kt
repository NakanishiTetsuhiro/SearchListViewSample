package com.tutorialkart.searchlistviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ListView
import java.util.*


class MainActivity : AppCompatActivity(), TextWatcher {

    private var listViewArraylist: ArrayList<Company> = ArrayList()
    private var listViewAdapter: MyAdapter? = null
    private var listView: ListView? = null

    private var searchedListViewarraylist: ArrayList<Company> = ArrayList()
    private var searchedListViewAdapter: MyAdapter? = null
    private var searchedListView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        searchedListView = findViewById(R.id.searchedListView)

        // fastScroll有効化
        listView!!.isFastScrollEnabled = true
        // スクロールを常に表示するように
        listView!!.isFastScrollAlwaysVisible = true


        var company = Company()
        var favoriteList: ArrayList<Company> = ArrayList()
        var normalList: ArrayList<Company> = ArrayList()

        // START: ここでListViewに入れ込みたいデータをくるくるまわしていれればいいと思う
        company = Company()
        company.id = 1
        company.name = "株式会社ふー"
        company.kana = "かぶしきがいしゃふう"
        normalList.add(company)

        company = Company()
        company.id = 2
        company.name = "hoge"
        company.kana = "foo"
        normalList.add(company)

        company = Company()
        company.id = 11
        company.name = "株式会社お気に入り2"
        company.kana = "かぶしきがいしゃおきにいり2"
        company.isFavorite = true
        favoriteList.add(company)

        company = Company()
        company.id = 3
        company.name = "まじですかい有限会社"
        company.kana = "まじですかいゆうげんがいしゃ"
        normalList.add(company)

        company = Company()
        company.id = 4
        company.name = "株式会社bar"
        company.kana = "かぶしきがいしゃばあ"
        normalList.add(company)

        company = Company()
        company.id = 5
        company.name = "株式会社お気に入り"
        company.kana = "かぶしきがいしゃおきにいり"
        company.isFavorite = true
        favoriteList.add(company)

        company = Company()
        company.id = 6
        company.name = "てきとう合同会社"
        company.kana = "てきとうごうどうがいしゃ"
        normalList.add(company)
        // END: ここで入れ込みたいデータをくるくるまわしていれればいいと思う

        Collections.sort(listViewArraylist, KanaComparator())

        listViewArraylist.addAll(favoriteList)
        listViewArraylist.addAll(normalList)

        // 検索したあと用のアダプタをセット(検索に関係のない索引の行を追加する前に)
        searchedListViewAdapter = MyAdapter(this@MainActivity, listViewArraylist)
        searchedListViewAdapter!!.setCompanyList(listViewArraylist)
        searchedListView!!.adapter = searchedListViewAdapter

        // お気に入りの索引を配列の先頭につっこむ
        company = Company()
        company.id = 0
        company.name = "お気に入り"
        company.isEnabled = false
        favoriteList.add(0, company)

        // ソートしたあとに、その他の索引を配列の先頭につっこむ
        company = Company()
        company.id = 0
        company.name = "その他"
        company.isEnabled = false
        normalList.add(0, company)

        searchedListViewarraylist.addAll(favoriteList)
        searchedListViewarraylist.addAll(normalList)

        // 検索する前用のアダプタをセット
        listViewAdapter = MyAdapter(this@MainActivity, searchedListViewarraylist)
        listViewAdapter!!.setCompanyList(searchedListViewarraylist)
        listView!!.adapter = listViewAdapter

        // 検索結果を表示する用のlistViewを非表示に設定
        searchedListView!!.visibility = View.GONE

        // テキストが検索欄に入力されたのを検知するリスナーをセット
        (findViewById<EditText>(R.id.editText)).addTextChangedListener(this)
    }

    override fun afterTextChanged(p0: Editable?) {
        if(p0!!.length > 0) {
            searchedListView!!.visibility = View.VISIBLE
            listView!!.visibility = View.GONE

            // テキストが入力されたあとにFilterを呼ばれるようにしたらフィルタリングできますやん！
            searchedListViewAdapter!!.filter.filter(p0)

        } else {
            searchedListView!!.visibility = View.GONE
            listView!!.visibility = View.VISIBLE
        }
    }

    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}
