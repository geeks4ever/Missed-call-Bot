package com.geeks4ever.message_bot.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geeks4ever.message_bot.R;
import com.geeks4ever.message_bot.model.ModeModel;
import com.geeks4ever.message_bot.model.adaptors.ModeListAdaptor;
import com.geeks4ever.message_bot.viewModel.MainViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 7:54 PM.
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

public class HomePage extends AppCompatActivity {

    private MainViewModel viewModel;
    private ModeListAdaptor adaptor;

    FrameLayout onOffButton;
    FrameLayout botIcon;
    MaterialTextView onOffText;
    RecyclerView recyclerView;
    MaterialButton addModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        getPermissions();

        onOffButton = findViewById(R.id.home_page_on_off_button);
        botIcon = findViewById(R.id.home_page_bot_icon);
        onOffText = findViewById(R.id.home_page_on_off_text);
        recyclerView = findViewById(R.id.home_page_modes_recycle_view);
        addModeButton = findViewById(R.id.home_page_add_mode_button);



        adaptor = new ModeListAdaptor(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        viewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(  getApplication()  )).get(MainViewModel.class);

        viewModel.getIsOn().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    on();
                else
                    off();
            }
        });

        viewModel.getDarkMode().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        viewModel.getStartWithBoot().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        onOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toggleOnOff();
            }
        });


        viewModel.getModeList().observeForever(new Observer<ArrayList<ModeModel>>() {
            @Override
            public void onChanged(ArrayList<ModeModel> modeModels) {

                if(modeModels.isEmpty())
                    off();

                adaptor.updateList(modeModels);
            }
        });

        addModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AddModePage.class);
                i.putExtra("position", -1);
                startActivity(i);
            }
        });


    }

    private void getPermissions() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.SEND_SMS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }
                        else{
                            tryAgain();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void tryAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs PHONE, cALL LOG, SMS permissions to work.");
        builder.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPermissions();
            }
        });
        builder.setNegativeButton("CLOSE APP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }


    public void removeItem(int position){
        viewModel.removeItem(position);
    }

    public void replaceItem(int position){
        Intent i = new Intent(getBaseContext(), AddModePage.class);
        i.putExtra("position", position);
        startActivity(i);
    }

    public void moveToTop(int position){
        viewModel.moveItemToTop(position);
        recyclerView.smoothScrollToPosition(0);
    }


    private void on(){
        onOffButton.setBackground(ContextCompat.getDrawable(this,R.drawable.round_rect_on_lite));
        botIcon.setBackground(ContextCompat.getDrawable(this,R.drawable.bot_on));
        onOffText.setText("ON");
        onOffText.setTextColor(getColor(R.color.on_color));
    }


    private void off(){
        onOffButton.setBackground(ContextCompat.getDrawable(this,R.drawable.round_rect_off_lite));
        botIcon.setBackground(ContextCompat.getDrawable(this,R.drawable.bot_off));
        onOffText.setText("OFF");
        onOffText.setTextColor(getColor(R.color.off_color));
    }



    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs PHONE, cALL LOG, SMS permissions to work. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("CLOSE APP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}