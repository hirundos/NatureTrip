package com.example.dkdus.triptonature.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.adapter.MyRecyclerAdapter
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.ui.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    var datalist: MutableList<Item> = mutableListOf()
    lateinit var viewModel: HomeViewModel
    var siAreaCode = 0
    var guAreaCode = 0
    lateinit var arrayAdapter : ArrayAdapter<String>
    var areaCodeList = mutableListOf<Int>()
    var areaNameList = mutableListOf<String>()
    var sortSelect = arrayListOf<String>("제목순","조회순","수정일순","생성일순")
    var sortTag = arrayListOf<String>("A","B","C","D")
    var sortCode : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeRecyclerview.layoutManager = LinearLayoutManager(context)

        val myAdapter = MyRecyclerAdapter(this, datalist) { item ->
            val intent = Intent(context, PlaceDetailActivity::class.java)
            intent.putExtra("placeData", item)
            startActivity(intent)
        }
        homeRecyclerview.adapter = myAdapter

        textSort()
        searchBtn.setOnClickListener{
            searchPlace()
        }
        spinnerFun()
        callPlace()
    }

    private fun textSort(){
        sortText.setOnClickListener {
            sortCode++
            if(sortCode % 4 == 0)
                sortCode = 0
            sortText.text = sortSelect[sortCode]
            searchPlace()
        }
    }

    private fun spinnerFun(){
        areaSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position < 9)
                    siAreaCode = position
                else
                    siAreaCode = position + 22
                if(siAreaCode > 0){
                    callArea(siAreaCode)
                    arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, areaNameList)
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    detailSearch.adapter = arrayAdapter
                    guAreaCode = 0
                    detailSearch.setSelection(0)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        detailSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                guAreaCode = areaCodeList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun searchPlace(){
        (homeRecyclerview.adapter as MyRecyclerAdapter).clear()
        datalist.clear()
        callPlace()
    }

    private fun callPlace(){
        viewModel.callPlace(siAreaCode, guAreaCode, sortTag[sortCode]).observe(viewLifecycleOwner, Observer {
            (homeRecyclerview.adapter as MyRecyclerAdapter).addPost(it)
        })
    }

    private fun callArea(areaCode : Int){
        areaCodeList.clear()
        areaNameList.clear()

        areaCodeList.add(0)
        areaNameList.add("구 선택")
        viewModel.callArea(areaCode).observe(viewLifecycleOwner, Observer {
            var arr = it.item
            for(i in arr.indices){
                areaCodeList.add(it.item[i].code)
                areaNameList.add(it.item[i].name)
            }
        })
    }

}
