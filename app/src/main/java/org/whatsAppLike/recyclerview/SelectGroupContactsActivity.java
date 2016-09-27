package org.whatsAppLike.recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.whatsAppLike.recyclerview.adapter.SearchUserAdapter;
import org.whatsAppLike.recyclerview.adapter.SelectGroupContactsAdaper;
import org.whatsAppLike.recyclerview.common.DividerItemDecoration;
import org.whatsAppLike.recyclerview.common.FeedItemAnimator;
import org.whatsAppLike.recyclerview.common.SearchCallback;
import org.whatsAppLike.recyclerview.model.UserList;
import org.whatsAppLike.recyclerview.model.UserListResponseModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rana lucky
 */
public class SelectGroupContactsActivity extends AppCompatActivity implements SearchCallback,  AdapterView.OnItemSelectedListener,
        SearchView.OnQueryTextListener {
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    View container;
    boolean playAnimations = true;
    LinearLayoutManager horizontalLayoutManagaer;
    TextView noRecordFound;
    int count = 0;
    private RecyclerView mainListRecyclerView,selectedListRecylerView;
    private SelectGroupContactsAdaper mAdapter;
    private SearchUserAdapter selectedUserAdapter;
    private ArrayList<UserList> mModels = new ArrayList<UserList>();
    ArrayList<UserList> selectedModelList = new ArrayList<UserList>();
    Toolbar toolbar;
  //  MenuItem doneBtn;
    private boolean pendingIntroAnimation = true;


    ImageView backBtn;
    TextView itemCount,doneBtn;
    SearchView searchV;
    FloatingActionButton fabCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_contact_layout);

        initLayout();
      /* Intialize Toolbar */
        initToolBar();

        setMainListAdapter();

        setUpSelectedListAdapter(selectedModelList);

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
          mAdapter.notifyDataSetChanged();
        }
    }


    /*alpha change animation on first time window will get focus*/
@Override
public void onWindowFocusChanged(boolean hasFocus)
{
    super.onWindowFocusChanged(hasFocus);
    if(hasFocus)
    {
       // showContainer();
       // playAnimations = false;
    }

}

    private void showContainer() {
        container.animate().alpha(1f).setDuration(500);
    }




    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setTitle(selectedModelList.size() +" / "+mModels.size());

        setSupportActionBar(toolbar);
        backBtn   = (ImageView)toolbar.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        itemCount = (TextView)toolbar.findViewById(R.id.itemCount);
        /*set the intial count of selected / total count*/
        increaseCount();

        doneBtn   = (TextView)toolbar.findViewById(R.id.doneTv);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
        searchV   = (SearchView)toolbar.findViewById(R.id.searchView);


        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            setToolbar();
        }



}

    public void increaseCount(){
        itemCount.setText("New Group\n"+selectedModelList.size() +" of "+mModels.size()+" selected");
        /*Animation sgAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim);
        itemCount.startAnimation(sgAnimation);*/

    }

    public void onBackPressed()
    {
        Intent callAgain = new Intent(getApplicationContext(),SelectGroupContactsActivity.class);
        callAgain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(callAgain);
    }
    private void initLayout() {

        fabCreate = (FloatingActionButton) findViewById(R.id.btnCreate);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Button clicked",Toast.LENGTH_SHORT).show();
            }
        });
        container = findViewById(R.id.container);
        mainListRecyclerView = (RecyclerView) findViewById(R.id.main_list_recyclerView);
        selectedListRecylerView = (RecyclerView) findViewById(R.id.selectedListRecylerView);

        noRecordFound = (TextView) findViewById(R.id.noRecordFound);
        mModels = new ArrayList<UserList>();




        mainListRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mainListRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                // Toast.makeText(getActivity(), position + " is selected!", Toast.LENGTH_SHORT).show();

                TextView mText = (TextView) view.findViewById(R.id.idTv);
                String value = mText.getText().toString().trim();
                int selPos = 0;
                boolean itemStatus = false;
                UserList newModel = null;
                int removedItemPosition = 0;

                for(int i = 0; i < mModels.size();i++)
                {
                    UserList model = mModels.get(i);

                    if(value.equals(model.getId()))
                    {
                        if(model.isCheckStatus())
                        {
                            model.setCheckStatus(false);
                            count--;
                            itemStatus = false;
                            newModel = model;
                            removedItemPosition = i;
                        }
                        else
                        {
                            model.setCheckStatus(true);
                            count++;
                            itemStatus = true;
                            newModel = model;
                        }
                        mAdapter.notifyDataSetChanged();

                        break;
                    }
                }


       /*         Insert new items to horizonontal list view */
                if(itemStatus) {
                    int pos = selectedUserAdapter.insert(newModel);
                    selectedListRecylerView.scrollToPosition(selectedModelList.size()-1);

                }
                else
                {

                  int p = selectedUserAdapter.removeItemPos(newModel);
                    View view3 =  horizontalLayoutManagaer.findViewByPosition(p);

                    if(view3!=null) {
                        //ImageView profilePic = (ImageView) view3.findViewById(R.id.profilePic);
                        RelativeLayout container = (RelativeLayout)view3.findViewById(R.id.container);
                        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f);
                        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f);

                        ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(container, scaleYholder, scaleXholder);
                        animateProfilePic.setDuration(300);
                        animateProfilePic.start();
                    }


                  selectedUserAdapter.remove(newModel);
                }
               /*set the intial count of selected / total count*/
                increaseCount();

                showHideHorizontalView();
       }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        selectedListRecylerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), selectedListRecylerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextView id = (TextView)view.findViewById(R.id.idTv);
                //ImageView profilePic = (ImageView)view.findViewById(R.id.profilePic);
                RelativeLayout container = (RelativeLayout)view.findViewById(R.id.container);

                boolean itemStatus = false;
                UserList newModel = null;

                for(int i = 0; i < mModels.size();i++)
                {
                    UserList model = mModels.get(i);

                    if(id.getText().toString().trim().equals(model.getId()))
                    {
                        if(model.isCheckStatus())
                        {
                            model.setCheckStatus(false);
                            count--;
                            itemStatus = false;
                            newModel = model;
                        }
                        else
                        {
                            model.setCheckStatus(true);
                            count++;
                            itemStatus = true;
                            newModel = model;

                        }
                        mAdapter.notifyDataSetChanged();

                        break;
                    }
                }

                if(itemStatus) {
                    selectedUserAdapter.insert(newModel);

                }
                else
                {
                    PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X,0f);
                    PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y,0f);

                    ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(container,scaleYholder, scaleXholder);
                    animateProfilePic.setDuration(300);
                    animateProfilePic.start();
                    selectedUserAdapter.remove(newModel);

                }

                showHideHorizontalView();
             /*set the intial count of selected / total count*/
                increaseCount();


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    private void showHideHorizontalView() {
        if(selectedModelList.size()>0)
        {

          /*  //Load animation
            Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),   R.anim.slide_up);
             // Start animation
            mainListRecyclerView.startAnimation(slide_down);
*/
            // selectedListRecylerView.setVisibility(View.VISIBLE);
            PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f);
            PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f);

            ObjectAnimator animateView = ObjectAnimator.ofPropertyValuesHolder(selectedListRecylerView, scaleYholder, scaleXholder);
            animateView.setDuration(500);
            animateView.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    selectedListRecylerView.setVisibility(View.VISIBLE);
                }
            });
            animateView.start();


        }
        else
        {
            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            // Start animation
            mainListRecyclerView.startAnimation(slide_up);

            PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f);
            PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f);

            ObjectAnimator animateView = ObjectAnimator.ofPropertyValuesHolder(selectedListRecylerView, scaleYholder, scaleXholder);
            animateView.setDuration(500);
            animateView.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    selectedListRecylerView.setVisibility(View.GONE);

                }
            });
            animateView.start();
        }
    }


    private void setUpSelectedListAdapter(ArrayList<UserList> list) {
         horizontalLayoutManagaer      = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        selectedListRecylerView.setLayoutManager(horizontalLayoutManagaer);
        selectedUserAdapter = new SearchUserAdapter(getApplicationContext(), list,this);

/*
        Animation to increase add remove duration
*/
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        selectedListRecylerView.setItemAnimator(itemAnimator);

        selectedListRecylerView.setAdapter(selectedUserAdapter);



    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainListRecyclerView.smoothScrollToPosition(0);
                mAdapter.showLoadingView();
            }
        }, 500);
    }
    private void setMainListAdapter() {
        mAdapter = new SelectGroupContactsAdaper(getApplicationContext(), mModels);

       // if (mModels.size() > 0) {
            mainListRecyclerView.setHasFixedSize(true);
           // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };

            mainListRecyclerView.setLayoutManager(mLayoutManager);
            mainListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            //mainListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mainListRecyclerView.setItemAnimator(new FeedItemAnimator());

            mainListRecyclerView.setAdapter(mAdapter);

          /*  noRecordFound.setVisibility(View.GONE);
        } else {
            noRecordFound.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void changeCheckedStatus(String  id) {

    }




    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    private void setToolbar() {
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.fab_margin));

        int actionbarSize = dpToPx(56);
        backBtn.setTranslationY(-actionbarSize);
        itemCount.setTranslationY(-actionbarSize);
        searchV.setTranslationY(-actionbarSize);
        doneBtn.setTranslationY(-actionbarSize);

        toolbar.animate()
                    .translationY(0)
                    .setDuration(ANIM_DURATION_TOOLBAR)
                    .setStartDelay(300) ;
        backBtn.animate()
                    .translationY(0)
                    .setDuration(ANIM_DURATION_TOOLBAR)
                    .setStartDelay(400);
        itemCount.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500);

        searchV.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(600);
         doneBtn.animate()
                    .translationY(0)
                    .setStartDelay(700)
                    .setDuration(ANIM_DURATION_TOOLBAR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        setUpList();
                        /*set the intial count of selected / total count*/
                        increaseCount();

                        mAdapter. updateItems(mModels);

                        fabCreate.animate()
                                .translationY(0)
                                .setInterpolator(new OvershootInterpolator(1.f))
                                .setStartDelay(500)
                                .setDuration(ANIM_DURATION_FAB)
                                .start();
                    }
                })
                .start();
                   /* .setStartDelay(500);
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {

                            setUpList();
                            toolbar.setTitle(selectedModelList.size() +" / "+mModels.size());

                           mAdapter. updateItems(mModels);

                        }
                    })
                   .start();*/

    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_create_menu, menu);
        menu.removeItem(R.id.Next);

        doneBtn  = menu.findItem(R.id.Done);
        doneBtn.setActionView(R.layout.menu_item_view);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            setToolbar();
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.Done) {
            Toast.makeText(getApplicationContext(), "Done clicked.", Toast.LENGTH_SHORT).show();
            return true;

        }
        return  false;
    }*/
    @Override
    public boolean onQueryTextChange(String query) {
        if(mModels.size()>0) {

            final ArrayList<UserList> filteredModelList = filter(mModels, query);
            mAdapter.animateTo(filteredModelList);
            mainListRecyclerView.scrollToPosition(0);
            return true;
        }
        return false;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<UserList> filter(List<UserList> models, String query) {
        query = query.toLowerCase();

        final ArrayList<UserList> filteredModelList = new ArrayList<UserList>();
        for (UserList model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }






    private void setUpList() {

       /* fetching list view data from json file which is stored in asset folder*/
        final String customGSON = "{\"userList\":" + sampleReadMethod() + "}";

        UserListResponseModel userModel = new Gson().fromJson(customGSON, UserListResponseModel.class);
        mModels = userModel.getList();

        int j=0;
        for( j = 0;j < mModels.size()-1;j++);
        {

            UserList model = mModels.get(j);
            model.setCheckStatus(false);
            mModels.set(j,model);
        }
    }


    public String sampleReadMethod()
    {
        String json = null;
        try {
            InputStream is;

            is = getAssets().open("dev_user_list.json");


            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;


    }








}