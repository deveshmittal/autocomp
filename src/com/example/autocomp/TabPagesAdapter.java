package com.example.autocomp;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagesAdapter extends FragmentPagerAdapter {

	public TabPagesAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		// This is theoone that carries transit code
		case 0: 
			return new TransitFragment();
		case 1:
			// Bus fragment activity
			return new BusFragment();
		case 2:
			// Train fragment activity
			return new TrainFragment();
		case 3:
			return new ExtraFragment();
		
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}