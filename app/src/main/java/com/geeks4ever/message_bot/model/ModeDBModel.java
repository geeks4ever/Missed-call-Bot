package com.geeks4ever.message_bot.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

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

@Entity(tableName = "modeDBModel")
@TypeConverters(convertors.class)
public class ModeDBModel {

    @PrimaryKey
    public int key;

    @ColumnInfo(name = "ModeArrayList")
    public ArrayList<ModeModel> ModeList;

    public ModeDBModel(int key, ArrayList<ModeModel> ModeList) {
        this.key = key;
        this.ModeList = ModeList;
    }
}
