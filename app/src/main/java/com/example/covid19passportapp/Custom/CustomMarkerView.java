package com.example.covid19passportapp.Custom;

import android.content.Context;
import android.widget.TextView;

import com.example.covid19passportapp.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import org.joda.time.DateTime;

public class CustomMarkerView extends MarkerView {
    private TextView markerTextView;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        // find your layout components
        markerTextView = (TextView) findViewById(R.id.markerTextView);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        DateTime d = new DateTime((long) e.getX());
        markerTextView.setText("" + e.getY() + ", " + d.toString("MMM d, ''yy"));

        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}
