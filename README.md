# whatsAppGroupContactSelector
whats app like group contact add/remove functionality sample

Sampe app functionality Video link:

<video width="320" height="240" controls>
  <source src=https://www.youtube.com/embed/HdnWH7tGRA8" >

<iframe width="420" height="315"
src="https://www.youtube.com/embed/XGSy3_Czz8k">
</iframe>
 

To add animation for adding and removing recyler view , following two method are used in adapter of this sample app:

 

1 .

// Insert a new item to the RecyclerView on a predefined position or just send 0 as position if you want to add items at the end of list
public void insert(int position, UserList data) {
    mModels.add(data);
    notifyItemInserted(position);
}

2. 
// Remove a RecyclerView item containing a specified Data object
public void remove(UserList data) {
    int position = mModels.indexOf(data);
    mModels.remove(position);
    notifyItemRemoved(position);
}
 

 

Then just have the need to call these methods from recyler view on item click listener or we can call them from adapter imageView / Textview click listener as per requirement.

in this sample i am calling these two methods from Activity on recyler view on item click listener.
