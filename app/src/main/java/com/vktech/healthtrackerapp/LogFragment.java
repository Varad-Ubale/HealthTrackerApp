package com.vktech.healthtrackerapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogFragment extends Fragment implements LogAdapter.OnLogActionListener {

    private List<LogEntry> logList = new ArrayList<>();
    private RecyclerView rvLogs;
    private LogAdapter adapter;
    private Storage storage;
    private FloatingActionButton btnAddEntry;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        rvLogs = view.findViewById(R.id.rvLogs);
        btnAddEntry = view.findViewById(R.id.fabAdd);

        storage = new Storage(getContext());
        logList.addAll(storage.getAllLogs());

        adapter = new LogAdapter(getContext(), logList, this);

        rvLogs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLogs.setAdapter(adapter);

        btnAddEntry.setOnClickListener(v -> showLogDialog(null));

        return view;
    }

    private void showLogDialog(final LogEntry entryToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_log, null);
        builder.setView(dialogView);

        final EditText etSteps = dialogView.findViewById(R.id.etSteps);
        final EditText etWater = dialogView.findViewById(R.id.etWater);
        final EditText etSleep = dialogView.findViewById(R.id.etSleep);
        final EditText etNotes = dialogView.findViewById(R.id.etNotes);

        builder.setTitle(entryToEdit == null ? "Add New Log" : "Edit Log");

        if (entryToEdit != null) {
            etSteps.setText(String.valueOf(entryToEdit.getSteps()));
            etWater.setText(String.valueOf(entryToEdit.getWater()));
            etSleep.setText(String.valueOf(entryToEdit.getSleep()));
            etNotes.setText(entryToEdit.getNotes());
        }

        builder.setPositiveButton(entryToEdit == null ? "Add" : "Save", (dialog, which) -> {
            try {
                int steps = Integer.parseInt(etSteps.getText().toString());
                int water = Integer.parseInt(etWater.getText().toString());
                float sleep = Float.parseFloat(etSleep.getText().toString());
                String notes = etNotes.getText().toString();
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                if (entryToEdit == null) {
                    // Add new entry
                    long id = System.currentTimeMillis(); // Use timestamp as a unique ID
                    LogEntry newEntry = new LogEntry(id, currentDate, steps, water, sleep, notes);
                    storage.addLog(newEntry);
                    logList.add(newEntry);
                    adapter.notifyItemInserted(logList.size() - 1);
                    rvLogs.scrollToPosition(logList.size() - 1);
                } else {
                    // Update existing entry
                    entryToEdit.setSteps(steps);
                    entryToEdit.setWater(water);
                    entryToEdit.setSleep(sleep);
                    entryToEdit.setNotes(notes);
                    storage.updateLog(entryToEdit);
                    int position = logList.indexOf(entryToEdit);
                    if (position != -1) {
                        adapter.notifyItemChanged(position);
                    }
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void onEdit(LogEntry entry) {
        showLogDialog(entry);
    }

    @Override
    public void onDelete(LogEntry entry) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Log")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    int position = logList.indexOf(entry);
                    if (position != -1) {
                        storage.deleteLog(entry);
                        logList.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}