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
        var food = Company()

        // お気に入り(^^)/とかの見出しをつけたいがここのrowだけスタイルを変える方法を僕たちはまだ知らない
//        food = Food()
//        food.id = 0
//        food.name = "お気に入り"
//        food.kana = "おきにいり"
//        food.enabled = false
//        arraylist.add(food)

        // アイデア：お気に入りの会社を入れる配列と、そうじゃない配列を用意して、最後に２つをjoinさせたらどうだろうか。そしたらfavorite=trueの会社が一番上にくるし
        food = Company()
        food.id = 1
        food.name = "株式会社ふー"
        food.kana = "かぶしきがいしゃふう"
        arraylist.add(food)

        food = Company()
        food.id = 2
        food.name = "hoge"
        food.kana = "foo"
        arraylist.add(food)

        food = Company()
        food.id = 3
        food.name = "まじですかい有限会社"
        food.kana = "まじですかいゆうげんがいしゃ"

        arraylist.add(food)

        food = Company()
        food.id = 4
        food.name = "株式会社bar"
        food.kana = "かぶしきがいしゃばあ"
        arraylist.add(food)
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
