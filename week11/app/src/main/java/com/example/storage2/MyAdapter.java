package com.example.storage2;

import android.content.Context;
import android.icu.text.SymbolTable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<Comment> commentList; // 评论列表
    private Context mContext;
    private String username; // 当前页面的用户名

    public MyAdapter(Context context, List<Comment> list, String username) {
        mContext = context;
        commentList = list;
        this.username = username;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // 向外提供接口
    public interface onItemLikeListener {
        void onLikeClick(int i);
    }

    private onItemLikeListener monItemLikeListener;

    public void setOnItemLikeClickListener(onItemLikeListener monItemLikeListener) {
        this.monItemLikeListener = monItemLikeListener;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            viewHolder.commentName = (TextView)view.findViewById(R.id.commentName);
            viewHolder.commentContent = (TextView)view.findViewById(R.id.commentContent);
            viewHolder.commentTime = (TextView)view.findViewById(R.id.commentTime);
            viewHolder.likeNumber = (TextView)view.findViewById(R.id.likeNumber);
            viewHolder.img = (ImageView)view.findViewById(R.id.img);
            viewHolder.likeImg = (ImageView)view.findViewById(R.id.likeImg);

            viewHolder.likeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monItemLikeListener.onLikeClick(i);
                }
            });
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }

        myDB mydb = new myDB(view.getContext());
        viewHolder.commentName.setText(commentList.get(i).getCommentName());
        viewHolder.commentTime.setText(commentList.get(i).getCommentTime());
        viewHolder.commentContent.setText(commentList.get(i).getCommentContent());

        // 从数据库中获取当前评论的点赞数
        viewHolder.likeNumber.setText(mydb.getLikeNumberByCommentId(commentList.get(i).getId()) + "");
        viewHolder.img.setImageBitmap(commentList.get(i).getImg());
        viewHolder.likeImg.setImageBitmap(commentList.get(i).getLikeImg());

        // 控制点赞状态
        int commentid = commentList.get(i).getId();
        // 从数据库获取点赞当前评论的用户ID列表
        ArrayList<Integer> likeduserlist = mydb.getLikedListById(commentid);
        if (likeduserlist != null) {
            for (int k = 0; k < likeduserlist.size(); k++) {
                System.out.println("comment: " + commentid + "user: "+ likeduserlist.get(k) + "present: " + mydb.getUserIdByName(username));
                // 如果当前用户在点赞列表中，则设置当前评论的点赞状态是已点赞（red）
                if (likeduserlist.get(k) == mydb.getUserIdByName(username)) {
                    viewHolder.likeImg.setImageResource(R.drawable.red);
                }
            }
        }

        return view;
    }

    class ViewHolder {
        TextView commentName;
        TextView commentContent;
        TextView commentTime;
        TextView likeNumber;
        ImageView img;
        ImageView likeImg;
        Button mButton;
    }
}
