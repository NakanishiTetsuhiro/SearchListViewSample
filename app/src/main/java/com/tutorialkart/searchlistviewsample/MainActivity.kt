package com.tutorialkart.searchlistviewsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView
import java.text.Collator
import java.util.*


class MainActivity : AppCompatActivity(), TextWatcher {

    private var arraylist: ArrayList<Company> = ArrayList()
    private var myAdapter: MyAdapter? = null
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        // fastScroll有効化
        listView!!.isFastScrollEnabled = true
        // スクロールを常に表示するように
        listView!!.isFastScrollAlwaysVisible = true


        // START: ここでListViewに入れ込みたいデータをくるくるまわしていれればいいと思う
        var company = Company()

        // TODO: お気に入り(^^)/とかの見出しをつけたいがここのrowだけスタイルを変える方法を僕たちはまだ知らない
//        company = Company()
//        company.id = 0
//        company.name = "お気に入り"
//        company.kana = "おきにいり"
//        company.isEnabled = false
//        arraylist.add(company)

        // アイデア：お気に入りの会社を入れる配列と、そうじゃない配列を用意して、最後に２つをjoinさせたらどうだろうか。そしたらfavorite=trueの会社が一番上にくるし
        company = Company()
        company.id = 1
        company.name = "株式会社ふー"
        company.kana = "かぶしきがいしゃふう"
        arraylist.add(company)

        company = Company()
        company.id = 2
        company.name = "hoge"
        company.kana = "foo"
        arraylist.add(company)

        company = Company()
        company.id = 3
        company.name = "まじですかい有限会社"
        company.kana = "まじですかいゆうげんがいしゃ"
        arraylist.add(company)

        company = Company()
        company.id = 4
        company.name = "株式会社bar"
        company.kana = "かぶしきがいしゃばあ"
        arraylist.add(company)

        // TODO: お気に入りのアイテムをリストの上に持ってくる機能の実装
        company = Company()
        company.id = 5
        company.name = "株式会社お気に入り"
        company.kana = "かぶしきがいしゃおきにいり"
        company.isFavorite = true
        arraylist.add(company)

        company = Company()
        company.id = 6
        company.name = "Pfizer Holdings G.K."
        company.kana = "ふぁいざーほーるでぃんぐすごうどうがいしゃ"
        arraylist.add(company)
        // END: ここで入れ込みたいデータをくるくるまわしていれればいいと思う

//        val collator = Collator.getInstance(Locale.JAPANESE)
//        Collections.sort(arraylist, collator)
        Collections.sort(arraylist, KanaComparator())

        // adapterつくった
        myAdapter = MyAdapter(this@MainActivity, arraylist)
        // さっき作った配列をアダプタにせっと！
        myAdapter!!.setCompanyList(arraylist)
        // listViewにさっき作ったadapterをセット
        listView!!.adapter = myAdapter

        // テキストが検索欄に入力されたのを検知するリスナーをセット
        (findViewById<EditText>(R.id.editText)).addTextChangedListener(this)
    }

    override fun afterTextChanged(p0: Editable?) {
        // テキストが入力されたあとにFilterを呼ばれるようにしたらフィルタリングできますやん！
        myAdapter!!.filter.filter(p0)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}
