package com.yao.sharedelementtransition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yao.sharedelementtransition.data.Person;
import com.yao.sharedelementtransition.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.rv_persons)
    RecyclerView rvPersons;

    private List<Person> mPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPersons = new ArrayList<>();
        for (int i=0; i<20; i++){
            Person person = new Person("rebot" + i, null, R.drawable.default_avatar_1, "I am rebot " + i);
            person.avatar = i%2 == 0 ?
                    "https://vignette3.wikia.nocookie.net/shin-chan/images/3/35/%E5%B9%BF%E5%BF%97.png/revision/latest?cb=20131101030708&path-prefix=zh":
                    "https://vignette1.wikia.nocookie.net/shin-chan/images/2/25/%E7%99%BD.png/revision/latest?cb=20131105085828&path-prefix=zh";
            mPersons.add(person);
        }

        rvPersons.setLayoutManager(new LinearLayoutManager(this));
        rvPersons.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvPersons.setAdapter(new PersonAdapter());
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonItemHolder>{

        @Override
        public PersonItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false));
        }

        @Override
        public void onBindViewHolder(final PersonItemHolder holder, int position) {
            final Person person = mPersons.get(position);
            if (TextUtils.isEmpty(person.avatar)) {
                if (position % 2 == 0) {
                    Glide.with(MainActivity.this).load(R.drawable.default_avatar_1).into(holder.mIvAvatar);
                } else {
                    Glide.with(MainActivity.this).load(R.drawable.default_avatar_2).into(holder.mIvAvatar);
                }
            } else {
                Glide.with(MainActivity.this).load(person.avatar).into(holder.mIvAvatar);
            }
            holder.mTvName.setText(person.name);
            holder.mTvDesc.setText(person.desc);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PersonDetailActivity.class);
                    intent.putExtra(PersonDetailActivity.EXTRA_PERSON, person);
                    intent.putParcelableArrayListExtra(PersonDetailActivity.EXTRA_ELEMENTS,
                            ViewUtil.getSharedElement(MainActivity.this, holder.mIvAvatar, holder.mTvName, holder.mTvDesc));
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPersons.size();
        }
    }

    public static class PersonItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView mIvAvatar;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;

        public PersonItemHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
