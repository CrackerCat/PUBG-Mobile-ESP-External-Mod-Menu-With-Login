package com.sameer.arora;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.topjohnwu.superuser.Shell;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnClickListener {

    String gameName = "com.tencent.ig";
    static int gameType = 1;
    private int storage_permision = 1;
    static int bit = 1;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("SameerArora");
    }

    private boolean isDisplay = false;
    private boolean isMenuDis = false;
    //WindowManager.LayoutParams params;
    Context ctx;
    View menu;

    int mx, my;

    public static String socket, Path, Paths, verify;
    public String MemHack;
    Process process;
    private Timer _timer = new Timer();
    private TimerTask waiting;

    public String daemonPath;
    public String daemonPath64;
    public String daemonPathIND32;
    public String daemonPathIND64;

    private RadioButton radiobutton1;
    private RadioButton radiobutton2;

    private RadioButton radiobutton4;
    private RadioButton radiobutton6;
    private RadioButton radiobutton5;
    private RadioButton radiobutton3;

    static boolean isRoot = false;
    static boolean isNoroot = false;
    static boolean rootMod = false;

    static boolean is32 = false;
    static boolean is64 = false;
    static boolean bitMod = false;

    static boolean isIND = false;
    static boolean isInt = true;
    static boolean verMod = true;
    static public TextView KeyValid;

    String indMEM;
    private Double tdate;

    public boolean onESP = false;
    public boolean isDaemon = false;

    private RadioGroup selver, radiogroup1, radiogroup2, radiogroup3;
    public LinearLayout Linear1, Linear2, progg;
    public EditText user, pass;
    public Button mbutton1, mbutton2, mbutton5, mbutton4, mbutton6, mbutton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CheckFloatViewPermission();

        ctx = this;

        String Getid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        tdate = Double.parseDouble(format.format(today));

        if (Shell.rootAccess()) {
            isRoot = true;
            ExecuteElf("su");
            Paths = "su -c " + getFilesDir().toString() + "/sockkr";
            Path = "su -c " + getFilesDir().toString() + "/sock64";
            isNoroot = false;
            rootMod = true;
        } else {
            isNoroot = true;
            isRoot = false;
            Paths = getFilesDir().toString() + "/sockkr";
            Path = getFilesDir().toString() + "/sock64";
            rootMod = true;
        }

        mbutton1 = (Button) findViewById(R.id.button1);
        mbutton2 = (Button) findViewById(R.id.button2);

        mbutton1.setOnClickListener(this);
        mbutton2.setOnClickListener(this);

        radiogroup1 = (RadioGroup) findViewById(R.id.radioInd);
        radiogroup3 = (RadioGroup) findViewById(R.id.radioBit);

        radiobutton1 = (RadioButton) findViewById(R.id.radiobutton1);
        radiobutton2 = (RadioButton) findViewById(R.id.radiobutton2);

        radiobutton5 = (RadioButton) findViewById(R.id.radiobutton5);
        radiobutton6 = (RadioButton) findViewById(R.id.radiobutton6);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);

        progg = (LinearLayout) findViewById(R.id.progg);
        KeyValid = (TextView) findViewById(R.id.KeyValid);

        Linear1 = (LinearLayout) findViewById(R.id.linear1);
        Linear2 = (LinearLayout) findViewById(R.id.linear2);
        Linear1.setVisibility(View.VISIBLE);
        Linear2.setVisibility(View.GONE);

        if (!isConfigExist()) {
            Init();
        } else {
            SharedPreferences sp = getApplicationContext().getSharedPreferences("espValue", Context.MODE_PRIVATE);
            String ed = sp.getString("key", "");
            if (ed.length() > 20) {
                user.setText(ed.substring(0, 12));
                pass.setText(ed.substring(13, 21));
            }
        }

        selver = (RadioGroup) findViewById(R.id.selver);
        selver.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.global:
                        gameName = "com.tencent.ig";
                        gameType = 1;
                        break;

                    case R.id.korea:
                        gameName = "com.pubg.krmobile";
                        gameType = 2;
                        break;

                    case R.id.taiwan:
                        gameName = "com.rekoo.pubgm";
                        gameType = 4;
                        break;

                    case R.id.veitnam:
                        gameName = "com.vng.pubgmobile";
                        gameType = 3;
                        break;
                }
            }
        });

        Button login = (Button) findViewById(R.id.login);
        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("espValue", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                String username = user.getText().toString();
                String password = pass.getText().toString();

                if (password.length() == 8 && username.length() == 12) {
                    progg.setVisibility(View.VISIBLE);
                    login.setVisibility(View.GONE);
                    String key = username + ":" + password;
                    new BackTask().execute("https://pubgwale.com/?id=" + Getid + "&key=" + key);
                    waiting = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        double ndate = Double.parseDouble(BackTask.rslt.replace("-", ""));
                                        if (tdate > ndate) {
                                            if (ndate == 1) {
                                                progg.setVisibility(View.GONE);
                                                login.setVisibility(View.VISIBLE);
                                                Toast.makeText(MainActivity.this, "Invalid Key", Toast.LENGTH_LONG).show();
                                            } else if (ndate == 2) {
                                                progg.setVisibility(View.GONE);
                                                login.setVisibility(View.VISIBLE);
                                                Toast.makeText(MainActivity.this, "Key In Use", Toast.LENGTH_LONG).show();
                                            } else {
                                                progg.setVisibility(View.GONE);
                                                login.setVisibility(View.VISIBLE);
                                                Toast.makeText(MainActivity.this, "Key Has Expired", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                                            } else {
                                                requestStoragePermission();
                                            }
                                            Linear1.setVisibility(View.GONE);
                                            Linear2.setVisibility(View.VISIBLE);
                                            KeyValid.setText("Validity :- " + BackTask.rslt);
                                            ed.putString("key", key);
                                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    catch (NumberFormatException ex)
                                    {
                                        progg.setVisibility(View.GONE);
                                        login.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    catch (NullPointerException ex)
                                    {
                                        progg.setVisibility(View.GONE);
                                        login.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    };
                    _timer.schedule(waiting, (int) (3000));
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Key", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("espValue", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                String username = user.getText().toString();
                String password = pass.getText().toString();

                Linear1.setVisibility(View.GONE);
                Linear2.setVisibility(View.VISIBLE);
                KeyValid.setText("Validity :- Testing mode");
                ed.putString("key", "Testing mode");
                Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
            }
        });

        ImageView paste = (ImageView) findViewById(R.id.paste);
        paste.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String ed = clipboard.getText().toString();
                if (ed.length() > 20) {
                    user.setText(ed.substring(0, 12));
                    pass.setText(ed.substring(13, 21));
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Key", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView imageLogo = (ImageView) findViewById(R.id.telegram);
        imageLogo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://telegram.me/SameerAroraYT");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radiobutton1:
                        //Toast.makeText(MainActivity.this,"Please Select Bit Version First !!",Toast.LENGTH_LONG).show();
                        isIND = true;
                        isInt = false;
                        indMEM = "IND";
                        verMod = true;
                        break;

                    case R.id.radiobutton2:
                        isInt = true;
                        isIND = false;
                        indMEM = "INTER";
                        verMod = true;
                        break;
                }
            }
        });

        radiogroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radiobutton5:
                        is32 = true;
                        is64 = false;
                        bitMod = true;
                        break;

                    case R.id.radiobutton6:
                        is64 = true;
                        is32 = false;
                        bitMod = true;
                        break;
                }
            }
        });

        ExecuteElf("su -c");

        loadAssets();
        loadAssets64();
        loadMomery();

        socket = daemonPath;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (BackTask.rslt != "") {
                    if (isDisplay == false && isMenuDis == false) {
                        if (!rootMod || !verMod || !bitMod) {
                            Toast.makeText(MainActivity.this, "Please Select Settings First !!", Toast.LENGTH_LONG).show();
                        } else if (isDisplay == false && isMenuDis == false) {
                            if (isNoroot) {
                                if (is32) {
                                    if (isIND) {
                                        socket = daemonPathIND32;
                                        //				MemHack = daemonPathMEM;
                                    } else {
                                        socket = daemonPath;
                                        //			MemHack = daemonPathMEM;
                                    }
                                } else if (is64) {
                                    if (isIND) {
                                        //Toast.makeText(MainActivity.this,"64bit IND version is currently not supported !!",Toast.LENGTH_LONG).show();
                                        socket = daemonPathIND64;
                                        //				MemHack = daemonPath64;
                                    } else {
                                        //			MemHack = daemonPathMEM;
                                        socket = daemonPath64;
                                    }
                                }
                            }

                            if (isRoot) {
                                if (is32) {
                                    if (isIND) {
                                        socket = "su -c " + daemonPathIND32;
                                        //				MemHack = "su -c " + daemonPathMEM;
                                    } else {
                                        socket = "su -c " + daemonPath;
                                        //			MemHack = "su -c " + daemonPathMEM;
                                    }
                                } else if (is64) {

                                    if (isIND) {
                                        //Toast.makeText(MainActivity.this,"64bit IND version is currently not supported !!",Toast.LENGTH_LONG).show();
                                        socket = "su -c " + daemonPathIND64;
                                        //	MemHack = "su -c " + daemonPath64;

                                    } else {
                                        socket = "su -c " + daemonPath64;
                                        //		MemHack = "su -c " + daemonPath64;
                                    }
                                }
                            }
                            startPatcher();
                            startService(new Intent(this, Overlay.class));
                            // ShowFloatWindow();
                            isDisplay = true;
                            //startDaemon();
                            isDaemon = true;
                            //Toast.makeText(MainActivity.this,socket,Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Already Started !!", Toast.LENGTH_LONG).show();
                        }
                        // FloatButton();
                        //  startPatcher();
                    } else {
                    }
                }
                break;
            case R.id.button2:
                if (isDisplay == true) {
                    isDisplay = false;
                    isMenuDis = false;
                    isDaemon = false;
                    //process.destroy();
                    //Stop();
                    stopService(new Intent(this, FloatLogo.class));
                    stopService(new Intent(this, Overlay.class));
                }
                break;
        }
    }

    public void CheckFloatViewPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Enable Floating Permission ", Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        }
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (FloatLogo.class.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    void startPatcher() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 123);
        } else {
            startFloater();
        }
    }

    private void startFloater() {
        if (!isServiceRunning()) {
            startService(new Intent(this, FloatLogo.class));
        } else {
            Toast.makeText(this, "Service Already Running!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ExecuteElf(String shell) {
        String s = shell;
        try {
            Runtime.getRuntime().exec(s, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Shell(String str) {
        DataOutputStream dataOutputStream = null;
        try {
            process = Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            e.printStackTrace();
            process = null;
        }
        if (process != null) {
            dataOutputStream = new DataOutputStream(process.getOutputStream());
        }
        try {
            dataOutputStream.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            process.waitFor();
        } catch (InterruptedException e3) {
            e3.printStackTrace();
        }
    }

    public void startDaemon() {
        new Thread() {
            public void run() {
                Shell(socket);

            }
        }.start();
    }

    public void loadAssets() {
        String filepath = Environment.getExternalStorageDirectory() + "/Android/data/.tyb";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            byte[] buffer = "DO NOT DELETE".getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String pathf = getFilesDir().toString() + "/sock";
        try {
            OutputStream myOutput = new FileOutputStream(pathf);
            byte[] buffer = new byte[1024];
            int length;
            InputStream myInput = getAssets().open("sock");
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();
            myOutput.flush();
            myOutput.close();

        } catch (IOException e) {
        }

        daemonPath = getFilesDir().toString() + "/sock";

        try {
            Runtime.getRuntime().exec("chmod 777 " + daemonPath);
        } catch (IOException e) {
        }

    }

    public void loadAssets64() {

        String pathf = getFilesDir().toString() + "/sock64";
        try {
            OutputStream myOutput = new FileOutputStream(pathf);
            byte[] buffer = new byte[1024];
            int length;
            InputStream myInput = getAssets().open("sock64");
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();
            myOutput.flush();
            myOutput.close();

        } catch (IOException e) {
        }

        daemonPath64 = getFilesDir().toString() + "/sock64";

        try {
            Runtime.getRuntime().exec("chmod 777 " + daemonPath64);
        } catch (IOException e) {
        }
    }

    public void loadMomery() {
        String pathf = getFilesDir().toString() + "/sockkr";
        try {
            OutputStream myOutput = new FileOutputStream(pathf);
            byte[] buffer = new byte[1024];
            int length;
            InputStream myInput = getAssets().open("sockkr");
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();
            myOutput.flush();
            myOutput.close();

        } catch (IOException e) {
        }

        String Path64 = getFilesDir().toString() + "/sockkr";

        try {
            Runtime.getRuntime().exec("chmod 777 " + Path64);
        } catch (IOException e) {
        }

    }

    void Init() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("espValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("key", "feubfubv");
        ed.putInt("fps", 30);
        ed.putBoolean("Box", true);
        ed.putBoolean("Line", true);
        ed.putBoolean("Distance", false);
        ed.putBoolean("Health", false);
        ed.putBoolean("Name", false);
        ed.putBoolean("Head Position", false);
        ed.putBoolean("Back Mark", false);
        ed.putBoolean("Skelton", false);
        ed.putBoolean("Grenade Warning", false);
        ed.putBoolean("Enemy Weapon", false);
        ed.apply();
    }

    boolean isConfigExist() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("espValue", Context.MODE_PRIVATE);
        return sp.contains("key");
    }

    private static class BackTask extends AsyncTask<String, Integer, String> {
        static String rslt;

        @Override
        protected void onPreExecute() {
        }

        protected String doInBackground(String... address) {
            String output = "";
            try {
                java.net.URL url = new java.net.URL(address[0]);
                java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(url.openStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    output += line;
                }
                in.close();
            } catch (java.net.MalformedURLException e) {
                output = e.getMessage();
            } catch (java.io.IOException e) {
                output = e.getMessage();
            } catch (Exception e) {
                output = e.toString();
            }
            return output;
        }

        protected void onProgressUpdate(Integer... values) {
        }

        protected void onPostExecute(String s) {
            rslt = verify = s;
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("We need to move some files For Applying Hacks")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, storage_permision);
                        }
                    })
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, storage_permision);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == storage_permision) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ctx, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}