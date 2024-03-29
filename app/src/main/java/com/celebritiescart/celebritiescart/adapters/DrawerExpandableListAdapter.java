package com.celebritiescart.celebritiescart.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.helpers.Typefaces;
import com.celebritiescart.celebritiescart.models.drawer_model.Drawer_Items;
import com.celebritiescart.celebritiescart.R;

import java.util.List;
import java.util.Map;


/**
 * DrawerExpandableListAdapter is the adapter class of ExpandableList of NavigationDrawer in MainActivity
 **/

public class DrawerExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Drawer_Items> headerList;
    private Map<Drawer_Items, List<Drawer_Items>> childList;


    public DrawerExpandableListAdapter(Context context, List<Drawer_Items> headerList, Map<Drawer_Items, List<Drawer_Items>> childList) {
        this.context = context;
        this.headerList = headerList;
        this.childList = childList;
    }



    //********** Called when Rendering each Group of the List *********//

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
        Drawer_Items group = headerList.get(groupPosition);

        // Check if an existing View is being Reused, otherwise Inflate the View with custom Layout
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_header_layout, null);
        }


        ImageView headerListStateIcon = convertView.findViewById(R.id.main_drawer_list_state_image);

        TextView sidemenu_count_badge = convertView.findViewById(R.id.sidemenu_count_badge);
        if(group.getCount()!=null){
            sidemenu_count_badge.setText(group.getCount());
            sidemenu_count_badge.setVisibility(View.VISIBLE);
            sidemenu_count_badge.setTypeface(Typeface.createFromAsset(
                    this.context.getAssets(),
                    "font/baskvill_regular.OTF"
            ));
        }

        // set the Image based on Group State
        if (getChildrenCount(groupPosition) > 0) {
            // Group has some number of Child
            headerListStateIcon.setVisibility(View.VISIBLE);

            if (isExpanded) {
                // set Image resource when the Group is Expanded
                headerListStateIcon.setImageResource(R.drawable.ic_chevron_up);
            } else {
                // set Image resource when the Group is Collapsed
                headerListStateIcon.setImageResource(R.drawable.ic_chevron_down);
            }
            
        } else {
            // Group has no Child
            headerListStateIcon.setVisibility(View.GONE);
        }
    
    
        // set the Group Icon
//        ImageView headerListIcon = (ImageView) convertView.findViewById(R.id.main_drawer_list_header_icon);
//        headerListIcon.setImageResource(group.getIcon());
//        headerListIcon.setColorFilter(Color.WHITE);

        // set the Group Title
        TextView headerListText = convertView.findViewById(R.id.main_drawer_list_header_text);
        Typeface font = Typefaces.get(context, "baskvill_regular");



//        headerListText.setTypeface(font, Typeface.BOLD);

        headerListText.setText(group.getTitle());
        headerListText.setTextColor(Color.WHITE);

        
        return convertView;
    }




    //********** Called when Rendering each Child of a given Group of the List *********//

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        
        Drawer_Items child = childList.get(this.headerList.get(groupPosition)).get(childPosition);

        // Check if an existing View is being Reused, otherwise Inflate the View with custom Layout
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_list_child_layout, null);
        }
    
    
        // set the Child Icon
        ImageView childListIcon = convertView.findViewById(R.id.main_drawer_list_child_icon);
        childListIcon.setImageResource(child.getIcon());
        childListIcon.setColorFilter(Color.WHITE);


        // set the Child Title
        TextView childListText = convertView.findViewById(R.id.main_drawer_list_child_text);
        Typeface font = Typefaces.get(context, "baskvill_regular");;

        childListText.setTypeface(font, Typeface.NORMAL);
        childListText.setText(child.getTitle());
        childListText.setTextColor(Color.WHITE);

        
        return convertView;
    }



    //********** Returns the total number of Groups *********//

    @Override
    public int getGroupCount() {
        return this.headerList.size();
    }



    //********** Returns the ID of a Group at the given Position *********//

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }



    //********** Returns the Data of a specified Group at the given Position *********//

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerList.get(groupPosition);
    }



    //********** Returns the number of Children in a specified Group at the given Position *********//

    @Override
    public int getChildrenCount(int groupPosition) {
        if (childList.get(headerList.get(groupPosition)) == null){
            return 0;
        } else {
            return childList.get(this.headerList.get(groupPosition)).size();
        }
    }



    //********** Returns the ID of specified Child at given Position within the specified Group at the given Position *********//

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }



    //********** Returns the Data of specified Child at the given Position within the specified Group at the given Position *********//

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childList.get(this.headerList.get(groupPosition)).get(childPosition);
    }



    //********** Checks whether the Child and Group ID’s are Stable across changes to the underlying Data *********//

    @Override
    public boolean hasStableIds() {
        return false;
    }



    //********** Checks whether the Child at the specified Position is Selectable or Not *********//

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
