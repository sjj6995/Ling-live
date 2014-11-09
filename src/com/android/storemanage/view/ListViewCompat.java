package com.android.storemanage.view;


import com.android.storemanage.entity.UserPrizeEntity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ListViewCompat extends ListView {

	private SlideView mFocusedItemView;

	public ListViewCompat(Context context) {
		super(context);
	}

    public ListViewCompat(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ListViewCompat(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            int x = (int) event.getX();
            int y = (int) event.getY();
            //我们想知道当前点击了哪一行
            int position = pointToPosition(x, y);
//            Log.e(TAG, "postion=" + position);
            if (position != INVALID_POSITION) {
                //得到当前点击行的数据从而取出当前行的item。
                //可能有人怀疑，为什么要这么干？为什么不用getChildAt(position)？
                //因为ListView会进行缓存，如果你不这么干，有些行的view你是得不到的。
            	UserPrizeEntity data = (UserPrizeEntity) getItemAtPosition(position);
                mFocusedItemView = data.slideView;
//                Log.e(TAG, "FocusedItemView=" + mFocusedItemView);
            }
        }
        default:
            break;
        }

        //向当前点击的view发送滑动事件请求，其实就是向SlideView发请求
        if (mFocusedItemView != null) {
            mFocusedItemView.onRequireTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }
}
