package com.geeks4ever.message_bot;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 7:59 PM.
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

public class SmsService extends Service {

    boolean flag;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        flag = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences sp = getApplicationContext().getSharedPreferences("settings", 0);

        if(flag && sp.getBoolean("isOn", false)){

            String phoneNumber = intent.getExtras().getString("number");
            String message = sp.getString("currentMessage", "error");
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            flag = false;
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
