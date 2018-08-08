package something.ru.locationphotofinder.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PlaceAutocompleteAdapter extends ArrayAdapter<AutocompletePrediction>
        implements Filterable {

    private static final String TAG = PlaceAutocompleteAdapter.class.getSimpleName();
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    private ArrayList<AutocompletePrediction> resultList;
    private GeoDataClient geoDataClient;
    private LatLngBounds searchBounds;
    private AutocompleteFilter placeFilter;
    private Filter filter;


    public PlaceAutocompleteAdapter(Context context, GeoDataClient geoDataClient,
                                    LatLngBounds searchBounds, AutocompleteFilter placeFilter) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        this.geoDataClient = geoDataClient;
        this.searchBounds = searchBounds;
        this.placeFilter = placeFilter;
        filter = new Filter();
    }

    @Override
    public int getCount() {
        return resultList == null ? 0 : resultList.size();
    }

    @Override
    public AutocompletePrediction getItem(int position) {
        return resultList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        AutocompletePrediction item = getItem(position);
        if (item != null) {
            TextView textView1 = row.findViewById(android.R.id.text1);
            TextView textView2 = row.findViewById(android.R.id.text2);

         /* Sets the primary and secondary text for a row.
            Note that getPrimaryText() and getSecondaryText () return a CharSequence that may contain
            styling based on the given CharacterStyle.*/
            textView1.setText(item.getPrimaryText(STYLE_BOLD));
            textView2.setText(item.getSecondaryText(STYLE_BOLD));
        }
        return row;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }


    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        Task<AutocompletePredictionBufferResponse> results =
                geoDataClient.getAutocompletePredictions(constraint.toString(), searchBounds,
                        placeFilter);
        try {
            Tasks.await(results, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            AutocompletePredictionBufferResponse autocompletePredictions = results.getResult();
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        } catch (RuntimeExecutionException e) {

            Toast.makeText(getContext(), "Error contacting API: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    class Filter extends android.widget.Filter {
        @Override
        protected android.widget.Filter.FilterResults performFiltering(CharSequence constraint) {
            android.widget.Filter.FilterResults results = new android.widget.Filter.FilterResults();

            ArrayList<AutocompletePrediction> filterData = new ArrayList<>();
            if (constraint != null) {
                filterData = getAutocomplete(constraint);
            }

            results.values = filterData;
            if (filterData != null) {
                results.count = filterData.size();
            } else {
                results.count = 0;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, android.widget.Filter.FilterResults
                results) {
            if (results != null && results.count > 0) {
                resultList = (ArrayList<AutocompletePrediction>) results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            if (resultValue instanceof AutocompletePrediction) {
                return ((AutocompletePrediction) resultValue).getFullText(null);
            } else {
                return super.convertResultToString(resultValue);
            }
        }
    }
}
