package org.tyaa.furniturehelper.manager.common;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.tyaa.furniturehelper.manager.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by yurii on 11.12.17.
 */

public class Utility {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private static Bitmap mBitmap;
    private static File mFile;

    //Проверка разрешений на работу с картой памяти
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
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
        /*Log.d("MyLog", "drawableToURI");
        Uri imageUri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                //"res:///" +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;*/
        // Get the image from drawable resource as drawable object
        Drawable drawable = context.getDrawable(drawableId);
        mBitmap = ((BitmapDrawable)drawable).getBitmap();
        // Get the external storage directory path
        String path = Environment.getExternalStorageDirectory().toString();
        // Create a file to save the image
        mFile =
                new File(path, context.getResources()
                        .getResourceEntryName(drawableId) + ".png");
        try{
            OutputStream stream = null;
            stream = new FileOutputStream(mFile);
            mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Parse the saved image path to uri
        return mFile.getAbsolutePath();
    }

    /**
     * get Drawable from string
     * */
    public static final Drawable uriStringToDrawable(@NonNull String _uriString) {

        Uri imageUri = Uri.parse(_uriString);
        String path = imageUri.toString();
        Log.d("MyLog", path);
        return Drawable.createFromPath(path);
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

        mBitmap = _bitmap;
        // Get the external storage directory path
        String path = Environment.getExternalStorageDirectory().toString();
        // Create a file to save the image
        mFile =
                new File(path, _fileName + "_copy.png");
        try{
            OutputStream stream = null;
            stream = new FileOutputStream(mFile);
            mBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Parse the saved image path to uri
        return mFile.getAbsolutePath();
    }
}
