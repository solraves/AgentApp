package mg.agentsin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.makemyandroidapp.googleformuploader.GoogleFormUploader;


public class O2_ProductTourActivity extends AppCompatActivity {
    static final int data = 1;
    static final int NUM_PAGES = 4;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    LinearLayout circles;
   // Button skip;

     RadioGroup radioBoard;
     RadioGroup radioGroup1;
     RadioButton radioButton;
     RadioButton radioButton1;
    int selectedId;
    int selectedId2;

    Button done;
    ImageButton next;
    boolean isOpaque = true;
    AutoCompleteTextView fedu,fname;
    EditText mname;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.form_pager);


        // skip = Button.class.cast(findViewById(R.id.skip));
        /*skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTutorial();
            }
        }); */

        next = ImageButton.class.cast(findViewById(R.id.next));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });
 
        done = Button.class.cast(findViewById(R.id.done));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup);
                radioBoard = (RadioGroup) findViewById(R.id.radioBoard);
                //selectedId = radioGroup1.getCheckedRadioButtonId();
                selectedId2 = radioBoard.getCheckedRadioButtonId();
                ///radioButton = (RadioButton) findViewById(selectedId);
                radioButton1 = (RadioButton) findViewById(selectedId2);
                fname=(AutoCompleteTextView)findViewById(R.id.fname);
                mname=(EditText)findViewById(R.id.mname);
                fedu=(AutoCompleteTextView)findViewById(R.id.fedu);
                GoogleFormUploader uploader = new GoogleFormUploader("1wjJdQIfjTyYwzFhJtWY4PGKm1WGR0L_P2HY1ZUBOcDU");
                uploader.addEntry("1223471477", fname.getText().toString());
                uploader.addEntry("718902895", mname.getText().toString());
                uploader.addEntry("2099023069", fedu.getText().toString());
                uploader.addEntry("167759872", radioButton1.getText().toString());
                uploader.upload();
            }
        });
 
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
//                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
//                        pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if (position == NUM_PAGES - 2) {
                    //skip.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                } else if (position < NUM_PAGES - 2) {
                   // skip.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                } else if (position == NUM_PAGES - 1) {
                    //endTutorial();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
 
        buildCircles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pager!=null){
            pager.clearOnPageChangeListeners();
        }
    }

    private void buildCircles(){
        circles = LinearLayout.class.cast(findViewById(R.id.circles));
 
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);
 
        for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.green1);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }
 
        setIndicator(0);
    }
 
    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = (ImageView) circles.getChildAt(i);
                if(i == index){
                	circle.setColorFilter(getResources().getColor(R.color.tab_text_color_selected));
                }else {
                	circle.setColorFilter(getResources().getColor(android.R.color.transparent));
                }
            }
        }
    }
 
    private void endTutorial(){
        Intent landing = new Intent(O2_ProductTourActivity.this, LoginActivity.class);
        startActivity(landing);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
 
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }
 
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
 
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public Fragment getItem(int position) {
            O2a_ProductTourFragment tp = null;
            switch(position){
                case 0:
                    tp = O2a_ProductTourFragment.newInstance(R.layout.course_to_opt);
                    break;
                case 1:
                    tp = O2a_ProductTourFragment.newInstance(R.layout.student_details);
                    break;
                case 2:
                    tp = O2a_ProductTourFragment.newInstance(R.layout.family_background);
                    break;
                case 3:
                    tp = O2a_ProductTourFragment.newInstance(R.layout.howdoyouknow);
                    break;
//                case 4:
//                    tp = O2a_ProductTourFragment.newInstance(R.layout.o2e_tutorial5);
//                    break;
            }
 
            return tp;
        }
 
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    }
