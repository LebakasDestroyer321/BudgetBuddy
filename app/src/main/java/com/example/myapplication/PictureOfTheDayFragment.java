package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.FragmentPictureOfTheDayBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PictureOfTheDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PictureOfTheDayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentPictureOfTheDayBinding binding;
    private MainViewModel mainViewModel;

    public PictureOfTheDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PictureOfTheDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PictureOfTheDayFragment newInstance(String param1, String param2) {
        PictureOfTheDayFragment fragment = new PictureOfTheDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false);

        mainViewModel.getTitle().observe(getViewLifecycleOwner(), title -> binding.textView3.setText(title));
        mainViewModel.getExplanation().observe(getViewLifecycleOwner(), explanation -> binding.textView6.setText(explanation));
        mainViewModel.getImageUrl().observe(getViewLifecycleOwner(), imageUrl -> Glide.with(requireContext()).load(imageUrl).into(binding.apiImageView));

        return inflater.inflate(R.layout.fragment_picture_of_the_day, container, false);
    }
}