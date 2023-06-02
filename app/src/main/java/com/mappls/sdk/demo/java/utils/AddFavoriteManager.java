package com.mappls.sdk.demo.java.utils;

import com.mappls.sdk.plugins.places.autocomplete.model.MapplsFavoritePlace;

import java.util.ArrayList;

public class AddFavoriteManager {

    private static final AddFavoriteManager OUR_INSTANCE = new AddFavoriteManager();
    private ArrayList<MapplsFavoritePlace> list = new ArrayList<MapplsFavoritePlace>();

    public static AddFavoriteManager getInstance(){
        return OUR_INSTANCE;
    }

    private AddFavoriteManager(){

    }

    public ArrayList<MapplsFavoritePlace> getList(){
        return this.list;
    }

    public void addToArray(MapplsFavoritePlace value){
        list.add(value);
    }

}
