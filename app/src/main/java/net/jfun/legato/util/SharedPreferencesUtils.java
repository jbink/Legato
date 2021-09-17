package net.jfun.legato.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtils {
    /**
     * <pre>
     * String 데이터를 저장합니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @param value   값
     */
    public static void putStringSharedPreference (Context context, String key, String value) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(key, value);
        editor.apply();
    }

    /**
     * <pre>
     * Boolean 데이터를 저장합니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @param value   값
     */
    public static void putBooleanSharedPreference (Context context, String key, boolean value) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * <pre>
     * Integer 데이터를 저장합니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @param value   값
     */
    public static void putIntSharedPreference (Context context, String key, int value) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * <pre>
     * Long 데이터를 저장합니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @param value   값
     */
    public static void putLongSharedPreference (Context context, String key, long value) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * <pre>
     * List<String> 데이터를 저장합니다.
     * </pre>
     * @param context
     * @param key
     * @param list
     */
    public static void putStringListSharedPreference(Context context, String key, List<String> list) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray listAsString = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            listAsString.put(list.get(i));
        }
        if (!list.isEmpty()) {
            editor.putString(key, listAsString.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    /**
     * <pre>
     * String 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @return 읽어온 값, 값이 없을 경우 null이 반환된다.
     */
    public static String getStringSharedPreference (Context context, String key) {
        return getStringSharedPreference(context, key, null);
    }

    /**
     * <pre>
     * String 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context  컨텍스트
     * @param key      키
     * @param defValue 디폴트 값
     * @return 읽어온 값, 값이 없을 경우 defValue값이 반환된다.
     */
    public static String getStringSharedPreference (Context context, String key, String defValue) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getString(key, defValue);
    }

    /**
     * <pre>
     * Boolean 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @return 읽어온 값, 값이 없을 경우 false가 반환된다.
     */
    public static boolean getBooleanSharedPreference (Context context, String key) {
        return getBooleanSharedPreference(context, key, false);
    }

    /**
     * <pre>
     * Boolean 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context  컨텍스트
     * @param key      키
     * @param defValue 디폴트 값
     * @return 읽어온 값, 값이 없을 경우 defValue가 반환된다.
     */
    public static boolean getBooleanSharedPreference (Context context, String key, boolean defValue) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getBoolean(key, defValue);
    }

    /**
     * <pre>
     * Int 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @return 읽어온 값, 값이 없을 경우 0이 반환된다.
     */
    public static int getIntSharedPreference (Context context, String key) {
        return getIntSharedPreference(context, key, 0);
    }

    /**
     * <pre>
     * Int 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context  컨텍스트
     * @param key      키
     * @param defValue 디폴트 값
     * @return 읽어온 값, 값이 없을 경우 defValue값이 반환된다.
     */
    public static int getIntSharedPreference (Context context, String key, int defValue) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getInt(key, defValue);
    }

    /**
     * <pre>
     * Long 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context 컨텍스트
     * @param key     키
     * @return 읽어온 값, 값이 없을 경우 0이 반환된다.
     */
    public static long getLongSharedPreference (Context context, String key) {
        return getLongSharedPreference(context, key, 0);
    }

    /**
     * <pre>
     * Long 데이터를 읽어옵니다.
     * </pre>
     *
     * @param context  컨텍스트
     * @param key      키
     * @param defValue 디폴트 값
     * @return 읽어온 값, 값이 없을 경우 defValue값이 반환된다.
     */
    public static long getLongSharedPreference (Context context, String key, long defValue) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getLong(key, defValue);
    }

    /**
     * <pre>
     * List<String> 데이터를 읽어옵니다.
     * </pre>
     * @param context
     * @param key
     * @return
     */
    public static List<String> getStringListSharedPreference(Context context, String key) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        List<String> list = new ArrayList<>();

        if (json != null) {
            try {
                JSONArray arrayJson = new JSONArray(json);
                for (int i = 0; i < arrayJson.length(); i++) {
                    String content = arrayJson.optString(i);
                    list.add(content);
                }
            } catch (JSONException e) {
            }
        }

        return list;
    }

    /**
     * 모든 프리퍼런스 데이터를 초기화
     * @param context 컨텍스트
     */
    public static void clearAllPreferencesData(Context context) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().apply();
    }
}
