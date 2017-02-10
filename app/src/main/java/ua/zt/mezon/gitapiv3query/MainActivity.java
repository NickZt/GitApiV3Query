package ua.zt.mezon.gitapiv3query;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import ua.zt.mezon.gitapiv3query.presenter.GetPresenter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final boolean SHOW_DEBUG = true;
    private Animation fab_show;

    private GetPresenter mPresenter=GetPresenter.getInstance();

    ProgressBar mLoading;
    Toolbar toolbar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fabedit_show);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fab in action", Snackbar.LENGTH_LONG)
                        .setAction("Wanna turn on designers fantasy?",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // turn on designers fantasy
                                        if (SHOW_DEBUG)
                                            Toast.makeText(v.getContext(), "Yep, this all action ", Toast.LENGTH_SHORT)
                                                    .show();
                                        fab.startAnimation(fab_show);
                                    }
                                }).show();
            }
        });
        mLoading= (ProgressBar) findViewById(R.id.progress_bar);
        mPresenter.iniGetPresenter(this);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               if (query != null) {
                   mPresenter.loadRepositories(query, false);
               }
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // your text view here
//                textView.setText(newText);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                textView.setText(query);
//                return true;
//            }
//        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgress(boolean isShow) {
        mLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void onRefreshDone() {
    }

    public void onRequestFail(String error) {
        Log.w(TAG, "Error: " + error);
        Toast.makeText(this, "Error. Please try again!", Toast.LENGTH_SHORT).show();
    }
}
