package com.geeks4ever.message_bot.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.geeks4ever.message_bot.R;
import com.geeks4ever.message_bot.viewModel.MainViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 6:50 PM.
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

public class AddModePage extends AppCompatActivity {


    private MainViewModel viewModel;

    private TextInputEditText title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mode_page);

        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");

        title = findViewById(R.id.add_mode_page_title_edit_text);
        description = findViewById(R.id.add_mode_page_description_edit_text);


        viewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        MaterialButton addButton = findViewById(R.id.add_mode_page_add_button);

        if(position > -1){
            title.setText(viewModel.getItemDetails(position).get(0));
            description.setText(viewModel.getItemDetails(position).get(1));
            addButton.setText("update");
        }


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.getText() == null || description.getText() == null ||
                        title.getText().toString().equals("") || description.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Please fill both the fields!", Toast.LENGTH_SHORT).show();
                else if (position > -1){
                    viewModel.replaceItem(position, title.getText().toString(), description.getText().toString());
                    finish();
                }
                else {
                    viewModel.addToModesList(title.getText().toString(), description.getText().toString());
                    finish();
                }

            }
        });

    }
}