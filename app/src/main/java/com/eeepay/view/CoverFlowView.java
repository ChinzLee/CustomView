package com.eeepay.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class CoverFlowView extends RecyclerView {


    public CoverFlowView(Context context) {
        super(context);
    }

    public CoverFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverFlowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return true;
    }

}
