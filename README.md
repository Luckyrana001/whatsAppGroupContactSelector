# whatsAppGroupContactSelector
whats app like group contact add/remove functionality sample


<img src="https://github.com/Luckyrana001/whatsAppGroupContactSelector/blob/master/anim.gif" width="350"/>
<br>
To add animation for adding and removing recyler view , following two method are used in adapter of this sample app: 

1 . <B> // Add item containing a specified Data object </B></br>
public void insert(int position, UserList data)

2. <B> // Remove a RecyclerView item containing a specified Data object </B></br>
public void remove(UserList data) 
   
 Above two methods only show add and remove animation but to show shrink and grow animation we have used following lines of code:
 <B> SHRINK ITEM ANIMATION ALONG WITH ITEM REMOVE  </B>
 <code>   RelativeLayout container = (RelativeLayout)view.findViewById(R.id.container);
                        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f);
                        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f);

                        ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(container, scaleYholder, scaleXholder);
                        animateProfilePic.setDuration(300);
                        animateProfilePic.start();
                        </code>
                        
  <BR> <B>  ITEM GROW ANIMATION ALONG WITH NEW ITEM ADD </B>
                      
  <code>   RelativeLayout container = (RelativeLayout)view.findViewById(R.id.container);
                        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f);
                        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f);

                        ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(container, scaleYholder, scaleXholder);
                        animateProfilePic.setDuration(300);
                        animateProfilePic.start();
                        </code>

Then just have the need to call these methods from recyler view on item click listener or we can call them from adapter imageView / Textview click listener as per requirement.

in this sample i am calling these two methods from Activity on recyler view on item click listener.
