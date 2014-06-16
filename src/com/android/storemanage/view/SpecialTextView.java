package com.android.storemanage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SpecialTextView extends TextView{

	public SpecialTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SpecialTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SpecialTextView(Context context) {
		super(context);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
