package com.celebritiescart.celebritiescart.customs;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * EndlessRecyclerViewScroll will fetch new data when we reach the bottom by scrolling down the RecyclerView (Infinite_Scrolling)
 **/

public abstract class EndlessRecyclerViewScroll extends RecyclerView.OnScrollListener {

    private View bottomBar = null;
    private View endMsg = null;

    private boolean loading = true;             // True if we are still waiting for the last set of data to load
    private int current_page = 0;               // The current offset index of data you have loaded
    private int previousTotal = 0;              // The total number of items in the RecyclerView after the last load
    private int totalItemCount;                 // The total number of items in the RecyclerView
    private int visibleItemCount;               // The total number of visible items in the RecyclerView
    private int firstVisibleItem;               // The current position of first visible item in the total visible items
    private int visibleThreshold;               // The minimum amount of items to have below your current scroll position before loading more


    public EndlessRecyclerViewScroll() {
    }

    public EndlessRecyclerViewScroll(View bottomBar) {
        this.bottomBar = bottomBar;
    }
    public EndlessRecyclerViewScroll(View bottomBar,View endMsg) {
        this.bottomBar = bottomBar;
        this.endMsg=endMsg;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        if (dy > 0) {
            try {
                endMsg.setVisibility(View.GONE);
            }
            catch (Exception e){

            }
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = recyclerView.getLayoutManager().getItemCount();
            visibleThreshold = 1;
//            visibleThreshold = ((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount();
            firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            int lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();


            // If it's still loading, and the RecyclerView count has changed
            try {


                if (loading && (totalItemCount > previousTotal)) {
                    endMsg.setVisibility(View.GONE);

                    previousTotal = totalItemCount;
                    loading = false;
                }
            }
            catch (Exception e){

            }

            if (!loading &&  lastVisibleItem == totalItemCount-1) {
                try {


                    // End has been reached
                    endMsg.setVisibility(View.GONE);
                }catch (Exception e){

                }
                loading = true;
                current_page++;
    
                recyclerView.scrollToPosition(lastVisibleItem);

                onLoadMore(current_page);

                if(recyclerView.getChildCount()>=totalItemCount) {
                    try {


                        endMsg.setVisibility(View.GONE);
                    }
                    catch (Exception e){

                    }
                }else {
//                    endMsg.setVisibility(View.VISIBLE);

                }
//                endMsg.setVisibility(View.GONE);
            }
            else {
                try {


                    endMsg.setVisibility(View.GONE);
                }catch (Exception e){

                }

//                endMsg.setVisibility(View.VISIBLE);
            }
    
    
            if (bottomBar != null) {
                // Animate the loading view to 0% opacity. After the animation ends,
                // set its visibility to GONE as an optimization step (it won't participate in layout passes, etc.)
                bottomBar.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                bottomBar.setVisibility(View.GONE);

                            }
                        });
            }else {
                try {


                    endMsg.setVisibility(View.GONE);
                } catch (Exception e) {

                }
                try {


                    if (recyclerView.getChildCount() >= totalItemCount) {

                        endMsg.setVisibility(View.GONE);
                    } else {
                        endMsg.setVisibility(View.VISIBLE);

                    }
                }catch (Exception e){

                }
            }
            try {


                if (lastVisibleItem == totalItemCount - 1) {
//                endMsg.setAlpha(1f);
                    if (recyclerView.getChildCount() >= totalItemCount) {

                        endMsg.setVisibility(View.GONE);
                    } else {
                        endMsg.setVisibility(View.VISIBLE);

                    }
                } else {
                    endMsg.setVisibility(View.GONE);

//                endMsg.setAlpha(0f);

                }
            }catch (Exception e){

            }
            if(recyclerView.getChildCount()>=totalItemCount) {
                try {


                    endMsg.setVisibility(View.GONE);
                }catch (Exception e){

                }
            }else {
//                endMsg.setVisibility(View.VISIBLE);

            }

        } else if (dy < 0) {
            try {


                endMsg.setVisibility(View.GONE);
            }catch (Exception e){

            }
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = recyclerView.getLayoutManager().getItemCount();
            visibleThreshold = 1;
//            visibleThreshold = ((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount();
            firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            int lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();





            if( lastVisibleItem == totalItemCount-1) {
//                endMsg.setAlpha(1f);
            }else{
//                endMsg.setAlpha(0f);
                try {


                    endMsg.setVisibility(View.GONE);
                }catch (Exception e){

                }


            }
            if (bottomBar != null) {
                try {


                    endMsg.setVisibility(View.GONE);
                }catch (Exception e){

                }
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                bottomBar.setAlpha(0f);
//                endMsg.setAlpha(0f);

                bottomBar.setVisibility(View.VISIBLE);
                try {


                    endMsg.setVisibility(View.GONE);
                    if (recyclerView.getChildCount() >= totalItemCount) {
                        endMsg.setVisibility(View.GONE);
                    } else {
                        endMsg.setVisibility(View.GONE);

                    }
                }catch (Exception e){

                }
                // Animate the content view to 100% opacity, and clear any animation listener set on the view.
                bottomBar.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .setListener(null);
            }else {
                try {


                    endMsg.setVisibility(View.GONE);

                    if (recyclerView.getChildCount() >= totalItemCount) {
                        endMsg.setVisibility(View.GONE);
                    } else {
                        endMsg.setVisibility(View.VISIBLE);

                    }
                }catch (Exception e){

                }
            }
            
        }else {
            try {
                endMsg.setVisibility(View.GONE);

            }
catch (Exception e){

}
            System.out.print(dy);
            try {

                if(recyclerView.getChildCount()>=totalItemCount) {

                    endMsg.setVisibility(View.GONE);
            }else {
                endMsg.setVisibility(View.VISIBLE);

            }

        }catch (Exception e){

            }

        }
        
    }


    public abstract void onLoadMore(int current_page);

}

