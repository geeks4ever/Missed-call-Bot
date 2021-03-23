package com.geeks4ever.message_bot.viewModel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.geeks4ever.message_bot.model.ModeDBModel;
import com.geeks4ever.message_bot.model.ModeModel;
import com.geeks4ever.message_bot.model.repository.ModeRepository;
import com.geeks4ever.message_bot.model.repository.SettingsRepository;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 7:41 PM.
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

public class MainViewModel extends AndroidViewModel {

    private ModeRepository repository;
    private SettingsRepository settingsRepository;
    private Context context;

    final MutableLiveData<ArrayList<ModeModel>> list = new MutableLiveData<>();

    private MutableLiveData<Boolean> isOn = new MutableLiveData<>();
    private MutableLiveData<Boolean> darkMode = new MutableLiveData<>();
    private MutableLiveData<Boolean> startWithBoot = new MutableLiveData<>();


    //_______________________________________   Constructor   ______________________________________

    public MainViewModel(@NonNull Application application) {
        super(application);

        context = application;
        repository = ModeRepository.getInstance(application);

        settingsRepository = SettingsRepository.getInstance(application);

        repository.getModeList().observeForever(new Observer<List<ModeDBModel>>() {
            @Override
            public void onChanged(List<ModeDBModel> modeDBModels) {
                if(!modeDBModels.isEmpty()) {
                    list.setValue(modeDBModels.get(0).ModeList);
                    if(!modeDBModels.get(0).ModeList.isEmpty())
                        settingsRepository.setCurrentMessage(modeDBModels.get(0).ModeList.get(0).Description);
                }
            }
        });
        settingsRepository.getIsOn().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isOn.setValue(aBoolean);
            }
        });
        settingsRepository.getDarkMode().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                darkMode.setValue(aBoolean);
            }
        });
        settingsRepository.getStartWithBoot().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startWithBoot.setValue(aBoolean);
            }
        });

    }


    //_______________________________________    Methods    ________________________________________

    public void toggleOnOff(){

        if(list.getValue() == null || list.getValue().isEmpty()){
            setIsOn(false);
            Toast.makeText(context, "Add a mode to enable the service", Toast.LENGTH_SHORT).show();
            return;
        }
        setIsOn(!isOn.getValue());
    }

    public void toggleDarkMode(){
        setDarkMode(!darkMode.getValue());
    }

    public void toggleStartWithBoot(){
        setStartWithBoot(!startWithBoot.getValue());
    }

    public ArrayList<String> getItemDetails(int position){
        return repository.getItemDetails(position);
    }

    public void moveItemToTop(int position){
        repository.moveItemToTop(position);
    }

    public void removeItem(int position){
        repository.removeItem(position);
    }

















    //__________________________________   Getters and Setters   ___________________________________

    public LiveData<ArrayList<ModeModel>> getModeList(){
        return list;
    }

    public LiveData<Boolean> getIsOn(){
        return isOn;
    }

    public LiveData<Boolean> getDarkMode(){
        return darkMode;
    }

    public LiveData<Boolean> getStartWithBoot(){
        return startWithBoot;
    }

    public void setIsOn(boolean isOn){
        settingsRepository.setIsOn(isOn);
    }

    public void setDarkMode(boolean darkMode){
        settingsRepository.setDarkMode(darkMode);
    }

    public void setStartWithBoot(boolean startWithBoot){
        settingsRepository.setStartWithBoot(startWithBoot);
    }

    public void replaceItem(int position, String title, String Description){

        repository.replaceItem(position, title, Description);

    }


    public void addToModesList(String title, String Description){

        repository.addToList(title, Description);

    }

}
