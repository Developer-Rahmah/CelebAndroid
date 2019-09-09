package com.celebritiescart.celebritiescart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.news_model.all_news.NewsDetails;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;


public class NewsDescription extends Fragment {

    View rootView;
    
    ImageView news_cover;
    TextView news_title, news_date;
    WebView news_description_webView;

    NewsDetails newsDetails;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_description, container, false);
        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }
        

        // Get NewsDetails from bundle arguments
        newsDetails = getArguments().getParcelable("NewsDetails");
    
    
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        SpannableString s = new SpannableString(getString(R.string.news_description));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);


        // Binding Layout Views
        news_cover = rootView.findViewById(R.id.news_cover);
        news_title = rootView.findViewById(R.id.news_title);
        news_date = rootView.findViewById(R.id.news_date);
        news_description_webView = rootView.findViewById(R.id.news_description_webView);


        // Set News Details
        news_title.setText(newsDetails.getNewsName());
        news_date.setText(String.valueOf(newsDetails.getNewsDateAdded()));
    
        
        String description = newsDetails.getNewsDescription();
        String styleSheet = "<style> " +
                                "body{background:#eeeeee; margin:0; padding:0} " +
                                "p{color:#666666;} " +
                                "img{display:inline; height:auto; max-width:100%;}" +
                            "</style>";
    
        news_description_webView.setHorizontalScrollBarEnabled(false);
        news_description_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
        

        Glide
            .with(getContext()).asBitmap()
            .load(newsDetails.getNewsImage())

                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
            .into(news_cover);
        


        return rootView;

    }

}

