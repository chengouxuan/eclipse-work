package com.example.ucmhomedemo21.common;


import com.example.ucmhomedemo21.GlobalViewIds;
import com.example.ucmhomedemo21.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;


public class AddressBarLayout extends RelativeLayout {
	

	public AddressBarLayout(Context context) {
		super(context);
		this.setupViews();
	}
	
	public AddressBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}
	
	public AddressBarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupViews();
	}
	
	private void setupViews() {
		
		final int addressBarStarButtonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_STAR_BUTTON);
		final int addressBarEditTextSearchId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_EDIT_TEXT_SEARCH);
		final int addressBarEditTextId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_EDIT_TEXT);
		final int addressBarRightLineId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_RIGHT_LINE);
		final int addressBarLeftLineId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_LEFT_LINE);
		final int backgroundId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_BACKGROUND);
		
		RelativeLayout outMostLayout = this;
		
		{
			// setup background
			
			ImageView background = new ImageView(this.getContext());
			background.setId(backgroundId);
			background.setImageResource(R.drawable.add_url_bg);
			background.setScaleType(ScaleType.FIT_XY);
			
			RelativeLayout.LayoutParams backgroundLayoutParams = new RelativeLayout.LayoutParams(0, 0);
			backgroundLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, addressBarEditTextSearchId);
			backgroundLayoutParams.addRule(RelativeLayout.ALIGN_LEFT, addressBarStarButtonId);
			backgroundLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, addressBarStarButtonId);
			backgroundLayoutParams.addRule(RelativeLayout.ALIGN_TOP, addressBarStarButtonId);
			
			background.setLayoutParams(backgroundLayoutParams);
			outMostLayout.addView(background);
		}
		
		{
			// setup star button
			
			Button starButton = new Button(this.getContext());
			starButton.setId(addressBarStarButtonId);
			starButton.setBackgroundResource(R.drawable.add_bookmark_button_selector);
			
			float width = this.getContext().getResources().getDimension(R.dimen.address_bar_star_button_width);
			float height = this.getContext().getResources().getDimension(R.dimen.address_bar_common_height);
			RelativeLayout.LayoutParams starButtonLayoutParams = new RelativeLayout.LayoutParams((int)(0.5f + width), (int)(0.5f + height));
			
			starButton.setLayoutParams(starButtonLayoutParams);
			outMostLayout.addView(starButton);
		}

		{
			// setup left line of address bar edit text
			
			ImageView line = new ImageView(this.getContext());
			line.setImageResource(R.drawable.add_line_0);
			line.setScaleType(ScaleType.CENTER_INSIDE);
			line.setId(addressBarLeftLineId);
			
			float height = this.getContext().getResources().getDimension(R.dimen.address_bar_common_height);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)(0.5f + height));
			layoutParams.addRule(RelativeLayout.RIGHT_OF, addressBarStarButtonId);
			layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			
			line.setLayoutParams(layoutParams);
			outMostLayout.addView(line);
		}
		
		{
			// setup edit text
			
			EditText editText = new EditText(this.getContext());
			editText.setId(addressBarEditTextId);
			editText.setMaxLines(1);
			float leftPadding = this.getContext().getResources().getDimension(R.dimen.common_padding);
			editText.setPadding((int)(0.5f + leftPadding), 0, 0, 0);
			editText.setBackgroundResource(R.drawable.address_bar_edit_text_selector);
			editText.setHint(R.string.type_in_address_hint);
			
			float width = this.getContext().getResources().getDimension(R.dimen.address_bar_edit_text_width);
			float height = this.getContext().getResources().getDimension(R.dimen.address_bar_common_height);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(0.5f + width), (int)(0.5f + height));
			params.addRule(RelativeLayout.RIGHT_OF, addressBarLeftLineId);
		
			editText.setLayoutParams(params);
			outMostLayout.addView(editText);
		}
		
		{
			// setup right line of address bar edit text
			
			ImageView line = new ImageView(this.getContext());
			line.setImageResource(R.drawable.add_line_1);
			line.setScaleType(ScaleType.CENTER_INSIDE);
			line.setId(addressBarRightLineId);
			
			float height = this.getContext().getResources().getDimension(R.dimen.address_bar_common_height);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)(0.5f + height));
			layoutParams.addRule(RelativeLayout.RIGHT_OF, addressBarEditTextId);
			layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			
			line.setLayoutParams(layoutParams);
			outMostLayout.addView(line);
		}
		
		{
			// setup the magnifier image
			
			ImageView imageView = new ImageView(this.getContext());
			imageView.setImageResource(R.drawable.add_serch_icon);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			
			float width = this.getContext().getResources().getDimension(R.dimen.address_bar_magnifier_width);
			float height = this.getContext().getResources().getDimension(R.dimen.address_bar_common_height);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(0.5f + width), (int)(0.5f + height));
			params.addRule(RelativeLayout.RIGHT_OF, addressBarRightLineId);
			float leftMargin = this.getContext().getResources().getDimension(R.dimen.address_bar_magnifier_left_margin);
			params.setMargins((int)(0.5f + leftMargin), 0, 0, 0);
			
			imageView.setLayoutParams(params);
			outMostLayout.addView(imageView);
		}
		
		{
			// setup edit text for searching
			
			EditText editText = new EditText(this.getContext());
			editText.setMaxLines(1);
			float leftPadding = this.getContext().getResources().getDimension(R.dimen.address_bar_edit_text_search_left_padding);
			float rightPadding = this.getContext().getResources().getDimension(R.dimen.common_padding);
			editText.setPadding((int)(0.5f + leftPadding), 0, (int)(0.5f + rightPadding), 0);
			editText.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			editText.setHint(R.string.search_hint);
			editText.setId(addressBarEditTextSearchId);
			editText.setBackgroundResource(R.drawable.address_bar_edit_text_search_selector);
			
			float width = this.getContext().getResources().getDimension(R.dimen.address_bar_edit_text_width);
			float height = this.getContext().getResources().getDimension(R.dimen.address_bar_common_height);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(0.5f + width), (int)(0.5f + height));
			params.addRule(RelativeLayout.RIGHT_OF, addressBarRightLineId);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
			
			editText.setLayoutParams(params);
			outMostLayout.addView(editText);
		}
	}
}
