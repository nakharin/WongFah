package com.nakharin.wongfah.manager;

import android.content.Context;

import com.nakharin.wongfah.Contextor;
import com.nakharin.wongfah.network.model.JsonCategory;
import com.nakharin.wongfah.network.model.JsonMenu;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CategoryManager {

    private static CategoryManager instance = null;

    public static  CategoryManager getInstance() {
        if (instance == null)
            instance = new CategoryManager();
        return instance;
    }

    private Context mContext;

    private CategoryManager() {
        mContext = Contextor.getInstance().getContext();
    }


    private ArrayList<JsonCategory> categoryList = new ArrayList<>();

    public ArrayList<JsonCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<JsonCategory> categoryList) {
        this.categoryList.addAll(categoryList);
    }

    public ArrayList<JsonMenu> getMenuList(int index) {
        return categoryList.get(index).getMenus();
    }
}
