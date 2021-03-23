package com.geeks4ever.message_bot;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.geeks4ever.message_bot.model.CallLogModel;

import java.util.ArrayList;
import java.util.List;

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

public class CallReceiver extends BroadcastReceiver {
    private static ContentResolver resolver;
    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                if (state == TelephonyManager.CALL_STATE_IDLE) {

                    new Handler().postDelayed(() -> {
                        List<CallLogModel> callLogDetails = fetch(context, 1, 0);

                        if(callLogDetails.get(0).type.equals("3")){

                            Intent i = new Intent(context, SmsService.class);
                            i.putExtra("number", callLogDetails.get(0).number);
                            context.startService(i);
                        }

                    }, 200);

                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static List<CallLogModel> fetch(Context context, int limit, int offset) {
        resolver = context.getContentResolver();
        ArrayList<CallLogModel> callLogModelLongSparseArray = new ArrayList<>();
        // Create a new cursor and go to the first position
        Cursor cursor = createCursor(limit, offset);
        cursor.moveToFirst();
        // Get the column indexes
        int idxNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int idxType = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int idxID = cursor.getColumnIndex(CallLog.Calls._ID);

        // Map the columns to the fields of the contact
        while (!cursor.isAfterLast()) {

            CallLogModel callLogModel = new CallLogModel();
            // Get data using cursor.getString(index) and map it to callLogModel object
            callLogModel.type = cursor.getString(idxType);
            callLogModel.number = cursor.getString(idxNumber);
            callLogModel.id = cursor.getString(idxID);


            // Add the contact to the collection
            callLogModelLongSparseArray.add(callLogModel);


            cursor.moveToNext();
        }
        // Close the cursor
        cursor.close();

        return callLogModelLongSparseArray;
    }

    private static Cursor createCursor(int limit, int offset) {

        String sortOrder = CallLog.Calls.DATE + " DESC limit " + limit + " offset " + offset;

        return resolver.query(
                CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls.NUMBER,
                        CallLog.Calls.TYPE,
                        CallLog.Calls.DATE,
                        CallLog.Calls._ID},
                null,
                null,
                sortOrder
        );
    }

}