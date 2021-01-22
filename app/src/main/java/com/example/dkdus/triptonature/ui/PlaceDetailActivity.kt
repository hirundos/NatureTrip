package com.example.dkdus.triptonature.ui

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.database.AppDatabase
import com.example.dkdus.triptonature.database.ItemDao
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.ui.login.RegistActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PlaceDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var placeItem : Item
    lateinit var db : AppDatabase
    var checkStar = false
    lateinit var itemDao : ItemDao
    var lat : Double = 37.5666102
    var lot : Double = 126.9783881

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        db = Room.databaseBuilder(application, AppDatabase::class.java, "place-db")
            .build()
        itemDao = db.itemDao()

        if(intent.hasExtra("placeData")){
            placeItem = intent.getParcelableExtra<Item>("placeData")
        }

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

        GeoAddr()
        checkDuplicate(placeItem)
        putData()
        buttonAction()

    }

    //주소 -> 좌표 변환
    private fun GeoAddr(){
        var geocode = Geocoder(this)
        var addrList : List<Address> = mutableListOf()
        addrList = geocode.getFromLocationName(placeItem.addr, 10)
        if(addrList.isEmpty()){
            Log.d("emptyAddr", "해당 주소는 없습니다")
        } else{
            var addr : Address = addrList[0]
            lat = addr.latitude
            lot = addr.longitude
        }
    }

    private fun buttonAction() {
        btn_call.setOnClickListener {
            dialPhoneNumber(placeItem.tel.toString())
        }
        btn_star.setOnClickListener{
            insertData(placeItem)
        }
        btn_talk.setOnClickListener {
            val intent = Intent(this, CommentActivity::class.java)
            intent.putExtra("PlaceId",placeItem.title)
            startActivity(intent)
        }
    }

    //관광지 정보 보여주기
    private fun putData(){
        detail_title.text = placeItem.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detail_summary.text = Html.fromHtml(placeItem.summary, Html.FROM_HTML_MODE_LEGACY)
        }else{
            detail_summary.text = Html.fromHtml(placeItem.summary)
        }
        if(placeItem.mainimage!= null)
            Glide.with(this).load(placeItem.mainimage).into(detail_image)

        detail_loc.text = placeItem.addr
        hit.text = "조회수 : "+placeItem.readcount.toString()+"회 "
    }

    //즐겨찾기 된 장소였는지 확인
    private fun checkDuplicate(item: Item){
        GlobalScope.launch(Dispatchers.IO) {
            if(itemDao.exist(item.title.toString())){
                checkStar = true
                placeItem.id = itemDao.getId()
                btn_star.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.ic_baseline_star_24,
                    0,
                    0
                )

            }
        }

    }
//여행지 즐겨찾기 추가 기능
    private fun insertData(item: Item){
        if (!checkStar) {
            GlobalScope.launch(Dispatchers.IO) {
                itemDao.insert(item)
                checkStar = true
            }
            btn_star.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.ic_baseline_star_24,
                0,
                0
            )
            Toast.makeText(this,"내 여행지에 추가되었습니다.",Toast.LENGTH_SHORT).show()
        }else{
            GlobalScope.launch(Dispatchers.IO) {
                itemDao.delete(item)
            }
            btn_star.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.ic_baseline_star_border_24,
                0,
                0
            )
            finish()
        }
    }

    //전화 걸기
    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    //해당 위치를 지도에 표시하는 기능
    override fun onMapReady(p0: NaverMap) {
        val marker = Marker()
        p0.cameraPosition = CameraPosition(LatLng(lat, lot), 16.0)
        marker.position = LatLng(lat, lot)
        marker.map = p0
    }


}