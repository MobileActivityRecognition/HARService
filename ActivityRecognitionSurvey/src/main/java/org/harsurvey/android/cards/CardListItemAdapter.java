/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.harsurvey.android.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.hardroid.common.HumanActivity;
import org.harsurvey.android.util.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * List Item for dialog
 */
public class CardListItemAdapter extends ArrayAdapter<String> {
    List<String> items;

    public CardListItemAdapter(Context context, List<String> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        items = objects;
    }

    public CardListItemAdapter(Context context, String[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        items = Arrays.asList(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = getContext();
        TextView textView = (TextView) super.getView(position, convertView, parent);
        HumanActivity.Type type = HumanActivity.Type.valueOf(items.get(position));
        Drawable image = Constants.getActivityIcon(context, type);
        textView.setText(Constants.getActivityString(context, type));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(image, null, null, null);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null);
        }
        return textView;
    }
}
