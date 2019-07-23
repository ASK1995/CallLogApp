package com.example.android.calllog;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import java.util.ArrayList;

public class CallLogUtils {

    private static CallLogUtils instance;
    private Context context;
    private ArrayList<CallLogInfo> mainList = null;

    private CallLogUtils(Context context){
        this.context = context;
    }

    public static CallLogUtils getInstance(Context context){
        if(instance == null)
            instance = new CallLogUtils(context);
        return instance;
    }

    private void loadData(){
        mainList = new ArrayList<>();

        String projection[] = {"_id", CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE, CallLog.Calls.CACHED_NAME};
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI,projection,null,null, CallLog.Calls.DEFAULT_SORT_ORDER + " LIMIT 50");

        if(cursor == null){
            Log.d("CALLLOG","cursor is null");
            return;
        }

        if(cursor.getCount() == 0){
            Log.d("CALLLOG","cursor size is 0");
            return;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            CallLogInfo callLogInfo = new CallLogInfo();
            callLogInfo.setNumber(cursor.getString(cursor.getColumnIndex( CallLog.Calls.NUMBER )));
            callLogInfo.setCallType(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
            callLogInfo.setDate(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
            mainList.add(callLogInfo);

            cursor.moveToNext();
        }
        cursor.close();
    }

    public ArrayList<CallLogInfo> readCallLogs(){
        if(mainList == null)
            loadData();
        return mainList;
    }

}