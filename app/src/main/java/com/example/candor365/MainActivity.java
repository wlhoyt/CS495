package com.example.candor365;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.ErrorCodes;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = homepageFrag.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = calendarFrag.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = settingFrag.class;
                break;
            default:
                fragmentClass = homepageFrag.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
}

//public class MainActivity extends AppCompatActivity {
//    private FirebaseAuth mAuth;
//    private static final int RC_SIGN_IN = 472;
//
//    private Button googleSignInButton;
//
//    private View.OnClickListener googleSignInListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            googleSignInButtonClicked();
//        }
//    };
//
//    private void googleSignInButtonClicked(){
//        signIn();
//    }
//
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////
////        mAuth = FirebaseAuth.getInstance();
////    }
//
//    // [START on_start_check_user]
////    @Override
////    public void onStart() {
////        super.onStart();
////        googleSignInButton = (Button) findViewById(R.id.googleSignInButton);
////        googleSignInButton.setOnClickListener(googleSignInListener);
////        if (mAuth.getCurrentUser() != null) {
////            //signed in
////            Toast.makeText(getApplicationContext(),"Already Signed In", Toast.LENGTH_SHORT).show();
////            startActivity(SignedInActivity.createIntent(this, null));
////        } else {
////            // not signed in
////            startActivityForResult(
////                    // Get an instance of AuthUI based on the default app
////                    AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build())).build(),
////                    RC_SIGN_IN);
////        }
////    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            IdpResponse response = IdpResponse.fromResultIntent(data);
//
//            // Successfully signed in
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Result Code "+ resultCode, Toast.LENGTH_SHORT).show();
//                startActivity(SignedInActivity.createIntent(this, response));
//                Toast.makeText(this, "Logging IN", Toast.LENGTH_SHORT).show();
//                finish();
//            } else {
//                // Sign in failed
//                if (response == null) {
//                    // User pressed back button
//                    Toast.makeText(this, "Login cancelled", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
//                    Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
//
//                    return;
//                }
//
//                Toast.makeText(this, "It just Barfed " + resultCode, Toast.LENGTH_SHORT).show();
//                //Log.e(TAG, "Sign-in error: ", response.getError());
//            }
//        }
//    }
//
//    @NonNull
//    public static Intent createIntent(@NonNull Context context, @Nullable IdpResponse response) {
//        return new Intent().setClass(context, MainActivity.class);
//    }
//
//
//    private void signIn(){
//        startActivityForResult(
//                // Get an instance of AuthUI based on the default app
//                AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build())).build(),
//                RC_SIGN_IN);
//    }
//}
