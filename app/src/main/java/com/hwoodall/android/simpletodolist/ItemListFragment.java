package com.hwoodall.android.simpletodolist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;


public class ItemListFragment extends Fragment {

    private RecyclerView mItemRecyclerView;
    private ItemAdapter mAdapter;
    private TextView mTitleTextView;
    private TextView mDateTextView;

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ListItem mListItem;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date);
        }

        public void bind(ListItem item) {
            mListItem = item;
            mTitleTextView.setText(mListItem.getTitle());
            SimpleDateFormat format = new SimpleDateFormat("E, MMMM dd, yyyy");
            mDateTextView.setText(format.format(mListItem.getDate()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = ToDoActivity.newIntent(getActivity(), mListItem.getId());
            startActivity(intent);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        private List<ListItem> mItems;
        private ImageView mImageView;

        public ItemAdapter(List<ListItem> items) {
            mItems = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {

            ListItem item = mItems.get(position);

            mImageView = (ImageView) holder.itemView.findViewById(R.id.is_solved_image);
            if  (item.isSolved()) {
                mImageView.setImageResource(R.drawable.ic_check_box_complete);
            } else {
                mImageView.setImageResource(R.drawable.ic_check_box_incomplete);
            }

            holder.bind(item);
        }


        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public void setItems(List<ListItem> items) {
            mItems = items;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mItemRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mItemRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_item_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_item:
                ListItem listItem = new ListItem();
                ItemBank.get(getActivity()).addItem(listItem);
                Intent intent = ToDoActivity.newIntent(getActivity(), listItem.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        ItemBank itemBank = ItemBank.get(getActivity());
        List<ListItem> items = itemBank.getItems();

        if(mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            mItemRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }
    }
}
