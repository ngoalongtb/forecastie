package com.gfd.cropwis.models;

import java.util.ArrayList;
import java.util.List;

import com.gfd.cropwis.R;

public class NavigationDrawerItem {

	private String title;
	private int imageId;

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

    public NavigationDrawerItem() {
    }

    public NavigationDrawerItem(String title, int imageId) {
		this.title = title;
		this.imageId = imageId;
	}

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
							R.drawable.ic_close_black_24dp, R.drawable.ic_close_black_24dp,
							R.drawable.ic_close_black_24dp, R.drawable.ic_close_black_24dp,
							R.drawable.ic_close_black_24dp, R.drawable.ic_close_black_24dp};
	}

	private static String[] getTitles() {

		return new String[] {
				"ic_close_black_24dp", "ic_close_black_24dp", "ic_close_black_24dp", "ic_close_black_24dp", "ic_close_black_24dp", "ic_close_black_24dp"
		};
	}
}
