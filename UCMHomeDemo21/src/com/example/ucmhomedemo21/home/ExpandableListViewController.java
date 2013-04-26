package com.example.ucmhomedemo21.home;

import android.view.View;

public interface ExpandableListViewController {
	public abstract void groupClicked(View senderView/* no necessarily exactly the 'group view' */);
}
