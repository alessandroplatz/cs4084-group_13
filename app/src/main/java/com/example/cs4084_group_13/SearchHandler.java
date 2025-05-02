// SearchHandler.java
package com.example.cs4084_group_13;

import android.widget.Filter;
import java.util.ArrayList;

public class SearchHandler {
    private final CustomAdaptor adapter;
    private final ArrayList<Integer> originalIds;
    private final ArrayList<String> originalNames;

    public SearchHandler(CustomAdaptor adapter,
                         ArrayList<Integer> originalIds,
                         ArrayList<String> originalNames) {
        this.adapter = adapter;
        this.originalIds = new ArrayList<>(originalIds); // 创建原始数据副本
        this.originalNames = new ArrayList<>(originalNames);
    }

    public void filter(String query) {
        ArrayList<Integer> filteredIds = new ArrayList<>();
        ArrayList<String> filteredNames = new ArrayList<>();

        if (query.isEmpty()) {
            filteredIds.addAll(originalIds);
            filteredNames.addAll(originalNames);
        } else {
            String lowerQuery = query.toLowerCase();
            for (int i = 0; i < originalNames.size(); i++) {
                if (originalNames.get(i).toLowerCase().contains(lowerQuery)) {
                    filteredIds.add(originalIds.get(i));
                    filteredNames.add(originalNames.get(i));
                }
            }
        }
        adapter.updateData(filteredIds, filteredNames);
    }
}
