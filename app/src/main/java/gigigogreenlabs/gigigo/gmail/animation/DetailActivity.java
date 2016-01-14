package gigigogreenlabs.gigigo.gmail.animation;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Name: Sergio Vasco
 * Date: 30/11/15.
 */
public class DetailActivity extends AppCompatActivity
    implements AppBarLayout.OnOffsetChangedListener {

  private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
  private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
  private static final int ALPHA_ANIMATIONS_DURATION              = 200;

  private boolean mIsTheTitleVisible          = false;
  private boolean mIsTheTitleContainerVisible = true;

  private LinearLayout mTitleContainer;
  private TextView mTitle;
  private AppBarLayout mAppBarLayout;
  private FrameLayout mFrameParallax;
  private Toolbar mToolbar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    bindActivity();

    mToolbar.setTitle("");
    mAppBarLayout.addOnOffsetChangedListener(this);

    setSupportActionBar(mToolbar);
    startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    initParallaxValues();
  }

  private void bindActivity() {
    mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
    mTitle          = (TextView) findViewById(R.id.main_textview_title);
    mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
    mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
    //mImageparallax  = (ImageView) findViewById(R.id.main_imageview_placeholder);
    mFrameParallax  = (FrameLayout) findViewById(R.id.main_framelayout_title);
  }

  private void initParallaxValues() {
    CollapsingToolbarLayout.LayoutParams petBackgroundLp =
        (CollapsingToolbarLayout.LayoutParams) mFrameParallax.getLayoutParams();

    petBackgroundLp.setParallaxMultiplier(0.3f);

    mFrameParallax.setLayoutParams(petBackgroundLp);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
    int maxScroll = appBarLayout.getTotalScrollRange();
    float percentage = (float) Math.abs(offset) / (float) maxScroll;

    handleAlphaOnTitle(percentage);
    handleToolbarTitleVisibility(percentage);
  }

  private void handleToolbarTitleVisibility(float percentage) {
    if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

      if(!mIsTheTitleVisible) {
        startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
        mIsTheTitleVisible = true;
      }

    } else {

      if (mIsTheTitleVisible) {
        startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
        mIsTheTitleVisible = false;
      }
    }
  }

  private void handleAlphaOnTitle(float percentage) {
    if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
      if(mIsTheTitleContainerVisible) {
        startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
        mIsTheTitleContainerVisible = false;
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      }

    } else {

      if (!mIsTheTitleContainerVisible) {
        startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
        mIsTheTitleContainerVisible = true;
      }
    }
  }

  public static void startAlphaAnimation (View v, long duration, int visibility) {
    AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
        ? new AlphaAnimation(0f, 1f)
        : new AlphaAnimation(1f, 0f);

    alphaAnimation.setDuration(duration);
    alphaAnimation.setFillAfter(true);
    v.startAnimation(alphaAnimation);
  }
}
