package com.vktech.healthtrackerapp;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Storage {

    private static final String PREF_NAME = "health_logs";
    private static final String KEY_LOGS = "logs";

    private final SharedPreferences prefs;

    public Storage(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private void saveLogs(List<LogEntry> logList) {
        JSONArray jsonArray = new JSONArray();
        for (LogEntry entry : logList) {
            try {
                JSONObject log = new JSONObject();
                log.put("id", entry.getId());
                log.put("date", entry.getDate());
                log.put("steps", entry.getSteps());
                log.put("water", entry.getWater());
                log.put("sleep", entry.getSleep());
                log.put("notes", entry.getNotes());
                jsonArray.put(log);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        prefs.edit().putString(KEY_LOGS, jsonArray.toString()).apply();
    }

    public List<LogEntry> getAllLogs() {
        String data = prefs.getString(KEY_LOGS, "[]");
        List<LogEntry> list = new ArrayList<>();
        try {
            JSONArray logs = new JSONArray(data);
            for (int i = 0; i < logs.length(); i++) {
                JSONObject o = logs.optJSONObject(i);
                if (o != null) {
                    long id = o.optLong("id");
                    String date = o.optString("date", "");
                    int steps = o.optInt("steps", 0);
                    int water = o.optInt("water", 0);
                    float sleep = (float) o.optDouble("sleep", 0.0);
                    String notes = o.optString("notes", "");
                    list.add(new LogEntry(id, date, steps, water, sleep, notes));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Sort by ID to maintain order
        Collections.sort(list, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
        return list;
    }

    public void addLog(LogEntry entry) {
        List<LogEntry> logs = getAllLogs();
        logs.add(entry);
        saveLogs(logs);
    }

    public void updateLog(LogEntry entry) {
        List<LogEntry> logs = getAllLogs();
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getId() == entry.getId()) {
                logs.set(i, entry);
                break;
            }
        }
        saveLogs(logs);
    }

    public void deleteLog(LogEntry entry) {
        List<LogEntry> logs = getAllLogs();
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getId() == entry.getId()) {
                logs.remove(i);
                break;
            }
        }
        saveLogs(logs);
    }
}