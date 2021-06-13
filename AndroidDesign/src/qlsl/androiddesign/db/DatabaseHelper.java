package qlsl.androiddesign.db;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import qlsl.androiddesign.application.SoftwareApplication;
import qlsl.androiddesign.dao.subdao.ExampleDao;
import qlsl.androiddesign.daoimpl.subdaoimpl.ExampleDaoImpl;
import qlsl.androiddesign.db.othertable.ChatMessage;
import qlsl.androiddesign.db.othertable.ChatQueue;
import qlsl.androiddesign.db.othertable.Example;
import qlsl.androiddesign.db.othertable.ToolColor;
import qlsl.androiddesign.db.othertable.ToolPager;
import qlsl.androiddesign.db.othertable.ToolSpeech;
import qlsl.androiddesign.util.commonutil.Log;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "qlsl.db";

	private static final int DATABASE_VERSION = 1;

	private static DatabaseHelper databaseHelper;

	public static final String sql_tool_color = "insert into ToolColor(isOpenArt,isShowSingle,textColor,titleBarColor,contentBarColor,textSize,sleep)values(1,1,'#FF000000','#7F7F7F7F','#7F7F7F7F',17,520)";
	public static final String sql_tool_speech = "insert into ToolSpeech(isAutoPlay,isClickPlay,pitch,speaker,speed,stream,volume)values(1,0,55,10,1,3,60)";
	public static final String sql_tool_pager = "insert into ToolPager(isOpen,pager,total)values(1,15,100)";

	private ExampleDao exampleDao;

	/**
	 * 基础Dao
	 */

	/**
	 * 其他Dao
	 */

	public static DatabaseHelper getInstance() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(SoftwareApplication.getInstance(), DatabaseHelper.class);
		}
		return databaseHelper;
	}

	public ExampleDao getExampleDao() throws SQLException {
		if (exampleDao == null) {
			exampleDao = new ExampleDaoImpl(getConnectionSource(), Example.class);
		}
		return exampleDao;
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public DatabaseHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
	}

	private void createDefaultData(SQLiteDatabase db) {
		db.execSQL(sql_tool_color);
		db.execSQL(sql_tool_speech);
		db.execSQL(sql_tool_pager);
	}

	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			outputDatabaseHelperOnCreateInfo();

			int status_example = TableUtils.createTableIfNotExists(connectionSource, Example.class);
			outputCreateTableStatus(status_example, Example.class);

			int status_toolColor = TableUtils.createTableIfNotExists(connectionSource, ToolColor.class);
			outputCreateTableStatus(status_toolColor, ToolColor.class);

			int status_toolSpeech = TableUtils.createTableIfNotExists(connectionSource, ToolSpeech.class);
			outputCreateTableStatus(status_toolSpeech, ToolSpeech.class);

			int status_toolPager = TableUtils.createTableIfNotExists(connectionSource, ToolPager.class);
			outputCreateTableStatus(status_toolPager, ToolPager.class);

			int status_chat_queue = TableUtils.createTableIfNotExists(connectionSource, ChatQueue.class);
			outputCreateTableStatus(status_chat_queue, ChatQueue.class);

			int status_chat_msg = TableUtils.createTableIfNotExists(connectionSource, ChatMessage.class);
			outputCreateTableStatus(status_chat_msg, ChatMessage.class);

			createDefaultData(db);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2, int arg3) {
		try {
			outputDatabaseHelperOnUpgradeInfo();

			TableUtils.dropTable(connectionSource, Example.class, false);
			int status_example = TableUtils.createTableIfNotExists(connectionSource, Example.class);
			outputCreateTableStatus(status_example, Example.class);

			TableUtils.dropTable(connectionSource, ToolColor.class, false);
			int status_toolColor = TableUtils.createTableIfNotExists(connectionSource, ToolColor.class);
			outputCreateTableStatus(status_toolColor, ToolColor.class);

			TableUtils.dropTable(connectionSource, ToolSpeech.class, false);
			int status_toolSpeech = TableUtils.createTableIfNotExists(connectionSource, ToolSpeech.class);
			outputCreateTableStatus(status_toolSpeech, ToolSpeech.class);

			TableUtils.dropTable(connectionSource, ToolPager.class, false);
			int status_toolPager = TableUtils.createTableIfNotExists(connectionSource, ToolPager.class);
			outputCreateTableStatus(status_toolPager, ToolPager.class);

			TableUtils.dropTable(connectionSource, ChatQueue.class, false);
			int status_chat_queue = TableUtils.createTableIfNotExists(connectionSource, ChatQueue.class);
			outputCreateTableStatus(status_chat_queue, ChatQueue.class);

			TableUtils.dropTable(connectionSource, ChatMessage.class, false);
			int status_chat_msg = TableUtils.createTableIfNotExists(connectionSource, ChatMessage.class);
			outputCreateTableStatus(status_chat_msg, ChatMessage.class);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void outputDatabaseHelperOnCreateInfo() {
		Log.i("onCreate：OrmLiteSqliteOpenHelper：<br/>" + getClass().getName());
	}

	private void outputDatabaseHelperOnUpgradeInfo() {
		Log.i("onUpgrade：DatabaseHelper：<br/>" + getClass().getName());
	}

	private void outputCreateTableStatus(int status, Class<?> destClass) {
		Log.i("createTableIfNotExists：<br/>table:" + destClass.getName() + "：<br/>" + "status:" + status);
	}

}
