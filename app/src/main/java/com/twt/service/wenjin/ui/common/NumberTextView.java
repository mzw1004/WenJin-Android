package com.twt.service.wenjin.ui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twt.service.wenjin.R;

/**
 * Created by M on 2015/4/10.
 */
public class NumberTextView extends LinearLayout {

    private TextView tvNumber;
    private TextView tvText;
    private String text;

    public NumberTextView(Context context) {
        super(context);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.textview_with_number, this);
        tvNumber = (TextView) findViewById(R.id.custom_tv_number);
        tvText = (TextView) findViewById(R.id.custom_tv_text);

        setupAttributes(attrs);

        tvText.setText(text);
    }

    private void setupAttributes(AttributeSet attrs) {
        // Obtain a typed array of attributes
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.NumberTextView, 0, 0);
        // Extract custom attributes into member variables
        try {
            text = a.getString(R.styleable.NumberTextView_ntvText);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setNumber(int number) {
        if (number > 10000) {
            number /= 10000;
            tvNumber.setText(number + "W");
        } else if (number > 1000) {
            number /= 1000;
            tvNumber.setText(number + "K");
        } else {
            tvNumber.setText(number + "");
        }
    }

    public void setTvText(String text) {
        this.text = text;
        tvText.setText(text);
    }
}
