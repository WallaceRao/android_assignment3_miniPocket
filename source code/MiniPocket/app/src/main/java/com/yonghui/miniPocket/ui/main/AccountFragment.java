package com.yonghui.miniPocket.ui.main;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yonghui.miniPocket.R;
import com.yonghui.miniPocket.bean.Count;
import com.yonghui.miniPocket.db.SqliteManage;
import com.yonghui.miniPocket.ui.addAccount.AddCountActivity;
import com.yonghui.miniPocket.ui.accountDetail.CountDetialActivity;
import com.yonghui.miniPocket.ui.input.InputActivity;
import com.yonghui.miniPocket.utils.DialogUtils;
import com.yonghui.miniPocket.utils.FormatUtils;
import com.yonghui.miniPocket.view.swipemenulistview.SwipeMenu;
import com.yonghui.miniPocket.view.swipemenulistview.SwipeMenuCreator;
import com.yonghui.miniPocket.view.swipemenulistview.SwipeMenuItem;
import com.yonghui.miniPocket.view.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yonghui Rao
 */
public class AccountFragment extends Fragment {
    private TextView mTotal;
    private SwipeMenuListView mLv;
    private ImageButton mIb_add, mIb_change;
    private AcountAdapter mAdapter;
    private List<Count> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_count, null);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        double total = 0;
        mList = new ArrayList<>();
        SqliteManage.QueryResult result = SqliteManage.getInstance(getActivity()).query("count", null, null);
        while (result.cursor.moveToNext()) {
            Count count = new Count();
            count.setCount(result.cursor.getString(result.cursor.getColumnIndex("count")));
            count.setNumber(result.cursor.getDouble(result.cursor.getColumnIndex("money")));
            total += result.cursor.getDouble(result.cursor.getColumnIndex("money"));
            mList.add(count);
        }
        result.cursor.close();
        result.db.close();
        mTotal.setText(FormatUtils.format2d(total));
        if (mList.size() == 0) {
            Toast.makeText(getActivity(), "You have no account yet, click to add an account！", Toast.LENGTH_SHORT).show();
        }
        mAdapter = new AcountAdapter(mList, getActivity());
        mLv.setAdapter(mAdapter);
        intItemMenu();
    }

    private void initView(View view) {
        mLv = (SwipeMenuListView) view.findViewById(R.id.count_lv_count);
        mIb_add = (ImageButton) view.findViewById(R.id.count_ib_add);

        mTotal = (TextView) view.findViewById(R.id.fragment_count_tv_total);
        mIb_add.setOnClickListener(mListener);

    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            Intent intent = null;
            switch (v.getId()) {
                case R.id.count_ib_add:
                    intent = new Intent(AccountFragment.this.getActivity(), AddCountActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    };

    private void intItemMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem modify = new SwipeMenuItem(getActivity().getApplicationContext());
                modify.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                modify.setWidth(dp2px(80));
                modify.setTitleSize(18);
                menu.addMenuItem(modify);
                modify.setIcon(R.drawable.ic_modify);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(dp2px(80));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        mLv.setMenuCreator(creator);
        mLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final int i = position;
                switch (index) {
                    case 0:
                        DialogUtils.show(getActivity(), "Apply change?", new DialogUtils.DialogCallBack() {
                            @Override
                            public void doListener() {
                                Intent intent = new Intent(AccountFragment.this.getActivity(), InputActivity.class);
                                startActivityForResult(intent, i);
                            }
                        });
                        break;
                    case 1:
                        DialogUtils.show(getActivity(), "Delete account?", new DialogUtils.DialogCallBack() {
                            @Override
                            public void doListener() {
                                SqliteManage.getInstance(getActivity()).delteItem
                                        ("count", "count=?", new String[]{mList.get(i).getCount()});
                                initData();
                            }
                        });
                        break;
                }
                return false;
            }
        });

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CountDetialActivity.class);
                intent.putExtra("count", mList.get(position).getCount());
                startActivity(intent);
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            ContentValues values = new ContentValues();
            values.put("money", data.getStringExtra("msgmoney"));
            SqliteManage.getInstance(getActivity()).updateItem
                    ("count", "count=?", new String[]{mList.get(requestCode).getCount() + ""}, values);
        }
    }
}
