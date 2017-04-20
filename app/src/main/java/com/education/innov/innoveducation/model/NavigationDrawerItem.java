package com.education.innov.innoveducation.model;

import com.education.innov.innoveducation.R;


import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerItem {

	private String title;
	private int imageId;

	public static List<NavigationDrawerItem> getData() {
		List<NavigationDrawerItem> dataList = new ArrayList<>();

		int[] imageIds = getImages();
		String[] titles = getTitles();

		for (int i = 0; i < titles.length; i++) {
			NavigationDrawerItem navItem = new NavigationDrawerItem();
			navItem.setTitle(titles[i]);
			navItem.setImageId(imageIds[i]);
			dataList.add(navItem);
		}
		return dataList;
	}

	private static int[] getImages() {

		return new int[]{
				R.drawable.ic_action_name,
				R.drawable.ic_action_name,
				R.drawable.ic_action_name,
				R.drawable.ic_action_name,
				R.drawable.ic_action_name,
				R.drawable.ic_action_name,
				R.drawable.ic_action_name,
				R.drawable.ic_action_name};
	}

	private static String[] getTitles() {

		return new String[] {
				"Add Child", "Associations", "Evénements à proximité", "Contacter nous","Profile","Log out"
		};
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
}
