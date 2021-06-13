package qlsl.androiddesign.asynctask.subasynctask;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import qlsl.androiddesign.adapter.baseadapter.BaseAdapter;
import qlsl.androiddesign.asynctask.baseasynctask.AsyncTask;
import qlsl.androiddesign.db.DatabaseHelper;
import qlsl.androiddesign.entity.commonentity.BaseLocation;
import qlsl.androiddesign.util.commonutil.LocationUtils;
import qlsl.androiddesign.util.commonutil.Log;

/**
 * 基于高德地图定位SDK获取距离<br/>
 * 
 */
public class LocationAsyncTask<T extends BaseLocation> extends AsyncTask<Double, Void, Void> {

	private AbsListView listView;

	public LocationAsyncTask(AbsListView listView) {
		this.listView = listView;
	}

	protected Void doInBackground(Double... params) {
		updateListDistanceData(params[0], params[1]);
		return null;
	}

	protected void onPostExecute(Void result) {
		notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	private void updateListDistanceData(double current_lat, double current_lng) {
		BaseAdapter<T> adapter = castAdapter(listView.getAdapter());
		if (adapter != null) {
			List<T> list = adapter.getList();
			Iterator<T> iterator = list.iterator();
			for (T location : list) {
				location = iterator.next();

				Double dest_lat = location.getLatitude();
				Double dest_lng = location.getLongitude();

				if (dest_lat != null && dest_lng != null) {
					double distance = LocationUtils.getDistance(current_lat, current_lng, dest_lat, dest_lng);
					location.setDistance(distance);

					DatabaseHelper dbHelper = DatabaseHelper.getInstance();
					Class<T> destClass = (Class<T>) location.getClass();
					try {
						dbHelper.getDao(destClass).update(location);
					} catch (SQLException e) {
						Log.e("qlsl", e);
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void notifyDataSetChanged() {
		BaseAdapter<T> adapter = castAdapter(listView.getAdapter());
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	@SuppressWarnings("unchecked")
	private BaseAdapter<T> castAdapter(ListAdapter listAdapter) {
		BaseAdapter<T> adapter = null;
		if (listAdapter != null) {
			if (listAdapter instanceof BaseAdapter) {
				adapter = (BaseAdapter<T>) listAdapter;
			} else if (listAdapter instanceof HeaderViewListAdapter) {
				HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter) listAdapter;
				adapter = (BaseAdapter<T>) headerAdapter.getWrappedAdapter();
			}
		}
		return adapter;
	}
}
