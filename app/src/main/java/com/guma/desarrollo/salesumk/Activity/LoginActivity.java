package com.guma.desarrollo.salesumk.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.guma.desarrollo.salesumk.Lib.Funciones;

import com.guma.desarrollo.salesumk.Lib.Ubicacion;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.guma.desarrollo.salesumk.DataBase.DataBaseHelper;
import com.guma.desarrollo.salesumk.Lib.ClssURL;
import com.guma.desarrollo.salesumk.Lib.Variables;
import com.guma.desarrollo.salesumk.R;

import org.apache.http.Header;
import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>
{
    DataBaseHelper myDB;
    Variables myVar;
    Funciones vrf;

    private static final String[] DUMMY_CREDENTIALS = new String[]
            { "foo@example.com:hello", "bar@example.com:world" };
    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
//        Ubicacion ub = new Ubicacion(this);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener
                (new TextView.OnEditorActionListener()
                    {
                        @Override
                        public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
                        {
                            if (id == R.id.password || id == EditorInfo.IME_NULL)
                            {
                                attemptLogin();
                                return true;
                            }
                            return false;
                        }
                    }
                );
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener
                (new OnClickListener()
                    {
                        @Override
                        public void onClick(View view) { attemptLogin(); }
                    }
                );
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        myDB = new DataBaseHelper(this);
    }
    private void populateAutoComplete() { getLoaderManager().initLoader(0, null, this); }
    private void attemptLogin()
    {
        if (mAuthTask != null) { return; }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // Check for a valid email address.
        if (TextUtils.isEmpty(email))
        {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        else
        {
            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password) && !isPasswordValid(password))
            {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }
        }
        if (cancel) { focusView.requestFocus(); }
        else
        {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
    private boolean isPasswordValid(String password) { return password.length() > 3; }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener
            (new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                }
            );
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener
            (new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                }
            );
        }
        else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new
        CursorLoader( this,
                      Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                                           ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                      ProfileQuery.PROJECTION,
                      ContactsContract.Contacts.Data.MIMETYPE +
                         " = ?", new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},
                      ContactsContract.Contacts.Data.IS_PRIMARY + " DESC"
                     );
    }
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) { }
    private void addEmailsToAutoComplete(List<String> emailAddressCollection)
    {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
        mEmailView.setAdapter(adapter);
    }
    private interface ProfileQuery
    {
        String[] PROJECTION =
                {
                    ContactsContract.CommonDataKinds.Email.ADDRESS,
                    ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
                };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
    {
        private final String mEmail;
        private final String mPassword;
        UserLoginTask(String email, String password)
        {
            mEmail = email;
            mPassword = password;
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                // Simulate network access.
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                return false;
            }
            for (String credential : DUMMY_CREDENTIALS)
            {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail))
                {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success)
        {
            mAuthTask = null;
            showProgress(false);
            if (success)
            {
                mEmailView.getText().toString();
                mPasswordView.getText().toString();
                Cursor res = myDB.GetAllData(mEmailView.getText().toString(), mPasswordView.getText().toString());
                if (res.getCount() == 0) { Login(mEmailView.getText().toString(), mPasswordView.getText().toString()); }
                else { CallView(mEmailView.getText().toString(),myDB.GetNameUser(mEmailView.getText().toString())); }

            }
            else
            {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }
        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
        }
    }
    private void Login(String User, String Passw)
    {
        AsyncHttpClient Cnx = new AsyncHttpClient();
        RequestParams paramentros = new RequestParams();
        paramentros.put("U",User.toUpperCase());
        paramentros.put("P",Passw.toUpperCase());
        Cnx.post
        (ClssURL.getURL_login(), paramentros, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                if (statusCode==200)
                {
                    ArrayList<String> MeEncontro = obtDatosUS(new String(responseBody));
                    String[] items = MeEncontro.get(0).toString().split(",");
                    if (Integer.parseInt(items[4])==0){ Error404("Informacion Equivocada"); }
                    else
                    {
                        boolean Inserted = myDB.insertDataUS(items[0].toString(),items[1].toString(),items[2].toString(),items[3].toString());
                        if (Inserted == true){ CallView(items[1].toString(),items[3].toString()); }
                        else{ Error404("Error de Actualizacion de datos"); }
                    }
                }
                else{ Error404("Sin Cobertura de datos."); }
            }
            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) { Error404("Sin Cobertura de datos."); }
        }
        );
    }
    public void CallView(String User, String Nombre)
    {
        Intent MenuIntent = new Intent(LoginActivity.this,MainActivity.class);
        MenuIntent.putExtra("Vendedor",User.toUpperCase());
        myVar.setNameVendedor(Nombre.toUpperCase());
        myVar.setIdVendedor(User.toUpperCase());
        LoginActivity.this.startActivity(MenuIntent);
        finish();
    }
    public void Error404(String Error)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(Error)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }
    public ArrayList<String> obtDatosUS(String response)
    {
        ArrayList<String> listado = new ArrayList<String>();
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            String texto;

            for (int i=0; i<jsonArray.length(); i++)
            {
                texto = jsonArray.getJSONObject(i).getString("Key")+ "," +
                        jsonArray.getJSONObject(i).getString("Usr")+ "," +
                        jsonArray.getJSONObject(i).getString("Pss")+ "," +
                        jsonArray.getJSONObject(i).getString("NOMBRE")+ "," +
                        jsonArray.getJSONObject(i).getString("Correcto");
                listado.add(texto);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listado;
    }
}
