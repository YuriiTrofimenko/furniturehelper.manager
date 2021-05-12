package org.tyaa.furniturehelper.manager.common;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yurii on 11.12.17.
 */

public class Utility {
    //a folder name for images
    private final static String imagesFolder = "Manager Images";

    //прсстая проверка наличия разрешения
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isThereAPermission(final Context context, final String permissionId){
        return ContextCompat.checkSelfPermission(context, permissionId) == PackageManager.PERMISSION_GRANTED;
    }

    //Проверка разрешений на работу с картой памяти
    //Проверка разрешения с ответом в onRequestPermissionsResult
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context, final String permissionId, final int requestId) {
        int currentAPIVersion = Build.VERSION.SDK_INT;

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, permissionId) == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,permissionId)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permissionId}, requestId);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permissionId}, requestId);
                }

                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * get uri to drawable or any other resource type if u wish
     * @param context - context
     * @param drawableId - drawable res id
     * @return - uri
     */
    public static final String drawableToURIString(@NonNull Context context,
                                          @AnyRes int drawableId) {

        String filename = context.getResources()
                .getResourceEntryName(drawableId) + ".png";

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imagesFolder);

        filename = folder.getAbsolutePath() + "/" + filename;
        Log.d("valery", "Utility 1723: " + filename);

        if (!folder.exists()) {
            if (!isThereAPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.d("valery", "1816can't write");
                return "";
            }
            if (!folder.mkdirs()) {
                Log.d("valery", "1734can't make directory");
                return "";
            }
        }

        File file = new File(filename);

        if (file.exists())
            return filename;

        // Get the image from drawable resource as drawable object
        Drawable drawable = context.getResources().getDrawable(drawableId);
        Bitmap mBitmap = ((BitmapDrawable)drawable).getBitmap();

        // Create a file to save the image
        FileOutputStream fs;
     //   Log.d("valery", "Utility 2: " + filename);
        try{
            fs = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fs);
            fs.flush();

            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filename;
    }

    /**
     * get Drawable from string
     * */
    public static final Drawable uriStringToDrawable(@NonNull String _uriString) {
 //       Log.d("MyLog", "uriStringToDrawable 2:" + _uriString);
        java.io.FileInputStream fs;

        try {
            Log.d("valery", "Utility 1713: " +  _uriString);
            fs = new java.io.FileInputStream(_uriString);
      //      Log.d("MyLog", "got stream");
            return Drawable.createFromStream(fs, _uriString);
        }catch(java.io.IOException ex){
            ex.printStackTrace();
        }

        return Global.EMPTY_DRAWABLE;
    }

    /**
     * get uri to any resource type
     * @param context - context
     * @param resId - resource id
     * @throws Resources.NotFoundException if the given ID does not exist.
     * @return - Uri to resource by given id
     */
    public static final Uri resourceToURI(@NonNull Context context,
                                             @AnyRes int resId)
            throws Resources.NotFoundException {
        /** Return a Resources instance for your application package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        /** return uri */
        return resUri;
    }

    /*public static String uriStringFromGalleryUri(@NonNull Context context, Uri contentUri) {

        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor =
                context.getContentResolver().query(
                        contentUri
                        , proj
                        , null
                        , null
                        , null
                );
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        Log.d("asd", res);
        return res;
    }*/

    public static final String bitmapToUriString(@NonNull Context _context,
                                                     Bitmap _bitmap
                                                    , String _fileName
                                                ) {
        Bitmap mBitmap = _bitmap;
        // Get the external storage directory path
        String filename = _fileName + "_copy.png";

    //    Log.d("valery", "Utility 3: " + filename);
        // Create a file to save the image
        try{
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imagesFolder);

            filename = folder.getAbsolutePath() + "/" + filename;
            Log.d("valery", "Utility 3: " + filename);

            File file = new File(filename);

            if (file.exists())
                return filename;

            FileOutputStream stream = new java.io.FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return filename;
    }
}
