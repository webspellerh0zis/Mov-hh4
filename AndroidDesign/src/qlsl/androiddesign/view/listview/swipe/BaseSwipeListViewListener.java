package qlsl.androiddesign.view.listview.swipe;

public class BaseSwipeListViewListener implements SwipeListViewListener {

	public void onOpened(int position, boolean toRight) {
	}

	public void onClosed(int position, boolean fromRight) {
	}

	public void onListChanged() {
	}

	public void onMove(int position, float x) {
	}

	public void onStartOpen(int position, int action, boolean right) {
	}

	public void onStartClose(int position, boolean right) {
	}

	public void onClickFrontView(int position) {
	}

	public void onClickBackView(int position) {
	}

	public void onDismiss(int[] reverseSortedPositions) {
	}

	public int onChangeSwipeMode(int position) {
		return SwipeListView.SWIPE_MODE_DEFAULT;
	}

	public void onChoiceChanged(int position, boolean selected) {
	}

	public void onChoiceStarted() {
	}

	public void onChoiceEnded() {
	}

	public void onFirstListItem() {
	}

	public void onLastListItem() {
	}
}
