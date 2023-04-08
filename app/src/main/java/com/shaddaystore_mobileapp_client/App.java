package com.shaddaystore_mobileapp_client;


import android.app.Application;
import android.content.Context;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

public class App extends Application {
    private Context context;

    /*int request_Code = 200;*/
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp((DumperPluginsProvider) new SampleDumperPluginsProvider(context))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                        .build()
        );
    }

    private void requestPermissions(String[] strings, int request_code) {
    }

    public static class SampleDumperPluginsProvider implements DumperPluginsProvider {
        private Context context;

        public SampleDumperPluginsProvider(Context context) {
            this.context = context;
        }

        @Override
        public Iterable<DumperPlugin> get() {
            /*
            ArrayList<DumperPlugin> plugins = new ArrayList<>();
            for (DumperPlugin dp : Stetho.defaultDumperPluginsProvider(context).get()) {
                plugins.add(dp);
            }

            return plugins;*/
            return Stetho.defaultDumperPluginsProvider(context).get();
        }
    }


    /*// Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    *//**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     *//*
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }*/
}
