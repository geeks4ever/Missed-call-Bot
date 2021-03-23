package com.geeks4ever.message_bot.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.geeks4ever.message_bot.model.ModeDBModel;
import com.geeks4ever.message_bot.model.ModeModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 6:48 PM.
 *
 * This file/part of Message-Bot is OpenSource.
 *
 * Message-Bot is a free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Message-Bot is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Message-Bot.
 * If not, see http://www.gnu.org/licenses/.
 */

public class ModeRepository {

    private static ModeRepository modeRepository;

    private ModeDAO modeDAO;
    private LiveData<List<ModeDBModel>> modeModelLiveData;

    public static ModeRepository getInstance(Application application){
        if(modeRepository == null)
            modeRepository = new ModeRepository(application);
        return modeRepository;
    }

    private ModeRepository(Application application){

        modeDAO = ModeDatabase.getInstance(application).modeDAO();
        modeModelLiveData = modeDAO.getModeList();

    }

    public LiveData<List<ModeDBModel>> getModeList(){
        return modeModelLiveData;
    }

    public void replaceItem(int position, String title, String Description){
        ArrayList<ModeModel> list = new ArrayList<>();
        if(!(modeModelLiveData.getValue() == null || modeModelLiveData.getValue().isEmpty()))
            list = modeModelLiveData.getValue().get(0).ModeList;

        list.set(position, new ModeModel(title,Description) );

        setModeList(list);
    }

    public void addToList(String title, String Description){
        ArrayList<ModeModel> list = new ArrayList<>();
        if(!(modeModelLiveData.getValue() == null || modeModelLiveData.getValue().isEmpty()))
            list = modeModelLiveData.getValue().get(0).ModeList;

        list.add(0, new ModeModel(title,Description));

        setModeList(list);
    }

    public void removeItem(int position){
        ArrayList<ModeModel> list = new ArrayList<>();
        if(!(modeModelLiveData.getValue() == null || modeModelLiveData.getValue().isEmpty()))
            list = modeModelLiveData.getValue().get(0).ModeList;

        list.remove(position);

        setModeList(list);
    }


    public ArrayList<String> getItemDetails(int position){
        ArrayList<String> list = new ArrayList<>();
        list.add(modeModelLiveData.getValue().get(0).ModeList.get(position).title);
        list.add(modeModelLiveData.getValue().get(0).ModeList.get(position).Description);
        return list;
    }



    public void moveItemToTop(int position){

        ArrayList<ModeModel> list = new ArrayList<>();
        if(!(modeModelLiveData.getValue() == null || modeModelLiveData.getValue().isEmpty()))
            list = modeModelLiveData.getValue().get(0).ModeList;

        ModeModel temp = list.get(position);
        list.remove(position);
        list.add(0,temp);

        setModeList(list);
    }

    public void setModeList(ArrayList<ModeModel> modelArrayList){
        new SetModeAsyncTask(modeDAO).execute(new ModeDBModel(0, modelArrayList));
    }

    public String getCurrentModeMessage() {

        return modeModelLiveData.getValue().get(0).ModeList.get(0).Description;
    }


    private static class SetModeAsyncTask extends AsyncTask<ModeDBModel, Void, Void>{

        private ModeDAO modeDAO;

        private SetModeAsyncTask(ModeDAO modeDAO){
            this.modeDAO = modeDAO;
        }

        @Override
        protected Void doInBackground(ModeDBModel... modeDBs) {
            modeDAO.setModeList(modeDBs[0]);
            return null;
        }
    }



}
