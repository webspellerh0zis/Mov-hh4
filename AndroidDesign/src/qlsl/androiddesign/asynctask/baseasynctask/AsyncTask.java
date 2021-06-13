package qlsl.androiddesign.asynctask.baseasynctask;

public class AsyncTask<Params, Progress, Result>
		extends
			android.os.AsyncTask<Params, Progress, Result> {

	public AsyncTask() {

	}

	protected Result doInBackground(Params... params) {
		return null;
	}

	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
	}

}