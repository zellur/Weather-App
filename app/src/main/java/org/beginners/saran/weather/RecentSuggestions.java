package org.beginners.saran.weather;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by saran on 1/4/2018.
 */

public class RecentSuggestions extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY="org.beginners.saran.weather.RecentSuggestions";
    public static final int MODE=DATABASE_MODE_QUERIES;

    public RecentSuggestions() {
        setupSuggestions(AUTHORITY,MODE);
    }
}
