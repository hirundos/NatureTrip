package com.example.dkdus.triptonature.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.adapter.MyRecyclerAdapter
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.ui.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    var datalist: MutableList<Item> = mutableListOf()
    lateinit var viewModel: HomeViewModel
    var page : Int = 1
    val maxPage = 224
    var areaCode = 0
    lateinit var arrayAdapter : ArrayAdapter<String>
    var areaCodeList = mutableListOf<Int>()
    var areaNameList = mutableListOf<String>()

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
        (homeRecyclerview.adapter as MyRecyclerAdapter).setLoadingView(true)

        spinnerFun()
        callPlace()
        checkScroll()
    }

    private fun spinnerFun(){
        areaSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position < 9)
                    areaCode = position
                else
                    areaCode = position + 22
                if(areaCode > 0){
                    callArea(areaCode)
                    arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, areaNameList)
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    detailSearch.adapter = arrayAdapter
                    detailSearch.setSelection(0)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        detailSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("####", position.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun callPlace(){
        (homeRecyclerview.adapter as MyRecyclerAdapter).setLoadingView(false)
        viewModel.callPlace(page).observe(viewLifecycleOwner, Observer {
            (homeRecyclerview.adapter as MyRecyclerAdapter).addPost(it)
            (homeRecyclerview.adapter as MyRecyclerAdapter).setLoadingView(true)
        })
    }

    private fun callArea(areaCode : Int){
        areaCodeList.clear()
        areaNameList.clear()

        areaCodeList.add(0)
        areaNameList.add("선택")
        viewModel.callArea(areaCode).observe(viewLifecycleOwner, Observer {
            var arr = it.item
            for(i in arr.indices){
                areaCodeList.add(it.item[i].code)
                areaNameList.add(it.item[i].name)
            }
        })
    }

    private fun checkScroll() {
        homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!homeRecyclerview.canScrollVertically(1) && hasPage()){
                    page++;
                    callPlace()
                }
            }
        })
    }

    private fun hasPage(): Boolean {
        if(page <= maxPage)
            return true
        return false
    }


}
