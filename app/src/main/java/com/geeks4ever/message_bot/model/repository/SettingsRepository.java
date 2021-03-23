package com.geeks4ever.message_bot.model.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 6:16 PM.
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

public class SettingsRepository {

    private static SettingsRepository settingsRepository;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private MutableLiveData<Boolean> isOn = new MutableLiveData<>();
    private MutableLiveData<Boolean> darkMode = new MutableLiveData<>();
    private MutableLiveData<Boolean> startWithBoot = new MutableLiveData<>();

    private SettingsRepository(Context context) {

        sharedPreferences = context.getSharedPreferences("settings", 0);
        editor = sharedPreferences.edit();

        updateVariables();
    }

    public static SettingsRepository getInstance(Context context){

        if(settingsRepository == null)
            settingsRepository = new SettingsRepository(context);
        return settingsRepository;
    }

    private void updateVariables(){
        isOn.setValue(sharedPreferences.getBoolean("isOn", false));
        darkMode.setValue(sharedPreferences.getBoolean("darkMode", false));
        startWithBoot.setValue(sharedPreferences.getBoolean("startWithBoot", false));
    }

    public LiveData<Boolean> getIsOn() {
        return isOn;
    }

    private void updateSharedPreference(String key, Boolean value){
        editor.putBoolean(key, value);
        editor.commit();
        updateVariables();
    }

    public void setCurrentMessage(String msg){
        editor.putString("currentMessage", msg);
        editor.commit();
    }

    public void setIsOn(boolean isOn) {
        updateSharedPreference("isOn", isOn);
    }

    public LiveData<Boolean> getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        updateSharedPreference("darkMode", darkMode);
    }

    public LiveData<Boolean> getStartWithBoot() {
        return startWithBoot;
    }

    public void setStartWithBoot(boolean startWithBoot) {
        updateSharedPreference("startWithBoot", startWithBoot);
    }

}
