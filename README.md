# whatsAppGroupContactSelector
whats app like group contact add/remove functionality sample

<img src="https://drive.google.com/file/d/0B31j9kjWGFfHOEtURXpBbDIzSlE/view" alt="Mountain View" style="width:304px;height:228px;">

To add animation for adding and removing recyler view , following two method are used in adapter of this sample app:

 

1 . <B> // Add item containing a specified Data object </B>
public void insert(int position, UserList data)

2. <B> // Remove a RecyclerView item containing a specified Data object </B>
public void remove(UserList data) 
   
 

 

Then just have the need to call these methods from recyler view on item click listener or we can call them from adapter imageView / Textview click listener as per requirement.

in this sample i am calling these two methods from Activity on recyler view on item click listener.
