package ua.zt.mezon.gitapiv3query;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import ua.zt.mezon.gitapiv3query.datacontroller.RestManager;
import ua.zt.mezon.gitapiv3query.model.adapter.FlowerAdapter;
import ua.zt.mezon.gitapiv3query.presenter.GetPresenter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final boolean SHOW_DEBUG = true;
    private Animation fab_show;
    ProgressBar mLoading;
    private GetPresenter mPresenter;


    private RecyclerView mRecyclerView;
    private RestManager mManager;
    private FlowerAdapter mFlowerAdapter;

    private Button mReload;
    private ProgressDialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab_show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fabedit_show);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fab in action", Snackbar.LENGTH_LONG)
                        .setAction("Wanna see all POI? ",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // тип показ
                                        if (SHOW_DEBUG)
                                            Toast.makeText(v.getContext(), "Yep, this all action ", Toast.LENGTH_SHORT)
                                                    .show();
                                        fab.startAnimation(fab_show);
                                    }
                                }).show();
            }
        });
        mLoading= (ProgressBar) findViewById(R.id.progress_bar);
        mPresenter = new GetPresenter(this);
        mPresenter.loadRepositories(false);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
