package com.geeks4ever.message_bot.model.adaptors;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.geeks4ever.message_bot.R;
import com.geeks4ever.message_bot.model.ModeModel;
import com.geeks4ever.message_bot.view.HomePage;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

/*
 * Created by Praveen Kumar for Message-Bot.
 * Copyright (c) 2021.
 * Last modified on 21/3/21 6:52 PM.
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

public class ModeListAdaptor extends RecyclerView.Adapter<ModeListAdaptor.ViewHolder> {

    private ArrayList<ModeModel> list;
    private Context context;
    private HomePage homePage;

    public ModeListAdaptor(HomePage homePage){
        this.list = new ArrayList<>();
        this.context = homePage;
        this.homePage = homePage;

    }

    public void updateList(ArrayList<ModeModel> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mode_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position == 0){
            holder.root.setBackground(ContextCompat.getDrawable(context,R.drawable.round_rect_lite_active));
            holder.switchMaterial.setClickable(false);
            holder.switchMaterial.setChecked(true);
            holder.title.setText(list.get(0).title);
            holder.description.setText(list.get(0).Description);
        }
        else {
            holder.root.setBackground(ContextCompat.getDrawable(context,R.drawable.round_rect_lite_grey));
            holder.switchMaterial.setClickable(true);
            holder.switchMaterial.setChecked(false);
            holder.title.setText(list.get(position).title);
            holder.description.setText(list.get(position).Description);
            holder.switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) homePage.moveToTop(position);
                }
            });
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] colors = {"Edit", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0)
                            homePage.replaceItem(position);
                        else
                            homePage.removeItem(position);
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout root;
        public MaterialTextView title, description;
        public SwitchMaterial switchMaterial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.root = itemView.findViewById(R.id.mode_item_root);
            this.title = itemView.findViewById(R.id.mode_item_title);
            this.description = itemView.findViewById(R.id.mode_item_description);
            this.switchMaterial = itemView.findViewById(R.id.mode_item_switch);
        }

    }


}
