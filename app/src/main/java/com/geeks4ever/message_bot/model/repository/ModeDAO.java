package com.geeks4ever.message_bot.model.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.geeks4ever.message_bot.model.ModeDBModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

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

@Dao
public interface ModeDAO {

    @Query("Select * from modeDBModel where `key`= 0")
    LiveData<List<ModeDBModel>> getModeList();

    @Insert (onConflict = REPLACE)
    void setModeList(ModeDBModel mode);

}
