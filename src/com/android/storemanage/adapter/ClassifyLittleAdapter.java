package com.android.storemanage.adapter;

import java.util.List;

import com.android.storemanage.R;
import com.android.storemanage.entity.CategoryEntity;
import com.android.storemanage.utils.JFConfig;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassifyLittleAdapter extends BaseAdapter {
	private Context mContext;
	private List<CategoryEntity> lists;

	public ClassifyLittleAdapter() {
		super();
	}

	public ClassifyLittleAdapter(Context mContext, List<CategoryEntity> lists) {
		super();
		this.mContext = mContext;
		this.lists = lists;
	}
	

	/**
	 * @param lists the lists to set
	 */
	public void setLists(List<CategoryEntity> lists) {
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return null != lists ? lists.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = View.inflate(mContext, R.layout.classify_little_item, null);
			holder = new ViewHolder();
			holder.tView = (TextView) convertView.findViewById(R.id.tv_classify_name);
			holder.isNew = (ImageView) convertView.findViewById(R.id.iv_class_new);
			holder.ivImageView = (ImageView) convertView.findViewById(R.id.iv_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CategoryEntity entity = lists.get(position);
		if(null != entity){
			holder.tView.setText(entity.getCategoryTitle());
			Picasso.with(mContext)
			.load(JFConfig.IMA_URL + entity.getCategoryLittleimgpath()).placeholder(R.drawable.img_empty)
			.into(holder.ivImageView);
			if (!TextUtils.isEmpty(entity.getCategorySfnew())) {
				if ("1".equals(entity.getCategorySfnew())) {
//					holder.ivIsNew.setVisibility(View.VISIBLE);
					if (entity.getDbOpptime() == Long.parseLong(entity.getCategoryOpptime())) {
						holder.isNew.setVisibility(View.INVISIBLE);
					} else {
						holder.isNew.setVisibility(View.VISIBLE);
					}
				} else {
					holder.isNew.setVisibility(View.INVISIBLE);
				}
			} else {
				holder.isNew.setVisibility(View.INVISIBLE);
			}
		}
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView ivImageView,isNew;
		TextView tView;
	}
	
//	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}

}
