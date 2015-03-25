package com.warmtel.android.common.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.warmtel.android.common.bean.ChannelItem;
import com.warmtel.android.common.db.DataBaseHelper;

@SuppressLint("DefaultLocale")
public class ChannelDao implements ChannelDaoInterface {
	private DataBaseHelper helper=null;
	private SQLiteDatabase database=null;
	
	public ChannelDao(Context context)
	{
		helper=DataBaseHelper.getInstance(context);
		
	}

	@Override
	public boolean addCache(ChannelItem item) {
		boolean flag=false;
		database=helper.getReadableDatabase();
		long id=-1;
		try {
		database.beginTransaction();
		ContentValues values=new ContentValues();
		java.lang.Class<? extends ChannelItem> clazz = item.getClass();
        String tableNmae = clazz.getSimpleName();
        Method[] methods = clazz.getMethods();
        for(Method method:methods)
        {
        	String mName=method.getName();
        	if(mName.startsWith("get") && !mName.startsWith("getClass")) {
                String fieldName = mName.substring(3, mName.length()).toLowerCase();
                Object value;
					value = method.invoke(item, null);
                if (value instanceof String) {
                    values.put(fieldName, (String) value);
                }
        	}
        }
        id = database.insert(tableNmae, null, values);
        flag = (id != -1 ? true : false);
        database.setTransactionSuccessful();
        	} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(database!=null)
				{
					database.endTransaction();
					database.close();
				}
			}
		return flag;
	}

	@Override
	public boolean deleteCache(String whereClause, String[] whereArgs) {
		 boolean flag = false;
		 database=helper.getReadableDatabase();
	        int count = 0;
	        try {
	            database.beginTransaction();
	            count = database.delete(DataBaseHelper.TABLE_CHANNEL, whereClause, whereArgs);
	            flag = (count > 0 ? true : false);
	            database.setTransactionSuccessful();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } finally {
	            if (database != null) {
	            	database.endTransaction();
	                database.close();
	            }
	        }
	        return flag;
	}

	@Override
	public boolean updateCache(ContentValues values, String whereClause,String[] whereArgs) {
		   boolean flag = false;
		   database=helper.getReadableDatabase();
	        int count = 0;
	        try {
	            database = helper.getWritableDatabase();
	            count = database.update(DataBaseHelper.TABLE_CHANNEL, values, whereClause, whereArgs);
	            flag = (count > 0 ? true : false);
	        } catch (Exception e) {
	        } finally {
	            if (database != null) {
	                database.endTransaction();
	                database.close();
	            }
	        }
	        return flag;
	}
	/**
	 * 得到频道标题和频道
	 */
	@Override
	public Map<String, String> viewCache(String selection,String[] selectionArgs) {
		    database=helper.getReadableDatabase();
	        Cursor cursor = null;
	        Map<String, String> map = new HashMap<String, String>();
	        try {
	            database.beginTransaction();
	            cursor = database.query(true, DataBaseHelper.TABLE_CHANNEL, null, selection,selectionArgs, null, null, null, null);
	            int cols_len = cursor.getColumnCount();
	            while (cursor.moveToNext()) {
	                for (int i = 0; i < cols_len; i++) {
	                    String cols_name = cursor.getColumnName(i);
	                    String cols_values = cursor.getString(cursor.getColumnIndex(cols_name));
	                    if (cols_values == null) {
	                        cols_values = "";
	                    }
	                    map.put(cols_name, cols_values);
	                }
	            }
	            database.setTransactionSuccessful();
	        } catch (Exception e) {
	        } finally {
	            if (database != null) {
	                database.endTransaction();
	                cursor.close();
	                database.close();
	            }
	        }
	        return map;
	}
	@Override
	public List<Map<String, String>> listCache(String selection,String[] selectionArgs) {
		   List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		   database=helper.getReadableDatabase();
	        Cursor cursor = null;
	        try {
	            database.beginTransaction();
	            cursor = database.query(false, DataBaseHelper.TABLE_CHANNEL, null, selection, selectionArgs,null, null, null, null);
	            int cols_len = cursor.getColumnCount();
	            while (cursor.moveToNext()) {
	                Map<String, String> map = new HashMap<String, String>();
	                for (int i = 0; i < cols_len; i++) {
	                    String cols_name = cursor.getColumnName(i);
	                    String cols_values = cursor.getString(cursor
	                            .getColumnIndex(cols_name));
	                    if (cols_values == null) {
	                        cols_values = "";
	                    }
	                    map.put(cols_name, cols_values);
	                }
	                list.add(map);
	            }
	            database.setTransactionSuccessful();
	        } catch (Exception e) {
	        } finally {
	            if (database != null) {
	                database.endTransaction();
	                cursor.close();
	                database.close();
	            }
	        }
	        return list;
	}

	@Override
	public void clearFeedTable() {
		 String sql = "DELETE FROM " + DataBaseHelper.TABLE_CHANNEL + ";";
		 database=helper.getReadableDatabase();
		 database.execSQL(sql);
	     revertSeq();
	}
	  private void revertSeq() {
	        String sql = "update sqlite_sequence set seq=0 where name='"+ DataBaseHelper.TABLE_CHANNEL + "'";
	        database=helper.getReadableDatabase();
	        database.execSQL(sql);
	    }

}
