# whatsAppGroupContactSelector
whats app like group contact add/remove functionality sample

Sampe app functionality Video link:
<iframe width="665" height="399" src="https://www.youtube.com/embed/HdnWH7tGRA8" frameborder="0" allowfullscreen></iframe>

 

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
