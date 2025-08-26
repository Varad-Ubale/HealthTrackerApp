package com.vktech.healthtrackerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView tvDate, tvSteps, tvWater, tvSleep;
    private ProgressBar pbSteps, pbWater, pbSleep;
    private Storage storage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        tvDate = v.findViewById(R.id.tvDate);
        tvSteps = v.findViewById(R.id.tvSteps);
        tvWater = v.findViewById(R.id.tvWater);
        tvSleep = v.findViewById(R.id.tvSleep);
        pbSteps = v.findViewById(R.id.pbSteps);
        pbWater = v.findViewById(R.id.pbWater);
        pbSleep = v.findViewById(R.id.pbSleep);

        storage = new Storage(requireContext());

        // Set today's date
        String today = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(new Date());
        tvDate.setText(today);

        loadLatestData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when the fragment becomes visible again
        loadLatestData();
    }

    private void loadLatestData() {
        // Set max values
        int maxSteps = 10000;
        int maxWater = 2000;
        int maxSleep = 8;
        pbSteps.setMax(maxSteps);
        pbWater.setMax(maxWater);
        pbSleep.setMax(maxSleep);

        List<LogEntry> logs = storage.getAllLogs();
        LogEntry latestLog = null;
        if (!logs.isEmpty()) {
            latestLog = logs.get(logs.size() - 1); // Get the most recent entry
        }

        if (latestLog != null) {
            int steps = latestLog.getSteps();
            int water = latestLog.getWater();
            int sleep = (int) latestLog.getSleep(); // ProgressBar needs int

            pbSteps.setProgress(steps);
            pbWater.setProgress(water);
            pbSleep.setProgress(sleep);

            tvSteps.setText(String.format(Locale.getDefault(), "%d / %d", steps, maxSteps));
            tvWater.setText(String.format(Locale.getDefault(), "%d / %d ml", water, maxWater));
            tvSleep.setText(String.format(Locale.getDefault(), "%d / %d hrs", sleep, maxSleep));
        } else {
            // No data available, show defaults
            pbSteps.setProgress(0);
            pbWater.setProgress(0);
            pbSleep.setProgress(0);

            tvSteps.setText(String.format(Locale.getDefault(), "0 / %d", maxSteps));
            tvWater.setText(String.format(Locale.getDefault(), "0 / %d ml", maxWater));
            tvSleep.setText(String.format(Locale.getDefault(), "0 / %d hrs", maxSleep));
        }
    }
}