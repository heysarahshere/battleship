package com.scc.battleship;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersListAdapter extends ArrayAdapter<UserPreferences> {
  private Activity activity;
  private ArrayList<UserPreferences> allUsers;
  private static LayoutInflater inflater = null;

  public UsersListAdapter(Activity activity, int textViewResourceId, ArrayList<UserPreferences> _allUsers) {
    super(activity, textViewResourceId, _allUsers);
    try {
      this.activity = activity;
      this.allUsers = _allUsers;

      inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    } catch (Exception e) {

    }
  }

  public int getCount() {
    return allUsers.size();
  }

  public UserPreferences getItem(UserPreferences position) {
    return position;
  }

  public long getItemId(int position) {
    return position;
  }

  public static class ViewHolder {
    public ImageView onlineImage, avatarImageView;
    public TextView display_description;

  }

  public View getView(int position, View convertView, ViewGroup parent) {
    View vi = convertView;
    final ViewHolder holder;
    try {
      if (convertView == null) {
        vi = inflater.inflate(R.layout.view_row, null);
        holder = new ViewHolder();

        holder.avatarImageView = (ImageView) vi.findViewById(R.id.avatarImageView);
        holder.onlineImage = (ImageView) vi.findViewById(R.id.onlineImage);
        holder.display_description = (TextView) vi.findViewById(R.id.display_description);


        vi.setTag(holder);
      } else {
        holder = (ViewHolder) vi.getTag();
      }

      if(allUsers.get(position).getOnline() == false){
        holder.onlineImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.offline));
      } else {
        holder.onlineImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.online));
      }



      Picasso.with( getContext() ).load( "http://www.battlegameserver.com/" + allUsers.get(position).getAvatarImage() ).into( holder.avatarImageView );
//      holder.avatarImageView.setI(allUsers.get(position).getAvailable().toString());
      holder.display_description.setText(allUsers.get(position).getAvatarName());


    } catch (Exception e) {


    }
    return vi;
  }
}
