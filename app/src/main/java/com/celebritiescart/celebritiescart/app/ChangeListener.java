package com.celebritiescart.celebritiescart.app;

public class ChangeListener {
    boolean boo = false;

    public ChangeListener(boolean b){
        boo = b;
    }

    private listener l = null;

    public interface listener{
        void onChange(boolean b);
    }

    public void setChangeListener(listener mListener){
        l = mListener;
    }

    public void somethingChanged(){
        if(l != null){
            l.onChange(boo);
        }
    }


}