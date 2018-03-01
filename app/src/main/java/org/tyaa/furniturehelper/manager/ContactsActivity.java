package org.tyaa.furniturehelper.manager;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static java.lang.Long.getLong;

public class ContactsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;
    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME
            };

    // The column index for the _ID column
    private static final Long CONTACT_ID_INDEX = 0L;
    // The column index for the LOOKUP_KEY column
    private static final String LOOKUP_KEY_INDEX = "1";

    // Defines the text expression
    /*@SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";*/
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " NOTNULL" :
                    ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL";
    // Defines a variable for the search string
    private String mSearchString;
    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };

    public static final String EXTRA_PHONE_NUMBER =
            "org.tyaa.furniturehelper.manager.ContactsActivity.PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Gets the ListView from the View list of the parent activity
        mContactsList = findViewById(android.R.id.list);
        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.contacts_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);

        // Set the item click listener
        mContactsList.setOnItemClickListener(this);

        /*mContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the Cursor
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                // Move to the selected contact
                cursor.moveToPosition(position);
                // Get the _ID value
                //mContactId = getLong(String.valueOf(CONTACT_ID_INDEX));
                // Get the selected LOOKUP KEY
                //mContactKey = getString(LOOKUP_KEY_INDEX);
                // Create the contact's content Uri
                mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);
                //
                int phoneNumberIndex = cursor
                        .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = cursor.getString(phoneNumberIndex);
                Log.d("my", phoneNumber);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
                setResult(RESULT_OK, intent);
            }
        });*/

        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);

        // Initializes the loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        /*return new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );*/
        return new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get the Cursor
        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        mContactId = CONTACT_ID_INDEX;
        // Get the selected LOOKUP KEY
        mContactKey = LOOKUP_KEY_INDEX;
        // Create the contact's content Uri
        mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);
        //
        String[] projection =
                new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor people = getContentResolver().query(
                mContactUri
                , projection
                , null
                , null
                , null
        );
        int phoneNumberIndex = people
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String phoneNumber = cursor.getString(phoneNumberIndex);
        Log.d("my", phoneNumber);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);

        setResult(RESULT_OK, intent);
    }
}
