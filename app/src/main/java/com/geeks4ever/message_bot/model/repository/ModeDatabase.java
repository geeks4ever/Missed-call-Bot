package com.geeks4ever.message_bot.model.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.geeks4ever.message_bot.model.ModeDBModel;
import com.geeks4ever.message_bot.model.convertors;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 1:32 PM.
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

@Database(entities = {ModeDBModel.class}, version = 1, exportSchema = false)
@TypeConverters(convertors.class)
public abstract class ModeDatabase extends RoomDatabase {

    private static ModeDatabase db;

    public abstract ModeDAO modeDAO();

    public static synchronized ModeDatabase getInstance(Context context){

        if(db == null){
            db = Room.databaseBuilder(context.getApplicationContext(),
                    ModeDatabase.class, "mode_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }


}
