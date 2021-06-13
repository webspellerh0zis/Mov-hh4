package qlsl.androiddesign.preference;

import com.qlsl.androiddesign.appname.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SystemSettingToggleButtonPreference extends Preference {

	private static final int[] icons = new int[] {
			R.drawable.basic_setting_icon6, R.drawable.basic_setting_icon1,
			R.drawable.basic_setting_icon2, R.drawable.basic_setting_icon7 };

	private static int iconArrayIndex = 0;

	private Boolean oldValue = false;

	private boolean mDisableDependentsState;

	public SystemSettingToggleButtonPreference(Context context,
			AttributeSet attrs) {
		super(context, attrs);
		setLayoutResource(R.layout.listitem_system_setting);
		
	}

	protected Object onGetDefaultValue(TypedArray a, int index) {
		oldValue = Boolean.parseBoolean(a.getString(index));
		return oldValue;
	}

	@SuppressLint("NewApi")
	protected void onBindView(final View view) {
		super.onBindView(view);
		ImageView iv = (ImageView) view.findViewById(R.id.icon);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		final ToggleButton tbtn = (ToggleButton) view.findViewById(R.id.tbtn);

		if (iconArrayIndex == icons.length) {
			iconArrayIndex = 0;
		}
		tbtn.setChecked(oldValue);
		iv.setImageResource(icons[iconArrayIndex++]);
		tv.setText(getTitle());
		tbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				updatePreference(tbtn.isChecked());
			}
		});
	}

	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		Boolean temp = restoreValue ? getPersistedBoolean(false)
				: (Boolean) defaultValue;
		if (!restoreValue)
			persistBoolean(temp);
		this.oldValue = temp;
	}

	private void updatePreference(Boolean newValue) {
		oldValue = newValue;
		notifyDependencyChange(shouldDisableDependents());
		SharedPreferences.Editor editor = getEditor();
		editor.putBoolean(getKey(), newValue);
		editor.commit();
	}

	public boolean shouldDisableDependents() {
		boolean shouldDisable = mDisableDependentsState ? oldValue : !oldValue;
		return shouldDisable || super.shouldDisableDependents();
	}

	public boolean getDisableDependentsState() {
		return mDisableDependentsState;
	}

	public void setDisableDependentsState(boolean disableDependentsState) {
		mDisableDependentsState = disableDependentsState;
	}

}
