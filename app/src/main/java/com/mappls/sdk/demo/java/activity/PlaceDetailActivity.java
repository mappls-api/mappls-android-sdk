package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mappls.sdk.demo.databinding.ActivityPlaceDetailBinding;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.adapter.PlaceDetailAdapter;
import com.mappls.sdk.demo.java.model.PlaceDetailModel;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetail;
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetailManager;
import com.mappls.sdk.services.api.placedetail.model.PlaceDetailResponse;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailActivity extends AppCompatActivity {

    ActivityPlaceDetailBinding mBinding;
    private PlaceDetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place_detail);
        adapter = new PlaceDetailAdapter();
        mBinding.rvPlaceDetail.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvPlaceDetail.setAdapter(adapter);
        mBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapplsPin = mBinding.etMapplsPin.getText().toString();
                if(!mapplsPin.isEmpty()) {
                    callPlaceDetail(mapplsPin);
                } else {
                    Toast.makeText(PlaceDetailActivity.this, "Please add Mappls Pin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callPlaceDetail(String mapplsPin) {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        MapplsPlaceDetail placeDetail = MapplsPlaceDetail.builder()
                .mapplsPin(mapplsPin)
                .build();
        MapplsPlaceDetailManager.newInstance(placeDetail).call(new OnResponseCallback<PlaceDetailResponse>() {
            @Override
            public void onSuccess(PlaceDetailResponse placeDetailResponse) {
                if(placeDetailResponse != null) {
                    setValues(placeDetailResponse);
                } else {
                    Toast.makeText(PlaceDetailActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(PlaceDetailActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setValues(PlaceDetailResponse placeDetailResponse) {
        List<PlaceDetailModel> placeDetailModels = new ArrayList<>();
        addItem(placeDetailModels,"Mappls Pin", placeDetailResponse.getMapplsPin());
        addItem(placeDetailModels,"Latitude", placeDetailResponse.getLatitude() + "");
        addItem(placeDetailModels,"Longitude", placeDetailResponse.getLongitude() + "");
        addItem(placeDetailModels,"Place Name", placeDetailResponse.getPlaceName());
        addItem(placeDetailModels,"Place Address", placeDetailResponse.getAddress());
        addItem(placeDetailModels,"City", placeDetailResponse.getCity());
        addItem(placeDetailModels,"House Name", placeDetailResponse.getHouseName());
        addItem(placeDetailModels,"District", placeDetailResponse.getDistrict());
        addItem(placeDetailModels,"House Number", placeDetailResponse.getHouseNumber());
        addItem(placeDetailModels,"Locality", placeDetailResponse.getLocality());
        addItem(placeDetailModels,"Pin code", placeDetailResponse.getPincode());
        addItem(placeDetailModels,"POI", placeDetailResponse.getPoi());
        addItem(placeDetailModels,"State", placeDetailResponse.getState());
        addItem(placeDetailModels,"Street", placeDetailResponse.getStreet());
        addItem(placeDetailModels,"Sub District", placeDetailResponse.getSubDistrict());

        adapter.setPlaceDetailModels(placeDetailModels);
    }

    private void addItem(List<PlaceDetailModel> placeDetailModels, String title, String value) {
        if(value != null) {
            placeDetailModels.add(new PlaceDetailModel(PlaceDetailModel.TYPE_ITEM, title, value));
        }
    }
}