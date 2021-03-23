package com.geeks4ever.message_bot.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 5:41 PM.
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

public class convertors {

    static Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<ModeModel> deCode(String data) {

        if (data == null) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<ArrayList<ModeModel>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String enCode(ArrayList<ModeModel> someObjects) {
        return gson.toJson(someObjects);
    }

}
