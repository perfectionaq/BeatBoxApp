package kamalqawlaq.com.beatboxapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kamalqawlaq.com.beatboxapp.databinding.FragmentBeatBoxBinding;
import kamalqawlaq.com.beatboxapp.databinding.ListItemSoundBinding;

/**
 * Created by tauta on 6/8/18.
 */

public class BeatBoxFragment extends Fragment {

    private BeatBox beatBox;

    public static BeatBoxFragment newInstance() {

        Bundle args = new Bundle();

        BeatBoxFragment fragment = new BeatBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beatBox = new BeatBox(getActivity());
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.recyclerView.setAdapter(new SoundAdapter(beatBox.getSounds()));

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beatBox.release();
    }

    private class SoundHolder extends RecyclerView.ViewHolder{
        private ListItemSoundBinding mBinding;

        public SoundHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(beatBox));
        }

        public void bind(Sound sound){
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings();
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds){
            mSounds = sounds;
        }
        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater =     LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_sound, parent, false);


            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
