package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.codebreaker.adapter.SimpleGuessAdapter;
import edu.cnm.deepdive.codebreaker.databinding.FragmentPlayBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.GameViewModel;
import org.jetbrains.annotations.NotNull;

public class PlayFragment extends Fragment implements TextWatcher {

  private FragmentPlayBinding binding;
  private GameViewModel viewModel;
  private int codeLength;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentPlayBinding.inflate(inflater, container, false);
    binding.submit.setOnClickListener((v) -> {
      /* TODO Submit guess from spinners to view model. */
      viewModel.submitGuess(binding.guess.getText().toString().toUpperCase().trim());
      binding.guess.setText("");
    });
    binding.guess.addTextChangedListener(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull @NotNull View view,
      @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity()).get(GameViewModel.class);
    viewModel.getGame().observe(getViewLifecycleOwner(), (game) -> {
      // TODO Update make game display better
      codeLength = game.getLength();
      if (game.isSolved()) {
        binding.guess.setEnabled(false);
        binding.submit.setEnabled(false);
      } else {
        binding.guess.setEnabled(true);
        enforceSubmitConditions();
      }
      SimpleGuessAdapter adapter = new SimpleGuessAdapter(getContext(), game.getGuesses());
      binding.guessList.setAdapter(adapter);
    });
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    enforceSubmitConditions();  }

  private void enforceSubmitConditions() {
    binding.submit.setEnabled(binding.guess.getText().toString().trim().length() == codeLength);
  }
}